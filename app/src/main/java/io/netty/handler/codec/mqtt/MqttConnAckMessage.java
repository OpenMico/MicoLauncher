package io.netty.handler.codec.mqtt;

/* loaded from: classes4.dex */
public final class MqttConnAckMessage extends MqttMessage {
    public MqttConnAckMessage(MqttFixedHeader mqttFixedHeader, MqttConnAckVariableHeader mqttConnAckVariableHeader) {
        super(mqttFixedHeader, mqttConnAckVariableHeader);
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttConnAckVariableHeader variableHeader() {
        return (MqttConnAckVariableHeader) super.variableHeader();
    }
}
