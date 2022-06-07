package org.fourthline.cling.support.model.container;

import org.fourthline.cling.support.model.DIDLObject;

/* loaded from: classes5.dex */
public class PersonContainer extends Container {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.container.person");

    public PersonContainer() {
        setClazz(CLASS);
    }

    public PersonContainer(Container container) {
        super(container);
    }

    public PersonContainer(String str, Container container, String str2, String str3, Integer num) {
        this(str, container.getId(), str2, str3, num);
    }

    public PersonContainer(String str, String str2, String str3, String str4, Integer num) {
        super(str, str2, str3, str4, CLASS, num);
    }

    public String getLanguage() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.LANGUAGE.class);
    }

    public PersonContainer setLanguage(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.LANGUAGE(str));
        return this;
    }
}
