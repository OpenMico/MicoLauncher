package androidx.core.text;

import java.nio.CharBuffer;
import java.util.Locale;

/* loaded from: classes.dex */
public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat LTR = new e(null, false);
    public static final TextDirectionHeuristicCompat RTL = new e(null, true);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR = new e(b.a, false);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL = new e(b.a, true);
    public static final TextDirectionHeuristicCompat ANYRTL_LTR = new e(a.a, false);
    public static final TextDirectionHeuristicCompat LOCALE = f.a;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface c {
        int a(CharSequence charSequence, int i, int i2);
    }

    static int a(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    static int b(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                switch (i) {
                    case 14:
                    case 15:
                        return 1;
                    case 16:
                    case 17:
                        return 0;
                    default:
                        return 2;
                }
        }
    }

    /* loaded from: classes.dex */
    private static abstract class d implements TextDirectionHeuristicCompat {
        private final c a;

        protected abstract boolean a();

        d(c cVar) {
            this.a = cVar;
        }

        @Override // androidx.core.text.TextDirectionHeuristicCompat
        public boolean isRtl(char[] cArr, int i, int i2) {
            return isRtl(CharBuffer.wrap(cArr), i, i2);
        }

        @Override // androidx.core.text.TextDirectionHeuristicCompat
        public boolean isRtl(CharSequence charSequence, int i, int i2) {
            if (charSequence == null || i < 0 || i2 < 0 || charSequence.length() - i2 < i) {
                throw new IllegalArgumentException();
            } else if (this.a == null) {
                return a();
            } else {
                return a(charSequence, i, i2);
            }
        }

        private boolean a(CharSequence charSequence, int i, int i2) {
            switch (this.a.a(charSequence, i, i2)) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return a();
            }
        }
    }

    /* loaded from: classes.dex */
    private static class e extends d {
        private final boolean a;

        e(c cVar, boolean z) {
            super(cVar);
            this.a = z;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.d
        protected boolean a() {
            return this.a;
        }
    }

    /* loaded from: classes.dex */
    private static class b implements c {
        static final b a = new b();

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.c
        public int a(CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            int i4 = 2;
            while (i < i3 && i4 == 2) {
                i4 = TextDirectionHeuristicsCompat.b(Character.getDirectionality(charSequence.charAt(i)));
                i++;
            }
            return i4;
        }

        private b() {
        }
    }

    /* loaded from: classes.dex */
    private static class a implements c {
        static final a a = new a(true);
        private final boolean b;

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.c
        public int a(CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            boolean z = false;
            while (i < i3) {
                switch (TextDirectionHeuristicsCompat.a(Character.getDirectionality(charSequence.charAt(i)))) {
                    case 0:
                        if (!this.b) {
                            z = true;
                            break;
                        } else {
                            return 0;
                        }
                    case 1:
                        if (this.b) {
                            z = true;
                            break;
                        } else {
                            return 1;
                        }
                }
                i++;
                z = z;
            }
            if (z) {
                return this.b ? 1 : 0;
            }
            return 2;
        }

        private a(boolean z) {
            this.b = z;
        }
    }

    /* loaded from: classes.dex */
    private static class f extends d {
        static final f a = new f();

        f() {
            super(null);
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.d
        protected boolean a() {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }
    }

    private TextDirectionHeuristicsCompat() {
    }
}
