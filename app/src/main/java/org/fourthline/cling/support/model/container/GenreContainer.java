package org.fourthline.cling.support.model.container;

import org.fourthline.cling.support.model.DIDLObject;

/* loaded from: classes5.dex */
public class GenreContainer extends Container {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.container.genre");

    public GenreContainer() {
        setClazz(CLASS);
    }

    public GenreContainer(Container container) {
        super(container);
    }

    public GenreContainer(String str, Container container, String str2, String str3, Integer num) {
        this(str, container.getId(), str2, str3, num);
    }

    public GenreContainer(String str, String str2, String str3, String str4, Integer num) {
        super(str, str2, str3, str4, CLASS, num);
    }
}
