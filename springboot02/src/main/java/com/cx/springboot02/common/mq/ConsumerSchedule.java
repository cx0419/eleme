package com.cx.springboot02.common.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cx.springboot02.common.E.PayState;
import com.cx.springboot02.common.utils.DateUtils;
import com.cx.springboot02.pojo.Order;
import com.cx.springboot02.service.impl.OrderServiceImpl;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Description MQ消费者
 *      CommandLineRunner 初始化预加载数据
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
@Component
public class ConsumerSchedule implements CommandLineRunner {

    @Value("${rocketmq.consumer.consumer-group}")
    private String consumerGroup;

    @Value("${rocketmq.namesrv-addr}")
    private String nameSrvAddr;

    @Autowired
    OrderServiceImpl orderService;

    private static final Logger log = LoggerFactory.getLogger(Logger.class);

    public void messageListener() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.consumerGroup);
        consumer.setNamesrvAddr(this.nameSrvAddr);

        /**
         * 订阅主题
         */
        consumer.subscribe("Topic", "*");

        /**
         * 设置消费消息数
         */
        consumer.setConsumeMessageBatchMaxSize(1);

        /**
         * 设置消费模式
         */
        consumer.setMessageModel(MessageModel.BROADCASTING);//设置广播消费模式
        /**
         * 注册消息监听
         */
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            for (Message message : messages) {
                System.out.println("["+ DateUtils.getCurrentTime() +"]监听到消息：" + new String(message.getBody()));
                String str = new String(message.getBody());
                JSONObject jsonObject = JSON.parseObject(str);
                Order order = JSON.toJavaObject(jsonObject, Order.class);
                if(orderService.getById(order.getId()).getOrderState()==PayState.UNPAID.getCode()){
                    //如果订单没有被修改则将订单进行
                    orderService.updatePayById(order, PayState.PAID_TIMEOUT);
                    log.info("订单"+order.getId()+"已经成功被消费[订单超时]{}",order);
                }else{
                    //订单如果已经被客户处理了(支付完成),那么直接消费成功
                    log.info("订单"+order.getId()+"已经成功被消费[已支付]{}",order);
                }

            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }
}
