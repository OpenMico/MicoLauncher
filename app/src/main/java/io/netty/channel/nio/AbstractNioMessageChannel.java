package io.netty.channel.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.AbstractNioChannel;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class AbstractNioMessageChannel extends AbstractNioChannel {
    boolean f;

    protected boolean continueOnWriteError() {
        return false;
    }

    protected abstract int doReadMessages(List<Object> list) throws Exception;

    protected abstract boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

    public AbstractNioMessageChannel(Channel channel, SelectableChannel selectableChannel, int i) {
        super(channel, selectableChannel, i);
    }

    @Override // io.netty.channel.AbstractChannel
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    public void doBeginRead() throws Exception {
        if (!this.f) {
            super.doBeginRead();
        }
    }

    /* loaded from: classes4.dex */
    public final class a extends AbstractNioChannel.AbstractNioUnsafe {
        static final /* synthetic */ boolean c = !AbstractNioMessageChannel.class.desiredAssertionStatus();
        private final List<Object> g;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a() {
            super();
            AbstractNioMessageChannel.this = r1;
            this.g = new ArrayList();
        }

        @Override // io.netty.channel.nio.AbstractNioChannel.NioUnsafe
        public void read() {
            boolean z;
            boolean z2;
            boolean isAutoRead;
            if (c || AbstractNioMessageChannel.this.eventLoop().inEventLoop()) {
                ChannelConfig config = AbstractNioMessageChannel.this.config();
                ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
                RecvByteBufAllocator.Handle recvBufAllocHandle = AbstractNioMessageChannel.this.unsafe().recvBufAllocHandle();
                recvBufAllocHandle.reset(config);
                Throwable th = null;
                do {
                    z = false;
                    try {
                        try {
                            int doReadMessages = AbstractNioMessageChannel.this.doReadMessages(this.g);
                            if (doReadMessages == 0) {
                                break;
                            } else if (doReadMessages < 0) {
                                z = true;
                                break;
                            } else {
                                recvBufAllocHandle.incMessagesRead(doReadMessages);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            z = false;
                        }
                    } finally {
                        if (!AbstractNioMessageChannel.this.d && !config.isAutoRead()) {
                            removeReadOp();
                        }
                    }
                } while (recvBufAllocHandle.continueReading());
                z = false;
                int size = this.g.size();
                for (int i = 0; i < size; i++) {
                    AbstractNioMessageChannel.this.d = false;
                    pipeline.fireChannelRead(this.g.get(i));
                }
                this.g.clear();
                recvBufAllocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                if (th != null) {
                    if ((th instanceof IOException) && !(th instanceof PortUnreachableException)) {
                        if (!(AbstractNioMessageChannel.this instanceof ServerChannel)) {
                            z = true;
                        }
                    }
                    pipeline.fireExceptionCaught(th);
                }
                if (z) {
                    AbstractNioMessageChannel.this.f = true;
                    if (AbstractNioMessageChannel.this.isOpen()) {
                        close(voidPromise());
                    }
                }
                if (!(z2 || isAutoRead)) {
                    return;
                }
                return;
            }
            throw new AssertionError();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SelectionKey selectionKey = selectionKey();
        int interestOps = selectionKey.interestOps();
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                boolean z = false;
                try {
                    int writeSpinCount = config().getWriteSpinCount() - 1;
                    while (true) {
                        if (writeSpinCount < 0) {
                            break;
                        } else if (doWriteMessage(current, channelOutboundBuffer)) {
                            z = true;
                            break;
                        } else {
                            writeSpinCount--;
                        }
                    }
                } catch (IOException e) {
                    if (continueOnWriteError()) {
                        channelOutboundBuffer.remove(e);
                    } else {
                        throw e;
                    }
                }
                if (z) {
                    channelOutboundBuffer.remove();
                } else if ((interestOps & 4) == 0) {
                    selectionKey.interestOps(interestOps | 4);
                    return;
                } else {
                    return;
                }
            } else if ((interestOps & 4) != 0) {
                selectionKey.interestOps(interestOps & (-5));
                return;
            } else {
                return;
            }
        }
    }
}
