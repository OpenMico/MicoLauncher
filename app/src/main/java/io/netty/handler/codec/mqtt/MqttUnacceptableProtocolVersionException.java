package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderException;

/* loaded from: classes4.dex */
public final class MqttUnacceptableProtocolVersionException extends DecoderException {
    private static final long serialVersionUID = 4914652213232455749L;

    public MqttUnacceptableProtocolVersionException() {
    }

    public MqttUnacceptableProtocolVersionException(String str, Throwable th) {
        super(str, th);
    }

    public MqttUnacceptableProtocolVersionException(String str) {
        super(str);
    }

    public MqttUnacceptableProtocolVersionException(Throwable th) {
        super(th);
    }
}
