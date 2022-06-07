package com.xiaomi.accountsdk.request.intercept;

/* loaded from: classes2.dex */
public class NetworkInterceptor {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static volatile NetworkInterceptCallback sInstance = new EmptyNetworkInterceptCallback();

        private Holder() {
        }
    }

    public static void set(NetworkInterceptCallback networkInterceptCallback) {
        NetworkInterceptCallback unused = Holder.sInstance = networkInterceptCallback;
    }

    public static NetworkInterceptCallback get() {
        return Holder.sInstance;
    }
}
