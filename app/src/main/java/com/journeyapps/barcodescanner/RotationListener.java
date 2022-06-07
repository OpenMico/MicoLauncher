package com.journeyapps.barcodescanner;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.WindowManager;

/* loaded from: classes2.dex */
public class RotationListener {
    private int a;
    private WindowManager b;
    private OrientationEventListener c;
    private RotationCallback d;

    public void listen(Context context, RotationCallback rotationCallback) {
        stop();
        Context applicationContext = context.getApplicationContext();
        this.d = rotationCallback;
        this.b = (WindowManager) applicationContext.getSystemService("window");
        this.c = new OrientationEventListener(applicationContext, 3) { // from class: com.journeyapps.barcodescanner.RotationListener.1
            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i) {
                int rotation;
                WindowManager windowManager = RotationListener.this.b;
                RotationCallback rotationCallback2 = RotationListener.this.d;
                if (RotationListener.this.b != null && rotationCallback2 != null && (rotation = windowManager.getDefaultDisplay().getRotation()) != RotationListener.this.a) {
                    RotationListener.this.a = rotation;
                    rotationCallback2.onRotationChanged(rotation);
                }
            }
        };
        this.c.enable();
        this.a = this.b.getDefaultDisplay().getRotation();
    }

    public void stop() {
        OrientationEventListener orientationEventListener = this.c;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
        this.c = null;
        this.b = null;
        this.d = null;
    }
}
