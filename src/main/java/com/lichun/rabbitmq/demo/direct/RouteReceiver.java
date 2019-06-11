package com.lichun.rabbitmq.demo.direct;

import com.lichun.rabbitmq.demo.until.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

public class RouteReceiver {
    public static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare("directqueue", false, false, false, null);
        //绑定消息队列和keyconsumerTag
        channel.queueBind("directqueue", EXCHANGE_NAME, "key1");
        //可以绑定多个key
        channel.queueBind("directqueue", EXCHANGE_NAME, "key2");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Receive:" + message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume("directqueue", deliverCallback, consumerTag ->{});
    }

}
