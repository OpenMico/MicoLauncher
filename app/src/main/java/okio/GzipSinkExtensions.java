package okio;

import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: GzipSink.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b¨\u0006\u0003"}, d2 = {"gzip", "Lokio/GzipSink;", "Lokio/Sink;", "okio"}, k = 2, mv = {1, 1, 16})
@JvmName(name = "-GzipSinkExtensions")
/* renamed from: okio.-GzipSinkExtensions  reason: invalid class name */
/* loaded from: classes5.dex */
public final class GzipSinkExtensions {
    @NotNull
    public static final GzipSink gzip(@NotNull Sink gzip) {
        Intrinsics.checkParameterIsNotNull(gzip, "$this$gzip");
        return new GzipSink(gzip);
    }
}
