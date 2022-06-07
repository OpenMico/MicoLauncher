package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractChannel.kt */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u000006:\u0004defgB)\u0012 \u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0013\u001a\u000e\u0012\u0002\b\u00030\u0011j\u0006\u0012\u0002\b\u0003`\u00122\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00152\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u0018H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ\u001b\u0010\u001f\u001a\u00020\u00032\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010!\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010\"J\u001b\u0010!\u001a\u00020\b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010#J)\u0010&\u001a\u00020\u00032\u0018\u0010%\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u00030\u0002j\u0002`$H\u0016¢\u0006\u0004\b&\u0010\u0007J\u0019\u0010'\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0004\b+\u0010,J#\u0010/\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010.\u001a\u0006\u0012\u0002\b\u00030-H\u0014¢\u0006\u0004\b/\u00100J\u0017\u00102\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u000201H\u0014¢\u0006\u0004\b2\u00103JX\u00109\u001a\u00020\u0003\"\u0004\b\u0001\u001042\f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00010-2\u0006\u0010\u0010\u001a\u00028\u00002(\u00108\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000107\u0012\u0006\u0012\u0004\u0018\u00010\u001a05H\u0002ø\u0001\u0000¢\u0006\u0004\b9\u0010:J\u001b\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H\u0086@ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010;J\u001d\u0010=\u001a\b\u0012\u0002\b\u0003\u0018\u00010<2\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b=\u0010>J\u001b\u0010?\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0004\b?\u0010;J\u0017\u0010@\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010<H\u0014¢\u0006\u0004\b@\u0010AJ\u0011\u0010B\u001a\u0004\u0018\u00010\u0018H\u0004¢\u0006\u0004\bB\u0010CJ\u000f\u0010E\u001a\u00020DH\u0016¢\u0006\u0004\bE\u0010FJ$\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030G2\u0006\u0010\u0010\u001a\u00028\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\bH\u0010,J+\u0010J\u001a\u00020\u0003*\u0006\u0012\u0002\b\u0003072\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\bJ\u0010KR\u0016\u0010M\u001a\u00020D8T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\bL\u0010FR\u001c\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8D@\u0004X\u0084\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001c\u0010R\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8D@\u0004X\u0084\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010OR\u0016\u0010S\u001a\u00020\n8$@$X¤\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0016\u0010U\u001a\u00020\n8$@$X¤\u0004¢\u0006\u0006\u001a\u0004\bU\u0010TR\u0013\u0010V\u001a\u00020\n8F@\u0006¢\u0006\u0006\u001a\u0004\bV\u0010TR\u0016\u0010W\u001a\u00020\n8B@\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\bW\u0010TR%\u0010[\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000060X8F@\u0006¢\u0006\u0006\u001a\u0004\bY\u0010ZR0\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00048\u0004@\u0005X\u0085\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\\R\u001c\u0010^\u001a\u00020]8\u0004@\u0004X\u0084\u0004¢\u0006\f\n\u0004\b^\u0010_\u001a\u0004\b`\u0010aR\u0016\u0010c\u001a\u00020D8B@\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\bb\u0010F\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006h"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel;", "E", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", "element", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "helpCloseAndGetSendException", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "trySend", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "isBufferAlwaysFull", "()Z", "isBufferFull", "isClosedForSend", "isFullImpl", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public abstract class AbstractSendChannel<E> implements SendChannel<E> {
    private static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");
    @NotNull
    private final LockFreeLinkedListHead a = new LockFreeLinkedListHead();
    @NotNull
    private volatile /* synthetic */ Object onCloseHandler = null;
    @JvmField
    @Nullable
    protected final Function1<E, Unit> onUndeliveredElement;

    @NotNull
    protected String getBufferDebugString() {
        return "";
    }

    protected abstract boolean isBufferAlwaysFull();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean isBufferFull();

    protected void onClosedIdempotent(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractSendChannel(@Nullable Function1<? super E, Unit> function1) {
        this.onUndeliveredElement = function1;
    }

    @NotNull
    protected final LockFreeLinkedListHead getQueue() {
        return this.a;
    }

    @NotNull
    protected Object offerInternal(E e) {
        ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed;
        Symbol tryResumeReceive;
        do {
            takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
            if (takeFirstReceiveOrPeekClosed == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            tryResumeReceive = takeFirstReceiveOrPeekClosed.tryResumeReceive(e, null);
        } while (tryResumeReceive == null);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(tryResumeReceive == CancellableContinuationImplKt.RESUME_TOKEN)) {
                throw new AssertionError();
            }
        }
        takeFirstReceiveOrPeekClosed.completeResumeReceive(e);
        return takeFirstReceiveOrPeekClosed.getOfferResult();
    }

    @NotNull
    protected Object offerSelectInternal(E e, @NotNull SelectInstance<?> selectInstance) {
        TryOfferDesc<E> describeTryOffer = describeTryOffer(e);
        Object performAtomicTrySelect = selectInstance.performAtomicTrySelect(describeTryOffer);
        if (performAtomicTrySelect != null) {
            return performAtomicTrySelect;
        }
        ReceiveOrClosed<? super E> result = describeTryOffer.getResult();
        result.completeResumeReceive(e);
        return result.getOfferResult();
    }

    @Nullable
    protected final Closed<?> getClosedForSend() {
        LockFreeLinkedListNode prevNode = this.a.getPrevNode();
        Closed<?> closed = prevNode instanceof Closed ? (Closed) prevNode : null;
        if (closed == null) {
            return null;
        }
        b(closed);
        return closed;
    }

    @Nullable
    protected final Closed<?> getClosedForReceive() {
        LockFreeLinkedListNode nextNode = this.a.getNextNode();
        Closed<?> closed = nextNode instanceof Closed ? (Closed) nextNode : null;
        if (closed == null) {
            return null;
        }
        b(closed);
        return closed;
    }

    @Nullable
    protected final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        Send send;
        LockFreeLinkedListNode removeOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.a;
        while (true) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            send = null;
            if (lockFreeLinkedListNode == lockFreeLinkedListHead || !(lockFreeLinkedListNode instanceof Send)) {
                break;
            } else if ((!(((Send) lockFreeLinkedListNode) instanceof Closed) || lockFreeLinkedListNode.isRemoved()) && (removeOrNext = lockFreeLinkedListNode.removeOrNext()) != null) {
                removeOrNext.helpRemovePrev();
            }
        }
        send = lockFreeLinkedListNode;
        return send;
    }

    @Nullable
    protected final ReceiveOrClosed<?> sendBuffered(E e) {
        LockFreeLinkedListNode prevNode;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.a;
        SendBuffered sendBuffered = new SendBuffered(e);
        do {
            prevNode = lockFreeLinkedListHead.getPrevNode();
            if (prevNode instanceof ReceiveOrClosed) {
                return (ReceiveOrClosed) prevNode;
            }
        } while (!prevNode.addNext(sendBuffered, lockFreeLinkedListHead));
        return null;
    }

    @NotNull
    protected final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E e) {
        return new a(this.a, e);
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    private static class a<E> extends LockFreeLinkedListNode.AddLastDesc<SendBuffered<? extends E>> {
        public a(@NotNull LockFreeLinkedListHead lockFreeLinkedListHead, E e) {
            super(lockFreeLinkedListHead, new SendBuffered(e));
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode instanceof Closed) {
                return lockFreeLinkedListNode;
            }
            if (lockFreeLinkedListNode instanceof ReceiveOrClosed) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean isClosedForSend() {
        return getClosedForSend() != null;
    }

    public final boolean a() {
        return !(this.a.getNextNode() instanceof ReceiveOrClosed) && isBufferFull();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public final Object send(E e, @NotNull Continuation<? super Unit> continuation) {
        Object a2;
        return (offerInternal(e) != AbstractChannelKt.OFFER_SUCCESS && (a2 = a((AbstractSendChannel<E>) e, continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? a2 : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean offer(E e) {
        UndeliveredElementException callUndeliveredElementCatchingException$default;
        try {
            return SendChannel.DefaultImpls.offer(this, e);
        } catch (Throwable th) {
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            if (function1 == null || (callUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) == null) {
                throw th;
            }
            ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException$default, th);
            throw callUndeliveredElementCatchingException$default;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* renamed from: trySend-JP2dKIU */
    public final Object mo1761trySendJP2dKIU(E e) {
        Object offerInternal = offerInternal(e);
        if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
            return ChannelResult.Companion.m1781successJP2dKIU(Unit.INSTANCE);
        }
        if (offerInternal == AbstractChannelKt.OFFER_FAILED) {
            Closed<?> closedForSend = getClosedForSend();
            return closedForSend == null ? ChannelResult.Companion.m1780failurePtdJZtk() : ChannelResult.Companion.m1779closedJP2dKIU(a(closedForSend));
        } else if (offerInternal instanceof Closed) {
            return ChannelResult.Companion.m1779closedJP2dKIU(a((Closed) offerInternal));
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("trySend returned ", offerInternal).toString());
        }
    }

    private final Throwable a(Closed<?> closed) {
        b(closed);
        return closed.getSendException();
    }

    private final Throwable a(E e, Closed<?> closed) {
        UndeliveredElementException callUndeliveredElementCatchingException$default;
        b(closed);
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (callUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) == null) {
            return closed.getSendException();
        }
        ExceptionsKt.addSuppressed(callUndeliveredElementCatchingException$default, closed.getSendException());
        throw callUndeliveredElementCatchingException$default;
    }

    public final void a(Continuation<?> continuation, E e, Closed<?> closed) {
        UndeliveredElementException callUndeliveredElementCatchingException$default;
        b(closed);
        Throwable sendException = closed.getSendException();
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (callUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) == null) {
            Result.Companion companion = Result.Companion;
            continuation.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(sendException)));
            return;
        }
        UndeliveredElementException undeliveredElementException = callUndeliveredElementCatchingException$default;
        ExceptionsKt.addSuppressed(undeliveredElementException, sendException);
        Result.Companion companion2 = Result.Companion;
        continuation.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(undeliveredElementException)));
    }

    @Nullable
    protected Object enqueueSend(@NotNull Send send) {
        boolean z;
        LockFreeLinkedListNode prevNode;
        if (isBufferAlwaysFull()) {
            LockFreeLinkedListHead lockFreeLinkedListHead = this.a;
            do {
                prevNode = lockFreeLinkedListHead.getPrevNode();
                if (prevNode instanceof ReceiveOrClosed) {
                    return prevNode;
                }
            } while (!prevNode.addNext(send, lockFreeLinkedListHead));
            return null;
        }
        LockFreeLinkedListHead lockFreeLinkedListHead2 = this.a;
        Send send2 = send;
        LockFreeLinkedListNode.CondAddOp abstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1 = new LockFreeLinkedListNode.CondAddOp(this) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1
            final /* synthetic */ AbstractSendChannel b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(LockFreeLinkedListNode.this);
                this.b = this;
            }

            @Nullable
            public Object prepare(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
                if (this.b.isBufferFull()) {
                    return null;
                }
                return LockFreeLinkedListKt.getCONDITION_FALSE();
            }
        };
        while (true) {
            LockFreeLinkedListNode prevNode2 = lockFreeLinkedListHead2.getPrevNode();
            if (!(prevNode2 instanceof ReceiveOrClosed)) {
                switch (prevNode2.tryCondAddNext(send2, lockFreeLinkedListHead2, abstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1)) {
                    case 1:
                        z = true;
                        break;
                    case 2:
                        z = false;
                        break;
                }
            } else {
                return prevNode2;
            }
        }
        if (!z) {
            return AbstractChannelKt.ENQUEUE_FAILED;
        }
        return null;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable th) {
        boolean z;
        Closed<?> closed = new Closed<>(th);
        LockFreeLinkedListHead lockFreeLinkedListHead = this.a;
        while (true) {
            LockFreeLinkedListNode prevNode = lockFreeLinkedListHead.getPrevNode();
            z = true;
            if (!(prevNode instanceof Closed)) {
                if (prevNode.addNext(closed, lockFreeLinkedListHead)) {
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            closed = (Closed) this.a.getPrevNode();
        }
        b(closed);
        if (z) {
            a(th);
        }
        return z;
    }

    private final void a(Throwable th) {
        Object obj = this.onCloseHandler;
        if (obj != null && obj != AbstractChannelKt.HANDLER_INVOKED && b.compareAndSet(this, obj, AbstractChannelKt.HANDLER_INVOKED)) {
            ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(obj, 1)).invoke(th);
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(@NotNull Function1<? super Throwable, Unit> function1) {
        if (!b.compareAndSet(this, null, function1)) {
            Object obj = this.onCloseHandler;
            if (obj == AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Another handler was already registered: ", obj));
        }
        Closed<?> closedForSend = getClosedForSend();
        if (closedForSend != null && b.compareAndSet(this, function1, AbstractChannelKt.HANDLER_INVOKED)) {
            function1.invoke(closedForSend.closeCause);
        }
    }

    private final void b(Closed<?> closed) {
        Object obj = InlineList.m1790constructorimpl$default(null, 1, null);
        while (true) {
            LockFreeLinkedListNode prevNode = closed.getPrevNode();
            Receive receive = prevNode instanceof Receive ? (Receive) prevNode : null;
            if (receive == null) {
                break;
            } else if (!receive.remove()) {
                receive.helpRemove();
            } else {
                obj = InlineList.m1795plusFjFbRPM(obj, receive);
            }
        }
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                ((Receive) obj).resumeReceiveClosed(closed);
            } else if (obj != null) {
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size() - 1;
                if (size >= 0) {
                    while (true) {
                        int i = size - 1;
                        ((Receive) arrayList.get(size)).resumeReceiveClosed(closed);
                        if (i < 0) {
                            break;
                        }
                        size = i;
                    }
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
        onClosedIdempotent(closed);
    }

    @Nullable
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        ReceiveOrClosed<E> receiveOrClosed;
        LockFreeLinkedListNode removeOrNext;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.a;
        while (true) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext();
            receiveOrClosed = null;
            if (lockFreeLinkedListNode == lockFreeLinkedListHead || !(lockFreeLinkedListNode instanceof ReceiveOrClosed)) {
                break;
            } else if ((!(((ReceiveOrClosed) lockFreeLinkedListNode) instanceof Closed) || lockFreeLinkedListNode.isRemoved()) && (removeOrNext = lockFreeLinkedListNode.removeOrNext()) != null) {
                removeOrNext.helpRemovePrev();
            }
        }
        receiveOrClosed = lockFreeLinkedListNode;
        return receiveOrClosed;
    }

    @NotNull
    protected final TryOfferDesc<E> describeTryOffer(E e) {
        return new TryOfferDesc<>(e, this.a);
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\n\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011H\u0016R\u0012\u0010\u0005\u001a\u00028\u00018\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "element", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "Ljava/lang/Object;", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class TryOfferDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>> {
        @JvmField
        public final E element;

        public TryOfferDesc(E e, @NotNull LockFreeLinkedListHead lockFreeLinkedListHead) {
            super(lockFreeLinkedListHead);
            this.element = e;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc, kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode instanceof Closed) {
                return lockFreeLinkedListNode;
            }
            if (!(lockFreeLinkedListNode instanceof ReceiveOrClosed)) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        public Object onPrepare(@NotNull LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol tryResumeReceive = ((ReceiveOrClosed) prepareOp.affected).tryResumeReceive(this.element, prepareOp);
            if (tryResumeReceive == null) {
                return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            }
            if (tryResumeReceive == AtomicKt.RETRY_ATOMIC) {
                return AtomicKt.RETRY_ATOMIC;
            }
            if (!DebugKt.getASSERTIONS_ENABLED()) {
                return null;
            }
            if (tryResumeReceive == CancellableContinuationImplKt.RESUME_TOKEN) {
                return null;
            }
            throw new AssertionError();
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public final SelectClause2<E, SendChannel<E>> getOnSend() {
        return (SelectClause2) ((SelectClause2<E, SendChannel<? super E>>) new SelectClause2<E, SendChannel<? super E>>(this) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$onSend$1
            final /* synthetic */ AbstractSendChannel<E> a;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.a = this;
            }

            @Override // kotlinx.coroutines.selects.SelectClause2
            public <R> void registerSelectClause2(@NotNull SelectInstance<? super R> selectInstance, E e, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> function2) {
                this.a.a(selectInstance, (SelectInstance) e, (Function2<? super SendChannel<? super SelectInstance>, ? super Continuation<? super R>, ? extends Object>) function2);
            }
        });
    }

    public final <R> void a(SelectInstance<? super R> selectInstance, E e, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> function2) {
        while (!selectInstance.isSelected()) {
            if (a()) {
                b bVar = new b(e, this, selectInstance, function2);
                Object enqueueSend = enqueueSend(bVar);
                if (enqueueSend == null) {
                    selectInstance.disposeOnSelect(bVar);
                    return;
                } else if (enqueueSend instanceof Closed) {
                    throw StackTraceRecoveryKt.recoverStackTrace(a((AbstractSendChannel<E>) e, (Closed) enqueueSend));
                } else if (enqueueSend != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueSend instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + enqueueSend + ' ').toString());
                }
            }
            Object offerSelectInternal = offerSelectInternal(e, selectInstance);
            if (offerSelectInternal != SelectKt.getALREADY_SELECTED()) {
                if (offerSelectInternal != AbstractChannelKt.OFFER_FAILED && offerSelectInternal != AtomicKt.RETRY_ATOMIC) {
                    if (offerSelectInternal == AbstractChannelKt.OFFER_SUCCESS) {
                        UndispatchedKt.startCoroutineUnintercepted(function2, this, selectInstance.getCompletion());
                        return;
                    } else if (offerSelectInternal instanceof Closed) {
                        throw StackTraceRecoveryKt.recoverStackTrace(a((AbstractSendChannel<E>) e, (Closed) offerSelectInternal));
                    } else {
                        throw new IllegalStateException(Intrinsics.stringPlus("offerSelectInternal returned ", offerSelectInternal).toString());
                    }
                }
            } else {
                return;
            }
        }
    }

    @NotNull
    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '{' + b() + '}' + getBufferDebugString();
    }

    private final String b() {
        String str;
        LockFreeLinkedListNode nextNode = this.a.getNextNode();
        if (nextNode == this.a) {
            return "EmptyQueue";
        }
        if (nextNode instanceof Closed) {
            str = nextNode.toString();
        } else if (nextNode instanceof Receive) {
            str = "ReceiveQueued";
        } else {
            str = nextNode instanceof Send ? "SendQueued" : Intrinsics.stringPlus("UNEXPECTED:", nextNode);
        }
        LockFreeLinkedListNode prevNode = this.a.getPrevNode();
        if (prevNode == nextNode) {
            return str;
        }
        String str2 = str + ",queueSize=" + c();
        if (!(prevNode instanceof Closed)) {
            return str2;
        }
        return str2 + ",closedForSend=" + prevNode;
    }

    private final int c() {
        LockFreeLinkedListHead lockFreeLinkedListHead = this.a;
        int i = 0;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListHead.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, lockFreeLinkedListHead); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof LockFreeLinkedListNode) {
                i++;
            }
        }
        return i;
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\u00020\u00032\u00020\u0004BV\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t\u0012(\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000bø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0014\u0010\u0017\u001a\u00020\u00152\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0015H\u0016R7\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000b8\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", "E", "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "channel", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/AbstractSendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "undeliveredElement", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b<E, R> extends Send implements DisposableHandle {
        @JvmField
        @NotNull
        public final AbstractSendChannel<E> a;
        @JvmField
        @NotNull
        public final SelectInstance<R> b;
        @JvmField
        @NotNull
        public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> c;
        private final E d;

        @Override // kotlinx.coroutines.channels.Send
        public E getPollResult() {
            return this.d;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public b(E e, @NotNull AbstractSendChannel<E> abstractSendChannel, @NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> function2) {
            this.d = e;
            this.a = abstractSendChannel;
            this.b = selectInstance;
            this.c = function2;
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Symbol tryResumeSend(@Nullable LockFreeLinkedListNode.PrepareOp prepareOp) {
            return (Symbol) this.b.trySelectOther(prepareOp);
        }

        @Override // kotlinx.coroutines.channels.Send
        public void completeResumeSend() {
            CancellableKt.startCoroutineCancellable$default(this.c, this.a, this.b.getCompletion(), null, 4, null);
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public void dispose() {
            if (remove()) {
                undeliveredElement();
            }
        }

        @Override // kotlinx.coroutines.channels.Send
        public void resumeSendClosed(@NotNull Closed<?> closed) {
            if (this.b.trySelect()) {
                this.b.resumeSelectWithException(closed.getSendException());
            }
        }

        @Override // kotlinx.coroutines.channels.Send
        public void undeliveredElement() {
            Function1<E, Unit> function1 = this.a.onUndeliveredElement;
            if (function1 != null) {
                OnUndeliveredElementKt.callUndeliveredElement(function1, getPollResult(), this.b.getCompletion().getContext());
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "SendSelect@" + DebugStringsKt.getHexAddress(this) + '(' + getPollResult() + ")[" + this.a + ", " + this.b + ']';
        }
    }

    /* compiled from: AbstractChannel.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0012\u0010\u0003\u001a\u00028\u00018\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "E", "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class SendBuffered<E> extends Send {
        @JvmField
        public final E element;

        @Override // kotlinx.coroutines.channels.Send
        public void completeResumeSend() {
        }

        public SendBuffered(E e) {
            this.element = e;
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Object getPollResult() {
            return this.element;
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Symbol tryResumeSend(@Nullable LockFreeLinkedListNode.PrepareOp prepareOp) {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            if (prepareOp != null) {
                prepareOp.finishPrepare();
            }
            return symbol;
        }

        @Override // kotlinx.coroutines.channels.Send
        public void resumeSendClosed(@NotNull Closed<?> closed) {
            if (DebugKt.getASSERTIONS_ENABLED()) {
                throw new AssertionError();
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "SendBuffered@" + DebugStringsKt.getHexAddress(this) + '(' + this.element + ')';
        }
    }

    public final Object a(E e, Continuation<? super Unit> continuation) {
        SendElementWithUndeliveredHandler sendElementWithUndeliveredHandler;
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation;
        while (true) {
            if (a()) {
                Function1<E, Unit> function1 = this.onUndeliveredElement;
                if (function1 == null) {
                    sendElementWithUndeliveredHandler = new SendElement(e, cancellableContinuationImpl);
                } else {
                    sendElementWithUndeliveredHandler = new SendElementWithUndeliveredHandler(e, cancellableContinuationImpl, function1);
                }
                Object enqueueSend = enqueueSend(sendElementWithUndeliveredHandler);
                if (enqueueSend == null) {
                    CancellableContinuationKt.removeOnCancellation(cancellableContinuationImpl, sendElementWithUndeliveredHandler);
                    break;
                } else if (enqueueSend instanceof Closed) {
                    a((Continuation<?>) cancellableContinuationImpl, (CancellableContinuationImpl) e, (Closed<?>) enqueueSend);
                    break;
                } else if (enqueueSend != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueSend instanceof Receive)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("enqueueSend returned ", enqueueSend).toString());
                }
            }
            Object offerInternal = offerInternal(e);
            if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m1220constructorimpl(unit));
                break;
            } else if (offerInternal != AbstractChannelKt.OFFER_FAILED) {
                if (offerInternal instanceof Closed) {
                    a((Continuation<?>) cancellableContinuationImpl, (CancellableContinuationImpl) e, (Closed<?>) offerInternal);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("offerInternal returned ", offerInternal).toString());
                }
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }
}
