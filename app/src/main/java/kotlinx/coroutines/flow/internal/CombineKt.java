package kotlinx.coroutines.flow.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.idm.service.iot.LightService;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: JadxRuntimeException in pass: ClassModifier
    jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T1
    	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:53)
    	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
    	at jadx.core.dex.visitors.ClassModifier.removeSyntheticFields(ClassModifier.java:80)
    	at jadx.core.dex.visitors.ClassModifier.visit(ClassModifier.java:58)
    	at jadx.core.dex.visitors.ClassModifier.visit(ClassModifier.java:52)
    */
/* compiled from: Combine.kt */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001an\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012(\u0010\u0007\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n0\bH\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a\u0090\u0001\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0014\u0010\u0010\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00010\u00112\u0016\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u0001H\u000e\u0018\u00010\u00110\u001329\u0010\u0007\u001a5\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t\u0012\u0006\u0012\u0004\u0018\u00010\n0\b¢\u0006\u0002\b\u0014H\u0081@ø\u0001\u0000¢\u0006\u0002\u0010\u0015*\u001c\b\u0002\u0010\u0016\"\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u00172\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0017\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, d2 = {"zipImpl", "Lkotlinx/coroutines/flow/Flow;", "R", "T1", "T2", LightService.LightPropertyCommand.FLOW, "flow2", "transform", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "combineInternal", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "flows", "", "arrayFactory", "Lkotlin/Function0;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;[Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Update", "Lkotlin/collections/IndexedValue;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class CombineKt {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Combine.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", f = "Combine.kt", i = {0, 0, 0, 0}, l = {57, 79, 82}, m = "invokeSuspend", n = {"latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues"}, s = {"L$0", "L$1", "L$2", "I$0"})
    /* loaded from: classes5.dex */
    public static final class a extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0<T[]> $arrayFactory;
        final /* synthetic */ Flow<T>[] $flows;
        final /* synthetic */ FlowCollector<R> $this_combineInternal;
        final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        a(Flow<? extends T>[] flowArr, Function0<T[]> function0, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, FlowCollector<? super R> flowCollector, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$flows = flowArr;
            this.$arrayFactory = function0;
            this.$transform = function3;
            this.$this_combineInternal = flowCollector;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            a aVar = new a(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
            aVar.L$0 = obj;
            return aVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x00bd A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00be  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00cb  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00ce A[LOOP:1: B:27:0x00ce->B:33:0x00f2, LOOP_START, PHI: r3 r7 
          PHI: (r3v2 int) = (r3v1 int), (r3v3 int) binds: [B:24:0x00c9, B:33:0x00f2] A[DONT_GENERATE, DONT_INLINE]
          PHI: (r7v3 kotlin.collections.IndexedValue) = (r7v2 kotlin.collections.IndexedValue), (r7v16 kotlin.collections.IndexedValue) binds: [B:24:0x00c9, B:33:0x00f2] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00f6  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0148  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r21) {
            /*
                Method dump skipped, instructions count: 348
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: Combine.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1", f = "Combine.kt", i = {}, l = {147}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$a$1 */
        /* loaded from: classes5.dex */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Flow<T>[] $flows;
            final /* synthetic */ int $i;
            final /* synthetic */ AtomicInteger $nonClosed;
            final /* synthetic */ Channel<IndexedValue<Object>> $resultChannel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(Flow<? extends T>[] flowArr, int i, AtomicInteger atomicInteger, Channel<IndexedValue<Object>> channel, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$flows = flowArr;
                this.$i = i;
                this.$nonClosed = atomicInteger;
                this.$resultChannel = channel;
            }

            @Nullable
            /* renamed from: a */
            public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new AnonymousClass1(this.$flows, this.$i, this.$nonClosed, this.$resultChannel, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                AtomicInteger atomicInteger;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                try {
                    switch (this.label) {
                        case 0:
                            ResultKt.throwOnFailure(obj);
                            this.label = 1;
                            if (this.$flows[this.$i].collect(new CombineKt$combineInternal$2$1$invokeSuspend$$inlined$collect$1(this.$resultChannel, this.$i), this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            break;
                        case 1:
                            ResultKt.throwOnFailure(obj);
                            break;
                        default:
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    if (atomicInteger.decrementAndGet() == 0) {
                        SendChannel.DefaultImpls.close$default(this.$resultChannel, null, 1, null);
                    }
                    return Unit.INSTANCE;
                } finally {
                    if (this.$nonClosed.decrementAndGet() == 0) {
                        SendChannel.DefaultImpls.close$default(this.$resultChannel, null, 1, null);
                    }
                }
            }
        }
    }

    @PublishedApi
    @Nullable
    public static final <R, T> Object combineInternal(@NotNull FlowCollector<? super R> flowCollector, @NotNull Flow<? extends T>[] flowArr, @NotNull Function0<T[]> function0, @NotNull Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, @NotNull Continuation<? super Unit> continuation) {
        Object flowScope = FlowCoroutineKt.flowScope(new a(flowArr, function0, function3, flowCollector, null), continuation);
        return flowScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? flowScope : Unit.INSTANCE;
    }

    /* compiled from: Combine.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\u00020\u0005H\u008a@"}, d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1", f = "Combine.kt", i = {0}, l = {129}, m = "invokeSuspend", n = {"second"}, s = {"L$0"})
    /* loaded from: classes5.dex */
    static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T1> $flow;
        final /* synthetic */ Flow<T2> $flow2;
        final /* synthetic */ FlowCollector<R> $this_unsafeFlow;
        final /* synthetic */ Function3<T1, T2, Continuation<? super R>, Object> $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public b(FlowCollector<? super R> flowCollector, Flow<? extends T2> flow, Flow<? extends T1> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super b> continuation) {
            super(2, continuation);
            this.$this_unsafeFlow = flowCollector;
            this.$flow2 = flow;
            this.$flow = flow2;
            this.$transform = function3;
        }

        @Nullable
        public final Object a(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            b bVar = new b(this.$this_unsafeFlow, this.$flow2, this.$flow, this.$transform, continuation);
            bVar.L$0 = obj;
            return bVar;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return a(coroutineScope, continuation);
        }

        /* compiled from: Combine.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u00020\u00060\u0005H\u008a@"}, d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/channels/ProducerScope;", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1", f = "Combine.kt", i = {}, l = {147}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes5.dex */
        public static final class a extends SuspendLambda implements Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object> {
            final /* synthetic */ Flow<T2> $flow2;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            a(Flow<? extends T2> flow, Continuation<? super a> continuation) {
                super(2, continuation);
                this.$flow2 = flow;
            }

            @Nullable
            public final Object a(@NotNull ProducerScope<Object> producerScope, @Nullable Continuation<? super Unit> continuation) {
                return ((a) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                a aVar = new a(this.$flow2, continuation);
                aVar.L$0 = obj;
                return aVar;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* synthetic */ Object invoke(ProducerScope<? super Object> producerScope, Continuation<? super Unit> continuation) {
                return a(producerScope, continuation);
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
                        if (this.$flow2.collect(new FlowCollector<T2>() { // from class: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$invokeSuspend$$inlined$collect$1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            @Nullable
                            public Object emit(T2 t2, @NotNull Continuation<? super Unit> continuation) {
                                SendChannel channel = ProducerScope.this.getChannel();
                                Symbol symbol = t2;
                                if (t2 == 0) {
                                    symbol = NullSurrogateKt.NULL;
                                }
                                Object send = channel.send(symbol == 1 ? 1 : 0, continuation);
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

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            ReceiveChannel receiveChannel;
            Throwable th;
            AbortFlowException e;
            CompletableJob a2;
            CoroutineContext coroutineContext;
            Object threadContextElements;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    receiveChannel = ProduceKt.produce$default(coroutineScope, null, 0, new a(this.$flow2, null), 3, null);
                    a2 = u.a((Job) null, 1, (Object) null);
                    ((SendChannel) receiveChannel).invokeOnClose(new AnonymousClass1(a2, this.$this_unsafeFlow));
                    try {
                        coroutineContext = coroutineScope.getCoroutineContext();
                        threadContextElements = ThreadContextKt.threadContextElements(coroutineContext);
                        this.L$0 = receiveChannel;
                        this.label = 1;
                    } catch (AbortFlowException e2) {
                        e = e2;
                        receiveChannel = receiveChannel;
                        FlowExceptions_commonKt.checkOwnership(e, this.$this_unsafeFlow);
                        ReceiveChannel.DefaultImpls.cancel$default(receiveChannel, (CancellationException) null, 1, (Object) null);
                        return Unit.INSTANCE;
                    } catch (Throwable th2) {
                        th = th2;
                        ReceiveChannel.DefaultImpls.cancel$default(receiveChannel, (CancellationException) null, 1, (Object) null);
                        throw th;
                    }
                    if (ChannelFlowKt.withContextUndispatched$default(coroutineScope.getCoroutineContext().plus(a2), Unit.INSTANCE, null, new AnonymousClass2(this.$flow, coroutineContext, threadContextElements, receiveChannel, this.$this_unsafeFlow, this.$transform, null), this, 4, null) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    receiveChannel = receiveChannel;
                    ReceiveChannel.DefaultImpls.cancel$default(receiveChannel, (CancellationException) null, 1, (Object) null);
                    return Unit.INSTANCE;
                case 1:
                    receiveChannel = (ReceiveChannel) this.L$0;
                    try {
                        try {
                            ResultKt.throwOnFailure(obj);
                        } catch (AbortFlowException e3) {
                            e = e3;
                            FlowExceptions_commonKt.checkOwnership(e, this.$this_unsafeFlow);
                            ReceiveChannel.DefaultImpls.cancel$default(receiveChannel, (CancellationException) null, 1, (Object) null);
                            return Unit.INSTANCE;
                        }
                        ReceiveChannel.DefaultImpls.cancel$default(receiveChannel, (CancellationException) null, 1, (Object) null);
                        return Unit.INSTANCE;
                    } catch (Throwable th3) {
                        th = th3;
                        ReceiveChannel.DefaultImpls.cancel$default(receiveChannel, (CancellationException) null, 1, (Object) null);
                        throw th;
                    }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        /* compiled from: Combine.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\n"}, d2 = {"<anonymous>", "", "T1", "T2", "R", "it", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$b$1 */
        /* loaded from: classes5.dex */
        public static final class AnonymousClass1 extends Lambda implements Function1<Throwable, Unit> {
            final /* synthetic */ CompletableJob $collectJob;
            final /* synthetic */ FlowCollector<R> $this_unsafeFlow;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(CompletableJob completableJob, FlowCollector<? super R> flowCollector) {
                super(1);
                this.$collectJob = completableJob;
                this.$this_unsafeFlow = flowCollector;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* synthetic */ Unit invoke(Throwable th) {
                a(th);
                return Unit.INSTANCE;
            }

            public final void a(@Nullable Throwable th) {
                if (this.$collectJob.isActive()) {
                    this.$collectJob.cancel((CancellationException) new AbortFlowException(this.$this_unsafeFlow));
                }
            }
        }

        /* compiled from: Combine.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u008a@"}, d2 = {"<anonymous>", "", "T1", "T2", "R", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2", f = "Combine.kt", i = {}, l = {147}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$b$2 */
        /* loaded from: classes5.dex */
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<Unit, Continuation<? super Unit>, Object> {
            final /* synthetic */ Object $cnt;
            final /* synthetic */ Flow<T1> $flow;
            final /* synthetic */ CoroutineContext $scopeContext;
            final /* synthetic */ ReceiveChannel<Object> $second;
            final /* synthetic */ FlowCollector<R> $this_unsafeFlow;
            final /* synthetic */ Function3<T1, T2, Continuation<? super R>, Object> $transform;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass2(Flow<? extends T1> flow, CoroutineContext coroutineContext, Object obj, ReceiveChannel<? extends Object> receiveChannel, FlowCollector<? super R> flowCollector, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$flow = flow;
                this.$scopeContext = coroutineContext;
                this.$cnt = obj;
                this.$second = receiveChannel;
                this.$this_unsafeFlow = flowCollector;
                this.$transform = function3;
            }

            @Nullable
            public final Object a(@NotNull Unit unit, @Nullable Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(unit, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new AnonymousClass2(this.$flow, this.$scopeContext, this.$cnt, this.$second, this.$this_unsafeFlow, this.$transform, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* synthetic */ Object invoke(Unit unit, Continuation<? super Unit> continuation) {
                return a(unit, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        Flow<T1> flow = this.$flow;
                        final CoroutineContext coroutineContext = this.$scopeContext;
                        final Object obj2 = this.$cnt;
                        final ReceiveChannel<Object> receiveChannel = this.$second;
                        final FlowCollector<R> flowCollector = this.$this_unsafeFlow;
                        final Function3<T1, T2, Continuation<? super R>, Object> function3 = this.$transform;
                        this.label = 1;
                        if (flow.collect(new FlowCollector<T1>() { // from class: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$invokeSuspend$$inlined$collect$1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            @Nullable
                            public Object emit(T1 t1, @NotNull Continuation<? super Unit> continuation) {
                                Object withContextUndispatched = ChannelFlowKt.withContextUndispatched(CoroutineContext.this, Unit.INSTANCE, obj2, new CombineKt.b.AnonymousClass2.a(receiveChannel, flowCollector, function3, t1, null), continuation);
                                return withContextUndispatched == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? withContextUndispatched : Unit.INSTANCE;
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

            /* compiled from: Combine.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u008a@"}, d2 = {"<anonymous>", "", "T1", "T2", "R", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
            @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$1$1", f = "Combine.kt", i = {}, l = {132, 135, 135}, m = "invokeSuspend", n = {}, s = {})
            /* renamed from: kotlinx.coroutines.flow.internal.CombineKt$b$2$a */
            /* loaded from: classes5.dex */
            static final class a extends SuspendLambda implements Function2<Unit, Continuation<? super Unit>, Object> {
                final /* synthetic */ ReceiveChannel<Object> $second;
                final /* synthetic */ FlowCollector<R> $this_unsafeFlow;
                final /* synthetic */ Function3<T1, T2, Continuation<? super R>, Object> $transform;
                final /* synthetic */ T1 $value;
                Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public a(ReceiveChannel<? extends Object> receiveChannel, FlowCollector<? super R> flowCollector, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3, T1 t1, Continuation<? super a> continuation) {
                    super(2, continuation);
                    this.$second = receiveChannel;
                    this.$this_unsafeFlow = flowCollector;
                    this.$transform = function3;
                    this.$value = t1;
                }

                @Nullable
                public final Object a(@NotNull Unit unit, @Nullable Continuation<? super Unit> continuation) {
                    return ((a) create(unit, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @NotNull
                public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                    return new a(this.$second, this.$this_unsafeFlow, this.$transform, this.$value, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* synthetic */ Object invoke(Unit unit, Continuation<? super Unit> continuation) {
                    return a(unit, continuation);
                }

                /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
                /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0071 A[RETURN] */
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
                        r2 = 0
                        switch(r1) {
                            case 0: goto L_0x0028;
                            case 1: goto L_0x001e;
                            case 2: goto L_0x0016;
                            case 3: goto L_0x0012;
                            default: goto L_0x000a;
                        }
                    L_0x000a:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r0)
                        throw r7
                    L_0x0012:
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L_0x0072
                    L_0x0016:
                        java.lang.Object r1 = r6.L$0
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L_0x0063
                    L_0x001e:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.channels.ChannelResult r7 = (kotlinx.coroutines.channels.ChannelResult) r7
                        java.lang.Object r7 = r7.m1778unboximpl()
                        goto L_0x003a
                    L_0x0028:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.channels.ReceiveChannel<java.lang.Object> r7 = r6.$second
                        r1 = r6
                        kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                        r3 = 1
                        r6.label = r3
                        java.lang.Object r7 = r7.mo1759receiveCatchingJP2dKIU(r1)
                        if (r7 != r0) goto L_0x003a
                        return r0
                    L_0x003a:
                        kotlinx.coroutines.flow.FlowCollector<R> r1 = r6.$this_unsafeFlow
                        boolean r3 = r7 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
                        if (r3 == 0) goto L_0x004e
                        java.lang.Throwable r7 = kotlinx.coroutines.channels.ChannelResult.m1770exceptionOrNullimpl(r7)
                        if (r7 != 0) goto L_0x004d
                        kotlinx.coroutines.flow.internal.AbortFlowException r7 = new kotlinx.coroutines.flow.internal.AbortFlowException
                        r7.<init>(r1)
                        java.lang.Throwable r7 = (java.lang.Throwable) r7
                    L_0x004d:
                        throw r7
                    L_0x004e:
                        kotlin.jvm.functions.Function3<T1, T2, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r3 = r6.$transform
                        T1 r4 = r6.$value
                        kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
                        if (r7 != r5) goto L_0x0057
                        r7 = r2
                    L_0x0057:
                        r6.L$0 = r1
                        r5 = 2
                        r6.label = r5
                        java.lang.Object r7 = r3.invoke(r4, r7, r6)
                        if (r7 != r0) goto L_0x0063
                        return r0
                    L_0x0063:
                        r3 = r6
                        kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                        r6.L$0 = r2
                        r2 = 3
                        r6.label = r2
                        java.lang.Object r7 = r1.emit(r7, r3)
                        if (r7 != r0) goto L_0x0072
                        return r0
                    L_0x0072:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt.b.AnonymousClass2.a.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        }
    }

    @NotNull
    public static final <T1, T2, R> Flow<R> zipImpl(@NotNull final Flow<? extends T1> flow, @NotNull final Flow<? extends T2> flow2, @NotNull final Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super R> flowCollector, @NotNull Continuation<? super Unit> continuation) {
                Object coroutineScope = CoroutineScopeKt.coroutineScope(new CombineKt.b(flowCollector, Flow.this, flow, function3, null), continuation);
                return coroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
            }
        };
    }
}
