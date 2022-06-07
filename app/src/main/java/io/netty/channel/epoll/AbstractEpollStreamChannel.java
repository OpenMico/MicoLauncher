package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.MpscLinkedQueueNode;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class AbstractEpollStreamChannel extends AbstractEpollChannel implements DuplexChannel {
    private ChannelPromise g;
    private ScheduledFuture<?> h;
    private SocketAddress i;
    private Queue<SpliceInTask> j;
    private FileDescriptor k;
    private FileDescriptor l;
    static final /* synthetic */ boolean d = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
    private static final String e = " (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) DefaultFileRegion.class) + ')';
    private static final InternalLogger f = InternalLoggerFactory.getInstance(AbstractEpollStreamChannel.class);
    static final ClosedChannelException c = new ClosedChannelException();

    static {
        c.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    static /* synthetic */ SocketAddress d(AbstractEpollStreamChannel abstractEpollStreamChannel) {
        return abstractEpollStreamChannel.i;
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public /* bridge */ /* synthetic */ boolean isActive() {
        return super.isActive();
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public /* bridge */ /* synthetic */ boolean isOpen() {
        return super.isOpen();
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public /* bridge */ /* synthetic */ ChannelMetadata metadata() {
        return super.metadata();
    }

    @Deprecated
    protected AbstractEpollStreamChannel(Channel channel, int i) {
        this(channel, new Socket(i));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(int i) {
        this(new Socket(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public AbstractEpollStreamChannel(FileDescriptor fileDescriptor) {
        this(new Socket(fileDescriptor.intValue()));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(Socket socket) {
        this(socket, a(socket));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractEpollStreamChannel(Channel channel, Socket socket) {
        super(channel, socket, Native.EPOLLIN, true);
        this.flags |= Native.EPOLLRDHUP;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractEpollStreamChannel(Socket socket, boolean z) {
        super(null, socket, Native.EPOLLIN, z);
        this.flags |= Native.EPOLLRDHUP;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new a();
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel abstractEpollStreamChannel, int i) {
        return spliceTo(abstractEpollStreamChannel, i, newPromise());
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel abstractEpollStreamChannel, int i, ChannelPromise channelPromise) {
        if (abstractEpollStreamChannel.eventLoop() != eventLoop()) {
            throw new IllegalArgumentException("EventLoops are not the same.");
        } else if (i < 0) {
            throw new IllegalArgumentException("len: " + i + " (expected: >= 0)");
        } else if (abstractEpollStreamChannel.config().getEpollMode() == EpollMode.LEVEL_TRIGGERED && config().getEpollMode() == EpollMode.LEVEL_TRIGGERED) {
            ObjectUtil.checkNotNull(channelPromise, "promise");
            if (!isOpen()) {
                channelPromise.tryFailure(c);
            } else {
                a(new c(abstractEpollStreamChannel, i, channelPromise));
                a(channelPromise);
            }
            return channelPromise;
        } else {
            throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
        }
    }

    public final ChannelFuture spliceTo(FileDescriptor fileDescriptor, int i, int i2) {
        return spliceTo(fileDescriptor, i, i2, newPromise());
    }

    public final ChannelFuture spliceTo(FileDescriptor fileDescriptor, int i, int i2, ChannelPromise channelPromise) {
        if (i2 < 0) {
            throw new IllegalArgumentException("len: " + i2 + " (expected: >= 0)");
        } else if (i < 0) {
            throw new IllegalArgumentException("offset must be >= 0 but was " + i);
        } else if (config().getEpollMode() == EpollMode.LEVEL_TRIGGERED) {
            ObjectUtil.checkNotNull(channelPromise, "promise");
            if (!isOpen()) {
                channelPromise.tryFailure(c);
            } else {
                a(new b(fileDescriptor, i, i2, channelPromise));
                a(channelPromise);
            }
            return channelPromise;
        } else {
            throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
        }
    }

    private void a(ChannelPromise channelPromise) {
        if (!isOpen() && channelPromise.tryFailure(c)) {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    AbstractEpollStreamChannel.this.c();
                }
            });
        }
    }

    private boolean a(ChannelOutboundBuffer channelOutboundBuffer, ByteBuf byteBuf, int i) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            channelOutboundBuffer.remove();
            return true;
        } else if (byteBuf.hasMemoryAddress() || byteBuf.nioBufferCount() == 1) {
            int doWriteBytes = doWriteBytes(byteBuf, i);
            channelOutboundBuffer.removeBytes(doWriteBytes);
            return doWriteBytes == readableBytes;
        } else {
            ByteBuffer[] nioBuffers = byteBuf.nioBuffers();
            return a(channelOutboundBuffer, nioBuffers, nioBuffers.length, readableBytes, i);
        }
    }

    private boolean a(ChannelOutboundBuffer channelOutboundBuffer, e eVar, int i) throws IOException {
        long c2 = eVar.c();
        int b2 = eVar.b();
        if (!d && c2 == 0) {
            throw new AssertionError();
        } else if (d || b2 != 0) {
            int i2 = b2 + 0;
            boolean z = true;
            long j = c2;
            int i3 = b2;
            int i4 = 0;
            for (int i5 = i - 1; i5 >= 0; i5--) {
                long writevAddresses = fd().writevAddresses(eVar.a(i4), i3);
                if (writevAddresses == 0) {
                    break;
                }
                j -= writevAddresses;
                if (j == 0) {
                    break;
                }
                do {
                    long a2 = eVar.a(i4, writevAddresses);
                    if (a2 == -1) {
                        break;
                    }
                    i4++;
                    i3--;
                    writevAddresses -= a2;
                    if (i4 < i2) {
                    }
                } while (writevAddresses > 0);
            }
            z = false;
            channelOutboundBuffer.removeBytes(c2 - j);
            return z;
        } else {
            throw new AssertionError();
        }
    }

    private boolean a(ChannelOutboundBuffer channelOutboundBuffer, ByteBuffer[] byteBufferArr, int i, long j, int i2) throws IOException {
        if (d || j != 0) {
            int i3 = i + 0;
            boolean z = true;
            int i4 = i2 - 1;
            int i5 = i;
            long j2 = j;
            int i6 = 0;
            while (i4 >= 0) {
                long writev = fd().writev(byteBufferArr, i6, i5);
                if (writev == 0) {
                    break;
                }
                j2 -= writev;
                if (j2 == 0) {
                    break;
                }
                while (true) {
                    ByteBuffer byteBuffer = byteBufferArr[i6];
                    int position = byteBuffer.position();
                    long limit = byteBuffer.limit() - position;
                    if (limit > writev) {
                        byteBuffer.position(position + ((int) writev));
                        break;
                    }
                    i6++;
                    i5--;
                    writev -= limit;
                    if (i6 < i3 && writev > 0) {
                        i4 = i4;
                    }
                }
                i4--;
                z = true;
            }
            z = false;
            channelOutboundBuffer.removeBytes(j - j2);
            return z;
        }
        throw new AssertionError();
    }

    private boolean a(ChannelOutboundBuffer channelOutboundBuffer, DefaultFileRegion defaultFileRegion, int i) throws Exception {
        long j;
        long count = defaultFileRegion.count();
        if (defaultFileRegion.transferred() >= count) {
            channelOutboundBuffer.remove();
            return true;
        }
        long position = defaultFileRegion.position();
        boolean z = false;
        long j2 = 0;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            long transferred = defaultFileRegion.transferred();
            long sendfile = Native.sendfile(fd().intValue(), defaultFileRegion, position, transferred, count - transferred);
            if (sendfile == 0) {
                break;
            }
            j2 += sendfile;
            if (defaultFileRegion.transfered() >= count) {
                z = true;
                j = j2;
                break;
            }
        }
        j = j2;
        if (j > 0) {
            channelOutboundBuffer.progress(j);
        }
        if (z) {
            channelOutboundBuffer.remove();
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002c, code lost:
        a(io.netty.channel.epoll.Native.EPOLLOUT);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
        return;
     */
    @Override // io.netty.channel.AbstractChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void doWrite(io.netty.channel.ChannelOutboundBuffer r4) throws java.lang.Exception {
        /*
            r3 = this;
            io.netty.channel.epoll.EpollChannelConfig r0 = r3.config()
            int r0 = r0.getWriteSpinCount()
        L_0x0008:
            int r1 = r4.size()
            if (r1 != 0) goto L_0x0014
            int r4 = io.netty.channel.epoll.Native.EPOLLOUT
            r3.b(r4)
            return
        L_0x0014:
            r2 = 1
            if (r1 <= r2) goto L_0x0026
            java.lang.Object r1 = r4.current()
            boolean r1 = r1 instanceof io.netty.buffer.ByteBuf
            if (r1 == 0) goto L_0x0026
            boolean r1 = r3.a(r4, r0)
            if (r1 != 0) goto L_0x0008
            goto L_0x002c
        L_0x0026:
            boolean r1 = r3.doWriteSingle(r4, r0)
            if (r1 != 0) goto L_0x0008
        L_0x002c:
            int r4 = io.netty.channel.epoll.Native.EPOLLOUT
            r3.a(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.AbstractEpollStreamChannel.doWrite(io.netty.channel.ChannelOutboundBuffer):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean doWriteSingle(ChannelOutboundBuffer channelOutboundBuffer, int i) throws Exception {
        Object current = channelOutboundBuffer.current();
        if (current instanceof ByteBuf) {
            return a(channelOutboundBuffer, (ByteBuf) current, i);
        }
        if (current instanceof DefaultFileRegion) {
            return a(channelOutboundBuffer, (DefaultFileRegion) current, i);
        }
        if (!(current instanceof d)) {
            throw new Error();
        } else if (!((d) current).a()) {
            return false;
        } else {
            channelOutboundBuffer.remove();
            return true;
        }
    }

    private boolean a(ChannelOutboundBuffer channelOutboundBuffer, int i) throws Exception {
        if (PlatformDependent.hasUnsafe()) {
            e a2 = ((b) eventLoop()).a();
            channelOutboundBuffer.forEachFlushedMessage(a2);
            if (a2.b() < 1) {
                channelOutboundBuffer.removeBytes(0L);
            } else if (!a(channelOutboundBuffer, a2, i)) {
                return false;
            }
        } else {
            ByteBuffer[] nioBuffers = channelOutboundBuffer.nioBuffers();
            int nioBufferCount = channelOutboundBuffer.nioBufferCount();
            if (nioBufferCount < 1) {
                channelOutboundBuffer.removeBytes(0L);
            } else if (!a(channelOutboundBuffer, nioBuffers, nioBufferCount, channelOutboundBuffer.nioBufferSize(), i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public Object filterOutboundMessage(Object obj) {
        if (obj instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) obj;
            if (!byteBuf.hasMemoryAddress() && (PlatformDependent.hasUnsafe() || !byteBuf.isDirect())) {
                if (byteBuf instanceof CompositeByteBuf) {
                    CompositeByteBuf compositeByteBuf = (CompositeByteBuf) byteBuf;
                    if (!compositeByteBuf.isDirect() || compositeByteBuf.nioBufferCount() > Native.IOV_MAX) {
                        byteBuf = newDirectBuffer(byteBuf);
                        if (!d && !byteBuf.hasMemoryAddress()) {
                            throw new AssertionError();
                        }
                    }
                } else {
                    byteBuf = newDirectBuffer(byteBuf);
                    if (!d && !byteBuf.hasMemoryAddress()) {
                        throw new AssertionError();
                    }
                }
            }
            return byteBuf;
        } else if ((obj instanceof DefaultFileRegion) || (obj instanceof d)) {
            return obj;
        } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ChannelPromise channelPromise) {
        try {
            fd().shutdown(false, true);
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(ChannelPromise channelPromise) {
        try {
            fd().shutdown(true, false);
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(ChannelPromise channelPromise) {
        try {
            fd().shutdown(true, true);
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isOutputShutdown() {
        return fd().isOutputShutdown();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isInputShutdown() {
        return fd().isInputShutdown();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isShutdown() {
        return fd().isShutdown();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((a) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    AbstractEpollStreamChannel.this.b(channelPromise);
                }
            });
        } else {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                b(channelPromise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.3
                    @Override // java.lang.Runnable
                    public void run() {
                        AbstractEpollStreamChannel.this.b(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((a) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.4
                @Override // java.lang.Runnable
                public void run() {
                    AbstractEpollStreamChannel.this.c(channelPromise);
                }
            });
        } else {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                c(channelPromise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.5
                    @Override // java.lang.Runnable
                    public void run() {
                        AbstractEpollStreamChannel.this.c(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((a) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.6
                @Override // java.lang.Runnable
                public void run() {
                    AbstractEpollStreamChannel.this.d(channelPromise);
                }
            });
        } else {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                d(channelPromise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.7
                    @Override // java.lang.Runnable
                    public void run() {
                        AbstractEpollStreamChannel.this.d(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        try {
            ChannelPromise channelPromise = this.g;
            if (channelPromise != null) {
                channelPromise.tryFailure(c);
                this.g = null;
            }
            ScheduledFuture<?> scheduledFuture = this.h;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                this.h = null;
            }
            super.doClose();
        } finally {
            b(this.k);
            b(this.l);
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (this.j != null) {
            while (true) {
                SpliceInTask poll = this.j.poll();
                if (poll != null) {
                    poll.c.tryFailure(c);
                } else {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            fd().bind(socketAddress2);
        }
        try {
            boolean connect = fd().connect(socketAddress);
            if (!connect) {
                a(Native.EPOLLOUT);
            }
            return connect;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(FileDescriptor fileDescriptor) {
        if (fileDescriptor != null) {
            try {
                fileDescriptor.close();
            } catch (IOException e2) {
                if (f.isWarnEnabled()) {
                    f.warn("Error while closing a pipe", (Throwable) e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class a extends AbstractEpollChannel.AbstractEpollUnsafe {
        static final /* synthetic */ boolean h = !AbstractEpollStreamChannel.class.desiredAssertionStatus();

        /* JADX INFO: Access modifiers changed from: package-private */
        public a() {
            super();
        }

        @Override // io.netty.channel.AbstractChannel.AbstractUnsafe
        protected Executor prepareToClose() {
            return super.prepareToClose();
        }

        private void a(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, c cVar) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.c = false;
                    channelPipeline.fireChannelRead((Object) byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            cVar.readComplete();
            channelPipeline.fireChannelReadComplete();
            channelPipeline.fireExceptionCaught(th);
            if (z || (th instanceof IOException)) {
                e();
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(final SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                try {
                    if (AbstractEpollStreamChannel.this.g == null) {
                        boolean isActive = AbstractEpollStreamChannel.this.isActive();
                        if (AbstractEpollStreamChannel.this.doConnect(socketAddress, socketAddress2)) {
                            a(channelPromise, isActive);
                            return;
                        }
                        AbstractEpollStreamChannel.this.g = channelPromise;
                        AbstractEpollStreamChannel.this.i = socketAddress;
                        int connectTimeoutMillis = AbstractEpollStreamChannel.this.config().getConnectTimeoutMillis();
                        if (connectTimeoutMillis > 0) {
                            AbstractEpollStreamChannel.this.h = AbstractEpollStreamChannel.this.eventLoop().schedule((Runnable) new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.a.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    ChannelPromise channelPromise2 = AbstractEpollStreamChannel.this.g;
                                    ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + socketAddress);
                                    if (channelPromise2 != null && channelPromise2.tryFailure(connectTimeoutException)) {
                                        a aVar = a.this;
                                        aVar.close(aVar.voidPromise());
                                    }
                                }
                            }, connectTimeoutMillis, TimeUnit.MILLISECONDS);
                        }
                        channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.a.2
                            /* renamed from: a */
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (channelFuture.isCancelled()) {
                                    if (AbstractEpollStreamChannel.this.h != null) {
                                        AbstractEpollStreamChannel.this.h.cancel(false);
                                    }
                                    AbstractEpollStreamChannel.this.g = null;
                                    a aVar = a.this;
                                    aVar.close(aVar.voidPromise());
                                }
                            }
                        });
                        return;
                    }
                    throw new IllegalStateException("connection attempt already made");
                } catch (Throwable th) {
                    closeIfClosed();
                    channelPromise.tryFailure(annotateConnectException(th, socketAddress));
                }
            }
        }

        private void a(ChannelPromise channelPromise, boolean z) {
            if (channelPromise != null) {
                AbstractEpollStreamChannel.this.active = true;
                boolean trySuccess = channelPromise.trySuccess();
                if (!z && AbstractEpollStreamChannel.this.isActive()) {
                    AbstractEpollStreamChannel.this.pipeline().fireChannelActive();
                }
                if (!trySuccess) {
                    close(voidPromise());
                }
            }
        }

        private void a(ChannelPromise channelPromise, Throwable th) {
            if (channelPromise != null) {
                channelPromise.tryFailure(th);
                closeIfClosed();
            }
        }

        /* JADX WARN: Finally extract failed */
        private void h() {
            if (h || AbstractEpollStreamChannel.this.eventLoop().inEventLoop()) {
                try {
                    boolean isActive = AbstractEpollStreamChannel.this.isActive();
                    if (g()) {
                        a(AbstractEpollStreamChannel.this.g, isActive);
                        if (AbstractEpollStreamChannel.this.h != null) {
                            AbstractEpollStreamChannel.this.h.cancel(false);
                        }
                        AbstractEpollStreamChannel.this.g = null;
                    }
                } catch (Throwable th) {
                    if (AbstractEpollStreamChannel.this.h != null) {
                        AbstractEpollStreamChannel.this.h.cancel(false);
                    }
                    AbstractEpollStreamChannel.this.g = null;
                    throw th;
                }
            } else {
                throw new AssertionError();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe
        public void f() {
            if (AbstractEpollStreamChannel.this.g != null) {
                h();
            } else {
                super.f();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public boolean g() throws Exception {
            if (AbstractEpollStreamChannel.this.fd().finishConnect()) {
                AbstractEpollStreamChannel.this.b(Native.EPOLLOUT);
                return true;
            }
            AbstractEpollStreamChannel.this.a(Native.EPOLLOUT);
            return false;
        }

        @Override // io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe
        c a(RecvByteBufAllocator.Handle handle) {
            return new d(handle, AbstractEpollStreamChannel.this.config());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0079, code lost:
            r5.release();
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
            if (r6.lastBytesRead() >= 0) goto L_0x009f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
            r3 = true;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:51:0x009f A[EDGE_INSN: B:51:0x009f->B:30:0x009f ?: BREAK  , SYNTHETIC] */
        @Override // io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                r10 = this;
                io.netty.channel.epoll.AbstractEpollStreamChannel r0 = io.netty.channel.epoll.AbstractEpollStreamChannel.this
                io.netty.channel.unix.Socket r0 = r0.fd()
                boolean r0 = r0.isInputShutdown()
                if (r0 == 0) goto L_0x0010
                r10.clearEpollIn0()
                return
            L_0x0010:
                io.netty.channel.epoll.AbstractEpollStreamChannel r0 = io.netty.channel.epoll.AbstractEpollStreamChannel.this
                io.netty.channel.epoll.EpollChannelConfig r0 = r0.config()
                io.netty.channel.epoll.c r6 = r10.recvBufAllocHandle()
                io.netty.channel.epoll.AbstractEpollStreamChannel r1 = io.netty.channel.epoll.AbstractEpollStreamChannel.this
                int r2 = io.netty.channel.epoll.Native.EPOLLET
                boolean r1 = r1.c(r2)
                r6.a(r1)
                io.netty.channel.epoll.AbstractEpollStreamChannel r1 = io.netty.channel.epoll.AbstractEpollStreamChannel.this
                io.netty.channel.ChannelPipeline r2 = r1.pipeline()
                io.netty.buffer.ByteBufAllocator r1 = r0.getAllocator()
                r6.reset(r0)
                r10.b()
            L_0x0035:
                r3 = 0
                r4 = 0
                io.netty.channel.epoll.AbstractEpollStreamChannel r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.this     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                java.util.Queue r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.e(r5)     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r5 == 0) goto L_0x0065
                io.netty.channel.epoll.AbstractEpollStreamChannel r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.this     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                java.util.Queue r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.e(r5)     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                java.lang.Object r5 = r5.peek()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                io.netty.channel.epoll.AbstractEpollStreamChannel$SpliceInTask r5 = (io.netty.channel.epoll.AbstractEpollStreamChannel.SpliceInTask) r5     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r5 == 0) goto L_0x0065
                boolean r5 = r5.a(r6)     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r5 == 0) goto L_0x009f
                io.netty.channel.epoll.AbstractEpollStreamChannel r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.this     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                boolean r5 = r5.isActive()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r5 == 0) goto L_0x0099
                io.netty.channel.epoll.AbstractEpollStreamChannel r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.this     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                java.util.Queue r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.e(r5)     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                r5.remove()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                goto L_0x0099
            L_0x0065:
                io.netty.buffer.ByteBuf r5 = r6.allocate(r1)     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                io.netty.channel.epoll.AbstractEpollStreamChannel r7 = io.netty.channel.epoll.AbstractEpollStreamChannel.this     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                int r7 = r7.doReadBytes(r5)     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                r6.lastBytesRead(r7)     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                int r7 = r6.lastBytesRead()     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                r8 = 1
                if (r7 > 0) goto L_0x0084
                r5.release()     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                int r1 = r6.lastBytesRead()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r1 >= 0) goto L_0x009f
                r3 = r8
                goto L_0x009f
            L_0x0084:
                r6.incMessagesRead(r8)     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                r10.c = r3     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                r2.fireChannelRead(r5)     // Catch: Throwable -> 0x00ab, all -> 0x00b1
                io.netty.channel.epoll.AbstractEpollStreamChannel r5 = io.netty.channel.epoll.AbstractEpollStreamChannel.this     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                io.netty.channel.unix.Socket r5 = r5.fd()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                boolean r5 = r5.isInputShutdown()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r5 == 0) goto L_0x0099
                goto L_0x009f
            L_0x0099:
                boolean r5 = r6.continueReading()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r5 != 0) goto L_0x0035
            L_0x009f:
                r6.readComplete()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                r2.fireChannelReadComplete()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                if (r3 == 0) goto L_0x00bb
                r10.e()     // Catch: Throwable -> 0x00b3, all -> 0x00b1
                goto L_0x00bb
            L_0x00ab:
                r1 = move-exception
                r4 = r1
                r9 = r5
                r5 = r3
                r3 = r9
                goto L_0x00b7
            L_0x00b1:
                r1 = move-exception
                goto L_0x00bf
            L_0x00b3:
                r1 = move-exception
                r5 = r3
                r3 = r4
                r4 = r1
            L_0x00b7:
                r1 = r10
                r1.a(r2, r3, r4, r5, r6)     // Catch: all -> 0x00b1
            L_0x00bb:
                r10.a(r0)
                return
            L_0x00bf:
                r10.a(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.AbstractEpollStreamChannel.a.a():void");
        }
    }

    private void a(final SpliceInTask spliceInTask) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            b(spliceInTask);
        } else {
            eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollStreamChannel.8
                @Override // java.lang.Runnable
                public void run() {
                    AbstractEpollStreamChannel.this.b(spliceInTask);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(SpliceInTask spliceInTask) {
        if (this.j == null) {
            this.j = PlatformDependent.newMpscQueue();
        }
        this.j.add(spliceInTask);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public abstract class SpliceInTask extends MpscLinkedQueueNode<SpliceInTask> {
        final ChannelPromise c;
        int d;

        abstract boolean a(RecvByteBufAllocator.Handle handle);

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.netty.util.internal.MpscLinkedQueueNode
        public SpliceInTask value() {
            return this;
        }

        protected SpliceInTask(int i, ChannelPromise channelPromise) {
            this.c = channelPromise;
            this.d = i;
        }

        protected final int spliceIn(FileDescriptor fileDescriptor, RecvByteBufAllocator.Handle handle) throws IOException {
            int min = Math.min(handle.guess(), this.d);
            int i = 0;
            while (true) {
                int splice = Native.splice(AbstractEpollStreamChannel.this.fd().intValue(), -1L, fileDescriptor.intValue(), -1L, min);
                if (splice == 0) {
                    return i;
                }
                i += splice;
                min -= splice;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class c extends SpliceInTask implements ChannelFutureListener {
        static final /* synthetic */ boolean a = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        private final AbstractEpollStreamChannel f;

        c(AbstractEpollStreamChannel abstractEpollStreamChannel, int i, ChannelPromise channelPromise) {
            super(i, channelPromise);
            this.f = abstractEpollStreamChannel;
        }

        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                this.c.setFailure(channelFuture.cause());
            }
        }

        @Override // io.netty.channel.epoll.AbstractEpollStreamChannel.SpliceInTask
        public boolean a(RecvByteBufAllocator.Handle handle) {
            ChannelPromise channelPromise;
            if (!a && !this.f.eventLoop().inEventLoop()) {
                throw new AssertionError();
            } else if (this.d == 0) {
                this.c.setSuccess();
                return true;
            } else {
                try {
                    FileDescriptor fileDescriptor = this.f.l;
                    if (fileDescriptor == null) {
                        FileDescriptor[] pipe = FileDescriptor.pipe();
                        this.f.k = pipe[0];
                        fileDescriptor = this.f.l = pipe[1];
                    }
                    int spliceIn = spliceIn(fileDescriptor, handle);
                    if (spliceIn > 0) {
                        if (this.d != Integer.MAX_VALUE) {
                            this.d -= spliceIn;
                        }
                        if (this.d == 0) {
                            channelPromise = this.c;
                        } else {
                            channelPromise = this.f.newPromise().addListener((GenericFutureListener<? extends Future<? super Void>>) this);
                        }
                        boolean isAutoRead = AbstractEpollStreamChannel.this.config().isAutoRead();
                        this.f.unsafe().write(new d(this.f, spliceIn, isAutoRead), channelPromise);
                        this.f.unsafe().flush();
                        if (isAutoRead && !channelPromise.isDone()) {
                            AbstractEpollStreamChannel.this.config().setAutoRead(false);
                        }
                    }
                    return this.d == 0;
                } catch (Throwable th) {
                    this.c.setFailure(th);
                    return true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class d {
        static final /* synthetic */ boolean a = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        private final AbstractEpollStreamChannel c;
        private final boolean d;
        private int e;

        d(AbstractEpollStreamChannel abstractEpollStreamChannel, int i, boolean z) {
            this.c = abstractEpollStreamChannel;
            this.e = i;
            this.d = z;
        }

        public boolean a() throws Exception {
            if (a || this.c.eventLoop().inEventLoop()) {
                try {
                    this.e -= Native.splice(this.c.k.intValue(), -1L, this.c.fd().intValue(), -1L, this.e);
                    if (this.e != 0) {
                        return false;
                    }
                    if (this.d) {
                        AbstractEpollStreamChannel.this.config().setAutoRead(true);
                    }
                    return true;
                } catch (IOException e) {
                    if (this.d) {
                        AbstractEpollStreamChannel.this.config().setAutoRead(true);
                    }
                    throw e;
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b extends SpliceInTask {
        static final /* synthetic */ boolean a = !AbstractEpollStreamChannel.class.desiredAssertionStatus();
        private final FileDescriptor f;
        private final ChannelPromise g;
        private final int h;

        /* renamed from: a */
        public b value() {
            return this;
        }

        b(FileDescriptor fileDescriptor, int i, int i2, ChannelPromise channelPromise) {
            super(i2, channelPromise);
            this.f = fileDescriptor;
            this.g = channelPromise;
            this.h = i;
        }

        @Override // io.netty.channel.epoll.AbstractEpollStreamChannel.SpliceInTask
        public boolean a(RecvByteBufAllocator.Handle handle) {
            if (!a && !AbstractEpollStreamChannel.this.eventLoop().inEventLoop()) {
                throw new AssertionError();
            } else if (this.d == 0) {
                this.g.setSuccess();
                return true;
            } else {
                try {
                    FileDescriptor[] pipe = FileDescriptor.pipe();
                    FileDescriptor fileDescriptor = pipe[0];
                    FileDescriptor fileDescriptor2 = pipe[1];
                    int spliceIn = spliceIn(fileDescriptor2, handle);
                    if (spliceIn > 0) {
                        if (this.d != Integer.MAX_VALUE) {
                            this.d -= spliceIn;
                        }
                        do {
                            spliceIn -= Native.splice(fileDescriptor.intValue(), -1L, this.f.intValue(), this.h, spliceIn);
                        } while (spliceIn > 0);
                        if (this.d == 0) {
                            this.g.setSuccess();
                            AbstractEpollStreamChannel.b(fileDescriptor);
                            AbstractEpollStreamChannel.b(fileDescriptor2);
                            return true;
                        }
                    }
                    AbstractEpollStreamChannel.b(fileDescriptor);
                    AbstractEpollStreamChannel.b(fileDescriptor2);
                    return false;
                } catch (Throwable th) {
                    this.g.setFailure(th);
                    return true;
                }
            }
        }
    }
}
