package com.allenliu.versionchecklib.core;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.callback.DownloadListener;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.utils.ALog;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class AVersionService extends Service implements DownloadListener {
    public static final String PERMISSION_ACTION = "com.allenliu.versionchecklib.filepermisssion.action";
    public static final String VERSION_PARAMS_EXTRA_KEY = "VERSION_PARAMS_EXTRA_KEY";
    public static final String VERSION_PARAMS_KEY = "VERSION_PARAMS_KEY";
    Callback a = new Callback() { // from class: com.allenliu.versionchecklib.core.AVersionService.1
        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            AVersionService.this.c();
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                final String string = response.body().string();
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.allenliu.versionchecklib.core.AVersionService.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AVersionService.this.onResponses(AVersionService.this, string);
                    }
                });
                return;
            }
            AVersionService.this.c();
        }
    };
    String b;
    String c;
    String d;
    Bundle e;
    protected VersionParams versionParams;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerDownloading(int i) {
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerStartDownload() {
    }

    public abstract void onResponses(AVersionService aVersionService, String str);

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            try {
                this.versionParams = (VersionParams) intent.getParcelableExtra(VERSION_PARAMS_KEY);
                a();
                if (this.versionParams.isOnlyDownload()) {
                    showVersionDialog(this.versionParams.getDownloadUrl(), this.versionParams.getTitle(), this.versionParams.getUpdateMsg(), this.versionParams.getParamBundle());
                } else {
                    b();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void a() {
        try {
            String str = this.versionParams.getDownloadAPKPath() + getApplicationContext().getString(R.string.versionchecklib_download_apkname, getApplicationContext().getPackageName());
            if (!DownloadManager.checkAPKIsExists(getApplicationContext(), str)) {
                ALog.e("删除本地apk");
                new File(str).delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        long pauseRequestTime = this.versionParams.getPauseRequestTime();
        if (pauseRequestTime > 0) {
            ALog.e("请求版本接口失败，下次请求将在" + pauseRequestTime + "ms后开始");
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.allenliu.versionchecklib.core.AVersionService.2
                @Override // java.lang.Runnable
                public void run() {
                    AVersionService.this.b();
                }
            }, pauseRequestTime);
        }
    }

    private void d() {
        Request request;
        OkHttpClient httpClient = AllenHttp.getHttpClient();
        switch (this.versionParams.getRequestMethod()) {
            case GET:
                request = AllenHttp.get(this.versionParams).build();
                break;
            case POST:
                request = AllenHttp.post(this.versionParams).build();
                break;
            case POSTJSON:
                request = AllenHttp.postJson(this.versionParams).build();
                break;
            default:
                request = null;
                break;
        }
        httpClient.newCall(request).enqueue(this.a);
    }

    public void showVersionDialog(String str, String str2, String str3) {
        showVersionDialog(str, str2, str3, null);
    }

    public void showVersionDialog(String str, String str2, String str3, Bundle bundle) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = bundle;
        if (this.versionParams.isSilentDownload()) {
            registerReceiver(new VersionBroadCastReceiver(), new IntentFilter(PERMISSION_ACTION));
            Intent intent = new Intent(this, PermissionDialogActivity.class);
            intent.addFlags(268435456);
            startActivity(intent);
            return;
        }
        f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        DownloadManager.downloadAPK(this.b, this.versionParams, this);
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerDownloadSuccess(File file) {
        f();
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerDownloadFail() {
        stopSelf();
    }

    private void f() {
        Intent intent = new Intent(getApplicationContext(), this.versionParams.getCustomDownloadActivityClass());
        String str = this.d;
        if (str != null) {
            intent.putExtra("text", str);
        }
        String str2 = this.b;
        if (str2 != null) {
            intent.putExtra("downloadUrl", str2);
        }
        String str3 = this.c;
        if (str3 != null) {
            intent.putExtra("title", str3);
        }
        Bundle bundle = this.e;
        if (bundle != null) {
            this.versionParams.setParamBundle(bundle);
        }
        intent.putExtra(VERSION_PARAMS_KEY, this.versionParams);
        intent.addFlags(268435456);
        startActivity(intent);
        stopSelf();
    }

    public void setVersionParams(VersionParams versionParams) {
        this.versionParams = versionParams;
    }

    /* loaded from: classes.dex */
    public class VersionBroadCastReceiver extends BroadcastReceiver {
        public VersionBroadCastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AVersionService.PERMISSION_ACTION)) {
                if (intent.getBooleanExtra("result", false)) {
                    AVersionService.this.e();
                }
                AVersionService.this.unregisterReceiver(this);
            }
        }
    }
}
