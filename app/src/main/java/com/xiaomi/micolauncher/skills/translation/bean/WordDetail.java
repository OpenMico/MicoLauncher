package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class WordDetail implements Serializable {
    @SerializedName("antonym")
    private List<Synonym> antonyms;
    @SerializedName("deformations")
    private List<WordDeformation> deformations;
    @SerializedName("parts")
    private PartSimple partSimple;
    @SerializedName("sentences")
    private List<ExampleSentence> sentences;
    @SerializedName("synonym")
    private List<Synonym> synonyms;

    public WordDetail(FullScreenTemplate.WordDetail wordDetail) {
        if (wordDetail.getParts().isPresent()) {
            this.partSimple = new PartSimple(wordDetail.getParts().get());
        }
        if (wordDetail.getDeformations().isPresent()) {
            ArrayList arrayList = new ArrayList();
            for (FullScreenTemplate.WordDeformation wordDeformation : wordDetail.getDeformations().get()) {
                arrayList.add(new WordDeformation(wordDeformation));
            }
            this.deformations = arrayList;
        }
        if (wordDetail.getSentences().isPresent()) {
            ArrayList arrayList2 = new ArrayList();
            for (FullScreenTemplate.ExampleSentence exampleSentence : wordDetail.getSentences().get()) {
                arrayList2.add(new ExampleSentence(exampleSentence));
            }
            this.sentences = arrayList2;
        }
        if (wordDetail.getSynonym().isPresent()) {
            ArrayList arrayList3 = new ArrayList();
            for (FullScreenTemplate.Synonym synonym : wordDetail.getSynonym().get()) {
                arrayList3.add(new Synonym(synonym));
            }
            this.synonyms = arrayList3;
        }
        if (wordDetail.getAntonym().isPresent()) {
            ArrayList arrayList4 = new ArrayList();
            for (FullScreenTemplate.Synonym synonym2 : wordDetail.getAntonym().get()) {
                arrayList4.add(new Synonym(synonym2));
            }
            this.antonyms = arrayList4;
        }
    }

    public PartSimple getPartSimple() {
        return this.partSimple;
    }

    public List<WordDeformation> getDeformations() {
        return this.deformations;
    }

    public List<ExampleSentence> getSentences() {
        return this.sentences;
    }

    public List<Synonym> getSynonyms() {
        return this.synonyms;
    }

    public List<Synonym> getAntonyms() {
        return this.antonyms;
    }
}
