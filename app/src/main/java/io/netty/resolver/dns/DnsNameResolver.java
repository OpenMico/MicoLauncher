package io.netty.resolver.dns;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import io.netty.handler.codec.dns.DatagramDnsResponse;
import io.netty.handler.codec.dns.DatagramDnsResponseDecoder;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.resolver.InetNameResolver;
import io.netty.util.NetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.IDN;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class DnsNameResolver extends InetNameResolver {
    private static final DatagramDnsResponseDecoder i;
    private static final DatagramDnsQueryEncoder j;
    final DnsServerAddresses c;
    final ChannelFuture d;
    final DatagramChannel e;
    private final DnsCache k;
    private final long m;
    private final int n;
    private final boolean o;
    private final InternetProtocolFamily[] p;
    private final boolean q;
    private final int r;
    private final boolean s;
    private final HostsFileEntriesResolver t;
    static final /* synthetic */ boolean g = !DnsNameResolver.class.desiredAssertionStatus();
    private static final InternalLogger h = InternalLoggerFactory.getInstance(DnsNameResolver.class);
    static final InetSocketAddress a = new InetSocketAddress(0);
    static final InternetProtocolFamily[] b = new InternetProtocolFamily[2];
    final d f = new d();
    private final FastThreadLocal<DnsServerAddressStream> l = new FastThreadLocal<DnsServerAddressStream>() { // from class: io.netty.resolver.dns.DnsNameResolver.1
        /* renamed from: a */
        public DnsServerAddressStream initialValue() throws Exception {
            return DnsNameResolver.this.c.stream();
        }
    };

    /* JADX WARN: Multi-variable type inference failed */
    private static Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> a(Promise<?> promise) {
        return promise;
    }

    static {
        if (Boolean.getBoolean("java.net.preferIPv6Addresses")) {
            b[0] = InternetProtocolFamily.IPv6;
            b[1] = InternetProtocolFamily.IPv4;
            h.debug("-Djava.net.preferIPv6Addresses: true");
        } else {
            b[0] = InternetProtocolFamily.IPv4;
            b[1] = InternetProtocolFamily.IPv6;
            h.debug("-Djava.net.preferIPv6Addresses: false");
        }
        i = new DatagramDnsResponseDecoder();
        j = new DatagramDnsQueryEncoder();
    }

    public DnsNameResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, InetSocketAddress inetSocketAddress, DnsServerAddresses dnsServerAddresses, DnsCache dnsCache, long j2, InternetProtocolFamily[] internetProtocolFamilyArr, boolean z, int i2, boolean z2, int i3, boolean z3, HostsFileEntriesResolver hostsFileEntriesResolver) {
        super(eventLoop);
        ObjectUtil.checkNotNull(channelFactory, "channelFactory");
        ObjectUtil.checkNotNull(inetSocketAddress, "localAddress");
        this.c = (DnsServerAddresses) ObjectUtil.checkNotNull(dnsServerAddresses, "nameServerAddresses");
        this.m = ObjectUtil.checkPositive(j2, "queryTimeoutMillis");
        this.p = (InternetProtocolFamily[]) ObjectUtil.checkNonEmpty(internetProtocolFamilyArr, "resolvedAddressTypes");
        this.q = z;
        this.n = ObjectUtil.checkPositive(i2, "maxQueriesPerResolve");
        this.o = z2;
        this.r = ObjectUtil.checkPositive(i3, "maxPayloadSize");
        this.s = z3;
        this.t = (HostsFileEntriesResolver) ObjectUtil.checkNotNull(hostsFileEntriesResolver, "hostsFileEntriesResolver");
        this.k = dnsCache;
        this.d = a(channelFactory, inetSocketAddress);
        this.e = (DatagramChannel) this.d.channel();
        this.e.config().setRecvByteBufAllocator((RecvByteBufAllocator) new FixedRecvByteBufAllocator(i3));
    }

    private ChannelFuture a(ChannelFactory<? extends DatagramChannel> channelFactory, InetSocketAddress inetSocketAddress) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(executor());
        bootstrap.channelFactory((ChannelFactory) channelFactory);
        final a aVar = new a();
        bootstrap.handler(new ChannelInitializer<DatagramChannel>() { // from class: io.netty.resolver.dns.DnsNameResolver.2
            /* renamed from: a */
            public void initChannel(DatagramChannel datagramChannel) throws Exception {
                datagramChannel.pipeline().addLast(DnsNameResolver.i, DnsNameResolver.j, aVar);
            }
        });
        ChannelFuture bind = bootstrap.bind(inetSocketAddress);
        bind.channel().closeFuture().addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.resolver.dns.DnsNameResolver.3
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                DnsNameResolver.this.k.clear();
            }
        });
        return bind;
    }

    public DnsCache resolveCache() {
        return this.k;
    }

    public long queryTimeoutMillis() {
        return this.m;
    }

    public List<InternetProtocolFamily> resolvedAddressTypes() {
        return Arrays.asList(this.p);
    }

    public InternetProtocolFamily[] a() {
        return this.p;
    }

    public boolean isRecursionDesired() {
        return this.q;
    }

    public int maxQueriesPerResolve() {
        return this.n;
    }

    public boolean isTraceEnabled() {
        return this.o;
    }

    public int maxPayloadSize() {
        return this.r;
    }

    public boolean isOptResourceEnabled() {
        return this.s;
    }

    public HostsFileEntriesResolver hostsFileEntriesResolver() {
        return this.t;
    }

    @Override // io.netty.resolver.SimpleNameResolver, io.netty.resolver.NameResolver, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.e.close();
    }

    @Override // io.netty.resolver.SimpleNameResolver
    public EventLoop executor() {
        return (EventLoop) super.executor();
    }

    private InetAddress a(String str) {
        HostsFileEntriesResolver hostsFileEntriesResolver = this.t;
        if (hostsFileEntriesResolver != null) {
            return hostsFileEntriesResolver.address(str);
        }
        return null;
    }

    @Override // io.netty.resolver.SimpleNameResolver
    protected void doResolve(String str, Promise<InetAddress> promise) throws Exception {
        doResolve(str, promise, this.k);
    }

    protected void doResolve(String str, Promise<InetAddress> promise, DnsCache dnsCache) throws Exception {
        byte[] createByteArrayFromIpAddressString = NetUtil.createByteArrayFromIpAddressString(str);
        if (createByteArrayFromIpAddressString != null) {
            promise.setSuccess(InetAddress.getByAddress(createByteArrayFromIpAddressString));
            return;
        }
        String b2 = b(str);
        InetAddress a2 = a(b2);
        if (a2 != null) {
            promise.setSuccess(a2);
        } else if (!a(b2, promise, dnsCache)) {
            b(b2, promise, dnsCache);
        }
    }

    private boolean a(String str, Promise<InetAddress> promise, DnsCache dnsCache) {
        InetAddress inetAddress;
        Throwable th;
        List<DnsCacheEntry> list = dnsCache.get(str);
        if (list == null) {
            return false;
        }
        synchronized (list) {
            int size = list.size();
            if (!g && size <= 0) {
                throw new AssertionError();
            }
            inetAddress = null;
            if (list.get(0).cause() != null) {
                th = list.get(0).cause();
            } else {
                InternetProtocolFamily[] internetProtocolFamilyArr = this.p;
                InetAddress inetAddress2 = null;
                for (InternetProtocolFamily internetProtocolFamily : internetProtocolFamilyArr) {
                    int i2 = 0;
                    while (true) {
                        if (i2 < size) {
                            DnsCacheEntry dnsCacheEntry = list.get(i2);
                            if (internetProtocolFamily.addressType().isInstance(dnsCacheEntry.address())) {
                                inetAddress2 = dnsCacheEntry.address();
                                break;
                            }
                            i2++;
                        }
                    }
                }
                th = null;
                inetAddress = inetAddress2;
            }
        }
        if (inetAddress != null) {
            b(promise, inetAddress);
            return true;
        } else if (th == null) {
            return false;
        } else {
            if (promise.tryFailure(th)) {
                return true;
            }
            h.warn("Failed to notify failure to a promise: {}", promise, th);
            return true;
        }
    }

    public static void b(Promise<InetAddress> promise, InetAddress inetAddress) {
        if (!promise.trySuccess(inetAddress)) {
            h.warn("Failed to notify success ({}) to a promise: {}", inetAddress, promise);
        }
    }

    private void b(String str, Promise<InetAddress> promise, DnsCache dnsCache) {
        new b<InetAddress>(this, str, promise, dnsCache) { // from class: io.netty.resolver.dns.DnsNameResolver.4
            @Override // io.netty.resolver.dns.b
            protected boolean a(Class<? extends InetAddress> cls, List<DnsCacheEntry> list) {
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    InetAddress address = list.get(i2).address();
                    if (cls.isInstance(address)) {
                        DnsNameResolver.b(a(), address);
                        return true;
                    }
                }
                return false;
            }
        }.b();
    }

    @Override // io.netty.resolver.SimpleNameResolver
    protected void doResolveAll(String str, Promise<List<InetAddress>> promise) throws Exception {
        doResolveAll(str, promise, this.k);
    }

    protected void doResolveAll(String str, Promise<List<InetAddress>> promise, DnsCache dnsCache) throws Exception {
        byte[] createByteArrayFromIpAddressString = NetUtil.createByteArrayFromIpAddressString(str);
        if (createByteArrayFromIpAddressString != null) {
            promise.setSuccess(Collections.singletonList(InetAddress.getByAddress(createByteArrayFromIpAddressString)));
            return;
        }
        String b2 = b(str);
        InetAddress a2 = a(b2);
        if (a2 != null) {
            promise.setSuccess(Collections.singletonList(a2));
        } else if (!c(b2, promise, dnsCache)) {
            d(b2, promise, dnsCache);
        }
    }

    private boolean c(String str, Promise<List<InetAddress>> promise, DnsCache dnsCache) {
        ArrayList arrayList;
        Throwable th;
        List<DnsCacheEntry> list = dnsCache.get(str);
        if (list == null) {
            return false;
        }
        synchronized (list) {
            int size = list.size();
            if (!g && size <= 0) {
                throw new AssertionError();
            }
            arrayList = null;
            if (list.get(0).cause() != null) {
                th = list.get(0).cause();
            } else {
                InternetProtocolFamily[] internetProtocolFamilyArr = this.p;
                int length = internetProtocolFamilyArr.length;
                int i2 = 0;
                ArrayList arrayList2 = null;
                while (i2 < length) {
                    InternetProtocolFamily internetProtocolFamily = internetProtocolFamilyArr[i2];
                    ArrayList arrayList3 = arrayList2;
                    for (int i3 = 0; i3 < size; i3++) {
                        DnsCacheEntry dnsCacheEntry = list.get(i3);
                        if (internetProtocolFamily.addressType().isInstance(dnsCacheEntry.address())) {
                            if (arrayList3 == null) {
                                arrayList3 = new ArrayList(size);
                            }
                            arrayList3.add(dnsCacheEntry.address());
                        }
                    }
                    i2++;
                    arrayList2 = arrayList3;
                }
                th = null;
                arrayList = arrayList2;
            }
        }
        if (arrayList != null) {
            promise.trySuccess(arrayList);
            return true;
        } else if (th == null) {
            return false;
        } else {
            promise.tryFailure(th);
            return true;
        }
    }

    private void d(String str, Promise<List<InetAddress>> promise, DnsCache dnsCache) {
        new b<List<InetAddress>>(this, str, promise, dnsCache) { // from class: io.netty.resolver.dns.DnsNameResolver.5
            @Override // io.netty.resolver.dns.b
            protected boolean a(Class<? extends InetAddress> cls, List<DnsCacheEntry> list) {
                int size = list.size();
                ArrayList arrayList = null;
                for (int i2 = 0; i2 < size; i2++) {
                    InetAddress address = list.get(i2).address();
                    if (cls.isInstance(address)) {
                        if (arrayList == null) {
                            arrayList = new ArrayList(size);
                        }
                        arrayList.add(address);
                    }
                }
                if (arrayList == null) {
                    return false;
                }
                a().trySuccess(arrayList);
                return true;
            }
        }.b();
    }

    private static String b(String str) {
        return IDN.toASCII(str);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion dnsQuestion) {
        return query(e(), dnsQuestion);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable) {
        return query(e(), dnsQuestion, iterable);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion dnsQuestion, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return query(e(), dnsQuestion, Collections.emptyList(), promise);
    }

    private InetSocketAddress e() {
        return this.l.get().next();
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion) {
        return a(inetSocketAddress, dnsQuestion, Collections.emptyList(), this.e.eventLoop().newPromise());
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable) {
        return a(inetSocketAddress, dnsQuestion, iterable, this.e.eventLoop().newPromise());
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return a(inetSocketAddress, dnsQuestion, Collections.emptyList(), promise);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return a(inetSocketAddress, dnsQuestion, iterable, promise);
    }

    private Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> a(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> a2 = a((Promise) ObjectUtil.checkNotNull(promise, "promise"));
        try {
            new c(this, inetSocketAddress, dnsQuestion, iterable, a2).c();
            return a2;
        } catch (Exception e) {
            return a2.setFailure(e);
        }
    }

    /* loaded from: classes4.dex */
    public final class a extends ChannelInboundHandlerAdapter {
        private a() {
            DnsNameResolver.this = r1;
        }

        @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            try {
                DatagramDnsResponse datagramDnsResponse = (DatagramDnsResponse) obj;
                int id = datagramDnsResponse.id();
                if (DnsNameResolver.h.isDebugEnabled()) {
                    DnsNameResolver.h.debug("{} RECEIVED: [{}: {}], {}", DnsNameResolver.this.e, Integer.valueOf(id), datagramDnsResponse.sender(), datagramDnsResponse);
                }
                c a = DnsNameResolver.this.f.a(datagramDnsResponse.sender(), id);
                if (a == null) {
                    DnsNameResolver.h.warn("{} Received a DNS response with an unknown ID: {}", DnsNameResolver.this.e, Integer.valueOf(id));
                } else {
                    a.a(datagramDnsResponse);
                }
            } finally {
                ReferenceCountUtil.safeRelease(obj);
            }
        }

        @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            DnsNameResolver.h.warn("{} Unexpected exception: ", DnsNameResolver.this.e, th);
        }
    }
}
