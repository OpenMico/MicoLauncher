package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import com.xiaomi.mipush.sdk.Constants;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.UShort;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.eclipse.jetty.util.StringUtil;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class InetAddresses {
    private static final Splitter a = Splitter.on('.').limit(4);
    private static final Splitter b = Splitter.on(':').limit(10);
    private static final Inet4Address c = (Inet4Address) forString("127.0.0.1");
    private static final Inet4Address d = (Inet4Address) forString(StringUtil.ALL_INTERFACES);

    private InetAddresses() {
    }

    private static Inet4Address a(byte[] bArr) {
        Preconditions.checkArgument(bArr.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", bArr.length);
        return (Inet4Address) b(bArr);
    }

    public static InetAddress forString(String str) {
        byte[] a2 = a(str);
        if (a2 != null) {
            return b(a2);
        }
        throw a("'%s' is not an IP string literal.", str);
    }

    public static boolean isInetAddress(String str) {
        return a(str) != null;
    }

    @NullableDecl
    private static byte[] a(String str) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '.') {
                z2 = true;
            } else if (charAt == ':') {
                if (z2) {
                    return null;
                }
                z = true;
            } else if (Character.digit(charAt, 16) == -1) {
                return null;
            }
        }
        if (z) {
            if (!z2 || (str = d(str)) != null) {
                return c(str);
            }
            return null;
        } else if (z2) {
            return b(str);
        } else {
            return null;
        }
    }

    @NullableDecl
    private static byte[] b(String str) {
        byte[] bArr = new byte[4];
        try {
            int i = 0;
            for (String str2 : a.split(str)) {
                i++;
                bArr[i] = e(str2);
            }
            if (i == 4) {
                return bArr;
            }
            return null;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    @NullableDecl
    private static byte[] c(String str) {
        int i;
        int i2;
        List<String> splitToList = b.splitToList(str);
        if (splitToList.size() < 3 || splitToList.size() > 9) {
            return null;
        }
        int i3 = -1;
        for (int i4 = 1; i4 < splitToList.size() - 1; i4++) {
            if (splitToList.get(i4).length() == 0) {
                if (i3 >= 0) {
                    return null;
                }
                i3 = i4;
            }
        }
        if (i3 >= 0) {
            i2 = (splitToList.size() - i3) - 1;
            if (splitToList.get(0).length() == 0) {
                i = i3 - 1;
                if (i != 0) {
                    return null;
                }
            } else {
                i = i3;
            }
            if (((String) Iterables.getLast(splitToList)).length() == 0 && i2 - 1 != 0) {
                return null;
            }
        } else {
            i = splitToList.size();
            i2 = 0;
        }
        int i5 = 8 - (i + i2);
        if (i3 < 0 ? i5 != 0 : i5 < 1) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(16);
        for (int i6 = 0; i6 < i; i6++) {
            try {
                allocate.putShort(f(splitToList.get(i6)));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        for (int i7 = 0; i7 < i5; i7++) {
            allocate.putShort((short) 0);
        }
        while (i2 > 0) {
            allocate.putShort(f(splitToList.get(splitToList.size() - i2)));
            i2--;
        }
        return allocate.array();
    }

    @NullableDecl
    private static String d(String str) {
        int lastIndexOf = str.lastIndexOf(58) + 1;
        String substring = str.substring(0, lastIndexOf);
        byte[] b2 = b(str.substring(lastIndexOf));
        if (b2 == null) {
            return null;
        }
        String hexString = Integer.toHexString(((b2[0] & 255) << 8) | (b2[1] & 255));
        String hexString2 = Integer.toHexString((b2[3] & 255) | ((b2[2] & 255) << 8));
        return substring + hexString + Constants.COLON_SEPARATOR + hexString2;
    }

    private static byte e(String str) {
        int parseInt = Integer.parseInt(str);
        if (parseInt <= 255 && (!str.startsWith("0") || str.length() <= 1)) {
            return (byte) parseInt;
        }
        throw new NumberFormatException();
    }

    private static short f(String str) {
        int parseInt = Integer.parseInt(str, 16);
        if (parseInt <= 65535) {
            return (short) parseInt;
        }
        throw new NumberFormatException();
    }

    private static InetAddress b(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }

    public static String toAddrString(InetAddress inetAddress) {
        Preconditions.checkNotNull(inetAddress);
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        Preconditions.checkArgument(inetAddress instanceof Inet6Address);
        byte[] address = inetAddress.getAddress();
        int[] iArr = new int[8];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 2;
            iArr[i] = Ints.fromBytes((byte) 0, (byte) 0, address[i2], address[i2 + 1]);
        }
        a(iArr);
        return b(iArr);
    }

    private static void a(int[] iArr) {
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < iArr.length + 1; i4++) {
            if (i4 >= iArr.length || iArr[i4] != 0) {
                if (i3 >= 0) {
                    int i5 = i4 - i3;
                    if (i5 > i) {
                        i2 = i3;
                        i = i5;
                    }
                    i3 = -1;
                }
            } else if (i3 < 0) {
                i3 = i4;
            }
        }
        if (i >= 2) {
            Arrays.fill(iArr, i2, i + i2, -1);
        }
    }

    private static String b(int[] iArr) {
        StringBuilder sb = new StringBuilder(39);
        int i = 0;
        boolean z = false;
        while (i < iArr.length) {
            boolean z2 = iArr[i] >= 0;
            if (z2) {
                if (z) {
                    sb.append(':');
                }
                sb.append(Integer.toHexString(iArr[i]));
            } else if (i == 0 || z) {
                sb.append("::");
            }
            i++;
            z = z2;
        }
        return sb.toString();
    }

    public static String toUriString(InetAddress inetAddress) {
        if (!(inetAddress instanceof Inet6Address)) {
            return toAddrString(inetAddress);
        }
        return "[" + toAddrString(inetAddress) + "]";
    }

    public static InetAddress forUriString(String str) {
        InetAddress g = g(str);
        if (g != null) {
            return g;
        }
        throw a("Not a valid URI IP literal: '%s'", str);
    }

    @NullableDecl
    private static InetAddress g(String str) {
        int i;
        Preconditions.checkNotNull(str);
        if (!str.startsWith("[") || !str.endsWith("]")) {
            i = 4;
        } else {
            str = str.substring(1, str.length() - 1);
            i = 16;
        }
        byte[] a2 = a(str);
        if (a2 == null || a2.length != i) {
            return null;
        }
        return b(a2);
    }

    public static boolean isUriInetAddress(String str) {
        return g(str) != null;
    }

    public static boolean isCompatIPv4Address(Inet6Address inet6Address) {
        if (!inet6Address.isIPv4CompatibleAddress()) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        return (address[12] == 0 && address[13] == 0 && address[14] == 0 && (address[15] == 0 || address[15] == 1)) ? false : true;
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", toAddrString(inet6Address));
        return a(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean is6to4Address(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        return address[0] == 32 && address[1] == 2;
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", toAddrString(inet6Address));
        return a(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class TeredoInfo {
        private final Inet4Address a;
        private final Inet4Address b;
        private final int c;
        private final int d;

        public TeredoInfo(@NullableDecl Inet4Address inet4Address, @NullableDecl Inet4Address inet4Address2, int i, int i2) {
            boolean z = true;
            Preconditions.checkArgument(i >= 0 && i <= 65535, "port '%s' is out of range (0 <= port <= 0xffff)", i);
            Preconditions.checkArgument((i2 < 0 || i2 > 65535) ? false : z, "flags '%s' is out of range (0 <= flags <= 0xffff)", i2);
            this.a = (Inet4Address) MoreObjects.firstNonNull(inet4Address, InetAddresses.d);
            this.b = (Inet4Address) MoreObjects.firstNonNull(inet4Address2, InetAddresses.d);
            this.c = i;
            this.d = i2;
        }

        public Inet4Address getServer() {
            return this.a;
        }

        public Inet4Address getClient() {
            return this.b;
        }

        public int getPort() {
            return this.c;
        }

        public int getFlags() {
            return this.d;
        }
    }

    public static boolean isTeredoAddress(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        return address[0] == 32 && address[1] == 1 && address[2] == 0 && address[3] == 0;
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inet6Address) {
        Preconditions.checkArgument(isTeredoAddress(inet6Address), "Address '%s' is not a Teredo address.", toAddrString(inet6Address));
        byte[] address = inet6Address.getAddress();
        Inet4Address a2 = a(Arrays.copyOfRange(address, 4, 8));
        int readShort = ByteStreams.newDataInput(address, 8).readShort() & UShort.MAX_VALUE;
        int i = 65535 & (~ByteStreams.newDataInput(address, 10).readShort());
        byte[] copyOfRange = Arrays.copyOfRange(address, 12, 16);
        for (int i2 = 0; i2 < copyOfRange.length; i2++) {
            copyOfRange[i2] = (byte) (~copyOfRange[i2]);
        }
        return new TeredoInfo(a2, a(copyOfRange), i, readShort);
    }

    public static boolean isIsatapAddress(Inet6Address inet6Address) {
        if (isTeredoAddress(inet6Address)) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        return (address[8] | 3) == 3 && address[9] == 0 && address[10] == 94 && address[11] == -2;
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", toAddrString(inet6Address));
        return a(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        return isCompatIPv4Address(inet6Address) || is6to4Address(inet6Address) || isTeredoAddress(inet6Address);
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (isCompatIPv4Address(inet6Address)) {
            return getCompatIPv4Address(inet6Address);
        }
        if (is6to4Address(inet6Address)) {
            return get6to4IPv4Address(inet6Address);
        }
        if (isTeredoAddress(inet6Address)) {
            return getTeredoInfo(inet6Address).getClient();
        }
        throw a("'%s' has no embedded IPv4 address.", toAddrString(inet6Address));
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001b, code lost:
        if (r2 >= 12) goto L_0x0026;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0020, code lost:
        if (r4[r2] == (-1)) goto L_0x0023;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0022, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0023, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0026, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isMappedIPv4Address(java.lang.String r4) {
        /*
            byte[] r4 = a(r4)
            r0 = 0
            if (r4 == 0) goto L_0x0028
            int r1 = r4.length
            r2 = 16
            if (r1 != r2) goto L_0x0028
            r1 = r0
        L_0x000d:
            r2 = 10
            if (r1 >= r2) goto L_0x0019
            byte r2 = r4[r1]
            if (r2 == 0) goto L_0x0016
            return r0
        L_0x0016:
            int r1 = r1 + 1
            goto L_0x000d
        L_0x0019:
            r1 = 12
            if (r2 >= r1) goto L_0x0026
            byte r1 = r4[r2]
            r3 = -1
            if (r1 == r3) goto L_0x0023
            return r0
        L_0x0023:
            int r2 = r2 + 1
            goto L_0x0019
        L_0x0026:
            r4 = 1
            return r4
        L_0x0028:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.net.InetAddresses.isMappedIPv4Address(java.lang.String):boolean");
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetAddress) {
        boolean z;
        long j;
        if (inetAddress instanceof Inet4Address) {
            return (Inet4Address) inetAddress;
        }
        byte[] address = inetAddress.getAddress();
        int i = 0;
        while (true) {
            if (i >= 15) {
                z = true;
                break;
            } else if (address[i] != 0) {
                z = false;
                break;
            } else {
                i++;
            }
        }
        if (z && address[15] == 1) {
            return c;
        }
        if (z && address[15] == 0) {
            return d;
        }
        Inet6Address inet6Address = (Inet6Address) inetAddress;
        if (hasEmbeddedIPv4ClientAddress(inet6Address)) {
            j = getEmbeddedIPv4ClientAddress(inet6Address).hashCode();
        } else {
            j = ByteBuffer.wrap(inet6Address.getAddress(), 0, 8).getLong();
        }
        int asInt = Hashing.murmur3_32().hashLong(j).asInt() | (-536870912);
        if (asInt == -1) {
            asInt = -2;
        }
        return a(Ints.toByteArray(asInt));
    }

    public static int coerceToInteger(InetAddress inetAddress) {
        return ByteStreams.newDataInput(getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }

    public static Inet4Address fromInteger(int i) {
        return a(Ints.toByteArray(i));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] bArr) throws UnknownHostException {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[(bArr.length - i) - 1];
        }
        return InetAddress.getByAddress(bArr2);
    }

    public static InetAddress decrement(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        int length = address.length - 1;
        while (length >= 0 && address[length] == 0) {
            address[length] = -1;
            length--;
        }
        Preconditions.checkArgument(length >= 0, "Decrementing %s would wrap.", inetAddress);
        address[length] = (byte) (address[length] - 1);
        return b(address);
    }

    public static InetAddress increment(InetAddress inetAddress) {
        boolean z;
        byte[] address = inetAddress.getAddress();
        int length = address.length - 1;
        while (true) {
            z = false;
            if (length < 0 || address[length] != -1) {
                break;
            }
            address[length] = 0;
            length--;
        }
        if (length >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Incrementing %s would wrap.", inetAddress);
        address[length] = (byte) (address[length] + 1);
        return b(address);
    }

    public static boolean isMaximum(InetAddress inetAddress) {
        for (byte b2 : inetAddress.getAddress()) {
            if (b2 != -1) {
                return false;
            }
        }
        return true;
    }

    private static IllegalArgumentException a(String str, Object... objArr) {
        return new IllegalArgumentException(String.format(Locale.ROOT, str, objArr));
    }
}
