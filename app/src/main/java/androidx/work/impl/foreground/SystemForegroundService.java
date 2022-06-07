package androidx.work.impl.foreground;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LifecycleService;
import androidx.work.Logger;
import androidx.work.impl.foreground.SystemForegroundDispatcher;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemForegroundService extends LifecycleService implements SystemForegroundDispatcher.a {
    private static final String c = Logger.tagWithPrefix("SystemFgService");
    @Nullable
    private static SystemForegroundService d = null;
    SystemForegroundDispatcher a;
    NotificationManager b;
    private Handler e;
    private boolean f;

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onCreate() {
        super.onCreate();
        d = this;
        a();
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public int onStartCommand(@Nullable Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        if (this.f) {
            Logger.get().info(c, "Re-initializing SystemForegroundService after a request to shut-down.", new Throwable[0]);
            this.a.a();
            a();
            this.f = false;
        }
        if (intent == null) {
            return 3;
        }
        this.a.a(intent);
        return 3;
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.a.a();
    }

    @MainThread
    private void a() {
        this.e = new Handler(Looper.getMainLooper());
        this.b = (NotificationManager) getApplicationContext().getSystemService("notification");
        this.a = new SystemForegroundDispatcher(getApplicationContext());
        this.a.a(this);
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.a
    @MainThread
    public void stop() {
        this.f = true;
        Logger.get().debug(c, "All commands completed.", new Throwable[0]);
        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(true);
        }
        d = null;
        stopSelf();
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.a
    public void startForeground(final int i, final int i2, @NonNull final Notification notification) {
        this.e.post(new Runnable() { // from class: androidx.work.impl.foreground.SystemForegroundService.1
            @Override // java.lang.Runnable
            public void run() {
                if (Build.VERSION.SDK_INT >= 29) {
                    SystemForegroundService.this.startForeground(i, notification, i2);
                } else {
                    SystemForegroundService.this.startForeground(i, notification);
                }
            }
        });
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.a
    public void notify(final int i, @NonNull final Notification notification) {
        this.e.post(new Runnable() { // from class: androidx.work.impl.foreground.SystemForegroundService.2
            @Override // java.lang.Runnable
            public void run() {
                SystemForegroundService.this.b.notify(i, notification);
            }
        });
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.a
    public void cancelNotification(final int i) {
        this.e.post(new Runnable() { // from class: androidx.work.impl.foreground.SystemForegroundService.3
            @Override // java.lang.Runnable
            public void run() {
                SystemForegroundService.this.b.cancel(i);
            }
        });
    }

    @Nullable
    public static SystemForegroundService getInstance() {
        return d;
    }
}
