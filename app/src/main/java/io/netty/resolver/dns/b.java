package io.netty.resolver.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.dns.DefaultDnsQuestion;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.StringUtil;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DnsNameResolverContext.java */
/* loaded from: classes4.dex */
public abstract class b<T> {
    private final DnsNameResolver c;
    private final DnsServerAddressStream d;
    private final Promise<T> e;
    private final String f;
    private final DnsCache g;
    private final boolean h;
    private final int i;
    private final InternetProtocolFamily[] j;
    private final Set<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> k = Collections.newSetFromMap(new IdentityHashMap());
    private List<DnsCacheEntry> l;
    private StringBuilder m;
    private int n;
    private boolean o;
    static final /* synthetic */ boolean b = !b.class.desiredAssertionStatus();
    private static final FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>> a = new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>() { // from class: io.netty.resolver.dns.b.1
        @Override // io.netty.util.concurrent.GenericFutureListener
        public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
            if (future.isSuccess()) {
                future.getNow().release();
            }
        }
    };

    protected abstract boolean a(Class<? extends InetAddress> cls, List<DnsCacheEntry> list);

    /* JADX INFO: Access modifiers changed from: protected */
    public b(DnsNameResolver dnsNameResolver, String str, Promise<T> promise, DnsCache dnsCache) {
        this.c = dnsNameResolver;
        this.e = promise;
        this.f = str;
        this.g = dnsCache;
        this.d = dnsNameResolver.c.stream();
        this.i = dnsNameResolver.maxQueriesPerResolve();
        this.j = dnsNameResolver.a();
        this.h = dnsNameResolver.isTraceEnabled();
        this.n = this.i;
    }

    protected Promise<T> a() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        DnsRecordType dnsRecordType;
        InetSocketAddress next = this.d.next();
        for (InternetProtocolFamily internetProtocolFamily : this.j) {
            switch (internetProtocolFamily) {
                case IPv4:
                    dnsRecordType = DnsRecordType.A;
                    break;
                case IPv6:
                    dnsRecordType = DnsRecordType.AAAA;
                    break;
                default:
                    throw new Error();
            }
            a(next, new DefaultDnsQuestion(this.f, dnsRecordType));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(InetSocketAddress inetSocketAddress, final DnsQuestion dnsQuestion) {
        if (this.n == 0 || this.e.isCancelled()) {
            c();
            return;
        }
        this.n--;
        Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query = this.c.query(inetSocketAddress, dnsQuestion);
        this.k.add(query);
        query.addListener(new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>() { // from class: io.netty.resolver.dns.b.2
            @Override // io.netty.util.concurrent.GenericFutureListener
            public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
                b.this.k.remove(future);
                if (!b.this.e.isDone() && !future.isCancelled()) {
                    try {
                        if (future.isSuccess()) {
                            b.this.a(dnsQuestion, future.getNow());
                        } else {
                            if (b.this.h) {
                                b.this.a(future.cause());
                            }
                            b.this.a(b.this.d.next(), dnsQuestion);
                        }
                    } finally {
                        b.this.c();
                    }
                }
            }
        });
    }

    void a(DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope) {
        try {
            DnsResponse content = addressedEnvelope.content();
            DnsResponseCode code = content.code();
            if (code == DnsResponseCode.NOERROR) {
                DnsRecordType type = dnsQuestion.type();
                if (!(type == DnsRecordType.A || type == DnsRecordType.AAAA)) {
                    if (type == DnsRecordType.CNAME) {
                        b(dnsQuestion, addressedEnvelope);
                    }
                    return;
                }
                a(type, dnsQuestion, addressedEnvelope);
                return;
            }
            if (this.h) {
                a(addressedEnvelope.sender(), "response code: " + code + " with " + content.count(DnsSection.ANSWER) + " answer(s) and " + content.count(DnsSection.AUTHORITY) + " authority resource(s)");
            }
            if (code != DnsResponseCode.NXDOMAIN) {
                a(this.d.next(), dnsQuestion);
            }
        } finally {
            ReferenceCountUtil.safeRelease(addressedEnvelope);
        }
    }

    private void a(DnsRecordType dnsRecordType, DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope) {
        ByteBuf content;
        int readableBytes;
        DnsResponse content2 = addressedEnvelope.content();
        Map<String, String> a2 = a(content2);
        int count = content2.count(DnsSection.ANSWER);
        boolean z = false;
        for (int i = 0; i < count; i++) {
            DnsRecord recordAt = content2.recordAt(DnsSection.ANSWER, i);
            DnsRecordType type = recordAt.type();
            if (type == DnsRecordType.A || type == DnsRecordType.AAAA) {
                String lowerCase = dnsQuestion.name().toLowerCase(Locale.US);
                String lowerCase2 = recordAt.name().toLowerCase(Locale.US);
                if (!lowerCase2.equals(lowerCase)) {
                    do {
                        lowerCase = a2.get(lowerCase);
                        if (!lowerCase2.equals(lowerCase)) {
                            break;
                            break;
                        }
                        break;
                    } while (lowerCase != null);
                    if (lowerCase == null) {
                        continue;
                    }
                }
                if ((recordAt instanceof DnsRawRecord) && ((readableBytes = (content = ((ByteBufHolder) recordAt).content()).readableBytes()) == 4 || readableBytes == 16)) {
                    byte[] bArr = new byte[readableBytes];
                    content.getBytes(content.readerIndex(), bArr);
                    try {
                        InetAddress byAddress = InetAddress.getByAddress(this.f, bArr);
                        if (this.l == null) {
                            this.l = new ArrayList(8);
                        }
                        DnsCacheEntry dnsCacheEntry = new DnsCacheEntry(this.f, byAddress);
                        this.g.cache(this.f, byAddress, recordAt.timeToLive(), this.c.e.eventLoop());
                        this.l.add(dnsCacheEntry);
                        z = true;
                    } catch (UnknownHostException e) {
                        throw new Error(e);
                    }
                }
            }
        }
        if (!z) {
            if (this.h) {
                a(addressedEnvelope.sender(), "no matching " + dnsRecordType + " record found");
            }
            if (!a2.isEmpty()) {
                a(dnsQuestion, addressedEnvelope, a2, false);
            }
        }
    }

    private void b(DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope) {
        a(dnsQuestion, addressedEnvelope, a(addressedEnvelope.content()), true);
    }

    private void a(DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, Map<String, String> map, boolean z) {
        String remove;
        String lowerCase = dnsQuestion.name().toLowerCase(Locale.US);
        boolean z2 = false;
        String str = lowerCase;
        while (!map.isEmpty() && (remove = map.remove(str)) != null) {
            z2 = true;
            str = remove;
        }
        if (z2) {
            a(addressedEnvelope.sender(), lowerCase, str);
        } else if (z && this.h) {
            a(addressedEnvelope.sender(), "no matching CNAME record found");
        }
    }

    private static Map<String, String> a(DnsResponse dnsResponse) {
        String a2;
        int count = dnsResponse.count(DnsSection.ANSWER);
        HashMap hashMap = null;
        for (int i = 0; i < count; i++) {
            DnsRecord recordAt = dnsResponse.recordAt(DnsSection.ANSWER, i);
            if (recordAt.type() == DnsRecordType.CNAME && (recordAt instanceof DnsRawRecord) && (a2 = a(((ByteBufHolder) recordAt).content())) != null) {
                if (hashMap == null) {
                    hashMap = new HashMap();
                }
                hashMap.put(recordAt.name().toLowerCase(Locale.US), a2.toLowerCase(Locale.US));
            }
        }
        return hashMap != null ? hashMap : Collections.emptyMap();
    }

    void c() {
        if (!this.k.isEmpty()) {
            if (d()) {
                e();
            }
        } else if (this.l != null || this.o) {
            e();
        } else {
            this.o = true;
            a(this.d.next(), new DefaultDnsQuestion(this.f, DnsRecordType.CNAME));
        }
    }

    private boolean d() {
        List<DnsCacheEntry> list = this.l;
        if (list == null) {
            return false;
        }
        int size = list.size();
        switch (this.j[0]) {
            case IPv4:
                for (int i = 0; i < size; i++) {
                    if (this.l.get(i).address() instanceof Inet4Address) {
                        return true;
                    }
                }
                break;
            case IPv6:
                for (int i2 = 0; i2 < size; i2++) {
                    if (this.l.get(i2).address() instanceof Inet6Address) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private void e() {
        if (!this.k.isEmpty()) {
            Iterator<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> it = this.k.iterator();
            while (it.hasNext()) {
                Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> next = it.next();
                it.remove();
                if (!next.cancel(false)) {
                    next.addListener(a);
                }
            }
        }
        if (this.l != null) {
            for (InternetProtocolFamily internetProtocolFamily : this.j) {
                if (a(internetProtocolFamily.addressType(), this.l)) {
                    return;
                }
            }
        }
        int i = this.i - this.n;
        StringBuilder sb = new StringBuilder(64);
        sb.append("failed to resolve '");
        sb.append(this.f);
        sb.append('\'');
        if (i > 1) {
            if (i < this.i) {
                sb.append(" after ");
                sb.append(i);
                sb.append(" queries ");
            } else {
                sb.append(". Exceeded max queries per resolve ");
                sb.append(this.i);
                sb.append(' ');
            }
        }
        if (this.m != null) {
            sb.append(':');
            sb.append((CharSequence) this.m);
        }
        UnknownHostException unknownHostException = new UnknownHostException(sb.toString());
        this.g.cache(this.f, unknownHostException, this.c.e.eventLoop());
        this.e.tryFailure(unknownHostException);
    }

    static String a(ByteBuf byteBuf) {
        byteBuf.markReaderIndex();
        try {
            int writerIndex = byteBuf.writerIndex();
            StringBuilder sb = new StringBuilder(byteBuf.readableBytes() << 1);
            short readUnsignedByte = byteBuf.readUnsignedByte();
            int i = 0;
            int i2 = -1;
            while (byteBuf.isReadable() && readUnsignedByte != 0) {
                if ((readUnsignedByte & 192) == 192) {
                    if (i2 == -1) {
                        i2 = byteBuf.readerIndex() + 1;
                    }
                    int readUnsignedByte2 = ((readUnsignedByte & 63) << 8) | byteBuf.readUnsignedByte();
                    if (readUnsignedByte2 >= writerIndex) {
                        return null;
                    }
                    byteBuf.readerIndex(readUnsignedByte2);
                    i += 2;
                    if (i >= writerIndex) {
                        return null;
                    }
                } else {
                    sb.append(byteBuf.toString(byteBuf.readerIndex(), readUnsignedByte, CharsetUtil.UTF_8));
                    sb.append('.');
                    byteBuf.skipBytes(readUnsignedByte);
                }
                readUnsignedByte = byteBuf.readUnsignedByte();
            }
            if (i2 != -1) {
                byteBuf.readerIndex(i2);
            }
            if (sb.length() == 0) {
                return null;
            }
            return sb.substring(0, sb.length() - 1);
        } finally {
            byteBuf.resetReaderIndex();
        }
    }

    private void a(InetSocketAddress inetSocketAddress, String str, String str2) {
        if (this.h) {
            if (this.m == null) {
                this.m = new StringBuilder(128);
            }
            this.m.append(StringUtil.NEWLINE);
            this.m.append("\tfrom ");
            this.m.append(inetSocketAddress);
            this.m.append(": ");
            this.m.append(str);
            this.m.append(" CNAME ");
            this.m.append(str2);
        }
        InetSocketAddress next = this.d.next();
        a(next, new DefaultDnsQuestion(str2, DnsRecordType.A));
        a(next, new DefaultDnsQuestion(str2, DnsRecordType.AAAA));
    }

    private void a(InetSocketAddress inetSocketAddress, String str) {
        if (b || this.h) {
            if (this.m == null) {
                this.m = new StringBuilder(128);
            }
            this.m.append(StringUtil.NEWLINE);
            this.m.append("\tfrom ");
            this.m.append(inetSocketAddress);
            this.m.append(": ");
            this.m.append(str);
            return;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Throwable th) {
        if (b || this.h) {
            if (this.m == null) {
                this.m = new StringBuilder(128);
            }
            this.m.append(StringUtil.NEWLINE);
            this.m.append("Caused by: ");
            this.m.append(th);
            return;
        }
        throw new AssertionError();
    }
}
