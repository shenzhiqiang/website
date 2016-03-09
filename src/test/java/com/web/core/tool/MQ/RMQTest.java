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
//        new MQExConsumerDelProd("", "key", "exname", "direct", "localhost", 5672);
//
//        MQDelProducer producer = new MQDelProducer("exname", "direct", "localhost", 5672);
//
//        for (int i = 0; i < 5; i++) {
//            HashMap message = new HashMap();
//            message.put("del_id", i);
//            producer.sendMessage(message, "key");
//            System.out.println("del_id: "+ i +" sent.");
//        }
    }
}