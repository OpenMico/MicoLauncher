package com.xiaomi.micolauncher.module.video.childmode;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.RemoteViews;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildModeNotification {
    private static final int a = (int) TimeUnit.MINUTES.toSeconds(1);
    private static final long b = TimeUnit.MINUTES.toMillis(1);
    private static final long c = TimeUnit.SECONDS.toMillis(1);
    private Context d;
    private CountDownTimer g;
    private int f = MicoNotification.generateUniqueId();
    private MicoNotification e = a(a());

    public ChildModeNotification(Context context) {
        this.d = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MicoNotification a(RemoteViews remoteViews) {
        return new MicoNotification.Builder(this.d).setNotificationId(this.f).setRemoteViews(remoteViews).setTimeoutAfter(a).setOnlyAlertOnce(true).build();
    }

    private RemoteViews a() {
        RemoteViews remoteViews = new RemoteViews(this.d.getPackageName(), (int) R.layout.notification_child_mode);
        a(remoteViews, a);
        b(remoteViews);
        return remoteViews;
    }

    private void b(final RemoteViews remoteViews) {
        this.g = new CountDownTimer(b, c) { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeNotification.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                ChildModeNotification.this.a(remoteViews, ((int) TimeUnit.MILLISECONDS.toSeconds(j)) + 1);
                if (ChildModeNotification.this.e != null && ChildModeNotification.this.b()) {
                    ChildModeNotification childModeNotification = ChildModeNotification.this;
                    childModeNotification.e = childModeNotification.a(remoteViews);
                    ChildModeNotification.this.e.show();
                }
            }
        };
        this.g.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(RemoteViews remoteViews, int i) {
        remoteViews.setTextViewText(R.id.time_tv, String.format("%ds", Integer.valueOf(i)));
    }

    public void show() {
        L.childmode.i("show child mode notification");
        MicoNotification micoNotification = this.e;
        if (micoNotification != null) {
            micoNotification.show();
        }
    }

    public void cancel() {
        L.childmode.i("cancel child mode notification");
        MicoNotification micoNotification = this.e;
        if (micoNotification != null) {
            micoNotification.cancel();
            this.g.cancel();
            this.e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        return ThirdPartyAppProxy.getInstance().isTimedThirdPartyAppInForeground(this.d);
    }
}
