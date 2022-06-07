package org.apache.commons.lang.math;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class DoubleRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892740L;
    private transient Double a;
    private transient Double b;
    private transient int c;
    private transient String d;
    private final double max;
    private final double min;

    public DoubleRange(double d) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (!Double.isNaN(d)) {
            this.min = d;
            this.max = d;
            return;
        }
        throw new IllegalArgumentException("The number must not be NaN");
    }

    public DoubleRange(Number number) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number != null) {
            this.min = number.doubleValue();
            this.max = number.doubleValue();
            if (Double.isNaN(this.min) || Double.isNaN(this.max)) {
                throw new IllegalArgumentException("The number must not be NaN");
            } else if (number instanceof Double) {
                Double d = (Double) number;
                this.a = d;
                this.b = d;
            }
        } else {
            throw new IllegalArgumentException("The number must not be null");
        }
    }

    public DoubleRange(double d, double d2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (Double.isNaN(d) || Double.isNaN(d2)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        } else if (d2 < d) {
            this.min = d2;
            this.max = d;
        } else {
            this.min = d;
            this.max = d2;
        }
    }

    public DoubleRange(Number number, Number number2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        double doubleValue = number.doubleValue();
        double doubleValue2 = number2.doubleValue();
        if (Double.isNaN(doubleValue) || Double.isNaN(doubleValue2)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        } else if (doubleValue2 < doubleValue) {
            this.min = doubleValue2;
            this.max = doubleValue;
            if (number2 instanceof Double) {
                this.a = (Double) number2;
            }
            if (number instanceof Double) {
                this.b = (Double) number;
            }
        } else {
            this.min = doubleValue;
            this.max = doubleValue2;
            if (number instanceof Double) {
                this.a = (Double) number;
            }
            if (number2 instanceof Double) {
                this.b = (Double) number2;
            }
        }
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        if (this.a == null) {
            this.a = new Double(this.min);
        }
        return this.a;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMinimumLong() {
        return (long) this.min;
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
            this.b = new Double(this.max);
        }
        return this.b;
    }

    @Override // org.apache.commons.lang.math.Range
    public long getMaximumLong() {
        return (long) this.max;
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
        return containsDouble(number.doubleValue());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsDouble(double d) {
        return d >= this.min && d <= this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsRange(Range range) {
        return range != null && containsDouble(range.getMinimumDouble()) && containsDouble(range.getMaximumDouble());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean overlapsRange(Range range) {
        if (range == null) {
            return false;
        }
        return range.containsDouble(this.min) || range.containsDouble(this.max) || containsDouble(range.getMinimumDouble());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoubleRange)) {
            return false;
        }
        DoubleRange doubleRange = (DoubleRange) obj;
        return Double.doubleToLongBits(this.min) == Double.doubleToLongBits(doubleRange.min) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(doubleRange.max);
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.c == 0) {
            this.c = 17;
            this.c = (this.c * 37) + getClass().hashCode();
            long doubleToLongBits = Double.doubleToLongBits(this.min);
            this.c = (this.c * 37) + ((int) (doubleToLongBits ^ (doubleToLongBits >> 32)));
            long doubleToLongBits2 = Double.doubleToLongBits(this.max);
            this.c = (this.c * 37) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >> 32)));
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
}
