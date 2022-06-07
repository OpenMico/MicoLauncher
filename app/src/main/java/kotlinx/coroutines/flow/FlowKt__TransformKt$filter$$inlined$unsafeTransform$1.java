package kotlinx.coroutines.flow;

import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SafeCollector.common.kt */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class FlowKt__TransformKt$filter$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Flow a;
    final /* synthetic */ Function2 b;

    /* compiled from: Collect.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0007"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", b.p, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2  reason: invalid class name */
    /* loaded from: classes5.dex */
    public static final class AnonymousClass2 implements FlowCollector<T> {
        final /* synthetic */ FlowCollector a;
        final /* synthetic */ Function2 b;

        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2", f = "Transform.kt", i = {0, 0}, l = {136, 136}, m = "emit", n = {b.p, "$this$filter_u24lambda_u2d0"}, s = {"L$0", "L$1"})
        /* renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1  reason: invalid class name */
        /* loaded from: classes5.dex */
        public static final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            Object L$1;
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
                return AnonymousClass2.this.emit(null, this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
        /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x003a  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Object emit(java.lang.Object r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L_0x0014
                r0 = r7
                kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r7 = r0.label
                int r7 = r7 - r2
                r0.label = r7
                goto L_0x0019
            L_0x0014:
                kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1
                r0.<init>(r7)
            L_0x0019:
                java.lang.Object r7 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                switch(r2) {
                    case 0: goto L_0x003a;
                    case 1: goto L_0x0030;
                    case 2: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L_0x002c:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x006d
            L_0x0030:
                java.lang.Object r6 = r0.L$1
                kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                java.lang.Object r2 = r0.L$0
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x0056
            L_0x003a:
                kotlin.ResultKt.throwOnFailure(r7)
                kotlinx.coroutines.flow.FlowCollector r7 = r5.a
                r2 = r0
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                kotlin.jvm.functions.Function2 r2 = r5.b
                r0.L$0 = r6
                r0.L$1 = r7
                r3 = 1
                r0.label = r3
                java.lang.Object r2 = r2.invoke(r6, r0)
                if (r2 != r1) goto L_0x0052
                return r1
            L_0x0052:
                r4 = r2
                r2 = r6
                r6 = r7
                r7 = r4
            L_0x0056:
                java.lang.Boolean r7 = (java.lang.Boolean) r7
                boolean r7 = r7.booleanValue()
                if (r7 == 0) goto L_0x006d
                r7 = 0
                r0.L$0 = r7
                r0.L$1 = r7
                r7 = 2
                r0.label = r7
                java.lang.Object r6 = r6.emit(r2, r0)
                if (r6 != r1) goto L_0x006d
                return r1
            L_0x006d:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }

        public AnonymousClass2(FlowCollector flowCollector, Function2 function2) {
            this.a = flowCollector;
            this.b = function2;
        }

        @Nullable
        public Object emit$$forInline(Object obj, @NotNull Continuation continuation) {
            InlineMarker.mark(4);
            new AnonymousClass1(continuation);
            InlineMarker.mark(5);
            FlowCollector flowCollector = this.a;
            if (((Boolean) this.b.invoke(obj, continuation)).booleanValue()) {
                InlineMarker.mark(0);
                flowCollector.emit(obj, continuation);
                InlineMarker.mark(1);
            }
            return Unit.INSTANCE;
        }
    }

    public FlowKt__TransformKt$filter$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.a = flow;
        this.b = function2;
    }

    @Override // kotlinx.coroutines.flow.Flow
    @Nullable
    public Object collect(@NotNull FlowCollector flowCollector, @NotNull Continuation continuation) {
        Object collect = this.a.collect(new AnonymousClass2(flowCollector, this.b), continuation);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    @Nullable
    public Object collect$$forInline(@NotNull FlowCollector flowCollector, @NotNull Continuation continuation) {
        InlineMarker.mark(4);
        new ContinuationImpl(continuation) { // from class: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.1
            int label;
            /* synthetic */ Object result;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.this.collect(null, this);
            }
        };
        InlineMarker.mark(5);
        InlineMarker.mark(0);
        this.a.collect(new AnonymousClass2(flowCollector, this.b), continuation);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
