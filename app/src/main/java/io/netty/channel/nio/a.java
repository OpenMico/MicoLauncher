package io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Iterator;

/* compiled from: SelectedSelectionKeySet.java */
/* loaded from: classes4.dex */
final class a extends AbstractSet<SelectionKey> {
    private int b;
    private int d;
    private boolean e = true;
    private SelectionKey[] a = new SelectionKey[1024];
    private SelectionKey[] c = (SelectionKey[]) this.a.clone();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        return false;
    }

    /* renamed from: a */
    public boolean add(SelectionKey selectionKey) {
        if (selectionKey == null) {
            return false;
        }
        if (this.e) {
            int i = this.b;
            SelectionKey[] selectionKeyArr = this.a;
            int i2 = i + 1;
            selectionKeyArr[i] = selectionKey;
            this.b = i2;
            if (i2 != selectionKeyArr.length) {
                return true;
            }
            b();
            return true;
        }
        int i3 = this.d;
        SelectionKey[] selectionKeyArr2 = this.c;
        int i4 = i3 + 1;
        selectionKeyArr2[i3] = selectionKey;
        this.d = i4;
        if (i4 != selectionKeyArr2.length) {
            return true;
        }
        c();
        return true;
    }

    private void b() {
        SelectionKey[] selectionKeyArr = this.a;
        SelectionKey[] selectionKeyArr2 = new SelectionKey[selectionKeyArr.length << 1];
        System.arraycopy(selectionKeyArr, 0, selectionKeyArr2, 0, this.b);
        this.a = selectionKeyArr2;
    }

    private void c() {
        SelectionKey[] selectionKeyArr = this.c;
        SelectionKey[] selectionKeyArr2 = new SelectionKey[selectionKeyArr.length << 1];
        System.arraycopy(selectionKeyArr, 0, selectionKeyArr2, 0, this.d);
        this.c = selectionKeyArr2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SelectionKey[] a() {
        if (this.e) {
            this.e = false;
            SelectionKey[] selectionKeyArr = this.a;
            selectionKeyArr[this.b] = null;
            this.d = 0;
            return selectionKeyArr;
        }
        this.e = true;
        SelectionKey[] selectionKeyArr2 = this.c;
        selectionKeyArr2[this.d] = null;
        this.b = 0;
        return selectionKeyArr2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        if (this.e) {
            return this.b;
        }
        return this.d;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<SelectionKey> iterator() {
        throw new UnsupportedOperationException();
    }
}
