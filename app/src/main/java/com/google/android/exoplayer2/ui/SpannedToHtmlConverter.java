package com.google.android.exoplayer2.ui;

import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.TextEmphasisSpan;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
final class SpannedToHtmlConverter {
    private static final Pattern a = Pattern.compile("(&#13;)?&#10;");

    private static String a(int i) {
        return i != 2 ? "over right" : "under left";
    }

    public static HtmlAndCss a(@Nullable CharSequence charSequence, float f) {
        if (charSequence == null) {
            return new HtmlAndCss("", ImmutableMap.of());
        }
        if (!(charSequence instanceof Spanned)) {
            return new HtmlAndCss(a(charSequence), ImmutableMap.of());
        }
        Spanned spanned = (Spanned) charSequence;
        HashSet<Integer> hashSet = new HashSet();
        int i = 0;
        for (BackgroundColorSpan backgroundColorSpan : (BackgroundColorSpan[]) spanned.getSpans(0, spanned.length(), BackgroundColorSpan.class)) {
            hashSet.add(Integer.valueOf(backgroundColorSpan.getBackgroundColor()));
        }
        HashMap hashMap = new HashMap();
        for (Integer num : hashSet) {
            int intValue = num.intValue();
            StringBuilder sb = new StringBuilder(14);
            sb.append("bg_");
            sb.append(intValue);
            hashMap.put(b.a(sb.toString()), Util.formatInvariant("background-color:%s;", b.a(intValue)));
        }
        SparseArray<b> a2 = a(spanned, f);
        StringBuilder sb2 = new StringBuilder(spanned.length());
        int i2 = 0;
        while (i < a2.size()) {
            int keyAt = a2.keyAt(i);
            sb2.append(a(spanned.subSequence(i2, keyAt)));
            b bVar = a2.get(keyAt);
            Collections.sort(bVar.b, a.f);
            for (a aVar : bVar.b) {
                sb2.append(aVar.d);
            }
            Collections.sort(bVar.a, a.e);
            for (a aVar2 : bVar.a) {
                sb2.append(aVar2.c);
            }
            i++;
            i2 = keyAt;
        }
        sb2.append(a(spanned.subSequence(i2, spanned.length())));
        return new HtmlAndCss(sb2.toString(), hashMap);
    }

    private static SparseArray<b> a(Spanned spanned, float f) {
        SparseArray<b> sparseArray = new SparseArray<>();
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        for (Object obj : spans) {
            String a2 = a(obj, f);
            String a3 = a(obj);
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            if (a2 != null) {
                Assertions.checkNotNull(a3);
                a aVar = new a(spanStart, spanEnd, a2, a3);
                a(sparseArray, spanStart).a.add(aVar);
                a(sparseArray, spanEnd).b.add(aVar);
            }
        }
        return sparseArray;
    }

