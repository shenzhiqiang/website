package com.web.core.tool.MQ;

import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by shenzhiqiang on 16/3/9.
 */
public class MQDelProducer extends RMQ {
    public MQDelProducer(String exName, String exType, String host, int port, String userName, String password) throws IOException {
        super("", "", exName, exType, host, port, userName, password);
    }

    public void sendMessage(Serializable object, String bindKey) throws IOException {
        channel.basicPublish(this.exName, bindKey, null, SerializationUtils.serialize(object));
    }

}
