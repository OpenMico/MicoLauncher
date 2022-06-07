package com.google.android.exoplayer2.video.spherical;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.view.Display;
import androidx.annotation.BinderThread;
import androidx.renderscript.ScriptIntrinsicBLAS;

/* loaded from: classes2.dex */
final class OrientationListener implements SensorEventListener {
    private final float[] a = new float[16];
    private final float[] b = new float[16];
    private final float[] c = new float[16];
    private final float[] d = new float[3];
    private final Display e;
    private final Listener[] f;
    private boolean g;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onOrientationChange(float[] fArr, float f);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public OrientationListener(Display display, Listener... listenerArr) {
        this.e = display;
        this.f = listenerArr;
    }

    @Override // android.hardware.SensorEventListener
    @BinderThread
    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorManager.getRotationMatrixFromVector(this.a, sensorEvent.values);
        a(this.a, this.e.getRotation());
        float b = b(this.a);
        c(this.a);
        a(this.a);
        a(this.a, b);
    }

    private void a(float[] fArr, float f) {
        for (Listener listener : this.f) {
            listener.onOrientationChange(fArr, f);
        }
    }

    private void a(float[] fArr) {
        if (!this.g) {
            a.a(this.c, fArr);
            this.g = true;
        }
        float[] fArr2 = this.b;
        System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
        Matrix.multiplyMM(fArr, 0, this.b, 0, this.c, 0);
    }

    private float b(float[] fArr) {
        SensorManager.remapCoordinateSystem(fArr, 1, ScriptIntrinsicBLAS.NON_UNIT, this.b);
        SensorManager.getOrientation(this.b, this.d);
        return this.d[2];
    }

    private void a(float[] fArr, int i) {
        int i2;
        int i3 = 130;
        switch (i) {
            case 0:
                return;
            case 1:
                i3 = 2;
                i2 = 129;
                break;
            case 2:
                i2 = 130;
                i3 = 129;
                break;
            case 3:
                i2 = 1;
                break;
            default:
                throw new IllegalStateException();
        }
        float[] fArr2 = this.b;
        System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
        SensorManager.remapCoordinateSystem(this.b, i3, i2, fArr);
    }

    private static void c(float[] fArr) {
        Matrix.rotateM(fArr, 0, 90.0f, 1.0f, 0.0f, 0.0f);
    }
}
