package com.stone.demo.subscribe.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by 石头 on 2017/6/20.
 */
public class FirstEvent extends ApplicationEvent {

    private String content;

    public String getContent(){
        return content;
    }

    public FirstEvent(Object source, String content) {
        super(source);
        this.content = content;
    }

}
