package com.umeng.analytics.pro;

import java.io.ByteArrayOutputStream;

/* compiled from: TByteArrayOutputStream.java */
/* loaded from: classes2.dex */
public class as extends ByteArrayOutputStream {
    public as(int i) {
        super(i);
    }

    public as() {
    }

    public byte[] a() {
        return this.buf;
    }

    public int b() {
        return this.count;
    }
}
