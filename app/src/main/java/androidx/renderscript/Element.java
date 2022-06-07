package androidx.renderscript;

/* loaded from: classes.dex */
public class Element extends BaseObj {
    int a;
    Element[] b;
    String[] c;
    int[] d;
    int[] e;
    int[] f;
    DataType g;
    DataKind h;
    boolean i;
    int j;

    private void c() {
        if (this.b != null) {
            int length = this.c.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (this.c[i2].charAt(0) != '#') {
                    i++;
                }
            }
            this.f = new int[i];
            int i3 = 0;
            for (int i4 = 0; i4 < length; i4++) {
                if (this.c[i4].charAt(0) != '#') {
                    i3++;
                    this.f[i3] = i4;
                }
            }
        }
    }

    public int getBytesSize() {
        return this.a;
    }

    public int getVectorSize() {
        return this.j;
    }

    /* loaded from: classes.dex */
    public enum DataType {
        NONE(0, 0),
        FLOAT_32(2, 4),
        FLOAT_64(3, 8),
        SIGNED_8(4, 1),
        SIGNED_16(5, 2),
        SIGNED_32(6, 4),
        SIGNED_64(7, 8),
        UNSIGNED_8(8, 1),
        UNSIGNED_16(9, 2),
        UNSIGNED_32(10, 4),
        UNSIGNED_64(11, 8),
        BOOLEAN(12, 1),
        UNSIGNED_5_6_5(13, 2),
        UNSIGNED_5_5_5_1(14, 2),
        UNSIGNED_4_4_4_4(15, 2),
        MATRIX_4X4(16, 64),
        MATRIX_3X3(17, 36),
        MATRIX_2X2(18, 16),
        RS_ELEMENT(1000),
        RS_TYPE(1001),
        RS_ALLOCATION(1002),
        RS_SAMPLER(1003),
        RS_SCRIPT(1004);
        
        int mID;
        int mSize;

        DataType(int i, int i2) {
            this.mID = i;
            this.mSize = i2;
        }

        DataType(int i) {
            this.mID = i;
            this.mSize = 4;
            if (RenderScript.g == 8) {
                this.mSize = 32;
            }
        }
    }

    /* loaded from: classes.dex */
    public enum DataKind {
        USER(0),
        PIXEL_L(7),
        PIXEL_A(8),
        PIXEL_LA(9),
        PIXEL_RGB(10),
        PIXEL_RGBA(11),
        PIXEL_DEPTH(12),
        PIXEL_YUV(13);
        
        int mID;

        DataKind(int i) {
            this.mID = i;
        }
    }

    public boolean isComplex() {
        if (this.b == null) {
            return false;
        }
        int i = 0;
        while (true) {
            Element[] elementArr = this.b;
            if (i >= elementArr.length) {
                return false;
            }
            if (elementArr[i].b != null) {
                return true;
            }
            i++;
        }
    }

    public int getSubElementCount() {
        int[] iArr = this.f;
        if (iArr == null) {
            return 0;
        }
        return iArr.length;
    }

    public Element getSubElement(int i) {
        int[] iArr = this.f;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        } else if (i >= 0 && i < iArr.length) {
            return this.b[iArr[i]];
        } else {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
    }

    public String getSubElementName(int i) {
        int[] iArr = this.f;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        } else if (i >= 0 && i < iArr.length) {
            return this.c[iArr[i]];
        } else {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
    }

    public int getSubElementArraySize(int i) {
        int[] iArr = this.f;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        } else if (i >= 0 && i < iArr.length) {
            return this.d[iArr[i]];
        } else {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
    }

    public int getSubElementOffsetBytes(int i) {
        int[] iArr = this.f;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        } else if (i >= 0 && i < iArr.length) {
            return this.e[iArr[i]];
        } else {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
    }

    public DataType getDataType() {
        return this.g;
    }

    public DataKind getDataKind() {
        return this.h;
    }

    public static Element BOOLEAN(RenderScript renderScript) {
        if (renderScript.y == null) {
            renderScript.y = a(renderScript, DataType.BOOLEAN);
        }
        return renderScript.y;
    }

    public static Element U8(RenderScript renderScript) {
        if (renderScript.o == null) {
            renderScript.o = a(renderScript, DataType.UNSIGNED_8);
        }
        return renderScript.o;
    }

    public static Element I8(RenderScript renderScript) {
        if (renderScript.p == null) {
            renderScript.p = a(renderScript, DataType.SIGNED_8);
        }
        return renderScript.p;
    }

    public static Element U16(RenderScript renderScript) {
        if (renderScript.q == null) {
            renderScript.q = a(renderScript, DataType.UNSIGNED_16);
        }
        return renderScript.q;
    }

    public static Element I16(RenderScript renderScript) {
        if (renderScript.r == null) {
            renderScript.r = a(renderScript, DataType.SIGNED_16);
        }
        return renderScript.r;
    }

    public static Element U32(RenderScript renderScript) {
        if (renderScript.s == null) {
            renderScript.s = a(renderScript, DataType.UNSIGNED_32);
        }
        return renderScript.s;
    }

    public static Element I32(RenderScript renderScript) {
        if (renderScript.t == null) {
            renderScript.t = a(renderScript, DataType.SIGNED_32);
        }
        return renderScript.t;
    }

    public static Element U64(RenderScript renderScript) {
        if (renderScript.u == null) {
            renderScript.u = a(renderScript, DataType.UNSIGNED_64);
        }
        return renderScript.u;
    }

    public static Element I64(RenderScript renderScript) {
        if (renderScript.v == null) {
            renderScript.v = a(renderScript, DataType.SIGNED_64);
        }
        return renderScript.v;
    }

    public static Element F32(RenderScript renderScript) {
        if (renderScript.w == null) {
            renderScript.w = a(renderScript, DataType.FLOAT_32);
        }
        return renderScript.w;
    }

    public static Element F64(RenderScript renderScript) {
        if (renderScript.x == null) {
            renderScript.x = a(renderScript, DataType.FLOAT_64);
        }
        return renderScript.x;
    }

    public static Element ELEMENT(RenderScript renderScript) {
        if (renderScript.z == null) {
            renderScript.z = a(renderScript, DataType.RS_ELEMENT);
        }
        return renderScript.z;
    }

    public static Element TYPE(RenderScript renderScript) {
        if (renderScript.A == null) {
            renderScript.A = a(renderScript, DataType.RS_TYPE);
        }
        return renderScript.A;
    }

    public static Element ALLOCATION(RenderScript renderScript) {
        if (renderScript.B == null) {
            renderScript.B = a(renderScript, DataType.RS_ALLOCATION);
        }
        return renderScript.B;
    }

    public static Element SAMPLER(RenderScript renderScript) {
        if (renderScript.C == null) {
            renderScript.C = a(renderScript, DataType.RS_SAMPLER);
        }
        return renderScript.C;
    }

    public static Element SCRIPT(RenderScript renderScript) {
        if (renderScript.D == null) {
            renderScript.D = a(renderScript, DataType.RS_SCRIPT);
        }
        return renderScript.D;
    }

    public static Element A_8(RenderScript renderScript) {
        if (renderScript.E == null) {
            renderScript.E = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_A);
        }
        return renderScript.E;
    }

    public static Element RGB_565(RenderScript renderScript) {
        if (renderScript.F == null) {
            renderScript.F = createPixel(renderScript, DataType.UNSIGNED_5_6_5, DataKind.PIXEL_RGB);
        }
        return renderScript.F;
    }

    public static Element RGB_888(RenderScript renderScript) {
        if (renderScript.G == null) {
            renderScript.G = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_RGB);
        }
        return renderScript.G;
    }

    public static Element RGBA_5551(RenderScript renderScript) {
        if (renderScript.H == null) {
            renderScript.H = createPixel(renderScript, DataType.UNSIGNED_5_5_5_1, DataKind.PIXEL_RGBA);
        }
        return renderScript.H;
    }

    public static Element RGBA_4444(RenderScript renderScript) {
        if (renderScript.I == null) {
            renderScript.I = createPixel(renderScript, DataType.UNSIGNED_4_4_4_4, DataKind.PIXEL_RGBA);
        }
        return renderScript.I;
    }

    public static Element RGBA_8888(RenderScript renderScript) {
        if (renderScript.J == null) {
            renderScript.J = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_RGBA);
        }
        return renderScript.J;
    }

    public static Element F32_2(RenderScript renderScript) {
        if (renderScript.K == null) {
            renderScript.K = createVector(renderScript, DataType.FLOAT_32, 2);
        }
        return renderScript.K;
    }

    public static Element F32_3(RenderScript renderScript) {
        if (renderScript.L == null) {
            renderScript.L = createVector(renderScript, DataType.FLOAT_32, 3);
        }
        return renderScript.L;
    }

    public static Element F32_4(RenderScript renderScript) {
        if (renderScript.M == null) {
            renderScript.M = createVector(renderScript, DataType.FLOAT_32, 4);
        }
        return renderScript.M;
    }

    public static Element F64_2(RenderScript renderScript) {
        if (renderScript.N == null) {
            renderScript.N = createVector(renderScript, DataType.FLOAT_64, 2);
        }
        return renderScript.N;
    }

    public static Element F64_3(RenderScript renderScript) {
        if (renderScript.O == null) {
            renderScript.O = createVector(renderScript, DataType.FLOAT_64, 3);
        }
        return renderScript.O;
    }

    public static Element F64_4(RenderScript renderScript) {
        if (renderScript.P == null) {
            renderScript.P = createVector(renderScript, DataType.FLOAT_64, 4);
        }
        return renderScript.P;
    }

    public static Element U8_2(RenderScript renderScript) {
        if (renderScript.Q == null) {
            renderScript.Q = createVector(renderScript, DataType.UNSIGNED_8, 2);
        }
        return renderScript.Q;
    }

    public static Element U8_3(RenderScript renderScript) {
        if (renderScript.R == null) {
            renderScript.R = createVector(renderScript, DataType.UNSIGNED_8, 3);
        }
        return renderScript.R;
    }

    public static Element U8_4(RenderScript renderScript) {
        if (renderScript.S == null) {
            renderScript.S = createVector(renderScript, DataType.UNSIGNED_8, 4);
        }
        return renderScript.S;
    }

    public static Element I8_2(RenderScript renderScript) {
        if (renderScript.T == null) {
            renderScript.T = createVector(renderScript, DataType.SIGNED_8, 2);
        }
        return renderScript.T;
    }

    public static Element I8_3(RenderScript renderScript) {
        if (renderScript.U == null) {
            renderScript.U = createVector(renderScript, DataType.SIGNED_8, 3);
        }
        return renderScript.U;
    }

    public static Element I8_4(RenderScript renderScript) {
        if (renderScript.V == null) {
            renderScript.V = createVector(renderScript, DataType.SIGNED_8, 4);
        }
        return renderScript.V;
    }

    public static Element U16_2(RenderScript renderScript) {
        if (renderScript.W == null) {
            renderScript.W = createVector(renderScript, DataType.UNSIGNED_16, 2);
        }
        return renderScript.W;
    }

    public static Element U16_3(RenderScript renderScript) {
        if (renderScript.X == null) {
            renderScript.X = createVector(renderScript, DataType.UNSIGNED_16, 3);
        }
        return renderScript.X;
    }

    public static Element U16_4(RenderScript renderScript) {
        if (renderScript.Y == null) {
            renderScript.Y = createVector(renderScript, DataType.UNSIGNED_16, 4);
        }
        return renderScript.Y;
    }

    public static Element I16_2(RenderScript renderScript) {
        if (renderScript.Z == null) {
            renderScript.Z = createVector(renderScript, DataType.SIGNED_16, 2);
        }
        return renderScript.Z;
    }

    public static Element I16_3(RenderScript renderScript) {
        if (renderScript.aa == null) {
            renderScript.aa = createVector(renderScript, DataType.SIGNED_16, 3);
        }
        return renderScript.aa;
    }

    public static Element I16_4(RenderScript renderScript) {
        if (renderScript.ab == null) {
            renderScript.ab = createVector(renderScript, DataType.SIGNED_16, 4);
        }
        return renderScript.ab;
    }

    public static Element U32_2(RenderScript renderScript) {
        if (renderScript.ac == null) {
            renderScript.ac = createVector(renderScript, DataType.UNSIGNED_32, 2);
        }
        return renderScript.ac;
    }

    public static Element U32_3(RenderScript renderScript) {
        if (renderScript.ad == null) {
            renderScript.ad = createVector(renderScript, DataType.UNSIGNED_32, 3);
        }
        return renderScript.ad;
    }

    public static Element U32_4(RenderScript renderScript) {
        if (renderScript.ae == null) {
            renderScript.ae = createVector(renderScript, DataType.UNSIGNED_32, 4);
        }
        return renderScript.ae;
    }

    public static Element I32_2(RenderScript renderScript) {
        if (renderScript.af == null) {
            renderScript.af = createVector(renderScript, DataType.SIGNED_32, 2);
        }
        return renderScript.af;
    }

    public static Element I32_3(RenderScript renderScript) {
        if (renderScript.ag == null) {
            renderScript.ag = createVector(renderScript, DataType.SIGNED_32, 3);
        }
        return renderScript.ag;
    }

    public static Element I32_4(RenderScript renderScript) {
        if (renderScript.ah == null) {
            renderScript.ah = createVector(renderScript, DataType.SIGNED_32, 4);
        }
        return renderScript.ah;
    }

    public static Element U64_2(RenderScript renderScript) {
        if (renderScript.ai == null) {
            renderScript.ai = createVector(renderScript, DataType.UNSIGNED_64, 2);
        }
        return renderScript.ai;
    }

    public static Element U64_3(RenderScript renderScript) {
        if (renderScript.aj == null) {
            renderScript.aj = createVector(renderScript, DataType.UNSIGNED_64, 3);
        }
        return renderScript.aj;
    }

    public static Element U64_4(RenderScript renderScript) {
        if (renderScript.ak == null) {
            renderScript.ak = createVector(renderScript, DataType.UNSIGNED_64, 4);
        }
        return renderScript.ak;
    }

    public static Element I64_2(RenderScript renderScript) {
        if (renderScript.al == null) {
            renderScript.al = createVector(renderScript, DataType.SIGNED_64, 2);
        }
        return renderScript.al;
    }

    public static Element I64_3(RenderScript renderScript) {
        if (renderScript.am == null) {
            renderScript.am = createVector(renderScript, DataType.SIGNED_64, 3);
        }
        return renderScript.am;
    }

    public static Element I64_4(RenderScript renderScript) {
        if (renderScript.an == null) {
            renderScript.an = createVector(renderScript, DataType.SIGNED_64, 4);
        }
        return renderScript.an;
    }

    public static Element MATRIX_4X4(RenderScript renderScript) {
        if (renderScript.ao == null) {
            renderScript.ao = a(renderScript, DataType.MATRIX_4X4);
        }
        return renderScript.ao;
    }

    public static Element MATRIX_3X3(RenderScript renderScript) {
        if (renderScript.ap == null) {
            renderScript.ap = a(renderScript, DataType.MATRIX_3X3);
        }
        return renderScript.ap;
    }

    public static Element MATRIX_2X2(RenderScript renderScript) {
        if (renderScript.aq == null) {
            renderScript.aq = a(renderScript, DataType.MATRIX_2X2);
        }
        return renderScript.aq;
    }

    Element(long j, RenderScript renderScript, Element[] elementArr, String[] strArr, int[] iArr) {
        super(j, renderScript);
        int i = 0;
        this.a = 0;
        this.j = 1;
        this.b = elementArr;
        this.c = strArr;
        this.d = iArr;
        this.g = DataType.NONE;
        this.h = DataKind.USER;
        this.e = new int[this.b.length];
        while (true) {
            Element[] elementArr2 = this.b;
            if (i < elementArr2.length) {
                int[] iArr2 = this.e;
                int i2 = this.a;
                iArr2[i] = i2;
                this.a = i2 + (elementArr2[i].a * this.d[i]);
                i++;
            } else {
                c();
                return;
            }
        }
    }

    Element(long j, RenderScript renderScript, DataType dataType, DataKind dataKind, boolean z, int i) {
        super(j, renderScript);
        if (dataType == DataType.UNSIGNED_5_6_5 || dataType == DataType.UNSIGNED_4_4_4_4 || dataType == DataType.UNSIGNED_5_5_5_1) {
            this.a = dataType.mSize;
        } else if (i == 3) {
            this.a = dataType.mSize * 4;
        } else {
            this.a = dataType.mSize * i;
        }
        this.g = dataType;
        this.h = dataKind;
        this.i = z;
        this.j = i;
    }

    public long getDummyElement(RenderScript renderScript) {
        return renderScript.b(this.g.mID, this.h.mID, this.i, this.j);
    }

    static Element a(RenderScript renderScript, DataType dataType) {
        DataKind dataKind = DataKind.USER;
        return new Element(renderScript.a(dataType.mID, dataKind.mID, false, 1), renderScript, dataType, dataKind, false, 1);
    }

    public static Element createVector(RenderScript renderScript, DataType dataType, int i) {
        if (i < 2 || i > 4) {
            throw new RSIllegalArgumentException("Vector size out of range 2-4.");
        }
        switch (dataType) {
            case FLOAT_32:
            case FLOAT_64:
            case SIGNED_8:
            case SIGNED_16:
            case SIGNED_32:
            case SIGNED_64:
            case UNSIGNED_8:
            case UNSIGNED_16:
            case UNSIGNED_32:
            case UNSIGNED_64:
            case BOOLEAN:
                DataKind dataKind = DataKind.USER;
                return new Element(renderScript.a(dataType.mID, dataKind.mID, false, i), renderScript, dataType, dataKind, false, i);
            default:
                throw new RSIllegalArgumentException("Cannot create vector of non-primitive type.");
        }
    }

    public static Element createPixel(RenderScript renderScript, DataType dataType, DataKind dataKind) {
        if (dataKind != DataKind.PIXEL_L && dataKind != DataKind.PIXEL_A && dataKind != DataKind.PIXEL_LA && dataKind != DataKind.PIXEL_RGB && dataKind != DataKind.PIXEL_RGBA && dataKind != DataKind.PIXEL_DEPTH && dataKind != DataKind.PIXEL_YUV) {
            throw new RSIllegalArgumentException("Unsupported DataKind");
        } else if (dataType != DataType.UNSIGNED_8 && dataType != DataType.UNSIGNED_16 && dataType != DataType.UNSIGNED_5_6_5 && dataType != DataType.UNSIGNED_4_4_4_4 && dataType != DataType.UNSIGNED_5_5_5_1) {
            throw new RSIllegalArgumentException("Unsupported DataType");
        } else if (dataType == DataType.UNSIGNED_5_6_5 && dataKind != DataKind.PIXEL_RGB) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        } else if (dataType == DataType.UNSIGNED_5_5_5_1 && dataKind != DataKind.PIXEL_RGBA) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        } else if (dataType == DataType.UNSIGNED_4_4_4_4 && dataKind != DataKind.PIXEL_RGBA) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        } else if (dataType != DataType.UNSIGNED_16 || dataKind == DataKind.PIXEL_DEPTH) {
            int i = 1;
            switch (dataKind) {
                case PIXEL_LA:
                    i = 2;
                    break;
                case PIXEL_RGB:
                    i = 3;
                    break;
                case PIXEL_RGBA:
                    i = 4;
                    break;
            }
            return new Element(renderScript.a(dataType.mID, dataKind.mID, true, i), renderScript, dataType, dataKind, true, i);
        } else {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
    }

    public boolean isCompatible(Element element) {
        if (equals(element)) {
            return true;
        }
        return this.a == element.a && this.g != DataType.NONE && this.g == element.g && this.j == element.j;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        RenderScript a;
        int f;
        int e = 0;
        Element[] b = new Element[8];
        String[] c = new String[8];
        int[] d = new int[8];

        public Builder(RenderScript renderScript) {
            this.a = renderScript;
        }

        public Builder add(Element element, String str, int i) {
            if (i < 1) {
                throw new RSIllegalArgumentException("Array size cannot be less than 1.");
            } else if (this.f == 0 || !str.startsWith("#padding_")) {
                if (element.j == 3) {
                    this.f = 1;
                } else {
                    this.f = 0;
                }
                int i2 = this.e;
                Element[] elementArr = this.b;
                if (i2 == elementArr.length) {
                    Element[] elementArr2 = new Element[i2 + 8];
                    String[] strArr = new String[i2 + 8];
                    int[] iArr = new int[i2 + 8];
                    System.arraycopy(elementArr, 0, elementArr2, 0, i2);
                    System.arraycopy(this.c, 0, strArr, 0, this.e);
                    System.arraycopy(this.d, 0, iArr, 0, this.e);
                    this.b = elementArr2;
                    this.c = strArr;
                    this.d = iArr;
                }
                Element[] elementArr3 = this.b;
                int i3 = this.e;
                elementArr3[i3] = element;
                this.c[i3] = str;
                this.d[i3] = i;
                this.e = i3 + 1;
                return this;
            } else {
                this.f = 0;
                return this;
            }
        }

        public Builder add(Element element, String str) {
            return add(element, str, 1);
        }

        public Element create() {
            this.a.g();
            int i = this.e;
            Element[] elementArr = new Element[i];
            String[] strArr = new String[i];
            int[] iArr = new int[i];
            System.arraycopy(this.b, 0, elementArr, 0, i);
            System.arraycopy(this.c, 0, strArr, 0, this.e);
            System.arraycopy(this.d, 0, iArr, 0, this.e);
            long[] jArr = new long[elementArr.length];
            for (int i2 = 0; i2 < elementArr.length; i2++) {
                jArr[i2] = elementArr[i2].a(this.a);
            }
            return new Element(this.a.a(jArr, strArr, iArr), this.a, elementArr, strArr, iArr);
        }
    }
}
