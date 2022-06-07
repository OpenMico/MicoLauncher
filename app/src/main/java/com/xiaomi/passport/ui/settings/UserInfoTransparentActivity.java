package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask;
import com.xiaomi.passport.ui.settings.UserInfoManager;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;

/* loaded from: classes4.dex */
public class UserInfoTransparentActivity extends Activity {
    private static final long ONE_DAY_IN_MILLIS = 86400000;
    private static final int REQUEST_BIND_RECOVERY_EMAIL = 17;
    private static final int REQUEST_CODE_CHANGE_PASSWORD = 18;
    private static final int REQUEST_PASSPORT_IDENTITY = 16;
    private static final int REQUEST_PASSPORT_PHONE_IDENTITY = 10001;
    private static final int REQUEST_UPDATE_SAFE_PHONE = 10002;
    private static final String TAG = "UserInfoTransparentActivity";
    private Account mAccount;
    private GetIdentityAuthUrlTask mGetIdentityUrlTask;
    private GetIdentityAuthUrlTask mGetPhoneIdentityAuthTask;
    private IdentityAuthReason mIdentityAuthReason;
    private MiAccountManager mMiAccountManager;
    private String mUserInfoType;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        this.mMiAccountManager = MiAccountManager.get(this);
        this.mAccount = this.mMiAccountManager.getXiaomiAccount();
        if (this.mAccount == null) {
            AccountLog.i(TAG, "no xiaomi account");
            finish();
            return;
        }
        this.mUserInfoType = getIntent().getStringExtra(AccountIntent.STAT_KEY_SOURCE);
        switch (UserInfoManager.UserInfoType.valueOf(this.mUserInfoType)) {
            case Modify_User_Phone:
                handlerUserPhoneInfo();
                return;
            case Modify_User_Email:
                handlerUserEmailInfo();
                return;
            case Modify_User_Password:
                handlerUserPasswordInfo();
                return;
            default:
                return;
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        GetIdentityAuthUrlTask getIdentityAuthUrlTask = this.mGetPhoneIdentityAuthTask;
        if (getIdentityAuthUrlTask != null) {
            getIdentityAuthUrlTask.cancel(true);
            this.mGetPhoneIdentityAuthTask = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 16:
                if (i2 == -1) {
                    NotificationAuthResult notificationAuthResult = (NotificationAuthResult) intent.getParcelableExtra("notification_auth_end");
                    if (notificationAuthResult != null) {
                        new UserDataProxy(this).setUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN, notificationAuthResult.serviceToken);
                        onPassIdentityAuth(this.mIdentityAuthReason);
                    } else {
                        return;
                    }
                }
                finish();
                return;
            case 17:
                if (i2 == 9999) {
                    doIdentityAuth(IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE);
                    return;
                } else {
                    finish();
                    return;
                }
            case 18:
                finish();
                return;
            default:
                switch (i) {
                    case 10001:
                        if (i2 == -1) {
                            NotificationAuthResult notificationAuthResult2 = (NotificationAuthResult) intent.getParcelableExtra("notification_auth_end");
                            if (notificationAuthResult2 != null) {
                                new UserDataProxy(this).setUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN, notificationAuthResult2.serviceToken);
                                startUpdateSafePhone();
                            } else {
                                return;
                            }
                        }
                        finish();
                        return;
                    case REQUEST_UPDATE_SAFE_PHONE /* 10002 */:
                        finish();
                        return;
                    default:
                        return;
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateSafePhone() {
        Intent intent = new Intent(this, BindPhoneActivity.class);
        intent.setPackage(getPackageName());
        startActivityForResult(intent, REQUEST_UPDATE_SAFE_PHONE);
    }

    private void handlerUserPhoneInfo() {
        doPhoneIdentityAuth();
    }

    private void handlerUserEmailInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(this.mAccount.name, 0);
        String string = sharedPreferences.getString(Constants.SP_UNACTIVATED_EMAIL_ADDRESS, null);
        long j = sharedPreferences.getLong(Constants.SP_UNACTIVATED_EMAIL_TIME_STAMP, 0L);
        String userData = new UserDataProxy(this).getUserData(this.mAccount, Constants.ACCOUNT_USER_EMAIL);
        if (System.currentTimeMillis() - j > 86400000) {
            sharedPreferences.edit().clear().apply();
        } else if (isStillUnactivatedEmail(userData, string)) {
            startBindSafeEmailActivity(true, string);
            return;
        }
        if (TextUtils.isEmpty(userData)) {
            doIdentityAuth(IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE);
        } else {
            showConfirmDialog(R.string.update_email_address_dialog_title, R.string.update_email_address_dialog_message, 17039370, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.UserInfoTransparentActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    UserInfoTransparentActivity.this.doIdentityAuth(IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE);
                }
            }, 17039360, null);
        }
    }

    private void showConfirmDialog(int i, int i2, int i3, DialogInterface.OnClickListener onClickListener, int i4, DialogInterface.OnClickListener onClickListener2) {
        new AlertDialog.Builder(this).setTitle(i).setMessage(i2).setPositiveButton(i3, onClickListener).setNegativeButton(i4, onClickListener2).create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (this.mGetIdentityUrlTask == null) {
            this.mIdentityAuthReason = identityAuthReason;
            this.mGetIdentityUrlTask = new GetIdentityAuthUrlTask(this, new UserDataProxy(this).getUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), identityAuthReason, new GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback() { // from class: com.xiaomi.passport.ui.settings.UserInfoTransparentActivity.2
                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(int i) {
                    UserInfoTransparentActivity.this.mGetIdentityUrlTask = null;
                    Toast.makeText(UserInfoTransparentActivity.this, i, 1).show();
                    UserInfoTransparentActivity.this.finish();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onNeedNotification(String str) {
                    UserInfoTransparentActivity.this.mGetIdentityUrlTask = null;
                    Intent newNotificationIntent = AuthenticatorUtil.newNotificationIntent(UserInfoTransparentActivity.this, null, str, "passportapi", true, null);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_USER_ID, UserInfoTransparentActivity.this.mAccount.name);
                    UserInfoTransparentActivity.this.overridePendingTransition(0, 0);
                    UserInfoTransparentActivity.this.startActivityForResult(newNotificationIntent, 16);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onSuccess() {
                    UserInfoTransparentActivity.this.mGetIdentityUrlTask = null;
                    UserInfoTransparentActivity userInfoTransparentActivity = UserInfoTransparentActivity.this;
                    userInfoTransparentActivity.onPassIdentityAuth(userInfoTransparentActivity.mIdentityAuthReason);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(ServerError serverError) {
                    UserInfoTransparentActivity.this.mGetIdentityUrlTask = null;
                    if (!UserInfoTransparentActivity.this.isFinishing()) {
                        CommonErrorHandler.Companion.showErrorWithTips(UserInfoTransparentActivity.this, serverError);
                    }
                }
            });
            this.mGetIdentityUrlTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    private boolean isStillUnactivatedEmail(String str, String str2) {
        return !TextUtils.isEmpty(str2) && (TextUtils.isEmpty(str) || !str2.equals(str));
    }

    private void startBindSafeEmailActivity(boolean z, String str) {
        Intent intent = new Intent(this, BindSafeEmailActivity.class);
        intent.putExtra(Constants.EXTRA_HAS_UNACTIVATED_EMAIL, z);
        intent.putExtra(Constants.SP_UNACTIVATED_EMAIL_ADDRESS, str);
        startActivityForResult(intent, 17);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.passport.ui.settings.UserInfoTransparentActivity$4  reason: invalid class name */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason = new int[IdentityAuthReason.values().length];

        static {
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $SwitchMap$com$xiaomi$passport$ui$settings$UserInfoManager$UserInfoType = new int[UserInfoManager.UserInfoType.values().length];
            try {
                $SwitchMap$com$xiaomi$passport$ui$settings$UserInfoManager$UserInfoType[UserInfoManager.UserInfoType.Modify_User_Phone.ordinal()] = 1;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$settings$UserInfoManager$UserInfoType[UserInfoManager.UserInfoType.Modify_User_Email.ordinal()] = 2;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$settings$UserInfoManager$UserInfoType[UserInfoManager.UserInfoType.Modify_User_Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPassIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (identityAuthReason != null && AnonymousClass4.$SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[identityAuthReason.ordinal()] == 1) {
            startBindSafeEmailActivity(false, null);
        }
    }

    private void handlerUserPasswordInfo() {
        Intent newIntent = ChangePasswordActivity.newIntent(this);
        overridePendingTransition(0, 0);
        startActivityForResult(newIntent, 18);
    }

    private void doPhoneIdentityAuth() {
        if (this.mGetPhoneIdentityAuthTask == null) {
            this.mGetPhoneIdentityAuthTask = new GetIdentityAuthUrlTask(this, new UserDataProxy(this).getUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), IdentityAuthReason.MODIFY_SAFE_PHONE, new GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback() { // from class: com.xiaomi.passport.ui.settings.UserInfoTransparentActivity.3
                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onNeedNotification(String str) {
                    UserInfoTransparentActivity.this.mGetPhoneIdentityAuthTask = null;
                    Intent newNotificationIntent = AuthenticatorUtil.newNotificationIntent(UserInfoTransparentActivity.this, null, str, "passportapi", true, null);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_USER_ID, UserInfoTransparentActivity.this.mAccount.name);
                    UserInfoTransparentActivity.this.startActivityForResult(newNotificationIntent, 10001);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onSuccess() {
                    UserInfoTransparentActivity.this.mGetPhoneIdentityAuthTask = null;
                    UserInfoTransparentActivity.this.startUpdateSafePhone();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(int i) {
                    UserInfoTransparentActivity.this.mGetPhoneIdentityAuthTask = null;
                    Toast.makeText(UserInfoTransparentActivity.this, i, 1).show();
                    UserInfoManager.sendModifyUserPhoneResultByLocalBroadcast(UserInfoTransparentActivity.this.getApplicationContext(), false, i);
                    UserInfoTransparentActivity.this.finish();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(ServerError serverError) {
                    UserInfoTransparentActivity.this.mGetPhoneIdentityAuthTask = null;
                    if (!UserInfoTransparentActivity.this.isFinishing()) {
                        CommonErrorHandler.Companion.showErrorWithTips(UserInfoTransparentActivity.this, serverError);
                    }
                }
            });
            this.mGetPhoneIdentityAuthTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }
}
