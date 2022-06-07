package io.netty.resolver.dns;

import io.netty.util.NetUtil;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.ThreadLocalRandom;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DnsQueryContextManager.java */
/* loaded from: classes4.dex */
public final class d {
    final Map<InetSocketAddress, IntObjectMap<c>> a = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(c cVar) {
        IntObjectMap<c> b = b(cVar.a());
        int nextInt = ThreadLocalRandom.current().nextInt(1, 65536);
        synchronized (b) {
            int i = 0;
            while (b.containsKey(nextInt)) {
                nextInt = (nextInt + 1) & 65535;
                i++;
                if (i >= 131070) {
                    throw new IllegalStateException("query ID space exhausted: " + cVar.b());
                }
            }
            b.put(nextInt, (int) cVar);
        }
        return nextInt;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c a(InetSocketAddress inetSocketAddress, int i) {
        c cVar;
        IntObjectMap<c> a = a(inetSocketAddress);
        if (a == null) {
            return null;
        }
        synchronized (a) {
            cVar = a.get(i);
        }
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c b(InetSocketAddress inetSocketAddress, int i) {
        c remove;
        IntObjectMap<c> a = a(inetSocketAddress);
        if (a == null) {
            return null;
        }
        synchronized (a) {
            remove = a.remove(i);
        }
        return remove;
    }

    private IntObjectMap<c> a(InetSocketAddress inetSocketAddress) {
        IntObjectMap<c> intObjectMap;
        synchronized (this.a) {
            intObjectMap = this.a.get(inetSocketAddress);
        }
        return intObjectMap;
    }

    private IntObjectMap<c> b(InetSocketAddress inetSocketAddress) {
        synchronized (this.a) {
            IntObjectMap<c> intObjectMap = this.a.get(inetSocketAddress);
            if (intObjectMap != null) {
                return intObjectMap;
            }
            IntObjectHashMap intObjectHashMap = new IntObjectHashMap();
            InetAddress address = inetSocketAddress.getAddress();
            int port = inetSocketAddress.getPort();
            this.a.put(inetSocketAddress, intObjectHashMap);
            if (address instanceof Inet4Address) {
                Inet4Address inet4Address = (Inet4Address) address;
                if (inet4Address.isLoopbackAddress()) {
                    this.a.put(new InetSocketAddress(NetUtil.LOCALHOST6, port), intObjectHashMap);
                } else {
                    this.a.put(new InetSocketAddress(a(inet4Address), port), intObjectHashMap);
                }
            } else if (address instanceof Inet6Address) {
                Inet6Address inet6Address = (Inet6Address) address;
                if (inet6Address.isLoopbackAddress()) {
                    this.a.put(new InetSocketAddress(NetUtil.LOCALHOST4, port), intObjectHashMap);
                } else if (inet6Address.isIPv4CompatibleAddress()) {
                    this.a.put(new InetSocketAddress(a(inet6Address), port), intObjectHashMap);
                }
            }
            return intObjectHashMap;
        }
    }

    private static Inet6Address a(Inet4Address inet4Address) {
        byte[] address = inet4Address.getAddress();
        try {
            return (Inet6Address) InetAddress.getByAddress(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, address[0], address[1], address[2], address[3]});
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }

    private static Inet4Address a(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        try {
            return (Inet4Address) InetAddress.getByAddress(new byte[]{address[12], address[13], address[14], address[15]});
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }
}
