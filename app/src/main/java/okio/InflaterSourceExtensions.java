package okio;

import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: InflaterSource.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086\b¨\u0006\u0005"}, d2 = {"inflate", "Lokio/InflaterSource;", "Lokio/Source;", "inflater", "Ljava/util/zip/Inflater;", "okio"}, k = 2, mv = {1, 1, 16})
@JvmName(name = "-InflaterSourceExtensions")
/* renamed from: okio.-InflaterSourceExtensions  reason: invalid class name */
/* loaded from: classes5.dex */
public final class InflaterSourceExtensions {
    public static /* synthetic */ InflaterSource inflate$default(Source inflate, Inflater inflater, int i, Object obj) {
        if ((i & 1) != 0) {
            inflater = new Inflater();
        }
        Intrinsics.checkParameterIsNotNull(inflate, "$this$inflate");
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return new InflaterSource(inflate, inflater);
    }

    @NotNull
    public static final InflaterSource inflate(@NotNull Source inflate, @NotNull Inflater inflater) {
        Intrinsics.checkParameterIsNotNull(inflate, "$this$inflate");
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return new InflaterSource(inflate, inflater);
    }
}
