package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public final class DnsNameResolverException extends RuntimeException {
    private static final long serialVersionUID = -8826717909627131850L;
    private final DnsQuestion question;
    private final InetSocketAddress remoteAddress;

    public DnsNameResolverException(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, String str) {
        super(str);
        this.remoteAddress = a(inetSocketAddress);
        this.question = a(dnsQuestion);
    }

    public DnsNameResolverException(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, String str, Throwable th) {
        super(str, th);
        this.remoteAddress = a(inetSocketAddress);
        this.question = a(dnsQuestion);
    }

    private static InetSocketAddress a(InetSocketAddress inetSocketAddress) {
        return (InetSocketAddress) ObjectUtil.checkNotNull(inetSocketAddress, "remoteAddress");
    }

    private static DnsQuestion a(DnsQuestion dnsQuestion) {
        return (DnsQuestion) ObjectUtil.checkNotNull(dnsQuestion, "question");
    }

    public InetSocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    public DnsQuestion question() {
        return this.question;
    }

    @Override // java.lang.Throwable
    public Throwable fillInStackTrace() {
        setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        return this;
    }
}
