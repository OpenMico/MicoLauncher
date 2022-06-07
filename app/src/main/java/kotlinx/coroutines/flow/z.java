package kotlinx.coroutines.flow;

import com.xiaomi.idm.service.iot.LightService;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.JvmField;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SharedFlow.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0014\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016J'\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00050\r2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016¢\u0006\u0002\u0010\u000eR\u001a\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "()V", "cont", "Lkotlin/coroutines/Continuation;", "", MiSoundBoxCommandExtras.INDEX, "", "allocateLocked", "", LightService.LightPropertyCommand.FLOW, "freeLocked", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;)[Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class z extends AbstractSharedFlowSlot<SharedFlowImpl<?>> {
    @JvmField
    public long a = -1;
    @JvmField
    @Nullable
    public Continuation<? super Unit> b;

    /* renamed from: a */
    public boolean allocateLocked(@NotNull SharedFlowImpl<?> sharedFlowImpl) {
        if (this.a >= 0) {
            return false;
        }
        this.a = sharedFlowImpl.a();
        return true;
    }

    @NotNull
    /* renamed from: b */
    public Continuation<Unit>[] freeLocked(@NotNull SharedFlowImpl<?> sharedFlowImpl) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(this.a >= 0)) {
                throw new AssertionError();
            }
        }
        long j = this.a;
        this.a = -1L;
        this.b = null;
        return sharedFlowImpl.a(j);
    }
}
