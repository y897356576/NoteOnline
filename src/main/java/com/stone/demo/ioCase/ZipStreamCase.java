package com.stone.demo.ioCase;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * Created by 石头 on 2017/7/11.
 */
public class ZipStreamCase {

    public static void main(String[] args) {
//        String filePath = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\ZipStreamCase.java";
//        String outputPathGzip = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\ZipStreamCaseCopy.java.zip";
//        doGZipCase(filePath, outputPath);
//        readGZipCase(outputPath);
        String[] strs = new String[5];
        strs[0] = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\EchoCase.java";
        strs[1] = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\FileStreamReadWriteCase.java";
        strs[2] = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\ImgReaderCase.java";
        strs[3] = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\NioChannelCase.java";
        strs[4] = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\RedirectCase.java";
        String outputPathZip = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\IOZipCase.zip";
        doZipCase(strs, outputPathZip);
        readZipCase(outputPathZip);
    }

    private static void doGZipCase(String filePath, String outputPath){
        BufferedReader reader = null;
        BufferedOutputStream bos = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            bos = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outputPath)));
            String content = "";
            while((content = reader.readLine()) != null) {
                bos.write((content+"\r\n").getBytes("utf-8"));
            }
            System.out.println("doZip end");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                bos.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

    private static void readGZipCase(String filePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))));
            int i;
            while((i = reader.read()) != -1) {
                System.out.print((char)i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doZipCase(String[] filePaths, String outputPath) {
        FileOutputStream fos = null;
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(outputPath);
            cos = new CheckedOutputStream(fos, new CRC32());  // Adler32/CRC32数据校验方式，通过比较输出流与输入流的Checksum是否相等验证完整性
            zos = new ZipOutputStream(cos);
            bos = new BufferedOutputStream(zos);

            System.out.println("cos Checksum : " + cos.getChecksum().getValue());

            zos.setComment("do zipStream case");  //设置压缩包注释
            for (String filePath : filePaths) {
                File file = new File(filePath);
                System.out.println("Writing file : " + filePath);

                BufferedReader reader = new BufferedReader(new FileReader(file));
                zos.putNextEntry(new ZipEntry(file.getName()));
                int c;
                while ((c = reader.read()) != -1) {
                    bos.write(c);  //只起缓冲作用
                }
                reader.close();
                bos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                zos.close();
                cos.close();
                fos.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

    private static void readZipCase(String filePath) {
        FileInputStream fis = null;
        CheckedInputStream cis = null;
        ZipInputStream zis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(filePath);
            cis = new CheckedInputStream(fis, new CRC32());  // Adler32/CRC32数据校验方式，通过比较输出流与输入流的Checksum是否相等验证完整性
            zis = new ZipInputStream(cis);
            bis = new BufferedInputStream(zis);
            System.out.println("cis Checksum : " + cis.getChecksum().getValue());

            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                System.out.println("Reading file : " + ze);
                int i;
                while ((i = bis.read()) != -1) {
                    System.out.print((char)i);
                }
            }

            /*System.out.println("ZipFile Test");
            ZipFile zf = new ZipFile(filePath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();
                System.out.println("File : " + ze2);
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                zis.close();
                cis.close();
                fis.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

}
