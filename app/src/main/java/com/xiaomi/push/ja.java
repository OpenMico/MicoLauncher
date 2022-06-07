package com.xiaomi.push;

/* loaded from: classes4.dex */
public class ja {
    public final byte a;

    /* renamed from: a  reason: collision with other field name */
    public final String f179a;

    /* renamed from: a  reason: collision with other field name */
    public final short f180a;

    public ja() {
        this("", (byte) 0, (short) 0);
    }

    public ja(String str, byte b, short s) {
        this.f179a = str;
        this.a = b;
        this.f180a = s;
    }

    public String toString() {
        return "<TField name:'" + this.f179a + "' type:" + ((int) this.a) + " field-id:" + ((int) this.f180a) + ">";
    }
}
