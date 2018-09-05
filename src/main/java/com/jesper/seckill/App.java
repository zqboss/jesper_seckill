package com.jesper.seckill;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class App {
    // 队列名称
    public final static String QUEUE_NAME = "Hello.rabbitMQ";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 配置连接属性
        factory.setHost("192.168.97.53"); // 虚拟机地址
        factory.setPort(5672); // 端口号
        factory.setUsername("zqboss"); // 用户名
        factory.setPassword("zengqiao"); // 密码
        
        // 得到连接，创建通道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // 声明一个叫Hello.rabbitMQ的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message = "spingmvc4556";

        // 发送消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}