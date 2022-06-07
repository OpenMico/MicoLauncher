package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

/* loaded from: classes4.dex */
public abstract class AbstractNioByteChannel extends AbstractNioChannel {
    private static final String f = " (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) FileRegion.class) + ')';
    private Runnable g;

    protected abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    protected abstract int doWriteBytes(ByteBuf byteBuf) throws Exception;

    protected abstract long doWriteFileRegion(FileRegion fileRegion) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract ChannelFuture shutdownInput();

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractNioByteChannel(Channel channel, SelectableChannel selectableChannel) {
        super(channel, selectableChannel, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public class NioByteUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
        /* JADX INFO: Access modifiers changed from: protected */
        public NioByteUnsafe() {
            super();
        }

        private void a(ChannelPipeline channelPipeline) {
            if (!AbstractNioByteChannel.this.isOpen()) {
                return;
            }
            if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                AbstractNioByteChannel.this.shutdownInput();
                SelectionKey selectionKey = AbstractNioByteChannel.this.selectionKey();
                selectionKey.interestOps(selectionKey.interestOps() & (~AbstractNioByteChannel.this.readInterestOp));
                channelPipeline.fireUserEventTriggered((Object) ChannelInputShutdownEvent.INSTANCE);
                return;
            }
            close(voidPromise());
        }

        private void a(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, RecvByteBufAllocator.Handle handle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    AbstractNioByteChannel.this.d = false;
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

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0036, code lost:
            r2 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x002d, code lost:
            r5.release();
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0034, code lost:
            if (r7.lastBytesRead() >= 0) goto L_0x0048;
         */
        @Override // io.netty.channel.nio.AbstractNioChannel.NioUnsafe
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void read() {
            /*
                r9 = this;
                io.netty.channel.nio.AbstractNioByteChannel r0 = io.netty.channel.nio.AbstractNioByteChannel.this
                io.netty.channel.ChannelConfig r0 = r0.config()
                io.netty.channel.nio.AbstractNioByteChannel r1 = io.netty.channel.nio.AbstractNioByteChannel.this
                io.netty.channel.ChannelPipeline r3 = r1.pipeline()
                io.netty.buffer.ByteBufAllocator r1 = r0.getAllocator()
                io.netty.channel.RecvByteBufAllocator$Handle r7 = r9.recvBufAllocHandle()
                r7.reset(r0)
            L_0x0017:
                r2 = 0
                r4 = 0
                io.netty.buffer.ByteBuf r5 = r7.allocate(r1)     // Catch: Throwable -> 0x0067, all -> 0x0065
                io.netty.channel.nio.AbstractNioByteChannel r6 = io.netty.channel.nio.AbstractNioByteChannel.this     // Catch: Throwable -> 0x0060, all -> 0x0065
                int r6 = r6.doReadBytes(r5)     // Catch: Throwable -> 0x0060, all -> 0x0065
                r7.lastBytesRead(r6)     // Catch: Throwable -> 0x0060, all -> 0x0065
                int r6 = r7.lastBytesRead()     // Catch: Throwable -> 0x0060, all -> 0x0065
                r8 = 1
                if (r6 > 0) goto L_0x0038
                r5.release()     // Catch: Throwable -> 0x0060, all -> 0x0065
                int r1 = r7.lastBytesRead()     // Catch: Throwable -> 0x0067, all -> 0x0065
                if (r1 >= 0) goto L_0x0048
                r2 = r8
                goto L_0x0048
            L_0x0038:
                r7.incMessagesRead(r8)     // Catch: Throwable -> 0x0060, all -> 0x0065
                io.netty.channel.nio.AbstractNioByteChannel r6 = io.netty.channel.nio.AbstractNioByteChannel.this     // Catch: Throwable -> 0x0060, all -> 0x0065
                r6.d = r2     // Catch: Throwable -> 0x0060, all -> 0x0065
                r3.fireChannelRead(r5)     // Catch: Throwable -> 0x0060, all -> 0x0065
                boolean r5 = r7.continueReading()     // Catch: Throwable -> 0x0067, all -> 0x0065
                if (r5 != 0) goto L_0x0017
            L_0x0048:
                r7.readComplete()     // Catch: Throwable -> 0x0067, all -> 0x0065
                r3.fireChannelReadComplete()     // Catch: Throwable -> 0x0067, all -> 0x0065
                if (r2 == 0) goto L_0x0053
                r9.a(r3)     // Catch: Throwable -> 0x0067, all -> 0x0065
            L_0x0053:
                io.netty.channel.nio.AbstractNioByteChannel r1 = io.netty.channel.nio.AbstractNioByteChannel.this
                boolean r1 = r1.d
                if (r1 != 0) goto L_0x007d
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x007d
                goto L_0x007a
            L_0x0060:
                r1 = move-exception
                r6 = r2
                r4 = r5
                r5 = r1
                goto L_0x006a
            L_0x0065:
                r1 = move-exception
                goto L_0x007e
            L_0x0067:
                r1 = move-exception
                r5 = r1
                r6 = r2
            L_0x006a:
                r2 = r9
                r2.a(r3, r4, r5, r6, r7)     // Catch: all -> 0x0065
                io.netty.channel.nio.AbstractNioByteChannel r1 = io.netty.channel.nio.AbstractNioByteChannel.this
                boolean r1 = r1.d
                if (r1 != 0) goto L_0x007d
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x007d
            L_0x007a:
                r9.removeReadOp()
            L_0x007d:
                return
            L_0x007e:
                io.netty.channel.nio.AbstractNioByteChannel r2 = io.netty.channel.nio.AbstractNioByteChannel.this
                boolean r2 = r2.d
                if (r2 != 0) goto L_0x008d
                boolean r0 = r0.isAutoRead()
                if (r0 != 0) goto L_0x008d
                r9.removeReadOp()
            L_0x008d:
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.AbstractNioByteChannel.NioByteUnsafe.read():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009e, code lost:
        incompleteWrite(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a1, code lost:
        return;
     */
    @Override // io.netty.channel.AbstractChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doWrite(io.netty.channel.ChannelOutboundBuffer r19) throws java.lang.Exception {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = 0
            r3 = -1
            r5 = r2
            r4 = r3
        L_0x0008:
            java.lang.Object r6 = r19.current()
            if (r6 != 0) goto L_0x0012
            r18.clearOpWrite()
            return
        L_0x0012:
            boolean r7 = r6 instanceof io.netty.buffer.ByteBuf
            r8 = 0
            r10 = 1
            if (r7 == 0) goto L_0x0052
            io.netty.buffer.ByteBuf r6 = (io.netty.buffer.ByteBuf) r6
            int r7 = r6.readableBytes()
            if (r7 != 0) goto L_0x0025
            r19.remove()
            goto L_0x0008
        L_0x0025:
            if (r4 != r3) goto L_0x002f
            io.netty.channel.ChannelConfig r4 = r18.config()
            int r4 = r4.getWriteSpinCount()
        L_0x002f:
            int r7 = r4 + (-1)
        L_0x0031:
            if (r7 < 0) goto L_0x0048
            int r11 = r0.doWriteBytes(r6)
            if (r11 != 0) goto L_0x003c
            r5 = r10
            r10 = r2
            goto L_0x0049
        L_0x003c:
            long r11 = (long) r11
            long r8 = r8 + r11
            boolean r11 = r6.isReadable()
            if (r11 != 0) goto L_0x0045
            goto L_0x0049
        L_0x0045:
            int r7 = r7 + (-1)
            goto L_0x0031
        L_0x0048:
            r10 = r2
        L_0x0049:
            r1.progress(r8)
            if (r10 == 0) goto L_0x009e
            r19.remove()
            goto L_0x0008
        L_0x0052:
            boolean r7 = r6 instanceof io.netty.channel.FileRegion
            if (r7 == 0) goto L_0x00a2
            io.netty.channel.FileRegion r6 = (io.netty.channel.FileRegion) r6
            long r11 = r6.transferred()
            long r13 = r6.count()
            int r7 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r7 < 0) goto L_0x0066
            r7 = r10
            goto L_0x0067
        L_0x0066:
            r7 = r2
        L_0x0067:
            if (r7 != 0) goto L_0x0097
            if (r4 != r3) goto L_0x0073
            io.netty.channel.ChannelConfig r4 = r18.config()
            int r4 = r4.getWriteSpinCount()
        L_0x0073:
            int r11 = r4 + (-1)
            r12 = r8
        L_0x0076:
            if (r11 < 0) goto L_0x0094
            long r14 = r0.doWriteFileRegion(r6)
            int r16 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r16 != 0) goto L_0x0082
            r5 = r10
            goto L_0x0094
        L_0x0082:
            long r12 = r12 + r14
            long r14 = r6.transferred()
            long r16 = r6.count()
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 < 0) goto L_0x0091
            r7 = r10
            goto L_0x0094
        L_0x0091:
            int r11 = r11 + (-1)
            goto L_0x0076
        L_0x0094:
            r1.progress(r12)
        L_0x0097:
            if (r7 == 0) goto L_0x009e
            r19.remove()
            goto L_0x0008
        L_0x009e:
            r0.incompleteWrite(r5)
            return
        L_0x00a2:
            java.lang.Error r1 = new java.lang.Error
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.AbstractNioByteChannel.doWrite(io.netty.channel.ChannelOutboundBuffer):void");
    }

    @Override // io.netty.channel.AbstractChannel
    protected final Object filterOutboundMessage(Object obj) {
        if (obj instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) obj;
            return byteBuf.isDirect() ? obj : newDirectBuffer(byteBuf);
        } else if (obj instanceof FileRegion) {
            return obj;
        } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + f);
        }
    }

    protected final void incompleteWrite(boolean z) {
        if (z) {
            setOpWrite();
            return;
        }
        Runnable runnable = this.g;
        if (runnable == null) {
            runnable = new Runnable() { // from class: io.netty.channel.nio.AbstractNioByteChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    AbstractNioByteChannel.this.flush();
                }
            };
            this.g = runnable;
        }
        eventLoop().execute(runnable);
    }

    protected final void setOpWrite() {
        SelectionKey selectionKey = selectionKey();
        if (selectionKey.isValid()) {
            int interestOps = selectionKey.interestOps();
            if ((interestOps & 4) == 0) {
                selectionKey.interestOps(interestOps | 4);
            }
        }
    }

    protected final void clearOpWrite() {
        SelectionKey selectionKey = selectionKey();
        if (selectionKey.isValid()) {
            int interestOps = selectionKey.interestOps();
            if ((interestOps & 4) != 0) {
                selectionKey.interestOps(interestOps & (-5));
            }
        }
    }
}
