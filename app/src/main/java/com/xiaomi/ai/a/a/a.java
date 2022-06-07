package com.xiaomi.ai.a.a;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.c;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public class a extends com.xiaomi.ai.a.a {
    private String d;
    private String e;
    private String f;
    private OkHttpClient g = new OkHttpClient();

    public a(com.xiaomi.ai.core.a aVar) {
        super(3, aVar);
        c();
    }

    private void c() {
        if (this.b.getAivsConfig().getInt(AivsConfig.ENV, -1) != -1) {
            this.d = this.b.getAivsConfig().getString(AivsConfig.Auth.CLIENT_ID);
            if (f.a(this.d)) {
                Logger.d("DeviceTokenProvider", "initProvider: CLIENT_ID is not set");
                throw new IllegalArgumentException("CLIENT_ID is not set");
            } else if (this.b.getClientInfo().getDeviceId().isPresent()) {
                this.e = this.b.getClientInfo().getDeviceId().get();
                if (this.b.getAivsConfig().getInt(AivsConfig.Auth.REQ_TOKEN_MODE) != 1) {
                    this.f = this.b.getAivsConfig().getString(AivsConfig.Auth.DeviceToken.SIGN);
                    if (f.a(this.f)) {
                        Logger.d("DeviceTokenProvider", "initProvider: SIGN is not set");
                        throw new IllegalArgumentException("AivsConfig.Auth.DeviceToken.SIGN is not set");
                    }
                }
            } else {
                Logger.d("DeviceTokenProvider", "initProvider: device id is not set");
                throw new IllegalArgumentException("device id is not set");
            }
        } else {
            Logger.d("DeviceTokenProvider", "initProvider: failed, ENV is not set");
            throw new IllegalArgumentException("ENV is not set");
        }
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2) {
        String str;
        AivsError aivsError;
        Response execute;
        String str2;
        int i = this.b.getAivsConfig().getInt(AivsConfig.Auth.REQ_TOKEN_MODE);
        if (!z || i != 2) {
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("client_id", this.d);
            builder.add("device", com.xiaomi.ai.b.a.b(this.e.getBytes(), 11));
            if (!z) {
                builder.add("refresh_token", this.b.getListener().a(this.b, "refresh_token"));
            }
            builder.add("sign", this.f);
            try {
                execute = this.g.newCall(new Request.Builder().url(new c(this.b.getAivsConfig()).f().concat(z ? "/thirdparty/auth/token" : "/thirdparty/refresh/token")).post(builder.build()).build()).execute();
            } catch (IOException e) {
                Logger.d("DeviceTokenProvider", Logger.throwableToString(e));
                str = "network connect failed, " + e.getMessage();
                aivsError = new AivsError(StdStatuses.CONNECT_FAILED, str);
                this.c = aivsError;
                a("msg", str, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str, z2);
                return null;
            } catch (Exception e2) {
                Logger.d("DeviceTokenProvider", Logger.throwableToString(e2));
                str = "device token auth exception " + e2.getMessage();
                aivsError = new AivsError(401, str);
                this.c = aivsError;
                a("msg", str, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str, z2);
                return null;
            }
            if (execute == null || !execute.isSuccessful()) {
                if (execute != null) {
                    if (execute.code() == 401 || execute.code() == 400) {
                        this.b.clearAuthToken();
                    }
                    str2 = execute.toString();
                    if (execute.headers() != null) {
                        str2 = str2 + "headers=" + execute.headers().toString();
                    }
                    if (execute.body() != null) {
                        str2 = str2 + ", body=" + execute.body().string();
                    }
                    a("sdk.connect.error.code", execute.code(), z2);
                } else {
                    str2 = "response is null";
                }
                Logger.d("DeviceTokenProvider", "requestToken: " + str2);
                a("msg", str2, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str2, z2);
                return null;
            }
            String string = execute.body().string();
            ObjectNode objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(string);
            if (objectNode == null) {
                String str3 = "invalid device token body " + string;
                Logger.d("DeviceTokenProvider", "requestToken" + str3);
                this.c = new AivsError(401, str3);
                a("msg", str3, false, z2);
                a("result", -1, true, z2);
                a("sdk.connect.error.msg", str3, z2);
                return null;
            }
            JsonNode path = objectNode.path("code");
            if (path.isNumber() && path.asInt() == 0) {
                if (!objectNode.path("result").isObject()) {
                    String str4 = "no result object in device token body " + string;
                    Logger.d("DeviceTokenProvider", "requestToken: " + str4);
                    this.c = new AivsError(401, str4);
                    a("msg", str4, false, z2);
                    a("result", -1, true, z2);
                    a("sdk.connect.error.msg", str4, z2);
                    return null;
                }
                ObjectNode objectNode2 = (ObjectNode) objectNode.path("result");
                if (objectNode2 == null || !objectNode2.path(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2).isTextual() || !objectNode2.path("refresh_token").isTextual() || !objectNode2.path(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2).isNumber()) {
                    String str5 = "invalid tokens in device token body " + string;
                    Logger.d("DeviceTokenProvider", "requestToken:" + str5);
                    this.c = new AivsError(401, str5);
                    a("msg", str5, false, z2);
                    a("result", -1, true, z2);
                    a("sdk.connect.error.msg", str5, z2);
                    return null;
                }
                String asText = objectNode2.path(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2).asText();
                String asText2 = objectNode2.path("refresh_token").asText();
                long asLong = objectNode2.path(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2).asLong();
                this.b.getListener().a(this.b, XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2, asText);
                this.b.getListener().a(this.b, "refresh_token", asText2);
                this.b.getListener().a(this.b, "expire_at", String.format("%d", Long.valueOf((System.currentTimeMillis() / 1000) + asLong)));
                if (f.a(asText)) {
                    a("result", -1, false, z2);
                    a("msg", "access token is null or empty", true, z2);
                } else {
                    a("result", 0, true, z2);
                }
                return asText;
            }
            String str6 = "invalid code in device token body " + string;
            Logger.d("DeviceTokenProvider", "requestToken" + str6);
            this.c = new AivsError(401, str6);
            a("msg", str6, false, z2);
            a("result", -1, true, z2);
            a("sdk.connect.error.msg", str6, z2);
            return null;
        }
        String d = this.b.getListener().d(this.b);
        if (f.a(d)) {
            this.c = new AivsError(StdStatuses.MISSING_TOKEN, "token is null");
        }
        return d;
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2, Map<String, String> map) {
        Logger.b("DeviceTokenProvider", "getAuthHeader: forceRefresh : " + z + " isTrack : " + z2);
        String b = b(z, z2);
        if (!f.a(b)) {
            return String.format("%s app_id:%s,access_token:%s", "TP-TOKEN-V1", this.d, b);
        }
        Logger.d("DeviceTokenProvider", "getAuthHeader: get access token failed");
        return null;
    }
}
