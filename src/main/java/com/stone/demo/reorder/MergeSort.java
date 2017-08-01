package com.stone.demo.reorder;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by 石头 on 2017/8/1.
 */
public class MergeSort {

    public static void main(String[] args) {
        Integer[] ints = new Integer[]{1, 3, 2, 5, 4, 6, 7, 8, 3, 5, 0, -1};

        int len = 100000;
        Integer[] ints1 = new Integer[len];
        Random random = new Random(47);
        for (int i = 0; i < len; i++) {
            ints1[i] = random.nextInt(100000);
        }

        Long date = new Date().getTime();

        System.out.println(Arrays.toString(mergeSort(ints1)));

        System.out.println((new Date().getTime()-date));
    }

    private static Integer[] mergeSort(Integer[] is) {
        int len = is.length;
        if ( len/2 < 1 ) {
            return is;
        }

        Integer[] leftArrs = Arrays.copyOfRange(is, 0, len/2);
        Integer[] rightArrs = Arrays.copyOfRange(is, len/2, len);
        leftArrs = mergeSort(leftArrs);
        rightArrs = mergeSort(rightArrs);

        Integer[] mergeArrs = new Integer[leftArrs.length + rightArrs.length];

        Integer leftIndex = 0;
        Integer rightIndex = 0;
        A: for (int i = 0; i < leftArrs.length + rightArrs.length; i++) {
            if (leftIndex == leftArrs.length) {
                for (int j = rightIndex; j < rightArrs.length; j++) {
                    mergeArrs[leftIndex + j] = rightArrs[j];
                }
                break A;
            }
            if (rightIndex == rightArrs.length) {
                for (int j = leftIndex; j < leftArrs.length; j++) {
                    mergeArrs[rightIndex + j] = leftArrs[j];
                }
                break A;
            }

            if (leftArrs[leftIndex] <= rightArrs[rightIndex]) {
                mergeArrs[leftIndex + rightIndex] = leftArrs[leftIndex];
                leftIndex++;
            } else {
                mergeArrs[leftIndex + rightIndex] = rightArrs[rightIndex];
                rightIndex++;
            }
        }

        return mergeArrs;
    }

}
