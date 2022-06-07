package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import io.netty.handler.codec.rtsp.RtspHeaders;
import kotlin.Metadata;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.time.Duration;
import kotlin.time.ExperimentalTime;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Delay.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00050\u0004H\u0007\u001a:\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00070\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\b\b\u001a&\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0005H\u0007\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\t\u0010\n\u001a7\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00050\u0004H\u0002¢\u0006\u0002\b\r\u001a$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u0005H\u0000\u001a&\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0005H\u0007\u001a3\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0007H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0017\u0010\n\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0018"}, d2 = {"debounce", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "timeoutMillis", "Lkotlin/Function1;", "", RtspHeaders.Values.TIMEOUT, "Lkotlin/time/Duration;", "debounceDuration", "debounce-HG0u8IE", "(Lkotlinx/coroutines/flow/Flow;J)Lkotlinx/coroutines/flow/Flow;", "debounceInternal", "timeoutMillisSelector", "debounceInternal$FlowKt__DelayKt", "fixedPeriodTicker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "", "Lkotlinx/coroutines/CoroutineScope;", "delayMillis", "initialDelayMillis", com.xiaomi.onetrack.a.a.e, "periodMillis", "period", "sample-HG0u8IE", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class l {

    /* compiled from: Delay.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a extends Lambda implements Function1<T, Long> {
        final /* synthetic */ long $timeoutMillis;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(long j) {
            super(1);
            this.$timeoutMillis = j;
        }

        public final long a(T t) {
            return this.$timeoutMillis;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Long invoke(Object obj) {
            return Long.valueOf(a(obj));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @FlowPreview
    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow, long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? flow : c(flow, new a(j));
        }
        throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
    }

    @FlowPreview
    @OverloadResolutionByLambdaReturnType
    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow, @NotNull Function1<? super T, Long> function1) {
        return c(flow, function1);
    }

    @FlowPreview
    @ExperimentalTime
    @NotNull
    public static final <T> Flow<T> b(@NotNull Flow<? extends T> flow, long j) {
        return FlowKt.debounce(flow, DelayKt.m1754toDelayMillisLRDsOJo(j));
    }

    /* compiled from: Delay.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "emittedItem"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b extends Lambda implements Function1<T, Long> {
        final /* synthetic */ Function1<T, Duration> $timeout;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        b(Function1<? super T, Duration> function1) {
            super(1);
            this.$timeout = function1;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Long invoke(Object obj) {
            return Long.valueOf(a(obj));
        }

        public final long a(T t) {
            return DelayKt.m1754toDelayMillisLRDsOJo(this.$timeout.invoke(t).m1720unboximpl());
        }
    }

    @FlowPreview
    @JvmName(name = "debounceDuration")
    @NotNull
    @OverloadResolutionByLambdaReturnType
    @ExperimentalTime
    public static final <T> Flow<T> b(@NotNull Flow<? extends T> flow, @NotNull Function1<? super T, Duration> function1) {
        return c(flow, new b(function1));
    }

    /* compiled from: Delay.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1", f = "Delay.kt", i = {}, l = {224, 358}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class c extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T> $this_debounceInternal;
        final /* synthetic */ Function1<T, Long> $timeoutMillisSelector;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        c(Function1<? super T, Long> function1, Flow<? extends T> flow, Continuation<? super c> continuation) {
            super(3, continuation);
            this.$timeoutMillisSelector = function1;
            this.$this_debounceInternal = flow;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            c cVar = new c(this.$timeoutMillisSelector, this.$this_debounceInternal, continuation);
            cVar.L$0 = coroutineScope;
            cVar.L$1 = flowCollector;
            return cVar.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(12:7|29|33|(4:35|(1:40)(1:39)|41|(2:43|44))|45|61|46|(1:48)|49|52|(1:54)|(1:56)(2:57|58)) */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x013e, code lost:
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x013f, code lost:
            r14.handleBuilderException(r0);
         */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00ea  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0123 A[Catch: Throwable -> 0x013e, TryCatch #0 {Throwable -> 0x013e, blocks: (B:46:0x011c, B:48:0x0123, B:49:0x012f), top: B:61:0x011c }] */
        /* JADX WARN: Removed duplicated region for block: B:54:0x014c  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0151 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0152  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r18) {
            /*
                Method dump skipped, instructions count: 364
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.l.c.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: Delay.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$values$1", f = "Delay.kt", i = {}, l = {352}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: kotlinx.coroutines.flow.l$c$c */
        /* loaded from: classes5.dex */
        public static final class C0375c extends SuspendLambda implements Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object> {
            final /* synthetic */ Flow<T> $this_debounceInternal;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C0375c(Flow<? extends T> flow, Continuation<? super C0375c> continuation) {
                super(2, continuation);
                this.$this_debounceInternal = flow;
            }

            @Nullable
            /* renamed from: a */
            public final Object invoke(@NotNull ProducerScope<Object> producerScope, @Nullable Continuation<? super Unit> continuation) {
                return ((C0375c) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C0375c cVar = new C0375c(this.$this_debounceInternal, continuation);
                cVar.L$0 = obj;
                return cVar;
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
                        if (this.$this_debounceInternal.collect(new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$values$1$invokeSuspend$$inlined$collect$1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            @Nullable
                            public Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
                                ProducerScope producerScope2 = ProducerScope.this;
                                Symbol symbol = t;
                                if (t == 0) {
                                    symbol = NullSurrogateKt.NULL;
                                }
                                Object send = producerScope2.send(symbol == 1 ? 1 : 0, continuation);
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

        /* compiled from: Delay.kt */
        @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$1", f = "Delay.kt", i = {}, l = {235}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes5.dex */
        public static final class a extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
            final /* synthetic */ FlowCollector<T> $downstream;
            final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            a(FlowCollector<? super T> flowCollector, Ref.ObjectRef<Object> objectRef, Continuation<? super a> continuation) {
                super(1, continuation);
                this.$downstream = flowCollector;
                this.$lastValue = objectRef;
            }

            @Nullable
            /* renamed from: a */
            public final Object invoke(@Nullable Continuation<? super Unit> continuation) {
                return ((a) create(continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@NotNull Continuation<?> continuation) {
                return new a(this.$downstream, this.$lastValue, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        FlowCollector<T> flowCollector = this.$downstream;
                        Symbol symbol = NullSurrogateKt.NULL;
                        Object obj2 = this.$lastValue.element;
                        if (obj2 == symbol) {
                            obj2 = null;
                        }
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        break;
                    case 1:
                        ResultKt.throwOnFailure(obj);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this.$lastValue.element = null;
                return Unit.INSTANCE;
            }
        }

        /* compiled from: Delay.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, com.xiaomi.onetrack.api.b.p, "Lkotlinx/coroutines/channels/ChannelResult;", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2", f = "Delay.kt", i = {}, l = {245}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes5.dex */
        public static final class b extends SuspendLambda implements Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object> {
            final /* synthetic */ FlowCollector<T> $downstream;
            final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
            /* synthetic */ Object L$0;
            Object L$1;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            b(Ref.ObjectRef<Object> objectRef, FlowCollector<? super T> flowCollector, Continuation<? super b> continuation) {
                super(2, continuation);
                this.$lastValue = objectRef;
                this.$downstream = flowCollector;
            }

            @Nullable
            public final Object a(@NotNull Object obj, @Nullable Continuation<? super Unit> continuation) {
                return ((b) create(ChannelResult.m1766boximpl(obj), continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                b bVar = new b(this.$lastValue, this.$downstream, continuation);
                bVar.L$0 = obj;
                return bVar;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* synthetic */ Object invoke(ChannelResult<? extends Object> channelResult, Continuation<? super Unit> continuation) {
                return a(channelResult.m1778unboximpl(), continuation);
            }

            /* JADX WARN: Type inference failed for: r6v2, types: [kotlinx.coroutines.internal.Symbol, T] */
            /* JADX WARN: Type inference failed for: r6v5, types: [T, java.lang.Object] */
            /* JADX WARN: Unknown variable types count: 1 */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @org.jetbrains.annotations.Nullable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r6) {
                /*
                    r5 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r1 = r5.label
                    switch(r1) {
                        case 0: goto L_0x001c;
                        case 1: goto L_0x0011;
                        default: goto L_0x0009;
                    }
                L_0x0009:
                    java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r6.<init>(r0)
                    throw r6
                L_0x0011:
                    java.lang.Object r0 = r5.L$1
                    kotlin.jvm.internal.Ref$ObjectRef r0 = (kotlin.jvm.internal.Ref.ObjectRef) r0
                    java.lang.Object r1 = r5.L$0
                    kotlin.ResultKt.throwOnFailure(r6)
                    r1 = r0
                    goto L_0x0054
                L_0x001c:
                    kotlin.ResultKt.throwOnFailure(r6)
                    java.lang.Object r6 = r5.L$0
                    kotlinx.coroutines.channels.ChannelResult r6 = (kotlinx.coroutines.channels.ChannelResult) r6
                    java.lang.Object r6 = r6.m1778unboximpl()
                    kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r1 = r5.$lastValue
                    boolean r2 = r6 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
                    if (r2 != 0) goto L_0x002f
                    r1.element = r6
                L_0x002f:
                    kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r1 = r5.$lastValue
                    kotlinx.coroutines.flow.FlowCollector<T> r3 = r5.$downstream
                    if (r2 == 0) goto L_0x005a
                    java.lang.Throwable r2 = kotlinx.coroutines.channels.ChannelResult.m1770exceptionOrNullimpl(r6)
                    if (r2 != 0) goto L_0x0059
                    T r2 = r1.element
                    if (r2 == 0) goto L_0x0054
                    kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
                    T r4 = r1.element
                    if (r4 != r2) goto L_0x0046
                    r4 = 0
                L_0x0046:
                    r5.L$0 = r6
                    r5.L$1 = r1
                    r6 = 1
                    r5.label = r6
                    java.lang.Object r6 = r3.emit(r4, r5)
                    if (r6 != r0) goto L_0x0054
                    return r0
                L_0x0054:
                    kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
                    r1.element = r6
                    goto L_0x005a
                L_0x0059:
                    throw r2
                L_0x005a:
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.l.c.b.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    private static final <T> Flow<T> c(Flow<? extends T> flow, Function1<? super T, Long> function1) {
        return FlowCoroutineKt.scopedFlow(new c(function1, flow, null));
    }

    /* compiled from: Delay.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2", f = "Delay.kt", i = {0, 0, 0, 0}, l = {355}, m = "invokeSuspend", n = {"downstream", "values", "lastValue", "ticker"}, s = {"L$0", "L$1", "L$2", "L$3"})
    /* loaded from: classes5.dex */
    public static final class e extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $periodMillis;
        final /* synthetic */ Flow<T> $this_sample;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        e(long j, Flow<? extends T> flow, Continuation<? super e> continuation) {
            super(3, continuation);
            this.$periodMillis = j;
            this.$this_sample = flow;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            e eVar = new e(this.$periodMillis, this.$this_sample, continuation);
            eVar.L$0 = coroutineScope;
            eVar.L$1 = flowCollector;
            return eVar.invokeSuspend(Unit.INSTANCE);
        }

        /* compiled from: Delay.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1", f = "Delay.kt", i = {}, l = {352}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes5.dex */
        public static final class c extends SuspendLambda implements Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object> {
            final /* synthetic */ Flow<T> $this_sample;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            c(Flow<? extends T> flow, Continuation<? super c> continuation) {
                super(2, continuation);
                this.$this_sample = flow;
            }

            @Nullable
            /* renamed from: a */
            public final Object invoke(@NotNull ProducerScope<Object> producerScope, @Nullable Continuation<? super Unit> continuation) {
                return ((c) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                c cVar = new c(this.$this_sample, continuation);
                cVar.L$0 = obj;
                return cVar;
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
                        if (this.$this_sample.collect(new FlowCollector<T>() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$values$1$invokeSuspend$$inlined$collect$1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            @Nullable
                            public Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
                                ProducerScope producerScope2 = ProducerScope.this;
                                Symbol symbol = t;
                                if (t == 0) {
                                    symbol = NullSurrogateKt.NULL;
                                }
                                Object send = producerScope2.send(symbol == 1 ? 1 : 0, continuation);
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
            e eVar;
            FlowCollector flowCollector;
            ReceiveChannel receiveChannel;
            Ref.ObjectRef objectRef;
            ReceiveChannel receiveChannel2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    flowCollector = (FlowCollector) this.L$1;
                    receiveChannel = ProduceKt.produce$default(coroutineScope, null, -1, new c(this.$this_sample, null), 1, null);
                    Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                    receiveChannel2 = l.a(coroutineScope, this.$periodMillis, 0L, 2, null);
                    objectRef = objectRef2;
                    eVar = this;
                    break;
                case 1:
                    receiveChannel2 = (ReceiveChannel) this.L$3;
                    objectRef = (Ref.ObjectRef) this.L$2;
                    receiveChannel = (ReceiveChannel) this.L$1;
                    flowCollector = (FlowCollector) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    eVar = this;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            while (objectRef.element != NullSurrogateKt.DONE) {
                eVar.L$0 = flowCollector;
                eVar.L$1 = receiveChannel;
                eVar.L$2 = objectRef;
                eVar.L$3 = receiveChannel2;
                eVar.label = 1;
                e eVar2 = eVar;
                SelectBuilderImpl selectBuilderImpl = new SelectBuilderImpl(eVar2);
                try {
                    SelectBuilderImpl selectBuilderImpl2 = selectBuilderImpl;
                    selectBuilderImpl2.invoke(receiveChannel.getOnReceiveCatching(), new a(objectRef, receiveChannel2, null));
                    selectBuilderImpl2.invoke(receiveChannel2.getOnReceive(), new b(objectRef, flowCollector, null));
                } catch (Throwable th) {
                    selectBuilderImpl.handleBuilderException(th);
                }
                Object result = selectBuilderImpl.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(eVar2);
                    continue;
                }
                if (result == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }

        /* compiled from: Delay.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "result", "Lkotlinx/coroutines/channels/ChannelResult;", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$1", f = "Delay.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes5.dex */
        public static final class a extends SuspendLambda implements Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
            final /* synthetic */ ReceiveChannel<Unit> $ticker;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(Ref.ObjectRef<Object> objectRef, ReceiveChannel<Unit> receiveChannel, Continuation<? super a> continuation) {
                super(2, continuation);
                this.$lastValue = objectRef;
                this.$ticker = receiveChannel;
            }

            @Nullable
            public final Object a(@NotNull Object obj, @Nullable Continuation<? super Unit> continuation) {
                return ((a) create(ChannelResult.m1766boximpl(obj), continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                a aVar = new a(this.$lastValue, this.$ticker, continuation);
                aVar.L$0 = obj;
                return aVar;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* synthetic */ Object invoke(ChannelResult<? extends Object> channelResult, Continuation<? super Unit> continuation) {
                return a(channelResult.m1778unboximpl(), continuation);
            }

            /* JADX WARN: Type inference failed for: r4v4, types: [T, java.lang.Object] */
            /* JADX WARN: Type inference failed for: r4v9, types: [kotlinx.coroutines.internal.Symbol, T] */
            /* JADX WARN: Unknown variable types count: 1 */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @org.jetbrains.annotations.Nullable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r4) {
                /*
                    r3 = this;
                    kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r0 = r3.label
                    if (r0 != 0) goto L_0x0039
                    kotlin.ResultKt.throwOnFailure(r4)
                    java.lang.Object r4 = r3.L$0
                    kotlinx.coroutines.channels.ChannelResult r4 = (kotlinx.coroutines.channels.ChannelResult) r4
                    java.lang.Object r4 = r4.m1778unboximpl()
                    kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r0 = r3.$lastValue
                    boolean r1 = r4 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
                    if (r1 != 0) goto L_0x001a
                    r0.element = r4
                L_0x001a:
                    kotlinx.coroutines.channels.ReceiveChannel<kotlin.Unit> r0 = r3.$ticker
                    kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r2 = r3.$lastValue
                    if (r1 == 0) goto L_0x0036
                    java.lang.Throwable r4 = kotlinx.coroutines.channels.ChannelResult.m1770exceptionOrNullimpl(r4)
                    if (r4 != 0) goto L_0x0035
                    kotlinx.coroutines.flow.internal.ChildCancelledException r4 = new kotlinx.coroutines.flow.internal.ChildCancelledException
                    r4.<init>()
                    java.util.concurrent.CancellationException r4 = (java.util.concurrent.CancellationException) r4
                    r0.cancel(r4)
                    kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
                    r2.element = r4
                    goto L_0x0036
                L_0x0035:
                    throw r4
                L_0x0036:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                L_0x0039:
                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r4.<init>(r0)
                    throw r4
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.l.e.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* compiled from: Delay.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2$1$2", f = "Delay.kt", i = {}, l = {302}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes5.dex */
        public static final class b extends SuspendLambda implements Function2<Unit, Continuation<? super Unit>, Object> {
            final /* synthetic */ FlowCollector<T> $downstream;
            final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            b(Ref.ObjectRef<Object> objectRef, FlowCollector<? super T> flowCollector, Continuation<? super b> continuation) {
                super(2, continuation);
                this.$lastValue = objectRef;
                this.$downstream = flowCollector;
            }

            @Nullable
            /* renamed from: a */
            public final Object invoke(@NotNull Unit unit, @Nullable Continuation<? super Unit> continuation) {
                return ((b) create(unit, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new b(this.$lastValue, this.$downstream, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        Object obj2 = this.$lastValue.element;
                        if (obj2 == null) {
                            return Unit.INSTANCE;
                        }
                        this.$lastValue.element = null;
                        FlowCollector<T> flowCollector = this.$downstream;
                        if (obj2 == NullSurrogateKt.NULL) {
                            obj2 = null;
                        }
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutine_suspended) {
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
    }

    @FlowPreview
    @NotNull
    public static final <T> Flow<T> c(@NotNull Flow<? extends T> flow, long j) {
        if (j > 0) {
            return FlowCoroutineKt.scopedFlow(new e(j, flow, null));
        }
        throw new IllegalArgumentException("Sample period should be positive".toString());
    }

    public static /* synthetic */ ReceiveChannel a(CoroutineScope coroutineScope, long j, long j2, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        return FlowKt.fixedPeriodTicker(coroutineScope, j, j2);
    }

    /* compiled from: Delay.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3", f = "Delay.kt", i = {0, 1, 2}, l = {316, 318, 319}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "$this$produce"}, s = {"L$0", "L$0", "L$0"})
    /* loaded from: classes5.dex */
    public static final class d extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $delayMillis;
        final /* synthetic */ long $initialDelayMillis;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(long j, long j2, Continuation<? super d> continuation) {
            super(2, continuation);
            this.$initialDelayMillis = j;
            this.$delayMillis = j2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super Unit> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((d) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            d dVar = new d(this.$initialDelayMillis, this.$delayMillis, continuation);
            dVar.L$0 = obj;
            return dVar;
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:56)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:30)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:18)
            */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0059 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:18:0x006a A[RETURN] */
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
                    case 0: goto L_0x002b;
                    case 1: goto L_0x0023;
                    case 2: goto L_0x001a;
                    case 3: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L_0x0011:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                kotlin.ResultKt.throwOnFailure(r7)
                r7 = r6
                goto L_0x0045
            L_0x001a:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                kotlin.ResultKt.throwOnFailure(r7)
                r7 = r6
                goto L_0x005a
            L_0x0023:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x0044
            L_0x002b:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                r1 = r7
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                long r2 = r6.$initialDelayMillis
                r7 = r6
                kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                r6.L$0 = r1
                r4 = 1
                r6.label = r4
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r2, r7)
                if (r7 != r0) goto L_0x0044
                return r0
            L_0x0044:
                r7 = r6
            L_0x0045:
                kotlinx.coroutines.channels.SendChannel r2 = r1.getChannel()
                kotlin.Unit r3 = kotlin.Unit.INSTANCE
                r4 = r7
                kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                r7.L$0 = r1
                r5 = 2
                r7.label = r5
                java.lang.Object r2 = r2.send(r3, r4)
                if (r2 != r0) goto L_0x005a
                return r0
            L_0x005a:
                long r2 = r7.$delayMillis
                r4 = r7
                kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                r7.L$0 = r1
                r5 = 3
                r7.label = r5
                java.lang.Object r2 = kotlinx.coroutines.DelayKt.delay(r2, r4)
                if (r2 != r0) goto L_0x0045
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.l.d.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public static final ReceiveChannel<Unit> a(@NotNull CoroutineScope coroutineScope, long j, long j2) {
        boolean z = true;
        if (j >= 0) {
            if (j2 < 0) {
                z = false;
            }
            if (z) {
                return ProduceKt.produce$default(coroutineScope, null, 0, new d(j2, j, null), 1, null);
            }
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
    }

    @FlowPreview
    @ExperimentalTime
    @NotNull
    public static final <T> Flow<T> d(@NotNull Flow<? extends T> flow, long j) {
        return FlowKt.sample(flow, DelayKt.m1754toDelayMillisLRDsOJo(j));
    }
}
