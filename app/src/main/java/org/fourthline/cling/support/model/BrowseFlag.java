package org.fourthline.cling.support.model;

/* loaded from: classes5.dex */
public enum BrowseFlag {
    METADATA("BrowseMetadata"),
    DIRECT_CHILDREN("BrowseDirectChildren");
    
    private String protocolString;

    BrowseFlag(String str) {
        this.protocolString = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.protocolString;
    }

    public static BrowseFlag valueOrNullOf(String str) {
        BrowseFlag[] values = values();
        for (BrowseFlag browseFlag : values) {
            if (browseFlag.toString().equals(str)) {
                return browseFlag;
            }
        }
        return null;
    }
}
