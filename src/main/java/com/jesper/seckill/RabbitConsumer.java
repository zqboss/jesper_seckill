package com.jesper.seckill;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

//消费者
public class RabbitConsumer {

  private final static String QUEUE_NAME = "Hello.rabbitMQ"; //消息队列名

  public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
          factory.setHost("192.168.97.53"); // 虚拟机地址
          factory.setPort(5672); // 端口号
          factory.setUsername("zqboss"); // 用户名
          factory.setPassword("zengqiao"); // 密码
       // 打开连接和创建频道，与发送端一样  
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
     // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。  
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
      // 创建队列消费者  
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 指定消费队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {  //消费者程序运行开着 如果生产者新增了数据会自动获取
          Thread.sleep(500);
             // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          System.out.println("'[x] Received '" + message );
  }   
  }
}