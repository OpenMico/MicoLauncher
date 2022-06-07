package com.xiaomi.micolauncher.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseFragment;

/* loaded from: classes3.dex */
public class CommonSkillFragmentActivity extends BaseActivity {
    public static final String KEY_FRAGMENT_CLASS = "fragment";

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setDefaultScheduleDuration();
        a(getIntent());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent);
    }

    private void a(Intent intent) {
        Fragment instantiate = BaseFragment.instantiate(this, intent.getStringExtra("fragment"), intent.getExtras());
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(16908290, instantiate);
        beginTransaction.commitAllowingStateLoss();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            scheduleToClose(DEFAULT_CLOSE_DURATION);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
