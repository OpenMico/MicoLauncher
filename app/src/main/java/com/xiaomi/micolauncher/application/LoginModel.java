package com.xiaomi.micolauncher.application;

import android.content.Context;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.LoginResultEvent;
import com.xiaomi.micolauncher.instruciton.impl.AlertsOperation;

/* loaded from: classes3.dex */
public class LoginModel {

    /* loaded from: classes3.dex */
    public static class a {
        private static final LoginModel a = new LoginModel();
    }

    private LoginModel() {
    }

    public static LoginModel getInstance() {
        return a.a;
    }

    public void onLoginSuccess(Context context) {
        AlertsOperation.storeAlerts();
        EventBusRegistry.getEventBus().post(new LoginResultEvent(true));
    }

    public void onLoginFailed() {
        EventBusRegistry.getEventBus().post(new LoginResultEvent(false));
    }

    public boolean hasValidAccount() {
        return TokenManager.isInited() && TokenManager.getInstance().hasValidAccount();
    }

    public boolean hasUid() {
        return ContainerUtil.hasData(TokenManager.getInstance().getUserId());
    }

    public static boolean shouldMiHomeRedirectLongPollUrl() {
        return Hardware.isBigScreen();
    }
}
