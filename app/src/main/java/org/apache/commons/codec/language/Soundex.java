package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class Soundex implements StringEncoder {
    @Deprecated
    private int b;
    private final char[] c;
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    private static final char[] a = US_ENGLISH_MAPPING_STRING.toCharArray();
    public static final Soundex US_ENGLISH = new Soundex();

    public Soundex() {
        this.b = 4;
        this.c = a;
    }

    public Soundex(char[] cArr) {
        this.b = 4;
        this.c = new char[cArr.length];
        System.arraycopy(cArr, 0, this.c, 0, cArr.length);
    }

    public Soundex(String str) {
        this.b = 4;
        this.c = str.toCharArray();
    }

    public int difference(String str, String str2) throws EncoderException {
        return a.a(this, str, str2);
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return soundex((String) obj);
        }
        throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return soundex(str);
    }

    private char a(String str, int i) {
        char charAt;
        char a2 = a(str.charAt(i));
        if (i > 1 && a2 != '0' && ('H' == (charAt = str.charAt(i - 1)) || 'W' == charAt)) {
            char charAt2 = str.charAt(i - 2);
            if (a(charAt2) == a2 || 'H' == charAt2 || 'W' == charAt2) {
                return (char) 0;
            }
        }
        return a2;
    }

    @Deprecated
    public int getMaxLength() {
        return this.b;
    }

    private char[] a() {
        return this.c;
    }

    private char a(char c) {
        int i = c - 'A';
        if (i >= 0 && i < a().length) {
            return a()[i];
        }
        throw new IllegalArgumentException("The character is not mapped: " + c);
    }

    @Deprecated
    public void setMaxLength(int i) {
        this.b = i;
    }

    public String soundex(String str) {
        if (str == null) {
            return null;
        }
        String a2 = a.a(str);
        if (a2.length() == 0) {
            return a2;
        }
        char[] cArr = {'0', '0', '0', '0'};
        cArr[0] = a2.charAt(0);
        int i = 1;
        char a3 = a(a2, 0);
        int i2 = 1;
        while (i < a2.length() && i2 < cArr.length) {
            int i3 = i + 1;
            char a4 = a(a2, i);
            if (a4 != 0) {
                if (!(a4 == '0' || a4 == a3)) {
                    i2++;
                    cArr[i2] = a4;
                }
                a3 = a4;
                i = i3;
            } else {
                i = i3;
            }
        }
        return new String(cArr);
    }
}
