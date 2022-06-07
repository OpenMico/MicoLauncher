package okio;

import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;

/* compiled from: Okio.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0007\u001a\n\u0010\b\u001a\u00020\u0006*\u00020\t\u001a\n\u0010\n\u001a\u00020\u000b*\u00020\u0006\u001a\n\u0010\n\u001a\u00020\f*\u00020\r\u001a\u0016\u0010\u000e\u001a\u00020\u0006*\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\u0001H\u0007\u001a\n\u0010\u000e\u001a\u00020\u0006*\u00020\u0010\u001a\n\u0010\u000e\u001a\u00020\u0006*\u00020\u0011\u001a%\u0010\u000e\u001a\u00020\u0006*\u00020\u00122\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014\"\u00020\u0015H\u0007¢\u0006\u0002\u0010\u0016\u001a\n\u0010\u0017\u001a\u00020\r*\u00020\t\u001a\n\u0010\u0017\u001a\u00020\r*\u00020\u0018\u001a\n\u0010\u0017\u001a\u00020\r*\u00020\u0011\u001a%\u0010\u0017\u001a\u00020\r*\u00020\u00122\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014\"\u00020\u0015H\u0007¢\u0006\u0002\u0010\u0019\"\u001c\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0004¨\u0006\u001a"}, d2 = {"isAndroidGetsocknameError", "", "Ljava/lang/AssertionError;", "Lkotlin/AssertionError;", "(Ljava/lang/AssertionError;)Z", "blackholeSink", "Lokio/Sink;", "blackhole", "appendingSink", "Ljava/io/File;", "buffer", "Lokio/BufferedSink;", "Lokio/BufferedSource;", "Lokio/Source;", "sink", RtspHeaders.Values.APPEND, "Ljava/io/OutputStream;", "Ljava/net/Socket;", "Ljava/nio/file/Path;", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Sink;", "source", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Source;", "okio"}, k = 2, mv = {1, 1, 16})
@JvmName(name = "Okio")
/* loaded from: classes5.dex */
public final class Okio {
    @JvmOverloads
    @NotNull
    public static final Sink sink(@NotNull File file) throws FileNotFoundException {
        return sink$default(file, false, 1, null);
    }

    @NotNull
    public static final BufferedSource buffer(@NotNull Source buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$buffer");
        return new RealBufferedSource(buffer);
    }

    @NotNull
    public static final BufferedSink buffer(@NotNull Sink buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$buffer");
        return new RealBufferedSink(buffer);
    }

    @NotNull
    public static final Sink sink(@NotNull OutputStream sink) {
        Intrinsics.checkParameterIsNotNull(sink, "$this$sink");
        return new OutputStreamSink(sink, new Timeout());
    }

    @NotNull
    public static final Source source(@NotNull InputStream source) {
        Intrinsics.checkParameterIsNotNull(source, "$this$source");
        return new InputStreamSource(source, new Timeout());
    }

    @JvmName(name = "blackhole")
    @NotNull
    public static final Sink blackhole() {
        return new BlackholeSink();
    }

    @NotNull
    public static final Sink sink(@NotNull Socket sink) throws IOException {
        Intrinsics.checkParameterIsNotNull(sink, "$this$sink");
        SocketAsyncTimeout socketAsyncTimeout = new SocketAsyncTimeout(sink);
        OutputStream outputStream = sink.getOutputStream();
        Intrinsics.checkExpressionValueIsNotNull(outputStream, "getOutputStream()");
        return socketAsyncTimeout.sink(new OutputStreamSink(outputStream, socketAsyncTimeout));
    }

    @NotNull
    public static final Source source(@NotNull Socket source) throws IOException {
        Intrinsics.checkParameterIsNotNull(source, "$this$source");
        SocketAsyncTimeout socketAsyncTimeout = new SocketAsyncTimeout(source);
        InputStream inputStream = source.getInputStream();
        Intrinsics.checkExpressionValueIsNotNull(inputStream, "getInputStream()");
        return socketAsyncTimeout.source(new InputStreamSource(inputStream, socketAsyncTimeout));
    }

    @JvmOverloads
    @NotNull
    public static final Sink sink(@NotNull File sink, boolean z) throws FileNotFoundException {
        Intrinsics.checkParameterIsNotNull(sink, "$this$sink");
        return sink(new FileOutputStream(sink, z));
    }

    public static /* synthetic */ Sink sink$default(File file, boolean z, int i, Object obj) throws FileNotFoundException {
        if ((i & 1) != 0) {
            z = false;
        }
        return sink(file, z);
    }

    @NotNull
    public static final Sink appendingSink(@NotNull File appendingSink) throws FileNotFoundException {
        Intrinsics.checkParameterIsNotNull(appendingSink, "$this$appendingSink");
        return sink(new FileOutputStream(appendingSink, true));
    }

    @NotNull
    public static final Source source(@NotNull File source) throws FileNotFoundException {
        Intrinsics.checkParameterIsNotNull(source, "$this$source");
        return source(new FileInputStream(source));
    }

    @IgnoreJRERequirement
    @NotNull
    public static final Sink sink(@NotNull Path sink, @NotNull OpenOption... options) throws IOException {
        Intrinsics.checkParameterIsNotNull(sink, "$this$sink");
        Intrinsics.checkParameterIsNotNull(options, "options");
        OutputStream newOutputStream = Files.newOutputStream(sink, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkExpressionValueIsNotNull(newOutputStream, "Files.newOutputStream(this, *options)");
        return sink(newOutputStream);
    }

    @IgnoreJRERequirement
    @NotNull
    public static final Source source(@NotNull Path source, @NotNull OpenOption... options) throws IOException {
        Intrinsics.checkParameterIsNotNull(source, "$this$source");
        Intrinsics.checkParameterIsNotNull(options, "options");
        InputStream newInputStream = Files.newInputStream(source, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkExpressionValueIsNotNull(newInputStream, "Files.newInputStream(this, *options)");
        return source(newInputStream);
    }

    public static final boolean isAndroidGetsocknameError(@NotNull AssertionError isAndroidGetsocknameError) {
        Intrinsics.checkParameterIsNotNull(isAndroidGetsocknameError, "$this$isAndroidGetsocknameError");
        if (isAndroidGetsocknameError.getCause() == null) {
            return false;
        }
        String message = isAndroidGetsocknameError.getMessage();
        return message != null ? StringsKt.contains$default((CharSequence) message, (CharSequence) "getsockname failed", false, 2, (Object) null) : false;
    }
}
