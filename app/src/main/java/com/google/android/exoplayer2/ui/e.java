package com.google.android.exoplayer2.ui;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.span.LanguageFeatureSpan;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Predicate;

/* compiled from: SubtitleViewUtils.java */
/* loaded from: classes2.dex */
final class e {
    public static float a(int i, float f, int i2, int i3) {
        if (f == -3.4028235E38f) {
            return -3.4028235E38f;
        }
        switch (i) {
            case 0:
                return f * i3;
            case 1:
                return f * i2;
            case 2:
                return f;
            default:
                return -3.4028235E38f;
        }
    }

    public static void a(Cue.Builder builder) {
        builder.clearWindowColor();
        if (builder.getText() instanceof Spanned) {
            if (!(builder.getText() instanceof Spannable)) {
                builder.setText(SpannableString.valueOf(builder.getText()));
            }
            a((Spannable) Assertions.checkNotNull(builder.getText()), $$Lambda$e$_nW7cWIGJktr_JlHUfcge6j2w1k.INSTANCE);
        }
        b(builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Object obj) {
        return !(obj instanceof LanguageFeatureSpan);
    }

    public static void b(Cue.Builder builder) {
        builder.setTextSize(-3.4028235E38f, Integer.MIN_VALUE);
        if (builder.getText() instanceof Spanned) {
            if (!(builder.getText() instanceof Spannable)) {
                builder.setText(SpannableString.valueOf(builder.getText()));
            }
            a((Spannable) Assertions.checkNotNull(builder.getText()), $$Lambda$e$l9iPHSLJcdfrRO21P6YzUooYJRI.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Object obj) {
        return (obj instanceof AbsoluteSizeSpan) || (obj instanceof RelativeSizeSpan);
    }

    private static void a(Spannable spannable, Predicate<Object> predicate) {
        Object[] spans = spannable.getSpans(0, spannable.length(), Object.class);
        for (Object obj : spans) {
            if (predicate.apply(obj)) {
                spannable.removeSpan(obj);
            }
        }
    }
}
