package com.xiaomi.ai.android.a.a;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.ai.android.capability.StorageCapability;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.android.utils.SecurityUtil;
import com.xiaomi.ai.android.utils.b;
import com.xiaomi.ai.android.utils.c;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.g;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes.dex */
public class a extends com.xiaomi.ai.a.a {
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private d k;
    private String l;
    private OkHttpClient m;

    public a(d dVar, int i) {
        super(i);
        String str;
        this.k = dVar;
        if (i == 5) {
            str = "AA-TOKEN-V1";
        } else {
            str = i == 6 ? "DAA-TOKEN-V1" : str;
            this.m = new OkHttpClient();
            c();
        }
        this.l = str;
        this.m = new OkHttpClient();
        c();
    }

    private String a(String str, String str2) {
        return Base64.encodeToString(SecurityUtil.a((str + str2).getBytes()), 11).trim();
    }

    private String a(String str, String str2, String str3) {
        String replace = b.a("SHA1", (str + str2 + str3).getBytes()).replace(Constants.COLON_SEPARATOR, "");
        return (b.a(3) + str + b.a(5) + replace + b.a(2) + b.a(3)).toLowerCase();
    }

    private String a(String str, String str2, String str3, String str4, String str5) {
        String a = b.a("SHA1", (str + str2 + str3 + str4 + str5).getBytes());
        return (b.a(3) + str + b.a(5) + a + b.a(2) + String.format("%08x", Integer.valueOf(b.a(str + str4))) + b.a(3)).toLowerCase().replace(Constants.COLON_SEPARATOR, "");
    }

    private FormBody a(boolean z) {
        String a = b.a(8);
        String a2 = a(a, this.h);
        String a3 = a(a, this.g, this.h, this.f, this.i);
        FormBody.Builder builder = new FormBody.Builder();
        if (!z) {
            String readKeyValue = ((StorageCapability) this.k.a(StorageCapability.class)).readKeyValue("refresh_token");
            builder.add("grant_type", "refresh_token");
            builder.add("refresh_token", readKeyValue);
        } else {
            builder.add("grant_type", "app_token");
        }
        builder.add("client_id", this.g);
        builder.add("api_key", a2);
        builder.add("device_id", this.f);
        builder.add("package_name", this.j);
        builder.add("md5_sign", this.d.toLowerCase());
        builder.add("sha256_sign", this.e.toLowerCase());
        builder.add(IChannel.EXTRA_OPEN_SIGNATURE, a3);
        Logger.a("AnonymousProvider", "md5_sign:" + this.d.toLowerCase());
        Logger.a("AnonymousProvider", "sha256_sign:" + this.e.toLowerCase());
        return builder.build();
    }

