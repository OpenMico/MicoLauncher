package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.umcrash.UMCrash;
import com.umeng.umcrash.UMCrashCallback;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class UMengSetup implements ISetupRule {
    public static final String APP_KEY = "60410aa06ee47d382b724802";

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        if (!DebugHelper.isDebugVersion()) {
            UMConfigure.init(context, APP_KEY, "XiaoMi", 1, null);
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
            UMConfigure.setLogEnabled(DebugHelper.isDebugVersion());
            UMCrash.registerUMCrashCallback(new UMCrashCallback() { // from class: com.xiaomi.micolauncher.application.setup.UMengSetup.1
                @Override // com.umeng.umcrash.UMCrashCallback
                public String onCallback() {
                    return LoginManager.get().getUserId() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + com.xiaomi.micolauncher.application.Constants.getSn();
                }
            });
        }
    }
}
