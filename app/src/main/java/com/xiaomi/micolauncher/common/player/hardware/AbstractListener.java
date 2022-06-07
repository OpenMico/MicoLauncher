package com.xiaomi.micolauncher.common.player.hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.umeng.analytics.pro.ai;

/* loaded from: classes3.dex */
public abstract class AbstractListener implements SensorEventListener {
    private final SensorManager b;
    private Sensor d;
    long a = 0;
    private boolean c = false;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AbstractListener(Context context) {
        this.b = (SensorManager) context.getSystemService(ai.ac);
    }

    public void unregister() {
        if (this.d != null && this.c) {
            this.b.unregisterListener(this);
            this.c = false;
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.a = sensorEvent.timestamp;
    }

    boolean a(int i) {
        SensorManager sensorManager;
        if (this.c || (sensorManager = this.b) == null) {
            return false;
        }
        if (this.d == null) {
            Sensor defaultSensor = sensorManager.getDefaultSensor(i);
            this.d = defaultSensor;
            if (defaultSensor == null) {
                return false;
            }
        }
        this.a = 0L;
        this.c = this.b.registerListener(this, this.d, 3);
        return this.c;
    }
}
