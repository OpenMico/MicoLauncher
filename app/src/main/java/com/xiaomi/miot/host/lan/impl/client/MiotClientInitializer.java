package com.xiaomi.miot.host.lan.impl.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

/* loaded from: classes2.dex */
public class MiotClientInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_PACKET_LENGTH = 8192;
    private MiotClientHandler handler;

    public MiotClientInitializer(MiotClientListener miotClientListener) {
        this.handler = new MiotClientHandler(miotClientListener);
    }

    public void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new JsonObjectDecoder(8192));
        pipeline.addLast(this.handler);
    }

    public static byte[] translateToFullBufferData(byte[] bArr) {
        byte[] bArr2 = new byte[8064];
        if (bArr == null) {
            return bArr2;
        }
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public static byte[] add8EmptyBufferData(byte[] bArr) {
        if (bArr == null) {
            return bArr;
        }
        byte[] bArr2 = new byte[bArr.length + 8];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }
}
