package io.netty.handler.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.socksx.v4.DefaultSocks4CommandRequest;
import io.netty.handler.codec.socksx.v4.Socks4ClientDecoder;
import io.netty.handler.codec.socksx.v4.Socks4ClientEncoder;
import io.netty.handler.codec.socksx.v4.Socks4CommandResponse;
import io.netty.handler.codec.socksx.v4.Socks4CommandStatus;
import io.netty.handler.codec.socksx.v4.Socks4CommandType;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class Socks4ProxyHandler extends ProxyHandler {
    private final String a;
    private String b;
    private String c;

    @Override // io.netty.handler.proxy.ProxyHandler
    public String protocol() {
        return "socks4";
    }

    public Socks4ProxyHandler(SocketAddress socketAddress) {
        this(socketAddress, null);
    }

    public Socks4ProxyHandler(SocketAddress socketAddress, String str) {
        super(socketAddress);
        if (str != null && str.length() == 0) {
            str = null;
        }
        this.a = str;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    public String authScheme() {
        return this.a != null ? "username" : "none";
    }

    public String username() {
        return this.a;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        String name = channelHandlerContext.name();
        Socks4ClientDecoder socks4ClientDecoder = new Socks4ClientDecoder();
        pipeline.addBefore(name, null, socks4ClientDecoder);
        this.b = pipeline.context(socks4ClientDecoder).name();
        this.c = this.b + ".encoder";
        pipeline.addBefore(name, this.c, Socks4ClientEncoder.INSTANCE);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().remove(this.c);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().remove(this.b);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        String str;
        InetSocketAddress inetSocketAddress = (InetSocketAddress) destinationAddress();
        if (inetSocketAddress.isUnresolved()) {
            str = inetSocketAddress.getHostString();
        } else {
            str = inetSocketAddress.getAddress().getHostAddress();
        }
        Socks4CommandType socks4CommandType = Socks4CommandType.CONNECT;
        int port = inetSocketAddress.getPort();
        String str2 = this.a;
        if (str2 == null) {
            str2 = "";
        }
        return new DefaultSocks4CommandRequest(socks4CommandType, str, port, str2);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        Socks4CommandStatus status = ((Socks4CommandResponse) obj).status();
        if (status == Socks4CommandStatus.SUCCESS) {
            return true;
        }
        throw new ProxyConnectException(exceptionMessage("status: " + status));
    }
}
