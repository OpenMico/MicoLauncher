package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PartSimple implements Serializable {
    @SerializedName("meanings")
    private List<PartMeaning> meanings;
    @SerializedName("phonetic_symbols")
    private List<PhoneticSymbol> phoneticSymbols;
    @SerializedName("word")
    private String word;
    @SerializedName("word_tags")
    private List<String> wordTags;

    public PartSimple(FullScreenTemplate.PartSimple partSimple) {
        this.word = partSimple.getWord();
        if (partSimple.getPhoneticSymbols().isPresent()) {
            ArrayList arrayList = new ArrayList();
            for (FullScreenTemplate.PhoneticSymbol phoneticSymbol : partSimple.getPhoneticSymbols().get()) {
                arrayList.add(new PhoneticSymbol(phoneticSymbol));
            }
            this.phoneticSymbols = arrayList;
        }
        if (partSimple.getMeanings().isPresent()) {
            ArrayList arrayList2 = new ArrayList();
            for (FullScreenTemplate.PartMeaning partMeaning : partSimple.getMeanings().get()) {
                arrayList2.add(new PartMeaning(partMeaning));
            }
            this.meanings = arrayList2;
        }
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String str) {
        this.word = str;
    }

    public List<PhoneticSymbol> getPhoneticSymbols() {
        return this.phoneticSymbols;
    }

    public List<PartMeaning> getMeanings() {
        return this.meanings;
    }

    public List<String> getWordTags() {
        return this.wordTags;
    }
}
