package org.apache.commons.lang;

/* loaded from: classes5.dex */
public class CharUtils {
    public static final char CR = '\r';
    public static final char LF = '\n';
    private static final String[] a = new String[128];
    private static final Character[] b = new Character[128];

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(char c) {
        return 55296 <= c && 56319 >= c;
    }

    public static boolean isAscii(char c) {
        return c < 128;
    }

    public static boolean isAsciiAlpha(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    public static boolean isAsciiAlphaLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isAsciiAlphaUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isAsciiAlphanumeric(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    public static boolean isAsciiControl(char c) {
        return c < ' ' || c == 127;
    }

    public static boolean isAsciiNumeric(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isAsciiPrintable(char c) {
        return c >= ' ' && c < 127;
    }

    static {
        for (int i = 127; i >= 0; i--) {
            a[i] = "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007f".substring(i, i + 1);
            b[i] = new Character((char) i);
        }
    }

    public static Character toCharacterObject(char c) {
        Character[] chArr = b;
        if (c < chArr.length) {
            return chArr[c];
        }
        return new Character(c);
    }

    public static Character toCharacterObject(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return toCharacterObject(str.charAt(0));
    }

    public static char toChar(Character ch) {
        if (ch != null) {
            return ch.charValue();
        }
        throw new IllegalArgumentException("The Character must not be null");
    }

    public static char toChar(Character ch, char c) {
        return ch == null ? c : ch.charValue();
    }

    public static char toChar(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str.charAt(0);
        }
        throw new IllegalArgumentException("The String must not be empty");
    }

    public static char toChar(String str, char c) {
        return StringUtils.isEmpty(str) ? c : str.charAt(0);
    }

    public static int toIntValue(char c) {
        if (isAsciiNumeric(c)) {
            return c - '0';
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("The character ");
        stringBuffer.append(c);
        stringBuffer.append(" is not in the range '0' - '9'");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static int toIntValue(char c, int i) {
        return !isAsciiNumeric(c) ? i : c - '0';
    }

    public static int toIntValue(Character ch) {
        if (ch != null) {
            return toIntValue(ch.charValue());
        }
        throw new IllegalArgumentException("The character must not be null");
    }

    public static int toIntValue(Character ch, int i) {
        return ch == null ? i : toIntValue(ch.charValue(), i);
    }

    public static String toString(char c) {
        if (c < 128) {
            return a[c];
        }
        return new String(new char[]{c});
    }

    public static String toString(Character ch) {
        if (ch == null) {
            return null;
        }
        return toString(ch.charValue());
    }

    public static String unicodeEscaped(char c) {
        if (c < 16) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\\u000");
            stringBuffer.append(Integer.toHexString(c));
            return stringBuffer.toString();
        } else if (c < 256) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("\\u00");
            stringBuffer2.append(Integer.toHexString(c));
            return stringBuffer2.toString();
        } else if (c < 4096) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\\u0");
            stringBuffer3.append(Integer.toHexString(c));
            return stringBuffer3.toString();
        } else {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("\\u");
            stringBuffer4.append(Integer.toHexString(c));
            return stringBuffer4.toString();
        }
    }

    public static String unicodeEscaped(Character ch) {
        if (ch == null) {
            return null;
        }
        return unicodeEscaped(ch.charValue());
    }
}
