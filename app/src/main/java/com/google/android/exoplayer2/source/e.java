package com.google.android.exoplayer2.source;

import android.util.SparseArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Consumer;

/* compiled from: SpannedData.java */
/* loaded from: classes2.dex */
final class e<V> {
    private int a;
    private final SparseArray<V> b;
    private final Consumer<V> c;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Object obj) {
    }

    public e() {
        this($$Lambda$e$ri061enH25DulzBQIurYWW9Syp8.INSTANCE);
    }

    public e(Consumer<V> consumer) {
        this.b = new SparseArray<>();
        this.c = consumer;
        this.a = -1;
    }

    public V a(int i) {
        if (this.a == -1) {
            this.a = 0;
        }
        while (true) {
            int i2 = this.a;
            if (i2 <= 0 || i >= this.b.keyAt(i2)) {
                break;
            }
            this.a--;
        }
        while (this.a < this.b.size() - 1 && i >= this.b.keyAt(this.a + 1)) {
            this.a++;
        }
        return this.b.valueAt(this.a);
    }

    public void a(int i, V v) {
        boolean z = false;
        if (this.a == -1) {
            Assertions.checkState(this.b.size() == 0);
            this.a = 0;
        }
        if (this.b.size() > 0) {
            SparseArray<V> sparseArray = this.b;
            int keyAt = sparseArray.keyAt(sparseArray.size() - 1);
            if (i >= keyAt) {
                z = true;
            }
            Assertions.checkArgument(z);
            if (keyAt == i) {
                SparseArray<V> sparseArray2 = this.b;
                this.c.accept(sparseArray2.valueAt(sparseArray2.size() - 1));
            }
        }
        this.b.append(i, v);
    }

    public V a() {
        SparseArray<V> sparseArray = this.b;
        return sparseArray.valueAt(sparseArray.size() - 1);
    }

    public void b(int i) {
        int i2 = 0;
        while (i2 < this.b.size() - 1) {
            int i3 = i2 + 1;
            if (i >= this.b.keyAt(i3)) {
                this.c.accept(this.b.valueAt(i2));
                this.b.removeAt(i2);
                int i4 = this.a;
                if (i4 > 0) {
                    this.a = i4 - 1;
                }
                i2 = i3;
            } else {
                return;
            }
        }
    }

    public void c(int i) {
        for (int size = this.b.size() - 1; size >= 0 && i < this.b.keyAt(size); size--) {
            this.c.accept(this.b.valueAt(size));
            this.b.removeAt(size);
        }
        this.a = this.b.size() > 0 ? Math.min(this.a, this.b.size() - 1) : -1;
    }

    public void b() {
        for (int i = 0; i < this.b.size(); i++) {
            this.c.accept(this.b.valueAt(i));
        }
        this.a = -1;
        this.b.clear();
    }

    public boolean c() {
        return this.b.size() == 0;
    }
}
