package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

/* compiled from: Zip.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0010\u0000\u001a\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003H\n"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R"}, k = 3, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
final class FlowKt__ZipKt$combine$5$1 extends Lambda implements Function0<T[]> {
    final /* synthetic */ Flow<T>[] $flows;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__ZipKt$combine$5$1(Flow<? extends T>[] flowArr) {
        super(0);
        this.$flows = flowArr;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [T[], java.lang.Object[]] */
    @Override // kotlin.jvm.functions.Function0
    @Nullable
    public final T[] invoke() {
        int length = this.$flows.length;
        Intrinsics.reifiedOperationMarker(0, "T?");
        return new Object[length];
    }
}
