package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

/* loaded from: classes4.dex */
public class DefaultHttp2LocalFlowController implements Http2LocalFlowController {
    public static final float DEFAULT_WINDOW_UPDATE_RATIO = 0.5f;
    static final /* synthetic */ boolean a = !DefaultHttp2LocalFlowController.class.desiredAssertionStatus();
    private static final c h = new c() { // from class: io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.2
        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public int a() {
            return 0;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void a(int i) {
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public int b() {
            return 0;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public boolean b(int i) throws Http2Exception {
            return false;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public int d() {
            return 0;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void d(int i) throws Http2Exception {
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public boolean c() throws Http2Exception {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public float e() {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void a(float f) {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void c(int i) throws Http2Exception {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void a(boolean z) {
            throw new UnsupportedOperationException();
        }
    };
    private final Http2Connection b;
    private final Http2Connection.PropertyKey c;
    private Http2FrameWriter d;
    private ChannelHandlerContext e;
    private float f;
    private int g;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public interface c {
        int a();

        void a(float f);

        void a(int i);

        void a(boolean z);

        int b();

        boolean b(int i) throws Http2Exception;

        void c(int i) throws Http2Exception;

        boolean c() throws Http2Exception;

        int d();

        void d(int i) throws Http2Exception;

        float e();
    }

    public DefaultHttp2LocalFlowController(Http2Connection http2Connection) {
        this(http2Connection, 0.5f, false);
    }

    public DefaultHttp2LocalFlowController(Http2Connection http2Connection, float f, boolean z) {
        this.g = 65535;
        this.b = (Http2Connection) ObjectUtil.checkNotNull(http2Connection, "connection");
        windowUpdateRatio(f);
        this.c = http2Connection.newKey();
        http2Connection.connectionStream().setProperty(this.c, z ? new a(http2Connection.connectionStream(), this.g) : new b(http2Connection.connectionStream(), this.g));
        http2Connection.addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.1
            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(DefaultHttp2LocalFlowController.this.c, DefaultHttp2LocalFlowController.h);
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamActive(Http2Stream http2Stream) {
                Http2Connection.PropertyKey propertyKey = DefaultHttp2LocalFlowController.this.c;
                DefaultHttp2LocalFlowController defaultHttp2LocalFlowController = DefaultHttp2LocalFlowController.this;
                http2Stream.setProperty(propertyKey, new b(http2Stream, defaultHttp2LocalFlowController.g));
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamClosed(Http2Stream http2Stream) {
                try {
                    try {
                        c a2 = DefaultHttp2LocalFlowController.this.a(http2Stream);
                        int d2 = a2.d();
                        if (DefaultHttp2LocalFlowController.this.e != null && d2 > 0) {
                            DefaultHttp2LocalFlowController.this.b().b(d2);
                            a2.b(d2);
                        }
                    } catch (Http2Exception e) {
                        PlatformDependent.throwException(e);
                    }
                } finally {
                    http2Stream.setProperty(DefaultHttp2LocalFlowController.this.c, DefaultHttp2LocalFlowController.h);
                }
            }
        });
    }

    @Override // io.netty.handler.codec.http2.Http2LocalFlowController
    public DefaultHttp2LocalFlowController frameWriter(Http2FrameWriter http2FrameWriter) {
        this.d = (Http2FrameWriter) ObjectUtil.checkNotNull(http2FrameWriter, "frameWriter");
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public void channelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.e = (ChannelHandlerContext) ObjectUtil.checkNotNull(channelHandlerContext, "ctx");
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public void initialWindowSize(int i) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (a || (channelHandlerContext = this.e) == null || channelHandlerContext.executor().inEventLoop()) {
            this.g = i;
            d dVar = new d(i - this.g);
            this.b.forEachActiveStream(dVar);
            dVar.a();
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
        return a(http2Stream).a();
    }

    @Override // io.netty.handler.codec.http2.Http2LocalFlowController
    public int initialWindowSize(Http2Stream http2Stream) {
        return a(http2Stream).b();
    }

    @Override // io.netty.handler.codec.http2.Http2FlowController
    public void incrementWindowSize(Http2Stream http2Stream, int i) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (a || ((channelHandlerContext = this.e) != null && channelHandlerContext.executor().inEventLoop())) {
            c a2 = a(http2Stream);
            a2.a(i);
            a2.c();
            return;
        }
        throw new AssertionError();
    }

    @Override // io.netty.handler.codec.http2.Http2LocalFlowController
    public boolean consumeBytes(Http2Stream http2Stream, int i) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (!a && ((channelHandlerContext = this.e) == null || !channelHandlerContext.executor().inEventLoop())) {
            throw new AssertionError();
        } else if (i < 0) {
            throw new IllegalArgumentException("numBytes must not be negative");
        } else if (i == 0 || http2Stream == null || b(http2Stream)) {
            return false;
        } else {
            if (http2Stream.id() != 0) {
                return a(http2Stream).b(i) | b().b(i);
            }
            throw new UnsupportedOperationException("Returning bytes for the connection window is not supported");
        }
    }

    @Override // io.netty.handler.codec.http2.Http2LocalFlowController
    public int unconsumedBytes(Http2Stream http2Stream) {
        return a(http2Stream).d();
    }

    private static void a(float f) {
        double d2 = f;
        if (Double.compare(d2, 0.0d) <= 0 || Double.compare(d2, 1.0d) >= 0) {
            throw new IllegalArgumentException("Invalid ratio: " + f);
        }
    }

    public void windowUpdateRatio(float f) {
        ChannelHandlerContext channelHandlerContext;
        if (a || (channelHandlerContext = this.e) == null || channelHandlerContext.executor().inEventLoop()) {
            a(f);
            this.f = f;
            return;
        }
        throw new AssertionError();
    }

    public float windowUpdateRatio() {
        return this.f;
    }

    public void windowUpdateRatio(Http2Stream http2Stream, float f) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (a || ((channelHandlerContext = this.e) != null && channelHandlerContext.executor().inEventLoop())) {
            a(f);
            c a2 = a(http2Stream);
            a2.a(f);
            a2.c();
            return;
        }
        throw new AssertionError();
    }

    public float windowUpdateRatio(Http2Stream http2Stream) throws Http2Exception {
        return a(http2Stream).e();
    }

    @Override // io.netty.handler.codec.http2.Http2LocalFlowController
    public void receiveFlowControlledFrame(Http2Stream http2Stream, ByteBuf byteBuf, int i, boolean z) throws Http2Exception {
        ChannelHandlerContext channelHandlerContext;
        if (a || ((channelHandlerContext = this.e) != null && channelHandlerContext.executor().inEventLoop())) {
            int readableBytes = byteBuf.readableBytes() + i;
            c b2 = b();
            b2.c(readableBytes);
            if (http2Stream != null && !b(http2Stream)) {
                c a2 = a(http2Stream);
                a2.a(z);
                a2.c(readableBytes);
            } else if (readableBytes > 0) {
                b2.b(readableBytes);
            }
        } else {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public c b() {
        return (c) this.b.connectionStream().getProperty(this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public c a(Http2Stream http2Stream) {
        return (c) http2Stream.getProperty(this.c);
    }

    private static boolean b(Http2Stream http2Stream) {
        return http2Stream.state() == Http2Stream.State.CLOSED;
    }

    /* loaded from: classes4.dex */
    private final class a extends b {
        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.b, io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public boolean b(int i) throws Http2Exception {
            return false;
        }

        public a(Http2Stream http2Stream, int i) {
            super(http2Stream, i);
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.b, io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void c(int i) throws Http2Exception {
            super.c(i);
            super.b(i);
        }
    }

    /* loaded from: classes4.dex */
    private class b implements c {
        static final /* synthetic */ boolean b = !DefaultHttp2LocalFlowController.class.desiredAssertionStatus();
        private final Http2Stream a;
        private int d;
        private int e;
        private int f;
        private float g;
        private int h;
        private boolean i;

        public b(Http2Stream http2Stream, int i) {
            this.a = http2Stream;
            e(i);
            this.g = DefaultHttp2LocalFlowController.this.f;
        }

        public void e(int i) {
            if (b || DefaultHttp2LocalFlowController.this.e == null || DefaultHttp2LocalFlowController.this.e.executor().inEventLoop()) {
                this.f = i;
                this.e = i;
                this.d = i;
                return;
            }
            throw new AssertionError();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public int a() {
            return this.d;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public int b() {
            return this.f;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void a(boolean z) {
            this.i = z;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public float e() {
            return this.g;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void a(float f) {
            if (b || DefaultHttp2LocalFlowController.this.e == null || DefaultHttp2LocalFlowController.this.e.executor().inEventLoop()) {
                this.g = f;
                return;
            }
            throw new AssertionError();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void a(int i) {
            int min = (int) Math.min(2147483647L, Math.max(0L, this.f + i));
            int i2 = this.f;
            this.f = i2 + (min - i2);
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void d(int i) throws Http2Exception {
            if (i <= 0 || this.d <= Integer.MAX_VALUE - i) {
                this.d += i;
                this.e += i;
                if (i >= 0) {
                    i = 0;
                }
                this.h = i;
                return;
            }
            throw Http2Exception.streamError(this.a.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window overflowed for stream: %d", Integer.valueOf(this.a.id()));
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public void c(int i) throws Http2Exception {
            if (b || i >= 0) {
                this.d -= i;
                if (this.d < this.h) {
                    throw Http2Exception.streamError(this.a.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window exceeded for stream: %d", Integer.valueOf(this.a.id()));
                }
                return;
            }
            throw new AssertionError();
        }

        private void f(int i) throws Http2Exception {
            int i2 = this.e;
            if (i2 - i >= this.d) {
                this.e = i2 - i;
                return;
            }
            throw Http2Exception.streamError(this.a.id(), Http2Error.INTERNAL_ERROR, "Attempting to return too many bytes for stream %d", Integer.valueOf(this.a.id()));
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public boolean b(int i) throws Http2Exception {
            f(i);
            return c();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public int d() {
            return this.e - this.d;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.c
        public boolean c() throws Http2Exception {
            int i;
            if (this.i || (i = this.f) <= 0 || this.e > ((int) (i * this.g))) {
                return false;
            }
            f();
            return true;
        }

        private void f() throws Http2Exception {
            int i = this.f - this.e;
            try {
                d(i);
                DefaultHttp2LocalFlowController.this.d.writeWindowUpdate(DefaultHttp2LocalFlowController.this.e, this.a.id(), i, DefaultHttp2LocalFlowController.this.e.newPromise());
            } catch (Throwable th) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, th, "Attempting to return too many bytes for stream %d", Integer.valueOf(this.a.id()));
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class d implements Http2StreamVisitor {
        private Http2Exception.CompositeStreamException b;
        private final int c;

        public d(int i) {
            this.c = i;
        }

        @Override // io.netty.handler.codec.http2.Http2StreamVisitor
        public boolean visit(Http2Stream http2Stream) throws Http2Exception {
            try {
                c a = DefaultHttp2LocalFlowController.this.a(http2Stream);
                a.d(this.c);
                a.a(this.c);
                return true;
            } catch (Http2Exception.StreamException e) {
                if (this.b == null) {
                    this.b = new Http2Exception.CompositeStreamException(e.error(), 4);
                }
                this.b.add(e);
                return true;
            }
        }

        public void a() throws Http2Exception.CompositeStreamException {
            Http2Exception.CompositeStreamException compositeStreamException = this.b;
            if (compositeStreamException != null) {
                throw compositeStreamException;
            }
        }
    }
}
