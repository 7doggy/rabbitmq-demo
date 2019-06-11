package com.lichun.rabbitmq.demo.topic;

import com.lichun.rabbitmq.demo.until.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {

    public static final String EXCHANGE_NAME = "testtopic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.queueDeclare("topicqueue", false, false, false, null);
        channel.queueBind("topicqueue", EXCHANGE_NAME, "key1.#");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Receive:" + message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };

        channel.basicConsume("topicqueue",deliverCallback, consumerTag -> { });
    }
}
