package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;

/* loaded from: classes4.dex */
public final class MqttMessageFactory {
    public static MqttMessage newMessage(MqttFixedHeader mqttFixedHeader, Object obj, Object obj2) {
        switch (mqttFixedHeader.messageType()) {
            case CONNECT:
                return new MqttConnectMessage(mqttFixedHeader, (MqttConnectVariableHeader) obj, (MqttConnectPayload) obj2);
            case CONNACK:
                return new MqttConnAckMessage(mqttFixedHeader, (MqttConnAckVariableHeader) obj);
            case SUBSCRIBE:
                return new MqttSubscribeMessage(mqttFixedHeader, (MqttMessageIdVariableHeader) obj, (MqttSubscribePayload) obj2);
            case SUBACK:
                return new MqttSubAckMessage(mqttFixedHeader, (MqttMessageIdVariableHeader) obj, (MqttSubAckPayload) obj2);
            case UNSUBACK:
                return new MqttUnsubAckMessage(mqttFixedHeader, (MqttMessageIdVariableHeader) obj);
            case UNSUBSCRIBE:
                return new MqttUnsubscribeMessage(mqttFixedHeader, (MqttMessageIdVariableHeader) obj, (MqttUnsubscribePayload) obj2);
            case PUBLISH:
                return new MqttPublishMessage(mqttFixedHeader, (MqttPublishVariableHeader) obj, (ByteBuf) obj2);
            case PUBACK:
                return new MqttPubAckMessage(mqttFixedHeader, (MqttMessageIdVariableHeader) obj);
            case PUBREC:
            case PUBREL:
            case PUBCOMP:
                return new MqttMessage(mqttFixedHeader, obj);
            case PINGREQ:
            case PINGRESP:
            case DISCONNECT:
                return new MqttMessage(mqttFixedHeader);
            default:
                throw new IllegalArgumentException("unknown message type: " + mqttFixedHeader.messageType());
        }
    }

    public static MqttMessage newInvalidMessage(Throwable th) {
        return new MqttMessage(null, null, null, DecoderResult.failure(th));
    }

    private MqttMessageFactory() {
    }
}
