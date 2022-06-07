package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class SynonymDetail implements Serializable {
    @SerializedName("word_meaning")
    private String wordMeaning;
    @SerializedName("words")
    private List<String> words;

    public SynonymDetail(FullScreenTemplate.SynonymDetail synonymDetail) {
        this.wordMeaning = synonymDetail.getWordMeaning().isPresent() ? synonymDetail.getWordMeaning().get() : "";
        if (synonymDetail.getWords().isPresent()) {
            this.words = synonymDetail.getWords().get();
        }
    }

    public List<String> getWords() {
        return this.words;
    }

    public void setWords(List<String> list) {
        this.words = list;
    }

    public String getWordMeaning() {
        return this.wordMeaning;
    }

    public void setWordMeaning(String str) {
        this.wordMeaning = str;
    }
}
