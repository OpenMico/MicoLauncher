package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public abstract class BaseAppCommandProcessor implements IAppCommandProcessor {
    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void cast(Context context, boolean z) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void communicate(Context context, String str) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public abstract String getPackage();

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void like(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean needCamera() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void next(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void pause(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void play(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean preferPreviousAndNextOnGesture() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void previous(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void repeat(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void seek(Context context, long j) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void select(Context context, int i) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void skipStart(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportBackward() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportCast() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportCommandSdk() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportCommunicate() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportForward() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportGesture() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportLike() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportNext() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportPrevious() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportRepeat() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSeek() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSelect() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSetResolution() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSkipStart() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSoundboxControlAction(PlaybackControllerInfo playbackControllerInfo) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras) {
        L.video.d("AppCommandProcessor is : %s", getClass().getName());
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(getPackage());
        if (!(onFillIntentExtras == null || launchIntentForPackage == null)) {
            launchIntentForPackage = onFillIntentExtras.fillExtra(launchIntentForPackage);
        }
        if (launchIntentForPackage != null) {
            ActivityLifeCycleManager.startActivityQuietly(launchIntentForPackage);
        }
        Screen.getInstance().closeLockScreen();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void forward(Context context, long j) {
        if (supportForward()) {
            L.video.e("forward not implemented?");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void backward(Context context, long j) {
        if (supportBackward()) {
            L.video.e("backward not implemented");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stop(Context context) {
        L.video.i("stop package %s", getPackage());
        doStop(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stopFromUI(Context context) {
        stop(context);
    }

    public void doStop(Context context) {
        ActivityUtil.forceStopPackage(context, getPackage());
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void setResolution(Context context, String str) {
        if (supportSetResolution()) {
            L.video.e("set resolution not implemented");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void quit(Context context) {
        L.video.i("quit package %s", getPackage());
        doQuit(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void quitFromUI(Context context) {
        quit(context);
    }

    public void doQuit(Context context) {
        if (Constants.MICO_VOIP_PKG.equals(getPackage())) {
            L.video.i("current package is micovoip,not force stop");
        } else {
            ActivityUtil.forceStopPackage(context, getPackage());
        }
    }
}
