package com.xiaomi.micolauncher.module.video.childmode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.ChildModeConfig;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.event.QuitChildModeDialogEvent;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.SchemaReceiver;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenOpenManager;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.video.childmode.bean.StopAudioEvent;
import com.xiaomi.micolauncher.module.video.ui.EnterChildModeConfirmActivity;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public class ChildModeManager {
    private static final long a = TimeUnit.HOURS.toMillis(1);
    private final Handler b;
    private ChildModeNotification c;
    public boolean childModeDialogShowing;
    private ChildModeConfig d;
    private ChildModeConfig.AntiAddictionType e;
    private boolean f;

    /* loaded from: classes3.dex */
    public static class a {
        private static final ChildModeManager a = new ChildModeManager();
    }

    public static ChildModeManager getManager() {
        return a.a;
    }

    private ChildModeManager() {
        this.b = new MicoHandler(ThreadUtil.getWorkHandler().getLooper()) { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeManager.1
            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public String getLogTag() {
                return "ChildModeManager";
            }

            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public void processMessage(Message message) {
                Logger logger = L.childmode;
                logger.i("on receive message " + message.what);
                Logger logger2 = L.childmode;
                logger2.i("time remain type " + message.obj);
                ChildModeManager.this.setLock(true);
                TimeType timeTypeByMsgWhat = TimeType.getTimeTypeByMsgWhat(message.what);
                if (timeTypeByMsgWhat != null) {
                    if (((Integer) message.obj).intValue() == 0) {
                        PreferenceUtils.setSettingLong(ChildModeManager.this.e(), "KEY_LAST_EACH_ONCE_FORBID_TIME", System.currentTimeMillis());
                    }
                    ChildModeManager.this.a(timeTypeByMsgWhat);
                }
            }
        };
        this.d = ChildModeConfig.getChildModeConfig(e());
        this.e = this.d.getAntiAddiction().antiAddictionType;
    }

    public void setChildModeConfig(ChildModeConfig childModeConfig) {
        if (childModeConfig != null) {
            this.d = childModeConfig;
            a(childModeConfig.getAntiAddiction().antiAddictionType);
            updateLockState();
            if (!childModeConfig.getAntiAddiction().watchTimeRestricted) {
                getManager().setLock(false);
            } else {
                getManager().startAllTiming();
            }
        }
    }

    public boolean start(TimeType timeType) {
        if (timeType.recodeState) {
            L.childmode.i("time type  %s already timing return ", timeType);
            return true;
        } else if (hasUnLockTimeManage(e())) {
            L.childmode.i("has unlock time manage on this day");
            setLock(false);
            this.b.removeMessages(timeType.messageWhat);
            return true;
        } else if (!this.d.getAntiAddiction().watchTimeRestricted) {
            setLock(false);
            return true;
        } else if (this.d.getAntiAddiction().workMode == 0 && !this.d.isChildMode) {
            setLock(false);
            return true;
        } else if (a(e()) && a(timeType, this.e)) {
            L.childmode.i("in forbid time period");
            setLock(true);
            a(timeType);
            return false;
        } else if (isScreenLock()) {
            L.childmode.i("in screen lock, return");
            return false;
        } else {
            Pair<Integer, Integer> d = d(e());
            L.childmode.i("time manage time remain in minutes: %s  type : %s", d.second, this.e);
            if (((Integer) d.second).intValue() > 0 || !a(timeType, this.e)) {
                if (((Integer) d.second).intValue() >= 1440) {
                    L.childmode.i("time manege record end");
                    this.b.removeMessages(timeType.messageWhat);
                } else if (timeType.recodeState) {
                    L.childmode.i("time type  %s already timing return ", timeType);
                    return true;
                } else {
                    L.childmode.i("startTiming %s", timeType);
                    timeType.atomicLong.set(System.currentTimeMillis());
                    timeType.recodeState = true;
                    if (a(timeType, this.e)) {
                        L.childmode.i("startTiming sendMessageDelayed %d minutes, type is %d", d.second, d.first);
                        Message obtain = Message.obtain();
                        obtain.obj = d.first;
                        obtain.what = timeType.messageWhat;
                        this.b.sendMessageDelayed(obtain, TimeUnit.MINUTES.toMillis(((Integer) d.second).intValue()));
                    }
                }
                return true;
            }
            L.childmode.i("time manege time out");
            setLock(true);
            a(timeType);
            return false;
        }
    }

    private boolean a(TimeType timeType, ChildModeConfig.AntiAddictionType antiAddictionType) {
        String str = "";
        switch (antiAddictionType) {
            case ALL_FUNCTION:
                str = TimeType.NOT_LOCKSCREEN.key + TimeType.AUDIO_IN_LOCKSCREEN.key + TimeType.VIDEO.key + TimeType.AUDIO.key;
                break;
            case SCREEN_FUNCTION:
                str = TimeType.NOT_LOCKSCREEN.key;
                break;
            case VIDEO_AUDIO:
                str = TimeType.VIDEO.key + TimeType.AUDIO.key;
                break;
            case VIDEO:
                str = TimeType.VIDEO.key;
                break;
        }
        return str.contains(timeType.key);
    }

    public boolean canExecute(TimeType timeType) {
        if (hasUnLockTimeManage(e())) {
            return true;
        }
        if (this.d.getAntiAddiction().workMode == 0 && !this.d.isChildMode) {
            return true;
        }
        if (a(e()) && a(timeType, this.e)) {
            L.childmode.e("can not execute");
            b(e());
            return false;
        } else if (((Integer) d(e()).second).intValue() > 0 || !a(timeType, this.e)) {
            return true;
        } else {
            L.childmode.e("can not execute");
            b(e());
            return false;
        }
    }

    public void startAllTiming() {
        L.childmode.i("start all timing");
        if (d()) {
            stopTiming(TimeType.VIDEO);
            start(TimeType.VIDEO);
        }
        if (!Screen.getInstance().isLockScreenDisplaying() && Screen.getInstance().isInteractive()) {
            stopTiming(TimeType.NOT_LOCKSCREEN);
            start(TimeType.NOT_LOCKSCREEN);
        }
        if ((Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) && LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus()) {
            stopTiming(TimeType.AUDIO_IN_LOCKSCREEN);
            start(TimeType.AUDIO_IN_LOCKSCREEN);
        }
        if (LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus()) {
            stopTiming(TimeType.AUDIO);
            start(TimeType.AUDIO);
        }
    }

    public synchronized void stopTiming(TimeType timeType) {
        if (timeType.recodeState) {
            L.childmode.i("stopTiming %s", timeType);
            this.b.removeMessages(timeType.messageWhat);
            if (this.c != null) {
                this.c.cancel();
                this.c = null;
            }
            long andSet = timeType.atomicLong.getAndSet(0L);
            if (andSet > 0) {
                int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - andSet);
                L.childmode.i("child mode record end, time period is %s", Integer.valueOf(minutes));
                a(e(), timeType, minutes);
                timeType.recodeState = false;
            }
        }
    }

    public boolean allowPlayingVideoOrShowActivity(Context context) {
        return canExecute(TimeType.VIDEO);
    }

    private boolean a(Context context) {
        ChildModeConfig load = ChildModeStorage.getInstance().load(context);
        L.childmode.d("child mode config %s", load);
        if (load == null || load.getAntiAddiction() == null || !load.getAntiAddiction().watchTimeRestricted) {
            return false;
        }
        return ChildModeUtils.isInForbidTimePeriod(load.getAntiAddiction(), System.currentTimeMillis());
    }

    public void a(TimeType timeType) {
        stopTiming(timeType);
        switch (this.e) {
            case ALL_FUNCTION:
            case SCREEN_FUNCTION:
                b();
                c();
                if (Screen.getInstance().isInteractive()) {
                    LockScreenOpenManager.openLockScreen(e(), false);
                    return;
                }
                return;
            case VIDEO_AUDIO:
                c();
                a();
                b(e());
                return;
            case VIDEO:
                a();
                b(e());
                return;
            default:
                return;
        }
    }

    private void a() {
        b();
        ActivityLifeCycleManager.getInstance().gotoMainActivity(e());
    }

    private void b() {
        ThirdPartyAppProxy.getInstance().stop(e());
        ThirdPartyAppProxy.getInstance().quit(e());
    }

    private void c() {
        AudioPolicyService.getInstance().playbackController(new PlaybackControllerInfo(PlaybackControl.PAUSE));
        EventBusRegistry.getEventBus().post(new StopAudioEvent());
    }

    private boolean d() {
        return (MicoApplication.isVideoInForeground() && VideoMode.MITV_SERIAL.equals(VideoModel.getInstance().getMode())) || ThirdPartyAppProxy.getInstance().isTimedThirdPartyAppInForeground(e());
    }

    private void b(Context context) {
        if (!ActivityLifeCycleManager.getInstance().isChildModeForbidVideoInForeground()) {
            ActivityLifeCycleManager.startActivityQuietly(new Intent(context, ChildModeForbidVideoActivity.class));
        }
    }

    private void a(Context context, TimeType timeType, int i) {
        PreferenceUtils.setSettingInt(context, timeType.key, PreferenceUtils.getSettingInt(context, timeType.key, 0) + i);
        PreferenceUtils.setSettingLong(context, "KEY_CHILD_MODE_LAST_UPDATE_TIME", System.currentTimeMillis());
    }

    private int a(Context context, TimeType timeType) {
        if (!TimeUtils.checkSameDay(PreferenceUtils.getSettingLong(context, "KEY_CHILD_MODE_LAST_UPDATE_TIME", 0L), System.currentTimeMillis())) {
            PreferenceUtils.setSettingInt(context, timeType.key, 0);
        }
        return PreferenceUtils.getSettingInt(context, timeType.key, 0);
    }

    private int c(Context context) {
        switch (this.e) {
            case ALL_FUNCTION:
                return a(context, TimeType.NOT_LOCKSCREEN) + a(context, TimeType.AUDIO_IN_LOCKSCREEN);
            case SCREEN_FUNCTION:
                return a(context, TimeType.NOT_LOCKSCREEN);
            case VIDEO_AUDIO:
                return a(context, TimeType.VIDEO) + a(context, TimeType.AUDIO);
            default:
                return a(context, TimeType.VIDEO);
        }
    }

    private Pair<Integer, Integer> d(Context context) {
        return ChildModeUtils.calculateTimeRemainInMinutes(ChildModeStorage.getInstance().load(context), c(context), PreferenceUtils.getSettingLong(context, "KEY_LAST_EACH_ONCE_FORBID_TIME", 0L));
    }

    public boolean isChildMode() {
        ChildModeConfig load = ChildModeStorage.getInstance().load(e());
        return load != null && load.isChildMode;
    }

    public boolean hasUnLockTimeManage(Context context) {
        return PreferenceUtils.getSettingBoolean(context, "KEY_HAS_UNLOCK_TIME_MANAGE", false) && TimeUtils.checkSameDay(PreferenceUtils.getSettingLong(context, "KEY_LAST_UNLOCK_TIME_MANAGE_TIME", 0L), System.currentTimeMillis());
    }

    public void setUnLockTimeManage(Context context) {
        PreferenceUtils.setSettingBoolean(context, "KEY_HAS_UNLOCK_TIME_MANAGE", true);
        PreferenceUtils.setSettingLong(context, "KEY_LAST_UNLOCK_TIME_MANAGE_TIME", System.currentTimeMillis());
        setLock(false);
    }

    public Context e() {
        return MicoApplication.getGlobalContext();
    }

    public static void playChildModeChangedTts(Context context) {
        PromptSoundPlayer.getInstance().play(context, getManager().isChildMode() ? R.raw.ertongmoshihuanyinglaidao : R.raw.ertongmoshituichu);
    }

    public boolean isLock(ChildModeConfig.AntiAddictionType antiAddictionType) {
        this.e = getManager().d.getAntiAddiction().antiAddictionType;
        return this.f && this.e == antiAddictionType;
    }

    public void setLock(boolean z) {
        Logger logger = L.childmode;
        logger.i("set isLock " + z);
        boolean z2 = this.f;
        this.f = z;
        if (this.f != z2) {
            LockScreenSendBroadcast.sendUpdateLockStateEvent(e());
        }
    }

    private void a(ChildModeConfig.AntiAddictionType antiAddictionType) {
        Logger logger = L.childmode;
        logger.i("set addictionType " + antiAddictionType);
        ChildModeConfig.AntiAddictionType antiAddictionType2 = this.e;
        this.e = antiAddictionType;
        if (this.e != antiAddictionType2) {
            LockScreenSendBroadcast.sendUpdateLockStateEvent(e());
        }
    }

    public boolean isScreenLock() {
        return isLock(ChildModeConfig.AntiAddictionType.ALL_FUNCTION) || isLock(ChildModeConfig.AntiAddictionType.SCREEN_FUNCTION);
    }

    public void updateLockState() {
        if (!this.d.getAntiAddiction().watchTimeRestricted) {
            setLock(false);
        } else if (hasUnLockTimeManage(e())) {
            L.childmode.i("updateLockState: has unlock time manage on this day");
            setLock(false);
        } else if (this.d.getAntiAddiction().workMode == 0 && !this.d.isChildMode) {
            setLock(false);
        } else if (a(e())) {
            L.childmode.i("updateLockState: in forbid time period");
            setLock(true);
        } else {
            int intValue = ((Integer) d(e()).second).intValue();
            L.childmode.i("updateLockState: time manage time remain in minutes: %s  type : %s", Integer.valueOf(intValue), this.e);
            if (intValue <= 0) {
                setLock(true);
            } else {
                setLock(false);
            }
        }
    }

    public void showOpenChildModeDialog(final Context context, boolean z) {
        if (this.childModeDialogShowing) {
            L.base.d("child mode dialog is showing");
        } else if (z && context != null) {
            if (Hardware.isBigScreen()) {
                CustomDialog build = new CustomDialog.Builder().setTitleResId(R.string.child_mode_open).setMessageResId(R.string.child_mode_open_msg).setPositiveResId(R.string.child_mode_enter_now).setNegativeResId(R.string.child_mode_hold_on).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeManager$-lv_NjNTiPDYq-eHgvY9Jcr8QZk
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChildModeManager.b(context, view);
                    }
                }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeManager$tVvcCgLcLC6GtIRG1a-EbRJ2zXY
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChildModeManager.sendCurrentChildModeStatusBroadcast(context);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeManager$ehgQu7sb71QWSbId-s2xrhoKFUI
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        ChildModeManager.sendCurrentChildModeStatusBroadcast(context);
                    }
                }).setHasNegativeButton(true).build();
                build.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeManager$MBnouSZLkVuKMNl-NevfyXar1Ec
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        ChildModeManager.this.a(dialogInterface);
                    }
                });
                build.show();
            } else {
                ActivityLifeCycleManager.startActivityQuietly(new Intent(context, EnterChildModeConfirmActivity.class));
            }
            this.childModeDialogShowing = true;
        } else if (!z) {
            L.base.d("quit child mode");
            if (Hardware.hasCameraCapability(context)) {
                EventBusRegistry.getEventBus().post(new QuitChildModeDialogEvent());
            } else {
                ToastUtil.showToast((int) R.string.child_mode_exit_msg2, 1);
            }
        }
    }

    public static /* synthetic */ void b(Context context, View view) {
        ChildModeStorage.getInstance().enterChildMode(context);
    }

    public /* synthetic */ void a(DialogInterface dialogInterface) {
        this.childModeDialogShowing = false;
    }

    /* loaded from: classes3.dex */
    public enum TimeType {
        AUDIO("KEY_AUDIO_USED_TIME_IN_MINUTE", 2),
        VIDEO("KEY_CHILD_MODE_USED_TIME_IN_MINUTE", 1),
        NOT_LOCKSCREEN("KEY_NOT_IN_LOCKSCREEN_USED_TIME_IN_MINUTE", 3),
        AUDIO_IN_LOCKSCREEN("KEY_AUDIO_IN_LOCKSCREEN_USED_TIME_IN_MINUTE", 4);
        
        private final String key;
        private final int messageWhat;
        private final AtomicLong atomicLong = new AtomicLong();
        private boolean recodeState = false;

        TimeType(String str, int i) {
            this.key = str;
            this.messageWhat = i;
        }

        public static TimeType getTimeTypeByMsgWhat(int i) {
            switch (i) {
                case 1:
                    return VIDEO;
                case 2:
                    return AUDIO;
                case 3:
                    return NOT_LOCKSCREEN;
                case 4:
                    return AUDIO_IN_LOCKSCREEN;
                default:
                    return null;
            }
        }
    }

    public static void sendCurrentChildModeStatusBroadcast(Context context) {
        sendCurrentChildModeStatusBroadcast(context, getManager().isChildMode());
    }

    public static void sendCurrentChildModeStatusBroadcast(Context context, boolean z) {
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.mico.action.childmode.launcher");
        intent.putExtra(SchemaReceiver.CHILD_MODE_STATUS, z);
        context.sendBroadcast(intent);
    }
}
