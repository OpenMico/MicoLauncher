package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;

/* loaded from: classes.dex */
public class AsyncListUtil<T> {
    final Class<T> a;
    final int b;
    final DataCallback<T> c;
    final ViewCallback d;
    final TileList<T> e;
    final ThreadUtil.MainThreadCallback<T> f;
    final ThreadUtil.BackgroundCallback<T> g;
    boolean k;
    final int[] h = new int[2];
    final int[] i = new int[2];
    final int[] j = new int[2];
    private int p = 0;
    int l = 0;
    int m = 0;
    int n = this.m;
    final SparseIntArray o = new SparseIntArray();
    private final ThreadUtil.MainThreadCallback<T> q = new ThreadUtil.MainThreadCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.1
        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void updateItemCount(int i, int i2) {
            if (a(i)) {
                AsyncListUtil asyncListUtil = AsyncListUtil.this;
                asyncListUtil.l = i2;
                asyncListUtil.d.onDataRefresh();
                AsyncListUtil asyncListUtil2 = AsyncListUtil.this;
                asyncListUtil2.m = asyncListUtil2.n;
                a();
                AsyncListUtil asyncListUtil3 = AsyncListUtil.this;
                asyncListUtil3.k = false;
                asyncListUtil3.a();
            }
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void addTile(int i, TileList.Tile<T> tile) {
            if (!a(i)) {
                AsyncListUtil.this.g.recycleTile(tile);
                return;
            }
            TileList.Tile<T> a = AsyncListUtil.this.e.a(tile);
            if (a != null) {
                Log.e("AsyncListUtil", "duplicate tile @" + a.mStartPosition);
                AsyncListUtil.this.g.recycleTile(a);
            }
            int i2 = tile.mStartPosition + tile.mItemCount;
            int i3 = 0;
            while (i3 < AsyncListUtil.this.o.size()) {
                int keyAt = AsyncListUtil.this.o.keyAt(i3);
                if (tile.mStartPosition > keyAt || keyAt >= i2) {
                    i3++;
                } else {
                    AsyncListUtil.this.o.removeAt(i3);
                    AsyncListUtil.this.d.onItemLoaded(keyAt);
                }
            }
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void removeTile(int i, int i2) {
            if (a(i)) {
                TileList.Tile<T> c = AsyncListUtil.this.e.c(i2);
                if (c == null) {
                    Log.e("AsyncListUtil", "tile not found @" + i2);
                    return;
                }
                AsyncListUtil.this.g.recycleTile(c);
            }
        }

        private void a() {
            for (int i = 0; i < AsyncListUtil.this.e.a(); i++) {
                AsyncListUtil.this.g.recycleTile(AsyncListUtil.this.e.b(i));
            }
            AsyncListUtil.this.e.b();
        }

        private boolean a(int i) {
            return i == AsyncListUtil.this.n;
        }
    };
    private final ThreadUtil.BackgroundCallback<T> r = new ThreadUtil.BackgroundCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.2
        final SparseBooleanArray a = new SparseBooleanArray();
        private TileList.Tile<T> c;
        private int d;
        private int e;
        private int f;
        private int g;

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void refresh(int i) {
            this.d = i;
            this.a.clear();
            this.e = AsyncListUtil.this.c.refreshData();
            AsyncListUtil.this.f.updateItemCount(this.d, this.e);
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            if (i <= i2) {
                int a = a(i);
                int a2 = a(i2);
                this.f = a(i3);
                this.g = a(i4);
                if (i5 == 1) {
                    a(this.f, a2, i5, true);
                    a(a2 + AsyncListUtil.this.b, this.g, i5, false);
                    return;
                }
                a(a, this.g, i5, false);
                a(this.f, a - AsyncListUtil.this.b, i5, true);
            }
        }

        private int a(int i) {
            return i - (i % AsyncListUtil.this.b);
        }

        private void a(int i, int i2, int i3, boolean z) {
            int i4 = i;
            while (i4 <= i2) {
                AsyncListUtil.this.g.loadTile(z ? (i2 + i) - i4 : i4, i3);
                i4 += AsyncListUtil.this.b;
            }
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void loadTile(int i, int i2) {
            if (!b(i)) {
                TileList.Tile<T> a = a();
                a.mStartPosition = i;
                a.mItemCount = Math.min(AsyncListUtil.this.b, this.e - a.mStartPosition);
                AsyncListUtil.this.c.fillData(a.mItems, a.mStartPosition, a.mItemCount);
                d(i2);
                a(a);
            }
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void recycleTile(TileList.Tile<T> tile) {
            AsyncListUtil.this.c.recycleData(tile.mItems, tile.mItemCount);
            tile.a = this.c;
            this.c = tile;
        }

        private TileList.Tile<T> a() {
            TileList.Tile<T> tile = this.c;
            if (tile == null) {
                return new TileList.Tile<>(AsyncListUtil.this.a, AsyncListUtil.this.b);
            }
            this.c = tile.a;
            return tile;
        }

        private boolean b(int i) {
            return this.a.get(i);
        }

        private void a(TileList.Tile<T> tile) {
            this.a.put(tile.mStartPosition, true);
            AsyncListUtil.this.f.addTile(this.d, tile);
        }

        private void c(int i) {
            this.a.delete(i);
            AsyncListUtil.this.f.removeTile(this.d, i);
        }

        private void d(int i) {
            int maxCachedTiles = AsyncListUtil.this.c.getMaxCachedTiles();
            while (this.a.size() >= maxCachedTiles) {
                int keyAt = this.a.keyAt(0);
                SparseBooleanArray sparseBooleanArray = this.a;
                int keyAt2 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                int i2 = this.f - keyAt;
                int i3 = keyAt2 - this.g;
                if (i2 > 0 && (i2 >= i3 || i == 2)) {
                    c(keyAt);
                } else if (i3 <= 0) {
                    return;
                } else {
                    if (i2 < i3 || i == 1) {
                        c(keyAt2);
                    } else {
                        return;
                    }
                }
            }
        }
    };

    /* loaded from: classes.dex */
    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(@NonNull T[] tArr, int i, int i2);

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }

        @WorkerThread
        public void recycleData(@NonNull T[] tArr, int i) {
        }

        @WorkerThread
        public abstract int refreshData();
    }

