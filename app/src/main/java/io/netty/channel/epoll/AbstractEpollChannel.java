package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.UnixChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.UnresolvedAddressException;

/* loaded from: classes4.dex */
public abstract class AbstractEpollChannel extends AbstractChannel implements UnixChannel {
    private static final ChannelMetadata c = new ChannelMetadata(false);
    protected volatile boolean active;
    private final int d;
    private final Socket e;
    protected int flags;

    @Override // io.netty.channel.Channel
    public abstract EpollChannelConfig config();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public abstract AbstractEpollUnsafe newUnsafe();

    public AbstractEpollChannel(Socket socket, int i) {
        this(null, socket, i, false);
    }

    public AbstractEpollChannel(Channel channel, Socket socket, int i, boolean z) {
        super(channel);
        this.flags = Native.EPOLLET;
        this.e = (Socket) ObjectUtil.checkNotNull(socket, "fd");
        this.d = i;
        this.flags |= i;
        this.active = z;
    }

    static boolean a(Socket socket) {
        try {
            return socket.getSoError() == 0;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public void a(int i) throws IOException {
        if (!c(i)) {
            this.flags = i | this.flags;
            c();
        }
    }

    public void b(int i) throws IOException {
        if (c(i)) {
            this.flags = (~i) & this.flags;
            c();
        }
    }

    public boolean c(int i) {
        return (i & this.flags) != 0;
    }

    @Override // io.netty.channel.unix.UnixChannel
    public final Socket fd() {
        return this.e;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return this.active;
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return c;
    }

    @Override // io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        this.active = false;
        try {
            doDeregister();
        } finally {
            this.e.close();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof b;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.e.isOpen();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDeregister() throws Exception {
        ((b) eventLoop()).c(this);
    }

    @Override // io.netty.channel.AbstractChannel
    protected final void doBeginRead() throws Exception {
        AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollUnsafe) unsafe();
        abstractEpollUnsafe.c = true;
        a(this.d);
        if (abstractEpollUnsafe.d) {
            abstractEpollUnsafe.c();
        }
    }

    public final void b() {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            final AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollUnsafe) unsafe();
            if (eventLoop.inEventLoop()) {
                abstractEpollUnsafe.clearEpollIn0();
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.epoll.AbstractEpollChannel.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!abstractEpollUnsafe.c && !AbstractEpollChannel.this.config().isAutoRead()) {
                            abstractEpollUnsafe.clearEpollIn0();
                        }
                    }
                });
            }
        } else {
            this.flags &= ~this.d;
        }
    }

    private void c() throws IOException {
        if (isOpen() && isRegistered()) {
            ((b) eventLoop()).b(this);
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doRegister() throws Exception {
        ((AbstractEpollUnsafe) unsafe()).e = false;
        ((b) eventLoop()).a(this);
    }

    protected final ByteBuf newDirectBuffer(ByteBuf byteBuf) {
        return newDirectBuffer(byteBuf, byteBuf);
    }

    protected final ByteBuf newDirectBuffer(Object obj, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(obj);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            return a(obj, byteBuf, alloc, readableBytes);
        }
        ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer == null) {
            return a(obj, byteBuf, alloc, readableBytes);
        }
        threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(obj);
        return threadLocalDirectBuffer;
    }

    private static ByteBuf a(Object obj, ByteBuf byteBuf, ByteBufAllocator byteBufAllocator, int i) {
        ByteBuf directBuffer = byteBufAllocator.directBuffer(i);
        directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), i);
        ReferenceCountUtil.safeRelease(obj);
        return directBuffer;
    }

    public static void checkResolvable(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress.isUnresolved()) {
            throw new UnresolvedAddressException();
        }
    }

    protected final int doReadBytes(ByteBuf byteBuf) throws Exception {
        int i;
        int writerIndex = byteBuf.writerIndex();
        unsafe().recvBufAllocHandle().attemptedBytesRead(byteBuf.writableBytes());
        if (byteBuf.hasMemoryAddress()) {
            i = this.e.readAddress(byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
        } else {
            ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
            i = this.e.read(internalNioBuffer, internalNioBuffer.position(), internalNioBuffer.limit());
        }
        if (i > 0) {
            byteBuf.writerIndex(writerIndex + i);
        }
        return i;
    }

    protected final int doWriteBytes(ByteBuf byteBuf, int i) throws Exception {
        ByteBuffer byteBuffer;
        int readableBytes = byteBuf.readableBytes();
        int i2 = 0;
        if (!byteBuf.hasMemoryAddress()) {
            if (byteBuf.nioBufferCount() == 1) {
                byteBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
            } else {
                byteBuffer = byteBuf.nioBuffer();
            }
            for (int i3 = i - 1; i3 >= 0; i3--) {
                int position = byteBuffer.position();
                int write = this.e.write(byteBuffer, position, byteBuffer.limit());
                if (write <= 0) {
                    break;
                }
                byteBuffer.position(position + write);
                i2 += write;
                if (i2 == readableBytes) {
                    return i2;
                }
            }
        } else {
            long memoryAddress = byteBuf.memoryAddress();
            int readerIndex = byteBuf.readerIndex();
            int writerIndex = byteBuf.writerIndex();
            for (int i4 = i - 1; i4 >= 0; i4--) {
                int writeAddress = this.e.writeAddress(memoryAddress, readerIndex, writerIndex);
                if (writeAddress <= 0) {
                    break;
                }
                i2 += writeAddress;
                if (i2 == readableBytes) {
                    return i2;
                }
                readerIndex += writeAddress;
            }
        }
        if (i2 < readableBytes) {
            a(Native.EPOLLOUT);
        }
        return i2;
    }

    /* loaded from: classes4.dex */
    public abstract class AbstractEpollUnsafe extends AbstractChannel.AbstractUnsafe {
        static final /* synthetic */ boolean f = !AbstractEpollChannel.class.desiredAssertionStatus();
        boolean c;
        boolean d;
        boolean e;
        private c h;
        private Runnable i;

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract void a();

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AbstractEpollUnsafe() {
            super();
            AbstractEpollChannel.this = r1;
        }

        final void b() {
            this.d = false;
        }

        final void a(ChannelConfig channelConfig) {
            this.d = this.h.b();
            if (!this.c && !channelConfig.isAutoRead()) {
                AbstractEpollChannel.this.b();
            } else if (this.c && this.d && !AbstractEpollChannel.this.fd().isInputShutdown()) {
                c();
            }
        }

        final void c() {
            if (!this.e) {
                this.e = true;
                if (this.i == null) {
                    this.i = new Runnable() { // from class: io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AbstractEpollUnsafe abstractEpollUnsafe = AbstractEpollUnsafe.this;
                            abstractEpollUnsafe.e = false;
                            abstractEpollUnsafe.a();
                        }
                    };
                }
                AbstractEpollChannel.this.eventLoop().execute(this.i);
            }
        }

        public final void d() {
            recvBufAllocHandle().a();
            if (AbstractEpollChannel.this.isActive()) {
                a();
                g();
            }
            e();
        }

        private void g() {
            try {
                AbstractEpollChannel.this.b(Native.EPOLLRDHUP);
            } catch (IOException e) {
                AbstractEpollChannel.this.pipeline().fireExceptionCaught((Throwable) e);
                close(voidPromise());
            }
        }

        void e() {
            if (AbstractEpollChannel.this.fd().isInputShutdown()) {
                return;
            }
            if (Boolean.TRUE.equals(AbstractEpollChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                try {
                    AbstractEpollChannel.this.fd().shutdown(true, false);
                    clearEpollIn0();
                    AbstractEpollChannel.this.pipeline().fireUserEventTriggered((Object) ChannelInputShutdownEvent.INSTANCE);
                } catch (IOException unused) {
                    AbstractEpollChannel.this.pipeline().fireUserEventTriggered((Object) ChannelInputShutdownEvent.INSTANCE);
                    close(voidPromise());
                }
            } else {
                close(voidPromise());
            }
        }

        @Override // io.netty.channel.AbstractChannel.AbstractUnsafe, io.netty.channel.Channel.Unsafe
        public c recvBufAllocHandle() {
            if (this.h == null) {
                this.h = a(super.recvBufAllocHandle());
            }
            return this.h;
        }

        c a(RecvByteBufAllocator.Handle handle) {
            return new c(handle, AbstractEpollChannel.this.config());
        }

        @Override // io.netty.channel.AbstractChannel.AbstractUnsafe
        public void flush0() {
            if (!AbstractEpollChannel.this.c(Native.EPOLLOUT)) {
                super.flush0();
            }
        }

        public void f() {
            if (!AbstractEpollChannel.this.fd().isOutputShutdown()) {
                super.flush0();
            }
        }

        protected final void clearEpollIn0() {
            if (f || AbstractEpollChannel.this.eventLoop().inEventLoop()) {
                try {
                    this.c = false;
                    AbstractEpollChannel.this.b(AbstractEpollChannel.this.d);
                } catch (IOException e) {
                    AbstractEpollChannel.this.pipeline().fireExceptionCaught((Throwable) e);
                    AbstractEpollChannel.this.unsafe().close(AbstractEpollChannel.this.unsafe().voidPromise());
                }
            } else {
                throw new AssertionError();
            }
        }
    }
}
