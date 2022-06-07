package androidx.core.os;

import android.os.Build;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/* compiled from: LocaleListCompatWrapper.java */
/* loaded from: classes.dex */
final class a implements b {
    private static final Locale[] c = new Locale[0];
    private static final Locale d = new Locale("en", "XA");
    private static final Locale e = new Locale("ar", "XB");
    private static final Locale f = LocaleListCompat.a("en-Latn");
    private final Locale[] a;
    @NonNull
    private final String b;

    @Override // androidx.core.os.b
    @Nullable
    public Object a() {
        return null;
    }

    @Override // androidx.core.os.b
    public Locale a(int i) {
        if (i >= 0) {
            Locale[] localeArr = this.a;
            if (i < localeArr.length) {
                return localeArr[i];
            }
        }
        return null;
    }

    @Override // androidx.core.os.b
    public boolean b() {
        return this.a.length == 0;
    }

    @Override // androidx.core.os.b
    public int c() {
        return this.a.length;
    }

    @Override // androidx.core.os.b
    public int a(Locale locale) {
        int i = 0;
        while (true) {
            Locale[] localeArr = this.a;
            if (i >= localeArr.length) {
                return -1;
            }
            if (localeArr[i].equals(locale)) {
                return i;
            }
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        Locale[] localeArr = ((a) obj).a;
        if (this.a.length != localeArr.length) {
            return false;
        }
        int i = 0;
        while (true) {
            Locale[] localeArr2 = this.a;
            if (i >= localeArr2.length) {
                return true;
            }
            if (!localeArr2[i].equals(localeArr[i])) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int i = 1;
        int i2 = 0;
        while (true) {
            Locale[] localeArr = this.a;
            if (i2 >= localeArr.length) {
                return i;
            }
            i = (i * 31) + localeArr[i2].hashCode();
            i2++;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0;
        while (true) {
            Locale[] localeArr = this.a;
            if (i < localeArr.length) {
                sb.append(localeArr[i]);
                if (i < this.a.length - 1) {
                    sb.append(StringUtil.COMMA);
                }
                i++;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    @Override // androidx.core.os.b
    public String d() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull Locale... localeArr) {
        if (localeArr.length == 0) {
            this.a = c;
            this.b = "";
            return;
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < localeArr.length; i++) {
            Locale locale = localeArr[i];
            if (locale != null) {
                if (!hashSet.contains(locale)) {
                    Locale locale2 = (Locale) locale.clone();
                    arrayList.add(locale2);
                    a(sb, locale2);
                    if (i < localeArr.length - 1) {
                        sb.append(StringUtil.COMMA);
                    }
                    hashSet.add(locale2);
                }
            } else {
                throw new NullPointerException("list[" + i + "] is null");
            }
        }
        this.a = (Locale[]) arrayList.toArray(new Locale[arrayList.size()]);
        this.b = sb.toString();
    }

    @VisibleForTesting
    static void a(StringBuilder sb, Locale locale) {
        sb.append(locale.getLanguage());
        String country = locale.getCountry();
        if (country != null && !country.isEmpty()) {
            sb.append('-');
            sb.append(locale.getCountry());
        }
    }

    private static String b(Locale locale) {
        if (Build.VERSION.SDK_INT < 21) {
            return "";
        }
        String script = locale.getScript();
        return !script.isEmpty() ? script : "";
    }

    private static boolean c(Locale locale) {
        return d.equals(locale) || e.equals(locale);
    }

    @IntRange(from = 0, to = 1)
    private static int a(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return 1;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || c(locale) || c(locale2)) {
            return 0;
        }
        String b = b(locale);
        if (!b.isEmpty()) {
            return b.equals(b(locale2)) ? 1 : 0;
        }
        String country = locale.getCountry();
        return (country.isEmpty() || country.equals(locale2.getCountry())) ? 1 : 0;
    }

    private int d(Locale locale) {
        int i = 0;
        while (true) {
            Locale[] localeArr = this.a;
            if (i >= localeArr.length) {
                return Integer.MAX_VALUE;
            }
            if (a(locale, localeArr[i]) > 0) {
                return i;
            }
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001b, code lost:
        if (r6 < Integer.MAX_VALUE) goto L_0x001f;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(java.util.Collection<java.lang.String> r5, boolean r6) {
        /*
            r4 = this;
            java.util.Locale[] r0 = r4.a
            int r1 = r0.length
            r2 = 0
            r3 = 1
            if (r1 != r3) goto L_0x0008
            return r2
        L_0x0008:
            int r0 = r0.length
            if (r0 != 0) goto L_0x000d
            r5 = -1
            return r5
        L_0x000d:
            r0 = 2147483647(0x7fffffff, float:NaN)
            if (r6 == 0) goto L_0x001e
            java.util.Locale r6 = androidx.core.os.a.f
            int r6 = r4.d(r6)
            if (r6 != 0) goto L_0x001b
            return r2
        L_0x001b:
            if (r6 >= r0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r6 = r0
        L_0x001f:
            java.util.Iterator r5 = r5.iterator()
        L_0x0023:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L_0x003e
            java.lang.Object r1 = r5.next()
            java.lang.String r1 = (java.lang.String) r1
            java.util.Locale r1 = androidx.core.os.LocaleListCompat.a(r1)
            int r1 = r4.d(r1)
            if (r1 != 0) goto L_0x003a
            return r2
        L_0x003a:
            if (r1 >= r6) goto L_0x0023
            r6 = r1
            goto L_0x0023
        L_0x003e:
            if (r6 != r0) goto L_0x0041
            return r2
        L_0x0041:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.a.a(java.util.Collection, boolean):int");
    }

    private Locale b(Collection<String> collection, boolean z) {
        int a = a(collection, z);
        if (a == -1) {
            return null;
        }
        return this.a[a];
    }

    @Override // androidx.core.os.b
    public Locale a(@NonNull String[] strArr) {
        return b(Arrays.asList(strArr), false);
    }
}
