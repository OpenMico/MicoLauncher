package miuix.animation.physics;

/* loaded from: classes5.dex */
public class AccelerateOperator implements PhysicsOperator {
    @Override // miuix.animation.physics.PhysicsOperator
    public double updateVelocity(double d, double d2, double d3, double d4, double... dArr) {
        return d + (d2 * d4);
    }

    @Override // miuix.animation.physics.PhysicsOperator
    public void getParameters(float[] fArr, double[] dArr) {
        dArr[0] = fArr[0] * 1000.0d;
    }
}
