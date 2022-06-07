package org.apache.commons.lang3.builder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class DiffResult implements Iterable<Diff<?>> {
    public static final String OBJECTS_SAME_STRING = "";
    private final List<Diff<?>> a;
    private final Object b;
    private final Object c;
    private final ToStringStyle d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DiffResult(Object obj, Object obj2, List<Diff<?>> list, ToStringStyle toStringStyle) {
        if (obj == null) {
            throw new IllegalArgumentException("Left hand object cannot be null");
        } else if (obj2 == null) {
            throw new IllegalArgumentException("Right hand object cannot be null");
        } else if (list != null) {
            this.a = list;
            this.b = obj;
            this.c = obj2;
            if (toStringStyle == null) {
                this.d = ToStringStyle.DEFAULT_STYLE;
            } else {
                this.d = toStringStyle;
            }
        } else {
            throw new IllegalArgumentException("List of differences cannot be null");
        }
    }

    public List<Diff<?>> getDiffs() {
        return Collections.unmodifiableList(this.a);
    }

    public int getNumberOfDiffs() {
        return this.a.size();
    }

    public ToStringStyle getToStringStyle() {
        return this.d;
    }

    public String toString() {
        return toString(this.d);
    }

    public String toString(ToStringStyle toStringStyle) {
        if (this.a.size() == 0) {
            return "";
        }
        ToStringBuilder toStringBuilder = new ToStringBuilder(this.b, toStringStyle);
        ToStringBuilder toStringBuilder2 = new ToStringBuilder(this.c, toStringStyle);
        for (Diff<?> diff : this.a) {
            toStringBuilder.append(diff.getFieldName(), diff.getLeft());
            toStringBuilder2.append(diff.getFieldName(), diff.getRight());
        }
        return String.format("%s %s %s", toStringBuilder.build(), "differs from", toStringBuilder2.build());
    }

    @Override // java.lang.Iterable
    public Iterator<Diff<?>> iterator() {
        return this.a.iterator();
    }
}
