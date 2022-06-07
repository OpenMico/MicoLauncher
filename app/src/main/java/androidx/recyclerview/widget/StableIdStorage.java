package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;

/* loaded from: classes.dex */
interface StableIdStorage {

    /* loaded from: classes.dex */
    public interface StableIdLookup {
        long localToGlobal(long j);
    }

    @NonNull
    StableIdLookup createStableIdLookup();

    /* loaded from: classes.dex */
    public static class NoStableIdStorage implements StableIdStorage {
        private final StableIdLookup a = new StableIdLookup() { // from class: androidx.recyclerview.widget.StableIdStorage.NoStableIdStorage.1
            @Override // androidx.recyclerview.widget.StableIdStorage.StableIdLookup
            public long localToGlobal(long j) {
                return -1L;
            }
        };

        @Override // androidx.recyclerview.widget.StableIdStorage
        @NonNull
        public StableIdLookup createStableIdLookup() {
            return this.a;
        }
    }

    /* loaded from: classes.dex */
    public static class SharedPoolStableIdStorage implements StableIdStorage {
        private final StableIdLookup a = new StableIdLookup() { // from class: androidx.recyclerview.widget.StableIdStorage.SharedPoolStableIdStorage.1
            @Override // androidx.recyclerview.widget.StableIdStorage.StableIdLookup
            public long localToGlobal(long j) {
                return j;
            }
        };

        @Override // androidx.recyclerview.widget.StableIdStorage
        @NonNull
        public StableIdLookup createStableIdLookup() {
            return this.a;
        }
    }

    /* loaded from: classes.dex */
    public static class IsolatedStableIdStorage implements StableIdStorage {
        long a = 0;

        long a() {
            long j = this.a;
            this.a = 1 + j;
            return j;
        }

        @Override // androidx.recyclerview.widget.StableIdStorage
        @NonNull
        public StableIdLookup createStableIdLookup() {
            return new a();
        }

        /* loaded from: classes.dex */
        class a implements StableIdLookup {
            private final LongSparseArray<Long> b = new LongSparseArray<>();

            a() {
            }

            @Override // androidx.recyclerview.widget.StableIdStorage.StableIdLookup
            public long localToGlobal(long j) {
                Long l = this.b.get(j);
                if (l == null) {
                    l = Long.valueOf(IsolatedStableIdStorage.this.a());
                    this.b.put(j, l);
                }
                return l.longValue();
            }
        }
    }
}
