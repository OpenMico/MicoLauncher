package com.xiaomi.miot.host.lan.impl.client;

import io.netty.channel.ChannelHandlerContext;

/* loaded from: classes2.dex */
public interface MiotClientListener {
    void onConnected();

    void onDisconnected();

    void onReceiveMessage(ChannelHandlerContext channelHandlerContext, byte[] bArr);
}
