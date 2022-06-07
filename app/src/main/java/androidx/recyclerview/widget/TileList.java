package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
class TileList<T> {
    final int a;
    Tile<T> b;
    private final SparseArray<Tile<T>> c = new SparseArray<>(10);

    public TileList(int i) {
        this.a = i;
    }

    public T a(int i) {
        Tile<T> tile = this.b;
        if (tile == null || !tile.a(i)) {
            int indexOfKey = this.c.indexOfKey(i - (i % this.a));
            if (indexOfKey < 0) {
                return null;
            }
            this.b = this.c.valueAt(indexOfKey);
        }
        return this.b.b(i);
    }

    public int a() {
        return this.c.size();
    }

    public void b() {
        this.c.clear();
    }

    public Tile<T> b(int i) {
        if (i < 0 || i >= this.c.size()) {
            return null;
        }
        return this.c.valueAt(i);
    }

    public Tile<T> a(Tile<T> tile) {
        int indexOfKey = this.c.indexOfKey(tile.mStartPosition);
        if (indexOfKey < 0) {
            this.c.put(tile.mStartPosition, tile);
            return null;
        }
        Tile<T> valueAt = this.c.valueAt(indexOfKey);
        this.c.setValueAt(indexOfKey, tile);
        if (this.b == valueAt) {
            this.b = tile;
        }
        return valueAt;
    }

    public Tile<T> c(int i) {
        Tile<T> tile = this.c.get(i);
        if (this.b == tile) {
            this.b = null;
        }
        this.c.delete(i);
        return tile;
    }

    /* loaded from: classes.dex */
    public static class Tile<T> {
        Tile<T> a;
        public int mItemCount;
        public final T[] mItems;
        public int mStartPosition;

        public Tile(Class<T> cls, int i) {
            this.mItems = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        }

        boolean a(int i) {
            int i2 = this.mStartPosition;
            return i2 <= i && i < i2 + this.mItemCount;
        }

        T b(int i) {
            return this.mItems[i - this.mStartPosition];
        }
    }
}
