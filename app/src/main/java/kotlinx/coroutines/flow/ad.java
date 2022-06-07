package kotlinx.coroutines.flow;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SharingStarted.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/flow/StartedWhileSubscribed;", "Lkotlinx/coroutines/flow/SharingStarted;", "stopTimeout", "", "replayExpiration", "(JJ)V", "command", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharingCommand;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "equals", "", "other", "", "hashCode", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ad implements SharingStarted {
    private final long a;
    private final long b;

    public ad(long j, long j2) {
        this.a = j;
        this.b = j2;
        boolean z = true;
        if (this.a >= 0) {
            if (!(this.b < 0 ? false : z)) {
                throw new IllegalArgumentException(("replayExpiration(" + this.b + " ms) cannot be negative").toString());
            }
            return;
        }
        throw new IllegalArgumentException(("stopTimeout(" + this.a + " ms) cannot be negative").toString());
    }

    /* compiled from: SharingStarted.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;", "count", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$1", f = "SharingStarted.kt", i = {1, 2, 3}, l = {179, Opcodes.PUTFIELD, Opcodes.INVOKESPECIAL, Opcodes.INVOKESTATIC, 186}, m = "invokeSuspend", n = {"$this$transformLatest", "$this$transformLatest", "$this$transformLatest"}, s = {"L$0", "L$0", "L$0"})
    /* loaded from: classes5.dex */
    static final class a extends SuspendLambda implements Function3<FlowCollector<? super SharingCommand>, Integer, Continuation<? super Unit>, Object> {
        /* synthetic */ int I$0;
        private /* synthetic */ Object L$0;
        int label;

        a(Continuation<? super a> continuation) {
            super(3, continuation);
        }

        @Nullable
        public final Object a(@NotNull FlowCollector<? super SharingCommand> flowCollector, int i, @Nullable Continuation<? super Unit> continuation) {
            a aVar = new a(continuation);
            aVar.L$0 = flowCollector;
            aVar.I$0 = i;
            return aVar.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* synthetic */ Object invoke(FlowCollector<? super SharingCommand> flowCollector, Integer num, Continuation<? super Unit> continuation) {
            return a(flowCollector, num.intValue(), continuation);
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x008f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00a1 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                switch(r1) {
                    case 0: goto L_0x002e;
                    case 1: goto L_0x002a;
                    case 2: goto L_0x0022;
                    case 3: goto L_0x001a;
                    case 4: goto L_0x0011;
                    case 5: goto L_0x002a;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L_0x0011:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x0090
            L_0x001a:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x007b
            L_0x0022:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x005e
            L_0x002a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x00a2
            L_0x002e:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                r1 = r7
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                int r7 = r6.I$0
                if (r7 <= 0) goto L_0x0049
                kotlinx.coroutines.flow.SharingCommand r7 = kotlinx.coroutines.flow.SharingCommand.START
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 1
                r6.label = r3
                java.lang.Object r7 = r1.emit(r7, r2)
                if (r7 != r0) goto L_0x00a2
                return r0
            L_0x0049:
                kotlinx.coroutines.flow.ad r7 = kotlinx.coroutines.flow.ad.this
                long r2 = kotlinx.coroutines.flow.ad.a(r7)
                r7 = r6
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r6.L$0 = r1
                r4 = 2
                r6.label = r4
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r2, r7)
                if (r7 != r0) goto L_0x005e
                return r0
            L_0x005e:
                kotlinx.coroutines.flow.ad r7 = kotlinx.coroutines.flow.ad.this
                long r2 = kotlinx.coroutines.flow.ad.b(r7)
                r4 = 0
                int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r7 <= 0) goto L_0x0090
                kotlinx.coroutines.flow.SharingCommand r7 = kotlinx.coroutines.flow.SharingCommand.STOP
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.L$0 = r1
                r3 = 3
                r6.label = r3
                java.lang.Object r7 = r1.emit(r7, r2)
                if (r7 != r0) goto L_0x007b
                return r0
            L_0x007b:
                kotlinx.coroutines.flow.ad r7 = kotlinx.coroutines.flow.ad.this
                long r2 = kotlinx.coroutines.flow.ad.b(r7)
                r7 = r6
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r6.L$0 = r1
                r4 = 4
                r6.label = r4
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r2, r7)
                if (r7 != r0) goto L_0x0090
                return r0
            L_0x0090:
                kotlinx.coroutines.flow.SharingCommand r7 = kotlinx.coroutines.flow.SharingCommand.STOP_AND_RESET_REPLAY_CACHE
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 0
                r6.L$0 = r3
                r3 = 5
                r6.label = r3
                java.lang.Object r7 = r1.emit(r7, r2)
                if (r7 != r0) goto L_0x00a2
                return r0
            L_0x00a2:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.ad.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlinx.coroutines.flow.SharingStarted
    @NotNull
    public Flow<SharingCommand> command(@NotNull StateFlow<Integer> stateFlow) {
        return FlowKt.distinctUntilChanged(FlowKt.dropWhile(FlowKt.transformLatest(stateFlow, new a(null)), new b(null)));
    }

    /* compiled from: SharingStarted.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "it", "Lkotlinx/coroutines/flow/SharingCommand;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$2", f = "SharingStarted.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    static final class b extends SuspendLambda implements Function2<SharingCommand, Continuation<? super Boolean>, Object> {
        /* synthetic */ Object L$0;
        int label;

        b(Continuation<? super b> continuation) {
            super(2, continuation);
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull SharingCommand sharingCommand, @Nullable Continuation<? super Boolean> continuation) {
            return ((b) create(sharingCommand, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            b bVar = new b(continuation);
            bVar.L$0 = obj;
            return bVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return Boxing.boxBoolean(((SharingCommand) this.L$0) != SharingCommand.START);
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @NotNull
    public String toString() {
        List createListBuilder = CollectionsKt.createListBuilder(2);
        if (this.a > 0) {
            createListBuilder.add("stopTimeout=" + this.a + "ms");
        }
        if (this.b < Long.MAX_VALUE) {
            createListBuilder.add("replayExpiration=" + this.b + "ms");
        }
        List build = CollectionsKt.build(createListBuilder);
        return "SharingStarted.WhileSubscribed(" + CollectionsKt.joinToString$default(build, null, null, null, 0, null, null, 63, null) + ')';
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ad) {
            ad adVar = (ad) obj;
            if (this.a == adVar.a && this.b == adVar.b) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Long.hashCode(this.a) * 31) + Long.hashCode(this.b);
    }
}
