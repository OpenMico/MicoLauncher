package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public abstract class InetNameResolver extends SimpleNameResolver<InetAddress> {
    private volatile AddressResolver<InetSocketAddress> a;

    /* JADX INFO: Access modifiers changed from: protected */
    public InetNameResolver(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    public AddressResolver<InetSocketAddress> asAddressResolver() {
        AddressResolver<InetSocketAddress> addressResolver = this.a;
        if (addressResolver == null) {
            synchronized (this) {
                addressResolver = this.a;
                if (addressResolver == null) {
                    addressResolver = new InetSocketAddressResolver(executor(), this);
                    this.a = addressResolver;
                }
            }
        }
        return addressResolver;
    }
}
