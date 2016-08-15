package com.model.tutorial.publish_subscribe;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lilongjie on 16/8/15.
 */
public class Reciever {
    private final static String EXCHANGE_NAME = "test_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queueName = channel.queueDeclare().getQueue();
        System.out.println(queueName);
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("Waiting for message.To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("recieve:" + message);
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }
}
