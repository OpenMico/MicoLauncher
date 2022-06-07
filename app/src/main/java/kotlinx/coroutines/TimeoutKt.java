package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import io.netty.handler.codec.rtsp.RtspHeaders;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.time.ExperimentalTime;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Timeout.kt */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a_\u0010\u0006\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\b\"\b\b\u0001\u0010\t*\u0002H\b2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\n2'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001aU\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0086@ø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\u0013\u001a]\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0087@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0004\b\u0016\u0010\u0013\u001aJ\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aR\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0087@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0013\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0019"}, d2 = {"TimeoutCancellationException", "Lkotlinx/coroutines/TimeoutCancellationException;", "time", "", "coroutine", "Lkotlinx/coroutines/Job;", "setupTimeout", "", "U", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/TimeoutCoroutine;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "withTimeout", "timeMillis", "(JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", RtspHeaders.Values.TIMEOUT, "Lkotlin/time/Duration;", "withTimeout-KLykuaI", "withTimeoutOrNull", "withTimeoutOrNull-KLykuaI", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class TimeoutKt {

    /* compiled from: Timeout.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.TimeoutKt", f = "Timeout.kt", i = {0}, l = {101}, m = "withTimeoutOrNull", n = {"coroutine"}, s = {"L$1"})
    /* loaded from: classes5.dex */
    public static final class a<T> extends ContinuationImpl {
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        a(Continuation<? super a> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TimeoutKt.withTimeoutOrNull(0L, null, this);
        }
    }

    @Nullable
    public static final <T> Object withTimeout(long j, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        if (j > 0) {
            Object a2 = a(new ah(j, continuation), function2);
            if (a2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return a2;
        }
        throw new TimeoutCancellationException("Timed out immediately");
    }

    @ExperimentalTime
    @Nullable
    /* renamed from: withTimeout-KLykuaI */
    public static final <T> Object m1755withTimeoutKLykuaI(long j, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        return withTimeout(DelayKt.m1754toDelayMillisLRDsOJo(j), function2, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARN: Type inference failed for: r4v1, types: [kotlinx.coroutines.ah, T] */
    /* JADX WARN: Unknown variable types count: 1 */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.lang.Object withTimeoutOrNull(long r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super kotlinx.coroutines.CoroutineScope, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super T> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.TimeoutKt.a
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.TimeoutKt$a r0 = (kotlinx.coroutines.TimeoutKt.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.TimeoutKt$a r0 = new kotlinx.coroutines.TimeoutKt$a
            r0.<init>(r9)
        L_0x0019:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            long r6 = r0.J$0
            java.lang.Object r6 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref.ObjectRef) r6
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: TimeoutCancellationException -> 0x003c
            r6 = r9
            goto L_0x0073
        L_0x003c:
            r7 = move-exception
            r9 = r6
            goto L_0x0075
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r9)
            r4 = 0
            int r9 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r9 > 0) goto L_0x0049
            return r3
        L_0x0049:
            kotlin.jvm.internal.Ref$ObjectRef r9 = new kotlin.jvm.internal.Ref$ObjectRef
            r9.<init>()
            r0.L$0 = r8     // Catch: TimeoutCancellationException -> 0x0074
            r0.L$1 = r9     // Catch: TimeoutCancellationException -> 0x0074
            r0.J$0 = r6     // Catch: TimeoutCancellationException -> 0x0074
            r2 = 1
            r0.label = r2     // Catch: TimeoutCancellationException -> 0x0074
            r2 = r0
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2     // Catch: TimeoutCancellationException -> 0x0074
            kotlinx.coroutines.ah r4 = new kotlinx.coroutines.ah     // Catch: TimeoutCancellationException -> 0x0074
            r4.<init>(r6, r2)     // Catch: TimeoutCancellationException -> 0x0074
            r9.element = r4     // Catch: TimeoutCancellationException -> 0x0074
            java.lang.Object r6 = a(r4, r8)     // Catch: TimeoutCancellationException -> 0x0074
            java.lang.Object r7 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch: TimeoutCancellationException -> 0x0074
            if (r6 != r7) goto L_0x0070
            kotlin.coroutines.Continuation r0 = (kotlin.coroutines.Continuation) r0     // Catch: TimeoutCancellationException -> 0x0074
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r0)     // Catch: TimeoutCancellationException -> 0x0074
        L_0x0070:
            if (r6 != r1) goto L_0x0073
            return r1
        L_0x0073:
            return r6
        L_0x0074:
            r7 = move-exception
        L_0x0075:
            kotlinx.coroutines.Job r6 = r7.coroutine
            T r8 = r9.element
            if (r6 != r8) goto L_0x007c
            return r3
        L_0x007c:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(long, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @ExperimentalTime
    @Nullable
    /* renamed from: withTimeoutOrNull-KLykuaI */
    public static final <T> Object m1756withTimeoutOrNullKLykuaI(long j, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        return withTimeoutOrNull(DelayKt.m1754toDelayMillisLRDsOJo(j), function2, continuation);
    }

    private static final <U, T extends U> Object a(ah<U, ? super T> ahVar, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        JobKt.disposeOnCompletion(ahVar, DelayKt.getDelay(ahVar.uCont.getContext()).invokeOnTimeout(ahVar.a, ahVar, ahVar.getContext()));
        return UndispatchedKt.startUndispatchedOrReturnIgnoreTimeout(ahVar, ahVar, function2);
    }

    @NotNull
    public static final TimeoutCancellationException TimeoutCancellationException(long j, @NotNull Job job) {
        return new TimeoutCancellationException("Timed out waiting for " + j + " ms", job);
    }
}
