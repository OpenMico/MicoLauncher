package com.xiaomi.micolauncher.module.initialize.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.initialize.BootActivity;

/* loaded from: classes3.dex */
public class StepRefreshTokenFailedFragment extends BaseFragment {
    public static final String STEP_NAME = "StepRefreshTokenFailedFragment";
    TextView a;
    private BootActivity b;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_big_step_refresh_token_fail, viewGroup, false);
        this.a = (TextView) inflate.findViewById(R.id.relogin);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepRefreshTokenFailedFragment$_SLnL3YDtjcJRZrX4wrJll-PYNs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StepRefreshTokenFailedFragment.this.a(view);
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.b.nextStep(true, null);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.b = (BootActivity) getActivity();
    }
}
