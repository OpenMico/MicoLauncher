package com.xiaomi.infra.galaxy.fds.android.model;

import org.seamless.xhtml.XHTMLElement;

/* loaded from: classes3.dex */
public class ThumbParam extends UserParam {
    public ThumbParam(int i, int i2) {
        this.params.put("thumb", "1");
        this.params.put("w", Integer.toString(i));
        this.params.put(XHTMLElement.XPATH_PREFIX, Integer.toString(i2));
    }
}
