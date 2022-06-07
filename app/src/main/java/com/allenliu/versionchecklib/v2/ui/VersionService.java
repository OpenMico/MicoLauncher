package com.allenliu.versionchecklib.v2.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.callback.DownloadListener;
import com.allenliu.versionchecklib.core.DownloadManager;
import com.allenliu.versionchecklib.core.PermissionDialogActivity;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.utils.AllenEventBusUtil;
import com.allenliu.versionchecklib.utils.AppUtils;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.RequestVersionBuilder;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.allenliu.versionchecklib.v2.eventbus.CommonEvent;
import com.allenliu.versionchecklib.v2.net.DownloadMangerV2;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes.dex */
public class VersionService extends Service {
    public static DownloadBuilder builder;
    private BuilderHelper a;
    private NotificationHelper b;
    private boolean c = false;
    private ExecutorService d;

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ALog.e("version service create");
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        ALog.e("version service destroy");
        builder = null;
        this.a = null;
        NotificationHelper notificationHelper = this.b;
        if (notificationHelper != null) {
            notificationHelper.onDestroy();
        }
        this.b = null;
        this.c = false;
        ExecutorService executorService = this.d;
        if (executorService != null) {
            executorService.shutdown();
        }
        stopForeground(true);
        AllenHttp.getHttpClient().dispatcher().cancelAll();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public static void enqueueWork(Context context) {
        Intent intent = new Intent(context, VersionService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    protected void onHandleWork() {
        if (b()) {
            a();
        } else {
            c();
        }
    }

    private void a() {
        Request request;
        RequestVersionBuilder requestVersionBuilder = builder.getRequestVersionBuilder();
        OkHttpClient httpClient = AllenHttp.getHttpClient();
        switch (requestVersionBuilder.getRequestMethod()) {
            case GET:
                request = AllenHttp.get(requestVersionBuilder).build();
                break;
            case POST:
                request = AllenHttp.post(requestVersionBuilder).build();
                break;
            case POSTJSON:
                request = AllenHttp.postJson(requestVersionBuilder).build();
                break;
            default:
                request = null;
                break;
        }
        final RequestVersionListener requestVersionListener = requestVersionBuilder.getRequestVersionListener();
        Handler handler = new Handler(Looper.getMainLooper());
        if (requestVersionListener != null) {
            try {
                final Response execute = httpClient.newCall(request).execute();
                if (execute.isSuccessful()) {
                    if (this.c) {
                        final String string = execute.body().string();
                        handler.post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.ui.VersionService.1
                            @Override // java.lang.Runnable
                            public void run() {
                                VersionService.builder.setVersionBundle(requestVersionListener.onRequestVersionSuccess(string));
                                VersionService.this.c();
                            }
                        });
                    }
                } else if (this.c) {
                    handler.post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.ui.VersionService.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AllenVersionChecker.getInstance().cancelAllMission(VersionService.this.getApplicationContext());
                            requestVersionListener.onRequestVersionFailure(execute.message());
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (this.c) {
                    handler.post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.ui.VersionService.3
                        @Override // java.lang.Runnable
                        public void run() {
                            AllenVersionChecker.getInstance().cancelAllMission(VersionService.this.getApplicationContext());
                            requestVersionListener.onRequestVersionFailure(e.getMessage());
                        }
                    });
                }
            }
        } else {
            throw new RuntimeException("using request version function,you must set a requestVersionListener");
        }
    }

    private boolean b() {
        return builder.getRequestVersionBuilder() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (builder.getVersionBundle() == null) {
            AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
        } else if (builder.isDirectDownload()) {
            AllenEventBusUtil.sendEventBus(98);
        } else if (builder.isSilentDownload()) {
            g();
        } else {
            d();
        }
    }

    private void d() {
        if (builder != null) {
            Intent intent = new Intent(this, UIActivity.class);
            intent.addFlags(268435456);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        DownloadBuilder downloadBuilder = builder;
        if (downloadBuilder != null && downloadBuilder.isShowDownloadingDialog()) {
            Intent intent = new Intent(this, DownloadingActivity.class);
            intent.addFlags(268435456);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        CommonEvent commonEvent = new CommonEvent();
        commonEvent.setEventType(100);
        commonEvent.setData(Integer.valueOf(i));
        commonEvent.setSuccessful(true);
        EventBus.getDefault().post(commonEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (builder != null) {
            Intent intent = new Intent(this, DownloadFailedActivity.class);
            intent.addFlags(268435456);
            startActivity(intent);
        }
    }

    private void g() {
        if (builder != null) {
            Intent intent = new Intent(this, PermissionDialogActivity.class);
            intent.addFlags(268435456);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        AllenEventBusUtil.sendEventBus(101);
        String i = i();
        if (builder.isSilentDownload()) {
            d();
            return;
        }
        this.a.checkForceUpdate();
        AppUtils.installApk(getApplicationContext(), new File(i));
    }

    private String i() {
        StringBuilder sb = new StringBuilder();
        sb.append(builder.getDownloadAPKPath());
        int i = R.string.versionchecklib_download_apkname;
        Object[] objArr = new Object[1];
        objArr[0] = builder.getApkName() != null ? builder.getApkName() : getPackageName();
        sb.append(getString(i, objArr));
        return sb.toString();
    }

    @WorkerThread
    private void j() {
        String i = i();
        if (!DownloadManager.checkAPKIsExists(getApplicationContext(), i, builder.getNewestVersionCode()) || builder.isForceRedownload()) {
            this.a.checkAndDeleteAPK();
            String downloadUrl = builder.getDownloadUrl();
            if (downloadUrl == null && builder.getVersionBundle() != null) {
                downloadUrl = builder.getVersionBundle().getDownloadUrl();
            }
            if (downloadUrl != null) {
                ALog.e("downloadPath:" + i);
                String downloadAPKPath = builder.getDownloadAPKPath();
                int i2 = R.string.versionchecklib_download_apkname;
                Object[] objArr = new Object[1];
                objArr[0] = builder.getApkName() != null ? builder.getApkName() : getPackageName();
                DownloadMangerV2.download(downloadUrl, downloadAPKPath, getString(i2, objArr), new DownloadListener() { // from class: com.allenliu.versionchecklib.v2.ui.VersionService.4
                    @Override // com.allenliu.versionchecklib.callback.DownloadListener
                    public void onCheckerDownloading(int i3) {
                        if (VersionService.this.c) {
                            if (!VersionService.builder.isSilentDownload()) {
                                VersionService.this.b.updateNotification(i3);
                                VersionService.this.a(i3);
                            }
                            if (VersionService.builder.getApkDownloadListener() != null) {
                                VersionService.builder.getApkDownloadListener().onDownloading(i3);
                            }
                        }
                    }

                    @Override // com.allenliu.versionchecklib.callback.DownloadListener
                    public void onCheckerDownloadSuccess(File file) {
                        if (VersionService.this.c) {
                            if (!VersionService.builder.isSilentDownload()) {
                                VersionService.this.b.showDownloadCompleteNotifcation(file);
                            }
                            if (VersionService.builder.getApkDownloadListener() != null) {
                                VersionService.builder.getApkDownloadListener().onDownloadSuccess(file);
                            }
                            VersionService.this.h();
                        }
                    }

                    @Override // com.allenliu.versionchecklib.callback.DownloadListener
                    public void onCheckerDownloadFail() {
                        if (VersionService.this.c) {
                            if (VersionService.builder.getApkDownloadListener() != null) {
                                VersionService.builder.getApkDownloadListener().onDownloadFail();
                            }
                            if (!VersionService.builder.isSilentDownload()) {
                                AllenEventBusUtil.sendEventBus(102);
                                if (VersionService.builder.isShowDownloadFailDialog()) {
                                    VersionService.this.f();
                                }
                                VersionService.this.b.showDownloadFailedNotification();
                                return;
                            }
                            AllenVersionChecker.getInstance().cancelAllMission(VersionService.this.getApplicationContext());
                        }
                    }

                    @Override // com.allenliu.versionchecklib.callback.DownloadListener
                    public void onCheckerStartDownload() {
                        ALog.e("start download apk");
                        if (!VersionService.builder.isSilentDownload()) {
                            VersionService.this.b.showNotification();
                            VersionService.this.e();
                        }
                    }
                });
                return;
            }
            AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
            throw new RuntimeException("you must set a download url for download function using");
        }
        ALog.e("using cache");
        h();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(CommonEvent commonEvent) {
        switch (commonEvent.getEventType()) {
            case 98:
                g();
                return;
            case 99:
                if (((Boolean) commonEvent.getData()).booleanValue()) {
                    j();
                    return;
                } else {
                    stopSelf();
                    return;
                }
            default:
                return;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public synchronized void onReceiveDownloadBuilder(DownloadBuilder downloadBuilder) {
        builder = downloadBuilder;
        if (builder != null) {
            this.c = true;
            this.a = new BuilderHelper(getApplicationContext(), builder);
            this.b = new NotificationHelper(getApplicationContext(), builder);
            startForeground(1, this.b.getServiceNotification());
            this.d = Executors.newSingleThreadExecutor();
            this.d.submit(new Runnable() { // from class: com.allenliu.versionchecklib.v2.ui.VersionService.5
                @Override // java.lang.Runnable
                public void run() {
                    VersionService.this.onHandleWork();
                }
            });
        }
        EventBus.getDefault().removeStickyEvent(downloadBuilder);
    }
}
