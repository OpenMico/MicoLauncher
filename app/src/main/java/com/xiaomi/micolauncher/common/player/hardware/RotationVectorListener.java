package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Build;

/* loaded from: classes3.dex */
public class RotationVectorListener extends AbstractListener {
    public final float[] values = {0.0f, 0.0f, 0.0f};

    public RotationVectorListener(Context context) {
        super(context);
    }

    public boolean register() {
        if (Build.VERSION.SDK_INT < 18 || !a(15)) {
            return a(11);
        }
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.player.hardware.AbstractListener, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.arraycopy(sensorEvent.values, 0, this.values, 0, 3);
    }
}
