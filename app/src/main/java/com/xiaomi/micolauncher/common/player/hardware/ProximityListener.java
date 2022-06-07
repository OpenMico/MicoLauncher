package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;

/* loaded from: classes3.dex */
public class ProximityListener extends AbstractListener {
    private float b = 0.0f;

    public ProximityListener(Context context) {
        super(context);
    }

    public boolean register() {
        return a(8);
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.b = sensorEvent.values[0];
    }

    public float getCentimeters() {
        return this.b;
    }
}
