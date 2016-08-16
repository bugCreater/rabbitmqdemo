package com.model.tutorial.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lilongjie on 16/8/15.
 */
public class Reciever2 {
    private final static String EXCHANGE_NAME = "test_exchange";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.230.128");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"1234");
        System.out.println("Waiting for message.To exit press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 指定接收者，第二个参数为自动应答，无需手动应答
        channel.basicConsume(queueName, true, consumer);
        while (true)
        {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
