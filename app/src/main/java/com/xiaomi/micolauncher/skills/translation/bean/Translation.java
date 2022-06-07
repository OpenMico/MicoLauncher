package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.Template;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class Translation implements Serializable {
    @SerializedName("dest_language")
    private String destLanguage;
    @SerializedName("dest_text")
    private String destText;
    @SerializedName("dictionary")
    private DictionaryTranslation dictionary;
    @SerializedName("machine")
    private MachineTranslation machine;
    @SerializedName("src_language")
    private String srcLanguage;
    @SerializedName("src_text")
    private String srcText;
    @SerializedName("type")
    private String type;

    public Translation(Template.Translation translation) {
        this.srcText = translation.getSrcText();
        this.destText = translation.getDestText();
        this.destLanguage = translation.getDestLanguage().isPresent() ? translation.getDestLanguage().get() : "";
        this.srcLanguage = translation.getSrcLanguage().isPresent() ? translation.getSrcLanguage().get() : "";
        this.type = translation.getType().isPresent() ? translation.getType().get().name() : "";
        MachineTranslation machineTranslation = null;
        this.dictionary = translation.getDictionary().isPresent() ? new DictionaryTranslation(translation.getDictionary().get()) : null;
        this.machine = translation.getMachine().isPresent() ? new MachineTranslation(translation.getMachine().get()) : machineTranslation;
    }

    public String getSrcText() {
        return this.srcText;
    }

    public String getDestLanguage() {
        return this.destLanguage;
    }

    public String getDestText() {
        return this.destText;
    }

    public String getSrcLanguage() {
        return this.srcLanguage;
    }

    public String getType() {
        return this.type;
    }

    public DictionaryTranslation getDictionary() {
        return this.dictionary;
    }

    public MachineTranslation getMachine() {
        return this.machine;
    }
}
