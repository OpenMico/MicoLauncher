package com.xiaomi.micolauncher.common.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.common.schema.handler.SettingSchemaHandler;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerActivityV2;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.AlarmEvent;

/* loaded from: classes3.dex */
public class NotificationHelper {
    public static final int DURATION_SECS_ALBUM = 10;
    public static final int DURATION_SECS_BATTERY = 7;
    public static final int DURATION_SECS_ENTER_CHILD_MODE = 15;
    public static final int DURATION_SECS_INTEGER_MAX_VALUE = Integer.MAX_VALUE;
    public static final int INVALID_ACTION_RES = -1;
    public static final int NOTIFICATION_TYPE_ALARM = 2;
    public static final int NOTIFICATION_TYPE_BABY_COURSE = 4;
    public static final int NOTIFICATION_TYPE_DOORBELL = 1;
    public static final int NOTIFICATION_TYPE_HOURLY_CHIME = 6;
    public static final int NOTIFICATION_TYPE_NETWORK_LOST = 0;
    public static final int NOTIFICATION_TYPE_SLIDE_TO_LOOK_MORE = 3;
    public static final int NOTIFICATION_TYPE_SUPPORT_GESTURE = 5;
    private static boolean a = false;
    private static boolean b = false;

    /* loaded from: classes3.dex */
    public interface AlarmContentText {
        default String alarmId() {
            return "";
        }

        AlarmContent getAlarmContentText(Context context, boolean z);

        default int notificationType() {
            return 2;
        }
    }

    public static void init(Context context) {
        a = Hardware.isBigScreen();
        a(context, "mico_launcher_no_sound", 2);
        a(context, "mico_launcher_sound", 4);
        a(context, "com.xiaomi.micolauncher", 2);
        b = true;
    }

    public static boolean isInit() {
        return b;
    }

    private static void a(Context context, String str, int i) {
        NotificationManager a2;
        if (context != null && (a2 = a(context)) != null && a2.getNotificationChannel(str) == null) {
            a2.createNotificationChannel(new NotificationChannel(str, str, i));
        }
    }

