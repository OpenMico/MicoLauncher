package com.xiaomi.micolauncher.module.miot.mesh;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class IotDevicesBindNeedWifiActivity extends BaseActivity {
    private Context a;
    private Runnable b;
    private Handler c = new Handler(Looper.getMainLooper());
    private TextView d;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = this;
        a();
    }

    private void a() {
        setContentView(R.layout.activity_iot_devices_bind_need_wifi);
        this.d = (TextView) findViewById(R.id.bind_fail_text);
        b();
        if (MiotProvisionManagerWrapper.getGlobalError() == MiotProvisionManagerWrapper.GLOBAL_ERROR.NO_ENCRYPTED_AVAILABLE_WIFI) {
            this.d.setText(R.string.iot_devices_provision_need_encrypted_wifi);
            SpeechManager.getInstance().ttsRequest(this.a.getString(R.string.iot_devices_provision_need_encrypted_wifi));
        } else {
            this.d.setText(R.string.iot_devices_provision_need_wifi);
            SpeechManager.getInstance().ttsRequest(this.a.getString(R.string.iot_no_available_24g_wifi));
        }
        findViewById(R.id.goto_settings).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$IotDevicesBindNeedWifiActivity$BmVwGFXkpeV3wnW6xZcgAjfAOpM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IotDevicesBindNeedWifiActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        c();
        SettingOpenManager.openWifi(this.a);
        finish();
    }

    private void b() {
        L.mesh.d("setAutoExitActivity");
        this.b = new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$IotDevicesBindNeedWifiActivity$pAKE_UBrrosliPXs5WjSrWtjOSA
            @Override // java.lang.Runnable
            public final void run() {
                IotDevicesBindNeedWifiActivity.this.d();
            }
        };
        this.c.postDelayed(this.b, TimeUnit.SECONDS.toMillis(7L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        L.mesh.d("waitTimer exitActivity ");
        finish();
    }

    private void c() {
        L.mesh.d("clearAutoExitActivity");
        Runnable runnable = this.b;
        if (runnable != null) {
            this.c.removeCallbacks(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        c();
    }
}
