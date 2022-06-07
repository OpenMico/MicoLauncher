package kotlinx.coroutines.flow.internal;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlinx.coroutines.flow.FlowCollector;
import org.jetbrains.annotations.NotNull;

/* compiled from: FlowExceptions.common.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0000¨\u0006\b"}, d2 = {"checkIndexOverflow", "", MiSoundBoxCommandExtras.INDEX, "checkOwnership", "", "Lkotlinx/coroutines/flow/internal/AbortFlowException;", "owner", "Lkotlinx/coroutines/flow/FlowCollector;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class FlowExceptions_commonKt {
    public static final void checkOwnership(@NotNull AbortFlowException abortFlowException, @NotNull FlowCollector<?> flowCollector) {
        if (abortFlowException.getOwner() != flowCollector) {
            throw abortFlowException;
        }
    }

    @PublishedApi
    public static final int checkIndexOverflow(int i) {
        if (i >= 0) {
            return i;
        }
        throw new ArithmeticException("Index overflow has happened");
    }
}
