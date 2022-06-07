package com.google.android.exoplayer2.text.span;

import android.text.Spannable;

/* loaded from: classes2.dex */
public final class SpanUtil {
    public static void addOrReplaceSpan(Spannable spannable, Object obj, int i, int i2, int i3) {
        Object[] spans = spannable.getSpans(i, i2, obj.getClass());
        for (Object obj2 : spans) {
            if (spannable.getSpanStart(obj2) == i && spannable.getSpanEnd(obj2) == i2 && spannable.getSpanFlags(obj2) == i3) {
                spannable.removeSpan(obj2);
            }
        }
        spannable.setSpan(obj, i, i2, i3);
    }

    private SpanUtil() {
    }
}