package kotlinx.coroutines.channels;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.ObsoleteCoroutinesApi;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.selects.SelectClause1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Channels.common.kt */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u001a\u001a\u0010\u0002\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0001\u001aC\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\t0\n2\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0004\u0012\u0004\u0012\u0002H\b0\f¢\u0006\u0002\b\rH\u0087\b¢\u0006\u0002\u0010\u000e\u001aP\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\t0\u00042\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0004\u0012\u0004\u0012\u0002H\b0\f¢\u0006\u0002\b\rH\u0086\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000f\u001a5\u0010\u0010\u001a\u00020\u0003\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\n2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\fH\u0087Hø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a5\u0010\u0010\u001a\u00020\u0003\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u00042\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\fH\u0086Hø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a$\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\t0\u0015\"\b\b\u0000\u0010\t*\u00020\u0016*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0007\u001a'\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\b\b\u0000\u0010\t*\u00020\u0016*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a'\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\t0\u001a\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"DEFAULT_CLOSE_MESSAGE", "", "cancelConsumed", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "cause", "", "consume", "R", "E", "Lkotlinx/coroutines/channels/BroadcastChannel;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "action", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "", "receiveOrNull", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class d {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {104}, m = "consumeEach", n = {"action", "$this$consume$iv"}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class a<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
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
            return d.a((ReceiveChannel) null, (Function1) null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {129}, m = "consumeEach", n = {"action", "channel$iv"}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class b<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
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
            return d.a((BroadcastChannel) null, (Function1) null, this);
        }
    }

    /* compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {Opcodes.LCMP}, m = "toList", n = {"$this$toList_u24lambda_u2d3", "$this$consume$iv$iv"}, s = {"L$1", "L$2"})
    /* loaded from: classes5.dex */
    static final class c<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        c(Continuation<? super c> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toList(null, this);
        }
    }

    @ObsoleteCoroutinesApi
    public static final <E, R> R a(@NotNull BroadcastChannel<E> broadcastChannel, @NotNull Function1<? super ReceiveChannel<? extends E>, ? extends R> function1) {
        ReceiveChannel<E> openSubscription = broadcastChannel.openSubscription();
        try {
            return (R) function1.invoke(openSubscription);
        } finally {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default((ReceiveChannel) openSubscription, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated(level = DeprecationLevel.WARNING, message = "Deprecated in the favour of 'receiveCatching'", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    @Nullable
    public static final <E> Object a(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull Continuation<? super E> continuation) {
        return receiveChannel.receiveOrNull(continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Deprecated in the favour of 'onReceiveCatching'")
    @NotNull
    public static final <E> SelectClause1<E> a(@NotNull ReceiveChannel<? extends E> receiveChannel) {
        return (SelectClause1<? extends E>) receiveChannel.getOnReceiveOrNull();
    }

    public static final <E, R> R a(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull Function1<? super ReceiveChannel<? extends E>, ? extends R> function1) {
        try {
            return (R) function1.invoke(receiveChannel);
        } finally {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed(receiveChannel, null);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006a A[Catch: Throwable -> 0x0041, all -> 0x003e, TryCatch #5 {Throwable -> 0x0041, all -> 0x003e, blocks: (B:13:0x003a, B:24:0x0062, B:26:0x006a, B:27:0x0074), top: B:46:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0074 A[Catch: Throwable -> 0x0041, all -> 0x003e, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0041, all -> 0x003e, blocks: (B:13:0x003a, B:24:0x0062, B:26:0x006a, B:27:0x0074), top: B:46:0x003a }] */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <E> java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.d.a
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.d$a r0 = (kotlinx.coroutines.channels.d.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.d$a r0 = new kotlinx.coroutines.channels.d$a
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            switch(r2) {
                case 0: goto L_0x0045;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002e:
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0041, all -> 0x003e
            goto L_0x0062
        L_0x003e:
            r6 = move-exception
            goto L_0x0091
        L_0x0041:
            r6 = move-exception
            r3 = r6
            r6 = r7
            goto L_0x0090
        L_0x0045:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: Throwable -> 0x008e, all -> 0x0089
            r5 = r8
            r8 = r6
            r6 = r5
        L_0x004f:
            r0.L$0 = r7     // Catch: Throwable -> 0x0085, all -> 0x0082
            r0.L$1 = r8     // Catch: Throwable -> 0x0085, all -> 0x0082
            r0.L$2 = r6     // Catch: Throwable -> 0x0085, all -> 0x0082
            r0.label = r4     // Catch: Throwable -> 0x0085, all -> 0x0082
            java.lang.Object r2 = r6.hasNext(r0)     // Catch: Throwable -> 0x0085, all -> 0x0082
            if (r2 != r1) goto L_0x005e
            return r1
        L_0x005e:
            r5 = r2
            r2 = r7
            r7 = r8
            r8 = r5
        L_0x0062:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: Throwable -> 0x0041, all -> 0x003e
            boolean r8 = r8.booleanValue()     // Catch: Throwable -> 0x0041, all -> 0x003e
            if (r8 == 0) goto L_0x0074
            java.lang.Object r8 = r6.next()     // Catch: Throwable -> 0x0041, all -> 0x003e
            r2.invoke(r8)     // Catch: Throwable -> 0x0041, all -> 0x003e
            r8 = r7
            r7 = r2
            goto L_0x004f
        L_0x0074:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x0041, all -> 0x003e
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L_0x0082:
            r6 = move-exception
            r7 = r8
            goto L_0x0091
        L_0x0085:
            r6 = move-exception
            r3 = r6
            r6 = r8
            goto L_0x0090
        L_0x0089:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x0091
        L_0x008e:
            r7 = move-exception
            r3 = r7
        L_0x0090:
            throw r3     // Catch: all -> 0x0089
        L_0x0091:
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.d.a(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0069 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0075 A[Catch: Throwable -> 0x0045, all -> 0x0041, TryCatch #5 {all -> 0x0041, Throwable -> 0x0045, blocks: (B:13:0x003d, B:25:0x006d, B:27:0x0075, B:28:0x007e), top: B:46:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007e A[Catch: Throwable -> 0x0045, all -> 0x0041, TRY_LEAVE, TryCatch #5 {all -> 0x0041, Throwable -> 0x0045, blocks: (B:13:0x003d, B:25:0x006d, B:27:0x0075, B:28:0x007e), top: B:46:0x003d }] */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <E> java.lang.Object b(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.util.List<? extends E>> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.d.c
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.d$c r0 = (kotlinx.coroutines.channels.d.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.d$c r0 = new kotlinx.coroutines.channels.d$c
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0049;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            java.lang.Object r7 = r0.L$3
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r2 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r4 = r0.L$1
            java.util.List r4 = (java.util.List) r4
            java.lang.Object r5 = r0.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0045, all -> 0x0041
            goto L_0x006d
        L_0x0041:
            r7 = move-exception
            r8 = r2
            goto L_0x0096
        L_0x0045:
            r7 = move-exception
            r3 = r7
            r7 = r2
            goto L_0x0095
        L_0x0049:
            kotlin.ResultKt.throwOnFailure(r8)
            java.util.List r8 = kotlin.collections.CollectionsKt.createListBuilder()
            kotlinx.coroutines.channels.ChannelIterator r2 = r7.iterator()     // Catch: Throwable -> 0x0093, all -> 0x008e
            r4 = r8
            r5 = r4
            r8 = r7
            r7 = r2
        L_0x0058:
            r0.L$0 = r5     // Catch: Throwable -> 0x008a, all -> 0x0088
            r0.L$1 = r4     // Catch: Throwable -> 0x008a, all -> 0x0088
            r0.L$2 = r8     // Catch: Throwable -> 0x008a, all -> 0x0088
            r0.L$3 = r7     // Catch: Throwable -> 0x008a, all -> 0x0088
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x008a, all -> 0x0088
            java.lang.Object r2 = r7.hasNext(r0)     // Catch: Throwable -> 0x008a, all -> 0x0088
            if (r2 != r1) goto L_0x006a
            return r1
        L_0x006a:
            r6 = r2
            r2 = r8
            r8 = r6
        L_0x006d:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: Throwable -> 0x0045, all -> 0x0041
            boolean r8 = r8.booleanValue()     // Catch: Throwable -> 0x0045, all -> 0x0041
            if (r8 == 0) goto L_0x007e
            java.lang.Object r8 = r7.next()     // Catch: Throwable -> 0x0045, all -> 0x0041
            r4.add(r8)     // Catch: Throwable -> 0x0045, all -> 0x0041
            r8 = r2
            goto L_0x0058
        L_0x007e:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x0045, all -> 0x0041
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            java.util.List r7 = kotlin.collections.CollectionsKt.build(r5)
            return r7
        L_0x0088:
            r7 = move-exception
            goto L_0x0096
        L_0x008a:
            r7 = move-exception
            r3 = r7
            r7 = r8
            goto L_0x0095
        L_0x008e:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto L_0x0096
        L_0x0093:
            r8 = move-exception
            r3 = r8
        L_0x0095:
            throw r3     // Catch: all -> 0x008e
        L_0x0096:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.d.b(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0069 A[Catch: all -> 0x003e, TryCatch #2 {all -> 0x003e, blocks: (B:13:0x003a, B:22:0x0061, B:24:0x0069, B:25:0x0073), top: B:38:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0073 A[Catch: all -> 0x003e, TRY_LEAVE, TryCatch #2 {all -> 0x003e, blocks: (B:13:0x003a, B:22:0x0061, B:24:0x0069, B:25:0x0073), top: B:38:0x003a }] */
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <E> java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.BroadcastChannel<E> r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.d.b
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.d$b r0 = (kotlinx.coroutines.channels.d.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.d$b r0 = new kotlinx.coroutines.channels.d$b
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            switch(r2) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002e:
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: all -> 0x003e
            goto L_0x0061
        L_0x003e:
            r6 = move-exception
            goto L_0x0088
        L_0x0040:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ReceiveChannel r6 = r6.openSubscription()
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: all -> 0x0084
            r5 = r8
            r8 = r6
            r6 = r5
        L_0x004e:
            r0.L$0 = r7     // Catch: all -> 0x0081
            r0.L$1 = r8     // Catch: all -> 0x0081
            r0.L$2 = r6     // Catch: all -> 0x0081
            r0.label = r4     // Catch: all -> 0x0081
            java.lang.Object r2 = r6.hasNext(r0)     // Catch: all -> 0x0081
            if (r2 != r1) goto L_0x005d
            return r1
        L_0x005d:
            r5 = r2
            r2 = r7
            r7 = r8
            r8 = r5
        L_0x0061:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: all -> 0x003e
            boolean r8 = r8.booleanValue()     // Catch: all -> 0x003e
            if (r8 == 0) goto L_0x0073
            java.lang.Object r8 = r6.next()     // Catch: all -> 0x003e
            r2.invoke(r8)     // Catch: all -> 0x003e
            r8 = r7
            r7 = r2
            goto L_0x004e
        L_0x0073:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: all -> 0x003e
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default(r7, r3, r4, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L_0x0081:
            r6 = move-exception
            r7 = r8
            goto L_0x0088
        L_0x0084:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L_0x0088:
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default(r7, r3, r4, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.d.a(kotlinx.coroutines.channels.BroadcastChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @PublishedApi
    public static final void a(@NotNull ReceiveChannel<?> receiveChannel, @Nullable Throwable th) {
        CancellationException cancellationException = null;
        if (th != null) {
            if (th instanceof CancellationException) {
                cancellationException = (CancellationException) th;
            }
            if (cancellationException == null) {
                cancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", th);
            }
        }
        receiveChannel.cancel(cancellationException);
    }
}
