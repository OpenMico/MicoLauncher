package org.apache.commons.codec.language.bm;

/* loaded from: classes5.dex */
public enum NameType {
    ASHKENAZI("ash"),
    GENERIC("gen"),
    SEPHARDIC("sep");
    
    private final String name;

    NameType(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
