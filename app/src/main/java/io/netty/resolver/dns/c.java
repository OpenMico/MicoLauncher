package io.netty.resolver.dns;

import io.netty.buffer.Unpooled;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.dns.DatagramDnsQuery;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/* compiled from: DnsQueryContext.java */
/* loaded from: classes4.dex */
public final class c {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(c.class);
    private final DnsNameResolver b;
    private final Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> c;
    private final int d;
    private final DnsQuestion e;
    private final Iterable<DnsRecord> f;
    private final DnsRecord g;
    private final InetSocketAddress h;
    private final boolean i;
    private volatile ScheduledFuture<?> j;

    public c(DnsNameResolver dnsNameResolver, InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable, Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise) {
        this.b = (DnsNameResolver) ObjectUtil.checkNotNull(dnsNameResolver, "parent");
        this.h = (InetSocketAddress) ObjectUtil.checkNotNull(inetSocketAddress, "nameServerAddr");
        this.e = (DnsQuestion) ObjectUtil.checkNotNull(dnsQuestion, "question");
        this.f = (Iterable) ObjectUtil.checkNotNull(iterable, "additional");
        this.c = (Promise) ObjectUtil.checkNotNull(promise, "promise");
        this.i = dnsNameResolver.isRecursionDesired();
        this.d = dnsNameResolver.f.a(this);
        if (dnsNameResolver.isOptResourceEnabled()) {
            this.g = new DefaultDnsRawRecord("", DnsRecordType.OPT, dnsNameResolver.maxPayloadSize(), 0L, Unpooled.EMPTY_BUFFER);
        } else {
            this.g = null;
        }
    }

    public InetSocketAddress a() {
        return this.h;
    }

    public DnsQuestion b() {
        return this.e;
    }

    public void c() {
        DnsQuestion b = b();
        InetSocketAddress a2 = a();
        DatagramDnsQuery datagramDnsQuery = new DatagramDnsQuery(null, a2, this.d);
        datagramDnsQuery.setRecursionDesired(this.i);
        datagramDnsQuery.addRecord(DnsSection.QUESTION, (DnsRecord) b);
        for (DnsRecord dnsRecord : this.f) {
            datagramDnsQuery.addRecord(DnsSection.ADDITIONAL, dnsRecord);
        }
        if (this.g != null) {
            datagramDnsQuery.addRecord(DnsSection.ADDITIONAL, this.g);
        }
        if (a.isDebugEnabled()) {
            a.debug("{} WRITE: [{}: {}], {}", this.b.e, Integer.valueOf(this.d), a2, b);
        }
        a((DnsQuery) datagramDnsQuery);
    }

    private void a(final DnsQuery dnsQuery) {
        if (this.b.d.isDone()) {
            b(dnsQuery);
        } else {
            this.b.d.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.resolver.dns.c.1
                /* renamed from: a */
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        c.this.b(dnsQuery);
                    } else {
                        c.this.c.tryFailure(channelFuture.cause());
                    }
                }
            });
        }
    }

    public void b(DnsQuery dnsQuery) {
        final ChannelFuture writeAndFlush = this.b.e.writeAndFlush(dnsQuery);
        if (writeAndFlush.isDone()) {
            a(writeAndFlush);
        } else {
            writeAndFlush.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.resolver.dns.c.2
                /* renamed from: a */
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    c.this.a(writeAndFlush);
                }
            });
        }
    }

    public void a(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            a("failed to send a query", channelFuture.cause());
            return;
        }
        final long queryTimeoutMillis = this.b.queryTimeoutMillis();
        if (queryTimeoutMillis > 0) {
            this.j = this.b.e.eventLoop().schedule((Runnable) new OneTimeTask() { // from class: io.netty.resolver.dns.c.3
                @Override // java.lang.Runnable
                public void run() {
                    if (!c.this.c.isDone()) {
                        c cVar = c.this;
                        cVar.a("query timed out after " + queryTimeoutMillis + " milliseconds", (Throwable) null);
                    }
                }
            }, queryTimeoutMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void a(AddressedEnvelope<? extends DnsResponse, InetSocketAddress> addressedEnvelope) {
        DnsResponse dnsResponse = (DnsResponse) addressedEnvelope.content();
        if (dnsResponse.count(DnsSection.QUESTION) != 1) {
            a.warn("Received a DNS response with invalid number of questions: {}", addressedEnvelope);
        } else if (!b().equals(dnsResponse.recordAt(DnsSection.QUESTION))) {
            a.warn("Received a mismatching DNS response: {}", addressedEnvelope);
        } else {
            b(addressedEnvelope);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b(AddressedEnvelope<? extends DnsResponse, InetSocketAddress> addressedEnvelope) {
        this.b.f.b(a(), this.d);
        ScheduledFuture<?> scheduledFuture = this.j;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise = this.c;
        if (promise.setUncancellable()) {
            promise.setSuccess(addressedEnvelope.retain());
        }
    }

    public void a(String str, Throwable th) {
        DnsNameResolverException dnsNameResolverException;
        InetSocketAddress a2 = a();
        this.b.f.b(a2, this.d);
        StringBuilder sb = new StringBuilder(str.length() + 64);
        sb.append('[');
        sb.append(a2);
        sb.append("] ");
        sb.append(str);
        sb.append(" (no stack trace available)");
        if (th != null) {
            dnsNameResolverException = new DnsNameResolverException(a2, b(), sb.toString(), th);
        } else {
            dnsNameResolverException = new DnsNameResolverException(a2, b(), sb.toString());
        }
        this.c.tryFailure(dnsNameResolverException);
    }
}
