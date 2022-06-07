package com.xiaomi.ai.api;

import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Network;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Settings {

    @NamespaceName(name = "SetAIShortcut", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class SetAIShortcut implements ContextPayload {
    }

    /* loaded from: classes3.dex */
    public enum PlayerSource {
        UNKNOWN(-1),
        AUTO(0),
        QQ_MUSIC(1),
        KW_MUSIC(2),
        XIAOMI_MUSIC(3),
        NETEASE_MUSIC(4),
        KUGOU_MUSIC(5);
        
        private int id;

        PlayerSource(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PlayerType {
        UNKNOWN(-1),
        AUDIO(0),
        VIDEO(1);
        
        private int id;

        PlayerType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SDKLanguage {
        JAVA(0),
        CPP(1),
        C(2);
        
        private int id;

        SDKLanguage(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TVLicense {
        UNKNOWN(-1),
        ICNTV(0),
        GITV(1);
        
        private int id;

        TVLicense(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TtsAudioType {
        UNKNOWN(-1),
        STREAM(0),
        URL(1);
        
        private int id;

        TtsAudioType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class AsrConfig {
        private Optional<Boolean> vad = Optional.empty();
        private Optional<String> lang = Optional.empty();
        private Optional<Boolean> partial_result = Optional.empty();
        private Optional<AudioFormat> format = Optional.empty();
        private Optional<AsrTuningParams> tuning_params = Optional.empty();
        private Optional<Boolean> smart_volume = Optional.empty();

        public AsrConfig setVad(boolean z) {
            this.vad = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isVad() {
            return this.vad;
        }

        public AsrConfig setLang(String str) {
            this.lang = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLang() {
            return this.lang;
        }

        public AsrConfig setPartialResult(boolean z) {
            this.partial_result = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPartialResult() {
            return this.partial_result;
        }

        public AsrConfig setFormat(AudioFormat audioFormat) {
            this.format = Optional.ofNullable(audioFormat);
            return this;
        }

        public Optional<AudioFormat> getFormat() {
            return this.format;
        }

        public AsrConfig setTuningParams(AsrTuningParams asrTuningParams) {
            this.tuning_params = Optional.ofNullable(asrTuningParams);
            return this;
        }

        public Optional<AsrTuningParams> getTuningParams() {
            return this.tuning_params;
        }

        public AsrConfig setSmartVolume(boolean z) {
            this.smart_volume = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSmartVolume() {
            return this.smart_volume;
        }
    }

    /* loaded from: classes3.dex */
    public static class AsrTuningParams {
        private Optional<String> vendor = Optional.empty();
        private Optional<Integer> max_audio_seconds = Optional.empty();
        private Optional<Boolean> enable_timeout = Optional.empty();

        public AsrTuningParams setVendor(String str) {
            this.vendor = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVendor() {
            return this.vendor;
        }

        public AsrTuningParams setMaxAudioSeconds(int i) {
            this.max_audio_seconds = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMaxAudioSeconds() {
            return this.max_audio_seconds;
        }

        public AsrTuningParams setEnableTimeout(boolean z) {
            this.enable_timeout = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnableTimeout() {
            return this.enable_timeout;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioFormat {
        private Optional<String> codec = Optional.empty();
        private Optional<Integer> bits = Optional.empty();
        private Optional<Integer> rate = Optional.empty();
        private Optional<Integer> channel = Optional.empty();
        private Optional<Integer> opus_frame_size = Optional.empty();

        public AudioFormat setCodec(String str) {
            this.codec = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCodec() {
            return this.codec;
        }

        public AudioFormat setBits(int i) {
            this.bits = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getBits() {
            return this.bits;
        }

        public AudioFormat setRate(int i) {
            this.rate = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getRate() {
            return this.rate;
        }

        public AudioFormat setChannel(int i) {
            this.channel = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getChannel() {
            return this.channel;
        }

        public AudioFormat setOpusFrameSize(int i) {
            this.opus_frame_size = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getOpusFrameSize() {
            return this.opus_frame_size;
        }
    }

    @NamespaceName(name = "AuthorizationUpdated", namespace = "Settings")
    @Deprecated
    /* loaded from: classes3.dex */
    public static class AuthorizationUpdated implements EventPayload {
        @Required
        private List<String> authorization;

        public AuthorizationUpdated() {
        }

        public AuthorizationUpdated(List<String> list) {
            this.authorization = list;
        }

        @Required
        public AuthorizationUpdated setAuthorization(List<String> list) {
            this.authorization = list;
            return this;
        }

        @Required
        public List<String> getAuthorization() {
            return this.authorization;
        }
    }

    /* loaded from: classes3.dex */
    public static class ClientInfo {
        private Optional<String> device_id = Optional.empty();
        private Optional<Double> longitude = Optional.empty();
        private Optional<Double> latitude = Optional.empty();
        private Optional<String> time_zone = Optional.empty();
        private Optional<Boolean> continuous_dialog = Optional.empty();
        private Optional<String> android_id = Optional.empty();
        private Optional<Network.NetworkType> network = Optional.empty();
        private Optional<String> engine_id = Optional.empty();
        private Optional<String> mode = Optional.empty();
        private Optional<String> udid = Optional.empty();
        private Optional<String> oaid = Optional.empty();
        private Optional<String> vaid = Optional.empty();
        private Optional<String> aaid = Optional.empty();
        private Optional<String> vid = Optional.empty();
        private Optional<String> pid = Optional.empty();
        private Optional<String> miot_did = Optional.empty();
        private Optional<Long> timestamp = Optional.empty();
        private Optional<String> bind_id = Optional.empty();
        private Optional<Long> capabilities_version = Optional.empty();
        private Optional<WakeUpWithVoiceSetting> wake_up_with_voice_setting = Optional.empty();
        private Optional<Boolean> gps_opened = Optional.empty();
        private Optional<String> sn = Optional.empty();
        private Optional<String> custom_did = Optional.empty();
        private Optional<Boolean> restrict_imei = Optional.empty();
        private Optional<String> model_id = Optional.empty();

        public ClientInfo setDeviceId(String str) {
            this.device_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceId() {
            return this.device_id;
        }

        public ClientInfo setLongitude(double d) {
            this.longitude = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getLongitude() {
            return this.longitude;
        }

        public ClientInfo setLatitude(double d) {
            this.latitude = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getLatitude() {
            return this.latitude;
        }

        public ClientInfo setTimeZone(String str) {
            this.time_zone = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTimeZone() {
            return this.time_zone;
        }

        public ClientInfo setContinuousDialog(boolean z) {
            this.continuous_dialog = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isContinuousDialog() {
            return this.continuous_dialog;
        }

        public ClientInfo setAndroidId(String str) {
            this.android_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidId() {
            return this.android_id;
        }

        public ClientInfo setNetwork(Network.NetworkType networkType) {
            this.network = Optional.ofNullable(networkType);
            return this;
        }

        public Optional<Network.NetworkType> getNetwork() {
            return this.network;
        }

        public ClientInfo setEngineId(String str) {
            this.engine_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEngineId() {
            return this.engine_id;
        }

        public ClientInfo setMode(String str) {
            this.mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMode() {
            return this.mode;
        }

        public ClientInfo setUdid(String str) {
            this.udid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUdid() {
            return this.udid;
        }

        public ClientInfo setOaid(String str) {
            this.oaid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOaid() {
            return this.oaid;
        }

        public ClientInfo setVaid(String str) {
            this.vaid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVaid() {
            return this.vaid;
        }

        public ClientInfo setAaid(String str) {
            this.aaid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAaid() {
            return this.aaid;
        }

        public ClientInfo setVid(String str) {
            this.vid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVid() {
            return this.vid;
        }

        public ClientInfo setPid(String str) {
            this.pid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPid() {
            return this.pid;
        }

        public ClientInfo setMiotDid(String str) {
            this.miot_did = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMiotDid() {
            return this.miot_did;
        }

        public ClientInfo setTimestamp(long j) {
            this.timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getTimestamp() {
            return this.timestamp;
        }

        public ClientInfo setBindId(String str) {
            this.bind_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBindId() {
            return this.bind_id;
        }

        public ClientInfo setCapabilitiesVersion(long j) {
            this.capabilities_version = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getCapabilitiesVersion() {
            return this.capabilities_version;
        }

        public ClientInfo setWakeUpWithVoiceSetting(WakeUpWithVoiceSetting wakeUpWithVoiceSetting) {
            this.wake_up_with_voice_setting = Optional.ofNullable(wakeUpWithVoiceSetting);
            return this;
        }

        public Optional<WakeUpWithVoiceSetting> getWakeUpWithVoiceSetting() {
            return this.wake_up_with_voice_setting;
        }

        public ClientInfo setGpsOpened(boolean z) {
            this.gps_opened = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isGpsOpened() {
            return this.gps_opened;
        }

        public ClientInfo setSn(String str) {
            this.sn = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSn() {
            return this.sn;
        }

        public ClientInfo setCustomDid(String str) {
            this.custom_did = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCustomDid() {
            return this.custom_did;
        }

        public ClientInfo setRestrictImei(boolean z) {
            this.restrict_imei = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isRestrictImei() {
            return this.restrict_imei;
        }

        public ClientInfo setModelId(String str) {
            this.model_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModelId() {
            return this.model_id;
        }
    }

    @NamespaceName(name = "ConnectionChallenge", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class ConnectionChallenge implements InstructionPayload {
        @Required
        private String challenge;
        private Optional<String> aes_token = Optional.empty();
        private Optional<Integer> token_expires_in = Optional.empty();

        public ConnectionChallenge() {
        }

        public ConnectionChallenge(String str) {
            this.challenge = str;
        }

        @Required
        public ConnectionChallenge setChallenge(String str) {
            this.challenge = str;
            return this;
        }

        @Required
        public String getChallenge() {
            return this.challenge;
        }

        public ConnectionChallenge setAesToken(String str) {
            this.aes_token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAesToken() {
            return this.aes_token;
        }

        public ConnectionChallenge setTokenExpiresIn(int i) {
            this.token_expires_in = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTokenExpiresIn() {
            return this.token_expires_in;
        }
    }

    @NamespaceName(name = "ConnectionChallengeAck", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class ConnectionChallengeAck implements EventPayload {
        @Required
        private String challenge_md5;

        public ConnectionChallengeAck() {
        }

        public ConnectionChallengeAck(String str) {
            this.challenge_md5 = str;
        }

        @Required
        public ConnectionChallengeAck setChallengeMd5(String str) {
            this.challenge_md5 = str;
            return this;
        }

        @Required
        public String getChallengeMd5() {
            return this.challenge_md5;
        }
    }

    @NamespaceName(name = "GlobalConfig", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class GlobalConfig implements EventPayload {
        private Optional<AsrConfig> asr = Optional.empty();
        private Optional<TtsConfig> tts = Optional.empty();
        private Optional<ClientInfo> client_info = Optional.empty();
        private Optional<PreAsrConfig> pre_asr = Optional.empty();
        private Optional<PushConfig> push = Optional.empty();
        private Optional<SDKConfig> sdk = Optional.empty();
        private Optional<LocaleConfig> locale = Optional.empty();

        public GlobalConfig setAsr(AsrConfig asrConfig) {
            this.asr = Optional.ofNullable(asrConfig);
            return this;
        }

        public Optional<AsrConfig> getAsr() {
            return this.asr;
        }

        public GlobalConfig setTts(TtsConfig ttsConfig) {
            this.tts = Optional.ofNullable(ttsConfig);
            return this;
        }

        public Optional<TtsConfig> getTts() {
            return this.tts;
        }

        public GlobalConfig setClientInfo(ClientInfo clientInfo) {
            this.client_info = Optional.ofNullable(clientInfo);
            return this;
        }

        public Optional<ClientInfo> getClientInfo() {
            return this.client_info;
        }

        public GlobalConfig setPreAsr(PreAsrConfig preAsrConfig) {
            this.pre_asr = Optional.ofNullable(preAsrConfig);
            return this;
        }

        public Optional<PreAsrConfig> getPreAsr() {
            return this.pre_asr;
        }

        public GlobalConfig setPush(PushConfig pushConfig) {
            this.push = Optional.ofNullable(pushConfig);
            return this;
        }

        public Optional<PushConfig> getPush() {
            return this.push;
        }

        public GlobalConfig setSdk(SDKConfig sDKConfig) {
            this.sdk = Optional.ofNullable(sDKConfig);
            return this;
        }

        public Optional<SDKConfig> getSdk() {
            return this.sdk;
        }

        public GlobalConfig setLocale(LocaleConfig localeConfig) {
            this.locale = Optional.ofNullable(localeConfig);
            return this;
        }

        public Optional<LocaleConfig> getLocale() {
            return this.locale;
        }
    }

    @NamespaceName(name = "GlobalConfigState", namespace = "Settings")
    @Deprecated
    /* loaded from: classes3.dex */
    public static class GlobalConfigState implements ContextPayload {
        private Optional<TtsConfig> tts = Optional.empty();

        public GlobalConfigState setTts(TtsConfig ttsConfig) {
            this.tts = Optional.ofNullable(ttsConfig);
            return this;
        }

        public Optional<TtsConfig> getTts() {
            return this.tts;
        }
    }

    @NamespaceName(name = "HeadersUpdated", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class HeadersUpdated implements EventPayload {
        private Optional<List<String>> authorization = Optional.empty();
        private Optional<String> user_agent = Optional.empty();

        public HeadersUpdated setAuthorization(List<String> list) {
            this.authorization = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getAuthorization() {
            return this.authorization;
        }

        public HeadersUpdated setUserAgent(String str) {
            this.user_agent = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUserAgent() {
            return this.user_agent;
        }
    }

    /* loaded from: classes3.dex */
    public static class LocaleConfig {
        @Required
        private List<String> langs;
        @Required
        private String location;
        private Optional<Common.LocaleRegion> region = Optional.empty();

        public LocaleConfig() {
        }

        public LocaleConfig(List<String> list, String str) {
            this.langs = list;
            this.location = str;
        }

        @Required
        public LocaleConfig setLangs(List<String> list) {
            this.langs = list;
            return this;
        }

        @Required
        public List<String> getLangs() {
            return this.langs;
        }

        @Required
        public LocaleConfig setLocation(String str) {
            this.location = str;
            return this;
        }

        @Required
        public String getLocation() {
            return this.location;
        }

        public LocaleConfig setRegion(Common.LocaleRegion localeRegion) {
            this.region = Optional.ofNullable(localeRegion);
            return this;
        }

        public Optional<Common.LocaleRegion> getRegion() {
            return this.region;
        }
    }

    /* loaded from: classes3.dex */
    public static class MIPushConfig {
        @Required
        private String reg_id;

        public MIPushConfig() {
        }

        public MIPushConfig(String str) {
            this.reg_id = str;
        }

        @Required
        public MIPushConfig setRegId(String str) {
            this.reg_id = str;
            return this;
        }

        @Required
        public String getRegId() {
            return this.reg_id;
        }
    }

    @NamespaceName(name = "PowerState", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class PowerState implements ContextPayload {
        @Required
        private boolean scheduled_shutdown;

        public PowerState() {
        }

        public PowerState(boolean z) {
            this.scheduled_shutdown = z;
        }

        @Required
        public PowerState setScheduledShutdown(boolean z) {
            this.scheduled_shutdown = z;
            return this;
        }

        @Required
        public boolean isScheduledShutdown() {
            return this.scheduled_shutdown;
        }
    }

    /* loaded from: classes3.dex */
    public static class PreAsrConfig {
        private Optional<Integer> track = Optional.empty();

        public PreAsrConfig setTrack(int i) {
            this.track = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTrack() {
            return this.track;
        }
    }

    /* loaded from: classes3.dex */
    public static class PushConfig {
        private Optional<MIPushConfig> mi = Optional.empty();
        private Optional<UMengPushConfig> umeng = Optional.empty();

        public PushConfig setMi(MIPushConfig mIPushConfig) {
            this.mi = Optional.ofNullable(mIPushConfig);
            return this;
        }

        public Optional<MIPushConfig> getMi() {
            return this.mi;
        }

        public PushConfig setUmeng(UMengPushConfig uMengPushConfig) {
            this.umeng = Optional.ofNullable(uMengPushConfig);
            return this;
        }

        public Optional<UMengPushConfig> getUmeng() {
            return this.umeng;
        }
    }

    /* loaded from: classes3.dex */
    public static class SDKConfig {
        @Required
        private SDKLanguage lang;
        @Required
        private int version;

        public SDKConfig() {
        }

        public SDKConfig(int i, SDKLanguage sDKLanguage) {
            this.version = i;
            this.lang = sDKLanguage;
        }

        @Required
        public SDKConfig setVersion(int i) {
            this.version = i;
            return this;
        }

        @Required
        public int getVersion() {
            return this.version;
        }

        @Required
        public SDKConfig setLang(SDKLanguage sDKLanguage) {
            this.lang = sDKLanguage;
            return this;
        }

        @Required
        public SDKLanguage getLang() {
            return this.lang;
        }
    }

    @NamespaceName(name = "SetPlayerProperty", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class SetPlayerProperty implements InstructionPayload {
        @Required
        private PlayerType player_type;
        private Optional<PlayerSource> source = Optional.empty();

        public SetPlayerProperty() {
        }

        public SetPlayerProperty(PlayerType playerType) {
            this.player_type = playerType;
        }

        @Required
        public SetPlayerProperty setPlayerType(PlayerType playerType) {
            this.player_type = playerType;
            return this;
        }

        @Required
        public PlayerType getPlayerType() {
            return this.player_type;
        }

        public SetPlayerProperty setSource(PlayerSource playerSource) {
            this.source = Optional.ofNullable(playerSource);
            return this;
        }

        public Optional<PlayerSource> getSource() {
            return this.source;
        }
    }

    @NamespaceName(name = "TVClientInfo", namespace = "Settings")
    /* loaded from: classes3.dex */
    public static class TVClientInfo implements ContextPayload {
        private Optional<TVLicense> license = Optional.empty();

        public TVClientInfo setLicense(TVLicense tVLicense) {
            this.license = Optional.ofNullable(tVLicense);
            return this;
        }

        public Optional<TVLicense> getLicense() {
            return this.license;
        }
    }

    /* loaded from: classes3.dex */
    public static class TtsConfig {
        private Optional<String> codec = Optional.empty();
        private Optional<String> lang = Optional.empty();
        private Optional<Integer> volume = Optional.empty();
        private Optional<TtsAudioType> audio_type = Optional.empty();
        private Optional<TtsTuningParams> tuning_params = Optional.empty();
        private Optional<String> vendor = Optional.empty();
        private Optional<String> speaker = Optional.empty();
        private Optional<SpeechSynthesizer.TTSEmotion> emotion = Optional.empty();

        public TtsConfig setCodec(String str) {
            this.codec = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCodec() {
            return this.codec;
        }

        public TtsConfig setLang(String str) {
            this.lang = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLang() {
            return this.lang;
        }

        public TtsConfig setVolume(int i) {
            this.volume = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVolume() {
            return this.volume;
        }

        public TtsConfig setAudioType(TtsAudioType ttsAudioType) {
            this.audio_type = Optional.ofNullable(ttsAudioType);
            return this;
        }

        public Optional<TtsAudioType> getAudioType() {
            return this.audio_type;
        }

        public TtsConfig setTuningParams(TtsTuningParams ttsTuningParams) {
            this.tuning_params = Optional.ofNullable(ttsTuningParams);
            return this;
        }

        public Optional<TtsTuningParams> getTuningParams() {
            return this.tuning_params;
        }

        public TtsConfig setVendor(String str) {
            this.vendor = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVendor() {
            return this.vendor;
        }

        public TtsConfig setSpeaker(String str) {
            this.speaker = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSpeaker() {
            return this.speaker;
        }

        public TtsConfig setEmotion(SpeechSynthesizer.TTSEmotion tTSEmotion) {
            this.emotion = Optional.ofNullable(tTSEmotion);
            return this;
        }

        public Optional<SpeechSynthesizer.TTSEmotion> getEmotion() {
            return this.emotion;
        }
    }

    /* loaded from: classes3.dex */
    public static class TtsTuningParams {
        private Optional<String> vendor = Optional.empty();
        private Optional<Integer> speed = Optional.empty();
        private Optional<Integer> tone = Optional.empty();
        private Optional<Integer> rate = Optional.empty();

        public TtsTuningParams setVendor(String str) {
            this.vendor = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVendor() {
            return this.vendor;
        }

        public TtsTuningParams setSpeed(int i) {
            this.speed = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSpeed() {
            return this.speed;
        }

        public TtsTuningParams setTone(int i) {
            this.tone = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTone() {
            return this.tone;
        }

        public TtsTuningParams setRate(int i) {
            this.rate = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getRate() {
            return this.rate;
        }
    }

    /* loaded from: classes3.dex */
    public static class UMengPushConfig {
        @Required
        private String device_token;

        public UMengPushConfig() {
        }

        public UMengPushConfig(String str) {
            this.device_token = str;
        }

        @Required
        public UMengPushConfig setDeviceToken(String str) {
            this.device_token = str;
            return this;
        }

        @Required
        public String getDeviceToken() {
            return this.device_token;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeUpWithVoiceSetting {
        @Required
        private boolean voice;
        @Required
        private boolean voiceprint;

        public WakeUpWithVoiceSetting() {
        }

        public WakeUpWithVoiceSetting(boolean z, boolean z2) {
            this.voice = z;
            this.voiceprint = z2;
        }

        @Required
        public WakeUpWithVoiceSetting setVoice(boolean z) {
            this.voice = z;
            return this;
        }

        @Required
        public boolean isVoice() {
            return this.voice;
        }

        @Required
        public WakeUpWithVoiceSetting setVoiceprint(boolean z) {
            this.voiceprint = z;
            return this;
        }

        @Required
        public boolean isVoiceprint() {
            return this.voiceprint;
        }
    }
}
