package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class PartMeaning implements Serializable {
    @SerializedName("meaning")
    private String meaning;
    @SerializedName("speech_part")
    private String speechPart;

    public PartMeaning(FullScreenTemplate.PartMeaning partMeaning) {
        this.speechPart = partMeaning.getSpeechPart().isPresent() ? partMeaning.getSpeechPart().get() : "";
        this.meaning = partMeaning.getMeaning().isPresent() ? partMeaning.getMeaning().get() : "";
    }

    public String getSpeechPart() {
        return this.speechPart;
    }

    public String getMeaning() {
        return this.meaning;
    }
}
