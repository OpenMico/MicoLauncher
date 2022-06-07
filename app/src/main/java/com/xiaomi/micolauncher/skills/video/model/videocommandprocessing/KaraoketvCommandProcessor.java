package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class KaraoketvCommandProcessor extends BaseAppCommandProcessor {
    public static final String LOG_OUT = "KaraoketvCommandProcessor";
    public static final String PACKAGE_NAME_KARAOKETV = "com.tencent.karaoketv";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return PACKAGE_NAME_KARAOKETV;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportNext() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportPrevious() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportRepeat() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras) {
        super.startApp(context, onFillIntentExtras);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void play(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("karaoketv://?action=17&pull_from=12121")));
        VideoMediaSession.getInstance().setPlaying();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void pause(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("karaoketv://?action=18&pull_from=12121")));
        VideoMediaSession.getInstance().setStopped();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stop(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("karaoketv://?action=19&pull_from=12121")));
        VideoMediaSession.getInstance().setStopped();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void next(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("karaoketv://?action=21&pull_from=12121")));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void previous(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("karaoketv://?action=20&pull_from=12121")));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void repeat(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("karaoketv://?action=25&pull_from=12121")));
    }
}
