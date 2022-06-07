package com.xiaomi.passport.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class AccountSettingsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ACCOUNT = 65536;
    private static final String TAG = "AccountSettingsActivity";
    private AccountSettingsFragment mFragment;
    private ServiceTokenFuture mRefreshTokenFuture;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            Toast.makeText(this, R.string.passport_unknow_error, 0).show();
            finish();
        } else if (MiAccountManager.get(this).getXiaomiAccount() != null) {
            addSettingFragment();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != 65536) {
            AccountSettingsFragment accountSettingsFragment = this.mFragment;
            if (accountSettingsFragment != null) {
                accountSettingsFragment.onActivityResultOfFragment(i, i2, intent);
            }
        } else if (i2 == -1) {
            AccountLog.d(TAG, "add account success");
            addSettingFragment();
        } else {
            AccountLog.d(TAG, "add account cancelled");
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (MiAccountManager.get(this).getXiaomiAccount() == null) {
            finish();
            Toast.makeText(this, R.string.no_account, 0).show();
        } else if (needTriggerNewGetAuthToken((Activity) new WeakReference(this).get())) {
            this.mRefreshTokenFuture = MiAccountManager.get(this).getServiceToken(this, "passportapi");
            XiaomiPassportExecutor.getSingleton().execute(new Runnable() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ServiceTokenResult serviceTokenResult = AccountSettingsActivity.this.mRefreshTokenFuture.get();
                        if (serviceTokenResult == null || TextUtils.isEmpty(serviceTokenResult.serviceToken)) {
                            AccountLog.e(AccountSettingsActivity.TAG, "cannot get service token");
                            AccountSettingsActivity.this.finish();
                        }
                    } finally {
                        AccountSettingsActivity.this.mRefreshTokenFuture = null;
                    }
                }
            });
        }
    }

    private boolean needTriggerNewGetAuthToken(Activity activity) {
        return activity != null && !activity.isFinishing() && this.mRefreshTokenFuture == null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void addSettingFragment() {
        this.mFragment = new AccountSettingsFragment();
        Intent intent = getIntent();
        if (!(intent == null || intent.getExtras() == null)) {
            this.mFragment.setArguments(intent.getExtras());
        }
        FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, this.mFragment);
    }
}
