package com.stone.demo.ioCase;

import java.io.*;

/**
 * Created by 石头 on 2017/7/7.
 */
public class RedirectCase {

    public static void main(String[] args) throws IOException {
        PrintStream console = System.out;
        InputStream is = new BufferedInputStream(new FileInputStream(
                new File("E:\\Projects\\mavenCase_fir\\src\\main\\java\\com\\stone\\demo\\ioCase\\RedirectCase.java")));
        OutputStream os = new BufferedOutputStream(new FileOutputStream(
                new File("E:\\Projects\\mavenCase_fir\\src\\main\\java\\com\\stone\\demo\\ioCase\\RedirectCaseCopy.java")));
        System.setOut(new PrintStream(os));
        String str = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while((str = br.readLine()) != null) {
            System.out.println(str);
        }
        is.close();
        os.close();
        System.setOut(console);
    }

}
