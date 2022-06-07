package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.offline.Download;
import java.util.List;

/* loaded from: classes2.dex */
public final class DownloadNotificationHelper {
    private final NotificationCompat.Builder a;

    public DownloadNotificationHelper(Context context, String str) {
        this.a = new NotificationCompat.Builder(context.getApplicationContext(), str);
    }

    public Notification buildProgressNotification(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str, List<Download> list) {
        int i2;
        int i3;
        boolean z = true;
        float f = 0.0f;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = false;
        int i4 = 0;
        boolean z5 = false;
        for (int i5 = 0; i5 < list.size(); i5++) {
            Download download = list.get(i5);
            if (download.state == 5) {
                z4 = true;
            } else if (download.state == 7 || download.state == 2) {
                float percentDownloaded = download.getPercentDownloaded();
                if (percentDownloaded != -1.0f) {
                    f += percentDownloaded;
                    z2 = false;
                }
                z5 = (download.getBytesDownloaded() > 0) | z5;
                i4++;
                z3 = true;
            }
        }
        if (z3) {
            i2 = R.string.exo_download_downloading;
        } else {
            i2 = z4 ? R.string.exo_download_removing : 0;
        }
        if (z3) {
            i3 = (int) (f / i4);
            if (!z2 || !z5) {
                z = false;
            }
        } else {
            z = true;
            i3 = 0;
        }
        return a(context, i, pendingIntent, str, i2, 100, i3, z, true, false);
    }

    public Notification buildDownloadCompletedNotification(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str) {
        return a(context, i, pendingIntent, str, R.string.exo_download_completed);
    }

    public Notification buildDownloadFailedNotification(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str) {
        return a(context, i, pendingIntent, str, R.string.exo_download_failed);
    }

    private Notification a(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str, @StringRes int i2) {
        return a(context, i, pendingIntent, str, i2, 0, 0, false, false, true);
    }

    private Notification a(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str, @StringRes int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        this.a.setSmallIcon(i);
        NotificationCompat.BigTextStyle bigTextStyle = null;
        this.a.setContentTitle(i2 == 0 ? null : context.getResources().getString(i2));
        this.a.setContentIntent(pendingIntent);
        NotificationCompat.Builder builder = this.a;
        if (str != null) {
            bigTextStyle = new NotificationCompat.BigTextStyle().bigText(str);
        }
        builder.setStyle(bigTextStyle);
        this.a.setProgress(i3, i4, z);
        this.a.setOngoing(z2);
        this.a.setShowWhen(z3);
        return this.a.build();
    }
}
