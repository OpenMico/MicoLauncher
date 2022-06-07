package com.google.common.net;

import com.google.common.annotations.GwtCompatible;
import com.google.common.escape.Escaper;

@GwtCompatible
/* loaded from: classes2.dex */
public final class UrlEscapers {
    private static final Escaper a = new PercentEscaper("-_.*", true);
    private static final Escaper b = new PercentEscaper("-._~!$'()*,;&=@:+", false);
    private static final Escaper c = new PercentEscaper("-._~!$'()*,;&=@:+/?", false);

    private UrlEscapers() {
    }

    public static Escaper urlFormParameterEscaper() {
        return a;
    }

    public static Escaper urlPathSegmentEscaper() {
        return b;
    }

    public static Escaper urlFragmentEscaper() {
        return c;
    }
}
