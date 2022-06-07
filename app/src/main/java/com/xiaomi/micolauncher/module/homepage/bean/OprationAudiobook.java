package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class OprationAudiobook {
    @SerializedName(AudiobookDataManager.FIRST_ITEM_ID)
    private String a;
    @SerializedName("itemList")
    private List<AudiobookContent> b;

    public String getFirstItemId() {
        return this.a;
    }

    public void setFirstItemId(String str) {
        this.a = str;
    }

    public List<AudiobookContent> getItemList() {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        return this.b;
    }

    public void putContent(AudiobookContent audiobookContent) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        this.b.add(audiobookContent);
    }

    public String toString() {
        return "OprationAudiobook{firstItemId='" + this.a + "', itemList=" + this.b + '}';
    }
}
