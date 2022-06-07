package com.xiaomi.micolauncher.skills.video.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoPlayerActivity extends BaseEventActivity {
    private static final String a = "VideoPlayerActivity";
    private boolean b;
    private ExoVideoFragment c;
    private Runnable d = new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$VideoPlayerActivity$_EWGuiTnFlTIF0ISomMSi8Nuarw
        @Override // java.lang.Runnable
        public final void run() {
            VideoPlayerActivity.this.a();
        }
    };

    public /* synthetic */ void a() {
        EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_LIST_COMPLETE));
        finish();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        getWindow().setFlags(1024, 1024);
        getWindow().setFormat(-3);
        getWindow().getDecorView().setSystemUiVisibility(4);
        setContentView(R.layout.activity_video_player);
        initVideoFragment();
        CameraDetectionController.getManager().showSupportGestureNotification(this);
        ThreadUtil.getWorkHandler().removeCallbacks(this.d);
        long cmdVideoDuration = VideoModel.getInstance().getCmdVideoDuration();
        if (cmdVideoDuration > 0) {
            ThreadUtil.getWorkHandler().postDelayed(this.d, TimeUnit.SECONDS.toMillis(cmdVideoDuration));
            VideoModel.getInstance().setCmdVideoDuration(0L);
        }
        SpeechManager.getInstance().uiShow(QueryLatency.VideoPlayer);
    }

    protected void initVideoFragment() {
        this.c = ExoVideoFragment.newInstance(getIntent());
        getSupportFragmentManager().beginTransaction().replace(R.id.video_player_container, this.c).commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    private void a(Intent intent) {
        ExoVideoFragment exoVideoFragment = this.c;
        if (exoVideoFragment != null) {
            exoVideoFragment.handleIntent(intent);
            this.c.initPlay();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent);
        SpeechManager.getInstance().uiShow(QueryLatency.VideoPlayer);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        ThirdPartyAppProxy.getInstance().setTargetPackage(getApplication(), getPackageName());
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        ThreadUtil.getWorkHandler().removeCallbacks(this.d);
    }

    public void closeActivity(boolean z) {
        this.b = z;
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (!this.finishBySameActivity) {
            VideoModel.getInstance().clear();
        }
        ThirdPartyAppProxy instance = ThirdPartyAppProxy.getInstance();
        if (!instance.isCurrentProcessorIsMicoProcessor()) {
            L.video.w("should be mico processor, but is %s", instance.getCurrentProcessor());
        } else if (!this.b) {
            Context applicationContext = getApplicationContext();
            instance.stop(applicationContext);
            instance.quit(applicationContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ExoVideoFragment exoVideoFragment = this.c;
        if (exoVideoFragment != null) {
            exoVideoFragment.onActivityResult(i, i2, intent);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.NO_AUTO_REGISTER;
    }
}
