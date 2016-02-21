package com.web.core.tool;

import com.web.core.tool.MQ.MQConsumerDelImg;
import com.web.core.tool.MQ.MQProducer;

import java.util.HashMap;

/**
 * Created by shenzhiqiang on 16/2/18.
 */
public class TEST {
    public TEST() throws Exception{
        MQConsumerDelImg consumer = new MQConsumerDelImg("queue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        MQProducer producer = new MQProducer("queue");

        for (int i = 0; i < 5; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number "+ i +" sent.");
        }
    }
}
