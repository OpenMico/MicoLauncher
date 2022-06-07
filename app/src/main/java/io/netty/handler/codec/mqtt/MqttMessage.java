package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class MqttMessage {
    private final MqttFixedHeader a;
    private final Object b;
    private final Object c;
    private final DecoderResult d;

    public MqttMessage(MqttFixedHeader mqttFixedHeader) {
        this(mqttFixedHeader, null, null);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object obj) {
        this(mqttFixedHeader, obj, null);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object obj, Object obj2) {
        this(mqttFixedHeader, obj, obj2, DecoderResult.SUCCESS);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object obj, Object obj2, DecoderResult decoderResult) {
        this.a = mqttFixedHeader;
        this.b = obj;
        this.c = obj2;
        this.d = decoderResult;
    }

    public MqttFixedHeader fixedHeader() {
        return this.a;
    }

    public Object variableHeader() {
        return this.b;
    }

    public Object payload() {
        return this.c;
    }

    public DecoderResult decoderResult() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(StringUtil.simpleClassName(this));
        sb.append('[');
        sb.append("fixedHeader=");
        sb.append(fixedHeader() != null ? fixedHeader().toString() : "");
        sb.append(", variableHeader=");
        sb.append(variableHeader() != null ? this.b.toString() : "");
        sb.append(", payload=");
        sb.append(payload() != null ? this.c.toString() : "");
        sb.append(']');
        return sb.toString();
    }
}
