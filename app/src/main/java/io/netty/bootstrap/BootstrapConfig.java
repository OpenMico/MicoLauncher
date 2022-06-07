package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.resolver.AddressResolverGroup;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class BootstrapConfig extends AbstractBootstrapConfig<Bootstrap, Channel> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public BootstrapConfig(Bootstrap bootstrap) {
        super(bootstrap);
    }

    public SocketAddress remoteAddress() {
        return ((Bootstrap) this.bootstrap).i();
    }

    public AddressResolverGroup<?> resolver() {
        return ((Bootstrap) this.bootstrap).j();
    }

    @Override // io.netty.bootstrap.AbstractBootstrapConfig
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.setLength(sb.length() - 1);
        sb.append(", resolver: ");
        sb.append(resolver());
        SocketAddress remoteAddress = remoteAddress();
        if (remoteAddress != null) {
            sb.append(", remoteAddress: ");
            sb.append(remoteAddress);
        }
        sb.append(')');
        return sb.toString();
    }
}
