package com.xiaomi.micolauncher;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.TextView;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;

/* loaded from: classes3.dex */
public class SplashLaunchingState implements LauncherState {
    TextView a;
    private final BaseActivity b;
    private OnSwitchToHomeStateListener c;

    @Override // com.xiaomi.micolauncher.LauncherState
    public boolean needRegisterEventBus() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onDispatchTouchEvent(MotionEvent motionEvent) {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onPause() {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onResume() {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onTrimMemory(int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SplashLaunchingState(BaseActivity baseActivity) {
        this.b = baseActivity;
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onCreate(Bundle bundle) {
        if (!SystemSetting.isInitialized(this.b)) {
            L.base.e("failed to create splash, not inited");
            this.b.finish();
            return;
        }
        this.b.setContentView(R.layout.activity_launcher);
        this.a = (TextView) this.b.findViewById(R.id.error_tips);
        Settings.System.putInt(this.b.getContentResolver(), "wifi_sleep_policy", 2);
        if (SystemSetting.getLastSyncTime() > System.currentTimeMillis()) {
            SystemClock.setCurrentTimeMillis(SystemSetting.getLastSyncTime());
        }
        if (TextUtils.isEmpty(SystemSetting.getMiotDeviceId())) {
            this.a.setText(R.string.error_did_not_found);
            return;
        }
        SystemSetting.setIsInitialized();
        SystemSetting.setIntroMoviePlayed(this.b);
        a();
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onActivityResult(int i, int i2, Intent intent) {
        a();
    }

    private void a() {
        this.c.switchToHomeState();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(OnSwitchToHomeStateListener onSwitchToHomeStateListener) {
        this.c = onSwitchToHomeStateListener;
    }
}
