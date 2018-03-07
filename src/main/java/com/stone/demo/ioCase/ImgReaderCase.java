package com.stone.demo.ioCase;

import java.io.*;

/**
 * Created by 石头 on 2017/7/10.
 */
public class ImgReaderCase {

    public static void main(String[] args) {
        String filePath = "E:\\Projects\\noteOnline\\src\\main\\webapp\\img\\bagd-hg.png";
        String outputPath1 = "E:\\Projects\\noteOnline\\src\\main\\webapp\\img\\bagd-hg-copy1.png";
        String outputPath2 = "E:\\Projects\\noteOnline\\src\\main\\webapp\\img\\bagd-hg-copy2.png";
        String outputPath3 = "E:\\Projects\\noteOnline\\src\\main\\webapp\\img\\bagd-hg-copy3.png";
        doCopyByStream(filePath, outputPath1);
        doCopyByReader(filePath, outputPath2);
        doCopyByReaderStream(filePath, outputPath3);
    }

    private static void doCopyByStream(String filePath, String outputPath) {
        InputStream is = null;
        OutputStream os = null;
        try{
            is = new BufferedInputStream(new FileInputStream(filePath));
            os = new BufferedOutputStream(new FileOutputStream(outputPath));
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            os.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

    /**
     * Reader Writer字符流，读取图片视频不可用
     * @param filePath
     * @param outputPath
     */
    private static void doCopyByReader(String filePath, String outputPath) {
        InputStream is = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try{
            is = new BufferedInputStream(new FileInputStream(filePath));
            reader = new BufferedReader(new InputStreamReader(is));
            writer = new BufferedWriter(new FileWriter(outputPath));
            String str = "";
            while((str = reader.readLine()) != null) {
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                reader.close();
                writer.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

    /**
     * Reader Writer字符流，读取图片视频不可用
     * @param filePath
     * @param outputPath
     */
    private static void doCopyByReaderStream(String filePath, String outputPath) {
        InputStream is = null;
        OutputStream os = null;
        BufferedReader reader = null;
        try{
            is = new BufferedInputStream(new FileInputStream(filePath));
            reader = new BufferedReader(new InputStreamReader(is));
            os = new BufferedOutputStream(new FileOutputStream(outputPath));
            int i;
            while((i= reader.read()) != -1) {
                os.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                reader.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

}
