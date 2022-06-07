package io.netty.handler.codec.mqtt;

/* loaded from: classes4.dex */
public final class MqttUnsubscribeMessage extends MqttMessage {
    public MqttUnsubscribeMessage(MqttFixedHeader mqttFixedHeader, MqttMessageIdVariableHeader mqttMessageIdVariableHeader, MqttUnsubscribePayload mqttUnsubscribePayload) {
        super(mqttFixedHeader, mqttMessageIdVariableHeader, mqttUnsubscribePayload);
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttMessageIdVariableHeader variableHeader() {
        return (MqttMessageIdVariableHeader) super.variableHeader();
    }

    @Override // io.netty.handler.codec.mqtt.MqttMessage
    public MqttUnsubscribePayload payload() {
        return (MqttUnsubscribePayload) super.payload();
    }
}
