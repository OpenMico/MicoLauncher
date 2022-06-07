package kotlin.io;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.n;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileReadWrite.kt */
@Metadata(d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001aB\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\u000f\n\u0006\b\u0011(+0\u0001\n\u0005\b\u009920\u0001¨\u00061"}, d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k = 5, mv = {1, 5, 1}, xi = 1, xs = "kotlin/io/FilesKt")
/* loaded from: classes5.dex */
class c extends b {
    @NotNull
    public static final byte[] readBytes(@NotNull File readBytes) {
        Intrinsics.checkNotNullParameter(readBytes, "$this$readBytes");
        FileInputStream fileInputStream = new FileInputStream(readBytes);
        Throwable th = null;
        try {
            FileInputStream fileInputStream2 = fileInputStream;
            long length = readBytes.length();
            if (length <= Integer.MAX_VALUE) {
                int i = (int) length;
                byte[] bArr = new byte[i];
                int i2 = 0;
                while (i > 0) {
                    int read = fileInputStream2.read(bArr, i2, i);
                    if (read < 0) {
                        break;
                    }
                    i -= read;
                    i2 += read;
                }
                if (i > 0) {
                    bArr = Arrays.copyOf(bArr, i2);
                    Intrinsics.checkNotNullExpressionValue(bArr, "java.util.Arrays.copyOf(this, newSize)");
                } else {
                    int read2 = fileInputStream2.read();
                    if (read2 != -1) {
                        a aVar = new a(n.a.p);
                        aVar.write(read2);
                        ByteStreamsKt.copyTo$default(fileInputStream2, aVar, 0, 2, null);
                        int length2 = bArr.length + aVar.size();
                        if (length2 >= 0) {
                            byte[] a2 = aVar.a();
                            byte[] copyOf = Arrays.copyOf(bArr, length2);
                            Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, newSize)");
                            bArr = ArraysKt.copyInto(a2, copyOf, bArr.length, 0, aVar.size());
                        } else {
                            throw new OutOfMemoryError("File " + readBytes + " is too big to fit in memory.");
                        }
                    }
                }
                return bArr;
            }
            throw new OutOfMemoryError("File " + readBytes + " is too big (" + length + " bytes) to fit in memory.");
        } finally {
            CloseableKt.closeFinally(fileInputStream, th);
        }
    }

    public static final void writeBytes(@NotNull File writeBytes, @NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(writeBytes, "$this$writeBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        FileOutputStream fileOutputStream = new FileOutputStream(writeBytes);
        Throwable th = null;
        try {
            fileOutputStream.write(array);
            Unit unit = Unit.INSTANCE;
        } finally {
            CloseableKt.closeFinally(fileOutputStream, th);
        }
    }

    public static final void appendBytes(@NotNull File appendBytes, @NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(appendBytes, "$this$appendBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        FileOutputStream fileOutputStream = new FileOutputStream(appendBytes, true);
        Throwable th = null;
        try {
            fileOutputStream.write(array);
            Unit unit = Unit.INSTANCE;
        } finally {
            CloseableKt.closeFinally(fileOutputStream, th);
        }
    }

    @NotNull
    public static final String readText(@NotNull File readText, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(readText, "$this$readText");
        Intrinsics.checkNotNullParameter(charset, "charset");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(readText), charset);
        Throwable th = null;
        try {
            return TextStreamsKt.readText(inputStreamReader);
        } finally {
            CloseableKt.closeFinally(inputStreamReader, th);
        }
    }

    public static /* synthetic */ String readText$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readText(file, charset);
    }

    public static final void writeText(@NotNull File writeText, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(writeText, "$this$writeText");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
        FilesKt.writeBytes(writeText, bytes);
    }

    public static /* synthetic */ void writeText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, str, charset);
    }

    public static final void appendText(@NotNull File appendText, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(appendText, "$this$appendText");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
        FilesKt.appendBytes(appendText, bytes);
    }

    public static /* synthetic */ void appendText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, str, charset);
    }

    public static final void forEachBlock(@NotNull File forEachBlock, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(forEachBlock, "$this$forEachBlock");
        Intrinsics.checkNotNullParameter(action, "action");
        FilesKt.forEachBlock(forEachBlock, 4096, action);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [byte[], java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void forEachBlock(@org.jetbrains.annotations.NotNull java.io.File r3, int r4, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super byte[], ? super java.lang.Integer, kotlin.Unit> r5) {
        /*
            java.lang.String r0 = "$this$forEachBlock"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            java.lang.String r0 = "action"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            r0 = 512(0x200, float:7.175E-43)
            int r4 = kotlin.ranges.RangesKt.coerceAtLeast(r4, r0)
            byte[] r4 = new byte[r4]
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r3)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r3 = 0
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r1 = r0
            java.io.FileInputStream r1 = (java.io.FileInputStream) r1     // Catch: Throwable -> 0x0035, all -> 0x0033
        L_0x001f:
            int r2 = r1.read(r4)     // Catch: Throwable -> 0x0035, all -> 0x0033
            if (r2 > 0) goto L_0x002b
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x0035, all -> 0x0033
            kotlin.io.CloseableKt.closeFinally(r0, r3)
            return
        L_0x002b:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: Throwable -> 0x0035, all -> 0x0033
            r5.invoke(r4, r2)     // Catch: Throwable -> 0x0035, all -> 0x0033
            goto L_0x001f
        L_0x0033:
            r4 = move-exception
            goto L_0x0037
        L_0x0035:
            r3 = move-exception
            throw r3     // Catch: all -> 0x0033
        L_0x0037:
            kotlin.io.CloseableKt.closeFinally(r0, r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.c.forEachBlock(java.io.File, int, kotlin.jvm.functions.Function2):void");
    }

    public static /* synthetic */ void forEachLine$default(File file, Charset charset, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    public static final void forEachLine(@NotNull File forEachLine, @NotNull Charset charset, @NotNull Function1<? super String, Unit> action) {
        Intrinsics.checkNotNullParameter(forEachLine, "$this$forEachLine");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(action, "action");
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader(new FileInputStream(forEachLine), charset)), action);
    }

    public static /* synthetic */ List readLines$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readLines(file, charset);
    }

    /* compiled from: FileReadWrite.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    static final class a extends Lambda implements Function1<String, Unit> {
        final /* synthetic */ ArrayList $result;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(ArrayList arrayList) {
            super(1);
            this.$result = arrayList;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(String str) {
            a(str);
            return Unit.INSTANCE;
        }

        public final void a(@NotNull String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            this.$result.add(it);
        }
    }

    @NotNull
    public static final List<String> readLines(@NotNull File readLines, @NotNull Charset charset) {
        Intrinsics.checkNotNullParameter(readLines, "$this$readLines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        ArrayList arrayList = new ArrayList();
        FilesKt.forEachLine(readLines, charset, new a(arrayList));
        return arrayList;
    }

    public static /* synthetic */ Object useLines$default(File useLines, Charset charset, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(useLines, "$this$useLines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(block, "block");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(useLines), charset);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = null;
        try {
            Object invoke = block.invoke(TextStreamsKt.lineSequence(bufferedReader));
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th);
            } else {
                bufferedReader.close();
            }
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th);
            } else if (th == null) {
                bufferedReader.close();
            } else {
                try {
                    bufferedReader.close();
                } catch (Throwable unused) {
                }
            }
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    public static final <T> T useLines(@NotNull File useLines, @NotNull Charset charset, @NotNull Function1<? super Sequence<String>, ? extends T> block) {
        Intrinsics.checkNotNullParameter(useLines, "$this$useLines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(block, "block");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(useLines), charset);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = null;
        try {
            T t = (T) block.invoke(TextStreamsKt.lineSequence(bufferedReader));
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th);
            } else {
                bufferedReader.close();
            }
            InlineMarker.finallyEnd(1);
            return t;
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th);
            } else if (th == null) {
                bufferedReader.close();
            } else {
                try {
                    bufferedReader.close();
                } catch (Throwable unused) {
                }
            }
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }
}
