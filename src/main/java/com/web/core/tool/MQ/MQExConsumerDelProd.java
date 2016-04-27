package com.web.core.tool.MQ;

import com.rabbitmq.client.AMQP;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/3/10.
 */
public class MQExConsumerDelProd extends RMQ implements Runnable, Consumer {
    private static Log logger = LogFactory.getLog(MQExConsumerDelProd.class);

    @Resource
    IProductsTableDao iProductsTableDao;
    @Resource
    MQDelProducer mqDelProducer;

    boolean ack = false; // message acknowledgments


    public MQExConsumerDelProd(String endpointName, String bindKey, String exName, String exType, String host, int port, String userName, String password) throws IOException {
        super(endpointName, bindKey, exName, exType, host, port, userName, password);
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
        System.out.println("Consumer ProdDel" + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               AMQP.BasicProperties props, byte[] body) throws IOException {
        ProductsTable prod = null;
        Map map = null;

        try {
            map = (HashMap) SerializationUtils.deserialize(body);
            prod = iProductsTableDao.selectDelOneById((Integer) map.get("del_id"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        boolean delStatus = true;
        if (prod != null)
            try {
                String[] coverImgSplit = prod.getCover_image_url().split("/");
                if (!prod.getCover_image_url().equals("")) {
                    if (coverImgSplit.length > 0) {
                        String filename = coverImgSplit[coverImgSplit.length - 1];

                        if (!SCSTool.delObject(filename)) {
                            delStatus = false;
                        }
                    }
                }

                if (!prod.getImage_urls().equals("")) {
                    String[] imgUrlsSplit = prod.getImage_urls().split(";");
                    for(String imgStr: imgUrlsSplit) {
                        String[] imgSplit = imgStr.split("/");
                        if (imgSplit.length > 0) {
                            String filename = imgSplit[imgSplit.length - 1];
                            if (!SCSTool.delObject(filename)) {
                                delStatus = false;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        if (!delStatus) {
            try{
                HashMap message = new HashMap();
                message.put("del_id", (Integer) map.get("del_id"));
                mqDelProducer.sendMessage(message, "del_prod");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        if (delStatus && prod != null)
            try {
                iProductsTableDao.delOneById((Integer) map.get("del_id"));
            } catch (Exception e) {
                try{
                    HashMap message = new HashMap();
                    message.put("del_id", (Integer) map.get("del_id"));
                    mqDelProducer.sendMessage(message, "del_prod");
                } catch (Exception ex) {
                    logger.error(e.getMessage(), ex);
                }
                logger.error(e.getMessage(), e);
            }
        logger.info("RMQ Prod Del ID: " + map.get("del_id"));
        channel.basicAck(env.getDeliveryTag(), false);
    }

    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}

}
