package com.xiaomi.micolauncher.module.miot;

import android.os.Bundle;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MiotMainActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Hardware.isBigScreen()) {
            setContentView(R.layout.activity_miot_08a_alpha);
        } else {
            setContentView(R.layout.activity_miot);
        }
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }
}
