package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class NoopAddressResolverGroup extends AddressResolverGroup<SocketAddress> {
    public static final NoopAddressResolverGroup INSTANCE = new NoopAddressResolverGroup();

    private NoopAddressResolverGroup() {
    }

    @Override // io.netty.resolver.AddressResolverGroup
    protected AddressResolver<SocketAddress> newResolver(EventExecutor eventExecutor) throws Exception {
        return new NoopAddressResolver(eventExecutor);
    }
}
