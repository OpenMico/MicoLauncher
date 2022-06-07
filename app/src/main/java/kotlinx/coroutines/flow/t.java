package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: JadxRuntimeException in pass: ClassModifier
    jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
    	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:53)
    	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
    	at jadx.core.dex.visitors.ClassModifier.removeSyntheticFields(ClassModifier.java:80)
    	at jadx.core.dex.visitors.ClassModifier.visit(ClassModifier.java:58)
    	at jadx.core.dex.visitors.ClassModifier.visit(ClassModifier.java:52)
    */
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Share.kt */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a+\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¢\u0006\u0002\b\f\u001aM\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u0002H\u0002¢\u0006\u0004\b\u0017\u0010\u0018\u001aA\u0010\u0019\u001a\u00020\u001a\"\u0004\b\u0000\u0010\u0002*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\t2\u0012\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u001cH\u0002¢\u0006\u0002\b\u001d\u001aS\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012-\u0010\u001f\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\"\u0012\u0006\u0012\u0004\u0018\u00010#0 ¢\u0006\u0002\b$ø\u0001\u0000¢\u0006\u0002\u0010%\u001a6\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\t2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a/\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\t2\u0006\u0010'\u001a\u00020\u000fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010)\u001a9\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\t2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u0002¢\u0006\u0002\u0010*\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006+"}, d2 = {"asSharedFlow", "Lkotlinx/coroutines/flow/SharedFlow;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/MutableSharedFlow;", "asStateFlow", "Lkotlinx/coroutines/flow/StateFlow;", "Lkotlinx/coroutines/flow/MutableStateFlow;", "configureSharing", "Lkotlinx/coroutines/flow/SharingConfig;", "Lkotlinx/coroutines/flow/Flow;", "replay", "", "configureSharing$FlowKt__ShareKt", "launchSharing", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CoroutineScope;", c.R, "Lkotlin/coroutines/CoroutineContext;", "upstream", "shared", "started", "Lkotlinx/coroutines/flow/SharingStarted;", "initialValue", "launchSharing$FlowKt__ShareKt", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/MutableSharedFlow;Lkotlinx/coroutines/flow/SharingStarted;Ljava/lang/Object;)Lkotlinx/coroutines/Job;", "launchSharingDeferred", "", "result", "Lkotlinx/coroutines/CompletableDeferred;", "launchSharingDeferred$FlowKt__ShareKt", "onSubscription", "action", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/SharedFlow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/SharedFlow;", "shareIn", "scope", "stateIn", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/flow/SharingStarted;Ljava/lang/Object;)Lkotlinx/coroutines/flow/StateFlow;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class t {
    public static /* synthetic */ SharedFlow a(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return FlowKt.shareIn(flow, coroutineScope, sharingStarted, i);
    }

    @NotNull
    public static final <T> SharedFlow<T> a(@NotNull Flow<? extends T> flow, @NotNull CoroutineScope coroutineScope, @NotNull SharingStarted sharingStarted, int i) {
        aa a2 = a(flow, i);
        MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(i, a2.b, a2.c);
        return new w(MutableSharedFlow, a(coroutineScope, a2.d, (Flow<? extends Symbol>) a2.a, (MutableSharedFlow<Symbol>) MutableSharedFlow, sharingStarted, SharedFlowKt.NO_VALUE));
    }

    private static final <T> aa<T> a(Flow<? extends T> flow, int i) {
        ChannelFlow channelFlow;
        Flow<T> dropChannelOperators;
        int i2 = 1;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(i >= 0)) {
                throw new AssertionError();
            }
        }
        int coerceAtLeast = RangesKt.coerceAtLeast(i, Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core()) - i;
        if (!(flow instanceof ChannelFlow) || (dropChannelOperators = (channelFlow = (ChannelFlow) flow).dropChannelOperators()) == null) {
            return new aa<>(flow, coerceAtLeast, BufferOverflow.SUSPEND, EmptyCoroutineContext.INSTANCE);
        }
        int i3 = channelFlow.capacity;
        if (i3 != 0) {
            switch (i3) {
                case -3:
                case -2:
                    break;
                default:
                    i2 = channelFlow.capacity;
                    break;
            }
            return new aa<>(dropChannelOperators, i2, channelFlow.onBufferOverflow, channelFlow.context);
        }
        if (channelFlow.onBufferOverflow == BufferOverflow.SUSPEND) {
            i2 = channelFlow.capacity == 0 ? 0 : coerceAtLeast;
        } else if (i != 0) {
            i2 = 0;
        }
        return new aa<>(dropChannelOperators, i2, channelFlow.onBufferOverflow, channelFlow.context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Share.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1", f = "Share.kt", i = {}, l = {206, 210, 211, 217}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class a extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ T $initialValue;
        final /* synthetic */ MutableSharedFlow<T> $shared;
        final /* synthetic */ SharingStarted $started;
        final /* synthetic */ Flow<T> $upstream;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        a(SharingStarted sharingStarted, Flow<? extends T> flow, MutableSharedFlow<T> mutableSharedFlow, T t, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$started = sharingStarted;
            this.$upstream = flow;
            this.$shared = mutableSharedFlow;
            this.$initialValue = t;
        }

        @Nullable
        public final Object a(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new a(this.$started, this.$upstream, this.$shared, this.$initialValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return a(coroutineScope, continuation);
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0073 A[RETURN] */
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
                    case 0: goto L_0x001a;
                    case 1: goto L_0x0015;
                    case 2: goto L_0x0011;
                    case 3: goto L_0x0015;
                    case 4: goto L_0x0015;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L_0x0011:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x0061
            L_0x0015:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x009e
            L_0x001a:
                kotlin.ResultKt.throwOnFailure(r7)
                kotlinx.coroutines.flow.SharingStarted r7 = r6.$started
                kotlinx.coroutines.flow.SharingStarted$Companion r1 = kotlinx.coroutines.flow.SharingStarted.Companion
                kotlinx.coroutines.flow.SharingStarted r1 = r1.getEagerly()
                if (r7 != r1) goto L_0x003a
                kotlinx.coroutines.flow.Flow<T> r7 = r6.$upstream
                kotlinx.coroutines.flow.MutableSharedFlow<T> r1 = r6.$shared
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 1
                r6.label = r3
                java.lang.Object r7 = r7.collect(r1, r2)
                if (r7 != r0) goto L_0x009e
                return r0
            L_0x003a:
                kotlinx.coroutines.flow.SharingStarted r7 = r6.$started
                kotlinx.coroutines.flow.SharingStarted$Companion r1 = kotlinx.coroutines.flow.SharingStarted.Companion
                kotlinx.coroutines.flow.SharingStarted r1 = r1.getLazily()
                r2 = 0
                if (r7 != r1) goto L_0x0074
                kotlinx.coroutines.flow.MutableSharedFlow<T> r7 = r6.$shared
                kotlinx.coroutines.flow.StateFlow r7 = r7.getSubscriptionCount()
                kotlinx.coroutines.flow.Flow r7 = (kotlinx.coroutines.flow.Flow) r7
                kotlinx.coroutines.flow.t$a$1 r1 = new kotlinx.coroutines.flow.t$a$1
                r1.<init>(r2)
                kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r7, r1, r2)
                if (r7 != r0) goto L_0x0061
                return r0
            L_0x0061:
                kotlinx.coroutines.flow.Flow<T> r7 = r6.$upstream
                kotlinx.coroutines.flow.MutableSharedFlow<T> r1 = r6.$shared
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 3
                r6.label = r3
                java.lang.Object r7 = r7.collect(r1, r2)
                if (r7 != r0) goto L_0x009e
                return r0
            L_0x0074:
                kotlinx.coroutines.flow.SharingStarted r7 = r6.$started
                kotlinx.coroutines.flow.MutableSharedFlow<T> r1 = r6.$shared
                kotlinx.coroutines.flow.StateFlow r1 = r1.getSubscriptionCount()
                kotlinx.coroutines.flow.Flow r7 = r7.command(r1)
                kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r7)
                kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2 r1 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2
                kotlinx.coroutines.flow.Flow<T> r3 = r6.$upstream
                kotlinx.coroutines.flow.MutableSharedFlow<T> r4 = r6.$shared
                T r5 = r6.$initialValue
                r1.<init>(r3, r4, r5, r2)
                kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 4
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.collectLatest(r7, r1, r2)
                if (r7 != r0) goto L_0x009e
                return r0
            L_0x009e:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.t.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: Share.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1", f = "Share.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: kotlinx.coroutines.flow.t$a$1  reason: invalid class name */
        /* loaded from: classes5.dex */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<Integer, Continuation<? super Boolean>, Object> {
            /* synthetic */ int I$0;
            int label;

            AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
            }

            @Nullable
            public final Object a(int i, @Nullable Continuation<? super Boolean> continuation) {
                return ((AnonymousClass1) create(Integer.valueOf(i), continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                AnonymousClass1 r0 = new AnonymousClass1(continuation);
                r0.I$0 = ((Number) obj).intValue();
                return r0;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* synthetic */ Object invoke(Integer num, Continuation<? super Boolean> continuation) {
                return a(num.intValue(), continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    return Boxing.boxBoolean(this.I$0 > 0);
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private static final <T> Job a(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow<? extends T> flow, MutableSharedFlow<T> mutableSharedFlow, SharingStarted sharingStarted, T t) {
        return BuildersKt.launch$default(coroutineScope, coroutineContext, null, new a(sharingStarted, flow, mutableSharedFlow, t, null), 2, null);
    }

    @NotNull
    public static final <T> StateFlow<T> a(@NotNull Flow<? extends T> flow, @NotNull CoroutineScope coroutineScope, @NotNull SharingStarted sharingStarted, T t) {
        aa a2 = a(flow, 1);
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(t);
        return new x(MutableStateFlow, a(coroutineScope, a2.d, a2.a, MutableStateFlow, sharingStarted, t));
    }

    @Nullable
    public static final <T> Object a(@NotNull Flow<? extends T> flow, @NotNull CoroutineScope coroutineScope, @NotNull Continuation<? super StateFlow<? extends T>> continuation) {
        aa a2 = a(flow, 1);
        CompletableDeferred CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        a(coroutineScope, a2.d, a2.a, CompletableDeferred$default);
        return CompletableDeferred$default.await(continuation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Share.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1", f = "Share.kt", i = {}, l = {418}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CompletableDeferred<StateFlow<T>> $result;
        final /* synthetic */ Flow<T> $upstream;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        b(Flow<? extends T> flow, CompletableDeferred<StateFlow<T>> completableDeferred, Continuation<? super b> continuation) {
            super(2, continuation);
            this.$upstream = flow;
            this.$result = completableDeferred;
        }

        @Nullable
        public final Object a(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            b bVar = new b(this.$upstream, this.$result, continuation);
            bVar.L$0 = obj;
            return bVar;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return a(coroutineScope, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            try {
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                        Flow<T> flow = this.$upstream;
                        final CompletableDeferred<StateFlow<T>> completableDeferred = this.$result;
                        this.label = 1;
                        if (flow.collect(new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$invokeSuspend$$inlined$collect$1
                            /* JADX WARN: Type inference failed for: r5v2, types: [T, kotlinx.coroutines.flow.MutableStateFlow] */
                            /* JADX WARN: Unknown variable types count: 1 */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            @org.jetbrains.annotations.Nullable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public java.lang.Object emit(T r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
                                /*
                                    r4 = this;
                                    kotlin.jvm.internal.Ref$ObjectRef r6 = kotlin.jvm.internal.Ref.ObjectRef.this
                                    T r6 = r6.element
                                    kotlinx.coroutines.flow.MutableStateFlow r6 = (kotlinx.coroutines.flow.MutableStateFlow) r6
                                    if (r6 != 0) goto L_0x000a
                                    r6 = 0
                                    goto L_0x000f
                                L_0x000a:
                                    r6.setValue(r5)
                                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                L_0x000f:
                                    if (r6 != 0) goto L_0x0032
                                    kotlinx.coroutines.CoroutineScope r6 = r2
                                    kotlin.jvm.internal.Ref$ObjectRef r0 = kotlin.jvm.internal.Ref.ObjectRef.this
                                    kotlinx.coroutines.flow.MutableStateFlow r5 = kotlinx.coroutines.flow.StateFlowKt.MutableStateFlow(r5)
                                    kotlinx.coroutines.CompletableDeferred r1 = r3
                                    kotlinx.coroutines.flow.x r2 = new kotlinx.coroutines.flow.x
                                    r3 = r5
                                    kotlinx.coroutines.flow.StateFlow r3 = (kotlinx.coroutines.flow.StateFlow) r3
                                    kotlin.coroutines.CoroutineContext r6 = r6.getCoroutineContext()
                                    kotlinx.coroutines.Job r6 = kotlinx.coroutines.JobKt.getJob(r6)
                                    r2.<init>(r3, r6)
                                    r1.complete(r2)
                                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                    r0.element = r5
                                L_0x0032:
                                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                    return r5
                                */
                                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$invokeSuspend$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
            } catch (Throwable th) {
                this.$result.completeExceptionally(th);
                throw th;
            }
        }
    }

    private static final <T> void a(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow<? extends T> flow, CompletableDeferred<StateFlow<T>> completableDeferred) {
        BuildersKt.launch$default(coroutineScope, coroutineContext, null, new b(flow, completableDeferred, null), 2, null);
    }

    @NotNull
    public static final <T> SharedFlow<T> a(@NotNull MutableSharedFlow<T> mutableSharedFlow) {
        return new w(mutableSharedFlow, null);
    }

    @NotNull
    public static final <T> StateFlow<T> a(@NotNull MutableStateFlow<T> mutableStateFlow) {
        return new x(mutableStateFlow, null);
    }

    @NotNull
    public static final <T> SharedFlow<T> a(@NotNull SharedFlow<? extends T> sharedFlow, @NotNull Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        return new ag(sharedFlow, function2);
    }
}
