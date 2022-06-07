package org.apache.commons.lang.math;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class IntRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892730L;
    private transient Integer a;
    private transient Integer b;
    private transient int c;
    private transient String d;
    private final int max;
    private final int min;

    public IntRange(int i) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        this.min = i;
        this.max = i;
    }

    public IntRange(Number number) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number != null) {
            this.min = number.intValue();
            this.max = number.intValue();
            if (number instanceof Integer) {
                Integer num = (Integer) number;
                this.a = num;
                this.b = num;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The number must not be null");
    }

    public IntRange(int i, int i2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (i2 < i) {
            this.min = i2;
            this.max = i;
            return;
        }
        this.min = i;
        this.max = i2;
    }

    public IntRange(Number number, Number number2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        int intValue = number.intValue();
        int intValue2 = number2.intValue();
        if (intValue2 < intValue) {
            this.min = intValue2;
            this.max = intValue;
            if (number2 instanceof Integer) {
                this.a = (Integer) number2;
            }
            if (number instanceof Integer) {
                this.b = (Integer) number;
                return;
            }
            return;
        }
        this.min = intValue;
        this.max = intValue2;
        if (number instanceof Integer) {
            this.a = (Integer) number;
        }
        if (number2 instanceof Integer) {
            this.b = (Integer) number2;
        }
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        if (this.a == null) {
            this.a = new Integer(this.min);
        }
        return this.a;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMinimumLong() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMinimumInteger() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMinimumDouble() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMinimumFloat() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMaximumNumber() {
        if (this.b == null) {
            this.b = new Integer(this.max);
        }
        return this.b;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMaximumLong() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public int getMaximumInteger() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public double getMaximumDouble() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public float getMaximumFloat() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        return containsInteger(number.intValue());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsInteger(int i) {
        return i >= this.min && i <= this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsRange(Range range) {
        return range != null && containsInteger(range.getMinimumInteger()) && containsInteger(range.getMaximumInteger());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean overlapsRange(Range range) {
        if (range == null) {
            return false;
        }
        return range.containsInteger(this.min) || range.containsInteger(this.max) || containsInteger(range.getMinimumInteger());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntRange)) {
            return false;
        }
        IntRange intRange = (IntRange) obj;
        return this.min == intRange.min && this.max == intRange.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.c == 0) {
            this.c = 17;
            this.c = (this.c * 37) + getClass().hashCode();
            this.c = (this.c * 37) + this.min;
            this.c = (this.c * 37) + this.max;
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

    public int[] toArray() {
        int[] iArr = new int[(this.max - this.min) + 1];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = this.min + i;
        }
        return iArr;
    }
}
