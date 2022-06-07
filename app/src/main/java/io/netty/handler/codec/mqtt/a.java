package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderException;

/* compiled from: MqttCodecUtil.java */
/* loaded from: classes4.dex */
final class a {
    private static final char[] a = {'#', '+'};

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(int i) {
        return i != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        for (char c : a) {
            if (str.indexOf(c) >= 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(MqttVersion mqttVersion, String str) {
        if (mqttVersion == MqttVersion.MQTT_3_1) {
            return str != null && str.length() >= 1 && str.length() <= 23;
        }
        if (mqttVersion == MqttVersion.MQTT_3_1_1) {
            return str != null;
        }
        throw new IllegalArgumentException(mqttVersion + " is unknown mqtt version");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MqttFixedHeader a(MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case PUBREL:
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                if (mqttFixedHeader.qosLevel() != MqttQoS.AT_LEAST_ONCE) {
                    throw new DecoderException(mqttFixedHeader.messageType().name() + " message must have QoS 1");
                }
                break;
        }
        return mqttFixedHeader;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MqttFixedHeader b(MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case PUBREL:
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                return mqttFixedHeader.isRetain() ? new MqttFixedHeader(mqttFixedHeader.messageType(), mqttFixedHeader.isDup(), mqttFixedHeader.qosLevel(), false, mqttFixedHeader.remainingLength()) : mqttFixedHeader;
            case CONNECT:
            case CONNACK:
            case PUBACK:
            case PUBREC:
            case PUBCOMP:
            case SUBACK:
            case UNSUBACK:
            case PINGREQ:
            case PINGRESP:
            case DISCONNECT:
                return (mqttFixedHeader.isDup() || mqttFixedHeader.qosLevel() != MqttQoS.AT_MOST_ONCE || mqttFixedHeader.isRetain()) ? new MqttFixedHeader(mqttFixedHeader.messageType(), false, MqttQoS.AT_MOST_ONCE, false, mqttFixedHeader.remainingLength()) : mqttFixedHeader;
            default:
                return mqttFixedHeader;
        }
    }
}
