package com.bob.common.service;

import com.alibaba.fastjson.JSON;
import com.bob.common.util.MQUtil;
import com.bob.common.enums.CustomerMQCfg;
import com.bob.common.queue.Consumer;
import com.bob.common.queue.MQConfig;
import com.bob.common.vo.User;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

@Component
public class ActualConsumer extends Consumer {
    @Override
    protected void consume(MessageExt msg, ConsumeConcurrentlyContext context) {
        String messageBody = null;
        try {
           // messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
            User user = JSON.parseObject(msg.getBody(),User.class);
            logger.info("ActualConsumer:{}",JSON.toJSONString(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected MQConfig getMQConfig() {
        return MQUtil.getMQConfig(CustomerMQCfg.CUSTOMER_PARAM.getGroup(), CustomerMQCfg.CUSTOMER_PARAM.getTopic());
    }
}
