package com.xiaomi.micolauncher.common.speech.utils;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.MultiModal;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.Observable;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public abstract class BaseSpeechEngine {
    private static final long a = TimeUnit.MINUTES.toMillis(30);
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    private static final long c = TimeUnit.HOURS.toMillis(1);
    private static final long d = TimeUnit.HOURS.toMillis(24);
    private static final long e = TimeUnit.DAYS.toMillis(30);
    private TokenChangedListener f;
    private final Object g = new Object();
    private final Handler i = new Handler(ThreadUtil.getWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                L.speech.d("%s REFRESH_SERVICE_TOKEN", "[BaseSpeechEngine]: ");
                BaseSpeechEngine.this.i.removeMessages(1);
                BaseSpeechEngine.this.a(message.arg1, message.arg2);
            }
            return false;
        }
    });
    @GuardedBy("refreshTokenLock")
    private volatile long h = b;

    /* loaded from: classes3.dex */
    public enum Config {
        ALG_VENDOR
    }

    /* loaded from: classes3.dex */
    public interface TokenChangedListener {
        void onTokenChanged(boolean z);
    }

    public abstract int addAsrAudioData(@NonNull byte[] bArr, int i, boolean z);

    public abstract boolean asrRequest(byte[] bArr, String str);

    public abstract boolean bundleRequest(boolean z, byte[] bArr, String str, boolean z2);

    public abstract void cleanAllUserLoginData();

    public boolean eventRequest(@NonNull String str, @NonNull String str2, String str3, boolean z) {
        return true;
    }

    public abstract void forceStop();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract String getAuthorizationFromEngine();

    public abstract String getAuthorizationValue();

    public boolean getOAuthStatus() {
        return true;
    }

    protected abstract long getTokenExpiredAt();

    public abstract boolean nlpRequest(String str, @NonNull String str2, String str3, boolean z);

    public abstract boolean nlpRequest(String str, @NonNull String str2, boolean z);

    public abstract boolean nlpTtsRequest(@NonNull String str, String str2, boolean z);

    public abstract boolean nlpTtsRequest(@NonNull String str, boolean z);

    public boolean postEvent(@NonNull Event event) {
        return false;
    }

    public boolean postEventWithContext(@NonNull Event event) {
        return false;
    }

    public void postVisionRecognizeResultEvent(MultiModal.VisionRecognizeResult visionRecognizeResult) {
    }

    public abstract void release();

    public abstract void setConfig(@NonNull Config config, @NonNull String str);

    public abstract boolean start();

    public abstract void stop();

    public boolean streamTts() {
        return true;
    }

    public abstract boolean ttsRequest(@NonNull String str);

    public abstract void uploadMultiChannelAsr(byte[] bArr, int i, String str, Object obj);

    public abstract void uploadMultiChannelWuw(byte[] bArr, int i, String str, boolean z, Object obj);

    public abstract void uploadSingleChannelWuw(byte[] bArr, int i, String str, boolean z, Object obj);

    public void wrapTravelUpload(ObjectNode objectNode) {
    }

    public void wrapTravelUploadMap(MapNode mapNode) {
    }

    public long a() {
        long currentTimeMillis = System.currentTimeMillis();
        long tokenExpiredAt = getTokenExpiredAt() - currentTimeMillis;
        if (tokenExpiredAt < 0) {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(currentTimeMillis);
            L.speech.w("token expires time should not be less than current time %s", instance);
            tokenExpiredAt = c;
        }
        if (tokenExpiredAt > e) {
            Calendar instance2 = Calendar.getInstance();
            instance2.setTimeInMillis(currentTimeMillis);
            L.speech.w("token expires time should not be more than current time %s", instance2);
            tokenExpiredAt = d;
        }
        long nextInt = tokenExpiredAt - new Random(SystemClock.currentThreadTimeMillis()).nextInt((int) (tokenExpiredAt / 5));
        L.speech.d("%s getRefreshTokenInterval=%s, base=%s", "[BaseSpeechEngine]: ", Long.valueOf(nextInt), Long.valueOf(tokenExpiredAt));
        return nextInt;
    }

    public long b() {
        synchronized (this.g) {
            this.h = Math.min(a, this.h * 2);
            L.speech.d("%s updateRetryRefreshTokenInterval=%s", "[BaseSpeechEngine]: ", Long.valueOf(this.h));
        }
        return this.h;
    }

    public void a(int i, long j) {
        a(i, 0, 0, j);
    }

    private void a(int i, int i2, int i3, long j) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        this.i.sendMessageDelayed(obtain, j);
    }

    public void refreshToken(boolean z, final boolean z2) {
        L.speech.i("%s refreshToken enter", "[BaseSpeechEngine]: ");
        Observable.fromCallable(new Callable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$H-qutaWXzwm-b3OG9uNK6B0FRt0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BaseSpeechEngine.this.getAuthorizationFromEngine();
            }
        }).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine.2
            /* renamed from: a */
            public void onSuccess(String str) {
                L.speech.d("%s refreshToken.onSuccess=%s", "[BaseSpeechEngine]: ", str);
                if (!TextUtils.isEmpty(str)) {
                    if (BaseSpeechEngine.this.f != null) {
                        BaseSpeechEngine.this.f.onTokenChanged(z2);
                    }
                    BaseSpeechEngine.this.resetRefreshTokenRetryInterval(false);
                    BaseSpeechEngine baseSpeechEngine = BaseSpeechEngine.this;
                    baseSpeechEngine.a(1, baseSpeechEngine.a());
                    return;
                }
                L.speech.e("%s refreshToken.response is null, try", "[BaseSpeechEngine]: ");
                BaseSpeechEngine baseSpeechEngine2 = BaseSpeechEngine.this;
                baseSpeechEngine2.a(1, baseSpeechEngine2.b());
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                L.speech.e("%s refreshToken.onFail, msg=%s", "[BaseSpeechEngine]: ", apiError.toString());
                BaseSpeechEngine baseSpeechEngine = BaseSpeechEngine.this;
                baseSpeechEngine.a(1, baseSpeechEngine.b());
            }
        });
    }

    void a(int i, int i2) {
        boolean z = true;
        boolean z2 = i > 0;
        if (i2 <= 0) {
            z = false;
        }
        refreshToken(z2, z);
    }

    public void setTokenChangedListener(TokenChangedListener tokenChangedListener) {
        this.f = tokenChangedListener;
    }

    public void resetRefreshTokenRetryInterval(boolean z) {
        L.speech.d("%s resetRefreshTokenRetryInterval.refreshToken=%b", "[BaseSpeechEngine]: ", Boolean.valueOf(z));
        synchronized (this.g) {
            if (this.h > b) {
                this.h = b;
                if (z) {
                    a(1, this.h);
                }
            }
        }
    }

    public void asyncRefreshToken() {
        synchronized (this.g) {
            this.h = b;
        }
        a(1, b);
        L.speech.d("asyncRefreshToken, refreshTokenRetryInterval=%s", Long.valueOf(this.h));
    }

    public void asyncRefreshTokenSilence() {
        synchronized (this.g) {
            this.h = b;
        }
        a(1, 0, 1, b);
        L.speech.d("asyncRefreshTokenSilence, refreshTokenRetryInterval=%s", Long.valueOf(this.h));
    }

    public String getDeviceId(Context context) {
        return SpeechEngineHelper.getDeviceId(context);
    }
}
