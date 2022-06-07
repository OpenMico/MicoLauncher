package com.xiaomi.micolauncher.api.service;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.onetrack.api.b;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface SpeakerService {
    public static final String API_PREVIEW4TEST_URL = "https://staging.speaker.xiaomi.com";
    public static final String API_PREVIEW_URL = "https://preview.speaker.xiaomi.com";
    public static final String API_PRODUCTION_URL = "https://api.speaker.xiaomi.com";

    /* loaded from: classes3.dex */
    public static class SpeechTTS {
        @SerializedName("code")
        public int code;
        @SerializedName("msg")
        public String msg;
        @SerializedName("request_id")
        public String request_id;
        @SerializedName(b.I)
        public List<String> ttsUrlList;
    }

    @ResponseHandler
    @GET("/speaker/api/common/config")
    Observable<Config> getConfig();

    @ResponseHandler
    @GET("/speaker/api/common/tts")
    Observable<SpeechTTS> text2tts(@Header("token") String str, @Query("appId") String str2, @Query("toSpeaks") List<String> list, @Query("lang") String str3);

    @Keep
    /* loaded from: classes3.dex */
    public static class Config {
        @SerializedName("interConnectionOff")
        public String interConnectionOff;

        public String toString() {
            return "Config{interConnectionOff='" + this.interConnectionOff + "'}";
        }
    }
}
