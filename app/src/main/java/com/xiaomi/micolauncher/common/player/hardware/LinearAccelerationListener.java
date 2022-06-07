package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;

/* loaded from: classes3.dex */
public class LinearAccelerationListener extends AbstractListener {
    public final float[] values = {0.0f, 0.0f, 0.0f};

    public LinearAccelerationListener(Context context) {
        super(context);
    }

    public boolean register() {
        return a(10);
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.values[0] = sensorEvent.values[0];
        this.values[1] = sensorEvent.values[1];
        this.values[2] = sensorEvent.values[2];
    }
}
