package com.xiaomi.micolauncher.common.speech.utils;

import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.common.Optional;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.setting.utils.FullDuplexSpeechHelper;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.passport.StatConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class QueryLatency {
    public static final String AlarmCountDown = "alarm.countdown";
    public static final String AlarmCreate = "alarm.create";
    private static final String LOG_TAG = "[QueryLatency]: ";
    public static final String MultiLine = "michat.multiline";
    public static final String MusicPlayer = "music.player";
    public static final String RadioPlayer = "station.player";
    public static final String VideoPlayer = "mobilevideo.player";
    public static final String VideoRecommend = "mobilevideo.recommend";
    public static final String Weather = "weather.view";
    private boolean cancel;
    private boolean isPlaying;
    private boolean isTimeOut;
    private String requestAsr;
    private String requestId;
    private transient long timeDiff;
    private String uiType;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
    private static final EventIdSet idSet = new EventIdSet(5);
    private static final QueryLatency instance = new QueryLatency();
    private final boolean localVad = SpeechConfig.isLocalVad();
    private boolean debug = SpeechConfig.isAutoTest();
    private SystemTimeStamp timeStampSystem = null;
    private AudioFileTimeStamp timeStampAudioFile = null;
    private Latency zLatency = null;

    public static QueryLatency getInstance() {
        return instance;
    }

    private QueryLatency() {
    }

    public void wrapTravelInit() {
        this.timeStampSystem = new SystemTimeStamp();
        this.cancel = false;
    }

    public void cancelWrapTravel() {
        this.cancel = true;
    }

    public void setWakeupInfoMs(WakeupInfo wakeupInfo) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.WuwAudioEnd = wakeupInfo.getShouldWakeupAtMs();
            this.timeStampSystem.WakeupLocal = wakeupInfo.getAlgWakeupAtMs();
            this.timeStampSystem.WakeupReal = wakeupInfo.getMdWakeupAtMs();
            this.timeStampSystem.WakeupEventTranEnd = wakeupInfo.getWakeupEventAtMs();
        }
        this.isPlaying = Screen.getInstance().isHoldByPlay();
    }

    public void setWuwSendStartMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.WuwSendStart = currentTimeMillis;
        }
    }

    public void setWuwSendEndMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.WuwSendEnd = currentTimeMillis;
        }
    }

    public void setPlaySoundAtMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.PlayWakeupSound = currentTimeMillis;
        }
    }

    public void setPauseSoundAtMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.PauseWakeupSound = currentTimeMillis;
        }
    }

    public void setSpeechRecognizeSendMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrRecognizeSend = currentTimeMillis;
        }
    }

    public void setSpeechRecognizeFinishSendMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrRecognizeFinishSend = currentTimeMillis;
        }
    }

    public void setSpeechFinishSpeakStreamMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.FinishSpeakStream = currentTimeMillis;
        }
    }

    public void setSpeechLaunchAppIntentMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.LaunchAppIntent = currentTimeMillis;
        }
    }

    public void onTruncationNotification() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.TruncationNotification = currentTimeMillis;
        }
    }

    public void onWakeupCancel() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.WakeupCancel = currentTimeMillis;
        }
    }

    public void onAsrCancel() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrCancel = currentTimeMillis;
        }
    }

    public void setAsrSendStartMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrSendStart = currentTimeMillis;
        }
    }

    public void setAsrStartMs(long j, long j2, long j3) {
        L.latency.d("setAsrStartMs() .audio=%d .alg=%d", Long.valueOf(j2), Long.valueOf(j3));
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrAudioStart = j2;
            systemTimeStamp.AsrLocalVadStart = j3;
            this.timeDiff = j3 - j;
            this.isTimeOut = false;
        }
    }

    public void setAsrEndMs(long j, long j2, long j3) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null && j3 < systemTimeStamp.AsrLocalVadEnd) {
            SystemTimeStamp systemTimeStamp2 = this.timeStampSystem;
            systemTimeStamp2.AsrAudioEnd = j2;
            systemTimeStamp2.AsrLocalVadEnd = j3;
        }
    }

    public void setAsrSendEnd(long j) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrTotalBytes = j;
            systemTimeStamp.AsrSendEnd = System.currentTimeMillis();
        }
    }

    public void setAsrSendRealEnd() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrSendRealEnd = System.currentTimeMillis();
        }
    }

    void setBrainStartPacketMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrCloudVadStart = currentTimeMillis;
            long j = systemTimeStamp.AsrAudioStart;
        }
    }

    public void setBrainEndPacketMs() {
        long currentTimeMillis = System.currentTimeMillis();
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.AsrCloudVadEnd = currentTimeMillis;
            long j = systemTimeStamp.AsrAudioEnd;
        }
    }

    public void compareSetAsrFirstPartialMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null && systemTimeStamp.AsrFirstResultPartial == 0) {
            this.timeStampSystem.AsrFirstResultPartial = System.currentTimeMillis();
        }
    }

    public void compareSetAsrResultPartialMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null && systemTimeStamp.AsrResultPartial == 0) {
            this.timeStampSystem.AsrResultPartial = System.currentTimeMillis();
        }
    }

    public void setAsrFinalResult(String str, Optional<Long> optional, Optional<Long> optional2) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            this.requestAsr = str;
            systemTimeStamp.AsrResultFinal = System.currentTimeMillis();
            this.timeStampSystem.AsrBeginOffset = 0L;
            if (optional != null && optional.isPresent()) {
                this.timeStampSystem.AsrBeginOffset = optional.get().longValue();
            }
            this.timeStampSystem.AsrEndOffset = 0L;
            if (optional2 != null && optional2.isPresent()) {
                this.timeStampSystem.AsrEndOffset = optional2.get().longValue();
            }
            if (this.timeStampSystem.AsrCloudVadEnd == 0) {
                SystemTimeStamp systemTimeStamp2 = this.timeStampSystem;
                systemTimeStamp2.AsrCloudVadEnd = systemTimeStamp2.AsrResultFinal;
            }
        }
    }

    public void compareSetTtsFirstPacketMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null && systemTimeStamp.TtsStreamStart == 0) {
            this.timeStampSystem.TtsStreamStart = System.currentTimeMillis();
        }
    }

    public void setTtsEndPacketMs(long j) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.TtsStreamEnd = System.currentTimeMillis();
            this.timeStampSystem.TtsStreamBytes = j;
        }
    }

    public void setTtsPlayStartMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.TtsPlay = System.currentTimeMillis();
        }
    }

    public void setTtsPlayUrlStartMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.TtsUrlPlay = System.currentTimeMillis();
        }
        SystemTimeStamp systemTimeStamp2 = this.timeStampSystem;
        if (systemTimeStamp2 != null && systemTimeStamp2.TtsPlay == 0) {
            this.timeStampSystem.TtsPlay = System.currentTimeMillis();
        }
    }

    public void setTtsPlayStreamStartMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.TtsStreamPlay = System.currentTimeMillis();
        }
        SystemTimeStamp systemTimeStamp2 = this.timeStampSystem;
        if (systemTimeStamp2 != null && systemTimeStamp2.TtsPlay == 0) {
            this.timeStampSystem.TtsPlay = System.currentTimeMillis();
        }
    }

    public void setTtsDisplayStartMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.TtsDisplay = System.currentTimeMillis();
        }
    }

    public void setUiShowStartMs(String str) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.UiShow = System.currentTimeMillis();
            this.uiType = str;
        }
    }

    void setTimeOut() {
        this.isTimeOut = true;
    }

    public void setNlpTimeStamp(String str) {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            this.requestId = str;
            systemTimeStamp.NlpStartAnswer = System.currentTimeMillis();
        }
    }

    public void setNlpFinishMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.NlpFinishAnswer = System.currentTimeMillis();
        }
    }

    public void setSpeechSpeakWithUrlMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.SpeakWithUrl = System.currentTimeMillis();
        }
    }

    public void setSpeechSpeakWithStreamMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.SpeakWithStream = System.currentTimeMillis();
        }
    }

    public void setDialogFinishMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.DialogFinish = System.currentTimeMillis();
        }
    }

    private void recordLatency() {
        long j;
        long j2;
        long j3;
        if (this.timeStampSystem.AsrTotalBytes <= 0 || this.timeStampSystem.AsrResultFinal <= this.timeStampSystem.AsrCloudVadStart) {
            L.speech.d("%s totalBytes=%s, start=%s, end=%s", LOG_TAG, Long.valueOf(this.timeStampSystem.AsrTotalBytes), Long.valueOf(this.timeStampSystem.AsrCloudVadStart), Long.valueOf(this.timeStampSystem.AsrResultFinal));
            j = 0;
        } else {
            j = this.timeStampSystem.AsrTotalBytes / (this.timeStampSystem.AsrResultFinal - this.timeStampSystem.AsrCloudVadStart);
        }
        if (this.timeStampSystem.TtsStreamBytes <= 0 || this.timeStampSystem.TtsStreamEnd <= this.timeStampSystem.TtsStreamStart) {
            L.speech.d("%s ttsBytes=%s, start=%s, end=%s", LOG_TAG, Long.valueOf(this.timeStampSystem.TtsStreamBytes), Long.valueOf(this.timeStampSystem.TtsStreamStart), Long.valueOf(this.timeStampSystem.TtsStreamEnd));
            j3 = 0;
            j2 = 0;
        } else {
            j2 = this.timeStampSystem.TtsStreamBytes / (this.timeStampSystem.TtsStreamEnd - this.timeStampSystem.TtsStreamStart);
            j3 = 0;
        }
        if (j > j3 && j2 > j3) {
            String format = String.format(Locale.getDefault(), "asr=%s, nlp=%s, tts=%s, play=%s, total=%s, up_rates=%s, down_rates=%s, real_time=%s", Long.valueOf(this.timeStampSystem.AsrResultFinal - this.timeStampSystem.AsrSendEnd), Long.valueOf(this.timeStampSystem.NlpStartAnswer - this.timeStampSystem.AsrResultFinal), Long.valueOf(this.timeStampSystem.TtsStreamStart - this.timeStampSystem.NlpStartAnswer), Long.valueOf(this.timeStampSystem.TtsPlay - this.timeStampSystem.TtsStreamStart), Long.valueOf(this.timeStampSystem.TtsPlay - this.timeStampSystem.AsrSendEnd), Long.valueOf(j), Long.valueOf(j2), Float.valueOf(((float) j) / 32.0f));
            L.speech.d("%s %s", LOG_TAG, format);
            StatPoints.recordPoint(StatPoints.Event.micolog_speech, StatPoints.SpeechKey.query_latency, format);
        }
    }

    public void setWakeupViewShowMs() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp != null) {
            systemTimeStamp.WakeupViewShow = System.currentTimeMillis();
        }
    }

    private long getUploadRatePerSecond() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp == null) {
            return 0L;
        }
        if (systemTimeStamp.AsrTotalBytes > 0 && this.timeStampSystem.AsrSendEnd > this.timeStampSystem.AsrSendStart) {
            return (this.timeStampSystem.AsrTotalBytes * 1000) / (this.timeStampSystem.AsrSendEnd - this.timeStampSystem.AsrSendStart);
        }
        L.speech.d("%s totalBytes=%s, start=%s, end=%s", LOG_TAG, Long.valueOf(this.timeStampSystem.AsrTotalBytes), Long.valueOf(this.timeStampSystem.AsrCloudVadStart), Long.valueOf(this.timeStampSystem.AsrResultFinal));
        return 0L;
    }

    private long getDownloadRatePerSecond() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp == null) {
            return 0L;
        }
        if (systemTimeStamp.TtsStreamBytes > 0 && this.timeStampSystem.TtsStreamEnd > this.timeStampSystem.TtsStreamStart) {
            return (this.timeStampSystem.TtsStreamBytes * 1000) / (this.timeStampSystem.TtsStreamEnd - this.timeStampSystem.TtsStreamStart);
        }
        L.speech.d("%s ttsBytes=%s, start=%s, end=%s", LOG_TAG, Long.valueOf(this.timeStampSystem.TtsStreamBytes), Long.valueOf(this.timeStampSystem.TtsStreamStart), Long.valueOf(this.timeStampSystem.TtsStreamEnd));
        return 0L;
    }

    public void printInfo() {
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        if (systemTimeStamp == null) {
            L.speech.w("%s printInfo.timeStampSystem=null", LOG_TAG);
            return;
        }
        this.timeStampAudioFile = new AudioFileTimeStamp(systemTimeStamp);
        this.zLatency = new Latency(this.timeStampSystem);
        Logger logger = L.latency;
        logger.json("[QueryLatency]: printInfo:" + Gsons.getGson().toJson(instance));
        recordLatency();
        this.timeStampAudioFile = null;
        this.timeStampSystem = null;
        this.zLatency = null;
    }

    /* loaded from: classes3.dex */
    public final class Latency {
        private long aLocalVad;
        private long bVadLocal2Cloud;
        private long cCloudVad;
        private long dAsrPartial2AsrFinal;
        private long dVad2AsrFinal;
        private long eAsrFinal2Nlp;
        private long fNlp2Tts;
        private long gTts2Play;
        private long total;

        Latency(BaseTimeStamp baseTimeStamp) {
            QueryLatency.this = r5;
            if (baseTimeStamp != null) {
                if (r5.localVad) {
                    this.aLocalVad = baseTimeStamp.AsrLocalVadEnd - baseTimeStamp.AsrAudioEnd;
                    this.bVadLocal2Cloud = baseTimeStamp.AsrCloudVadEnd - baseTimeStamp.AsrLocalVadEnd;
                } else {
                    this.cCloudVad = baseTimeStamp.AsrCloudVadEnd - baseTimeStamp.AsrAudioEnd;
                }
                this.dAsrPartial2AsrFinal = baseTimeStamp.AsrResultFinal - baseTimeStamp.AsrResultPartial;
                this.dVad2AsrFinal = baseTimeStamp.AsrResultFinal - baseTimeStamp.AsrCloudVadEnd;
                this.eAsrFinal2Nlp = baseTimeStamp.NlpStartAnswer - baseTimeStamp.AsrResultFinal;
                if (baseTimeStamp.TtsStreamStart > baseTimeStamp.NlpStartAnswer) {
                    this.fNlp2Tts = baseTimeStamp.TtsStreamStart - baseTimeStamp.NlpStartAnswer;
                }
                if (baseTimeStamp.TtsPlay > baseTimeStamp.TtsStreamStart) {
                    this.gTts2Play = baseTimeStamp.TtsPlay - baseTimeStamp.TtsStreamStart;
                }
                if (baseTimeStamp.TtsPlay > baseTimeStamp.AsrAudioEnd) {
                    this.total = baseTimeStamp.TtsPlay - baseTimeStamp.AsrAudioEnd;
                } else if (baseTimeStamp.NlpStartAnswer > baseTimeStamp.AsrAudioEnd) {
                    this.total = baseTimeStamp.NlpStartAnswer - baseTimeStamp.AsrAudioEnd;
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public final class SystemTimeStamp extends BaseTimeStamp {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SystemTimeStamp() {
            super();
            QueryLatency.this = r3;
            this.AsrSendEnd = 0L;
            this.AsrSendRealEnd = 0L;
            this.AsrTotalBytes = 0L;
            this.AsrResultPartial = 0L;
            this.AsrFirstResultPartial = 0L;
            this.WakeupCancel = 0L;
            this.AsrCloudVadEnd = Long.MAX_VALUE;
            this.AsrLocalVadEnd = Long.MAX_VALUE;
        }
    }

    /* loaded from: classes3.dex */
    public final class AudioFileTimeStamp extends BaseTimeStamp {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AudioFileTimeStamp(BaseTimeStamp baseTimeStamp) {
            super();
            QueryLatency.this = r7;
            if (baseTimeStamp != null) {
                long j = QueryLatency.instance.timeDiff;
                this.AsrAudioStart = baseTimeStamp.AsrAudioStart - j;
                this.AsrLocalVadStart = baseTimeStamp.AsrLocalVadStart - j;
                this.AsrCloudVadStart = baseTimeStamp.AsrCloudVadStart - j;
                this.AsrAudioEnd = baseTimeStamp.AsrAudioEnd - j;
                this.AsrLocalVadEnd = baseTimeStamp.AsrLocalVadEnd - j;
                this.AsrCloudVadEnd = baseTimeStamp.AsrCloudVadEnd - j;
                this.AsrResultFinal = baseTimeStamp.AsrResultFinal - j;
                this.NlpStartAnswer = baseTimeStamp.NlpStartAnswer - j;
                if (baseTimeStamp.TtsStreamStart > 0) {
                    this.TtsStreamStart = baseTimeStamp.TtsStreamStart - j;
                }
                if (baseTimeStamp.TtsPlay > 0) {
                    this.TtsPlay = baseTimeStamp.TtsPlay - j;
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public abstract class BaseTimeStamp {
        long AsrAudioEnd;
        long AsrAudioStart;
        long AsrBeginOffset;
        long AsrCancel;
        long AsrCloudVadEnd;
        long AsrCloudVadStart;
        long AsrEndOffset;
        long AsrFirstResultPartial;
        long AsrLocalVadEnd;
        long AsrLocalVadStart;
        long AsrRecognizeFinishSend;
        long AsrRecognizeSend;
        long AsrResultFinal;
        long AsrResultPartial;
        long AsrSendEnd;
        long AsrSendRealEnd;
        long AsrSendStart;
        long AsrTotalBytes;
        long DialogFinish;
        long FinishSpeakStream;
        long LaunchAppIntent;
        long NlpFinishAnswer;
        long NlpStartAnswer;
        long PauseWakeupSound;
        long PlayWakeupSound;
        long SpeakWithStream;
        long SpeakWithUrl;
        long TruncationNotification;
        long TtsDisplay;
        long TtsPlay;
        long TtsStreamBytes;
        long TtsStreamEnd;
        long TtsStreamPlay;
        long TtsStreamStart;
        long TtsUrlPlay;
        long UiShow;
        long WakeupCancel;
        long WakeupEventTranEnd;
        long WakeupLocal;
        long WakeupReal;
        long WakeupViewShow;
        long WuwAudioEnd;
        long WuwSendEnd;
        long WuwSendStart;

        private BaseTimeStamp() {
            QueryLatency.this = r1;
        }
    }

    public WrapTravelNode getWrapTravelFailureNode(int i, String str, String str2) {
        WrapTravelNode wrapTravelNode = new WrapTravelNode();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode createObjectNode = objectMapper.createObjectNode();
        createObjectNode.put("eventId", str2);
        ObjectNode createObjectNode2 = objectMapper.createObjectNode();
        createObjectNode2.put("type", "error");
        createObjectNode2.put(TraceConstants.Result.ERROR_MSG, i + "_" + str + "_" + str2);
        createObjectNode.set("result", createObjectNode2);
        wrapTravelNode.object = createObjectNode;
        MapNode mapNode = new MapNode();
        mapNode.requestId = str2;
        mapNode.putResult("type", "error");
        mapNode.putResult(TraceConstants.Result.ERROR_CODE, Integer.valueOf(i));
        mapNode.putResult(TraceConstants.Result.ERROR_MSG, str);
        wrapTravelNode.map = mapNode;
        L.latency.i("WrapTravelFailure Node dump message = %s", createObjectNode.toString());
        return wrapTravelNode;
    }

    public WrapTravelNode getWrapTravelNode() {
        long j;
        int i;
        ObjectNode objectNode;
        char c;
        int i2;
        SystemTimeStamp systemTimeStamp = this.timeStampSystem;
        String str = this.requestId;
        if (systemTimeStamp == null) {
            L.latency.i("%s wrapTravel().error invalid timastamp struct.", LOG_TAG);
            return null;
        } else if (TextUtils.isEmpty(str)) {
            L.latency.i("%s wrapTravel().error invalid dialogId.", LOG_TAG);
            return null;
        } else if (idSet.has(str)) {
            return null;
        } else {
            idSet.add(str);
            MapNode mapNode = new MapNode();
            StringBuilder sb = new StringBuilder();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode createObjectNode = objectMapper.createObjectNode();
            createObjectNode.put("eventId", str);
            mapNode.requestId = str;
            ObjectNode createObjectNode2 = objectMapper.createObjectNode();
            ObjectNode createObjectNode3 = objectMapper.createObjectNode();
            long j2 = systemTimeStamp.AsrCloudVadEnd;
            if (systemTimeStamp.AsrLocalVadEnd <= systemTimeStamp.AsrCloudVadEnd) {
                j = systemTimeStamp.AsrLocalVadEnd;
                i = 0;
            } else {
                j = j2;
                i = 1;
            }
            createObjectNode2.put("app.vad.type", i);
            createObjectNode2.put("app.wakeup.status", this.isPlaying ? "play" : "not_play");
            boolean nearbyWakeup = SystemSetting.getNearbyWakeup();
            createObjectNode2.put("app.wakeup.near.awaken.status", nearbyWakeup ? "on" : "off");
            boolean isEnabled = FullDuplexSpeechHelper.isEnabled();
            mapNode.putResult(TraceConstants.Result.DUPLEX, Integer.valueOf(isEnabled ? 1 : 0));
            createObjectNode2.put("type", StatConstants.BIND_SUCCESS);
            mapNode.putResult("type", StatConstants.BIND_SUCCESS);
            if (this.debug) {
                sb.append("eventId|");
                sb.append(str);
                sb.append("|VAD类型|");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("app.vad.type=");
                sb2.append(i == 1 ? "云端判停" : "本地判停");
                sb.append(sb2.toString());
                sb.append("|asr最终结果|");
                sb.append(TextUtils.isEmpty(this.requestAsr) ? "" : this.requestAsr);
                sb.append("|当前媒体播放|");
                StringBuilder sb3 = new StringBuilder();
                sb3.append("app.wakeup.status=");
                sb3.append(this.isPlaying ? "播放" : "未播放");
                sb.append(sb3.toString());
                sb.append("|就近唤醒开关|");
                sb.append(nearbyWakeup ? "on" : "off");
                sb.append("|全双工开关|");
                sb.append(isEnabled ? "on" : "off");
            }
            long uploadRatePerSecond = getUploadRatePerSecond();
            createObjectNode2.put("app.asr.upload.rate", uploadRatePerSecond);
            mapNode.putResult(TraceConstants.Result.ASR_UPLOAD_RATE, Integer.valueOf((int) uploadRatePerSecond));
            long downloadRatePerSecond = getDownloadRatePerSecond();
            createObjectNode2.put("app.tts.download.rate", downloadRatePerSecond);
            mapNode.putResult(TraceConstants.Result.TTS_UPLOAD_RATE, Integer.valueOf((int) downloadRatePerSecond));
            if (this.debug) {
                sb.append("|上传速率|");
                sb.append("app.asr.upload.rate=" + uploadRatePerSecond);
                sb.append("|下载速率|");
                sb.append("app.tts.download.rate=" + downloadRatePerSecond);
            }
            createObjectNode3.put("app.wakeup.frontend.algo.begin", systemTimeStamp.WuwAudioEnd);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.AFTER_TALK_XUE, Long.valueOf(systemTimeStamp.WuwAudioEnd));
            mapNode.put(TraceConstants.TimeStamp.Wakeup.FRONTEND_ALGO_BEGIN, Long.valueOf(systemTimeStamp.WuwAudioEnd));
            createObjectNode3.put("app.wakeup.frontend.algo.end", systemTimeStamp.WakeupLocal);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.FRONTEND_ALGO_END, Long.valueOf(systemTimeStamp.WakeupLocal));
            createObjectNode3.put("app.wakeup.near.awaken.begin", systemTimeStamp.WakeupLocal);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.NEAR_AWAKEN_BEGIN, Long.valueOf(systemTimeStamp.WakeupLocal));
            createObjectNode3.put("app.wakeup.near.awaken.end", systemTimeStamp.WakeupReal);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.NEAR_AWAKEN_END, Long.valueOf(systemTimeStamp.WakeupReal));
            createObjectNode3.put("app.wakeup.event.tran.begin", systemTimeStamp.WakeupReal);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.SEND_SUCC_EVENT, Long.valueOf(systemTimeStamp.WakeupReal));
            createObjectNode3.put("app.wakeup.event.tran.end", systemTimeStamp.WakeupEventTranEnd);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.RECV_SUCC_EVENT, Long.valueOf(systemTimeStamp.WakeupEventTranEnd));
            if (systemTimeStamp.WakeupEventTranEnd < systemTimeStamp.WuwAudioEnd) {
                L.latency.i("%s wrapTravel().error 唤醒应答延迟 illegal", LOG_TAG);
                return null;
            }
            if (this.debug) {
                WakeupDuration wakeupDuration = new WakeupDuration(systemTimeStamp.WuwAudioEnd, systemTimeStamp.WakeupEventTranEnd);
                objectNode = createObjectNode;
                wakeupDuration.algo = new Duration(systemTimeStamp.WuwAudioEnd, systemTimeStamp.WakeupLocal);
                wakeupDuration.awaken = new Duration(systemTimeStamp.WakeupLocal, systemTimeStamp.WakeupReal);
                wakeupDuration.trans = new Duration(systemTimeStamp.WakeupReal, systemTimeStamp.WakeupEventTranEnd);
                sb.append(wakeupDuration.dump("听觉-唤醒延迟"));
            } else {
                objectNode = createObjectNode;
            }
            createObjectNode3.put("app.wakeup.play.begin", systemTimeStamp.PlayWakeupSound);
            systemTimeStamp.PauseWakeupSound = systemTimeStamp.PlayWakeupSound + 300;
            createObjectNode3.put("app.wakeup.play.end", systemTimeStamp.PauseWakeupSound);
            if (this.debug) {
                sb.append(new PairDuration(systemTimeStamp.PlayWakeupSound, systemTimeStamp.PauseWakeupSound).dump("听觉-唤醒播放耗时"));
            }
            createObjectNode3.put("app.voice.wakeup", systemTimeStamp.WuwAudioEnd);
            createObjectNode3.put("app.wakeup.ui", systemTimeStamp.WakeupViewShow);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.BALL_APPEAR, Long.valueOf(systemTimeStamp.WakeupViewShow));
            if (this.debug) {
                sb.append(new WakeupUiDuration(systemTimeStamp.WuwAudioEnd, systemTimeStamp.WakeupViewShow).dump("视觉-唤醒延迟"));
            }
            createObjectNode3.put("app.send.wakeup.command", systemTimeStamp.WakeupReal);
            createObjectNode3.put("app.wakeup.say.hi", systemTimeStamp.PlayWakeupSound);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.SAY_HI, Long.valueOf(systemTimeStamp.PlayWakeupSound));
            createObjectNode3.put("app.send.first.wakeupvoice", systemTimeStamp.WuwSendStart);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.SEND_FIRST_BINARY, Long.valueOf(systemTimeStamp.WuwSendStart));
            createObjectNode3.put("app.send.last.wakeupvoice", systemTimeStamp.WuwSendEnd);
            mapNode.put(TraceConstants.TimeStamp.Wakeup.SEND_LAST_BINARY, Long.valueOf(systemTimeStamp.WuwSendEnd));
            if (this.debug) {
                sb.append("|就近唤醒判定|");
                sb.append("app.send.wakeup.command=" + sdf.format(new Date(systemTimeStamp.WakeupReal)));
                sb.append("|播放我在|");
                sb.append("app.wakeup.say.hi=" + sdf.format(new Date(systemTimeStamp.PlayWakeupSound)));
                sb.append("|创建第一个asr包|");
                sb.append("app.send.first.wakeupvoice=" + sdf.format(new Date(systemTimeStamp.WuwSendStart)));
                sb.append("|创建最后一个asr包|");
                sb.append("app.send.last.wakeupvoice=" + sdf.format(new Date(systemTimeStamp.WuwSendEnd)));
            }
            createObjectNode3.put("app.wakeup.open.mic", systemTimeStamp.WakeupReal);
            mapNode.put(TraceConstants.TimeStamp.ASR.OPEN_MIC, Long.valueOf(systemTimeStamp.WakeupReal));
            createObjectNode3.put("app.record.start", systemTimeStamp.AsrAudioStart);
            createObjectNode3.put("app.record.finish", systemTimeStamp.AsrAudioEnd);
            createObjectNode3.put("app.asr.recognize.send.start", systemTimeStamp.AsrRecognizeSend);
            mapNode.put(TraceConstants.TimeStamp.ASR.SEND_RECOGNIZER_RECOGNIZE, Long.valueOf(systemTimeStamp.AsrRecognizeSend));
            createObjectNode3.put("app.asr.recognize.send.finish", systemTimeStamp.AsrRecognizeFinishSend);
            mapNode.put(TraceConstants.TimeStamp.ASR.SEND_RECOGNIZER_RECOGNIZEFINISHED, Long.valueOf(systemTimeStamp.AsrRecognizeFinishSend));
            if (this.debug) {
                sb.append("|打开麦克风|");
                sb.append("app.wakeup.open.mic=" + sdf.format(new Date(systemTimeStamp.WakeupReal)));
                sb.append("|开始说话|");
                sb.append("app.record.start=" + sdf.format(new Date(systemTimeStamp.AsrAudioStart)));
                sb.append("|结束说话|");
                sb.append("app.record.finish=" + sdf.format(new Date(systemTimeStamp.AsrAudioEnd)));
                sb.append("|发送Recognize|");
                sb.append("app.asr.recognize.send.start=" + sdf.format(new Date(systemTimeStamp.AsrRecognizeSend)));
                sb.append("|发送RecognizeFinish|");
                sb.append("app.asr.recognize.send.finish=" + sdf.format(new Date(systemTimeStamp.AsrRecognizeFinishSend)));
            }
            createObjectNode3.put("app.asr.send.first", systemTimeStamp.AsrSendStart);
            mapNode.put(TraceConstants.TimeStamp.ASR.SEND_FIRST_BINARY, Long.valueOf(systemTimeStamp.AsrSendStart));
            createObjectNode3.put("app.asr.send.last", systemTimeStamp.AsrSendEnd);
            mapNode.put(TraceConstants.TimeStamp.ASR.SEND_LAST_BINARY, Long.valueOf(systemTimeStamp.AsrSendEnd));
            if (this.debug) {
                sb.append(new PairDuration(systemTimeStamp.AsrSendStart, systemTimeStamp.AsrSendEnd).dump("听觉-asr时长"));
            }
            createObjectNode3.put("app.asr.real.send.last", systemTimeStamp.AsrSendRealEnd);
            createObjectNode3.put("app.asr.recv.first.partial", systemTimeStamp.AsrFirstResultPartial);
            mapNode.put(TraceConstants.TimeStamp.ASR.RECV_FIRST_PARTIAL, Long.valueOf(systemTimeStamp.AsrFirstResultPartial));
            createObjectNode3.put("app.nlp.recv.start", systemTimeStamp.NlpStartAnswer);
            mapNode.put(TraceConstants.TimeStamp.NLP.RECV_STARTANSWER, Long.valueOf(systemTimeStamp.NlpStartAnswer));
            createObjectNode3.put("app.nlp.recv.finish", systemTimeStamp.NlpFinishAnswer);
            mapNode.put(TraceConstants.TimeStamp.NLP.RECV_FINISHANSWER, Long.valueOf(systemTimeStamp.NlpFinishAnswer));
            createObjectNode3.put("app.nlp.recv.speak.url", systemTimeStamp.SpeakWithUrl);
            mapNode.put(TraceConstants.TimeStamp.NLP.RECV_SPEAK_URL, Long.valueOf(systemTimeStamp.SpeakWithUrl));
            createObjectNode3.put("app.nlp.recv.speak.stream", systemTimeStamp.SpeakWithStream);
            mapNode.put(TraceConstants.TimeStamp.NLP.RECV_SPEAK_STREAM, Long.valueOf(systemTimeStamp.SpeakWithStream));
            createObjectNode3.put("app.tts.recv.first.audio", systemTimeStamp.TtsStreamStart);
            mapNode.put(TraceConstants.TimeStamp.TTS.RECV_FIRST_BINARY, Long.valueOf(systemTimeStamp.TtsStreamStart));
            createObjectNode3.put("app.exec.tts", systemTimeStamp.TtsPlay);
            if (systemTimeStamp.TtsUrlPlay > 0) {
                mapNode.put(TraceConstants.TimeStamp.TTS.START_PLAY_URL, Long.valueOf(systemTimeStamp.TtsUrlPlay));
            }
            if (systemTimeStamp.TtsStreamPlay > 0) {
                mapNode.put(TraceConstants.TimeStamp.TTS.START_PLAY_STREAM, Long.valueOf(systemTimeStamp.TtsStreamPlay));
            }
            mapNode.put(TraceConstants.TimeStamp.TTS.RECV_SYNTHESIZER_FINISHSPEAKSTREAM, Long.valueOf(systemTimeStamp.FinishSpeakStream));
            mapNode.put(TraceConstants.TimeStamp.TTS.DIALOG_FINISH, Long.valueOf(systemTimeStamp.DialogFinish));
            if (systemTimeStamp.TtsPlay < systemTimeStamp.AsrSendEnd) {
                L.latency.i("%s wrapTravel().error 指令响应耗时 illegal", LOG_TAG);
                return null;
            }
            if (this.debug) {
                ResponseDuration responseDuration = new ResponseDuration(systemTimeStamp.AsrSendEnd, systemTimeStamp.TtsPlay);
                responseDuration.sendasr = new Duration(systemTimeStamp.AsrSendEnd, systemTimeStamp.AsrSendRealEnd);
                responseDuration.asrnlp = new Duration(systemTimeStamp.AsrSendRealEnd, systemTimeStamp.NlpFinishAnswer);
                responseDuration.nlptts = new Duration(systemTimeStamp.NlpFinishAnswer, systemTimeStamp.TtsStreamStart);
                responseDuration.ttsplay = new Duration(systemTimeStamp.TtsStreamStart, systemTimeStamp.TtsPlay);
                sb.append(responseDuration.dump("听觉-指令响应耗时"));
            }
            createObjectNode3.put("app.asr.send.vad.begin", systemTimeStamp.AsrLocalVadStart);
            createObjectNode3.put("app.asr.recv.first.text", systemTimeStamp.AsrResultPartial);
            mapNode.put(TraceConstants.TimeStamp.ASR.RECV_FIRST_TEXT, Long.valueOf(systemTimeStamp.AsrResultPartial));
            if (this.debug) {
                sb.append(new PairDuration(systemTimeStamp.AsrLocalVadStart, systemTimeStamp.AsrResultPartial).dump("视觉-VAD首包文字上屏"));
            }
            createObjectNode3.put("app.asr.send.vad.end", j);
            createObjectNode3.put("app.asr.recv.final", systemTimeStamp.AsrResultFinal);
            mapNode.put(TraceConstants.TimeStamp.ASR.RECV_FINAL, Long.valueOf(systemTimeStamp.AsrResultFinal));
            mapNode.putResult(TraceConstants.TimeStamp.ASR.FINAL_SIZE, Integer.valueOf(TextUtils.isEmpty(this.requestAsr) ? 0 : this.requestAsr.length()));
            if (TextUtils.isEmpty(this.requestAsr)) {
                mapNode.putResult("type", "asr_no_result");
            }
            mapNode.put(TraceConstants.TimeStamp.ASR.START_TALKING_OFFSET, Long.valueOf(systemTimeStamp.AsrBeginOffset));
            mapNode.put(TraceConstants.TimeStamp.ASR.FINISH_TALKING_OFFSET, Long.valueOf(systemTimeStamp.AsrEndOffset));
            if (systemTimeStamp.WakeupEventTranEnd < systemTimeStamp.TruncationNotification) {
                mapNode.put(TraceConstants.TimeStamp.ASR.RECV_SYSTEM_TRUNCATIONNOTIFICATION, Long.valueOf(systemTimeStamp.TruncationNotification));
            }
            if (this.debug) {
                sb.append(new PairDuration(j, systemTimeStamp.AsrResultFinal).dump("视觉-VAD尾包文字上屏"));
            }
            if (systemTimeStamp.TtsDisplay > systemTimeStamp.AsrSendRealEnd) {
                createObjectNode3.put("app.tts.show.text", systemTimeStamp.TtsDisplay);
                if (this.debug) {
                    sb.append(new PairDuration(systemTimeStamp.AsrResultFinal, systemTimeStamp.TtsDisplay).dump("视觉-TTS回复上屏时延"));
                }
            }
            if (systemTimeStamp.UiShow > systemTimeStamp.AsrFirstResultPartial) {
                createObjectNode3.put("app.launchapp.intent", systemTimeStamp.LaunchAppIntent);
                mapNode.put(TraceConstants.TimeStamp.EXEC.SEND_LAUNCHAPP_INTENT, Long.valueOf(systemTimeStamp.LaunchAppIntent));
                createObjectNode3.put("app.exec.ui", systemTimeStamp.UiShow);
                mapNode.put(TraceConstants.TimeStamp.EXEC.UI_PAGE, Long.valueOf(systemTimeStamp.UiShow));
                createObjectNode2.put("app.ui.type", this.uiType);
                if (this.debug) {
                    sb.append("|LaunchApp发送intent|");
                    sb.append("app.launchapp.intent=" + sdf.format(new Date(systemTimeStamp.LaunchAppIntent)));
                    sb.append("|开始显示UI|");
                    sb.append("app.exec.ui=" + sdf.format(new Date(systemTimeStamp.UiShow)));
                    sb.append("|UI类型|");
                    sb.append("app.ui.type=" + this.uiType);
                }
            }
            objectNode.set("timestamps", createObjectNode3);
            objectNode.set("result", createObjectNode2);
            if (systemTimeStamp.WakeupCancel > 0) {
                mapNode.putResult("type", "cancel");
            }
            if (systemTimeStamp.WakeupEventTranEnd < systemTimeStamp.AsrCancel) {
                mapNode.putResult("type", "cancel");
            }
            if (this.cancel) {
                L.latency.i("%s wrapTravel().error has cancel.", LOG_TAG);
                mapNode.putResult("type", "cancel");
            }
            WrapTravelNode wrapTravelNode = new WrapTravelNode();
            if (!this.cancel) {
                wrapTravelNode.object = objectNode;
                if (!this.debug) {
                    i2 = 1;
                    c = 0;
                    L.latency.i("WrapTravel Node dump message = %s", objectNode.toString());
                } else {
                    i2 = 1;
                    c = 0;
                    L.latency.i("WrapTravel Node dump message = %s", sb.toString());
                }
            } else {
                i2 = 1;
                c = 0;
            }
            wrapTravelNode.map = mapNode;
            if (this.debug) {
                Logger logger = L.latency;
                Object[] objArr = new Object[i2];
                objArr[c] = mapNode.dump();
                logger.i("Map Node dump = %s", objArr);
            }
            return wrapTravelNode;
        }
    }

    /* loaded from: classes3.dex */
    public static class EventIdSet {
        private static final Object PRESENT = new Object();
        Map<String, Object> fifoCache;
        final int sizeLimit;

        public EventIdSet(int i) {
            this.sizeLimit = i;
            if (this.fifoCache == null) {
                this.fifoCache = new LinkedHashMap<String, Object>(this.sizeLimit, 0.75f, false) { // from class: com.xiaomi.micolauncher.common.speech.utils.QueryLatency.EventIdSet.1
                    @Override // java.util.LinkedHashMap
                    protected boolean removeEldestEntry(Map.Entry<String, Object> entry) {
                        return size() > EventIdSet.this.sizeLimit;
                    }
                };
            }
        }

        public boolean has(String str) {
            return (str == null || this.fifoCache.get(str) == null) ? false : true;
        }

        public void add(String str) {
            if (str != null) {
                this.fifoCache.put(str, PRESENT);
            }
        }

        public String dump() {
            StringBuilder sb = new StringBuilder();
            synchronized (this.fifoCache) {
                Iterator<Map.Entry<String, Object>> it = this.fifoCache.entrySet().iterator();
                while (it.hasNext()) {
                    sb.append("|" + it.next().getKey());
                }
            }
            return sb.toString();
        }
    }

    /* loaded from: classes3.dex */
    public class Duration {
        long end;
        long start;

        public Duration(long j, long j2) {
            QueryLatency.this = r1;
            this.start = j;
            this.end = j2;
        }
    }

    /* loaded from: classes3.dex */
    public class WakeupDuration extends Duration {
        Duration algo;
        Duration awaken;
        Duration trans;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WakeupDuration(long j, long j2) {
            super(j, j2);
            QueryLatency.this = r1;
        }

        String dump(String str) {
            return str + "[start|" + QueryLatency.sdf.format(new Date(this.start)) + "|end|" + QueryLatency.sdf.format(new Date(this.end)) + "|algo|" + (this.algo.end - this.algo.start) + "|awaken|" + (this.awaken.end - this.awaken.start) + "|trans|" + (this.trans.end - this.trans.start) + "|full|" + (this.end - this.start) + "]";
        }
    }

    /* loaded from: classes3.dex */
    public class WakeupUiDuration extends Duration {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WakeupUiDuration(long j, long j2) {
            super(j, j2);
            QueryLatency.this = r1;
        }

        String dump(String str) {
            return str + "[start|" + QueryLatency.sdf.format(new Date(this.start)) + "|end|" + QueryLatency.sdf.format(new Date(this.end)) + "|full|" + (this.end - this.start) + "]";
        }
    }

    /* loaded from: classes3.dex */
    public class ResponseDuration extends Duration {
        Duration asrnlp;
        Duration nlptts;
        Duration sendasr;
        Duration ttsplay;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ResponseDuration(long j, long j2) {
            super(j, j2);
            QueryLatency.this = r1;
        }

        String dump(String str) {
            return str + "[start|" + QueryLatency.sdf.format(new Date(this.start)) + "|end|" + QueryLatency.sdf.format(new Date(this.end)) + "|sendasr|" + (this.sendasr.end - this.sendasr.start) + "|asrnlp|" + (this.asrnlp.end - this.asrnlp.start) + "|nlptts|" + (this.nlptts.end - this.nlptts.start) + "|ttsplay|" + (this.ttsplay.end - this.ttsplay.start) + "|full|" + (this.end - this.start) + "]";
        }
    }

    /* loaded from: classes3.dex */
    public class PairDuration extends Duration {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PairDuration(long j, long j2) {
            super(j, j2);
            QueryLatency.this = r1;
        }

        String dump(String str) {
            return str + "[start|" + QueryLatency.sdf.format(new Date(this.start)) + "|end|" + QueryLatency.sdf.format(new Date(this.end)) + "|full|" + (this.end - this.start) + "]";
        }
    }
}
