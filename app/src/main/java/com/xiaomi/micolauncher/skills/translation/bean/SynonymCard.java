package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.Template;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SynonymCard implements Serializable {
    @SerializedName("synonym_words")
    private List<String> synonymWords;
    @SerializedName(alternate = {"h5_translations"}, value = "translations")
    private List<MachineTranslation> translations;

    public SynonymCard(Template.SynonymCardV2 synonymCardV2) {
        if (synonymCardV2.getTranslations().isPresent()) {
            ArrayList arrayList = new ArrayList();
            for (Template.MachineTranslation machineTranslation : synonymCardV2.getTranslations().get()) {
                arrayList.add(new MachineTranslation(machineTranslation));
            }
            this.translations = arrayList;
        }
        if (synonymCardV2.getSynonymWords().isPresent()) {
            this.synonymWords = synonymCardV2.getSynonymWords().get();
        }
    }

    public List<MachineTranslation> getTranslations() {
        return this.translations;
    }

    public List<String> getSynonymWords() {
        return this.synonymWords;
    }
}
