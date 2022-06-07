package io.netty.bootstrap;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ServerBootstrapConfig extends AbstractBootstrapConfig<ServerBootstrap, ServerChannel> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public ServerBootstrapConfig(ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
    }

    public EventLoopGroup childGroup() {
        return ((ServerBootstrap) this.bootstrap).childGroup();
    }

    public ChannelHandler childHandler() {
        return ((ServerBootstrap) this.bootstrap).i();
    }

    public Map<ChannelOption<?>, Object> childOptions() {
        return ((ServerBootstrap) this.bootstrap).j();
    }

    public Map<AttributeKey<?>, Object> childAttrs() {
        return ((ServerBootstrap) this.bootstrap).k();
    }

    @Override // io.netty.bootstrap.AbstractBootstrapConfig
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.setLength(sb.length() - 1);
        sb.append(", ");
        EventLoopGroup childGroup = childGroup();
        if (childGroup != null) {
            sb.append("childGroup: ");
            sb.append(StringUtil.simpleClassName(childGroup));
            sb.append(", ");
        }
        Map<ChannelOption<?>, Object> childOptions = childOptions();
        if (!childOptions.isEmpty()) {
            sb.append("childOptions: ");
            sb.append(childOptions);
            sb.append(", ");
        }
        Map<AttributeKey<?>, Object> childAttrs = childAttrs();
        if (!childAttrs.isEmpty()) {
            sb.append("childAttrs: ");
            sb.append(childAttrs);
            sb.append(", ");
        }
        ChannelHandler childHandler = childHandler();
        if (childHandler != null) {
            sb.append("childHandler: ");
            sb.append(childHandler);
            sb.append(", ");
        }
        if (sb.charAt(sb.length() - 1) == '(') {
            sb.append(')');
        } else {
            sb.setCharAt(sb.length() - 2, ')');
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
