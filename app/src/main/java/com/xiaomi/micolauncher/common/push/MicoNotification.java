package com.xiaomi.micolauncher.common.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.widget.RemoteViews;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class MicoNotification {
    public static final String ACTION_NOTICE_CLOSE = "com.xiaomi.micolauncher.action.CLOSE_NOTIFICATION";
    public static final int FORBID_BANNER_NOTIFICATION_GROUP_TAG = 2;
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static final int REMIND_NOTIFICATION_GROUP_TAG = 1;
    private static AtomicInteger n = new AtomicInteger(1024);
    private Context a;
    private int b;
    private final int c;
    private Notification d;
    private final RemoteViews e;
    private final PendingIntent f;
    private final PendingIntent g;
    private final boolean h;
    private final String i;
    private final String j;
    private String k;
    private final int l;
    private final String m;

    private MicoNotification(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        this.m = builder.m;
    }

    public static int generateUniqueId() {
        return n.incrementAndGet();
    }

    public void show() {
        a("mico_launcher_sound");
    }

    public void showSilently() {
        a("mico_launcher_no_sound");
    }

    private void a(String str) {
        a(ACTION_NOTICE_CLOSE, str);
    }

    public void show(String str, BroadcastReceiver broadcastReceiver) {
        a(str, "mico_launcher_sound");
    }

    public void showSilently(String str, BroadcastReceiver broadcastReceiver) {
        a(str, "mico_launcher_no_sound");
    }

    private void a(String str, String str2) {
        if (NotificationHelper.isInit()) {
            if (this.c == Integer.MAX_VALUE) {
                cancelNotificationIfActive(this.a, this.b);
            }
            Notification.Builder visibility = new Notification.Builder(this.a, str2).setVisibility(1);
            int i = this.l;
            if (i == 0) {
                i = R.mipmap.ic_launcher;
            }
            this.d = visibility.setSmallIcon(i).setCustomHeadsUpContentView(this.e).setContentIntent(this.f).setDeleteIntent(this.g).setTimeoutAfter(TimeUnit.SECONDS.toMillis(this.c)).setAutoCancel(true).setSound((Uri) null, (AudioAttributes) null).setContentTitle(this.i).setContentText(this.j).setGroup(this.m).setOnlyAlertOnce(this.h).build();
            if (ThreadUtil.isMainThread()) {
                NotificationManager a = a(this.a);
                a.notify(Constants.NOTIFICATION_TAG + generateUniqueId() + 1, this.b, this.d);
                return;
            }
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.push.-$$Lambda$MicoNotification$PZK5dcGxGjgzDzbqXFl3UiLdY-E
                @Override // java.lang.Runnable
                public final void run() {
                    MicoNotification.this.b();
                }
            });
        }
    }

    public /* synthetic */ void b() {
        NotificationManager a = a(this.a);
        a.notify(Constants.NOTIFICATION_TAG + generateUniqueId() + 1, this.b, this.d);
    }

    public void cancel() {
        CountDownModel.getInstance().cancel();
        a(this.a, this.b);
    }

    public void notifyNotification() {
        if (this.d == null) {
            return;
        }
        if (ThreadUtil.isMainThread()) {
            a(this.a).notify(this.b, this.d);
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.push.-$$Lambda$MicoNotification$lerHNHtOVd7YMpORdtH-DhkPukE
                @Override // java.lang.Runnable
                public final void run() {
                    MicoNotification.this.a();
                }
            });
        }
    }

    public /* synthetic */ void a() {
        a(this.a).notify(this.b, this.d);
    }

    public static boolean cancelNotificationIfActive(Context context, int i, boolean z) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
        boolean z2 = false;
        for (StatusBarNotification statusBarNotification : activeNotifications) {
            if (statusBarNotification.getId() == i) {
                notificationManager.cancel(statusBarNotification.getTag(), i);
                if (!z) {
                    return true;
                }
                z2 = true;
            }
        }
        return z2;
    }

    public static boolean cancelNotificationIfActive(Context context, int i) {
        return cancelNotificationIfActive(context, i, true);
    }

    public static void a(Context context, int i) {
        cancelNotificationIfActive(context, i);
    }

    private static NotificationManager a(Context context) {
        return (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private Context a;
        private int b;
        private int c;
        private int d;
        private RemoteViews e;
        private PendingIntent f;
        private PendingIntent g;
        private boolean h;
        private String i;
        private String j;
        private String k;
        private int l;
        private String m;
        private int n;
        private int o;

        public int getSmallIcon() {
            return this.l;
        }

        public Builder setSmallIcon(int i) {
            this.l = i;
            return this;
        }

        private Builder() {
        }

        public Builder(Context context) {
            this.a = context;
        }

        public Builder setGroup(String str) {
            this.m = str;
            return this;
        }

        public int getRequestCode() {
            return this.d;
        }

        public Builder setRequestCode(int i) {
            this.d = i;
            return this;
        }

        public PendingIntent getPendingIntent() {
            return this.f;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            this.f = pendingIntent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.g = pendingIntent;
            return this;
        }

        public RemoteViews getRemoteViews() {
            return this.e;
        }

        public Builder setRemoteViews(RemoteViews remoteViews) {
            this.e = remoteViews;
            return this;
        }

        public int getNotificationId() {
            return this.b;
        }

        public Builder setNotificationId(int i) {
            this.b = i;
            return this;
        }

        public int getTimeoutAfter() {
            return this.c;
        }

        public Builder setTimeoutAfter(int i) {
            this.c = i;
            return this;
        }

        public Builder setOnlyAlertOnce(boolean z) {
            this.h = z;
            return this;
        }

        public Builder setTitle(String str) {
            this.i = str;
            return this;
        }

        public Builder setText(String str) {
            this.j = str;
            return this;
        }

        public Builder setHowToDeal(String str) {
            this.k = str;
            return this;
        }

        public int getLayoutId() {
            return this.n;
        }

        public Builder setLayoutId(int i) {
            this.n = i;
            return this;
        }

        public int getActionRes() {
            return this.o;
        }

        public Builder setActionRes(int i) {
            this.o = i;
            return this;
        }

        public MicoNotification build() {
            return new MicoNotification(this);
        }
    }

    public static RemoteViews createRemoteViews(Context context, String str) {
        return createRemoteViews(context, R.layout.view_notification_banner, str);
    }

    public static RemoteViews createRemoteViews(Context context, int i, String str) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), i);
        remoteViews.setTextViewText(R.id.text, str);
        return remoteViews;
    }
}
