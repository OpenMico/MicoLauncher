package io.netty.handler.codec.http2;

import com.xiaomi.ai.core.AivsConfig;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;
import java.util.Deque;

/* loaded from: classes4.dex */
public final class UniformStreamByteDistributor implements StreamByteDistributor {
    private final Http2Connection.PropertyKey a;
    private final Deque<a> b = new ArrayDeque(4);
    private int c = 1024;
    private long d;

    static /* synthetic */ long a(UniformStreamByteDistributor uniformStreamByteDistributor, long j) {
        long j2 = uniformStreamByteDistributor.d + j;
        uniformStreamByteDistributor.d = j2;
        return j2;
    }

    public UniformStreamByteDistributor(Http2Connection http2Connection) {
        this.a = http2Connection.newKey();
        Http2Stream connectionStream = http2Connection.connectionStream();
        connectionStream.setProperty(this.a, new a(connectionStream));
        http2Connection.addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.UniformStreamByteDistributor.1
            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(UniformStreamByteDistributor.this.a, new a(http2Stream));
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamClosed(Http2Stream http2Stream) {
                UniformStreamByteDistributor.this.a(http2Stream).c();
            }
        });
    }

    public void minAllocationChunk(int i) {
        if (i > 0) {
            this.c = i;
            return;
        }
        throw new IllegalArgumentException("minAllocationChunk must be > 0");
    }

    @Override // io.netty.handler.codec.http2.StreamByteDistributor
    public void updateStreamableBytes(StreamByteDistributor.StreamState streamState) {
        a(streamState.stream()).a(Http2CodecUtil.streamableBytes(streamState), streamState.hasFrame(), streamState.windowSize());
    }

    @Override // io.netty.handler.codec.http2.StreamByteDistributor
    public boolean distribute(int i, StreamByteDistributor.Writer writer) throws Http2Exception {
        ObjectUtil.checkNotNull(writer, "writer");
        int size = this.b.size();
        if (size == 0) {
            return this.d > 0;
        }
        int max = Math.max(this.c, i / size);
        a pollFirst = this.b.pollFirst();
        while (true) {
            pollFirst.d = false;
            if (!pollFirst.c) {
                if (i == 0 && pollFirst.b > 0) {
                    this.b.addFirst(pollFirst);
                    pollFirst.d = true;
                    break;
                }
                int min = Math.min(max, Math.min(i, pollFirst.b));
                i -= min;
                pollFirst.a(min, writer);
            }
            pollFirst = this.b.pollFirst();
            if (pollFirst == null) {
                break;
            }
        }
        return this.d > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public a a(Http2Stream http2Stream) {
        return (a) ((Http2Stream) ObjectUtil.checkNotNull(http2Stream, AivsConfig.Tts.AUDIO_TYPE_STREAM)).getProperty(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a {
        static final /* synthetic */ boolean f = !UniformStreamByteDistributor.class.desiredAssertionStatus();
        final Http2Stream a;
        int b;
        boolean c;
        boolean d;
        boolean e;

        a(Http2Stream http2Stream) {
            this.a = http2Stream;
        }

        void a(int i, boolean z, int i2) {
            if (f || z || i == 0) {
                int i3 = i - this.b;
                if (i3 != 0) {
                    this.b = i;
                    UniformStreamByteDistributor.a(UniformStreamByteDistributor.this, i3);
                }
                this.c = i2 < 0;
                if (!z) {
                    return;
                }
                if (i2 > 0 || (i2 == 0 && !this.e)) {
                    a();
                    return;
                }
                return;
            }
            throw new AssertionError("hasFrame: " + z + " newStreamableBytes: " + i);
        }

        void a(int i, StreamByteDistributor.Writer writer) throws Http2Exception {
            this.e = true;
            try {
                writer.write(this.a, i);
            } finally {
                this.e = false;
            }
        }

        void a() {
            if (!this.d) {
                this.d = true;
                UniformStreamByteDistributor.this.b.addLast(this);
            }
        }

        void b() {
            if (this.d) {
                this.d = false;
                UniformStreamByteDistributor.this.b.remove(this);
            }
        }

        void c() {
            b();
            a(0, false, 0);
        }
    }
}
