package com.xiaomi.ai.android.helper;

import android.text.TextUtils;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.c;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class WakeUpGuideHelper {
    private OkHttpClient a;
    private d b;

    /* loaded from: classes2.dex */
    public interface WakeUpGuideCallback {
        void onError(String str);

        void onSuccess(String str);
    }

    public WakeUpGuideHelper(Engine engine) {
        d dVar = (d) engine;
        this.b = dVar;
        long j = dVar.b().getInt(AivsConfig.Connection.CONNECT_TIMEOUT);
        this.a = new OkHttpClient.Builder().connectTimeout(j, TimeUnit.SECONDS).readTimeout(j, TimeUnit.SECONDS).writeTimeout(j, TimeUnit.SECONDS).addInterceptor(new com.xiaomi.ai.transport.d(dVar.g())).build();
    }

    public void getWakeUpGuideInfo(String str, final WakeUpGuideCallback wakeUpGuideCallback) {
        if (wakeUpGuideCallback == null) {
            Logger.d("WakeUpGuideHelper", "getWakeUpGuideInfo ,callback can not null");
            return;
        }
        Logger.a("WakeUpGuideHelper", "getWakeUpGuideInfo");
        if (TextUtils.isEmpty(str)) {
            Logger.d("WakeUpGuideHelper", "getWakeUpGuideInfo ,bodyJson can not empty");
            wakeUpGuideCallback.onError("bodyJson can not empty");
            return;
        }
        String string = this.b.b().getString(AivsConfig.Connection.USER_AGENT);
        String authorization = this.b.getAuthorization();
        if (TextUtils.isEmpty(authorization)) {
            Logger.d("WakeUpGuideHelper", " getAuthorization is null");
            wakeUpGuideCallback.onError("getAuthorization is null");
        } else if (TextUtils.isEmpty(string)) {
            Logger.d("WakeUpGuideHelper", " user_agent is null");
            wakeUpGuideCallback.onError("user_agent can not empty");
        } else {
            String l = new c(this.b.b()).l();
            this.a.newCall(new Request.Builder().url(l).addHeader("Authorization", authorization).addHeader("user_agent", string).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str)).build()).enqueue(new Callback() { // from class: com.xiaomi.ai.android.helper.WakeUpGuideHelper.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    Logger.d("WakeUpGuideHelper", "getWakeUpGuideInfo failure " + iOException.getMessage());
                    wakeUpGuideCallback.onError(iOException.toString());
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    if (response == null) {
                        wakeUpGuideCallback.onError("response null");
                    } else if (response.isSuccessful()) {
                        try {
                            Logger.b("WakeUpGuideHelper", "getWakeUpGuideInfo success");
                            wakeUpGuideCallback.onSuccess(response.body().string());
                        } catch (Exception e) {
                            Logger.d("WakeUpGuideHelper", Logger.throwableToString(e));
                            WakeUpGuideCallback wakeUpGuideCallback2 = wakeUpGuideCallback;
                            wakeUpGuideCallback2.onError("data parse error:" + e.toString());
                        }
                    } else {
                        Logger.d("WakeUpGuideHelper", "getWakeUpGuideInfo failed net work:" + response.code() + StringUtils.SPACE + response.message());
                        WakeUpGuideCallback wakeUpGuideCallback3 = wakeUpGuideCallback;
                        wakeUpGuideCallback3.onError("net work fail:" + response.code() + StringUtils.SPACE + response.message());
                    }
                }
            });
        }
    }
}
