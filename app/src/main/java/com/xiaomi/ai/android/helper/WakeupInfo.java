package com.xiaomi.ai.android.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umeng.analytics.pro.c;

@JsonInclude(JsonInclude.Include.NON_NULL)
/* loaded from: classes2.dex */
public class WakeupInfo {
    @JsonProperty("acoustic_info")
    public AcousticInfo acousticInfo;
    @JsonProperty("audio_info")
    public String audioInfo;
    @JsonProperty("audio_meta")
    public AudioMeta audioMeta;
    public Device device;
    @JsonProperty("device_id")
    public String deviceId;
    @JsonProperty("pre_request_id")
    public String preRequestId;
    @JsonProperty("pre_wakeup_time_interval")
    public long preWakeupTimeInterval;
    @JsonProperty("request_id")
    public String requestId;
    @JsonProperty("user_info")
    public UserInfo userInfo;
    @JsonProperty("wakeup_audio")
    public String wakeupAudio;
    @JsonProperty("wakeup_type")
    public WakeupType wakeupType;
    @JsonProperty("wakeup_vendor")
    public String wakeupVendor;
    @JsonProperty("wakeup_word")
    public String wakeupWord;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    /* loaded from: classes2.dex */
    public static class AcousticInfo {
        @JsonProperty("group_id")
        public String groupId;
        @JsonProperty(c.K)
        public String groupInfo;
        @JsonProperty("wakeup_type")
        public String wakeupType;
        public String wuwid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    /* loaded from: classes2.dex */
    public static class AudioMeta {
        public int channel;
        public String codec;
        public int rate;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    /* loaded from: classes2.dex */
    public static class Device {
        @JsonProperty("bind_id")
        public String bindId;
        @JsonProperty("feature_id")
        public int featureId;
        public String ip;
        public double latitude;
        @JsonProperty("license_provider")
        public int licenseProvider;
        public double longitude;
        @JsonProperty("miot_version")
        public String miotVersion;
        public String model;
        public String network;
        @JsonProperty("platform_id")
        public int platformId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    /* loaded from: classes2.dex */
    public static class UserInfo {
        public int age;
        public String gender;
        public String id;
        @JsonProperty("id_type")
        public String idType;
        public String ip;
    }

    /* loaded from: classes2.dex */
    public enum WakeupType {
        WAKEUP_REAL,
        WAKEUP_SUSP,
        WAKEUP_SUSP_HARD
    }
}
