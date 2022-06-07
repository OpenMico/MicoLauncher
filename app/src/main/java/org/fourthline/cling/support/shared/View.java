package org.fourthline.cling.support.shared;

import java.awt.Component;

/* loaded from: classes5.dex */
public interface View<P> {
    Component asUIComponent();

    void setPresenter(P p);
}
