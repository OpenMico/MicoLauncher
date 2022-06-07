package io.netty.handler.codec.http2;

import com.google.common.net.HttpHeaders;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutor;

/* loaded from: classes4.dex */
public final class Http2CodecUtil {
    public static final int CONNECTION_STREAM_ID = 0;
    public static final int CONTINUATION_FRAME_HEADER_LENGTH = 10;
    public static final int DATA_FRAME_HEADER_LENGTH = 10;
    public static final boolean DEFAULT_ENABLE_PUSH = true;
    public static final int DEFAULT_HEADER_TABLE_SIZE = 4096;
    public static final int DEFAULT_MAX_FRAME_SIZE = 16384;
    public static final int DEFAULT_MAX_HEADER_SIZE = 8192;
    public static final short DEFAULT_PRIORITY_WEIGHT = 16;
    public static final int DEFAULT_WINDOW_SIZE = 65535;
    public static final int FRAME_HEADER_LENGTH = 9;
    public static final int GO_AWAY_FRAME_HEADER_LENGTH = 17;
    public static final int HEADERS_FRAME_HEADER_LENGTH = 15;
    public static final int HTTP_UPGRADE_STREAM_ID = 1;
    public static final int INT_FIELD_LENGTH = 4;
    public static final long MAX_CONCURRENT_STREAMS = 4294967295L;
    public static final int MAX_FRAME_SIZE_LOWER_BOUND = 16384;
    public static final int MAX_FRAME_SIZE_UPPER_BOUND = 16777215;
    public static final long MAX_HEADER_LIST_SIZE = Long.MAX_VALUE;
    public static final int MAX_HEADER_TABLE_SIZE = Integer.MAX_VALUE;
    public static final int MAX_INITIAL_WINDOW_SIZE = Integer.MAX_VALUE;
    public static final short MAX_UNSIGNED_BYTE = 255;
    public static final long MAX_UNSIGNED_INT = 4294967295L;
    public static final int MAX_UNSIGNED_SHORT = 65535;
    public static final short MAX_WEIGHT = 256;
    public static final long MIN_CONCURRENT_STREAMS = 0;
    public static final long MIN_HEADER_LIST_SIZE = 0;
    public static final long MIN_HEADER_TABLE_SIZE = 0;
    public static final int MIN_INITIAL_WINDOW_SIZE = 0;
    public static final short MIN_WEIGHT = 1;
    public static final int NUM_STANDARD_SETTINGS = 6;
    public static final int PING_FRAME_PAYLOAD_LENGTH = 8;
    public static final int PRIORITY_ENTRY_LENGTH = 5;
    public static final int PRIORITY_FRAME_LENGTH = 14;
    public static final int PUSH_PROMISE_FRAME_HEADER_LENGTH = 14;
    public static final int RST_STREAM_FRAME_LENGTH = 13;
    public static final char SETTINGS_ENABLE_PUSH = 2;
    public static final char SETTINGS_HEADER_TABLE_SIZE = 1;
    public static final char SETTINGS_INITIAL_WINDOW_SIZE = 4;
    public static final char SETTINGS_MAX_CONCURRENT_STREAMS = 3;
    public static final char SETTINGS_MAX_FRAME_SIZE = 5;
    public static final char SETTINGS_MAX_HEADER_LIST_SIZE = 6;
    public static final int SETTING_ENTRY_LENGTH = 6;
    public static final int SMALLEST_MAX_CONCURRENT_STREAMS = 100;
    public static final int WINDOW_UPDATE_FRAME_LENGTH = 13;
    public static final CharSequence HTTP_UPGRADE_SETTINGS_HEADER = new AsciiString(HttpHeaders.HTTP2_SETTINGS);
    public static final CharSequence HTTP_UPGRADE_PROTOCOL_NAME = "h2c";
    public static final CharSequence TLS_UPGRADE_PROTOCOL_NAME = ApplicationProtocolNames.HTTP_2;
    private static final ByteBuf a = Unpooled.unreleasableBuffer(Unpooled.directBuffer(24).writeBytes("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n".getBytes(CharsetUtil.UTF_8))).asReadOnly();
    private static final ByteBuf b = Unpooled.unreleasableBuffer(Unpooled.directBuffer(8).writeZero(8)).asReadOnly();

    public static boolean isMaxFrameSizeValid(int i) {
        return i >= 16384 && i <= 16777215;
    }

    public static ByteBuf connectionPrefaceBuf() {
        return a.retainedDuplicate();
    }

    public static ByteBuf emptyPingBuf() {
        return b.retainedDuplicate();
    }

