package androidx.renderscript;

/* loaded from: classes.dex */
public class Type extends BaseObj {
    int a;
    int b;
    int c;
    boolean d;
    boolean e;
    int f;
    int g;
    Element h;

    /* loaded from: classes.dex */
    public enum CubemapFace {
        POSITIVE_X(0),
        NEGATIVE_X(1),
        POSITIVE_Y(2),
        NEGATIVE_Y(3),
        POSITIVE_Z(4),
        NEGATIVE_Z(5);
        
        int mID;

        CubemapFace(int i) {
            this.mID = i;
        }
    }

    public Element getElement() {
        return this.h;
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public int getZ() {
        return this.c;
    }

    public int getYuv() {
        return this.f;
    }

    public boolean hasMipmaps() {
        return this.d;
    }

    public boolean hasFaces() {
        return this.e;
    }

    public int getCount() {
        return this.g;
    }

    void c() {
        boolean hasMipmaps = hasMipmaps();
        int x = getX();
        int y = getY();
        int z = getZ();
        int i = hasFaces() ? 6 : 1;
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }
        if (z == 0) {
            z = 1;
        }
        int i2 = x * y * z * i;
        while (hasMipmaps && (x > 1 || y > 1 || z > 1)) {
            if (x > 1) {
                x >>= 1;
            }
            if (y > 1) {
                y >>= 1;
            }
            if (z > 1) {
                z >>= 1;
            }
            i2 += x * y * z * i;
        }
        this.g = i2;
    }

    Type(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public long getDummyType(RenderScript renderScript, long j) {
        return renderScript.b(j, this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public static Type createX(RenderScript renderScript, Element element, int i) {
        if (i >= 1) {
            Type type = new Type(renderScript.a(element.a(renderScript), i, 0, 0, false, false, 0), renderScript);
            type.h = element;
            type.a = i;
            type.c();
            return type;
        }
        throw new RSInvalidStateException("Dimension must be >= 1.");
    }

    public static Type createXY(RenderScript renderScript, Element element, int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new RSInvalidStateException("Dimension must be >= 1.");
        }
        Type type = new Type(renderScript.a(element.a(renderScript), i, i2, 0, false, false, 0), renderScript);
        type.h = element;
        type.a = i;
        type.b = i2;
        type.c();
        return type;
    }

    public static Type createXYZ(RenderScript renderScript, Element element, int i, int i2, int i3) {
        if (i < 1 || i2 < 1 || i3 < 1) {
            throw new RSInvalidStateException("Dimension must be >= 1.");
        }
        Type type = new Type(renderScript.a(element.a(renderScript), i, i2, i3, false, false, 0), renderScript);
        type.h = element;
        type.a = i;
        type.b = i2;
        type.c = i3;
        type.c();
        return type;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        RenderScript a;
        int b = 1;
        int c;
        int d;
        boolean e;
        boolean f;
        int g;
        Element h;

        public Builder(RenderScript renderScript, Element element) {
            element.b();
            this.a = renderScript;
            this.h = element;
        }

        public Builder setX(int i) {
            if (i >= 1) {
                this.b = i;
                return this;
            }
            throw new RSIllegalArgumentException("Values of less than 1 for Dimension X are not valid.");
        }

        public Builder setY(int i) {
            if (i >= 1) {
                this.c = i;
                return this;
            }
            throw new RSIllegalArgumentException("Values of less than 1 for Dimension Y are not valid.");
        }

        public Builder setZ(int i) {
            if (i >= 1) {
                this.d = i;
                return this;
            }
            throw new RSIllegalArgumentException("Values of less than 1 for Dimension Z are not valid.");
        }

        public Builder setMipmaps(boolean z) {
            this.e = z;
            return this;
        }

        public Builder setFaces(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setYuvFormat(int i) {
            if (i == 17 || i == 842094169) {
                this.g = i;
                return this;
            }
            throw new RSIllegalArgumentException("Only NV21 and YV12 are supported..");
        }

        public Type create() {
            if (this.d > 0) {
                if (this.b < 1 || this.c < 1) {
                    throw new RSInvalidStateException("Both X and Y dimension required when Z is present.");
                } else if (this.f) {
                    throw new RSInvalidStateException("Cube maps not supported with 3D types.");
                }
            }
            if (this.c > 0 && this.b < 1) {
                throw new RSInvalidStateException("X dimension required when Y is present.");
            } else if (this.f && this.c < 1) {
                throw new RSInvalidStateException("Cube maps require 2D Types.");
            } else if (this.g == 0 || (this.d == 0 && !this.f && !this.e)) {
                RenderScript renderScript = this.a;
                Type type = new Type(renderScript.a(this.h.a(renderScript), this.b, this.c, this.d, this.e, this.f, this.g), this.a);
                type.h = this.h;
                type.a = this.b;
                type.b = this.c;
                type.c = this.d;
                type.d = this.e;
                type.e = this.f;
                type.f = this.g;
                type.c();
                return type;
            } else {
                throw new RSInvalidStateException("YUV only supports basic 2D.");
            }
        }
    }
}
