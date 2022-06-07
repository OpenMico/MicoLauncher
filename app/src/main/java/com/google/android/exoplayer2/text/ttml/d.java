package com.google.android.exoplayer2.text.ttml;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.SpanUtil;
import com.google.android.exoplayer2.text.span.TextEmphasisSpan;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* compiled from: TtmlRenderUtil.java */
/* loaded from: classes2.dex */
final class d {
    @Nullable
    public static TtmlStyle a(@Nullable TtmlStyle ttmlStyle, @Nullable String[] strArr, Map<String, TtmlStyle> map) {
        int i = 0;
        if (ttmlStyle == null) {
            if (strArr == null) {
                return null;
            }
            if (strArr.length == 1) {
                return map.get(strArr[0]);
            }
            if (strArr.length > 1) {
                TtmlStyle ttmlStyle2 = new TtmlStyle();
                int length = strArr.length;
                while (i < length) {
                    ttmlStyle2.a(map.get(strArr[i]));
                    i++;
                }
                return ttmlStyle2;
            }
        } else if (strArr != null && strArr.length == 1) {
            return ttmlStyle.a(map.get(strArr[0]));
        } else {
            if (strArr != null && strArr.length > 1) {
                int length2 = strArr.length;
                while (i < length2) {
                    ttmlStyle.a(map.get(strArr[i]));
                    i++;
                }
                return ttmlStyle;
            }
        }
        return ttmlStyle;
    }

    public static void a(Spannable spannable, int i, int i2, TtmlStyle ttmlStyle, @Nullable b bVar, Map<String, TtmlStyle> map, int i3) {
        b a;
        TtmlStyle a2;
        int i4;
        int i5;
        if (ttmlStyle.a() != -1) {
            spannable.setSpan(new StyleSpan(ttmlStyle.a()), i, i2, 33);
        }
        if (ttmlStyle.b()) {
            spannable.setSpan(new StrikethroughSpan(), i, i2, 33);
        }
        if (ttmlStyle.c()) {
            spannable.setSpan(new UnderlineSpan(), i, i2, 33);
        }
        if (ttmlStyle.f()) {
            SpanUtil.addOrReplaceSpan(spannable, new ForegroundColorSpan(ttmlStyle.e()), i, i2, 33);
        }
        if (ttmlStyle.h()) {
            SpanUtil.addOrReplaceSpan(spannable, new BackgroundColorSpan(ttmlStyle.g()), i, i2, 33);
        }
        if (ttmlStyle.d() != null) {
            SpanUtil.addOrReplaceSpan(spannable, new TypefaceSpan(ttmlStyle.d()), i, i2, 33);
        }
        if (ttmlStyle.p() != null) {
            TextEmphasis textEmphasis = (TextEmphasis) Assertions.checkNotNull(ttmlStyle.p());
            if (textEmphasis.a == -1) {
                i4 = (i3 == 2 || i3 == 1) ? 3 : 1;
                i5 = 1;
            } else {
                i4 = textEmphasis.a;
                i5 = textEmphasis.b;
            }
            SpanUtil.addOrReplaceSpan(spannable, new TextEmphasisSpan(i4, i5, textEmphasis.c == -2 ? 1 : textEmphasis.c), i, i2, 33);
        }
        switch (ttmlStyle.k()) {
            case 2:
                b b = b(bVar, map);
                if (!(b == null || (a = a(b, map)) == null)) {
                    if (a.a() == 1 && a.a(0).b != null) {
                        String str = (String) Util.castNonNull(a.a(0).b);
                        TtmlStyle a3 = a(a.f, a.c(), map);
                        int l = a3 != null ? a3.l() : -1;
                        if (l == -1 && (a2 = a(b.f, b.c(), map)) != null) {
                            l = a2.l();
                        }
                        spannable.setSpan(new RubySpan(str, l), i, i2, 33);
                        break;
                    } else {
                        Log.i("TtmlRenderUtil", "Skipping rubyText node without exactly one text child.");
                        break;
                    }
                }
                break;
            case 3:
            case 4:
                spannable.setSpan(new a(), i, i2, 33);
                break;
        }
        if (ttmlStyle.o()) {
            SpanUtil.addOrReplaceSpan(spannable, new HorizontalTextInVerticalContextSpan(), i, i2, 33);
        }
        switch (ttmlStyle.q()) {
            case 1:
                SpanUtil.addOrReplaceSpan(spannable, new AbsoluteSizeSpan((int) ttmlStyle.r(), true), i, i2, 33);
                return;
            case 2:
                SpanUtil.addOrReplaceSpan(spannable, new RelativeSizeSpan(ttmlStyle.r()), i, i2, 33);
                return;
            case 3:
                SpanUtil.addOrReplaceSpan(spannable, new RelativeSizeSpan(ttmlStyle.r() / 100.0f), i, i2, 33);
                return;
            default:
                return;
        }
    }

    @Nullable
    private static b a(b bVar, Map<String, TtmlStyle> map) {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(bVar);
        while (!arrayDeque.isEmpty()) {
            b bVar2 = (b) arrayDeque.pop();
            TtmlStyle a = a(bVar2.f, bVar2.c(), map);
            if (a != null && a.k() == 3) {
                return bVar2;
            }
            for (int a2 = bVar2.a() - 1; a2 >= 0; a2--) {
                arrayDeque.push(bVar2.a(a2));
            }
        }
        return null;
    }

    @Nullable
    private static b b(@Nullable b bVar, Map<String, TtmlStyle> map) {
        while (bVar != null) {
            TtmlStyle a = a(bVar.f, bVar.c(), map);
            if (a != null && a.k() == 1) {
                return bVar;
            }
            bVar = bVar.i;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length() - 1;
        while (length >= 0 && spannableStringBuilder.charAt(length) == ' ') {
            length--;
        }
        if (length >= 0 && spannableStringBuilder.charAt(length) != '\n') {
            spannableStringBuilder.append('\n');
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str) {
        return str.replaceAll("\r\n", "\n").replaceAll(" *\n *", "\n").replaceAll("\n", StringUtils.SPACE).replaceAll("[ \t\\x0B\f\r]+", StringUtils.SPACE);
    }
}
