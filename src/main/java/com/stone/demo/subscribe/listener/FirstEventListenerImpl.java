package com.stone.demo.subscribe.listener;

import com.stone.demo.subscribe.event.FirstEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by 石头 on 2017/6/20.
 */
@Component
public class FirstEventListenerImpl implements ApplicationListener<FirstEvent> {

    @Override
    public void onApplicationEvent(FirstEvent firstEvent) {
        System.out.println("Listener_Impl_Source:" + firstEvent.getSource());
        System.out.println("Listener_Impl_Content:" + firstEvent.getContent());
    }

}
