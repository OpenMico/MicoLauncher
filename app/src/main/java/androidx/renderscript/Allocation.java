package androidx.renderscript;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.Surface;
import androidx.renderscript.Element;
import androidx.renderscript.Type;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class Allocation extends BaseObj {
    public static final int USAGE_GRAPHICS_TEXTURE = 2;
    public static final int USAGE_IO_INPUT = 32;
    public static final int USAGE_IO_OUTPUT = 64;
    public static final int USAGE_SCRIPT = 1;
    public static final int USAGE_SHARED = 128;
    static BitmapFactory.Options s = new BitmapFactory.Options();
    Type a;
    Bitmap b;
    int c;
    int d;
    Allocation e;
    boolean i;
    int k;
    int m;
    int n;
    int o;
    int p;
    long q;
    boolean r;
    ByteBuffer f = null;
    long g = 0;
    boolean h = true;
    boolean j = false;
    Type.CubemapFace l = Type.CubemapFace.POSITIVE_X;

    public static Allocation createCubemapFromCubeFaces(RenderScript renderScript, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, MipmapControl mipmapControl, int i) {
        return null;
    }

    private Element.DataType a(Object obj, boolean z) {
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            Class<?> componentType = cls.getComponentType();
            if (!componentType.isPrimitive()) {
                throw new RSIllegalArgumentException("Object passed is not an Array of primitives.");
            } else if (componentType == Long.TYPE) {
                if (!z) {
                    return Element.DataType.SIGNED_64;
                }
                d();
                return this.a.h.g;
            } else if (componentType == Integer.TYPE) {
                if (!z) {
                    return Element.DataType.SIGNED_32;
                }
                e();
                return this.a.h.g;
            } else if (componentType == Short.TYPE) {
                if (!z) {
                    return Element.DataType.SIGNED_16;
                }
                f();
                return this.a.h.g;
            } else if (componentType == Byte.TYPE) {
                if (!z) {
                    return Element.DataType.SIGNED_8;
                }
                g();
                return this.a.h.g;
            } else if (componentType == Float.TYPE) {
                if (z) {
                    h();
                }
                return Element.DataType.FLOAT_32;
            } else if (componentType != Double.TYPE) {
                return null;
            } else {
                if (z) {
                    i();
                }
                return Element.DataType.FLOAT_64;
            }
        } else {
            throw new RSIllegalArgumentException("Object passed is not an array of primitives.");
        }
    }

    /* loaded from: classes.dex */
    public enum MipmapControl {
        MIPMAP_NONE(0),
        MIPMAP_FULL(1),
        MIPMAP_ON_SYNC_TO_TEXTURE(2);
        
        int mID;

        MipmapControl(int i) {
            this.mID = i;
        }
    }

    public long getIncAllocID() {
        return this.q;
    }

    public void setIncAllocID(long j) {
        this.q = j;
    }

    private long c() {
        Allocation allocation = this.e;
        if (allocation != null) {
            return allocation.a(this.t);
        }
        return a(this.t);
    }

    public Element getElement() {
        return this.a.getElement();
    }

    public int getUsage() {
        return this.c;
    }

    public void setAutoPadding(boolean z) {
        this.j = z;
    }

    public int getBytesSize() {
        if (this.a.f != 0) {
            return (int) Math.ceil(this.a.getCount() * this.a.getElement().getBytesSize() * 1.5d);
        }
        return this.a.getCount() * this.a.getElement().getBytesSize();
    }

    private void a(Type type) {
        this.m = type.getX();
        this.n = type.getY();
        this.o = type.getZ();
        this.p = this.m;
        int i = this.n;
        if (i > 1) {
            this.p *= i;
        }
        int i2 = this.o;
        if (i2 > 1) {
            this.p *= i2;
        }
    }

    private void a(Bitmap bitmap) {
        this.b = bitmap;
    }

    Allocation(long j, RenderScript renderScript, Type type, int i) {
        super(j, renderScript);
        this.i = true;
        if ((i & (-228)) == 0) {
            if ((i & 32) != 0) {
                this.i = false;
                if ((i & (-36)) != 0) {
                    throw new RSIllegalArgumentException("Invalid usage combination.");
                }
            }
            this.a = type;
            this.c = i;
            this.q = 0L;
            this.r = false;
            if (type != null) {
                this.d = this.a.getCount() * this.a.getElement().getBytesSize();
                a(type);
            }
            if (RenderScript.b) {
                try {
                    RenderScript.d.invoke(RenderScript.c, Integer.valueOf(this.d));
                } catch (Exception e) {
                    Log.e("RenderScript_jni", "Couldn't invoke registerNativeAllocation:" + e);
                    throw new RSRuntimeException("Couldn't invoke registerNativeAllocation:" + e);
                }
            }
        } else {
            throw new RSIllegalArgumentException("Unknown usage specified.");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.renderscript.BaseObj
    public void finalize() throws Throwable {
        if (RenderScript.b) {
            RenderScript.e.invoke(RenderScript.c, Integer.valueOf(this.d));
        }
        super.finalize();
    }

    private void d() {
        if (this.a.h.g != Element.DataType.SIGNED_64 && this.a.h.g != Element.DataType.UNSIGNED_64) {
            throw new RSIllegalArgumentException("64 bit integer source does not match allocation type " + this.a.h.g);
        }
    }

    private void e() {
        if (this.a.h.g != Element.DataType.SIGNED_32 && this.a.h.g != Element.DataType.UNSIGNED_32) {
            throw new RSIllegalArgumentException("32 bit integer source does not match allocation type " + this.a.h.g);
        }
    }

    private void f() {
        if (this.a.h.g != Element.DataType.SIGNED_16 && this.a.h.g != Element.DataType.UNSIGNED_16) {
            throw new RSIllegalArgumentException("16 bit integer source does not match allocation type " + this.a.h.g);
        }
    }

    private void g() {
        if (this.a.h.g != Element.DataType.SIGNED_8 && this.a.h.g != Element.DataType.UNSIGNED_8) {
            throw new RSIllegalArgumentException("8 bit integer source does not match allocation type " + this.a.h.g);
        }
    }

    private void h() {
        if (this.a.h.g != Element.DataType.FLOAT_32) {
            throw new RSIllegalArgumentException("32 bit float source does not match allocation type " + this.a.h.g);
        }
    }

    private void i() {
        if (this.a.h.g != Element.DataType.FLOAT_64) {
            throw new RSIllegalArgumentException("64 bit float source does not match allocation type " + this.a.h.g);
        }
    }

    private void j() {
        if (this.a.h.g != Element.DataType.RS_ELEMENT && this.a.h.g != Element.DataType.RS_TYPE && this.a.h.g != Element.DataType.RS_ALLOCATION && this.a.h.g != Element.DataType.RS_SAMPLER && this.a.h.g != Element.DataType.RS_SCRIPT) {
            throw new RSIllegalArgumentException("Object source does not match allocation type " + this.a.h.g);
        }
    }

    public Type getType() {
        return this.a;
    }

    public void syncAll(int i) {
        switch (i) {
            case 1:
            case 2:
                this.t.g();
                this.t.a(c(), i);
                return;
            default:
                throw new RSIllegalArgumentException("Source must be exactly one usage type.");
        }
    }

    public void ioSend() {
        if ((this.c & 64) != 0) {
            this.t.g();
            this.t.b(a(this.t));
            return;
        }
        throw new RSIllegalArgumentException("Can only send buffer if IO_OUTPUT usage specified.");
    }

    public void ioSendOutput() {
        ioSend();
    }

    public ByteBuffer getByteBuffer() {
        byte[] bArr;
        int x = this.a.getX() * this.a.getElement().getBytesSize();
        if (this.t.a() >= 21) {
            if (this.f == null || (this.c & 32) != 0) {
                this.f = this.t.a(a(this.t), x, this.a.getY(), this.a.getZ());
            }
            return this.f;
        } else if (this.a.getZ() > 0) {
            return null;
        } else {
            if (this.a.getY() > 0) {
                bArr = new byte[this.a.getY() * x];
                b(0, 0, this.a.getX(), this.a.getY(), bArr, Element.DataType.SIGNED_8, x * this.a.getY());
            } else {
                bArr = new byte[x];
                copy1DRangeToUnchecked(0, this.a.getX(), bArr);
            }
            ByteBuffer asReadOnlyBuffer = ByteBuffer.wrap(bArr).asReadOnlyBuffer();
            this.g = x;
            return asReadOnlyBuffer;
        }
    }

    public long getStride() {
        if (this.g == 0) {
            if (this.t.a() > 21) {
                this.g = this.t.d(a(this.t));
            } else {
                this.g = this.a.getX() * this.a.getElement().getBytesSize();
            }
        }
        return this.g;
    }

    public void ioReceive() {
        if ((this.c & 32) != 0) {
            this.t.g();
            this.t.c(a(this.t));
            return;
        }
        throw new RSIllegalArgumentException("Can only receive if IO_INPUT usage specified.");
    }

    public void copyFrom(BaseObj[] baseObjArr) {
        this.t.g();
        j();
        if (baseObjArr.length != this.p) {
            throw new RSIllegalArgumentException("Array size mismatch, allocation sizeX = " + this.p + ", array length = " + baseObjArr.length);
        } else if (RenderScript.g == 8) {
            long[] jArr = new long[baseObjArr.length * 4];
            for (int i = 0; i < baseObjArr.length; i++) {
                jArr[i * 4] = baseObjArr[i].a(this.t);
            }
            copy1DRangeFromUnchecked(0, this.p, jArr);
        } else {
            int[] iArr = new int[baseObjArr.length];
            for (int i2 = 0; i2 < baseObjArr.length; i2++) {
                iArr[i2] = (int) baseObjArr[i2].a(this.t);
            }
            copy1DRangeFromUnchecked(0, this.p, iArr);
        }
    }

    private void b(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config != null) {
            switch (AnonymousClass1.a[config.ordinal()]) {
                case 1:
                    if (this.a.getElement().h != Element.DataKind.PIXEL_A) {
                        throw new RSIllegalArgumentException("Allocation kind is " + this.a.getElement().h + ", type " + this.a.getElement().g + " of " + this.a.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                    }
                    return;
                case 2:
                    if (this.a.getElement().h != Element.DataKind.PIXEL_RGBA || this.a.getElement().getBytesSize() != 4) {
                        throw new RSIllegalArgumentException("Allocation kind is " + this.a.getElement().h + ", type " + this.a.getElement().g + " of " + this.a.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                    }
                    return;
                case 3:
                    if (this.a.getElement().h != Element.DataKind.PIXEL_RGB || this.a.getElement().getBytesSize() != 2) {
                        throw new RSIllegalArgumentException("Allocation kind is " + this.a.getElement().h + ", type " + this.a.getElement().g + " of " + this.a.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                    }
                    return;
                case 4:
                    if (this.a.getElement().h != Element.DataKind.PIXEL_RGBA || this.a.getElement().getBytesSize() != 2) {
                        throw new RSIllegalArgumentException("Allocation kind is " + this.a.getElement().h + ", type " + this.a.getElement().g + " of " + this.a.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
                    }
                    return;
                default:
                    return;
            }
        } else {
            throw new RSIllegalArgumentException("Bitmap has an unsupported format for this operation");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: androidx.renderscript.Allocation$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Bitmap.Config.values().length];

        static {
            try {
                a[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Bitmap.Config.ARGB_8888.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Bitmap.Config.RGB_565.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[Bitmap.Config.ARGB_4444.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void c(Bitmap bitmap) {
        if (this.m != bitmap.getWidth() || this.n != bitmap.getHeight()) {
            throw new RSIllegalArgumentException("Cannot update allocation from bitmap, sizes mismatch");
        }
    }

    private void a(Object obj, Element.DataType dataType, int i) {
        this.t.g();
        int i2 = this.o;
        if (i2 > 0) {
            a(0, 0, 0, this.m, this.n, i2, obj, dataType, i);
            return;
        }
        int i3 = this.n;
        if (i3 > 0) {
            a(0, 0, this.m, i3, obj, dataType, i);
        } else {
            a(0, this.p, obj, dataType, i);
        }
    }

    public void copyFromUnchecked(Object obj) {
        a(obj, a(obj, false), Array.getLength(obj));
    }

    public void copyFromUnchecked(int[] iArr) {
        a(iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyFromUnchecked(short[] sArr) {
        a(sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyFromUnchecked(byte[] bArr) {
        a(bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyFromUnchecked(float[] fArr) {
        a(fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyFrom(Object obj) {
        a(obj, a(obj, true), Array.getLength(obj));
    }

    public void copyFrom(int[] iArr) {
        e();
        a(iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyFrom(short[] sArr) {
        f();
        a(sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyFrom(byte[] bArr) {
        g();
        a(bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyFrom(float[] fArr) {
        h();
        a(fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyFrom(Bitmap bitmap) {
        this.t.g();
        if (bitmap.getConfig() == null) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            copyFrom(createBitmap);
            return;
        }
        c(bitmap);
        b(bitmap);
        this.t.b(a(this.t), bitmap);
    }

    public void copyFrom(Allocation allocation) {
        this.t.g();
        if (this.a.equals(allocation.getType())) {
            copy2DRangeFrom(0, 0, this.m, this.n, allocation, 0, 0);
            return;
        }
        throw new RSIllegalArgumentException("Types of allocations must match.");
    }

    public void setFromFieldPacker(int i, FieldPacker fieldPacker) {
        this.t.g();
        int bytesSize = this.a.h.getBytesSize();
        byte[] data = fieldPacker.getData();
        int pos = fieldPacker.getPos();
        int i2 = pos / bytesSize;
        if (bytesSize * i2 == pos) {
            copy1DRangeFromUnchecked(i, i2, data);
            return;
        }
        throw new RSIllegalArgumentException("Field packer length " + pos + " not divisible by element size " + bytesSize + ".");
    }

    public void setFromFieldPacker(int i, int i2, FieldPacker fieldPacker) {
        this.t.g();
        if (i2 >= this.a.h.b.length) {
            throw new RSIllegalArgumentException("Component_number " + i2 + " out of range.");
        } else if (i >= 0) {
            byte[] data = fieldPacker.getData();
            int pos = fieldPacker.getPos();
            int bytesSize = this.a.h.b[i2].getBytesSize() * this.a.h.d[i2];
            if (pos == bytesSize) {
                this.t.a(c(), i, this.k, i2, data, pos);
                return;
            }
            throw new RSIllegalArgumentException("Field packer sizelength " + pos + " does not match component size " + bytesSize + ".");
        } else {
            throw new RSIllegalArgumentException("Offset must be >= 0.");
        }
    }

    private void a(int i, int i2, int i3, int i4, boolean z) {
        this.t.g();
        if (i < 0) {
            throw new RSIllegalArgumentException("Offset must be >= 0.");
        } else if (i2 < 1) {
            throw new RSIllegalArgumentException("Count must be >= 1.");
        } else if (i + i2 > this.p) {
            throw new RSIllegalArgumentException("Overflow, Available count " + this.p + ", got " + i2 + " at offset " + i + ".");
        } else if (z) {
            if (i3 < (i4 / 4) * 3) {
                throw new RSIllegalArgumentException("Array too small for allocation type.");
            }
        } else if (i3 < i4) {
            throw new RSIllegalArgumentException("Array too small for allocation type.");
        }
    }

    public void generateMipmaps() {
        this.t.e(a(this.t));
    }

    private void a(int i, int i2, Object obj, Element.DataType dataType, int i3) {
        boolean z;
        Element.DataType dataType2;
        int bytesSize = this.a.h.getBytesSize() * i2;
        if (!this.j || this.a.getElement().getVectorSize() != 3) {
            z = false;
            dataType2 = dataType;
        } else {
            z = true;
            dataType2 = dataType;
        }
        a(i, i2, i3 * dataType2.mSize, bytesSize, z);
        this.t.a(c(), i, this.k, i2, obj, bytesSize, dataType, this.a.h.g.mSize, z);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, Object obj) {
        a(i, i2, obj, a(obj, false), Array.getLength(obj));
    }

    public void copy1DRangeFromUnchecked(int i, int i2, int[] iArr) {
        a(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, short[] sArr) {
        a(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, byte[] bArr) {
        a(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, float[] fArr) {
        a(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, Object obj) {
        a(i, i2, obj, a(obj, true), Array.getLength(obj));
    }

    public void copy1DRangeFrom(int i, int i2, int[] iArr) {
        e();
        a(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, short[] sArr) {
        f();
        a(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, byte[] bArr) {
        g();
        a(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, float[] fArr) {
        h();
        a(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, Allocation allocation, int i3) {
        this.t.a(c(), i, 0, this.k, this.l.mID, i2, 1, allocation.a(this.t), i3, 0, allocation.k, allocation.l.mID);
    }

    private void a(int i, int i2, int i3, int i4) {
        if (this.e == null) {
            if (i < 0 || i2 < 0) {
                throw new RSIllegalArgumentException("Offset cannot be negative.");
            } else if (i4 < 0 || i3 < 0) {
                throw new RSIllegalArgumentException("Height or width cannot be negative.");
            } else if (i + i3 > this.m || i2 + i4 > this.n) {
                throw new RSIllegalArgumentException("Updated region larger than allocation.");
            }
        }
    }

    void a(int i, int i2, int i3, int i4, Object obj, Element.DataType dataType, int i5) {
        boolean z;
        int i6;
        this.t.g();
        a(i, i2, i3, i4);
        int bytesSize = this.a.h.getBytesSize() * i3 * i4;
        int i7 = dataType.mSize * i5;
        if (!this.j || this.a.getElement().getVectorSize() != 3) {
            if (bytesSize <= i7) {
                z = false;
                i6 = i7;
            } else {
                throw new RSIllegalArgumentException("Array too small for allocation type.");
            }
        } else if ((bytesSize / 4) * 3 <= i7) {
            z = true;
            i6 = bytesSize;
        } else {
            throw new RSIllegalArgumentException("Array too small for allocation type.");
        }
        this.t.a(c(), i, i2, this.k, this.l.mID, i3, i4, obj, i6, dataType, this.a.h.g.mSize, z);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, Object obj) {
        a(i, i2, i3, i4, obj, a(obj, true), Array.getLength(obj));
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, byte[] bArr) {
        g();
        a(i, i2, i3, i4, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, short[] sArr) {
        f();
        a(i, i2, i3, i4, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, int[] iArr) {
        e();
        a(i, i2, i3, i4, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, float[] fArr) {
        h();
        a(i, i2, i3, i4, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, Allocation allocation, int i5, int i6) {
        this.t.g();
        a(i, i2, i3, i4);
        this.t.a(c(), i, i2, this.k, this.l.mID, i3, i4, allocation.a(this.t), i5, i6, allocation.k, allocation.l.mID);
    }

    public void copy2DRangeFrom(int i, int i2, Bitmap bitmap) {
        this.t.g();
        if (bitmap.getConfig() == null) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            copy2DRangeFrom(i, i2, createBitmap);
            return;
        }
        b(bitmap);
        a(i, i2, bitmap.getWidth(), bitmap.getHeight());
        this.t.a(c(), i, i2, this.k, this.l.mID, bitmap);
    }

    private void a(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.e == null) {
            if (i < 0 || i2 < 0 || i3 < 0) {
                throw new RSIllegalArgumentException("Offset cannot be negative.");
            } else if (i5 < 0 || i4 < 0 || i6 < 0) {
                throw new RSIllegalArgumentException("Height or width cannot be negative.");
            } else if (i + i4 > this.m || i2 + i5 > this.n || i3 + i6 > this.o) {
                throw new RSIllegalArgumentException("Updated region larger than allocation.");
            }
        }
    }

    private void a(int i, int i2, int i3, int i4, int i5, int i6, Object obj, Element.DataType dataType, int i7) {
        boolean z;
        int i8;
        this.t.g();
        a(i, i2, i3, i4, i5, i6);
        int bytesSize = this.a.h.getBytesSize() * i4 * i5 * i6;
        int i9 = dataType.mSize * i7;
        if (!this.j || this.a.getElement().getVectorSize() != 3) {
            if (bytesSize <= i9) {
                z = false;
                i8 = i9;
            } else {
                throw new RSIllegalArgumentException("Array too small for allocation type.");
            }
        } else if ((bytesSize / 4) * 3 <= i9) {
            z = true;
            i8 = bytesSize;
        } else {
            throw new RSIllegalArgumentException("Array too small for allocation type.");
        }
        this.t.a(c(), i, i2, i3, this.k, i4, i5, i6, obj, i8, dataType, this.a.h.g.mSize, z);
    }

    public void copy3DRangeFrom(int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
        a(i, i2, i3, i4, i5, i6, obj, a(obj, true), Array.getLength(obj));
    }

    public void copy3DRangeFrom(int i, int i2, int i3, int i4, int i5, int i6, Allocation allocation, int i7, int i8, int i9) {
        this.t.g();
        a(i, i2, i3, i4, i5, i6);
        this.t.a(c(), i, i2, i3, this.k, i4, i5, i6, allocation.a(this.t), i7, i8, i9, allocation.k);
    }

    public void copyTo(Bitmap bitmap) {
        this.t.g();
        b(bitmap);
        c(bitmap);
        this.t.a(a(this.t), bitmap);
    }

    private void b(Object obj, Element.DataType dataType, int i) {
        this.t.g();
        boolean z = this.j && this.a.getElement().getVectorSize() == 3;
        if (z) {
            if (dataType.mSize * i < (this.d / 4) * 3) {
                throw new RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
            }
        } else if (dataType.mSize * i < this.d) {
            throw new RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
        }
        this.t.a(a(this.t), obj, dataType, this.a.h.g.mSize, z);
    }

    public void copyTo(Object obj) {
        b(obj, a(obj, true), Array.getLength(obj));
    }

    public void copyTo(byte[] bArr) {
        g();
        b(bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyTo(short[] sArr) {
        f();
        b(sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyTo(int[] iArr) {
        e();
        b(iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyTo(float[] fArr) {
        h();
        b(fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    private void b(int i, int i2, Object obj, Element.DataType dataType, int i3) {
        boolean z;
        Element.DataType dataType2;
        int bytesSize = this.a.h.getBytesSize() * i2;
        if (!this.j || this.a.getElement().getVectorSize() != 3) {
            z = false;
            dataType2 = dataType;
        } else {
            z = true;
            dataType2 = dataType;
        }
        a(i, i2, i3 * dataType2.mSize, bytesSize, z);
        this.t.b(c(), i, this.k, i2, obj, bytesSize, dataType, this.a.h.g.mSize, z);
    }

    public void copy1DRangeToUnchecked(int i, int i2, Object obj) {
        b(i, i2, obj, a(obj, false), Array.getLength(obj));
    }

    public void copy1DRangeToUnchecked(int i, int i2, int[] iArr) {
        b(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, short[] sArr) {
        b(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, byte[] bArr) {
        b(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, float[] fArr) {
        b(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeTo(int i, int i2, Object obj) {
        b(i, i2, obj, a(obj, true), Array.getLength(obj));
    }

    public void copy1DRangeTo(int i, int i2, int[] iArr) {
        e();
        b(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeTo(int i, int i2, short[] sArr) {
        f();
        b(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeTo(int i, int i2, byte[] bArr) {
        g();
        b(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeTo(int i, int i2, float[] fArr) {
        h();
        b(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    void b(int i, int i2, int i3, int i4, Object obj, Element.DataType dataType, int i5) {
        boolean z;
        int i6;
        this.t.g();
        a(i, i2, i3, i4);
        int bytesSize = this.a.h.getBytesSize() * i3 * i4;
        int i7 = dataType.mSize * i5;
        if (!this.j || this.a.getElement().getVectorSize() != 3) {
            if (bytesSize <= i7) {
                z = false;
                i6 = i7;
            } else {
                throw new RSIllegalArgumentException("Array too small for allocation type.");
            }
        } else if ((bytesSize / 4) * 3 <= i7) {
            z = true;
            i6 = bytesSize;
        } else {
            throw new RSIllegalArgumentException("Array too small for allocation type.");
        }
        this.t.b(c(), i, i2, this.k, this.l.mID, i3, i4, obj, i6, dataType, this.a.h.g.mSize, z);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, Object obj) {
        b(i, i2, i3, i4, obj, a(obj, true), Array.getLength(obj));
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, byte[] bArr) {
        g();
        b(i, i2, i3, i4, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, short[] sArr) {
        f();
        b(i, i2, i3, i4, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, int[] iArr) {
        e();
        b(i, i2, i3, i4, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, float[] fArr) {
        h();
        b(i, i2, i3, i4, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    static {
        s.inScaled = false;
    }

    public static Allocation createTyped(RenderScript renderScript, Type type, MipmapControl mipmapControl, int i) {
        renderScript.g();
        if (type.a(renderScript) == 0) {
            throw new RSInvalidStateException("Bad Type");
        } else if (renderScript.h() || (i & 32) == 0) {
            long a = renderScript.a(type.a(renderScript), mipmapControl.mID, i, 0L);
            if (a != 0) {
                return new Allocation(a, renderScript, type, i);
            }
            throw new RSRuntimeException("Allocation creation failed.");
        } else {
            throw new RSRuntimeException("USAGE_IO not supported, Allocation creation failed.");
        }
    }

    public static Allocation createTyped(RenderScript renderScript, Type type, int i) {
        return createTyped(renderScript, type, MipmapControl.MIPMAP_NONE, i);
    }

    public static Allocation createTyped(RenderScript renderScript, Type type) {
        return createTyped(renderScript, type, MipmapControl.MIPMAP_NONE, 1);
    }

    public static Allocation createSized(RenderScript renderScript, Element element, int i, int i2) {
        renderScript.g();
        Type.Builder builder = new Type.Builder(renderScript, element);
        builder.setX(i);
        Type create = builder.create();
        long a = renderScript.a(create.a(renderScript), MipmapControl.MIPMAP_NONE.mID, i2, 0L);
        if (a != 0) {
            return new Allocation(a, renderScript, create, i2);
        }
        throw new RSRuntimeException("Allocation creation failed.");
    }

    public static Allocation createSized(RenderScript renderScript, Element element, int i) {
        return createSized(renderScript, element, i, 1);
    }

    static Element a(RenderScript renderScript, Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config == Bitmap.Config.ALPHA_8) {
            return Element.A_8(renderScript);
        }
        if (config == Bitmap.Config.ARGB_4444) {
            return Element.RGBA_4444(renderScript);
        }
        if (config == Bitmap.Config.ARGB_8888) {
            return Element.RGBA_8888(renderScript);
        }
        if (config == Bitmap.Config.RGB_565) {
            return Element.RGB_565(renderScript);
        }
        throw new RSInvalidStateException("Bad bitmap type: " + config);
    }

    static Type a(RenderScript renderScript, Bitmap bitmap, MipmapControl mipmapControl) {
        Type.Builder builder = new Type.Builder(renderScript, a(renderScript, bitmap));
        builder.setX(bitmap.getWidth());
        builder.setY(bitmap.getHeight());
        builder.setMipmaps(mipmapControl == MipmapControl.MIPMAP_FULL);
        return builder.create();
    }

    public static Allocation createFromBitmap(RenderScript renderScript, Bitmap bitmap, MipmapControl mipmapControl, int i) {
        renderScript.g();
        if (bitmap.getConfig() != null) {
            Type a = a(renderScript, bitmap, mipmapControl);
            if (mipmapControl == MipmapControl.MIPMAP_NONE && a.getElement().isCompatible(Element.RGBA_8888(renderScript)) && i == 131) {
                long b = renderScript.b(a.a(renderScript), mipmapControl.mID, bitmap, i);
                if (b != 0) {
                    Allocation allocation = new Allocation(b, renderScript, a, i);
                    allocation.a(bitmap);
                    return allocation;
                }
                throw new RSRuntimeException("Load failed.");
            }
            long a2 = renderScript.a(a.a(renderScript), mipmapControl.mID, bitmap, i);
            if (a2 != 0) {
                return new Allocation(a2, renderScript, a, i);
            }
            throw new RSRuntimeException("Load failed.");
        } else if ((i & 128) == 0) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            return createFromBitmap(renderScript, createBitmap, mipmapControl, i);
        } else {
            throw new RSIllegalArgumentException("USAGE_SHARED cannot be used with a Bitmap that has a null config.");
        }
    }

    public void setSurface(Surface surface) {
        this.t.g();
        if ((this.c & 64) != 0) {
            this.t.a(a(this.t), surface);
            return;
        }
        throw new RSInvalidStateException("Allocation is not USAGE_IO_OUTPUT.");
    }

    public static Allocation createFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        return createFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, ScriptIntrinsicBLAS.NON_UNIT);
    }

    public static Allocation createCubemapFromBitmap(RenderScript renderScript, Bitmap bitmap, MipmapControl mipmapControl, int i) {
        renderScript.g();
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (width % 6 != 0) {
            throw new RSIllegalArgumentException("Cubemap height must be multiple of 6");
        } else if (width / 6 == height) {
            boolean z = false;
            if (((height + (-1)) & height) == 0) {
                Element a = a(renderScript, bitmap);
                Type.Builder builder = new Type.Builder(renderScript, a);
                builder.setX(height);
                builder.setY(height);
                builder.setFaces(true);
                if (mipmapControl == MipmapControl.MIPMAP_FULL) {
                    z = true;
                }
                builder.setMipmaps(z);
                Type create = builder.create();
                long c = renderScript.c(create.a(renderScript), mipmapControl.mID, bitmap, i);
                if (c != 0) {
                    return new Allocation(c, renderScript, create, i);
                }
                throw new RSRuntimeException("Load failed for bitmap " + bitmap + " element " + a);
            }
            throw new RSIllegalArgumentException("Only power of 2 cube faces supported");
        } else {
            throw new RSIllegalArgumentException("Only square cube map faces supported");
        }
    }

    public static Allocation createCubemapFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        return createCubemapFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createCubemapFromCubeFaces(RenderScript renderScript, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6) {
        return createCubemapFromCubeFaces(renderScript, bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createFromBitmapResource(RenderScript renderScript, Resources resources, int i, MipmapControl mipmapControl, int i2) {
        renderScript.g();
        if ((i2 & 224) == 0) {
            Bitmap decodeResource = BitmapFactory.decodeResource(resources, i);
            Allocation createFromBitmap = createFromBitmap(renderScript, decodeResource, mipmapControl, i2);
            decodeResource.recycle();
            return createFromBitmap;
        }
        throw new RSIllegalArgumentException("Unsupported usage specified.");
    }

    public static Allocation createFromBitmapResource(RenderScript renderScript, Resources resources, int i) {
        return createFromBitmapResource(renderScript, resources, i, MipmapControl.MIPMAP_NONE, 3);
    }

    public static Allocation createFromString(RenderScript renderScript, String str, int i) {
        renderScript.g();
        try {
            byte[] bytes = str.getBytes("UTF-8");
            Allocation createSized = createSized(renderScript, Element.U8(renderScript), bytes.length, i);
            createSized.copyFrom(bytes);
            return createSized;
        } catch (Exception unused) {
            throw new RSRuntimeException("Could not convert string to utf-8.");
        }
    }

    @Override // androidx.renderscript.BaseObj
    public void destroy() {
        if (this.q != 0) {
            boolean z = false;
            synchronized (this) {
                if (!this.r) {
                    this.r = true;
                    z = true;
                }
            }
            if (z) {
                ReentrantReadWriteLock.ReadLock readLock = this.t.m.readLock();
                readLock.lock();
                if (this.t.i()) {
                    this.t.h(this.q);
                }
                readLock.unlock();
                this.q = 0L;
            }
        }
        if ((this.c & 96) != 0) {
            setSurface(null);
        }
        super.destroy();
    }
}
