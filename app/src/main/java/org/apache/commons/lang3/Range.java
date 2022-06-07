package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Comparator;

/* loaded from: classes5.dex */
public final class Range<T> implements Serializable {
    private static final long serialVersionUID = 1;
    private transient int a;
    private transient String b;
    private final Comparator<T> comparator;
    private final T maximum;
    private final T minimum;

    /* JADX WARN: Incorrect types in method signature: <T::Ljava/lang/Comparable<TT;>;>(TT;)Lorg/apache/commons/lang3/Range<TT;>; */
    public static Range is(Comparable comparable) {
        return between(comparable, comparable, null);
    }

    public static <T> Range<T> is(T t, Comparator<T> comparator) {
        return between(t, t, comparator);
    }

    /* JADX WARN: Incorrect types in method signature: <T::Ljava/lang/Comparable<TT;>;>(TT;TT;)Lorg/apache/commons/lang3/Range<TT;>; */
    public static Range between(Comparable comparable, Comparable comparable2) {
        return between(comparable, comparable2, null);
    }

    public static <T> Range<T> between(T t, T t2, Comparator<T> comparator) {
        return new Range<>(t, t2, comparator);
    }

    private Range(T t, T t2, Comparator<T> comparator) {
        if (t == null || t2 == null) {
            throw new IllegalArgumentException("Elements in a range must not be null: element1=" + t + ", element2=" + t2);
        }
        if (comparator == null) {
            this.comparator = a.INSTANCE;
        } else {
            this.comparator = comparator;
        }
        if (this.comparator.compare(t, t2) < 1) {
            this.minimum = t;
            this.maximum = t2;
            return;
        }
        this.minimum = t2;
        this.maximum = t;
    }

    public T getMinimum() {
        return this.minimum;
    }

    public T getMaximum() {
        return this.maximum;
    }

    public Comparator<T> getComparator() {
        return this.comparator;
    }

    public boolean isNaturalOrdering() {
        return this.comparator == a.INSTANCE;
    }

    public boolean contains(T t) {
        return t != null && this.comparator.compare(t, this.minimum) > -1 && this.comparator.compare(t, this.maximum) < 1;
    }

    public boolean isAfter(T t) {
        return t != null && this.comparator.compare(t, this.minimum) < 0;
    }

    public boolean isStartedBy(T t) {
        return t != null && this.comparator.compare(t, this.minimum) == 0;
    }

    public boolean isEndedBy(T t) {
        return t != null && this.comparator.compare(t, this.maximum) == 0;
    }

    public boolean isBefore(T t) {
        return t != null && this.comparator.compare(t, this.maximum) > 0;
    }

    public int elementCompareTo(T t) {
        if (t == null) {
            throw new NullPointerException("Element is null");
        } else if (isAfter(t)) {
            return -1;
        } else {
            return isBefore(t) ? 1 : 0;
        }
    }

    public boolean containsRange(Range<T> range) {
        return range != null && contains(range.minimum) && contains(range.maximum);
    }

    public boolean isAfterRange(Range<T> range) {
        if (range == null) {
            return false;
        }
        return isAfter(range.maximum);
    }

    public boolean isOverlappedBy(Range<T> range) {
        if (range == null) {
            return false;
        }
        return range.contains(this.minimum) || range.contains(this.maximum) || contains(range.minimum);
    }

    public boolean isBeforeRange(Range<T> range) {
        if (range == null) {
            return false;
        }
        return isBefore(range.minimum);
    }

    public Range<T> intersectionWith(Range<T> range) {
        if (!isOverlappedBy(range)) {
            throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", range));
        } else if (equals(range)) {
            return this;
        } else {
            return between(getComparator().compare(this.minimum, range.minimum) < 0 ? range.minimum : this.minimum, getComparator().compare(this.maximum, range.maximum) < 0 ? this.maximum : range.maximum, getComparator());
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Range range = (Range) obj;
        return this.minimum.equals(range.minimum) && this.maximum.equals(range.maximum);
    }

    public int hashCode() {
        int i = this.a;
        if (i != 0) {
            return i;
        }
        int hashCode = ((((629 + getClass().hashCode()) * 37) + this.minimum.hashCode()) * 37) + this.maximum.hashCode();
        this.a = hashCode;
        return hashCode;
    }

    public String toString() {
        if (this.b == null) {
            this.b = "[" + this.minimum + ".." + this.maximum + "]";
        }
        return this.b;
    }

    public String toString(String str) {
        return String.format(str, this.minimum, this.maximum, this.comparator);
    }

    /* loaded from: classes5.dex */
    private enum a implements Comparator {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }
}
