package com.xiaomi.smarthome.core.server.internal.account;

import android.text.TextUtils;
import com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api;
import com.xiaomi.smarthome.globalsetting.GlobalSetting;
import com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo;

/* loaded from: classes4.dex */
public class AccountManager {
    public static final String UNLOGGEDIN_USERID = "0";
    private static AccountManager a;
    private static Object b = new Object();
    private MiServiceTokenInfo c;
    private AccountCallBack d;

    public static AccountManager getInstance() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new AccountManager();
                }
            }
        }
        return a;
    }

    public static synchronized void init(NetApiConfig netApiConfig) {
        synchronized (AccountManager.class) {
            GlobalSetting.setAppContext(netApiConfig.sAppContext);
            GlobalSetting.setIsDebug(netApiConfig.IS_DEBUG_BUILD_TYPE);
            GlobalSetting.setServer(netApiConfig.mServer);
            GlobalSetting.setServerEnv(netApiConfig.mServerEnv);
            GlobalSetting.setLocale(netApiConfig.mLocale);
        }
    }

    public synchronized String getMiId() {
        if (this.c == null) {
            return "0";
        }
        if (TextUtils.isEmpty(this.c.getUserId())) {
            return "0";
        }
        return this.c.getUserId();
    }

    public synchronized void doUnAuthorized(int i) {
        this.c = null;
        if (this.d != null) {
            this.d.doUnAuthorized(i);
        }
    }

    public synchronized MiServiceTokenInfo getMiServiceToken() {
        if (this.c == null) {
            return null;
        }
        return this.c;
    }

    public synchronized void setMiServiceToken(MiServiceTokenInfo miServiceTokenInfo) {
        this.c = miServiceTokenInfo;
    }

    public void setAccountCallBack(AccountCallBack accountCallBack) {
        this.d = accountCallBack;
    }

    public synchronized boolean isMiLoggedIn() {
        return this.c != null;
    }

    public void loginout() {
        this.c = null;
        SmartHomeRc4Api.getInstance().setInitialized(false);
    }
}
