package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.onetrack.api.b;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: AbstractIterator.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH$J\b\u0010\n\u001a\u00020\tH\u0004J\t\u0010\u000b\u001a\u00020\fH\u0096\u0002J\u000e\u0010\r\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\fH\u0002R\u0012\u0010\u0004\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lkotlin/collections/AbstractIterator;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "nextValue", "Ljava/lang/Object;", XiaomiOAuthConstants.EXTRA_STATE_2, "Lkotlin/collections/State;", "computeNext", "", "done", "hasNext", "", "next", "()Ljava/lang/Object;", "setNext", b.p, "(Ljava/lang/Object;)V", "tryToComputeNext", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public abstract class AbstractIterator<T> implements Iterator<T>, KMappedMarker {
    private ah a = ah.NotReady;
    private T b;

    protected abstract void computeNext();

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.a != ah.Failed) {
            switch (this.a) {
                case Done:
                    return false;
                case Ready:
                    return true;
                default:
                    return a();
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    @Override // java.util.Iterator
    public T next() {
        if (hasNext()) {
            this.a = ah.NotReady;
            return this.b;
        }
        throw new NoSuchElementException();
    }

    private final boolean a() {
        this.a = ah.Failed;
        computeNext();
        return this.a == ah.Ready;
    }

    protected final void setNext(T t) {
        this.b = t;
        this.a = ah.Ready;
    }

    protected final void done() {
        this.a = ah.Done;
    }
}
