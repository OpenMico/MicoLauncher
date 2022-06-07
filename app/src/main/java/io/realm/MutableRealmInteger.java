package io.realm;

import io.realm.internal.ManageableObject;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public abstract class MutableRealmInteger implements ManageableObject, Comparable<MutableRealmInteger> {
    public abstract void decrement(long j);

    @Nullable
    public abstract Long get();

    public abstract void increment(long j);

    public abstract void set(@Nullable Long l);

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class a extends MutableRealmInteger {
        @Nullable
        private Long a;

        @Override // io.realm.internal.ManageableObject
        public boolean isFrozen() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isManaged() {
            return false;
        }

        @Override // io.realm.internal.ManageableObject
        public boolean isValid() {
            return true;
        }

        @Override // io.realm.MutableRealmInteger, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(MutableRealmInteger mutableRealmInteger) {
            return MutableRealmInteger.super.compareTo(mutableRealmInteger);
        }

        a(@Nullable Long l) {
            this.a = l;
        }

        @Override // io.realm.MutableRealmInteger
        public void set(@Nullable Long l) {
            this.a = l;
        }

        @Override // io.realm.MutableRealmInteger
        @Nullable
        public Long get() {
            return this.a;
        }

        @Override // io.realm.MutableRealmInteger
        public void increment(long j) {
            Long l = this.a;
            if (l != null) {
                this.a = Long.valueOf(l.longValue() + j);
                return;
            }
            throw new IllegalStateException("Cannot increment a MutableRealmInteger whose value is null. Set its value first.");
        }

        @Override // io.realm.MutableRealmInteger
        public void decrement(long j) {
            increment(-j);
        }
    }

    public static MutableRealmInteger valueOf(Long l) {
        return new a(l);
    }

    public static MutableRealmInteger ofNull() {
        return new a(null);
    }

    public static MutableRealmInteger valueOf(long j) {
        return valueOf(Long.valueOf(j));
    }

    public static MutableRealmInteger valueOf(String str) {
        return valueOf(Long.parseLong(str));
    }

    MutableRealmInteger() {
    }

    public final void set(long j) {
        set(Long.valueOf(j));
    }

    public final boolean isNull() {
        return get() == null;
    }

    public final int compareTo(MutableRealmInteger mutableRealmInteger) {
        Long l = get();
        Long l2 = mutableRealmInteger.get();
        if (l == null) {
            return l2 == null ? 0 : -1;
        }
        if (l2 == null) {
            return 1;
        }
        return l.compareTo(l2);
    }

    public final int hashCode() {
        Long l = get();
        if (l == null) {
            return 0;
        }
        return l.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MutableRealmInteger)) {
            return false;
        }
        Long l = get();
        Long l2 = ((MutableRealmInteger) obj).get();
        if (l == null) {
            return l2 == null;
        }
        return l.equals(l2);
    }
}
