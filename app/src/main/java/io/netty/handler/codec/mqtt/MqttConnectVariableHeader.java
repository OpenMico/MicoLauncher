package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class MqttConnectVariableHeader {
    private final String a;
    private final int b;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final int f;
    private final boolean g;
    private final boolean h;
    private final int i;

    public MqttConnectVariableHeader(String str, int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, int i3) {
        this.a = str;
        this.b = i;
        this.c = z;
        this.d = z2;
        this.e = z3;
        this.f = i2;
        this.g = z4;
        this.h = z5;
        this.i = i3;
    }

    public String name() {
        return this.a;
    }

    public int version() {
        return this.b;
    }

    public boolean hasUserName() {
        return this.c;
    }

    public boolean hasPassword() {
        return this.d;
    }

    public boolean isWillRetain() {
        return this.e;
    }

    public int willQos() {
        return this.f;
    }

    public boolean isWillFlag() {
        return this.g;
    }

    public boolean isCleanSession() {
        return this.h;
    }

    public int keepAliveTimeSeconds() {
        return this.i;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[name=" + this.a + ", version=" + this.b + ", hasUserName=" + this.c + ", hasPassword=" + this.d + ", isWillRetain=" + this.e + ", isWillFlag=" + this.g + ", isCleanSession=" + this.h + ", keepAliveTimeSeconds=" + this.i + ']';
    }
}
