package com.stone.demo.subscribe.handler;

import com.stone.demo.subscribe.event.FirstEvent;
import com.stone.demo.subscribe.publisher.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by 石头 on 2017/6/20.
 */
@Controller
@RequestMapping("/listenerHandler")
public class FirstEventReleaseController {

    @Autowired
    private EventPublisher publisher;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void doFirstEventReleaseTest1(){
        publisher.doEventPublish(new FirstEvent(this, "[GET]:from listenerTest controller"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doFirstEventReleaseTest2(HttpServletResponse response){
        publisher.doEventPublish(new FirstEvent(this, "[POST]:from listenerTest controller"));
    }

}
