package com.xiaomi.micolauncher.skills.voiceprint.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;

/* loaded from: classes3.dex */
public class VoicePrintRegisterResultFragment extends BaseFragment {
    TextView a;
    ImageView b;
    LottieAnimationView c;
    TextView d;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.voice_print_success_page, viewGroup, false);
        this.a = (TextView) inflate.findViewById(R.id.tv_title);
        this.b = (ImageView) inflate.findViewById(R.id.img_fail);
        this.c = (LottieAnimationView) inflate.findViewById(R.id.ani_result);
        this.d = (TextView) inflate.findViewById(R.id.tts_tv);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("result");
            boolean booleanValue = !TextUtils.isEmpty(string) ? Boolean.valueOf(string).booleanValue() : true;
            String string2 = arguments.getString(VoicePrintRegisterController.PARAMS_TTS);
            boolean z = VoicePrintRegisterModel.getInstance().getRegisterType() == VoicePrintRegisterModel.RegisterType.REGISTER_TYPE_VOICE_PAYMENT;
            if (booleanValue) {
                String string3 = arguments.getString(VoicePrintRegisterController.PARAMS_NICK_NAME);
                if (!TextUtils.isEmpty(string3)) {
                    this.a.setText(getString(R.string.skill_voice_print_result_hello, string3));
                }
                if (z) {
                    this.a.setText(getString(R.string.skill_voice_print_result_payment_success));
                    this.d.setText(getString(R.string.skill_voice_print_result_payment_tip));
                }
                this.b.setImageResource(R.drawable.vocalprint_success);
            } else {
                this.a.setText(R.string.skill_voice_print_result_hello_error);
                this.b.setImageResource(R.drawable.vocalprint_failure);
                if (TextUtils.isEmpty(string2)) {
                    this.d.setText(R.string.skill_voice_print_result_fail);
                } else {
                    this.d.setText(string2);
                }
            }
        }
        return inflate;
    }
}
