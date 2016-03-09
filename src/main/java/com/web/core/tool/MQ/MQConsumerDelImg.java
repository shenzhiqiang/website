package com.web.core.tool.MQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.web.core.entity.ProductsTable;
import com.web.core.tool.SCSTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/3/10.
 */
public class MQConsumerDelImg extends RMQ implements Runnable, Consumer {
    private static Log logger = LogFactory.getLog(MQConsumerDelImg.class);

    @Resource
    MQDelProducer mqDelProducer;

    boolean ack = false; // message acknowledgments


    public MQConsumerDelImg(String endpointName, String bindKey, String exName, String exType, String host, int port) throws IOException {
        super(endpointName, bindKey, exName, exType, host, port);
        Thread consumerThread = new Thread(this);
        consumerThread.start();
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, ack, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer ImgDel" + consumerTag + " registered");
    }

    public boolean delImg(String imgUrl) {
        String[] imgSplit = imgUrl.split("/");
        if (imgSplit.length > 0) {
            String filename = imgSplit[imgSplit.length - 1];
            if (!SCSTool.delObject(filename)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               AMQP.BasicProperties props, byte[] body) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(body);
        String delUrl = (String) map.get("del_url");
        if (!delImg(delUrl)) {
            mqDelProducer.sendMessage(body, "del_img_key");
        }

        logger.info("RMQ Img Del URL: " + delUrl);
        channel.basicAck(env.getDeliveryTag(), false);
    }

    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}

}
