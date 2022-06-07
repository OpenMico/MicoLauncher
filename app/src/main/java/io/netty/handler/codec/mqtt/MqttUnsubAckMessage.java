package io.netty.handler.codec.mqtt;

/* loaded from: classes4.dex */
public final class MqttUnsubAckMessage extends MqttMessage {
    public MqttUnsubAckMessage(MqttFixedHeader mqttFixedHeader, MqttMessageIdVariableHeader mqttMessageIdVariableHeader) {
        super(mqttFixedHeader, mqttMessageIdVariableHeader, null);
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttMessageIdVariableHeader variableHeader() {
        return (MqttMessageIdVariableHeader) super.variableHeader();
    }
}
