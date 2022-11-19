package com.cx.springboot02.controller;

import com.cx.springboot02.common.mq.ProducerSchedule;
import com.cx.springboot02.common.utils.Unobstructed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ProducerSchedule producerSchedule;
    
    @GetMapping("/push")
    @Unobstructed
    public void pushMessageToMQ() {
        producerSchedule.send("Topic", "Coisini");
    }
}