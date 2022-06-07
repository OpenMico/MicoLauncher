package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Synonym implements Serializable {
    @SerializedName("details")
    private List<SynonymDetail> details;
    @SerializedName("speech_part")
    private String speechPart;

    public Synonym(FullScreenTemplate.Synonym synonym) {
        this.speechPart = synonym.getSpeechPart().isPresent() ? synonym.getSpeechPart().get() : "";
        if (synonym.getDetails().isPresent()) {
            ArrayList arrayList = new ArrayList();
            for (FullScreenTemplate.SynonymDetail synonymDetail : synonym.getDetails().get()) {
                arrayList.add(new SynonymDetail(synonymDetail));
            }
            this.details = arrayList;
        }
    }

    public String getSpeechPart() {
        return this.speechPart;
    }

    public void setSpeechPart(String str) {
        this.speechPart = str;
    }

    public List<SynonymDetail> getDetails() {
        return this.details;
    }

    public void setDetails(List<SynonymDetail> list) {
        this.details = list;
    }
}
