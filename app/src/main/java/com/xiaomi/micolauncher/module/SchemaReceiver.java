package com.xiaomi.micolauncher.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.application.setup.ReceiversManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class SchemaReceiver extends BroadcastReceiver implements ReceiversManager.IAppBroadcastReceiverRule {
    public static final String CHILD_MODE_ACTION_RECV = "com.xiaomi.mico.action.childmode.launcher";
    public static final String CHILD_MODE_STATUS = "com.xiaomi.child.mode.state";
    public static final String KET_SYSTEM_UI_TYPE = "KEY_TYPE";
    public static final String KeySystemUiAction = "KEY_OPERATION";
    public static final String SchemaAction = "com.xiaomi.lx04.SYSTEM_UI_OPERATION";
    private final Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum a {
        setting,
        home,
        music,
        play,
        pause,
        next,
        prev,
        music_query,
        SET,
        GET
    }

    public SchemaReceiver(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ReceiversManager.IAppBroadcastReceiverRule
    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SchemaAction);
        intentFilter.addAction("com.xiaomi.mico.action.childmode.systemui");
        this.a.registerReceiver(this, intentFilter);
        EventBusRegistry.getEventBus().register(this);
    }

    @Override // com.xiaomi.micolauncher.application.setup.ReceiversManager.IAppBroadcastReceiverRule
    public void destroy() {
        EventBusRegistry.getEventBus().unregister(this);
        this.a.unregisterReceiver(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00fa  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onReceive(android.content.Context r7, android.content.Intent r8) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.SchemaReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    private void a(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("com.xiaomi.child.mode.action");
        if (stringExtra != null) {
            Screen.getInstance().systemUiDismiss(0);
            if (stringExtra.equals(a.SET.name())) {
                ChildModeManager.getManager().showOpenChildModeDialog(context, intent.getBooleanExtra(CHILD_MODE_STATUS, false));
            } else if (stringExtra.equals(a.GET.name())) {
                ChildModeManager.sendCurrentChildModeStatusBroadcast(context);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void prev(MediaControllerEvent.Prev prev) {
        PlayerApi.prev();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void next(MediaControllerEvent.Next next) {
        PlayerApi.next();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void play(MediaControllerEvent.Play play) {
        PlayerApi.playOrPlayRecommend();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pause(MediaControllerEvent.Pause pause) {
        PlayerApi.pauseByManually = true;
        PlayerApi.pause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void music(MediaControllerEvent.Jump jump) {
        Screen.getInstance().systemUiDismiss(0);
        PlayerApi.playRecommendOrShowPlayerView(MicoApplication.getGlobalContext().getApplicationContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void prevWifi(MediaControllerEvent.PrevWifi prevWifi) {
        PlayerApi.prevWiFi();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nextWifi(MediaControllerEvent.NextWifi nextWifi) {
        PlayerApi.nextWiFi();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playWifi(MediaControllerEvent.PlayWifi playWifi) {
        PlayerApi.playWiFiMusicAndPushData();
    }
}
