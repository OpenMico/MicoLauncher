package kotlinx.coroutines.flow;

import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Collect.kt */
@Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", b.p, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class StartedLazily$command$1$invokeSuspend$$inlined$collect$1 implements FlowCollector<Integer> {
    final /* synthetic */ Ref.BooleanRef a;
    final /* synthetic */ FlowCollector b;

    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1", f = "SharingStarted.kt", i = {}, l = {137}, m = "emit", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1  reason: invalid class name */
    /* loaded from: classes5.dex */
    public static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StartedLazily$command$1$invokeSuspend$$inlined$collect$1.this.emit(null, this);
        }
    }

    public StartedLazily$command$1$invokeSuspend$$inlined$collect$1(Ref.BooleanRef booleanRef, FlowCollector flowCollector) {
        this.a = booleanRef;
        this.b = flowCollector;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object emit(java.lang.Integer r4, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
        /*
            r3 = this;
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1.AnonymousClass1
            if (r0 == 0) goto L_0x0014
            r0 = r5
            kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1
            r0.<init>(r5)
        L_0x0019:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0030;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x0056
        L_0x0030:
            kotlin.ResultKt.throwOnFailure(r5)
            r5 = r0
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            java.lang.Number r4 = (java.lang.Number) r4
            int r4 = r4.intValue()
            if (r4 <= 0) goto L_0x0056
            kotlin.jvm.internal.Ref$BooleanRef r4 = r3.a
            boolean r4 = r4.element
            if (r4 != 0) goto L_0x0056
            kotlin.jvm.internal.Ref$BooleanRef r4 = r3.a
            r5 = 1
            r4.element = r5
            kotlinx.coroutines.flow.FlowCollector r4 = r3.b
            kotlinx.coroutines.flow.SharingCommand r2 = kotlinx.coroutines.flow.SharingCommand.START
            r0.label = r5
            java.lang.Object r4 = r4.emit(r2, r0)
            if (r4 != r1) goto L_0x0056
            return r1
        L_0x0056:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
