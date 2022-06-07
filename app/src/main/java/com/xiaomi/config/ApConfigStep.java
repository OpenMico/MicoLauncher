package com.xiaomi.config;

import java.util.Random;

/* loaded from: classes3.dex */
public class ApConfigStep {
    public static final int MSG_GET_MODEL = 127;
    private static int a = -1;
    protected String mModel;

    /* loaded from: classes3.dex */
    public interface ConfigCompletedHandler {
        void onSuccess(long j);

        void onfailed(int i, String str);
    }

    public final int generateNonce() {
        a++;
        if (a <= 0) {
            a = new Random().nextInt(10000) + 1;
        }
        return a;
    }
}
