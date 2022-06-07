package com.xiaomi.micolauncher.common.speech;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.os.Parcel;
import android.os.SystemClock;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.android.helper.WakeUpGuideHelper;
import com.xiaomi.ai.api.MultiModal;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.PlayByRemoteEvent;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.player.TtsPcmPlayer;
import com.xiaomi.micolauncher.common.player.TtsPlayer;
import com.xiaomi.micolauncher.common.player.TtsUrlPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import com.xiaomi.micolauncher.common.speech.utils.MiAivsWrapper;
import com.xiaomi.micolauncher.common.speech.utils.MultiChannelAudioHandler;
import com.xiaomi.micolauncher.common.speech.utils.PrivacyHelper;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventHandler;
import com.xiaomi.micolauncher.common.speech.utils.SpeechLog;
import com.xiaomi.micolauncher.common.speech.utils.WakeupInfo;
import com.xiaomi.micolauncher.common.speech.utils.WifiInfoPackage;
import com.xiaomi.micolauncher.common.speech.utils.WrapTravelNode;
import com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode;
import com.xiaomi.micolauncher.common.speech.workmode.DialogSpeechMode;
import com.xiaomi.micolauncher.common.speech.workmode.MemoSpeechMode;
import com.xiaomi.micolauncher.common.speech.workmode.NormalSpeechMode;
import com.xiaomi.micolauncher.common.speech.workmode.VoicePrintSpeechMode;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.common.track.TrackLog;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.module.setting.utils.FullDuplexSpeechHelper;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class SpeechManager implements BaseSpeechEngine.TokenChangedListener {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile SpeechManager b;
    private Context a;
    private VpmClient c;
    private TtsPlayer d;
    @SuppressLint({"StaticFieldLeak"})
    private BaseSpeechEngine e;
    private MultiChannelAudioHandler f;
    private a g;
    private SpeechLog h;
    private String i;
    private boolean j;
    private boolean k;
    private String l;
    private boolean m;
    private BaseSpeechWorkMode n;

    public static SpeechManager getInstance() {
        SpeechManager peekInstance = peekInstance();
        if (peekInstance != null) {
            return peekInstance;
        }
        throw new IllegalStateException("call init before getInstance!");
    }

    public static SpeechManager peekInstance() {
        if (b != null) {
            return b;
        }
        synchronized (SpeechManager.class) {
            if (b == null) {
                return null;
            }
            return b;
        }
    }

    private SpeechManager(Context context) {
        MibrainConfig mibrainConfig = MibrainConfig.getMibrainConfig(context);
        if (mibrainConfig != null) {
            this.a = context;
            this.g = new a(context, ThreadUtil.getLightWorkHandler().getLooper());
            this.e = new MiAivsWrapper(context, this.g);
            this.e.setTokenChangedListener(this);
            this.c = new VpmClient(this.g, mibrainConfig.clientId, context.getPackageName(), 5000);
            this.c.setLogHook($$Lambda$SpeechManager$__E_SkdEjv5F5ez7Du49qmCpk.INSTANCE);
            if (this.e.streamTts()) {
                this.d = new TtsPcmPlayer(this.g);
            } else {
                this.d = new TtsUrlPlayer(this.g);
            }
            this.h = new SpeechLog();
            if (SpeechConfig.isLabTest()) {
                this.f = null;
            } else {
                this.f = new MultiChannelAudioHandler(this.c, this.e);
            }
            this.n = new NormalSpeechMode(this.c, this.e);
            if (!EventBusRegistry.getEventBus().isRegistered(this)) {
                EventBusRegistry.getEventBus().register(this);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("SpeechManager can not get MibrainConfig");
    }

    public static /* synthetic */ void a(String str, Object[] objArr) {
        L.speech.d(str, objArr);
    }

    public static void init(Context context) {
        synchronized (SpeechManager.class) {
            if (b == null) {
                b = new SpeechManager(context.getApplicationContext());
            }
        }
    }

    public void nlpRequest(String str) {
        nlpRequest(str, false, null);
    }

    public void nlpRequest(String str, boolean z) {
        nlpRequest(str, z, null);
    }

    public void nlpRequest(String str, String str2) {
        nlpRequest(str, false, str2);
    }

    public void nlpRequest(String str, boolean z, String str2) {
        if (z) {
            this.g.a();
            d();
            setUnWakeup();
            L.speech.i("%s nlpRequest disableMic, remove all msgs, set unwakeup", "[SpeechManager]:");
        }
        L.speech.d("nlp request %s", str);
        String l = Long.toString(SystemClock.elapsedRealtime());
        if (TextUtils.isEmpty(str2)) {
            this.e.nlpRequest(l, str, this.n.getNewSession());
        } else {
            this.e.nlpRequest(l, str, str2, false);
        }
        this.n.clearNewSession();
        this.j = true;
    }

    private void a(String str, boolean z, boolean z2) {
        Logger logger = L.speech;
        logger.i("[SpeechManager]:ttsRequest.content=" + str);
        if (!TextUtils.isEmpty(str)) {
            a(1003, 1005, 1001, 1002, 1000, 1008);
            if (z2) {
                this.d.enableFocus();
            } else {
                this.d.disableFocus();
            }
            this.i = str;
            if (z) {
                a(1004, str);
            }
            this.j = false;
            this.e.ttsRequest(str);
        }
    }

    public void ttsRequest(String str) {
        ttsRequest(str, false);
    }

    public void ttsRequest(String str, boolean z) {
        ttsRequest(str, z, true);
    }

    public void ttsRequest(String str, boolean z, boolean z2) {
        a(str, z, z2);
    }

    public void nlpTtsRequest(String str) {
        nlpTtsRequest(str, null, false);
    }

    public void nlpTtsRequest(String str, boolean z) {
        nlpTtsRequest(str, null, z);
    }

    public void nlpTtsRequest(String str, String str2, boolean z) {
        a(1003, 1001, 1002, 1000, 1008);
        if (z) {
            a(2005, str);
        }
        this.j = true;
        if (TextUtils.isEmpty(str2)) {
            this.e.nlpTtsRequest(str, this.n.getNewSession());
        } else {
            this.e.nlpTtsRequest(str, str2, this.n.getNewSession());
        }
        this.n.clearNewSession();
    }

    public boolean isNlpRequest() {
        return this.j;
    }

    public void asrFinalResultRequest(String str) {
        AsrTtsCard.getInstance().onAsrResult(str, 0L, false);
        getInstance().cancelSpeech();
        nlpTtsRequest(str);
    }

    public void uiShow(String str) {
        a(7000, str);
    }

    public void destroy() {
        L.speech.d("[SpeechManager]:SpeechManager: destroy");
        VpmClient vpmClient = this.c;
        if (vpmClient != null) {
            vpmClient.vpmDestroy();
        }
        BaseSpeechEngine baseSpeechEngine = this.e;
        if (baseSpeechEngine != null) {
            baseSpeechEngine.release();
        }
    }

    public boolean isRunning() {
        return this.c.vpmIsRunning();
    }

    public String getDialogId() {
        String str = this.l;
        return str == null ? "null" : str;
    }

    public void start() {
        this.c.vpmStart();
        BaseSpeechEngine baseSpeechEngine = this.e;
        if (baseSpeechEngine != null) {
            baseSpeechEngine.start();
        }
        setVpmWorking(!Mic.getInstance().isMicMute());
        MultiChannelAudioHandler multiChannelAudioHandler = this.f;
        if (multiChannelAudioHandler != null) {
            multiChannelAudioHandler.start();
        }
    }

    public void stop() {
        d();
        dialogClose(false);
        setVpmWorking(false);
        this.c.vpmStop();
    }

    public void uploadLog() {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.-$$Lambda$SpeechManager$esR7jNQCgDFmy094FxII0HPdWAA
            @Override // java.lang.Runnable
            public final void run() {
                SpeechManager.this.e();
            }
        });
    }

    public /* synthetic */ void e() {
        this.c.vpmUploadLog();
    }

    public void a() {
        this.c.vpmStartUpload();
    }

    public void stopUploadData() {
        this.c.vpmStopUpload();
    }

    public /* synthetic */ void a(boolean z) {
        this.c.vpmSetWorking(z);
    }

    public void setVpmWorking(final boolean z) {
        ThreadUtil.getWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.-$$Lambda$SpeechManager$6AlyUhsAxxB28SzOyYKW7Y593ds
            @Override // java.lang.Runnable
            public final void run() {
                SpeechManager.this.a(z);
            }
        });
    }

    public void enablePrivacy() {
        this.c.privacyEnable();
    }

    public void disablePrivacy() {
        this.c.privacyDisable();
    }

    public void forceRefreshToken() {
        this.e.cleanAllUserLoginData();
        this.e.asyncRefreshToken();
    }

    public void enterPowerSaveMode() {
        this.c.vpmSleepModeEnter();
    }

    public void enterPerfMode() {
        this.c.vpmSleepModeExit();
    }

    public String getAuthorizationValueBlocked() {
        ThreadUtil.verifyThread();
        return this.e.getAuthorizationValue();
    }

    /* loaded from: classes3.dex */
    public class UpdateMiotInitFinish {
        public String authorization;

        public UpdateMiotInitFinish() {
            SpeechManager.this = r1;
        }
    }

    public void screenOff() {
        BaseSpeechEngine baseSpeechEngine = this.e;
        if (baseSpeechEngine != null) {
            baseSpeechEngine.forceStop();
        }
        a aVar = this.g;
        if (aVar != null) {
            aVar.a();
        }
        d();
        VpmClient vpmClient = this.c;
        if (vpmClient != null) {
            vpmClient.vpmSetUnWakeup();
        }
        OperationManager.getInstance().onUnWakeup();
    }

    public void setNewSessionWhenEmptyAsr() {
        this.m = true;
    }

    public void checkSetNewSession() {
        if (this.m) {
            this.n.setNewSession();
            this.m = false;
        }
    }

    public void setNewSession() {
        L.speech.d("%s setNewSession", "[SpeechManager]:");
        this.n.setNewSession();
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine.TokenChangedListener
    public void onTokenChanged(boolean z) {
        if (!z) {
            start();
        }
        String authorizationValueBlocked = getAuthorizationValueBlocked();
        UpdateMiotInitFinish updateMiotInitFinish = new UpdateMiotInitFinish();
        updateMiotInitFinish.authorization = authorizationValueBlocked;
        EventBusRegistry.getEventBus().post(updateMiotInitFinish);
        this.c.vpmUpdateDeviceInfo(TokenManager.getInstance().getUserId(), this.e.getDeviceId(this.a), authorizationValueBlocked, ApiConstants.getMicoUplogHostEnv(), SystemSetting.getDeviceID());
        TrackLog.shareInstance().updateAuthToken(authorizationValueBlocked);
        a(authorizationValueBlocked);
    }

    public void b() {
        WifiInfoPackage wifiInfoPackage = new WifiInfoPackage(this.a);
        L.speech.i("onWifiInfoChanged: bssid = %s, ssid = %s, rssi = %d", wifiInfoPackage.bssid, wifiInfoPackage.ssid, Integer.valueOf(wifiInfoPackage.rssi));
        this.c.vpmUpdateWifiInfo(wifiInfoPackage.bssid, wifiInfoPackage.ssid, wifiInfoPackage.rssi);
    }

    public void onWifiInfoChanged() {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.-$$Lambda$SpeechManager$-p1OjpK0H6Py3TzhmRhAsn1ZTYw
            @Override // java.lang.Runnable
            public final void run() {
                SpeechManager.this.b();
            }
        });
    }

    public boolean isAvailable() {
        return this.e.getOAuthStatus() && this.c.vpmIsRunning();
    }

    public void stopTts() {
        L.speech.d("[SpeechManager]:stopTts");
        if (this.d.isPlaying()) {
            this.d.requestFinish();
        }
        d();
    }

    public void enterMemoMode() {
        if (!(this.n instanceof MemoSpeechMode)) {
            L.speech.d("%s enter mono mode", "[SpeechManager]:");
            setWakeup();
            BaseSpeechWorkMode baseSpeechWorkMode = this.n;
            if (baseSpeechWorkMode != null) {
                baseSpeechWorkMode.release();
            }
            this.n = new MemoSpeechMode(this.c, this.e);
        }
    }

    public void enterVoicePrintMode() {
        if (!(this.n instanceof VoicePrintSpeechMode)) {
            L.speech.d("%s enterVoicePrintMode", "[SpeechManager]:");
            BaseSpeechWorkMode baseSpeechWorkMode = this.n;
            if (baseSpeechWorkMode != null) {
                baseSpeechWorkMode.release();
            }
            this.n = new VoicePrintSpeechMode(this.c, this.e);
        }
    }

    public void enterNormalMode() {
        enterNormalMode(true);
    }

    public void enterNormalMode(boolean z) {
        L.speech.d("%s enterNormalMode stopTts=%s", "[SpeechManager]:", Boolean.valueOf(z));
        this.g.a();
        if (z) {
            setUnWakeup();
            this.e.forceStop();
            this.d.requestFinish();
        }
        d();
        BaseSpeechWorkMode baseSpeechWorkMode = this.n;
        if (baseSpeechWorkMode != null) {
            baseSpeechWorkMode.release();
        }
        if (!(this.n instanceof NormalSpeechMode)) {
            this.n = new NormalSpeechMode(this.c, this.e);
        }
    }

    public void setWakeup() {
        L.speech.d("%s setWakeup", "[SpeechManager]:");
        Screen.getInstance().onResume();
        a(6000);
        this.n.setWakeup();
    }

    public void setUnWakeup() {
        L.speech.d("[SpeechManager]:setUnWakeup");
        this.c.vpmSetUnWakeup();
        this.n.setUnWakeup();
        this.g.a();
    }

    public void sendEventRequest(String str, String str2, String str3, boolean z) {
        this.e.eventRequest(str, str2, str3, z);
        this.n.clearNewSession();
    }

    public void sendEventRequest(Event event) {
        this.e.postEventWithContext(event);
    }

    public boolean isDialogMode() {
        return this.n.isDialog();
    }

    public boolean isDialogContinuous() {
        return this.n.isDialogContinuous();
    }

    public boolean isVoiceWakeup() {
        return this.k;
    }

    public String getSceneType() {
        return this.n.getSceneType();
    }

    public String getSpeakingTts() {
        return this.i;
    }

    public void setSpeakingTts(String str) {
        this.i = str;
    }

    public void cleanAllUserLoginData() {
        this.e.cleanAllUserLoginData();
    }

    public void startUploadMultiChsAsr() {
        MultiChannelAudioHandler multiChannelAudioHandler = this.f;
        if (multiChannelAudioHandler != null) {
            multiChannelAudioHandler.startUploadAsr();
        }
    }

    public void checkNeedToOpenMic() {
        a(6001);
        a(6001);
    }

    public void c() {
        if (!(this.n instanceof DialogSpeechMode)) {
            this.n = new DialogSpeechMode(this.c, this.e);
        }
        a(6000);
        this.n.dialogOpen();
        L.speech.d("[SpeechManager]:dialogOpen");
    }

    public void cancelSpeech() {
        this.c.vpmSetUnWakeup();
        this.d.requestFinish();
        this.e.forceStop();
        this.c.enableVoiceWakeup();
        this.g.removeCallbacksAndMessages(null);
    }

    public void muteTts() {
        this.d.mute();
    }

    public int vpmPlayWakeup(int i, int i2) {
        VpmClient vpmClient = this.c;
        if (vpmClient != null) {
            return vpmClient.vpmPlayWakeup(i, i2);
        }
        return 0;
    }

    public void dialogClose(boolean z) {
        a(6000);
        this.n.dialogClose(z);
        L.speech.d("%s dialogClose", "[SpeechManager]:");
    }

    public void exitContinuousDialog() {
        L.speech.d("%s exitContinuousDialog", "[SpeechManager]:");
        QueryLatency.getInstance().cancelWrapTravel();
        dialogClose(false);
    }

    private void a(int... iArr) {
        if (this.g != null) {
            for (int i : iArr) {
                this.g.removeMessages(i);
            }
        }
    }

    private void d() {
        a aVar = this.g;
        if (aVar != null) {
            aVar.removeCallbacksAndMessages(null);
        }
    }

    private void a(int i) {
        a aVar = this.g;
        if (aVar != null) {
            aVar.sendEmptyMessage(i);
        }
    }

    private void a(int i, Object obj) {
        a aVar = this.g;
        if (aVar != null) {
            aVar.obtainMessage(i, obj).sendToTarget();
        }
    }

    public void WrapTravel() {
        WrapTravelNode wrapTravelNode = QueryLatency.getInstance().getWrapTravelNode();
        if (!(wrapTravelNode == null || wrapTravelNode.object == null)) {
            this.e.wrapTravelUpload(wrapTravelNode.object);
        }
        if (wrapTravelNode != null && wrapTravelNode.map != null && !wrapTravelNode.map.isEmpty()) {
            this.e.wrapTravelUploadMap(wrapTravelNode.map);
        }
    }

    public void WrapTravelUiShow() {
        L.speech.i("%s WrapTravelUiShow", "[SpeechManager]:");
        WrapTravelNode wrapTravelNode = QueryLatency.getInstance().getWrapTravelNode();
        if (!(wrapTravelNode == null || wrapTravelNode.object == null)) {
            this.e.wrapTravelUpload(wrapTravelNode.object);
        }
        if (wrapTravelNode != null && wrapTravelNode.map != null && !wrapTravelNode.map.isEmpty()) {
            this.e.wrapTravelUploadMap(wrapTravelNode.map);
        }
    }

    public void WrapTravelFailure(int i, String str, String str2) {
        WrapTravelNode wrapTravelFailureNode = QueryLatency.getInstance().getWrapTravelFailureNode(i, str, str2);
        if (!(wrapTravelFailureNode == null || wrapTravelFailureNode.object == null)) {
            this.e.wrapTravelUpload(wrapTravelFailureNode.object);
        }
        if (wrapTravelFailureNode != null && wrapTravelFailureNode.map != null && !wrapTravelFailureNode.map.isEmpty()) {
            this.e.wrapTravelUploadMap(wrapTravelFailureNode.map);
        }
    }

    /* loaded from: classes3.dex */
    public final class a extends SpeechEventHandler {
        Context a;
        private boolean c;
        private boolean d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a(Context context, Looper looper) {
            super(looper);
            SpeechManager.this = r1;
            this.a = context;
            this.d = false;
        }

        void a() {
            this.c = false;
        }

        private void b() {
            SpeechManager.this.k = false;
            SpeechManager.this.n.setWakeupInfo(null);
            SpeechManager.this.c.vpmSetWakeup();
        }

        private void c() {
            WakeupInfo.setWakeupEventAtMs();
            SpeechManager.this.e.forceStop();
            SpeechManager.this.k = true;
            this.c = false;
            SpeechManager.this.d.enableFocus();
            this.d = true ^ SpeechManager.this.n.wakeupHandle();
            if (SpeechConfig.supportDialogSpeech()) {
                if (FullDuplexSpeechHelper.isEnabled()) {
                    if (SpeechManager.this.n instanceof NormalSpeechMode) {
                        SpeechManager speechManager = SpeechManager.this;
                        speechManager.n = new DialogSpeechMode(speechManager.c, SpeechManager.this.e);
                    }
                } else if (SpeechManager.this.n instanceof DialogSpeechMode) {
                    SpeechManager speechManager2 = SpeechManager.this;
                    speechManager2.n = new NormalSpeechMode(speechManager2.c, SpeechManager.this.e);
                }
            }
            Screen.getInstance().systemUiDismiss(0);
            StatPoints.recordPoint(StatPoints.Event.micolog_wakeup, StatPoints.WakeupKey.xatx_count);
            int powerSaveModeSleepMs = SpeechConfig.getPowerSaveModeSleepMs();
            if (powerSaveModeSleepMs > 0) {
                SpeechManager.this.g.removeMessages(6002);
                SpeechManager.this.g.sendEmptyMessageDelayed(6002, TimeUnit.SECONDS.toMillis(powerSaveModeSleepMs));
            }
        }

        private void a(Object obj) {
            WakeupInfo wakeupInfo;
            removeMessages(0);
            removeMessages(2000);
            removeMessages(1000);
            if (obj instanceof Parcel) {
                wakeupInfo = new WakeupInfo((Parcel) obj);
                SpeechManager.this.h.writeWakeupLog(wakeupInfo.getAlgWakeupAtMs(), wakeupInfo.getWakeupAngle(), wakeupInfo.getWakeupScore(), wakeupInfo.getAlgDelay(), wakeupInfo.getWakeupCnt());
            } else {
                wakeupInfo = new WakeupInfo();
            }
            L.speech.i("[SpeechManager]:MSG_VPM_WUW.work_mode=%s", SpeechManager.this.n.name());
            SpeechManager.this.n.setWakeupInfo(wakeupInfo);
            wakeupInfo.printWakeupInfo();
            QueryLatency.getInstance().setWakeupInfoMs(wakeupInfo);
            if (SpeechManager.this.n != null) {
                SpeechManager.this.j = false;
                SpeechManager.this.n.startPostAudio();
            }
        }

        private void a(Parcel parcel) {
            String str = "";
            String str2 = "";
            String str3 = "1";
            parcel.setDataPosition(0);
            int readInt = parcel.readInt();
            if (readInt > 0) {
                byte[] bArr = new byte[readInt];
                parcel.readByteArray(bArr);
                str = new String(bArr);
            }
            int readInt2 = parcel.readInt();
            if (readInt2 > 0) {
                byte[] bArr2 = new byte[readInt2];
                parcel.readByteArray(bArr2);
                str2 = new String(bArr2);
            }
            int readInt3 = parcel.readInt();
            if (readInt3 > 0) {
                byte[] bArr3 = new byte[readInt3];
                parcel.readByteArray(bArr3);
                str3 = new String(bArr3);
            }
            L.speech.d("%s vpm_stat_points.event=%s, .key=%s, .val=%s", "[SpeechManager]:", str, str2, str3);
            if (!TextUtils.isEmpty(str2)) {
                StatPoints.recordPoint(str, str2, str3);
            }
        }

        private void b(Object obj) {
            TtsPlayer.TtsPayload ttsPayload;
            if (obj instanceof TtsPlayer.TtsPayload) {
                ttsPayload = (TtsPlayer.TtsPayload) obj;
            } else {
                ttsPayload = new TtsPlayer.TtsPayload("", SpeechManager.this.l, SpeechManager.this.i);
            }
            OperationManager.getInstance().onTtsPlayEnd(ttsPayload.content, ttsPayload.id, ttsPayload.interrupt);
            L.speech.i("[SpeechManager]:MSG_TTS_PLAY_FINISH.mReOpenMic=%s, work_mode=%s", Boolean.valueOf(this.c), SpeechManager.this.n.name());
            if (this.c) {
                this.c = false;
                sendEmptyMessage(6000);
                OperationManager.getInstance().onWakeup(false, false);
            } else {
                SpeechManager.this.n.ttsPlayFinishHandle();
            }
            SpeechManager.getInstance().WrapTravel();
            QueryLatency.getInstance().printInfo();
            if (PrivacyHelper.privacyNeedInit()) {
                PrivacyHelper.privacyInit();
            } else if (PrivacyHelper.isPrivacyEnable()) {
                SpeechManager.this.a();
            }
            if (SpeechManager.this.f != null) {
                SpeechManager.this.f.startSave(SpeechManager.this.l);
                SpeechManager.this.f.startUploadWuw();
            }
        }

        private void d() {
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.-$$Lambda$SpeechManager$a$lUUz5ZNDzth--k0liXAvh6W9pbM
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechManager.a.this.g();
                }
            });
        }

        public /* synthetic */ void g() {
            SpeechManager.this.c.vpmEnableWuw();
            SpeechManager.this.c.vpmSetWorking(!Mic.getInstance().isMicMute());
            SpeechManager.this.c.enableVoiceWakeup();
            SpeechManager.this.c.vpmSetWakeupSense(SpeechConfig.b());
            if (PrivacyHelper.isPrivacyEnable()) {
                SpeechManager.this.enablePrivacy();
            } else {
                SpeechManager.this.disablePrivacy();
            }
            if (SpeechConfig.a(this.a)) {
                SpeechManager.this.c.vpmMultiWuwEnable();
            }
            if (Screen.getInstance().isInteractive() || AudioPolicyService.getInstance().hasActiveAudioSource()) {
                SpeechManager.this.c.vpmSleepModeExit();
            } else {
                SpeechManager.this.c.vpmSleepModeEnter();
            }
            SpeechManager.this.b();
        }

        private void b(Parcel parcel) {
            parcel.setDataPosition(0);
            byte[] bArr = new byte[parcel.readInt()];
            parcel.setDataPosition(0);
            parcel.readByteArray(bArr);
            String str = new String(bArr);
            L.speech.i("%s product_code=%s", "[SpeechManager]:", str);
            SpeechManager.this.e.setConfig(BaseSpeechEngine.Config.ALG_VENDOR, str);
            if (SpeechManager.this.c != null) {
                SpeechManager.this.e.asyncRefreshToken();
                d();
            }
        }

        private void e() {
            WakeupInfo.setLocalWakeupAtMs();
            SpeechManager.this.n.onLocalWakeup();
            ThreadUtil.getWorkHandler().postDelayed($$Lambda$SpeechManager$a$n5G0p7pi99ixRwJVFjrUGswJZ8.INSTANCE, TimeUnit.SECONDS.toMillis(1L));
            QueryLatency.getInstance().wrapTravelInit();
            AsrTtsCard.getInstance().onWakeupWithoutSound();
        }

        public static /* synthetic */ void f() {
            EventBusRegistry.getEventBus().post(new PlayByRemoteEvent());
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:152:0x04a0  */
        @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventHandler, android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r20) {
            /*
                Method dump skipped, instructions count: 1352
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.speech.SpeechManager.a.handleMessage(android.os.Message):void");
        }

        public static /* synthetic */ void a(String str) {
            OperationManager.getInstance().process(str);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWifiConnectivityChangedEvent(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            this.e.resetRefreshTokenRetryInterval(true);
        }
    }

    public a getUiHandler() {
        return this.g;
    }

    public void cacheWuw(byte[] bArr, int i) {
        MultiChannelAudioHandler multiChannelAudioHandler = this.f;
        if (multiChannelAudioHandler != null) {
            multiChannelAudioHandler.cacheSingleWuw(bArr, i);
        }
    }

    public void cacheAsr(byte[] bArr, int i, boolean z) {
        MultiChannelAudioHandler multiChannelAudioHandler = this.f;
        if (multiChannelAudioHandler != null) {
            multiChannelAudioHandler.cacheSingAsr(bArr, i, z);
        }
    }

    public void enterScene(int i, String str) {
        this.n.enterScene(i, str);
    }

    private void a(String str) {
        Settings.HeadersUpdated headersUpdated = new Settings.HeadersUpdated();
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        headersUpdated.setAuthorization(arrayList);
        sendEventRequest(APIUtils.buildEvent(headersUpdated));
    }

    public void onQueryResultInvalid() {
        this.n.onQueryResultInvalid();
    }

    public void onQueryResultNoTts() {
        this.n.onQueryResultNoTts();
    }

    public WakeUpGuideHelper getWakeUpGuideHelper() {
        return ((MiAivsWrapper) this.e).getWakeUpGuideHelper();
    }

    public void postVisionRecognizeResultEvent(MultiModal.VisionRecognizeResult visionRecognizeResult) {
        this.e.postVisionRecognizeResultEvent(visionRecognizeResult);
    }
}
