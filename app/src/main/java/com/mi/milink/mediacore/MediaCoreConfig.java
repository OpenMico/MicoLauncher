package com.mi.milink.mediacore;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

/* loaded from: classes2.dex */
public class MediaCoreConfig {
    public static final String ACTION_AUDIO_PLAYBACK = "milink.intent.action.AUDIO_PLAYBACK";
    public static final String ACTION_AUDIO_PLAYBACK_FOR_MUSIC = "milink.intent.action.AUDIO_PLAYBACK_FOR_MUSIC";
    public static final String ACTION_NOTICE = "milink.intent.action.NOTICE";
    public static final String KEY_MESSAGE = "msg";
    private static MediaCoreConfig a = new MediaCoreConfig();
    private Notification d;
    private Notification e;
    private String g;
    private String h;
    private boolean j;
    private boolean b = true;
    private int c = 9898;
    private Bundle f = Bundle.EMPTY;
    private int i = -1;

    public static boolean isEnable() {
        return a.b;
    }

    public MediaCoreConfig setEnable(boolean z) {
        this.b = z;
        return this;
    }

    @Nullable
    public static Bundle getOptionArguments() {
        return a.f;
    }

    public MediaCoreConfig setOptionArguments(Bundle bundle) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        this.f = bundle;
        return this;
    }

    public static String getDeviceId(Context context) {
        synchronized (MediaCoreConfig.class) {
            String str = a.g;
            if (str != null) {
                return str;
            }
            String b = b.b(context);
            a.g = b;
            return b;
        }
    }

    public static String getDeviceModel() {
        synchronized (MediaCoreConfig.class) {
            String str = a.h;
            if (str != null) {
                return str;
            }
            String b = b.b();
            a.h = b;
            return b;
        }
    }

    public MediaCoreConfig setDeviceModel(String str) {
        this.h = str;
        return this;
    }

    public static int getDeviceCategory() {
        return a.i;
    }

    public MediaCoreConfig setDeviceCategory(int i) {
        this.i = i;
        return this;
    }

    public static int getForegroundNotificationId() {
        return a.c;
    }

    @Nullable
    public static Notification getForegroundNotification(@NonNull Context context) {
        Notification notification;
        synchronized (MediaCoreConfig.class) {
            notification = a.d;
            if (notification == null) {
                notification = a(b.d(context));
            }
        }
        return notification;
    }

    public static MediaCoreConfig edit() {
        MediaCoreConfig mediaCoreConfig;
        synchronized (MediaCoreConfig.class) {
            mediaCoreConfig = new MediaCoreConfig();
            mediaCoreConfig.b = a.b;
            mediaCoreConfig.c = a.c;
            mediaCoreConfig.g = a.g;
            mediaCoreConfig.h = a.h;
            mediaCoreConfig.d = a.d;
            mediaCoreConfig.i = a.i;
            mediaCoreConfig.f = new Bundle(a.f);
            mediaCoreConfig.j = true;
        }
        return mediaCoreConfig;
    }

    public static boolean isHasCustomConfig() {
        return a.j;
    }

    private static Notification a(Context context) {
        Notification notification;
        Notification notification2 = a.e;
        if (notification2 != null) {
            return notification2;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            String str = context.getPackageName() + ":media-core";
            NotificationChannel notificationChannel = new NotificationChannel(str, context.getApplicationInfo().name, 2);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            ((NotificationManager) context.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
            notification = new Notification.Builder(context, str).setSmallIcon(context.getApplicationInfo().icon).setLocalOnly(true).setDefaults(0).setContentTitle("").setContentText("").setPriority(-2).build();
        } else {
            notification = new Notification.Builder(context).setSmallIcon(context.getApplicationInfo().icon).setLocalOnly(true).setDefaults(0).setContentTitle("").setContentText("").setPriority(-2).build();
        }
        a.e = notification;
        return notification;
    }

    public MediaCoreConfig setDeviceId(String str) {
        this.g = str;
        return this;
    }

    public void commit() {
        synchronized (MediaCoreConfig.class) {
            a = this;
        }
    }

    public MediaCoreConfig setForegroundNotification(int i, Notification notification) {
        this.c = i;
        this.d = (Notification) Objects.requireNonNull(notification);
        return this;
    }
}
