package com.xiaomi.micolauncher.common.utils;

import com.xiaomi.mico.utils.ThreadUtil;

/* loaded from: classes3.dex */
public class AsyncTaskUtils {
    public static void runAsync(final Runnable runnable) {
        if (ThreadUtil.isMainThread()) {
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$AsyncTaskUtils$H8DDJ-TQWMHsO0OwCvzJtIh4Vn4
                @Override // java.lang.Runnable
                public final void run() {
                    AsyncTaskUtils.a(runnable);
                }
            });
        } else if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }
}
