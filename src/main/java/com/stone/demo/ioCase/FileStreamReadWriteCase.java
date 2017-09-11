package com.stone.demo.ioCase;

import java.io.*;

/**
 * Created by 石头 on 2017/7/6.
 */
public class FileStreamReadWriteCase {

    public static void main(String[] args){
        Class clazz = new FileStreamReadWriteCase().getClass();
        String rootPath = clazz.getResource("/").getFile();
        String hereClassPath = clazz.getResource("").getFile() + clazz.getSimpleName() + ".class";
        String hereJavaPath = "E:\\Projects\\mavenCase_fir\\src\\main\\java\\com\\stone\\demo\\ioCase\\FileStreamReadWriteCase.java";
//        getFiles(rootPath);
//        readFileByBufferedInputStream(hereJavaPath);
//        readFileByBufferedReader(hereJavaPath);
//        readFileByStringReader("Hello World!");
        writeFileByBufferedWriter(hereJavaPath);
    }

    private static File[] getFiles(String filePath){
        File file = new File(filePath);
        File[] files = file.listFiles();
        System.out.println("ClassesPosition : " + filePath);
        System.out.println("FileGetName : " + file.getName());
        for(File fileSingle : files){
            System.out.println("FileName : " + fileSingle.getName());
        }
        return files;
    }

    private static String readFileByBufferedInputStream(String fileName){
        String result = "";
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            is = new FileInputStream(fileName);
            bis = new BufferedInputStream(is);  //可使用缓存，速度较快
            int l = bis.available();
            byte[] bytes = new byte[l];
            bis.read(bytes);
            result = new String(bytes, "utf-8");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }

    private static String readFileByBufferedReader(String fileName){
        StringBuilder result = new StringBuilder();
        Reader r = null;
        BufferedReader br = null;
        try {
            r = new FileReader(fileName);
            br = new BufferedReader(r);
            String temp;
            while((temp = br.readLine()) != null){
                result.append(temp).append("\r\n");
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                br.close();
                r.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println(result);
        return result.toString();
    }

    private static void readFileByStringReader(String str){
        StringReader sr = new StringReader(str);
        try{
            int i = 100;
            while(i-- > 0){
                System.out.println((char)sr.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFileByBufferedWriter(String pathName) {
        BufferedWriter bw = null;
        try{
            String content = readFileByBufferedReader(pathName);
            content = content.replaceAll("FileReadWriteCase", "FileStreamReadWriteCaseCopy1");
            bw = new BufferedWriter(new FileWriter(pathName.replace(".java", "Copy.java")));
            bw.write(content, 0, content.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
