package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;

/* loaded from: classes3.dex */
public class MagneticFieldListener extends AbstractListener {
    public final float[] values = {0.0f, 0.0f, 0.0f};
    public final float[] filtered = {0.0f, 0.0f, 0.0f};

    public MagneticFieldListener(Context context) {
        super(context);
    }

    public boolean register() {
        return a(2);
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr = this.filtered;
        fArr[0] = (fArr[0] * 0.8f) + (sensorEvent.values[0] * 0.19999999f);
        float[] fArr2 = this.filtered;
        fArr2[1] = (fArr2[1] * 0.8f) + (sensorEvent.values[1] * 0.19999999f);
        float[] fArr3 = this.filtered;
        fArr3[2] = (fArr3[2] * 0.8f) + (sensorEvent.values[2] * 0.19999999f);
        this.values[0] = sensorEvent.values[0];
        this.values[1] = sensorEvent.values[1];
        this.values[2] = sensorEvent.values[2];
    }
}
