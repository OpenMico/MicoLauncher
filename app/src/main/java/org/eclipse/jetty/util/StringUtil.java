package org.eclipse.jetty.util;

import com.alibaba.fastjson.parser.JSONLexer;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.base.Ascii;
import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import kotlin.text.Typography;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class StringUtil {
    public static final String ALL_INTERFACES = "0.0.0.0";
    public static final String CRLF = "\r\n";
    public static final String __ISO_8859_1 = "ISO-8859-1";
    public static final String __UTF16 = "UTF-16";
    public static final String __UTF8 = "UTF-8";
    public static final String __UTF8Alt = "UTF8";
    private static final Logger LOG = Log.getLogger(StringUtil.class);
    public static final String __LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    public static final Charset __UTF8_CHARSET = Charset.forName("UTF-8");
    public static final Charset __ISO_8859_1_CHARSET = Charset.forName("ISO-8859-1");
    private static char[] lowercases = {0, 1, 2, 3, 4, 5, 6, 7, '\b', '\t', '\n', 11, '\f', '\r', 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, JSONLexer.EOI, 27, 28, 29, 30, 31, ' ', '!', '\"', '#', '$', '%', Typography.amp, '\'', '(', ')', '*', '+', io.netty.util.internal.StringUtil.COMMA, '-', '.', JsonPointer.SEPARATOR, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', Typography.less, '=', Typography.greater, '?', '@', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', Ascii.MAX};

    public static String nonNull(String str) {
        return str == null ? "" : str;
    }

    public static String asciiToLowerCase(String str) {
        int i;
        char[] cArr;
        char c;
        int length = str.length();
        while (true) {
            i = length - 1;
            if (length <= 0) {
                cArr = null;
                break;
            }
            char charAt = str.charAt(i);
            if (charAt <= 127 && charAt != (c = lowercases[charAt])) {
                cArr = str.toCharArray();
                cArr[i] = c;
                break;
            }
            length = i;
        }
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            if (cArr[i2] <= 127) {
                cArr[i2] = lowercases[cArr[i2]];
            }
            i = i2;
        }
        return cArr == null ? str : new String(cArr);
    }

    public static boolean startsWithIgnoreCase(String str, String str2) {
        if (str2 == null) {
            return true;
        }
        if (str == null || str.length() < str2.length()) {
            return false;
        }
        for (int i = 0; i < str2.length(); i++) {
            char charAt = str.charAt(i);
            char charAt2 = str2.charAt(i);
            if (charAt != charAt2) {
                if (charAt <= 127) {
                    charAt = lowercases[charAt];
                }
                if (charAt2 <= 127) {
                    charAt2 = lowercases[charAt2];
                }
                if (charAt != charAt2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean endsWithIgnoreCase(String str, String str2) {
        int length;
        if (str2 == null) {
            return true;
        }
        if (str == null || (r2 = str.length()) < (length = str2.length())) {
            return false;
        }
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            int length2 = length2 - 1;
            char charAt = str.charAt(length2);
            char charAt2 = str2.charAt(i);
            if (charAt != charAt2) {
                if (charAt <= 127) {
                    charAt = lowercases[charAt];
                }
                if (charAt2 <= 127) {
                    charAt2 = lowercases[charAt2];
                }
                if (charAt != charAt2) {
                    return false;
                }
            }
            length = i;
        }
    }

    public static int indexFrom(String str, String str2) {
        for (int i = 0; i < str.length(); i++) {
            if (str2.indexOf(str.charAt(i)) >= 0) {
                return i;
            }
        }
        return -1;
    }

    public static String replace(String str, String str2, String str3) {
        int i = 0;
        int indexOf = str.indexOf(str2, 0);
        if (indexOf == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + str3.length());
        do {
            sb.append(str.substring(i, indexOf));
            sb.append(str3);
            i = str2.length() + indexOf;
            indexOf = str.indexOf(str2, i);
        } while (indexOf != -1);
        if (i < str.length()) {
            sb.append(str.substring(i, str.length()));
        }
        return sb.toString();
    }

    public static String unquote(String str) {
        return QuotedStringTokenizer.unquote(str);
    }

    public static void append(StringBuilder sb, String str, int i, int i2) {
        synchronized (sb) {
            int i3 = i2 + i;
            while (i < i3) {
                if (i >= str.length()) {
                    break;
                }
                sb.append(str.charAt(i));
                i++;
            }
        }
    }

    public static void append(StringBuilder sb, byte b, int i) {
        int i2 = b & 255;
        int i3 = ((i2 / i) % i) + 48;
        if (i3 > 57) {
            i3 = ((i3 - 48) - 10) + 97;
        }
        sb.append((char) i3);
        int i4 = (i2 % i) + 48;
        if (i4 > 57) {
            i4 = ((i4 - 48) - 10) + 97;
        }
        sb.append((char) i4);
    }

    public static void append2digits(StringBuffer stringBuffer, int i) {
        if (i < 100) {
            stringBuffer.append((char) ((i / 10) + 48));
            stringBuffer.append((char) ((i % 10) + 48));
        }
    }

    public static void append2digits(StringBuilder sb, int i) {
        if (i < 100) {
            sb.append((char) ((i / 10) + 48));
            sb.append((char) ((i % 10) + 48));
        }
    }

    public static boolean equals(String str, char[] cArr, int i, int i2) {
        if (str.length() != i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (cArr[i + i3] != str.charAt(i3)) {
                return false;
            }
        }
        return true;
    }

    public static String toUTF8String(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toString(byte[] bArr, int i, int i2, String str) {
        try {
            return new String(bArr, i, i2, str);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean isUTF8(String str) {
        return "UTF-8".equalsIgnoreCase(str) || __UTF8Alt.equalsIgnoreCase(str);
    }

    public static String printable(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!Character.isISOControl(charAt)) {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static String printable(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            char c = (char) bArr[i];
            if (Character.isWhitespace(c) || (c > ' ' && c < 127)) {
                sb.append(c);
            } else {
                sb.append("0x");
                TypeUtil.toHex(bArr[i], sb);
            }
        }
        return sb.toString();
    }

    public static byte[] getBytes(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (Exception e) {
            LOG.warn(e);
            return str.getBytes();
        }
    }

    public static byte[] getBytes(String str, String str2) {
        try {
            return str.getBytes(str2);
        } catch (Exception e) {
            LOG.warn(e);
            return str.getBytes();
        }
    }

    public static String sidBytesToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("S-");
        sb.append(Byte.toString(bArr[0]));
        sb.append('-');
        StringBuilder sb2 = new StringBuilder();
        for (int i = 2; i <= 7; i++) {
            sb2.append(Integer.toHexString(bArr[i] & 255));
        }
        sb.append(Long.parseLong(sb2.toString(), 16));
        byte b = bArr[1];
        for (int i2 = 0; i2 < b; i2++) {
            int i3 = i2 * 4;
            sb2.setLength(0);
            sb2.append(String.format("%02X%02X%02X%02X", Integer.valueOf(bArr[i3 + 11] & 255), Integer.valueOf(bArr[i3 + 10] & 255), Integer.valueOf(bArr[i3 + 9] & 255), Integer.valueOf(bArr[i3 + 8] & 255)));
            sb.append('-');
            sb.append(Long.parseLong(sb2.toString(), 16));
        }
        return sb.toString();
    }

    public static byte[] sidStringToBytes(String str) {
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        int length = split.length - 3;
        byte[] bArr = new byte[(length * 4) + 8];
        int i = 0;
        bArr[0] = (byte) Integer.parseInt(split[1]);
        bArr[1] = (byte) length;
        int i2 = 2;
        String hexString = Long.toHexString(Long.parseLong(split[2]));
        while (hexString.length() < 12) {
            hexString = "0" + hexString;
        }
        while (i < hexString.length()) {
            i2++;
            int i3 = i + 2;
            bArr[i2] = (byte) Integer.parseInt(hexString.substring(i, i3), 16);
            i = i3;
        }
        for (int i4 = 3; i4 < split.length; i4++) {
            String hexString2 = Long.toHexString(Long.parseLong(split[i4]));
            while (hexString2.length() < 8) {
                hexString2 = "0" + hexString2;
            }
            int length2 = hexString2.length();
            while (length2 > 0) {
                i2++;
                int i5 = length2 - 2;
                bArr[i2] = (byte) Integer.parseInt(hexString2.substring(i5, length2), 16);
                length2 = i5;
            }
        }
        return bArr;
    }
}
