package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;

/* loaded from: classes3.dex */
public class LightListener extends AbstractListener {
    private float b = 0.0f;

    public LightListener(Context context) {
        super(context);
    }

    public boolean register() {
        return a(5);
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.b = sensorEvent.values[0];
    }

    public float getAmbient() {
        return this.b;
    }
}
