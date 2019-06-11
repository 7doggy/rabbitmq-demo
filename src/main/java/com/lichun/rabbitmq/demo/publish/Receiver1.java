package com.lichun.rabbitmq.demo.publish;

import com.lichun.rabbitmq.demo.until.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

public class Receiver1 {
    private static final String EXCHANGE_NAME = "testexchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        //定义一个交换机，publish/subscribe模式必须用fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        channel.queueDeclare("testpubqueue1", false, false, false, null);
        //绑定队列到交互机
        channel.queueBind("testpubqueue1", EXCHANGE_NAME, "");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "utf-8");
            System.out.println("Receive:" + msg);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume("testpubqueue1", false, deliverCallback, consumerTag -> {
        });

    }
}
