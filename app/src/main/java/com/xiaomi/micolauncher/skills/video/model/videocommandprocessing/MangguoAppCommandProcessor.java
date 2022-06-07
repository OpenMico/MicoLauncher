package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.Locale;

/* loaded from: classes3.dex */
public class MangguoAppCommandProcessor extends SdkBasedAppCommandProcessor {
    public static final String PACKAGE_NAME_MANGGUO = "com.mgtv.tv";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return PACKAGE_NAME_MANGGUO;
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
        return String.format("mgtvapp://vod/player?clipId=%s&from=com.mxiaomi.miclauncher&actionSourceId=XADQ", str);
    }

    public static String getUrl(String str, String str2) {
        return String.format(Locale.ENGLISH, "mgtvapp://vod/player?clipId=%s&partId=%s&from=com.mxiaomi.miclauncher&actionSourceId=XADQ", str, str2);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
        if (startArgs != null) {
            ThirdPartyAppProxy.handleAppByThirdPartySchema(context, this, startArgs.getUri().getData(), false);
        }
    }
}
