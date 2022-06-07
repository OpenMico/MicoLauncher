package com.xiaomi.passport.ui.internal;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;

/* loaded from: classes4.dex */
public class BaseActivity extends Activity implements OnBackNotifier {
    OnBackListener mOnBackListener;

    protected boolean needTranslucentStatusBar() {
        return true;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        OnBackListener onBackListener = this.mOnBackListener;
        if (onBackListener == null || !onBackListener.onBackPressed()) {
            try {
                super.onBackPressed();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        XMPassportSettings.ensureApplicationContext(getApplication());
        isTranslucentStatusBar();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        setContentView(LayoutInflater.from(this).inflate(i, (ViewGroup) null));
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        if (isTranslucentStatusBar()) {
            view.setFitsSystemWindows(true);
        }
        super.setContentView(view);
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (isTranslucentStatusBar()) {
            view.setFitsSystemWindows(true);
        }
        super.setContentView(view, layoutParams);
    }

    public boolean isTranslucentStatusBar() {
        return needTranslucentStatusBar();
    }

    protected boolean finishIfAccountExist() {
        Account xiaomiAccount = AuthenticatorUtil.getXiaomiAccount(this);
        if (xiaomiAccount == null) {
            return false;
        }
        finishOnResult(-1, new AccountInfo.Builder().userId(xiaomiAccount.name).build());
        return true;
    }

    @Override // com.xiaomi.passport.ui.internal.OnBackNotifier
    public void setOnBackListener(OnBackListener onBackListener) {
        this.mOnBackListener = onBackListener;
    }

    @Override // com.xiaomi.passport.ui.internal.OnBackNotifier
    public OnBackListener getOnBackListener() {
        return this.mOnBackListener;
    }

    protected void finishOnResult(int i) {
        finishOnResult(i, null);
    }

    protected void finishOnResult(int i, AccountInfo accountInfo) {
        AuthenticatorUtil.handleAccountAuthenticatorResponse(getIntent().getParcelableExtra("accountAuthenticatorResponse"), AccountHelper.getAccountAuthenticatorResponseResult(i, accountInfo, getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false)));
        setResult(i);
        finish();
    }
}
