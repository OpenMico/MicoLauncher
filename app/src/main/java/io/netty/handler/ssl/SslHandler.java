package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.channel.PendingWriteQueue;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    static final /* synthetic */ boolean a = !SslHandler.class.desiredAssertionStatus();
    private static final InternalLogger c = InternalLoggerFactory.getInstance(SslHandler.class);
    private static final Pattern d = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
    private static final Pattern e = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
    private static final SSLException f = new SSLException("SSLEngine closed already");
    private static final SSLException g = new SSLException("handshake timed out");
    private static final ClosedChannelException h = new ClosedChannelException();
    private volatile long A;
    private volatile long B;
    private volatile ChannelHandlerContext i;
    private final SSLEngine j;
    private final int k;
    private final Executor l;
    private final ByteBuffer[] m;
    private final boolean n;
    private final boolean o;
    private final boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private PendingWriteQueue t;
    private Promise<Channel> u;
    private final a v;
    private boolean w;
    private boolean x;
    private int y;
    private boolean z;

    static {
        f.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        g.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        h.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public SslHandler(SSLEngine sSLEngine) {
        this(sSLEngine, false);
    }

    public SslHandler(SSLEngine sSLEngine, boolean z) {
        this(sSLEngine, z, ImmediateExecutor.INSTANCE);
    }

    @Deprecated
    public SslHandler(SSLEngine sSLEngine, Executor executor) {
        this(sSLEngine, false, executor);
    }

    @Deprecated
    public SslHandler(SSLEngine sSLEngine, boolean z, Executor executor) {
        boolean z2 = true;
        this.m = new ByteBuffer[1];
        this.u = new a();
        this.v = new a();
        this.A = 10000L;
        this.B = 3000L;
        if (sSLEngine == null) {
            throw new NullPointerException("engine");
        } else if (executor != null) {
            this.j = sSLEngine;
            this.l = executor;
            this.p = z;
            this.k = sSLEngine.getSession().getPacketBufferSize();
            boolean z3 = sSLEngine instanceof OpenSslEngine;
            this.n = z3;
            this.o = z3 ? false : z2;
            setCumulator(z3 ? COMPOSITE_CUMULATOR : MERGE_CUMULATOR);
        } else {
            throw new NullPointerException("delegatedTaskExecutor");
        }
    }

    public long getHandshakeTimeoutMillis() {
        return this.A;
    }

    public void setHandshakeTimeout(long j, TimeUnit timeUnit) {
        if (timeUnit != null) {
            setHandshakeTimeoutMillis(timeUnit.toMillis(j));
            return;
        }
        throw new NullPointerException("unit");
    }

    public void setHandshakeTimeoutMillis(long j) {
        if (j >= 0) {
            this.A = j;
            return;
        }
        throw new IllegalArgumentException("handshakeTimeoutMillis: " + j + " (expected: >= 0)");
    }

    public long getCloseNotifyTimeoutMillis() {
        return this.B;
    }

    public void setCloseNotifyTimeout(long j, TimeUnit timeUnit) {
        if (timeUnit != null) {
            setCloseNotifyTimeoutMillis(timeUnit.toMillis(j));
            return;
        }
        throw new NullPointerException("unit");
    }

    public void setCloseNotifyTimeoutMillis(long j) {
        if (j >= 0) {
            this.B = j;
            return;
        }
        throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + j + " (expected: >= 0)");
    }

    public SSLEngine engine() {
        return this.j;
    }

    public String applicationProtocol() {
        SSLSession session = engine().getSession();
        if (!(session instanceof a)) {
            return null;
        }
        return ((a) session).a();
    }

    public Future<Channel> handshakeFuture() {
        return this.u;
    }

    public ChannelFuture close() {
        return close(this.i.newPromise());
    }

    public ChannelFuture close(final ChannelPromise channelPromise) {
        final ChannelHandlerContext channelHandlerContext = this.i;
        channelHandlerContext.executor().execute(new OneTimeTask() { // from class: io.netty.handler.ssl.SslHandler.1
            @Override // java.lang.Runnable
            public void run() {
                SslHandler.this.x = true;
                SslHandler.this.j.closeOutbound();
                try {
                    SslHandler.this.write(channelHandlerContext, Unpooled.EMPTY_BUFFER, channelPromise);
                    SslHandler.this.flush(channelHandlerContext);
                } catch (Exception e2) {
                    if (!channelPromise.tryFailure(e2)) {
                        SslHandler.c.warn("{} flush() raised a masked exception.", channelHandlerContext.channel(), e2);
                    }
                }
            }
        });
        return channelPromise;
    }

    public Future<Channel> sslCloseFuture() {
        return this.v;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    public void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.t.isEmpty()) {
            this.t.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
        }
        SSLEngine sSLEngine = this.j;
        if (sSLEngine instanceof OpenSslEngine) {
            ((OpenSslEngine) sSLEngine).shutdown();
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        a(channelHandlerContext, channelPromise, true);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        a(channelHandlerContext, channelPromise, false);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.u.isDone()) {
            this.s = true;
        }
        channelHandlerContext.read();
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (!(obj instanceof ByteBuf)) {
            channelPromise.setFailure((Throwable) new UnsupportedMessageTypeException(obj, ByteBuf.class));
        } else {
            this.t.add(obj, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.p || this.q) {
            if (this.t.isEmpty()) {
                this.t.add(Unpooled.EMPTY_BUFFER, channelHandlerContext.newPromise());
            }
            if (!this.u.isDone()) {
                this.r = true;
            }
            try {
                a(channelHandlerContext, false);
            } finally {
                channelHandlerContext.flush();
            }
        } else {
            this.q = true;
            this.t.removeAndWriteAll();
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, boolean z) throws SSLException {
        Throwable th;
        SSLException e2;
        ChannelPromise remove;
        boolean z2;
        ByteBufAllocator alloc = channelHandlerContext.alloc();
        ByteBuf byteBuf = null;
        ChannelPromise channelPromise = null;
        while (true) {
            try {
                try {
                    Object current = this.t.current();
                    if (current == null) {
                        a(channelHandlerContext, byteBuf, channelPromise, z, false);
                        return;
                    }
                    ByteBuf byteBuf2 = (ByteBuf) current;
                    if (byteBuf == null) {
                        byteBuf = b(channelHandlerContext, byteBuf2.readableBytes());
                    }
                    SSLEngineResult a2 = a(alloc, this.j, byteBuf2, byteBuf);
                    remove = !byteBuf2.isReadable() ? this.t.remove() : null;
                    try {
                        if (a2.getStatus() == SSLEngineResult.Status.CLOSED) {
                            this.t.removeAndFailAll(f);
                            z2 = false;
                        } else {
                            switch (AnonymousClass9.a[a2.getHandshakeStatus().ordinal()]) {
                                case 1:
                                    c();
                                    channelPromise = remove;
                                    continue;
                                case 2:
                                    e();
                                    d();
                                    break;
                                case 3:
                                    d();
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    z2 = true;
                                    break;
                                default:
                                    throw new IllegalStateException("Unknown handshake status: " + a2.getHandshakeStatus());
                            }
                            a(channelHandlerContext, byteBuf, remove, z, false);
                            byteBuf = null;
                            channelPromise = null;
                        }
                    } catch (SSLException e3) {
                        e2 = e3;
                        channelPromise = remove;
                        a(channelHandlerContext, e2);
                        throw e2;
                    } catch (Throwable th2) {
                        th = th2;
                        byteBuf = byteBuf;
                        channelPromise = remove;
                        a(channelHandlerContext, byteBuf, channelPromise, z, false);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (SSLException e4) {
                e2 = e4;
            }
        }
        a(channelHandlerContext, byteBuf, remove, z, z2);
    }

    private void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ChannelPromise channelPromise, boolean z, boolean z2) {
        if (byteBuf == null) {
            byteBuf = Unpooled.EMPTY_BUFFER;
        } else if (!byteBuf.isReadable()) {
            byteBuf.release();
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        if (channelPromise != null) {
            channelHandlerContext.write(byteBuf, channelPromise);
        } else {
            channelHandlerContext.write(byteBuf);
        }
        if (z) {
            this.w = true;
        }
        if (z2) {
            a(channelHandlerContext);
        }
    }

    private void b(ChannelHandlerContext channelHandlerContext, boolean z) throws SSLException {
        ByteBufAllocator alloc = channelHandlerContext.alloc();
        ByteBuf byteBuf = null;
        while (true) {
            if (byteBuf == null) {
                try {
                    try {
                        byteBuf = b(channelHandlerContext, 0);
                    } catch (SSLException e2) {
                        a(channelHandlerContext, e2);
                        b(channelHandlerContext);
                        throw e2;
                    }
                } finally {
                    if (byteBuf != null) {
                        byteBuf.release();
                    }
                }
            }
            SSLEngineResult a2 = a(alloc, this.j, Unpooled.EMPTY_BUFFER, byteBuf);
            if (a2.bytesProduced() > 0) {
                channelHandlerContext.write(byteBuf);
                if (z) {
                    this.w = true;
                }
                byteBuf = null;
            }
            switch (AnonymousClass9.a[a2.getHandshakeStatus().ordinal()]) {
                case 1:
                    c();
                    break;
                case 2:
                    e();
                    break;
                case 3:
                    d();
                    if (!z) {
                        c(channelHandlerContext);
                        break;
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if (!z) {
                        c(channelHandlerContext);
                        break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown handshake status: " + a2.getHandshakeStatus());
            }
            if (!(a2.bytesProduced() == 0 || (a2.bytesConsumed() == 0 && a2.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING))) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007d A[Catch: all -> 0x0026, LOOP:0: B:18:0x0042->B:24:0x007d, LOOP_END, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0026, blocks: (B:9:0x001a, B:18:0x0042, B:24:0x007d), top: B:32:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0073 A[EDGE_INSN: B:34:0x0073->B:20:0x0073 ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private javax.net.ssl.SSLEngineResult a(io.netty.buffer.ByteBufAllocator r8, javax.net.ssl.SSLEngine r9, io.netty.buffer.ByteBuf r10, io.netty.buffer.ByteBuf r11) throws javax.net.ssl.SSLException {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            int r2 = r10.readerIndex()     // Catch: all -> 0x0083
            int r3 = r10.readableBytes()     // Catch: all -> 0x0083
            boolean r4 = r10.isDirect()     // Catch: all -> 0x0083
            r5 = 1
            if (r4 != 0) goto L_0x0028
            boolean r4 = r7.n     // Catch: all -> 0x0083
            if (r4 != 0) goto L_0x0016
            goto L_0x0028
        L_0x0016:
            io.netty.buffer.ByteBuf r8 = r8.directBuffer(r3)     // Catch: all -> 0x0083
            r8.writeBytes(r10, r2, r3)     // Catch: all -> 0x0026
            java.nio.ByteBuffer[] r2 = r7.m     // Catch: all -> 0x0026
            java.nio.ByteBuffer r3 = r8.internalNioBuffer(r1, r3)     // Catch: all -> 0x0026
            r2[r1] = r3     // Catch: all -> 0x0026
            goto L_0x0042
        L_0x0026:
            r9 = move-exception
            goto L_0x0085
        L_0x0028:
            boolean r8 = r10 instanceof io.netty.buffer.CompositeByteBuf     // Catch: all -> 0x0083
            if (r8 != 0) goto L_0x003d
            int r8 = r10.nioBufferCount()     // Catch: all -> 0x0083
            if (r8 != r5) goto L_0x003d
            java.nio.ByteBuffer[] r8 = r7.m     // Catch: all -> 0x0083
            java.nio.ByteBuffer r2 = r10.internalNioBuffer(r2, r3)     // Catch: all -> 0x0083
            r8[r1] = r2     // Catch: all -> 0x0083
            r2 = r8
            r8 = r0
            goto L_0x0042
        L_0x003d:
            java.nio.ByteBuffer[] r2 = r10.nioBuffers()     // Catch: all -> 0x0083
            r8 = r0
        L_0x0042:
            int r3 = r11.writerIndex()     // Catch: all -> 0x0026
            int r4 = r11.writableBytes()     // Catch: all -> 0x0026
            java.nio.ByteBuffer r3 = r11.nioBuffer(r3, r4)     // Catch: all -> 0x0026
            javax.net.ssl.SSLEngineResult r3 = r9.wrap(r2, r3)     // Catch: all -> 0x0026
            int r4 = r3.bytesConsumed()     // Catch: all -> 0x0026
            r10.skipBytes(r4)     // Catch: all -> 0x0026
            int r4 = r11.writerIndex()     // Catch: all -> 0x0026
            int r6 = r3.bytesProduced()     // Catch: all -> 0x0026
            int r4 = r4 + r6
            r11.writerIndex(r4)     // Catch: all -> 0x0026
            int[] r4 = io.netty.handler.ssl.SslHandler.AnonymousClass9.b     // Catch: all -> 0x0026
            javax.net.ssl.SSLEngineResult$Status r6 = r3.getStatus()     // Catch: all -> 0x0026
            int r6 = r6.ordinal()     // Catch: all -> 0x0026
            r4 = r4[r6]     // Catch: all -> 0x0026
            if (r4 == r5) goto L_0x007d
            java.nio.ByteBuffer[] r9 = r7.m
            r9[r1] = r0
            if (r8 == 0) goto L_0x007c
            r8.release()
        L_0x007c:
            return r3
        L_0x007d:
            int r3 = r7.k     // Catch: all -> 0x0026
            r11.ensureWritable(r3)     // Catch: all -> 0x0026
            goto L_0x0042
        L_0x0083:
            r9 = move-exception
            r8 = r0
        L_0x0085:
            java.nio.ByteBuffer[] r10 = r7.m
            r10[r1] = r0
            if (r8 == 0) goto L_0x008e
            r8.release()
        L_0x008e:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslHandler.a(io.netty.buffer.ByteBufAllocator, javax.net.ssl.SSLEngine, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):javax.net.ssl.SSLEngineResult");
    }

    /* renamed from: io.netty.handler.ssl.SslHandler$9 */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b = new int[SSLEngineResult.Status.values().length];

        static {
            try {
                b[SSLEngineResult.Status.BUFFER_OVERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[SSLEngineResult.Status.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            a = new int[SSLEngineResult.HandshakeStatus.values().length];
            try {
                a[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext, h, !this.x);
        super.channelInactive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (a(th)) {
            if (c.isDebugEnabled()) {
                c.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", channelHandlerContext.channel(), th);
            }
            if (channelHandlerContext.channel().isActive()) {
                channelHandlerContext.close();
                return;
            }
            return;
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    private boolean a(Throwable th) {
        if (!(th instanceof SSLException) && (th instanceof IOException) && this.v.isDone()) {
            if (e.matcher(String.valueOf(th.getMessage()).toLowerCase()).matches()) {
                return true;
            }
            StackTraceElement[] stackTrace = th.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                if (!className.startsWith("io.netty.") && "read".equals(methodName)) {
                    if (d.matcher(className).matches()) {
                        return true;
                    }
                    try {
                        Class<?> loadClass = PlatformDependent.getClassLoader(getClass()).loadClass(className);
                        if (!SocketChannel.class.isAssignableFrom(loadClass) && !DatagramChannel.class.isAssignableFrom(loadClass)) {
                            if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(loadClass.getSuperclass().getName())) {
                                return true;
                            }
                        }
                        return true;
                    } catch (ClassNotFoundException unused) {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEncrypted(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() >= 5) {
            return n.a(byteBuf, byteBuf.readerIndex()) != -1;
        }
        throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws SSLException {
        int i;
        int i2;
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        int i3 = this.y;
        boolean z = false;
        if (i3 <= 0) {
            i = readerIndex;
            i3 = 0;
        } else if (writerIndex - readerIndex >= i3) {
            i = readerIndex + i3;
            this.y = 0;
        } else {
            return;
        }
        while (true) {
            if (i3 >= 18713 || (i2 = writerIndex - i) < 5) {
                break;
            }
            int a2 = n.a(byteBuf, i);
            if (a2 == -1) {
                z = true;
                break;
            } else if (!a && a2 <= 0) {
                throw new AssertionError();
            } else if (a2 > i2) {
                this.y = a2;
                break;
            } else {
                int i4 = i3 + a2;
                if (i4 > 18713) {
                    break;
                }
                i += a2;
                i3 = i4;
            }
        }
        if (i3 > 0) {
            byteBuf.skipBytes(i3);
            boolean a3 = a(channelHandlerContext, byteBuf, readerIndex, i3);
            if (!this.z) {
                this.z = a3;
            }
        }
        if (z) {
            NotSslRecordException notSslRecordException = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(byteBuf));
            byteBuf.skipBytes(byteBuf.readableBytes());
            channelHandlerContext.fireExceptionCaught((Throwable) notSslRecordException);
            a(channelHandlerContext, notSslRecordException);
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        discardSomeReadBytes();
        b(channelHandlerContext);
        a(channelHandlerContext);
        this.z = false;
        channelHandlerContext.fireChannelReadComplete();
    }

    private void a(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.channel().config().isAutoRead()) {
            return;
        }
        if (!this.z || !this.u.isDone()) {
            channelHandlerContext.read();
        }
    }

    private void b(ChannelHandlerContext channelHandlerContext) {
        if (this.w) {
            this.w = false;
            channelHandlerContext.flush();
        }
    }

    private void c(ChannelHandlerContext channelHandlerContext) throws SSLException {
        a(channelHandlerContext, Unpooled.EMPTY_BUFFER, 0, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0092, code lost:
        if (r3 != javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP) goto L_0x0097;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0094, code lost:
        a(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0097, code lost:
        if (r13 == false) goto L_0x009c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0099, code lost:
        a(r17, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x009c, code lost:
        if (r14 == false) goto L_0x00a7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x009e, code lost:
        r16.v.trySuccess(r17.channel());
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b5, code lost:
        return r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:?, code lost:
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0090 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(io.netty.channel.ChannelHandlerContext r17, io.netty.buffer.ByteBuf r18, int r19, int r20) throws javax.net.ssl.SSLException {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslHandler.a(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, int, int):boolean");
    }

    private SSLEngineResult a(SSLEngine sSLEngine, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
        SSLEngineResult sSLEngineResult;
        int nioBufferCount = byteBuf.nioBufferCount();
        int writerIndex = byteBuf2.writerIndex();
        if (!(sSLEngine instanceof OpenSslEngine) || nioBufferCount <= 1) {
            sSLEngineResult = sSLEngine.unwrap(a(byteBuf, i, i2), a(byteBuf2, writerIndex, byteBuf2.writableBytes()));
        } else {
            OpenSslEngine openSslEngine = (OpenSslEngine) sSLEngine;
            try {
                this.m[0] = a(byteBuf2, writerIndex, byteBuf2.writableBytes());
                sSLEngineResult = openSslEngine.unwrap(byteBuf.nioBuffers(i, i2), this.m);
                byteBuf2.writerIndex(sSLEngineResult.bytesProduced() + writerIndex);
            } finally {
                this.m[0] = null;
            }
        }
        byteBuf2.writerIndex(writerIndex + sSLEngineResult.bytesProduced());
        return sSLEngineResult;
    }

    private static ByteBuffer a(ByteBuf byteBuf, int i, int i2) {
        return byteBuf.nioBufferCount() == 1 ? byteBuf.internalNioBuffer(i, i2) : byteBuf.nioBuffer(i, i2);
    }

    private void c() {
        if (this.l == ImmediateExecutor.INSTANCE) {
            while (true) {
                Runnable delegatedTask = this.j.getDelegatedTask();
                if (delegatedTask != null) {
                    delegatedTask.run();
                } else {
                    return;
                }
            }
        } else {
            final ArrayList arrayList = new ArrayList(2);
            while (true) {
                Runnable delegatedTask2 = this.j.getDelegatedTask();
                if (delegatedTask2 == null) {
                    break;
                }
                arrayList.add(delegatedTask2);
            }
            if (!arrayList.isEmpty()) {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                this.l.execute(new OneTimeTask() { // from class: io.netty.handler.ssl.SslHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                for (Runnable runnable : arrayList) {
                                    runnable.run();
                                }
                            } catch (Exception e2) {
                                SslHandler.this.i.fireExceptionCaught((Throwable) e2);
                            }
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
                });
                boolean z = false;
                while (countDownLatch.getCount() != 0) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
                if (z) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean d() {
        if (this.u.isDone()) {
            return false;
        }
        e();
        return true;
    }

    private void e() {
        this.u.trySuccess(this.i.channel());
        if (c.isDebugEnabled()) {
            c.debug("{} HANDSHAKEN: {}", this.i.channel(), this.j.getSession().getCipherSuite());
        }
        this.i.fireUserEventTriggered((Object) SslHandshakeCompletionEvent.SUCCESS);
        if (this.s && !this.i.channel().config().isAutoRead()) {
            this.s = false;
            this.i.read();
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, Throwable th) {
        a(channelHandlerContext, th, true);
    }

    private void a(ChannelHandlerContext channelHandlerContext, Throwable th, boolean z) {
        this.j.closeOutbound();
        if (z) {
            try {
                this.j.closeInbound();
            } catch (SSLException e2) {
                String message = e2.getMessage();
                if (message == null || !message.contains("possible truncation attack")) {
                    c.debug("{} SSLEngine.closeInbound() raised an exception.", channelHandlerContext.channel(), e2);
                }
            }
        }
        b(th);
        this.t.removeAndFailAll(th);
    }

    public void b(Throwable th) {
        if (this.u.tryFailure(th)) {
            n.a(this.i, th);
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise, boolean z) throws Exception {
        if (channelHandlerContext.channel().isActive()) {
            this.x = true;
            this.j.closeOutbound();
            ChannelPromise newPromise = channelHandlerContext.newPromise();
            write(channelHandlerContext, Unpooled.EMPTY_BUFFER, newPromise);
            flush(channelHandlerContext);
            a(channelHandlerContext, newPromise, channelPromise);
        } else if (z) {
            channelHandlerContext.disconnect(channelPromise);
        } else {
            channelHandlerContext.close(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.i = channelHandlerContext;
        this.t = new PendingWriteQueue(channelHandlerContext);
        if (channelHandlerContext.channel().isActive() && this.j.getUseClientMode()) {
            a((Promise<Channel>) null);
        }
    }

    public Future<Channel> renegotiate() {
        ChannelHandlerContext channelHandlerContext = this.i;
        if (channelHandlerContext != null) {
            return renegotiate(channelHandlerContext.executor().newPromise());
        }
        throw new IllegalStateException();
    }

    public Future<Channel> renegotiate(final Promise<Channel> promise) {
        if (promise != null) {
            ChannelHandlerContext channelHandlerContext = this.i;
            if (channelHandlerContext != null) {
                EventExecutor executor = channelHandlerContext.executor();
                if (!executor.inEventLoop()) {
                    executor.execute(new OneTimeTask() { // from class: io.netty.handler.ssl.SslHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            SslHandler.this.a(promise);
                        }
                    });
                    return promise;
                }
                a(promise);
                return promise;
            }
            throw new IllegalStateException();
        }
        throw new NullPointerException("promise");
    }

    public void a(final Promise<Channel> promise) {
        if (promise != null) {
            Promise<Channel> promise2 = this.u;
            if (!promise2.isDone()) {
                promise2.addListener((GenericFutureListener<? extends Future<? super Channel>>) new FutureListener<Channel>() { // from class: io.netty.handler.ssl.SslHandler.4
                    @Override // io.netty.util.concurrent.GenericFutureListener
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if (future.isSuccess()) {
                            promise.setSuccess(future.getNow());
                        } else {
                            promise.setFailure(future.cause());
                        }
                    }
                });
                return;
            }
            this.u = promise;
        } else if (this.j.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            promise = this.u;
            if (!a && promise.isDone()) {
                throw new AssertionError();
            }
        } else {
            return;
        }
        ChannelHandlerContext channelHandlerContext = this.i;
        try {
            this.j.beginHandshake();
            b(channelHandlerContext, false);
            channelHandlerContext.flush();
        } catch (Exception e2) {
            b(e2);
        }
        long j = this.A;
        if (j > 0 && !promise.isDone()) {
            final ScheduledFuture<?> schedule = channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.ssl.SslHandler.5
                @Override // java.lang.Runnable
                public void run() {
                    if (!promise.isDone()) {
                        SslHandler.this.b(SslHandler.g);
                    }
                }
            }, j, TimeUnit.MILLISECONDS);
            promise.addListener((GenericFutureListener<? extends Future<? super Channel>>) new FutureListener<Channel>() { // from class: io.netty.handler.ssl.SslHandler.6
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<Channel> future) throws Exception {
                    schedule.cancel(false);
                }
            });
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.p && this.j.getUseClientMode()) {
            a((Promise<Channel>) null);
        }
        channelHandlerContext.fireChannelActive();
    }

    private void a(final ChannelHandlerContext channelHandlerContext, ChannelFuture channelFuture, final ChannelPromise channelPromise) {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        final ScheduledFuture<?> schedule = this.B > 0 ? channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.ssl.SslHandler.7
            @Override // java.lang.Runnable
            public void run() {
                SslHandler.c.warn("{} Last write attempt timed out; force-closing the connection.", channelHandlerContext.channel());
                ChannelHandlerContext channelHandlerContext2 = channelHandlerContext;
                channelHandlerContext2.close(channelHandlerContext2.newPromise()).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(channelPromise));
            }
        }, this.B, TimeUnit.MILLISECONDS) : null;
        channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.ssl.SslHandler.8
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture2) throws Exception {
                java.util.concurrent.ScheduledFuture scheduledFuture = schedule;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                ChannelHandlerContext channelHandlerContext2 = channelHandlerContext;
                channelHandlerContext2.close(channelHandlerContext2.newPromise()).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(channelPromise));
            }
        });
    }

    private ByteBuf a(ChannelHandlerContext channelHandlerContext, int i) {
        ByteBufAllocator alloc = channelHandlerContext.alloc();
        if (this.n) {
            return alloc.directBuffer(i);
        }
        return alloc.buffer(i);
    }

    private ByteBuf b(ChannelHandlerContext channelHandlerContext, int i) {
        if (this.o) {
            return a(channelHandlerContext, this.k);
        }
        return a(channelHandlerContext, Math.min(i + 2329, this.k));
    }

    /* loaded from: classes4.dex */
    public final class a extends DefaultPromise<Channel> {
        private a() {
            SslHandler.this = r1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.concurrent.DefaultPromise
        public EventExecutor executor() {
            if (SslHandler.this.i != null) {
                return SslHandler.this.i.executor();
            }
            throw new IllegalStateException();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.concurrent.DefaultPromise
        public void checkDeadLock() {
            if (SslHandler.this.i != null) {
                super.checkDeadLock();
            }
        }
    }
}
