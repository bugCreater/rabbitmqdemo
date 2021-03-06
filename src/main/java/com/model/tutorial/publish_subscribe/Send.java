package com.model.tutorial.publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lilongjie on 16/8/15.
 */
public class Send {
    private final static String EXCHANGE_NAME = "test_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String message = "hello";
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("Sent:" + message);
        channel.close();
        connection.close();
    }
}
