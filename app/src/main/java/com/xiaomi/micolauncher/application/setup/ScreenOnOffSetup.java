package com.xiaomi.micolauncher.application.setup;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.Launcher;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.ScreenOnOffEvent;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.SmartHomeLockControl;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerActivityV2;
import com.xiaomi.micolauncher.skills.music.vip.MusicVipActivity;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.voice.WhiteNoiseActivity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ScreenOnOffSetup extends AbsSyncSetup {
    private static final List<Class<? extends Activity>> a = new ArrayList();
    private a b;
    private Context c;
    private Handler d = new Handler(Looper.myLooper()) { // from class: com.xiaomi.micolauncher.application.setup.ScreenOnOffSetup.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                ScreenOnOffSetup.this.a((String) message.obj);
                return;
            }
            throw new IllegalStateException("Unexpected value: " + message.what);
        }
    };
    private final Runnable e = new Runnable() { // from class: com.xiaomi.micolauncher.application.setup.ScreenOnOffSetup.2
        @Override // java.lang.Runnable
        public void run() {
            AudioPolicyService.getInstance().requestForceStopAll(AudioSource.AUDIO_SOURCE_POWER_KEY);
            ThirdPartyAppProxy.getInstance().quit(ScreenOnOffSetup.this.c);
        }
    };

    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    static {
        a.add(Launcher.class);
        a.add(PlayerActivityV2.class);
        a.add(WhiteNoiseActivity.class);
        a.add(MusicVipActivity.class);
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        this.c = context;
        this.b = new a();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(this.b, intentFilter);
    }

    /* loaded from: classes3.dex */
    class a extends BroadcastReceiver {
        a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ScreenOnOffSetup.this.d.sendMessage(ScreenOnOffSetup.this.d.obtainMessage(1, intent.getAction()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        if ("android.intent.action.SCREEN_OFF".equals(str)) {
            EventBusRegistry.getEventBus().post(new ScreenOnOffEvent(false));
            ThreadUtil.postInMainThread(this.e);
            a();
            if (RomUpdateAdapter.getInstance().isUpdateOngoing()) {
                SystemSetting.setScreenOffAfterReboot(true);
            }
            ThreadUtil.postDelayedInMainThread($$Lambda$ScreenOnOffSetup$QKK_KA5JGGBDRGGQGmhag512Ws.INSTANCE, 1000L);
            SmartHomeLockControl.getIns().setNeedShowLock(true);
            return;
        }
        EventBusRegistry.getEventBus().post(new ScreenOnOffEvent(true));
        ThreadUtil.removeCallbacksInMainThread(this.e);
        if (RomUpdateAdapter.getInstance().isUpdateOngoing()) {
            SystemSetting.setScreenOffAfterReboot(false);
        }
        ChildModeManager.getManager().start(ChildModeManager.TimeType.NOT_LOCKSCREEN);
        if (LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus()) {
            ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b() {
        ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.NOT_LOCKSCREEN);
        if (LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus()) {
            ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
        }
    }

    private void a() {
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        if (a(topActivity)) {
            topActivity.finish();
        }
    }

    private static boolean a(Activity activity) {
        return ActivityLifeCycleManager.isValidActivity(activity) && !a.contains(activity.getClass());
    }

    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        a aVar;
        super.onDestroy();
        Context context = this.c;
        if (context != null && (aVar = this.b) != null) {
            context.unregisterReceiver(aVar);
        }
    }
}
