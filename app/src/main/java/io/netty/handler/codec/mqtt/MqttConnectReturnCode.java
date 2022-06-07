package io.netty.handler.codec.mqtt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public enum MqttConnectReturnCode {
    CONNECTION_ACCEPTED((byte) 0),
    CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION((byte) 1),
    CONNECTION_REFUSED_IDENTIFIER_REJECTED((byte) 2),
    CONNECTION_REFUSED_SERVER_UNAVAILABLE((byte) 3),
    CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD((byte) 4),
    CONNECTION_REFUSED_NOT_AUTHORIZED((byte) 5);
    
    private static final Map<Byte, MqttConnectReturnCode> a;
    private final byte byteValue;

    static {
        HashMap hashMap = new HashMap();
        MqttConnectReturnCode[] values = values();
        for (MqttConnectReturnCode mqttConnectReturnCode : values) {
            hashMap.put(Byte.valueOf(mqttConnectReturnCode.byteValue), mqttConnectReturnCode);
        }
        a = Collections.unmodifiableMap(hashMap);
    }

    MqttConnectReturnCode(byte b2) {
        this.byteValue = b2;
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public static MqttConnectReturnCode valueOf(byte b2) {
        if (a.containsKey(Byte.valueOf(b2))) {
            return a.get(Byte.valueOf(b2));
        }
        throw new IllegalArgumentException("unknown connect return code: " + (b2 & 255));
    }
}
