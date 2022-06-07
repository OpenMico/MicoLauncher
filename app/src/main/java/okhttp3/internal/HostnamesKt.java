package okhttp3.internal;

import com.xiaomi.idm.service.iot.InputMethodService;
import com.xiaomi.mipush.sdk.Constants;
import java.net.IDN;
import java.net.InetAddress;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: hostnames.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0002\u001a\"\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\r\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u0003*\u00020\u0003¨\u0006\u000f"}, d2 = {"decodeIpv4Suffix", "", InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, "", "pos", "", "limit", "address", "", "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class HostnamesKt {
    @Nullable
    public static final String toCanonicalHost(@NotNull String toCanonicalHost) {
        InetAddress inetAddress;
        Intrinsics.checkParameterIsNotNull(toCanonicalHost, "$this$toCanonicalHost");
        boolean z = true;
        if (StringsKt.contains$default((CharSequence) toCanonicalHost, (CharSequence) Constants.COLON_SEPARATOR, false, 2, (Object) null)) {
            if (!StringsKt.startsWith$default(toCanonicalHost, "[", false, 2, (Object) null) || !StringsKt.endsWith$default(toCanonicalHost, "]", false, 2, (Object) null)) {
                inetAddress = decodeIpv6(toCanonicalHost, 0, toCanonicalHost.length());
            } else {
                inetAddress = decodeIpv6(toCanonicalHost, 1, toCanonicalHost.length() - 1);
            }
            if (inetAddress == null) {
                return null;
            }
            byte[] address = inetAddress.getAddress();
            if (address.length == 16) {
                Intrinsics.checkExpressionValueIsNotNull(address, "address");
                return inet6AddressToAscii(address);
            } else if (address.length == 4) {
                return inetAddress.getHostAddress();
            } else {
                throw new AssertionError("Invalid IPv6 address: '" + toCanonicalHost + '\'');
            }
        } else {
            try {
                String ascii = IDN.toASCII(toCanonicalHost);
                Intrinsics.checkExpressionValueIsNotNull(ascii, "IDN.toASCII(host)");
                Locale locale = Locale.US;
                Intrinsics.checkExpressionValueIsNotNull(locale, "Locale.US");
                if (ascii != null) {
                    String lowerCase = ascii.toLowerCase(locale);
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
                    if (lowerCase.length() != 0) {
                        z = false;
                    }
                    if (!z && !containsInvalidHostnameAsciiCodes(lowerCase)) {
                        return lowerCase;
                    }
                    return null;
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
    }

    private static final boolean containsInvalidHostnameAsciiCodes(@NotNull String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt <= 31 || charAt >= 127 || StringsKt.indexOf$default((CharSequence) " #%/:?@[\\]", charAt, 0, false, 6, (Object) null) != -1) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0099, code lost:
        if (r12 == r8.length) goto L_0x00ad;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x009b, code lost:
        if (r13 != (-1)) goto L_0x009e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x009d, code lost:
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009e, code lost:
        r1 = r12 - r13;
        java.lang.System.arraycopy(r8, r13, r8, r8.length - r1, r1);
        java.util.Arrays.fill(r8, r13, (r8.length - r12) + r13, (byte) 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b1, code lost:
        return java.net.InetAddress.getByAddress(r8);
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final java.net.InetAddress decodeIpv6(java.lang.String r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 178
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.HostnamesKt.decodeIpv6(java.lang.String, int, int):java.net.InetAddress");
    }

    private static final boolean decodeIpv4Suffix(String str, int i, int i2, byte[] bArr, int i3) {
        int i4 = i3;
        while (i < i2) {
            if (i4 == bArr.length) {
                return false;
            }
            if (i4 != i3) {
                if (str.charAt(i) != '.') {
                    return false;
                }
                i++;
            }
            int i5 = i;
            int i6 = 0;
            while (i5 < i2) {
                char charAt = str.charAt(i5);
                if (charAt < '0' || charAt > '9') {
                    break;
                } else if ((i6 == 0 && i != i5) || (i6 = ((i6 * 10) + charAt) - 48) > 255) {
                    return false;
                } else {
                    i5++;
                }
            }
            if (i5 - i == 0) {
                return false;
            }
            i4++;
            bArr[i4] = (byte) i6;
            i = i5;
        }
        return i4 == i3 + 4;
    }

    private static final String inet6AddressToAscii(byte[] bArr) {
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        while (i4 < bArr.length) {
            int i5 = i4;
            while (i5 < 16 && bArr[i5] == 0 && bArr[i5 + 1] == 0) {
                i5 += 2;
            }
            int i6 = i5 - i4;
            if (i6 > i3 && i6 >= 4) {
                i2 = i4;
                i3 = i6;
            }
            i4 = i5 + 2;
        }
        Buffer buffer = new Buffer();
        while (i < bArr.length) {
            if (i == i2) {
                buffer.writeByte(58);
                i += i3;
                if (i == 16) {
                    buffer.writeByte(58);
                }
            } else {
                if (i > 0) {
                    buffer.writeByte(58);
                }
                buffer.writeHexadecimalUnsignedLong((Util.and(bArr[i], 255) << 8) | Util.and(bArr[i + 1], 255));
                i += 2;
            }
        }
        return buffer.readUtf8();
    }
}
