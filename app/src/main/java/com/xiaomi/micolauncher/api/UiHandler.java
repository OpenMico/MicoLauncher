package com.xiaomi.micolauncher.api;

import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import java.io.IOException;

/* loaded from: classes3.dex */
public class UiHandler {
    public static void back() {
        a(4);
    }

    public static void home() {
        a(3);
    }

    private static void a(final int i) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$UiHandler$LkYN1MEhJjVqYYEdTEyRPCwOYMQ
            @Override // java.lang.Runnable
            public final void run() {
                UiHandler.b(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(int i) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("input keyevent " + i);
        } catch (IOException e) {
            L.base.w("inputKeyevent", e);
        }
    }
}
