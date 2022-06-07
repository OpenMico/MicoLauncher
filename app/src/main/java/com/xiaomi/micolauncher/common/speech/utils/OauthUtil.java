package com.xiaomi.micolauncher.common.speech.utils;

import android.content.Context;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.model.PassportScope;
import com.xiaomi.micolauncher.api.service.ROPCGSServiceHelper;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;

/* loaded from: classes3.dex */
public final class OauthUtil {
    private static final OauthUtil a = new OauthUtil();
    private static final int[] b = {20000, 6000};

    public static OauthUtil getInstance() {
        return a;
    }

    public String getOauthToken() {
        String a2 = a(false);
        L.speech.i("getOauthToken.token=%s", a2);
        return a2;
    }

    public String getOauthCode() {
        String a2 = a(true);
        L.speech.i("getOauthCode.code=%s", a2);
        return a2;
    }

    private synchronized String a(boolean z) {
        L.speech.i("getCodeOrToken .code=%s", Boolean.valueOf(z));
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(ApiConstants.OAUTH_SID);
        String str = null;
        if (blockGetServiceToken != null && blockGetServiceToken.errorCode == ServiceTokenResult.ErrorCode.ERROR_NONE) {
            if (z) {
                PassportScope.ScopeResult scopeResult = ROPCGSServiceHelper.getScopeResult(a());
                if (scopeResult != null) {
                    str = scopeResult.code;
                }
            } else {
                PassportScope.IssuedTokenResult tokenResult = ROPCGSServiceHelper.getTokenResult(a());
                if (tokenResult != null) {
                    str = tokenResult.accessToken;
                }
            }
            PassportScope.IssuedTokenResult tokenResult2 = ROPCGSServiceHelper.getTokenResult(a());
            if (tokenResult2 != null) {
                str = z ? tokenResult2.refreshToken : tokenResult2.accessToken;
            }
            Logger logger = L.speech;
            Object[] objArr = new Object[2];
            objArr[0] = z ? "code" : "token";
            objArr[1] = str;
            logger.d("OauthUtil getCodeOrToken.%s=%s", objArr);
            Threads.verifyThread();
            return str;
        }
        Logger logger2 = L.speech;
        Object[] objArr2 = new Object[1];
        objArr2[0] = blockGetServiceToken == null ? null : blockGetServiceToken.errorCode;
        logger2.e("preToken get failed, error=%s", objArr2);
        return null;
    }

    private Context a() {
        return MicoApplication.getGlobalContext();
    }

    public static int[] getScope() {
        return b;
    }
}
