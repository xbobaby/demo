package com.bob.common.service;

import com.bob.common.util.MQUtil;
import com.bob.common.enums.CustomerMQCfg;
import com.bob.common.queue.MQConfig;
import com.bob.common.queue.Productor;
import org.springframework.stereotype.Component;

@Component
public class ActualProductor extends Productor {
    @Override
    protected MQConfig getMQConfig() {
        return MQUtil.getMQConfig(CustomerMQCfg.CUSTOMER_PARAM.getGroup(), CustomerMQCfg.CUSTOMER_PARAM.getTopic());
    }
}
