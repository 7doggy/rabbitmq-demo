package com.lichun.rabbitmq.demo.topic;

import com.lichun.rabbitmq.demo.until.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {
    public static final String EXCHANGE_NAME = "testtopic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        channel.basicPublish(EXCHANGE_NAME, "key1.update",null,"topic的消息".getBytes());

        channel.close();
        connection.close();
    }
}
