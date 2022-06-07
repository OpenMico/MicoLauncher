package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttConnAckVariableHeader {
    private final MqttConnectReturnCode a;
    private final boolean b;

    public MqttConnAckVariableHeader(MqttConnectReturnCode mqttConnectReturnCode, boolean z) {
        this.a = mqttConnectReturnCode;
        this.b = z;
    }

    public MqttConnectReturnCode connectReturnCode() {
        return this.a;
    }

    public boolean isSessionPresent() {
        return this.b;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[connectReturnCode=" + this.a + ", sessionPresent=" + this.b + ']';
    }
}
