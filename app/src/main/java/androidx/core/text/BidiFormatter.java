package androidx.core.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public final class BidiFormatter {
    private final boolean f;
    private final int g;
    private final TextDirectionHeuristicCompat h;
    static final TextDirectionHeuristicCompat a = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    private static final String d = Character.toString(8206);
    private static final String e = Character.toString(8207);
    static final BidiFormatter b = new BidiFormatter(false, 2, a);
    static final BidiFormatter c = new BidiFormatter(true, 2, a);

    /* loaded from: classes.dex */
    public static final class Builder {
        private boolean a;
        private int b;
        private TextDirectionHeuristicCompat c;

        public Builder() {
            a(BidiFormatter.a(Locale.getDefault()));
        }

        public Builder(boolean z) {
            a(z);
        }

        public Builder(Locale locale) {
            a(BidiFormatter.a(locale));
        }

        private void a(boolean z) {
            this.a = z;
            this.c = BidiFormatter.a;
            this.b = 2;
        }

        public Builder stereoReset(boolean z) {
            if (z) {
                this.b |= 2;
            } else {
                this.b &= -3;
            }
            return this;
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.c = textDirectionHeuristicCompat;
            return this;
        }

        private static BidiFormatter b(boolean z) {
            return z ? BidiFormatter.c : BidiFormatter.b;
        }

        public BidiFormatter build() {
            if (this.b == 2 && this.c == BidiFormatter.a) {
                return b(this.a);
            }
            return new BidiFormatter(this.a, this.b, this.c);
        }
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(boolean z) {
        return new Builder(z).build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.f = z;
        this.g = i;
        this.h = textDirectionHeuristicCompat;
    }

    public boolean isRtlContext() {
        return this.f;
    }

    public boolean getStereoReset() {
        return (this.g & 2) != 0;
    }

    private String a(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean isRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (this.f || (!isRtl && a(charSequence) != 1)) {
            return this.f ? (!isRtl || a(charSequence) == -1) ? e : "" : "";
        }
        return d;
    }

    private String b(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean isRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (this.f || (!isRtl && b(charSequence) != 1)) {
            return this.f ? (!isRtl || b(charSequence) == -1) ? e : "" : "";
        }
        return d;
    }

    public boolean isRtl(String str) {
        return isRtl((CharSequence) str);
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.h.isRtl(charSequence, 0, charSequence.length());
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (str == null) {
            return null;
        }
        return unicodeWrap((CharSequence) str, textDirectionHeuristicCompat, z).toString();
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (charSequence == null) {
            return null;
        }
        boolean isRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (getStereoReset() && z) {
            spannableStringBuilder.append((CharSequence) b(charSequence, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        if (isRtl != this.f) {
            spannableStringBuilder.append(isRtl ? (char) 8235 : (char) 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append((char) 8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (z) {
            spannableStringBuilder.append((CharSequence) a(charSequence, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        return spannableStringBuilder;
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(str, textDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(charSequence, textDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String str, boolean z) {
        return unicodeWrap(str, this.h, z);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean z) {
        return unicodeWrap(charSequence, this.h, z);
    }

    public String unicodeWrap(String str) {
        return unicodeWrap(str, this.h, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return unicodeWrap(charSequence, this.h, true);
    }

    static boolean a(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
    }

    private static int a(CharSequence charSequence) {
        return new a(charSequence, false).b();
    }

    private static int b(CharSequence charSequence) {
        return new a(charSequence, false).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private static final byte[] a = new byte[1792];
        private final CharSequence b;
        private final boolean c;
        private final int d;
        private int e;
        private char f;

        static {
            for (int i = 0; i < 1792; i++) {
                a[i] = Character.getDirectionality(i);
            }
        }

        a(CharSequence charSequence, boolean z) {
            this.b = charSequence;
            this.c = z;
            this.d = charSequence.length();
        }

        int a() {
            this.e = 0;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (this.e < this.d && i == 0) {
                byte c = c();
                if (c != 9) {
                    switch (c) {
                        case 0:
                            if (i3 == 0) {
                                return -1;
                            }
                            i = i3;
                            break;
                        case 1:
                        case 2:
                            if (i3 == 0) {
                                return 1;
                            }
                            i = i3;
                            break;
                        default:
                            switch (c) {
                                case 14:
                                case 15:
                                    i3++;
                                    i2 = -1;
                                    break;
                                case 16:
                                case 17:
                                    i3++;
                                    i2 = 1;
                                    break;
                                case 18:
                                    i3--;
                                    i2 = 0;
                                    break;
                                default:
                                    i = i3;
                                    break;
                            }
                    }
                }
            }
            if (i == 0) {
                return 0;
            }
            if (i2 != 0) {
                return i2;
            }
            while (this.e > 0) {
                switch (d()) {
                    case 14:
                    case 15:
                        if (i != i3) {
                            i3--;
                            break;
                        } else {
                            return -1;
                        }
                    case 16:
                    case 17:
                        if (i != i3) {
                            i3--;
                            break;
                        } else {
                            return 1;
                        }
                    case 18:
                        i3++;
                        break;
                }
            }
            return 0;
        }

        int b() {
            this.e = this.d;
            int i = 0;
            int i2 = 0;
            while (this.e > 0) {
                byte d = d();
                if (d != 9) {
                    switch (d) {
                        case 0:
                            if (i2 != 0) {
                                if (i != 0) {
                                    break;
                                } else {
                                    i = i2;
                                    break;
                                }
                            } else {
                                return -1;
                            }
                        case 1:
                        case 2:
                            if (i2 != 0) {
                                if (i != 0) {
                                    break;
                                } else {
                                    i = i2;
                                    break;
                                }
                            } else {
                                return 1;
                            }
                        default:
                            switch (d) {
                                case 14:
                                case 15:
                                    if (i != i2) {
                                        i2--;
                                        break;
                                    } else {
                                        return -1;
                                    }
                                case 16:
                                case 17:
                                    if (i != i2) {
                                        i2--;
                                        break;
                                    } else {
                                        return 1;
                                    }
                                case 18:
                                    i2++;
                                    break;
                                default:
                                    if (i != 0) {
                                        break;
                                    } else {
                                        i = i2;
                                        break;
                                    }
                            }
                    }
                }
            }
            return 0;
        }

        private static byte a(char c) {
            return c < 1792 ? a[c] : Character.getDirectionality(c);
        }

        byte c() {
            this.f = this.b.charAt(this.e);
            if (Character.isHighSurrogate(this.f)) {
                int codePointAt = Character.codePointAt(this.b, this.e);
                this.e += Character.charCount(codePointAt);
                return Character.getDirectionality(codePointAt);
            }
            this.e++;
            byte a2 = a(this.f);
            if (!this.c) {
                return a2;
            }
            char c = this.f;
            if (c == '<') {
                return e();
            }
            return c == '&' ? g() : a2;
        }

        byte d() {
            this.f = this.b.charAt(this.e - 1);
            if (Character.isLowSurrogate(this.f)) {
                int codePointBefore = Character.codePointBefore(this.b, this.e);
                this.e -= Character.charCount(codePointBefore);
                return Character.getDirectionality(codePointBefore);
            }
            this.e--;
            byte a2 = a(this.f);
            if (!this.c) {
                return a2;
            }
            char c = this.f;
            if (c == '>') {
                return f();
            }
            return c == ';' ? h() : a2;
        }

        private byte e() {
            char charAt;
            int i = this.e;
            while (true) {
                int i2 = this.e;
                if (i2 < this.d) {
                    CharSequence charSequence = this.b;
                    this.e = i2 + 1;
                    this.f = charSequence.charAt(i2);
                    char c = this.f;
                    if (c == '>') {
                        return (byte) 12;
                    }
                    if (c == '\"' || c == '\'') {
                        char c2 = this.f;
                        do {
                            int i3 = this.e;
                            if (i3 < this.d) {
                                CharSequence charSequence2 = this.b;
                                this.e = i3 + 1;
                                charAt = charSequence2.charAt(i3);
                                this.f = charAt;
                            }
                        } while (charAt != c2);
                    }
                } else {
                    this.e = i;
                    this.f = Typography.less;
                    return (byte) 13;
                }
            }
        }

        private byte f() {
            char charAt;
            int i = this.e;
            while (true) {
                int i2 = this.e;
                if (i2 <= 0) {
                    break;
                }
                CharSequence charSequence = this.b;
                int i3 = i2 - 1;
                this.e = i3;
                this.f = charSequence.charAt(i3);
                char c = this.f;
                if (c == '<') {
                    return (byte) 12;
                }
                if (c == '>') {
                    break;
                } else if (c == '\"' || c == '\'') {
                    char c2 = this.f;
                    do {
                        int i4 = this.e;
                        if (i4 > 0) {
                            CharSequence charSequence2 = this.b;
                            int i5 = i4 - 1;
                            this.e = i5;
                            charAt = charSequence2.charAt(i5);
                            this.f = charAt;
                        }
                    } while (charAt != c2);
                }
            }
            this.e = i;
            this.f = Typography.greater;
            return (byte) 13;
        }

        private byte g() {
            char charAt;
            do {
                int i = this.e;
                if (i >= this.d) {
                    return (byte) 12;
                }
                CharSequence charSequence = this.b;
                this.e = i + 1;
                charAt = charSequence.charAt(i);
                this.f = charAt;
            } while (charAt != ';');
            return (byte) 12;
        }

        private byte h() {
            char c;
            int i = this.e;
            do {
                int i2 = this.e;
                if (i2 <= 0) {
                    break;
                }
                CharSequence charSequence = this.b;
                int i3 = i2 - 1;
                this.e = i3;
                this.f = charSequence.charAt(i3);
                c = this.f;
                if (c == '&') {
                    return (byte) 12;
                }
            } while (c != ';');
            this.e = i;
            this.f = ';';
            return (byte) 13;
        }
    }
}
