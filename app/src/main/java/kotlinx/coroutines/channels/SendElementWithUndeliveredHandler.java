package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbstractChannel.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B9\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u001c\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\bj\b\u0012\u0004\u0012\u00028\u0000`\t¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016R&\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\bj\b\u0012\u0004\u0012\u00028\u0000`\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/channels/SendElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/SendElement;", "pollResult", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "onUndeliveredElement", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;Lkotlin/jvm/functions/Function1;)V", "remove", "", "undeliveredElement", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class SendElementWithUndeliveredHandler<E> extends SendElement<E> {
    @JvmField
    @NotNull
    public final Function1<E, Unit> onUndeliveredElement;

    /* JADX WARN: Multi-variable type inference failed */
    public SendElementWithUndeliveredHandler(E e, @NotNull CancellableContinuation<? super Unit> cancellableContinuation, @NotNull Function1<? super E, Unit> function1) {
        super(e, cancellableContinuation);
        this.onUndeliveredElement = function1;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public boolean remove() {
        if (!super.remove()) {
            return false;
        }
        undeliveredElement();
        return true;
    }

    @Override // kotlinx.coroutines.channels.Send
    public void undeliveredElement() {
        OnUndeliveredElementKt.callUndeliveredElement(this.onUndeliveredElement, getPollResult(), this.cont.getContext());
    }
}
