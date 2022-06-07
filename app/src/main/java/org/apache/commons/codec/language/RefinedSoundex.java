package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class RefinedSoundex implements StringEncoder {
    private final char[] b;
    public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
    private static final char[] a = US_ENGLISH_MAPPING_STRING.toCharArray();
    public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();

    public RefinedSoundex() {
        this.b = a;
    }

    public RefinedSoundex(char[] cArr) {
        this.b = new char[cArr.length];
        System.arraycopy(cArr, 0, this.b, 0, cArr.length);
    }

    public RefinedSoundex(String str) {
        this.b = str.toCharArray();
    }

    public int difference(String str, String str2) throws EncoderException {
        return a.a(this, str, str2);
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return soundex((String) obj);
        }
        throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return soundex(str);
    }

    char a(char c) {
        if (!Character.isLetter(c)) {
            return (char) 0;
        }
        return this.b[Character.toUpperCase(c) - 'A'];
    }

    public String soundex(String str) {
        if (str == null) {
            return null;
        }
        String a2 = a.a(str);
        if (a2.length() == 0) {
            return a2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a2.charAt(0));
        char c = '*';
        for (int i = 0; i < a2.length(); i++) {
            char a3 = a(a2.charAt(i));
            if (a3 != c) {
                if (a3 != 0) {
                    sb.append(a3);
                }
                c = a3;
            }
        }
        return sb.toString();
    }
}
