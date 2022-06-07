package io.netty.channel.unix;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/* loaded from: classes4.dex */
public final class NativeInetAddress {
    private static final byte[] c = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1};
    final byte[] a;
    final int b;

    public static NativeInetAddress newInstance(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        if (inetAddress instanceof Inet6Address) {
            return new NativeInetAddress(address, ((Inet6Address) inetAddress).getScopeId());
        }
        return new NativeInetAddress(ipv4MappedIpv6Address(address));
    }

    public NativeInetAddress(byte[] bArr, int i) {
        this.a = bArr;
        this.b = i;
    }

    public NativeInetAddress(byte[] bArr) {
        this(bArr, 0);
    }

    public byte[] address() {
        return this.a;
    }

    public int scopeId() {
        return this.b;
    }

    public static byte[] ipv4MappedIpv6Address(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        byte[] bArr3 = c;
        System.arraycopy(bArr3, 0, bArr2, 0, bArr3.length);
        System.arraycopy(bArr, 0, bArr2, 12, bArr.length);
        return bArr2;
    }

    public static InetSocketAddress address(byte[] bArr, int i, int i2) {
        InetAddress inetAddress;
        int i3 = i + i2;
        int a = a(bArr, i3 - 4);
        try {
            if (i2 == 8) {
                byte[] bArr2 = new byte[4];
                System.arraycopy(bArr, i, bArr2, 0, 4);
                inetAddress = InetAddress.getByAddress(bArr2);
            } else if (i2 == 24) {
                byte[] bArr3 = new byte[16];
                System.arraycopy(bArr, i, bArr3, 0, 16);
                inetAddress = Inet6Address.getByAddress((String) null, bArr3, a(bArr, i3 - 8));
            } else {
                throw new Error();
            }
            return new InetSocketAddress(inetAddress, a);
        } catch (UnknownHostException e) {
            throw new Error("Should never happen", e);
        }
    }

    static int a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }
}
