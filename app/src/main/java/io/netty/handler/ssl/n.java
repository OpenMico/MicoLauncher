package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SslUtils.java */
/* loaded from: classes4.dex */
public final class n {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(ByteBuf byteBuf, int i) {
        boolean z;
        int i2 = 0;
        switch (byteBuf.getUnsignedByte(i)) {
            case 20:
            case 21:
            case 22:
            case 23:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            if (byteBuf.getUnsignedByte(i + 1) == 3) {
                int unsignedShort = byteBuf.getUnsignedShort(i + 3) + 5;
                if (unsignedShort <= 5) {
                    z = false;
                    i2 = unsignedShort;
                } else {
                    i2 = unsignedShort;
                }
            } else {
                z = false;
            }
        }
        if (!z) {
            int i3 = (byteBuf.getUnsignedByte(i) & 128) != 0 ? 2 : 3;
            short unsignedByte = byteBuf.getUnsignedByte(i + i3 + 1);
            if (unsignedByte != 2 && unsignedByte != 3) {
                return -1;
            }
            if (i3 == 2) {
                i2 = (byteBuf.getShort(i) & ShortCompanionObject.MAX_VALUE) + 2;
            } else {
                i2 = (byteBuf.getShort(i) & 16383) + 3;
            }
            if (i2 <= i3) {
                return -1;
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(ChannelHandlerContext channelHandlerContext, Throwable th) {
        channelHandlerContext.flush();
        channelHandlerContext.fireUserEventTriggered((Object) new SslHandshakeCompletionEvent(th));
        channelHandlerContext.close();
    }
}
