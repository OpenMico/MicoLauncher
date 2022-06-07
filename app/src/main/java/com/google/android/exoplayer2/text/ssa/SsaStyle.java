package com.google.android.exoplayer2.text.ssa;

import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.Ints;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class SsaStyle {
    public final String a;
    public final int b;
    @Nullable
    @ColorInt
    public final Integer c;
    public final float d;
    public final boolean e;
    public final boolean f;
    public final boolean g;
    public final boolean h;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface SsaAlignment {
    }

    private static boolean a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private SsaStyle(String str, int i, @Nullable @ColorInt Integer num, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.a = str;
        this.b = i;
        this.c = num;
        this.d = f;
        this.e = z;
        this.f = z2;
        this.g = z3;
        this.h = z4;
    }

    @Nullable
    public static SsaStyle a(String str, a aVar) {
        Assertions.checkArgument(str.startsWith("Style:"));
        String[] split = TextUtils.split(str.substring(6), Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length != aVar.i) {
            Log.w("SsaStyle", Util.formatInvariant("Skipping malformed 'Style:' line (expected %s values, found %s): '%s'", Integer.valueOf(aVar.i), Integer.valueOf(split.length), str));
            return null;
        }
        try {
            return new SsaStyle(split[aVar.a].trim(), aVar.b != -1 ? c(split[aVar.b].trim()) : -1, aVar.c != -1 ? a(split[aVar.c].trim()) : null, aVar.d != -1 ? d(split[aVar.d].trim()) : -3.4028235E38f, aVar.e != -1 && e(split[aVar.e].trim()), aVar.f != -1 && e(split[aVar.f].trim()), aVar.g != -1 && e(split[aVar.g].trim()), aVar.h != -1 && e(split[aVar.h].trim()));
        } catch (RuntimeException e) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36);
            sb.append("Skipping malformed 'Style:' line: '");
            sb.append(str);
            sb.append(LrcRow.SINGLE_QUOTE);
            Log.w("SsaStyle", sb.toString(), e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(String str) {
        try {
            int parseInt = Integer.parseInt(str.trim());
            if (a(parseInt)) {
                return parseInt;
            }
        } catch (NumberFormatException unused) {
        }
        String valueOf = String.valueOf(str);
        Log.w("SsaStyle", valueOf.length() != 0 ? "Ignoring unknown alignment: ".concat(valueOf) : new String("Ignoring unknown alignment: "));
        return -1;
    }

    @Nullable
    @ColorInt
    public static Integer a(String str) {
        long j;
        try {
            if (str.startsWith("&H")) {
                j = Long.parseLong(str.substring(2), 16);
            } else {
                j = Long.parseLong(str);
            }
            Assertions.checkArgument(j <= 4294967295L);
            return Integer.valueOf(Color.argb(Ints.checkedCast(((j >> 24) & 255) ^ 255), Ints.checkedCast(j & 255), Ints.checkedCast((j >> 8) & 255), Ints.checkedCast((j >> 16) & 255)));
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36);
            sb.append("Failed to parse color expression: '");
            sb.append(str);
            sb.append(LrcRow.SINGLE_QUOTE);
            Log.w("SsaStyle", sb.toString(), e);
            return null;
        }
    }

    private static float d(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 29);
            sb.append("Failed to parse font size: '");
            sb.append(str);
            sb.append(LrcRow.SINGLE_QUOTE);
            Log.w("SsaStyle", sb.toString(), e);
            return -3.4028235E38f;
        }
    }

    private static boolean e(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            return parseInt == 1 || parseInt == -1;
        } catch (NumberFormatException e) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
            sb.append("Failed to parse boolean value: '");
            sb.append(str);
            sb.append(LrcRow.SINGLE_QUOTE);
            Log.w("SsaStyle", sb.toString(), e);
            return false;
        }
    }

    /* loaded from: classes2.dex */
    static final class a {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final int i;

        private a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
            this.g = i7;
            this.h = i8;
            this.i = i9;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Nullable
        public static a a(String str) {
            char c;
            String[] split = TextUtils.split(str.substring(7), Constants.ACCEPT_TIME_SEPARATOR_SP);
            int i = -1;
            int i2 = -1;
            int i3 = -1;
            int i4 = -1;
            int i5 = -1;
            int i6 = -1;
            int i7 = -1;
            int i8 = -1;
            for (int i9 = 0; i9 < split.length; i9++) {
                String lowerCase = Ascii.toLowerCase(split[i9].trim());
                switch (lowerCase.hashCode()) {
                    case -1178781136:
                        if (lowerCase.equals("italic")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1026963764:
                        if (lowerCase.equals("underline")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -192095652:
                        if (lowerCase.equals("strikeout")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -70925746:
                        if (lowerCase.equals("primarycolour")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3029637:
                        if (lowerCase.equals("bold")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3373707:
                        if (lowerCase.equals("name")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 366554320:
                        if (lowerCase.equals("fontsize")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1767875043:
                        if (lowerCase.equals("alignment")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = i9;
                        break;
                    case 1:
                        i2 = i9;
                        break;
                    case 2:
                        i3 = i9;
                        break;
                    case 3:
                        i4 = i9;
                        break;
                    case 4:
                        i5 = i9;
                        break;
                    case 5:
                        i6 = i9;
                        break;
                    case 6:
                        i7 = i9;
                        break;
                    case 7:
                        i8 = i9;
                        break;
                }
            }
            if (i != -1) {
                return new a(i, i2, i3, i4, i5, i6, i7, i8, split.length);
            }
            return null;
        }
    }

    /* loaded from: classes2.dex */
    static final class b {
        private static final Pattern c = Pattern.compile("\\{([^}]*)\\}");
        private static final Pattern d = Pattern.compile(Util.formatInvariant("\\\\pos\\((%1$s),(%1$s)\\)", "\\s*\\d+(?:\\.\\d+)?\\s*"));
        private static final Pattern e = Pattern.compile(Util.formatInvariant("\\\\move\\(%1$s,%1$s,(%1$s),(%1$s)(?:,%1$s,%1$s)?\\)", "\\s*\\d+(?:\\.\\d+)?\\s*"));
        private static final Pattern f = Pattern.compile("\\\\an(\\d+)");
        public final int a;
        @Nullable
        public final PointF b;

        private b(int i, @Nullable PointF pointF) {
            this.a = i;
            this.b = pointF;
        }

        public static b a(String str) {
            Matcher matcher = c.matcher(str);
            PointF pointF = null;
            int i = -1;
            while (matcher.find()) {
                String str2 = (String) Assertions.checkNotNull(matcher.group(1));
                try {
                    PointF c2 = c(str2);
                    if (c2 != null) {
                        pointF = c2;
                    }
                } catch (RuntimeException unused) {
                }
                try {
                    int d2 = d(str2);
                    if (d2 != -1) {
                        i = d2;
                    }
                } catch (RuntimeException unused2) {
                }
            }
            return new b(i, pointF);
        }

        public static String b(String str) {
            return c.matcher(str).replaceAll("");
        }

        @Nullable
        private static PointF c(String str) {
            String str2;
            String str3;
            Matcher matcher = d.matcher(str);
            Matcher matcher2 = e.matcher(str);
            boolean find = matcher.find();
            boolean find2 = matcher2.find();
            if (find) {
                if (find2) {
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 82);
                    sb.append("Override has both \\pos(x,y) and \\move(x1,y1,x2,y2); using \\pos values. override='");
                    sb.append(str);
                    sb.append(LrcRow.SINGLE_QUOTE);
                    Log.i("SsaStyle.Overrides", sb.toString());
                }
                str2 = matcher.group(1);
                str3 = matcher.group(2);
            } else if (!find2) {
                return null;
            } else {
                str2 = matcher2.group(1);
                str3 = matcher2.group(2);
            }
            return new PointF(Float.parseFloat(((String) Assertions.checkNotNull(str2)).trim()), Float.parseFloat(((String) Assertions.checkNotNull(str3)).trim()));
        }

        private static int d(String str) {
            Matcher matcher = f.matcher(str);
            if (matcher.find()) {
                return SsaStyle.c((String) Assertions.checkNotNull(matcher.group(1)));
            }
            return -1;
        }
    }
}
