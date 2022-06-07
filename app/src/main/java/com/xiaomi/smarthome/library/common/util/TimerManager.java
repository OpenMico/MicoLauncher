package com.xiaomi.smarthome.library.common.util;

import android.content.Context;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes4.dex */
public class TimerManager {
    private final Timer a = new Timer(false);
    private final Handler b;

    public TimerManager(Handler handler) {
        this.b = handler;
    }

    public void registerTask(Context context, final Runnable runnable, long j, long j2) {
        this.a.schedule(new TimerTask() { // from class: com.xiaomi.smarthome.library.common.util.TimerManager.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                TimerManager.this.b.post(runnable);
            }
        }, j, j2);
    }

    public void registerTask(TimerTask timerTask, long j, long j2) {
        this.a.schedule(timerTask, j, j2);
    }

    public void cancel() {
        this.a.cancel();
    }
}