    public AsyncListUtil(@NonNull Class<T> cls, int i, @NonNull DataCallback<T> dataCallback, @NonNull ViewCallback viewCallback) {
        this.a = cls;
        this.b = i;
        this.c = dataCallback;
        this.d = viewCallback;
        this.e = new TileList<>(this.b);
        h hVar = new h();
        this.f = hVar.a(this.q);
        this.g = hVar.a(this.r);
        refresh();
    }

    private boolean b() {
        return this.n != this.m;
    }

    public void onRangeChanged() {
        if (!b()) {
            a();
            this.k = true;
        }
    }

    public void refresh() {
        this.o.clear();
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.g;
        int i = this.n + 1;
        this.n = i;
        backgroundCallback.refresh(i);
    }

    @Nullable
    public T getItem(int i) {
        if (i < 0 || i >= this.l) {
            throw new IndexOutOfBoundsException(i + " is not within 0 and " + this.l);
        }
        T a = this.e.a(i);
        if (a == null && !b()) {
            this.o.put(i, 0);
        }
        return a;
    }

    public int getItemCount() {
        return this.l;
    }

    void a() {
        this.d.getItemRangeInto(this.h);
        int[] iArr = this.h;
        if (iArr[0] <= iArr[1] && iArr[0] >= 0 && iArr[1] < this.l) {
            if (!this.k) {
                this.p = 0;
            } else {
                int i = iArr[0];
                int[] iArr2 = this.i;
                if (i > iArr2[1] || iArr2[0] > iArr[1]) {
                    this.p = 0;
                } else if (iArr[0] < iArr2[0]) {
                    this.p = 1;
                } else if (iArr[0] > iArr2[0]) {
                    this.p = 2;
                }
            }
            int[] iArr3 = this.i;
            int[] iArr4 = this.h;
            iArr3[0] = iArr4[0];
            iArr3[1] = iArr4[1];
            this.d.extendRangeInto(iArr4, this.j, this.p);
            int[] iArr5 = this.j;
            iArr5[0] = Math.min(this.h[0], Math.max(iArr5[0], 0));
            int[] iArr6 = this.j;
            iArr6[1] = Math.max(this.h[1], Math.min(iArr6[1], this.l - 1));
            ThreadUtil.BackgroundCallback<T> backgroundCallback = this.g;
            int[] iArr7 = this.h;
            int i2 = iArr7[0];
            int i3 = iArr7[1];
            int[] iArr8 = this.j;
            backgroundCallback.updateRange(i2, i3, iArr8[0], iArr8[1], this.p);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public abstract void getItemRangeInto(@NonNull int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i);

        @UiThread
        public void extendRangeInto(@NonNull int[] iArr, @NonNull int[] iArr2, int i) {
            int i2 = (iArr[1] - iArr[0]) + 1;
            int i3 = i2 / 2;
            iArr2[0] = iArr[0] - (i == 1 ? i2 : i3);
            int i4 = iArr[1];
            if (i != 2) {
                i2 = i3;
            }
            iArr2[1] = i4 + i2;
        }
    }
}
