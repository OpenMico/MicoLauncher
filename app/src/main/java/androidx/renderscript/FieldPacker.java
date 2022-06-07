package androidx.renderscript;

import android.util.Log;
import java.util.BitSet;

/* loaded from: classes.dex */
public class FieldPacker {
    private byte[] a;
    private int b;
    private int c;
    private BitSet d;

    public FieldPacker(int i) {
        this.b = 0;
        this.c = i;
        this.a = new byte[i];
        this.d = new BitSet();
    }

    public FieldPacker(byte[] bArr) {
        this.b = bArr.length;
        this.c = bArr.length;
        this.a = bArr;
        this.d = new BitSet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static FieldPacker a(Object[] objArr) {
        FieldPacker fieldPacker = new FieldPacker(RenderScript.g * 8);
        for (Object obj : objArr) {
            fieldPacker.a(obj);
        }
        fieldPacker.a(fieldPacker.b);
        return fieldPacker;
    }

    public void align(int i) {
        if (i > 0) {
            int i2 = i - 1;
            if ((i & i2) == 0) {
                while (true) {
                    int i3 = this.b;
                    if ((i3 & i2) != 0) {
                        this.d.flip(i3);
                        byte[] bArr = this.a;
                        int i4 = this.b;
                        this.b = i4 + 1;
                        bArr[i4] = 0;
                    } else {
                        return;
                    }
                }
            }
        }
        throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + i);
    }

