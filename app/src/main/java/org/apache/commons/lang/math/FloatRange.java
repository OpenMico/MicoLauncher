package org.apache.commons.lang.math;

import io.netty.util.internal.StringUtil;
import java.io.Serializable;

/* loaded from: classes5.dex */
public final class FloatRange extends Range implements Serializable {
    private static final long serialVersionUID = 71849363892750L;
    private transient Float a;
    private transient Float b;
    private transient int c;
    private transient String d;
    private final float max;
    private final float min;

    public FloatRange(float f) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (!Float.isNaN(f)) {
            this.min = f;
            this.max = f;
            return;
        }
        throw new IllegalArgumentException("The number must not be NaN");
    }

    public FloatRange(Number number) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number != null) {
            this.min = number.floatValue();
            this.max = number.floatValue();
            if (Float.isNaN(this.min) || Float.isNaN(this.max)) {
                throw new IllegalArgumentException("The number must not be NaN");
            } else if (number instanceof Float) {
                Float f = (Float) number;
                this.a = f;
                this.b = f;
            }
        } else {
            throw new IllegalArgumentException("The number must not be null");
        }
    }

    public FloatRange(float f, float f2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        } else if (f2 < f) {
            this.min = f2;
            this.max = f;
        } else {
            this.min = f;
            this.max = f2;
        }
    }

    public FloatRange(Number number, Number number2) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        float floatValue = number.floatValue();
        float floatValue2 = number2.floatValue();
        if (Float.isNaN(floatValue) || Float.isNaN(floatValue2)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        } else if (floatValue2 < floatValue) {
            this.min = floatValue2;
            this.max = floatValue;
            if (number2 instanceof Float) {
                this.a = (Float) number2;
            }
            if (number instanceof Float) {
                this.b = (Float) number;
            }
        } else {
            this.min = floatValue;
            this.max = floatValue2;
            if (number instanceof Float) {
                this.a = (Float) number;
            }
            if (number2 instanceof Float) {
                this.b = (Float) number2;
            }
        }
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMinimumNumber() {
        if (this.a == null) {
            this.a = new Float(this.min);
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
        return this.min;
    }

    @Override // org.apache.commons.lang.math.Range
    public Number getMaximumNumber() {
        if (this.b == null) {
            this.b = new Float(this.max);
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
        return this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsNumber(Number number) {
        if (number == null) {
            return false;
        }
        return containsFloat(number.floatValue());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsFloat(float f) {
        return f >= this.min && f <= this.max;
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean containsRange(Range range) {
        return range != null && containsFloat(range.getMinimumFloat()) && containsFloat(range.getMaximumFloat());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean overlapsRange(Range range) {
        if (range == null) {
            return false;
        }
        return range.containsFloat(this.min) || range.containsFloat(this.max) || containsFloat(range.getMinimumFloat());
    }

    @Override // org.apache.commons.lang.math.Range
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FloatRange)) {
            return false;
        }
        FloatRange floatRange = (FloatRange) obj;
        return Float.floatToIntBits(this.min) == Float.floatToIntBits(floatRange.min) && Float.floatToIntBits(this.max) == Float.floatToIntBits(floatRange.max);
    }

    @Override // org.apache.commons.lang.math.Range
    public int hashCode() {
        if (this.c == 0) {
            this.c = 17;
            this.c = (this.c * 37) + getClass().hashCode();
            this.c = (this.c * 37) + Float.floatToIntBits(this.min);
            this.c = (this.c * 37) + Float.floatToIntBits(this.max);
        }
        return this.c;
    }

    @Override // org.apache.commons.lang.math.Range
    public String toString() {
        if (this.d == null) {
            StringBuffer stringBuffer = new StringBuffer(32);
            stringBuffer.append("Range[");
            stringBuffer.append(this.min);
            stringBuffer.append(StringUtil.COMMA);
            stringBuffer.append(this.max);
            stringBuffer.append(']');
            this.d = stringBuffer.toString();
        }
        return this.d;
    }
}
