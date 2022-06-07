package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask;
import com.xiaomi.passport.ui.settings.QueryUserInfoTask;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;

/* loaded from: classes4.dex */
public class UserPhoneInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PASSPORT_PHONE_IDENTITY = 10001;
    private static final int REQUEST_UPDATE_SAFE_PHONE = 10002;
    private static final String TAG = "UserPhoneInfoActivity";
    private Account mAccount;
    private GetIdentityAuthUrlTask mGetPhoneIdentityAuthTask;
    private MiAccountManager mMiAccountManager;
    private QueryUserInfoTask mQueryUserInfoTask;
    private QueryUserInfoTask.QueryUserInfoCallback mUpdateCallback = new QueryUserInfoTask.QueryUserInfoCallback() { // from class: com.xiaomi.passport.ui.settings.UserPhoneInfoActivity.1
        @Override // com.xiaomi.passport.ui.settings.QueryUserInfoTask.QueryUserInfoCallback
        public void onResult(XiaomiUserCoreInfo xiaomiUserCoreInfo) {
            UserPhoneInfoActivity.this.refreshUpdatePhoneInfo();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        setContentView(R.layout.user_phone_info);
        this.mMiAccountManager = MiAccountManager.get(this);
        this.mAccount = this.mMiAccountManager.getXiaomiAccount();
        if (this.mAccount == null) {
            AccountLog.i(TAG, "no xiaomi account");
            finish();
            return;
        }
        initIconInfo(findViewById(R.id.use_sign_in), R.drawable.icon_sign_in, R.string.sign_in);
        initIconInfo(findViewById(R.id.use_get_back_pwd), R.drawable.icon_get_back_pwd, R.string.get_back_pwd);
        initIconInfo(findViewById(R.id.use_identity), R.drawable.icon_identity, R.string.identity);
        refreshUpdatePhoneInfo();
        queryUserInfoOnline();
    }

    private void queryUserInfoOnline() {
        if (this.mQueryUserInfoTask == null || AsyncTask.Status.RUNNING != this.mQueryUserInfoTask.getStatus()) {
            this.mQueryUserInfoTask = new QueryUserInfoTask(this, this.mUpdateCallback);
            this.mQueryUserInfoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        AccountStatInterface.getInstance().statPageStart(TAG);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        AccountStatInterface.getInstance().statPageEnd(TAG);
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        GetIdentityAuthUrlTask getIdentityAuthUrlTask = this.mGetPhoneIdentityAuthTask;
        if (getIdentityAuthUrlTask != null) {
            getIdentityAuthUrlTask.cancel(true);
            this.mGetPhoneIdentityAuthTask = null;
        }
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.action_btn) {
            doPhoneIdentityAuth();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        NotificationAuthResult notificationAuthResult;
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 10001:
                if (i2 == -1 && (notificationAuthResult = (NotificationAuthResult) intent.getParcelableExtra("notification_auth_end")) != null) {
                    new UserDataProxy(this).setUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN, notificationAuthResult.serviceToken);
                    startUpdateSafePhone();
                    return;
                }
                return;
            case REQUEST_UPDATE_SAFE_PHONE /* 10002 */:
                if (i2 == -1) {
                    refreshUpdatePhoneInfo();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUpdatePhoneInfo() {
        String userData = new UserDataProxy(this).getUserData(this.mAccount, Constants.ACCOUNT_USER_PHONE);
        boolean isEmpty = TextUtils.isEmpty(userData);
        ImageView imageView = (ImageView) findViewById(R.id.icon_phone);
        int i = 8;
        if (imageView != null) {
            imageView.setVisibility(isEmpty ? 8 : 0);
        }
        TextView textView = (TextView) findViewById(R.id.phone_num);
        if (textView != null) {
            if (isEmpty) {
                userData = getString(R.string.no_phone);
            }
            textView.setText(userData);
        }
        TextView textView2 = (TextView) findViewById(R.id.update_phone_notice);
        if (textView2 != null) {
            if (!isEmpty) {
                i = 0;
            }
            textView2.setVisibility(i);
        }
        Button button = (Button) findViewById(R.id.action_btn);
        if (button != null) {
            button.setText(isEmpty ? R.string.action_add_phone : R.string.action_update_phone);
            button.setOnClickListener(this);
        }
    }

    private void initIconInfo(View view, int i, int i2) {
        if (view != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            if (imageView != null) {
                imageView.setImageDrawable(getResources().getDrawable(i));
            }
            TextView textView = (TextView) view.findViewById(R.id.icon_desc);
            if (textView != null) {
                textView.setText(i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateSafePhone() {
        Intent intent = new Intent(this, BindPhoneActivity.class);
        intent.setPackage(getPackageName());
        startActivityForResult(intent, REQUEST_UPDATE_SAFE_PHONE);
    }

    private void doPhoneIdentityAuth() {
        if (this.mGetPhoneIdentityAuthTask == null) {
            this.mGetPhoneIdentityAuthTask = new GetIdentityAuthUrlTask(this, new UserDataProxy(this).getUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), IdentityAuthReason.MODIFY_SAFE_PHONE, new GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback() { // from class: com.xiaomi.passport.ui.settings.UserPhoneInfoActivity.2
                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onNeedNotification(String str) {
                    UserPhoneInfoActivity.this.mGetPhoneIdentityAuthTask = null;
                    Intent newNotificationIntent = AuthenticatorUtil.newNotificationIntent(UserPhoneInfoActivity.this, null, str, "passportapi", true, null);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_USER_ID, UserPhoneInfoActivity.this.mAccount.name);
                    UserPhoneInfoActivity.this.startActivityForResult(newNotificationIntent, 10001);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onSuccess() {
                    UserPhoneInfoActivity.this.mGetPhoneIdentityAuthTask = null;
                    UserPhoneInfoActivity.this.startUpdateSafePhone();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(int i) {
                    UserPhoneInfoActivity.this.mGetPhoneIdentityAuthTask = null;
                    Toast.makeText(UserPhoneInfoActivity.this, i, 1).show();
                    UserInfoManager.sendModifyUserPhoneResultByLocalBroadcast(UserPhoneInfoActivity.this.getApplicationContext(), false, i);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(ServerError serverError) {
                    UserPhoneInfoActivity.this.mGetPhoneIdentityAuthTask = null;
                    if (!UserPhoneInfoActivity.this.isFinishing()) {
                        CommonErrorHandler.Companion.showErrorWithTips(UserPhoneInfoActivity.this, serverError);
                    }
                }
            });
            this.mGetPhoneIdentityAuthTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }
}
