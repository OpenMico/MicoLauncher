package com.xiaomi.micolauncher.skills.soundboxcontrol.view;

import android.content.Context;
import android.view.View;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.common.utils.VideoMonitorHelper;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.BackEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.OpenDistanceProtectModeEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.ShowQueryDistanceProtectEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class SoundBoxControlUiStarter {
    private Context a;

    public SoundBoxControlUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processBackEvent(BackEvent backEvent) {
        ActivityLifeCycleManager.getInstance().getTopActivity();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processShowQueryDistanceProtectEvent(ShowQueryDistanceProtectEvent showQueryDistanceProtectEvent) {
        if (ActivityLifeCycleManager.isValidActivity(ActivityLifeCycleManager.getInstance().getTopActivity())) {
            final CustomDialog build = new CustomDialog.Builder().setTitleResId(R.string.distance_protection_open).setMessageResId(R.string.distance_protection_open_desc).setPositiveResId(R.string.distance_protection_open_now).setNegativeResId(R.string.child_mode_hold_on).build();
            build.show();
            build.getPositiveButton().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.soundboxcontrol.view.-$$Lambda$SoundBoxControlUiStarter$g_gHn4zrgt7I3P2sZxaFoPoJwRw
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SoundBoxControlUiStarter.this.a(build, view);
                }
            });
            PreferenceUtils.increaseSettingInt(this.a, ChildModeStorage.KEY_DISTANCE_CHILDMODE_QUERY_COUNT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(CustomDialog customDialog, View view) {
        if (VideoMonitorHelper.getInstance().isVideoMonitorRunning()) {
            ToastUtil.showToast((int) R.string.video_monitor_running);
            return;
        }
        SystemSetting.setKeyDistanceProtectionEnable(true);
        SystemSetting.setKeyDistanceProtectionEnableChildMode(true);
        EventBusRegistry.getEventBus().post(new OpenDistanceProtectModeEvent(true));
        Camera2VisualRecognitionManager.getInstance(this.a).requestStartAction(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
        customDialog.dismiss();
    }
}
