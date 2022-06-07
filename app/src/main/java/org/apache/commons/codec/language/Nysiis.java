package org.apache.commons.codec.language;

import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.util.regex.Pattern;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class Nysiis implements StringEncoder {
    private static final char[] a = {'A'};
    private static final char[] b = {'A', 'F'};
    private static final char[] c = {'C'};
    private static final char[] d = {'F', 'F'};
    private static final char[] e = {'G'};
    private static final char[] f = {'N'};
    private static final char[] g = {'N', 'N'};
    private static final char[] h = {'S'};
    private static final char[] i = {'S', 'S', 'S'};
    private static final Pattern j = Pattern.compile("^MAC");
    private static final Pattern k = Pattern.compile("^KN");
    private static final Pattern l = Pattern.compile("^K");
    private static final Pattern m = Pattern.compile("^(PH|PF)");
    private static final Pattern n = Pattern.compile("^SCH");
    private static final Pattern o = Pattern.compile("(EE|IE)$");
    private static final Pattern p = Pattern.compile("(DT|RT|RD|NT|ND)$");
    private final boolean q;

    private static boolean a(char c2) {
        return c2 == 'A' || c2 == 'E' || c2 == 'I' || c2 == 'O' || c2 == 'U';
    }

    private static char[] a(char c2, char c3, char c4, char c5) {
        if (c3 == 'E' && c4 == 'V') {
            return b;
        }
        if (a(c3)) {
            return a;
        }
        if (c3 == 'Q') {
            return e;
        }
        if (c3 == 'Z') {
            return h;
        }
        if (c3 == 'M') {
            return f;
        }
        if (c3 == 'K') {
            if (c4 == 'N') {
                return g;
            }
            return c;
        } else if (c3 == 'S' && c4 == 'C' && c5 == 'H') {
            return i;
        } else {
            if (c3 == 'P' && c4 == 'H') {
                return d;
            }
            return (c3 != 'H' || (a(c2) && a(c4))) ? (c3 != 'W' || !a(c2)) ? new char[]{c3} : new char[]{c2} : new char[]{c2};
        }
    }

    public Nysiis() {
        this(true);
    }

    public Nysiis(boolean z) {
        this.q = z;
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return nysiis((String) obj);
        }
        throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return nysiis(str);
    }

    public boolean isStrict() {
        return this.q;
    }

    public String nysiis(String str) {
        if (str == null) {
            return null;
        }
        String a2 = a.a(str);
        if (a2.length() == 0) {
            return a2;
        }
        String replaceFirst = p.matcher(o.matcher(n.matcher(m.matcher(l.matcher(k.matcher(j.matcher(a2).replaceFirst("MCC")).replaceFirst("NN")).replaceFirst(HomePageRecordHelper.AREA_C)).replaceFirst("FF")).replaceFirst("SSS")).replaceFirst("Y")).replaceFirst(HomePageRecordHelper.AREA_D);
        StringBuilder sb = new StringBuilder(replaceFirst.length());
        sb.append(replaceFirst.charAt(0));
        char[] charArray = replaceFirst.toCharArray();
        int length = charArray.length;
        int i2 = 1;
        while (i2 < length) {
            char c2 = ' ';
            char c3 = i2 < length + (-1) ? charArray[i2 + 1] : ' ';
            if (i2 < length - 2) {
                c2 = charArray[i2 + 2];
            }
            int i3 = i2 - 1;
            char[] a3 = a(charArray[i3], charArray[i2], c3, c2);
            System.arraycopy(a3, 0, charArray, i2, a3.length);
            if (charArray[i2] != charArray[i3]) {
                sb.append(charArray[i2]);
            }
            i2++;
        }
        if (sb.length() > 1) {
            char charAt = sb.charAt(sb.length() - 1);
            if (charAt == 'S') {
                sb.deleteCharAt(sb.length() - 1);
                charAt = sb.charAt(sb.length() - 1);
            }
            if (sb.length() > 2 && sb.charAt(sb.length() - 2) == 'A' && charAt == 'Y') {
                sb.deleteCharAt(sb.length() - 2);
            }
            if (charAt == 'A') {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        String sb2 = sb.toString();
        return isStrict() ? sb2.substring(0, Math.min(6, sb2.length())) : sb2;
    }
}
