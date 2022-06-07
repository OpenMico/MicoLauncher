package com.xiaomi.micolauncher.common.speech.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.helper.MultiChannelHelper;
import com.xiaomi.ai.android.helper.WakeUpGuideHelper;
import com.xiaomi.ai.android.helper.WakeupInfo;
import com.xiaomi.ai.api.MultiModal;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.SpeechWakeup;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.capability.ConnectionCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.capability.ErrorCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.capability.InstructionCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.capability.MIAuthCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.capability.SpeechSynthesizerCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import java.util.Map;

/* loaded from: classes3.dex */
public final class MiAivsWrapper extends BaseSpeechEngine {
    private Engine a;
    private Context b;
    private ErrorCapabilityImpl c;
    private String d;
    private WakeUpGuideHelper e;
    private volatile boolean f;

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean getOAuthStatus() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void setConfig(@NonNull BaseSpeechEngine.Config config, @NonNull String str) {
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean streamTts() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void uploadMultiChannelAsr(byte[] bArr, int i, String str, Object obj) {
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void wrapTravelUpload(ObjectNode objectNode) {
    }

    public MiAivsWrapper(Context context, Handler handler) {
        L.speech.d("MiAivsWrapper(%s)", Integer.valueOf(hashCode()));
        this.b = context;
        this.a = Engine.create(context.getApplicationContext(), a(), SpeechEngineHelper.createClientInfo(context), 1);
        SpeechEngineHelper.setLoggerLevelAndHooker();
        a(handler);
    }

    private AivsConfig a() {
        return SpeechEngineHelper.createConfiguration(this.b, streamTts());
    }

    private void a(Handler handler) {
        Engine engine = this.a;
        if (engine != null && handler != null) {
            engine.registerCapability(InstructionCapabilityImpl.init(handler));
            this.a.registerCapability(new SpeechSynthesizerCapabilityImpl(handler));
            this.c = new ErrorCapabilityImpl(handler, this.b);
            this.a.registerCapability(this.c);
            this.a.registerCapability(new MIAuthCapabilityImpl());
            this.a.registerCapability(new ConnectionCapabilityImpl());
        }
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void cleanAllUserLoginData() {
        if (this.a != null) {
            L.speech.d("[MiAivsWrapper]:cleanAllUserLoginData");
            this.a.clearUserData();
        }
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void uploadMultiChannelWuw(byte[] bArr, int i, String str, boolean z, Object obj) {
        int i2;
        if (this.a != null && bArr != null && bArr.length > 0) {
            L.speech.d("uploadMultiChannelWuw requestId %s size %d datalength %d", str, Integer.valueOf(i), Integer.valueOf(bArr.length));
            int i3 = 16;
            int i4 = 16000;
            if (Hardware.isX10()) {
                i3 = 32;
                i4 = 48000;
                i2 = 4;
            } else {
                i2 = 1;
            }
            WakeupInfo wakeupInfo = new WakeupInfo();
            wakeupInfo.deviceId = SystemSetting.getDeviceID();
            wakeupInfo.requestId = str;
            wakeupInfo.wakeupType = z ? WakeupInfo.WakeupType.WAKEUP_REAL : WakeupInfo.WakeupType.WAKEUP_SUSP;
            WakeupInfo.AudioMeta audioMeta = new WakeupInfo.AudioMeta();
            audioMeta.channel = 4;
            audioMeta.rate = OpusUtil.SAMPLE_RATE;
            audioMeta.codec = "pcm";
            wakeupInfo.audioMeta = audioMeta;
            try {
                new MultiChannelHelper(this.a).uploadData(wakeupInfo, bArr, i2, i3, i4, new MultiChannelHelper.MultiChannelCallback() { // from class: com.xiaomi.micolauncher.common.speech.utils.MiAivsWrapper.1
                    @Override // com.xiaomi.ai.android.helper.MultiChannelHelper.MultiChannelCallback
                    public void onError(String str2) {
                        L.speech.e("uploadMultiChannelWuw failure with %s", str2);
                    }

                    @Override // com.xiaomi.ai.android.helper.MultiChannelHelper.MultiChannelCallback
                    public void onSuccess(String str2) {
                        L.speech.d("uploadMultiChannelWuw success with %s", str2);
                    }
                });
            } catch (Throwable unused) {
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void uploadSingleChannelWuw(byte[] bArr, int i, String str, boolean z, Object obj) {
        if (this.a != null && bArr != null && bArr.length > 0) {
            L.speech.d("uploadSingleChannelWuw requestId %s size %d datalength %d", str, Integer.valueOf(i), Integer.valueOf(bArr.length));
            WakeupInfo wakeupInfo = new WakeupInfo();
            wakeupInfo.deviceId = SystemSetting.getDeviceID();
            wakeupInfo.requestId = str;
            wakeupInfo.wakeupType = z ? WakeupInfo.WakeupType.WAKEUP_REAL : WakeupInfo.WakeupType.WAKEUP_SUSP;
            WakeupInfo.AudioMeta audioMeta = new WakeupInfo.AudioMeta();
            audioMeta.channel = 1;
            audioMeta.rate = 16000;
            audioMeta.codec = "pcm";
            wakeupInfo.audioMeta = audioMeta;
            try {
                new MultiChannelHelper(this.a).uploadData(wakeupInfo, bArr, 1, 16, 16000, new MultiChannelHelper.MultiChannelCallback() { // from class: com.xiaomi.micolauncher.common.speech.utils.MiAivsWrapper.2
                    @Override // com.xiaomi.ai.android.helper.MultiChannelHelper.MultiChannelCallback
                    public void onError(String str2) {
                        L.speech.e("uploadSingleChannelWuw failure with %s", str2);
                    }

                    @Override // com.xiaomi.ai.android.helper.MultiChannelHelper.MultiChannelCallback
                    public void onSuccess(String str2) {
                        L.speech.d("uploadSingleChannelWuw success with %s", str2);
                    }
                });
            } catch (Throwable unused) {
            }
        }
    }

    private void b() {
        QueryLatency.getInstance().setSpeechRecognizeFinishSendMs();
        postEvent(APIUtils.buildEvent(new SpeechRecognizer.RecognizeStreamFinished(), null, this.d));
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean start() {
        this.a.interrupt();
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$MiAivsWrapper$_VZb-182gaafK5il576RlEDgjjU
            @Override // java.lang.Runnable
            public final void run() {
                MiAivsWrapper.this.c();
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        this.f = this.a.start();
        a(this.f);
        L.speech.d("MiAivsWrapper start,engineRunning=%s", Boolean.valueOf(this.f));
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void stop() {
        L.speech.d("%s stop", "[MiAivsWrapper]:");
        Engine engine = this.a;
        if (engine != null) {
            engine.interrupt();
        }
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void release() {
        this.a.release();
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean bundleRequest(boolean z, byte[] bArr, String str, boolean z2) {
        String str2;
        Event event;
        int i;
        if (!this.f) {
            L.speech.d("bundleRequest,engineRunning=%s", Boolean.valueOf(this.f));
            this.f = this.a.start();
            a(this.f);
            L.speech.d("bundleRequest,started engineRunning=%s", Boolean.valueOf(this.f));
        }
        ErrorCapabilityImpl errorCapabilityImpl = this.c;
        if (errorCapabilityImpl != null) {
            errorCapabilityImpl.setWakeTime();
        }
        if (bArr != null) {
            SpeechWakeup.Wakeup wakeup = new SpeechWakeup.Wakeup();
            wakeup.setRecognizeFollowed(true);
            wakeup.setRecognizeVoicePrint(true);
            SpeechWakeup.WakeupInfo wakeupInfo = new SpeechWakeup.WakeupInfo("horizon", "wakeup_real");
            wakeupInfo.setWord("小爱同学");
            wakeup.setWakeupInfo(wakeupInfo);
            wakeup.setAudioMeta(new SpeechWakeup.AudioMeta("opus", 1, 16000, "SSED", 0));
            Event buildEvent = APIUtils.buildEvent(wakeup);
            str2 = buildEvent.getId();
            L.speech.i("bundleRequest, eventId=%s,changeSession=%s,wakeupAudio.length=%d", str2, Boolean.valueOf(z), Integer.valueOf(bArr.length));
            if (!postEvent(buildEvent)) {
                L.base.w("mEngine bundleRequest: postWakeupEvent failed %s", str2);
                return false;
            }
            QueryLatency.getInstance().setWuwSendStartMs();
            int i2 = 0;
            boolean z3 = false;
            for (int length = bArr.length; length > 0; length -= i) {
                i = 16384;
                if (length <= 16384) {
                    QueryLatency.getInstance().setWuwSendEndMs();
                    z3 = true;
                    i = length;
                }
                if (!this.a.postData(bArr, i2, i, z3)) {
                    L.base.w("[MiAivsWrapper]:mEngine bundleRequest: postData failed %s", str2);
                    return false;
                }
                i2 += i;
            }
            if (!postEvent(APIUtils.buildEvent(new SpeechWakeup.WakeupStreamFinished(), null, str2))) {
                L.base.w("mEngine bundleRequest: postWakeupStreamFinished Event failed, eventId %s", str2);
                return false;
            }
        } else {
            str2 = null;
        }
        SpeechRecognizer.Recognize recognize = new SpeechRecognizer.Recognize();
        SpeechRecognizer.AudioProcessType audioProcessType = InstructionCapabilityImpl.getInstance().getAudioProcessType();
        if (audioProcessType != null && !z) {
            SpeechRecognizer.AudioProcess audioProcess = new SpeechRecognizer.AudioProcess();
            audioProcess.setEnable(audioProcessType);
            SpeechRecognizer.PostBack postBack = new SpeechRecognizer.PostBack();
            postBack.setAudioProcess(audioProcess);
            recognize.setPostBack(postBack);
        }
        if (TextUtils.isEmpty(str2)) {
            L.capability.w("%s bundleRequest.wakeUpEventId is null", "[MiAivsWrapper]:");
            event = APIUtils.buildEvent(recognize);
        } else {
            event = APIUtils.buildEvent(recognize, null, str2);
        }
        L.base.i("mEngine mEventId=%s", this.d);
        event.setContext(SpeechContextHelper.getContexts(true));
        if (z) {
            event.addContext(SpeechContextHelper.renewSessionContext());
        }
        this.d = event.getId();
        QueryLatency.getInstance().setSpeechRecognizeSendMs();
        if (!postEvent(event)) {
            L.base.i("mEngine startAsrTtsNlp: postEvent failed");
            return false;
        }
        InstructionCapabilityImpl.getInstance().setSpeechDialogId(this.d);
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean asrRequest(byte[] bArr, String str) {
        SpeechRecognizer.Recognize recognize = new SpeechRecognizer.Recognize();
        recognize.setRemoveEndPunctuation(false);
        Event buildEvent = APIUtils.buildEvent(recognize);
        buildEvent.addContext(SpeechContextHelper.requestControlContext(true, true));
        if (!postEvent(buildEvent)) {
            L.base.i("startAsrTtsNlp: postEvent failed");
            return false;
        }
        this.d = buildEvent.getId();
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public int addAsrAudioData(@NonNull byte[] bArr, int i, boolean z) {
        int i2 = 0;
        if (i > 0) {
            while (i > 0) {
                int i3 = 16384;
                if (i <= 16384) {
                    i3 = i;
                }
                try {
                    this.a.postData(bArr, i2, i3, z);
                    i2 += i3;
                    i -= i3;
                } catch (IllegalStateException e) {
                    Logger logger = L.speech;
                    logger.d("[MiAivsWrapper]:addAsrAudioData exception=" + e);
                }
            }
        }
        if (z) {
            b();
        }
        return i2;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean ttsRequest(@NonNull String str) {
        L.speech.d("%s ttsRequest=%s", "[MiAivsWrapper]:", str);
        Event buildEvent = APIUtils.buildEvent(new SpeechSynthesizer.Synthesize(str));
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        return postEvent(buildEvent);
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean nlpRequest(String str, @NonNull String str2, boolean z) {
        return nlpRequest(str, str2, null, z);
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean nlpRequest(String str, @NonNull String str2, String str3, boolean z) {
        Event buildEvent = APIUtils.buildEvent(new Nlp.Request(str2));
        if (!TextUtils.isEmpty(str3)) {
            L.monitor.i("[MiAivsWrapper]:NlpRequest.uuid_from_ubus=%s.dialog_id=%s", str3, buildEvent.getId());
        }
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        buildEvent.addContext(SpeechContextHelper.requestControlContext(true, false));
        if (z) {
            buildEvent.addContext(SpeechContextHelper.renewSessionContext());
        }
        return postEvent(buildEvent);
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean nlpTtsRequest(@NonNull String str, boolean z) {
        return nlpTtsRequest(str, null, z);
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean nlpTtsRequest(@NonNull String str, String str2, boolean z) {
        Event buildEvent = APIUtils.buildEvent(new Nlp.Request(str));
        if (!TextUtils.isEmpty(str2)) {
            L.monitor.i("[MiAivsWrapper]:NlpTtsRequest.uuid_from_ubus=%s.dialog_id=%s", str2, buildEvent.getId());
        }
        buildEvent.setContext(SpeechContextHelper.getContexts(false));
        if (z) {
            buildEvent.addContext(SpeechContextHelper.renewSessionContext());
        }
        return postEvent(buildEvent);
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean postEvent(@NonNull Event event) {
        Engine engine = this.a;
        if (engine == null) {
            return false;
        }
        boolean postEvent = engine.postEvent(event);
        L.speech.w("%s postEvent=%s,eventStr=%s", "[MiAivsWrapper]:", Boolean.valueOf(postEvent), event.toString());
        return postEvent;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public boolean postEventWithContext(@NonNull Event event) {
        if (this.a == null) {
            return false;
        }
        event.setContext(SpeechContextHelper.getContexts(false));
        boolean postEvent = this.a.postEvent(event);
        L.speech.d("%s postEvent=%s,eventStr=%s", "[MiAivsWrapper]:", Boolean.valueOf(postEvent), event.toString());
        return postEvent;
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void forceStop() {
        L.speech.d("%s forceStop", "[MiAivsWrapper]:");
        Engine engine = this.a;
        if (engine != null) {
            engine.interrupt();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public String getAuthorizationFromEngine() {
        return this.a.requestAuthorization();
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    protected long getTokenExpiredAt() {
        return this.a.getExpireAt();
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public String getAuthorizationValue() {
        return this.a.getAuthorization();
    }

    public WakeUpGuideHelper getWakeUpGuideHelper() {
        return this.e;
    }

    private void a(boolean z) {
        if (z && this.e == null) {
            this.e = new WakeUpGuideHelper(this.a);
        }
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void postVisionRecognizeResultEvent(MultiModal.VisionRecognizeResult visionRecognizeResult) {
        L.camera2.d("postVisionRecognizeResultEvent !");
        if (this.a == null) {
            L.camera2.e("mEngine is null !");
            return;
        }
        Event buildEvent = APIUtils.buildEvent(new MultiModal.VisionRecognizeStarted());
        this.a.postEvent(buildEvent);
        String id = buildEvent.getId();
        this.a.postEvent(APIUtils.buildEvent(visionRecognizeResult, null, id));
        this.a.postEvent(APIUtils.buildEvent(new MultiModal.VisionRecognizeFinished(), null, id));
    }

    @Override // com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine
    public void wrapTravelUploadMap(MapNode mapNode) {
        this.a.startTrace();
        this.a.traceRequestId(mapNode.requestId);
        if (mapNode.timeValue != null && !mapNode.timeValue.isEmpty()) {
            for (Map.Entry<String, Long> entry : mapNode.timeValue.entrySet()) {
                String key = entry.getKey();
                Long value = entry.getValue();
                if (!(key == null || value == null)) {
                    this.a.traceTimeStamps(key, value.longValue());
                }
            }
        }
        if (mapNode.codeValue != null && !mapNode.codeValue.isEmpty()) {
            for (Map.Entry<String, Integer> entry2 : mapNode.codeValue.entrySet()) {
                String key2 = entry2.getKey();
                Integer value2 = entry2.getValue();
                if (!(key2 == null || value2 == null)) {
                    this.a.traceResult(key2, value2.intValue());
                }
            }
        }
        if (mapNode.stateValue != null && !mapNode.stateValue.isEmpty()) {
            for (Map.Entry<String, String> entry3 : mapNode.stateValue.entrySet()) {
                String key3 = entry3.getKey();
                String value3 = entry3.getValue();
                if (!(key3 == null || value3 == null)) {
                    this.a.traceResult(key3, value3);
                }
            }
        }
        this.a.finishTrace();
    }
}
