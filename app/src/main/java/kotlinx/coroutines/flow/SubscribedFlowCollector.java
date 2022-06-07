package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Share.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002BE\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012-\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\nJ\u0019\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000H\u0096Aø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0011\u0010\u000f\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0010R:\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tX\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/flow/SubscribedFlowCollector;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "action", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "emit", b.p, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onSubscription", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class SubscribedFlowCollector<T> implements FlowCollector<T> {
    @NotNull
    private final FlowCollector<T> a;
    @NotNull
    private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> b;

    /* compiled from: Share.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.SubscribedFlowCollector", f = "Share.kt", i = {0}, l = {410, 414}, m = "onSubscription", n = {"safeCollector"}, s = {"L$1"})
    /* loaded from: classes5.dex */
    public static final class a extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ SubscribedFlowCollector<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(SubscribedFlowCollector<T> subscribedFlowCollector, Continuation<? super a> continuation) {
            super(continuation);
            this.this$0 = subscribedFlowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.onSubscription(this);
        }
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    @Nullable
    public Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
        return this.a.emit(t, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SubscribedFlowCollector(@NotNull FlowCollector<? super T> flowCollector, @NotNull Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        this.a = flowCollector;
        this.b = function2;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007c  */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onSubscription(@org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector.a
            if (r0 == 0) goto L_0x0014
            r0 = r5
            kotlinx.coroutines.flow.SubscribedFlowCollector$a r0 = (kotlinx.coroutines.flow.SubscribedFlowCollector.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.SubscribedFlowCollector$a r0 = new kotlinx.coroutines.flow.SubscribedFlowCollector$a
            r0.<init>(r4, r5)
        L_0x0019:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0030;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x0079
        L_0x0030:
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.flow.internal.SafeCollector r2 = (kotlinx.coroutines.flow.internal.SafeCollector) r2
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.flow.SubscribedFlowCollector r3 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: all -> 0x003c
            goto L_0x005f
        L_0x003c:
            r5 = move-exception
            r3 = r2
            goto L_0x0080
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.flow.FlowCollector<T> r5 = r4.a
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            kotlinx.coroutines.flow.internal.SafeCollector r3 = new kotlinx.coroutines.flow.internal.SafeCollector
            r3.<init>(r5, r2)
            kotlin.jvm.functions.Function2<kotlinx.coroutines.flow.FlowCollector<? super T>, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r5 = r4.b     // Catch: all -> 0x007f
            r0.L$0 = r4     // Catch: all -> 0x007f
            r0.L$1 = r3     // Catch: all -> 0x007f
            r2 = 1
            r0.label = r2     // Catch: all -> 0x007f
            java.lang.Object r5 = r5.invoke(r3, r0)     // Catch: all -> 0x007f
            if (r5 != r1) goto L_0x005d
            return r1
        L_0x005d:
            r2 = r3
            r3 = r4
        L_0x005f:
            r2.releaseIntercepted()
            kotlinx.coroutines.flow.FlowCollector<T> r5 = r3.a
            boolean r2 = r5 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector
            if (r2 == 0) goto L_0x007c
            kotlinx.coroutines.flow.SubscribedFlowCollector r5 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r5
            r2 = 0
            r0.L$0 = r2
            r0.L$1 = r2
            r2 = 2
            r0.label = r2
            java.lang.Object r5 = r5.onSubscription(r0)
            if (r5 != r1) goto L_0x0079
            return r1
        L_0x0079:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L_0x007c:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L_0x007f:
            r5 = move-exception
        L_0x0080:
            r3.releaseIntercepted()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SubscribedFlowCollector.onSubscription(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
