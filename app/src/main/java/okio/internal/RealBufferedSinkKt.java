package okio.internal;

import com.umeng.analytics.pro.ai;
import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.RealBufferedSink;
import okio.Source;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;

/* compiled from: RealBufferedSink.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a\r\u0010\u0005\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0007\u001a\u00020\b*\u00020\u0002H\u0080\b\u001a\r\u0010\t\u001a\u00020\n*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0080\b\u001a%\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0080\b\u001a\u001d\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0080\b\u001a\u001d\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u0016\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\f\u001a\u00020\u0015H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010\u0019\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u001b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u001c\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010\u001e\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010\u001f\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010 \u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010!\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010#\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010$\u001a\u00020\u0004*\u00020\u00022\u0006\u0010%\u001a\u00020\nH\u0080\b\u001a%\u0010$\u001a\u00020\u0004*\u00020\u00022\u0006\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010(\u001a\u00020\u0004*\u00020\u00022\u0006\u0010)\u001a\u00020\u000fH\u0080\b¨\u0006*"}, d2 = {"commonClose", "", "Lokio/RealBufferedSink;", "commonEmit", "Lokio/BufferedSink;", "commonEmitCompleteSegments", "commonFlush", "commonTimeout", "Lokio/Timeout;", "commonToString", "", "commonWrite", "source", "", "offset", "", "byteCount", "Lokio/Buffer;", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "b", "commonWriteDecimalLong", ai.aC, "commonWriteHexadecimalUnsignedLong", "commonWriteInt", ai.aA, "commonWriteIntLe", "commonWriteLong", "commonWriteLongLe", "commonWriteShort", ai.az, "commonWriteShortLe", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "okio"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class RealBufferedSinkKt {
    public static final void commonWrite(@NotNull RealBufferedSink commonWrite, @NotNull Buffer source, long j) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!commonWrite.closed) {
            commonWrite.bufferField.write(source, j);
            commonWrite.emitCompleteSegments();
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWrite(@NotNull RealBufferedSink commonWrite, @NotNull ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!commonWrite.closed) {
            commonWrite.bufferField.write(byteString);
            return commonWrite.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWrite(@NotNull RealBufferedSink commonWrite, @NotNull ByteString byteString, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(byteString, "byteString");
        if (!commonWrite.closed) {
            commonWrite.bufferField.write(byteString, i, i2);
            return commonWrite.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteUtf8(@NotNull RealBufferedSink commonWriteUtf8, @NotNull String string) {
        Intrinsics.checkParameterIsNotNull(commonWriteUtf8, "$this$commonWriteUtf8");
        Intrinsics.checkParameterIsNotNull(string, "string");
        if (!commonWriteUtf8.closed) {
            commonWriteUtf8.bufferField.writeUtf8(string);
            return commonWriteUtf8.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteUtf8(@NotNull RealBufferedSink commonWriteUtf8, @NotNull String string, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonWriteUtf8, "$this$commonWriteUtf8");
        Intrinsics.checkParameterIsNotNull(string, "string");
        if (!commonWriteUtf8.closed) {
            commonWriteUtf8.bufferField.writeUtf8(string, i, i2);
            return commonWriteUtf8.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteUtf8CodePoint(@NotNull RealBufferedSink commonWriteUtf8CodePoint, int i) {
        Intrinsics.checkParameterIsNotNull(commonWriteUtf8CodePoint, "$this$commonWriteUtf8CodePoint");
        if (!commonWriteUtf8CodePoint.closed) {
            commonWriteUtf8CodePoint.bufferField.writeUtf8CodePoint(i);
            return commonWriteUtf8CodePoint.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWrite(@NotNull RealBufferedSink commonWrite, @NotNull byte[] source) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!commonWrite.closed) {
            commonWrite.bufferField.write(source);
            return commonWrite.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWrite(@NotNull RealBufferedSink commonWrite, @NotNull byte[] source, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(source, "source");
        if (!commonWrite.closed) {
            commonWrite.bufferField.write(source, i, i2);
            return commonWrite.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteByte(@NotNull RealBufferedSink commonWriteByte, int i) {
        Intrinsics.checkParameterIsNotNull(commonWriteByte, "$this$commonWriteByte");
        if (!commonWriteByte.closed) {
            commonWriteByte.bufferField.writeByte(i);
            return commonWriteByte.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteShort(@NotNull RealBufferedSink commonWriteShort, int i) {
        Intrinsics.checkParameterIsNotNull(commonWriteShort, "$this$commonWriteShort");
        if (!commonWriteShort.closed) {
            commonWriteShort.bufferField.writeShort(i);
            return commonWriteShort.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteShortLe(@NotNull RealBufferedSink commonWriteShortLe, int i) {
        Intrinsics.checkParameterIsNotNull(commonWriteShortLe, "$this$commonWriteShortLe");
        if (!commonWriteShortLe.closed) {
            commonWriteShortLe.bufferField.writeShortLe(i);
            return commonWriteShortLe.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteInt(@NotNull RealBufferedSink commonWriteInt, int i) {
        Intrinsics.checkParameterIsNotNull(commonWriteInt, "$this$commonWriteInt");
        if (!commonWriteInt.closed) {
            commonWriteInt.bufferField.writeInt(i);
            return commonWriteInt.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteIntLe(@NotNull RealBufferedSink commonWriteIntLe, int i) {
        Intrinsics.checkParameterIsNotNull(commonWriteIntLe, "$this$commonWriteIntLe");
        if (!commonWriteIntLe.closed) {
            commonWriteIntLe.bufferField.writeIntLe(i);
            return commonWriteIntLe.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteLong(@NotNull RealBufferedSink commonWriteLong, long j) {
        Intrinsics.checkParameterIsNotNull(commonWriteLong, "$this$commonWriteLong");
        if (!commonWriteLong.closed) {
            commonWriteLong.bufferField.writeLong(j);
            return commonWriteLong.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteLongLe(@NotNull RealBufferedSink commonWriteLongLe, long j) {
        Intrinsics.checkParameterIsNotNull(commonWriteLongLe, "$this$commonWriteLongLe");
        if (!commonWriteLongLe.closed) {
            commonWriteLongLe.bufferField.writeLongLe(j);
            return commonWriteLongLe.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteDecimalLong(@NotNull RealBufferedSink commonWriteDecimalLong, long j) {
        Intrinsics.checkParameterIsNotNull(commonWriteDecimalLong, "$this$commonWriteDecimalLong");
        if (!commonWriteDecimalLong.closed) {
            commonWriteDecimalLong.bufferField.writeDecimalLong(j);
            return commonWriteDecimalLong.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonWriteHexadecimalUnsignedLong(@NotNull RealBufferedSink commonWriteHexadecimalUnsignedLong, long j) {
        Intrinsics.checkParameterIsNotNull(commonWriteHexadecimalUnsignedLong, "$this$commonWriteHexadecimalUnsignedLong");
        if (!commonWriteHexadecimalUnsignedLong.closed) {
            commonWriteHexadecimalUnsignedLong.bufferField.writeHexadecimalUnsignedLong(j);
            return commonWriteHexadecimalUnsignedLong.emitCompleteSegments();
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonEmitCompleteSegments(@NotNull RealBufferedSink commonEmitCompleteSegments) {
        Intrinsics.checkParameterIsNotNull(commonEmitCompleteSegments, "$this$commonEmitCompleteSegments");
        if (!commonEmitCompleteSegments.closed) {
            long completeSegmentByteCount = commonEmitCompleteSegments.bufferField.completeSegmentByteCount();
            if (completeSegmentByteCount > 0) {
                commonEmitCompleteSegments.sink.write(commonEmitCompleteSegments.bufferField, completeSegmentByteCount);
            }
            return commonEmitCompleteSegments;
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final BufferedSink commonEmit(@NotNull RealBufferedSink commonEmit) {
        Intrinsics.checkParameterIsNotNull(commonEmit, "$this$commonEmit");
        if (!commonEmit.closed) {
            long size = commonEmit.bufferField.size();
            if (size > 0) {
                commonEmit.sink.write(commonEmit.bufferField, size);
            }
            return commonEmit;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final void commonFlush(@NotNull RealBufferedSink commonFlush) {
        Intrinsics.checkParameterIsNotNull(commonFlush, "$this$commonFlush");
        if (!commonFlush.closed) {
            if (commonFlush.bufferField.size() > 0) {
                commonFlush.sink.write(commonFlush.bufferField, commonFlush.bufferField.size());
            }
            commonFlush.sink.flush();
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final void commonClose(@NotNull RealBufferedSink commonClose) {
        Intrinsics.checkParameterIsNotNull(commonClose, "$this$commonClose");
        if (!commonClose.closed) {
            Throwable th = null;
            try {
                if (commonClose.bufferField.size() > 0) {
                    commonClose.sink.write(commonClose.bufferField, commonClose.bufferField.size());
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                commonClose.sink.close();
            } catch (Throwable th3) {
                th = th3;
                if (th == null) {
                }
            }
            commonClose.closed = true;
            if (th != null) {
                throw th;
            }
        }
    }

    @NotNull
    public static final Timeout commonTimeout(@NotNull RealBufferedSink commonTimeout) {
        Intrinsics.checkParameterIsNotNull(commonTimeout, "$this$commonTimeout");
        return commonTimeout.sink.timeout();
    }

    @NotNull
    public static final String commonToString(@NotNull RealBufferedSink commonToString) {
        Intrinsics.checkParameterIsNotNull(commonToString, "$this$commonToString");
        return "buffer(" + commonToString.sink + ')';
    }

    public static final long commonWriteAll(@NotNull RealBufferedSink commonWriteAll, @NotNull Source source) {
        Intrinsics.checkParameterIsNotNull(commonWriteAll, "$this$commonWriteAll");
        Intrinsics.checkParameterIsNotNull(source, "source");
        long j = 0;
        while (true) {
            long read = source.read(commonWriteAll.bufferField, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
            commonWriteAll.emitCompleteSegments();
        }
    }

    @NotNull
    public static final BufferedSink commonWrite(@NotNull RealBufferedSink commonWrite, @NotNull Source source, long j) {
        Intrinsics.checkParameterIsNotNull(commonWrite, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(source, "source");
        while (j > 0) {
            long read = source.read(commonWrite.bufferField, j);
            if (read != -1) {
                j -= read;
                commonWrite.emitCompleteSegments();
            } else {
                throw new EOFException();
            }
        }
        return commonWrite;
    }
}
