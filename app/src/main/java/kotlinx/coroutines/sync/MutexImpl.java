package kotlinx.coroutines.sync;

import com.milink.base.contract.LockContract;
import com.xiaomi.onetrack.OneTrack;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Mutex.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u00112\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110 :\u0006$%&'()B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\n\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0096@ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001d\u0010\f\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0082@ø\u0001\u0000¢\u0006\u0004\b\f\u0010\u000bJT\u0010\u0014\u001a\u00020\t\"\u0004\b\u0000\u0010\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\"\u0010\u0013\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0010H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u0019\u001a\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\u0019\u0010\bJ\u0019\u0010\u001a\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001c\u001a\u00020\u00018V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u001f\u001a\u00020\u00018@@\u0000X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001dR$\u0010#\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110 8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006*"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl;", "", "locked", "<init>", "(Z)V", "", "owner", "holdsLock", "(Ljava/lang/Object;)Z", "", LockContract.Matcher.LOCK, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "toString", "()Ljava/lang/String;", "tryLock", OneTrack.Event.UNLOCK, "(Ljava/lang/Object;)V", "isLocked", "()Z", "isLockedEmptyQueueState$kotlinx_coroutines_core", "isLockedEmptyQueueState", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLock", "LockCont", "LockSelect", "LockWaiter", "LockedQueue", "TryLockDesc", "UnlockOp", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class MutexImpl implements SelectClause2<Object, Mutex>, Mutex {
    static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(MutexImpl.class, Object.class, "_state");
    @NotNull
    volatile /* synthetic */ Object _state;

    public MutexImpl(boolean z) {
        this._state = z ? MutexKt.f : MutexKt.g;
    }

    public final boolean isLockedEmptyQueueState$kotlinx_coroutines_core() {
        Object obj = this._state;
        return (obj instanceof d) && ((d) obj).isEmpty();
    }

    @Override // kotlinx.coroutines.sync.Mutex
    @Nullable
    public Object lock(@Nullable Object obj, @NotNull Continuation<? super Unit> continuation) {
        Object a2;
        return (!tryLock(obj) && (a2 = a(obj, continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? a2 : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "it", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class g extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ Object $owner;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g(Object obj) {
            super(1);
            this.$owner = obj;
        }

        public final void a(@NotNull Throwable th) {
            MutexImpl.this.unlock(this.$owner);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }
    }

    @Override // kotlinx.coroutines.sync.Mutex
    @NotNull
    public SelectClause2<Object, Mutex> getOnLock() {
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0083 A[LOOP_START] */
    @Override // kotlinx.coroutines.selects.SelectClause2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <R> void registerSelectClause2(@org.jetbrains.annotations.NotNull kotlinx.coroutines.selects.SelectInstance<? super R> r8, @org.jetbrains.annotations.Nullable java.lang.Object r9, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super kotlinx.coroutines.sync.Mutex, ? super kotlin.coroutines.Continuation<? super R>, ? extends java.lang.Object> r10) {
        /*
            r7 = this;
        L_0x0000:
            boolean r0 = r8.isSelected()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.Object r0 = r7._state
            boolean r1 = r0 instanceof kotlinx.coroutines.sync.b
            if (r1 == 0) goto L_0x005f
            r1 = r0
            kotlinx.coroutines.sync.b r1 = (kotlinx.coroutines.sync.b) r1
            java.lang.Object r2 = r1.a
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.sync.MutexKt.access$getUNLOCKED$p()
            if (r2 == r3) goto L_0x0025
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = kotlinx.coroutines.sync.MutexImpl.a
            kotlinx.coroutines.sync.MutexImpl$d r3 = new kotlinx.coroutines.sync.MutexImpl$d
            java.lang.Object r1 = r1.a
            r3.<init>(r1)
            r2.compareAndSet(r7, r0, r3)
            goto L_0x0000
        L_0x0025:
            kotlinx.coroutines.sync.MutexImpl$e r0 = new kotlinx.coroutines.sync.MutexImpl$e
            r0.<init>(r7, r9)
            kotlinx.coroutines.internal.AtomicDesc r0 = (kotlinx.coroutines.internal.AtomicDesc) r0
            java.lang.Object r0 = r8.performAtomicTrySelect(r0)
            if (r0 != 0) goto L_0x003a
            kotlin.coroutines.Continuation r8 = r8.getCompletion()
            kotlinx.coroutines.intrinsics.UndispatchedKt.startCoroutineUnintercepted(r10, r7, r8)
            return
        L_0x003a:
            java.lang.Object r1 = kotlinx.coroutines.selects.SelectKt.getALREADY_SELECTED()
            if (r0 != r1) goto L_0x0041
            return
        L_0x0041:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.sync.MutexKt.access$getLOCK_FAIL$p()
            if (r0 != r1) goto L_0x0048
            goto L_0x0000
        L_0x0048:
            java.lang.Object r1 = kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC
            if (r0 != r1) goto L_0x004d
            goto L_0x0000
        L_0x004d:
            java.lang.String r8 = "performAtomicTrySelect(TryLockDesc) returned "
            java.lang.String r8 = kotlin.jvm.internal.Intrinsics.stringPlus(r8, r0)
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r8 = r8.toString()
            r9.<init>(r8)
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            throw r9
        L_0x005f:
            boolean r1 = r0 instanceof kotlinx.coroutines.sync.MutexImpl.d
            if (r1 == 0) goto L_0x00aa
            r1 = r0
            kotlinx.coroutines.sync.MutexImpl$d r1 = (kotlinx.coroutines.sync.MutexImpl.d) r1
            java.lang.Object r1 = r1.a
            r2 = 1
            r3 = 0
            if (r1 == r9) goto L_0x006e
            r1 = r2
            goto L_0x006f
        L_0x006e:
            r1 = r3
        L_0x006f:
            if (r1 == 0) goto L_0x0098
            kotlinx.coroutines.sync.MutexImpl$b r1 = new kotlinx.coroutines.sync.MutexImpl$b
            r1.<init>(r9, r8, r10)
            r4 = r0
            kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r4
            kotlinx.coroutines.sync.MutexImpl$registerSelectClause2$$inlined$addLastIf$1 r5 = new kotlinx.coroutines.sync.MutexImpl$registerSelectClause2$$inlined$addLastIf$1
            r6 = r1
            kotlinx.coroutines.internal.LockFreeLinkedListNode r6 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r6
            r5.<init>(r7, r0)
            kotlinx.coroutines.internal.LockFreeLinkedListNode$CondAddOp r5 = (kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp) r5
        L_0x0083:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = r4.getPrevNode()
            int r0 = r0.tryCondAddNext(r6, r4, r5)
            switch(r0) {
                case 1: goto L_0x0090;
                case 2: goto L_0x008f;
                default: goto L_0x008e;
            }
        L_0x008e:
            goto L_0x0083
        L_0x008f:
            r2 = r3
        L_0x0090:
            if (r2 == 0) goto L_0x0000
            kotlinx.coroutines.DisposableHandle r1 = (kotlinx.coroutines.DisposableHandle) r1
            r8.disposeOnSelect(r1)
            return
        L_0x0098:
            java.lang.String r8 = "Already locked by "
            java.lang.String r8 = kotlin.jvm.internal.Intrinsics.stringPlus(r8, r9)
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r8 = r8.toString()
            r9.<init>(r8)
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            throw r9
        L_0x00aa:
            boolean r1 = r0 instanceof kotlinx.coroutines.internal.OpDescriptor
            if (r1 == 0) goto L_0x00b5
            kotlinx.coroutines.internal.OpDescriptor r0 = (kotlinx.coroutines.internal.OpDescriptor) r0
            r0.perform(r7)
            goto L_0x0000
        L_0x00b5:
            java.lang.String r8 = "Illegal state "
            java.lang.String r8 = kotlin.jvm.internal.Intrinsics.stringPlus(r8, r0)
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r8 = r8.toString()
            r9.<init>(r8)
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.MutexImpl.registerSelectClause2(kotlinx.coroutines.selects.SelectInstance, java.lang.Object, kotlin.jvm.functions.Function2):void");
    }

    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u0016J\u0016\u0010\f\u001a\u0004\u0018\u00010\u00052\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "mutex", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "prepare", "PrepareOp", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    private static final class e extends AtomicDesc {
        @JvmField
        @NotNull
        public final MutexImpl a;
        @JvmField
        @Nullable
        public final Object b;

        public e(@NotNull MutexImpl mutexImpl, @Nullable Object obj) {
            this.a = mutexImpl;
            this.b = obj;
        }

        /* compiled from: Mutex.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "(Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;Lkotlinx/coroutines/internal/AtomicOp;)V", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes5.dex */
        private final class a extends OpDescriptor {
            @NotNull
            private final AtomicOp<?> b;

            public a(AtomicOp<?> atomicOp) {
                this.b = atomicOp;
            }

            @Override // kotlinx.coroutines.internal.OpDescriptor
            @NotNull
            public AtomicOp<?> getAtomicOp() {
                return this.b;
            }

            @Override // kotlinx.coroutines.internal.OpDescriptor
            @Nullable
            public Object perform(@Nullable Object obj) {
                Object atomicOp = getAtomicOp().isDecided() ? MutexKt.g : getAtomicOp();
                if (obj != null) {
                    MutexImpl.a.compareAndSet((MutexImpl) obj, this, atomicOp);
                    return null;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
            }
        }

        @Override // kotlinx.coroutines.internal.AtomicDesc
        @Nullable
        public Object prepare(@NotNull AtomicOp<?> atomicOp) {
            b bVar;
            Symbol symbol;
            a aVar = new a(atomicOp);
            MutexImpl mutexImpl = this.a;
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = MutexImpl.a;
            bVar = MutexKt.g;
            if (atomicReferenceFieldUpdater.compareAndSet(mutexImpl, bVar, aVar)) {
                return aVar.perform(this.a);
            }
            symbol = MutexKt.a;
            return symbol;
        }

        @Override // kotlinx.coroutines.internal.AtomicDesc
        public void complete(@NotNull AtomicOp<?> atomicOp, @Nullable Object obj) {
            b bVar;
            if (obj != null) {
                bVar = MutexKt.g;
            } else {
                Object obj2 = this.b;
                bVar = obj2 == null ? MutexKt.f : new b(obj2);
            }
            MutexImpl.a.compareAndSet(this.a, atomicOp, bVar);
        }
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public boolean holdsLock(@NotNull Object obj) {
        Object obj2 = this._state;
        return obj2 instanceof b ? ((b) obj2).a == obj : (obj2 instanceof d) && ((d) obj2).a == obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "owner", "", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class d extends LockFreeLinkedListHead {
        @JvmField
        @NotNull
        public Object a;

        public d(@NotNull Object obj) {
            this.a = obj;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "LockedQueue[" + this.a + ']';
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b¢\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H&J\u0006\u0010\t\u001a\u00020\u0007J\n\u0010\n\u001a\u0004\u0018\u00010\u0004H&R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/DisposableHandle;", "owner", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "completeResumeLockWaiter", "", "token", "dispose", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public abstract class c extends LockFreeLinkedListNode implements DisposableHandle {
        @JvmField
        @Nullable
        public final Object d;

        @Nullable
        public abstract Object a();

        public abstract void a(@NotNull Object obj);

        public c(Object obj) {
            this.d = obj;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final void dispose() {
            remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u001d\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockCont;", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeLockWaiter", "token", "toString", "", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public final class a extends c {
        @JvmField
        @NotNull
        public final CancellableContinuation<Unit> a;

        /* JADX WARN: Multi-variable type inference failed */
        public a(Object obj, @Nullable CancellableContinuation<? super Unit> cancellableContinuation) {
            super(obj);
            this.a = cancellableContinuation;
        }

        /* compiled from: Mutex.kt */
        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "it", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        /* renamed from: kotlinx.coroutines.sync.MutexImpl$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0376a extends Lambda implements Function1<Throwable, Unit> {
            final /* synthetic */ MutexImpl this$0;
            final /* synthetic */ a this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C0376a(MutexImpl mutexImpl, a aVar) {
                super(1);
                this.this$0 = mutexImpl;
                this.this$1 = aVar;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* synthetic */ Unit invoke(Throwable th) {
                a(th);
                return Unit.INSTANCE;
            }

            public final void a(@NotNull Throwable th) {
                this.this$0.unlock(this.this$1.d);
            }
        }

        @Override // kotlinx.coroutines.sync.MutexImpl.c
        @Nullable
        public Object a() {
            return this.a.tryResume(Unit.INSTANCE, null, new C0376a(MutexImpl.this, this));
        }

        @Override // kotlinx.coroutines.sync.MutexImpl.c
        public void a(@NotNull Object obj) {
            this.a.completeResume(obj);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "LockCont[" + this.d + ", " + this.a + "] for " + MutexImpl.this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00060\u0002R\u00020\u0003BD\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\"\u0010\b\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00050\tø\u0001\u0000¢\u0006\u0002\u0010\fJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0016R1\u0010\b\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00050\t8\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\rR\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockSelect;", "R", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "completeResumeLockWaiter", "", "token", "toString", "", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public final class b<R> extends c {
        @JvmField
        @NotNull
        public final SelectInstance<R> a;
        @JvmField
        @NotNull
        public final Function2<Mutex, Continuation<? super R>, Object> b;

        /* JADX WARN: Multi-variable type inference failed */
        public b(Object obj, @Nullable SelectInstance<? super R> selectInstance, @NotNull Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> function2) {
            super(obj);
            this.a = selectInstance;
            this.b = function2;
        }

        @Override // kotlinx.coroutines.sync.MutexImpl.c
        @Nullable
        public Object a() {
            Symbol symbol;
            if (!this.a.trySelect()) {
                return null;
            }
            symbol = MutexKt.c;
            return symbol;
        }

        /* compiled from: Mutex.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n"}, d2 = {"<anonymous>", "", "R", "it", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes5.dex */
        static final class a extends Lambda implements Function1<Throwable, Unit> {
            final /* synthetic */ MutexImpl this$0;
            final /* synthetic */ b<R> this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(MutexImpl mutexImpl, b<R> bVar) {
                super(1);
                this.this$0 = mutexImpl;
                this.this$1 = bVar;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* synthetic */ Unit invoke(Throwable th) {
                a(th);
                return Unit.INSTANCE;
            }

            public final void a(@NotNull Throwable th) {
                this.this$0.unlock(this.this$1.d);
            }
        }

        @Override // kotlinx.coroutines.sync.MutexImpl.c
        public void a(@NotNull Object obj) {
            Symbol symbol;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                symbol = MutexKt.c;
                if (!(obj == symbol)) {
                    throw new AssertionError();
                }
            }
            CancellableKt.startCoroutineCancellable(this.b, MutexImpl.this, this.a.getCompletion(), new a(MutexImpl.this, this));
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "LockSelect[" + this.d + ", " + this.a + "] for " + MutexImpl.this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Mutex.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u0002H\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/sync/MutexImpl;", "queue", "Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V", "complete", "", "affected", "failure", "", "prepare", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class f extends AtomicOp<MutexImpl> {
        @JvmField
        @NotNull
        public final d a;

        public f(@NotNull d dVar) {
            this.a = dVar;
        }

        @Nullable
        /* renamed from: a */
        public Object prepare(@NotNull MutexImpl mutexImpl) {
            Symbol symbol;
            if (this.a.isEmpty()) {
                return null;
            }
            symbol = MutexKt.b;
            return symbol;
        }

        /* renamed from: a */
        public void complete(@NotNull MutexImpl mutexImpl, @Nullable Object obj) {
            MutexImpl.a.compareAndSet(mutexImpl, this, obj == null ? MutexKt.g : this.a);
        }
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public boolean isLocked() {
        Symbol symbol;
        while (true) {
            Object obj = this._state;
            if (obj instanceof b) {
                Object obj2 = ((b) obj).a;
                symbol = MutexKt.e;
                return obj2 != symbol;
            } else if (obj instanceof d) {
                return true;
            } else {
                if (obj instanceof OpDescriptor) {
                    ((OpDescriptor) obj).perform(this);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", obj).toString());
                }
            }
        }
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public boolean tryLock(@Nullable Object obj) {
        Symbol symbol;
        while (true) {
            Object obj2 = this._state;
            boolean z = true;
            if (obj2 instanceof b) {
                Object obj3 = ((b) obj2).a;
                symbol = MutexKt.e;
                if (obj3 != symbol) {
                    return false;
                }
                if (a.compareAndSet(this, obj2, obj == null ? MutexKt.f : new b(obj))) {
                    return true;
                }
            } else if (obj2 instanceof d) {
                if (((d) obj2).a == obj) {
                    z = false;
                }
                if (z) {
                    return false;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Already locked by ", obj).toString());
            } else if (obj2 instanceof OpDescriptor) {
                ((OpDescriptor) obj2).perform(this);
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", obj2).toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006e A[LOOP_START] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object a(java.lang.Object r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r11)
            kotlinx.coroutines.CancellableContinuationImpl r0 = kotlinx.coroutines.CancellableContinuationKt.getOrCreateCancellableContinuation(r0)
            r1 = r0
            kotlinx.coroutines.CancellableContinuation r1 = (kotlinx.coroutines.CancellableContinuation) r1
            kotlinx.coroutines.sync.MutexImpl$a r2 = new kotlinx.coroutines.sync.MutexImpl$a
            r2.<init>(r10, r1)
        L_0x0010:
            java.lang.Object r3 = r9._state
            boolean r4 = r3 instanceof kotlinx.coroutines.sync.b
            if (r4 == 0) goto L_0x004f
            r4 = r3
            kotlinx.coroutines.sync.b r4 = (kotlinx.coroutines.sync.b) r4
            java.lang.Object r5 = r4.a
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.sync.MutexKt.access$getUNLOCKED$p()
            if (r5 == r6) goto L_0x002e
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = kotlinx.coroutines.sync.MutexImpl.a
            kotlinx.coroutines.sync.MutexImpl$d r6 = new kotlinx.coroutines.sync.MutexImpl$d
            java.lang.Object r4 = r4.a
            r6.<init>(r4)
            r5.compareAndSet(r9, r3, r6)
            goto L_0x0010
        L_0x002e:
            if (r10 != 0) goto L_0x0035
            kotlinx.coroutines.sync.b r4 = kotlinx.coroutines.sync.MutexKt.access$getEMPTY_LOCKED$p()
            goto L_0x003a
        L_0x0035:
            kotlinx.coroutines.sync.b r4 = new kotlinx.coroutines.sync.b
            r4.<init>(r10)
        L_0x003a:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = kotlinx.coroutines.sync.MutexImpl.a
            boolean r3 = r5.compareAndSet(r9, r3, r4)
            if (r3 == 0) goto L_0x0010
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            kotlinx.coroutines.sync.MutexImpl$g r3 = new kotlinx.coroutines.sync.MutexImpl$g
            r3.<init>(r10)
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            r1.resume(r2, r3)
            goto L_0x0080
        L_0x004f:
            boolean r4 = r3 instanceof kotlinx.coroutines.sync.MutexImpl.d
            if (r4 == 0) goto L_0x00a9
            r4 = r3
            kotlinx.coroutines.sync.MutexImpl$d r4 = (kotlinx.coroutines.sync.MutexImpl.d) r4
            java.lang.Object r4 = r4.a
            r5 = 1
            r6 = 0
            if (r4 == r10) goto L_0x005e
            r4 = r5
            goto L_0x005f
        L_0x005e:
            r4 = r6
        L_0x005f:
            if (r4 == 0) goto L_0x0097
            r4 = r3
            kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r4
            kotlinx.coroutines.sync.MutexImpl$lockSuspend$lambda-6$lambda-5$$inlined$addLastIf$1 r7 = new kotlinx.coroutines.sync.MutexImpl$lockSuspend$lambda-6$lambda-5$$inlined$addLastIf$1
            r8 = r2
            kotlinx.coroutines.internal.LockFreeLinkedListNode r8 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r8
            r7.<init>(r9, r3)
            kotlinx.coroutines.internal.LockFreeLinkedListNode$CondAddOp r7 = (kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp) r7
        L_0x006e:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r3 = r4.getPrevNode()
            int r3 = r3.tryCondAddNext(r8, r4, r7)
            switch(r3) {
                case 1: goto L_0x007b;
                case 2: goto L_0x007a;
                default: goto L_0x0079;
            }
        L_0x0079:
            goto L_0x006e
        L_0x007a:
            r5 = r6
        L_0x007b:
            if (r5 == 0) goto L_0x0010
            kotlinx.coroutines.CancellableContinuationKt.removeOnCancellation(r1, r8)
        L_0x0080:
            java.lang.Object r10 = r0.getResult()
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r10 != r0) goto L_0x008d
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r11)
        L_0x008d:
            java.lang.Object r11 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r10 != r11) goto L_0x0094
            return r10
        L_0x0094:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L_0x0097:
            java.lang.String r11 = "Already locked by "
            java.lang.String r10 = kotlin.jvm.internal.Intrinsics.stringPlus(r11, r10)
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r10 = r10.toString()
            r11.<init>(r10)
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            throw r11
        L_0x00a9:
            boolean r4 = r3 instanceof kotlinx.coroutines.internal.OpDescriptor
            if (r4 == 0) goto L_0x00b4
            kotlinx.coroutines.internal.OpDescriptor r3 = (kotlinx.coroutines.internal.OpDescriptor) r3
            r3.perform(r9)
            goto L_0x0010
        L_0x00b4:
            java.lang.String r10 = "Illegal state "
            java.lang.String r10 = kotlin.jvm.internal.Intrinsics.stringPlus(r10, r3)
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r10 = r10.toString()
            r11.<init>(r10)
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.MutexImpl.a(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public void unlock(@Nullable Object obj) {
        b bVar;
        Symbol symbol;
        while (true) {
            Object obj2 = this._state;
            boolean z = true;
            if (obj2 instanceof b) {
                if (obj == null) {
                    Object obj3 = ((b) obj2).a;
                    symbol = MutexKt.e;
                    if (obj3 == symbol) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException("Mutex is not locked".toString());
                    }
                } else {
                    b bVar2 = (b) obj2;
                    if (bVar2.a != obj) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + bVar2.a + " but expected " + obj).toString());
                    }
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
                bVar = MutexKt.g;
                if (atomicReferenceFieldUpdater.compareAndSet(this, obj2, bVar)) {
                    return;
                }
            } else if (obj2 instanceof OpDescriptor) {
                ((OpDescriptor) obj2).perform(this);
            } else if (obj2 instanceof d) {
                if (obj != null) {
                    d dVar = (d) obj2;
                    if (dVar.a != obj) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + dVar.a + " but expected " + obj).toString());
                    }
                }
                d dVar2 = (d) obj2;
                LockFreeLinkedListNode removeFirstOrNull = dVar2.removeFirstOrNull();
                if (removeFirstOrNull == null) {
                    f fVar = new f(dVar2);
                    if (a.compareAndSet(this, obj2, fVar) && fVar.perform(this) == null) {
                        return;
                    }
                } else {
                    c cVar = (c) removeFirstOrNull;
                    Object a2 = cVar.a();
                    if (a2 != null) {
                        Object obj4 = cVar.d;
                        if (obj4 == null) {
                            obj4 = MutexKt.d;
                        }
                        dVar2.a = obj4;
                        cVar.a(a2);
                        return;
                    }
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", obj2).toString());
            }
        }
    }

    @NotNull
    public String toString() {
        while (true) {
            Object obj = this._state;
            if (obj instanceof b) {
                return "Mutex[" + ((b) obj).a + ']';
            } else if (obj instanceof OpDescriptor) {
                ((OpDescriptor) obj).perform(this);
            } else if (obj instanceof d) {
                return "Mutex[" + ((d) obj).a + ']';
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", obj).toString());
            }
        }
    }
}
