package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class DnsNameResolverBuilder {
    private final EventLoop a;
    private ChannelFactory<? extends DatagramChannel> b;
    private DnsCache e;
    private Integer f;
    private Integer g;
    private Integer h;
    private boolean m;
    private InetSocketAddress c = DnsNameResolver.a;
    private DnsServerAddresses d = DnsServerAddresses.defaultAddresses();
    private long i = 5000;
    private InternetProtocolFamily[] j = DnsNameResolver.b;
    private boolean k = true;
    private int l = 16;
    private int n = 4096;
    private boolean o = true;
    private HostsFileEntriesResolver p = HostsFileEntriesResolver.DEFAULT;

    public DnsNameResolverBuilder(EventLoop eventLoop) {
        this.a = eventLoop;
    }

    public DnsNameResolverBuilder channelFactory(ChannelFactory<? extends DatagramChannel> channelFactory) {
        this.b = channelFactory;
        return this;
    }

    public DnsNameResolverBuilder channelType(Class<? extends DatagramChannel> cls) {
        return channelFactory(new ReflectiveChannelFactory(cls));
    }

    public DnsNameResolverBuilder localAddress(InetSocketAddress inetSocketAddress) {
        this.c = inetSocketAddress;
        return this;
    }

    public DnsNameResolverBuilder nameServerAddresses(DnsServerAddresses dnsServerAddresses) {
        this.d = dnsServerAddresses;
        return this;
    }

    public DnsNameResolverBuilder resolveCache(DnsCache dnsCache) {
        this.e = dnsCache;
        return this;
    }

    public DnsNameResolverBuilder ttl(int i, int i2) {
        this.g = Integer.valueOf(i2);
        this.f = Integer.valueOf(i);
        return this;
    }

    public DnsNameResolverBuilder negativeTtl(int i) {
        this.h = Integer.valueOf(i);
        return this;
    }

    public DnsNameResolverBuilder queryTimeoutMillis(long j) {
        this.i = j;
        return this;
    }

    public DnsNameResolverBuilder resolvedAddressTypes(InternetProtocolFamily... internetProtocolFamilyArr) {
        ObjectUtil.checkNotNull(internetProtocolFamilyArr, "resolvedAddressTypes");
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList(InternetProtocolFamily.values().length);
        for (InternetProtocolFamily internetProtocolFamily : internetProtocolFamilyArr) {
            if (internetProtocolFamily == null) {
                break;
            }
            if (!arrayList.contains(internetProtocolFamily)) {
                arrayList.add(internetProtocolFamily);
            }
        }
        if (!arrayList.isEmpty()) {
            this.j = (InternetProtocolFamily[]) arrayList.toArray(new InternetProtocolFamily[arrayList.size()]);
            return this;
        }
        throw new IllegalArgumentException("no protocol family specified");
    }

    public DnsNameResolverBuilder resolvedAddressTypes(Iterable<InternetProtocolFamily> iterable) {
        InternetProtocolFamily next;
        ObjectUtil.checkNotNull(iterable, "resolveAddressTypes");
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList(InternetProtocolFamily.values().length);
        Iterator<InternetProtocolFamily> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            if (!arrayList.contains(next)) {
                arrayList.add(next);
            }
        }
        if (!arrayList.isEmpty()) {
            this.j = (InternetProtocolFamily[]) arrayList.toArray(new InternetProtocolFamily[arrayList.size()]);
            return this;
        }
        throw new IllegalArgumentException("no protocol family specified");
    }

    public DnsNameResolverBuilder recursionDesired(boolean z) {
        this.k = z;
        return this;
    }

    public DnsNameResolverBuilder maxQueriesPerResolve(int i) {
        this.l = i;
        return this;
    }

    public DnsNameResolverBuilder traceEnabled(boolean z) {
        this.m = z;
        return this;
    }

    public DnsNameResolverBuilder maxPayloadSize(int i) {
        this.n = i;
        return this;
    }

    public DnsNameResolverBuilder optResourceEnabled(boolean z) {
        this.o = z;
        return this;
    }

    public DnsNameResolverBuilder hostsFileEntriesResolver(HostsFileEntriesResolver hostsFileEntriesResolver) {
        this.p = hostsFileEntriesResolver;
        return this;
    }

    public DnsNameResolver build() {
        if (this.e == null || (this.f == null && this.g == null && this.h == null)) {
            DnsCache dnsCache = this.e;
            if (dnsCache == null) {
                dnsCache = new DefaultDnsCache(ObjectUtil.intValue(this.f, 0), ObjectUtil.intValue(this.g, Integer.MAX_VALUE), ObjectUtil.intValue(this.h, 0));
            }
            return new DnsNameResolver(this.a, this.b, this.c, this.d, dnsCache, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p);
        }
        throw new IllegalStateException("resolveCache and TTLs are mutually exclusive");
    }
}
