package com.google.zxing.client.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.umeng.analytics.pro.ai;

/* loaded from: classes2.dex */
public final class AmbientLightManager implements SensorEventListener {
    private CameraManager a;
    private CameraSettings b;
    private Sensor c;
    private Context d;
    private Handler e = new Handler();

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public AmbientLightManager(Context context, CameraManager cameraManager, CameraSettings cameraSettings) {
        this.d = context;
        this.a = cameraManager;
        this.b = cameraSettings;
    }

    public void start() {
        if (this.b.isAutoTorchEnabled()) {
            SensorManager sensorManager = (SensorManager) this.d.getSystemService(ai.ac);
            this.c = sensorManager.getDefaultSensor(5);
            Sensor sensor = this.c;
            if (sensor != null) {
                sensorManager.registerListener(this, sensor, 3);
            }
        }
    }

    public void stop() {
        if (this.c != null) {
            ((SensorManager) this.d.getSystemService(ai.ac)).unregisterListener(this);
            this.c = null;
        }
    }

    private void a(final boolean z) {
        this.e.post(new Runnable() { // from class: com.google.zxing.client.android.AmbientLightManager.1
            @Override // java.lang.Runnable
            public void run() {
                AmbientLightManager.this.a.setTorch(z);
            }
        });
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        float f = sensorEvent.values[0];
        if (this.a == null) {
            return;
        }
        if (f <= 45.0f) {
            a(true);
        } else if (f >= 450.0f) {
            a(false);
        }
    }
}
