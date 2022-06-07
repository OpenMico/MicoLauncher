package com.blankj.utilcode.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.blankj.utilcode.util.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class NotificationUtils {
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Importance {
    }

    public static boolean areNotificationsEnabled() {
        return NotificationManagerCompat.from(Utils.getApp()).areNotificationsEnabled();
    }

    public static void notify(int i, Utils.Consumer<NotificationCompat.Builder> consumer) {
        notify(null, i, ChannelConfig.DEFAULT_CHANNEL_CONFIG, consumer);
    }

    public static void notify(String str, int i, Utils.Consumer<NotificationCompat.Builder> consumer) {
        notify(str, i, ChannelConfig.DEFAULT_CHANNEL_CONFIG, consumer);
    }

    public static void notify(int i, ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        notify(null, i, channelConfig, consumer);
    }

    public static void notify(String str, int i, ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        NotificationManagerCompat.from(Utils.getApp()).notify(str, i, getNotification(channelConfig, consumer));
    }

    public static Notification getNotification(ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) Utils.getApp().getSystemService("notification")).createNotificationChannel(channelConfig.getNotificationChannel());
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Utils.getApp());
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setChannelId(channelConfig.a.getId());
        }
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    public static void cancel(String str, int i) {
        NotificationManagerCompat.from(Utils.getApp()).cancel(str, i);
    }

    public static void cancel(int i) {
        NotificationManagerCompat.from(Utils.getApp()).cancel(i);
    }

    public static void cancelAll() {
        NotificationManagerCompat.from(Utils.getApp()).cancelAll();
    }

    @RequiresPermission("android.permission.EXPAND_STATUS_BAR")
    public static void setNotificationBarVisibility(boolean z) {
        a(z ? Build.VERSION.SDK_INT <= 16 ? "expand" : "expandNotificationsPanel" : Build.VERSION.SDK_INT <= 16 ? "collapse" : "collapsePanels");
    }

    private static void a(String str) {
        try {
            Class.forName("android.app.StatusBarManager").getMethod(str, new Class[0]).invoke(Utils.getApp().getSystemService("statusbar"), new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes.dex */
    public static class ChannelConfig {
        public static final ChannelConfig DEFAULT_CHANNEL_CONFIG = new ChannelConfig(Utils.getApp().getPackageName(), Utils.getApp().getPackageName(), 3);
        private NotificationChannel a;

        public ChannelConfig(String str, CharSequence charSequence, int i) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a = new NotificationChannel(str, charSequence, i);
            }
        }

        public NotificationChannel getNotificationChannel() {
            return this.a;
        }

        public ChannelConfig setBypassDnd(boolean z) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setBypassDnd(z);
            }
            return this;
        }

        public ChannelConfig setDescription(String str) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setDescription(str);
            }
            return this;
        }

        public ChannelConfig setGroup(String str) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setGroup(str);
            }
            return this;
        }

        public ChannelConfig setImportance(int i) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setImportance(i);
            }
            return this;
        }

        public ChannelConfig setLightColor(int i) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setLightColor(i);
            }
            return this;
        }

        public ChannelConfig setLockscreenVisibility(int i) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setLockscreenVisibility(i);
            }
            return this;
        }

        public ChannelConfig setName(CharSequence charSequence) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setName(charSequence);
            }
            return this;
        }

        public ChannelConfig setShowBadge(boolean z) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setShowBadge(z);
            }
            return this;
        }

        public ChannelConfig setSound(Uri uri, AudioAttributes audioAttributes) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setSound(uri, audioAttributes);
            }
            return this;
        }

        public ChannelConfig setVibrationPattern(long[] jArr) {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a.setVibrationPattern(jArr);
            }
            return this;
        }
    }
}
