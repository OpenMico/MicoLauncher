package com.google.android.exoplayer2.text.ttml;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;
import kotlinx.coroutines.DebugKt;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class TextEmphasis {
    private static final Pattern d = Pattern.compile("\\s+");
    private static final ImmutableSet<String> e = ImmutableSet.of(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "none");
    private static final ImmutableSet<String> f = ImmutableSet.of("dot", "sesame", "circle");
    private static final ImmutableSet<String> g = ImmutableSet.of("filled", AbstractCircuitBreaker.PROPERTY_NAME);
    private static final ImmutableSet<String> h = ImmutableSet.of("after", "before", "outside");
    public final int a;
    public final int b;
    public final int c;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Position {
    }

    private TextEmphasis(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    @Nullable
    public static TextEmphasis a(@Nullable String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = Ascii.toLowerCase(str.trim());
        if (lowerCase.isEmpty()) {
            return null;
        }
        return a(ImmutableSet.copyOf(TextUtils.split(lowerCase, d)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:
        if (r8.equals(kotlinx.coroutines.DebugKt.DEBUG_PROPERTY_VALUE_AUTO) != false) goto L_0x0086;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.TextEmphasis a(com.google.common.collect.ImmutableSet<java.lang.String> r8) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TextEmphasis.a(com.google.common.collect.ImmutableSet):com.google.android.exoplayer2.text.ttml.TextEmphasis");
    }
}
