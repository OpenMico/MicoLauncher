package com.xiaomi.ai.android.impl;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.util.Log;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthorize;
import com.xiaomi.ai.android.capability.AuthCapability;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.android.utils.DeviceUtils;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.c;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.common.Optional;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class OAuthCapabilityImpl extends AuthCapability {
    private final Activity a;
    private final String b;
    private final String c;
    private final int[] d;
    private final d e;

    public OAuthCapabilityImpl(Activity activity, Engine engine, int[] iArr) {
        this.e = (d) engine;
        AivsConfig b = this.e.b();
        this.a = activity;
        this.b = b.getString(AivsConfig.Auth.CLIENT_ID);
        this.c = b.getString(AivsConfig.Auth.OAuth.REDIRECT_URL);
        this.d = iArr;
    }

    private String a() {
        try {
            String code = new XiaomiOAuthorize().setAppId(Long.parseLong(this.b)).setPlatform(0).setSkipConfirm(true).setNoMiui(true).setRedirectUrl(this.c).setScope(this.d).startGetOAuthCode(this.a).getResult().getCode();
            Logger.a("OAuthCapabilityImpl", "getAppOAuthCode:get auth code:" + code);
            return code;
        } catch (OperationCanceledException | XMAuthericationException | IOException e) {
            Logger.d("OAuthCapabilityImpl", Log.getStackTraceString(e));
            return null;
        }
    }

    private String b() {
        String str = this.e.n().getDeviceId().get();
        boolean z = this.e.b().getBoolean(AivsConfig.Auth.OAuth.ENABLE_UPLOAD_MIOT_DID);
        Optional<String> miotDid = this.e.g().getClientInfo().getMiotDid();
        try {
            String code = new XiaomiOAuthorize().setAppId(Long.parseLong(this.b)).setPlatform(1).setSkipConfirm(true).setNoMiui(true).setRedirectUrl(this.c).setScope(this.d).setDeviceID(str).setState((!z || !miotDid.isPresent()) ? DeviceUtils.getDefaultState(str) : DeviceUtils.getDefaultState(str, miotDid.get())).startGetOAuthCode(this.a).getResult().getCode();
            Logger.a("OAuthCapabilityImpl", "getDeviceOAuthCode: get auth code:" + code);
            return code;
        } catch (OperationCanceledException | XMAuthericationException | IOException e) {
            Logger.d("OAuthCapabilityImpl", Log.getStackTraceString(e));
            return null;
        }
    }

    public String getUserProfile() {
        String g = new c(this.e.b()).g();
        String string = this.e.b().getString(AivsConfig.Auth.CLIENT_ID);
        String accessToken = this.e.getAccessToken();
        try {
            return com.xiaomi.ai.b.d.a(g + "?clientId=" + string + "&token=" + accessToken, (Map<String, String>) null);
        } catch (IOException e) {
            Logger.d("OAuthCapabilityImpl", Log.getStackTraceString(e));
            return null;
        }
    }

    @Override // com.xiaomi.ai.android.capability.AuthCapability
    public String onGetOAuthCode() {
        if (this.e.m() == 4) {
            return a();
        }
        if (this.e.m() == 1) {
            return b();
        }
        return null;
    }

    public boolean refreshToken() {
        this.e.g().getAuthProvider().a(false, false);
        return true;
    }
}
