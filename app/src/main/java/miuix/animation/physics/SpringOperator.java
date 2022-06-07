package miuix.animation.physics;

/* loaded from: classes5.dex */
public class SpringOperator implements PhysicsOperator {
    double[] a;

    public SpringOperator() {
    }

    @Deprecated
    public SpringOperator(float f, float f2) {
        this.a = new double[2];
        getParameters(new float[]{f, f2}, this.a);
    }

    @Deprecated
    public double updateVelocity(double d, float f, float... fArr) {
        if (this.a == null) {
            return d;
        }
        double[] dArr = new double[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            dArr[i] = fArr[i];
        }
        double[] dArr2 = this.a;
        return updateVelocity(d, dArr2[0], dArr2[1], f, dArr);
    }

    @Override // miuix.animation.physics.PhysicsOperator
    public void getParameters(float[] fArr, double[] dArr) {
        double d = fArr[1];
        dArr[0] = Math.pow(6.283185307179586d / d, 2.0d);
        dArr[1] = Math.min((fArr[0] * 12.566370614359172d) / d, 60.0d);
    }

    @Override // miuix.animation.physics.PhysicsOperator
    public double updateVelocity(double d, double d2, double d3, double d4, double... dArr) {
        return (d * (1.0d - (d3 * d4))) + ((float) (d2 * (dArr[0] - dArr[1]) * d4));
    }
}
