package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001e\u0010\u0005\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, d2 = {"kotlin/sequences/FilteringSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class FilteringSequence$iterator$1 implements Iterator<T>, KMappedMarker {
    final /* synthetic */ FilteringSequence a;
    @NotNull
    private final Iterator<T> b;
    private int c = -1;
    @Nullable
    private T d;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FilteringSequence$iterator$1(FilteringSequence filteringSequence) {
        Sequence sequence;
        this.a = filteringSequence;
        sequence = filteringSequence.a;
        this.b = sequence.iterator();
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.b;
    }

    public final int getNextState() {
        return this.c;
    }

    public final void setNextState(int i) {
        this.c = i;
    }

    @Nullable
    public final T getNextItem() {
        return this.d;
    }

    public final void setNextItem(@Nullable T t) {
        this.d = t;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [T, java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a() {
        /*
            r3 = this;
        L_0x0000:
            java.util.Iterator<T> r0 = r3.b
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L_0x002c
            java.util.Iterator<T> r0 = r3.b
            java.lang.Object r0 = r0.next()
            kotlin.sequences.FilteringSequence r1 = r3.a
            kotlin.jvm.functions.Function1 r1 = kotlin.sequences.FilteringSequence.access$getPredicate$p(r1)
            java.lang.Object r1 = r1.invoke(r0)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            kotlin.sequences.FilteringSequence r2 = r3.a
            boolean r2 = kotlin.sequences.FilteringSequence.access$getSendWhen$p(r2)
            if (r1 != r2) goto L_0x0000
            r3.d = r0
            r0 = 1
            r3.c = r0
            return
        L_0x002c:
            r0 = 0
            r3.c = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.FilteringSequence$iterator$1.a():void");
    }

    @Override // java.util.Iterator
    public T next() {
        if (this.c == -1) {
            a();
        }
        if (this.c != 0) {
            T t = this.d;
            this.d = null;
            this.c = -1;
            return t;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.c == -1) {
            a();
        }
        return this.c == 1;
    }
}
