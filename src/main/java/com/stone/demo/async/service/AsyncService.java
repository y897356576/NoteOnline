package com.stone.demo.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 石头 on 2017/6/20.
 */
@Service
//@EnableAsync
public class AsyncService {

    @Async
    public void asyncMethod_fir_assist(List<Integer> list){
        try {
            Thread.sleep(3000);
            for (Integer i : list){
                System.out.println("Count:" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
