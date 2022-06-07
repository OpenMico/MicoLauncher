package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: IndexBasedArrayIterator.java */
/* loaded from: classes.dex */
abstract class b<T> implements Iterator<T> {
    private int a;
    private int b;
    private boolean c;

    protected abstract T a(int i);

    protected abstract void b(int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(int i) {
        this.a = i;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b < this.a;
    }

    @Override // java.util.Iterator
    public T next() {
        if (hasNext()) {
            T a = a(this.b);
            this.b++;
            this.c = true;
            return a;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public void remove() {
        if (this.c) {
            int i = this.b - 1;
            this.b = i;
            b(i);
            this.a--;
            this.c = false;
            return;
        }
        throw new IllegalStateException();
    }
}
