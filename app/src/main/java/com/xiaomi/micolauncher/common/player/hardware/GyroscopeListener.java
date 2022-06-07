package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

/* loaded from: classes3.dex */
public class GyroscopeListener extends AbstractListener {
    public final float[] rotation = {1.0f, 1.0f, 1.0f};
    private final float[] b = new float[4];
    private final float[] c = new float[9];

    public GyroscopeListener(Context context) {
        super(context);
    }

    public boolean register() {
        float[] fArr = this.rotation;
        fArr[2] = 1.0f;
        fArr[1] = 1.0f;
        fArr[0] = 1.0f;
        return a(4);
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.a > 0) {
            float f = ((float) (sensorEvent.timestamp - this.a)) * 1.0E-9f;
            float f2 = sensorEvent.values[0];
            float f3 = sensorEvent.values[1];
            float f4 = sensorEvent.values[2];
            float sqrt = (float) Math.sqrt((f2 * f2) + (f3 * f3) + (f4 * f4));
            if (sqrt > 1.0f) {
                f2 /= sqrt;
                f3 /= sqrt;
                f4 /= sqrt;
            }
            double d = (sqrt * f) / 2.0f;
            float sin = (float) Math.sin(d);
            float[] fArr = this.b;
            fArr[0] = f2 * sin;
            fArr[1] = f3 * sin;
            fArr[2] = sin * f4;
            fArr[3] = (float) Math.cos(d);
            SensorManager.getRotationMatrixFromVector(this.c, fArr);
            float[] fArr2 = this.rotation;
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            float f7 = fArr2[2];
            float[] fArr3 = this.c;
            fArr2[0] = (fArr3[0] * f5) + (fArr3[1] * f6) + (fArr3[2] * f7);
            fArr2[1] = (fArr3[3] * f5) + (fArr3[4] * f6) + (fArr3[5] * f7);
            fArr2[2] = (f5 * fArr3[6]) + (f6 * fArr3[7]) + (f7 * fArr3[8]);
        }
        this.a = sensorEvent.timestamp;
    }
}
