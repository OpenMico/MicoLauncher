package com.xiaomi.ai.error;

/* loaded from: classes3.dex */
public class AivsError {
    private int a;
    private String b;
    private String c;

    public AivsError(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public AivsError(int i, String str, String str2) {
        this.a = i;
        this.b = str;
        this.c = str2;
    }

    public int getErrorCode() {
        return this.a;
    }

    public String getErrorMessage() {
        return this.b;
    }

    public String getEventId() {
        return this.c;
    }
}
