package com.xiaomi.mico.visual.algorithm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;

/* loaded from: classes3.dex */
public class FaceDetectDistanceDialog extends FrameLayout {
    private Context a;

    public FaceDetectDistanceDialog(@NonNull Context context) {
        super(context);
        this.a = context;
        LayoutInflater.from(context).inflate(R.layout.activity_distance_trigger_dialog, this);
    }

    public void show() {
        L.camera2.d("FaceDetectDistanceDialog show");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.type = 2038;
        layoutParams.flags = 67109928;
        layoutParams.gravity = 17;
        ((WindowManager) this.a.getSystemService(WindowManager.class)).addView(this, layoutParams);
        a();
    }

    public void dismiss() {
        L.camera2.d("FaceDetectDistanceDialog dismiss");
        ((WindowManager) this.a.getSystemService(WindowManager.class)).removeView(this);
    }

    private void a() {
        L.camera2.d("FaceDetectDistanceDialog play tts");
        SpeechManager.getInstance().ttsRequest("小朋友, 离远一点. 离屏幕太近会伤眼睛哦");
    }
}
