package com.allenliu.versionchecklib.v2.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.core.PermissionDialogActivity;
import com.allenliu.versionchecklib.core.VersionFileProvider;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import java.io.File;

/* loaded from: classes.dex */
public class NotificationHelper {
    private DownloadBuilder c;
    private Context d;
    private int g;
    private String h;
    NotificationCompat.Builder a = null;
    NotificationManager b = null;
    private boolean e = false;
    private boolean f = false;
    private final int i = 1;

    public NotificationHelper(Context context, DownloadBuilder downloadBuilder) {
        this.g = 0;
        this.d = context;
        this.c = downloadBuilder;
        this.g = 0;
    }

    public void updateNotification(int i) {
        if (this.c.isShowNotification() && i - this.g > 5 && !this.e && !this.f) {
            this.a.setContentIntent(null);
            this.a.setContentText(String.format(this.h, Integer.valueOf(i)));
            this.a.setProgress(100, i, false);
            this.b.notify(1, this.a.build());
            this.g = i;
        }
    }

    public void showNotification() {
        this.e = false;
        this.f = false;
        if (this.c.isShowNotification()) {
            this.b = (NotificationManager) this.d.getSystemService("notification");
            this.a = a();
            this.b.notify(1, this.a.build());
        }
    }

    public void showDownloadCompleteNotifcation(File file) {
        Uri uri;
        this.e = true;
        if (this.c.isShowNotification()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (Build.VERSION.SDK_INT >= 24) {
                Context context = this.d;
                uri = VersionFileProvider.getUriForFile(context, this.d.getPackageName() + ".versionProvider", file);
                ALog.e(this.d.getPackageName() + "");
                intent.addFlags(1);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            this.a.setContentIntent(PendingIntent.getActivity(this.d, 0, intent, 0));
            this.a.setContentText(this.d.getString(R.string.versionchecklib_download_finish));
            this.a.setProgress(100, 100, false);
            this.b.cancelAll();
            this.b.notify(1, this.a.build());
        }
    }

    public void showDownloadFailedNotification() {
        this.e = false;
        this.f = true;
        if (this.c.isShowNotification()) {
            Intent intent = new Intent(this.d, PermissionDialogActivity.class);
            intent.addFlags(268435456);
            this.a.setContentIntent(PendingIntent.getActivity(this.d, 0, intent, 134217728));
            this.a.setContentText(this.d.getString(R.string.versionchecklib_download_fail));
            this.a.setProgress(100, 0, false);
            this.b.notify(1, this.a.build());
        }
    }

    private NotificationCompat.Builder a() {
        NotificationBuilder notificationBuilder = this.c.getNotificationBuilder();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("0", "ALLEN_NOTIFICATION", 2);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(-65536);
            notificationChannel.enableVibration(false);
            ((NotificationManager) this.d.getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.d, "0");
        builder.setAutoCancel(true);
        builder.setSmallIcon(this.c.getNotificationBuilder().getIcon());
        String string = this.d.getString(R.string.app_name);
        if (notificationBuilder.getContentTitle() != null) {
            string = notificationBuilder.getContentTitle();
        }
        builder.setContentTitle(string);
        String string2 = this.d.getString(R.string.versionchecklib_downloading);
        if (notificationBuilder.getTicker() != null) {
            string2 = notificationBuilder.getTicker();
        }
        builder.setTicker(string2);
        this.h = this.d.getString(R.string.versionchecklib_download_progress);
        if (notificationBuilder.getContentText() != null) {
            this.h = notificationBuilder.getContentText();
        }
        builder.setContentText(String.format(this.h, 0));
        if (notificationBuilder.isRingtone()) {
            RingtoneManager.getRingtone(this.d, RingtoneManager.getDefaultUri(2)).play();
        }
        return builder;
    }

    public void onDestroy() {
        NotificationManager notificationManager = this.b;
        if (notificationManager != null) {
            notificationManager.cancel(1);
        }
    }

    public Notification getServiceNotification() {
        if (Build.VERSION.SDK_INT < 26) {
            return new NotificationCompat.Builder(this.d).setContentTitle(this.d.getString(R.string.app_name)).setContentText(this.d.getString(R.string.versionchecklib_version_service_runing)).setPriority(0).setSmallIcon(this.c.getNotificationBuilder().getIcon()).setAutoCancel(false).build();
        }
        NotificationCompat.Builder autoCancel = new NotificationCompat.Builder(this.d, "version_service_id").setContentTitle(this.d.getString(R.string.app_name)).setContentText(this.d.getString(R.string.versionchecklib_version_service_runing)).setSmallIcon(this.c.getNotificationBuilder().getIcon()).setAutoCancel(false);
        NotificationChannel notificationChannel = new NotificationChannel("version_service_id", "version_service_name", 2);
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(false);
        ((NotificationManager) this.d.getSystemService("notification")).createNotificationChannel(notificationChannel);
        return autoCancel.build();
    }
}
