package com.allenliu.versionchecklib.core;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.callback.DownloadListener;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.core.http.FileCallBack;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.utils.AppUtils;
import java.io.File;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public class DownloadManager {
    private static int a = 0;
    private static boolean b = false;

    public static void downloadAPK(final String str, final VersionParams versionParams, final DownloadListener downloadListener) {
        final NotificationManager notificationManager;
        a = 0;
        b = false;
        if (str != null && !str.isEmpty()) {
            String str2 = versionParams.getDownloadAPKPath() + AllenChecker.getGlobalContext().getString(R.string.versionchecklib_download_apkname, AllenChecker.getGlobalContext().getPackageName());
            if (versionParams.isSilentDownload()) {
                if (versionParams.isForceRedownload()) {
                    a(AllenChecker.getGlobalContext(), str, versionParams, downloadListener);
                } else if (!checkAPKIsExists(AllenChecker.getGlobalContext(), str2)) {
                    a(AllenChecker.getGlobalContext(), str, versionParams, downloadListener);
                } else if (downloadListener != null) {
                    downloadListener.onCheckerDownloadSuccess(new File(str2));
                }
            } else if (versionParams.isForceRedownload() || !checkAPKIsExists(AllenChecker.getGlobalContext(), str2)) {
                if (downloadListener != null) {
                    downloadListener.onCheckerStartDownload();
                }
                final NotificationCompat.Builder builder = null;
                if (versionParams.isShowNotification()) {
                    NotificationManager notificationManager2 = (NotificationManager) AllenChecker.getGlobalContext().getSystemService("notification");
                    NotificationCompat.Builder a2 = a(AllenChecker.getGlobalContext());
                    notificationManager2.notify(0, a2.build());
                    builder = a2;
                    notificationManager = notificationManager2;
                } else {
                    notificationManager = null;
                }
                AllenHttp.getHttpClient().newCall(new Request.Builder().url(str).build()).enqueue(new FileCallBack(versionParams.getDownloadAPKPath(), AllenChecker.getGlobalContext().getString(R.string.versionchecklib_download_apkname, AllenChecker.getGlobalContext().getPackageName())) { // from class: com.allenliu.versionchecklib.core.DownloadManager.1
                    @Override // com.allenliu.versionchecklib.core.http.FileCallBack
                    public void onSuccess(File file, Call call, Response response) {
                        Uri uri;
                        downloadListener.onCheckerDownloadSuccess(file);
                        boolean unused = DownloadManager.b = true;
                        if (versionParams.isShowNotification()) {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            if (Build.VERSION.SDK_INT >= 24) {
                                Context globalContext = AllenChecker.getGlobalContext();
                                uri = VersionFileProvider.getUriForFile(globalContext, AllenChecker.getGlobalContext().getPackageName() + ".versionProvider", file);
                                ALog.e(AllenChecker.getGlobalContext().getPackageName() + "");
                                intent.addFlags(1);
                            } else {
                                uri = Uri.fromFile(file);
                            }
                            ALog.e("APK download Success");
                            intent.setDataAndType(uri, "application/vnd.android.package-archive");
                            builder.setContentIntent(PendingIntent.getActivity(AllenChecker.getGlobalContext(), 0, intent, 0));
                            builder.setContentText(AllenChecker.getGlobalContext().getString(R.string.versionchecklib_download_finish));
                            builder.setProgress(100, 100, false);
                            notificationManager.cancelAll();
                            notificationManager.notify(0, builder.build());
                        }
                        AppUtils.installApk(AllenChecker.getGlobalContext(), file);
                    }

                    @Override // com.allenliu.versionchecklib.core.http.FileCallBack
                    public void onDownloading(int i) {
                        ALog.e("downloadProgress:" + i + "");
                        downloadListener.onCheckerDownloading(i);
                        if (i - DownloadManager.a >= 5) {
                            int unused = DownloadManager.a = i;
                            if (versionParams.isShowNotification() && !DownloadManager.b) {
                                builder.setContentIntent(null);
                                builder.setContentText(String.format(AllenChecker.getGlobalContext().getString(R.string.versionchecklib_download_progress), Integer.valueOf(DownloadManager.a)));
                                builder.setProgress(100, DownloadManager.a, false);
                                notificationManager.notify(0, builder.build());
                            }
                        }
                    }

                    @Override // com.allenliu.versionchecklib.core.http.FileCallBack
                    public void onDownloadFailed() {
                        if (versionParams.isShowNotification()) {
                            Intent intent = new Intent(AllenChecker.getGlobalContext(), versionParams.getCustomDownloadActivityClass());
                            intent.putExtra("isRetry", true);
                            intent.putExtra(AVersionService.VERSION_PARAMS_KEY, versionParams);
                            intent.putExtra("downloadUrl", str);
                            builder.setContentIntent(PendingIntent.getActivity(AllenChecker.getGlobalContext(), 0, intent, 134217728));
                            builder.setContentText(AllenChecker.getGlobalContext().getString(R.string.versionchecklib_download_fail));
                            builder.setProgress(100, 0, false);
                            notificationManager.notify(0, builder.build());
                        }
                        ALog.e("file download failed");
                        downloadListener.onCheckerDownloadFail();
                    }
                });
            } else {
                if (downloadListener != null) {
                    downloadListener.onCheckerDownloadSuccess(new File(str2));
                }
                AppUtils.installApk(AllenChecker.getGlobalContext(), new File(str2));
            }
        }
    }

    private static void a(Context context, String str, VersionParams versionParams, final DownloadListener downloadListener) {
        Request build = new Request.Builder().url(str).build();
        if (downloadListener != null) {
            downloadListener.onCheckerStartDownload();
        }
        AllenHttp.getHttpClient().newCall(build).enqueue(new FileCallBack(versionParams.getDownloadAPKPath(), context.getString(R.string.versionchecklib_download_apkname, context.getPackageName())) { // from class: com.allenliu.versionchecklib.core.DownloadManager.2
            @Override // com.allenliu.versionchecklib.core.http.FileCallBack
            public void onSuccess(File file, Call call, Response response) {
                downloadListener.onCheckerDownloadSuccess(file);
            }

            @Override // com.allenliu.versionchecklib.core.http.FileCallBack
            public void onDownloading(int i) {
                ALog.e("silent downloadProgress:" + i + "");
                if (i - DownloadManager.a >= 5) {
                    int unused = DownloadManager.a = i;
                }
                downloadListener.onCheckerDownloading(i);
            }

            @Override // com.allenliu.versionchecklib.core.http.FileCallBack
            public void onDownloadFailed() {
                ALog.e("file silent download failed");
                downloadListener.onCheckerDownloadFail();
            }
        });
    }

    public static boolean checkAPKIsExists(Context context, String str) {
        return checkAPKIsExists(context, str, null);
    }

    public static boolean checkAPKIsExists(Context context, String str, Integer num) {
        if (!new File(str).exists()) {
            return false;
        }
        try {
            PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 1);
            ALog.e("本地安装包版本号：" + packageArchiveInfo.versionCode + "\n 当前app版本号：" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
            if (!context.getPackageName().equalsIgnoreCase(packageArchiveInfo.packageName) || context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode == packageArchiveInfo.versionCode) {
                return false;
            }
            if (num != null) {
                if (packageArchiveInfo.versionCode < num.intValue()) {
                    return false;
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static NotificationCompat.Builder a(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("0", "ALLEN_NOTIFICATION", 2);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(-65536);
            notificationChannel.enableVibration(false);
            ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setTicker(context.getString(R.string.versionchecklib_downloading));
        builder.setContentText(String.format(context.getString(R.string.versionchecklib_download_progress), 0));
        RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(2)).play();
        return builder;
    }
}
