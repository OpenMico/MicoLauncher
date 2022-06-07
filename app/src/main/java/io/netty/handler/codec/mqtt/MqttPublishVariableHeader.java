package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttPublishVariableHeader {
    private final String a;
    private final int b;

    public MqttPublishVariableHeader(String str, int i) {
        this.a = str;
        this.b = i;
    }

    public String topicName() {
        return this.a;
    }

    public int messageId() {
        return this.b;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[topicName=" + this.a + ", messageId=" + this.b + ']';
    }
}
