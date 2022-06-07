package com.xiaomi.passport.ui.internal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;

/* loaded from: classes4.dex */
public class LoginQRCodeScanResultActivity extends AppCompatActivity {
    private ScanCodeLoginFragment mFragment;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        this.mFragment = new ScanCodeLoginFragment();
        this.mFragment.setArguments(getIntent().getExtras());
        FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, this.mFragment);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        ScanCodeLoginFragment scanCodeLoginFragment = this.mFragment;
        if (scanCodeLoginFragment == null || !scanCodeLoginFragment.onBackPressed()) {
            try {
                super.onBackPressed();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        if (!isFinishing()) {
            this.mFragment.checkScanCodeSuccess();
        }
        super.finish();
    }
}
