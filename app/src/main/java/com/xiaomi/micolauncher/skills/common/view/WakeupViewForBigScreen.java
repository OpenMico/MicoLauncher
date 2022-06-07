package com.xiaomi.micolauncher.skills.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.common.WakeupHelper;
import com.xiaomi.micolauncher.skills.common.WakeupUtil;
import com.xiaomi.micolauncher.skills.common.view.wakeup.FrameSurfaceView;

/* loaded from: classes3.dex */
public class WakeupViewForBigScreen extends BaseWakeupView implements WakeupHelper {
    private WakeupTextView b = (WakeupTextView) this.mWakeupView.findViewById(R.id.wakeupTv);

    @SuppressLint({"InflateParams"})
    public WakeupViewForBigScreen(Context context) {
        super(context);
        LOG_TAG = "[WakeupViewForBigScreen]:";
        Logger logger = L.speech;
        logger.d(LOG_TAG + "construct");
        this.wakeupAnimalView = new WakeupAnimalViewForBig((FrameSurfaceView) this.mWakeupView.findViewById(R.id.wakeupAnim));
        this.wakeupAnimalView.init(0, 10, 90, 105, 194, 204, 225, 235);
        this.mWakeupView.findViewById(R.id.wakeup_fl).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$WakeupViewForBigScreen$Shyxsr3TE24NWQzrOkYEJcx6DiY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WakeupViewForBigScreen.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        L.wakeup.i("currentStatus   %s", this.wakeupAnimalView.getStatus().toString());
        SpeechManager.getInstance().dialogClose(false);
        switch (this.wakeupAnimalView.getStatus()) {
            case WAKEUP_STATUS_LISTENING:
            case WAKEUP_STATUS_SPEAKING:
                SpeechManager.getInstance().cancelSpeech();
                dismiss();
                return;
            case WAKEUP_STATUS_LOADING:
            case WAKEUP_STATUS_TTS:
                SpeechManager.getInstance().muteTts();
                return;
            default:
                return;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.BaseWakeupView, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (isShowing()) {
            this.b.setVisibility(8);
        }
        super.dismiss();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.BaseWakeupView
    protected void textViewReset() {
        Logger logger = L.speech;
        logger.d(LOG_TAG + "clearText");
        WakeupTextView wakeupTextView = this.b;
        if (wakeupTextView != null) {
            wakeupTextView.setVisibility(0);
            this.b.reset();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void showAsr(String str) {
        showAsr(str, false, false);
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void showAsr(String str, boolean z) {
        showAsr(str, z, false);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.BaseWakeupView, com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void showAsr(String str, boolean z, boolean z2) {
        super.showAsr(str, z, z2);
        if (this.wakeupAnimalView != null) {
            this.wakeupAnimalView.stepSpeaking();
        }
        this.b.setText(str, false, z, z2);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.BaseWakeupView
    protected boolean showNlpText(String str) {
        if (!WakeupUtil.withinShowLines(getContext(), str)) {
            return true;
        }
        this.b.reset();
        this.b.setText(str, true, true, false);
        this.b.setAlpha(0.0f);
        this.b.animate().alpha(1.0f).setDuration(1200L);
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.BaseWakeupView, com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void stepToTtsAnimation() {
        super.stepToTtsAnimation();
        WakeupTextView wakeupTextView = this.b;
        if (wakeupTextView != null) {
            wakeupTextView.reset();
        }
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        L.wakeup.i("wakeup view onBackPressed");
    }
}
