package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.skills.update.VersionUtil;
import com.xiaomi.onetrack.api.b;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.InternalCoroutinesApi;
import org.hapjs.features.channel.IChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LockFreeLinkedList.kt */
@InternalCoroutinesApi
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\f\b\u0017\u0018\u00002\u00020C:\u0005JKLMNB\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u0019\u0010\u0006\u001a\u00020\u00052\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u0003¢\u0006\u0004\b\u0006\u0010\u0007J,\u0010\u000b\u001a\u00020\t2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u00032\u000e\b\u0004\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086\b¢\u0006\u0004\b\u000b\u0010\fJ4\u0010\u000f\u001a\u00020\t2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u00032\u0016\u0010\u000e\u001a\u0012\u0012\b\u0012\u00060\u0000j\u0002`\u0003\u0012\u0004\u0012\u00020\t0\rH\u0086\b¢\u0006\u0004\b\u000f\u0010\u0010JD\u0010\u0011\u001a\u00020\t2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u00032\u0016\u0010\u000e\u001a\u0012\u0012\b\u0012\u00060\u0000j\u0002`\u0003\u0012\u0004\u0012\u00020\t0\r2\u000e\b\u0004\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086\b¢\u0006\u0004\b\u0011\u0010\u0012J'\u0010\u0014\u001a\u00020\t2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u00032\n\u0010\u0013\u001a\u00060\u0000j\u0002`\u0003H\u0001¢\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\t2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u0003¢\u0006\u0004\b\u0016\u0010\u0017J\"\u0010\u001a\u001a\n\u0018\u00010\u0000j\u0004\u0018\u0001`\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u0082\u0010¢\u0006\u0004\b\u001a\u0010\u001bJ)\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001d\"\f\b\u0000\u0010\u001c*\u00060\u0000j\u0002`\u00032\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010!\u001a\f\u0012\b\u0012\u00060\u0000j\u0002`\u00030 ¢\u0006\u0004\b!\u0010\"J \u0010$\u001a\u00060\u0000j\u0002`\u00032\n\u0010#\u001a\u00060\u0000j\u0002`\u0003H\u0082\u0010¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00052\n\u0010\u0013\u001a\u00060\u0000j\u0002`\u0003H\u0002¢\u0006\u0004\b&\u0010\u0007J\r\u0010'\u001a\u00020\u0005¢\u0006\u0004\b'\u0010\u0002J\u000f\u0010(\u001a\u00020\u0005H\u0001¢\u0006\u0004\b(\u0010\u0002J,\u0010*\u001a\u00020)2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u00032\u000e\b\u0004\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0081\b¢\u0006\u0004\b*\u0010+J\u0017\u0010,\u001a\n\u0018\u00010\u0000j\u0004\u0018\u0001`\u0003H\u0014¢\u0006\u0004\b,\u0010-J\u000f\u0010.\u001a\u00020\tH\u0016¢\u0006\u0004\b.\u0010/J.\u00100\u001a\u0004\u0018\u00018\u0000\"\u0006\b\u0000\u0010\u001c\u0018\u00012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\rH\u0086\b¢\u0006\u0004\b0\u00101J\u0015\u00102\u001a\n\u0018\u00010\u0000j\u0004\u0018\u0001`\u0003¢\u0006\u0004\b2\u0010-J\u0017\u00103\u001a\n\u0018\u00010\u0000j\u0004\u0018\u0001`\u0003H\u0001¢\u0006\u0004\b3\u0010-J\u000f\u00105\u001a\u000204H\u0002¢\u0006\u0004\b5\u00106J\u000f\u00108\u001a\u000207H\u0016¢\u0006\u0004\b8\u00109J/\u0010<\u001a\u00020;2\n\u0010\u0004\u001a\u00060\u0000j\u0002`\u00032\n\u0010\u0013\u001a\u00060\u0000j\u0002`\u00032\u0006\u0010:\u001a\u00020)H\u0001¢\u0006\u0004\b<\u0010=J'\u0010A\u001a\u00020\u00052\n\u0010>\u001a\u00060\u0000j\u0002`\u00032\n\u0010\u0013\u001a\u00060\u0000j\u0002`\u0003H\u0000¢\u0006\u0004\b?\u0010@R\u0016\u0010B\u001a\u00020\t8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010/R\u0013\u0010\u0013\u001a\u00020C8F@\u0006¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0017\u0010G\u001a\u00060\u0000j\u0002`\u00038F@\u0006¢\u0006\u0006\u001a\u0004\bF\u0010-R\u0017\u0010I\u001a\u00060\u0000j\u0002`\u00038F@\u0006¢\u0006\u0006\u001a\u0004\bH\u0010-¨\u0006O"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "<init>", "()V", "Lkotlinx/coroutines/internal/Node;", "node", "", "addLast", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "Lkotlin/Function0;", "", "condition", "addLastIf", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function0;)Z", "Lkotlin/Function1;", "predicate", "addLastIfPrev", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function1;)Z", "addLastIfPrevAndIf", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Z", "next", "addNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "addOneIfEmpty", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "correctPrev", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "describeAddLast", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "describeRemoveFirst", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", VersionUtil.VERSION_CURRENT, "findPrevNonRemoved", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "finishAdd", "helpRemove", "helpRemovePrev", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "makeCondAddOp", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function0;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "nextIfRemoved", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "remove", "()Z", "removeFirstIfIsInstanceOfOrPeekIf", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "removeFirstOrNull", "removeOrNext", "Lkotlinx/coroutines/internal/Removed;", "removed", "()Lkotlinx/coroutines/internal/Removed;", "", "toString", "()Ljava/lang/String;", "condAdd", "", "tryCondAddNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;)I", Remote.Response.ControlAction.ACTION_PREV, "validateNode$kotlinx_coroutines_core", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "validateNode", "isRemoved", "", "getNext", "()Ljava/lang/Object;", "getNextNode", "nextNode", "getPrevNode", "prevNode", "AbstractAtomicDesc", "AddLastDesc", "CondAddOp", "PrepareOp", "RemoveFirstDesc", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public class LockFreeLinkedListNode {
    @NotNull
    volatile /* synthetic */ Object _next = this;
    @NotNull
    volatile /* synthetic */ Object _prev = this;
    @NotNull
    private volatile /* synthetic */ Object _removedRef = null;
    static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_next");
    static final /* synthetic */ AtomicReferenceFieldUpdater f = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_prev");
    private static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_removedRef");

    public final b a() {
        b bVar = (b) this._removedRef;
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = new b(this);
        a.lazySet(this, bVar2);
        return bVar2;
    }

    /* compiled from: LockFreeLinkedList.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\b!\u0018\u00002\f\u0012\b\u0012\u00060\u0002j\u0002`\u00030\u0001B\u0011\u0012\n\u0010\u0004\u001a\u00060\u0002j\u0002`\u0003¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\u0002j\u0002`\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016R\u0014\u0010\u0004\u001a\u00060\u0002j\u0002`\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "newNode", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "oldNext", "complete", "", "affected", "failure", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    @PublishedApi
    /* loaded from: classes5.dex */
    public static abstract class CondAddOp extends AtomicOp<LockFreeLinkedListNode> {
        @JvmField
        @NotNull
        public final LockFreeLinkedListNode newNode;
        @JvmField
        @Nullable
        public LockFreeLinkedListNode oldNext;

        public CondAddOp(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            this.newNode = lockFreeLinkedListNode;
        }

        public void complete(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @Nullable Object obj) {
            boolean z = obj == null;
            LockFreeLinkedListNode lockFreeLinkedListNode2 = z ? this.newNode : this.oldNext;
            if (lockFreeLinkedListNode2 != null && LockFreeLinkedListNode.e.compareAndSet(lockFreeLinkedListNode, this, lockFreeLinkedListNode2) && z) {
                LockFreeLinkedListNode lockFreeLinkedListNode3 = this.newNode;
                LockFreeLinkedListNode lockFreeLinkedListNode4 = this.oldNext;
                Intrinsics.checkNotNull(lockFreeLinkedListNode4);
                lockFreeLinkedListNode3.b(lockFreeLinkedListNode4);
            }
        }
    }

    @PublishedApi
    @NotNull
    public final CondAddOp makeCondAddOp(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Function0<Boolean> function0) {
        return new LockFreeLinkedListNode$makeCondAddOp$1(function0, lockFreeLinkedListNode);
    }

    public boolean isRemoved() {
        return getNext() instanceof b;
    }

    @NotNull
    public final LockFreeLinkedListNode getNextNode() {
        return LockFreeLinkedListKt.unwrap(getNext());
    }

    @NotNull
    public final LockFreeLinkedListNode getPrevNode() {
        LockFreeLinkedListNode a2 = a((OpDescriptor) null);
        return a2 == null ? a((LockFreeLinkedListNode) this._prev) : a2;
    }

    private final LockFreeLinkedListNode a(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListNode._prev;
        }
        return lockFreeLinkedListNode;
    }

    public final boolean addOneIfEmpty(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
        f.lazySet(lockFreeLinkedListNode, this);
        e.lazySet(lockFreeLinkedListNode, this);
        while (getNext() == this) {
            if (e.compareAndSet(this, this, lockFreeLinkedListNode)) {
                lockFreeLinkedListNode.b(this);
                return true;
            }
        }
        return false;
    }

    public final void addLast(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
        do {
        } while (!getPrevNode().addNext(lockFreeLinkedListNode, this));
    }

    @NotNull
    public final <T extends LockFreeLinkedListNode> AddLastDesc<T> describeAddLast(@NotNull T t) {
        return new AddLastDesc<>(this, t);
    }

    public final boolean addLastIfPrev(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Function1<? super LockFreeLinkedListNode, Boolean> function1) {
        LockFreeLinkedListNode prevNode;
        do {
            prevNode = getPrevNode();
            if (!function1.invoke(prevNode).booleanValue()) {
                return false;
            }
        } while (!prevNode.addNext(lockFreeLinkedListNode, this));
        return true;
    }

    @PublishedApi
    public final boolean addNext(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2) {
        f.lazySet(lockFreeLinkedListNode, this);
        e.lazySet(lockFreeLinkedListNode, lockFreeLinkedListNode2);
        if (!e.compareAndSet(this, lockFreeLinkedListNode2, lockFreeLinkedListNode)) {
            return false;
        }
        lockFreeLinkedListNode.b(lockFreeLinkedListNode2);
        return true;
    }

    @PublishedApi
    public final int tryCondAddNext(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2, @NotNull CondAddOp condAddOp) {
        f.lazySet(lockFreeLinkedListNode, this);
        e.lazySet(lockFreeLinkedListNode, lockFreeLinkedListNode2);
        condAddOp.oldNext = lockFreeLinkedListNode2;
        if (!e.compareAndSet(this, lockFreeLinkedListNode2, condAddOp)) {
            return 0;
        }
        return condAddOp.perform(this) == null ? 1 : 2;
    }

    public boolean remove() {
        return removeOrNext() == null;
    }

    @PublishedApi
    @Nullable
    public final LockFreeLinkedListNode removeOrNext() {
        Object next;
        LockFreeLinkedListNode lockFreeLinkedListNode;
        do {
            next = getNext();
            if (next instanceof b) {
                return ((b) next).a;
            }
            if (next == this) {
                return (LockFreeLinkedListNode) next;
            }
            lockFreeLinkedListNode = (LockFreeLinkedListNode) next;
        } while (!e.compareAndSet(this, next, lockFreeLinkedListNode.a()));
        lockFreeLinkedListNode.a((OpDescriptor) null);
        return null;
    }

    public final void helpRemove() {
        ((b) getNext()).a.a((OpDescriptor) null);
    }

    @PublishedApi
    public final void helpRemovePrev() {
        LockFreeLinkedListNode lockFreeLinkedListNode = this;
        while (true) {
            Object next = lockFreeLinkedListNode.getNext();
            if (!(next instanceof b)) {
                lockFreeLinkedListNode.a((OpDescriptor) null);
                return;
            }
            lockFreeLinkedListNode = ((b) next).a;
        }
    }

    @Nullable
    public final LockFreeLinkedListNode removeFirstOrNull() {
        while (true) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) getNext();
            if (lockFreeLinkedListNode == this) {
                return null;
            }
            if (lockFreeLinkedListNode.remove()) {
                return lockFreeLinkedListNode;
            }
            lockFreeLinkedListNode.helpRemove();
        }
    }

    @NotNull
    public final RemoveFirstDesc<LockFreeLinkedListNode> describeRemoveFirst() {
        return new RemoveFirstDesc<>(this);
    }

    public final /* synthetic */ Object removeFirstIfIsInstanceOfOrPeekIf(Function1 function1) {
        LockFreeLinkedListNode removeOrNext;
        while (true) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) getNext();
            if (lockFreeLinkedListNode == this) {
                return null;
            }
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (!(lockFreeLinkedListNode instanceof Object)) {
                return null;
            }
            if ((((Boolean) function1.invoke(lockFreeLinkedListNode)).booleanValue() && !lockFreeLinkedListNode.isRemoved()) || (removeOrNext = lockFreeLinkedListNode.removeOrNext()) == null) {
                return lockFreeLinkedListNode;
            }
            removeOrNext.helpRemovePrev();
        }
    }

    /* compiled from: LockFreeLinkedList.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\b\u0016\u0018\u0000*\f\b\u0000\u0010\u0003*\u00060\u0001j\u0002`\u00022\u00020!B\u001b\u0012\n\u0010\u0004\u001a\u00060\u0001j\u0002`\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0004\b\u0006\u0010\u0007J'\u0010\u000b\u001a\u00020\n2\n\u0010\b\u001a\u00060\u0001j\u0002`\u00022\n\u0010\t\u001a\u00060\u0001j\u0002`\u0002H\u0014¢\u0006\u0004\b\u000b\u0010\u0007J\u0017\u0010\u000e\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\fH\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0012\u001a\u00020\u00112\n\u0010\b\u001a\u00060\u0001j\u0002`\u00022\u0006\u0010\t\u001a\u00020\u0010H\u0014¢\u0006\u0004\b\u0012\u0010\u0013J\u001f\u0010\u0016\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u00022\u0006\u0010\u0015\u001a\u00020\u0014H\u0004¢\u0006\u0004\b\u0016\u0010\u0017J'\u0010\u0018\u001a\u00020\u00102\n\u0010\b\u001a\u00060\u0001j\u0002`\u00022\n\u0010\t\u001a\u00060\u0001j\u0002`\u0002H\u0016¢\u0006\u0004\b\u0018\u0010\u0019R\u001e\u0010\u001c\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u00028D@\u0004X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0005\u001a\u00028\u00008\u0006@\u0007X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u001dR\u001a\u0010\u001f\u001a\u00060\u0001j\u0002`\u00028D@\u0004X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001bR\u001a\u0010\u0004\u001a\u00060\u0001j\u0002`\u00028\u0006@\u0007X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010\u001d¨\u0006 "}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", ExifInterface.GPS_DIRECTION_TRUE, "queue", "node", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "affected", "next", "", "finishOnSuccess", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "prepareOp", "finishPrepare", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "", "", b.M, "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "takeAffectedNode", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "updatedNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "affectedNode", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "getOriginalNext", "originalNext", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static class AddLastDesc<T extends LockFreeLinkedListNode> extends AbstractAtomicDesc {
        private static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(AddLastDesc.class, Object.class, "_affectedNode");
        @NotNull
        private volatile /* synthetic */ Object _affectedNode;
        @JvmField
        @NotNull
        public final T node;
        @JvmField
        @NotNull
        public final LockFreeLinkedListNode queue;

        public AddLastDesc(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull T t) {
            this.queue = lockFreeLinkedListNode;
            this.node = t;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                Object obj = this.node._next;
                T t2 = this.node;
                if (!(obj == t2 && t2._prev == this.node)) {
                    throw new AssertionError();
                }
            }
            this._affectedNode = null;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected final LockFreeLinkedListNode takeAffectedNode(@NotNull OpDescriptor opDescriptor) {
            return this.queue.a(opDescriptor);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected final LockFreeLinkedListNode getAffectedNode() {
            return (LockFreeLinkedListNode) this._affectedNode;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @NotNull
        protected final LockFreeLinkedListNode getOriginalNext() {
            return this.queue;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        protected boolean retry(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Object obj) {
            return obj != this.queue;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public void finishPrepare(@NotNull PrepareOp prepareOp) {
            a.compareAndSet(this, null, prepareOp.affected);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @NotNull
        public Object updatedNext(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2) {
            LockFreeLinkedListNode.f.compareAndSet(this.node, this.node, lockFreeLinkedListNode);
            LockFreeLinkedListNode.e.compareAndSet(this.node, this.node, this.queue);
            return this.node;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        protected void finishOnSuccess(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2) {
            this.node.b(this.queue);
        }
    }

    /* compiled from: LockFreeLinkedList.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020(B\u0013\u0012\n\u0010\u0004\u001a\u00060\u0002j\u0002`\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\u0004\u0018\u00010\b2\n\u0010\u0007\u001a\u00060\u0002j\u0002`\u0003H\u0014¢\u0006\u0004\b\t\u0010\nJ'\u0010\r\u001a\u00020\f2\n\u0010\u0007\u001a\u00060\u0002j\u0002`\u00032\n\u0010\u000b\u001a\u00060\u0002j\u0002`\u0003H\u0004¢\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016¢\u0006\u0004\b\u0011\u0010\u0012J#\u0010\u0014\u001a\u00020\u00132\n\u0010\u0007\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u000b\u001a\u00020\bH\u0004¢\u0006\u0004\b\u0014\u0010\u0015J\u001f\u0010\u0018\u001a\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00032\u0006\u0010\u0017\u001a\u00020\u0016H\u0004¢\u0006\u0004\b\u0018\u0010\u0019J%\u0010\u001a\u001a\u00020\b2\n\u0010\u0007\u001a\u00060\u0002j\u0002`\u00032\n\u0010\u000b\u001a\u00060\u0002j\u0002`\u0003¢\u0006\u0004\b\u001a\u0010\u001bR\u001e\u0010\u001e\u001a\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00038D@\u0004X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001e\u0010 \u001a\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00038D@\u0004X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001dR\u001a\u0010\u0004\u001a\u00060\u0002j\u0002`\u00038\u0006@\u0007X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010!R\u0019\u0010&\u001a\u00028\u00008F@\u0006¢\u0006\f\u0012\u0004\b$\u0010%\u001a\u0004\b\"\u0010#¨\u0006'"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "queue", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "affected", "", "failure", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "next", "", "finishOnSuccess", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "prepareOp", "finishPrepare", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "", b.M, "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "takeAffectedNode", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "updatedNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "affectedNode", "getOriginalNext", "originalNext", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "getResult", "()Ljava/lang/Object;", "getResult$annotations", "()V", "result", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static class RemoveFirstDesc<T> extends AbstractAtomicDesc {
        private static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(RemoveFirstDesc.class, Object.class, "_affectedNode");
        private static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(RemoveFirstDesc.class, Object.class, "_originalNext");
        @NotNull
        private volatile /* synthetic */ Object _affectedNode = null;
        @NotNull
        private volatile /* synthetic */ Object _originalNext = null;
        @JvmField
        @NotNull
        public final LockFreeLinkedListNode queue;

        public static /* synthetic */ void getResult$annotations() {
        }

        public RemoveFirstDesc(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            this.queue = lockFreeLinkedListNode;
        }

        public final T getResult() {
            T t = (T) getAffectedNode();
            Intrinsics.checkNotNull(t);
            return t;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected final LockFreeLinkedListNode takeAffectedNode(@NotNull OpDescriptor opDescriptor) {
            LockFreeLinkedListNode lockFreeLinkedListNode = this.queue;
            while (true) {
                Object obj = lockFreeLinkedListNode._next;
                if (!(obj instanceof OpDescriptor)) {
                    return (LockFreeLinkedListNode) obj;
                }
                OpDescriptor opDescriptor2 = (OpDescriptor) obj;
                if (opDescriptor.isEarlierThan(opDescriptor2)) {
                    return null;
                }
                opDescriptor2.perform(this.queue);
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected final LockFreeLinkedListNode getAffectedNode() {
            return (LockFreeLinkedListNode) this._affectedNode;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected final LockFreeLinkedListNode getOriginalNext() {
            return (LockFreeLinkedListNode) this._originalNext;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            if (lockFreeLinkedListNode == this.queue) {
                return LockFreeLinkedListKt.getLIST_EMPTY();
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        protected final boolean retry(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            ((b) obj).a.helpRemovePrev();
            return true;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        public void finishPrepare(@NotNull PrepareOp prepareOp) {
            a.compareAndSet(this, null, prepareOp.affected);
            b.compareAndSet(this, null, prepareOp.next);
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @NotNull
        public final Object updatedNext(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2) {
            return lockFreeLinkedListNode2.a();
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        protected final void finishOnSuccess(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2) {
            lockFreeLinkedListNode2.a((OpDescriptor) null);
        }
    }

    /* compiled from: LockFreeLinkedList.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B%\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\r\u001a\u00020\u000eJ\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0002\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0002\u001a\u00060\u0003j\u0002`\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00060\u0003j\u0002`\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "next", IChannel.EXTRA_ERROR_DESC, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "finishPrepare", "", "perform", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class PrepareOp extends OpDescriptor {
        @JvmField
        @NotNull
        public final LockFreeLinkedListNode affected;
        @JvmField
        @NotNull
        public final AbstractAtomicDesc desc;
        @JvmField
        @NotNull
        public final LockFreeLinkedListNode next;

        public PrepareOp(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2, @NotNull AbstractAtomicDesc abstractAtomicDesc) {
            this.affected = lockFreeLinkedListNode;
            this.next = lockFreeLinkedListNode2;
            this.desc = abstractAtomicDesc;
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @NotNull
        public AtomicOp<?> getAtomicOp() {
            return this.desc.getAtomicOp();
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @Nullable
        public Object perform(@Nullable Object obj) {
            Object obj2;
            Object obj3;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(obj == this.affected)) {
                    throw new AssertionError();
                }
            }
            if (obj != null) {
                LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
                Object onPrepare = this.desc.onPrepare(this);
                if (onPrepare == LockFreeLinkedList_commonKt.REMOVE_PREPARED) {
                    LockFreeLinkedListNode lockFreeLinkedListNode2 = this.next;
                    if (LockFreeLinkedListNode.e.compareAndSet(lockFreeLinkedListNode, this, lockFreeLinkedListNode2.a())) {
                        this.desc.onRemoved(lockFreeLinkedListNode);
                        lockFreeLinkedListNode2.a((OpDescriptor) null);
                    }
                    return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
                }
                if (onPrepare != null) {
                    obj2 = getAtomicOp().decide(onPrepare);
                } else {
                    obj2 = getAtomicOp().getConsensus();
                }
                if (obj2 == AtomicKt.NO_DECISION) {
                    obj3 = getAtomicOp();
                } else if (obj2 == null) {
                    obj3 = this.desc.updatedNext(lockFreeLinkedListNode, this.next);
                } else {
                    obj3 = this.next;
                }
                LockFreeLinkedListNode.e.compareAndSet(lockFreeLinkedListNode, this, obj3);
                return null;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
        }

        public final void finishPrepare() {
            this.desc.finishPrepare(this);
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @NotNull
        public String toString() {
            return "PrepareOp(op=" + getAtomicOp() + ')';
        }
    }

    /* compiled from: LockFreeLinkedList.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\u0010\u0010\u001a\u00060\u0004j\u0002`\u0005H\u0014J \u0010\u0011\u001a\u00020\u000b2\n\u0010\u0010\u001a\u00060\u0004j\u0002`\u00052\n\u0010\u0012\u001a\u00060\u0004j\u0002`\u0005H$J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H&J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0014\u0010\u0017\u001a\u00020\u000b2\n\u0010\u0010\u001a\u00060\u0004j\u0002`\u0005H\u0016J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u000f2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rJ\u001c\u0010\u0019\u001a\u00020\u001a2\n\u0010\u0010\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0012\u001a\u00020\u000fH\u0014J\u0018\u0010\u001b\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u00052\u0006\u0010\f\u001a\u00020\u001cH\u0014J \u0010\u001d\u001a\u00020\u000f2\n\u0010\u0010\u001a\u00060\u0004j\u0002`\u00052\n\u0010\u0012\u001a\u00060\u0004j\u0002`\u0005H&R\u001a\u0010\u0003\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u0005X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u0005X¤\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "()V", "affectedNode", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "originalNext", "getOriginalNext", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "", "affected", "finishOnSuccess", "next", "finishPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "onPrepare", "onRemoved", "prepare", b.M, "", "takeAffectedNode", "Lkotlinx/coroutines/internal/OpDescriptor;", "updatedNext", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static abstract class AbstractAtomicDesc extends AtomicDesc {
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
            return null;
        }

        protected abstract void finishOnSuccess(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2);

        public abstract void finishPrepare(@NotNull PrepareOp prepareOp);

        @Nullable
        protected abstract LockFreeLinkedListNode getAffectedNode();

        @Nullable
        protected abstract LockFreeLinkedListNode getOriginalNext();

        public void onRemoved(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
        }

        protected boolean retry(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Object obj) {
            return false;
        }

        @NotNull
        public abstract Object updatedNext(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2);

        @Nullable
        protected LockFreeLinkedListNode takeAffectedNode(@NotNull OpDescriptor opDescriptor) {
            LockFreeLinkedListNode affectedNode = getAffectedNode();
            Intrinsics.checkNotNull(affectedNode);
            return affectedNode;
        }

        @Nullable
        public Object onPrepare(@NotNull PrepareOp prepareOp) {
            finishPrepare(prepareOp);
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0057, code lost:
            if (kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED() == false) goto L_0x0067;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0059, code lost:
            if (r4 != null) goto L_0x005d;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x005b, code lost:
            r7 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x005d, code lost:
            r7 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x005e, code lost:
            if (r7 == false) goto L_0x0061;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0066, code lost:
            throw new java.lang.AssertionError();
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0067, code lost:
            return null;
         */
        @Override // kotlinx.coroutines.internal.AtomicDesc
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object prepare(@org.jetbrains.annotations.NotNull kotlinx.coroutines.internal.AtomicOp<?> r7) {
            /*
                r6 = this;
            L_0x0000:
                r0 = r7
                kotlinx.coroutines.internal.OpDescriptor r0 = (kotlinx.coroutines.internal.OpDescriptor) r0
                kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = r6.takeAffectedNode(r0)
                if (r0 != 0) goto L_0x000c
                java.lang.Object r7 = kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC
                return r7
            L_0x000c:
                java.lang.Object r1 = r0._next
                r2 = 0
                if (r1 != r7) goto L_0x0012
                return r2
            L_0x0012:
                boolean r3 = r7.isDecided()
                if (r3 == 0) goto L_0x0019
                return r2
            L_0x0019:
                boolean r3 = r1 instanceof kotlinx.coroutines.internal.OpDescriptor
                if (r3 == 0) goto L_0x002c
                kotlinx.coroutines.internal.OpDescriptor r1 = (kotlinx.coroutines.internal.OpDescriptor) r1
                boolean r2 = r7.isEarlierThan(r1)
                if (r2 == 0) goto L_0x0028
                java.lang.Object r7 = kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC
                return r7
            L_0x0028:
                r1.perform(r0)
                goto L_0x0000
            L_0x002c:
                java.lang.Object r3 = r6.failure(r0)
                if (r3 == 0) goto L_0x0033
                return r3
            L_0x0033:
                boolean r3 = r6.retry(r0, r1)
                if (r3 == 0) goto L_0x003a
                goto L_0x0000
            L_0x003a:
                kotlinx.coroutines.internal.LockFreeLinkedListNode$PrepareOp r3 = new kotlinx.coroutines.internal.LockFreeLinkedListNode$PrepareOp
                r4 = r1
                kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r4
                r3.<init>(r0, r4, r6)
                java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = kotlinx.coroutines.internal.LockFreeLinkedListNode.e
                boolean r4 = r4.compareAndSet(r0, r1, r3)
                if (r4 == 0) goto L_0x0000
                java.lang.Object r4 = r3.perform(r0)     // Catch: Throwable -> 0x0068
                java.lang.Object r5 = kotlinx.coroutines.internal.LockFreeLinkedList_commonKt.REMOVE_PREPARED     // Catch: Throwable -> 0x0068
                if (r4 != r5) goto L_0x0053
                goto L_0x0000
            L_0x0053:
                boolean r7 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()     // Catch: Throwable -> 0x0068
                if (r7 == 0) goto L_0x0067
                if (r4 != 0) goto L_0x005d
                r7 = 1
                goto L_0x005e
            L_0x005d:
                r7 = 0
            L_0x005e:
                if (r7 == 0) goto L_0x0061
                goto L_0x0067
            L_0x0061:
                java.lang.AssertionError r7 = new java.lang.AssertionError     // Catch: Throwable -> 0x0068
                r7.<init>()     // Catch: Throwable -> 0x0068
                throw r7     // Catch: Throwable -> 0x0068
            L_0x0067:
                return r2
            L_0x0068:
                r7 = move-exception
                java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = kotlinx.coroutines.internal.LockFreeLinkedListNode.e
                r2.compareAndSet(r0, r3, r1)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc.prepare(kotlinx.coroutines.internal.AtomicOp):java.lang.Object");
        }

        @Override // kotlinx.coroutines.internal.AtomicDesc
        public final void complete(@NotNull AtomicOp<?> atomicOp, @Nullable Object obj) {
            boolean z = obj == null;
            LockFreeLinkedListNode affectedNode = getAffectedNode();
            if (affectedNode != null) {
                LockFreeLinkedListNode originalNext = getOriginalNext();
                if (originalNext != null) {
                    if (LockFreeLinkedListNode.e.compareAndSet(affectedNode, atomicOp, z ? updatedNext(affectedNode, originalNext) : originalNext) && z) {
                        finishOnSuccess(affectedNode, originalNext);
                    }
                } else if (DebugKt.getASSERTIONS_ENABLED() && !(!z)) {
                    throw new AssertionError();
                }
            } else if (DebugKt.getASSERTIONS_ENABLED() && !(!z)) {
                throw new AssertionError();
            }
        }
    }

    @Nullable
    protected LockFreeLinkedListNode nextIfRemoved() {
        Object next = getNext();
        b bVar = next instanceof b ? (b) next : null;
        if (bVar == null) {
            return null;
        }
        return bVar.a;
    }

    public final LockFreeLinkedListNode a(OpDescriptor opDescriptor) {
        while (true) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) this._prev;
            LockFreeLinkedListNode lockFreeLinkedListNode2 = lockFreeLinkedListNode;
            LockFreeLinkedListNode lockFreeLinkedListNode3 = null;
            while (true) {
                Object obj = lockFreeLinkedListNode2._next;
                if (obj == this) {
                    if (lockFreeLinkedListNode == lockFreeLinkedListNode2 || f.compareAndSet(this, lockFreeLinkedListNode, lockFreeLinkedListNode2)) {
                        return lockFreeLinkedListNode2;
                    }
                } else if (isRemoved()) {
                    return null;
                } else {
                    if (obj == opDescriptor) {
                        return lockFreeLinkedListNode2;
                    }
                    if (obj instanceof OpDescriptor) {
                        if (opDescriptor != null && opDescriptor.isEarlierThan((OpDescriptor) obj)) {
                            return null;
                        }
                        ((OpDescriptor) obj).perform(lockFreeLinkedListNode2);
                    } else if (!(obj instanceof b)) {
                        lockFreeLinkedListNode3 = lockFreeLinkedListNode2;
                        lockFreeLinkedListNode2 = (LockFreeLinkedListNode) obj;
                    } else if (lockFreeLinkedListNode3 == null) {
                        lockFreeLinkedListNode2 = (LockFreeLinkedListNode) lockFreeLinkedListNode2._prev;
                    } else if (!e.compareAndSet(lockFreeLinkedListNode3, lockFreeLinkedListNode2, ((b) obj).a)) {
                        break;
                    } else {
                        lockFreeLinkedListNode2 = lockFreeLinkedListNode3;
                        lockFreeLinkedListNode3 = null;
                    }
                }
            }
        }
    }

    public final void validateNode$kotlinx_coroutines_core(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull LockFreeLinkedListNode lockFreeLinkedListNode2) {
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(lockFreeLinkedListNode == this._prev)) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (lockFreeLinkedListNode2 != this._next) {
                z = false;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((Object) getClass().getSimpleName());
        sb.append('@');
        sb.append((Object) Integer.toHexString(System.identityHashCode(this)));
        return sb.toString();
    }

    @NotNull
    public final Object getNext() {
        while (true) {
            Object obj = this._next;
            if (!(obj instanceof OpDescriptor)) {
                return obj;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    public final boolean addLastIf(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Function0<Boolean> function0) {
        while (true) {
            switch (getPrevNode().tryCondAddNext(lockFreeLinkedListNode, this, new LockFreeLinkedListNode$makeCondAddOp$1(function0, lockFreeLinkedListNode))) {
                case 1:
                    return true;
                case 2:
                    return false;
            }
        }
    }

    public final boolean addLastIfPrevAndIf(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode, @NotNull Function1<? super LockFreeLinkedListNode, Boolean> function1, @NotNull Function0<Boolean> function0) {
        LockFreeLinkedListNode$makeCondAddOp$1 lockFreeLinkedListNode$makeCondAddOp$1 = new LockFreeLinkedListNode$makeCondAddOp$1(function0, lockFreeLinkedListNode);
        while (true) {
            LockFreeLinkedListNode prevNode = getPrevNode();
            if (!function1.invoke(prevNode).booleanValue()) {
                return false;
            }
            switch (prevNode.tryCondAddNext(lockFreeLinkedListNode, this, lockFreeLinkedListNode$makeCondAddOp$1)) {
                case 1:
                    return true;
                case 2:
                    return false;
            }
        }
    }

    public final void b(LockFreeLinkedListNode lockFreeLinkedListNode) {
        LockFreeLinkedListNode lockFreeLinkedListNode2;
        do {
            lockFreeLinkedListNode2 = (LockFreeLinkedListNode) lockFreeLinkedListNode._prev;
            if (getNext() != lockFreeLinkedListNode) {
                return;
            }
        } while (!f.compareAndSet(lockFreeLinkedListNode, lockFreeLinkedListNode2, this));
        if (isRemoved()) {
            lockFreeLinkedListNode.a((OpDescriptor) null);
        }
    }
}
