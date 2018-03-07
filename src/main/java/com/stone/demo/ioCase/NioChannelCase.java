package com.stone.demo.ioCase;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by 石头 on 2017/7/10.
 */
public class NioChannelCase {

    public static void main(String[] args) {
//        String filePath = "E:\\Projects\\noteOnline\\src\\main\\webapp\\img\\bagd-hg.png";
//        String outputPath = "E:\\Projects\\noteOnline\\src\\main\\webapp\\img\\bagd-hg-copy.png";
        String filePath = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\NioChannelCase.java";
        String outputPath = "E:\\Projects\\noteOnline\\src\\main\\java\\com\\stone\\demo\\ioCase\\NioChannelCaseCopy.java";
//        channelCase(filePath, outputPath);
        transferCase(filePath, outputPath);
    }

    private static void channelCase(String filePath, String outPutPath) {
        FileChannel fci = null;
        FileChannel fco = null;
        try {
            fci = new FileInputStream(new File(filePath)).getChannel();
            fco = new FileOutputStream(new File(outPutPath)).getChannel();
            //FileLock fl = fci.lock();
            //FileLock fl  = fci.lock(0, fci.size(), true);
            //fl.release();
            ByteBuffer bf = ByteBuffer.allocate(1024);

            while(fci.read(bf) != -1) {
                bf.flip();  //反转此缓冲区使缓冲区，做好新序列信道读取或相对get操作的准备
                fco.write(bf);
                bf.clear(); //清除，为下一次写入做好准备
            }

            fco.position(fco.size());
            fco.write(ByteBuffer.wrap("//Append String Test".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fci.close();
                fco.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

    private static void transferCase(String filePath, String outPutPath) {
        FileChannel fci = null;
        FileChannel fco = null;
        try {
            fci = new FileInputStream(new File(filePath)).getChannel();
            fco = new FileOutputStream(new File(outPutPath)).getChannel();
            fci.transferTo(0, fci.size(), fco);
//            fco.transferFrom(fci, 0, fci.size());
            fco.write(ByteBuffer.wrap("//Append String Test".getBytes()), fci.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fci.close();
                fco.close();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }

}
