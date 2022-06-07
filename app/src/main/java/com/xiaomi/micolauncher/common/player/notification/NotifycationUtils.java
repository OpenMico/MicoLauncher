package com.xiaomi.micolauncher.common.player.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

/* loaded from: classes3.dex */
public class NotifycationUtils {
    public static Notification getNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (Build.VERSION.SDK_INT < 26) {
            return new Notification.Builder(context).setContentTitle("Mi").setAutoCancel(false).setDefaults(0).setContentTitle("").setContentText("").setPriority(-2).setSmallIcon(context.getApplicationInfo().icon).setWhen(System.currentTimeMillis()).build();
        }
        NotificationChannel notificationChannel = new NotificationChannel("com.xiaomi.micolauncher.notifcation.fake.milink.miplay", "com.xiaomi.micolauncher.notifcation.fake.milink.miplay", 2);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return new Notification.Builder(context, "com.xiaomi.micolauncher.notifcation.fake.milink.miplay").setAutoCancel(false).setDefaults(0).setContentTitle("").setContentText("").setPriority(-2).setSmallIcon(context.getApplicationInfo().icon).build();
    }
}
