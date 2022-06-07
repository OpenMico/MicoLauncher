package com.xiaomi.micolauncher.module.appstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.appstore.bean.DownloadRequest;
import com.xiaomi.micolauncher.module.appstore.manager.AppDownloadManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AppDownloadStatusActivity extends BaseActivity {
    ImageView a;
    TextView b;
    ProgressBar c;
    TextView d;
    TextView e;
    private DownloadRequest h;
    private long f = -1;
    private int g = 1;
    private Handler i = new MicoHandler(Looper.getMainLooper()) { // from class: com.xiaomi.micolauncher.module.appstore.activity.AppDownloadStatusActivity.1
        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return "AppStore";
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
            if (message.what == 0) {
                AppDownloadStatusActivity.this.a();
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        AppDownloadManager.getAppDownloadManager().queryDownloadDetailInfoByDownloadId(this, this.f).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.activity.-$$Lambda$AppDownloadStatusActivity$HLWFT0TGGCcIWQ-DKTwEDkPHqt0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppDownloadStatusActivity.this.b((DownloadRequest) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.activity.-$$Lambda$AppDownloadStatusActivity$N3tMu7HA94ks_nkDuxpG_akZEfI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppDownloadStatusActivity.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(DownloadRequest downloadRequest) throws Exception {
        this.h = downloadRequest;
        int downloadStatus = downloadRequest.getDownloadStatus();
        if (this.g != downloadStatus) {
            this.g = downloadStatus;
            L.storage.d("%s Download Status: %s", "AppDownloadStatusActivity", Integer.valueOf(this.g));
        }
        int i = this.g;
        if (i != 4) {
            if (i == 8) {
                this.d.setText(R.string.activity_app_download_succeed);
                a(downloadRequest);
                return;
            } else if (i != 16) {
                switch (i) {
                    case 1:
                    case 2:
                        break;
                    default:
                        L.storage.e("AppDownloadStatusActivity not handled %s", Integer.valueOf(this.g));
                        return;
                }
            } else {
                this.d.setText(R.string.activity_app_download_status_error_hint);
                scheduleToClose(BaseActivity.DEFAULT_CLOSE_DURATION);
                return;
            }
        }
        int downloadedSize = (int) ((downloadRequest.getDownloadedSize() * 80) / downloadRequest.getTotalSize());
        this.c.setProgress(downloadedSize);
        int i2 = this.g;
        if (i2 == 2) {
            if (downloadedSize > 0) {
                this.d.setText(getString(R.string.activity_app_download_status_percent, new Object[]{Integer.valueOf(downloadedSize)}));
            } else {
                this.d.setText(R.string.activity_app_download_status);
            }
        } else if (i2 == 4) {
            this.d.setText(getString(R.string.activity_app_download_status_pause_percent, new Object[]{Integer.valueOf(downloadedSize)}));
        } else {
            this.d.setText(getString(R.string.activity_app_download_status_pending_percent, new Object[]{Integer.valueOf(downloadedSize)}));
        }
        this.i.sendEmptyMessageDelayed(0, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.storage.e("AppDownloadStatusActivity", th);
        DownloadRequest downloadRequest = this.h;
        if (downloadRequest != null) {
            a(downloadRequest);
        }
    }

    private void a(DownloadRequest downloadRequest) {
        int installStatus = downloadRequest.getInstallStatus();
        if (installStatus == -2) {
            int installProgress = (int) (((downloadRequest.getInstallProgress() * 20.0f) / 100.0f) + 80.0f);
            this.c.setProgress(installProgress);
            this.d.setText(String.format(getString(R.string.activity_app_download_status_percent), Integer.valueOf(installProgress)));
            this.i.sendEmptyMessageDelayed(0, 250L);
        } else if (installStatus != 0) {
            this.d.setText(R.string.activity_app_install_status_error);
            scheduleToClose(BaseActivity.DEFAULT_CLOSE_DURATION);
        } else {
            if (!isDestroyed() && downloadRequest.getDownloadCallback() != null) {
                downloadRequest.getDownloadCallback().OnDownloadResult(downloadRequest);
            }
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_app_download_status);
        this.a = (ImageView) findViewById(R.id.icon_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (ProgressBar) findViewById(R.id.progress_bar);
        this.d = (TextView) findViewById(R.id.progress_tv);
        this.e = (TextView) findViewById(R.id.error_tv);
        a(getIntent());
    }

    void a(Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("EXTRA_ICON_URL");
            if (ContainerUtil.hasData(stringExtra)) {
                GlideUtils.bindImageView(this, stringExtra, this.a);
            }
            String stringExtra2 = intent.getStringExtra("EXTRA_APP_NAME");
            if (ContainerUtil.hasData(stringExtra2)) {
                this.b.setText(stringExtra2);
            }
            this.f = intent.getLongExtra("EXTRA_DOWNLOAD_ID", 0L);
        }
        long j = this.f;
        if (j > 0) {
            this.i.sendEmptyMessage(0);
        } else if (j == -1) {
            this.d.setText(R.string.activity_app_download_status);
        } else if (j == -2) {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent);
    }

    public static void startAppDownloadStatusActivity(Context context, String str, String str2, long j) {
        Intent intent = new Intent(context, AppDownloadStatusActivity.class);
        intent.putExtra("EXTRA_ICON_URL", str);
        intent.putExtra("EXTRA_APP_NAME", str2);
        intent.putExtra("EXTRA_DOWNLOAD_ID", j);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    public static void dealNotEnoughSpace(Context context) {
        Intent intent = new Intent(context, AppDownloadStatusActivity.class);
        intent.putExtra("EXTRA_DOWNLOAD_ID", -2);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChanged(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (!wifiConnectivityChangedEvent.isWifiSetting) {
            return;
        }
        if (wifiConnectivityChangedEvent.connected) {
            this.i.sendEmptyMessage(0);
        } else if (this.g != 8) {
            this.d.setText(R.string.activity_app_download_status_error_hint);
        }
    }
}
