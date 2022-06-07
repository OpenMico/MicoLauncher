package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/* loaded from: classes.dex */
public class SortedList<T> {
    public static final int INVALID_POSITION = -1;
    T[] a;
    private T[] b;
    private int c;
    private int d;
    private int e;
    private Callback f;
    private BatchedCallback g;
    private int h;
    private final Class<T> i;

    public SortedList(@NonNull Class<T> cls, @NonNull Callback<T> callback) {
        this(cls, callback, 10);
    }

    public SortedList(@NonNull Class<T> cls, @NonNull Callback<T> callback, int i) {
        this.i = cls;
        this.a = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        this.f = callback;
        this.h = 0;
    }

    public int size() {
        return this.h;
    }

    public int add(T t) {
        b();
        return a((SortedList<T>) t, true);
    }

    public void addAll(@NonNull T[] tArr, boolean z) {
        b();
        if (tArr.length != 0) {
            if (z) {
                a((Object[]) tArr);
            } else {
                a((Object[]) d(tArr));
            }
        }
    }

    public void addAll(@NonNull T... tArr) {
        addAll(tArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addAll(@NonNull Collection<T> collection) {
        addAll(collection.toArray((Object[]) Array.newInstance((Class<?>) this.i, collection.size())), true);
    }

    public void replaceAll(@NonNull T[] tArr, boolean z) {
        b();
        if (z) {
            b(tArr);
        } else {
            b(d(tArr));
        }
    }

    public void replaceAll(@NonNull T... tArr) {
        replaceAll(tArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void replaceAll(@NonNull Collection<T> collection) {
        replaceAll(collection.toArray((Object[]) Array.newInstance((Class<?>) this.i, collection.size())), true);
    }

    private void a(T[] tArr) {
        if (tArr.length >= 1) {
            int c = c(tArr);
            if (this.h == 0) {
                this.a = tArr;
                this.h = c;
                this.f.onInserted(0, c);
                return;
            }
            a(tArr, c);
        }
    }

    private void b(@NonNull T[] tArr) {
        boolean z = !(this.f instanceof BatchedCallback);
        if (z) {
            beginBatchedUpdates();
        }
        this.c = 0;
        this.d = this.h;
        this.b = this.a;
        this.e = 0;
        int c = c(tArr);
        this.a = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, c));
        while (true) {
            if (this.e >= c && this.c >= this.d) {
                break;
            }
            int i = this.c;
            int i2 = this.d;
            if (i >= i2) {
                int i3 = this.e;
                int i4 = c - i3;
                System.arraycopy(tArr, i3, this.a, i3, i4);
                this.e += i4;
                this.h += i4;
                this.f.onInserted(i3, i4);
                break;
            }
            int i5 = this.e;
            if (i5 >= c) {
                int i6 = i2 - i;
                this.h -= i6;
                this.f.onRemoved(i5, i6);
                break;
            }
            T t = this.b[i];
            T t2 = tArr[i5];
            int compare = this.f.compare(t, t2);
            if (compare < 0) {
                a();
            } else if (compare > 0) {
                a((SortedList<T>) t2);
            } else if (!this.f.areItemsTheSame(t, t2)) {
                a();
                a((SortedList<T>) t2);
            } else {
                T[] tArr2 = this.a;
                int i7 = this.e;
                tArr2[i7] = t2;
                this.c++;
                this.e = i7 + 1;
                if (!this.f.areContentsTheSame(t, t2)) {
                    Callback callback = this.f;
                    callback.onChanged(this.e - 1, 1, callback.getChangePayload(t, t2));
                }
            }
        }
        this.b = null;
        if (z) {
            endBatchedUpdates();
        }
    }

    private void a(T t) {
        T[] tArr = this.a;
        int i = this.e;
        tArr[i] = t;
        this.e = i + 1;
        this.h++;
        this.f.onInserted(this.e - 1, 1);
    }

    private void a() {
        this.h--;
        this.c++;
        this.f.onRemoved(this.e, 1);
    }

    private int c(@NonNull T[] tArr) {
        if (tArr.length == 0) {
            return 0;
        }
        Arrays.sort(tArr, this.f);
        int i = 0;
        int i2 = 1;
        for (int i3 = 1; i3 < tArr.length; i3++) {
            T t = tArr[i3];
            if (this.f.compare(tArr[i], t) == 0) {
                int a = a((SortedList<T>) t, (SortedList<T>[]) tArr, i, i2);
                if (a != -1) {
                    tArr[a] = t;
                } else {
                    if (i2 != i3) {
                        tArr[i2] = t;
                    }
                    i2++;
                }
            } else {
                if (i2 != i3) {
                    tArr[i2] = t;
                }
                i2++;
                i = i2;
            }
        }
        return i2;
    }

    private int a(T t, T[] tArr, int i, int i2) {
        while (i < i2) {
            if (this.f.areItemsTheSame(tArr[i], t)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void a(T[] tArr, int i) {
        int i2 = 0;
        boolean z = !(this.f instanceof BatchedCallback);
        if (z) {
            beginBatchedUpdates();
        }
        this.b = this.a;
        this.c = 0;
        int i3 = this.h;
        this.d = i3;
        this.a = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, i3 + i + 10));
        this.e = 0;
        while (true) {
            if (this.c >= this.d && i2 >= i) {
                break;
            }
            int i4 = this.c;
            int i5 = this.d;
            if (i4 == i5) {
                int i6 = i - i2;
                System.arraycopy(tArr, i2, this.a, this.e, i6);
                this.e += i6;
                this.h += i6;
                this.f.onInserted(this.e - i6, i6);
                break;
            } else if (i2 == i) {
                int i7 = i5 - i4;
                System.arraycopy(this.b, i4, this.a, this.e, i7);
                this.e += i7;
                break;
            } else {
                T t = this.b[i4];
                T t2 = tArr[i2];
                int compare = this.f.compare(t, t2);
                if (compare > 0) {
                    T[] tArr2 = this.a;
                    int i8 = this.e;
                    this.e = i8 + 1;
                    tArr2[i8] = t2;
                    this.h++;
                    i2++;
                    this.f.onInserted(this.e - 1, 1);
                } else if (compare != 0 || !this.f.areItemsTheSame(t, t2)) {
                    T[] tArr3 = this.a;
                    int i9 = this.e;
                    this.e = i9 + 1;
                    tArr3[i9] = t;
                    this.c++;
                } else {
                    T[] tArr4 = this.a;
                    int i10 = this.e;
                    this.e = i10 + 1;
                    tArr4[i10] = t2;
                    i2++;
                    this.c++;
                    if (!this.f.areContentsTheSame(t, t2)) {
                        Callback callback = this.f;
                        callback.onChanged(this.e - 1, 1, callback.getChangePayload(t, t2));
                    }
                }
            }
        }
        this.b = null;
        if (z) {
            endBatchedUpdates();
        }
    }

    private void b() {
        if (this.b != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public void beginBatchedUpdates() {
        b();
        Callback callback = this.f;
        if (!(callback instanceof BatchedCallback)) {
            if (this.g == null) {
                this.g = new BatchedCallback(callback);
            }
            this.f = this.g;
        }
    }

    public void endBatchedUpdates() {
        b();
        Callback callback = this.f;
        if (callback instanceof BatchedCallback) {
            ((BatchedCallback) callback).dispatchLastEvent();
        }
        Callback callback2 = this.f;
        BatchedCallback batchedCallback = this.g;
        if (callback2 == batchedCallback) {
            this.f = batchedCallback.a;
        }
    }

    private int a(T t, boolean z) {
        int a = a(t, this.a, 0, this.h, 1);
        if (a == -1) {
            a = 0;
        } else if (a < this.h) {
            T t2 = this.a[a];
            if (this.f.areItemsTheSame(t2, t)) {
                if (this.f.areContentsTheSame(t2, t)) {
                    this.a[a] = t;
                    return a;
                }
                this.a[a] = t;
                Callback callback = this.f;
                callback.onChanged(a, 1, callback.getChangePayload(t2, t));
                return a;
            }
        }
        a(a, (int) t);
        if (z) {
            this.f.onInserted(a, 1);
        }
        return a;
    }

    public boolean remove(T t) {
        b();
        return b(t, true);
    }

    public T removeItemAt(int i) {
        b();
        T t = get(i);
        a(i, true);
        return t;
    }

    private boolean b(T t, boolean z) {
        int a = a(t, this.a, 0, this.h, 2);
        if (a == -1) {
            return false;
        }
        a(a, z);
        return true;
    }

    private void a(int i, boolean z) {
        T[] tArr = this.a;
        System.arraycopy(tArr, i + 1, tArr, i, (this.h - i) - 1);
        this.h--;
        this.a[this.h] = null;
        if (z) {
            this.f.onRemoved(i, 1);
        }
    }

    public void updateItemAt(int i, T t) {
        b();
        T t2 = get(i);
        boolean z = t2 == t || !this.f.areContentsTheSame(t2, t);
        if (t2 == t || this.f.compare(t2, t) != 0) {
            if (z) {
                Callback callback = this.f;
                callback.onChanged(i, 1, callback.getChangePayload(t2, t));
            }
            a(i, false);
            int a = a((SortedList<T>) t, false);
            if (i != a) {
                this.f.onMoved(i, a);
                return;
            }
            return;
        }
        this.a[i] = t;
        if (z) {
            Callback callback2 = this.f;
            callback2.onChanged(i, 1, callback2.getChangePayload(t2, t));
        }
    }

    public void recalculatePositionOfItemAt(int i) {
        b();
        T t = get(i);
        a(i, false);
        int a = a((SortedList<T>) t, false);
        if (i != a) {
            this.f.onMoved(i, a);
        }
    }

    public T get(int i) throws IndexOutOfBoundsException {
        int i2;
        if (i >= this.h || i < 0) {
            throw new IndexOutOfBoundsException("Asked to get item at " + i + " but size is " + this.h);
        }
        T[] tArr = this.b;
        if (tArr == null || i < (i2 = this.e)) {
            return this.a[i];
        }
        return tArr[(i - i2) + this.c];
    }

    public int indexOf(T t) {
        if (this.b == null) {
            return a(t, this.a, 0, this.h, 4);
        }
        int a = a(t, this.a, 0, this.e, 4);
        if (a != -1) {
            return a;
        }
        int a2 = a(t, this.b, this.c, this.d, 4);
        if (a2 != -1) {
            return (a2 - this.c) + this.e;
        }
        return -1;
    }

    private int a(T t, T[] tArr, int i, int i2, int i3) {
        while (i < i2) {
            int i4 = (i + i2) / 2;
            T t2 = tArr[i4];
            int compare = this.f.compare(t2, t);
            if (compare < 0) {
                i = i4 + 1;
            } else if (compare != 0) {
                i2 = i4;
            } else if (this.f.areItemsTheSame(t2, t)) {
                return i4;
            } else {
                int a = a((SortedList<T>) t, i4, i, i2);
                return (i3 == 1 && a == -1) ? i4 : a;
            }
        }
        if (i3 == 1) {
            return i;
        }
        return -1;
    }

    private int a(T t, int i, int i2, int i3) {
        T t2;
        for (int i4 = i - 1; i4 >= i2; i4--) {
            T t3 = this.a[i4];
            if (this.f.compare(t3, t) != 0) {
                break;
            } else if (this.f.areItemsTheSame(t3, t)) {
                return i4;
            }
        }
        do {
            i++;
            if (i >= i3) {
                return -1;
            }
            t2 = this.a[i];
            if (this.f.compare(t2, t) != 0) {
                return -1;
            }
        } while (!this.f.areItemsTheSame(t2, t));
        return i;
    }

    private void a(int i, T t) {
        int i2 = this.h;
        if (i <= i2) {
            T[] tArr = this.a;
            if (i2 == tArr.length) {
                T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, tArr.length + 10));
                System.arraycopy(this.a, 0, tArr2, 0, i);
                tArr2[i] = t;
                System.arraycopy(this.a, i, tArr2, i + 1, this.h - i);
                this.a = tArr2;
            } else {
                System.arraycopy(tArr, i, tArr, i + 1, i2 - i);
                this.a[i] = t;
            }
            this.h++;
            return;
        }
        throw new IndexOutOfBoundsException("cannot add item to " + i + " because size is " + this.h);
    }

    private T[] d(T[] tArr) {
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, tArr.length));
        System.arraycopy(tArr, 0, tArr2, 0, tArr.length);
        return tArr2;
    }

    public void clear() {
        b();
        int i = this.h;
        if (i != 0) {
            Arrays.fill(this.a, 0, i, (Object) null);
            this.h = 0;
            this.f.onRemoved(0, i);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Callback<T2> implements ListUpdateCallback, Comparator<T2> {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        public abstract int compare(T2 t2, T2 t22);

        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return null;
        }

        public abstract void onChanged(int i, int i2);

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            onChanged(i, i2);
        }
    }

    /* loaded from: classes.dex */
    public static class BatchedCallback<T2> extends Callback<T2> {
        final Callback<T2> a;
        private final BatchingListUpdateCallback b;

        public BatchedCallback(Callback<T2> callback) {
            this.a = callback;
            this.b = new BatchingListUpdateCallback(this.a);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback, java.util.Comparator
        public int compare(T2 t2, T2 t22) {
            return this.a.compare(t2, t22);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int i, int i2) {
            this.b.onInserted(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int i, int i2) {
            this.b.onRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int i, int i2) {
            this.b.onMoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public void onChanged(int i, int i2) {
            this.b.onChanged(i, i2, null);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback, androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            this.b.onChanged(i, i2, obj);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public boolean areContentsTheSame(T2 t2, T2 t22) {
            return this.a.areContentsTheSame(t2, t22);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public boolean areItemsTheSame(T2 t2, T2 t22) {
            return this.a.areItemsTheSame(t2, t22);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return this.a.getChangePayload(t2, t22);
        }

        public void dispatchLastEvent() {
            this.b.dispatchLastEvent();
        }
    }
}
