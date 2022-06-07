package io.netty.handler.codec.http2;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: AbstractHttp2StreamChannel.java */
/* loaded from: classes4.dex */
abstract class a extends AbstractChannel {
    private final ChannelConfig g = new DefaultChannelConfig(this);
    private final Queue<Object> h = new ArrayDeque(4);
    private final Runnable i = new Runnable() { // from class: io.netty.handler.codec.http2.a.1
        @Override // java.lang.Runnable
        public void run() {
            if (a.this.k) {
                a.this.k = false;
                a.this.unsafe().recvBufAllocHandle().readComplete();
                a.this.pipeline().fireChannelReadComplete();
            }
        }
    };
    private boolean j;
    private boolean k;
    static final /* synthetic */ boolean d = !a.class.desiredAssertionStatus();
    protected static final Object c = new Object();
    private static final ChannelMetadata e = new ChannelMetadata(false, 16);
    private static final ClosedChannelException f = new ClosedChannelException();

    protected abstract void a(int i);

    protected abstract void a(Object obj) throws Exception;

    protected abstract void b();

    protected abstract EventExecutor c();

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return true;
    }

    static {
        f.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public a(Channel channel) {
        super(channel);
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return e;
    }

    @Override // io.netty.channel.Channel
    public ChannelConfig config() {
        return this.g;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return !this.j;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return !this.j;
    }

    @Override // io.netty.channel.AbstractChannel
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new C0203a();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return parent().localAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return parent().remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        this.j = true;
        while (!this.h.isEmpty()) {
            ReferenceCountUtil.release(this.h.poll());
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBeginRead() {
        if (!this.k) {
            RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
            recvBufAllocHandle.reset(config());
            if (this.h.isEmpty()) {
                this.k = true;
                return;
            }
            do {
                Object poll = this.h.poll();
                if (poll == null) {
                    break;
                } else if (!a(poll, recvBufAllocHandle)) {
                    return;
                }
            } while (recvBufAllocHandle.continueReading());
            recvBufAllocHandle.readComplete();
            pipeline().fireChannelReadComplete();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected final void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        if (!this.j) {
            EventExecutor c2 = c();
            if (c2.inEventLoop()) {
                while (true) {
                    Object current = channelOutboundBuffer.current();
                    if (current == null) {
                        b();
                        return;
                    }
                    try {
                        a(ReferenceCountUtil.retain(current));
                    } catch (Throwable th) {
                        pipeline().fireExceptionCaught(th);
                    }
                    channelOutboundBuffer.remove();
                }
            } else {
                final Object[] objArr = new Object[channelOutboundBuffer.size()];
                for (int i = 0; i < objArr.length; i++) {
                    objArr[i] = ReferenceCountUtil.retain(channelOutboundBuffer.current());
                    channelOutboundBuffer.remove();
                }
                c2.execute(new OneTimeTask() { // from class: io.netty.handler.codec.http2.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        for (Object obj : objArr) {
                            try {
                                a.this.a(obj);
                            } catch (Throwable th2) {
                                a.this.pipeline().fireExceptionCaught(th2);
                            }
                        }
                        a.this.b();
                    }
                });
            }
        } else {
            throw f;
        }
    }

    protected void b(final Object obj) {
        if (eventLoop().inEventLoop()) {
            c(obj);
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.handler.codec.http2.a.3
                @Override // java.lang.Runnable
                public void run() {
                    a.this.c(obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Object obj) {
        if (this.j) {
            ReferenceCountUtil.release(obj);
        } else if (!this.k) {
            this.h.add(obj);
        } else if (d || this.h.isEmpty()) {
            RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
            this.k = a(ObjectUtil.checkNotNull(obj, "msg"), recvBufAllocHandle);
            if (!recvBufAllocHandle.continueReading()) {
                this.i.run();
            }
        } else {
            throw new AssertionError();
        }
    }

    protected void d() {
        if (eventLoop().inEventLoop()) {
            this.i.run();
        } else {
            eventLoop().execute(this.i);
        }
    }

    private boolean a(Object obj, RecvByteBufAllocator.Handle handle) {
        int i = 0;
        if (obj == c) {
            handle.readComplete();
            pipeline().fireChannelReadComplete();
            unsafe().close(voidPromise());
            return false;
        }
        if (obj instanceof Http2DataFrame) {
            Http2DataFrame http2DataFrame = (Http2DataFrame) obj;
            i = http2DataFrame.content().readableBytes() + http2DataFrame.padding();
            handle.lastBytesRead(i);
        } else {
            handle.lastBytesRead(9);
        }
        handle.incMessagesRead(1);
        pipeline().fireChannelRead(obj);
        if (i != 0) {
            a(i);
        }
        return true;
    }

    /* compiled from: AbstractHttp2StreamChannel.java */
    /* renamed from: io.netty.handler.codec.http2.a$a  reason: collision with other inner class name */
    /* loaded from: classes4.dex */
    private final class C0203a extends AbstractChannel.AbstractUnsafe {
        private C0203a() {
            super();
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            channelPromise.setFailure((Throwable) new UnsupportedOperationException());
        }
    }
}
