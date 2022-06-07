package org.apache.commons.lang3.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/* loaded from: classes5.dex */
public class DiffBuilder implements Builder<DiffResult> {
    private final List<Diff<?>> a;
    private final boolean b;
    private final Object c;
    private final Object d;
    private final ToStringStyle e;

    public DiffBuilder(Object obj, Object obj2, ToStringStyle toStringStyle, boolean z) {
        if (obj == null) {
            throw new IllegalArgumentException("lhs cannot be null");
        } else if (obj2 != null) {
            this.a = new ArrayList();
            this.c = obj;
            this.d = obj2;
            this.e = toStringStyle;
            this.b = z && (obj == obj2 || obj.equals(obj2));
        } else {
            throw new IllegalArgumentException("rhs cannot be null");
        }
    }

    public DiffBuilder(Object obj, Object obj2, ToStringStyle toStringStyle) {
        this(obj, obj2, toStringStyle, true);
    }

    public DiffBuilder append(String str, final boolean z, final boolean z2) {
        if (str != null) {
            if (!this.b && z != z2) {
                this.a.add(new Diff<Boolean>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.1
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Boolean getLeft() {
                        return Boolean.valueOf(z);
                    }

                    /* renamed from: b */
                    public Boolean getRight() {
                        return Boolean.valueOf(z2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final boolean[] zArr, final boolean[] zArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(zArr, zArr2)) {
                this.a.add(new Diff<Boolean[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.11
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Boolean[] getLeft() {
                        return ArrayUtils.toObject(zArr);
                    }

                    /* renamed from: b */
                    public Boolean[] getRight() {
                        return ArrayUtils.toObject(zArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final byte b, final byte b2) {
        if (str != null) {
            if (!this.b && b != b2) {
                this.a.add(new Diff<Byte>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.12
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Byte getLeft() {
                        return Byte.valueOf(b);
                    }

                    /* renamed from: b */
                    public Byte getRight() {
                        return Byte.valueOf(b2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final byte[] bArr, final byte[] bArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(bArr, bArr2)) {
                this.a.add(new Diff<Byte[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.13
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Byte[] getLeft() {
                        return ArrayUtils.toObject(bArr);
                    }

                    /* renamed from: b */
                    public Byte[] getRight() {
                        return ArrayUtils.toObject(bArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final char c, final char c2) {
        if (str != null) {
            if (!this.b && c != c2) {
                this.a.add(new Diff<Character>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.14
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Character getLeft() {
                        return Character.valueOf(c);
                    }

                    /* renamed from: b */
                    public Character getRight() {
                        return Character.valueOf(c2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final char[] cArr, final char[] cArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(cArr, cArr2)) {
                this.a.add(new Diff<Character[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.15
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Character[] getLeft() {
                        return ArrayUtils.toObject(cArr);
                    }

                    /* renamed from: b */
                    public Character[] getRight() {
                        return ArrayUtils.toObject(cArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final double d, final double d2) {
        if (str != null) {
            if (!this.b && Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) {
                this.a.add(new Diff<Double>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.16
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Double getLeft() {
                        return Double.valueOf(d);
                    }

                    /* renamed from: b */
                    public Double getRight() {
                        return Double.valueOf(d2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final double[] dArr, final double[] dArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(dArr, dArr2)) {
                this.a.add(new Diff<Double[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.17
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Double[] getLeft() {
                        return ArrayUtils.toObject(dArr);
                    }

                    /* renamed from: b */
                    public Double[] getRight() {
                        return ArrayUtils.toObject(dArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final float f, final float f2) {
        if (str != null) {
            if (!this.b && Float.floatToIntBits(f) != Float.floatToIntBits(f2)) {
                this.a.add(new Diff<Float>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.18
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Float getLeft() {
                        return Float.valueOf(f);
                    }

                    /* renamed from: b */
                    public Float getRight() {
                        return Float.valueOf(f2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final float[] fArr, final float[] fArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(fArr, fArr2)) {
                this.a.add(new Diff<Float[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.2
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Float[] getLeft() {
                        return ArrayUtils.toObject(fArr);
                    }

                    /* renamed from: b */
                    public Float[] getRight() {
                        return ArrayUtils.toObject(fArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final int i, final int i2) {
        if (str != null) {
            if (!this.b && i != i2) {
                this.a.add(new Diff<Integer>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.3
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Integer getLeft() {
                        return Integer.valueOf(i);
                    }

                    /* renamed from: b */
                    public Integer getRight() {
                        return Integer.valueOf(i2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final int[] iArr, final int[] iArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(iArr, iArr2)) {
                this.a.add(new Diff<Integer[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.4
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Integer[] getLeft() {
                        return ArrayUtils.toObject(iArr);
                    }

                    /* renamed from: b */
                    public Integer[] getRight() {
                        return ArrayUtils.toObject(iArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final long j, final long j2) {
        if (str != null) {
            if (!this.b && j != j2) {
                this.a.add(new Diff<Long>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.5
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Long getLeft() {
                        return Long.valueOf(j);
                    }

                    /* renamed from: b */
                    public Long getRight() {
                        return Long.valueOf(j2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final long[] jArr, final long[] jArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(jArr, jArr2)) {
                this.a.add(new Diff<Long[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.6
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Long[] getLeft() {
                        return ArrayUtils.toObject(jArr);
                    }

                    /* renamed from: b */
                    public Long[] getRight() {
                        return ArrayUtils.toObject(jArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final short s, final short s2) {
        if (str != null) {
            if (!this.b && s != s2) {
                this.a.add(new Diff<Short>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.7
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Short getLeft() {
                        return Short.valueOf(s);
                    }

                    /* renamed from: b */
                    public Short getRight() {
                        return Short.valueOf(s2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final short[] sArr, final short[] sArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(sArr, sArr2)) {
                this.a.add(new Diff<Short[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.8
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Short[] getLeft() {
                        return ArrayUtils.toObject(sArr);
                    }

                    /* renamed from: b */
                    public Short[] getRight() {
                        return ArrayUtils.toObject(sArr2);
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, final Object obj, final Object obj2) {
        if (str == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        } else if (this.b || obj == obj2) {
            return this;
        } else {
            Object obj3 = obj != null ? obj : obj2;
            if (obj3.getClass().isArray()) {
                if (obj3 instanceof boolean[]) {
                    return append(str, (boolean[]) obj, (boolean[]) obj2);
                }
                if (obj3 instanceof byte[]) {
                    return append(str, (byte[]) obj, (byte[]) obj2);
                }
                if (obj3 instanceof char[]) {
                    return append(str, (char[]) obj, (char[]) obj2);
                }
                if (obj3 instanceof double[]) {
                    return append(str, (double[]) obj, (double[]) obj2);
                }
                if (obj3 instanceof float[]) {
                    return append(str, (float[]) obj, (float[]) obj2);
                }
                if (obj3 instanceof int[]) {
                    return append(str, (int[]) obj, (int[]) obj2);
                }
                if (obj3 instanceof long[]) {
                    return append(str, (long[]) obj, (long[]) obj2);
                }
                if (obj3 instanceof short[]) {
                    return append(str, (short[]) obj, (short[]) obj2);
                }
                return append(str, (Object[]) obj, (Object[]) obj2);
            } else if (obj != null && obj.equals(obj2)) {
                return this;
            } else {
                this.a.add(new Diff<Object>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.9
                    private static final long serialVersionUID = 1;

                    @Override // org.apache.commons.lang3.tuple.Pair
                    public Object getLeft() {
                        return obj;
                    }

                    @Override // org.apache.commons.lang3.tuple.Pair
                    public Object getRight() {
                        return obj2;
                    }
                });
                return this;
            }
        }
    }

    public DiffBuilder append(String str, final Object[] objArr, final Object[] objArr2) {
        if (str != null) {
            if (!this.b && !Arrays.equals(objArr, objArr2)) {
                this.a.add(new Diff<Object[]>(str) { // from class: org.apache.commons.lang3.builder.DiffBuilder.10
                    private static final long serialVersionUID = 1;

                    /* renamed from: a */
                    public Object[] getLeft() {
                        return objArr;
                    }

                    /* renamed from: b */
                    public Object[] getRight() {
                        return objArr2;
                    }
                });
            }
            return this;
        }
        throw new IllegalArgumentException("Field name cannot be null");
    }

    public DiffBuilder append(String str, DiffResult diffResult) {
        if (str == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        } else if (diffResult == null) {
            throw new IllegalArgumentException("Diff result cannot be null");
        } else if (this.b) {
            return this;
        } else {
            for (Diff<?> diff : diffResult.getDiffs()) {
                append(str + "." + diff.getFieldName(), diff.getLeft(), diff.getRight());
            }
            return this;
        }
    }

    @Override // org.apache.commons.lang3.builder.Builder
    public DiffResult build() {
        return new DiffResult(this.c, this.d, this.a, this.e);
    }
}
