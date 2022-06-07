package org.apache.commons.lang3.time;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.FieldPosition;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

/* loaded from: classes5.dex */
public class FastDatePrinter implements Serializable, DatePrinter {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static final ConcurrentMap<i, String> c = new ConcurrentHashMap(7);
    private static final long serialVersionUID = 1;
    private transient f[] a;
    private transient int b;
    private final Locale mLocale;
    private final String mPattern;
    private final TimeZone mTimeZone;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public interface d extends f {
        void a(Appendable appendable, int i) throws IOException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public interface f {
        int a();

        void a(Appendable appendable, Calendar calendar) throws IOException;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FastDatePrinter(String str, TimeZone timeZone, Locale locale) {
        this.mPattern = str;
        this.mTimeZone = timeZone;
        this.mLocale = locale;
        a();
    }

    private void a() {
        List<f> parsePattern = parsePattern();
        this.a = (f[]) parsePattern.toArray(new f[parsePattern.size()]);
        int length = this.a.length;
        int i2 = 0;
        while (true) {
            length--;
            if (length >= 0) {
                i2 += this.a[length].a();
            } else {
                this.b = i2;
                return;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.util.List<org.apache.commons.lang3.time.FastDatePrinter.f> parsePattern() {
        /*
            Method dump skipped, instructions count: 514
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDatePrinter.parsePattern():java.util.List");
    }

    protected String parseToken(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i2 = iArr[0];
        int length = str.length();
        char charAt = str.charAt(i2);
        if ((charAt >= 'A' && charAt <= 'Z') || (charAt >= 'a' && charAt <= 'z')) {
            sb.append(charAt);
            while (true) {
                int i3 = i2 + 1;
                if (i3 >= length || str.charAt(i3) != charAt) {
                    break;
                }
                sb.append(charAt);
                i2 = i3;
            }
        } else {
            sb.append('\'');
            boolean z = false;
            while (i2 < length) {
                char charAt2 = str.charAt(i2);
                if (charAt2 != '\'') {
                    if (!z && ((charAt2 >= 'A' && charAt2 <= 'Z') || (charAt2 >= 'a' && charAt2 <= 'z'))) {
                        i2--;
                        break;
                    }
                    sb.append(charAt2);
                } else {
                    int i4 = i2 + 1;
                    if (i4 >= length || str.charAt(i4) != '\'') {
                        z = !z;
                    } else {
                        sb.append(charAt2);
                        i2 = i4;
                    }
                }
                i2++;
            }
        }
        iArr[0] = i2;
        return sb.toString();
    }

    protected d selectNumberRule(int i2, int i3) {
        switch (i3) {
            case 1:
                return new r(i2);
            case 2:
                return new o(i2);
            default:
                return new e(i2, i3);
        }
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Date) {
            return format((Date) obj, stringBuffer);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, stringBuffer);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), stringBuffer);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown class: ");
        sb.append(obj == null ? "<null>" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String a(Object obj) {
        if (obj instanceof Date) {
            return format((Date) obj);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown class: ");
        sb.append(obj == null ? "<null>" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(long j2) {
        Calendar b2 = b();
        b2.setTimeInMillis(j2);
        return a(b2);
    }

    private String a(Calendar calendar) {
        return ((StringBuilder) a(calendar, (Calendar) new StringBuilder(this.b))).toString();
    }

    private Calendar b() {
        return Calendar.getInstance(this.mTimeZone, this.mLocale);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Date date) {
        Calendar b2 = b();
        b2.setTime(date);
        return a(b2);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Calendar calendar) {
        return ((StringBuilder) format(calendar, (Calendar) new StringBuilder(this.b))).toString();
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(long j2, StringBuffer stringBuffer) {
        Calendar b2 = b();
        b2.setTimeInMillis(j2);
        return (StringBuffer) a(b2, (Calendar) stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        Calendar b2 = b();
        b2.setTime(date);
        return (StringBuffer) a(b2, (Calendar) stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        return format(calendar.getTime(), stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(long j2, B b2) {
        Calendar b3 = b();
        b3.setTimeInMillis(j2);
        return (B) a(b3, (Calendar) b2);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Date date, B b2) {
        Calendar b3 = b();
        b3.setTime(date);
        return (B) a(b3, (Calendar) b2);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b2) {
        if (!calendar.getTimeZone().equals(this.mTimeZone)) {
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return (B) a(calendar, (Calendar) b2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        return (StringBuffer) a(calendar, (Calendar) stringBuffer);
    }

    private <B extends Appendable> B a(Calendar calendar, B b2) {
        try {
            for (f fVar : this.a) {
                fVar.a(b2, calendar);
            }
        } catch (IOException e2) {
            ExceptionUtils.rethrow(e2);
        }
        return b2;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.mPattern;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDatePrinter)) {
            return false;
        }
        FastDatePrinter fastDatePrinter = (FastDatePrinter) obj;
        return this.mPattern.equals(fastDatePrinter.mPattern) && this.mTimeZone.equals(fastDatePrinter.mTimeZone) && this.mLocale.equals(fastDatePrinter.mLocale);
    }

    public int hashCode() {
        return this.mPattern.hashCode() + ((this.mTimeZone.hashCode() + (this.mLocale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDatePrinter[" + this.mPattern + Constants.ACCEPT_TIME_SEPARATOR_SP + this.mLocale + Constants.ACCEPT_TIME_SEPARATOR_SP + this.mTimeZone.getID() + "]";
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Appendable appendable, int i2) throws IOException {
        appendable.append((char) ((i2 / 10) + 48));
        appendable.append((char) ((i2 % 10) + 48));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Appendable appendable, int i2, int i3) throws IOException {
        if (i2 < 10000) {
            int i4 = 4;
            if (i2 < 1000) {
                i4 = 3;
                if (i2 < 100) {
                    i4 = 2;
                    if (i2 < 10) {
                        i4 = 1;
                    }
                }
            }
            for (int i5 = i3 - i4; i5 > 0; i5--) {
                appendable.append('0');
            }
            switch (i4) {
                case 1:
                    break;
                case 4:
                    appendable.append((char) ((i2 / 1000) + 48));
                    i2 %= 1000;
                case 3:
                    if (i2 >= 100) {
                        appendable.append((char) ((i2 / 100) + 48));
                        i2 %= 100;
                    } else {
                        appendable.append('0');
                    }
                case 2:
                    if (i2 >= 10) {
                        appendable.append((char) ((i2 / 10) + 48));
                        i2 %= 10;
                        break;
                    } else {
                        appendable.append('0');
                        break;
                    }
                default:
                    return;
            }
            appendable.append((char) (i2 + 48));
            return;
        }
        char[] cArr = new char[10];
        int i6 = 0;
        while (i2 != 0) {
            i6++;
            cArr[i6] = (char) ((i2 % 10) + 48);
            i2 /= 10;
        }
        while (i6 < i3) {
            appendable.append('0');
            i3--;
        }
        while (true) {
            i6--;
            if (i6 >= 0) {
                appendable.append(cArr[i6]);
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a implements f {
        private final char a;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 1;
        }

        a(char c) {
            this.a = c;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class g implements f {
        private final String a;

        g(String str) {
            this.a = str;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.length();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class h implements f {
        private final int a;
        private final String[] b;

        h(int i, String[] strArr) {
            this.a = i;
            this.b = strArr;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            int length = this.b.length;
            int i = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                int length2 = this.b[length].length();
                if (length2 > i) {
                    i = length2;
                }
            }
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.b[calendar.get(this.a)]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class r implements d {
        private final int a;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 4;
        }

        r(int i) {
            this.a = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            if (i < 10) {
                appendable.append((char) (i + 48));
            } else if (i < 100) {
                FastDatePrinter.b(appendable, i);
            } else {
                FastDatePrinter.b(appendable, i, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class q implements d {
        static final q a = new q();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        q() {
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            if (i < 10) {
                appendable.append((char) (i + 48));
            } else {
                FastDatePrinter.b(appendable, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class e implements d {
        private final int a;
        private final int b;

        e(int i, int i2) {
            if (i2 >= 3) {
                this.a = i;
                this.b = i2;
                return;
            }
            throw new IllegalArgumentException();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.b;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            FastDatePrinter.b(appendable, i, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class o implements d {
        private final int a;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        o(int i) {
            this.a = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            if (i < 100) {
                FastDatePrinter.b(appendable, i);
            } else {
                FastDatePrinter.b(appendable, i, 2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class p implements d {
        static final p a = new p();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        p() {
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(1) % 100);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            FastDatePrinter.b(appendable, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class n implements d {
        static final n a = new n();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 2;
        }

        n() {
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            a(appendable, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public final void a(Appendable appendable, int i) throws IOException {
            FastDatePrinter.b(appendable, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class l implements d {
        private final d a;

        l(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(10);
            if (i == 0) {
                i = calendar.getLeastMaximum(10) + 1;
            }
            this.a.a(appendable, i);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class m implements d {
        private final d a;

        m(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(11);
            if (i == 0) {
                i = calendar.getMaximum(11) + 1;
            }
            this.a.a(appendable, i);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class b implements d {
        private final d a;

        b(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = 7;
            int i2 = calendar.get(7);
            d dVar = this.a;
            if (i2 != 1) {
                i = i2 - 1;
            }
            dVar.a(appendable, i);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class s implements d {
        private final d a;

        s(d dVar) {
            this.a = dVar;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            this.a.a(appendable, a.b(calendar));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.d
        public void a(Appendable appendable, int i) throws IOException {
            this.a.a(appendable, i);
        }
    }

    static String a(TimeZone timeZone, boolean z, int i2, Locale locale) {
        i iVar = new i(timeZone, z, i2, locale);
        String str = c.get(iVar);
        if (str != null) {
            return str;
        }
        String displayName = timeZone.getDisplayName(z, i2, locale);
        String putIfAbsent = c.putIfAbsent(iVar, displayName);
        return putIfAbsent != null ? putIfAbsent : displayName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class j implements f {
        private final Locale a;
        private final int b;
        private final String c;
        private final String d;

        j(TimeZone timeZone, Locale locale, int i) {
            this.a = locale;
            this.b = i;
            this.c = FastDatePrinter.a(timeZone, false, i, locale);
            this.d = FastDatePrinter.a(timeZone, true, i, locale);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return Math.max(this.c.length(), this.d.length());
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            TimeZone timeZone = calendar.getTimeZone();
            if (calendar.get(16) != 0) {
                appendable.append(FastDatePrinter.a(timeZone, true, this.b, this.a));
            } else {
                appendable.append(FastDatePrinter.a(timeZone, false, this.b, this.a));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class k implements f {
        static final k a = new k(true);
        static final k b = new k(false);
        final boolean c;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return 5;
        }

        k(boolean z) {
            this.c = z;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(15) + calendar.get(16);
            if (i < 0) {
                appendable.append('-');
                i = -i;
            } else {
                appendable.append('+');
            }
            int i2 = i / 3600000;
            FastDatePrinter.b(appendable, i2);
            if (this.c) {
                appendable.append(':');
            }
            FastDatePrinter.b(appendable, (i / 60000) - (i2 * 60));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class c implements f {
        static final c a = new c(3);
        static final c b = new c(5);
        static final c c = new c(6);
        final int d;

        static c a(int i) {
            switch (i) {
                case 1:
                    return a;
                case 2:
                    return b;
                case 3:
                    return c;
                default:
                    throw new IllegalArgumentException("invalid number of X");
            }
        }

        c(int i) {
            this.d = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public int a() {
            return this.d;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.f
        public void a(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(15) + calendar.get(16);
            if (i == 0) {
                appendable.append("Z");
                return;
            }
            if (i < 0) {
                appendable.append('-');
                i = -i;
            } else {
                appendable.append('+');
            }
            int i2 = i / 3600000;
            FastDatePrinter.b(appendable, i2);
            int i3 = this.d;
            if (i3 >= 5) {
                if (i3 == 6) {
                    appendable.append(':');
                }
                FastDatePrinter.b(appendable, (i / 60000) - (i2 * 60));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class i {
        private final TimeZone a;
        private final int b;
        private final Locale c;

        i(TimeZone timeZone, boolean z, int i, Locale locale) {
            this.a = timeZone;
            if (z) {
                this.b = Integer.MIN_VALUE | i;
            } else {
                this.b = i;
            }
            this.c = locale;
        }

        public int hashCode() {
            return (((this.b * 31) + this.c.hashCode()) * 31) + this.a.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof i)) {
                return false;
            }
            i iVar = (i) obj;
            return this.a.equals(iVar.a) && this.b == iVar.b && this.c.equals(iVar.c);
        }
    }
}
