package com.xiaomi.micolauncher.module.video.childmode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.module.child.LockSetActivity;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import com.xiaomi.micolauncher.module.setting.widget.LockAppDialog;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildModeForbidVideoActivity extends BaseActivity {
    private MultiAudioPlayer a;
    private Context b;
    private View c;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_child_mode_forbid_video);
        scheduleToClose(TimeUnit.MINUTES.toMillis(5L));
        this.a = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_MUSIC, true);
        a();
        this.b = this;
        this.c = findViewById(R.id.main_page_mask);
        ((ImageView) findViewById(R.id.kids_unlocked)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeForbidVideoActivity$cpZmmDQFBB3SMdX-A2pc4946S6g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChildModeForbidVideoActivity.this.b(view);
            }
        });
        ((ImageView) findViewById(R.id.kids_setting)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeForbidVideoActivity$s7wmysOGnWZg7lyLxwwRReNiU0k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChildModeForbidVideoActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        if ("".equals(MicoSettings.getAppLockPass(this.b))) {
            this.c.setVisibility(0);
            ActivityLifeCycleManager.startActivity(this, new Intent(this.b, LockSetActivity.class), 291, false);
            return;
        }
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeForbidVideoActivity$j4mKl3SfTvJIQiWymfR7Fmv1D9k
            @Override // java.lang.Runnable
            public final void run() {
                ChildModeForbidVideoActivity.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        LockAppDialog lockAppDialog = new LockAppDialog(this.b, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeForbidVideoActivity$7_2t451VxxsstD0Jm8dGZEUncB4
            @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
            public final void onPassCorrect() {
                ChildModeForbidVideoActivity.this.c();
            }
        });
        lockAppDialog.setTip(getString(R.string.time_manage_invalid));
        lockAppDialog.show();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        ChildModeManager.getManager().setUnLockTimeManage(this.b);
        ChildModeManager.getManager().updateLockState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        if ("".equals(MicoSettings.getAppLockPass(this.b))) {
            ActivityLifeCycleManager.startActivity(this, new Intent(this.b, LockSetActivity.class), 291, false);
        } else {
            SettingOpenManager.openChildProtection(this);
        }
        finish();
    }

    private void a() {
        this.a.playTts(getString(R.string.local_tts_child_mode_forbid_video));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 291 && i2 == -1) {
            ChildModeManager.getManager().setUnLockTimeManage(this.b);
            ChildModeManager.getManager().updateLockState();
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.a.stop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.c.setVisibility(8);
    }
}
