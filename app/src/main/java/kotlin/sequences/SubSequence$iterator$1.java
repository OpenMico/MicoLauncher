package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"kotlin/sequences/SubSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "position", "", "getPosition", "()I", "setPosition", "(I)V", "drop", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class SubSequence$iterator$1 implements Iterator<T>, KMappedMarker {
    final /* synthetic */ SubSequence a;
    @NotNull
    private final Iterator<T> b;
    private int c;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SubSequence$iterator$1(SubSequence subSequence) {
        Sequence sequence;
        this.a = subSequence;
        sequence = subSequence.a;
        this.b = sequence.iterator();
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.b;
    }

    public final int getPosition() {
        return this.c;
    }

    public final void setPosition(int i) {
        this.c = i;
    }

    /* JADX WARN: Incorrect condition in loop: B:3:0x0008 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a() {
        /*
            r2 = this;
        L_0x0000:
            int r0 = r2.c
            kotlin.sequences.SubSequence r1 = r2.a
            int r1 = kotlin.sequences.SubSequence.access$getStartIndex$p(r1)
            if (r0 >= r1) goto L_0x001e
            java.util.Iterator<T> r0 = r2.b
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L_0x001e
            java.util.Iterator<T> r0 = r2.b
            r0.next()
            int r0 = r2.c
            int r0 = r0 + 1
            r2.c = r0
            goto L_0x0000
        L_0x001e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SubSequence$iterator$1.a():void");
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        int i;
        a();
        int i2 = this.c;
        i = this.a.c;
        return i2 < i && this.b.hasNext();
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
    @Override // java.util.Iterator
    public T next() {
        int i;
        a();
        int i2 = this.c;
        i = this.a.c;
        if (i2 < i) {
            this.c++;
            return this.b.next();
        }
        throw new NoSuchElementException();
    }
}
