package com.xiaomi.micolauncher.common.speech.capability;

import android.content.Context;
import android.os.Handler;
import com.xiaomi.ai.android.capability.ErrorCapability;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.WifiInfoPackage;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class ErrorCapabilityImpl extends ErrorCapability {
    private static final long d = TimeUnit.SECONDS.toMillis(30);
    private Handler a;
    private long b;
    private Context c;

    public ErrorCapabilityImpl(Handler handler, Context context) {
        this.a = handler;
        this.c = context;
    }

    @Override // com.xiaomi.ai.android.capability.ErrorCapability
    public void onError(final AivsError aivsError) {
        Handler handler = this.a;
        if (handler != null) {
            handler.removeMessages(3001);
            this.a.sendEmptyMessage(3001);
            this.a.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.capability.-$$Lambda$ErrorCapabilityImpl$LIQeCIH69kVUy6CmmEV_7kHzBcY
                @Override // java.lang.Runnable
                public final void run() {
                    ErrorCapabilityImpl.b(AivsError.this);
                }
            });
            if (a() && MiotProvisionManagerWrapper.getInstance().isMeshState()) {
                this.a.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.capability.-$$Lambda$ErrorCapabilityImpl$pAT7GFHuTuZyBk7oScs28LfyRpU
                    @Override // java.lang.Runnable
                    public final void run() {
                        ErrorCapabilityImpl.this.a(aivsError);
                    }
                });
            }
        }
        L.monitor.e("ErrorCapabilityImpl.code=%s.message=%s.dialog_id=%s", Integer.valueOf(aivsError.getErrorCode()), aivsError.getErrorMessage(), aivsError.getEventId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(AivsError aivsError) {
        SpeechManager.getInstance().WrapTravelFailure(aivsError.getErrorCode(), aivsError.getErrorMessage(), aivsError.getEventId());
    }

    private boolean a() {
        return this.b > 0 && System.currentTimeMillis() - this.b < d;
    }

    public void setWakeTime() {
        this.b = System.currentTimeMillis();
    }

    public void wifiInfoStatPoint() {
        StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.aivs_asr_timeout_rssi, Integer.toString(new WifiInfoPackage(this.c).rssi));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: onAivsErrorHandle */
    public void a(AivsError aivsError) {
        if (this.c != null && aivsError != null) {
            switch (aivsError.getErrorCode()) {
                case 401:
                case StdStatuses.LOGIN_REQUIRED /* 40110001 */:
                case StdStatuses.RELOGIN_REQUIRED /* 40110002 */:
                case StdStatuses.REFRESH_TOKEN_EXPIRED /* 40110017 */:
                case StdStatuses.TOKEN_EXPIRED /* 40110018 */:
                case StdStatuses.INVALID_TOKEN /* 40110019 */:
                case StdStatuses.MISSING_TOKEN /* 40110023 */:
                    PromptSoundPlayer.getInstance().play(this.c, R.raw.mibrain_auth_failed);
                    return;
                case StdStatuses.CONNECT_FAILED /* 40010006 */:
                case StdStatuses.CONNECTION_INTERRUPT /* 40010008 */:
                    PromptSoundPlayer.getInstance().play(this.c, R.raw.mibrain_connect_timeout);
                    return;
                case StdStatuses.NETWORK_DISABLED /* 40010007 */:
                    PromptSoundPlayer.getInstance().play(this.c, R.raw.wifi_disconnect);
                    return;
                case StdStatuses.TOO_MANY_REQUEST_BY_MINUTE /* 42910001 */:
                default:
                    return;
                case StdStatuses.ASR_TIME_OUT /* 50010004 */:
                    wifiInfoStatPoint();
                    break;
                case StdStatuses.TTS_TIME_OUT /* 50010005 */:
                case StdStatuses.NLP_TIME_OUT /* 50010007 */:
                    break;
            }
            PromptSoundPlayer.getInstance().play(this.c, R.raw.mibrain_service_timeout);
        }
    }
}
