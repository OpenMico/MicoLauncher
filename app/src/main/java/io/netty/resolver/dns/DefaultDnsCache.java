package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class DefaultDnsCache implements DnsCache {
    static final /* synthetic */ boolean a = !DefaultDnsCache.class.desiredAssertionStatus();
    private final ConcurrentMap<String, List<DnsCacheEntry>> b;
    private final int c;
    private final int d;
    private final int e;

    public DefaultDnsCache() {
        this(0, Integer.MAX_VALUE, 0);
    }

    public DefaultDnsCache(int i, int i2, int i3) {
        this.b = PlatformDependent.newConcurrentHashMap();
        this.c = ObjectUtil.checkPositiveOrZero(i, "minTtl");
        this.d = ObjectUtil.checkPositiveOrZero(i2, "maxTtl");
        if (i <= i2) {
            this.e = ObjectUtil.checkPositiveOrZero(i3, "negativeTtl");
            return;
        }
        throw new IllegalArgumentException("minTtl: " + i + ", maxTtl: " + i2 + " (expected: 0 <= minTtl <= maxTtl)");
    }

    public int minTtl() {
        return this.c;
    }

    public int maxTtl() {
        return this.d;
    }

    public int negativeTtl() {
        return this.e;
    }

    @Override // io.netty.resolver.dns.DnsCache
    public void clear() {
        Iterator<Map.Entry<String, List<DnsCacheEntry>>> it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            it.remove();
            a(it.next().getValue());
        }
    }

    @Override // io.netty.resolver.dns.DnsCache
    public boolean clear(String str) {
        ObjectUtil.checkNotNull(str, "hostname");
        Iterator<Map.Entry<String, List<DnsCacheEntry>>> it = this.b.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Map.Entry<String, List<DnsCacheEntry>> next = it.next();
            if (next.getKey().equals(str)) {
                it.remove();
                a(next.getValue());
                z = true;
            }
        }
        return z;
    }

    @Override // io.netty.resolver.dns.DnsCache
    public List<DnsCacheEntry> get(String str) {
        ObjectUtil.checkNotNull(str, "hostname");
        return this.b.get(str);
    }

    private List<DnsCacheEntry> a(String str) {
        List<DnsCacheEntry> list = this.b.get(str);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(8);
        List<DnsCacheEntry> putIfAbsent = this.b.putIfAbsent(str, arrayList);
        return putIfAbsent != null ? putIfAbsent : arrayList;
    }

    @Override // io.netty.resolver.dns.DnsCache
    public void cache(String str, InetAddress inetAddress, long j, EventLoop eventLoop) {
        if (this.d != 0) {
            ObjectUtil.checkNotNull(str, "hostname");
            ObjectUtil.checkNotNull(inetAddress, "address");
            ObjectUtil.checkNotNull(eventLoop, "loop");
            int max = Math.max(this.c, (int) Math.min(this.d, j));
            List<DnsCacheEntry> a2 = a(str);
            DnsCacheEntry dnsCacheEntry = new DnsCacheEntry(str, inetAddress);
            synchronized (a2) {
                if (!a2.isEmpty()) {
                    DnsCacheEntry dnsCacheEntry2 = a2.get(0);
                    if (dnsCacheEntry2.cause() != null) {
                        if (!a && a2.size() != 1) {
                            throw new AssertionError();
                        }
                        dnsCacheEntry2.a();
                        a2.clear();
                    }
                }
                a2.add(dnsCacheEntry);
            }
            a(a2, dnsCacheEntry, max, eventLoop);
        }
    }

    @Override // io.netty.resolver.dns.DnsCache
    public void cache(String str, Throwable th, EventLoop eventLoop) {
        if (this.e != 0) {
            ObjectUtil.checkNotNull(str, "hostname");
            ObjectUtil.checkNotNull(th, "cause");
            ObjectUtil.checkNotNull(eventLoop, "loop");
            List<DnsCacheEntry> a2 = a(str);
            DnsCacheEntry dnsCacheEntry = new DnsCacheEntry(str, th);
            synchronized (a2) {
                int size = a2.size();
                for (int i = 0; i < size; i++) {
                    a2.get(i).a();
                }
                a2.clear();
                a2.add(dnsCacheEntry);
            }
            a(a2, dnsCacheEntry, this.e, eventLoop);
        }
    }

    private static void a(List<DnsCacheEntry> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).a();
        }
    }

    private void a(final List<DnsCacheEntry> list, final DnsCacheEntry dnsCacheEntry, int i, EventLoop eventLoop) {
        dnsCacheEntry.a(eventLoop, new OneTimeTask() { // from class: io.netty.resolver.dns.DefaultDnsCache.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (list) {
                    list.remove(dnsCacheEntry);
                    if (list.isEmpty()) {
                        DefaultDnsCache.this.b.remove(dnsCacheEntry.hostname());
                    }
                }
            }
        }, i, TimeUnit.SECONDS);
    }

    public String toString() {
        return "DefaultDnsCache(minTtl=" + this.c + ", maxTtl=" + this.d + ", negativeTtl=" + this.e + ", cached resolved hostname=" + this.b.size() + ")";
    }
}
