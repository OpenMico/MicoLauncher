package io.netty.handler.codec.mqtt;

/* loaded from: classes4.dex */
public final class MqttConnectMessage extends MqttMessage {
    public MqttConnectMessage(MqttFixedHeader mqttFixedHeader, MqttConnectVariableHeader mqttConnectVariableHeader, MqttConnectPayload mqttConnectPayload) {
        super(mqttFixedHeader, mqttConnectVariableHeader, mqttConnectPayload);
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttConnectVariableHeader variableHeader() {
        return (MqttConnectVariableHeader) super.variableHeader();
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttConnectPayload payload() {
        return (MqttConnectPayload) super.payload();
    }
}
