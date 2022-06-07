package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.Locale;

/* loaded from: classes3.dex */
public class YoukuAppCommandProcessor extends SdkBasedAppCommandProcessor {
    public static final String PACKAGE_NAME_YOUKU = "com.youku.iot";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return "com.youku.iot";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportGesture() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.SdkBasedAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    public static String getUrl(String str) {
        return String.format("ykiot://yingshi_detail/?id=%s&isBackYingHome=true&isfull=true", str);
    }

    public static String getUrl(String str, int i) {
        return String.format(Locale.ENGLISH, "ykiot://yingshi_detail/?id=%s&isBackYingHome=true&isfull=true&&subItem=%d&from_out=mi", str, Integer.valueOf(i));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
        if (startArgs != null && !ActivityLifeCycleManager.startActivityQuietly(startArgs.getUri())) {
            ThirdPartyAppProxy.handleAppByThirdPartySchema(context, this, startArgs.getUri().getData(), false);
        }
    }
}
