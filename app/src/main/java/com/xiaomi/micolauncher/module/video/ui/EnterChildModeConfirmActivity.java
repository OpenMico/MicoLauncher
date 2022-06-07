package com.xiaomi.micolauncher.module.video.ui;

import android.os.Bundle;
import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.ChildModeConfigChangedEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class EnterChildModeConfirmActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_enter_child_mode_confirm);
        setDefaultScheduleDuration();
        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$EnterChildModeConfirmActivity$D79S3BCoBZ2K2aUSclKJyarZDMQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EnterChildModeConfirmActivity.this.b(view);
            }
        });
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$EnterChildModeConfirmActivity$YI0i3cS-wh9ZLqe8q6Eiy__NTV0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EnterChildModeConfirmActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        ChildModeStorage.getInstance().enterChildMode(this);
        VideoDataManager.getManager().refreshChildModeCache();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        ChildModeManager.sendCurrentChildModeStatusBroadcast(this);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ChildModeManager.getManager().childModeDialogShowing = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecommendMusicDataChanged(ChildModeConfigChangedEvent childModeConfigChangedEvent) {
        ChildModeManager.playChildModeChangedTts(getApplicationContext());
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NotNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }
}
