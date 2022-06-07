package com.xiaomi.ai.android.vad;

/* loaded from: classes2.dex */
public interface IVad {
    boolean checkVad(byte[] bArr);

    boolean init();

    void release();
}
