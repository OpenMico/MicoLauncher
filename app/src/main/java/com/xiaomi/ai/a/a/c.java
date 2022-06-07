package com.xiaomi.ai.a.a;

import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.ai.a.a;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.common.Optional;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import okhttp3.OkHttpClient;

/* loaded from: classes.dex */
public class c extends a {
    private String d;
    private String e;
    private String f;
    private String g;
    private OkHttpClient h = new OkHttpClient();

    public c(int i, com.xiaomi.ai.core.a aVar) {
        super(i, aVar);
        f();
    }

    private String a(boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.a == 1) {
            sb.append("?pt=");
            sb.append(1);
            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            sb.append("scope_data=");
            sb.append(g());
        } else {
            sb.append("?pt=");
            sb.append(0);
        }
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        sb.append("client_id=");
        sb.append(this.d);
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        sb.append("client_secret=");
        sb.append(this.f);
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        sb.append("redirect_uri=");
        sb.append(this.e);
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        if (z) {
            str = this.b.getListener().c(this.b);
            if (f.a(str)) {
                Logger.d("OAuthProvider", "get authCode fail");
                return null;
            }
            Logger.b("OAuthProvider", "requestToken: get auth code:" + str);
            sb.append("grant_type=");
            sb.append("authorization_code");
            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            sb.append("code=");
        } else {
            Logger.b("OAuthProvider", "requestToken: refresh token");
            sb.append("grant_type=");
            sb.append("refresh_token");
            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            sb.append("refresh_token=");
            str = this.b.getListener().a(this.b, "refresh_token");
        }
        sb.append(str);
        return new com.xiaomi.ai.core.c(this.b.getAivsConfig()).d().concat("/oauth2/auth/token").concat(sb.toString());
    }

    private String c() {
        String a = this.b.getListener().a(this.b, XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2);
        if (f.a(a)) {
            return null;
        }
        String a2 = this.b.getListener().a(this.b, "expire_at");
        if (f.a(a2) || Long.parseLong(a2) - (System.currentTimeMillis() / 1000) <= 0) {
            return null;
        }
        Logger.a("OAuthProvider", "getToken: use cachedAccessToken:" + a);
        return a;
    }

    private void d() {
        this.b.getListener().b(this.b, "refresh_times_during_limit");
        this.b.getListener().a(this.b, "updated_at", String.valueOf(System.currentTimeMillis() / 1000));
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean e() {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.a.a.c.e():boolean");
    }

    private void f() {
        if (this.a != 4 && this.a != 1) {
            Logger.d("OAuthProvider", "initProvider: unsupported authType=" + this.a);
            throw new IllegalArgumentException("unsupported authType : " + this.a);
        } else if (this.b.getClientInfo().getDeviceId().isPresent()) {
            this.g = this.b.getClientInfo().getDeviceId().get();
            this.d = this.b.getAivsConfig().getString(AivsConfig.Auth.CLIENT_ID);
            if (f.a(this.d)) {
                Logger.d("OAuthProvider", "initProvider: CLIENT_ID is not set");
                throw new IllegalArgumentException("CLIENT_ID is not set");
            } else if (this.b.getAivsConfig().getInt(AivsConfig.Auth.REQ_TOKEN_MODE) != 1) {
                String string = this.b.getAivsConfig().getString(AivsConfig.Auth.OAuth.REDIRECT_URL);
                if (!f.a(string)) {
                    String string2 = this.b.getAivsConfig().getString(AivsConfig.Auth.OAuth.CLIENT_SECRET);
                    if (!f.a(string2)) {
                        try {
                            this.e = URLEncoder.encode(string, "UTF-8");
                            this.f = URLEncoder.encode(string2, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Logger.d("OAuthProvider", Logger.throwableToString(e));
                            throw new IllegalArgumentException("redirectUrl or clientSecret is illegal");
                        }
                    } else {
                        Logger.d("OAuthProvider", "initProvider: CLIENT_SECRET is not set");
                        throw new IllegalArgumentException("CLIENT_SECRET is not set");
                    }
                } else {
                    Logger.d("OAuthProvider", "initProvider: REDIRECT_URL is not set");
                    throw new IllegalArgumentException("REDIRECT_URL is not set");
                }
            }
        } else {
            Logger.d("OAuthProvider", "initProvider: device id is not set");
            throw new IllegalArgumentException("device id is not set");
        }
    }

    private String g() {
        Optional<String> miotDid = this.b.getClientInfo().getMiotDid();
        return com.xiaomi.ai.b.a.b(((!this.b.getAivsConfig().getBoolean(AivsConfig.Auth.OAuth.ENABLE_UPLOAD_MIOT_DID) || !miotDid.isPresent()) ? String.format("{\"d\":\"%s\"}", this.g) : String.format("{\"d\":\"%s\",\"md\":\"%s\"}", this.g, miotDid.get())).getBytes(), 11);
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x031d  */
    @Override // com.xiaomi.ai.a.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(boolean r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 806
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.a.a.c.a(boolean, boolean):java.lang.String");
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2, Map<String, String> map) {
        String str;
        String str2;
        String str3;
        Object[] objArr;
        Logger.b("OAuthProvider", "getAuthHeader: forceRefresh : " + z + " isTrack : " + z2);
        this.c = null;
        String b = b(z, z2);
        if (f.a(b)) {
            str = "OAuthProvider";
            str2 = "getAuthHeader: get access token failed";
        } else {
            String string = this.b.getAivsConfig().getString(AivsConfig.Auth.CLIENT_ID);
            if (this.a == 1) {
                str3 = "%s app_id:%s,scope_data:%s,access_token:%s";
                objArr = new Object[]{"DO-TOKEN-V1", string, g(), b};
            } else if (this.a == 4) {
                str3 = "%s dev_app_id:%s,access_token:%s";
                objArr = new Object[]{"AO-TOKEN-V1", string, b};
            } else {
                str = "OAuthProvider";
                str2 = "getAuthHeader: unsupported authType=" + this.a;
            }
            return String.format(str3, objArr);
        }
        Logger.d(str, str2);
        return null;
    }
}
