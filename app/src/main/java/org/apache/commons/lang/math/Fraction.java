package org.apache.commons.lang.math;

import com.fasterxml.jackson.core.JsonPointer;
import com.xiaomi.miplay.mylibrary.DataModel;
import java.math.BigInteger;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class Fraction extends Number implements Comparable {
    private static final long serialVersionUID = 65382027393090L;
    private transient int a = 0;
    private transient String b = null;
    private transient String c = null;
    private final int denominator;
    private final int numerator;
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ONE_HALF = new Fraction(1, 2);
    public static final Fraction ONE_THIRD = new Fraction(1, 3);
    public static final Fraction TWO_THIRDS = new Fraction(2, 3);
    public static final Fraction ONE_QUARTER = new Fraction(1, 4);
    public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
    public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
    public static final Fraction ONE_FIFTH = new Fraction(1, 5);
    public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
    public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
    public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);

    private Fraction(int i, int i2) {
        this.numerator = i;
        this.denominator = i2;
    }

    public static Fraction getFraction(int i, int i2) {
        if (i2 != 0) {
            if (i2 < 0) {
                if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                    throw new ArithmeticException("overflow: can't negate");
                }
                i = -i;
                i2 = -i2;
            }
            return new Fraction(i, i2);
        }
        throw new ArithmeticException("The denominator must not be zero");
    }

    public static Fraction getFraction(int i, int i2, int i3) {
        if (i3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        } else if (i3 < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        } else if (i2 >= 0) {
            long j = i < 0 ? (i * i3) - i2 : (i * i3) + i2;
            if (j >= -2147483648L && j <= 2147483647L) {
                return new Fraction((int) j, i3);
            }
            throw new ArithmeticException("Numerator too large to represent as an Integer.");
        } else {
            throw new ArithmeticException("The numerator must not be negative");
        }
    }

    public static Fraction getReducedFraction(int i, int i2) {
        if (i2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        } else if (i == 0) {
            return ZERO;
        } else {
            if (i2 == Integer.MIN_VALUE && (i & 1) == 0) {
                i /= 2;
                i2 /= 2;
            }
            if (i2 < 0) {
                if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                    throw new ArithmeticException("overflow: can't negate");
                }
                i = -i;
                i2 = -i2;
            }
            int a = a(i, i2);
            return new Fraction(i / a, i2 / a);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0074, code lost:
        return getReducedFraction((r12 + (r4 * r14)) * r0, r14);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.apache.commons.lang.math.Fraction getFraction(double r19) {
        /*
            r0 = 0
            int r0 = (r19 > r0 ? 1 : (r19 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0008
            r0 = -1
            goto L_0x0009
        L_0x0008:
            r0 = 1
        L_0x0009:
            double r2 = java.lang.Math.abs(r19)
            r4 = 4746794007244308480(0x41dfffffffc00000, double:2.147483647E9)
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x007d
            boolean r4 = java.lang.Double.isNaN(r2)
            if (r4 != 0) goto L_0x007d
            int r4 = (int) r2
            double r5 = (double) r4
            double r2 = r2 - r5
            int r5 = (int) r2
            r6 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r8 = (double) r5
            double r8 = r2 - r8
            r10 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
            r12 = 0
            r19 = r2
            r13 = r12
            r14 = r13
            r12 = 1
            r15 = 1
            r16 = 1
        L_0x0033:
            double r1 = r6 / r8
            int r1 = (int) r1
            double r2 = (double) r1
            double r2 = r2 * r8
            double r2 = r6 - r2
            int r6 = r5 * r12
            int r6 = r6 + r13
            int r5 = r5 * r14
            int r5 = r5 + r15
            r7 = r1
            r17 = r2
            double r1 = (double) r6
            r3 = r6
            r13 = r7
            double r6 = (double) r5
            double r1 = r1 / r6
            double r1 = r19 - r1
            double r1 = java.lang.Math.abs(r1)
            r6 = 1
            int r7 = r16 + 1
            int r10 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            r11 = 25
            if (r10 <= 0) goto L_0x006b
            r10 = 10000(0x2710, float:1.4013E-41)
            if (r5 > r10) goto L_0x006b
            if (r5 <= 0) goto L_0x006b
            if (r7 < r11) goto L_0x005f
            goto L_0x006b
        L_0x005f:
            r10 = r1
            r16 = r7
            r6 = r8
            r15 = r14
            r8 = r17
            r14 = r5
            r5 = r13
            r13 = r12
            r12 = r3
            goto L_0x0033
        L_0x006b:
            if (r7 == r11) goto L_0x0075
            int r4 = r4 * r14
            int r12 = r12 + r4
            int r12 = r12 * r0
            org.apache.commons.lang.math.Fraction r0 = getReducedFraction(r12, r14)
            return r0
        L_0x0075:
            java.lang.ArithmeticException r0 = new java.lang.ArithmeticException
            java.lang.String r1 = "Unable to convert double to fraction"
            r0.<init>(r1)
            throw r0
        L_0x007d:
            java.lang.ArithmeticException r0 = new java.lang.ArithmeticException
            java.lang.String r1 = "The value must not be greater than Integer.MAX_VALUE or NaN"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.math.Fraction.getFraction(double):org.apache.commons.lang.math.Fraction");
    }

    public static Fraction getFraction(String str) {
        if (str == null) {
            throw new IllegalArgumentException("The string must not be null");
        } else if (str.indexOf(46) >= 0) {
            return getFraction(Double.parseDouble(str));
        } else {
            int indexOf = str.indexOf(32);
            if (indexOf > 0) {
                int parseInt = Integer.parseInt(str.substring(0, indexOf));
                String substring = str.substring(indexOf + 1);
                int indexOf2 = substring.indexOf(47);
                if (indexOf2 >= 0) {
                    return getFraction(parseInt, Integer.parseInt(substring.substring(0, indexOf2)), Integer.parseInt(substring.substring(indexOf2 + 1)));
                }
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            }
            int indexOf3 = str.indexOf(47);
            if (indexOf3 < 0) {
                return getFraction(Integer.parseInt(str), 1);
            }
            return getFraction(Integer.parseInt(str.substring(0, indexOf3)), Integer.parseInt(str.substring(indexOf3 + 1)));
        }
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getProperNumerator() {
        return Math.abs(this.numerator % this.denominator);
    }

    public int getProperWhole() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.numerator / this.denominator;
    }

    public Fraction reduce() {
        int i = this.numerator;
        if (i == 0) {
            return equals(ZERO) ? this : ZERO;
        }
        int a = a(Math.abs(i), this.denominator);
        return a == 1 ? this : getFraction(this.numerator / a, this.denominator / a);
    }

    public Fraction invert() {
        int i = this.numerator;
        if (i == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        } else if (i == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: can't negate numerator");
        } else if (i < 0) {
            return new Fraction(-this.denominator, -i);
        } else {
            return new Fraction(this.denominator, i);
        }
    }

    public Fraction negate() {
        int i = this.numerator;
        if (i != Integer.MIN_VALUE) {
            return new Fraction(-i, this.denominator);
        }
        throw new ArithmeticException("overflow: too large to negate");
    }

    public Fraction abs() {
        return this.numerator >= 0 ? this : negate();
    }

    public Fraction pow(int i) {
        if (i == 1) {
            return this;
        }
        if (i == 0) {
            return ONE;
        }
        if (i >= 0) {
            Fraction multiplyBy = multiplyBy(this);
            if (i % 2 == 0) {
                return multiplyBy.pow(i / 2);
            }
            return multiplyBy.pow(i / 2).multiplyBy(this);
        } else if (i == Integer.MIN_VALUE) {
            return invert().pow(2).pow(-(i / 2));
        } else {
            return invert().pow(-i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x002a, code lost:
        if (r2 != 1) goto L_0x002e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002c, code lost:
        r2 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002e, code lost:
        r2 = -(r5 / 2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0033, code lost:
        if ((r2 & 1) != 0) goto L_0x0038;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0035, code lost:
        r2 = r2 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0038, code lost:
        if (r2 <= 0) goto L_0x003c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003a, code lost:
        r5 = -r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003c, code lost:
        r6 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003d, code lost:
        r2 = (r6 - r5) / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0041, code lost:
        if (r2 != 0) goto L_0x0031;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0047, code lost:
        return (-r5) * (1 << r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(int r5, int r6) {
        /*
            int r0 = java.lang.Math.abs(r5)
            r1 = 1
            if (r0 <= r1) goto L_0x0050
            int r0 = java.lang.Math.abs(r6)
            if (r0 > r1) goto L_0x000e
            goto L_0x0050
        L_0x000e:
            if (r5 <= 0) goto L_0x0011
            int r5 = -r5
        L_0x0011:
            if (r6 <= 0) goto L_0x0014
            int r6 = -r6
        L_0x0014:
            r0 = 0
        L_0x0015:
            r2 = r5 & 1
            r3 = 31
            if (r2 != 0) goto L_0x0028
            r4 = r6 & 1
            if (r4 != 0) goto L_0x0028
            if (r0 >= r3) goto L_0x0028
            int r5 = r5 / 2
            int r6 = r6 / 2
            int r0 = r0 + 1
            goto L_0x0015
        L_0x0028:
            if (r0 == r3) goto L_0x0048
            if (r2 != r1) goto L_0x002e
            r2 = r6
            goto L_0x0031
        L_0x002e:
            int r2 = r5 / 2
            int r2 = -r2
        L_0x0031:
            r3 = r2 & 1
            if (r3 != 0) goto L_0x0038
            int r2 = r2 / 2
            goto L_0x0031
        L_0x0038:
            if (r2 <= 0) goto L_0x003c
            int r5 = -r2
            goto L_0x003d
        L_0x003c:
            r6 = r2
        L_0x003d:
            int r2 = r6 - r5
            int r2 = r2 / 2
            if (r2 != 0) goto L_0x0031
            int r5 = -r5
            int r6 = r1 << r0
            int r5 = r5 * r6
            return r5
        L_0x0048:
            java.lang.ArithmeticException r5 = new java.lang.ArithmeticException
            java.lang.String r6 = "overflow: gcd is 2^31"
            r5.<init>(r6)
            throw r5
        L_0x0050:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.math.Fraction.a(int, int):int");
    }

    private static int b(int i, int i2) {
        long j = i * i2;
        if (j >= -2147483648L && j <= 2147483647L) {
            return (int) j;
        }
        throw new ArithmeticException("overflow: mul");
    }

    private static int c(int i, int i2) {
        long j = i * i2;
        if (j <= 2147483647L) {
            return (int) j;
        }
        throw new ArithmeticException("overflow: mulPos");
    }

    private static int d(int i, int i2) {
        long j = i + i2;
        if (j >= -2147483648L && j <= 2147483647L) {
            return (int) j;
        }
        throw new ArithmeticException("overflow: add");
    }

    private static int e(int i, int i2) {
        long j = i - i2;
        if (j >= -2147483648L && j <= 2147483647L) {
            return (int) j;
        }
        throw new ArithmeticException("overflow: add");
    }

    public Fraction add(Fraction fraction) {
        return a(fraction, true);
    }

    public Fraction subtract(Fraction fraction) {
        return a(fraction, false);
    }

    private Fraction a(Fraction fraction, boolean z) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        } else if (this.numerator == 0) {
            return z ? fraction : fraction.negate();
        } else {
            if (fraction.numerator == 0) {
                return this;
            }
            int a = a(this.denominator, fraction.denominator);
            if (a == 1) {
                int b = b(this.numerator, fraction.denominator);
                int b2 = b(fraction.numerator, this.denominator);
                return new Fraction(z ? d(b, b2) : e(b, b2), c(this.denominator, fraction.denominator));
            }
            BigInteger multiply = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(fraction.denominator / a));
            BigInteger multiply2 = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(this.denominator / a));
            BigInteger add = z ? multiply.add(multiply2) : multiply.subtract(multiply2);
            int intValue = add.mod(BigInteger.valueOf(a)).intValue();
            int a2 = intValue == 0 ? a : a(intValue, a);
            BigInteger divide = add.divide(BigInteger.valueOf(a2));
            if (divide.bitLength() <= 31) {
                return new Fraction(divide.intValue(), c(this.denominator / a, fraction.denominator / a2));
            }
            throw new ArithmeticException("overflow: numerator too large after multiply");
        }
    }

    public Fraction multiplyBy(Fraction fraction) {
        if (fraction != null) {
            int i = this.numerator;
            if (i == 0 || fraction.numerator == 0) {
                return ZERO;
            }
            int a = a(i, fraction.denominator);
            int a2 = a(fraction.numerator, this.denominator);
            return getReducedFraction(b(this.numerator / a, fraction.numerator / a2), c(this.denominator / a2, fraction.denominator / a));
        }
        throw new IllegalArgumentException("The fraction must not be null");
    }

    public Fraction divideBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        } else if (fraction.numerator != 0) {
            return multiplyBy(fraction.invert());
        } else {
            throw new ArithmeticException("The fraction to divide by must not be zero");
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction fraction = (Fraction) obj;
        return getNumerator() == fraction.getNumerator() && getDenominator() == fraction.getDenominator();
    }

    public int hashCode() {
        if (this.a == 0) {
            this.a = ((getNumerator() + 629) * 37) + getDenominator();
        }
        return this.a;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        int i;
        Fraction fraction = (Fraction) obj;
        if (this == fraction) {
            return 0;
        }
        if ((this.numerator == fraction.numerator && this.denominator == fraction.denominator) || this.numerator * fraction.denominator == fraction.numerator * this.denominator) {
            return 0;
        }
        return i < 0 ? -1 : 1;
    }

    public String toString() {
        if (this.b == null) {
            this.b = new StrBuilder(32).append(getNumerator()).append(JsonPointer.SEPARATOR).append(getDenominator()).toString();
        }
        return this.b;
    }

    public String toProperString() {
        if (this.c == null) {
            int i = this.numerator;
            if (i == 0) {
                this.c = "0";
            } else {
                int i2 = this.denominator;
                if (i == i2) {
                    this.c = "1";
                } else if (i == i2 * (-1)) {
                    this.c = DataModel.CIRCULATEFAIL_NO_SUPPORT;
                } else {
                    if (i > 0) {
                        i = -i;
                    }
                    if (i < (-this.denominator)) {
                        int properNumerator = getProperNumerator();
                        if (properNumerator == 0) {
                            this.c = Integer.toString(getProperWhole());
                        } else {
                            this.c = new StrBuilder(32).append(getProperWhole()).append(' ').append(properNumerator).append(JsonPointer.SEPARATOR).append(getDenominator()).toString();
                        }
                    } else {
                        this.c = new StrBuilder(32).append(getNumerator()).append(JsonPointer.SEPARATOR).append(getDenominator()).toString();
                    }
                }
            }
        }
        return this.c;
    }
}
