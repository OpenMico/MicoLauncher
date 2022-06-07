package io.netty.handler.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.socksx.v5.DefaultSocks5CommandRequest;
import io.netty.handler.codec.socksx.v5.DefaultSocks5InitialRequest;
import io.netty.handler.codec.socksx.v5.DefaultSocks5PasswordAuthRequest;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5ClientEncoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponse;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandStatus;
import io.netty.handler.codec.socksx.v5.Socks5CommandType;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequest;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponse;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponse;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthStatus;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collections;

/* loaded from: classes4.dex */
public final class Socks5ProxyHandler extends ProxyHandler {
    private static final Socks5InitialRequest a = new DefaultSocks5InitialRequest(Collections.singletonList(Socks5AuthMethod.NO_AUTH));
    private static final Socks5InitialRequest b = new DefaultSocks5InitialRequest(Arrays.asList(Socks5AuthMethod.NO_AUTH, Socks5AuthMethod.PASSWORD));
    private final String c;
    private final String d;
    private String e;
    private String f;

    @Override // io.netty.handler.proxy.ProxyHandler
    public String protocol() {
        return "socks5";
    }

    public Socks5ProxyHandler(SocketAddress socketAddress) {
        this(socketAddress, null, null);
    }

    public Socks5ProxyHandler(SocketAddress socketAddress, String str, String str2) {
        super(socketAddress);
        String str3 = null;
        if (str != null && str.length() == 0) {
            str = null;
        }
        str3 = (str2 == null || str2.length() != 0) ? str2 : str3;
        this.c = str;
        this.d = str3;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    public String authScheme() {
        return a() == Socks5AuthMethod.PASSWORD ? "password" : "none";
    }

    public String username() {
        return this.c;
    }

    public String password() {
        return this.d;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        String name = channelHandlerContext.name();
        Socks5InitialResponseDecoder socks5InitialResponseDecoder = new Socks5InitialResponseDecoder();
        pipeline.addBefore(name, null, socks5InitialResponseDecoder);
        this.e = pipeline.context(socks5InitialResponseDecoder).name();
        this.f = this.e + ".encoder";
        pipeline.addBefore(name, this.f, Socks5ClientEncoder.DEFAULT);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().remove(this.f);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        if (pipeline.context(this.e) != null) {
            pipeline.remove(this.e);
        }
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        return a() == Socks5AuthMethod.PASSWORD ? b : a;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof Socks5InitialResponse) {
            Socks5InitialResponse socks5InitialResponse = (Socks5InitialResponse) obj;
            Socks5AuthMethod a2 = a();
            if (socks5InitialResponse.authMethod() == Socks5AuthMethod.NO_AUTH || socks5InitialResponse.authMethod() == a2) {
                if (a2 == Socks5AuthMethod.NO_AUTH) {
                    a(channelHandlerContext);
                } else if (a2 == Socks5AuthMethod.PASSWORD) {
                    ChannelPipeline pipeline = channelHandlerContext.pipeline();
                    String str = this.e;
                    pipeline.replace(str, str, new Socks5PasswordAuthResponseDecoder());
                    String str2 = this.c;
                    if (str2 == null) {
                        str2 = "";
                    }
                    String str3 = this.d;
                    if (str3 == null) {
                        str3 = "";
                    }
                    sendToProxyServer(new DefaultSocks5PasswordAuthRequest(str2, str3));
                } else {
                    throw new Error();
                }
                return false;
            }
            throw new ProxyConnectException(exceptionMessage("unexpected authMethod: " + socks5InitialResponse.authMethod()));
        } else if (obj instanceof Socks5PasswordAuthResponse) {
            Socks5PasswordAuthResponse socks5PasswordAuthResponse = (Socks5PasswordAuthResponse) obj;
            if (socks5PasswordAuthResponse.status() == Socks5PasswordAuthStatus.SUCCESS) {
                a(channelHandlerContext);
                return false;
            }
            throw new ProxyConnectException(exceptionMessage("authStatus: " + socks5PasswordAuthResponse.status()));
        } else {
            Socks5CommandResponse socks5CommandResponse = (Socks5CommandResponse) obj;
            if (socks5CommandResponse.status() == Socks5CommandStatus.SUCCESS) {
                return true;
            }
            throw new ProxyConnectException(exceptionMessage("status: " + socks5CommandResponse.status()));
        }
    }

    private Socks5AuthMethod a() {
        if (this.c == null && this.d == null) {
            return Socks5AuthMethod.NO_AUTH;
        }
        return Socks5AuthMethod.PASSWORD;
    }

    private void a(ChannelHandlerContext channelHandlerContext) throws Exception {
        String str;
        Socks5AddressType socks5AddressType;
        InetSocketAddress inetSocketAddress = (InetSocketAddress) destinationAddress();
        if (inetSocketAddress.isUnresolved()) {
            socks5AddressType = Socks5AddressType.DOMAIN;
            str = inetSocketAddress.getHostString();
        } else {
            str = inetSocketAddress.getAddress().getHostAddress();
            if (NetUtil.isValidIpV4Address(str)) {
                socks5AddressType = Socks5AddressType.IPv4;
            } else if (NetUtil.isValidIpV6Address(str)) {
                socks5AddressType = Socks5AddressType.IPv6;
            } else {
                throw new ProxyConnectException(exceptionMessage("unknown address type: " + StringUtil.simpleClassName(str)));
            }
        }
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        String str2 = this.e;
        pipeline.replace(str2, str2, new Socks5CommandResponseDecoder());
        sendToProxyServer(new DefaultSocks5CommandRequest(Socks5CommandType.CONNECT, socks5AddressType, str, inetSocketAddress.getPort()));
    }
}
