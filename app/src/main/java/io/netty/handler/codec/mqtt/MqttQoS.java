package io.netty.handler.codec.mqtt;

/* loaded from: classes4.dex */
public enum MqttQoS {
    AT_MOST_ONCE(0),
    AT_LEAST_ONCE(1),
    EXACTLY_ONCE(2),
    FAILURE(128);
    
    private final int value;

    MqttQoS(int i) {
        this.value = i;
    }

    public int value() {
        return this.value;
    }

    public static MqttQoS valueOf(int i) {
        MqttQoS[] values = values();
        for (MqttQoS mqttQoS : values) {
            if (mqttQoS.value == i) {
                return mqttQoS;
            }
        }
        throw new IllegalArgumentException("invalid QoS: " + i);
    }
}
