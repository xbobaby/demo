package com.bob.common.queue;

import lombok.Data;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

@Data
public class MQConfig {
    String subExpression;
    String consumerGroup;
    String topic;
    String tag;
    String instanceName;
    MessageModel messageModel;
}
