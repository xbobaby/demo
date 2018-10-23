
package com.bob.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketmqProperties {
    private String namesrvAddr;
    private String pushConsumer;
    private String producerGroup;

}

