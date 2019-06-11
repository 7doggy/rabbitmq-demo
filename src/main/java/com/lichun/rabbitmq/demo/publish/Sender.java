package com.lichun.rabbitmq.demo.publish;

import com.lichun.rabbitmq.demo.until.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

    private static final String EXCHANGE_NAME = "testexchange";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //定义一个交换机，publish/subscribe模式必须用fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //发布订阅模式，消息是先发布到交换机中，而交换机是没有保存功能的，所以如果没有消费者，消息会丢失。
        channel.basicPublish(EXCHANGE_NAME, "", null, "发布订阅模式的消息".getBytes());

        channel.close();
        connection.close();

    }
}
