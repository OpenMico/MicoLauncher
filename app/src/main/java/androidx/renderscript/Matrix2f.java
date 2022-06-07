package androidx.renderscript;

/* loaded from: classes.dex */
public class Matrix2f {
    final float[] a;

    public Matrix2f() {
        this.a = new float[4];
        loadIdentity();
    }

    public Matrix2f(float[] fArr) {
        this.a = new float[4];
        float[] fArr2 = this.a;
        System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
    }

    public float[] getArray() {
        return this.a;
    }

    public float get(int i, int i2) {
        return this.a[(i * 2) + i2];
    }

    public void set(int i, int i2, float f) {
        this.a[(i * 2) + i2] = f;
    }

    public void loadIdentity() {
        float[] fArr = this.a;
        fArr[0] = 1.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 1.0f;
    }

    public void load(Matrix2f matrix2f) {
        float[] array = matrix2f.getArray();
        float[] fArr = this.a;
        System.arraycopy(array, 0, fArr, 0, fArr.length);
    }

    public void loadRotate(float f) {
        double d = f * 0.017453292f;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        float[] fArr = this.a;
        fArr[0] = cos;
        fArr[1] = -sin;
        fArr[2] = sin;
        fArr[3] = cos;
    }

    public void loadScale(float f, float f2) {
        loadIdentity();
        float[] fArr = this.a;
        fArr[0] = f;
        fArr[3] = f2;
    }

    public void loadMultiply(Matrix2f matrix2f, Matrix2f matrix2f2) {
        for (int i = 0; i < 2; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i2 = 0; i2 < 2; i2++) {
                float f3 = matrix2f2.get(i, i2);
                f += matrix2f.get(i2, 0) * f3;
                f2 += matrix2f.get(i2, 1) * f3;
            }
            set(i, 0, f);
            set(i, 1, f2);
        }
    }

    public void multiply(Matrix2f matrix2f) {
        Matrix2f matrix2f2 = new Matrix2f();
        matrix2f2.loadMultiply(this, matrix2f);
        load(matrix2f2);
    }

    public void rotate(float f) {
        Matrix2f matrix2f = new Matrix2f();
        matrix2f.loadRotate(f);
        multiply(matrix2f);
    }

    public void scale(float f, float f2) {
        Matrix2f matrix2f = new Matrix2f();
        matrix2f.loadScale(f, f2);
        multiply(matrix2f);
    }

    public void transpose() {
        float[] fArr = this.a;
        float f = fArr[1];
        fArr[1] = fArr[2];
        fArr[2] = f;
    }
}
