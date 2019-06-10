package com.lichun.rabbitmq.demo.hello;

import com.lichun.rabbitmq.demo.until.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    private final static String QUEUE_NAME = "testhello"; //队列名字

    public static void main(String[] args) throws TimeoutException, IOException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //声明队列，如果队列存在则什么都不做，如果不存在才创建。
        //参数1：队列名字
        //参数2：是否持久化队列，队列默认在内存中
        //参数3：是否排外，有两个作用，第一个，当我们的连接关闭后，是否会自动关闭队列；
        // 作用二，是否私有当前队列，如果私有了，其他通道不可以访问当前队列。如果为true，一般时是一个队列只适用于一个消费者的时候。
        //参数4：是否自动删除
        //参数5；一些其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送内容
        channel.basicPublish("", QUEUE_NAME, null, "发送的消息".getBytes());

        //关闭连接
        channel.close();
        connection.close();
    }
}
