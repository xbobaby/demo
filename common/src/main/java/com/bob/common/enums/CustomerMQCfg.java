package com.bob.common.enums;

public enum CustomerMQCfg {
    CUSTOMER_PARAM("CUSTOMER_GROUP", "CUSTOMER_TOPIC", "CUSTOMER_TAG", "自定义MQ配置信息");
    // 后续可以继续添加...;

    CustomerMQCfg(String group, String topic, String tag, String remark) {
        this.group = group;
        this.topic = topic;
        this.tag = tag;
        this.remark = remark;
    }

    private String group;
    private String topic;
    private String tag;
    private String remark;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
