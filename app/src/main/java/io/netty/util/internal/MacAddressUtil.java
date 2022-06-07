package io.netty.util.internal;

import io.netty.util.NetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class MacAddressUtil {
    public static final int MAC_ADDRESS_LENGTH = 8;
    private static final byte[] a = {-1};
    private static final InternalLogger b = InternalLoggerFactory.getInstance(MacAddressUtil.class);

    public static byte[] bestAvailableMac() {
        int a2;
        byte[] bArr = a;
        InetAddress inetAddress = NetUtil.LOCALHOST4;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                if (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement2 = inetAddresses.nextElement();
                    if (!nextElement2.isLoopbackAddress()) {
                        linkedHashMap.put(nextElement, nextElement2);
                    }
                }
            }
        } catch (SocketException e) {
            b.warn("Failed to retrieve the list of available network interfaces", (Throwable) e);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            NetworkInterface networkInterface = (NetworkInterface) entry.getKey();
            InetAddress inetAddress2 = (InetAddress) entry.getValue();
            if (!networkInterface.isVirtual()) {
                try {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    int a3 = a(bArr, hardwareAddress);
                    boolean z = true;
                    if (a3 >= 0 && (a3 != 0 || ((a2 = a(inetAddress, inetAddress2)) >= 0 && (a2 != 0 || bArr.length >= hardwareAddress.length)))) {
                        z = false;
                    }
                    if (z) {
                        inetAddress = inetAddress2;
                        bArr = hardwareAddress;
                    }
                } catch (SocketException e2) {
                    b.debug("Failed to get the hardware address of a network interface: {}", networkInterface, e2);
                }
            }
        }
        if (bArr == a) {
            return null;
        }
        if (bArr.length != 6) {
            return Arrays.copyOf(bArr, 8);
        }
        byte[] bArr2 = new byte[8];
        System.arraycopy(bArr, 0, bArr2, 0, 3);
        bArr2[3] = -1;
        bArr2[4] = -2;
        System.arraycopy(bArr, 3, bArr2, 5, 3);
        return bArr2;
    }

    public static String formatAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder(24);
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02x:", Integer.valueOf(bArr[i] & 255)));
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static int a(byte[] bArr, byte[] bArr2) {
        boolean z;
        if (bArr2 == null || bArr2.length < 6) {
            return 1;
        }
        int length = bArr2.length;
        int i = 0;
        while (true) {
            if (i < length) {
                byte b2 = bArr2[i];
                if (b2 != 0 && b2 != 1) {
                    z = false;
                    break;
                }
                i++;
            } else {
                z = true;
                break;
            }
        }
        if (!z && (bArr2[0] & 1) == 0) {
            return (bArr[0] & 2) == 0 ? (bArr2[0] & 2) == 0 ? 0 : 1 : (bArr2[0] & 2) == 0 ? -1 : 0;
        }
        return 1;
    }

    private static int a(InetAddress inetAddress, InetAddress inetAddress2) {
        return a(inetAddress) - a(inetAddress2);
    }

    private static int a(InetAddress inetAddress) {
        if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress()) {
            return 0;
        }
        if (inetAddress.isMulticastAddress()) {
            return 1;
        }
        if (inetAddress.isLinkLocalAddress()) {
            return 2;
        }
        return inetAddress.isSiteLocalAddress() ? 3 : 4;
    }

    private MacAddressUtil() {
    }
}
