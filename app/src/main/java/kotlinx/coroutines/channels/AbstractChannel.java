package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BeforeResumeCancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractChannel.kt */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0007STUVWXYB'\u0012 \u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0019\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J\u0016\u0010\u0019\u001a\u00020\u00062\u000e\u0010\u001a\u001a\n\u0018\u00010\u001cj\u0004\u0018\u0001`\u001dJ\u0017\u0010\u001e\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000¢\u0006\u0002\b\u001fJ\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0004J\u0016\u0010\"\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0002J\u0016\u0010%\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0014JR\u0010&\u001a\u00020\n\"\u0004\b\u0001\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2$\u0010*\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+2\u0006\u0010.\u001a\u00020/H\u0002ø\u0001\u0000¢\u0006\u0002\u00100J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00028\u000002H\u0086\u0002J\u0010\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u00020\nH\u0014J/\u00105\u001a\u00020\u00062\f\u00106\u001a\b\u0012\u0004\u0012\u000208072\n\u00109\u001a\u0006\u0012\u0002\b\u00030:H\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b;\u0010<J\b\u0010=\u001a\u00020\u0006H\u0014J\b\u0010>\u001a\u00020\u0006H\u0014J\n\u0010?\u001a\u0004\u0018\u00010,H\u0014J\u0016\u0010@\u001a\u0004\u0018\u00010,2\n\u0010(\u001a\u0006\u0012\u0002\b\u00030)H\u0014J\u0011\u0010#\u001a\u00028\u0000H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010AJ\"\u0010B\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H\u0086@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0002ø\u0001\u0001¢\u0006\u0004\bC\u0010AJ\u001f\u0010D\u001a\u0002H'\"\u0004\b\u0001\u0010'2\u0006\u0010.\u001a\u00020/H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010EJR\u0010F\u001a\u00020\u0006\"\u0004\b\u0001\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2\u0006\u0010.\u001a\u00020/2$\u0010*\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+H\u0002ø\u0001\u0000¢\u0006\u0002\u0010GJ \u0010H\u001a\u00020\u00062\n\u0010I\u001a\u0006\u0012\u0002\b\u00030J2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0002J\u0010\u0010K\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010LH\u0014J\u001c\u0010M\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017ø\u0001\u0000ø\u0001\u0002ø\u0001\u0001¢\u0006\u0004\bN\u0010OJX\u0010P\u001a\u00020\u0006\"\u0004\b\u0001\u0010'* \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2\u0006\u0010.\u001a\u00020/2\b\u0010Q\u001a\u0004\u0018\u00010,H\u0002ø\u0001\u0000¢\u0006\u0002\u0010RR\u0014\u0010\t\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0012\u0010\u000e\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0014\u0010\u0011\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00170\u00138Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0015\u0082\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006Z"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/Channel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "hasReceiveOrClosed", "", "getHasReceiveOrClosed", "()Z", "isBufferAlwaysEmpty", "isBufferEmpty", "isClosedForReceive", "isEmpty", "isEmptyImpl", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "cancelInternal$kotlinx_coroutines_core", "describeTryPoll", "Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "enqueueReceive", "receive", "Lkotlinx/coroutines/channels/Receive;", "enqueueReceiveInternal", "enqueueReceiveSelect", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)Z", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "onCancelIdempotent", "wasClosed", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "onReceiveDequeued", "onReceiveEnqueued", "pollInternal", "pollSelectInternal", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveSuspend", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerSelectReceiveMode", "(Lkotlinx/coroutines/selects/SelectInstance;ILkotlin/jvm/functions/Function2;)V", "removeReceiveOnCancel", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "takeFirstReceiveOrPeekClosed", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "tryStartBlockUnintercepted", com.xiaomi.onetrack.api.b.p, "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/selects/SelectInstance;ILjava/lang/Object;)V", "Itr", "ReceiveElement", "ReceiveElementWithUndeliveredHandler", "ReceiveHasNext", "ReceiveSelect", "RemoveReceiveOnCancel", "TryPollDesc", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public abstract class AbstractChannel<E> extends AbstractSendChannel<E> implements Channel<E> {

    /* compiled from: AbstractChannel.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.AbstractChannel", f = "AbstractChannel.kt", i = {}, l = {632}, m = "receiveCatching-JP2dKIU", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class g extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ AbstractChannel<E> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g(AbstractChannel<E> abstractChannel, Continuation<? super g> continuation) {
            super(continuation);
            this.this$0 = abstractChannel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            Object obj2 = this.this$0.mo1759receiveCatchingJP2dKIU(this);
            return obj2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? obj2 : ChannelResult.m1766boximpl(obj2);
        }
    }

    protected abstract boolean isBufferAlwaysEmpty();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean isBufferEmpty();

    protected void onReceiveDequeued() {
    }

    protected void onReceiveEnqueued() {
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public SelectClause1<E> getOnReceiveOrNull() {
        return Channel.DefaultImpls.getOnReceiveOrNull(this);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.WARNING, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    @Nullable
    public E poll() {
        return (E) Channel.DefaultImpls.poll(this);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    @LowPriorityInOverloadResolution
    @Nullable
    public Object receiveOrNull(@NotNull Continuation<? super E> continuation) {
        return Channel.DefaultImpls.receiveOrNull(this, continuation);
    }

    public AbstractChannel(@Nullable Function1<? super E, Unit> function1) {
        super(function1);
    }

    @Nullable
    protected Object pollInternal() {
        while (true) {
            Send takeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
            if (takeFirstSendOrPeekClosed == null) {
                return AbstractChannelKt.POLL_FAILED;
            }
            Symbol tryResumeSend = takeFirstSendOrPeekClosed.tryResumeSend(null);
            if (tryResumeSend != null) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (!(tryResumeSend == CancellableContinuationImplKt.RESUME_TOKEN)) {
                        throw new AssertionError();
                    }
                }
                takeFirstSendOrPeekClosed.completeResumeSend();
                return takeFirstSendOrPeekClosed.getPollResult();
            }
            takeFirstSendOrPeekClosed.undeliveredElement();
        }
    }

    @Nullable
    protected Object pollSelectInternal(@NotNull SelectInstance<?> selectInstance) {
        TryPollDesc<E> describeTryPoll = describeTryPoll();
        Object performAtomicTrySelect = selectInstance.performAtomicTrySelect(describeTryPoll);
        if (performAtomicTrySelect != null) {
            return performAtomicTrySelect;
        }
        describeTryPoll.getResult().completeResumeSend();
        return describeTryPoll.getResult().getPollResult();
    }

    protected final boolean getHasReceiveOrClosed() {
        return getQueue().getNextNode() instanceof ReceiveOrClosed;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        return getClosedForReceive() != null && isBufferEmpty();
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        return isEmptyImpl();
    }

    protected final boolean isEmptyImpl() {
        return !(getQueue().getNextNode() instanceof Send) && isBufferEmpty();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Nullable
    public final Object receive(@NotNull Continuation<? super E> continuation) {
        Object pollInternal = pollInternal();
        return (pollInternal == AbstractChannelKt.POLL_FAILED || (pollInternal instanceof Closed)) ? a(0, continuation) : pollInternal;
    }

    public boolean enqueueReceiveInternal(@NotNull Receive<? super E> receive) {
        LockFreeLinkedListNode prevNode;
        if (isBufferAlwaysEmpty()) {
            LockFreeLinkedListHead queue = getQueue();
            do {
                prevNode = queue.getPrevNode();
                if (!(!(prevNode instanceof Send))) {
                    return false;
                }
            } while (!prevNode.addNext(receive, queue));
            return true;
        }
        LockFreeLinkedListHead queue2 = getQueue();
        Receive<? super E> receive2 = receive;
        LockFreeLinkedListNode.CondAddOp abstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1 = new LockFreeLinkedListNode.CondAddOp(this) { // from class: kotlinx.coroutines.channels.AbstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1
            final /* synthetic */ AbstractChannel b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(LockFreeLinkedListNode.this);
                this.b = this;
            }

            @Nullable
            public Object prepare(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
                if (this.b.isBufferEmpty()) {
                    return null;
                }
                return LockFreeLinkedListKt.getCONDITION_FALSE();
            }
        };
        while (true) {
            LockFreeLinkedListNode prevNode2 = queue2.getPrevNode();
            if (!(!(prevNode2 instanceof Send))) {
                return false;
            }
            switch (prevNode2.tryCondAddNext(receive2, queue2, abstractChannel$enqueueReceiveInternal$$inlined$addLastIfPrevAndIf$1)) {
                case 1:
                    return true;
                case 2:
                    return false;
            }
        }
    }

    public final boolean a(Receive<? super E> receive) {
        boolean enqueueReceiveInternal = enqueueReceiveInternal(receive);
        if (enqueueReceiveInternal) {
            onReceiveEnqueued();
        }
        return enqueueReceiveInternal;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @org.jetbrains.annotations.Nullable
    /* renamed from: receiveCatching-JP2dKIU */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo1759receiveCatchingJP2dKIU(@org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof kotlinx.coroutines.channels.AbstractChannel.g
            if (r0 == 0) goto L_0x0014
            r0 = r4
            kotlinx.coroutines.channels.AbstractChannel$g r0 = (kotlinx.coroutines.channels.AbstractChannel.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r4 = r0.label
            int r4 = r4 - r2
            r0.label = r4
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.AbstractChannel$g r0 = new kotlinx.coroutines.channels.AbstractChannel$g
            r0.<init>(r3, r4)
        L_0x0019:
            java.lang.Object r4 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0030;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r0)
            throw r4
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r4)
            goto L_0x005b
        L_0x0030:
            kotlin.ResultKt.throwOnFailure(r4)
            java.lang.Object r4 = r3.pollInternal()
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.AbstractChannelKt.POLL_FAILED
            if (r4 == r2) goto L_0x0051
            boolean r0 = r4 instanceof kotlinx.coroutines.channels.Closed
            if (r0 == 0) goto L_0x004a
            kotlinx.coroutines.channels.ChannelResult$Companion r0 = kotlinx.coroutines.channels.ChannelResult.Companion
            kotlinx.coroutines.channels.Closed r4 = (kotlinx.coroutines.channels.Closed) r4
            java.lang.Throwable r4 = r4.closeCause
            java.lang.Object r4 = r0.m1779closedJP2dKIU(r4)
            goto L_0x0050
        L_0x004a:
            kotlinx.coroutines.channels.ChannelResult$Companion r0 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Object r4 = r0.m1781successJP2dKIU(r4)
        L_0x0050:
            return r4
        L_0x0051:
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = r3.a(r4, r0)
            if (r4 != r1) goto L_0x005b
            return r1
        L_0x005b:
            kotlinx.coroutines.channels.ChannelResult r4 = (kotlinx.coroutines.channels.ChannelResult) r4
            java.lang.Object r4 = r4.m1778unboximpl()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.AbstractChannel.mo1759receiveCatchingJP2dKIU(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    /* renamed from: tryReceive-PtdJZtk */
    public final Object mo1760tryReceivePtdJZtk() {
        Object pollInternal = pollInternal();
        return pollInternal == AbstractChannelKt.POLL_FAILED ? ChannelResult.Companion.m1780failurePtdJZtk() : pollInternal instanceof Closed ? ChannelResult.Companion.m1779closedJP2dKIU(((Closed) pollInternal).closeCause) : ChannelResult.Companion.m1781successJP2dKIU(pollInternal);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(@Nullable CancellationException cancellationException) {
        if (!isClosedForReceive()) {
            if (cancellationException == null) {
                cancellationException = new CancellationException(Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " was cancelled"));
            }
            cancel(cancellationException);
        }
    }

    /* renamed from: cancelInternal$kotlinx_coroutines_core */
    public final boolean cancel(@Nullable Throwable th) {
        boolean close = close(th);
        onCancelIdempotent(close);
        return close;
    }

    public void onCancelIdempotent(boolean z) {
        Closed<?> closedForSend = getClosedForSend();
        if (closedForSend != null) {
            Object obj = InlineList.m1790constructorimpl$default(null, 1, null);
            while (true) {
                LockFreeLinkedListNode prevNode = closedForSend.getPrevNode();
                if (prevNode instanceof LockFreeLinkedListHead) {
                    mo1758onCancelIdempotentListww6eGU(obj, closedForSend);
                    return;
                } else if (DebugKt.getASSERTIONS_ENABLED() && !(prevNode instanceof Send)) {
                    throw new AssertionError();
                } else if (!prevNode.remove()) {
                    prevNode.helpRemove();
                } else {
                    obj = InlineList.m1795plusFjFbRPM(obj, (Send) prevNode);
                }
            }
        } else {
            throw new IllegalStateException("Cannot happen".toString());
        }
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public final ChannelIterator<E> iterator() {
        return new a(this);
    }

    @NotNull
    protected final TryPollDesc<E> describeTryPoll() {
        return new TryPollDesc<>(getQueue());
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u0012\u0012\u0004\u0012\u00020\u00030\u0002j\b\u0012\u0004\u0012\u00020\u0003`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0016\u0010\f\u001a\u0004\u0018\u00010\t2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "onRemoved", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class TryPollDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<Send> {
        public TryPollDesc(@NotNull LockFreeLinkedListHead lockFreeLinkedListHead) {
            super(lockFreeLinkedListHead);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc, kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode instanceof Closed) {
                return lockFreeLinkedListNode;
            }
            if (!(lockFreeLinkedListNode instanceof Send)) {
                return AbstractChannelKt.POLL_FAILED;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        public Object onPrepare(@NotNull LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol tryResumeSend = ((Send) prepareOp.affected).tryResumeSend(prepareOp);
            if (tryResumeSend == null) {
                return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            }
            if (tryResumeSend == AtomicKt.RETRY_ATOMIC) {
                return AtomicKt.RETRY_ATOMIC;
            }
            if (!DebugKt.getASSERTIONS_ENABLED()) {
                return null;
            }
            if (tryResumeSend == CancellableContinuationImplKt.RESUME_TOKEN) {
                return null;
            }
            throw new AssertionError();
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public void onRemoved(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            ((Send) lockFreeLinkedListNode).undeliveredElement();
        }
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public final SelectClause1<E> getOnReceive() {
        return new SelectClause1<E>(this) { // from class: kotlinx.coroutines.channels.AbstractChannel$onReceive$1
            final /* synthetic */ AbstractChannel<E> a;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.a = this;
            }

            @Override // kotlinx.coroutines.selects.SelectClause1
            public <R> void registerSelectClause1(@NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2) {
                this.a.a(selectInstance, 0, function2);
            }
        };
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public final SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        return (SelectClause1) ((SelectClause1<ChannelResult<? extends E>>) new SelectClause1<ChannelResult<? extends E>>(this) { // from class: kotlinx.coroutines.channels.AbstractChannel$onReceiveCatching$1
            final /* synthetic */ AbstractChannel<E> a;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.a = this;
            }

            @Override // kotlinx.coroutines.selects.SelectClause1
            public <R> void registerSelectClause1(@NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super ChannelResult<? extends E>, ? super Continuation<? super R>, ? extends Object> function2) {
                this.a.a(selectInstance, 1, function2);
            }
        });
    }

    public final <R> void a(SelectInstance<? super R> selectInstance, int i, Function2<Object, ? super Continuation<? super R>, ? extends Object> function2) {
        while (!selectInstance.isSelected()) {
            if (!isEmptyImpl()) {
                Object pollSelectInternal = pollSelectInternal(selectInstance);
                if (pollSelectInternal != SelectKt.getALREADY_SELECTED()) {
                    if (!(pollSelectInternal == AbstractChannelKt.POLL_FAILED || pollSelectInternal == AtomicKt.RETRY_ATOMIC)) {
                        a(function2, selectInstance, i, pollSelectInternal);
                    }
                } else {
                    return;
                }
            } else if (a(selectInstance, function2, i)) {
                return;
            }
        }
    }

    private final <R> void a(Function2<Object, ? super Continuation<? super R>, ? extends Object> function2, SelectInstance<? super R> selectInstance, int i, Object obj) {
        boolean z = obj instanceof Closed;
        if (z) {
            switch (i) {
                case 0:
                    throw StackTraceRecoveryKt.recoverStackTrace(((Closed) obj).getReceiveException());
                case 1:
                    if (selectInstance.trySelect()) {
                        UndispatchedKt.startCoroutineUnintercepted(function2, ChannelResult.m1766boximpl(ChannelResult.Companion.m1779closedJP2dKIU(((Closed) obj).closeCause)), selectInstance.getCompletion());
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (i == 1) {
            UndispatchedKt.startCoroutineUnintercepted(function2, ChannelResult.m1766boximpl(z ? ChannelResult.Companion.m1779closedJP2dKIU(((Closed) obj).closeCause) : ChannelResult.Companion.m1781successJP2dKIU(obj)), selectInstance.getCompletion());
        } else {
            UndispatchedKt.startCoroutineUnintercepted(function2, obj, selectInstance.getCompletion());
        }
    }

    private final <R> boolean a(SelectInstance<? super R> selectInstance, Function2<Object, ? super Continuation<? super R>, ? extends Object> function2, int i) {
        e eVar = new e(this, selectInstance, function2, i);
        boolean a2 = a(eVar);
        if (a2) {
            selectInstance.disposeOnSelect(eVar);
        }
        return a2;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @Nullable
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed = super.takeFirstReceiveOrPeekClosed();
        if (takeFirstReceiveOrPeekClosed != null && !(takeFirstReceiveOrPeekClosed instanceof Closed)) {
            onReceiveDequeued();
        }
        return takeFirstReceiveOrPeekClosed;
    }

    public final void a(CancellableContinuation<?> cancellableContinuation, Receive<?> receive) {
        cancellableContinuation.invokeOnCancellation(new f(receive));
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$RemoveReceiveOnCancel;", "Lkotlinx/coroutines/BeforeResumeCancelHandler;", "receive", "Lkotlinx/coroutines/channels/Receive;", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/channels/Receive;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public final class f extends BeforeResumeCancelHandler {
        @NotNull
        private final Receive<?> b;

        public f(Receive<?> receive) {
            AbstractChannel.this = r1;
            this.b = receive;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.CancelHandlerBase
        /* renamed from: invoke */
        public void invoke2(@Nullable Throwable th) {
            if (this.b.remove()) {
                AbstractChannel.this.onReceiveDequeued();
            }
        }

        @NotNull
        public String toString() {
            return "RemoveReceiveOnCancel[" + this.b + ']';
        }
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0011\u0010\f\u001a\u00020\rH\u0096Bø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0011\u0010\u0010\u001a\u00020\rH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u0011\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\tR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "E", "Lkotlinx/coroutines/channels/ChannelIterator;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "(Lkotlinx/coroutines/channels/AbstractChannel;)V", "result", "", "getResult", "()Ljava/lang/Object;", "setResult", "(Ljava/lang/Object;)V", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextResult", "hasNextSuspend", "next", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a<E> implements ChannelIterator<E> {
        @JvmField
        @NotNull
        public final AbstractChannel<E> a;
        @Nullable
        private Object b = AbstractChannelKt.POLL_FAILED;

        public a(@NotNull AbstractChannel<E> abstractChannel) {
            this.a = abstractChannel;
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        @JvmName(name = "next")
        public /* synthetic */ Object next(Continuation continuation) {
            return ChannelIterator.DefaultImpls.next(this, continuation);
        }

        @Nullable
        public final Object a() {
            return this.b;
        }

        public final void a(@Nullable Object obj) {
            this.b = obj;
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        @Nullable
        public Object hasNext(@NotNull Continuation<? super Boolean> continuation) {
            if (a() != AbstractChannelKt.POLL_FAILED) {
                return Boxing.boxBoolean(b(a()));
            }
            a(this.a.pollInternal());
            return a() != AbstractChannelKt.POLL_FAILED ? Boxing.boxBoolean(b(a())) : a(continuation);
        }

        private final boolean b(Object obj) {
            if (!(obj instanceof Closed)) {
                return true;
            }
            Closed closed = (Closed) obj;
            if (closed.closeCause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(closed.getReceiveException());
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public E next() {
            E e = (E) this.b;
            if (e instanceof Closed) {
                throw StackTraceRecoveryKt.recoverStackTrace(((Closed) e).getReceiveException());
            } else if (e != AbstractChannelKt.POLL_FAILED) {
                this.b = AbstractChannelKt.POLL_FAILED;
                return e;
            } else {
                throw new IllegalStateException("'hasNext' should be called prior to 'next' invocation");
            }
        }

        private final Object a(Continuation<? super Boolean> continuation) {
            CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
            CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation;
            d dVar = new d(this, cancellableContinuationImpl);
            while (true) {
                d dVar2 = dVar;
                if (this.a.a(dVar2)) {
                    this.a.a(cancellableContinuationImpl, dVar2);
                    break;
                }
                Object pollInternal = this.a.pollInternal();
                a(pollInternal);
                if (pollInternal instanceof Closed) {
                    Closed closed = (Closed) pollInternal;
                    if (closed.closeCause == null) {
                        Boolean boxBoolean = Boxing.boxBoolean(false);
                        Result.Companion companion = Result.Companion;
                        cancellableContinuationImpl.resumeWith(Result.m1220constructorimpl(boxBoolean));
                    } else {
                        Throwable receiveException = closed.getReceiveException();
                        Result.Companion companion2 = Result.Companion;
                        cancellableContinuationImpl.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(receiveException)));
                    }
                } else if (pollInternal != AbstractChannelKt.POLL_FAILED) {
                    Boolean boxBoolean2 = Boxing.boxBoolean(true);
                    Function1 function1 = this.a.onUndeliveredElement;
                    cancellableContinuationImpl.resume(boxBoolean2, function1 == null ? null : OnUndeliveredElementKt.bindCancellationFun(function1, pollInternal, cancellableContinuationImpl.getContext()));
                }
            }
            Object result = orCreateCancellableContinuation.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        }
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\u00020\n2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00028\u0001¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J!\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000b\u001a\u00028\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016¢\u0006\u0002\u0010\u0018R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "E", "Lkotlinx/coroutines/channels/Receive;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "(Lkotlinx/coroutines/CancellableContinuation;I)V", "completeResumeReceive", "", com.xiaomi.onetrack.api.b.p, "(Ljava/lang/Object;)V", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "resumeValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static class b<E> extends Receive<E> {
        @JvmField
        @NotNull
        public final CancellableContinuation<Object> a;
        @JvmField
        public final int b;

        public b(@NotNull CancellableContinuation<Object> cancellableContinuation, int i) {
            this.a = cancellableContinuation;
            this.b = i;
        }

        @Nullable
        public final Object a(E e) {
            return this.b == 1 ? ChannelResult.m1766boximpl(ChannelResult.Companion.m1781successJP2dKIU(e)) : e;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        @Nullable
        public Symbol tryResumeReceive(E e, @Nullable LockFreeLinkedListNode.PrepareOp prepareOp) {
            Object tryResume = this.a.tryResume(a((b<E>) e), prepareOp == null ? null : prepareOp.desc, resumeOnCancellationFun(e));
            if (tryResume == null) {
                return null;
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(tryResume == CancellableContinuationImplKt.RESUME_TOKEN)) {
                    throw new AssertionError();
                }
            }
            if (prepareOp != null) {
                prepareOp.finishPrepare();
            }
            return CancellableContinuationImplKt.RESUME_TOKEN;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public void completeResumeReceive(E e) {
            this.a.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
        }

        @Override // kotlinx.coroutines.channels.Receive
        public void resumeReceiveClosed(@NotNull Closed<?> closed) {
            if (this.b == 1) {
                ChannelResult channelResult = ChannelResult.m1766boximpl(ChannelResult.Companion.m1779closedJP2dKIU(closed.closeCause));
                Result.Companion companion = Result.Companion;
                this.a.resumeWith(Result.m1220constructorimpl(channelResult));
                return;
            }
            Throwable receiveException = closed.getReceiveException();
            Result.Companion companion2 = Result.Companion;
            this.a.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(receiveException)));
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "ReceiveElement@" + DebugStringsKt.getHexAddress(this) + "[receiveMode=" + this.b + ']';
        }
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B;\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u001c\u0010\b\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00028\u0001`\u000b¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010R&\u0010\b\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00028\u0001`\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlinx/coroutines/CancellableContinuation;ILkotlin/jvm/functions/Function1;)V", "resumeOnCancellationFun", "", com.xiaomi.onetrack.api.b.p, "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class c<E> extends b<E> {
        @JvmField
        @NotNull
        public final Function1<E, Unit> c;

        /* JADX WARN: Multi-variable type inference failed */
        public c(@NotNull CancellableContinuation<Object> cancellableContinuation, int i, @NotNull Function1<? super E, Unit> function1) {
            super(cancellableContinuation, i);
            this.c = function1;
        }

        @Override // kotlinx.coroutines.channels.Receive
        @Nullable
        public Function1<Throwable, Unit> resumeOnCancellationFun(E e) {
            return OnUndeliveredElementKt.bindCancellationFun(this.c, e, this.a.getContext());
        }
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n\u0018\u00010\u000e2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u00020\n2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J!\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000b\u001a\u00028\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016¢\u0006\u0002\u0010\u001aR\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveHasNext;", "E", "Lkotlinx/coroutines/channels/Receive;", "iterator", "Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/channels/AbstractChannel$Itr;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeReceive", "", com.xiaomi.onetrack.api.b.p, "(Ljava/lang/Object;)V", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static class d<E> extends Receive<E> {
        @JvmField
        @NotNull
        public final a<E> a;
        @JvmField
        @NotNull
        public final CancellableContinuation<Boolean> b;

        /* JADX WARN: Multi-variable type inference failed */
        public d(@NotNull a<E> aVar, @NotNull CancellableContinuation<? super Boolean> cancellableContinuation) {
            this.a = aVar;
            this.b = cancellableContinuation;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        @Nullable
        public Symbol tryResumeReceive(E e, @Nullable LockFreeLinkedListNode.PrepareOp prepareOp) {
            boolean z = true;
            Object tryResume = this.b.tryResume(true, prepareOp == null ? null : prepareOp.desc, resumeOnCancellationFun(e));
            if (tryResume == null) {
                return null;
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (tryResume != CancellableContinuationImplKt.RESUME_TOKEN) {
                    z = false;
                }
                if (!z) {
                    throw new AssertionError();
                }
            }
            if (prepareOp != null) {
                prepareOp.finishPrepare();
            }
            return CancellableContinuationImplKt.RESUME_TOKEN;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public void completeResumeReceive(E e) {
            this.a.a(e);
            this.b.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
        }

        @Override // kotlinx.coroutines.channels.Receive
        public void resumeReceiveClosed(@NotNull Closed<?> closed) {
            Object obj;
            if (closed.closeCause == null) {
                obj = CancellableContinuation.DefaultImpls.tryResume$default(this.b, false, null, 2, null);
            } else {
                obj = this.b.tryResumeWithException(closed.getReceiveException());
            }
            if (obj != null) {
                this.a.a(closed);
                this.b.completeResume(obj);
            }
        }

        @Override // kotlinx.coroutines.channels.Receive
        @Nullable
        public Function1<Throwable, Unit> resumeOnCancellationFun(E e) {
            Function1 function1 = this.a.a.onUndeliveredElement;
            if (function1 == null) {
                return null;
            }
            return OnUndeliveredElementKt.bindCancellationFun(function1, e, this.b.getContext());
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return Intrinsics.stringPlus("ReceiveHasNext@", DebugStringsKt.getHexAddress(this));
        }
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u00032\u00020\u0004BR\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b\u0012$\u0010\t\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u0012\u0006\u0010\r\u001a\u00020\u000eø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0016J#\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00172\u0006\u0010\u0013\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0019J\u0014\u0010\u001a\u001a\u00020\u00122\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J!\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0013\u001a\u00028\u00022\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016¢\u0006\u0002\u0010#R3\u0010\t\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n8\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveSelect;", "R", "E", "Lkotlinx/coroutines/channels/Receive;", "Lkotlinx/coroutines/DisposableHandle;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)V", "Lkotlin/jvm/functions/Function2;", "completeResumeReceive", "", com.xiaomi.onetrack.api.b.p, "(Ljava/lang/Object;)V", "dispose", "resumeOnCancellationFun", "Lkotlin/Function1;", "", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class e<R, E> extends Receive<E> implements DisposableHandle {
        @JvmField
        @NotNull
        public final AbstractChannel<E> a;
        @JvmField
        @NotNull
        public final SelectInstance<R> b;
        @JvmField
        @NotNull
        public final Function2<Object, Continuation<? super R>, Object> c;
        @JvmField
        public final int d;

        /* JADX WARN: Multi-variable type inference failed */
        public e(@NotNull AbstractChannel<E> abstractChannel, @NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<Object, ? super Continuation<? super R>, ? extends Object> function2, int i) {
            this.a = abstractChannel;
            this.b = selectInstance;
            this.c = function2;
            this.d = i;
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        @Nullable
        public Symbol tryResumeReceive(E e, @Nullable LockFreeLinkedListNode.PrepareOp prepareOp) {
            return (Symbol) this.b.trySelectOther(prepareOp);
        }

        @Override // kotlinx.coroutines.channels.ReceiveOrClosed
        public void completeResumeReceive(E e) {
            CancellableKt.startCoroutineCancellable(this.c, this.d == 1 ? ChannelResult.m1766boximpl(ChannelResult.Companion.m1781successJP2dKIU(e)) : e, this.b.getCompletion(), resumeOnCancellationFun(e));
        }

        @Override // kotlinx.coroutines.channels.Receive
        public void resumeReceiveClosed(@NotNull Closed<?> closed) {
            if (this.b.trySelect()) {
                switch (this.d) {
                    case 0:
                        this.b.resumeSelectWithException(closed.getReceiveException());
                        return;
                    case 1:
                        CancellableKt.startCoroutineCancellable$default(this.c, ChannelResult.m1766boximpl(ChannelResult.Companion.m1779closedJP2dKIU(closed.closeCause)), this.b.getCompletion(), null, 4, null);
                        return;
                    default:
                        return;
                }
            }
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public void dispose() {
            if (remove()) {
                this.a.onReceiveDequeued();
            }
        }

        @Override // kotlinx.coroutines.channels.Receive
        @Nullable
        public Function1<Throwable, Unit> resumeOnCancellationFun(E e) {
            Function1 function1 = this.a.onUndeliveredElement;
            if (function1 == null) {
                return null;
            }
            return OnUndeliveredElementKt.bindCancellationFun(function1, e, this.b.getCompletion().getContext());
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "ReceiveSelect@" + DebugStringsKt.getHexAddress(this) + '[' + this.b + ",receiveMode=" + this.d + ']';
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [kotlinx.coroutines.channels.AbstractChannel$b] */
    public final <R> Object a(int i, Continuation<? super R> continuation) {
        c cVar;
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation;
        if (this.onUndeliveredElement == null) {
            cVar = new b(cancellableContinuationImpl, i);
        } else {
            cVar = new c(cancellableContinuationImpl, i, this.onUndeliveredElement);
        }
        while (true) {
            c cVar2 = cVar;
            if (!a(cVar2)) {
                Object pollInternal = pollInternal();
                if (!(pollInternal instanceof Closed)) {
                    if (pollInternal != AbstractChannelKt.POLL_FAILED) {
                        cancellableContinuationImpl.resume(cVar.a((c) pollInternal), cVar.resumeOnCancellationFun(pollInternal));
                        break;
                    }
                } else {
                    cVar.resumeReceiveClosed((Closed) pollInternal);
                    break;
                }
            } else {
                a(cancellableContinuationImpl, cVar2);
                break;
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    /* renamed from: onCancelIdempotentList-w-w6eGU */
    protected void mo1758onCancelIdempotentListww6eGU(@NotNull Object obj, @NotNull Closed<?> closed) {
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                ((Send) obj).resumeSendClosed(closed);
            } else if (obj != null) {
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size() - 1;
                if (size >= 0) {
                    while (true) {
                        int i = size - 1;
                        ((Send) arrayList.get(size)).resumeSendClosed(closed);
                        if (i >= 0) {
                            size = i;
                        } else {
                            return;
                        }
                    }
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
    }
}