    @Nullable
    private static String a(Object obj, float f) {
        float f2;
        if (obj instanceof StrikethroughSpan) {
            return "<span style='text-decoration:line-through;'>";
        }
        if (obj instanceof ForegroundColorSpan) {
            return Util.formatInvariant("<span style='color:%s;'>", b.a(((ForegroundColorSpan) obj).getForegroundColor()));
        }
        if (obj instanceof BackgroundColorSpan) {
            return Util.formatInvariant("<span class='bg_%s'>", Integer.valueOf(((BackgroundColorSpan) obj).getBackgroundColor()));
        }
        if (obj instanceof HorizontalTextInVerticalContextSpan) {
            return "<span style='text-combine-upright:all;'>";
        }
        if (obj instanceof AbsoluteSizeSpan) {
            AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) obj;
            if (absoluteSizeSpan.getDip()) {
                f2 = absoluteSizeSpan.getSize();
            } else {
                f2 = absoluteSizeSpan.getSize() / f;
            }
            return Util.formatInvariant("<span style='font-size:%.2fpx;'>", Float.valueOf(f2));
        } else if (obj instanceof RelativeSizeSpan) {
            return Util.formatInvariant("<span style='font-size:%.2f%%;'>", Float.valueOf(((RelativeSizeSpan) obj).getSizeChange() * 100.0f));
        } else {
            if (obj instanceof TypefaceSpan) {
                String family = ((TypefaceSpan) obj).getFamily();
                if (family != null) {
                    return Util.formatInvariant("<span style='font-family:\"%s\";'>", family);
                }
                return null;
            } else if (obj instanceof StyleSpan) {
                switch (((StyleSpan) obj).getStyle()) {
                    case 1:
                        return "<b>";
                    case 2:
                        return "<i>";
                    case 3:
                        return "<b><i>";
                    default:
                        return null;
                }
            } else if (obj instanceof RubySpan) {
                int i = ((RubySpan) obj).position;
                if (i == -1) {
                    return "<ruby style='ruby-position:unset;'>";
                }
                switch (i) {
                    case 1:
                        return "<ruby style='ruby-position:over;'>";
                    case 2:
                        return "<ruby style='ruby-position:under;'>";
                    default:
                        return null;
                }
            } else if (obj instanceof UnderlineSpan) {
                return "<u>";
            } else {
                if (!(obj instanceof TextEmphasisSpan)) {
                    return null;
                }
                TextEmphasisSpan textEmphasisSpan = (TextEmphasisSpan) obj;
                return Util.formatInvariant("<span style='-webkit-text-emphasis-style:%1$s;text-emphasis-style:%1$s;-webkit-text-emphasis-position:%2$s;text-emphasis-position:%2$s;display:inline-block;'>", a(textEmphasisSpan.markShape, textEmphasisSpan.markFill), a(textEmphasisSpan.position));
            }
        }
    }

    @Nullable
    private static String a(Object obj) {
        if ((obj instanceof StrikethroughSpan) || (obj instanceof ForegroundColorSpan) || (obj instanceof BackgroundColorSpan) || (obj instanceof HorizontalTextInVerticalContextSpan) || (obj instanceof AbsoluteSizeSpan) || (obj instanceof RelativeSizeSpan) || (obj instanceof TextEmphasisSpan)) {
            return "</span>";
        }
        if (!(obj instanceof TypefaceSpan)) {
            if (obj instanceof StyleSpan) {
                switch (((StyleSpan) obj).getStyle()) {
                    case 1:
                        return "</b>";
                    case 2:
                        return "</i>";
                    case 3:
                        return "</i></b>";
                }
            } else if (obj instanceof RubySpan) {
                String a2 = a((CharSequence) ((RubySpan) obj).rubyText);
                StringBuilder sb = new StringBuilder(String.valueOf(a2).length() + 16);
                sb.append("<rt>");
                sb.append(a2);
                sb.append("</rt></ruby>");
                return sb.toString();
            } else if (obj instanceof UnderlineSpan) {
                return "</u>";
            }
            return null;
        } else if (((TypefaceSpan) obj).getFamily() != null) {
            return "</span>";
        } else {
            return null;
        }
    }

    private static String a(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        switch (i2) {
            case 1:
                sb.append("filled ");
                break;
            case 2:
                sb.append("open ");
                break;
        }
        switch (i) {
            case 0:
                sb.append("none");
                break;
            case 1:
                sb.append("circle");
                break;
            case 2:
                sb.append("dot");
                break;
            case 3:
                sb.append("sesame");
                break;
            default:
                sb.append("unset");
                break;
        }
        return sb.toString();
    }

    private static b a(SparseArray<b> sparseArray, int i) {
        b bVar = sparseArray.get(i);
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = new b();
        sparseArray.put(i, bVar2);
        return bVar2;
    }

    private static String a(CharSequence charSequence) {
        return a.matcher(Html.escapeHtml(charSequence)).replaceAll("<br>");
    }

    /* loaded from: classes2.dex */
    public static class HtmlAndCss {
        public final Map<String, String> cssRuleSets;
        public final String html;

        private HtmlAndCss(String str, Map<String, String> map) {
            this.html = str;
            this.cssRuleSets = map;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private static final Comparator<a> e = $$Lambda$SpannedToHtmlConverter$a$iZO_zSba2VlXaSWJX916dzxEnXE.INSTANCE;
        private static final Comparator<a> f = $$Lambda$SpannedToHtmlConverter$a$emn4entnlEiUCG6dNVmctOrLiFs.INSTANCE;
        public final int a;
        public final int b;
        public final String c;
        public final String d;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int b(a aVar, a aVar2) {
            int compare = Integer.compare(aVar2.b, aVar.b);
            if (compare != 0) {
                return compare;
            }
            int compareTo = aVar.c.compareTo(aVar2.c);
            return compareTo != 0 ? compareTo : aVar.d.compareTo(aVar2.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int a(a aVar, a aVar2) {
            int compare = Integer.compare(aVar2.a, aVar.a);
            if (compare != 0) {
                return compare;
            }
            int compareTo = aVar2.c.compareTo(aVar.c);
            return compareTo != 0 ? compareTo : aVar2.d.compareTo(aVar.d);
        }

        private a(int i, int i2, String str, String str2) {
            this.a = i;
            this.b = i2;
            this.c = str;
            this.d = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        private final List<a> a = new ArrayList();
        private final List<a> b = new ArrayList();
    }
}
