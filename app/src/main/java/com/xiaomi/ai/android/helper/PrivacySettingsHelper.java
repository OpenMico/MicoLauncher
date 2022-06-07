package com.xiaomi.ai.android.helper;

import android.text.TextUtils;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.c;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PrivacySettingsHelper {
    private OkHttpClient a;
    private String b;
    private String c;

    /* loaded from: classes2.dex */
    public interface PrivacySettingsCallback {
        void onError(String str);

        void onSuccess(String str);
    }

    public PrivacySettingsHelper(final Engine engine) {
        d dVar = (d) engine;
        this.b = new c(dVar.b()).k();
        this.c = dVar.n().getDeviceId().get();
        long j = dVar.b().getInt(AivsConfig.Connection.CONNECT_TIMEOUT);
        this.a = new OkHttpClient.Builder().connectTimeout(j, TimeUnit.SECONDS).readTimeout(j, TimeUnit.SECONDS).writeTimeout(j, TimeUnit.SECONDS).addInterceptor(new Interceptor() { // from class: com.xiaomi.ai.android.helper.PrivacySettingsHelper.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) {
                Request request = chain.request();
                String authorization = engine.getAuthorization();
                if (!TextUtils.isEmpty(authorization)) {
                    return chain.proceed(request.newBuilder().header("Authorization", authorization).build());
                }
                Logger.d("PrivacySettingsHelper", " getAuthorization is null");
                throw new IOException("getAuthorization is null");
            }
        }).build();
    }

    public void getPrivacySettings(final PrivacySettingsCallback privacySettingsCallback) {
        if (privacySettingsCallback == null) {
            Logger.d("PrivacySettingsHelper", "getPrivacySettings :callback can not null");
            return;
        }
        HttpUrl.Builder newBuilder = HttpUrl.parse(this.b).newBuilder();
        newBuilder.addQueryParameter(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, this.c);
        this.a.newCall(new Request.Builder().url(newBuilder.build()).build()).enqueue(new Callback() { // from class: com.xiaomi.ai.android.helper.PrivacySettingsHelper.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Logger.d("PrivacySettingsHelper", "getPrivacySettings failure " + iOException.getMessage());
                privacySettingsCallback.onError(iOException.toString());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                if (response == null) {
                    privacySettingsCallback.onError("response null");
                } else if (response.isSuccessful()) {
                    try {
                        Logger.b("PrivacySettingsHelper", "getPrivacySettings success");
                        privacySettingsCallback.onSuccess(response.body().string());
                    } catch (Exception e) {
                        Logger.d("PrivacySettingsHelper", Logger.throwableToString(e));
                        PrivacySettingsCallback privacySettingsCallback2 = privacySettingsCallback;
                        privacySettingsCallback2.onError("data parse error:" + e.toString());
                    }
                } else {
                    Logger.d("PrivacySettingsHelper", "getPrivacySettings failed net work:" + response.code() + StringUtils.SPACE + response.message());
                    PrivacySettingsCallback privacySettingsCallback3 = privacySettingsCallback;
                    privacySettingsCallback3.onError("net work fail:" + response.code() + StringUtils.SPACE + response.message());
                }
            }
        });
    }

    public void uploadPrivacySettings(Map<String, Boolean> map, final PrivacySettingsCallback privacySettingsCallback) {
        if (map == null || map.isEmpty()) {
            Logger.d("PrivacySettingsHelper", "uploadPrivacySettings ,can't upload empty settings");
        } else if (privacySettingsCallback == null) {
            Logger.d("PrivacySettingsHelper", "uploadPrivacySettings ,callback can not null");
        } else {
            JSONObject jSONObject = new JSONObject();
            long currentTimeMillis = System.currentTimeMillis();
            try {
                jSONObject.put("device_id", this.c);
                jSONObject.put("watermark", currentTimeMillis);
                JSONObject jSONObject2 = new JSONObject();
                for (String str : map.keySet()) {
                    if (!TextUtils.isEmpty(str)) {
                        jSONObject2.put(str, map.get(str));
                    }
                }
                jSONObject.put("privacy_set", jSONObject2);
                Logger.a("PrivacySettingsHelper", jSONObject.toString());
                this.a.newCall(new Request.Builder().url(this.b).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jSONObject.toString())).build()).enqueue(new Callback() { // from class: com.xiaomi.ai.android.helper.PrivacySettingsHelper.3
                    @Override // okhttp3.Callback
                    public void onFailure(Call call, IOException iOException) {
                        Logger.d("PrivacySettingsHelper", "uploadPrivacySettings failure " + iOException.getMessage());
                        privacySettingsCallback.onError(iOException.toString());
                    }

                    @Override // okhttp3.Callback
                    public void onResponse(Call call, Response response) {
                        if (response == null) {
                            privacySettingsCallback.onError("response null");
                        } else if (response.isSuccessful()) {
                            try {
                                Logger.b("PrivacySettingsHelper", "uploadPrivacySettings success");
                                privacySettingsCallback.onSuccess(response.body().string());
                            } catch (Exception e) {
                                Logger.d("PrivacySettingsHelper", Logger.throwableToString(e));
                                PrivacySettingsCallback privacySettingsCallback2 = privacySettingsCallback;
                                privacySettingsCallback2.onError("data parse error:" + e.toString());
                            }
                        } else {
                            Logger.d("PrivacySettingsHelper", "uploadPrivacySettings failed net work:" + response.code() + StringUtils.SPACE + response.message());
                            PrivacySettingsCallback privacySettingsCallback3 = privacySettingsCallback;
                            privacySettingsCallback3.onError("net work fail:" + response.code() + StringUtils.SPACE + response.message());
                        }
                    }
                });
            } catch (JSONException e) {
                Logger.d("PrivacySettingsHelper", Logger.throwableToString(e));
                privacySettingsCallback.onError("uploadPrivacySettings fail JSONException: " + e.toString());
            }
        }
    }
}
