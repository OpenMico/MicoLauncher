package com.xiaomi.ai.android.helper;

import android.text.TextUtils;
import android.util.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.ai.android.codec.FlacEncoder;
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
public class MultiChannelHelper {
    private OkHttpClient a;
    private String b;
    private FlacEncoder c;
    private String d;
    private String e;

    /* loaded from: classes2.dex */
    public interface MultiChannelCallback {
        void onError(String str);

        void onSuccess(String str);
    }

    public MultiChannelHelper(final Engine engine) {
        d dVar = (d) engine;
        this.b = new c(dVar.b()).m();
        this.d = dVar.b().getString(AivsConfig.Auth.CLIENT_ID);
        this.e = dVar.b().getString(AivsConfig.Connection.USER_AGENT);
        long j = dVar.b().getInt(AivsConfig.Connection.CONNECT_TIMEOUT);
        this.a = new OkHttpClient.Builder().connectTimeout(j, TimeUnit.SECONDS).readTimeout(j, TimeUnit.SECONDS).writeTimeout(j, TimeUnit.SECONDS).addInterceptor(new Interceptor() { // from class: com.xiaomi.ai.android.helper.MultiChannelHelper.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) {
                Request request = chain.request();
                String authorization = engine.getAuthorization();
                if (!TextUtils.isEmpty(authorization)) {
                    return chain.proceed(request.newBuilder().header("Authorization", authorization).build());
                }
                Logger.d("MultiChannelHelper", " getAuthorization is null");
                throw new IOException("getAuthorization is null");
            }
        }).build();
        this.c = new FlacEncoder();
        if (!this.c.a()) {
            this.c.b();
            this.c = null;
        }
    }

    private <T> void a(String str, T t, final MultiChannelCallback multiChannelCallback) {
        String str2;
        try {
            str2 = new ObjectMapper().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            Logger.d("MultiChannelHelper", Logger.throwableToString(e));
            str2 = null;
        }
        Request.Builder post = new Request.Builder().url(str).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str2));
        if (!TextUtils.isEmpty(this.e)) {
            post.removeHeader("User-Agent").addHeader("User-Agent", this.e);
        }
        Logger.b("MultiChannelHelper", "uploadJson: url=" + str + " ,length=" + str2.getBytes().length);
        this.a.newCall(post.build()).enqueue(new Callback() { // from class: com.xiaomi.ai.android.helper.MultiChannelHelper.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Logger.d("MultiChannelHelper", Logger.throwableToString(iOException));
                MultiChannelCallback multiChannelCallback2 = multiChannelCallback;
                if (multiChannelCallback2 != null) {
                    multiChannelCallback2.onError(iOException.getMessage());
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                if (response == null) {
                    Logger.a("MultiChannelHelper", "response null");
                    MultiChannelCallback multiChannelCallback2 = multiChannelCallback;
                    if (multiChannelCallback2 != null) {
                        multiChannelCallback2.onError("response null");
                        return;
                    }
                    return;
                }
                try {
                    if (response.isSuccessful()) {
                        Logger.a("MultiChannelHelper", "uploadJson success");
                        if (multiChannelCallback != null) {
                            multiChannelCallback.onSuccess(response.body().string());
                            return;
                        }
                        return;
                    }
                    String string = response.body().string();
                    if (multiChannelCallback != null) {
                        MultiChannelCallback multiChannelCallback3 = multiChannelCallback;
                        multiChannelCallback3.onError("uploadJson fail. " + string);
                    }
                    Logger.d("MultiChannelHelper", "uploadJson failed. " + string);
                } catch (Exception e2) {
                    Logger.d("MultiChannelHelper", Logger.throwableToString(e2));
                }
            }
        });
    }

    protected void finalize() {
        super.finalize();
        FlacEncoder flacEncoder = this.c;
        if (flacEncoder != null) {
            flacEncoder.b();
            this.c = null;
        }
    }

    public void uploadData(AsrInfo asrInfo, byte[] bArr, int i, int i2, int i3, MultiChannelCallback multiChannelCallback) {
        if (bArr == null || bArr.length <= 0) {
            Logger.d("MultiChannelHelper", "uploadData asr: data is null");
            return;
        }
        FlacEncoder flacEncoder = this.c;
        if (flacEncoder == null) {
            Logger.d("MultiChannelHelper", "uploadData asr: FlacEncoder is null");
            return;
        }
        byte[] a = flacEncoder.a(bArr, i, i2, i3);
        if (a == null) {
            multiChannelCallback.onError("uploadData asr: encode fail.encode date is null");
        } else if (a.length > 2516582) {
            Logger.d("MultiChannelHelper", "uploadData asr: encode data too large. encoder size = " + a.length);
        } else {
            if (Logger.getLogLevel() == 3) {
                try {
                    Logger.a("MultiChannelHelper", "uploadData asr: data = " + new ObjectMapper().writeValueAsString(asrInfo));
                } catch (JsonProcessingException e) {
                    Logger.d("MultiChannelHelper", Logger.throwableToString(e));
                }
            }
            asrInfo.asrRecordAudio = Base64.encodeToString(a, 0);
            if (asrInfo.asrFormat != null) {
                if (i2 == 32) {
                    asrInfo.asrFormat.bits = 24;
                }
                asrInfo.asrFormat.codec = "flac";
            }
            a(this.b + "?app_id=" + this.d + "&type=ASR_RECORD", asrInfo, multiChannelCallback);
        }
    }

    public void uploadData(WakeupInfo wakeupInfo, byte[] bArr, int i, int i2, int i3, MultiChannelCallback multiChannelCallback) {
        if (bArr == null || bArr.length <= 0) {
            Logger.d("MultiChannelHelper", "uploadData wakeup: data is null");
            return;
        }
        FlacEncoder flacEncoder = this.c;
        if (flacEncoder == null) {
            Logger.d("MultiChannelHelper", "uploadData wakeup: FlacEncoder is null");
            return;
        }
        byte[] a = flacEncoder.a(bArr, i, i2, i3);
        if (a == null) {
            multiChannelCallback.onError("uploadData wakeup: encode fail.encode date is null");
        } else if (a.length > 2516582) {
            Logger.d("MultiChannelHelper", "uploadData wakeup: encode data too large. encoder size = " + a.length);
        } else {
            if (Logger.getLogLevel() == 3) {
                try {
                    Logger.a("MultiChannelHelper", "uploadData wakeup: data = " + new ObjectMapper().writeValueAsString(wakeupInfo));
                } catch (JsonProcessingException e) {
                    Logger.d("MultiChannelHelper", Logger.throwableToString(e));
                }
            }
            wakeupInfo.wakeupAudio = Base64.encodeToString(a, 0);
            if (wakeupInfo.audioMeta != null) {
                wakeupInfo.audioMeta.codec = "flac";
            }
            a(this.b + "?app_id=" + this.d + "&type=WAKEUP_AUDIO", wakeupInfo, multiChannelCallback);
        }
    }
}
