package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.webview.CommonWebActivity;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class BiliAppCommandProcessor extends BaseAppCommandProcessor {
    public static final String PACKAGE_NAME_BILI = "tv.danmaku.bili";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return "tv.danmaku.bili";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
        if (startArgs != null && !ActivityLifeCycleManager.startActivityQuietly(startArgs.getUri())) {
            ThirdPartyAppProxy.handleAppByThirdPartySchema(context, this, startArgs.getUri().getData(), false);
        }
    }

    public static Uri buildBiliAppUrl(boolean z, @NonNull String str, String str2) {
        Uri.Builder buildUpon = Uri.parse("bili://tv.danmaku.bili?").buildUpon();
        buildUpon.appendQueryParameter("type", "3");
        buildUpon.appendQueryParameter("isBangumi", z ? "0" : "1");
        if (z) {
            buildUpon.appendQueryParameter("avId", str);
            if (TextUtils.isEmpty(str2)) {
                str2 = "0";
            }
            buildUpon.appendQueryParameter("cId", str2);
        } else {
            buildUpon.appendQueryParameter("seasonId", str);
            if (!TextUtils.isEmpty(str2)) {
                buildUpon.appendQueryParameter("epId", str2);
            }
        }
        buildUpon.appendQueryParameter("progress", "0");
        buildUpon.appendQueryParameter(CommonWebActivity.COMMON_FULLSCREEN, "1");
        buildUpon.appendQueryParameter("from", "xiaomi");
        buildUpon.appendQueryParameter(Directive.DIRECTIVE_TYPE_RESOURCE, "1");
        return buildUpon.build();
    }
}
