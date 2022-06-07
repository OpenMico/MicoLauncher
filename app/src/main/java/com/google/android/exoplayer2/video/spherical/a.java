package com.google.android.exoplayer2.video.spherical;

import android.opengl.Matrix;
import com.google.android.exoplayer2.util.TimedValueQueue;

/* compiled from: FrameRotationQueue.java */
/* loaded from: classes2.dex */
final class a {
    private final float[] a = new float[16];
    private final float[] b = new float[16];
    private final TimedValueQueue<float[]> c = new TimedValueQueue<>();
    private boolean d;

    public void a(long j, float[] fArr) {
        this.c.add(j, fArr);
    }

    public void a() {
        this.c.clear();
        this.d = false;
    }

    public boolean a(float[] fArr, long j) {
        float[] pollFloor = this.c.pollFloor(j);
        if (pollFloor == null) {
            return false;
        }
        b(this.b, pollFloor);
        if (!this.d) {
            a(this.a, this.b);
            this.d = true;
        }
        Matrix.multiplyMM(fArr, 0, this.a, 0, this.b, 0);
        return true;
    }

    public static void a(float[] fArr, float[] fArr2) {
        Matrix.setIdentityM(fArr, 0);
        float sqrt = (float) Math.sqrt((fArr2[10] * fArr2[10]) + (fArr2[8] * fArr2[8]));
        fArr[0] = fArr2[10] / sqrt;
        fArr[2] = fArr2[8] / sqrt;
        fArr[8] = (-fArr2[8]) / sqrt;
        fArr[10] = fArr2[10] / sqrt;
    }

    private static void b(float[] fArr, float[] fArr2) {
        float f = fArr2[0];
        float f2 = -fArr2[1];
        float f3 = -fArr2[2];
        float length = Matrix.length(f, f2, f3);
        if (length != 0.0f) {
            Matrix.setRotateM(fArr, 0, (float) Math.toDegrees(length), f / length, f2 / length, f3 / length);
        } else {
            Matrix.setIdentityM(fArr, 0);
        }
    }
}
