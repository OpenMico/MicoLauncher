package org.apache.commons.lang3.tuple;

import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

/* loaded from: classes5.dex */
public abstract class Triple<L, M, R> implements Serializable, Comparable<Triple<L, M, R>> {
    private static final long serialVersionUID = 1;

    public abstract L getLeft();

    public abstract M getMiddle();

    public abstract R getRight();

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((Triple) ((Triple) obj));
    }

    public static <L, M, R> Triple<L, M, R> of(L l, M m, R r) {
        return new ImmutableTriple(l, m, r);
    }

    public int compareTo(Triple<L, M, R> triple) {
        return new CompareToBuilder().append(getLeft(), triple.getLeft()).append(getMiddle(), triple.getMiddle()).append(getRight(), triple.getRight()).toComparison();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Triple)) {
            return false;
        }
        Triple triple = (Triple) obj;
        return ObjectUtils.equals(getLeft(), triple.getLeft()) && ObjectUtils.equals(getMiddle(), triple.getMiddle()) && ObjectUtils.equals(getRight(), triple.getRight());
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (getLeft() == null ? 0 : getLeft().hashCode()) ^ (getMiddle() == null ? 0 : getMiddle().hashCode());
        if (getRight() != null) {
            i = getRight().hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return "(" + getLeft() + Constants.ACCEPT_TIME_SEPARATOR_SP + getMiddle() + Constants.ACCEPT_TIME_SEPARATOR_SP + getRight() + ")";
    }

    public String toString(String str) {
        return String.format(str, getLeft(), getMiddle(), getRight());
    }
}
