package com.xiaomi.micolauncher.common.speech.workmode;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.provider.FontsContractCompat;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.speech.utils.WakeupInfo;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public abstract class BaseSpeechWorkMode {
    private static final long c = TimeUnit.SECONDS.toMillis(30);
    VpmClient a;
    BaseSpeechEngine b;
    private Handler i;
    private boolean j;
    private boolean m;
    private final Object d = new Object();
    private final boolean e = SpeechConfig.isLabTest();
    @GuardedBy("wakeupInfoLock")
    private WakeupInfo h = null;
    private boolean k = true;
    private final boolean f = SpeechConfig.isLocalVad();
    private boolean g = false;
    private String l = null;

    private boolean i() {
        return false;
    }

    public String getSceneType() {
        return "";
    }

    public boolean isDialog() {
        return false;
    }

    public boolean isDialogContinuous() {
        return false;
    }

    public abstract String name();

    public void onQueryResultInvalid() {
    }

    public void onQueryResultNoTts() {
    }

    public boolean supportOpenMicWhenPlaying() {
        return false;
    }

    public BaseSpeechWorkMode(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine) {
        this.a = vpmClient;
        this.b = baseSpeechEngine;
        HandlerThread handlerThread = new HandlerThread("AudioHandleThread");
        handlerThread.start();
        this.i = new Handler(handlerThread.getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.speech.workmode.-$$Lambda$BaseSpeechWorkMode$Gp-A-KefE3to0kETTKxjA-R5gxA
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a;
                a = BaseSpeechWorkMode.this.a(message);
                return a;
            }
        });
    }

    public /* synthetic */ boolean a(Message message) {
        L.speech.d("%s Handler.msg=%d, mode=%s", "[BaseSpeechWorkMode]:", Integer.valueOf(message.what), name());
        switch (message.what) {
            case 1:
                f();
                break;
            case 2:
                this.a.enableVoiceWakeup();
                break;
        }
        return false;
    }

    public void onLocalWakeup() {
        SpeechContextHelper.cacheContext();
    }

    public boolean wakeupHandle() {
        stopPostAudio();
        return true;
    }

    public final void startPostAudio() {
        g();
        start();
    }

    public final void stopPostAudio() {
        L.speech.i("%s stopPostAudio", "[BaseSpeechWorkMode]:");
        g();
    }

    public void asrCancel() {
        this.b.forceStop();
    }

    public boolean getNewSession() {
        return this.k;
    }

    public void setNewSession() {
        this.k = true;
    }

    public void clearNewSession() {
        this.k = false;
    }

    public final void setWakeupInfo(WakeupInfo wakeupInfo) {
        synchronized (this.d) {
            this.h = wakeupInfo;
        }
    }

    private WakeupInfo c() {
        WakeupInfo wakeupInfo;
        synchronized (this.d) {
            wakeupInfo = this.h;
        }
        return wakeupInfo;
    }

    void a() {
        if (this.g) {
            this.a.disableVoiceWakeup();
        }
        this.i.removeMessages(2);
    }

    void b() {
        if (this.g) {
            this.a.enableVoiceWakeup();
            this.g = false;
        }
        this.i.removeMessages(2);
    }

    private byte[] d() {
        WakeupInfo c2 = c();
        if (c2 == null || c2.getWuwSize() <= 0) {
            return null;
        }
        int wuwSize = c2.getWuwSize();
        byte[] bArr = new byte[wuwSize];
        int vpmRead = this.a.vpmRead(bArr, wuwSize);
        if (vpmRead == wuwSize) {
            return bArr;
        }
        L.speech.e("%s ERROR(read wuw data): got_size=%d, wuw_size=%d", "[BaseSpeechWorkMode]:", Integer.valueOf(vpmRead), Integer.valueOf(wuwSize));
        return null;
    }

    private String e() {
        WakeupInfo c2 = c();
        if (c2 != null) {
            return c2.getWakeupId();
        }
        L.speech.d("%s getWakeupId.wakeupInfo is null", "[BaseSpeechWorkMode]:");
        return "";
    }

    boolean a(byte[] bArr) {
        boolean z;
        if (this.e) {
            z = this.b.asrRequest(bArr, e());
        } else {
            z = this.b.bundleRequest(this.k, bArr, e(), i());
        }
        if (!z) {
            L.speech.e("%s startRequest.error", "[BaseSpeechWorkMode]:");
        }
        clearNewSession();
        return z;
    }

    private void f() {
        byte[] bArr = new byte[16000];
        long currentTimeMillis = System.currentTimeMillis();
        byte[] d = d();
        if (d != null) {
            SpeechManager.getInstance().cacheWuw(d, d.length);
        }
        a();
        if (!a(d)) {
            b();
            return;
        }
        int vpmRead = this.a.vpmRead(bArr, 16000);
        Logger logger = L.speech;
        Object[] objArr = new Object[3];
        objArr[0] = "[BaseSpeechWorkMode]:";
        objArr[1] = Integer.valueOf(vpmRead);
        objArr[2] = Integer.valueOf(d == null ? 0 : d.length);
        logger.i("%s vpmRead.size=%d, .wuw_size=%s", objArr);
        if (vpmRead <= 0) {
            b();
            return;
        }
        SpeechManager.getInstance().cacheAsr(bArr, vpmRead, true);
        this.l = null;
        this.m = false;
        notifyAsrLength(0);
        this.j = false;
        QueryLatency.getInstance().setAsrSendStartMs();
        int i = 0;
        while (true) {
            if (vpmRead <= 0) {
                break;
            }
            if (vpmRead < 16000) {
                if (this.b.addAsrAudioData(bArr, vpmRead, false) < 0) {
                    L.speech.e("%s addAsrAudioDataError", "[BaseSpeechWorkMode]:");
                    break;
                }
            } else {
                L.speech.e("%s ERROR(read asr data): got_size=%d", "[BaseSpeechWorkMode]:", Integer.valueOf(vpmRead));
            }
            i += vpmRead;
            if (h()) {
                this.a.vpmSetUnWakeup();
                break;
            } else if (i >= 960000) {
                this.b.addAsrAudioData(new byte[0], 0, true);
                i = 0;
                break;
            } else {
                vpmRead = this.a.vpmRead(bArr, 16000);
                if (vpmRead > 0) {
                    SpeechManager.getInstance().cacheAsr(bArr, vpmRead, false);
                }
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        QueryLatency.getInstance().setAsrEndMs(0L, currentTimeMillis2, currentTimeMillis2);
        if (!h()) {
            this.m = true;
            this.b.addAsrAudioData(new byte[0], 0, true);
        }
        QueryLatency.getInstance().setAsrSendEnd(i);
        b();
        if (h()) {
            QueryLatency.getInstance().setAsrSendRealEnd();
        }
        L.speech.i("%s exit read(sys_time=%d, diff=%dms), total_size(%d)=%dms", "[BaseSpeechWorkMode]:", Long.valueOf(System.currentTimeMillis()), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Integer.valueOf(i), Integer.valueOf(i >> 5));
    }

    private void g() {
        this.i.removeCallbacksAndMessages(null);
        this.j = true;
    }

    private boolean h() {
        return this.j;
    }

    final void a(long j) {
        this.j = true;
        this.i.removeCallbacksAndMessages(null);
        this.i.sendEmptyMessageDelayed(1, j);
    }

    protected void start() {
        a(0L);
    }

    public final void notifyAsrLength(int i) {
        if (this.f) {
            this.a.vpmNotifyAsrLength(i);
        }
    }

    public void setWakeup() {
        OperationManager.getInstance().onWakeup(false, false);
    }

    public void setUnWakeup() {
        this.i.removeCallbacksAndMessages(null);
        g();
        OperationManager.getInstance().onUnWakeup();
    }

    public void asrPartialResultHandle(String str) {
        L.speech.d("%s asrPartialResultHandle.mode=%s", "[BaseSpeechWorkMode]:", name());
        OperationManager.getInstance().onAsrPartialResult(str);
        if (!TextUtils.isEmpty(str)) {
            if (Hardware.isX10() || Hardware.isX6A()) {
                notifyAsrLength(str.length());
            } else if (TextUtils.isEmpty(this.l)) {
                notifyAsrLength(str.length());
            }
            this.l = str;
        }
    }

    public void asrResultHandle(String str) {
        L.speech.d("%s asrResultHandle.mode=%s", "[BaseSpeechWorkMode]:", name());
        OperationManager.getInstance().onAsrResult(str);
        if (TextUtils.isEmpty(str)) {
            if (this.m) {
                if (TextUtils.isEmpty(this.l)) {
                    StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.asr_no_query_reason, "1");
                } else {
                    StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.asr_no_query_reason, "0");
                }
            } else if (TextUtils.isEmpty(this.l)) {
                StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.asr_no_query_reason, "3");
            } else {
                StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.asr_no_query_reason, "2");
            }
            StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.asr_no_query_count);
        }
        this.l = null;
    }

    public void ttsPlayFinishHandle() {
        L.speech.d("%s ttsPlayFinishHandle.mode=%s", "[BaseSpeechWorkMode]:", name());
    }

    public void dialogOpen() {
        L.speech.d("%s dialogOpen.mode=%s", "[BaseSpeechWorkMode]:", name());
    }

    public void dialogClose(boolean z) {
        L.speech.d("%s dialogClose.timeout=%b, .mode=%s", "[BaseSpeechWorkMode]:", Boolean.valueOf(z), name());
    }

    public void enterScene(int i, String str) {
        L.speech.d("%s enterScene.duration=%s, .type=%s, .mode=%s", "[BaseSpeechWorkMode]:", Integer.valueOf(i), str, name());
    }

    public void onRequestError() {
        setUnWakeup();
        L.speech.d("%s onRequestError.mode=%s", "[BaseSpeechWorkMode]:", name());
    }

    public void shortAudioFinish() {
        L.speech.d("%s dialogClose.mode=%s", "[BaseSpeechWorkMode]:", name());
    }

    public void release() {
        L.speech.d("%s release.mode=%s", "[BaseSpeechWorkMode]:", name());
        g();
    }

    public void disableVoiceWakeup() {
        this.g = true;
        this.a.disableVoiceWakeup();
        this.i.removeMessages(2);
        this.i.sendEmptyMessageDelayed(2, c);
    }

    public JSONObject getContextInfo() {
        L.speech.d("%s getContextInfo.disableVoiceWakeup=%s", "[BaseSpeechWorkMode]:", Boolean.valueOf(this.g));
        if (!this.g) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject2.put(FontsContractCompat.Columns.FILE_ID, "xxx");
            jSONObject3.put("user_audio_storage", jSONObject2);
            jSONObject.put("post_back", jSONObject3);
        } catch (JSONException e) {
            Logger logger = L.base;
            logger.d("[BaseSpeechWorkMode]:" + e.toString());
        }
        return jSONObject;
    }

    public void cancelSpeech() {
        SpeechManager.getInstance().cancelSpeech();
        AsrTtsCard.getInstance().onWakeupCancel();
        SpeechContextHelper.clearCachedContext();
    }
}
