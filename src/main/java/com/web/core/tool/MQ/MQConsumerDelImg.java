package com.web.core.tool.MQ;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.web.core.dao.IProductsTableDao;
import com.web.core.entity.ProductsTable;
import com.web.core.tool.SCSTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;

/**
 * Created by shenzhiqiang on 16/2/18.
 */
public class MQConsumerDelImg extends RMQ implements Runnable, Consumer {
    private static Log logger = LogFactory.getLog(MQConsumerDelImg.class);

    @Resource
    IProductsTableDao iProductsTableDao;

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
        System.out.println("Consumer " + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        ProductsTable prod = null;
        Map map = null;

        try {
            map = (HashMap) SerializationUtils.deserialize(body);
            prod = iProductsTableDao.selectDelOneById((Integer) map.get("del_id"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        boolean delStatus = false;
        if (prod != null)
            try {
                String[] coverImgSplit = prod.getCover_image_url().split("/");
                if (coverImgSplit.length > 0) {
                    String filename = coverImgSplit[coverImgSplit.length - 1];
                    System.out.println(filename);

                    delStatus = SCSTool.delObject(filename);
                }

                String[] imgUrlsSplit = prod.getImage_urls().split(";");
                for(String imgStr: imgUrlsSplit) {
                    String[] imgSplit = imgStr.split("/");
                    if (imgSplit.length > 0) {
                        String filename = coverImgSplit[imgSplit.length - 1];
                        delStatus = SCSTool.delObject(filename);
                    }
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        if (delStatus == true && prod != null)
            try {
                iProductsTableDao.delOneById((Integer) map.get("del_id"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        logger.info("RMQ Del ID: " + map.get("del_id"));
        channel.basicAck(env.getDeliveryTag(), false);
    }

    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}

}