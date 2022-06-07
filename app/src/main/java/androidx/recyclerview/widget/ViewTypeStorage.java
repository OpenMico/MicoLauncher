package androidx.recyclerview.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface ViewTypeStorage {

    /* loaded from: classes.dex */
    public interface ViewTypeLookup {
        void dispose();

        int globalToLocal(int i);

        int localToGlobal(int i);
    }

    @NonNull
    ViewTypeLookup createViewTypeWrapper(@NonNull i iVar);

    @NonNull
    i getWrapperForGlobalType(int i);

    /* loaded from: classes.dex */
    public static class SharedIdRangeViewTypeStorage implements ViewTypeStorage {
        SparseArray<List<i>> a = new SparseArray<>();

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public i getWrapperForGlobalType(int i) {
            List<i> list = this.a.get(i);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
            throw new IllegalArgumentException("Cannot find the wrapper for global view type " + i);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull i iVar) {
            return new a(iVar);
        }

        void a(@NonNull i iVar) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                List<i> valueAt = this.a.valueAt(size);
                if (valueAt.remove(iVar) && valueAt.isEmpty()) {
                    this.a.removeAt(size);
                }
            }
        }

        /* loaded from: classes.dex */
        class a implements ViewTypeLookup {
            final i a;

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int i) {
                return i;
            }

            a(i iVar) {
                SharedIdRangeViewTypeStorage.this = r1;
                this.a = iVar;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int i) {
                List<i> list = SharedIdRangeViewTypeStorage.this.a.get(i);
                if (list == null) {
                    list = new ArrayList<>();
                    SharedIdRangeViewTypeStorage.this.a.put(i, list);
                }
                if (!list.contains(this.a)) {
                    list.add(this.a);
                }
                return i;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                SharedIdRangeViewTypeStorage.this.a(this.a);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class IsolatedViewTypeStorage implements ViewTypeStorage {
        SparseArray<i> a = new SparseArray<>();
        int b = 0;

        int a(i iVar) {
            int i = this.b;
            this.b = i + 1;
            this.a.put(i, iVar);
            return i;
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public i getWrapperForGlobalType(int i) {
            i iVar = this.a.get(i);
            if (iVar != null) {
                return iVar;
            }
            throw new IllegalArgumentException("Cannot find the wrapper for global view type " + i);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull i iVar) {
            return new a(iVar);
        }

        void b(@NonNull i iVar) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                if (this.a.valueAt(size) == iVar) {
                    this.a.removeAt(size);
                }
            }
        }

        /* loaded from: classes.dex */
        class a implements ViewTypeLookup {
            final i a;
            private SparseIntArray c = new SparseIntArray(1);
            private SparseIntArray d = new SparseIntArray(1);

            a(i iVar) {
                IsolatedViewTypeStorage.this = r2;
                this.a = iVar;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int i) {
                int indexOfKey = this.c.indexOfKey(i);
                if (indexOfKey > -1) {
                    return this.c.valueAt(indexOfKey);
                }
                int a = IsolatedViewTypeStorage.this.a(this.a);
                this.c.put(i, a);
                this.d.put(a, i);
                return a;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int i) {
                int indexOfKey = this.d.indexOfKey(i);
                if (indexOfKey >= 0) {
                    return this.d.valueAt(indexOfKey);
                }
                throw new IllegalStateException("requested global type " + i + " does not belong to the adapter:" + this.a.a);
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                IsolatedViewTypeStorage.this.b(this.a);
            }
        }
    }
}
