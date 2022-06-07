package com.xiaomi.micolauncher.module.intercom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class IntercomMainFragment extends IntercomBaseFragment {
    public static IntercomMainFragment newInstance() {
        Bundle bundle = new Bundle();
        IntercomMainFragment intercomMainFragment = new IntercomMainFragment();
        intercomMainFragment.setArguments(bundle);
        return intercomMainFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    @org.jetbrains.annotations.Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater layoutInflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup viewGroup, @Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_intercom_main, (ViewGroup) null);
        inflate.findViewById(R.id.record_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.IntercomMainFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Mic.getInstance().isMicMute()) {
                    IntercomMainFragment.this.showMicMuteDialog();
                    return;
                }
                AudioPolicyService.getInstance().playbackController(PlaybackControl.PAUSE);
                IntercomMainFragment.this.showFragment(1, IntercomRecordFragment.newInstance(false, null), false);
            }
        });
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        dismissMicMuteDialog();
    }
}
