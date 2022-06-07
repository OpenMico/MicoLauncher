package com.xiaomi.passport.ui.diagnosis;

import android.app.Activity;
import android.os.Vibrator;

/* loaded from: classes4.dex */
public class DiagnosisLauncher {
    private volatile int mClickCount = 0;
    private final Activity mContext;

    public DiagnosisLauncher(Activity activity) {
        this.mContext = activity;
    }

    public void onClick() {
        this.mClickCount++;
        if (this.mClickCount >= 5) {
            this.mClickCount = 0;
            showDiagnosisUI();
        }
    }

    private void showDiagnosisUI() {
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService("vibrator");
        if (vibrator != null) {
            vibrator.vibrate(200L);
        }
        PassportDiagnosisActivity.start(this.mContext);
    }
}
