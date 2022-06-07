package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import com.xiaomi.idm.service.iot.LightService;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SharedFlow.kt */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0012\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\b\u0012\u0004\u0012\u0002H\u00010\u0006:\u0001bB\u001d\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0019\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0003H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020'2\u0006\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020'H\u0002J\u001f\u0010.\u001a\u00020'2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u000000H\u0096@ø\u0001\u0000¢\u0006\u0002\u00101J\u0010\u00102\u001a\u00020'2\u0006\u00103\u001a\u00020\u0012H\u0002J\b\u00104\u001a\u00020\u0003H\u0014J\u001d\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000e2\u0006\u00106\u001a\u00020\bH\u0014¢\u0006\u0002\u00107J\b\u00108\u001a\u00020'H\u0002J\u0019\u00109\u001a\u00020'2\u0006\u0010:\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010;J\u0019\u0010<\u001a\u00020'2\u0006\u0010:\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010;J\u0012\u0010=\u001a\u00020'2\b\u0010>\u001a\u0004\u0018\u00010\u000fH\u0002J1\u0010?\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020'\u0018\u00010@0\u000e2\u0014\u0010A\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020'\u0018\u00010@0\u000eH\u0002¢\u0006\u0002\u0010BJ&\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000D2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010H\u001a\u0004\u0018\u00010\u000f2\u0006\u0010I\u001a\u00020\u0012H\u0002J7\u0010J\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0010\u0010K\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000e2\u0006\u0010L\u001a\u00020\b2\u0006\u0010M\u001a\u00020\bH\u0002¢\u0006\u0002\u0010NJ\b\u0010O\u001a\u00020'H\u0016J\u0015\u0010P\u001a\u00020Q2\u0006\u0010:\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010RJ\u0015\u0010S\u001a\u00020Q2\u0006\u0010:\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010RJ\u0015\u0010T\u001a\u00020Q2\u0006\u0010:\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010RJ\u0010\u0010U\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u0003H\u0002J\u0012\u0010V\u001a\u0004\u0018\u00010\u000f2\u0006\u0010(\u001a\u00020\u0003H\u0002J(\u0010W\u001a\u00020'2\u0006\u0010X\u001a\u00020\u00122\u0006\u0010Y\u001a\u00020\u00122\u0006\u0010Z\u001a\u00020\u00122\u0006\u0010[\u001a\u00020\u0012H\u0002J%\u0010\\\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020'\u0018\u00010@0\u000e2\u0006\u0010]\u001a\u00020\u0012H\u0000¢\u0006\u0004\b^\u0010_J\r\u0010`\u001a\u00020\u0012H\u0000¢\u0006\u0002\baR\u001a\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u000e\u0010\u0018\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0014R\u000e\u0010\u001b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006c"}, d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "replay", "", "bufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(IILkotlinx/coroutines/channels/BufferOverflow;)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferEndIndex", "", "getBufferEndIndex", "()J", "bufferSize", "head", "getHead", "minCollectorIndex", "queueEndIndex", "getQueueEndIndex", "queueSize", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "replayIndex", "replaySize", "getReplaySize", "()I", "totalSize", "getTotalSize", "awaitValue", "", "slot", "(Lkotlinx/coroutines/flow/SharedFlowSlot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelEmitter", "emitter", "Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "cleanupTailLocked", "collect", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "correctCollectorIndexesOnDropOldest", "newHead", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/SharedFlowSlot;", "dropOldestLocked", "emit", com.xiaomi.onetrack.api.b.p, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitSuspend", "enqueueLocked", "item", "findSlotsToResumeLocked", "Lkotlin/coroutines/Continuation;", "resumesIn", "([Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;", "fuse", "Lkotlinx/coroutines/flow/Flow;", c.R, "Lkotlin/coroutines/CoroutineContext;", "capacity", "getPeekedValueLockedAt", MiSoundBoxCommandExtras.INDEX, "growBuffer", "curBuffer", "curSize", "newSize", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "tryEmitLocked", "tryEmitNoCollectorsLocked", "tryPeekLocked", "tryTakeValue", "updateBufferLocked", "newReplayIndex", "newMinCollectorIndex", "newBufferEndIndex", "newQueueEndIndex", "updateCollectorIndexLocked", "oldIndex", "updateCollectorIndexLocked$kotlinx_coroutines_core", "(J)[Lkotlin/coroutines/Continuation;", "updateNewCollectorIndexLocked", "updateNewCollectorIndexLocked$kotlinx_coroutines_core", "Emitter", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class SharedFlowImpl<T> extends AbstractSharedFlow<z> implements CancellableFlow<T>, MutableSharedFlow<T>, FusibleFlow<T> {
    private final int a;
    private final int b;
    @NotNull
    private final BufferOverflow c;
    @Nullable
    private Object[] d;
    private long e;
    private long f;
    private int g;
    private int h;

    /* compiled from: SharedFlow.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            iArr[BufferOverflow.DROP_LATEST.ordinal()] = 2;
            iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: SharedFlow.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.SharedFlowImpl", f = "SharedFlow.kt", i = {0, 0, 0}, l = {341, 348, 351}, m = "collect", n = {"this", "collector", "slot"}, s = {"L$0", "L$1", "L$2"})
    /* loaded from: classes5.dex */
    public static final class b extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ SharedFlowImpl<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(SharedFlowImpl<T> sharedFlowImpl, Continuation<? super b> continuation) {
            super(continuation);
            this.this$0 = sharedFlowImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.collect(null, this);
        }
    }

    public SharedFlowImpl(int i, int i2, @NotNull BufferOverflow bufferOverflow) {
        this.a = i;
        this.b = i2;
        this.c = bufferOverflow;
    }

    public final long c() {
        return Math.min(this.f, this.e);
    }

    private final int d() {
        return (int) ((c() + this.g) - this.e);
    }

    public final int e() {
        return this.g + this.h;
    }

    private final long f() {
        return c() + this.g;
    }

    private final long g() {
        return c() + this.g + this.h;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00bb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00a3 A[SYNTHETIC] */
    @Override // kotlinx.coroutines.flow.Flow
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object collect(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.FlowCollector<? super T> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public boolean tryEmit(T t) {
        int i;
        boolean z;
        Continuation<Unit>[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            i = 0;
            if (a((SharedFlowImpl<T>) t)) {
                continuationArr = a(continuationArr);
                z = true;
            } else {
                z = false;
            }
        }
        int length = continuationArr.length;
        while (i < length) {
            Continuation<Unit> continuation = continuationArr[i];
            i++;
            if (continuation != null) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                continuation.resumeWith(Result.m1220constructorimpl(unit));
            }
        }
        return z;
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow, kotlinx.coroutines.flow.FlowCollector
    @Nullable
    public Object emit(T t, @NotNull Continuation<? super Unit> continuation) {
        Object a2;
        return (!tryEmit(t) && (a2 = a((SharedFlowImpl<T>) t, continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? a2 : Unit.INSTANCE;
    }

    public final boolean a(T t) {
        if (getNCollectors() == 0) {
            return b((SharedFlowImpl<T>) t);
        }
        if (this.g >= this.b && this.f <= this.e) {
            switch (WhenMappings.$EnumSwitchMapping$0[this.c.ordinal()]) {
                case 1:
                    return false;
                case 2:
                    return true;
            }
        }
        c(t);
        this.g++;
        if (this.g > this.b) {
            h();
        }
        if (d() > this.a) {
            a(this.e + 1, this.f, f(), g());
        }
        return true;
    }

    private final boolean b(T t) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getNCollectors() == 0)) {
                throw new AssertionError();
            }
        }
        if (this.a == 0) {
            return true;
        }
        c(t);
        this.g++;
        if (this.g > this.a) {
            h();
        }
        this.f = c() + this.g;
        return true;
    }

    private final void h() {
        Object[] objArr = this.d;
        Intrinsics.checkNotNull(objArr);
        SharedFlowKt.a(objArr, c(), null);
        this.g--;
        long c = c() + 1;
        if (this.e < c) {
            this.e = c;
        }
        if (this.f < c) {
            b(c);
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(c() == c)) {
                throw new AssertionError();
            }
        }
    }

    private final void b(long j) {
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        SharedFlowImpl<T> sharedFlowImpl = this;
        if (!(((AbstractSharedFlow) sharedFlowImpl).b == 0 || (abstractSharedFlowSlotArr = ((AbstractSharedFlow) sharedFlowImpl).a) == null)) {
            for (AbstractSharedFlowSlot abstractSharedFlowSlot : abstractSharedFlowSlotArr) {
                if (abstractSharedFlowSlot != null) {
                    z zVar = (z) abstractSharedFlowSlot;
                    if (zVar.a >= 0 && zVar.a < j) {
                        zVar.a = j;
                    }
                }
            }
        }
        this.f = j;
    }

    public final void c(Object obj) {
        int e = e();
        Object[] objArr = this.d;
        if (objArr == null) {
            objArr = a(null, 0, 2);
        } else if (e >= objArr.length) {
            objArr = a(objArr, e, objArr.length * 2);
        }
        SharedFlowKt.a(objArr, c() + e, obj);
    }

    private final Object[] a(Object[] objArr, int i, int i2) {
        Object a2;
        int i3 = 0;
        if (i2 > 0) {
            Object[] objArr2 = new Object[i2];
            this.d = objArr2;
            if (objArr == null) {
                return objArr2;
            }
            long c = c();
            if (i > 0) {
                while (true) {
                    int i4 = i3 + 1;
                    long j = i3 + c;
                    a2 = SharedFlowKt.a(objArr, j);
                    SharedFlowKt.a(objArr2, j, a2);
                    if (i4 >= i) {
                        break;
                    }
                    i3 = i4;
                }
            }
            return objArr2;
        }
        throw new IllegalStateException("Buffer size overflow".toString());
    }

    public final long a() {
        long j = this.e;
        if (j < this.f) {
            this.f = j;
        }
        return j;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0151  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] a(long r23) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.a(long):kotlin.coroutines.Continuation[]");
    }

    private final void a(long j, long j2, long j3, long j4) {
        long min = Math.min(j2, j);
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(min >= c())) {
                throw new AssertionError();
            }
        }
        long c = c();
        if (c < min) {
            while (true) {
                long j5 = 1 + c;
                Object[] objArr = this.d;
                Intrinsics.checkNotNull(objArr);
                SharedFlowKt.a(objArr, c, null);
                if (j5 >= min) {
                    break;
                }
                c = j5;
            }
        }
        this.e = j;
        this.f = j2;
        this.g = (int) (j3 - min);
        this.h = (int) (j4 - j3);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(this.g >= 0)) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(this.h >= 0)) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (this.e > c() + this.g) {
                z = false;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0013, code lost:
        r1 = kotlinx.coroutines.flow.SharedFlowKt.a(r0, (c() + e()) - 1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void i() {
        /*
            r5 = this;
            int r0 = r5.b
            if (r0 != 0) goto L_0x000a
            int r0 = r5.h
            r1 = 1
            if (r0 > r1) goto L_0x000a
            return
        L_0x000a:
            java.lang.Object[] r0 = r5.d
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
        L_0x000f:
            int r1 = r5.h
            if (r1 <= 0) goto L_0x003d
            long r1 = r5.c()
            int r3 = r5.e()
            long r3 = (long) r3
            long r1 = r1 + r3
            r3 = 1
            long r1 = r1 - r3
            java.lang.Object r1 = kotlinx.coroutines.flow.SharedFlowKt.access$getBufferAt(r0, r1)
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            if (r1 != r2) goto L_0x003d
            int r1 = r5.h
            int r1 = r1 + (-1)
            r5.h = r1
            long r1 = r5.c()
            int r3 = r5.e()
            long r3 = (long) r3
            long r1 = r1 + r3
            r3 = 0
            kotlinx.coroutines.flow.SharedFlowKt.access$setBufferAt(r0, r1, r3)
            goto L_0x000f
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.i():void");
    }

    private final Object a(z zVar) {
        Object obj;
        Continuation<Unit>[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            long b2 = b(zVar);
            if (b2 < 0) {
                obj = SharedFlowKt.NO_VALUE;
            } else {
                long j = zVar.a;
                Object c = c(b2);
                zVar.a = b2 + 1;
                continuationArr = a(j);
                obj = c;
            }
        }
        int i = 0;
        int length = continuationArr.length;
        while (i < length) {
            Continuation<Unit> continuation = continuationArr[i];
            i++;
            if (continuation != null) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                continuation.resumeWith(Result.m1220constructorimpl(unit));
            }
        }
        return obj;
    }

    public final long b(z zVar) {
        long j = zVar.a;
        if (j < f()) {
            return j;
        }
        if (this.b <= 0 && j <= c() && this.h != 0) {
            return j;
        }
        return -1L;
    }

    private final Object c(long j) {
        Object a2;
        Object[] objArr = this.d;
        Intrinsics.checkNotNull(objArr);
        a2 = SharedFlowKt.a(objArr, j);
        return a2 instanceof a ? ((a) a2).c : a2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v9, types: [java.lang.Object[], java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] a(kotlin.coroutines.Continuation<kotlin.Unit>[] r11) {
        /*
            r10 = this;
            int r0 = r11.length
            r1 = r10
            kotlinx.coroutines.flow.internal.AbstractSharedFlow r1 = (kotlinx.coroutines.flow.internal.AbstractSharedFlow) r1
            int r2 = kotlinx.coroutines.flow.internal.AbstractSharedFlow.access$getNCollectors(r1)
            if (r2 != 0) goto L_0x000b
            goto L_0x0050
        L_0x000b:
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot[] r1 = kotlinx.coroutines.flow.internal.AbstractSharedFlow.access$getSlots(r1)
            if (r1 != 0) goto L_0x0012
            goto L_0x0050
        L_0x0012:
            int r2 = r1.length
            r3 = 0
        L_0x0014:
            if (r3 >= r2) goto L_0x0050
            r4 = r1[r3]
            if (r4 == 0) goto L_0x004d
            kotlinx.coroutines.flow.z r4 = (kotlinx.coroutines.flow.z) r4
            kotlin.coroutines.Continuation<? super kotlin.Unit> r5 = r4.b
            if (r5 != 0) goto L_0x0021
            goto L_0x004d
        L_0x0021:
            long r6 = r10.b(r4)
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 >= 0) goto L_0x002c
            goto L_0x004d
        L_0x002c:
            r6 = r11
            java.lang.Object[] r6 = (java.lang.Object[]) r6
            int r7 = r6.length
            if (r0 < r7) goto L_0x0042
            int r11 = r6.length
            r7 = 2
            int r11 = r11 * r7
            int r11 = java.lang.Math.max(r7, r11)
            java.lang.Object[] r11 = java.util.Arrays.copyOf(r6, r11)
            java.lang.String r6 = "java.util.Arrays.copyOf(this, newSize)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r6)
        L_0x0042:
            r6 = r11
            kotlin.coroutines.Continuation[] r6 = (kotlin.coroutines.Continuation[]) r6
            int r7 = r0 + 1
            r6[r0] = r5
            r0 = 0
            r4.b = r0
            r0 = r7
        L_0x004d:
            int r3 = r3 + 1
            goto L_0x0014
        L_0x0050:
            kotlin.coroutines.Continuation[] r11 = (kotlin.coroutines.Continuation[]) r11
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.a(kotlin.coroutines.Continuation[]):kotlin.coroutines.Continuation[]");
    }

    @NotNull
    /* renamed from: b */
    public z createSlot() {
        return new z();
    }

    @NotNull
    /* renamed from: a */
    public z[] createSlotArray(int i) {
        return new z[i];
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    @NotNull
    public Flow<T> fuse(@NotNull CoroutineContext coroutineContext, int i, @NotNull BufferOverflow bufferOverflow) {
        return SharedFlowKt.fuseSharedFlow(this, coroutineContext, i, bufferOverflow);
    }

    /* compiled from: SharedFlow.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B1\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\nH\u0016R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "Lkotlinx/coroutines/DisposableHandle;", LightService.LightPropertyCommand.FLOW, "Lkotlinx/coroutines/flow/SharedFlowImpl;", MiSoundBoxCommandExtras.INDEX, "", com.xiaomi.onetrack.api.b.p, "", "cont", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;JLjava/lang/Object;Lkotlin/coroutines/Continuation;)V", "dispose", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a implements DisposableHandle {
        @JvmField
        @NotNull
        public final SharedFlowImpl<?> a;
        @JvmField
        public long b;
        @JvmField
        @Nullable
        public final Object c;
        @JvmField
        @NotNull
        public final Continuation<Unit> d;

        /* JADX WARN: Multi-variable type inference failed */
        public a(@NotNull SharedFlowImpl<?> sharedFlowImpl, long j, @Nullable Object obj, @NotNull Continuation<? super Unit> continuation) {
            this.a = sharedFlowImpl;
            this.b = j;
            this.c = obj;
            this.d = continuation;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public void dispose() {
            this.a.a(this);
        }
    }

    @Override // kotlinx.coroutines.flow.SharedFlow
    @NotNull
    public List<T> getReplayCache() {
        Object a2;
        synchronized (this) {
            int d = d();
            if (d == 0) {
                return CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList(d);
            Object[] objArr = this.d;
            Intrinsics.checkNotNull(objArr);
            int i = 0;
            if (d > 0) {
                while (true) {
                    int i2 = i + 1;
                    a2 = SharedFlowKt.a(objArr, this.e + i);
                    arrayList.add(a2);
                    if (i2 >= d) {
                        break;
                    }
                    i = i2;
                }
            }
            return arrayList;
        }
    }

    private final Object a(T t, Continuation<? super Unit> continuation) {
        a aVar;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        Continuation<Unit>[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            if (a((SharedFlowImpl<T>) t)) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl2.resumeWith(Result.m1220constructorimpl(unit));
                continuationArr = a(continuationArr);
                aVar = null;
            } else {
                a aVar2 = new a(this, e() + c(), t, cancellableContinuationImpl2);
                c(aVar2);
                this.h++;
                if (this.b == 0) {
                    continuationArr = a(continuationArr);
                }
                aVar = aVar2;
            }
        }
        if (aVar != null) {
            CancellableContinuationKt.disposeOnCancellation(cancellableContinuationImpl2, aVar);
        }
        int i = 0;
        int length = continuationArr.length;
        while (i < length) {
            Continuation<Unit> continuation2 = continuationArr[i];
            i++;
            if (continuation2 != null) {
                Unit unit2 = Unit.INSTANCE;
                Result.Companion companion2 = Result.Companion;
                continuation2.resumeWith(Result.m1220constructorimpl(unit2));
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    public final void a(a aVar) {
        Object a2;
        synchronized (this) {
            if (aVar.b >= c()) {
                Object[] objArr = this.d;
                Intrinsics.checkNotNull(objArr);
                a2 = SharedFlowKt.a(objArr, aVar.b);
                if (a2 == aVar) {
                    SharedFlowKt.a(objArr, aVar.b, SharedFlowKt.NO_VALUE);
                    i();
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    private final Object a(z zVar, Continuation<? super Unit> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        synchronized (this) {
            if (b(zVar) < 0) {
                zVar.b = cancellableContinuationImpl2;
                zVar.b = cancellableContinuationImpl2;
            } else {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl2.resumeWith(Result.m1220constructorimpl(unit));
            }
            Unit unit2 = Unit.INSTANCE;
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public void resetReplayCache() {
        synchronized (this) {
            a(f(), this.f, f(), g());
            Unit unit = Unit.INSTANCE;
        }
    }
}