    private void c() {
        if (this.k.a() != null) {
            byte[] a = c.a(this.k.a(), this.k.a().getPackageName());
            this.d = b.a("MD5", a);
            this.e = b.a("SHA256", a);
            this.f = this.k.n().getDeviceId().get();
            if (this.k.b().getInt(AivsConfig.ENV, -1) != -1) {
                this.g = this.k.b().getString(AivsConfig.Auth.CLIENT_ID);
                if (!TextUtils.isEmpty(this.g)) {
                    this.h = this.k.b().getString(AivsConfig.Auth.Anonymous.API_KEY);
                    if (!TextUtils.isEmpty(this.h)) {
                        this.i = this.k.b().getString(AivsConfig.Auth.Anonymous.SIGN_SECRET);
                        if (!TextUtils.isEmpty(this.i)) {
                            if (this.a != 5) {
                                this.j = this.k.b().getString(AivsConfig.Auth.Anonymous.DEVICE_NAME);
                                if (!TextUtils.isEmpty(this.j)) {
                                    return;
                                }
                            }
                            this.j = this.k.a().getPackageName();
                            return;
                        }
                        Logger.d("AnonymousProvider", "initProvider: failed, SIGN_SECRET is not set");
                        throw new IllegalArgumentException("SIGN_SECRET is not set");
                    }
                    Logger.d("AnonymousProvider", "initProvider: failed, API_KEY is not set");
                    throw new IllegalArgumentException("API_KEY is not set");
                }
                Logger.d("AnonymousProvider", "initProvider: failed, CLIENT_ID is not set");
                throw new IllegalArgumentException("CLIENT_ID is not set");
            }
            Logger.d("AnonymousProvider", "initProvider: failed, KEY_ENV is not set");
            throw new IllegalArgumentException("KEY_ENV is not set");
        }
        Logger.d("AnonymousProvider", "initProvider: failed, context is not set");
        throw new IllegalArgumentException("context is not set");
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2) {
        String str;
        String str2;
        String str3;
        AivsError aivsError;
        Response execute;
        String str4;
        int i = this.b.getAivsConfig().getInt(AivsConfig.Auth.REQ_TOKEN_MODE);
        if (!z || i != 2) {
            FormBody a = a(z);
            if (!z) {
                str = new com.xiaomi.ai.core.c(this.k.b()).e();
                str2 = "/anonymous/app/refresh/token";
            } else {
                str = new com.xiaomi.ai.core.c(this.k.b()).e();
                str2 = "/anonymous/app/auth/token";
            }
            String concat = str.concat(str2);
            Logger.b("AnonymousProvider", "requestToken: requestUrl :" + concat);
            try {
                execute = this.m.newCall(new Request.Builder().url(concat).post(a).addHeader("Date", g.a()).addHeader("Content-type", "application/x-www-form-urlencoded").build()).execute();
            } catch (IOException e) {
                Logger.d("AnonymousProvider", Log.getStackTraceString(e));
                str3 = "network connect failed, " + e.getMessage();
                aivsError = new AivsError(StdStatuses.CONNECT_FAILED, str3);
                this.c = aivsError;
                a("msg", str3, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str3, z2);
                return null;
            } catch (Exception e2) {
                Logger.d("AnonymousProvider", Log.getStackTraceString(e2));
                str3 = "app anonymous auth exception " + e2.getMessage();
                aivsError = new AivsError(401, str3);
                this.c = aivsError;
                a("msg", str3, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str3, z2);
                return null;
            }
            if (execute == null || !execute.isSuccessful()) {
                if (execute != null) {
                    if (execute.code() == 401 || execute.code() == 400) {
                        this.b.clearAuthToken();
                    }
                    str4 = execute.toString();
                    if (execute.headers() != null) {
                        str4 = str4 + "headers=" + execute.headers().toString();
                    }
                    if (execute.body() != null) {
                        str4 = str4 + ", body=" + execute.body().string();
                    }
                    a("sdk.connect.error.code", execute.code(), z2);
                } else {
                    str4 = "response is null";
                }
                Logger.d("AnonymousProvider", "requestToken: " + str4);
                a("msg", str4, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str4, z2);
                return null;
            }
            String string = execute.body().string();
            Logger.a("AnonymousProvider", "bodyStr:" + string);
            ObjectNode objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(string);
            JsonNode path = objectNode.path("code");
            if (path.isNumber() && path.asInt() == 0) {
                if (!objectNode.path("result").isObject()) {
                    String str5 = "no result object in app anonymous body " + string;
                    Logger.d("AnonymousProvider", "requestToken: " + str5);
                    this.c = new AivsError(401, str5);
                    a("msg", str5, false, z2);
                    a("result", -1, true, z2);
                    a("sdk.connect.error.msg", str5, z2);
                    return null;
                }
                ObjectNode objectNode2 = (ObjectNode) objectNode.path("result");
                if (!objectNode2.path(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2).isTextual() || !objectNode2.path("refresh_token").isTextual() || !objectNode2.path(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2).isNumber()) {
                    String str6 = "invalid tokens in app anonymous body " + string;
                    Logger.d("AnonymousProvider", "requestToken:" + str6);
                    this.c = new AivsError(401, str6);
                    a("msg", str6, false, z2);
                    a("result", -1, true, z2);
                    a("sdk.connect.error.msg", str6, z2);
                    return null;
                }
                String asText = objectNode2.path(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2).asText();
                String asText2 = objectNode2.path("refresh_token").asText();
                long asLong = objectNode2.path(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2).asLong();
                StorageCapability storageCapability = (StorageCapability) this.k.a(StorageCapability.class);
                storageCapability.writeKeyValue(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2, asText);
                storageCapability.writeKeyValue("refresh_token", asText2);
                storageCapability.writeKeyValue("expire_at", String.format(Locale.US, "%d", Long.valueOf((System.currentTimeMillis() / 1000) + asLong)));
                if (TextUtils.isEmpty(asText)) {
                    a("result", -1, false, z2);
                    a("msg", "access token is null or empty", true, z2);
                } else {
                    a("result", 0, true, z2);
                }
                return asText;
            }
            String str7 = "invalid code in app anonymous body " + string;
            Logger.a("AnonymousProvider", "requestToken" + str7);
            this.c = new AivsError(401, str7);
            a("msg", str7, false, z2);
            a("result", -1, true, z2);
            a("sdk.connect.error.msg", str7, z2);
            return null;
        }
        String d = this.b.getListener().d(this.b);
        if (TextUtils.isEmpty(d)) {
            this.c = new AivsError(StdStatuses.MISSING_TOKEN, "token is null");
        }
        return d;
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2, Map<String, String> map) {
        Logger.b("AnonymousProvider", "getAuthHeader: forceRefresh : " + z + " isTrack : " + z2);
        String b = b(z, z2);
        if (TextUtils.isEmpty(b)) {
            Logger.d("AnonymousProvider", "getAuthHeader: get access token failed");
            return null;
        }
        String a = b.a(8);
        return String.format("%s client_id:%s,api_key:%s,access_token:%s,signature:%s", this.l, this.g, a(a, this.h), b, a(a, this.g, this.h));
    }
}
