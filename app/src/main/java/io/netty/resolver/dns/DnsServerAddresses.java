package io.netty.resolver.dns;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes4.dex */
public abstract class DnsServerAddresses {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(DnsServerAddresses.class);
    private static final List<InetSocketAddress> b;
    private static final InetSocketAddress[] c;
    private static final DnsServerAddresses d;

    public abstract DnsServerAddressStream stream();

    static {
        ArrayList arrayList = new ArrayList(2);
        try {
            Class<?> cls = Class.forName("sun.net.dns.ResolverConfiguration");
            for (String str : (List) cls.getMethod("nameservers", new Class[0]).invoke(cls.getMethod(AbstractCircuitBreaker.PROPERTY_NAME, new Class[0]).invoke(null, new Object[0]), new Object[0])) {
                if (str != null) {
                    arrayList.add(new InetSocketAddress(InetAddress.getByName(str), 53));
                }
            }
        } catch (Exception unused) {
        }
        if (arrayList.isEmpty()) {
            Collections.addAll(arrayList, new InetSocketAddress("8.8.8.8", 53), new InetSocketAddress("8.8.4.4", 53));
            if (a.isWarnEnabled()) {
                a.warn("Default DNS servers: {} (Google Public DNS as a fallback)", arrayList);
            }
        } else if (a.isDebugEnabled()) {
            a.debug("Default DNS servers: {} (sun.net.dns.ResolverConfiguration)", arrayList);
        }
        b = Collections.unmodifiableList(arrayList);
        c = (InetSocketAddress[]) arrayList.toArray(new InetSocketAddress[arrayList.size()]);
        d = sequential(c);
    }

    public static List<InetSocketAddress> defaultAddressList() {
        return b;
    }

    public static DnsServerAddresses defaultAddresses() {
        return d;
    }

    public static DnsServerAddresses sequential(Iterable<? extends InetSocketAddress> iterable) {
        return a(a(iterable));
    }

    public static DnsServerAddresses sequential(InetSocketAddress... inetSocketAddressArr) {
        return a(d(inetSocketAddressArr));
    }

    private static DnsServerAddresses a(InetSocketAddress... inetSocketAddressArr) {
        if (inetSocketAddressArr.length == 1) {
            return singleton(inetSocketAddressArr[0]);
        }
        return new a("sequential", inetSocketAddressArr) { // from class: io.netty.resolver.dns.DnsServerAddresses.1
            @Override // io.netty.resolver.dns.DnsServerAddresses
            public DnsServerAddressStream stream() {
                return new g(this.a, 0);
            }
        };
    }

    public static DnsServerAddresses shuffled(Iterable<? extends InetSocketAddress> iterable) {
        return b(a(iterable));
    }

    public static DnsServerAddresses shuffled(InetSocketAddress... inetSocketAddressArr) {
        return b(d(inetSocketAddressArr));
    }

    private static DnsServerAddresses b(InetSocketAddress[] inetSocketAddressArr) {
        if (inetSocketAddressArr.length == 1) {
            return singleton(inetSocketAddressArr[0]);
        }
        return new a("shuffled", inetSocketAddressArr) { // from class: io.netty.resolver.dns.DnsServerAddresses.2
            @Override // io.netty.resolver.dns.DnsServerAddresses
            public DnsServerAddressStream stream() {
                return new h(this.a);
            }
        };
    }

    public static DnsServerAddresses rotational(Iterable<? extends InetSocketAddress> iterable) {
        return c(a(iterable));
    }

    public static DnsServerAddresses rotational(InetSocketAddress... inetSocketAddressArr) {
        return c(d(inetSocketAddressArr));
    }

    private static DnsServerAddresses c(InetSocketAddress[] inetSocketAddressArr) {
        if (inetSocketAddressArr.length == 1) {
            return singleton(inetSocketAddressArr[0]);
        }
        return new f(inetSocketAddressArr);
    }

    public static DnsServerAddresses singleton(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress == null) {
            throw new NullPointerException("address");
        } else if (!inetSocketAddress.isUnresolved()) {
            return new i(inetSocketAddress);
        } else {
            throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
        }
    }

    private static InetSocketAddress[] a(Iterable<? extends InetSocketAddress> iterable) {
        ArrayList arrayList;
        InetSocketAddress inetSocketAddress;
        if (iterable != null) {
            if (iterable instanceof Collection) {
                arrayList = new ArrayList(((Collection) iterable).size());
            } else {
                arrayList = new ArrayList(4);
            }
            Iterator<? extends InetSocketAddress> it = iterable.iterator();
            while (it.hasNext() && (inetSocketAddress = (InetSocketAddress) it.next()) != null) {
                if (!inetSocketAddress.isUnresolved()) {
                    arrayList.add(inetSocketAddress);
                } else {
                    throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
                }
            }
            if (!arrayList.isEmpty()) {
                return (InetSocketAddress[]) arrayList.toArray(new InetSocketAddress[arrayList.size()]);
            }
            throw new IllegalArgumentException("empty addresses");
        }
        throw new NullPointerException("addresses");
    }

    private static InetSocketAddress[] d(InetSocketAddress[] inetSocketAddressArr) {
        if (inetSocketAddressArr != null) {
            ArrayList arrayList = InternalThreadLocalMap.get().arrayList(inetSocketAddressArr.length);
            for (InetSocketAddress inetSocketAddress : inetSocketAddressArr) {
                if (inetSocketAddress == null) {
                    break;
                } else if (!inetSocketAddress.isUnresolved()) {
                    arrayList.add(inetSocketAddress);
                } else {
                    throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
                }
            }
            if (arrayList.isEmpty()) {
                return c;
            }
            return (InetSocketAddress[]) arrayList.toArray(new InetSocketAddress[arrayList.size()]);
        }
        throw new NullPointerException("addresses");
    }
}