    public void subalign(int i) {
        int i2;
        int i3 = i - 1;
        if ((i & i3) == 0) {
            while (true) {
                i2 = this.b;
                if ((i2 & i3) == 0) {
                    break;
                }
                this.b = i2 - 1;
            }
            if (i2 > 0) {
                while (this.d.get(this.b - 1)) {
                    this.b--;
                    this.d.flip(this.b);
                }
                return;
            }
            return;
        }
        throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + i);
    }

    public void reset() {
        this.b = 0;
    }

    public void reset(int i) {
        if (i < 0 || i > this.c) {
            throw new RSIllegalArgumentException("out of range argument: " + i);
        }
        this.b = i;
    }

    public void skip(int i) {
        int i2 = this.b + i;
        if (i2 < 0 || i2 > this.c) {
            throw new RSIllegalArgumentException("out of range argument: " + i);
        }
        this.b = i2;
    }

    public void addI8(byte b) {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr[i] = b;
    }

    public byte subI8() {
        subalign(1);
        byte[] bArr = this.a;
        int i = this.b - 1;
        this.b = i;
        return bArr[i];
    }

    public void addI16(short s) {
        align(2);
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr[i] = (byte) (s & 255);
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = (byte) (s >> 8);
    }

    public short subI16() {
        subalign(2);
        byte[] bArr = this.a;
        int i = this.b - 1;
        this.b = i;
        int i2 = this.b - 1;
        this.b = i2;
        return (short) (((short) (bArr[i2] & 255)) | ((short) ((bArr[i] & 255) << 8)));
    }

    public void addI32(int i) {
        align(4);
        byte[] bArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = (byte) (i & 255);
        int i3 = this.b;
        this.b = i3 + 1;
        bArr[i3] = (byte) ((i >> 8) & 255);
        int i4 = this.b;
        this.b = i4 + 1;
        bArr[i4] = (byte) ((i >> 16) & 255);
        int i5 = this.b;
        this.b = i5 + 1;
        bArr[i5] = (byte) ((i >> 24) & 255);
    }

    public int subI32() {
        subalign(4);
        byte[] bArr = this.a;
        int i = this.b - 1;
        this.b = i;
        int i2 = this.b - 1;
        this.b = i2;
        int i3 = ((bArr[i] & 255) << 24) | ((bArr[i2] & 255) << 16);
        int i4 = this.b - 1;
        this.b = i4;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = this.b - 1;
        this.b = i6;
        return (bArr[i6] & 255) | i5;
    }

    public void addI64(long j) {
        align(8);
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr[i] = (byte) (j & 255);
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = (byte) ((j >> 8) & 255);
        int i3 = this.b;
        this.b = i3 + 1;
        bArr[i3] = (byte) ((j >> 16) & 255);
        int i4 = this.b;
        this.b = i4 + 1;
        bArr[i4] = (byte) ((j >> 24) & 255);
        int i5 = this.b;
        this.b = i5 + 1;
        bArr[i5] = (byte) ((j >> 32) & 255);
        int i6 = this.b;
        this.b = i6 + 1;
        bArr[i6] = (byte) ((j >> 40) & 255);
        int i7 = this.b;
        this.b = i7 + 1;
        bArr[i7] = (byte) ((j >> 48) & 255);
        int i8 = this.b;
        this.b = i8 + 1;
        bArr[i8] = (byte) ((j >> 56) & 255);
    }

    public long subI64() {
        subalign(8);
        byte[] bArr = this.a;
        int i = this.b - 1;
        this.b = i;
        int i2 = this.b - 1;
        this.b = i2;
        long j = ((bArr[i] & 255) << 56) | 0 | ((bArr[i2] & 255) << 48);
        int i3 = this.b - 1;
        this.b = i3;
        long j2 = j | ((bArr[i3] & 255) << 40);
        int i4 = this.b - 1;
        this.b = i4;
        long j3 = j2 | ((bArr[i4] & 255) << 32);
        int i5 = this.b - 1;
        this.b = i5;
        long j4 = j3 | ((bArr[i5] & 255) << 24);
        int i6 = this.b - 1;
        this.b = i6;
        long j5 = j4 | ((bArr[i6] & 255) << 16);
        int i7 = this.b - 1;
        this.b = i7;
        int i8 = this.b - 1;
        this.b = i8;
        return (bArr[i8] & 255) | j5 | ((bArr[i7] & 255) << 8);
    }

    public void addU8(short s) {
        if (s < 0 || s > 255) {
            Log.e("rs", "FieldPacker.addU8( " + ((int) s) + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr[i] = (byte) s;
    }

    public void addU16(int i) {
        if (i < 0 || i > 65535) {
            Log.e("rs", "FieldPacker.addU16( " + i + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        align(2);
        byte[] bArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = (byte) (i & 255);
        int i3 = this.b;
        this.b = i3 + 1;
        bArr[i3] = (byte) (i >> 8);
    }

    public void addU32(long j) {
        if (j < 0 || j > 4294967295L) {
            Log.e("rs", "FieldPacker.addU32( " + j + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        align(4);
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr[i] = (byte) (j & 255);
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = (byte) ((j >> 8) & 255);
        int i3 = this.b;
        this.b = i3 + 1;
        bArr[i3] = (byte) ((j >> 16) & 255);
        int i4 = this.b;
        this.b = i4 + 1;
        bArr[i4] = (byte) ((j >> 24) & 255);
    }

    public void addU64(long j) {
        if (j >= 0) {
            align(8);
            byte[] bArr = this.a;
            int i = this.b;
            this.b = i + 1;
            bArr[i] = (byte) (j & 255);
            int i2 = this.b;
            this.b = i2 + 1;
            bArr[i2] = (byte) ((j >> 8) & 255);
            int i3 = this.b;
            this.b = i3 + 1;
            bArr[i3] = (byte) ((j >> 16) & 255);
            int i4 = this.b;
            this.b = i4 + 1;
            bArr[i4] = (byte) ((j >> 24) & 255);
            int i5 = this.b;
            this.b = i5 + 1;
            bArr[i5] = (byte) ((j >> 32) & 255);
            int i6 = this.b;
            this.b = i6 + 1;
            bArr[i6] = (byte) ((j >> 40) & 255);
            int i7 = this.b;
            this.b = i7 + 1;
            bArr[i7] = (byte) ((j >> 48) & 255);
            int i8 = this.b;
            this.b = i8 + 1;
            bArr[i8] = (byte) ((j >> 56) & 255);
            return;
        }
        Log.e("rs", "FieldPacker.addU64( " + j + " )");
        throw new IllegalArgumentException("Saving value out of range for type");
    }

    public void addF32(float f) {
        addI32(Float.floatToRawIntBits(f));
    }

    public float subF32() {
        return Float.intBitsToFloat(subI32());
    }

    public void addF64(double d) {
        addI64(Double.doubleToRawLongBits(d));
    }

    public double subF64() {
        return Double.longBitsToDouble(subI64());
    }

    public void addObj(BaseObj baseObj) {
        if (baseObj != null) {
            if (RenderScript.g == 8) {
                addI64(baseObj.a((RenderScript) null));
                addI64(0L);
                addI64(0L);
                addI64(0L);
                return;
            }
            addI32((int) baseObj.a((RenderScript) null));
        } else if (RenderScript.g == 8) {
            addI64(0L);
            addI64(0L);
            addI64(0L);
            addI64(0L);
        } else {
            addI32(0);
        }
    }

    public void addF32(Float2 float2) {
        addF32(float2.x);
        addF32(float2.y);
    }

    public void addF32(Float3 float3) {
        addF32(float3.x);
        addF32(float3.y);
        addF32(float3.z);
    }

    public void addF32(Float4 float4) {
        addF32(float4.x);
        addF32(float4.y);
        addF32(float4.z);
        addF32(float4.w);
    }

    public void addF64(Double2 double2) {
        addF64(double2.x);
        addF64(double2.y);
    }

    public void addF64(Double3 double3) {
        addF64(double3.x);
        addF64(double3.y);
        addF64(double3.z);
    }

    public void addF64(Double4 double4) {
        addF64(double4.x);
        addF64(double4.y);
        addF64(double4.z);
        addF64(double4.w);
    }

    public void addI8(Byte2 byte2) {
        addI8(byte2.x);
        addI8(byte2.y);
    }

    public void addI8(Byte3 byte3) {
        addI8(byte3.x);
        addI8(byte3.y);
        addI8(byte3.z);
    }

    public void addI8(Byte4 byte4) {
        addI8(byte4.x);
        addI8(byte4.y);
        addI8(byte4.z);
        addI8(byte4.w);
    }

    public void addU8(Short2 short2) {
        addU8(short2.x);
        addU8(short2.y);
    }

    public void addU8(Short3 short3) {
        addU8(short3.x);
        addU8(short3.y);
        addU8(short3.z);
    }

    public void addU8(Short4 short4) {
        addU8(short4.x);
        addU8(short4.y);
        addU8(short4.z);
        addU8(short4.w);
    }

    public void addI16(Short2 short2) {
        addI16(short2.x);
        addI16(short2.y);
    }

    public void addI16(Short3 short3) {
        addI16(short3.x);
        addI16(short3.y);
        addI16(short3.z);
    }

    public void addI16(Short4 short4) {
        addI16(short4.x);
        addI16(short4.y);
        addI16(short4.z);
        addI16(short4.w);
    }

    public void addU16(Int2 int2) {
        addU16(int2.x);
        addU16(int2.y);
    }

    public void addU16(Int3 int3) {
        addU16(int3.x);
        addU16(int3.y);
        addU16(int3.z);
    }

    public void addU16(Int4 int4) {
        addU16(int4.x);
        addU16(int4.y);
        addU16(int4.z);
        addU16(int4.w);
    }

    public void addI32(Int2 int2) {
        addI32(int2.x);
        addI32(int2.y);
    }

    public void addI32(Int3 int3) {
        addI32(int3.x);
        addI32(int3.y);
        addI32(int3.z);
    }

    public void addI32(Int4 int4) {
        addI32(int4.x);
        addI32(int4.y);
        addI32(int4.z);
        addI32(int4.w);
    }

    public void addU32(Long2 long2) {
        addU32(long2.x);
        addU32(long2.y);
    }

    public void addU32(Long3 long3) {
        addU32(long3.x);
        addU32(long3.y);
        addU32(long3.z);
    }

    public void addU32(Long4 long4) {
        addU32(long4.x);
        addU32(long4.y);
        addU32(long4.z);
        addU32(long4.w);
    }

    public void addI64(Long2 long2) {
        addI64(long2.x);
        addI64(long2.y);
    }

    public void addI64(Long3 long3) {
        addI64(long3.x);
        addI64(long3.y);
        addI64(long3.z);
    }

    public void addI64(Long4 long4) {
        addI64(long4.x);
        addI64(long4.y);
        addI64(long4.z);
        addI64(long4.w);
    }

    public void addU64(Long2 long2) {
        addU64(long2.x);
        addU64(long2.y);
    }

    public void addU64(Long3 long3) {
        addU64(long3.x);
        addU64(long3.y);
        addU64(long3.z);
    }

    public void addU64(Long4 long4) {
        addU64(long4.x);
        addU64(long4.y);
        addU64(long4.z);
        addU64(long4.w);
    }

    public Float2 subFloat2() {
        Float2 float2 = new Float2();
        float2.y = subF32();
        float2.x = subF32();
        return float2;
    }

    public Float3 subFloat3() {
        Float3 float3 = new Float3();
        float3.z = subF32();
        float3.y = subF32();
        float3.x = subF32();
        return float3;
    }

    public Float4 subFloat4() {
        Float4 float4 = new Float4();
        float4.w = subF32();
        float4.z = subF32();
        float4.y = subF32();
        float4.x = subF32();
        return float4;
    }

    public Double2 subDouble2() {
        Double2 double2 = new Double2();
        double2.y = subF64();
        double2.x = subF64();
        return double2;
    }

    public Double3 subDouble3() {
        Double3 double3 = new Double3();
        double3.z = subF64();
        double3.y = subF64();
        double3.x = subF64();
        return double3;
    }

    public Double4 subDouble4() {
        Double4 double4 = new Double4();
        double4.w = subF64();
        double4.z = subF64();
        double4.y = subF64();
        double4.x = subF64();
        return double4;
    }

    public Byte2 subByte2() {
        Byte2 byte2 = new Byte2();
        byte2.y = subI8();
        byte2.x = subI8();
        return byte2;
    }

    public Byte3 subByte3() {
        Byte3 byte3 = new Byte3();
        byte3.z = subI8();
        byte3.y = subI8();
        byte3.x = subI8();
        return byte3;
    }

    public Byte4 subByte4() {
        Byte4 byte4 = new Byte4();
        byte4.w = subI8();
        byte4.z = subI8();
        byte4.y = subI8();
        byte4.x = subI8();
        return byte4;
    }

    public Short2 subShort2() {
        Short2 short2 = new Short2();
        short2.y = subI16();
        short2.x = subI16();
        return short2;
    }

    public Short3 subShort3() {
        Short3 short3 = new Short3();
        short3.z = subI16();
        short3.y = subI16();
        short3.x = subI16();
        return short3;
    }

    public Short4 subShort4() {
        Short4 short4 = new Short4();
        short4.w = subI16();
        short4.z = subI16();
        short4.y = subI16();
        short4.x = subI16();
        return short4;
    }

    public Int2 subInt2() {
        Int2 int2 = new Int2();
        int2.y = subI32();
        int2.x = subI32();
        return int2;
    }

    public Int3 subInt3() {
        Int3 int3 = new Int3();
        int3.z = subI32();
        int3.y = subI32();
        int3.x = subI32();
        return int3;
    }

    public Int4 subInt4() {
        Int4 int4 = new Int4();
        int4.w = subI32();
        int4.z = subI32();
        int4.y = subI32();
        int4.x = subI32();
        return int4;
    }

    public Long2 subLong2() {
        Long2 long2 = new Long2();
        long2.y = subI64();
        long2.x = subI64();
        return long2;
    }

    public Long3 subLong3() {
        Long3 long3 = new Long3();
        long3.z = subI64();
        long3.y = subI64();
        long3.x = subI64();
        return long3;
    }

    public Long4 subLong4() {
        Long4 long4 = new Long4();
        long4.w = subI64();
        long4.z = subI64();
        long4.y = subI64();
        long4.x = subI64();
        return long4;
    }

    public void addMatrix(Matrix4f matrix4f) {
        for (int i = 0; i < matrix4f.a.length; i++) {
            addF32(matrix4f.a[i]);
        }
    }

    public Matrix4f subMatrix4f() {
        Matrix4f matrix4f = new Matrix4f();
        for (int length = matrix4f.a.length - 1; length >= 0; length--) {
            matrix4f.a[length] = subF32();
        }
        return matrix4f;
    }

    public void addMatrix(Matrix3f matrix3f) {
        for (int i = 0; i < matrix3f.a.length; i++) {
            addF32(matrix3f.a[i]);
        }
    }

    public Matrix3f subMatrix3f() {
        Matrix3f matrix3f = new Matrix3f();
        for (int length = matrix3f.a.length - 1; length >= 0; length--) {
            matrix3f.a[length] = subF32();
        }
        return matrix3f;
    }

    public void addMatrix(Matrix2f matrix2f) {
        for (int i = 0; i < matrix2f.a.length; i++) {
            addF32(matrix2f.a[i]);
        }
    }

    public Matrix2f subMatrix2f() {
        Matrix2f matrix2f = new Matrix2f();
        for (int length = matrix2f.a.length - 1; length >= 0; length--) {
            matrix2f.a[length] = subF32();
        }
        return matrix2f;
    }

    public void addBoolean(boolean z) {
        addI8(z ? (byte) 1 : (byte) 0);
    }

    public boolean subBoolean() {
        return subI8() == 1;
    }

    public final byte[] getData() {
        return this.a;
    }

    public int getPos() {
        return this.b;
    }

    private static void a(FieldPacker fieldPacker, Object obj) {
        if (obj instanceof Boolean) {
            fieldPacker.addBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof Byte) {
            fieldPacker.addI8(((Byte) obj).byteValue());
        } else if (obj instanceof Short) {
            fieldPacker.addI16(((Short) obj).shortValue());
        } else if (obj instanceof Integer) {
            fieldPacker.addI32(((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            fieldPacker.addI64(((Long) obj).longValue());
        } else if (obj instanceof Float) {
            fieldPacker.addF32(((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            fieldPacker.addF64(((Double) obj).doubleValue());
        } else if (obj instanceof Byte2) {
            fieldPacker.addI8((Byte2) obj);
        } else if (obj instanceof Byte3) {
            fieldPacker.addI8((Byte3) obj);
        } else if (obj instanceof Byte4) {
            fieldPacker.addI8((Byte4) obj);
        } else if (obj instanceof Short2) {
            fieldPacker.addI16((Short2) obj);
        } else if (obj instanceof Short3) {
            fieldPacker.addI16((Short3) obj);
        } else if (obj instanceof Short4) {
            fieldPacker.addI16((Short4) obj);
        } else if (obj instanceof Int2) {
            fieldPacker.addI32((Int2) obj);
        } else if (obj instanceof Int3) {
            fieldPacker.addI32((Int3) obj);
        } else if (obj instanceof Int4) {
            fieldPacker.addI32((Int4) obj);
        } else if (obj instanceof Long2) {
            fieldPacker.addI64((Long2) obj);
        } else if (obj instanceof Long3) {
            fieldPacker.addI64((Long3) obj);
        } else if (obj instanceof Long4) {
            fieldPacker.addI64((Long4) obj);
        } else if (obj instanceof Float2) {
            fieldPacker.addF32((Float2) obj);
        } else if (obj instanceof Float3) {
            fieldPacker.addF32((Float3) obj);
        } else if (obj instanceof Float4) {
            fieldPacker.addF32((Float4) obj);
        } else if (obj instanceof Double2) {
            fieldPacker.addF64((Double2) obj);
        } else if (obj instanceof Double3) {
            fieldPacker.addF64((Double3) obj);
        } else if (obj instanceof Double4) {
            fieldPacker.addF64((Double4) obj);
        } else if (obj instanceof Matrix2f) {
            fieldPacker.addMatrix((Matrix2f) obj);
        } else if (obj instanceof Matrix3f) {
            fieldPacker.addMatrix((Matrix3f) obj);
        } else if (obj instanceof Matrix4f) {
            fieldPacker.addMatrix((Matrix4f) obj);
        } else if (obj instanceof BaseObj) {
            fieldPacker.addObj((BaseObj) obj);
        }
    }

    private boolean a(int i) {
        if (i == this.c) {
            return false;
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.a, 0, bArr, 0, this.b);
        this.a = bArr;
        this.c = i;
        return true;
    }

    private void a(Object obj) {
        boolean z;
        int i = this.b;
        do {
            z = false;
            try {
                a(this, obj);
                continue;
            } catch (ArrayIndexOutOfBoundsException unused) {
                this.b = i;
                a(this.c * 2);
                z = true;
                continue;
            }
        } while (z);
    }
}
