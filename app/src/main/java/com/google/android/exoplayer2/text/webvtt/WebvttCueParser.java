package com.google.android.exoplayer2.text.webvtt;

import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.SpanUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class WebvttCueParser {
    public static final Pattern CUE_HEADER_PATTERN = Pattern.compile("^(\\S+)\\s+-->\\s+(\\S+)(.*)?$");
    private static final Pattern a = Pattern.compile("(\\S+?):(\\S+)");
    private static final Map<String, Integer> b;
    private static final Map<String, Integer> c;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("white", Integer.valueOf(Color.rgb(255, 255, 255)));
        hashMap.put("lime", Integer.valueOf(Color.rgb(0, 255, 0)));
        hashMap.put("cyan", Integer.valueOf(Color.rgb(0, 255, 255)));
        hashMap.put("red", Integer.valueOf(Color.rgb(255, 0, 0)));
        hashMap.put("yellow", Integer.valueOf(Color.rgb(255, 255, 0)));
        hashMap.put("magenta", Integer.valueOf(Color.rgb(255, 0, 255)));
        hashMap.put("blue", Integer.valueOf(Color.rgb(0, 0, 255)));
        hashMap.put("black", Integer.valueOf(Color.rgb(0, 0, 0)));
        b = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("bg_white", Integer.valueOf(Color.rgb(255, 255, 255)));
        hashMap2.put("bg_lime", Integer.valueOf(Color.rgb(0, 255, 0)));
        hashMap2.put("bg_cyan", Integer.valueOf(Color.rgb(0, 255, 255)));
        hashMap2.put("bg_red", Integer.valueOf(Color.rgb(255, 0, 0)));
        hashMap2.put("bg_yellow", Integer.valueOf(Color.rgb(255, 255, 0)));
        hashMap2.put("bg_magenta", Integer.valueOf(Color.rgb(255, 0, 255)));
        hashMap2.put("bg_blue", Integer.valueOf(Color.rgb(0, 0, 255)));
        hashMap2.put("bg_black", Integer.valueOf(Color.rgb(0, 0, 0)));
        c = Collections.unmodifiableMap(hashMap2);
    }

    @Nullable
    public static WebvttCueInfo parseCue(ParsableByteArray parsableByteArray, List<WebvttCssStyle> list) {
        String readLine = parsableByteArray.readLine();
        if (readLine == null) {
            return null;
        }
        Matcher matcher = CUE_HEADER_PATTERN.matcher(readLine);
        if (matcher.matches()) {
            return a((String) null, matcher, parsableByteArray, list);
        }
        String readLine2 = parsableByteArray.readLine();
        if (readLine2 == null) {
            return null;
        }
        Matcher matcher2 = CUE_HEADER_PATTERN.matcher(readLine2);
        if (matcher2.matches()) {
            return a(readLine.trim(), matcher2, parsableByteArray, list);
        }
        return null;
    }

    public static Cue.Builder a(String str) {
        d dVar = new d();
        a(str, dVar);
        return dVar.b();
    }

    public static Cue a(CharSequence charSequence) {
        d dVar = new d();
        dVar.c = charSequence;
        return dVar.b().build();
    }

    public static SpannedString a(@Nullable String str, String str2, List<WebvttCssStyle> list) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ArrayDeque arrayDeque = new ArrayDeque();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < str2.length()) {
            char charAt = str2.charAt(i);
            if (charAt == '&') {
                i++;
                int indexOf = str2.indexOf(59, i);
                int indexOf2 = str2.indexOf(32, i);
                if (indexOf == -1) {
                    indexOf = indexOf2;
                } else if (indexOf2 != -1) {
                    indexOf = Math.min(indexOf, indexOf2);
                }
                if (indexOf != -1) {
                    a(str2.substring(i, indexOf), spannableStringBuilder);
                    if (indexOf == indexOf2) {
                        spannableStringBuilder.append((CharSequence) StringUtils.SPACE);
                    }
                    i = indexOf + 1;
                } else {
                    spannableStringBuilder.append(charAt);
                }
            } else if (charAt != '<') {
                spannableStringBuilder.append(charAt);
                i++;
            } else {
                int i2 = i + 1;
                if (i2 >= str2.length()) {
                    i = i2;
                } else {
                    int i3 = 1;
                    boolean z = str2.charAt(i2) == '/';
                    int a2 = a(str2, i2);
                    int i4 = a2 - 2;
                    boolean z2 = str2.charAt(i4) == '/';
                    if (z) {
                        i3 = 2;
                    }
                    int i5 = i + i3;
                    if (!z2) {
                        i4 = a2 - 1;
                    }
                    String substring = str2.substring(i5, i4);
                    if (!substring.trim().isEmpty()) {
                        String g = g(substring);
                        if (f(g)) {
                            if (z) {
                                while (!arrayDeque.isEmpty()) {
                                    b bVar = (b) arrayDeque.pop();
                                    a(str, bVar, arrayList, spannableStringBuilder, list);
                                    if (!arrayDeque.isEmpty()) {
                                        arrayList.add(new a(bVar, spannableStringBuilder.length()));
                                    } else {
                                        arrayList.clear();
                                    }
                                    if (bVar.a.equals(g)) {
                                        break;
                                    }
                                }
                            } else if (!z2) {
                                arrayDeque.push(b.a(substring, spannableStringBuilder.length()));
                            }
                            i = a2;
                        }
                    }
                    i = a2;
                }
            }
        }
        while (!arrayDeque.isEmpty()) {
            a(str, (b) arrayDeque.pop(), arrayList, spannableStringBuilder, list);
        }
        a(str, b.a(), Collections.emptyList(), spannableStringBuilder, list);
        return SpannedString.valueOf(spannableStringBuilder);
    }

    @Nullable
    private static WebvttCueInfo a(@Nullable String str, Matcher matcher, ParsableByteArray parsableByteArray, List<WebvttCssStyle> list) {
        d dVar = new d();
        try {
            dVar.a = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcher.group(1)));
            dVar.b = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcher.group(2)));
            a((String) Assertions.checkNotNull(matcher.group(3)), dVar);
            StringBuilder sb = new StringBuilder();
            String readLine = parsableByteArray.readLine();
            while (!TextUtils.isEmpty(readLine)) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(readLine.trim());
                readLine = parsableByteArray.readLine();
            }
            dVar.c = a(str, sb.toString(), list);
            return dVar.a();
        } catch (NumberFormatException unused) {
            String valueOf = String.valueOf(matcher.group());
            Log.w("WebvttCueParser", valueOf.length() != 0 ? "Skipping cue with bad header: ".concat(valueOf) : new String("Skipping cue with bad header: "));
            return null;
        }
    }

    private static void a(String str, d dVar) {
        Matcher matcher = a.matcher(str);
        while (matcher.find()) {
            String str2 = (String) Assertions.checkNotNull(matcher.group(1));
            String str3 = (String) Assertions.checkNotNull(matcher.group(2));
            try {
                if ("line".equals(str2)) {
                    b(str3, dVar);
                } else if ("align".equals(str2)) {
                    dVar.d = e(str3);
                } else if ("position".equals(str2)) {
                    c(str3, dVar);
                } else if ("size".equals(str2)) {
                    dVar.j = WebvttParserUtil.parsePercentage(str3);
                } else if ("vertical".equals(str2)) {
                    dVar.k = d(str3);
                } else {
                    StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 21 + String.valueOf(str3).length());
                    sb.append("Unknown cue setting ");
                    sb.append(str2);
                    sb.append(Constants.COLON_SEPARATOR);
                    sb.append(str3);
                    Log.w("WebvttCueParser", sb.toString());
                }
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(matcher.group());
                Log.w("WebvttCueParser", valueOf.length() != 0 ? "Skipping bad cue setting: ".concat(valueOf) : new String("Skipping bad cue setting: "));
            }
        }
    }

    private static void b(String str, d dVar) {
        int indexOf = str.indexOf(44);
        if (indexOf != -1) {
            dVar.g = b(str.substring(indexOf + 1));
            str = str.substring(0, indexOf);
        }
        if (str.endsWith("%")) {
            dVar.e = WebvttParserUtil.parsePercentage(str);
            dVar.f = 0;
            return;
        }
        dVar.e = Integer.parseInt(str);
        dVar.f = 1;
    }

    private static int b(String str) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode == -1364013995) {
            if (str.equals("center")) {
                c2 = 1;
            }
            c2 = 65535;
        } else if (hashCode == -1074341483) {
            if (str.equals("middle")) {
                c2 = 2;
            }
            c2 = 65535;
        } else if (hashCode != 100571) {
            if (hashCode == 109757538 && str.equals("start")) {
                c2 = 0;
            }
            c2 = 65535;
        } else {
            if (str.equals("end")) {
                c2 = 3;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                return 0;
            case 1:
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                String valueOf = String.valueOf(str);
                Log.w("WebvttCueParser", valueOf.length() != 0 ? "Invalid anchor value: ".concat(valueOf) : new String("Invalid anchor value: "));
                return Integer.MIN_VALUE;
        }
    }

    private static void c(String str, d dVar) {
        int indexOf = str.indexOf(44);
        if (indexOf != -1) {
            dVar.i = c(str.substring(indexOf + 1));
            str = str.substring(0, indexOf);
        }
        dVar.h = WebvttParserUtil.parsePercentage(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int c(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1842484672:
                if (str.equals("line-left")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1364013995:
                if (str.equals("center")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1276788989:
                if (str.equals("line-right")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1074341483:
                if (str.equals("middle")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 100571:
                if (str.equals("end")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 109757538:
                if (str.equals("start")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                return 1;
            case 4:
            case 5:
                return 2;
            default:
                String valueOf = String.valueOf(str);
                Log.w("WebvttCueParser", valueOf.length() != 0 ? "Invalid anchor value: ".concat(valueOf) : new String("Invalid anchor value: "));
                return Integer.MIN_VALUE;
        }
    }

    private static int d(String str) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode != 3462) {
            if (hashCode == 3642 && str.equals("rl")) {
                c2 = 0;
            }
            c2 = 65535;
        } else {
            if (str.equals("lr")) {
                c2 = 1;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                String valueOf = String.valueOf(str);
                Log.w("WebvttCueParser", valueOf.length() != 0 ? "Invalid 'vertical' value: ".concat(valueOf) : new String("Invalid 'vertical' value: "));
                return Integer.MIN_VALUE;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int e(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1364013995:
                if (str.equals("center")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1074341483:
                if (str.equals("middle")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 100571:
                if (str.equals("end")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 3317767:
                if (str.equals("left")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 108511772:
                if (str.equals("right")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 109757538:
                if (str.equals("start")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 5;
            default:
                String valueOf = String.valueOf(str);
                Log.w("WebvttCueParser", valueOf.length() != 0 ? "Invalid alignment value: ".concat(valueOf) : new String("Invalid alignment value: "));
                return 2;
        }
    }

    private static int a(String str, int i) {
        int indexOf = str.indexOf(62, i);
        return indexOf == -1 ? str.length() : indexOf + 1;
    }

    private static void a(String str, SpannableStringBuilder spannableStringBuilder) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode == 3309) {
            if (str.equals("gt")) {
                c2 = 1;
            }
            c2 = 65535;
        } else if (hashCode == 3464) {
            if (str.equals("lt")) {
                c2 = 0;
            }
            c2 = 65535;
        } else if (hashCode != 96708) {
            if (hashCode == 3374865 && str.equals("nbsp")) {
                c2 = 2;
            }
            c2 = 65535;
        } else {
            if (str.equals("amp")) {
                c2 = 3;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                spannableStringBuilder.append(Typography.less);
                return;
            case 1:
                spannableStringBuilder.append(Typography.greater);
                return;
            case 2:
                spannableStringBuilder.append(' ');
                return;
            case 3:
                spannableStringBuilder.append(Typography.amp);
                return;
            default:
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
                sb.append("ignoring unsupported entity: '&");
                sb.append(str);
                sb.append(";'");
                Log.w("WebvttCueParser", sb.toString());
                return;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static boolean f(String str) {
        char c2;
        switch (str.hashCode()) {
            case 98:
                if (str.equals("b")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 99:
                if (str.equals(ai.aD)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 105:
                if (str.equals(ai.aA)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 117:
                if (str.equals(ai.aE)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 118:
                if (str.equals(ai.aC)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 3650:
                if (str.equals("rt")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 3314158:
                if (str.equals("lang")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 3511770:
                if (str.equals("ruby")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void a(@Nullable String str, b bVar, List<a> list, SpannableStringBuilder spannableStringBuilder, List<WebvttCssStyle> list2) {
        char c2;
        int i = bVar.b;
        int length = spannableStringBuilder.length();
        String str2 = bVar.a;
        switch (str2.hashCode()) {
            case 0:
                if (str2.equals("")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 98:
                if (str2.equals("b")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 99:
                if (str2.equals(ai.aD)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 105:
                if (str2.equals(ai.aA)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 117:
                if (str2.equals(ai.aE)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 118:
                if (str2.equals(ai.aC)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 3314158:
                if (str2.equals("lang")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 3511770:
                if (str2.equals("ruby")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                spannableStringBuilder.setSpan(new StyleSpan(1), i, length, 33);
                break;
            case 1:
                spannableStringBuilder.setSpan(new StyleSpan(2), i, length, 33);
                break;
            case 2:
                a(spannableStringBuilder, str, bVar, list, list2);
                break;
            case 3:
                spannableStringBuilder.setSpan(new UnderlineSpan(), i, length, 33);
                break;
            case 4:
                a(spannableStringBuilder, bVar.d, i, length);
                break;
            case 5:
            case 6:
            case 7:
                break;
            default:
                return;
        }
        List<c> b2 = b(list2, str, bVar);
        for (int i2 = 0; i2 < b2.size(); i2++) {
            a(spannableStringBuilder, b2.get(i2).b, i, length);
        }
    }

    private static void a(SpannableStringBuilder spannableStringBuilder, @Nullable String str, b bVar, List<a> list, List<WebvttCssStyle> list2) {
        int a2 = a(list2, str, bVar);
        ArrayList arrayList = new ArrayList(list.size());
        arrayList.addAll(list);
        Collections.sort(arrayList, a.a);
        int i = bVar.b;
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            if ("rt".equals(((a) arrayList.get(i3)).b.a)) {
                a aVar = (a) arrayList.get(i3);
                int a3 = a(a(list2, str, aVar.b), a2, 1);
                int i4 = aVar.b.b - i2;
                int i5 = aVar.c - i2;
                CharSequence subSequence = spannableStringBuilder.subSequence(i4, i5);
                spannableStringBuilder.delete(i4, i5);
                spannableStringBuilder.setSpan(new RubySpan(subSequence.toString(), a3), i, i4, 33);
                i2 += subSequence.length();
                i = i4;
            }
        }
    }

    private static int a(List<WebvttCssStyle> list, @Nullable String str, b bVar) {
        List<c> b2 = b(list, str, bVar);
        for (int i = 0; i < b2.size(); i++) {
            WebvttCssStyle webvttCssStyle = b2.get(i).b;
            if (webvttCssStyle.getRubyPosition() != -1) {
                return webvttCssStyle.getRubyPosition();
            }
        }
        return -1;
    }

    private static int a(int i, int i2, int i3) {
        if (i != -1) {
            return i;
        }
        if (i2 != -1) {
            return i2;
        }
        if (i3 != -1) {
            return i3;
        }
        throw new IllegalArgumentException();
    }

    private static void a(SpannableStringBuilder spannableStringBuilder, Set<String> set, int i, int i2) {
        for (String str : set) {
            if (b.containsKey(str)) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(b.get(str).intValue()), i, i2, 33);
            } else if (c.containsKey(str)) {
                spannableStringBuilder.setSpan(new BackgroundColorSpan(c.get(str).intValue()), i, i2, 33);
            }
        }
    }

    private static void a(SpannableStringBuilder spannableStringBuilder, WebvttCssStyle webvttCssStyle, int i, int i2) {
        if (webvttCssStyle != null) {
            if (webvttCssStyle.getStyle() != -1) {
                SpanUtil.addOrReplaceSpan(spannableStringBuilder, new StyleSpan(webvttCssStyle.getStyle()), i, i2, 33);
            }
            if (webvttCssStyle.isLinethrough()) {
                spannableStringBuilder.setSpan(new StrikethroughSpan(), i, i2, 33);
            }
            if (webvttCssStyle.isUnderline()) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), i, i2, 33);
            }
            if (webvttCssStyle.hasFontColor()) {
                SpanUtil.addOrReplaceSpan(spannableStringBuilder, new ForegroundColorSpan(webvttCssStyle.getFontColor()), i, i2, 33);
            }
            if (webvttCssStyle.hasBackgroundColor()) {
                SpanUtil.addOrReplaceSpan(spannableStringBuilder, new BackgroundColorSpan(webvttCssStyle.getBackgroundColor()), i, i2, 33);
            }
            if (webvttCssStyle.getFontFamily() != null) {
                SpanUtil.addOrReplaceSpan(spannableStringBuilder, new TypefaceSpan(webvttCssStyle.getFontFamily()), i, i2, 33);
            }
            switch (webvttCssStyle.getFontSizeUnit()) {
                case 1:
                    SpanUtil.addOrReplaceSpan(spannableStringBuilder, new AbsoluteSizeSpan((int) webvttCssStyle.getFontSize(), true), i, i2, 33);
                    break;
                case 2:
                    SpanUtil.addOrReplaceSpan(spannableStringBuilder, new RelativeSizeSpan(webvttCssStyle.getFontSize()), i, i2, 33);
                    break;
                case 3:
                    SpanUtil.addOrReplaceSpan(spannableStringBuilder, new RelativeSizeSpan(webvttCssStyle.getFontSize() / 100.0f), i, i2, 33);
                    break;
            }
            if (webvttCssStyle.getCombineUpright()) {
                spannableStringBuilder.setSpan(new HorizontalTextInVerticalContextSpan(), i, i2, 33);
            }
        }
    }

    private static String g(String str) {
        String trim = str.trim();
        Assertions.checkArgument(!trim.isEmpty());
        return Util.splitAtFirst(trim, "[ \\.]")[0];
    }

    private static List<c> b(List<WebvttCssStyle> list, @Nullable String str, b bVar) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            WebvttCssStyle webvttCssStyle = list.get(i);
            int specificityScore = webvttCssStyle.getSpecificityScore(str, bVar.a, bVar.d, bVar.c);
            if (specificityScore > 0) {
                arrayList.add(new c(specificityScore, webvttCssStyle));
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* loaded from: classes2.dex */
    public static final class d {
        public CharSequence c;
        public long a = 0;
        public long b = 0;
        public int d = 2;
        public float e = -3.4028235E38f;
        public int f = 1;
        public int g = 0;
        public float h = -3.4028235E38f;
        public int i = Integer.MIN_VALUE;
        public float j = 1.0f;
        public int k = Integer.MIN_VALUE;

        private static float a(float f, int i) {
            int i2 = (f > (-3.4028235E38f) ? 1 : (f == (-3.4028235E38f) ? 0 : -1));
            if (i2 == 0 || i != 0 || (f >= 0.0f && f <= 1.0f)) {
                return i2 != 0 ? f : i == 0 ? 1.0f : -3.4028235E38f;
            }
            return 1.0f;
        }

        private static float a(int i) {
            switch (i) {
                case 4:
                    return 0.0f;
                case 5:
                    return 1.0f;
                default:
                    return 0.5f;
            }
        }

        private static int b(int i) {
            if (i == 1) {
                return 0;
            }
            switch (i) {
                case 3:
                case 5:
                    return 2;
                case 4:
                    return 0;
                default:
                    return 1;
            }
        }

        public WebvttCueInfo a() {
            return new WebvttCueInfo(b().build(), this.a, this.b);
        }

        public Cue.Builder b() {
            float f = this.h;
            if (f == -3.4028235E38f) {
                f = a(this.d);
            }
            int i = this.i;
            if (i == Integer.MIN_VALUE) {
                i = b(this.d);
            }
            Cue.Builder verticalType = new Cue.Builder().setTextAlignment(c(this.d)).setLine(a(this.e, this.f), this.f).setLineAnchor(this.g).setPosition(f).setPositionAnchor(i).setSize(Math.min(this.j, a(i, f))).setVerticalType(this.k);
            CharSequence charSequence = this.c;
            if (charSequence != null) {
                verticalType.setText(charSequence);
            }
            return verticalType;
        }

        @Nullable
        private static Layout.Alignment c(int i) {
            switch (i) {
                case 1:
                case 4:
                    return Layout.Alignment.ALIGN_NORMAL;
                case 2:
                    return Layout.Alignment.ALIGN_CENTER;
                case 3:
                case 5:
                    return Layout.Alignment.ALIGN_OPPOSITE;
                default:
                    StringBuilder sb = new StringBuilder(34);
                    sb.append("Unknown textAlignment: ");
                    sb.append(i);
                    Log.w("WebvttCueParser", sb.toString());
                    return null;
            }
        }

        private static float a(int i, float f) {
            switch (i) {
                case 0:
                    return 1.0f - f;
                case 1:
                    return f <= 0.5f ? f * 2.0f : (1.0f - f) * 2.0f;
                case 2:
                    return f;
                default:
                    throw new IllegalStateException(String.valueOf(i));
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class c implements Comparable<c> {
        public final int a;
        public final WebvttCssStyle b;

        public c(int i, WebvttCssStyle webvttCssStyle) {
            this.a = i;
            this.b = webvttCssStyle;
        }

        /* renamed from: a */
        public int compareTo(c cVar) {
            return Integer.compare(this.a, cVar.a);
        }
    }

    /* loaded from: classes2.dex */
    public static final class b {
        public final String a;
        public final int b;
        public final String c;
        public final Set<String> d;

        private b(String str, int i, String str2, Set<String> set) {
            this.b = i;
            this.a = str;
            this.c = str2;
            this.d = set;
        }

        public static b a(String str, int i) {
            String str2;
            String trim = str.trim();
            Assertions.checkArgument(!trim.isEmpty());
            int indexOf = trim.indexOf(StringUtils.SPACE);
            if (indexOf == -1) {
                str2 = "";
            } else {
                str2 = trim.substring(indexOf).trim();
                trim = trim.substring(0, indexOf);
            }
            String[] split = Util.split(trim, "\\.");
            String str3 = split[0];
            HashSet hashSet = new HashSet();
            for (int i2 = 1; i2 < split.length; i2++) {
                hashSet.add(split[i2]);
            }
            return new b(str3, i, str2, hashSet);
        }

        public static b a() {
            return new b("", 0, "", Collections.emptySet());
        }
    }

    /* loaded from: classes2.dex */
    public static class a {
        private static final Comparator<a> a = $$Lambda$WebvttCueParser$a$aqR52xmAYgZIvqi1BZyFeDK4NsU.INSTANCE;
        private final b b;
        private final int c;

        public static /* synthetic */ int a(a aVar, a aVar2) {
            return Integer.compare(aVar.b.b, aVar2.b.b);
        }

        private a(b bVar, int i) {
            this.b = bVar;
            this.c = i;
        }
    }
}
