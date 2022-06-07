package com.xiaomi.micolauncher.api.service;

import android.content.Context;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.xiaomi.mico.base.utils.XMStringUtils;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PassportScope;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.utils.OauthUtil;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEngineHelper;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ROPCGSServiceHelper {
    public static PassportScope.IssuedTokenResult getTokenResult(@NonNull Context context) {
        PassportScope.ScopeResult scopeResult = getScopeResult(context);
        if (scopeResult == null) {
            return null;
        }
        a aVar = new a(context);
        try {
            return ApiManager.passwordCredentialsService.issuedToken("password", MibrainConfig.getMibrainConfig(context).clientId, MibrainConfig.getMibrainConfig(context).clientKey, ApiConstants.OAUTH_SID, scopeResult.code, a(), 1, aVar.b()).blockingSingle();
        } catch (RuntimeException e) {
            L.login.e("ROPCGSServiceHelper : getTokenResult", e);
            return null;
        }
    }

    public static PassportScope.ScopeResult getScopeResult(@NonNull Context context) {
        try {
            return ApiManager.passwordCredentialsService.scope(Long.parseLong(MibrainConfig.getMibrainConfig(context).clientId), ApiConstants.OAUTH_SID, a(OauthUtil.getScope()), 1, new a(context).a()).blockingSingle();
        } catch (RuntimeException e) {
            L.login.e("ROPCGSServiceHelper : getScopeResult", e);
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static class a {
        private final String a;
        private final String b;

        private a(Context context) {
            this.a = SpeechEngineHelper.getDeviceId(context);
            this.b = SystemSetting.getMiotDeviceId();
        }

        public String a() {
            return XMStringUtils.getSha256("d=" + this.a + MusicGroupListActivity.SPECIAL_SYMBOL + "md=" + this.b);
        }

        String b() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("d", this.a);
                jSONObject.put("md", this.b);
                L.speech.i("ROPCGSServiceHelper getScopeData device id %s, miot did %s", this.a, this.b);
                return Base64.encodeToString(jSONObject.toString().getBytes(), 11);
            } catch (JSONException unused) {
                L.login.e("ROPCGSServiceHelper : failed to construct json");
                return null;
            }
        }
    }

    private static long a() {
        return Long.parseLong(TokenManager.getInstance().getUserId());
    }

    private static String a(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            sb.append(iArr[i]);
            if (i != iArr.length - 1) {
                sb.append(StringUtils.SPACE);
            }
        }
        return sb.toString();
    }
}
