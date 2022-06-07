package org.eclipse.jetty.util;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.UnsupportedEncodingException;

/* loaded from: classes5.dex */
public class URIUtil implements Cloneable {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String HTTPS_COLON = "https:";
    public static final String HTTP_COLON = "http:";
    public static final String SLASH = "/";
    public static final String __CHARSET = System.getProperty("org.eclipse.jetty.util.URI.charset", "UTF-8");

    private URIUtil() {
    }

    public static String encodePath(String str) {
        StringBuilder encodePath;
        return (str == null || str.length() == 0 || (encodePath = encodePath(null, str)) == null) ? str : encodePath.toString();
    }

    public static StringBuilder encodePath(StringBuilder sb, String str) {
        byte[] bArr = null;
        int i = 0;
        if (sb == null) {
            int i2 = 0;
            while (true) {
                if (i2 < str.length()) {
                    char charAt = str.charAt(i2);
                    switch (charAt) {
                        case ' ':
                        case '\"':
                        case '#':
                        case '%':
                        case '\'':
                        case ';':
                        case '<':
                        case '>':
                        case '?':
                            sb = new StringBuilder(str.length() * 2);
                            bArr = null;
                            break;
                        default:
                            if (charAt > 127) {
                                try {
                                    bArr = str.getBytes(__CHARSET);
                                    sb = new StringBuilder(str.length() * 2);
                                    break;
                                } catch (UnsupportedEncodingException e) {
                                    throw new IllegalStateException(e);
                                }
                            } else {
                                i2++;
                            }
                    }
                } else {
                    bArr = null;
                }
            }
            if (sb == null) {
                return null;
            }
        }
        synchronized (sb) {
            if (bArr != null) {
                while (i < bArr.length) {
                    byte b = bArr[i];
                    switch (b) {
                        case 32:
                            sb.append("%20");
                            break;
                        case 34:
                            sb.append("%22");
                            break;
                        case 35:
                            sb.append("%23");
                            break;
                        case 37:
                            sb.append("%25");
                            break;
                        case 39:
                            sb.append("%27");
                            break;
                        case 59:
                            sb.append("%3B");
                            break;
                        case 60:
                            sb.append("%3C");
                            break;
                        case 62:
                            sb.append("%3E");
                            break;
                        case 63:
                            sb.append("%3F");
                            break;
                        default:
                            if (b >= 0) {
                                sb.append((char) b);
                                break;
                            } else {
                                sb.append('%');
                                TypeUtil.toHex(b, sb);
                                break;
                            }
                    }
                    i++;
                }
            } else {
                while (i < str.length()) {
                    char charAt2 = str.charAt(i);
                    switch (charAt2) {
                        case ' ':
                            sb.append("%20");
                            break;
                        case '\"':
                            sb.append("%22");
                            break;
                        case '#':
                            sb.append("%23");
                            break;
                        case '%':
                            sb.append("%25");
                            break;
                        case '\'':
                            sb.append("%27");
                            break;
                        case ';':
                            sb.append("%3B");
                            break;
                        case '<':
                            sb.append("%3C");
                            break;
                        case '>':
                            sb.append("%3E");
                            break;
                        case '?':
                            sb.append("%3F");
                            break;
                        default:
                            sb.append(charAt2);
                            break;
                    }
                    i++;
                }
            }
        }
        return sb;
    }

