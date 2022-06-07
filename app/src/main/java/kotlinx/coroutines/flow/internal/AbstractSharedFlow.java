package kotlinx.coroutines.flow.internal;

import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractSharedFlow.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00060\u0003j\u0002`\u0004B\u0005¢\u0006\u0002\u0010\u0005J\r\u0010\u0018\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0019J\r\u0010\u001a\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000e2\u0006\u0010\u001c\u001a\u00020\bH$¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001f0!H\u0084\bJ\u0015\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010$R\u0016\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b@BX\u0084\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R:\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000e2\u0010\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000e@BX\u0084\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u0012\u0004\b\u0010\u0010\u0005\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006%"}, d2 = {"Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", ExifInterface.LATITUDE_SOUTH, "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "()V", "_subscriptionCount", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "<set-?>", "nCollectors", "getNCollectors", "()I", "nextIndex", "", "slots", "getSlots$annotations", "getSlots", "()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "allocateSlot", "()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "forEachSlotLocked", "", "block", "Lkotlin/Function1;", "freeSlot", "slot", "(Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>> {
    @Nullable
    private S[] a;
    private int b;
    private int c;
    @Nullable
    private MutableStateFlow<Integer> d;

    protected static /* synthetic */ void getSlots$annotations() {
    }

    @NotNull
    protected abstract S createSlot();

    @NotNull
    protected abstract S[] createSlotArray(int i);

    @Nullable
    protected final S[] getSlots() {
        return this.a;
    }

    protected final int getNCollectors() {
        return this.b;
    }

    protected final void forEachSlotLocked(@NotNull Function1<? super S, Unit> function1) {
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        if (!(this.b == 0 || (abstractSharedFlowSlotArr = this.a) == null)) {
            for (AbstractSharedFlowSlot abstractSharedFlowSlot : abstractSharedFlowSlotArr) {
                if (abstractSharedFlowSlot != null) {
                    function1.invoke(abstractSharedFlowSlot);
                }
            }
        }
    }

    @NotNull
    public final StateFlow<Integer> getSubscriptionCount() {
        MutableStateFlow<Integer> mutableStateFlow;
        synchronized (this) {
            mutableStateFlow = this.d;
            if (mutableStateFlow == null) {
                mutableStateFlow = StateFlowKt.MutableStateFlow(Integer.valueOf(getNCollectors()));
                this.d = mutableStateFlow;
            }
        }
        return mutableStateFlow;
    }

    @NotNull
    protected final S allocateSlot() {
        S s;
        MutableStateFlow<Integer> mutableStateFlow;
        synchronized (this) {
            S[] slots = getSlots();
            if (slots == null) {
                slots = createSlotArray(2);
                this.a = slots;
            } else if (getNCollectors() >= slots.length) {
                Object[] copyOf = Arrays.copyOf(slots, slots.length * 2);
                Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, newSize)");
                this.a = (S[]) ((AbstractSharedFlowSlot[]) copyOf);
                slots = (S[]) ((AbstractSharedFlowSlot[]) copyOf);
            }
            int i = this.c;
            do {
                s = slots[i];
                if (s == null) {
                    s = createSlot();
                    slots[i] = s;
                }
                i++;
                if (i >= slots.length) {
                    i = 0;
                }
            } while (!s.allocateLocked(this));
            this.c = i;
            this.b = getNCollectors() + 1;
            mutableStateFlow = this.d;
        }
        if (mutableStateFlow != null) {
            StateFlowKt.increment(mutableStateFlow, 1);
        }
        return s;
    }

    protected final void freeSlot(@NotNull S s) {
        MutableStateFlow<Integer> mutableStateFlow;
        int i;
        Continuation<Unit>[] freeLocked;
        synchronized (this) {
            this.b = getNCollectors() - 1;
            mutableStateFlow = this.d;
            i = 0;
            if (getNCollectors() == 0) {
                this.c = 0;
            }
            freeLocked = s.freeLocked(this);
        }
        int length = freeLocked.length;
        while (i < length) {
            Continuation<Unit> continuation = freeLocked[i];
            i++;
            if (continuation != null) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                continuation.resumeWith(Result.m1220constructorimpl(unit));
            }
        }
        if (mutableStateFlow != null) {
            StateFlowKt.increment(mutableStateFlow, -1);
        }
    }
}
