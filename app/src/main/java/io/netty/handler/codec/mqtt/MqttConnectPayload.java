package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttConnectPayload {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;

    public MqttConnectPayload(String str, String str2, String str3, String str4, String str5) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
    }

    public String clientIdentifier() {
        return this.a;
    }

    public String willTopic() {
        return this.b;
    }

    public String willMessage() {
        return this.c;
    }

    public String userName() {
        return this.d;
    }

    public String password() {
        return this.e;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[clientIdentifier=" + this.a + ", willTopic=" + this.b + ", willMessage=" + this.c + ", userName=" + this.d + ", password=" + this.e + ']';
    }
}
