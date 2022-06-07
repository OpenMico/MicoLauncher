package com.xiaomi.ai.android.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
/* loaded from: classes2.dex */
public class AsrInfo {
    @JsonProperty("asr_format")
    public AsrFormat asrFormat;
    @JsonProperty("asr_record_audio")
    public String asrRecordAudio;
    @JsonProperty("asr_record_words")
    public String asrRecordWords;
    @JsonProperty("asr_vendor")
    public String asrVendor;
    public Device device;
    @JsonProperty("device_id")
    public String deviceId;
    public String eids;
    public String locale;
    @JsonProperty("request_id")
    public String requestId;
    public Session session;
    @JsonProperty("use_extend")
    public boolean useExtend;
    @JsonProperty("user_info")
    public UserInfo userInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    /* loaded from: classes2.dex */
    public static class AsrFormat {
        public int bits;
        public int channel;
        public String codec;
        public String lang;
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
    public static class Session {
        public String id;
        @JsonProperty("is_new")
        public boolean isNew;
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
}
