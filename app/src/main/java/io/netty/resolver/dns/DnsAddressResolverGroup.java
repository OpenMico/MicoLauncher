package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.InetSocketAddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes4.dex */
public class DnsAddressResolverGroup extends AddressResolverGroup<InetSocketAddress> {
    private final ChannelFactory<? extends DatagramChannel> a;
    private final InetSocketAddress b;
    private final DnsServerAddresses c;
    private final ConcurrentMap<String, Promise<InetAddress>> d;
    private final ConcurrentMap<String, Promise<List<InetAddress>>> e;

    public DnsAddressResolverGroup(Class<? extends DatagramChannel> cls, DnsServerAddresses dnsServerAddresses) {
        this(cls, DnsNameResolver.a, dnsServerAddresses);
    }

    public DnsAddressResolverGroup(Class<? extends DatagramChannel> cls, InetSocketAddress inetSocketAddress, DnsServerAddresses dnsServerAddresses) {
        this(new ReflectiveChannelFactory(cls), inetSocketAddress, dnsServerAddresses);
    }

    public DnsAddressResolverGroup(ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddresses dnsServerAddresses) {
        this(channelFactory, DnsNameResolver.a, dnsServerAddresses);
    }

    public DnsAddressResolverGroup(ChannelFactory<? extends DatagramChannel> channelFactory, InetSocketAddress inetSocketAddress, DnsServerAddresses dnsServerAddresses) {
        this.d = PlatformDependent.newConcurrentHashMap();
        this.e = PlatformDependent.newConcurrentHashMap();
        this.a = channelFactory;
        this.b = inetSocketAddress;
        this.c = dnsServerAddresses;
    }

    @Override // io.netty.resolver.AddressResolverGroup
    protected final AddressResolver<InetSocketAddress> newResolver(EventExecutor eventExecutor) throws Exception {
        if (eventExecutor instanceof EventLoop) {
            return newResolver((EventLoop) eventExecutor, this.a, this.b, this.c);
        }
        throw new IllegalStateException("unsupported executor type: " + StringUtil.simpleClassName(eventExecutor) + " (expected: " + StringUtil.simpleClassName((Class<?>) EventLoop.class));
    }

    @Deprecated
    protected AddressResolver<InetSocketAddress> newResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, InetSocketAddress inetSocketAddress, DnsServerAddresses dnsServerAddresses) throws Exception {
        return new InetSocketAddressResolver(eventLoop, new e(eventLoop, newNameResolver(eventLoop, channelFactory, inetSocketAddress, dnsServerAddresses), this.d, this.e));
    }

    protected NameResolver<InetAddress> newNameResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, InetSocketAddress inetSocketAddress, DnsServerAddresses dnsServerAddresses) throws Exception {
        return new DnsNameResolverBuilder(eventLoop).channelFactory(channelFactory).localAddress(inetSocketAddress).nameServerAddresses(dnsServerAddresses).build();
    }
}
