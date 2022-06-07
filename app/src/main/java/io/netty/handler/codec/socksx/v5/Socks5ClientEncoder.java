package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.RandomAccess;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class Socks5ClientEncoder extends MessageToByteEncoder<Socks5Message> {
    public static final Socks5ClientEncoder DEFAULT = new Socks5ClientEncoder();
    private final Socks5AddressEncoder a;

    protected Socks5ClientEncoder() {
        this(Socks5AddressEncoder.DEFAULT);
    }

    public Socks5ClientEncoder(Socks5AddressEncoder socks5AddressEncoder) {
        if (socks5AddressEncoder != null) {
            this.a = socks5AddressEncoder;
            return;
        }
        throw new NullPointerException("addressEncoder");
    }

    protected final Socks5AddressEncoder addressEncoder() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Socks5Message socks5Message, ByteBuf byteBuf) throws Exception {
        if (socks5Message instanceof Socks5InitialRequest) {
            a((Socks5InitialRequest) socks5Message, byteBuf);
        } else if (socks5Message instanceof Socks5PasswordAuthRequest) {
            a((Socks5PasswordAuthRequest) socks5Message, byteBuf);
        } else if (socks5Message instanceof Socks5CommandRequest) {
            a((Socks5CommandRequest) socks5Message, byteBuf);
        } else {
            throw new EncoderException("unsupported message type: " + StringUtil.simpleClassName(socks5Message));
        }
    }

    private static void a(Socks5InitialRequest socks5InitialRequest, ByteBuf byteBuf) {
        byteBuf.writeByte(socks5InitialRequest.version().byteValue());
        List<Socks5AuthMethod> authMethods = socks5InitialRequest.authMethods();
        int size = authMethods.size();
        byteBuf.writeByte(size);
        if (authMethods instanceof RandomAccess) {
            for (int i = 0; i < size; i++) {
                byteBuf.writeByte(authMethods.get(i).byteValue());
            }
            return;
        }
        for (Socks5AuthMethod socks5AuthMethod : authMethods) {
            byteBuf.writeByte(socks5AuthMethod.byteValue());
        }
    }

    private static void a(Socks5PasswordAuthRequest socks5PasswordAuthRequest, ByteBuf byteBuf) {
        byteBuf.writeByte(1);
        String username = socks5PasswordAuthRequest.username();
        byteBuf.writeByte(username.length());
        ByteBufUtil.writeAscii(byteBuf, username);
        String password = socks5PasswordAuthRequest.password();
        byteBuf.writeByte(password.length());
        ByteBufUtil.writeAscii(byteBuf, password);
    }

    private void a(Socks5CommandRequest socks5CommandRequest, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(socks5CommandRequest.version().byteValue());
        byteBuf.writeByte(socks5CommandRequest.type().byteValue());
        byteBuf.writeByte(0);
        Socks5AddressType dstAddrType = socks5CommandRequest.dstAddrType();
        byteBuf.writeByte(dstAddrType.byteValue());
        this.a.encodeAddress(dstAddrType, socks5CommandRequest.dstAddr(), byteBuf);
        byteBuf.writeShort(socks5CommandRequest.dstPort());
    }
}
