package okio;

import com.umeng.analytics.pro.ai;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: RealBufferedSink.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0001H\u0016J\b\u0010\u0011\u001a\u00020\u0001H\u0016J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\b\u0010\u0013\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001eH\u0016J \u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#H\u0016J \u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020$2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010%\u001a\u00020!2\u0006\u0010\u001c\u001a\u00020$H\u0016J\u0010\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\u001bH\u0016J\u0010\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010*\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001bH\u0016J\u0010\u0010-\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001bH\u0016J\u0010\u0010.\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010/\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u001bH\u0016J\u0010\u00102\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u001bH\u0016J\u0018\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u000206H\u0016J(\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u001b2\u0006\u00105\u001a\u000206H\u0016J\u0010\u00109\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u0019H\u0016J \u00109\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u001bH\u0016J\u0010\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020\u001bH\u0016R\u001b\u0010\u0005\u001a\u00020\u00068Ö\u0002X\u0096\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lokio/RealBufferedSink;", "Lokio/BufferedSink;", "sink", "Lokio/Sink;", "(Lokio/Sink;)V", "buffer", "Lokio/Buffer;", "buffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "emit", "emitCompleteSegments", "flush", "isOpen", "outputStream", "Ljava/io/OutputStream;", RtspHeaders.Values.TIMEOUT, "Lokio/Timeout;", "toString", "", "write", "", "source", "Ljava/nio/ByteBuffer;", "", "offset", "byteCount", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "writeAll", "writeByte", "b", "writeDecimalLong", ai.aC, "writeHexadecimalUnsignedLong", "writeInt", ai.aA, "writeIntLe", "writeLong", "writeLongLe", "writeShort", ai.az, "writeShortLe", "writeString", "string", "charset", "Ljava/nio/charset/Charset;", "beginIndex", "endIndex", "writeUtf8", "writeUtf8CodePoint", "codePoint", "okio"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class RealBufferedSink implements BufferedSink {
    @JvmField
    @NotNull
    public final Buffer bufferField = new Buffer();
    @JvmField
    public boolean closed;
    @JvmField
    @NotNull
    public final Sink sink;

    public static /* synthetic */ void buffer$annotations() {
    }

    public RealBufferedSink(@NotNull Sink sink) {
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        this.sink = sink;
    }

    @Override // okio.BufferedSink, okio.BufferedSource
    @NotNull
    public Buffer getBuffer() {
        return this.bufferField;
    }

    @Override // okio.BufferedSink, okio.BufferedSource
    @NotNull
    public Buffer buffer() {
        return this.bufferField;
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeString(@NotNull String string, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        if (!this.closed) {
            this.bufferField.writeString(string, charset);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeString(@NotNull String string, int i, int i2, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        if (!this.closed) {
            this.bufferField.writeString(string, i, i2, charset);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(@NotNull ByteBuffer source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!this.closed) {
            int write = this.bufferField.write(source);
            emitCompleteSegments();
            return write;
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public OutputStream outputStream() {
        return new OutputStream() { // from class: okio.RealBufferedSink$outputStream$1
            @Override // java.io.OutputStream
            public void write(int i) {
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.bufferField.writeByte((int) ((byte) i));
                    RealBufferedSink.this.emitCompleteSegments();
                    return;
                }
                throw new IOException("closed");
            }

            @Override // java.io.OutputStream
            public void write(@NotNull byte[] data, int i, int i2) {
                Intrinsics.checkParameterIsNotNull(data, "data");
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.bufferField.write(data, i, i2);
                    RealBufferedSink.this.emitCompleteSegments();
                    return;
                }
                throw new IOException("closed");
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() {
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.flush();
                }
            }

            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                RealBufferedSink.this.close();
            }

            @NotNull
            public String toString() {
                return RealBufferedSink.this + ".outputStream()";
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.closed;
    }

    @Override // okio.Sink
    public void write(@NotNull Buffer source, long j) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!this.closed) {
            this.bufferField.write(source, j);
            emitCompleteSegments();
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink write(@NotNull ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!this.closed) {
            this.bufferField.write(byteString);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink write(@NotNull ByteString byteString, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!this.closed) {
            this.bufferField.write(byteString, i, i2);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeUtf8(@NotNull String string) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        if (!this.closed) {
            this.bufferField.writeUtf8(string);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeUtf8(@NotNull String string, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        if (!this.closed) {
            this.bufferField.writeUtf8(string, i, i2);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeUtf8CodePoint(int i) {
        if (!this.closed) {
            this.bufferField.writeUtf8CodePoint(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink write(@NotNull byte[] source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!this.closed) {
            this.bufferField.write(source);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink write(@NotNull byte[] source, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!this.closed) {
            this.bufferField.write(source, i, i2);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    public long writeAll(@NotNull Source source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        long j = 0;
        while (true) {
            long read = source.read(this.bufferField, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
            emitCompleteSegments();
        }
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink write(@NotNull Source source, long j) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        while (j > 0) {
            long read = source.read(this.bufferField, j);
            if (read != -1) {
                j -= read;
                emitCompleteSegments();
            } else {
                throw new EOFException();
            }
        }
        return this;
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeByte(int i) {
        if (!this.closed) {
            this.bufferField.writeByte(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeShort(int i) {
        if (!this.closed) {
            this.bufferField.writeShort(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeShortLe(int i) {
        if (!this.closed) {
            this.bufferField.writeShortLe(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeInt(int i) {
        if (!this.closed) {
            this.bufferField.writeInt(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeIntLe(int i) {
        if (!this.closed) {
            this.bufferField.writeIntLe(i);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeLong(long j) {
        if (!this.closed) {
            this.bufferField.writeLong(j);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeLongLe(long j) {
        if (!this.closed) {
            this.bufferField.writeLongLe(j);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeDecimalLong(long j) {
        if (!this.closed) {
            this.bufferField.writeDecimalLong(j);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink writeHexadecimalUnsignedLong(long j) {
        if (!this.closed) {
            this.bufferField.writeHexadecimalUnsignedLong(j);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink emitCompleteSegments() {
        if (!this.closed) {
            long completeSegmentByteCount = this.bufferField.completeSegmentByteCount();
            if (completeSegmentByteCount > 0) {
                this.sink.write(this.bufferField, completeSegmentByteCount);
            }
            return this;
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink
    @NotNull
    public BufferedSink emit() {
        if (!this.closed) {
            long size = this.bufferField.size();
            if (size > 0) {
                this.sink.write(this.bufferField, size);
            }
            return this;
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() {
        if (!this.closed) {
            if (this.bufferField.size() > 0) {
                Sink sink = this.sink;
                Buffer buffer = this.bufferField;
                sink.write(buffer, buffer.size());
            }
            this.sink.flush();
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable, okio.Sink
    public void close() {
        if (!this.closed) {
            Throwable th = null;
            try {
                if (this.bufferField.size() > 0) {
                    this.sink.write(this.bufferField, this.bufferField.size());
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                this.sink.close();
            } catch (Throwable th3) {
                th = th3;
                if (th == null) {
                }
            }
            this.closed = true;
            if (th != null) {
                throw th;
            }
        }
    }

    @Override // okio.Sink
    @NotNull
    public Timeout timeout() {
        return this.sink.timeout();
    }

    @NotNull
    public String toString() {
        return "buffer(" + this.sink + ')';
    }
}
