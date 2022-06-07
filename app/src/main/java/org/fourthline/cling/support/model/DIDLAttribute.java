package org.fourthline.cling.support.model;

/* loaded from: classes5.dex */
public class DIDLAttribute {
    private String namespaceURI;
    private String prefix;
    private String value;

    public DIDLAttribute(String str, String str2, String str3) {
        this.namespaceURI = str;
        this.prefix = str2;
        this.value = str3;
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getValue() {
        return this.value;
    }
}
