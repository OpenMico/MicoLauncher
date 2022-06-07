package org.apache.commons.lang.math;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class LongRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892720L;
    private transient Long a;
    private transient Long b;
    private transient int c;
    private transient String d;
    private final long max;
    private final long min;

    public LongRange(long j) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        this.min = j;
        this.max = j;
    }

    public LongRange(Number number) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number != null) {
            this.min = number.longValue();
            this.max = number.longValue();
            if (number instanceof Long) {
                Long l = (Long) number;
                this.a = l;
                this.b = l;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The number must not be null");
    }

    public LongRange(long j, long j2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (j2 < j) {
            this.min = j2;
            this.max = j;
            return;
        }
        this.min = j;
        this.max = j2;
    }

    public LongRange(Number number, Number number2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        long longValue = number.longValue();
        long longValue2 = number2.longValue();
        if (longValue2 < longValue) {
            this.min = longValue2;
            this.max = longValue;
            if (number2 instanceof Long) {
                this.a = (Long) number2;
            }
            if (number instanceof Long) {
                this.b = (Long) number;
                return;
            }
            return;
        }
        this.min = longValue;
        this.max = longValue2;
        if (number instanceof Long) {
            this.a = (Long) number;
        }
        if (number2 instanceof Long) {
            this.b = (Long) number2;
        }
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        if (this.a == null) {
            this.a = new Long(this.min);
        }
        return this.a;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMinimumLong() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMinimumInteger() {
        return (int) this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMinimumDouble() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMinimumFloat() {
        return (float) this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMaximumNumber() {
        if (this.b == null) {
            this.b = new Long(this.max);
        }
        return this.b;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMaximumLong() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMaximumInteger() {
        return (int) this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMaximumDouble() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMaximumFloat() {
        return (float) this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        return containsLong(number.longValue());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsLong(long j) {
        return j >= this.min && j <= this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsRange(Range range) {
        return range != null && containsLong(range.getMinimumLong()) && containsLong(range.getMaximumLong());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean overlapsRange(Range range) {
        if (range == null) {
            return false;
        }
        return range.containsLong(this.min) || range.containsLong(this.max) || containsLong(range.getMinimumLong());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LongRange)) {
            return false;
        }
        LongRange longRange = (LongRange) obj;
        return this.min == longRange.min && this.max == longRange.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.c == 0) {
            this.c = 17;
            this.c = (this.c * 37) + getClass().hashCode();
            long j = this.min;
            this.c = (this.c * 37) + ((int) (j ^ (j >> 32)));
            long j2 = this.max;
            this.c = (this.c * 37) + ((int) (j2 ^ (j2 >> 32)));
        }
        return this.c;
    }

    @Override // org.apache.commons.lang.math.Range
    public String toString() {
        if (this.d == null) {
            StrBuilder strBuilder = new StrBuilder(32);
            strBuilder.append("Range[");
            strBuilder.append(this.min);
            strBuilder.append(StringUtil.COMMA);
            strBuilder.append(this.max);
            strBuilder.append(']');
            this.d = strBuilder.toString();
        }
        return this.d;
    }

    public long[] toArray() {
        long[] jArr = new long[(int) ((this.max - this.min) + 1)];
        for (int i = 0; i < jArr.length; i++) {
            jArr[i] = this.min + i;
        }
        return jArr;
    }
}
