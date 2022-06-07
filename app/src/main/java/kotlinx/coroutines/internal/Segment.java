package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.api.model.Remote;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlinx.coroutines.internal.Segment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConcurrentLinkedList.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\b \u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0012\u0004\u0012\u00028\u00000\u001bB!\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00018\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\f\u001a\u00020\tH\u0000¢\u0006\u0004\b\n\u0010\u000bJ\r\u0010\u000e\u001a\u00020\r¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\tH\u0000¢\u0006\u0004\b\u0010\u0010\u000bR\u0019\u0010\u0003\u001a\u00020\u00028\u0006@\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0017\u001a\u00020\u00058&@&X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0019\u001a\u00020\t8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u000b¨\u0006\u001a"}, d2 = {"Lkotlinx/coroutines/internal/Segment;", ExifInterface.LATITUDE_SOUTH, "", "id", Remote.Response.ControlAction.ACTION_PREV, "", "pointers", "<init>", "(JLkotlinx/coroutines/internal/Segment;I)V", "", "decPointers$kotlinx_coroutines_core", "()Z", "decPointers", "", "onSlotCleaned", "()V", "tryIncPointers$kotlinx_coroutines_core", "tryIncPointers", "J", "getId", "()J", "getMaxSlots", "()I", "maxSlots", "getRemoved", "removed", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public abstract class Segment<S extends Segment<S>> extends ConcurrentLinkedListNode<S> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater b = AtomicIntegerFieldUpdater.newUpdater(Segment.class, "cleanedAndPointers");
    private final long a;
    @NotNull
    private volatile /* synthetic */ int cleanedAndPointers;

    public abstract int getMaxSlots();

    public Segment(long j, @Nullable S s, int i) {
        super(s);
        this.a = j;
        this.cleanedAndPointers = i << 16;
    }

    public final long getId() {
        return this.a;
    }

    @Override // kotlinx.coroutines.internal.ConcurrentLinkedListNode
    public boolean getRemoved() {
        return this.cleanedAndPointers == getMaxSlots() && !isTail();
    }

    public final boolean decPointers$kotlinx_coroutines_core() {
        return b.addAndGet(this, -65536) == getMaxSlots() && !isTail();
    }

    public final void onSlotCleaned() {
        if (b.incrementAndGet(this) == getMaxSlots() && !isTail()) {
            remove();
        }
    }

    public final boolean tryIncPointers$kotlinx_coroutines_core() {
        int i;
        do {
            i = this.cleanedAndPointers;
            if (!(i != getMaxSlots() || isTail())) {
                return false;
            }
        } while (!b.compareAndSet(this, i, 65536 + i));
        return true;
    }
}
