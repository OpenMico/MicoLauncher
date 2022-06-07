package androidx.renderscript;

import android.os.Build;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ScriptIntrinsicBLAS extends ScriptIntrinsic {
    public static final int CONJ_TRANSPOSE = 113;
    public static final int LEFT = 141;
    public static final int LOWER = 122;
    public static final int NON_UNIT = 131;
    public static final int NO_TRANSPOSE = 111;
    public static final int RIGHT = 142;
    public static final int TRANSPOSE = 112;
    public static final int UNIT = 132;
    public static final int UPPER = 121;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Diag {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Side {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Transpose {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Uplo {
    }

    private ScriptIntrinsicBLAS(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicBLAS create(RenderScript renderScript) {
        boolean z = renderScript.b() && Build.VERSION.SDK_INT < 23;
        ScriptIntrinsicBLAS scriptIntrinsicBLAS = new ScriptIntrinsicBLAS(renderScript.a(13, Element.U32(renderScript).a(renderScript), z), renderScript);
        scriptIntrinsicBLAS.setIncSupp(z);
        return scriptIntrinsicBLAS;
    }

    static void a(int i) {
        if (i != 141 && i != 142) {
            throw new RSRuntimeException("Invalid side passed to BLAS");
        }
    }

    static void b(int i) {
        if (i != 111 && i != 112 && i != 113) {
            throw new RSRuntimeException("Invalid transpose passed to BLAS");
        }
    }

    static void c(int i) {
        if (i != 111 && i != 113) {
            throw new RSRuntimeException("Invalid transpose passed to BLAS");
        }
    }

    static void d(int i) {
        if (i != 131 && i != 132) {
            throw new RSRuntimeException("Invalid diag passed to BLAS");
        }
    }

    static void e(int i) {
        if (i != 121 && i != 122) {
            throw new RSRuntimeException("Invalid uplo passed to BLAS");
        }
    }

    static void a(Element element, int i, Allocation allocation, Allocation allocation2, int i2, Allocation allocation3, int i3) {
        int i4;
        int i5;
        b(i);
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation2.getType().getY() > 1 || allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        } else {
            if (i == 111) {
                i4 = ((x - 1) * i2) + 1;
                i5 = ((y - 1) * i3) + 1;
            } else {
                i4 = ((y - 1) * i2) + 1;
                i5 = ((x - 1) * i3) + 1;
            }
            if (allocation2.getType().getX() != i4 || allocation3.getType().getX() != i5) {
                throw new RSRuntimeException("Incorrect vector dimensions for GEMV");
            }
        }
    }

    public void SGEMV(int i, float f, Allocation allocation, Allocation allocation2, int i2, float f2, Allocation allocation3, int i3) {
        a(Element.F32(this.t), i, allocation, allocation2, i2, allocation3, i3);
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 47, i, 0, 0, 0, 0, y, x, 0, f, j, j2, f2, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void DGEMV(int i, double d, Allocation allocation, Allocation allocation2, int i2, double d2, Allocation allocation3, int i3) {
        a(Element.F64(this.t), i, allocation, allocation2, i2, allocation3, i3);
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 55, i, 0, 0, 0, 0, y, x, 0, d, j, j2, d2, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void CGEMV(int i, Float2 float2, Allocation allocation, Allocation allocation2, int i2, Float2 float22, Allocation allocation3, int i3) {
        a(Element.F32_2(this.t), i, allocation, allocation2, i2, allocation3, i3);
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 63, i, 0, 0, 0, 0, y, x, 0, float2.x, float2.y, j, j2, float22.x, float22.y, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void ZGEMV(int i, Double2 double2, Allocation allocation, Allocation allocation2, int i2, Double2 double22, Allocation allocation3, int i3) {
        a(Element.F64_2(this.t), i, allocation, allocation2, i2, allocation3, i3);
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 71, i, 0, 0, 0, 0, y, x, 0, double2.x, double2.y, j, j2, double22.x, double22.y, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void SGBMV(int i, int i2, int i3, float f, Allocation allocation, Allocation allocation2, int i4, float f2, Allocation allocation3, int i5) {
        a(Element.F32(this.t), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 48, i, 0, 0, 0, 0, y, x, 0, f, j, j2, f2, j3, i4, i5, i2, i3, isIncSupp);
    }

    public void DGBMV(int i, int i2, int i3, double d, Allocation allocation, Allocation allocation2, int i4, double d2, Allocation allocation3, int i5) {
        a(Element.F64(this.t), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 56, i, 0, 0, 0, 0, y, x, 0, d, j, j2, d2, j3, i4, i5, i2, i3, isIncSupp);
    }

    public void CGBMV(int i, int i2, int i3, Float2 float2, Allocation allocation, Allocation allocation2, int i4, Float2 float22, Allocation allocation3, int i5) {
        a(Element.F32_2(this.t), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 64, i, 0, 0, 0, 0, y, x, 0, float2.x, float2.y, j, j2, float22.x, float22.y, j3, i4, i5, i2, i3, isIncSupp);
    }

    public void ZGBMV(int i, int i2, int i3, Double2 double2, Allocation allocation, Allocation allocation2, int i4, Double2 double22, Allocation allocation3, int i5) {
        a(Element.F64_2(this.t), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 72, i, 0, 0, 0, 0, y, x, 0, double2.x, double2.y, j, j2, double22.x, double22.y, j3, i4, i5, i2, i3, isIncSupp);
    }

    static void a(Element element, int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        b(i2);
        e(i);
        d(i3);
        int y = allocation.getType().getY();
        if (allocation.getType().getX() != y) {
            throw new RSRuntimeException("A must be a square matrix for TRMV");
        } else if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (i4 > 0) {
            if (allocation2.getType().getX() != ((y - 1) * i4) + 1) {
                throw new RSRuntimeException("Incorrect vector dimensions for TRMV");
            }
        } else {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
    }

    static int b(Element element, int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        b(i2);
        e(i);
        d(i3);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (allocation.getType().getY() <= 1) {
            int sqrt = (int) Math.sqrt(allocation.getType().getX() * 2.0d);
            if (allocation.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
                throw new RSRuntimeException("Invalid dimension for Ap");
            } else if (i4 > 0) {
                if (allocation2.getType().getX() == ((sqrt - 1) * i4) + 1) {
                    return sqrt;
                }
                throw new RSRuntimeException("Incorrect vector dimensions for TPMV");
            } else {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            }
        } else {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
    }

    public void STRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F32(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 49, i2, 0, 0, i, i3, 0, y, 0, 0.0f, j, j2, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void DTRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F64(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 57, i2, 0, 0, i, i3, 0, y, 0, 0.0d, j, j2, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void CTRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F32_2(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 65, i2, 0, 0, i, i3, 0, y, 0, 0.0f, 0.0f, j, j2, 0.0f, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void ZTRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F64_2(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 73, i2, 0, 0, i, i3, 0, y, 0, 0.0d, 0.0d, j, j2, 0.0d, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void STBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 >= 0) {
            a(Element.F32(this.t), i, i2, i3, allocation, allocation2, i5);
            int y = allocation.getType().getY();
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 50, i2, 0, 0, i, i3, 0, y, i4, 0.0f, j, j2, 0.0f, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void DTBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 >= 0) {
            a(Element.F64(this.t), i, i2, i3, allocation, allocation2, i5);
            int y = allocation.getType().getY();
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 58, i2, 0, 0, i, i3, 0, y, i4, 0.0d, j, j2, 0.0d, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void CTBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 >= 0) {
            a(Element.F32_2(this.t), i, i2, i3, allocation, allocation2, i5);
            int y = allocation.getType().getY();
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 66, i2, 0, 0, i, i3, 0, y, i4, 0.0f, 0.0f, j, j2, 0.0f, 0.0f, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void ZTBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 >= 0) {
            a(Element.F64_2(this.t), i, i2, i3, allocation, allocation2, i5);
            int y = allocation.getType().getY();
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 74, i2, 0, 0, i, i3, 0, y, i4, 0.0d, 0.0d, j, j2, 0.0d, 0.0d, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void STPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F32(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 51, i2, 0, 0, i, i3, 0, b, 0, 0.0f, j, j2, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void DTPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F64(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 59, i2, 0, 0, i, i3, 0, b, 0, 0.0d, j, j2, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void CTPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F32_2(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 67, i2, 0, 0, i, i3, 0, b, 0, 0.0f, 0.0f, j, j2, 0.0f, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void ZTPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F64_2(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 75, i2, 0, 0, i, i3, 0, b, 0, 0.0d, 0.0d, j, j2, 0.0d, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void STRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F32(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 52, i2, 0, 0, i, i3, 0, y, 0, 0.0f, j, j2, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void DTRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F64(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 60, i2, 0, 0, i, i3, 0, y, 0, 0.0d, j, j2, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void CTRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F32_2(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 68, i2, 0, 0, i, i3, 0, y, 0, 0.0f, 0.0f, j, j2, 0.0f, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void ZTRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        a(Element.F64_2(this.t), i, i2, i3, allocation, allocation2, i4);
        int y = allocation.getType().getY();
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 76, i2, 0, 0, i, i3, 0, y, 0, 0.0d, 0.0d, j, j2, 0.0d, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void STBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        a(Element.F32(this.t), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 >= 0) {
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 53, i2, 0, 0, i, i3, 0, y, i4, 0.0f, j, j2, 0.0f, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void DTBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        a(Element.F64(this.t), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 >= 0) {
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 61, i2, 0, 0, i, i3, 0, y, i4, 0.0d, j, j2, 0.0d, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void CTBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        a(Element.F32_2(this.t), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 >= 0) {
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 69, i2, 0, 0, i, i3, 0, y, i4, 0.0f, 0.0f, j, j2, 0.0f, 0.0f, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void ZTBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        a(Element.F64_2(this.t), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 >= 0) {
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            if (isIncSupp) {
                j = a(allocation);
                j2 = a(allocation2);
            }
            this.t.a(a(this.t), 77, i2, 0, 0, i, i3, 0, y, i4, 0.0d, 0.0d, j, j2, 0.0d, 0.0d, 0L, i5, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void STPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F32(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 54, i2, 0, 0, i, i3, 0, b, 0, 0.0f, j, j2, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void DTPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F64(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 62, i2, 0, 0, i, i3, 0, b, 0, 0.0d, j, j2, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void CTPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F32_2(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 70, i2, 0, 0, i, i3, 0, b, 0, 0.0f, 0.0f, j, j2, 0.0f, 0.0f, 0L, i4, 0, 0, 0, isIncSupp);
    }

    public void ZTPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        int b = b(Element.F64_2(this.t), i, i2, i3, allocation, allocation2, i4);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 78, i2, 0, 0, i, i3, 0, b, 0, 0.0d, 0.0d, j, j2, 0.0d, 0.0d, 0L, i4, 0, 0, 0, isIncSupp);
    }

    static int a(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3, int i2, int i3) {
        e(i);
        int y = allocation.getType().getY();
        if (allocation.getType().getX() != y) {
            throw new RSRuntimeException("A must be a square matrix for SYMV");
        } else if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation2.getType().getY() > 1 || allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        } else {
            int i4 = y - 1;
            if (allocation2.getType().getX() == (i2 * i4) + 1) {
                if (allocation3.getType().getX() == (i4 * i3) + 1) {
                    return y;
                }
                throw new RSRuntimeException("Incorrect vector dimensions for SYMV");
            }
            throw new RSRuntimeException("Incorrect vector dimensions for SYMV");
        }
    }

    static int b(Element element, int i, Allocation allocation, Allocation allocation2, int i2, Allocation allocation3, int i3) {
        e(i);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation2.getType().getY() > 1 || allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (allocation.getType().getY() <= 1) {
            int sqrt = (int) Math.sqrt(allocation.getType().getX() * 2.0d);
            if (allocation.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
                throw new RSRuntimeException("Invalid dimension for Ap");
            } else if (i2 <= 0 || i3 <= 0) {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            } else {
                int i4 = sqrt - 1;
                if (allocation2.getType().getX() == (i2 * i4) + 1) {
                    if (allocation3.getType().getX() == (i4 * i3) + 1) {
                        return sqrt;
                    }
                    throw new RSRuntimeException("Incorrect vector dimensions for SPMV");
                }
                throw new RSRuntimeException("Incorrect vector dimensions for SPMV");
            }
        } else {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
    }

    static void a(Element element, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else {
            int y = allocation3.getType().getY();
            int x = allocation3.getType().getX();
            if (x < 1 || y < 1) {
                throw new RSRuntimeException("M and N must be 1 or greater for GER");
            } else if (i <= 0 || i2 <= 0) {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            } else {
                if (allocation.getType().getX() == ((y - 1) * i) + 1) {
                    if (allocation2.getType().getX() != ((x - 1) * i2) + 1) {
                        throw new RSRuntimeException("Incorrect vector dimensions for GER");
                    }
                    return;
                }
                throw new RSRuntimeException("Incorrect vector dimensions for GER");
            }
        }
    }

    static int a(Element element, int i, Allocation allocation, int i2, Allocation allocation2) {
        e(i);
        if (!allocation2.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int x = allocation2.getType().getX();
        if (allocation.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (x != allocation2.getType().getY()) {
            throw new RSRuntimeException("A must be a symmetric matrix");
        } else if (i2 > 0) {
            if (allocation.getType().getX() == ((x - 1) * i2) + 1) {
                return x;
            }
            throw new RSRuntimeException("Incorrect vector dimensions for SYR");
        } else {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
    }

    static int b(Element element, int i, Allocation allocation, int i2, Allocation allocation2) {
        e(i);
        if (!allocation2.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (allocation2.getType().getY() <= 1) {
            int sqrt = (int) Math.sqrt(allocation2.getType().getX() * 2.0d);
            if (allocation2.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
                throw new RSRuntimeException("Invalid dimension for Ap");
            } else if (i2 > 0) {
                if (allocation.getType().getX() == ((sqrt - 1) * i2) + 1) {
                    return sqrt;
                }
                throw new RSRuntimeException("Incorrect vector dimensions for SPR");
            } else {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            }
        } else {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
    }

    static int a(Element element, int i, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        e(i);
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else {
            int x = allocation3.getType().getX();
            if (x != allocation3.getType().getY()) {
                throw new RSRuntimeException("A must be a symmetric matrix");
            } else if (i2 <= 0 || i3 <= 0) {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            } else {
                int i4 = x - 1;
                int i5 = (i2 * i4) + 1;
                int i6 = (i4 * i3) + 1;
                if (allocation.getType().getX() == i5 && allocation2.getType().getX() == i6) {
                    return x;
                }
                throw new RSRuntimeException("Incorrect vector dimensions for SYR");
            }
        }
    }

    static int b(Element element, int i, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        e(i);
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else if (allocation3.getType().getY() <= 1) {
            int sqrt = (int) Math.sqrt(allocation3.getType().getX() * 2.0d);
            if (allocation3.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
                throw new RSRuntimeException("Invalid dimension for Ap");
            } else if (i2 <= 0 || i3 <= 0) {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            } else {
                int i4 = sqrt - 1;
                int i5 = (i2 * i4) + 1;
                int i6 = (i4 * i3) + 1;
                if (allocation.getType().getX() == i5 && allocation2.getType().getX() == i6) {
                    return sqrt;
                }
                throw new RSRuntimeException("Incorrect vector dimensions for SPR2");
            }
        } else {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
    }

    public void SSYMV(int i, float f, Allocation allocation, Allocation allocation2, int i2, float f2, Allocation allocation3, int i3) {
        int a = a(Element.F32(this.t), i, allocation, allocation2, allocation3, i2, i3);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation);
            j = a2;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 79, 0, 0, 0, i, 0, 0, a, 0, f, j, j2, f2, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void SSBMV(int i, int i2, float f, Allocation allocation, Allocation allocation2, int i3, float f2, Allocation allocation3, int i4) {
        if (i2 >= 0) {
            int a = a(Element.F32(this.t), i, allocation, allocation2, allocation3, i3, i4);
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a2 = a(allocation);
                j = a2;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 80, 0, 0, 0, i, 0, 0, a, i2, f, j, j2, f2, j3, i3, i4, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void SSPMV(int i, float f, Allocation allocation, Allocation allocation2, int i2, float f2, Allocation allocation3, int i3) {
        int b = b(Element.F32(this.t), i, allocation, allocation2, i2, allocation3, i3);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 81, 0, 0, 0, i, 0, 0, b, 0, f, j, j2, f2, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void SGER(float f, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        a(Element.F32(this.t), allocation, i, allocation2, i2, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 82, 0, 0, 0, 0, 0, y, x, 0, f, j2, j3, 0.0f, j, i, i2, 0, 0, isIncSupp);
    }

    public void SSYR(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        int a = a(Element.F32(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation2);
            j2 = a(allocation);
            j = a2;
        }
        this.t.a(a(this.t), 83, 0, 0, 0, i, 0, 0, a, 0, f, j2, j, 0.0f, 0L, i2, 0, 0, 0, isIncSupp);
    }

    public void SSPR(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        int b = b(Element.F32(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a = a(allocation2);
            j2 = a(allocation);
            j = a;
        }
        this.t.a(a(this.t), 84, 0, 0, 0, i, 0, 0, b, 0, f, j2, j, 0.0f, 0L, i2, 0, 0, 0, isIncSupp);
    }

    public void SSYR2(int i, float f, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int a = a(Element.F32(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation3);
            j = a2;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 85, 0, 0, 0, i, 0, 0, a, 0, f, j2, j3, 0.0f, j, i2, i3, 0, 0, isIncSupp);
    }

    public void SSPR2(int i, float f, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int b = b(Element.F32(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 86, 0, 0, 0, i, 0, 0, b, 0, f, j2, j3, 0.0f, j, i2, i3, 0, 0, isIncSupp);
    }

    public void DSYMV(int i, double d, Allocation allocation, Allocation allocation2, int i2, double d2, Allocation allocation3, int i3) {
        int a = a(Element.F64(this.t), i, allocation, allocation2, allocation3, i2, i3);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation);
            j = a2;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 87, 0, 0, 0, i, 0, 0, a, 0, d, j, j2, d2, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void DSBMV(int i, int i2, double d, Allocation allocation, Allocation allocation2, int i3, double d2, Allocation allocation3, int i4) {
        if (i2 >= 0) {
            int a = a(Element.F64(this.t), i, allocation, allocation2, allocation3, i3, i4);
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a2 = a(allocation);
                j = a2;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 88, 0, 0, 0, i, 0, 0, a, i2, d, j, j2, d2, j3, i3, i4, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void DSPMV(int i, double d, Allocation allocation, Allocation allocation2, int i2, double d2, Allocation allocation3, int i3) {
        int b = b(Element.F64(this.t), i, allocation, allocation2, i2, allocation3, i3);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 89, 0, 0, 0, i, 0, 0, b, 0, d, j, j2, d2, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void DGER(double d, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        a(Element.F64(this.t), allocation, i, allocation2, i2, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 90, 0, 0, 0, 0, 0, y, x, 0, d, j2, j3, 0.0d, j, i, i2, 0, 0, isIncSupp);
    }

    public void DSYR(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        int a = a(Element.F64(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation2);
            j2 = a(allocation);
            j = a2;
        }
        this.t.a(a(this.t), 91, 0, 0, 0, i, 0, 0, a, 0, d, j2, j, 0.0d, 0L, i2, 0, 0, 0, isIncSupp);
    }

    public void DSPR(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        int b = b(Element.F64(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a = a(allocation2);
            j2 = a(allocation);
            j = a;
        }
        this.t.a(a(this.t), 92, 0, 0, 0, i, 0, 0, b, 0, d, j2, j, 0.0d, 0L, i2, 0, 0, 0, isIncSupp);
    }

    public void DSYR2(int i, double d, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int a = a(Element.F64(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation3);
            j = a2;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 93, 0, 0, 0, i, 0, 0, a, 0, d, j2, j3, 0.0d, j, i2, i3, 0, 0, isIncSupp);
    }

    public void DSPR2(int i, double d, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int b = b(Element.F64(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 94, 0, 0, 0, i, 0, 0, b, 0, d, j2, j3, 0.0d, j, i2, i3, 0, 0, isIncSupp);
    }

    static void b(Element element, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        } else {
            int y = allocation3.getType().getY();
            int x = allocation3.getType().getX();
            if (i <= 0 || i2 <= 0) {
                throw new RSRuntimeException("Vector increments must be greater than 0");
            }
            if (allocation.getType().getX() == ((y - 1) * i) + 1) {
                if (allocation2.getType().getX() != ((x - 1) * i2) + 1) {
                    throw new RSRuntimeException("Incorrect vector dimensions for GERU");
                }
                return;
            }
            throw new RSRuntimeException("Incorrect vector dimensions for GERU");
        }
    }

    public void CHEMV(int i, Float2 float2, Allocation allocation, Allocation allocation2, int i2, Float2 float22, Allocation allocation3, int i3) {
        int a = a(Element.F32_2(this.t), i, allocation2, i2, allocation3, i3, allocation);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation);
            j = a2;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 95, 0, 0, 0, i, 0, 0, a, 0, float2.x, float2.y, j, j2, float22.x, float22.y, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void CHBMV(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, int i3, Float2 float22, Allocation allocation3, int i4) {
        int a = a(Element.F32_2(this.t), i, allocation2, i3, allocation3, i4, allocation);
        if (i2 >= 0) {
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a2 = a(allocation);
                j = a2;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 96, 0, 0, 0, i, 0, 0, a, i2, float2.x, float2.y, j, j2, float22.x, float22.y, j3, i3, i4, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be 0 or greater for HBMV");
    }

    public void CHPMV(int i, Float2 float2, Allocation allocation, Allocation allocation2, int i2, Float2 float22, Allocation allocation3, int i3) {
        int b = b(Element.F32_2(this.t), i, allocation2, i2, allocation3, i3, allocation);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 97, 0, 0, 0, i, 0, 0, b, 0, float2.x, float2.y, j, j2, float22.x, float22.y, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void CGERU(Float2 float2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        b(Element.F32_2(this.t), allocation, i, allocation2, i2, allocation3);
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 98, 0, 0, 0, 0, 0, y, x, 0, float2.x, float2.y, j2, j3, 0.0f, 0.0f, j, i, i2, 0, 0, isIncSupp);
    }

    public void CGERC(Float2 float2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        b(Element.F32_2(this.t), allocation, i, allocation2, i2, allocation3);
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 99, 0, 0, 0, 0, 0, y, x, 0, float2.x, float2.y, j2, j3, 0.0f, 0.0f, j, i, i2, 0, 0, isIncSupp);
    }

    public void CHER(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        int a = a(Element.F32_2(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation2);
            j2 = a(allocation);
            j = a2;
        }
        this.t.a(a(this.t), 100, 0, 0, 0, i, 0, 0, a, 0, f, 0.0f, j2, 0L, 0.0f, 0.0f, j, i2, 0, 0, 0, isIncSupp);
    }

    public void CHPR(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        int b = b(Element.F32_2(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a = a(allocation2);
            j2 = a(allocation);
            j = a;
        }
        this.t.a(a(this.t), 101, 0, 0, 0, i, 0, 0, b, 0, f, 0.0f, j2, 0L, 0.0f, 0.0f, j, i2, 0, 0, 0, isIncSupp);
    }

    public void CHER2(int i, Float2 float2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int a = a(Element.F32_2(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation3);
            j = a2;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 102, 0, 0, 0, i, 0, 0, a, 0, float2.x, float2.y, j2, j3, 0.0f, 0.0f, j, i2, i3, 0, 0, isIncSupp);
    }

    public void CHPR2(int i, Float2 float2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int b = b(Element.F32_2(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 103, 0, 0, 0, i, 0, 0, b, 0, float2.x, float2.y, j2, j3, 0.0f, 0.0f, j, i2, i3, 0, 0, isIncSupp);
    }

    public void ZHEMV(int i, Double2 double2, Allocation allocation, Allocation allocation2, int i2, Double2 double22, Allocation allocation3, int i3) {
        int a = a(Element.F64_2(this.t), i, allocation2, i2, allocation3, i3, allocation);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation);
            j = a2;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 104, 0, 0, 0, i, 0, 0, a, 0, double2.x, double2.y, j, j2, double22.x, double22.y, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void ZHBMV(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, int i3, Double2 double22, Allocation allocation3, int i4) {
        int a = a(Element.F64_2(this.t), i, allocation2, i3, allocation3, i4, allocation);
        if (i2 >= 0) {
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a2 = a(allocation);
                j = a2;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 105, 0, 0, 0, i, 0, 0, a, i2, double2.x, double2.y, j, j2, double22.x, double22.y, j3, i3, i4, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("K must be 0 or greater for HBMV");
    }

    public void ZHPMV(int i, Double2 double2, Allocation allocation, Allocation allocation2, int i2, Double2 double22, Allocation allocation3, int i3) {
        int b = b(Element.F64_2(this.t), i, allocation2, i2, allocation3, i3, allocation);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 106, 0, 0, 0, i, 0, 0, b, 0, double2.x, double2.y, j, j2, double22.x, double22.y, j3, i2, i3, 0, 0, isIncSupp);
    }

    public void ZGERU(Double2 double2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        b(Element.F64_2(this.t), allocation, i, allocation2, i2, allocation3);
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 107, 0, 0, 0, 0, 0, y, x, 0, double2.x, double2.y, j2, j3, 0.0d, 0.0d, j, i, i2, 0, 0, isIncSupp);
    }

    public void ZGERC(Double2 double2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        b(Element.F64_2(this.t), allocation, i, allocation2, i2, allocation3);
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 108, 0, 0, 0, 0, 0, y, x, 0, double2.x, double2.y, j2, j3, 0.0d, 0.0d, j, i, i2, 0, 0, isIncSupp);
    }

    public void ZHER(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        int a = a(Element.F64_2(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation2);
            j2 = a(allocation);
            j = a2;
        }
        this.t.a(a(this.t), 109, 0, 0, 0, i, 0, 0, a, 0, d, 0.0d, j2, 0L, 0.0d, 0.0d, j, i2, 0, 0, 0, isIncSupp);
    }

    public void ZHPR(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        int b = b(Element.F64_2(this.t), i, allocation, i2, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation2.a(this.t);
        long j2 = allocation.a(this.t);
        if (isIncSupp) {
            long a = a(allocation2);
            j2 = a(allocation);
            j = a;
        }
        this.t.a(a(this.t), 110, 0, 0, 0, i, 0, 0, b, 0, d, 0.0d, j2, 0L, 0.0d, 0.0d, j, i2, 0, 0, 0, isIncSupp);
    }

    public void ZHER2(int i, Double2 double2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int a = a(Element.F64_2(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a2 = a(allocation3);
            j = a2;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 111, 0, 0, 0, i, 0, 0, a, 0, double2.x, double2.y, j2, j3, 0.0d, 0.0d, j, i2, i3, 0, 0, isIncSupp);
    }

    public void ZHPR2(int i, Double2 double2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        int b = b(Element.F64_2(this.t), i, allocation, i2, allocation2, i3, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation3.a(this.t);
        long j2 = allocation.a(this.t);
        long j3 = allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation3);
            j = a;
            j2 = a(allocation);
            j3 = a(allocation2);
        }
        this.t.a(a(this.t), 112, 0, 0, 0, i, 0, 0, b, 0, double2.x, double2.y, j2, j3, 0.0d, 0.0d, j, i2, i3, 0, 0, isIncSupp);
    }

    static void a(Element element, int i, int i2, int i3, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        int i4;
        int i5;
        int i6;
        int i7;
        if ((allocation != null && !allocation.getType().getElement().isCompatible(element)) || ((allocation2 != null && !allocation2.getType().getElement().isCompatible(element)) || (allocation3 != null && !allocation3.getType().getElement().isCompatible(element)))) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        } else if (allocation3 != null) {
            int y = allocation3.getType().getY();
            int x = allocation3.getType().getX();
            int i8 = -1;
            if (i3 != 142) {
                if (allocation == null) {
                    i5 = -1;
                    i6 = -1;
                } else if (i == 112 || i == 113) {
                    i5 = allocation.getType().getY();
                    i6 = allocation.getType().getX();
                } else {
                    i6 = allocation.getType().getY();
                    i5 = allocation.getType().getX();
                }
                if (allocation2 == null) {
                    i4 = -1;
                } else if (i2 == 112 || i2 == 113) {
                    i4 = allocation2.getType().getY();
                    i8 = allocation2.getType().getX();
                } else {
                    i8 = allocation2.getType().getY();
                    i4 = allocation2.getType().getX();
                }
            } else if ((allocation != null || allocation2 == null) && (allocation == null || allocation2 != null)) {
                if (allocation2 != null) {
                    i7 = allocation.getType().getY();
                    i4 = allocation.getType().getX();
                } else {
                    i7 = -1;
                    i4 = -1;
                }
                if (allocation != null) {
                    i6 = allocation2.getType().getY();
                    i5 = allocation2.getType().getX();
                    i8 = i7;
                } else {
                    i6 = -1;
                    i8 = i7;
                    i5 = -1;
                }
            } else {
                throw new RSRuntimeException("Provided Matrix A without Matrix B, or vice versa");
            }
            if (allocation == null || allocation2 == null || allocation3 == null) {
                if (allocation == null || allocation3 == null) {
                    if (allocation != null && allocation2 != null && i5 != i8) {
                        throw new RSRuntimeException("Called BLAS with invalid dimensions");
                    }
                } else if (y != x) {
                    throw new RSRuntimeException("Matrix C is not symmetric");
                } else if (i6 != y) {
                    throw new RSRuntimeException("Called BLAS with invalid dimensions");
                }
            } else if (i5 != i8 || i6 != y || i4 != x) {
                throw new RSRuntimeException("Called BLAS with invalid dimensions");
            }
        } else {
            throw new RSRuntimeException("Allocation C cannot be null");
        }
    }

    public void SGEMM(int i, int i2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        int i3;
        int i4;
        int i5;
        int i6;
        b(i);
        b(i2);
        a(Element.F32(this.t), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            i4 = allocation.getType().getX();
            i3 = allocation.getType().getY();
            i5 = i2;
        } else {
            i4 = allocation.getType().getY();
            i3 = allocation.getType().getX();
            i5 = i2;
        }
        if (i5 != 111) {
            i6 = allocation2.getType().getY();
        } else {
            i6 = allocation2.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 113, i, i2, 0, 0, 0, i4, i6, i3, f, j, j2, f2, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void DGEMM(int i, int i2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        int i3;
        int i4;
        int i5;
        int i6;
        b(i);
        b(i2);
        a(Element.F64(this.t), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            i4 = allocation.getType().getX();
            i3 = allocation.getType().getY();
            i5 = i2;
        } else {
            i4 = allocation.getType().getY();
            i3 = allocation.getType().getX();
            i5 = i2;
        }
        if (i5 != 111) {
            i6 = allocation2.getType().getY();
        } else {
            i6 = allocation2.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 119, i, i2, 0, 0, 0, i4, i6, i3, d, j, j2, d2, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void CGEMM(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        int i3;
        int i4;
        int i5;
        int i6;
        b(i);
        b(i2);
        a(Element.F32_2(this.t), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            i4 = allocation.getType().getX();
            i3 = allocation.getType().getY();
            i5 = i2;
        } else {
            i4 = allocation.getType().getY();
            i3 = allocation.getType().getX();
            i5 = i2;
        }
        if (i5 != 111) {
            i6 = allocation2.getType().getY();
        } else {
            i6 = allocation2.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 125, i, i2, 0, 0, 0, i4, i6, i3, float2.x, float2.y, j, j2, float22.x, float22.y, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void ZGEMM(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        int i3;
        int i4;
        int i5;
        int i6;
        b(i);
        b(i2);
        a(Element.F64_2(this.t), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            i4 = allocation.getType().getX();
            i3 = allocation.getType().getY();
            i5 = i2;
        } else {
            i4 = allocation.getType().getY();
            i3 = allocation.getType().getX();
            i5 = i2;
        }
        if (i5 != 111) {
            i6 = allocation2.getType().getY();
        } else {
            i6 = allocation2.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j = a;
            j2 = a(allocation2);
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), NON_UNIT, i, i2, 0, 0, 0, i4, i6, i3, double2.x, double2.y, j, j2, double22.x, double22.y, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void SSYMM(int i, int i2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        a(i);
        e(i2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            a(Element.F32(this.t), 0, 0, i, allocation, allocation2, allocation3);
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a = a(allocation);
                j = a;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 114, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, f, j, j2, f2, j3, 0, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void DSYMM(int i, int i2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        a(i);
        e(i2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            a(Element.F64(this.t), 0, 0, i, allocation, allocation2, allocation3);
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a = a(allocation);
                j = a;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 120, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, d, j, j2, d2, j3, 0, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void CSYMM(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        a(i);
        e(i2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            a(Element.F32_2(this.t), 0, 0, i, allocation, allocation2, allocation3);
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a = a(allocation);
                j = a;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 126, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, j, j2, float22.x, float22.y, j3, 0, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void ZSYMM(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        a(i);
        e(i2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            a(Element.F64_2(this.t), 0, 0, i, allocation, allocation2, allocation3);
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a = a(allocation);
                j = a;
                j2 = a(allocation2);
                j3 = a(allocation3);
            }
            this.t.a(a(this.t), 132, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, j, j2, double22.x, double22.y, j3, 0, 0, 0, 0, isIncSupp);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void SSYRK(int i, int i2, float f, Allocation allocation, float f2, Allocation allocation2) {
        int i3;
        b(i2);
        e(i);
        a(Element.F32(this.t), i2, 0, 0, allocation, (Allocation) null, allocation2);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 115, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), i3, f, j, 0L, f2, j2, 0, 0, 0, 0, isIncSupp);
    }

    public void DSYRK(int i, int i2, double d, Allocation allocation, double d2, Allocation allocation2) {
        int i3;
        b(i2);
        e(i);
        a(Element.F64(this.t), i2, 0, 0, allocation, (Allocation) null, allocation2);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 121, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), i3, d, j, 0L, d2, j2, 0, 0, 0, 0, isIncSupp);
    }

    public void CSYRK(int i, int i2, Float2 float2, Allocation allocation, Float2 float22, Allocation allocation2) {
        int i3;
        b(i2);
        e(i);
        a(Element.F32_2(this.t), i2, 0, 0, allocation, (Allocation) null, allocation2);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            a(allocation2);
            j = a;
        }
        this.t.a(a(this.t), 127, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), i3, float2.x, float2.y, j, 0L, float22.x, float22.y, allocation2.a(this.t), 0, 0, 0, 0, isIncSupp);
    }

    public void ZSYRK(int i, int i2, Double2 double2, Allocation allocation, Double2 double22, Allocation allocation2) {
        int i3;
        b(i2);
        e(i);
        a(Element.F64_2(this.t), i2, 0, 0, allocation, (Allocation) null, allocation2);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        allocation2.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            a(allocation2);
            j = a;
        }
        this.t.a(a(this.t), 133, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), i3, double2.x, double2.y, j, 0L, double22.x, double22.y, allocation2.a(this.t), 0, 0, 0, 0, isIncSupp);
    }

    static void a(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        int i2;
        b(i);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (i == 112) {
            i2 = allocation.getType().getX();
        } else {
            i2 = allocation.getType().getY();
        }
        if (allocation3.getType().getX() != i2 || allocation3.getType().getY() != i2) {
            throw new RSRuntimeException("Invalid symmetric matrix in SYR2K");
        } else if (allocation.getType().getX() != allocation2.getType().getX() || allocation.getType().getY() != allocation2.getType().getY()) {
            throw new RSRuntimeException("Invalid A and B in SYR2K");
        }
    }

    public void SSYR2K(int i, int i2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        int i3;
        e(i);
        a(Element.F32(this.t), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j2 = a(allocation2);
            j = a;
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 116, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), i3, f, j, j2, f2, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void DSYR2K(int i, int i2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        int i3;
        e(i);
        a(Element.F64(this.t), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j2 = a(allocation2);
            j = a;
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 122, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), i3, d, j, j2, d2, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void CSYR2K(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        int i3;
        e(i);
        a(Element.F32_2(this.t), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j2 = a(allocation2);
            j = a;
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 128, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), i3, float2.x, float2.y, j, j2, float22.x, float22.y, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void ZSYR2K(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        int i3;
        e(i);
        a(Element.F64_2(this.t), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j2 = a(allocation2);
            j = a;
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 134, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), i3, double2.x, double2.y, j, j2, double22.x, double22.y, j3, 0, 0, 0, 0, isIncSupp);
    }

    static void a(Element element, int i, int i2, Allocation allocation, Allocation allocation2) {
        a(i);
        b(i2);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        if (y == x) {
            int y2 = allocation2.getType().getY();
            int x2 = allocation2.getType().getX();
            if (i == 141) {
                if (x != y2) {
                    throw new RSRuntimeException("Called TRMM with invalid matrices");
                }
            } else if (x2 != y) {
                throw new RSRuntimeException("Called TRMM with invalid matrices");
            }
        } else {
            throw new RSRuntimeException("Called TRMM with a non-symmetric matrix A");
        }
    }

    public void STRMM(int i, int i2, int i3, int i4, float f, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        a(Element.F32(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 117, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, f, j, j2, 0.0f, 0L, 0, 0, 0, 0, isIncSupp);
    }

    public void DTRMM(int i, int i2, int i3, int i4, double d, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        a(Element.F64(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 123, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, d, j, j2, 0.0d, 0L, 0, 0, 0, 0, isIncSupp);
    }

    public void CTRMM(int i, int i2, int i3, int i4, Float2 float2, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        a(Element.F32_2(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 129, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, float2.x, float2.y, j, j2, 0.0f, 0.0f, 0L, 0, 0, 0, 0, isIncSupp);
    }

    public void ZTRMM(int i, int i2, int i3, int i4, Double2 double2, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        a(Element.F64_2(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 135, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, double2.x, double2.y, j, j2, 0.0d, 0.0d, 0L, 0, 0, 0, 0, isIncSupp);
    }

    static void b(Element element, int i, int i2, Allocation allocation, Allocation allocation2) {
        a(i);
        b(i2);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int x = allocation.getType().getX();
        if (x == allocation.getType().getY()) {
            int y = allocation2.getType().getY();
            int x2 = allocation2.getType().getX();
            if (i == 141) {
                if (x != y) {
                    throw new RSRuntimeException("Called TRSM with invalid matrix dimensions");
                }
            } else if (x != x2) {
                throw new RSRuntimeException("Called TRSM with invalid matrix dimensions");
            }
        } else {
            throw new RSRuntimeException("Called TRSM with a non-symmetric matrix A");
        }
    }

    public void STRSM(int i, int i2, int i3, int i4, float f, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        b(Element.F32(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 118, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, f, j, j2, 0.0f, 0L, 0, 0, 0, 0, isIncSupp);
    }

    public void DTRSM(int i, int i2, int i3, int i4, double d, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        b(Element.F64(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 124, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, d, j, j2, 0.0d, 0L, 0, 0, 0, 0, isIncSupp);
    }

    public void CTRSM(int i, int i2, int i3, int i4, Float2 float2, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        b(Element.F32_2(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 130, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, float2.x, float2.y, j, j2, 0.0f, 0.0f, 0L, 0, 0, 0, 0, isIncSupp);
    }

    public void ZTRSM(int i, int i2, int i3, int i4, Double2 double2, Allocation allocation, Allocation allocation2) {
        e(i2);
        d(i4);
        b(Element.F64_2(this.t), i, i3, allocation, allocation2);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), 136, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, double2.x, double2.y, j, j2, 0.0d, 0.0d, 0L, 0, 0, 0, 0, isIncSupp);
    }

    static void b(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        a(i);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int x = allocation.getType().getX();
        if (x != allocation.getType().getY()) {
            throw new RSRuntimeException("Called HEMM with non-square A");
        } else if ((i == 141 && x != allocation2.getType().getY()) || (i == 142 && x != allocation2.getType().getX())) {
            throw new RSRuntimeException("Called HEMM with invalid B");
        } else if (allocation2.getType().getX() != allocation3.getType().getX() || allocation2.getType().getY() != allocation3.getType().getY()) {
            throw new RSRuntimeException("Called HEMM with mismatched B and C");
        }
    }

    public void CHEMM(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        e(i2);
        b(Element.F32_2(this.t), i, allocation, allocation2, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j2 = a(allocation2);
            j = a;
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 137, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, j, j2, float22.x, float22.y, j3, 0, 0, 0, 0, isIncSupp);
    }

    public void ZHEMM(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        e(i2);
        b(Element.F64_2(this.t), i, allocation, allocation2, allocation3);
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        long j3 = allocation3.a(this.t);
        if (isIncSupp) {
            long a = a(allocation);
            j2 = a(allocation2);
            j = a;
            j3 = a(allocation3);
        }
        this.t.a(a(this.t), 140, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, j, j2, double22.x, double22.y, j3, 0, 0, 0, 0, isIncSupp);
    }

    static void a(Element element, int i, Allocation allocation, Allocation allocation2) {
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        c(i);
        int x = allocation2.getType().getX();
        if (x != allocation2.getType().getY()) {
            throw new RSRuntimeException("Called HERK with non-square C");
        } else if (i == 111) {
            if (x != allocation.getType().getY()) {
                throw new RSRuntimeException("Called HERK with invalid A");
            }
        } else if (x != allocation.getType().getX()) {
            throw new RSRuntimeException("Called HERK with invalid A");
        }
    }

    public void CHERK(int i, int i2, float f, Allocation allocation, float f2, Allocation allocation2) {
        int i3;
        e(i);
        a(Element.F32_2(this.t), i2, allocation, allocation2);
        if (i2 == 113) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), TsExtractor.TS_STREAM_TYPE_DTS, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), i3, f, 0.0f, j, 0L, f2, 0.0f, j2, 0, 0, 0, 0, isIncSupp);
    }

    public void ZHERK(int i, int i2, double d, Allocation allocation, double d2, Allocation allocation2) {
        int i3;
        e(i);
        a(Element.F64_2(this.t), i2, allocation, allocation2);
        if (i2 == 113) {
            i3 = allocation.getType().getY();
        } else {
            i3 = allocation.getType().getX();
        }
        boolean isIncSupp = isIncSupp();
        long j = allocation.a(this.t);
        long j2 = allocation2.a(this.t);
        if (isIncSupp) {
            j = a(allocation);
            j2 = a(allocation2);
        }
        this.t.a(a(this.t), LEFT, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), i3, d, 0.0d, j, 0L, d2, 0.0d, j2, 0, 0, 0, 0, isIncSupp);
    }

    static void c(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        c(i);
        int x = allocation3.getType().getX();
        if (x == allocation3.getType().getY()) {
            if (i == 111) {
                if (allocation.getType().getY() != x) {
                    throw new RSRuntimeException("Called HER2K with invalid matrices");
                }
            } else if (allocation.getType().getX() != x) {
                throw new RSRuntimeException("Called HER2K with invalid matrices");
            }
            if (allocation.getType().getX() != allocation2.getType().getX() || allocation.getType().getY() != allocation2.getType().getY()) {
                throw new RSRuntimeException("Called HER2K with invalid A and B matrices");
            }
            return;
        }
        throw new RSRuntimeException("Called HER2K with non-square C");
    }

    public void CHER2K(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, float f, Allocation allocation3) {
        int i3;
        e(i);
        c(Element.F32_2(this.t), i2, allocation, allocation2, allocation3);
        if (i2 == 111) {
            i3 = allocation.getType().getX();
        } else {
            i3 = allocation.getType().getY();
        }
        boolean isIncSupp = isIncSupp();
        allocation.a(this.t);
        long j = allocation2.a(this.t);
        long j2 = allocation3.a(this.t);
        if (isIncSupp) {
            a(allocation);
            j = a(allocation2);
            j2 = a(allocation3);
        }
        this.t.a(a(this.t), 139, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), i3, float2.x, float2.y, allocation.a(this.t), j, f, 0.0f, j2, 0, 0, 0, 0, isIncSupp);
    }

    public void ZHER2K(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, double d, Allocation allocation3) {
        int i3;
        e(i);
        c(Element.F64_2(this.t), i2, allocation, allocation2, allocation3);
        if (i2 == 111) {
            i3 = allocation.getType().getX();
        } else {
            i3 = allocation.getType().getY();
        }
        boolean isIncSupp = isIncSupp();
        allocation.a(this.t);
        long j = allocation2.a(this.t);
        long j2 = allocation3.a(this.t);
        if (isIncSupp) {
            a(allocation);
            j = a(allocation2);
            j2 = a(allocation3);
        }
        this.t.a(a(this.t), RIGHT, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), i3, double2.x, double2.y, allocation.a(this.t), j, d, 0.0d, j2, 0, 0, 0, 0, isIncSupp);
    }

    public void BNNM(Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3, int i3, int i4) {
        a(Element.U8(this.t), 111, 112, 0, allocation, allocation2, allocation3);
        if (i < 0 || i > 255) {
            throw new RSRuntimeException("Invalid a_offset passed to BNNM");
        } else if (i2 < 0 || i2 > 255) {
            throw new RSRuntimeException("Invalid b_offset passed to BNNM");
        } else {
            int y = allocation.getType().getY();
            int y2 = allocation2.getType().getY();
            int x = allocation.getType().getX();
            boolean isIncSupp = isIncSupp();
            long j = allocation.a(this.t);
            long j2 = allocation2.a(this.t);
            long j3 = allocation3.a(this.t);
            if (isIncSupp) {
                long a = a(allocation);
                long a2 = a(allocation2);
                j3 = a(allocation3);
                j2 = a2;
                j = a;
            }
            this.t.a(a(this.t), y, y2, x, j, i, j2, i2, j3, i3, i4, isIncSupp);
        }
    }
}
