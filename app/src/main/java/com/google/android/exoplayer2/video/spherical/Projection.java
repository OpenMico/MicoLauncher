package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
final class Projection {
    public final Mesh a;
    public final Mesh b;
    public final int c;
    public final boolean d;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface DrawMode {
    }

    public static Projection a(int i) {
        return a(50.0f, 36, 72, 180.0f, 360.0f, i);
    }

    public static Projection a(float f, int i, int i2, float f2, float f3, int i3) {
        float f4 = f;
        int i4 = i;
        int i5 = i2;
        Assertions.checkArgument(f4 > 0.0f);
        Assertions.checkArgument(i4 >= 1);
        Assertions.checkArgument(i5 >= 1);
        Assertions.checkArgument(f2 > 0.0f && f2 <= 180.0f);
        Assertions.checkArgument(f3 > 0.0f && f3 <= 360.0f);
        float radians = (float) Math.toRadians(f2);
        float radians2 = (float) Math.toRadians(f3);
        float f5 = radians / i4;
        float f6 = radians2 / i5;
        int i6 = i5 + 1;
        int i7 = ((i6 * 2) + 2) * i4;
        float[] fArr = new float[i7 * 3];
        float[] fArr2 = new float[i7 * 2];
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i8 < i4) {
            float f7 = radians / 2.0f;
            float f8 = (i8 * f5) - f7;
            int i11 = i8 + 1;
            float f9 = (i11 * f5) - f7;
            int i12 = 0;
            while (i12 < i6) {
                int i13 = i10;
                int i14 = 2;
                int i15 = i9;
                int i16 = 0;
                while (i16 < i14) {
                    if (i16 == 0) {
                        f8 = f8;
                        f9 = f8;
                    } else {
                        f8 = f8;
                    }
                    float f10 = i12 * f6;
                    int i17 = i15 + 1;
                    double d = f4;
                    double d2 = (f10 + 3.1415927f) - (radians2 / 2.0f);
                    double d3 = f9;
                    fArr[i15] = -((float) (Math.sin(d2) * d * Math.cos(d3)));
                    int i18 = i17 + 1;
                    fArr[i17] = (float) (d * Math.sin(d3));
                    i15 = i18 + 1;
                    fArr[i18] = (float) (d * Math.cos(d2) * Math.cos(d3));
                    int i19 = i13 + 1;
                    fArr2[i13] = f10 / radians2;
                    i13 = i19 + 1;
                    fArr2[i19] = ((i8 + i16) * f5) / radians;
                    if (i12 == 0 && i16 == 0) {
                        i12 = i12;
                        i5 = i2;
                    } else {
                        i12 = i12;
                        i5 = i2;
                        if (!(i12 == i5 && i16 == 1)) {
                            i14 = 2;
                            i16++;
                            i8 = i8;
                            i6 = i6;
                            f6 = f6;
                            i11 = i11;
                            radians = radians;
                            f5 = f5;
                            f4 = f;
                        }
                    }
                    System.arraycopy(fArr, i15 - 3, fArr, i15, 3);
                    i15 += 3;
                    i14 = 2;
                    System.arraycopy(fArr2, i13 - 2, fArr2, i13, 2);
                    i13 += 2;
                    i16++;
                    i8 = i8;
                    i6 = i6;
                    f6 = f6;
                    i11 = i11;
                    radians = radians;
                    f5 = f5;
                    f4 = f;
                }
                i12++;
                i9 = i15;
                i10 = i13;
                f6 = f6;
                f5 = f5;
                f4 = f;
            }
            i9 = i9;
            i10 = i10;
            i8 = i11;
            f4 = f;
            i4 = i;
        }
        return new Projection(new Mesh(new SubMesh(0, fArr, fArr2, 1)), i3);
    }

    public Projection(Mesh mesh, int i) {
        this(mesh, mesh, i);
    }

    public Projection(Mesh mesh, Mesh mesh2, int i) {
        this.a = mesh;
        this.b = mesh2;
        this.c = i;
        this.d = mesh == mesh2;
    }

    /* loaded from: classes2.dex */
    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int i, float[] fArr, float[] fArr2, int i2) {
            this.textureId = i;
            Assertions.checkArgument(((long) fArr.length) * 2 == ((long) fArr2.length) * 3);
            this.vertices = fArr;
            this.textureCoords = fArr2;
            this.mode = i2;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Mesh {
        private final SubMesh[] a;

        public Mesh(SubMesh... subMeshArr) {
            this.a = subMeshArr;
        }

        public int getSubMeshCount() {
            return this.a.length;
        }

        public SubMesh getSubMesh(int i) {
            return this.a[i];
        }
    }
}
