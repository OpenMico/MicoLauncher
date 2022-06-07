package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttTopicSubscription {
    private final String a;
    private final MqttQoS b;

    public MqttTopicSubscription(String str, MqttQoS mqttQoS) {
        this.a = str;
        this.b = mqttQoS;
    }

    public String topicName() {
        return this.a;
    }

    public MqttQoS qualityOfService() {
        return this.b;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[topicFilter=" + this.a + ", qualityOfService=" + this.b + ']';
    }
}
