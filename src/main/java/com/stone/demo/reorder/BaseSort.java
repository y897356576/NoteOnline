package com.stone.demo.reorder;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by 石头 on 2017/7/19.
 */
public class BaseSort {

    public static void main(String[] args) {
        Integer[] ints = new Integer[]{1, 3, 2, 5, 4, 3};

        int len = 100000;
        Integer[] ints1 = new Integer[len];
        Random random = new Random(47);
        for (int i = 0; i < len; i++) {
            ints1[i] = random.nextInt(100000);
        }

        Long date = new Date().getTime();

//        doBubbleSort(ints);
//        System.out.println(Arrays.toString(doBubbleSort(ints)));
//        doSelectSort(ints);
        System.out.println(Arrays.toString(doSelectSort(ints1)));

        System.out.println((new Date().getTime()-date));
    }

    private static Integer[] doBubbleSort(Integer[] ints) {
        System.out.println("bubbleSort method started");

        Integer index = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints.length-1-index; j++) {
                if (ints[j] > ints[j+1]) {
                    Integer temp = ints[j];
                    ints[j] = ints[j+1];
                    ints[j+1] = temp;
                }
            }
            index++;
        }
        return ints;
    }

    private static Integer[] doSelectSort(Integer[] ints) {
        System.out.println("selectSort method started");

        Integer tempContent = 0;
        Integer minIndex = 0;
        for (int i = 0; i < ints.length; i++) {
            tempContent = ints[i];
            minIndex = i;
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[j] < tempContent ) {
                    tempContent = ints[j];
                    minIndex = j;
                }
            }
            ints[minIndex] = ints[i];
            ints[i] = tempContent;
        }
        return ints;
    }

}
