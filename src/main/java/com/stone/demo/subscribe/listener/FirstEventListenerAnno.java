package com.stone.demo.subscribe.listener;

import com.stone.demo.subscribe.event.FirstEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by 石头 on 2017/6/20.
 */
@Component
public class FirstEventListenerAnno {

    @EventListener(condition = "#firstEvent.content.length()>0")
    private void eventListener(FirstEvent firstEvent){
        System.out.println("Listener_Anno_Source:" + firstEvent.getSource());
        System.out.println("Listener_Anno_Content:" + firstEvent.getContent());
    }

}
