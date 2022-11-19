package com.cx.springboot02.common.mq;

import com.cx.springboot02.common.utils.DateUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @Description MQ生产者
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
@Component
public class ProducerSchedule {

    private DefaultMQProducer producer;

    @Value("${rocketmq.producer.producer-group}")
    private String producerGroup;

    @Value("${rocketmq.namesrv-addr}")
    private String nameSrvAddr;

    public ProducerSchedule() {

    }

    private static final Logger log = LoggerFactory.getLogger(Logger.class);

    /**
     * 生产者构造
     * @PostConstruct该注解被用来修饰一个非静态的void（）方法
     * Bean初始化的执行顺序：
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void defaultMQProducer() {
        if (Objects.isNull(this.producer)) {
            this.producer = new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.nameSrvAddr);
        }

        try {
            this.producer.start();
            System.out.println("Producer start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息发布
     * @param topic
     * @param
     * @param messageText
     * @return
     */
    public String send(String topic, String messageText){
        Message message = new Message(topic, messageText.getBytes());
        /**
         * 延迟消息级别设置     1  2  3   4   5  6  7  8  9  10 11 12 13 14  15  16
         * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
         */
        message.setDelayTimeLevel(14);

        SendResult result = null;
        try {
            // 发送消息到一个Broker
            result = this.producer.send(message);
            log.info("发送延迟消息结果：======sendResult：{}", result);
            log.info("发送时间：{}", DateUtils.getCurrentTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.getMsgId();
    }

    /**
     * 推送延迟消息
     * @param topic
     * @param body
     * @param producerGroup
     * @return boolean
     */
    public boolean sendMessage(String topic, String body, String producerGroup)
    {
        try
        {
            Message recordMsg = new Message(topic, body.getBytes());
            producer.setProducerGroup(producerGroup);

            //设置消息延迟级别，我这里设置5，对应就是延时一分钟
            // "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
            recordMsg.setDelayTimeLevel(5);
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(recordMsg);
            // 通过sendResult返回消息是否成功送达
            log.info("发送延迟消息结果：======sendResult：{}", sendResult);
            log.info("发送时间：{}", DateUtils.getCurrentTime());

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("延迟消息队列推送消息异常:{},推送内容:{}", e.getMessage(), body);
        }
        return false;
    }

}
