package androidx.activity.contextaware;

import kotlin.Metadata;

/* compiled from: ContextAware.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00010\u0004H\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007"}, d2 = {"withContextAvailable", "R", "Landroidx/activity/contextaware/ContextAware;", "onContextAvailable", "Lkotlin/Function1;", "Landroid/content/Context;", "(Landroidx/activity/contextaware/ContextAware;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "activity-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ContextAwareKt {
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$listener$1] */
    /* JADX WARN: Unknown variable types count: 1 */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <R> java.lang.Object withContextAvailable(@org.jetbrains.annotations.NotNull androidx.activity.contextaware.ContextAware r3, @org.jetbrains.annotations.NotNull final kotlin.jvm.functions.Function1<? super android.content.Context, ? extends R> r4, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super R> r5) {
        /*
            android.content.Context r0 = r3.peekAvailableContext()
            if (r0 == 0) goto L_0x000b
            java.lang.Object r3 = r4.invoke(r0)
            return r3
        L_0x000b:
            kotlinx.coroutines.CancellableContinuationImpl r0 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r5)
            r2 = 1
            r0.<init>(r1, r2)
            r0.initCancellability()
            r1 = r0
            kotlinx.coroutines.CancellableContinuation r1 = (kotlinx.coroutines.CancellableContinuation) r1
            androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$listener$1 r2 = new androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$listener$1
            r2.<init>()
            r4 = r2
            androidx.activity.contextaware.OnContextAvailableListener r4 = (androidx.activity.contextaware.OnContextAvailableListener) r4
            r3.addOnContextAvailableListener(r4)
            androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$1 r4 = new androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$1
            r4.<init>(r3, r2)
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            r1.invokeOnCancellation(r4)
            java.lang.Object r3 = r0.getResult()
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r3 != r4) goto L_0x003d
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r5)
        L_0x003d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.activity.contextaware.ContextAwareKt.withContextAvailable(androidx.activity.contextaware.ContextAware, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
