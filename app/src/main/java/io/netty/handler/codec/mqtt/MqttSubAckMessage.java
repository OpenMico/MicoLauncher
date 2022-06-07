package io.netty.handler.codec.mqtt;

/* loaded from: classes4.dex */
public final class MqttSubAckMessage extends MqttMessage {
    public MqttSubAckMessage(MqttFixedHeader mqttFixedHeader, MqttMessageIdVariableHeader mqttMessageIdVariableHeader, MqttSubAckPayload mqttSubAckPayload) {
        super(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubAckPayload);
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttMessageIdVariableHeader variableHeader() {
        return (MqttMessageIdVariableHeader) super.variableHeader();
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttSubAckPayload payload() {
        return (MqttSubAckPayload) super.payload();
    }
}
