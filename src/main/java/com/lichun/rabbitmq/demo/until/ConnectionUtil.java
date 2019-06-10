package com.lichun.rabbitmq.demo.until;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    public static Connection getConnection() throws TimeoutException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost("/test");

        return connectionFactory.newConnection();

    }
}
