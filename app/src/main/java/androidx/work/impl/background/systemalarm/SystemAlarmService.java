package androidx.work.impl.background.systemalarm;

import android.content.Intent;
import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LifecycleService;
import androidx.work.Logger;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.utils.WakeLocks;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemAlarmService extends LifecycleService implements SystemAlarmDispatcher.b {
    private static final String a = Logger.tagWithPrefix("SystemAlarmService");
    private SystemAlarmDispatcher b;
    private boolean c;

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onCreate() {
        super.onCreate();
        a();
        this.c = false;
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.c = true;
        this.b.a();
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        if (this.c) {
            Logger.get().info(a, "Re-initializing SystemAlarmDispatcher after a request to shut-down.", new Throwable[0]);
            this.b.a();
            a();
            this.c = false;
        }
        if (intent == null) {
            return 3;
        }
        this.b.add(intent, i2);
        return 3;
    }

    @Override // androidx.work.impl.background.systemalarm.SystemAlarmDispatcher.b
    @MainThread
    public void onAllCommandsCompleted() {
        this.c = true;
        Logger.get().debug(a, "All commands completed in dispatcher", new Throwable[0]);
        WakeLocks.checkWakeLocks();
        stopSelf();
    }

    @MainThread
    private void a() {
        this.b = new SystemAlarmDispatcher(this);
        this.b.a(this);
    }
}
