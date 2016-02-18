package com.web.core.tool.MQ;

import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by shenzhiqiang on 16/2/18.
 */
public class MQProducer extends RMQ {

    public MQProducer(String endPointName) throws IOException{
        super(endPointName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
    }

}