    public static StringBuilder encodeString(StringBuilder sb, String str, String str2) {
        if (sb == null) {
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '%' || str2.indexOf(charAt) >= 0) {
                    sb = new StringBuilder(str.length() << 1);
                    break;
                }
            }
            if (sb == null) {
                return null;
            }
        }
        synchronized (sb) {
            for (int i2 = 0; i2 < str.length(); i2++) {
                char charAt2 = str.charAt(i2);
                if (charAt2 != '%' && str2.indexOf(charAt2) < 0) {
                    sb.append(charAt2);
                }
                sb.append('%');
                StringUtil.append(sb, (byte) (charAt2 & 255), 16);
            }
        }
        return sb;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x006d, code lost:
        r0 = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String decodePath(java.lang.String r11) {
        /*
            r0 = 0
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r11.length()
            r2 = 0
            r3 = r0
            r4 = r3
            r0 = r2
            r5 = r0
            r6 = r5
        L_0x000e:
            if (r0 >= r1) goto L_0x006d
            char r7 = r11.charAt(r0)
            r8 = 37
            if (r7 != r8) goto L_0x0038
            int r8 = r0 + 2
            if (r8 >= r1) goto L_0x0038
            if (r3 != 0) goto L_0x0025
            char[] r3 = new char[r1]
            byte[] r4 = new byte[r1]
            r11.getChars(r2, r0, r3, r2)
        L_0x0025:
            int r7 = r5 + 1
            int r0 = r0 + 1
            r9 = 16
            r10 = 2
            int r0 = org.eclipse.jetty.util.TypeUtil.parseInt(r11, r0, r10, r9)
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = (byte) r0
            r4[r5] = r0
            r5 = r7
            r0 = r8
            goto L_0x006a
        L_0x0038:
            r8 = 59
            if (r7 != r8) goto L_0x0044
            if (r3 != 0) goto L_0x006d
            char[] r3 = new char[r1]
            r11.getChars(r2, r0, r3, r2)
            goto L_0x006e
        L_0x0044:
            if (r4 != 0) goto L_0x0049
            int r6 = r6 + 1
            goto L_0x006a
        L_0x0049:
            if (r5 <= 0) goto L_0x0065
            java.lang.String r8 = new java.lang.String     // Catch: UnsupportedEncodingException -> 0x0053
            java.lang.String r9 = org.eclipse.jetty.util.URIUtil.__CHARSET     // Catch: UnsupportedEncodingException -> 0x0053
            r8.<init>(r4, r2, r5, r9)     // Catch: UnsupportedEncodingException -> 0x0053
            goto L_0x0058
        L_0x0053:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r4, r2, r5)
        L_0x0058:
            int r5 = r8.length()
            r8.getChars(r2, r5, r3, r6)
            int r5 = r8.length()
            int r6 = r6 + r5
            r5 = r2
        L_0x0065:
            int r8 = r6 + 1
            r3[r6] = r7
            r6 = r8
        L_0x006a:
            int r0 = r0 + 1
            goto L_0x000e
        L_0x006d:
            r0 = r6
        L_0x006e:
            if (r3 != 0) goto L_0x0071
            return r11
        L_0x0071:
            if (r5 <= 0) goto L_0x008c
            java.lang.String r11 = new java.lang.String     // Catch: UnsupportedEncodingException -> 0x007b
            java.lang.String r1 = org.eclipse.jetty.util.URIUtil.__CHARSET     // Catch: UnsupportedEncodingException -> 0x007b
            r11.<init>(r4, r2, r5, r1)     // Catch: UnsupportedEncodingException -> 0x007b
            goto L_0x0080
        L_0x007b:
            java.lang.String r11 = new java.lang.String
            r11.<init>(r4, r2, r5)
        L_0x0080:
            int r1 = r11.length()
            r11.getChars(r2, r1, r3, r0)
            int r11 = r11.length()
            int r0 = r0 + r11
        L_0x008c:
            java.lang.String r11 = new java.lang.String
            r11.<init>(r3, r2, r0)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.URIUtil.decodePath(java.lang.String):java.lang.String");
    }

    public static String decodePath(byte[] bArr, int i, int i2) {
        int i3;
        byte[] bArr2 = null;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= i2) {
                break;
            }
            int i6 = i5 + i;
            byte b = bArr[i6];
            if (b == 37 && (i3 = i5 + 2) < i2) {
                b = (byte) (TypeUtil.parseInt(bArr, i6 + 1, 2, 16) & 255);
                i5 = i3;
            } else if (b == 59) {
                i2 = i5;
                break;
            } else if (bArr2 == null) {
                i4++;
                i5++;
            }
            if (bArr2 == null) {
                bArr2 = new byte[i2];
                for (int i7 = 0; i7 < i4; i7++) {
                    bArr2[i7] = bArr[i7 + i];
                }
            }
            i4++;
            bArr2[i4] = b;
            i5++;
        }
        if (bArr2 == null) {
            return StringUtil.toString(bArr, i, i2, __CHARSET);
        }
        return StringUtil.toString(bArr2, 0, i4, __CHARSET);
    }

    public static String addPaths(String str, String str2) {
        if (str == null || str.length() == 0) {
            return (str == null || str2 != null) ? str2 : str;
        }
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        int indexOf = str.indexOf(59);
        if (indexOf < 0) {
            indexOf = str.indexOf(63);
        }
        if (indexOf == 0) {
            return str2 + str;
        }
        if (indexOf < 0) {
            indexOf = str.length();
        }
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 2);
        sb.append(str);
        int i = indexOf - 1;
        if (sb.charAt(i) == '/') {
            if (str2.startsWith("/")) {
                sb.deleteCharAt(i);
                sb.insert(i, str2);
            } else {
                sb.insert(indexOf, str2);
            }
        } else if (str2.startsWith("/")) {
            sb.insert(indexOf, str2);
        } else {
            sb.insert(indexOf, JsonPointer.SEPARATOR);
            sb.insert(indexOf + 1, str2);
        }
        return sb.toString();
    }

    public static String parentPath(String str) {
        int lastIndexOf;
        if (str == null || "/".equals(str) || (lastIndexOf = str.lastIndexOf(47, str.length() - 2)) < 0) {
            return null;
        }
        return str.substring(0, lastIndexOf + 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x012e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String canonicalPath(java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.URIUtil.canonicalPath(java.lang.String):java.lang.String");
    }

    public static String compactPath(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt == '/') {
                i2++;
                if (i2 == 2) {
                    break;
                }
            } else if (charAt == '?') {
                return str;
            } else {
                i2 = 0;
            }
            i++;
        }
        if (i2 < 2) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length());
        stringBuffer.append((CharSequence) str, 0, i);
        while (true) {
            if (i >= length) {
                break;
            }
            char charAt2 = str.charAt(i);
            if (charAt2 != '/') {
                if (charAt2 == '?') {
                    stringBuffer.append((CharSequence) str, i, length);
                    break;
                }
                stringBuffer.append(charAt2);
                i2 = 0;
            } else {
                i2++;
                if (i2 == 0) {
                    stringBuffer.append(charAt2);
                }
            }
            i++;
        }
        return stringBuffer.toString();
    }

    public static boolean hasScheme(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == ':') {
                return true;
            }
            if ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && (i <= 0 || !((charAt >= '0' && charAt <= '9') || charAt == '.' || charAt == '+' || charAt == '-')))) {
                break;
            }
        }
        return false;
    }
}
