package org.apache.commons.lang3.tuple;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

/* loaded from: classes5.dex */
public abstract class Pair<L, R> implements Serializable, Comparable<Pair<L, R>>, Map.Entry<L, R> {
    private static final long serialVersionUID = 4954918890077093841L;

    public abstract L getLeft();

    public abstract R getRight();

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((Pair) ((Pair) obj));
    }

    public static <L, R> Pair<L, R> of(L l, R r) {
        return new ImmutablePair(l, r);
    }

    @Override // java.util.Map.Entry
    public final L getKey() {
        return getLeft();
    }

    @Override // java.util.Map.Entry
    public R getValue() {
        return getRight();
    }

    public int compareTo(Pair<L, R> pair) {
        return new CompareToBuilder().append(getLeft(), pair.getLeft()).append(getRight(), pair.getRight()).toComparison();
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return ObjectUtils.equals(getKey(), entry.getKey()) && ObjectUtils.equals(getValue(), entry.getValue());
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        int i = 0;
        int hashCode = getKey() == null ? 0 : getKey().hashCode();
        if (getValue() != null) {
            i = getValue().hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(getLeft());
        sb.append(StringUtil.COMMA);
        sb.append(getRight());
        sb.append(')');
        return sb.toString();
    }

    public String toString(String str) {
        return String.format(str, getLeft(), getRight());
    }
}
