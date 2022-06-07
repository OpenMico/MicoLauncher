package com.xiaomi.micolauncher.application.setup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.miot.support.MicoSupConstants;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class LoginAndApiSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    @SuppressLint({"CheckResult"})
    public void setup(final Context context) {
        Log.e(MicoSupConstants.TAG_LAU, "Info: LoginAndApiSetup init!");
        TokenManager.init(context, new TokenManager.ICookieParams() { // from class: com.xiaomi.micolauncher.application.setup.LoginAndApiSetup.1
            private volatile ArrayList<String> c;

            @Override // com.xiaomi.mico.token.TokenManager.ICookieParams
            public ArrayList<String> getUrls() {
                if (this.c == null) {
                    this.c = new ArrayList<>();
                    for (ApiConstants.ServiceConfig serviceConfig : ApiConstants.ServiceConfigs) {
                        this.c.addAll(Arrays.asList(serviceConfig.getServiceUrls()));
                    }
                }
                return this.c;
            }

            @Override // com.xiaomi.mico.token.TokenManager.ICookieParams
            public String getMiBrainId() {
                return MibrainConfig.getMibrainConfig(context).clientId;
            }

            @Override // com.xiaomi.mico.token.TokenManager.ICookieParams
            public String getDeviceId() {
                return SystemSetting.getDeviceID();
            }
        });
        if (!TokenManager.getInstance().hasValidAccount()) {
            TokenManager.getInstance().migrateAccount(context);
        }
        Schedulers.io().scheduleDirect($$Lambda$LoginAndApiSetup$T4VkV8qdWWEjeADFh5ONPCn5kM0.INSTANCE);
        ThreadUtil.getIoThreadPool().submit($$Lambda$WZkte_vOtREL99qvl0CWZ7TI9EI.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a() {
        ApiManager.init(L.api);
        SystemSetting.getUserProfile().initUserCard();
        EventBusRegistry.getEventBus().postSticky(new RecommendEvent.ApiInited());
    }
}