    public static void notify(Context context, int i) {
        notify(context, i, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void notify(Context context, int i, AlarmContentText alarmContentText) {
        MicoNotification.Builder builder;
        int i2 = R.drawable.notice_alarm;
        boolean z = false;
        switch (i) {
            case 0:
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(SettingSchemaHandler.ACTION_WIFI));
                PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 0);
                builder = new MicoNotification.Builder(context);
                int i3 = R.string.notification_network_lost;
                MicoNotification.Builder title = builder.setNotificationId(R.string.notification_network_lost).setSmallIcon(a ? R.drawable.notice_wifi : R.drawable.notification_wifi).setTitle(context.getString(R.string.notification_title_net));
                if (a) {
                    i3 = R.string.notice_network_disconnected;
                }
                title.setText(context.getString(i3)).setHowToDeal(context.getString(R.string.notice_to_setting)).setPendingIntent(activity).setTimeoutAfter(Integer.MAX_VALUE).setLayoutId(R.layout.remoteview_network).setActionRes(R.string.setting);
                z = true;
                break;
            case 1:
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setData(Uri.parse(SettingSchemaHandler.ACTION_DOORBELL));
                PendingIntent activity2 = PendingIntent.getActivity(context, 0, intent2, 0);
                builder = new MicoNotification.Builder(context);
                builder.setNotificationId(R.string.doorbell_notify).setSmallIcon(a ? R.drawable.notice_ring : R.drawable.notification_bell).setTitle(context.getString(R.string.notification_title_door_ring)).setText(context.getString(R.string.notice_visitor_is_coming)).setHowToDeal(context.getString(R.string.notice_hang_up)).setPendingIntent(activity2).setTimeoutAfter(Integer.MAX_VALUE).setLayoutId(R.layout.view_alarm_banner).setActionRes(R.string.doorbell_notify);
                break;
            case 2:
                AlarmContent alarmContentText2 = alarmContentText.getAlarmContentText(context, a);
                L.alarm.d("AlarmContent %s", alarmContentText.toString());
                builder = new MicoNotification.Builder(context);
                MicoNotification.Builder notificationId = builder.setNotificationId(R.string.alarm_notify);
                if (!a) {
                    i2 = R.drawable.notification_alarm;
                }
                notificationId.setSmallIcon(i2).setTitle(context.getString(R.string.notification_title_alarm_bell)).setText(alarmContentText2.a).setHowToDeal(alarmContentText2.b).setTimeoutAfter(600).setLayoutId(R.layout.view_alarm_banner).setActionRes(-1);
                break;
            case 3:
                builder = new MicoNotification.Builder(context);
                MicoNotification.Builder notificationId2 = builder.setNotificationId(R.string.lateral_sliding_look_more_content);
                if (!a) {
                    i2 = R.drawable.notification_alarm;
                }
                notificationId2.setSmallIcon(i2).setTitle(context.getString(R.string.tips)).setText(context.getString(R.string.lateral_sliding_look_more_content)).setTimeoutAfter(5).setLayoutId(R.layout.view_alarm_banner).setActionRes(-1);
                break;
            case 4:
                Intent intent3 = new Intent("android.intent.action.VIEW");
                intent3.setData(Uri.parse(SettingSchemaHandler.ACTION_BABY_COURSE).buildUpon().appendQueryParameter(AlarmEvent.KEY_ALARM_ID, alarmContentText.alarmId()).build());
                PendingIntent activity3 = PendingIntent.getActivity(context, 0, intent3, 0);
                builder = new MicoNotification.Builder(context);
                builder.setNotificationId(R.string.notify_baby_course).setSmallIcon(R.drawable.notice_timetable).setTitle(context.getString(R.string.notify_baby_course)).setText(context.getString(R.string.notify_baby_course)).setHowToDeal(context.getString(R.string.notice_hang_up)).setPendingIntent(activity3).setTimeoutAfter(60).setLayoutId(R.layout.view_alarm_banner).setActionRes(-1);
                break;
            case 5:
                if (a) {
                    builder = new MicoNotification.Builder(context);
                    builder.setNotificationId(R.string.support_gesture_control).setSmallIcon(R.drawable.gesture_icon).setTitle(context.getString(R.string.notification_title_message)).setText(context.getString(R.string.support_gesture_control)).setTimeoutAfter(2);
                    break;
                }
                builder = null;
                break;
            case 6:
                AlarmContent alarmContentText3 = alarmContentText.getAlarmContentText(context, a);
                builder = new MicoNotification.Builder(context);
                MicoNotification.Builder notificationId3 = builder.setNotificationId(R.string.hourly_chime_title);
                if (!a) {
                    i2 = R.drawable.notification_alarm;
                }
                notificationId3.setSmallIcon(i2).setTitle(context.getResources().getString(R.string.hourly_chime_title)).setText(alarmContentText3.a).setHowToDeal(alarmContentText3.b).setTimeoutAfter(600).setLayoutId(R.layout.view_alarm_banner).setActionRes(-1);
                break;
            default:
                builder = null;
                break;
        }
        if (builder != null) {
            MicoNotification build = builder.build();
            if (z) {
                build.showSilently();
            } else {
                build.show();
            }
        }
    }

    public static void startForegroundNotification(Service service, int i) {
        if (!b) {
            a(service.getApplicationContext(), "com.xiaomi.micolauncher", 2);
        }
        Notification.Builder builder = new Notification.Builder(service.getApplicationContext(), "com.xiaomi.micolauncher");
        builder.setContentIntent(PendingIntent.getActivity(service, 0, new Intent(service, PlayerActivityV2.class), 0));
        builder.setContentText("");
        builder.setContentTitle("");
        builder.setSmallIcon(R.drawable.avatar_default);
        service.startForeground(i, builder.build());
    }

    public static void cancelNotification(Context context, int i) {
        MicoNotification.a(context, i);
    }

    private static NotificationManager a(Context context) {
        return (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    /* loaded from: classes3.dex */
    public static class AlarmContent {
        String a;
        String b;

        public AlarmContent(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }
}
