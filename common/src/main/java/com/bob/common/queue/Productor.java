package com.bob.common.queue;

import com.bob.common.config.RocketmqProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class Productor {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected DefaultMQProducer defaultMQProducer;

    @Autowired
    RocketmqProperties rocketmqProperties;

    @PostConstruct
    private void init() throws MQClientException {
        defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(rocketmqProperties.getNamesrvAddr());
        init(defaultMQProducer);
    }

    private void init(DefaultMQProducer defaultMQProducer) throws MQClientException {
        final MQConfig mqCfg = getMQConfig();
//        defaultMQProducer.setInstanceName(mqCfg.getInstanceName());
        defaultMQProducer.setProducerGroup(mqCfg.getConsumerGroup());
        defaultMQProducer.start();
        logger.info("ProductorGroup: {}, Topic: {}, SubExpression: {} started!", mqCfg.getConsumerGroup(), mqCfg.getTopic(), mqCfg.getSubExpression());
    }


    public SendResult send(Message msg) throws Exception {
        SendResult result = defaultMQProducer.send(msg);
        return result;
    }

    protected abstract MQConfig getMQConfig();
}