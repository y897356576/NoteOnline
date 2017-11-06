package com.stone.demo.payment.service.impl.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.stone.demo.payment.service.exception.DownloadAccountException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by sunqian on 2017/10/11.
 */
@Component
public class AccountAnalyzer {
    private Logger logger = LoggerFactory.getLogger("AccountAnalyzer");

    @Value("#{unionPayProperties['acpsdk.accountStorePath']}")
    private String accountStorePath;
    @Value("#{unionPayProperties['acpsdk.encoding']}")
    private String acpEncoding;


    public List<Map<Integer, String>> generateAccount(JSONObject accountResult) throws DownloadAccountException {
        byte[] fileContent = inflater(Base64.decodeBase64(accountResult.getString("fileContent")));
        File zipFile = save(fileContent, fileName(accountResult));
        List<String> fileList = unzip(zipFile, accountStorePath);
        List<Map<Integer, String>> result = new ArrayList<>();
        for (String file : fileList) {
            if (file.contains("ZM_")) {
                result = parseZMFile(file);
            }
        }
        return result;
    }


    /**
     * 解压缩.
     *
     * @param inputByte byte[]数组类型的数据
     * @return 解压缩后的数据
     * @throws IOException
     */
    private byte[] inflater(final byte[] inputByte) throws DownloadAccountException {
        int compressedDataLength = 0;
        Inflater compresser = new Inflater(false);
        compresser.setInput(inputByte, 0, inputByte.length);
        ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.inflate(result);
                if (compressedDataLength == 0) {
                    break;
                }
                o.write(result, 0, compressedDataLength);
            }
        } catch (Exception ex) {
            throw new DownloadAccountException("解压文件内容失败", ex);
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                logger.warn("关闭文件流失败");
            }
        }
        compresser.end();
        return o.toByteArray();
    }

    private String fileName(JSONObject accountResult) {
        if (accountResult.containsKey("fileName") == false || StringUtils.isEmpty(accountResult.getString("fileName"))) {
            return accountResult.getString("merId")
                    + "_" + accountResult.getString("batchNo") + "_"
                    + accountResult.getString("txnTime") + ".txt";
        } else {
            return accountResult.getString("fileName");
        }
    }

    /**
     * 功能：解析交易返回的fileContent字符串并落地 （ 解base64，解DEFLATE压缩并落地）<br>
     * 适用到的交易：对账文件下载，批量交易状态查询<br>
     *
     * @param fileContent 内容
     * @param fileName    文件名
     */
    private File save(byte[] fileContent, String fileName) throws DownloadAccountException {
        FileOutputStream out = null;
        try {
            File file = new File(accountStorePath + File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            out.write(fileContent, 0, fileContent.length);
            out.flush();
            return file;
        } catch (UnsupportedEncodingException e) {
            throw new DownloadAccountException("存储对账文件异常，" + e.getMessage());
        } catch (IOException e) {
            throw new DownloadAccountException("存储对账文件异常，" + e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.warn("存储对账文件异常，" + e.getMessage());
            }
        }
    }

    private List<String> unzip(File file, String path) throws DownloadAccountException {
        try {
            ZipFile zipFile = new ZipFile(file);
            return unzipEnumeration(zipFile, path);
        } catch (IOException e) {
            throw new DownloadAccountException("解压对账文件失败，" + e.getMessage());
        }
    }

    private List<String> unzipEnumeration(ZipFile zipFile, String folder) throws DownloadAccountException {
        List<String> fileList = new ArrayList<>();
        Enumeration zipEnum = zipFile.entries();
        while (zipEnum.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipEnum.nextElement();
            File entryFile = new File(folder + File.separator + entry.getName());
            if (entry.isDirectory()) {
                entryFile.mkdirs();
                continue;
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = zipFile.getInputStream(entry);
                outputStream = new FileOutputStream(entryFile);
                int n = 0;
                byte[] bb = new byte[1024];
                while ((n = inputStream.read(bb)) != -1) {
                    outputStream.write(bb, 0, n);
                }
                fileList.add(entryFile.getPath());
            } catch (IOException e) {
                throw new DownloadAccountException("解压对账文件失败，" + e.getMessage());
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                    if (outputStream != null) outputStream.close();
                } catch (IOException e) {
                    logger.warn("关闭文件流失败，" + e.getMessage());
                }
            }
        }
        return fileList;
    }

    /**
     * 功能：解析全渠道商户对账文件中的ZM文件并以List<Map>方式返回
     * 适用交易：对账文件下载后对文件的查看
     *
     * @param filePath ZM文件全路径
     * @return 包含每一笔交易中 序列号 和 值 的map序列
     */
    private List<Map<Integer, String>> parseZMFile(String filePath) throws DownloadAccountException {
        int lengthArray[] = {3, 11, 11, 6, 10, 19, 12, 4, 2, 21, 2, 32, 2, 6, 10, 13, 13, 4, 15, 2, 2, 6, 2, 4, 32, 1, 21, 15, 1, 15, 32, 13, 13, 8, 32, 13, 13, 12, 2, 1, 32, 98};
        return parseFile(filePath, lengthArray);
    }

    /**
     * 功能：解析全渠道商户 ZM,ZME对账文件
     *
     * @param filePath
     * @param lengthArray 参照《全渠道平台接入接口规范 第3部分 文件接口》 全渠道商户对账文件 6.1 ZM文件和6.2 ZME 文件 格式的类型长度组成int型数组
     * @return
     */
    private List<Map<Integer, String>> parseFile(String filePath, int lengthArray[]) throws DownloadAccountException {
        List<Map<Integer, String>> ZmDataList = new ArrayList<>();
        File file = new File(filePath);
        if (file.exists() == false || file.isFile() == false)
            throw new DownloadAccountException("下载的对账文件不存在");

        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(fileInputStream, "iso-8859-1");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                byte[] bs = lineTxt.getBytes("iso-8859-1");
                //解析的结果MAP，key为对账文件列序号，value为解析的值
                Map<Integer, String> ZmDataMap = new LinkedHashMap<Integer, String>();
                //左侧游标
                int leftIndex = 0;
                //右侧游标
                int rightIndex = 0;
                for (int i = 0; i < lengthArray.length; i++) {
                    rightIndex = leftIndex + lengthArray[i];
                    String filed = new String(Arrays.copyOfRange(bs, leftIndex, rightIndex), "gbk");
                    leftIndex = rightIndex + 1;
                    ZmDataMap.put(i, filed);
                }
                ZmDataList.add(ZmDataMap);
            }
        } catch (Exception e) {
            throw new DownloadAccountException("读取对账文件内容异常，" + e.getMessage());
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.warn("关闭文件流失败，" + e.getMessage());
                }
        }
        return ZmDataList;
    }
}
