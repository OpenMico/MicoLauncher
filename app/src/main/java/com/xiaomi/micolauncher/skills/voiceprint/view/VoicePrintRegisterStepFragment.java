package com.xiaomi.micolauncher.skills.voiceprint.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterProgressEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VoicePrintRegisterStepFragment extends BaseFragment {
    public static final String STEP_NAME = "VorProgress";
    private static final String[] c = {"0", "null"};
    TextView a;
    TextView b;

    public static VoicePrintRegisterStepFragment newInstance(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        VoicePrintRegisterStepFragment voicePrintRegisterStepFragment = new VoicePrintRegisterStepFragment();
        bundle.putString(VoicePrintRegisterController.PARAMS_TTS, str);
        bundle.putString(VoicePrintRegisterController.PARAMS_CNT, str2);
        bundle.putString(VoicePrintRegisterController.PARAMS_TOTAL_CNT, str3);
        voicePrintRegisterStepFragment.setArguments(bundle);
        return voicePrintRegisterStepFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    @NonNull
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.voice_print_step_page, viewGroup, false);
        this.a = (TextView) inflate.findViewById(R.id.tvStep);
        this.b = (TextView) inflate.findViewById(R.id.tvTts);
        Bundle arguments = getArguments();
        if (arguments != null) {
            a(arguments.getString(VoicePrintRegisterController.PARAMS_TOTAL_CNT), arguments.getString(VoicePrintRegisterController.PARAMS_CNT), arguments.getString(VoicePrintRegisterController.PARAMS_TTS));
        }
        return inflate;
    }

    private void a(String str, String str2, String str3) {
        boolean z;
        String[] strArr = c;
        for (String str4 : strArr) {
            L.skill.d("refreshView.step=%s", str2);
            if (str4.equals(str2) || TextUtils.isEmpty(str2)) {
                z = false;
                break;
            }
        }
        z = true;
        if (z) {
            this.a.setText(String.format("%s/%s", str2, str));
            this.a.setVisibility(0);
        } else {
            this.a.setVisibility(8);
        }
        if (!TextUtils.isEmpty(str3)) {
            setGradientText(str3);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Deprecated
    public void registerProgress(VoicePrintRegisterProgressEvent voicePrintRegisterProgressEvent) {
        L.skill.i("step.registerProgress.step=%s .paras=%s", voicePrintRegisterProgressEvent.getStep(), voicePrintRegisterProgressEvent.getParams());
        if (voicePrintRegisterProgressEvent.getStep() == VoicePrintRegisterProgressEvent.ProgressStep.PROGRESS_STEP_SPEAK_XATX) {
            ArrayMap<String, String> params = voicePrintRegisterProgressEvent.getParams();
            a(params.get(VoicePrintRegisterController.PARAMS_TOTAL_CNT), params.get(VoicePrintRegisterController.PARAMS_CNT), params.get(VoicePrintRegisterController.PARAMS_TTS));
        }
    }

    public void setGradientText(String str) {
        ((GradientTextView) this.b).setGradientText(str, new String[]{getString(R.string.xiaoaitongxue)}, 0);
    }
}
