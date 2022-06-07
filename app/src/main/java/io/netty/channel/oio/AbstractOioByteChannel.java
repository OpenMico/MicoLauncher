package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class AbstractOioByteChannel extends AbstractOioChannel {
    private static final ChannelMetadata d = new ChannelMetadata(false);
    private static final String e = " (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) FileRegion.class) + ')';

    protected abstract int available();

    protected abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    protected abstract void doWriteBytes(ByteBuf byteBuf) throws Exception;

    protected abstract void doWriteFileRegion(FileRegion fileRegion) throws Exception;

    protected abstract boolean isInputShutdown();

    protected abstract ChannelFuture shutdownInput();

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractOioByteChannel(Channel channel) {
        super(channel);
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return d;
    }

    private void a(ChannelPipeline channelPipeline) {
        if (!isOpen()) {
            return;
        }
        if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
            shutdownInput();
            channelPipeline.fireUserEventTriggered((Object) ChannelInputShutdownEvent.INSTANCE);
            return;
        }
        unsafe().close(unsafe().voidPromise());
    }

    private void a(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, RecvByteBufAllocator.Handle handle) {
        if (byteBuf != null) {
            if (byteBuf.isReadable()) {
                this.c = false;
                channelPipeline.fireChannelRead((Object) byteBuf);
            } else {
                byteBuf.release();
            }
        }
        handle.readComplete();
        channelPipeline.fireChannelReadComplete();
        channelPipeline.fireExceptionCaught(th);
        if (z || (th instanceof IOException)) {
            a(channelPipeline);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003e, code lost:
        if (r6.isReadable() != false) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
        r6.release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:
        if (r7.lastBytesRead() >= 0) goto L_0x004a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004a, code lost:
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004b, code lost:
        r6 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004d, code lost:
        r2 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
        r6 = false;
        r4 = null;
        r1 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
        r4 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00f5  */
    @Override // io.netty.channel.oio.AbstractOioChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void doRead() {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.oio.AbstractOioByteChannel.doRead():void");
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                if (current instanceof ByteBuf) {
                    ByteBuf byteBuf = (ByteBuf) current;
                    int readableBytes = byteBuf.readableBytes();
                    while (readableBytes > 0) {
                        doWriteBytes(byteBuf);
                        int readableBytes2 = byteBuf.readableBytes();
                        channelOutboundBuffer.progress(readableBytes - readableBytes2);
                        readableBytes = readableBytes2;
                    }
                    channelOutboundBuffer.remove();
                } else if (current instanceof FileRegion) {
                    FileRegion fileRegion = (FileRegion) current;
                    long transferred = fileRegion.transferred();
                    doWriteFileRegion(fileRegion);
                    channelOutboundBuffer.progress(fileRegion.transferred() - transferred);
                    channelOutboundBuffer.remove();
                } else {
                    channelOutboundBuffer.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(current)));
                }
            } else {
                return;
            }
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected final Object filterOutboundMessage(Object obj) throws Exception {
        if ((obj instanceof ByteBuf) || (obj instanceof FileRegion)) {
            return obj;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + e);
    }
}
