package io.netty.handler.codec.mqtt;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttFixedHeader {
    private final MqttMessageType a;
    private final boolean b;
    private final MqttQoS c;
    private final boolean d;
    private final int e;

    public MqttFixedHeader(MqttMessageType mqttMessageType, boolean z, MqttQoS mqttQoS, boolean z2, int i) {
        this.a = (MqttMessageType) ObjectUtil.checkNotNull(mqttMessageType, "messageType");
        this.b = z;
        this.c = (MqttQoS) ObjectUtil.checkNotNull(mqttQoS, "qosLevel");
        this.d = z2;
        this.e = i;
    }

    public MqttMessageType messageType() {
        return this.a;
    }

    public boolean isDup() {
        return this.b;
    }

    public MqttQoS qosLevel() {
        return this.c;
    }

    public boolean isRetain() {
        return this.d;
    }

    public int remainingLength() {
        return this.e;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[messageType=" + this.a + ", isDup=" + this.b + ", qosLevel=" + this.c + ", isRetain=" + this.d + ", remainingLength=" + this.e + ']';
    }
}
