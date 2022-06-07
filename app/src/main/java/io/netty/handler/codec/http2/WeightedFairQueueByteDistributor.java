package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.PriorityQueueNode;
import java.util.Queue;

/* loaded from: classes4.dex */
public final class WeightedFairQueueByteDistributor implements StreamByteDistributor {
    static final /* synthetic */ boolean a = !WeightedFairQueueByteDistributor.class.desiredAssertionStatus();
    private final Http2Connection.PropertyKey b;
    private final a c;
    private int d = 1024;

    public WeightedFairQueueByteDistributor(Http2Connection http2Connection) {
        this.b = http2Connection.newKey();
        Http2Stream connectionStream = http2Connection.connectionStream();
        Http2Connection.PropertyKey propertyKey = this.b;
        a aVar = new a(connectionStream, 16);
        this.c = aVar;
        connectionStream.setProperty(propertyKey, aVar);
        http2Connection.addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.WeightedFairQueueByteDistributor.1
            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(WeightedFairQueueByteDistributor.this.b, new a(WeightedFairQueueByteDistributor.this, http2Stream));
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onWeightChanged(Http2Stream http2Stream, short s) {
                Http2Stream parent;
                if (WeightedFairQueueByteDistributor.this.a(http2Stream).c != 0 && (parent = http2Stream.parent()) != null) {
                    WeightedFairQueueByteDistributor.this.a(parent).f += http2Stream.weight() - s;
                }
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamClosed(Http2Stream http2Stream) {
                WeightedFairQueueByteDistributor.this.a(http2Stream).c();
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onPriorityTreeParentChanged(Http2Stream http2Stream, Http2Stream http2Stream2) {
                Http2Stream parent = http2Stream.parent();
                if (parent != null) {
                    a a2 = WeightedFairQueueByteDistributor.this.a(http2Stream);
                    if (a2.c != 0) {
                        a a3 = WeightedFairQueueByteDistributor.this.a(parent);
                        a3.a(a2);
                        a3.a(a2.c);
                    }
                }
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onPriorityTreeParentChanging(Http2Stream http2Stream, Http2Stream http2Stream2) {
                Http2Stream parent = http2Stream.parent();
                if (parent != null) {
                    a a2 = WeightedFairQueueByteDistributor.this.a(http2Stream);
                    if (a2.c != 0) {
                        a a3 = WeightedFairQueueByteDistributor.this.a(parent);
                        a3.c(a2);
                        a3.a(-a2.c);
                    }
                }
            }
        });
    }

    @Override // io.netty.handler.codec.http2.StreamByteDistributor
    public void updateStreamableBytes(StreamByteDistributor.StreamState streamState) {
        a(streamState.stream()).a(Http2CodecUtil.streamableBytes(streamState), streamState.hasFrame() && streamState.windowSize() >= 0);
    }

    @Override // io.netty.handler.codec.http2.StreamByteDistributor
    public boolean distribute(int i, StreamByteDistributor.Writer writer) throws Http2Exception {
        ObjectUtil.checkNotNull(writer, "writer");
        if (this.c.c == 0) {
            return false;
        }
        while (true) {
            int i2 = this.c.c;
            i -= b(i, writer, this.c);
            if (this.c.c == 0 || (i <= 0 && i2 == this.c.c)) {
                break;
            }
        }
        return this.c.c != 0;
    }

    public void allocationQuantum(int i) {
        if (i > 0) {
            this.d = i;
            return;
        }
        throw new IllegalArgumentException("allocationQuantum must be > 0");
    }

    private int a(int i, StreamByteDistributor.Writer writer, a aVar) throws Http2Exception {
        if (!aVar.g) {
            return b(i, writer, aVar);
        }
        int min = Math.min(i, aVar.b);
        aVar.a(min, writer);
        if (min == 0 && i != 0) {
            aVar.a(aVar.b, false);
        }
        return min;
    }

    private int b(int i, StreamByteDistributor.Writer writer, a aVar) throws Http2Exception {
        long j = aVar.f;
        a a2 = aVar.a();
        a b = aVar.b();
        try {
            if (!a && b != null && b.d < a2.d) {
                throw new AssertionError("nextChildState.pseudoTime(" + b.d + ") <  childState.pseudoTime(" + a2.d + ")");
            }
            i = Math.min(i, (int) Math.min((((b.d - a2.d) * a2.a.weight()) / j) + this.d, 2147483647L));
            int a3 = a(i, writer, a2);
            aVar.e += a3;
            a2.a(aVar, a3, j);
            return a3;
        } finally {
            if (a2.c != 0) {
                aVar.b(a2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public a a(Http2Stream http2Stream) {
        return (a) http2Stream.getProperty(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements PriorityQueueNode<a> {
        static final /* synthetic */ boolean h = !WeightedFairQueueByteDistributor.class.desiredAssertionStatus();
        final Http2Stream a;
        int b;
        int c;
        long d;
        long e;
        long f;
        boolean g;
        private final Queue<a> j;
        private int k;

        a(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, Http2Stream http2Stream) {
            this(http2Stream, 0);
        }

        a(Http2Stream http2Stream, int i) {
            this.k = -1;
            this.a = http2Stream;
            this.j = new PriorityQueue(i);
        }

        void a(int i, StreamByteDistributor.Writer writer) throws Http2Exception {
            try {
                writer.write(this.a, i);
            } catch (Throwable th) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, th, "byte distribution write error", new Object[0]);
            }
        }

        void a(int i) {
            if (h || this.c + i >= 0) {
                this.c += i;
                if (!this.a.isRoot()) {
                    a a = WeightedFairQueueByteDistributor.this.a(this.a.parent());
                    int i2 = this.c;
                    if (i2 == 0) {
                        a.c(this);
                    } else if (i2 - i == 0) {
                        a.a(this);
                    }
                    a.a(i);
                    return;
                }
                return;
            }
            throw new AssertionError();
        }

        void a(int i, boolean z) {
            if (this.g != z) {
                a(z ? 1 : -1);
                this.g = z;
            }
            this.b = i;
        }

        void a(a aVar, int i, long j) {
            if (h || (this.a.id() != 0 && i >= 0)) {
                this.d = Math.min(this.d, aVar.e) + ((i * j) / this.a.weight());
                return;
            }
            throw new AssertionError();
        }

        void a(a aVar) {
            aVar.d = this.e;
            b(aVar);
        }

        void b(a aVar) {
            this.j.offer(aVar);
            this.f += aVar.a.weight();
        }

        a a() {
            a poll = this.j.poll();
            this.f -= poll.a.weight();
            return poll;
        }

        void c(a aVar) {
            if (this.j.remove(aVar)) {
                this.f -= aVar.a.weight();
            }
        }

        a b() {
            return this.j.peek();
        }

        void c() {
            a(0, false);
        }

        /* renamed from: d */
        public int compareTo(a aVar) {
            return MathUtil.compare(this.d, aVar.d);
        }

        @Override // io.netty.util.internal.PriorityQueueNode
        public int priorityQueueIndex() {
            return this.k;
        }

        @Override // io.netty.util.internal.PriorityQueueNode
        public void priorityQueueIndex(int i) {
            this.k = i;
        }
    }
}
