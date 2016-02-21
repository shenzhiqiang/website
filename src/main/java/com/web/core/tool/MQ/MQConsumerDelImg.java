package com.web.core.tool.MQ;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.springframework.util.SerializationUtils;

/**
 * Created by shenzhiqiang on 16/2/18.
 */
public class MQConsumerDelImg extends RMQ implements Runnable, Consumer {
    public MQConsumerDelImg(String endPointName) throws IOException{
        super(endPointName);
        Thread consumerThread = new Thread(this);
        consumerThread.start();
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            boolean ack = false; // message acknowledgments
            channel.basicConsume(endPointName, ack, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer "+consumerTag +" registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(body);
        System.out.println("Message Number "+ map.get("del_id") + " received.");
        channel.basicAck(env.getDeliveryTag(), false);

    }

    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}

}