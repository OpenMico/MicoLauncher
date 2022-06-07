package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.idm.service.iot.LightService;
import com.xiaomi.onetrack.api.b;
import java.util.Iterator;
import kotlin.BuilderInference;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Builders.kt */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\u001c\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aK\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\n\u001aK\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\n\u001a\u0012\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001aK\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\n\u001a\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0010\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0011\u001a+\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0013\"\u0002H\u0002¢\u0006\u0002\u0010\u0014\u001aT\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0016\u001a\u00020\u001724\b\u0001\u0010\u0003\u001a.\u0012\u0004\u0012\u00020\u0018\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u0002H\u00020\u0019¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u00070\u0004¢\u0006\u0002\b\tH\u0007\u001a\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u001eH\u0007\u001a!\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0013¢\u0006\u0002\u0010\u0014\u001a\u0010\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u0001*\u00020\u001f\u001a\u0010\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020 0\u0001*\u00020!\u001a\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\"\u001a\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020#\u001a\u0010\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u0001*\u00020$\u001a\u0010\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020 0\u0001*\u00020%\u001a\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020&\u001a6\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"}, d2 = {"callbackFlow", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "channelFlow", "emptyFlow", LightService.LightPropertyCommand.FLOW, "Lkotlinx/coroutines/flow/FlowCollector;", "flowOf", b.p, "(Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "elements", "", "([Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "flowViaChannel", "bufferSize", "", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/ParameterName;", "name", "channel", "asFlow", "Lkotlin/Function0;", "", "", "", "", "", "Lkotlin/ranges/IntRange;", "Lkotlin/ranges/LongRange;", "Lkotlin/sequences/Sequence;", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class g {
    @NotNull
    public static final <T> Flow<T> a(@BuilderInference @NotNull Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        return new y(function2);
    }

    @NotNull
    public static final <T> Flow<T> a() {
        return f.a;
    }

    public static /* synthetic */ Flow a(int i, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        return FlowKt.flowViaChannel(i, function2);
    }

    /* compiled from: Builders.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__BuildersKt$flowViaChannel$1", f = "Builders.kt", i = {}, l = {216}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class a extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function2<CoroutineScope, SendChannel<? super T>, Unit> $block;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        a(Function2<? super CoroutineScope, ? super SendChannel<? super T>, Unit> function2, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$block = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super T> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            a aVar = new a(this.$block, continuation);
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
                    ProducerScope producerScope = (ProducerScope) this.L$0;
                    this.$block.invoke(producerScope, producerScope.getChannel());
                    this.label = 1;
                    if (ProduceKt.awaitClose$default(producerScope, null, this, 1, null) == coroutine_suspended) {
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

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use channelFlow with awaitClose { } instead of flowViaChannel and invokeOnClose { }.")
    @FlowPreview
    @NotNull
    public static final <T> Flow<T> a(int i, @BuilderInference @NotNull Function2<? super CoroutineScope, ? super SendChannel<? super T>, Unit> function2) {
        Flow<T> a2;
        a2 = j.a(FlowKt.channelFlow(new a(function2, null)), i, null, 2, null);
        return a2;
    }

    @NotNull
    public static final <T> Flow<T> b(@BuilderInference @NotNull Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        return new d(function2, null, 0, null, 14, null);
    }

    @NotNull
    public static final <T> Flow<T> c(@BuilderInference @NotNull Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        return new a(function2, null, 0, null, 14, null);
    }

    @FlowPreview
    @NotNull
    public static final <T> Flow<T> a(@NotNull final Function0<? extends T> function0) {
        return new Flow<T>() { // from class: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super T> flowCollector, @NotNull Continuation<? super Unit> continuation) {
                Object emit = flowCollector.emit(Function0.this.invoke(), continuation);
                return emit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
            }
        };
    }

    @FlowPreview
    @NotNull
    public static final <T> Flow<T> a(@NotNull Function1<? super Continuation<? super T>, ? extends Object> function1) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$2(function1);
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull Iterable<? extends T> iterable) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3(iterable);
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull Iterator<? extends T> it) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$4(it);
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull Sequence<? extends T> sequence) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$5(sequence);
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull T... tArr) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(tArr);
    }

    @NotNull
    public static final <T> Flow<T> a(final T t) {
        return new Flow<T>() { // from class: kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super T> flowCollector, @NotNull Continuation<? super Unit> continuation) {
                Object emit = flowCollector.emit(t, continuation);
                return emit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emit : Unit.INSTANCE;
            }
        };
    }

    @NotNull
    public static final <T> Flow<T> b(@NotNull T[] tArr) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$6(tArr);
    }

    @NotNull
    public static final Flow<Integer> a(@NotNull int[] iArr) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7(iArr);
    }

    @NotNull
    public static final Flow<Long> a(@NotNull long[] jArr) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$8(jArr);
    }

    @NotNull
    public static final Flow<Integer> a(@NotNull IntRange intRange) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$9(intRange);
    }

    @NotNull
    public static final Flow<Long> a(@NotNull LongRange longRange) {
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10(longRange);
    }
}
