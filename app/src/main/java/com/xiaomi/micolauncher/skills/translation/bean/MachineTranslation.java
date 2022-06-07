package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.Template;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class MachineTranslation implements Serializable {
    @SerializedName("ph_symbol")
    private String phSymbol;
    @SerializedName("trans_text")
    private String transText;
    @SerializedName("trans_text_audio_url")
    private String transTextAudioUrl;
    @SerializedName("word_meaning")
    private String wordMeaning;

    public MachineTranslation(Template.MachineTranslation machineTranslation) {
        this.transText = machineTranslation.getTransText();
        this.transTextAudioUrl = machineTranslation.getTransTextAudioUrl();
        this.wordMeaning = machineTranslation.getWordMeaning().isPresent() ? machineTranslation.getWordMeaning().get() : "";
        this.phSymbol = machineTranslation.getPhSymbol().isPresent() ? machineTranslation.getPhSymbol().get() : "";
    }

    public String getTransText() {
        return this.transText;
    }

    public String getTransTextAudioUrl() {
        return this.transTextAudioUrl;
    }

    public String getWordMeaning() {
        return this.wordMeaning;
    }

    public String getPhSymbol() {
        return this.phSymbol;
    }

    public void setWordMeaning(String str) {
        this.wordMeaning = str;
    }
}
