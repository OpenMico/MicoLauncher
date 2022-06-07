package com.mi.milink.mediacore;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import com.mi.milink.mediacore.MediaCoreService;
import com.mi.milink.mediacore.a;
import java.lang.ref.Reference;
import java.util.Objects;

/* loaded from: classes2.dex */
public class MediaCoreService extends Service {
    private static Reference<a> a;
    private final Handler b = new Handler();
    private a c;

    public static void start(@NonNull Context context) {
        if (MediaCoreConfig.isEnable()) {
            Objects.requireNonNull(context, "context is null");
            Intent intent = new Intent(context, MediaCoreService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }

    public static void stop(@NonNull Context context) {
        ((Context) Objects.requireNonNull(context, "context is null")).stopService(new Intent(context, MediaCoreService.class));
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        c();
        a();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (!MediaCoreConfig.isEnable()) {
            this.b.postDelayed(new Runnable() { // from class: com.mi.milink.mediacore.-$$Lambda$TEx5SC4lDLcMLZsFp-tI_4lcFVM
                @Override // java.lang.Runnable
                public final void run() {
                    MediaCoreService.this.stopSelf();
                }
            }, 50L);
            return super.onStartCommand(intent, i, i2);
        }
        this.c.a();
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.b.removeCallbacksAndMessages(null);
        this.c.b();
        b();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000c, code lost:
        if (r0 == null) goto L_0x000e;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a() {
        /*
            r5 = this;
            java.lang.ref.Reference<com.mi.milink.mediacore.a> r0 = com.mi.milink.mediacore.MediaCoreService.a
            if (r0 == 0) goto L_0x000e
            java.lang.Object r0 = r0.get()
            com.mi.milink.mediacore.a r0 = (com.mi.milink.mediacore.a) r0
            r5.c = r0
            if (r0 != 0) goto L_0x002d
        L_0x000e:
            com.mi.milink.mediacore.a r0 = new com.mi.milink.mediacore.a
            android.content.Context r1 = r5.getApplicationContext()
            com.mi.milink.mediacore.MediaCoreService$a r2 = new com.mi.milink.mediacore.MediaCoreService$a
            android.os.Handler r3 = r5.b
            android.content.Context r4 = r5.getApplicationContext()
            r2.<init>(r3, r4)
            r0.<init>(r1, r2)
            r5.c = r0
            java.lang.ref.WeakReference r0 = new java.lang.ref.WeakReference
            com.mi.milink.mediacore.a r1 = r5.c
            r0.<init>(r1)
            com.mi.milink.mediacore.MediaCoreService.a = r0
        L_0x002d:
            boolean r0 = com.mi.milink.mediacore.MediaCoreConfig.isHasCustomConfig()
            if (r0 != 0) goto L_0x0046
            android.content.pm.ApplicationInfo r0 = r5.getApplicationInfo()
            int r0 = r0.flags
            r0 = r0 & 2
            if (r0 != 0) goto L_0x003e
            goto L_0x0046
        L_0x003e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Use Media core service, need set custom config.eg: MediaCoreConfig.edit()\n                .setDeviceCategory(DeviceCategory.PHONE)\n                .setEnable(true)\n                .setDeviceId(\"xxxxxx\")\n                .setDeviceModel(\"xxx\")\n                .setOptionArguments(options)\n                .setForegroundNotification(id, nt)\n                .commit();"
            r0.<init>(r1)
            throw r0
        L_0x0046:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mi.milink.mediacore.MediaCoreService.a():void");
    }

    private void b() {
        stopForeground(true);
    }

    private void c() {
        int foregroundNotificationId = MediaCoreConfig.getForegroundNotificationId();
        Notification foregroundNotification = MediaCoreConfig.getForegroundNotification(this);
        if (Build.VERSION.SDK_INT >= 29) {
            startForeground(foregroundNotificationId, foregroundNotification, 16);
        } else {
            startForeground(foregroundNotificationId, foregroundNotification);
        }
        Log.d("MediaCoreService", "start foreground service: MediaCoreService");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a implements a.b {
        private final Context a;
        private final Handler b;

        public a(Handler handler, Context context) {
            this.a = context;
            this.b = handler;
        }

        @Override // com.mi.milink.mediacore.a.b
        public void a() {
            this.b.post(new Runnable() { // from class: com.mi.milink.mediacore.-$$Lambda$MediaCoreService$a$RnkUA0CeWkL1bXOK7dlDBYuSyNg
                @Override // java.lang.Runnable
                public final void run() {
                    MediaCoreService.a.this.b();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b() {
            try {
                this.a.stopService(new Intent(this.a, MediaCoreService.class));
            } catch (Exception e) {
                Log.e("MediaCoreService", "Stop self(MediaCoreService) fail.", e);
            }
        }
    }
}
