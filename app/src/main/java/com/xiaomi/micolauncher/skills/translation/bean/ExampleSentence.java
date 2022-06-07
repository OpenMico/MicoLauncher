package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class ExampleSentence implements Serializable {
    @SerializedName(ServerSetting.SERVER_CN)
    private String cnSentence;
    @SerializedName("en")
    private String enSentence;
    @SerializedName("tts_url")
    private String ttsUrl;

    public ExampleSentence(FullScreenTemplate.ExampleSentence exampleSentence) {
        this.ttsUrl = exampleSentence.getTtsUrl().isPresent() ? exampleSentence.getTtsUrl().get() : "";
        this.enSentence = exampleSentence.getEn().isPresent() ? exampleSentence.getEn().get() : "";
        this.cnSentence = exampleSentence.getCn().isPresent() ? exampleSentence.getCn().get() : "";
    }

    public String getTtsUrl() {
        return this.ttsUrl;
    }

    public void setTtsUrl(String str) {
        this.ttsUrl = str;
    }

    public String getEnSentence() {
        return this.enSentence;
    }

    public void setEnSentence(String str) {
        this.enSentence = str;
    }

    public String getCnSentence() {
        return this.cnSentence;
    }

    public void setCnSentence(String str) {
        this.cnSentence = str;
    }
}
