package com.google.android.exoplayer2.util;

import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public final class FlagSet {
    private final SparseBooleanArray a;

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final SparseBooleanArray a = new SparseBooleanArray();
        private boolean b;

        public Builder add(int i) {
            Assertions.checkState(!this.b);
            this.a.append(i, true);
            return this;
        }

        public Builder addIf(int i, boolean z) {
            return z ? add(i) : this;
        }

        public Builder addAll(int... iArr) {
            for (int i : iArr) {
                add(i);
            }
            return this;
        }

        public Builder addAll(FlagSet flagSet) {
            for (int i = 0; i < flagSet.size(); i++) {
                add(flagSet.get(i));
            }
            return this;
        }

        public Builder remove(int i) {
            Assertions.checkState(!this.b);
            this.a.delete(i);
            return this;
        }

        public Builder removeIf(int i, boolean z) {
            return z ? remove(i) : this;
        }

        public Builder removeAll(int... iArr) {
            for (int i : iArr) {
                remove(i);
            }
            return this;
        }

        public FlagSet build() {
            Assertions.checkState(!this.b);
            this.b = true;
            return new FlagSet(this.a);
        }
    }

    private FlagSet(SparseBooleanArray sparseBooleanArray) {
        this.a = sparseBooleanArray;
    }

    public boolean contains(int i) {
        return this.a.get(i);
    }

    public boolean containsAny(int... iArr) {
        for (int i : iArr) {
            if (contains(i)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.a.size();
    }

    public int get(int i) {
        Assertions.checkIndex(i, 0, size());
        return this.a.keyAt(i);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlagSet)) {
            return false;
        }
        FlagSet flagSet = (FlagSet) obj;
        if (Util.SDK_INT >= 24) {
            return this.a.equals(flagSet.a);
        }
        if (size() != flagSet.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (get(i) != flagSet.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        if (Util.SDK_INT >= 24) {
            return this.a.hashCode();
        }
        int size = size();
        for (int i = 0; i < size(); i++) {
            size = (size * 31) + get(i);
        }
        return size;
    }
}
