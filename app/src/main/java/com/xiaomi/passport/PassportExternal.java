package com.xiaomi.passport;

import android.content.Context;
import com.xiaomi.accountsdk.request.IPStrategy;
import com.xiaomi.accountsdk.request.IPStrategyConfig;
import com.xiaomi.passport.PassportUserEnvironment;
import com.xiaomi.passport.interfaces.AuthenticatorComponentNameInterface;

/* loaded from: classes4.dex */
public class PassportExternal {
    private static AuthenticatorComponentNameInterface sAuthenticatorComponentNameInterface;

    public static void initEnvironment(PassportUserEnvironment passportUserEnvironment) {
        PassportUserEnvironment.Holder.setInstance(passportUserEnvironment);
    }

    public static PassportUserEnvironment getEnvironment() {
        return PassportUserEnvironment.Holder.getInstance();
    }

    public static void initIPUtilExternal(Context context) {
        IPUtilExternalImpl iPUtilExternalImpl = new IPUtilExternalImpl(context);
        IPStrategy.setIpUtilExternal(iPUtilExternalImpl);
        IPStrategyConfig.init(iPUtilExternalImpl);
    }

    public static void setAuthenticatorComponentNameInterface(AuthenticatorComponentNameInterface authenticatorComponentNameInterface) {
        sAuthenticatorComponentNameInterface = authenticatorComponentNameInterface;
    }

    public static AuthenticatorComponentNameInterface getAuthenticatorComponentNameInterface(Context context) {
        return sAuthenticatorComponentNameInterface;
    }
}
