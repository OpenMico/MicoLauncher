package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class Camera2VisualRecognitionSetup extends AbsSyncSetup {
    private Context a;

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        this.a = context;
        Camera2VisualRecognitionManager.init(context);
        L.camera2.d("ImsModelSetup init Camera2VisualRecognitionSetup.");
    }

    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        if (Camera2VisualRecognitionManager.getInstance(this.a) != null) {
            Camera2VisualRecognitionManager.getInstance(this.a).destroy();
        }
    }
}
