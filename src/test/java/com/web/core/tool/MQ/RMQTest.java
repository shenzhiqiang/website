package com.web.core.tool.MQ;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by shenzhiqiang on 16/2/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class RMQTest extends TestCase {

    @Test
    public void TEST() throws Exception{
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