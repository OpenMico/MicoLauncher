package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.RealBufferedSource;
import okio.Sink;
import okio.Timeout;
import okio.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealBufferedSource.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a%\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\r\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a-\u0010\u0011\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010\u001a\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001bH\u0080\b\u001a\r\u0010\u001c\u001a\u00020\b*\u00020\u0002H\u0080\b\u001a\r\u0010\u001d\u001a\u00020\u0018*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001e\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\u0015\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\u0080\b\u001a\u001d\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010!\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010\"\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010#\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010$\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010%\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010&\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010(\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010)\u001a\u00020**\u00020\u0002H\u0080\b\u001a\u0015\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010+\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\u000f\u0010,\u001a\u0004\u0018\u00010**\u00020\u0002H\u0080\b\u001a\u0015\u0010-\u001a\u00020**\u00020\u00022\u0006\u0010.\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010/\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0014*\u00020\u00022\u0006\u00102\u001a\u000203H\u0080\b\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0002H\u0080\b\u001a\r\u00107\u001a\u00020**\u00020\u0002H\u0080\b¨\u00068"}, d2 = {"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", "offset", "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class RealBufferedSourceKt {
    public static final long commonRead(@NotNull RealBufferedSource commonRead, @NotNull Buffer sink, long j) {
        Intrinsics.checkParameterIsNotNull(commonRead, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        } else if (!(!commonRead.closed)) {
            throw new IllegalStateException("closed".toString());
        } else if (commonRead.bufferField.size() == 0 && commonRead.source.read(commonRead.bufferField, 8192) == -1) {
            return -1L;
        } else {
            return commonRead.bufferField.read(sink, Math.min(j, commonRead.bufferField.size()));
        }
    }

    public static final boolean commonExhausted(@NotNull RealBufferedSource commonExhausted) {
        Intrinsics.checkParameterIsNotNull(commonExhausted, "$this$commonExhausted");
        if (!commonExhausted.closed) {
            return commonExhausted.bufferField.exhausted() && commonExhausted.source.read(commonExhausted.bufferField, (long) 8192) == -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final void commonRequire(@NotNull RealBufferedSource commonRequire, long j) {
        Intrinsics.checkParameterIsNotNull(commonRequire, "$this$commonRequire");
        if (!commonRequire.request(j)) {
            throw new EOFException();
        }
    }

    public static final boolean commonRequest(@NotNull RealBufferedSource commonRequest, long j) {
        Intrinsics.checkParameterIsNotNull(commonRequest, "$this$commonRequest");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        } else if (!commonRequest.closed) {
            while (commonRequest.bufferField.size() < j) {
                if (commonRequest.source.read(commonRequest.bufferField, 8192) == -1) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public static final byte commonReadByte(@NotNull RealBufferedSource commonReadByte) {
        Intrinsics.checkParameterIsNotNull(commonReadByte, "$this$commonReadByte");
        commonReadByte.require(1L);
        return commonReadByte.bufferField.readByte();
    }

    @NotNull
    public static final ByteString commonReadByteString(@NotNull RealBufferedSource commonReadByteString, long j) {
        Intrinsics.checkParameterIsNotNull(commonReadByteString, "$this$commonReadByteString");
        commonReadByteString.require(j);
        return commonReadByteString.bufferField.readByteString(j);
    }

    public static final int commonSelect(@NotNull RealBufferedSource commonSelect, @NotNull Options options) {
        Intrinsics.checkParameterIsNotNull(commonSelect, "$this$commonSelect");
        Intrinsics.checkParameterIsNotNull(options, "options");
        if (!commonSelect.closed) {
            do {
                int selectPrefix = BufferKt.selectPrefix(commonSelect.bufferField, options, true);
                switch (selectPrefix) {
                    case -2:
                        break;
                    case -1:
                        return -1;
                    default:
                        commonSelect.bufferField.skip(options.getByteStrings$okio()[selectPrefix].size());
                        return selectPrefix;
                }
            } while (commonSelect.source.read(commonSelect.bufferField, 8192) != -1);
            return -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    @NotNull
    public static final byte[] commonReadByteArray(@NotNull RealBufferedSource commonReadByteArray, long j) {
        Intrinsics.checkParameterIsNotNull(commonReadByteArray, "$this$commonReadByteArray");
        commonReadByteArray.require(j);
        return commonReadByteArray.bufferField.readByteArray(j);
    }

    public static final void commonReadFully(@NotNull RealBufferedSource commonReadFully, @NotNull byte[] sink) {
        Intrinsics.checkParameterIsNotNull(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        try {
            commonReadFully.require(sink.length);
            commonReadFully.bufferField.readFully(sink);
        } catch (EOFException e) {
            int i = 0;
            while (commonReadFully.bufferField.size() > 0) {
                int read = commonReadFully.bufferField.read(sink, i, (int) commonReadFully.bufferField.size());
                if (read != -1) {
                    i += read;
                } else {
                    throw new AssertionError();
                }
            }
            throw e;
        }
    }

    public static final int commonRead(@NotNull RealBufferedSource commonRead, @NotNull byte[] sink, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonRead, "$this$commonRead");
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        long j = i2;
        Util.checkOffsetAndCount(sink.length, i, j);
        if (commonRead.bufferField.size() == 0 && commonRead.source.read(commonRead.bufferField, 8192) == -1) {
            return -1;
        }
        return commonRead.bufferField.read(sink, i, (int) Math.min(j, commonRead.bufferField.size()));
    }

    public static final void commonReadFully(@NotNull RealBufferedSource commonReadFully, @NotNull Buffer sink, long j) {
        Intrinsics.checkParameterIsNotNull(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        try {
            commonReadFully.require(j);
            commonReadFully.bufferField.readFully(sink, j);
        } catch (EOFException e) {
            sink.writeAll(commonReadFully.bufferField);
            throw e;
        }
    }

    public static final long commonReadAll(@NotNull RealBufferedSource commonReadAll, @NotNull Sink sink) {
        Intrinsics.checkParameterIsNotNull(commonReadAll, "$this$commonReadAll");
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        long j = 0;
        while (commonReadAll.source.read(commonReadAll.bufferField, 8192) != -1) {
            long completeSegmentByteCount = commonReadAll.bufferField.completeSegmentByteCount();
            if (completeSegmentByteCount > 0) {
                j += completeSegmentByteCount;
                sink.write(commonReadAll.bufferField, completeSegmentByteCount);
            }
        }
        if (commonReadAll.bufferField.size() <= 0) {
            return j;
        }
        long size = j + commonReadAll.bufferField.size();
        sink.write(commonReadAll.bufferField, commonReadAll.bufferField.size());
        return size;
    }

    @NotNull
    public static final String commonReadUtf8(@NotNull RealBufferedSource commonReadUtf8, long j) {
        Intrinsics.checkParameterIsNotNull(commonReadUtf8, "$this$commonReadUtf8");
        commonReadUtf8.require(j);
        return commonReadUtf8.bufferField.readUtf8(j);
    }

    @Nullable
    public static final String commonReadUtf8Line(@NotNull RealBufferedSource commonReadUtf8Line) {
        Intrinsics.checkParameterIsNotNull(commonReadUtf8Line, "$this$commonReadUtf8Line");
        long indexOf = commonReadUtf8Line.indexOf((byte) 10);
        if (indexOf != -1) {
            return BufferKt.readUtf8Line(commonReadUtf8Line.bufferField, indexOf);
        }
        if (commonReadUtf8Line.bufferField.size() != 0) {
            return commonReadUtf8Line.readUtf8(commonReadUtf8Line.bufferField.size());
        }
        return null;
    }

    @NotNull
    public static final String commonReadUtf8LineStrict(@NotNull RealBufferedSource commonReadUtf8LineStrict, long j) {
        Intrinsics.checkParameterIsNotNull(commonReadUtf8LineStrict, "$this$commonReadUtf8LineStrict");
        if (j >= 0) {
            long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
            byte b = (byte) 10;
            long indexOf = commonReadUtf8LineStrict.indexOf(b, 0L, j2);
            if (indexOf != -1) {
                return BufferKt.readUtf8Line(commonReadUtf8LineStrict.bufferField, indexOf);
            }
            if (j2 < Long.MAX_VALUE && commonReadUtf8LineStrict.request(j2) && commonReadUtf8LineStrict.bufferField.getByte(j2 - 1) == ((byte) 13) && commonReadUtf8LineStrict.request(1 + j2) && commonReadUtf8LineStrict.bufferField.getByte(j2) == b) {
                return BufferKt.readUtf8Line(commonReadUtf8LineStrict.bufferField, j2);
            }
            Buffer buffer = new Buffer();
            commonReadUtf8LineStrict.bufferField.copyTo(buffer, 0L, Math.min(32, commonReadUtf8LineStrict.bufferField.size()));
            throw new EOFException("\\n not found: limit=" + Math.min(commonReadUtf8LineStrict.bufferField.size(), j) + " content=" + buffer.readByteString().hex() + "…");
        }
        throw new IllegalArgumentException(("limit < 0: " + j).toString());
    }

    public static final int commonReadUtf8CodePoint(@NotNull RealBufferedSource commonReadUtf8CodePoint) {
        Intrinsics.checkParameterIsNotNull(commonReadUtf8CodePoint, "$this$commonReadUtf8CodePoint");
        commonReadUtf8CodePoint.require(1L);
        byte b = commonReadUtf8CodePoint.bufferField.getByte(0L);
        if ((b & 224) == 192) {
            commonReadUtf8CodePoint.require(2L);
        } else if ((b & 240) == 224) {
            commonReadUtf8CodePoint.require(3L);
        } else if ((b & 248) == 240) {
            commonReadUtf8CodePoint.require(4L);
        }
        return commonReadUtf8CodePoint.bufferField.readUtf8CodePoint();
    }

    public static final short commonReadShort(@NotNull RealBufferedSource commonReadShort) {
        Intrinsics.checkParameterIsNotNull(commonReadShort, "$this$commonReadShort");
        commonReadShort.require(2L);
        return commonReadShort.bufferField.readShort();
    }

    public static final short commonReadShortLe(@NotNull RealBufferedSource commonReadShortLe) {
        Intrinsics.checkParameterIsNotNull(commonReadShortLe, "$this$commonReadShortLe");
        commonReadShortLe.require(2L);
        return commonReadShortLe.bufferField.readShortLe();
    }

    public static final int commonReadInt(@NotNull RealBufferedSource commonReadInt) {
        Intrinsics.checkParameterIsNotNull(commonReadInt, "$this$commonReadInt");
        commonReadInt.require(4L);
        return commonReadInt.bufferField.readInt();
    }

    public static final int commonReadIntLe(@NotNull RealBufferedSource commonReadIntLe) {
        Intrinsics.checkParameterIsNotNull(commonReadIntLe, "$this$commonReadIntLe");
        commonReadIntLe.require(4L);
        return commonReadIntLe.bufferField.readIntLe();
    }

    public static final long commonReadLong(@NotNull RealBufferedSource commonReadLong) {
        Intrinsics.checkParameterIsNotNull(commonReadLong, "$this$commonReadLong");
        commonReadLong.require(8L);
        return commonReadLong.bufferField.readLong();
    }

    public static final long commonReadLongLe(@NotNull RealBufferedSource commonReadLongLe) {
        Intrinsics.checkParameterIsNotNull(commonReadLongLe, "$this$commonReadLongLe");
        commonReadLongLe.require(8L);
        return commonReadLongLe.bufferField.readLongLe();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:
        if (r4 == 0) goto L_0x0034;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0034, code lost:
        r10 = new java.lang.StringBuilder();
        r10.append("Expected leading [0-9] or '-' character but was 0x");
        r0 = java.lang.Integer.toString(r8, kotlin.text.CharsKt.checkRadix(kotlin.text.CharsKt.checkRadix(16)));
        kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        r10.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005f, code lost:
        throw new java.lang.NumberFormatException(r10.toString());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final long commonReadDecimalLong(@org.jetbrains.annotations.NotNull okio.RealBufferedSource r10) {
        /*
            java.lang.String r0 = "$this$commonReadDecimalLong"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r10, r0)
            r0 = 1
            r10.require(r0)
            r2 = 0
            r4 = r2
        L_0x000d:
            long r6 = r4 + r0
            boolean r8 = r10.request(r6)
            if (r8 == 0) goto L_0x0060
            okio.Buffer r8 = r10.bufferField
            byte r8 = r8.getByte(r4)
            r9 = 48
            byte r9 = (byte) r9
            if (r8 < r9) goto L_0x0025
            r9 = 57
            byte r9 = (byte) r9
            if (r8 <= r9) goto L_0x002f
        L_0x0025:
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0031
            r5 = 45
            byte r5 = (byte) r5
            if (r8 == r5) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            r4 = r6
            goto L_0x000d
        L_0x0031:
            if (r4 == 0) goto L_0x0034
            goto L_0x0060
        L_0x0034:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "Expected leading [0-9] or '-' character but was 0x"
            r10.append(r0)
            r0 = 16
            int r0 = kotlin.text.CharsKt.checkRadix(r0)
            int r0 = kotlin.text.CharsKt.checkRadix(r0)
            java.lang.String r0 = java.lang.Integer.toString(r8, r0)
            java.lang.String r1 = "java.lang.Integer.toStri…(this, checkRadix(radix))"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            r0.<init>(r10)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        L_0x0060:
            okio.Buffer r10 = r10.bufferField
            long r0 = r10.readDecimalLong()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.RealBufferedSourceKt.commonReadDecimalLong(okio.RealBufferedSource):long");
    }

    public static final long commonReadHexadecimalUnsignedLong(@NotNull RealBufferedSource commonReadHexadecimalUnsignedLong) {
        byte b;
        Intrinsics.checkParameterIsNotNull(commonReadHexadecimalUnsignedLong, "$this$commonReadHexadecimalUnsignedLong");
        commonReadHexadecimalUnsignedLong.require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!commonReadHexadecimalUnsignedLong.request(i2)) {
                break;
            }
            b = commonReadHexadecimalUnsignedLong.bufferField.getByte(i);
            if ((b < ((byte) 48) || b > ((byte) 57)) && ((b < ((byte) 97) || b > ((byte) 102)) && (b < ((byte) 65) || b > ((byte) 70)))) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected leading [0-9a-fA-F] character but was 0x");
            String num = Integer.toString(b, CharsKt.checkRadix(CharsKt.checkRadix(16)));
            Intrinsics.checkExpressionValueIsNotNull(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
            sb.append(num);
            throw new NumberFormatException(sb.toString());
        }
        return commonReadHexadecimalUnsignedLong.bufferField.readHexadecimalUnsignedLong();
    }

    public static final void commonSkip(@NotNull RealBufferedSource commonSkip, long j) {
        Intrinsics.checkParameterIsNotNull(commonSkip, "$this$commonSkip");
        if (!commonSkip.closed) {
            while (j > 0) {
                if (commonSkip.bufferField.size() == 0 && commonSkip.source.read(commonSkip.bufferField, 8192) == -1) {
                    throw new EOFException();
                }
                long min = Math.min(j, commonSkip.bufferField.size());
                commonSkip.bufferField.skip(min);
                j -= min;
            }
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final long commonIndexOf(@NotNull RealBufferedSource commonIndexOf, byte b, long j, long j2) {
        Intrinsics.checkParameterIsNotNull(commonIndexOf, "$this$commonIndexOf");
        boolean z = true;
        if (!commonIndexOf.closed) {
            if (0 > j || j2 < j) {
                z = false;
            }
            if (z) {
                while (j < j2) {
                    long indexOf = commonIndexOf.bufferField.indexOf(b, j, j2);
                    if (indexOf != -1) {
                        return indexOf;
                    }
                    long size = commonIndexOf.bufferField.size();
                    if (size >= j2 || commonIndexOf.source.read(commonIndexOf.bufferField, 8192) == -1) {
                        return -1L;
                    }
                    j = Math.max(j, size);
                }
                return -1L;
            }
            throw new IllegalArgumentException(("fromIndex=" + j + " toIndex=" + j2).toString());
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final long commonIndexOf(@NotNull RealBufferedSource commonIndexOf, @NotNull ByteString bytes, long j) {
        Intrinsics.checkParameterIsNotNull(commonIndexOf, "$this$commonIndexOf");
        Intrinsics.checkParameterIsNotNull(bytes, "bytes");
        if (!commonIndexOf.closed) {
            while (true) {
                long indexOf = commonIndexOf.bufferField.indexOf(bytes, j);
                if (indexOf != -1) {
                    return indexOf;
                }
                long size = commonIndexOf.bufferField.size();
                if (commonIndexOf.source.read(commonIndexOf.bufferField, 8192) == -1) {
                    return -1L;
                }
                j = Math.max(j, (size - bytes.size()) + 1);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public static final long commonIndexOfElement(@NotNull RealBufferedSource commonIndexOfElement, @NotNull ByteString targetBytes, long j) {
        Intrinsics.checkParameterIsNotNull(commonIndexOfElement, "$this$commonIndexOfElement");
        Intrinsics.checkParameterIsNotNull(targetBytes, "targetBytes");
        if (!commonIndexOfElement.closed) {
            while (true) {
                long indexOfElement = commonIndexOfElement.bufferField.indexOfElement(targetBytes, j);
                if (indexOfElement != -1) {
                    return indexOfElement;
                }
                long size = commonIndexOfElement.bufferField.size();
                if (commonIndexOfElement.source.read(commonIndexOfElement.bufferField, 8192) == -1) {
                    return -1L;
                }
                j = Math.max(j, size);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public static final boolean commonRangeEquals(@NotNull RealBufferedSource commonRangeEquals, long j, @NotNull ByteString bytes, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(bytes, "bytes");
        if (!(!commonRangeEquals.closed)) {
            throw new IllegalStateException("closed".toString());
        } else if (j < 0 || i < 0 || i2 < 0 || bytes.size() - i < i2) {
            return false;
        } else {
            for (int i3 = 0; i3 < i2; i3++) {
                long j2 = i3 + j;
                if (!(commonRangeEquals.request(1 + j2) && commonRangeEquals.bufferField.getByte(j2) == bytes.getByte(i + i3))) {
                    return false;
                }
            }
            return true;
        }
    }

    @NotNull
    public static final BufferedSource commonPeek(@NotNull RealBufferedSource commonPeek) {
        Intrinsics.checkParameterIsNotNull(commonPeek, "$this$commonPeek");
        return Okio.buffer(new PeekSource(commonPeek));
    }

    public static final void commonClose(@NotNull RealBufferedSource commonClose) {
        Intrinsics.checkParameterIsNotNull(commonClose, "$this$commonClose");
        if (!commonClose.closed) {
            commonClose.closed = true;
            commonClose.source.close();
            commonClose.bufferField.clear();
        }
    }

    @NotNull
    public static final Timeout commonTimeout(@NotNull RealBufferedSource commonTimeout) {
        Intrinsics.checkParameterIsNotNull(commonTimeout, "$this$commonTimeout");
        return commonTimeout.source.timeout();
    }

    @NotNull
    public static final String commonToString(@NotNull RealBufferedSource commonToString) {
        Intrinsics.checkParameterIsNotNull(commonToString, "$this$commonToString");
        return "buffer(" + commonToString.source + ')';
    }

    @NotNull
    public static final ByteString commonReadByteString(@NotNull RealBufferedSource commonReadByteString) {
        Intrinsics.checkParameterIsNotNull(commonReadByteString, "$this$commonReadByteString");
        commonReadByteString.bufferField.writeAll(commonReadByteString.source);
        return commonReadByteString.bufferField.readByteString();
    }

    @NotNull
    public static final byte[] commonReadByteArray(@NotNull RealBufferedSource commonReadByteArray) {
        Intrinsics.checkParameterIsNotNull(commonReadByteArray, "$this$commonReadByteArray");
        commonReadByteArray.bufferField.writeAll(commonReadByteArray.source);
        return commonReadByteArray.bufferField.readByteArray();
    }

    @NotNull
    public static final String commonReadUtf8(@NotNull RealBufferedSource commonReadUtf8) {
        Intrinsics.checkParameterIsNotNull(commonReadUtf8, "$this$commonReadUtf8");
        commonReadUtf8.bufferField.writeAll(commonReadUtf8.source);
        return commonReadUtf8.bufferField.readUtf8();
    }
}
