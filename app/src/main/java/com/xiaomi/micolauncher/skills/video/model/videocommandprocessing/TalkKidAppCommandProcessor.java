package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraRelatedSwitchEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class TalkKidAppCommandProcessor extends SdkBasedAppCommandProcessor {
    public static final String PACKAGE_NAME_51TALK = "com.talk51.kidhd";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return PACKAGE_NAME_51TALK;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean needCamera() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.SdkBasedAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras) {
        EventBusRegistry.getEventBus().post(new CameraRelatedSwitchEvent(CameraRelatedSwitchEvent.event.CLOSE_WITH_RELEASE_CAMERA));
        super.startApp(context, onFillIntentExtras);
    }
}
