package com.xiaomi.passport.ui.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;

/* loaded from: classes4.dex */
public class BindSafeEmailActivity extends AppCompatActivity {
    private static final String TAG = "BindSafeEmailActivity";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
        } else if (getIntent().getBooleanExtra(Constants.EXTRA_HAS_UNACTIVATED_EMAIL, false)) {
            FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, UnactivatedEmailFragment.getUnactivatedEmailFragment(getIntent().getStringExtra(Constants.SP_UNACTIVATED_EMAIL_ADDRESS)));
        } else {
            InputBindedEmailFragment inputBindedEmailFragment = new InputBindedEmailFragment();
            inputBindedEmailFragment.setArguments(getIntent().getExtras());
            FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, inputBindedEmailFragment);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (MiAccountManager.get(this).getXiaomiAccount() == null) {
            AccountLog.i(TAG, "no xiaomi account");
            finish();
        }
    }
}
