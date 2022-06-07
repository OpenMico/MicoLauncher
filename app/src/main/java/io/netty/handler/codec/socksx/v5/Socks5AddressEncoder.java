package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;

/* loaded from: classes4.dex */
public interface Socks5AddressEncoder {
    public static final Socks5AddressEncoder DEFAULT = new Socks5AddressEncoder() { // from class: io.netty.handler.codec.socksx.v5.Socks5AddressEncoder.1
        @Override // io.netty.handler.codec.socksx.v5.Socks5AddressEncoder
        public void encodeAddress(Socks5AddressType socks5AddressType, String str, ByteBuf byteBuf) throws Exception {
            byte byteValue = socks5AddressType.byteValue();
            if (byteValue == Socks5AddressType.IPv4.byteValue()) {
                if (str != null) {
                    byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(str));
                } else {
                    byteBuf.writeInt(0);
                }
            } else if (byteValue == Socks5AddressType.DOMAIN.byteValue()) {
                if (str != null) {
                    byte[] bytes = str.getBytes(CharsetUtil.US_ASCII);
                    byteBuf.writeByte(bytes.length);
                    byteBuf.writeBytes(bytes);
                    return;
                }
                byteBuf.writeByte(1);
                byteBuf.writeByte(0);
            } else if (byteValue != Socks5AddressType.IPv6.byteValue()) {
                throw new EncoderException("unsupported addrType: " + (socks5AddressType.byteValue() & 255));
            } else if (str != null) {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(str));
            } else {
                byteBuf.writeLong(0L);
                byteBuf.writeLong(0L);
            }
        }
    };

    void encodeAddress(Socks5AddressType socks5AddressType, String str, ByteBuf byteBuf) throws Exception;
}
