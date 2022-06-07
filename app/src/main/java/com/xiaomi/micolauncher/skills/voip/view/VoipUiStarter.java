package com.xiaomi.micolauncher.skills.voip.view;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionListener;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;

/* loaded from: classes3.dex */
public class VoipUiStarter {
    private static Camera2VisualRecognitionListener b = new Camera2VisualRecognitionListener() { // from class: com.xiaomi.micolauncher.skills.voip.view.VoipUiStarter.1
        @Override // com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionListener
        public void onClosingVisualAlgorithm() {
            L.voip.i("onClosingVisualAlgorithm");
            VoipModel.getInstance().setIsVoipTriggerClosingVisualAlgorithm(true);
        }
    };
    private Context a;

    public VoipUiStarter(Context context) {
        this.a = context;
    }

    public static void checkFaceDetect(Context context, Intent intent, boolean[] zArr) {
        Camera2VisualRecognitionManager.getInstance(context).checkFaceDetect(context, intent, zArr, b);
    }
}
