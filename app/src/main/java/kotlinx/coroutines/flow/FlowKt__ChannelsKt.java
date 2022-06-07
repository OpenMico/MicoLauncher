package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ReplaceWith;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.BroadcastKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Channels.kt */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a0\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n\u001a/\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a9\u0010\u0010\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a&\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"asFlow", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/BroadcastChannel;", "broadcastIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "start", "Lkotlinx/coroutines/CoroutineStart;", "consumeAsFlow", "Lkotlinx/coroutines/channels/ReceiveChannel;", "emitAll", "", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAllImpl", "consume", "", "emitAllImpl$FlowKt__ChannelsKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceIn", "receiveAsFlow", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class FlowKt__ChannelsKt {

    /* compiled from: Channels.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 2;
            iArr[BufferOverflow.DROP_LATEST.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Channels.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ChannelsKt", f = "Channels.kt", i = {0, 0, 0}, l = {51, 62}, m = "emitAllImpl$FlowKt__ChannelsKt", n = {"$this$emitAllImpl", "channel", "consume"}, s = {"L$0", "L$1", "Z$0"})
    /* loaded from: classes5.dex */
    public static final class b<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
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
            return FlowKt__ChannelsKt.b(null, null, false, this);
        }
    }

    @Nullable
    public static final <T> Object a(@NotNull FlowCollector<? super T> flowCollector, @NotNull ReceiveChannel<? extends T> receiveChannel, @NotNull Continuation<? super Unit> continuation) {
        Object b2 = b(flowCollector, receiveChannel, true, continuation);
        return b2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? b2 : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0073 A[Catch: Throwable -> 0x0051, all -> 0x004f, TRY_LEAVE, TryCatch #1 {all -> 0x004f, blocks: (B:13:0x0037, B:15:0x0045, B:18:0x005d, B:21:0x006d, B:23:0x0073, B:29:0x0081, B:30:0x0082), top: B:36:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0082 A[Catch: Throwable -> 0x0051, all -> 0x004f, TRY_LEAVE, TryCatch #1 {all -> 0x004f, blocks: (B:13:0x0037, B:15:0x0045, B:18:0x005d, B:21:0x006d, B:23:0x0073, B:29:0x0081, B:30:0x0082), top: B:36:0x0022 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.lang.Object b(kotlinx.coroutines.flow.FlowCollector<? super T> r5, kotlinx.coroutines.channels.ReceiveChannel<? extends T> r6, boolean r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__ChannelsKt.b
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$b r0 = (kotlinx.coroutines.flow.FlowKt__ChannelsKt.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$b r0 = new kotlinx.coroutines.flow.FlowKt__ChannelsKt$b
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0054;
                case 1: goto L_0x003b;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002d:
            boolean r5 = r0.Z$0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0051, all -> 0x004f
            goto L_0x005d
        L_0x003b:
            boolean r5 = r0.Z$0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0051, all -> 0x004f
            kotlinx.coroutines.channels.ChannelResult r8 = (kotlinx.coroutines.channels.ChannelResult) r8     // Catch: Throwable -> 0x0051, all -> 0x004f
            java.lang.Object r8 = r8.m1778unboximpl()     // Catch: Throwable -> 0x0051, all -> 0x004f
            goto L_0x006d
        L_0x004f:
            r7 = move-exception
            goto L_0x0097
        L_0x0051:
            r7 = move-exception
            r3 = r7
            goto L_0x0096
        L_0x0054:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.FlowKt.ensureActive(r5)
            r4 = r7
            r7 = r5
            r5 = r4
        L_0x005d:
            r0.L$0 = r7     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$1 = r6     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.Z$0 = r5     // Catch: Throwable -> 0x0051, all -> 0x004f
            r8 = 1
            r0.label = r8     // Catch: Throwable -> 0x0051, all -> 0x004f
            java.lang.Object r8 = r6.mo1759receiveCatchingJP2dKIU(r0)     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r8 != r1) goto L_0x006d
            return r1
        L_0x006d:
            boolean r2 = kotlinx.coroutines.channels.ChannelResult.m1774isClosedimpl(r8)     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r2 == 0) goto L_0x0082
            java.lang.Throwable r7 = kotlinx.coroutines.channels.ChannelResult.m1770exceptionOrNullimpl(r8)     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r7 != 0) goto L_0x0081
            if (r5 == 0) goto L_0x007e
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
        L_0x007e:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L_0x0081:
            throw r7     // Catch: Throwable -> 0x0051, all -> 0x004f
        L_0x0082:
            java.lang.Object r8 = kotlinx.coroutines.channels.ChannelResult.m1772getOrThrowimpl(r8)     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$0 = r7     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$1 = r6     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.Z$0 = r5     // Catch: Throwable -> 0x0051, all -> 0x004f
            r2 = 2
            r0.label = r2     // Catch: Throwable -> 0x0051, all -> 0x004f
            java.lang.Object r8 = r7.emit(r8, r0)     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r8 != r1) goto L_0x005d
            return r1
        L_0x0096:
            throw r3     // Catch: all -> 0x004f
        L_0x0097:
            if (r5 == 0) goto L_0x009c
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
        L_0x009c:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ChannelsKt.b(kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.channels.ReceiveChannel, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull ReceiveChannel<? extends T> receiveChannel) {
        return new c(receiveChannel, false, null, 0, null, 28, null);
    }

    @NotNull
    public static final <T> Flow<T> b(@NotNull ReceiveChannel<? extends T> receiveChannel) {
        return new c(receiveChannel, true, null, 0, null, 28, null);
    }

    public static /* synthetic */ BroadcastChannel a(Flow flow, CoroutineScope coroutineScope, CoroutineStart coroutineStart, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.LAZY;
        }
        return FlowKt.broadcastIn(flow, coroutineScope, coroutineStart);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use shareIn operator and the resulting SharedFlow as a replacement for BroadcastChannel", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, SharingStarted.Lazily, 0)", imports = {}))
    @NotNull
    public static final <T> BroadcastChannel<T> a(@NotNull Flow<? extends T> flow, @NotNull CoroutineScope coroutineScope, @NotNull CoroutineStart coroutineStart) {
        int i;
        ChannelFlow asChannelFlow = ChannelFlowKt.asChannelFlow(flow);
        switch (WhenMappings.$EnumSwitchMapping$0[asChannelFlow.onBufferOverflow.ordinal()]) {
            case 1:
                i = asChannelFlow.getProduceCapacity$kotlinx_coroutines_core();
                break;
            case 2:
                i = -1;
                break;
            case 3:
                throw new IllegalArgumentException("Broadcast channel does not support BufferOverflow.DROP_LATEST");
            default:
                throw new NoWhenBranchMatchedException();
        }
        return BroadcastKt.broadcast$default(coroutineScope, asChannelFlow.context, i, coroutineStart, null, new a(flow, null), 8, null);
    }

    /* compiled from: Channels.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ChannelsKt$broadcastIn$1", f = "Channels.kt", i = {}, l = {233}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    static final class a extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T> $this_broadcastIn;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        a(Flow<? extends T> flow, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$this_broadcastIn = flow;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super T> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            a aVar = new a(this.$this_broadcastIn, continuation);
            aVar.L$0 = obj;
            return aVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    final ProducerScope producerScope = (ProducerScope) this.L$0;
                    this.label = 1;
                    if (this.$this_broadcastIn.collect(new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ChannelsKt$broadcastIn$1$invokeSuspend$$inlined$collect$1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        @Nullable
                        public Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
                            Object send = ProducerScope.this.send(t, continuation);
                            return send == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? send : Unit.INSTANCE;
                        }
                    }, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure(obj);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }
    }

    @FlowPreview
    @NotNull
    public static final <T> ReceiveChannel<T> a(@NotNull Flow<? extends T> flow, @NotNull CoroutineScope coroutineScope) {
        return ChannelFlowKt.asChannelFlow(flow).produceImpl(coroutineScope);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "'BroadcastChannel' is obsolete and all coreresponding operators are deprecated in the favour of StateFlow and SharedFlow")
    @NotNull
    public static final <T> Flow<T> a(@NotNull final BroadcastChannel<T> broadcastChannel) {
        return new Flow<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super T> flowCollector, @NotNull Continuation<? super Unit> continuation) {
                Object emitAll = FlowKt.emitAll(flowCollector, BroadcastChannel.this.openSubscription(), continuation);
                return emitAll == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitAll : Unit.INSTANCE;
            }
        };
    }
}
