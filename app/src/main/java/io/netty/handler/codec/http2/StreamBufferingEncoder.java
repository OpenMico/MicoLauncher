package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class StreamBufferingEncoder extends DecoratingHttp2ConnectionEncoder {
    private final TreeMap<Integer, d> a;
    private int b;
    private boolean c;

    /* loaded from: classes4.dex */
    public static final class Http2ChannelClosedException extends Http2Exception {
        private static final long serialVersionUID = 4768543442094476971L;

        public Http2ChannelClosedException() {
            super(Http2Error.REFUSED_STREAM, "Connection closed");
        }
    }

    /* loaded from: classes4.dex */
    public static final class Http2GoAwayException extends Http2Exception {
        private static final long serialVersionUID = 1326785622777291198L;
        private final byte[] debugData;
        private final long errorCode;
        private final int lastStreamId;

        public Http2GoAwayException(int i, long j, byte[] bArr) {
            super(Http2Error.STREAM_CLOSED);
            this.lastStreamId = i;
            this.errorCode = j;
            this.debugData = bArr;
        }

        public int lastStreamId() {
            return this.lastStreamId;
        }

        public long errorCode() {
            return this.errorCode;
        }

        public byte[] debugData() {
            return this.debugData;
        }
    }

    public StreamBufferingEncoder(Http2ConnectionEncoder http2ConnectionEncoder) {
        this(http2ConnectionEncoder, 100);
    }

    public StreamBufferingEncoder(Http2ConnectionEncoder http2ConnectionEncoder, int i) {
        super(http2ConnectionEncoder);
        this.a = new TreeMap<>();
        this.b = i;
        connection().addListener(new Http2ConnectionAdapter() { // from class: io.netty.handler.codec.http2.StreamBufferingEncoder.1
            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onGoAwayReceived(int i2, long j, ByteBuf byteBuf) {
                StreamBufferingEncoder.this.a(i2, j, byteBuf);
            }

            @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
            public void onStreamClosed(Http2Stream http2Stream) {
                StreamBufferingEncoder.this.a();
            }
        });
    }

    public int numBufferedStreams() {
        return this.a.size();
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z, ChannelPromise channelPromise) {
        return writeHeaders(channelHandlerContext, i, http2Headers, 0, (short) 16, false, i2, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2, ChannelPromise channelPromise) {
        if (this.c) {
            return channelPromise.setFailure((Throwable) new Http2ChannelClosedException());
        }
        if (a(i) || connection().goAwayReceived()) {
            return super.writeHeaders(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2, channelPromise);
        }
        if (b()) {
            return super.writeHeaders(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2, channelPromise);
        }
        d dVar = this.a.get(Integer.valueOf(i));
        if (dVar == null) {
            dVar = new d(channelHandlerContext, i);
            this.a.put(Integer.valueOf(i), dVar);
        }
        dVar.c.add(new c(http2Headers, i2, s, z, i3, z2, channelPromise));
        return channelPromise;
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int i, long j, ChannelPromise channelPromise) {
        if (a(i)) {
            return super.writeRstStream(channelHandlerContext, i, j, channelPromise);
        }
        d remove = this.a.remove(Integer.valueOf(i));
        if (remove != null) {
            remove.a(null);
            channelPromise.setSuccess();
        } else {
            channelPromise.setFailure((Throwable) Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream does not exist %d", Integer.valueOf(i)));
        }
        return channelPromise;
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2DataWriter
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z, ChannelPromise channelPromise) {
        if (a(i)) {
            return super.writeData(channelHandlerContext, i, byteBuf, i2, z, channelPromise);
        }
        d dVar = this.a.get(Integer.valueOf(i));
        if (dVar != null) {
            dVar.c.add(new a(byteBuf, i2, z, channelPromise));
        } else {
            ReferenceCountUtil.safeRelease(byteBuf);
            channelPromise.setFailure((Throwable) Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream does not exist %d", Integer.valueOf(i)));
        }
        return channelPromise;
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2ConnectionEncoder, io.netty.handler.codec.http2.Http2ConnectionEncoder
    public void remoteSettings(Http2Settings http2Settings) throws Http2Exception {
        super.remoteSettings(http2Settings);
        this.b = connection().local().maxActiveStreams();
        a();
    }

    @Override // io.netty.handler.codec.http2.DecoratingHttp2FrameWriter, io.netty.handler.codec.http2.Http2FrameWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            if (!this.c) {
                this.c = true;
                Http2ChannelClosedException http2ChannelClosedException = new Http2ChannelClosedException();
                while (!this.a.isEmpty()) {
                    this.a.pollFirstEntry().getValue().a(http2ChannelClosedException);
                }
            }
        } finally {
            super.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        while (!this.a.isEmpty() && b()) {
            this.a.pollFirstEntry().getValue().a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, long j, ByteBuf byteBuf) {
        Iterator<d> it = this.a.values().iterator();
        Http2GoAwayException http2GoAwayException = new Http2GoAwayException(i, j, ByteBufUtil.getBytes(byteBuf));
        while (it.hasNext()) {
            d next = it.next();
            if (next.b > i) {
                it.remove();
                next.a(http2GoAwayException);
            }
        }
    }

    private boolean b() {
        return connection().local().numActiveStreams() < this.b;
    }

    private boolean a(int i) {
        return i <= connection().local().lastStreamCreated();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class d {
        final ChannelHandlerContext a;
        final int b;
        final Queue<b> c = new ArrayDeque(2);

        d(ChannelHandlerContext channelHandlerContext, int i) {
            this.a = channelHandlerContext;
            this.b = i;
        }

        void a() {
            for (b bVar : this.c) {
                bVar.a(this.a, this.b);
            }
        }

        void a(Throwable th) {
            for (b bVar : this.c) {
                bVar.a(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static abstract class b {
        final ChannelPromise e;

        abstract void a(ChannelHandlerContext channelHandlerContext, int i);

        b(ChannelPromise channelPromise) {
            this.e = channelPromise;
        }

        void a(Throwable th) {
            if (th == null) {
                this.e.setSuccess();
            } else {
                this.e.setFailure(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class c extends b {
        final Http2Headers a;
        final int b;
        final short c;
        final boolean d;
        final int f;
        final boolean g;

        c(Http2Headers http2Headers, int i, short s, boolean z, int i2, boolean z2, ChannelPromise channelPromise) {
            super(channelPromise);
            this.a = http2Headers;
            this.b = i;
            this.c = s;
            this.d = z;
            this.f = i2;
            this.g = z2;
        }

        @Override // io.netty.handler.codec.http2.StreamBufferingEncoder.b
        void a(ChannelHandlerContext channelHandlerContext, int i) {
            StreamBufferingEncoder.this.writeHeaders(channelHandlerContext, i, this.a, this.b, this.c, this.d, this.f, this.g, this.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends b {
        final ByteBuf a;
        final int b;
        final boolean c;

        a(ByteBuf byteBuf, int i, boolean z, ChannelPromise channelPromise) {
            super(channelPromise);
            this.a = byteBuf;
            this.b = i;
            this.c = z;
        }

        @Override // io.netty.handler.codec.http2.StreamBufferingEncoder.b
        void a(Throwable th) {
            super.a(th);
            ReferenceCountUtil.safeRelease(this.a);
        }

        @Override // io.netty.handler.codec.http2.StreamBufferingEncoder.b
        void a(ChannelHandlerContext channelHandlerContext, int i) {
            StreamBufferingEncoder.this.writeData(channelHandlerContext, i, this.a, this.b, this.c, this.e);
        }
    }
}
