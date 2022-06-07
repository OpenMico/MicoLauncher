package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerAdapter;

/* compiled from: CodecUtil.java */
/* loaded from: classes4.dex */
final class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(ChannelHandlerAdapter channelHandlerAdapter) {
        if (channelHandlerAdapter.isSharable()) {
            throw new IllegalStateException("@Sharable annotation is not allowed");
        }
    }
}
