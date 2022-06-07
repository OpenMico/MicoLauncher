package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.Template;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class DictionaryTranslation implements Serializable {
    @SerializedName("part_simple")
    private PartSimple partSimple;
    @SerializedName("synonym_card")
    private SynonymCard synonymCard;
    @SerializedName("word_detail")
    private WordDetail wordDetail;
    @SerializedName("word_simple")
    private MachineTranslation wordSimple;

    public DictionaryTranslation() {
    }

    public DictionaryTranslation(Template.DictionaryTranslation dictionaryTranslation) {
        MachineTranslation machineTranslation = null;
        this.synonymCard = dictionaryTranslation.getSynonymCard().isPresent() ? new SynonymCard(dictionaryTranslation.getSynonymCard().get()) : null;
        this.partSimple = dictionaryTranslation.getPartSimple().isPresent() ? new PartSimple(dictionaryTranslation.getPartSimple().get()) : null;
        this.wordDetail = dictionaryTranslation.getWordDetail().isPresent() ? new WordDetail(dictionaryTranslation.getWordDetail().get()) : null;
        this.wordSimple = dictionaryTranslation.getWordSimple().isPresent() ? new MachineTranslation(dictionaryTranslation.getWordSimple().get()) : machineTranslation;
    }

    public SynonymCard getSynonymCard() {
        return this.synonymCard;
    }

    public PartSimple getPartSimple() {
        return this.partSimple;
    }

    public WordDetail getWordDetail() {
        return this.wordDetail;
    }

    public MachineTranslation getWordSimple() {
        return this.wordSimple;
    }

    public void setWordSimple(MachineTranslation machineTranslation) {
        this.wordSimple = machineTranslation;
    }
}
