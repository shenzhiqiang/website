package com.web.core.tool.MQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


/**
 * Created by shenzhiqiang on 16/2/18.
 */
public abstract class RMQ{

    private static Log logger = LogFactory.getLog(RMQ.class);
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    protected String exName;
    protected String exType;

    public RMQ(String endpointName, String host, int port, String userName, String password) throws IOException{
        this.endPointName = endpointName;

        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();

        //hostname of your rabbitmq server
        factory.setHost(host);
        factory.setPort(port);

        factory.setUsername(userName);
        factory.setPassword(password);

        //getting a connection
        connection = factory.newConnection();

        //creating a channel
        channel = connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare(endpointName, false, false, false, null);
    }

    public RMQ(String endpointName, String bindKey, String exName, String exType, String host, int port, String userName, String password) throws IOException{
        this.endPointName = endpointName;
        this.exName = exName;
        this.exType = exType;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);

        factory.setUsername(userName);
        factory.setPassword(password);

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(exName, exType);

        if (!bindKey.equals("")) {
            if (!endpointName.equals(""))
                channel.queueDeclare(endpointName, false, false, false, null);
            else
                this.endPointName = channel.queueDeclare().getQueue();

            channel.queueBind(endpointName, exName, bindKey);
        }
    }

    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws IOException
     */
    public void close() throws IOException{
        this.channel.close();
        this.connection.close();
    }
}
