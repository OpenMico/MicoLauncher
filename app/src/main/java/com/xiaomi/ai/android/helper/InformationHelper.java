package com.xiaomi.ai.android.helper;

import android.text.TextUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.c;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class InformationHelper {
    private OkHttpClient a;
    private d b;

    /* loaded from: classes2.dex */
    public interface InformationCallback {
        void onError(String str);

        void onSuccess(String str);
    }

    public InformationHelper(final Engine engine) {
        d dVar = (d) engine;
        this.b = dVar;
        long j = dVar.b().getInt(AivsConfig.Connection.CONNECT_TIMEOUT);
        this.a = new OkHttpClient.Builder().connectTimeout(j, TimeUnit.SECONDS).readTimeout(j, TimeUnit.SECONDS).writeTimeout(j, TimeUnit.SECONDS).addInterceptor(new Interceptor() { // from class: com.xiaomi.ai.android.helper.InformationHelper.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) {
                Request request = chain.request();
                String authorization = engine.getAuthorization();
                if (!TextUtils.isEmpty(authorization)) {
                    return chain.proceed(request.newBuilder().header("Authorization", authorization).build());
                }
                Logger.d("InformationHelper", " getAuthorization is null");
                throw new IOException("getAuthorization is null");
            }
        }).build();
    }

    public void uploadInfo(InformationInfo informationInfo, final InformationCallback informationCallback) {
        if (informationCallback == null) {
            Logger.d("InformationHelper", "uploadInfo: callback can not null");
            return;
        }
        String str = null;
        try {
            str = new ObjectMapper().writeValueAsString(informationInfo);
        } catch (JsonProcessingException e) {
            Logger.d("InformationHelper", Logger.throwableToString(e));
        }
        if (TextUtils.isEmpty(str)) {
            Logger.d("InformationHelper", "uploadInfo: InformationInfo is null");
            informationCallback.onError("InformationInfo is null");
            return;
        }
        Logger.a("InformationHelper", "uploadInfo: data = " + str);
        String n = new c(this.b.b()).n();
        Logger.b("InformationHelper", "uploadInfo: url=" + n + " ,length=" + str.getBytes().length);
        this.a.newCall(new Request.Builder().url(n).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str)).build()).enqueue(new Callback() { // from class: com.xiaomi.ai.android.helper.InformationHelper.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Logger.d("InformationHelper", Logger.throwableToString(iOException));
                InformationCallback informationCallback2 = informationCallback;
                if (informationCallback2 != null) {
                    informationCallback2.onError(iOException.getMessage());
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        Logger.b("InformationHelper", "uploadInfo success");
                        informationCallback.onSuccess(response.body().string());
                    } else {
                        String string = response.body().string();
                        Logger.d("InformationHelper", "uploadInfo failed. code=" + response.code() + " ,msg=" + string);
                        InformationCallback informationCallback2 = informationCallback;
                        informationCallback2.onError("uploadInfo fail. code=" + response.code() + " ,msg=" + string);
                    }
                } catch (Exception e2) {
                    Logger.d("InformationHelper", Logger.throwableToString(e2));
                }
            }
        });
    }
}
