package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import com.xiaomi.micolauncher.module.commandsdk.MiSoundBoxCommandSdkService;

/* loaded from: classes3.dex */
public class FullFeaturedSdkBasedAppCommandProcessor extends SdkBasedAppCommandProcessor {
    private final String a;
    private final boolean b;

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportLike() {
        return true;
    }

    public FullFeaturedSdkBasedAppCommandProcessor(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void like(Context context) {
        MiSoundBoxCommandSdkService.getInstance().like(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.SdkBasedAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return this.b;
    }
}
