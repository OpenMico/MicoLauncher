package org.apache.commons.lang.math;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class NumberRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892710L;
    private transient int a = 0;
    private transient String b = null;
    private final Number max;
    private final Number min;

    public NumberRange(Number number) {
        if (number == null) {
            throw new IllegalArgumentException("The number must not be null");
        } else if (!(number instanceof Comparable)) {
            throw new IllegalArgumentException("The number must implement Comparable");
        } else if ((number instanceof Double) && ((Double) number).isNaN()) {
            throw new IllegalArgumentException("The number must not be NaN");
        } else if (!(number instanceof Float) || !((Float) number).isNaN()) {
            this.min = number;
            this.max = number;
        } else {
            throw new IllegalArgumentException("The number must not be NaN");
        }
    }

    public NumberRange(Number number, Number number2) {
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        } else if (number.getClass() != number2.getClass()) {
            throw new IllegalArgumentException("The numbers must be of the same type");
        } else if (number instanceof Comparable) {
            if (number instanceof Double) {
                if (((Double) number).isNaN() || ((Double) number2).isNaN()) {
                    throw new IllegalArgumentException("The number must not be NaN");
                }
            } else if ((number instanceof Float) && (((Float) number).isNaN() || ((Float) number2).isNaN())) {
                throw new IllegalArgumentException("The number must not be NaN");
            }
            int compareTo = ((Comparable) number).compareTo(number2);
            if (compareTo == 0) {
                this.min = number;
                this.max = number;
            } else if (compareTo > 0) {
                this.min = number2;
                this.max = number;
            } else {
                this.min = number;
                this.max = number2;
            }
        } else {
            throw new IllegalArgumentException("The numbers must implement Comparable");
        }
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMaximumNumber() {
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        if (number.getClass() == this.min.getClass()) {
            return ((Comparable) this.min).compareTo(number) <= 0 && ((Comparable) this.max).compareTo(number) >= 0;
        }
        throw new IllegalArgumentException("The number must be of the same type as the range numbers");
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumberRange)) {
            return false;
        }
        NumberRange numberRange = (NumberRange) obj;
        return this.min.equals(numberRange.min) && this.max.equals(numberRange.max);
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.a == 0) {
            this.a = 17;
            this.a = (this.a * 37) + getClass().hashCode();
            this.a = (this.a * 37) + this.min.hashCode();
            this.a = (this.a * 37) + this.max.hashCode();
        }
        return this.a;
    }

    @Override // org.apache.commons.lang.math.Range
    public String toString() {
        if (this.b == null) {
            StrBuilder strBuilder = new StrBuilder(32);
            strBuilder.append("Range[");
            strBuilder.append(this.min);
            strBuilder.append(StringUtil.COMMA);
            strBuilder.append(this.max);
            strBuilder.append(']');
            this.b = strBuilder.toString();
        }
        return this.b;
    }
}
