package com.stone.demo.ioCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 石头 on 2017/7/7.
 */
public class EchoCase {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while((s = br.readLine()) != null && s.length() > 0) {
            System.out.println(s);
        }
    }

}
