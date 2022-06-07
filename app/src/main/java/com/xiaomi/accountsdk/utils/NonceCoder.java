package com.xiaomi.accountsdk.utils;

/* loaded from: classes2.dex */
public class NonceCoder {
    public static String generateNonce() {
        return generateNonce(SyncServerTimeExecutor.getInstance().getCurrentServerTimeMillis());
    }

    public static String generateNonce(long j) {
        return Coder.generateNonce(j);
    }
}
