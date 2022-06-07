package androidx.recyclerview.widget;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DiffUtil {
    private static final Comparator<b> a = new Comparator<b>() { // from class: androidx.recyclerview.widget.DiffUtil.1
        /* renamed from: a */
        public int compare(b bVar, b bVar2) {
            return bVar.a - bVar2.a;
        }
    };

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        @Nullable
        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    /* loaded from: classes.dex */
    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(@NonNull T t, @NonNull T t2);

        public abstract boolean areItemsTheSame(@NonNull T t, @NonNull T t2);

        @Nullable
        public Object getChangePayload(@NonNull T t, @NonNull T t2) {
            return null;
        }
    }

    private DiffUtil() {
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback) {
        return calculateDiff(callback, true);
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback, boolean z) {
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new d(0, oldListSize, 0, newListSize));
        int i = ((((oldListSize + newListSize) + 1) / 2) * 2) + 1;
        a aVar = new a(i);
        a aVar2 = new a(i);
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            d dVar = (d) arrayList2.remove(arrayList2.size() - 1);
            e a2 = a(dVar, callback, aVar, aVar2);
            if (a2 != null) {
                if (a2.c() > 0) {
                    arrayList.add(a2.d());
                }
                d dVar2 = arrayList3.isEmpty() ? new d() : (d) arrayList3.remove(arrayList3.size() - 1);
                dVar2.a = dVar.a;
                dVar2.c = dVar.c;
                dVar2.b = a2.a;
                dVar2.d = a2.b;
                arrayList2.add(dVar2);
                dVar.b = dVar.b;
                dVar.d = dVar.d;
                dVar.a = a2.c;
                dVar.c = a2.d;
                arrayList2.add(dVar);
            } else {
                arrayList3.add(dVar);
            }
        }
        Collections.sort(arrayList, a);
        return new DiffResult(callback, arrayList, aVar.a(), aVar2.a(), z);
    }

    @Nullable
    private static e a(d dVar, Callback callback, a aVar, a aVar2) {
        if (dVar.a() < 1 || dVar.b() < 1) {
            return null;
        }
        int a2 = ((dVar.a() + dVar.b()) + 1) / 2;
        aVar.a(1, dVar.a);
        aVar2.a(1, dVar.b);
        for (int i = 0; i < a2; i++) {
            e a3 = a(dVar, callback, aVar, aVar2, i);
            if (a3 != null) {
                return a3;
            }
            e b2 = b(dVar, callback, aVar, aVar2, i);
            if (b2 != null) {
                return b2;
            }
        }
        return null;
    }

    @Nullable
    private static e a(d dVar, Callback callback, a aVar, a aVar2, int i) {
        int i2;
        int i3;
        boolean z = true;
        if (Math.abs(dVar.a() - dVar.b()) % 2 != 1) {
            z = false;
        }
        int a2 = dVar.a() - dVar.b();
        int i4 = -i;
        for (int i5 = i4; i5 <= i; i5 += 2) {
            if (i5 == i4 || (i5 != i && aVar.a(i5 + 1) > aVar.a(i5 - 1))) {
                i3 = aVar.a(i5 + 1);
                i2 = i3;
            } else {
                int a3 = aVar.a(i5 - 1);
                i3 = a3 + 1;
                i2 = a3;
            }
            int i6 = (dVar.c + (i3 - dVar.a)) - i5;
            int i7 = (i == 0 || i3 != i2) ? i6 : i6 - 1;
            while (i3 < dVar.b && i6 < dVar.d) {
                if (!callback.areItemsTheSame(i3, i6)) {
                    break;
                }
                i3++;
                i6++;
            }
            aVar.a(i5, i3);
            if (z) {
                int i8 = a2 - i5;
                if (i8 >= i4 + 1 && i8 <= i - 1) {
                    if (aVar2.a(i8) <= i3) {
                        e eVar = new e();
                        eVar.a = i2;
                        eVar.b = i7;
                        eVar.c = i3;
                        eVar.d = i6;
                        eVar.e = false;
                        return eVar;
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    private static e b(d dVar, Callback callback, a aVar, a aVar2, int i) {
        int i2;
        int i3;
        boolean z = (dVar.a() - dVar.b()) % 2 == 0;
        int a2 = dVar.a() - dVar.b();
        int i4 = -i;
        for (int i5 = i4; i5 <= i; i5 += 2) {
            if (i5 == i4 || (i5 != i && aVar2.a(i5 + 1) < aVar2.a(i5 - 1))) {
                i3 = aVar2.a(i5 + 1);
                i2 = i3;
            } else {
                int a3 = aVar2.a(i5 - 1);
                i3 = a3 - 1;
                i2 = a3;
            }
            int i6 = dVar.d - ((dVar.b - i3) - i5);
            int i7 = (i == 0 || i3 != i2) ? i6 : i6 + 1;
            while (i3 > dVar.a && i6 > dVar.c) {
                if (!callback.areItemsTheSame(i3 - 1, i6 - 1)) {
                    break;
                }
                i3--;
                i6--;
            }
            aVar2.a(i5, i3);
            if (z) {
                int i8 = a2 - i5;
                if (i8 >= i4 && i8 <= i) {
                    if (aVar.a(i8) >= i3) {
                        e eVar = new e();
                        eVar.a = i3;
                        eVar.b = i6;
                        eVar.c = i2;
                        eVar.d = i7;
                        eVar.e = true;
                        return eVar;
                    }
                }
            }
        }
        return null;
    }

    /* loaded from: classes.dex */
    public static class b {
        public final int a;
        public final int b;
        public final int c;

        b(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        int a() {
            return this.a + this.c;
        }

        int b() {
            return this.b + this.c;
        }
    }

    /* loaded from: classes.dex */
    public static class e {
        public int a;
        public int b;
        public int c;
        public int d;
        public boolean e;

        e() {
        }

        boolean a() {
            return this.d - this.b != this.c - this.a;
        }

        boolean b() {
            return this.d - this.b > this.c - this.a;
        }

        int c() {
            return Math.min(this.c - this.a, this.d - this.b);
        }

        @NonNull
        b d() {
            if (!a()) {
                int i = this.a;
                return new b(i, this.b, this.c - i);
            } else if (this.e) {
                return new b(this.a, this.b, c());
            } else {
                if (b()) {
                    return new b(this.a, this.b + 1, c());
                }
                return new b(this.a + 1, this.b, c());
            }
        }
    }

    /* loaded from: classes.dex */
    public static class d {
        int a;
        int b;
        int c;
        int d;

        public d() {
        }

        public d(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }

        int a() {
            return this.b - this.a;
        }

        int b() {
            return this.d - this.c;
        }
    }

    /* loaded from: classes.dex */
    public static class DiffResult {
        public static final int NO_POSITION = -1;
        private final List<b> a;
        private final int[] b;
        private final int[] c;
        private final Callback d;
        private final int e;
        private final int f;
        private final boolean g;

        DiffResult(Callback callback, List<b> list, int[] iArr, int[] iArr2, boolean z) {
            this.a = list;
            this.b = iArr;
            this.c = iArr2;
            Arrays.fill(this.b, 0);
            Arrays.fill(this.c, 0);
            this.d = callback;
            this.e = callback.getOldListSize();
            this.f = callback.getNewListSize();
            this.g = z;
            a();
            b();
        }

        private void a() {
            b bVar = this.a.isEmpty() ? null : this.a.get(0);
            if (!(bVar != null && bVar.a == 0 && bVar.b == 0)) {
                this.a.add(0, new b(0, 0, 0));
            }
            this.a.add(new b(this.e, this.f, 0));
        }

        private void b() {
            for (b bVar : this.a) {
                for (int i = 0; i < bVar.c; i++) {
                    int i2 = bVar.a + i;
                    int i3 = bVar.b + i;
                    int i4 = this.d.areContentsTheSame(i2, i3) ? 1 : 2;
                    this.b[i2] = (i3 << 4) | i4;
                    this.c[i3] = (i2 << 4) | i4;
                }
            }
            if (this.g) {
                c();
            }
        }

        private void c() {
            int i = 0;
            for (b bVar : this.a) {
                while (i < bVar.a) {
                    if (this.b[i] == 0) {
                        a(i);
                    }
                    i++;
                }
                i = bVar.a();
            }
        }

        private void a(int i) {
            int size = this.a.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                b bVar = this.a.get(i3);
                while (i2 < bVar.b) {
                    if (this.c[i2] != 0 || !this.d.areItemsTheSame(i, i2)) {
                        i2++;
                    } else {
                        int i4 = this.d.areContentsTheSame(i, i2) ? 8 : 4;
                        this.b[i] = (i2 << 4) | i4;
                        this.c[i2] = (i << 4) | i4;
                        return;
                    }
                }
                i2 = bVar.b();
            }
        }

        public int convertOldPositionToNew(@IntRange(from = 0) int i) {
            if (i < 0 || i >= this.e) {
                throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + i + ", old list size = " + this.e);
            }
            int i2 = this.b[i];
            if ((i2 & 15) == 0) {
                return -1;
            }
            return i2 >> 4;
        }

        public int convertNewPositionToOld(@IntRange(from = 0) int i) {
            if (i < 0 || i >= this.f) {
                throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + i + ", new list size = " + this.f);
            }
            int i2 = this.c[i];
            if ((i2 & 15) == 0) {
                return -1;
            }
            return i2 >> 4;
        }

        public void dispatchUpdatesTo(@NonNull RecyclerView.Adapter adapter) {
            dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }

        public void dispatchUpdatesTo(@NonNull ListUpdateCallback listUpdateCallback) {
            BatchingListUpdateCallback batchingListUpdateCallback;
            int i;
            if (listUpdateCallback instanceof BatchingListUpdateCallback) {
                batchingListUpdateCallback = (BatchingListUpdateCallback) listUpdateCallback;
            } else {
                batchingListUpdateCallback = new BatchingListUpdateCallback(listUpdateCallback);
            }
            int i2 = this.e;
            ArrayDeque arrayDeque = new ArrayDeque();
            int i3 = this.e;
            int i4 = this.f;
            for (int size = this.a.size() - 1; size >= 0; size--) {
                b bVar = this.a.get(size);
                int a = bVar.a();
                int b = bVar.b();
                while (true) {
                    if (i3 > a) {
                        i3--;
                        int i5 = this.b[i3];
                        if ((i5 & 12) != 0) {
                            int i6 = i5 >> 4;
                            c a2 = a(arrayDeque, i6, false);
                            if (a2 != null) {
                                int i7 = (i2 - a2.b) - 1;
                                batchingListUpdateCallback.onMoved(i3, i7);
                                if ((i5 & 4) != 0) {
                                    batchingListUpdateCallback.onChanged(i7, 1, this.d.getChangePayload(i3, i6));
                                }
                            } else {
                                arrayDeque.add(new c(i3, (i2 - i3) - 1, true));
                            }
                        } else {
                            batchingListUpdateCallback.onRemoved(i3, 1);
                            i2--;
                        }
                    }
                }
                while (i4 > b) {
                    i4--;
                    int i8 = this.c[i4];
                    if ((i8 & 12) != 0) {
                        int i9 = i8 >> 4;
                        c a3 = a(arrayDeque, i9, true);
                        if (a3 == null) {
                            arrayDeque.add(new c(i4, i2 - i3, false));
                        } else {
                            batchingListUpdateCallback.onMoved((i2 - a3.b) - 1, i3);
                            if ((i8 & 4) != 0) {
                                batchingListUpdateCallback.onChanged(i3, 1, this.d.getChangePayload(i9, i4));
                            }
                        }
                    } else {
                        batchingListUpdateCallback.onInserted(i3, 1);
                        i2++;
                    }
                }
                int i10 = bVar.a;
                int i11 = bVar.b;
                for (i = 0; i < bVar.c; i++) {
                    if ((this.b[i10] & 15) == 2) {
                        batchingListUpdateCallback.onChanged(i10, 1, this.d.getChangePayload(i10, i11));
                    }
                    i10++;
                    i11++;
                }
                i3 = bVar.a;
                i4 = bVar.b;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        @Nullable
        private static c a(Collection<c> collection, int i, boolean z) {
            c cVar;
            Iterator<c> it = collection.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cVar = null;
                    break;
                }
                cVar = it.next();
                if (cVar.a == i && cVar.c == z) {
                    it.remove();
                    break;
                }
            }
            while (it.hasNext()) {
                c next = it.next();
                if (z) {
                    next.b--;
                } else {
                    next.b++;
                }
            }
            return cVar;
        }
    }

    /* loaded from: classes.dex */
    public static class c {
        int a;
        int b;
        boolean c;

        c(int i, int i2, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = z;
        }
    }

    /* loaded from: classes.dex */
    public static class a {
        private final int[] a;
        private final int b;

        a(int i) {
            this.a = new int[i];
            this.b = this.a.length / 2;
        }

        int a(int i) {
            return this.a[i + this.b];
        }

        int[] a() {
            return this.a;
        }

        void a(int i, int i2) {
            this.a[i + this.b] = i2;
        }
    }
}
