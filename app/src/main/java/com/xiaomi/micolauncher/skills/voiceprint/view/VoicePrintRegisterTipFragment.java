package com.xiaomi.micolauncher.skills.voiceprint.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRefreshTipEvent;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VoicePrintRegisterTipFragment extends BaseFragment {
    public static final String STEP_NAME = "VorTip";
    private View a;

    public static VoicePrintRegisterTipFragment newInstance(String str) {
        Bundle bundle = new Bundle();
        VoicePrintRegisterTipFragment voicePrintRegisterTipFragment = new VoicePrintRegisterTipFragment();
        bundle.putString(VoicePrintRegisterController.PARAMS_TTS, str);
        voicePrintRegisterTipFragment.setArguments(bundle);
        return voicePrintRegisterTipFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    @NonNull
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.voice_print_tip_page, viewGroup, false);
        a(this.a, getArguments() == null ? null : getArguments().getString(VoicePrintRegisterController.PARAMS_TTS));
        return this.a;
    }

    private void a(View view, String str) {
        boolean z = VoicePrintRegisterModel.getInstance().getRegisterType() == VoicePrintRegisterModel.RegisterType.REGISTER_TYPE_VOICE_PAYMENT;
        String nickName = VoicePrintRegisterModel.getInstance().getNickName();
        GradientTextView gradientTextView = (GradientTextView) view.findViewById(R.id.tvTip);
        if (z) {
            gradientTextView.setGradientText(getString(R.string.skill_voice_print_payment_home_tip, nickName), null, 1);
        } else if (!TextUtils.isEmpty(str)) {
            gradientTextView.setGradientText(str, null, 1);
        } else {
            gradientTextView.setGradientText(getString(R.string.skill_voice_print_home_tip), new String[]{getString(R.string.xiaoaitongxue)}, 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Deprecated
    public void onVoicePrintRefreshTip(VoicePrintRefreshTipEvent voicePrintRefreshTipEvent) {
        L.skill.i("onVoicePrintRefreshTip.tip=%s", voicePrintRefreshTipEvent.getTip());
        View view = this.a;
        if (view != null) {
            a(view, voicePrintRefreshTipEvent.getTip());
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }
}