    public static Http2Exception getEmbeddedHttp2Exception(Throwable th) {
        while (th != null) {
            if (th instanceof Http2Exception) {
                return (Http2Exception) th;
            }
            th = th.getCause();
        }
        return null;
    }

    public static ByteBuf toByteBuf(ChannelHandlerContext channelHandlerContext, Throwable th) {
        if (th == null || th.getMessage() == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        return ByteBufUtil.writeUtf8(channelHandlerContext.alloc(), th.getMessage());
    }

    public static int readUnsignedInt(ByteBuf byteBuf) {
        return (byteBuf.readByte() & 255) | ((byteBuf.readByte() & Byte.MAX_VALUE) << 24) | ((byteBuf.readByte() & 255) << 16) | ((byteBuf.readByte() & 255) << 8);
    }

    public static void writeUnsignedInt(long j, ByteBuf byteBuf) {
        byteBuf.writeByte((int) ((j >> 24) & 255));
        byteBuf.writeByte((int) ((j >> 16) & 255));
        byteBuf.writeByte((int) ((j >> 8) & 255));
        byteBuf.writeByte((int) (j & 255));
    }

    public static void writeUnsignedShort(int i, ByteBuf byteBuf) {
        byteBuf.writeByte((i >> 8) & 255);
        byteBuf.writeByte(i & 255);
    }

    public static void writeFrameHeader(ByteBuf byteBuf, int i, byte b2, Http2Flags http2Flags, int i2) {
        byteBuf.ensureWritable(i + 9);
        a(byteBuf, i, b2, http2Flags, i2);
    }

    public static int streamableBytes(StreamByteDistributor.StreamState streamState) {
        return Math.max(0, Math.min(streamState.pendingBytes(), streamState.windowSize()));
    }

    public static void a(ByteBuf byteBuf, int i, byte b2, Http2Flags http2Flags, int i2) {
        byteBuf.writeMedium(i);
        byteBuf.writeByte(b2);
        byteBuf.writeByte(http2Flags.value());
        byteBuf.writeInt(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a extends DefaultChannelPromise {
        static final /* synthetic */ boolean a = !Http2CodecUtil.class.desiredAssertionStatus();
        private final ChannelPromise b;
        private int c;
        private int d;
        private Throwable e;
        private boolean f;

        public a(ChannelPromise channelPromise, Channel channel, EventExecutor eventExecutor) {
            super(channel, eventExecutor);
            if (a || (channelPromise != null && !channelPromise.isDone())) {
                this.b = channelPromise;
                return;
            }
            throw new AssertionError();
        }

        public ChannelPromise a() {
            if (a || !this.f) {
                this.c++;
                return this;
            }
            throw new AssertionError("Done allocating. No more promises can be allocated.");
        }

        public ChannelPromise b() {
            if (!this.f) {
                this.f = true;
                int i = this.d;
                int i2 = this.c;
                if (i == i2 || i2 == 0) {
                    return f();
                }
            }
            return this;
        }

        @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
        public boolean tryFailure(Throwable th) {
            if (!c()) {
                return false;
            }
            this.d++;
            this.e = th;
            if (e()) {
                return g();
            }
            return true;
        }

        @Override // io.netty.channel.DefaultChannelPromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
        public ChannelPromise setFailure(Throwable th) {
            if (c()) {
                this.d++;
                this.e = th;
                if (e()) {
                    return f();
                }
            }
            return this;
        }

        @Override // io.netty.channel.DefaultChannelPromise, io.netty.channel.ChannelPromise
        public ChannelPromise setSuccess(Void r1) {
            if (d()) {
                this.d++;
                if (e()) {
                    f();
                }
            }
            return this;
        }

        /* renamed from: a */
        public boolean trySuccess(Void r2) {
            if (!d()) {
                return false;
            }
            this.d++;
            if (e()) {
                return g();
            }
            return true;
        }

        private boolean c() {
            return d() || this.c == 0;
        }

        private boolean d() {
            return this.d < this.c;
        }

        private boolean e() {
            return this.d == this.c && this.f;
        }

        private ChannelPromise f() {
            Throwable th = this.e;
            if (th == null) {
                this.b.setSuccess();
                return super.setSuccess((Void) null);
            }
            this.b.setFailure(th);
            return super.setFailure(this.e);
        }

        private boolean g() {
            Throwable th = this.e;
            if (th == null) {
                this.b.trySuccess();
                return super.trySuccess(null);
            }
            this.b.tryFailure(th);
            return super.tryFailure(this.e);
        }
    }

    private Http2CodecUtil() {
    }
}
