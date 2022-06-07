package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class ColognePhonetic implements StringEncoder {
    private static final char[] a = {'A', 'E', 'I', 'J', 'O', 'U', 'Y'};
    private static final char[] b = {'S', 'C', 'Z'};
    private static final char[] c = {'W', 'F', 'P', 'V'};
    private static final char[] d = {'G', 'K', 'Q'};
    private static final char[] e = {'C', 'K', 'Q'};
    private static final char[] f = {'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'};
    private static final char[] g = {'S', 'Z'};
    private static final char[] h = {'A', 'H', 'O', 'U', 'K', 'Q', 'X'};
    private static final char[] i = {'T', 'D', 'X'};
    private static final char[][] j = {new char[]{196, 'A'}, new char[]{220, 'U'}, new char[]{214, 'O'}, new char[]{223, 'S'}};

    /* loaded from: classes5.dex */
    private abstract class a {
        protected final char[] a;
        protected int b;

        protected abstract char[] a(int i, int i2);

        public a(char[] cArr) {
            this.b = 0;
            this.a = cArr;
            this.b = cArr.length;
        }

        public a(int i) {
            this.b = 0;
            this.a = new char[i];
            this.b = 0;
        }

        public int a() {
            return this.b;
        }

        public String toString() {
            return new String(a(0, this.b));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class c extends a {
        public c(int i) {
            super(i);
        }

        public void a(char c) {
            this.a[this.b] = c;
            this.b++;
        }

        @Override // org.apache.commons.codec.language.ColognePhonetic.a
        protected char[] a(int i, int i2) {
            char[] cArr = new char[i2];
            System.arraycopy(this.a, i, cArr, 0, i2);
            return cArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class b extends a {
        public b(char[] cArr) {
            super(cArr);
        }

        public void a(char c) {
            this.b++;
            this.a[c()] = c;
        }

        @Override // org.apache.commons.codec.language.ColognePhonetic.a
        protected char[] a(int i, int i2) {
            char[] cArr = new char[i2];
            System.arraycopy(this.a, (this.a.length - this.b) + i, cArr, 0, i2);
            return cArr;
        }

        public char b() {
            return this.a[c()];
        }

        protected int c() {
            return this.a.length - this.b;
        }

        public char d() {
            this.b--;
            return b();
        }
    }

    private static boolean a(char[] cArr, char c2) {
        for (char c3 : cArr) {
            if (c3 == c2) {
                return true;
            }
        }
        return false;
    }

    public String colognePhonetic(String str) {
        char c2;
        if (str == null) {
            return null;
        }
        String a2 = a(str);
        c cVar = new c(a2.length() * 2);
        b bVar = new b(a2.toCharArray());
        int a3 = bVar.a();
        char c3 = '/';
        char c4 = '-';
        while (a3 > 0) {
            char d2 = bVar.d();
            a3 = bVar.a();
            char b2 = a3 > 0 ? bVar.b() : '-';
            if (a(a, d2)) {
                c2 = '0';
            } else if (d2 == 'H' || d2 < 'A' || d2 > 'Z') {
                if (c3 == '/') {
                    a3 = a3;
                } else {
                    c2 = '-';
                }
            } else if (d2 == 'B' || (d2 == 'P' && b2 != 'H')) {
                c2 = '1';
            } else if ((d2 == 'D' || d2 == 'T') && !a(b, b2)) {
                c2 = '2';
            } else if (a(c, d2)) {
                c2 = '3';
            } else if (a(d, d2)) {
                c2 = '4';
            } else if (d2 == 'X' && !a(e, c4)) {
                bVar.a('S');
                a3++;
                c2 = '4';
            } else if (d2 == 'S' || d2 == 'Z') {
                c2 = '8';
            } else if (d2 != 'C') {
                c2 = a(i, d2) ? '8' : d2 == 'R' ? '7' : d2 == 'L' ? '5' : (d2 == 'M' || d2 == 'N') ? '6' : d2;
            } else if (c3 == '/') {
                c2 = a(f, b2) ? '4' : '8';
            } else {
                c2 = (a(g, c4) || !a(h, b2)) ? '8' : '4';
            }
            if (c2 != '-' && ((c3 != c2 && (c2 != '0' || c3 == '/')) || c2 < '0' || c2 > '8')) {
                cVar.a(c2);
            }
            c3 = c2;
            c4 = d2;
        }
        return cVar.toString();
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + obj.getClass().getName() + ".");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return colognePhonetic(str);
    }

    public boolean isEncodeEqual(String str, String str2) {
        return colognePhonetic(str).equals(colognePhonetic(str2));
    }

    private String a(String str) {
        char[] charArray = str.toUpperCase(Locale.GERMAN).toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] > 'Z') {
                char[][] cArr = j;
                int length = cArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        char[] cArr2 = cArr[i3];
                        if (charArray[i2] == cArr2[0]) {
                            charArray[i2] = cArr2[1];
                            break;
                        }
                        i3++;
                    }
                }
            }
        }
        return new String(charArray);
    }
}
