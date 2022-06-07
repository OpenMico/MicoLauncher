package com.xiaomi.micolauncher.skills.translation.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class PhoneticSymbol implements Serializable {
    @SerializedName("ph_symbol")
    private String phSymbol;
    @SerializedName("ph_type")
    private String phType;
    @SerializedName("ph_url")
    private String phUrl;

    public PhoneticSymbol(FullScreenTemplate.PhoneticSymbol phoneticSymbol) {
        this.phSymbol = phoneticSymbol.getPhSymbol().isPresent() ? phoneticSymbol.getPhSymbol().get() : "";
        this.phUrl = phoneticSymbol.getPhUrl().isPresent() ? phoneticSymbol.getPhUrl().get() : "";
        this.phType = phoneticSymbol.getPhType().isPresent() ? phoneticSymbol.getPhType().get().name() : "";
    }

    public String getPhSymbol() {
        return this.phSymbol;
    }

    public String getPhUrl() {
        return this.phUrl;
    }

    public String getPhType() {
        return this.phType;
    }

    public boolean isBritish() {
        return "BRITISH".equals(this.phType);
    }

    public boolean isAmerican() {
        return "AMERICAN".equals(this.phType);
    }

    public void setPhType(String str) {
        this.phType = str;
    }
}
