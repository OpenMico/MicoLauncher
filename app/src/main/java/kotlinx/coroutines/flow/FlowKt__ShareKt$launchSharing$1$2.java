package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Share.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it", "Lkotlinx/coroutines/flow/SharingCommand;"}, k = 3, mv = {1, 5, 1}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2", f = "Share.kt", i = {}, l = {219}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes5.dex */
public final class FlowKt__ShareKt$launchSharing$1$2 extends SuspendLambda implements Function2<SharingCommand, Continuation<? super Unit>, Object> {
    final /* synthetic */ T $initialValue;
    final /* synthetic */ MutableSharedFlow<T> $shared;
    final /* synthetic */ Flow<T> $upstream;
    /* synthetic */ Object L$0;
    int label;

    /* compiled from: Share.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SharingCommand.values().length];
            iArr[SharingCommand.START.ordinal()] = 1;
            iArr[SharingCommand.STOP.ordinal()] = 2;
            iArr[SharingCommand.STOP_AND_RESET_REPLAY_CACHE.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__ShareKt$launchSharing$1$2(Flow<? extends T> flow, MutableSharedFlow<T> mutableSharedFlow, T t, Continuation<? super FlowKt__ShareKt$launchSharing$1$2> continuation) {
        super(2, continuation);
        this.$upstream = flow;
        this.$shared = mutableSharedFlow;
        this.$initialValue = t;
    }

    @Nullable
    /* renamed from: a */
    public final Object invoke(@NotNull SharingCommand sharingCommand, @Nullable Continuation<? super Unit> continuation) {
        return ((FlowKt__ShareKt$launchSharing$1$2) create(sharingCommand, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        FlowKt__ShareKt$launchSharing$1$2 flowKt__ShareKt$launchSharing$1$2 = new FlowKt__ShareKt$launchSharing$1$2(this.$upstream, this.$shared, this.$initialValue, continuation);
        flowKt__ShareKt$launchSharing$1$2.L$0 = obj;
        return flowKt__ShareKt$launchSharing$1$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                switch (WhenMappings.$EnumSwitchMapping$0[((SharingCommand) this.L$0).ordinal()]) {
                    case 1:
                        this.label = 1;
                        if (this.$upstream.collect(this.$shared, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        break;
                    case 3:
                        if (this.$initialValue != SharedFlowKt.NO_VALUE) {
                            this.$shared.tryEmit(this.$initialValue);
                            break;
                        } else {
                            this.$shared.resetReplayCache();
                            break;
                        }
                }
            case 1:
                ResultKt.throwOnFailure(obj);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
