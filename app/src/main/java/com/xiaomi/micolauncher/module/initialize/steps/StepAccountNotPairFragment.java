package com.xiaomi.micolauncher.module.initialize.steps;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.initialize.BootActivity;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;

/* loaded from: classes3.dex */
public class StepAccountNotPairFragment extends BaseFragment implements View.OnClickListener {
    public static final String STEP_NAME = "account_not_pair";
    TextView a;
    TextView b;
    TextView c;
    private BootActivity d;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_big_step_account_not_pair, viewGroup, false);
        this.a = (TextView) inflate.findViewById(R.id.old_account);
        this.b = (TextView) inflate.findViewById(R.id.use_old_account);
        this.c = (TextView) inflate.findViewById(R.id.recovery);
        this.c.setOnClickListener(this);
        this.b.setOnClickListener(this);
        this.a.setText(getString(R.string.init_step_account_old_account, TokenManager.getInstance().getUserId()));
        SystemSetting.getUserProfile().getUserCardObservable().observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<ThirdPartyResponse.UserCard>() { // from class: com.xiaomi.micolauncher.module.initialize.steps.StepAccountNotPairFragment.1
            /* renamed from: a */
            public void onSuccess(ThirdPartyResponse.UserCard userCard) {
                if (!TextUtils.isEmpty(userCard.aliasNick)) {
                    StepAccountNotPairFragment.this.a.setText(StepAccountNotPairFragment.this.getString(R.string.init_step_account_old_account_with_alias, userCard.aliasNick, TokenManager.getInstance().getUserId()));
                }
            }
        });
        this.d = (BootActivity) getActivity();
        return inflate;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.recovery) {
            SettingOpenManager.openResetting(getActivity());
        } else if (id == R.id.use_old_account) {
            this.d.nextStep(false, null);
        }
    }
}
