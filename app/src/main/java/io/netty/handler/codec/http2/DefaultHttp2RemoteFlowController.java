package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.util.BooleanSupplier;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Deque;

/* loaded from: classes4.dex */
public class DefaultHttp2RemoteFlowController implements Http2RemoteFlowController {
    static final /* synthetic */ boolean a = !DefaultHttp2RemoteFlowController.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(DefaultHttp2RemoteFlowController.class);
    private final Http2Connection c;
    private final Http2Connection.PropertyKey d;
    private final StreamByteDistributor e;
    private final a f;
    private int g;
    private c h;
    private ChannelHandlerContext i;

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection) {
        this(http2Connection, (Http2RemoteFlowController.Listener) null);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection, StreamByteDistributor streamByteDistributor) {
        this(http2Connection, streamByteDistributor, null);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection, Http2RemoteFlowController.Listener listener) {
        this(http2Connection, new WeightedFairQueueByteDistributor(http2Connection), listener);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection, StreamByteDistributor streamByteDistributor, Http2RemoteFlowController.Listener listener) {
        this.g = 65535;
        this.c = (Http2Connection) ObjectUtil.checkNotNull(http2Connection, "connection");
        this.e = (StreamByteDistributor) ObjectUtil.checkNotNull(streamByteDistributor, "streamWriteDistributor");
        this.d = http2Connection.newKey();
        this.f = new a(http2Connection.connectionStream());
        http2Connection.connectionStream().setProperty(this.d, this.f);
        listener(listener);
        this.h.a(this.f, this.g);
        http2Connection.addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.1
            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(DefaultHttp2RemoteFlowController.this.d, new a(http2Stream));
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamActive(Http2Stream http2Stream) {
                DefaultHttp2RemoteFlowController.this.h.a(DefaultHttp2RemoteFlowController.this.a(http2Stream), DefaultHttp2RemoteFlowController.this.g);
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamClosed(Http2Stream http2Stream) {
                DefaultHttp2RemoteFlowController.this.a(http2Stream).c();
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamHalfClosed(Http2Stream http2Stream) {
                if (Http2Stream.State.HALF_CLOSED_LOCAL.equals(http2Stream.state())) {
                    DefaultHttp2RemoteFlowController.this.a(http2Stream).c();
                }
            }
        });
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public void channelHandlerContext(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
        this.i = (ChannelHandlerContext) ObjectUtil.checkNotNull(channelHandlerContext, "ctx");
        channelWritabilityChanged();
        if (b()) {
            writePendingBytes();
        }
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public ChannelHandlerContext channelHandlerContext() {
        return this.i;
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public void initialWindowSize(int i) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (a || (channelHandlerContext = this.i) == null || channelHandlerContext.executor().inEventLoop()) {
            this.h.a(i);
            return;
        }
        throw new AssertionError();
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public int initialWindowSize() {
        return this.g;
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public int windowSize(Http2Stream http2Stream) {
        return a(http2Stream).windowSize();
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public boolean isWritable(Http2Stream http2Stream) {
        return this.h.b(a(http2Stream));
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public void channelWritabilityChanged() throws Http2Exception {
        this.h.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        return this.i != null && c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        return this.i.channel().isWritable();
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public void listener(Http2RemoteFlowController.Listener listener) {
        this.h = listener == null ? new c() : new b(listener);
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public void incrementWindowSize(Http2Stream http2Stream, int i) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (a || (channelHandlerContext = this.i) == null || channelHandlerContext.executor().inEventLoop()) {
            this.h.b(a(http2Stream), i);
            return;
        }
        throw new AssertionError();
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public void addFlowControlled(Http2Stream http2Stream, Http2RemoteFlowController.FlowControlled flowControlled) {
        ChannelHandlerContext channelHandlerContext;
        if (a || (channelHandlerContext = this.i) == null || channelHandlerContext.executor().inEventLoop()) {
            ObjectUtil.checkNotNull(flowControlled, "frame");
            try {
                this.h.a(a(http2Stream), flowControlled);
            } catch (Throwable th) {
                flowControlled.error(this.i, th);
            }
        } else {
            throw new AssertionError();
        }
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public boolean hasFlowControlled(Http2Stream http2Stream) {
        return a(http2Stream).hasFrame();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public a a(Http2Stream http2Stream) {
        return (a) http2Stream.getProperty(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int d() {
        return this.f.windowSize();
    }

    private int e() {
        return Math.max(this.i.channel().config().getWriteBufferLowWaterMark(), 32768);
    }

    private int f() {
        int min = (int) Math.min(2147483647L, this.i.channel().bytesBeforeUnwritable());
        return Math.min(this.f.windowSize(), min > 0 ? Math.max(min, e()) : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int g() {
        return Math.min(d(), f());
    }

    @Override // io.netty.handler.codec.http2.Http2RemoteFlowController
    public void writePendingBytes() throws Http2Exception {
        this.h.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements StreamByteDistributor.StreamState {
        static final /* synthetic */ boolean a = !DefaultHttp2RemoteFlowController.class.desiredAssertionStatus();
        private final Http2Stream c;
        private int e;
        private int f;
        private boolean g;
        private boolean h;
        private boolean i;
        private BooleanSupplier j = new BooleanSupplier() { // from class: io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.a.1
            @Override // io.netty.util.BooleanSupplier
            public boolean get() throws Exception {
                return a.this.windowSize() - a.this.pendingBytes() > 0;
            }
        };
        private final Deque<Http2RemoteFlowController.FlowControlled> d = new ArrayDeque(2);

        a(Http2Stream http2Stream) {
            this.c = http2Stream;
        }

        boolean a() {
            try {
                return this.j.get();
            } catch (Throwable th) {
                throw new Error("isWritableSupplier should never throw!", th);
            }
        }

        @Override // io.netty.handler.codec.http2.StreamByteDistributor.StreamState
        public Http2Stream stream() {
            return this.c;
        }

        boolean b() {
            return this.g;
        }

        void a(boolean z) {
            this.g = z;
        }

        @Override // io.netty.handler.codec.http2.StreamByteDistributor.StreamState
        public int windowSize() {
            return this.e;
        }

        void a(int i) {
            this.e = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0062, code lost:
            r8.h = false;
            r9 = r9 - r3;
            b(r9, false);
            d(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x006e, code lost:
            if (r8.i == false) goto L_0x0073;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0070, code lost:
            a((java.lang.Throwable) null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0073, code lost:
            return -1;
         */
        /* JADX WARN: Finally extract failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int b(int r9) {
            /*
                r8 = this;
                r0 = 1
                r1 = 0
                r2 = 0
                boolean r3 = io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.a.a     // Catch: Throwable -> 0x008a, all -> 0x0087
                if (r3 != 0) goto L_0x0012
                boolean r3 = r8.h     // Catch: Throwable -> 0x008a, all -> 0x0087
                if (r3 != 0) goto L_0x000c
                goto L_0x0012
            L_0x000c:
                java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch: Throwable -> 0x008a, all -> 0x0087
                r3.<init>()     // Catch: Throwable -> 0x008a, all -> 0x0087
                throw r3     // Catch: Throwable -> 0x008a, all -> 0x0087
            L_0x0012:
                r8.h = r0     // Catch: Throwable -> 0x008a, all -> 0x0087
                r3 = r9
                r4 = r1
            L_0x0016:
                boolean r5 = r8.i     // Catch: Throwable -> 0x0085, all -> 0x00a0
                if (r5 != 0) goto L_0x0060
                io.netty.handler.codec.http2.Http2RemoteFlowController$FlowControlled r5 = r8.e()     // Catch: Throwable -> 0x0085, all -> 0x00a0
                if (r5 == 0) goto L_0x0060
                int r6 = r8.d()     // Catch: Throwable -> 0x0085, all -> 0x00a0
                int r6 = java.lang.Math.min(r3, r6)     // Catch: Throwable -> 0x0085, all -> 0x00a0
                if (r6 > 0) goto L_0x0031
                int r7 = r5.size()     // Catch: Throwable -> 0x0085, all -> 0x00a0
                if (r7 <= 0) goto L_0x0031
                goto L_0x0060
            L_0x0031:
                int r4 = r5.size()     // Catch: Throwable -> 0x0085, all -> 0x00a0
                io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController r7 = io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.this     // Catch: all -> 0x0058
                io.netty.channel.ChannelHandlerContext r7 = io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.d(r7)     // Catch: all -> 0x0058
                int r6 = java.lang.Math.max(r1, r6)     // Catch: all -> 0x0058
                r5.write(r7, r6)     // Catch: all -> 0x0058
                int r6 = r5.size()     // Catch: all -> 0x0058
                if (r6 != 0) goto L_0x0050
                java.util.Deque<io.netty.handler.codec.http2.Http2RemoteFlowController$FlowControlled> r6 = r8.d     // Catch: all -> 0x0058
                r6.remove()     // Catch: all -> 0x0058
                r5.writeComplete()     // Catch: all -> 0x0058
            L_0x0050:
                int r5 = r5.size()     // Catch: Throwable -> 0x0085, all -> 0x00a0
                int r4 = r4 - r5
                int r3 = r3 - r4
                r4 = r0
                goto L_0x0016
            L_0x0058:
                r6 = move-exception
                int r5 = r5.size()     // Catch: Throwable -> 0x0085, all -> 0x00a0
                int r4 = r4 - r5
                int r3 = r3 - r4
                throw r6     // Catch: Throwable -> 0x0085, all -> 0x00a0
            L_0x0060:
                if (r4 != 0) goto L_0x0074
                r0 = -1
                r8.h = r1
                int r9 = r9 - r3
                r8.b(r9, r1)
                r8.d(r9)
                boolean r9 = r8.i
                if (r9 == 0) goto L_0x0073
                r8.a(r2)
            L_0x0073:
                return r0
            L_0x0074:
                r8.h = r1
                int r9 = r9 - r3
                r8.b(r9, r1)
                r8.d(r9)
                boolean r0 = r8.i
                if (r0 == 0) goto L_0x009f
                r8.a(r2)
                goto L_0x009f
            L_0x0085:
                r4 = move-exception
                goto L_0x008d
            L_0x0087:
                r0 = move-exception
                r3 = r9
                goto L_0x00a1
            L_0x008a:
                r3 = move-exception
                r4 = r3
                r3 = r9
            L_0x008d:
                r8.i = r0     // Catch: all -> 0x00a0
                r8.h = r1
                int r9 = r9 - r3
                r8.b(r9, r1)
                r8.d(r9)
                boolean r0 = r8.i
                if (r0 == 0) goto L_0x009f
                r8.a(r4)
            L_0x009f:
                return r9
            L_0x00a0:
                r0 = move-exception
            L_0x00a1:
                r8.h = r1
                int r9 = r9 - r3
                r8.b(r9, r1)
                r8.d(r9)
                boolean r9 = r8.i
                if (r9 == 0) goto L_0x00b1
                r8.a(r2)
            L_0x00b1:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.a.b(int):int");
        }

        int c(int i) throws Http2Exception {
            if (i <= 0 || Integer.MAX_VALUE - i >= this.e) {
                this.e += i;
                DefaultHttp2RemoteFlowController.this.e.updateStreamableBytes(this);
                return this.e;
            }
            throw Http2Exception.streamError(this.c.id(), Http2Error.FLOW_CONTROL_ERROR, "Window size overflow for stream: %d", Integer.valueOf(this.c.id()));
        }

        private int d() {
            return Math.min(this.e, DefaultHttp2RemoteFlowController.this.d());
        }

        @Override // io.netty.handler.codec.http2.StreamByteDistributor.StreamState
        public int pendingBytes() {
            return this.f;
        }

        void a(Http2RemoteFlowController.FlowControlled flowControlled) {
            Http2RemoteFlowController.FlowControlled peekLast = this.d.peekLast();
            if (peekLast == null) {
                b(flowControlled);
                return;
            }
            int size = peekLast.size();
            if (peekLast.merge(DefaultHttp2RemoteFlowController.this.i, flowControlled)) {
                a(peekLast.size() - size, true);
            } else {
                b(flowControlled);
            }
        }

        private void b(Http2RemoteFlowController.FlowControlled flowControlled) {
            this.d.offer(flowControlled);
            a(flowControlled.size(), true);
        }

        @Override // io.netty.handler.codec.http2.StreamByteDistributor.StreamState
        public boolean hasFrame() {
            return !this.d.isEmpty();
        }

        private Http2RemoteFlowController.FlowControlled e() {
            return this.d.peek();
        }

        void c() {
            a((Throwable) null);
        }

        private void a(Throwable th) {
            this.i = true;
            if (!this.h) {
                while (true) {
                    Http2RemoteFlowController.FlowControlled poll = this.d.poll();
                    if (poll == null) {
                        DefaultHttp2RemoteFlowController.this.e.updateStreamableBytes(this);
                        this.j = BooleanSupplier.FALSE_SUPPLIER;
                        DefaultHttp2RemoteFlowController.this.h.a(this);
                        return;
                    }
                    a(poll, Http2Exception.streamError(this.c.id(), Http2Error.INTERNAL_ERROR, th, "Stream closed before write could take place", new Object[0]));
                }
            }
        }

        private void a(int i, boolean z) {
            this.f += i;
            DefaultHttp2RemoteFlowController.this.h.b(i);
            if (z) {
                DefaultHttp2RemoteFlowController.this.e.updateStreamableBytes(this);
            }
        }

        private void b(int i, boolean z) {
            a(-i, z);
        }

        private void d(int i) {
            int i2 = -i;
            try {
                DefaultHttp2RemoteFlowController.this.f.c(i2);
                c(i2);
            } catch (Http2Exception e) {
                throw new IllegalStateException("Invalid window state when writing frame: " + e.getMessage(), e);
            }
        }

        private void a(Http2RemoteFlowController.FlowControlled flowControlled, Http2Exception http2Exception) {
            if (a || DefaultHttp2RemoteFlowController.this.i != null) {
                b(flowControlled.size(), true);
                flowControlled.error(DefaultHttp2RemoteFlowController.this.i, http2Exception);
                return;
            }
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class c {
        private long a;
        private final StreamByteDistributor.Writer c;

        void a() throws Http2Exception {
        }

        void a(a aVar) {
        }

        private c() {
            this.c = new StreamByteDistributor.Writer() { // from class: io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c.1
                @Override // io.netty.handler.codec.http2.StreamByteDistributor.Writer
                public void write(Http2Stream http2Stream, int i) {
                    DefaultHttp2RemoteFlowController.this.a(http2Stream).b(i);
                }
            };
        }

        void a(a aVar, int i) {
            aVar.a(i);
        }

        void b(a aVar, int i) throws Http2Exception {
            aVar.c(i);
        }

        void a(a aVar, Http2RemoteFlowController.FlowControlled flowControlled) throws Http2Exception {
            aVar.a(flowControlled);
        }

        final void b(int i) {
            this.a += i;
        }

        final boolean b(a aVar) {
            return c() && aVar.a();
        }

        final void b() throws Http2Exception {
            int g = DefaultHttp2RemoteFlowController.this.g();
            while (DefaultHttp2RemoteFlowController.this.e.distribute(g, this.c) && (g = DefaultHttp2RemoteFlowController.this.g()) > 0 && DefaultHttp2RemoteFlowController.this.c()) {
            }
        }

        void a(int i) throws Http2Exception {
            if (i >= 0) {
                final int i2 = i - DefaultHttp2RemoteFlowController.this.g;
                DefaultHttp2RemoteFlowController.this.g = i;
                DefaultHttp2RemoteFlowController.this.c.forEachActiveStream(new Http2StreamVisitor() { // from class: io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c.2
                    @Override // io.netty.handler.codec.http2.Http2StreamVisitor
                    public boolean visit(Http2Stream http2Stream) throws Http2Exception {
                        DefaultHttp2RemoteFlowController.this.a(http2Stream).c(i2);
                        return true;
                    }
                });
                if (i2 > 0 && DefaultHttp2RemoteFlowController.this.b()) {
                    b();
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("Invalid initial window size: " + i);
        }

        final boolean c() {
            return ((long) DefaultHttp2RemoteFlowController.this.f.windowSize()) - this.a > 0 && DefaultHttp2RemoteFlowController.this.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b extends c {
        private final Http2RemoteFlowController.Listener c;
        private final Http2StreamVisitor d = new Http2StreamVisitor() { // from class: io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.b.1
            @Override // io.netty.handler.codec.http2.Http2StreamVisitor
            public boolean visit(Http2Stream http2Stream) throws Http2Exception {
                a a = DefaultHttp2RemoteFlowController.this.a(http2Stream);
                if (b.this.b(a) == a.b()) {
                    return true;
                }
                b.this.d(a);
                return true;
            }
        };

        b(Http2RemoteFlowController.Listener listener) {
            super();
            this.c = listener;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c
        void a(a aVar, int i) {
            super.a(aVar, i);
            try {
                c(aVar);
            } catch (Http2Exception e) {
                throw new RuntimeException("Caught unexpected exception from window", e);
            }
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c
        void b(a aVar, int i) throws Http2Exception {
            super.b(aVar, i);
            c(aVar);
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c
        void a(int i) throws Http2Exception {
            super.a(i);
            if (c()) {
                d();
            }
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c
        void a(a aVar, Http2RemoteFlowController.FlowControlled flowControlled) throws Http2Exception {
            super.a(aVar, flowControlled);
            e(aVar);
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c
        void a(a aVar) {
            try {
                e(aVar);
            } catch (Http2Exception e) {
                throw new RuntimeException("Caught unexpected exception from checkAllWritabilityChanged", e);
            }
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController.c
        void a() throws Http2Exception {
            if (DefaultHttp2RemoteFlowController.this.f.b() != DefaultHttp2RemoteFlowController.this.b()) {
                d();
            }
        }

        private void c(a aVar) throws Http2Exception {
            if (b(aVar) == aVar.b()) {
                return;
            }
            if (aVar == DefaultHttp2RemoteFlowController.this.f) {
                d();
            } else {
                d(aVar);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(a aVar) {
            aVar.a(!aVar.b());
            try {
                this.c.writabilityChanged(aVar.c);
            } catch (Throwable th) {
                DefaultHttp2RemoteFlowController.b.error("Caught Throwable from listener.writabilityChanged", th);
            }
        }

        private void e(a aVar) throws Http2Exception {
            if (c() != DefaultHttp2RemoteFlowController.this.f.b()) {
                d();
            } else if (b(aVar) != aVar.b()) {
                d(aVar);
            }
        }

        private void d() throws Http2Exception {
            DefaultHttp2RemoteFlowController.this.f.a(c());
            DefaultHttp2RemoteFlowController.this.c.forEachActiveStream(this.d);
        }
    }
}
