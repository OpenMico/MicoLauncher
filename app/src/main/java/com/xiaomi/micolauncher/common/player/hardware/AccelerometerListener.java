package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;

/* loaded from: classes3.dex */
public class AccelerometerListener extends AbstractListener {
    public final float[] gravity = {0.0f, 0.0f, 0.0f};
    public final float[] linear = {0.0f, 0.0f, 0.0f};
    public final float[] values = {0.0f, 0.0f, 0.0f};

    public AccelerometerListener(Context context) {
        super(context);
    }

    public boolean register() {
        return a(1);
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.a > 0) {
            float[] fArr = this.gravity;
            fArr[0] = (fArr[0] * 0.8f) + (sensorEvent.values[0] * 0.19999999f);
            float[] fArr2 = this.gravity;
            fArr2[1] = (fArr2[1] * 0.8f) + (sensorEvent.values[1] * 0.19999999f);
            float[] fArr3 = this.gravity;
            fArr3[2] = (fArr3[2] * 0.8f) + (sensorEvent.values[2] * 0.19999999f);
            this.linear[0] = sensorEvent.values[0] - this.gravity[0];
            this.linear[1] = sensorEvent.values[1] - this.gravity[1];
            this.linear[2] = sensorEvent.values[2] - this.gravity[2];
            this.values[0] = sensorEvent.values[0];
            this.values[1] = sensorEvent.values[1];
            this.values[2] = sensorEvent.values[2];
        }
        this.a = sensorEvent.timestamp;
    }
}
