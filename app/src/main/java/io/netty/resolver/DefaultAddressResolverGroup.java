package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public final class DefaultAddressResolverGroup extends AddressResolverGroup<InetSocketAddress> {
    public static final DefaultAddressResolverGroup INSTANCE = new DefaultAddressResolverGroup();

    private DefaultAddressResolverGroup() {
    }

    @Override // io.netty.resolver.AddressResolverGroup
    protected AddressResolver<InetSocketAddress> newResolver(EventExecutor eventExecutor) throws Exception {
        return new DefaultNameResolver(eventExecutor).asAddressResolver();
    }
}
