package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class SpeechWakeup {

    @NamespaceName(name = "WakeupStreamFinished", namespace = AIApiConstants.SpeechWakeup.NAME)
    /* loaded from: classes3.dex */
    public static class WakeupStreamFinished implements EventPayload {
    }

    /* loaded from: classes3.dex */
    public static class AcousticInfo {
        private Optional<String> group_id = Optional.empty();
        private Optional<String> group_info = Optional.empty();
        private Optional<String> wakeup_word_id = Optional.empty();
        private Optional<Long> sse_wakeup_ts = Optional.empty();
        private Optional<List<Float>> voiceprint_enroll_scores = Optional.empty();
        private Optional<String> wakeup_model_version = Optional.empty();

        public AcousticInfo setGroupId(String str) {
            this.group_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGroupId() {
            return this.group_id;
        }

        public AcousticInfo setGroupInfo(String str) {
            this.group_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGroupInfo() {
            return this.group_info;
        }

        public AcousticInfo setWakeupWordId(String str) {
            this.wakeup_word_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupWordId() {
            return this.wakeup_word_id;
        }

        public AcousticInfo setSseWakeupTs(long j) {
            this.sse_wakeup_ts = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getSseWakeupTs() {
            return this.sse_wakeup_ts;
        }

        public AcousticInfo setVoiceprintEnrollScores(List<Float> list) {
            this.voiceprint_enroll_scores = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Float>> getVoiceprintEnrollScores() {
            return this.voiceprint_enroll_scores;
        }

        public AcousticInfo setWakeupModelVersion(String str) {
            this.wakeup_model_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupModelVersion() {
            return this.wakeup_model_version;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioMeta {
        @Required
        private int channel;
        @Required
        private String codec;
        @Required
        private int duration_in_ms;
        @Required
        private int rate;
        @Required
        private String type;
        private Optional<Long> wakeup_time = Optional.empty();

        public AudioMeta() {
        }

        public AudioMeta(String str, int i, int i2, String str2, int i3) {
            this.codec = str;
            this.channel = i;
            this.rate = i2;
            this.type = str2;
            this.duration_in_ms = i3;
        }

        @Required
        public AudioMeta setCodec(String str) {
            this.codec = str;
            return this;
        }

        @Required
        public String getCodec() {
            return this.codec;
        }

        @Required
        public AudioMeta setChannel(int i) {
            this.channel = i;
            return this;
        }

        @Required
        public int getChannel() {
            return this.channel;
        }

        @Required
        public AudioMeta setRate(int i) {
            this.rate = i;
            return this;
        }

        @Required
        public int getRate() {
            return this.rate;
        }

        @Required
        public AudioMeta setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }

        @Required
        public AudioMeta setDurationInMs(int i) {
            this.duration_in_ms = i;
            return this;
        }

        @Required
        public int getDurationInMs() {
            return this.duration_in_ms;
        }

        public AudioMeta setWakeupTime(long j) {
            this.wakeup_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getWakeupTime() {
            return this.wakeup_time;
        }
    }

    @NamespaceName(name = "Wakeup", namespace = AIApiConstants.SpeechWakeup.NAME)
    /* loaded from: classes3.dex */
    public static class Wakeup implements EventPayload {
        @Required
        private AudioMeta audio_meta;
        @Required
        private WakeupInfo wakeup_info;
        private Optional<AcousticInfo> acoustic_info = Optional.empty();
        private Optional<Boolean> recognize_voice_print = Optional.empty();
        private Optional<Boolean> recognize_followed = Optional.empty();
        private Optional<Boolean> enable_natural_record_v2 = Optional.empty();
        private Optional<Boolean> enable_natural_record_v2_duplex = Optional.empty();

        public Wakeup() {
        }

        public Wakeup(WakeupInfo wakeupInfo, AudioMeta audioMeta) {
            this.wakeup_info = wakeupInfo;
            this.audio_meta = audioMeta;
        }

        @Required
        public Wakeup setWakeupInfo(WakeupInfo wakeupInfo) {
            this.wakeup_info = wakeupInfo;
            return this;
        }

        @Required
        public WakeupInfo getWakeupInfo() {
            return this.wakeup_info;
        }

        @Required
        public Wakeup setAudioMeta(AudioMeta audioMeta) {
            this.audio_meta = audioMeta;
            return this;
        }

        @Required
        public AudioMeta getAudioMeta() {
            return this.audio_meta;
        }

        public Wakeup setAcousticInfo(AcousticInfo acousticInfo) {
            this.acoustic_info = Optional.ofNullable(acousticInfo);
            return this;
        }

        public Optional<AcousticInfo> getAcousticInfo() {
            return this.acoustic_info;
        }

        public Wakeup setRecognizeVoicePrint(boolean z) {
            this.recognize_voice_print = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isRecognizeVoicePrint() {
            return this.recognize_voice_print;
        }

        public Wakeup setRecognizeFollowed(boolean z) {
            this.recognize_followed = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isRecognizeFollowed() {
            return this.recognize_followed;
        }

        public Wakeup setEnableNaturalRecordV2(boolean z) {
            this.enable_natural_record_v2 = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnableNaturalRecordV2() {
            return this.enable_natural_record_v2;
        }

        public Wakeup setEnableNaturalRecordV2Duplex(boolean z) {
            this.enable_natural_record_v2_duplex = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnableNaturalRecordV2Duplex() {
            return this.enable_natural_record_v2_duplex;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeupInfo {
        @Required
        private String type;
        @Required
        private String vendor;
        private Optional<String> word = Optional.empty();
        private Optional<Long> ts = Optional.empty();
        private Optional<String> last_request_id = Optional.empty();
        private Optional<Long> last_wakeup_time_interval = Optional.empty();

        public WakeupInfo() {
        }

        public WakeupInfo(String str, String str2) {
            this.vendor = str;
            this.type = str2;
        }

        @Required
        public WakeupInfo setVendor(String str) {
            this.vendor = str;
            return this;
        }

        @Required
        public String getVendor() {
            return this.vendor;
        }

        @Required
        public WakeupInfo setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }

        public WakeupInfo setWord(String str) {
            this.word = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWord() {
            return this.word;
        }

        public WakeupInfo setTs(long j) {
            this.ts = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getTs() {
            return this.ts;
        }

        public WakeupInfo setLastRequestId(String str) {
            this.last_request_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLastRequestId() {
            return this.last_request_id;
        }

        public WakeupInfo setLastWakeupTimeInterval(long j) {
            this.last_wakeup_time_interval = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLastWakeupTimeInterval() {
            return this.last_wakeup_time_interval;
        }
    }
}
