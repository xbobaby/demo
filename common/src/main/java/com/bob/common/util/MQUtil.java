package com.bob.common.util;

import com.bob.common.enums.CustomerMQCfg;
import com.bob.common.queue.MQConfig;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class MQUtil {
    public static MQConfig getMQConfig(String pushMessageGroup, String pushMessageTopic) {
        MQConfig mqCfg = new MQConfig() {
            public String getSubExpression() {
                return "*";
            }

            public String getConsumerGroup() {
                return pushMessageGroup;
            }

            public String getTopic() {
                return pushMessageTopic;
            }

            public String getInstanceName() {
                // 也可以使用UUID.randomUUID().toString()，如：return UUID.randomUUID().toString();
                return String.valueOf(System.currentTimeMillis());
            }

            public MessageModel getMessageModel() {
                return MessageModel.CLUSTERING;
            }
        };
        return mqCfg;
    }

    public static MQConfig getMQConfig(CustomerMQCfg customerMQCfg) {
        MQConfig mqCfg = new MQConfig() {
            public String getSubExpression() {
                return customerMQCfg.getTag();
            }

            public String getConsumerGroup() {
                return customerMQCfg.getGroup();
            }

            public String getTopic() {
                return customerMQCfg.getTopic();
            }

            public String getInstanceName() {
                // 也可以使用UUID.randomUUID().toString()，如：return UUID.randomUUID().toString();
                return String.valueOf(System.currentTimeMillis());
            }

            public MessageModel getMessageModel() {
                return MessageModel.CLUSTERING;
            }
        };
        return mqCfg;
    }
}
