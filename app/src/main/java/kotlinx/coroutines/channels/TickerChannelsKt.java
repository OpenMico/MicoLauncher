package kotlinx.coroutines.channels;

import com.umeng.analytics.pro.c;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.ObsoleteCoroutinesApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TickerChannels.kt */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a/\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a/\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"fixedDelayTicker", "", "delayMillis", "", "initialDelayMillis", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedPeriodTicker", "ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", c.R, "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class TickerChannelsKt {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TickerChannels.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.TickerChannelsKt", f = "TickerChannels.kt", i = {0, 0, 1, 1, 2, 2}, l = {106, 108, 109}, m = "fixedDelayTicker", n = {"channel", "delayMillis", "channel", "delayMillis", "channel", "delayMillis"}, s = {"L$0", "J$0", "L$0", "J$0", "L$0", "J$0"})
    /* loaded from: classes5.dex */
    public static final class a extends ContinuationImpl {
        long J$0;
        Object L$0;
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
            return TickerChannelsKt.b(0L, 0L, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TickerChannels.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.TickerChannelsKt", f = "TickerChannels.kt", i = {0, 1}, l = {84, 88, 94, 96}, m = "fixedPeriodTicker", n = {"deadline", "delayNs"}, s = {"J$1", "J$1"})
    /* loaded from: classes5.dex */
    public static final class b extends ContinuationImpl {
        long J$0;
        long J$1;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        b(Continuation<? super b> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TickerChannelsKt.a(0L, 0L, null, this);
        }
    }

    public static /* synthetic */ ReceiveChannel ticker$default(long j, long j2, CoroutineContext coroutineContext, TickerMode tickerMode, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        if ((i & 4) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 8) != 0) {
            tickerMode = TickerMode.FIXED_PERIOD;
        }
        return ticker(j, j2, coroutineContext, tickerMode);
    }

    @ObsoleteCoroutinesApi
    @NotNull
    public static final ReceiveChannel<Unit> ticker(long j, long j2, @NotNull CoroutineContext coroutineContext, @NotNull TickerMode tickerMode) {
        boolean z = true;
        if (j >= 0) {
            if (j2 < 0) {
                z = false;
            }
            if (z) {
                GlobalScope globalScope = GlobalScope.INSTANCE;
                Dispatchers dispatchers = Dispatchers.INSTANCE;
                return ProduceKt.produce(globalScope, Dispatchers.getUnconfined().plus(coroutineContext), 0, new TickerChannelsKt$ticker$3(tickerMode, j, j2, null));
            }
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0090 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00de A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object a(long r8, long r10, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r12, kotlin.coroutines.Continuation<? super kotlin.Unit> r13) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.a(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object b(long r3, long r5, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.TickerChannelsKt.a
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.TickerChannelsKt$a r0 = (kotlinx.coroutines.channels.TickerChannelsKt.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.TickerChannelsKt$a r0 = new kotlinx.coroutines.channels.TickerChannelsKt$a
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x004c;
                case 1: goto L_0x0041;
                case 2: goto L_0x0037;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        L_0x002c:
            long r3 = r0.J$0
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r8)
            r7 = r5
            goto L_0x005d
        L_0x0037:
            long r3 = r0.J$0
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x006e
        L_0x0041:
            long r3 = r0.J$0
            java.lang.Object r5 = r0.L$0
            r7 = r5
            kotlinx.coroutines.channels.SendChannel r7 = (kotlinx.coroutines.channels.SendChannel) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x005d
        L_0x004c:
            kotlin.ResultKt.throwOnFailure(r8)
            r0.L$0 = r7
            r0.J$0 = r3
            r8 = 1
            r0.label = r8
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r0)
            if (r5 != r1) goto L_0x005d
            return r1
        L_0x005d:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            r0.L$0 = r7
            r0.J$0 = r3
            r6 = 2
            r0.label = r6
            java.lang.Object r5 = r7.send(r5, r0)
            if (r5 != r1) goto L_0x006d
            return r1
        L_0x006d:
            r5 = r7
        L_0x006e:
            r0.L$0 = r5
            r0.J$0 = r3
            r6 = 3
            r0.label = r6
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r0)
            if (r6 != r1) goto L_0x007c
            return r1
        L_0x007c:
            r7 = r5
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.b(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
