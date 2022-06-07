package com.xiaomi.micolauncher.module.intercom;

import android.view.View;
import androidx.fragment.app.Fragment;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.setting.utils.Mic;

/* loaded from: classes3.dex */
public class IntercomBaseFragment extends BaseFragment {
    private CustomDialog a;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        scheduleToClose();
        super.onStart();
    }

    protected void showMicMuteDialog() {
        if (this.a == null) {
            this.a = new CustomDialog.Builder().setTitleResId(R.string.intercom_dialog_mic_mute_title).setMessageResId(R.string.intercom_dialog_mic_mute_msg).setPositiveResId(R.string.intercom_dialog_mic_mute_positive).setNegativeResId(R.string.intercom_dialog_mic_mute_negative).setPositiveOnClickListener($$Lambda$IntercomBaseFragment$QLhfvcTqgSbxIvKKY6CPIGTGnm0.INSTANCE).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomBaseFragment$7Pzkf1wuKNheiJclI6nvsUak3Cg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IntercomBaseFragment.this.a(view);
                }
            }).setHasNegativeButton(true).build();
        }
        this.a.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(View view) {
        Mic.getInstance().setMicMute(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        getActivity().onBackPressed();
    }

    protected void dismissMicMuteDialog() {
        CustomDialog customDialog = this.a;
        if (customDialog != null) {
            customDialog.dismiss();
            this.a = null;
        }
    }

    protected void showFragment(int i, Fragment fragment, boolean z) {
        ((IntercomActivity) getActivity()).showFragment(i, fragment, z);
    }

    protected void scheduleToClose() {
        if (getActivity() != null) {
            ((IntercomActivity) getActivity()).scheduleToClose(BaseActivity.DEFAULT_CLOSE_DURATION);
        }
    }
}
