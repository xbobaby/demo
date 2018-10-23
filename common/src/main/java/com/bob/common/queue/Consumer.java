package com.bob.common.queue;

import com.bob.common.properties.RocketmqProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class Consumer {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    RocketmqProperties rocketmqProperties;

    @PostConstruct
    private void init() throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setNamesrvAddr(rocketmqProperties.getNamesrvAddr());
        defaultMQPushConsumer.setConsumeThreadMax(20);
        defaultMQPushConsumer.setConsumeThreadMin(10);
        init(defaultMQPushConsumer);
    }

    private void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        final MQConfig mqCfg = getMQConfig();
        defaultMQPushConsumer.setInstanceName(mqCfg.getInstanceName());
        defaultMQPushConsumer.setConsumerGroup(mqCfg.getConsumerGroup());
        defaultMQPushConsumer.subscribe(mqCfg.getTopic(), mqCfg.getSubExpression());

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费,如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        defaultMQPushConsumer.setMessageModel(mqCfg.getMessageModel());// 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() { // 这里可以抽离出来，添加一个继承MessageListenerConcurrently的类
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                if (null != msgs && !msgs.isEmpty()) {
                    for (MessageExt msg : msgs) {
                        if (mqCfg.getTopic().equals(msg.getTopic())) {
                            try {
                                consume(msg, context);
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                        } else {
                            // 如果没有return success, consumer会重新消费该消息, 直到return success
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                    // 如果没有return success, consumer会重新消费该消息, 直到return success
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // Consumer对象在使用之前必须要调用start初始化, 初始化一次即可
        defaultMQPushConsumer.start();
        logger.info("ConsumerGroup: {}, Topic: {}, SubExpression: {} started!", mqCfg.getConsumerGroup(), mqCfg.getTopic(), mqCfg.getSubExpression());
    }

    protected abstract void consume(MessageExt msg, ConsumeConcurrentlyContext context) throws Exception;

    protected abstract MQConfig getMQConfig();
}