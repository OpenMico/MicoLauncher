package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class TestAppCommandProcessor extends SdkBasedAppCommandProcessor {
    public static final String PACKAGE_NAME_TEST = "com.xiaomi.mico.commandsdkclient";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return PACKAGE_NAME_TEST;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.SdkBasedAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("test://actor_detail/?id=204631&isBackYingHome=false&isfull=true")));
    }
}
