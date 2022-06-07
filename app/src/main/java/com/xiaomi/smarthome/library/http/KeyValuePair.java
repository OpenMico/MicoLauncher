package com.xiaomi.smarthome.library.http;

/* loaded from: classes4.dex */
public class KeyValuePair {
    private final String a;
    private final String b;

    public KeyValuePair(String str, String str2) {
        if (str != null) {
            this.a = str;
            this.b = str2;
            return;
        }
        throw new IllegalArgumentException("Key may not be null");
    }

    public String getKey() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }
}
