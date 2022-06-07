package io.netty.util;

import com.xiaomi.mipush.sdk.Constants;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.StringTokenizer;

/* loaded from: classes4.dex */
public final class NetUtil {
    public static final InetAddress LOCALHOST;
    public static final Inet4Address LOCALHOST4;
    public static final Inet6Address LOCALHOST6;
    public static final NetworkInterface LOOPBACK_IF;
    public static final int SOMAXCONN;
    private static final boolean a = Boolean.getBoolean("java.net.preferIPv4Stack");
    private static final InternalLogger b = InternalLoggerFactory.getInstance(NetUtil.class);

    private static boolean a(int i, int i2, int i3) {
        return i >= i2 && i < i3;
    }

    private static boolean b(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    private static boolean c(char c) {
        return c >= '0' && c <= '9';
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b0, code lost:
        r7 = r5.nextElement();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b6, code lost:
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0114, code lost:
        if (r7 == 0) goto L_0x0116;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f1  */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.net.InetAddress] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.net.InetAddress] */
    static {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.NetUtil.<clinit>():void");
    }

    public static boolean isIpV4StackPreferred() {
        return a;
    }

    public static byte[] createByteArrayFromIpAddressString(String str) {
        int i = 0;
        if (isValidIpV4Address(str)) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
            byte[] bArr = new byte[4];
            while (i < 4) {
                bArr[i] = (byte) Integer.parseInt(stringTokenizer.nextToken());
                i++;
            }
            return bArr;
        } else if (!isValidIpV6Address(str)) {
            return null;
        } else {
            if (str.charAt(0) == '[') {
                str = str.substring(1, str.length() - 1);
            }
            int indexOf = str.indexOf(37);
            if (indexOf >= 0) {
                str = str.substring(0, indexOf);
            }
            StringTokenizer stringTokenizer2 = new StringTokenizer(str, ":.", true);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            String str2 = "";
            String str3 = "";
            int i2 = -1;
            while (stringTokenizer2.hasMoreTokens()) {
                String nextToken = stringTokenizer2.nextToken();
                if (Constants.COLON_SEPARATOR.equals(nextToken)) {
                    if (Constants.COLON_SEPARATOR.equals(str2)) {
                        i2 = arrayList.size();
                        str3 = str2;
                        str2 = nextToken;
                    } else if (!str2.isEmpty()) {
                        arrayList.add(str2);
                    }
                } else if (".".equals(nextToken)) {
                    arrayList2.add(str2);
                }
                str3 = str2;
                str2 = nextToken;
            }
            if (Constants.COLON_SEPARATOR.equals(str3)) {
                if (Constants.COLON_SEPARATOR.equals(str2)) {
                    i2 = arrayList.size();
                } else {
                    arrayList.add(str2);
                }
            } else if (".".equals(str3)) {
                arrayList2.add(str2);
            }
            int i3 = 8;
            if (!arrayList2.isEmpty()) {
                i3 = 6;
            }
            if (i2 != -1) {
                int size = i3 - arrayList.size();
                for (int i4 = 0; i4 < size; i4++) {
                    arrayList.add(i2, "0");
                }
            }
            byte[] bArr2 = new byte[16];
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                a((String) arrayList.get(i5), bArr2, i5 << 1);
            }
            while (i < arrayList2.size()) {
                bArr2[i + 12] = (byte) (Integer.parseInt((String) arrayList2.get(i)) & 255);
                i++;
            }
            return bArr2;
        }
    }

    private static void a(String str, byte[] bArr, int i) {
        int i2;
        int length = str.length();
        int i3 = 0;
        bArr[i] = 0;
        int i4 = i + 1;
        bArr[i4] = 0;
        if (length > 3) {
            bArr[i] = (byte) ((a(str.charAt(0)) << 4) | bArr[i]);
            i3 = 1;
        }
        if (length > 2) {
            i3++;
            bArr[i] = (byte) (a(str.charAt(i3)) | bArr[i]);
        }
        if (length > 1) {
            i2 = i3 + 1;
            bArr[i4] = (byte) ((a(str.charAt(i3)) << 4) | bArr[i4]);
        } else {
            i2 = i3;
        }
        bArr[i4] = (byte) ((a(str.charAt(i2)) & 15) | bArr[i4]);
    }

    private static int a(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                switch (Character.toLowerCase(c)) {
                    case 'a':
                        return 10;
                    case 'b':
                        return 11;
                    case 'c':
                        return 12;
                    case 'd':
                        return 13;
                    case 'e':
                        return 14;
                    case 'f':
                        return 15;
                    default:
                        return 0;
                }
        }
    }

    public static String intToIpAddress(int i) {
        StringBuilder sb = new StringBuilder(15);
        sb.append((i >> 24) & 255);
        sb.append('.');
        sb.append((i >> 16) & 255);
        sb.append('.');
        sb.append((i >> 8) & 255);
        sb.append('.');
        sb.append(i & 255);
        return sb.toString();
    }

    public static String bytesToIpAddress(byte[] bArr, int i, int i2) {
        if (i2 == 4) {
            StringBuilder sb = new StringBuilder(15);
            int i3 = i + 1;
            sb.append((bArr[i] >> 24) & 255);
            sb.append('.');
            int i4 = i3 + 1;
            sb.append((bArr[i3] >> 16) & 255);
            sb.append('.');
            sb.append((bArr[i4] >> 8) & 255);
            sb.append('.');
            sb.append(bArr[i4 + 1] & 255);
            return sb.toString();
        } else if (i2 == 16) {
            StringBuilder sb2 = new StringBuilder(39);
            int i5 = i + 14;
            while (i < i5) {
                StringUtil.toHexString(sb2, bArr, i, 2);
                sb2.append(':');
                i += 2;
            }
            StringUtil.toHexString(sb2, bArr, i, 2);
            return sb2.toString();
        } else {
            throw new IllegalArgumentException("length: " + i2 + " (expected: 4 or 16)");
        }
    }

    public static boolean isValidIpV6Address(String str) {
        int i;
        int i2;
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int length2 = str.length();
        if (length2 < 2) {
            return false;
        }
        if (str.charAt(0) != '[') {
            i = length2;
            i2 = 0;
        } else if (str.charAt(length2 - 1) != ']') {
            return false;
        } else {
            i = length2 - 1;
            i2 = 1;
        }
        int indexOf = str.indexOf(37, i2);
        if (indexOf >= 0) {
            i = indexOf;
        }
        int i3 = i2;
        int i4 = 0;
        char c = 0;
        int i5 = 0;
        boolean z = false;
        while (i3 < i) {
            char charAt = str.charAt(i3);
            if (charAt == '.') {
                i4++;
                if (i4 > 3 || !a(sb.toString())) {
                    return false;
                }
                if (i5 != 6 && !z) {
                    return false;
                }
                if (i5 == 7 && str.charAt(i2) != ':' && str.charAt(i2 + 1) != ':') {
                    return false;
                }
                sb.delete(0, sb.length());
            } else if (charAt != ':') {
                if (sb.length() > 3 || !b(charAt)) {
                    return false;
                }
                sb.append(charAt);
            } else if ((i3 == i2 && (str.length() <= i3 || str.charAt(i3 + 1) != ':')) || (i5 = i5 + 1) > 7 || i4 > 0) {
                return false;
            } else {
                if (c == ':') {
                    if (z) {
                        return false;
                    }
                    z = true;
                }
                sb.delete(0, sb.length());
            }
            i3++;
            c = charAt;
        }
        if (i4 > 0) {
            return i4 == 3 && a(sb.toString()) && i5 < 7;
        }
        if (i5 == 7 || z) {
            return (sb.length() == 0 && str.charAt((length + (-1)) - i2) == ':' && str.charAt((length - 2) - i2) != ':') ? false : true;
        }
        return false;
    }

    private static boolean a(String str) {
        if (str.length() < 1 || str.length() > 3) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return false;
            }
        }
        return Integer.parseInt(str) <= 255;
    }

    public static boolean isValidIpV4Address(String str) {
        int length = str.length();
        if (length > 15) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '.') {
                i++;
                if (i > 3 || sb.length() == 0 || Integer.parseInt(sb.toString()) > 255) {
                    return false;
                }
                sb.delete(0, sb.length());
            } else if (!Character.isDigit(charAt) || sb.length() > 2) {
                return false;
            } else {
                sb.append(charAt);
            }
        }
        return sb.length() != 0 && Integer.parseInt(sb.toString()) <= 255 && i == 3;
    }

    public static Inet6Address getByName(CharSequence charSequence) {
        return getByName(charSequence, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x017c, code lost:
        if (r16.charAt(0) == ':') goto L_0x0182;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.net.Inet6Address getByName(java.lang.CharSequence r16, boolean r17) {
        /*
            Method dump skipped, instructions count: 709
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.NetUtil.getByName(java.lang.CharSequence, boolean):java.net.Inet6Address");
    }

    public static String toAddressString(InetAddress inetAddress) {
        return toAddressString(inetAddress, false);
    }

    public static String toAddressString(InetAddress inetAddress, boolean z) {
        int i;
        int i2;
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        if (inetAddress instanceof Inet6Address) {
            byte[] address = inetAddress.getAddress();
            int[] iArr = new int[8];
            boolean z2 = false;
            int i3 = 0;
            while (true) {
                i = 1;
                if (i3 >= iArr.length) {
                    break;
                }
                int i4 = i3 << 1;
                iArr[i3] = (address[i4 + 1] & 255) | ((address[i4] & 255) << 8);
                i3++;
            }
            int i5 = -1;
            i5 = -1;
            i5 = -1;
            int i6 = 0;
            int i7 = 0;
            while (i6 < iArr.length) {
                if (iArr[i6] == 0) {
                    if (i5 < 0) {
                        i5 = i6;
                    }
                } else if (i5 >= 0) {
                    int i8 = i6 - i5;
                    if (i8 > i7) {
                        i7 = i8;
                    } else {
                        i5 = i5;
                    }
                    i5 = -1;
                }
                i6++;
            }
            if (i5 < 0 || (i2 = i6 - i5) <= i7) {
                i2 = i7;
                i5 = i5;
            }
            if (i2 == 1) {
                i2 = 0;
            }
            int i9 = i2 + i5;
            StringBuilder sb = new StringBuilder(39);
            if (i9 < 0) {
                sb.append(Integer.toHexString(iArr[0]));
                while (i < iArr.length) {
                    sb.append(':');
                    sb.append(Integer.toHexString(iArr[i]));
                    i++;
                }
            } else {
                if (a(0, i5, i9)) {
                    sb.append("::");
                    if (z && i9 == 5 && iArr[5] == 65535) {
                        z2 = true;
                    }
                } else {
                    sb.append(Integer.toHexString(iArr[0]));
                }
                while (i < iArr.length) {
                    if (!a(i, i5, i9)) {
                        if (!a(i - 1, i5, i9)) {
                            if (!z2 || i == 6) {
                                sb.append(':');
                            } else {
                                sb.append('.');
                            }
                        }
                        if (!z2 || i <= 5) {
                            sb.append(Integer.toHexString(iArr[i]));
                        } else {
                            sb.append(iArr[i] >> 8);
                            sb.append('.');
                            sb.append(iArr[i] & 255);
                        }
                    } else if (!a(i - 1, i5, i9)) {
                        sb.append("::");
                    }
                    i++;
                }
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("Unhandled type: " + inetAddress.getClass());
    }

    private NetUtil() {
    }
}
