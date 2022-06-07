package kotlinx.coroutines.selects;

import com.xiaomi.micolauncher.common.track.TrackConstant;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellingNode;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectBuilder;
import org.hapjs.features.channel.IChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Select.kt */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0001\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020Y2\b\u0012\u0004\u0012\u00028\u00000Z2\b\u0012\u0004\u0012\u00028\u00000[2\b\u0012\u0004\u0012\u00028\u00000\u00022\u00060Bj\u0002`C:\u0004TUVWB\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ.\u0010\u0011\u001a\u00020\b2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0082\b¢\u0006\u0004\b\u0011\u0010\u0012J\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\n\u0018\u00010\u0015j\u0004\u0018\u0001`\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\bH\u0002¢\u0006\u0004\b\u001d\u0010\fJ8\u0010!\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u001e2\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0 H\u0016ø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u0019\u0010%\u001a\u0004\u0018\u00010\u000e2\u0006\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\b%\u0010&J\u0017\u0010(\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u0019H\u0016¢\u0006\u0004\b(\u0010\u001cJ \u0010+\u001a\u00020\b2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000)H\u0016ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u000f\u0010.\u001a\u00020-H\u0016¢\u0006\u0004\b.\u0010/J\u000f\u00101\u001a\u000200H\u0016¢\u0006\u0004\b1\u00102J\u001b\u00105\u001a\u0004\u0018\u00010\u000e2\b\u00104\u001a\u0004\u0018\u000103H\u0016¢\u0006\u0004\b5\u00106J5\u00108\u001a\u00020\b*\u0002072\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0 H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b8\u00109JG\u00108\u001a\u00020\b\"\u0004\b\u0001\u0010:*\b\u0012\u0004\u0012\u00028\u00010;2\"\u0010\u0010\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0<H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b8\u0010=J[\u00108\u001a\u00020\b\"\u0004\b\u0001\u0010>\"\u0004\b\u0002\u0010:*\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020?2\u0006\u0010@\u001a\u00028\u00012\"\u0010\u0010\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0<H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b8\u0010AR\u001e\u0010F\u001a\n\u0018\u00010Bj\u0004\u0018\u0001`C8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u001c\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0016\u0010M\u001a\u00020J8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0016\u0010N\u001a\u0002008V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u00102R(\u0010R\u001a\u0004\u0018\u00010\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u00068B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010\nR\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010S\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006X"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl;", "R", "Lkotlin/coroutines/Continuation;", "uCont", "<init>", "(Lkotlin/coroutines/Continuation;)V", "Lkotlinx/coroutines/DisposableHandle;", "handle", "", "disposeOnSelect", "(Lkotlinx/coroutines/DisposableHandle;)V", "doAfterSelect", "()V", "Lkotlin/Function0;", "", com.xiaomi.onetrack.api.b.p, "block", "doResume", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", "e", "handleBuilderException", "(Ljava/lang/Throwable;)V", "initCancellability", "", "timeMillis", "Lkotlin/Function1;", "onTimeout", "(JLkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/AtomicDesc;", IChannel.EXTRA_ERROR_DESC, "performAtomicTrySelect", "(Lkotlinx/coroutines/internal/AtomicDesc;)Ljava/lang/Object;", "exception", "resumeSelectWithException", "Lkotlin/Result;", "result", "resumeWith", "(Ljava/lang/Object;)V", "", "toString", "()Ljava/lang/String;", "", "trySelect", "()Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "otherOp", "trySelectOther", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectClause0;", "invoke", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getCompletion", "()Lkotlin/coroutines/Continuation;", TrackConstant.COMPLETION, "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", com.umeng.analytics.pro.c.R, "isSelected", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setParentHandle", "parentHandle", "Lkotlin/coroutines/Continuation;", "AtomicSelectOp", "DisposeNode", "PairSelectOp", "SelectOnCancelling", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/selects/SelectBuilder;", "Lkotlinx/coroutines/selects/SelectInstance;"}, k = 1, mv = {1, 5, 1}, xi = 48)
@PublishedApi
/* loaded from: classes5.dex */
public final class SelectBuilderImpl<R> extends LockFreeLinkedListHead implements Continuation<R>, CoroutineStackFrame, SelectBuilder<R>, SelectInstance<R> {
    static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(SelectBuilderImpl.class, Object.class, "_state");
    static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(SelectBuilderImpl.class, Object.class, "_result");
    @NotNull
    volatile /* synthetic */ Object _result;
    @NotNull
    private final Continuation<R> c;
    @NotNull
    volatile /* synthetic */ Object _state = SelectKt.getNOT_SELECTED();
    @NotNull
    private volatile /* synthetic */ Object _parentHandle = null;

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @Override // kotlinx.coroutines.selects.SelectBuilder
    public <P, Q> void invoke(@NotNull SelectClause2<? super P, ? extends Q> selectClause2, @NotNull Function2<? super Q, ? super Continuation<? super R>, ? extends Object> function2) {
        SelectBuilder.DefaultImpls.invoke(this, selectClause2, function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SelectBuilderImpl(@NotNull Continuation<? super R> continuation) {
        Object obj;
        this.c = continuation;
        obj = SelectKt.c;
        this._result = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public CoroutineStackFrame getCallerFrame() {
        Continuation<R> continuation = this.c;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    private final DisposableHandle a() {
        return (DisposableHandle) this._parentHandle;
    }

    private final void a(DisposableHandle disposableHandle) {
        this._parentHandle = disposableHandle;
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return this.c.getContext();
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    @NotNull
    public Continuation<R> getCompletion() {
        return this;
    }

    @PublishedApi
    @Nullable
    public final Object getResult() {
        Object obj;
        Object obj2;
        Object obj3;
        if (!isSelected()) {
            b();
        }
        Object obj4 = this._result;
        obj = SelectKt.c;
        if (obj4 == obj) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
            obj3 = SelectKt.c;
            if (atomicReferenceFieldUpdater.compareAndSet(this, obj3, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
                return IntrinsicsKt.getCOROUTINE_SUSPENDED();
            }
            obj4 = this._result;
        }
        obj2 = SelectKt.d;
        if (obj4 == obj2) {
            throw new IllegalStateException("Already resumed");
        } else if (!(obj4 instanceof CompletedExceptionally)) {
            return obj4;
        } else {
            throw ((CompletedExceptionally) obj4).cause;
        }
    }

    private final void b() {
        Job job = (Job) getContext().get(Job.Key);
        if (job != null) {
            DisposableHandle invokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(job, true, false, new d(), 2, null);
            a(invokeOnCompletion$default);
            if (isSelected()) {
                invokeOnCompletion$default.dispose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Select.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0096\u0002¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$SelectOnCancelling;", "Lkotlinx/coroutines/JobCancellingNode;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public final class d extends JobCancellingNode {
        public d() {
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.CompletionHandlerBase
        /* renamed from: invoke  reason: avoid collision after fix types in other method */
        public void invoke2(@Nullable Throwable th) {
            if (SelectBuilderImpl.this.trySelect()) {
                SelectBuilderImpl.this.resumeSelectWithException(getJob().getCancellationException());
            }
        }
    }

    @PublishedApi
    public final void handleBuilderException(@NotNull Throwable th) {
        if (trySelect()) {
            Result.Companion companion = Result.Companion;
            resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(th)));
        } else if (!(th instanceof CancellationException)) {
            Object result = getResult();
            if (result instanceof CompletedExceptionally) {
                Throwable th2 = ((CompletedExceptionally) result).cause;
                if (DebugKt.getRECOVER_STACK_TRACES()) {
                    th2 = StackTraceRecoveryKt.unwrapImpl(th2);
                }
                if (th2 == (!DebugKt.getRECOVER_STACK_TRACES() ? th : StackTraceRecoveryKt.unwrapImpl(th))) {
                    return;
                }
            }
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), th);
        }
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public void disposeOnSelect(@NotNull DisposableHandle disposableHandle) {
        b bVar = new b(disposableHandle);
        if (!isSelected()) {
            addLast(bVar);
            if (!isSelected()) {
                return;
            }
        }
        disposableHandle.dispose();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void c() {
        DisposableHandle a2 = a();
        if (a2 != null) {
            a2.dispose();
        }
        SelectBuilderImpl<R> selectBuilderImpl = this;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) selectBuilderImpl.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, selectBuilderImpl); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof b) {
                ((b) lockFreeLinkedListNode).a.dispose();
            }
        }
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public boolean trySelect() {
        Object trySelectOther = trySelectOther(null);
        if (trySelectOther == CancellableContinuationImplKt.RESUME_TOKEN) {
            return true;
        }
        if (trySelectOther == null) {
            return false;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Unexpected trySelectIdempotent result ", trySelectOther).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Select.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016R\u0018\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$PairSelectOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class c extends OpDescriptor {
        @JvmField
        @NotNull
        public final LockFreeLinkedListNode.PrepareOp a;

        public c(@NotNull LockFreeLinkedListNode.PrepareOp prepareOp) {
            this.a = prepareOp;
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @Nullable
        public Object perform(@Nullable Object obj) {
            if (obj != null) {
                SelectBuilderImpl selectBuilderImpl = (SelectBuilderImpl) obj;
                this.a.finishPrepare();
                Object decide = this.a.getAtomicOp().decide(null);
                SelectBuilderImpl.a.compareAndSet(selectBuilderImpl, this, decide == null ? this.a.desc : SelectKt.getNOT_SELECTED());
                return decide;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.selects.SelectBuilderImpl<*>");
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @NotNull
        public AtomicOp<?> getAtomicOp() {
            return this.a.getAtomicOp();
        }
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    @Nullable
    public Object performAtomicTrySelect(@NotNull AtomicDesc atomicDesc) {
        return new a(this, atomicDesc).perform(null);
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    @NotNull
    public String toString() {
        return "SelectInstance(state=" + this._state + ", result=" + this._result + ')';
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Select.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0019\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0002J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\rH\u0002R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$AtomicSelectOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "", "impl", "Lkotlinx/coroutines/selects/SelectBuilderImpl;", IChannel.EXTRA_ERROR_DESC, "Lkotlinx/coroutines/internal/AtomicDesc;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;Lkotlinx/coroutines/internal/AtomicDesc;)V", "opSequence", "", "getOpSequence", "()J", "complete", "", "affected", "failure", "completeSelect", "prepare", "prepareSelectOp", "toString", "", "undoPrepare", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a extends AtomicOp<Object> {
        @JvmField
        @NotNull
        public final SelectBuilderImpl<?> a;
        @JvmField
        @NotNull
        public final AtomicDesc b;
        private final long c;

        public a(@NotNull SelectBuilderImpl<?> selectBuilderImpl, @NotNull AtomicDesc atomicDesc) {
            SeqNumber seqNumber;
            this.a = selectBuilderImpl;
            this.b = atomicDesc;
            seqNumber = SelectKt.e;
            this.c = seqNumber.next();
            this.b.setAtomicOp(this);
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public long getOpSequence() {
            return this.c;
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        @Nullable
        public Object prepare(@Nullable Object obj) {
            Object a;
            if (obj == null && (a = a()) != null) {
                return a;
            }
            try {
                return this.b.prepare(this);
            } catch (Throwable th) {
                if (obj == null) {
                    b();
                }
                throw th;
            }
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public void complete(@Nullable Object obj, @Nullable Object obj2) {
            a(obj2);
            this.b.complete(this, obj2);
        }

        private final Object a() {
            SelectBuilderImpl<?> selectBuilderImpl = this.a;
            while (true) {
                Object obj = selectBuilderImpl._state;
                if (obj == this) {
                    return null;
                }
                if (obj instanceof OpDescriptor) {
                    ((OpDescriptor) obj).perform(this.a);
                } else if (obj != SelectKt.getNOT_SELECTED()) {
                    return SelectKt.getALREADY_SELECTED();
                } else {
                    if (SelectBuilderImpl.a.compareAndSet(this.a, SelectKt.getNOT_SELECTED(), this)) {
                        return null;
                    }
                }
            }
        }

        private final void b() {
            SelectBuilderImpl.a.compareAndSet(this.a, this, SelectKt.getNOT_SELECTED());
        }

        private final void a(Object obj) {
            boolean z = obj == null;
            if (SelectBuilderImpl.a.compareAndSet(this.a, this, z ? null : SelectKt.getNOT_SELECTED()) && z) {
                this.a.c();
            }
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @NotNull
        public String toString() {
            return "AtomicSelectOp(sequence=" + getOpSequence() + ')';
        }
    }

    @Override // kotlinx.coroutines.selects.SelectBuilder
    public void invoke(@NotNull SelectClause0 selectClause0, @NotNull Function1<? super Continuation<? super R>, ? extends Object> function1) {
        selectClause0.registerSelectClause0(this, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.selects.SelectBuilder
    public <Q> void invoke(@NotNull SelectClause1<? extends Q> selectClause1, @NotNull Function2<? super Q, ? super Continuation<? super R>, ? extends Object> function2) {
        selectClause1.registerSelectClause1(this, function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.selects.SelectBuilder
    public <P, Q> void invoke(@NotNull SelectClause2<? super P, ? extends Q> selectClause2, P p, @NotNull Function2<? super Q, ? super Continuation<? super R>, ? extends Object> function2) {
        selectClause2.registerSelectClause2(this, p, function2);
    }

    @Override // kotlinx.coroutines.selects.SelectBuilder
    public void onTimeout(long j, @NotNull final Function1<? super Continuation<? super R>, ? extends Object> function1) {
        if (j > 0) {
            disposeOnSelect(DelayKt.getDelay(getContext()).invokeOnTimeout(j, new Runnable() { // from class: kotlinx.coroutines.selects.SelectBuilderImpl$onTimeout$$inlined$Runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (SelectBuilderImpl.this.trySelect()) {
                        CancellableKt.startCoroutineCancellable(function1, SelectBuilderImpl.this.getCompletion());
                    }
                }
            }, getContext()));
        } else if (trySelect()) {
            UndispatchedKt.startCoroutineUnintercepted(function1, getCompletion());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Select.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$DisposeNode;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b extends LockFreeLinkedListNode {
        @JvmField
        @NotNull
        public final DisposableHandle a;

        public b(@NotNull DisposableHandle disposableHandle) {
            this.a = disposableHandle;
        }
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object obj) {
        Object obj2;
        Object obj3;
        Object obj4;
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected()) {
            while (true) {
                Object obj5 = this._result;
                obj2 = SelectKt.c;
                if (obj5 == obj2) {
                    Object state$default = CompletionStateKt.toState$default(obj, null, 1, null);
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
                    obj3 = SelectKt.c;
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj3, state$default)) {
                        return;
                    }
                } else if (obj5 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = b;
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    obj4 = SelectKt.d;
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, coroutine_suspended, obj4)) {
                        if (Result.m1225isFailureimpl(obj)) {
                            Continuation<R> continuation = this.c;
                            Throwable th = Result.m1223exceptionOrNullimpl(obj);
                            Intrinsics.checkNotNull(th);
                            Result.Companion companion = Result.Companion;
                            if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
                                th = StackTraceRecoveryKt.a(th, (CoroutineStackFrame) continuation);
                            }
                            continuation.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(th)));
                            return;
                        }
                        this.c.resumeWith(obj);
                        return;
                    }
                } else {
                    throw new IllegalStateException("Already resumed");
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public void resumeSelectWithException(@NotNull Throwable th) {
        Object obj;
        Object obj2;
        Object obj3;
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected()) {
            while (true) {
                Object obj4 = this._result;
                obj = SelectKt.c;
                if (obj4 == obj) {
                    Continuation<R> continuation = this.c;
                    CompletedExceptionally completedExceptionally = new CompletedExceptionally((!DebugKt.getRECOVER_STACK_TRACES() || !(continuation instanceof CoroutineStackFrame)) ? th : StackTraceRecoveryKt.a(th, (CoroutineStackFrame) continuation), false, 2, null);
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
                    obj2 = SelectKt.c;
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj2, completedExceptionally)) {
                        return;
                    }
                } else if (obj4 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = b;
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    obj3 = SelectKt.d;
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, coroutine_suspended, obj3)) {
                        Continuation intercepted = IntrinsicsKt.intercepted(this.c);
                        Result.Companion companion = Result.Companion;
                        intercepted.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(th)));
                        return;
                    }
                } else {
                    throw new IllegalStateException("Already resumed");
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public boolean isSelected() {
        while (true) {
            Object obj = this._state;
            if (obj == SelectKt.getNOT_SELECTED()) {
                return false;
            }
            if (!(obj instanceof OpDescriptor)) {
                return true;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
        c();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:
        return kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN;
     */
    @Override // kotlinx.coroutines.selects.SelectInstance
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object trySelectOther(@org.jetbrains.annotations.Nullable kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp r4) {
        /*
            r3 = this;
        L_0x0000:
            java.lang.Object r0 = r3._state
            java.lang.Object r1 = kotlinx.coroutines.selects.SelectKt.getNOT_SELECTED()
            r2 = 0
            if (r0 != r1) goto L_0x0037
            if (r4 != 0) goto L_0x0018
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = kotlinx.coroutines.selects.SelectBuilderImpl.a
            java.lang.Object r1 = kotlinx.coroutines.selects.SelectKt.getNOT_SELECTED()
            boolean r0 = r0.compareAndSet(r3, r1, r2)
            if (r0 != 0) goto L_0x0031
            goto L_0x0000
        L_0x0018:
            kotlinx.coroutines.selects.SelectBuilderImpl$c r0 = new kotlinx.coroutines.selects.SelectBuilderImpl$c
            r0.<init>(r4)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = kotlinx.coroutines.selects.SelectBuilderImpl.a
            java.lang.Object r2 = kotlinx.coroutines.selects.SelectKt.getNOT_SELECTED()
            boolean r1 = r1.compareAndSet(r3, r2, r0)
            if (r1 != 0) goto L_0x002a
            goto L_0x0000
        L_0x002a:
            java.lang.Object r4 = r0.perform(r3)
            if (r4 == 0) goto L_0x0031
            return r4
        L_0x0031:
            r3.c()
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN
            return r4
        L_0x0037:
            boolean r1 = r0 instanceof kotlinx.coroutines.internal.OpDescriptor
            if (r1 == 0) goto L_0x006d
            if (r4 == 0) goto L_0x0067
            kotlinx.coroutines.internal.AtomicOp r1 = r4.getAtomicOp()
            boolean r2 = r1 instanceof kotlinx.coroutines.selects.SelectBuilderImpl.a
            if (r2 == 0) goto L_0x005b
            r2 = r1
            kotlinx.coroutines.selects.SelectBuilderImpl$a r2 = (kotlinx.coroutines.selects.SelectBuilderImpl.a) r2
            kotlinx.coroutines.selects.SelectBuilderImpl<?> r2 = r2.a
            if (r2 == r3) goto L_0x004d
            goto L_0x005b
        L_0x004d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r0 = "Cannot use matching select clauses on the same object"
            java.lang.String r0 = r0.toString()
            r4.<init>(r0)
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            throw r4
        L_0x005b:
            r2 = r0
            kotlinx.coroutines.internal.OpDescriptor r2 = (kotlinx.coroutines.internal.OpDescriptor) r2
            boolean r1 = r1.isEarlierThan(r2)
            if (r1 == 0) goto L_0x0067
            java.lang.Object r4 = kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC
            return r4
        L_0x0067:
            kotlinx.coroutines.internal.OpDescriptor r0 = (kotlinx.coroutines.internal.OpDescriptor) r0
            r0.perform(r3)
            goto L_0x0000
        L_0x006d:
            if (r4 != 0) goto L_0x0070
            return r2
        L_0x0070:
            kotlinx.coroutines.internal.LockFreeLinkedListNode$AbstractAtomicDesc r4 = r4.desc
            if (r0 != r4) goto L_0x0077
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN
            return r4
        L_0x0077:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectBuilderImpl.trySelectOther(kotlinx.coroutines.internal.LockFreeLinkedListNode$PrepareOp):java.lang.Object");
    }
}
