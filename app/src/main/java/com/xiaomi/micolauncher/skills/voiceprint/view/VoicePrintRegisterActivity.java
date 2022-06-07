package com.xiaomi.micolauncher.skills.voiceprint.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterProgressEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VoicePrintRegisterActivity extends BaseActivity {
    private VoicePrintRegisterController a;
    private boolean b;
    private VoicePrintRegisterProgressEvent.ProgressStep c;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_voice_print_register);
        a(VoicePrintRegisterTipFragment.newInstance(getIntent().getStringExtra(VoicePrintRegisterController.PARAMS_TTS)));
        this.a = VoicePrintRegisterModel.getInstance().getController();
        this.b = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(VoicePrintRegisterTipFragment.newInstance(intent.getStringExtra(VoicePrintRegisterController.PARAMS_TTS)));
    }

    private void a(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        beginTransaction.commitAllowingStateLoss();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    @Deprecated
    public void registerProgress(VoicePrintRegisterProgressEvent voicePrintRegisterProgressEvent) {
        Bundle bundle = new Bundle();
        L.skill.i("activity.registerProgress.step=%s .paras=%s", voicePrintRegisterProgressEvent.getStep(), voicePrintRegisterProgressEvent.getParams());
        this.c = voicePrintRegisterProgressEvent.getStep();
        switch (voicePrintRegisterProgressEvent.getStep()) {
            case PROGRESS_STEP_START:
                a(new VoicePrintRegisterTipFragment());
                return;
            case PROGRESS_STEP_SHOW_XATX_PAGE:
                a(VoicePrintRegisterStepFragment.newInstance(voicePrintRegisterProgressEvent.getParams().get(VoicePrintRegisterController.PARAMS_TTS), voicePrintRegisterProgressEvent.getParams().get(VoicePrintRegisterController.PARAMS_CNT), voicePrintRegisterProgressEvent.getParams().get(VoicePrintRegisterController.PARAMS_TOTAL_CNT)));
                EventBusRegistry.getEventBus().removeStickyEvent(voicePrintRegisterProgressEvent);
                return;
            case PROGRESS_STEP_RESULT:
                VoicePrintRegisterResultFragment voicePrintRegisterResultFragment = new VoicePrintRegisterResultFragment();
                bundle.putString("result", voicePrintRegisterProgressEvent.getParams().get("result"));
                bundle.putString(VoicePrintRegisterController.PARAMS_NICK_NAME, voicePrintRegisterProgressEvent.getParams().get(VoicePrintRegisterController.PARAMS_NICK_NAME));
                bundle.putString(VoicePrintRegisterController.PARAMS_TTS, voicePrintRegisterProgressEvent.getParams().get(VoicePrintRegisterController.PARAMS_TTS));
                voicePrintRegisterResultFragment.setArguments(bundle);
                a(voicePrintRegisterResultFragment);
                return;
            case PROGRESS_STEP_END:
                this.b = false;
                finish();
                return;
            case PROGRESS_STEP_CANCEL:
                onPause();
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (VoicePrintRegisterModel.getInstance().isCanceled()) {
            onPause();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        VoicePrintRegisterController voicePrintRegisterController = this.a;
        if (voicePrintRegisterController != null && this.b) {
            voicePrintRegisterController.manualFinish();
            finish();
        }
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        VoicePrintRegisterModel.getInstance().clearCancel();
        VoicePrintRegisterModel.getInstance().setCurrentStep(DomainConfig.VoicePrint.Action.UNKNOWN);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        L.skill.i("onBackPressed");
        boolean z = VoicePrintRegisterModel.getInstance().getRegisterType() == VoicePrintRegisterModel.RegisterType.REGISTER_TYPE_VOICE_PAYMENT;
        VoicePrintRegisterProgressEvent.ProgressStep progressStep = this.c;
        if (progressStep == null || progressStep == VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SHOW_XATX_PAGE || this.c == VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SPEAK_XATX) {
            new CustomDialog.Builder().setLayoutResId(R.layout.dialog_voice_quit_dialog).setTitleResId(z ? R.string.voice_pay_register_quit_title : R.string.voice_register_quit_title).setPositiveResId(R.string.button_ok).setNegativeResId(R.string.cancel).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voiceprint.view.-$$Lambda$VoicePrintRegisterActivity$Ki7N7GbzJSw_nsA478Pd_A4F7Vg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VoicePrintRegisterActivity.this.a(view);
                }
            }).build().show();
        } else {
            super.onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmBoomEvent(AlarmBoomEvent alarmBoomEvent) {
        L.skill.i("activity.registerProgress receive alarm: %s", alarmBoomEvent.toString());
        onPause();
    }
}
