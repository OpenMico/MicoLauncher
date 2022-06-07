package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class NoopDnsCache implements DnsCache {
    public static final NoopDnsCache INSTANCE = new NoopDnsCache();

    @Override // io.netty.resolver.dns.DnsCache
    public void cache(String str, Throwable th, EventLoop eventLoop) {
    }

    @Override // io.netty.resolver.dns.DnsCache
    public void cache(String str, InetAddress inetAddress, long j, EventLoop eventLoop) {
    }

    @Override // io.netty.resolver.dns.DnsCache
    public void clear() {
    }

    @Override // io.netty.resolver.dns.DnsCache
    public boolean clear(String str) {
        return false;
    }

    private NoopDnsCache() {
    }

    @Override // io.netty.resolver.dns.DnsCache
    public List<DnsCacheEntry> get(String str) {
        return Collections.emptyList();
    }

    public String toString() {
        return NoopDnsCache.class.getSimpleName();
    }
}
