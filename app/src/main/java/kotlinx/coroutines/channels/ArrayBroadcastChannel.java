package kotlinx.coroutines.channels;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.onetrack.api.b;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ArrayBroadcastChannel.kt */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000K2\b\u0012\u0004\u0012\u00028\u00000L:\u0001IB\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0017¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\t\u001a\u00020\r2\u000e\u0010\u0007\u001a\n\u0018\u00010\u000bj\u0004\u0018\u0001`\fH\u0016¢\u0006\u0004\b\t\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u000f\u0010\nJ\u000f\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0004\b\u0012\u0010\nJ\u000f\u0010\u0014\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u0000H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ#\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0014¢\u0006\u0004\b\u001f\u0010 J\u0015\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0016¢\u0006\u0004\b\"\u0010#J4\u0010'\u001a\u00020\r2\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$2\u0010\b\u0002\u0010&\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$H\u0082\u0010¢\u0006\u0004\b'\u0010(R\u001e\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0)8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\u0016\u0010/\u001a\u00020,8T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u001a\u00102\u001a\u000600j\u0002`18\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b2\u00103R\u0019\u0010\u0003\u001a\u00020\u00028\u0006@\u0006¢\u0006\f\n\u0004\b\u0003\u00104\u001a\u0004\b5\u00106R$\u0010;\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b8\u0010\u0015\"\u0004\b9\u0010:R\u0016\u0010<\u001a\u00020\b8T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0016\u0010>\u001a\u00020\b8T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b>\u0010=R$\u0010A\u001a\u00020\u00022\u0006\u00107\u001a\u00020\u00028B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b?\u00106\"\u0004\b@\u0010\u0005R2\u0010D\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$0Bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$`C8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bD\u0010ER$\u0010H\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bF\u0010\u0015\"\u0004\bG\u0010:¨\u0006J"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "E", "", "capacity", "<init>", "(I)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "checkSubOffers", "()V", "close", "", "computeMinHead", "()J", MiSoundBoxCommandExtras.INDEX, "elementAt", "(J)Ljava/lang/Object;", "element", "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "I", "getCapacity", "()I", b.p, "getHead", "setHead", "(J)V", "head", "isBufferAlwaysFull", "()Z", "isBufferFull", "getSize", "setSize", "size", "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getTail", "setTail", "tail", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ArrayBroadcastChannel<E> extends AbstractSendChannel<E> implements BroadcastChannel<E> {
    @NotNull
    private volatile /* synthetic */ long _head;
    @NotNull
    private volatile /* synthetic */ int _size;
    @NotNull
    private volatile /* synthetic */ long _tail;
    private final int a;
    @NotNull
    private final ReentrantLock b;
    @NotNull
    private final Object[] c;
    @NotNull
    private final List<a<E>> d;

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected boolean isBufferAlwaysFull() {
        return false;
    }

    public final int getCapacity() {
        return this.a;
    }

    public ArrayBroadcastChannel(int i) {
        super(null);
        this.a = i;
        if (this.a < 1 ? false : true) {
            this.b = new ReentrantLock();
            this.c = new Object[this.a];
            this._head = 0L;
            this._tail = 0L;
            this._size = 0;
            this.d = ConcurrentKt.subscriberList();
            return;
        }
        throw new IllegalArgumentException(("ArrayBroadcastChannel capacity must be at least 1, but " + getCapacity() + " was specified").toString());
    }

    private final long a() {
        return this._head;
    }

    private final void a(long j) {
        this._head = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long b() {
        return this._tail;
    }

    private final void b(long j) {
        this._tail = j;
    }

    private final int c() {
        return this._size;
    }

    private final void a(int i) {
        this._size = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public boolean isBufferFull() {
        return c() >= this.a;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        a aVar = new a(this);
        a(this, aVar, null, 2, null);
        return aVar;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable th) {
        if (!super.close(th)) {
            return false;
        }
        d();
        return true;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public void cancel(@Nullable CancellationException cancellationException) {
        cancel((Throwable) cancellationException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public final boolean cancel(Throwable th) {
        boolean close = close(th);
        for (a<E> aVar : this.d) {
            aVar.cancel(th);
        }
        return close;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected Object offerInternal(E e) {
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int c = c();
            if (c >= getCapacity()) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            long b = b();
            this.c[(int) (b % getCapacity())] = e;
            a(c + 1);
            b(b + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            d();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected Object offerSelectInternal(E e, @NotNull SelectInstance<?> selectInstance) {
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int c = c();
            if (c >= getCapacity()) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            if (!selectInstance.trySelect()) {
                return SelectKt.getALREADY_SELECTED();
            }
            long b = b();
            this.c[(int) (b % getCapacity())] = e;
            a(c + 1);
            b(b + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            d();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            reentrantLock.unlock();
        }
    }

    private final void d() {
        boolean z = false;
        boolean z2 = false;
        for (a<E> aVar : this.d) {
            if (aVar.b()) {
                z = true;
                z2 = true;
            } else {
                z2 = true;
            }
        }
        if (z || !z2) {
            a(this, null, null, 3, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void a(ArrayBroadcastChannel arrayBroadcastChannel, a aVar, a aVar2, int i, Object obj) {
        if ((i & 1) != 0) {
            aVar = null;
        }
        if ((i & 2) != 0) {
            aVar2 = null;
        }
        arrayBroadcastChannel.a(aVar, aVar2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a5, code lost:
        throw new java.lang.AssertionError();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a(kotlinx.coroutines.channels.ArrayBroadcastChannel.a<E> r14, kotlinx.coroutines.channels.ArrayBroadcastChannel.a<E> r15) {
        /*
            r13 = this;
            r0 = 0
        L_0x0001:
            java.util.concurrent.locks.ReentrantLock r1 = r13.b
            java.util.concurrent.locks.Lock r1 = (java.util.concurrent.locks.Lock) r1
            r1.lock()
            if (r14 == 0) goto L_0x0025
            long r2 = r13.b()     // Catch: all -> 0x0022
            r14.a(r2)     // Catch: all -> 0x0022
            java.util.List<kotlinx.coroutines.channels.ArrayBroadcastChannel$a<E>> r2 = r13.d     // Catch: all -> 0x0022
            boolean r2 = r2.isEmpty()     // Catch: all -> 0x0022
            java.util.List<kotlinx.coroutines.channels.ArrayBroadcastChannel$a<E>> r3 = r13.d     // Catch: all -> 0x0022
            r3.add(r14)     // Catch: all -> 0x0022
            if (r2 != 0) goto L_0x0025
            r1.unlock()
            return
        L_0x0022:
            r14 = move-exception
            goto L_0x00d2
        L_0x0025:
            if (r15 == 0) goto L_0x003c
            java.util.List<kotlinx.coroutines.channels.ArrayBroadcastChannel$a<E>> r14 = r13.d     // Catch: all -> 0x0022
            r14.remove(r15)     // Catch: all -> 0x0022
            long r2 = r13.a()     // Catch: all -> 0x0022
            long r14 = r15.a()     // Catch: all -> 0x0022
            int r14 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r14 == 0) goto L_0x003c
            r1.unlock()
            return
        L_0x003c:
            long r14 = r13.e()     // Catch: all -> 0x0022
            long r2 = r13.b()     // Catch: all -> 0x0022
            long r4 = r13.a()     // Catch: all -> 0x0022
            long r14 = kotlin.ranges.RangesKt.coerceAtMost(r14, r2)     // Catch: all -> 0x0022
            int r6 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r6 > 0) goto L_0x0054
            r1.unlock()
            return
        L_0x0054:
            int r6 = r13.c()     // Catch: all -> 0x0022
        L_0x0058:
            int r7 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r7 >= 0) goto L_0x00ce
            java.lang.Object[] r7 = r13.c     // Catch: all -> 0x0022
            int r8 = r13.getCapacity()     // Catch: all -> 0x0022
            long r8 = (long) r8     // Catch: all -> 0x0022
            long r8 = r4 % r8
            int r8 = (int) r8     // Catch: all -> 0x0022
            r7[r8] = r0     // Catch: all -> 0x0022
            int r7 = r13.getCapacity()     // Catch: all -> 0x0022
            r8 = 0
            r9 = 1
            if (r6 < r7) goto L_0x0072
            r7 = r9
            goto L_0x0073
        L_0x0072:
            r7 = r8
        L_0x0073:
            r10 = 1
            long r4 = r4 + r10
            r13.a(r4)     // Catch: all -> 0x0022
            int r6 = r6 + (-1)
            r13.a(r6)     // Catch: all -> 0x0022
            if (r7 == 0) goto L_0x0058
        L_0x0080:
            kotlinx.coroutines.channels.Send r7 = r13.takeFirstSendOrPeekClosed()     // Catch: all -> 0x0022
            if (r7 != 0) goto L_0x0087
            goto L_0x0058
        L_0x0087:
            boolean r12 = r7 instanceof kotlinx.coroutines.channels.Closed     // Catch: all -> 0x0022
            if (r12 == 0) goto L_0x008c
            goto L_0x0058
        L_0x008c:
            kotlinx.coroutines.internal.Symbol r12 = r7.tryResumeSend(r0)     // Catch: all -> 0x0022
            if (r12 == 0) goto L_0x0080
            boolean r14 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()     // Catch: all -> 0x0022
            if (r14 == 0) goto L_0x00a6
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN     // Catch: all -> 0x0022
            if (r12 != r14) goto L_0x009d
            r8 = r9
        L_0x009d:
            if (r8 == 0) goto L_0x00a0
            goto L_0x00a6
        L_0x00a0:
            java.lang.AssertionError r14 = new java.lang.AssertionError     // Catch: all -> 0x0022
            r14.<init>()     // Catch: all -> 0x0022
            throw r14     // Catch: all -> 0x0022
        L_0x00a6:
            java.lang.Object[] r14 = r13.c     // Catch: all -> 0x0022
            int r15 = r13.getCapacity()     // Catch: all -> 0x0022
            long r4 = (long) r15     // Catch: all -> 0x0022
            long r4 = r2 % r4
            int r15 = (int) r4     // Catch: all -> 0x0022
            java.lang.Object r4 = r7.getPollResult()     // Catch: all -> 0x0022
            r14[r15] = r4     // Catch: all -> 0x0022
            int r6 = r6 + 1
            r13.a(r6)     // Catch: all -> 0x0022
            long r2 = r2 + r10
            r13.b(r2)     // Catch: all -> 0x0022
            kotlin.Unit r14 = kotlin.Unit.INSTANCE     // Catch: all -> 0x0022
            r1.unlock()
            r7.completeResumeSend()
            r13.d()
            r14 = r0
            r15 = r14
            goto L_0x0001
        L_0x00ce:
            r1.unlock()
            return
        L_0x00d2:
            r1.unlock()
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ArrayBroadcastChannel.a(kotlinx.coroutines.channels.ArrayBroadcastChannel$a, kotlinx.coroutines.channels.ArrayBroadcastChannel$a):void");
    }

    private final long e() {
        long j = Long.MAX_VALUE;
        for (a<E> aVar : this.d) {
            j = RangesKt.coerceAtMost(j, aVar.a());
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final E c(long j) {
        return (E) this.c[(int) (j % this.a)];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ArrayBroadcastChannel.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010'2\b\u0012\u0004\u0012\u00028\u00010(B\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\r\u0010\bJ\u0011\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u0011\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0014¢\u0006\u0004\b\u0011\u0010\u0010J\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0014¢\u0006\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00028\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0016R\u0016\u0010\u0017\u001a\u00020\u00068T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0016\u0010\u0018\u001a\u00020\u00068T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\bR\u0016\u0010\u0019\u001a\u00020\u00068T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\bR\u0016\u0010\u001a\u001a\u00020\u00068T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\bR$\u0010!\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010$\u001a\u00060\"j\u0002`#8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "", "checkOffer", "()Z", "", "cause", "close", "(Ljava/lang/Throwable;)Z", "needsToCheckOfferWithoutLock", "", "peekUnderLock", "()Ljava/lang/Object;", "pollInternal", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "isBufferAlwaysEmpty", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "", b.p, "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a<E> extends AbstractChannel<E> implements ReceiveChannel<E> {
        @NotNull
        private final ArrayBroadcastChannel<E> a;
        @NotNull
        private final ReentrantLock b = new ReentrantLock();
        @NotNull
        private volatile /* synthetic */ long _subHead = 0;

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected boolean isBufferAlwaysEmpty() {
            return false;
        }

        public a(@NotNull ArrayBroadcastChannel<E> arrayBroadcastChannel) {
            super(null);
            this.a = arrayBroadcastChannel;
        }

        public final long a() {
            return this._subHead;
        }

        public final void a(long j) {
            this._subHead = j;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // kotlinx.coroutines.channels.AbstractChannel
        public boolean isBufferEmpty() {
            return a() >= this.a.b();
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        protected boolean isBufferAlwaysFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        public boolean isBufferFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
        public boolean close(@Nullable Throwable th) {
            boolean close = super.close(th);
            if (close) {
                ArrayBroadcastChannel.a(this.a, null, this, 1, null);
                ReentrantLock reentrantLock = this.b;
                reentrantLock.lock();
                try {
                    a(this.a.b());
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock.unlock();
                }
            }
            return close;
        }

        public final boolean b() {
            Closed closed;
            boolean z = false;
            while (true) {
                closed = null;
                if (!c() || !this.b.tryLock()) {
                    break;
                }
                try {
                    E e = (E) d();
                    if (e != AbstractChannelKt.POLL_FAILED) {
                        if (e instanceof Closed) {
                            closed = (Closed) e;
                            break;
                        }
                        ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                        if (takeFirstReceiveOrPeekClosed != null && !(takeFirstReceiveOrPeekClosed instanceof Closed)) {
                            Symbol tryResumeReceive = takeFirstReceiveOrPeekClosed.tryResumeReceive(e, null);
                            if (tryResumeReceive != null) {
                                if (DebugKt.getASSERTIONS_ENABLED()) {
                                    if (!(tryResumeReceive == CancellableContinuationImplKt.RESUME_TOKEN)) {
                                        throw new AssertionError();
                                    }
                                }
                                a(a() + 1);
                                this.b.unlock();
                                takeFirstReceiveOrPeekClosed.completeResumeReceive(e);
                                z = true;
                            }
                        }
                    }
                } finally {
                    this.b.unlock();
                }
            }
            if (closed != null) {
                close(closed.closeCause);
            }
            return z;
        }

        /* JADX WARN: Finally extract failed */
        /* JADX WARN: Removed duplicated region for block: B:13:0x002a  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0040  */
        @Override // kotlinx.coroutines.channels.AbstractChannel
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected java.lang.Object pollInternal() {
            /*
                r8 = this;
                java.util.concurrent.locks.ReentrantLock r0 = r8.b
                java.util.concurrent.locks.Lock r0 = (java.util.concurrent.locks.Lock) r0
                r0.lock()
                java.lang.Object r1 = r8.d()     // Catch: all -> 0x0047
                boolean r2 = r1 instanceof kotlinx.coroutines.channels.Closed     // Catch: all -> 0x0047
                r3 = 1
                if (r2 == 0) goto L_0x0011
                goto L_0x0015
            L_0x0011:
                kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.AbstractChannelKt.POLL_FAILED     // Catch: all -> 0x0047
                if (r1 != r2) goto L_0x0017
            L_0x0015:
                r2 = 0
                goto L_0x0022
            L_0x0017:
                long r4 = r8.a()     // Catch: all -> 0x0047
                r6 = 1
                long r4 = r4 + r6
                r8.a(r4)     // Catch: all -> 0x0047
                r2 = r3
            L_0x0022:
                r0.unlock()
                boolean r0 = r1 instanceof kotlinx.coroutines.channels.Closed
                r4 = 0
                if (r0 == 0) goto L_0x002e
                r0 = r1
                kotlinx.coroutines.channels.Closed r0 = (kotlinx.coroutines.channels.Closed) r0
                goto L_0x002f
            L_0x002e:
                r0 = r4
            L_0x002f:
                if (r0 != 0) goto L_0x0032
                goto L_0x0037
            L_0x0032:
                java.lang.Throwable r0 = r0.closeCause
                r8.close(r0)
            L_0x0037:
                boolean r0 = r8.b()
                if (r0 == 0) goto L_0x003e
                r2 = r3
            L_0x003e:
                if (r2 == 0) goto L_0x0046
                kotlinx.coroutines.channels.ArrayBroadcastChannel<E> r0 = r8.a
                r2 = 3
                kotlinx.coroutines.channels.ArrayBroadcastChannel.a(r0, r4, r4, r2, r4)
            L_0x0046:
                return r1
            L_0x0047:
                r1 = move-exception
                r0.unlock()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ArrayBroadcastChannel.a.pollInternal():java.lang.Object");
        }

        /* JADX WARN: Finally extract failed */
        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        protected Object pollSelectInternal(@NotNull SelectInstance<?> selectInstance) {
            ReentrantLock reentrantLock = this.b;
            reentrantLock.lock();
            try {
                Object d = d();
                boolean z = true;
                z = false;
                if (!(d instanceof Closed) && d != AbstractChannelKt.POLL_FAILED) {
                    if (!selectInstance.trySelect()) {
                        d = SelectKt.getALREADY_SELECTED();
                    } else {
                        a(a() + 1);
                        z = true;
                    }
                }
                reentrantLock.unlock();
                Closed closed = d instanceof Closed ? (Closed) d : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (!b()) {
                }
                if (z) {
                    ArrayBroadcastChannel.a(this.a, null, null, 3, null);
                }
                return d;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        private final boolean c() {
            if (getClosedForReceive() != null) {
                return false;
            }
            return !isBufferEmpty() || this.a.getClosedForReceive() != null;
        }

        private final Object d() {
            long a = a();
            Closed<?> closedForReceive = this.a.getClosedForReceive();
            if (a < this.a.b()) {
                Object c = this.a.c(a);
                Closed<?> closedForReceive2 = getClosedForReceive();
                return closedForReceive2 != null ? closedForReceive2 : c;
            } else if (closedForReceive != null) {
                return closedForReceive;
            } else {
                Closed<?> closedForReceive3 = getClosedForReceive();
                return closedForReceive3 == null ? AbstractChannelKt.POLL_FAILED : closedForReceive3;
            }
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected String getBufferDebugString() {
        return "(buffer:capacity=" + this.c.length + ",size=" + c() + ')';
    }
}
