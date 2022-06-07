package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttMessageIdVariableHeader {
    private final int a;

    public static MqttMessageIdVariableHeader from(int i) {
        if (i >= 1 && i <= 65535) {
            return new MqttMessageIdVariableHeader(i);
        }
        throw new IllegalArgumentException("messageId: " + i + " (expected: 1 ~ 65535)");
    }

    private MqttMessageIdVariableHeader(int i) {
        this.a = i;
    }

    public int messageId() {
        return this.a;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[messageId=" + this.a + ']';
    }
}
