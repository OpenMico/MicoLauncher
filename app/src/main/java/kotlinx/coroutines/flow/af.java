package kotlinx.coroutines.flow;

import com.xiaomi.idm.service.iot.LightService;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StateFlow.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0013B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u001b\u0010\u0006\u001a\u00020\u00052\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\t\u001a\u00020\bH\u0086@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ)\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\b\u0018\u00010\f0\u000b2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0004\b\r\u0010\u000eJ\r\u0010\u000f\u001a\u00020\b¢\u0006\u0004\b\u000f\u0010\u0002J\r\u0010\u0010\u001a\u00020\u0005¢\u0006\u0004\b\u0010\u0010\u0011\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/flow/StateFlowSlot;", "<init>", "()V", "Lkotlinx/coroutines/flow/StateFlowImpl;", LightService.LightPropertyCommand.FLOW, "", "allocateLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)Z", "", "awaitPending", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "Lkotlin/coroutines/Continuation;", "freeLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)[Lkotlin/coroutines/Continuation;", "makePending", "takePending", "()Z", "kotlinx-coroutines-core", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class af extends AbstractSharedFlowSlot<ae<?>> {
    static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(af.class, Object.class, "_state");
    @NotNull
    volatile /* synthetic */ Object _state = null;

    /* renamed from: a */
    public boolean allocateLocked(@NotNull ae<?> aeVar) {
        Symbol symbol;
        if (this._state != null) {
            return false;
        }
        symbol = StateFlowKt.a;
        this._state = symbol;
        return true;
    }

    @NotNull
    /* renamed from: b */
    public Continuation<Unit>[] freeLocked(@NotNull ae<?> aeVar) {
        this._state = null;
        return AbstractSharedFlowKt.EMPTY_RESUMES;
    }

    public final boolean b() {
        Symbol symbol;
        Symbol symbol2;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
        symbol = StateFlowKt.a;
        Object andSet = atomicReferenceFieldUpdater.getAndSet(this, symbol);
        Intrinsics.checkNotNull(andSet);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(!(andSet instanceof CancellableContinuationImpl))) {
                throw new AssertionError();
            }
        }
        symbol2 = StateFlowKt.b;
        return andSet == symbol2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0005, code lost:
        r1 = kotlinx.coroutines.flow.StateFlowKt.b;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a() {
        /*
            r3 = this;
        L_0x0000:
            java.lang.Object r0 = r3._state
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.flow.StateFlowKt.access$getPENDING$p()
            if (r0 != r1) goto L_0x000c
            return
        L_0x000c:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.flow.StateFlowKt.access$getNONE$p()
            if (r0 != r1) goto L_0x001f
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = kotlinx.coroutines.flow.af.a
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.StateFlowKt.access$getPENDING$p()
            boolean r0 = r1.compareAndSet(r3, r0, r2)
            if (r0 == 0) goto L_0x0000
            return
        L_0x001f:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = kotlinx.coroutines.flow.af.a
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.StateFlowKt.access$getNONE$p()
            boolean r1 = r1.compareAndSet(r3, r0, r2)
            if (r1 == 0) goto L_0x0000
            kotlinx.coroutines.CancellableContinuationImpl r0 = (kotlinx.coroutines.CancellableContinuationImpl) r0
            kotlin.coroutines.Continuation r0 = (kotlin.coroutines.Continuation) r0
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r1 = kotlin.Result.m1220constructorimpl(r1)
            r0.resumeWith(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.af.a():void");
    }

    @Nullable
    public final Object a(@NotNull Continuation<? super Unit> continuation) {
        Symbol symbol;
        Symbol symbol2;
        boolean z = true;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!Boxing.boxBoolean(!(this._state instanceof CancellableContinuationImpl)).booleanValue()) {
                throw new AssertionError();
            }
        }
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
        symbol = StateFlowKt.a;
        if (!atomicReferenceFieldUpdater.compareAndSet(this, symbol, cancellableContinuationImpl2)) {
            if (DebugKt.getASSERTIONS_ENABLED()) {
                Object obj = this._state;
                symbol2 = StateFlowKt.b;
                if (obj != symbol2) {
                    z = false;
                }
                if (!Boxing.boxBoolean(z).booleanValue()) {
                    throw new AssertionError();
                }
            }
            Unit unit = Unit.INSTANCE;
            Result.Companion companion = Result.Companion;
            cancellableContinuationImpl2.resumeWith(Result.m1220constructorimpl(unit));
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }
}
