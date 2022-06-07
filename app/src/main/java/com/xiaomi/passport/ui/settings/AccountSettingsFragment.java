package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.Gender;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.AccountChangedBroadcastHelper;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask;
import com.xiaomi.passport.ui.settings.QueryUserInfoTask;
import com.xiaomi.passport.ui.settings.UploadMiUserProfileTask;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class AccountSettingsFragment extends Fragment {
    private static final long ONE_DAY_IN_MILLIS = 86400000;
    private static final int REQUEST_BIND_RECOVERY_EMAIL = 17;
    private static final int REQUEST_CODE_CHANGE_PASSWORD = 18;
    private static final int REQUEST_PASSPORT_IDENTITY = 16;
    private static final String TAG = "AccountSettingsFragment";
    private Account mAccount;
    private Activity mActivity;
    private GetIdentityAuthUrlTask mGetIdentityUrlTask;
    private IdentityAuthReason mIdentityAuthReason;
    private AccountPreferenceView mPrefUserAvatar;
    private AccountPreferenceView mPrefUserEmail;
    private AccountPreferenceView mPrefUserGender;
    private AccountPreferenceView mPrefUserID;
    private AccountPreferenceView mPrefUserName;
    private AccountPreferenceView mPrefUserPassword;
    private AccountPreferenceView mPrefUserPhone;
    private QueryUserInfoTask mQueryUserInfoTask;
    private Bitmap mUserAvatarBitmap;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private HashMap<UploadProfileType, UploadMiUserProfileTask> mUploadTask = new HashMap<>();
    private DialogInterface.OnClickListener mUpdateAvatarOnClickListener = new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.3
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case 0:
                    AccountSettingsFragment.this.startUpdateAvatarActivity(UserAvatarUpdateActivity.CAMERA);
                    break;
                case 1:
                    AccountSettingsFragment.this.startUpdateAvatarActivity(UserAvatarUpdateActivity.GALLERY);
                    break;
            }
            dialogInterface.dismiss();
        }
    };
    private View.OnClickListener mPreferenceItemCallback = new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.4
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                if (view == AccountSettingsFragment.this.mPrefUserName) {
                    AccountSettingsFragment.this.showUserNameUpdateDialog();
                } else if (view == AccountSettingsFragment.this.mPrefUserGender) {
                    AccountSettingsFragment.this.showUserGenderUpdateDialog();
                } else if (view == AccountSettingsFragment.this.mPrefUserAvatar) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingsFragment.this.mActivity);
                    builder.setTitle(R.string.user_avatar_update_title);
                    builder.setSingleChoiceItems(new String[]{AccountSettingsFragment.this.getString(R.string.account_user_avatar_from_camera), AccountSettingsFragment.this.getString(R.string.account_user_avatar_from_album)}, 0, AccountSettingsFragment.this.mUpdateAvatarOnClickListener);
                    builder.show();
                } else if (view == AccountSettingsFragment.this.mPrefUserPhone) {
                    AccountSettingsFragment.this.startActivity(SysHelper.newViewPhoneInfoIntent(AccountSettingsFragment.this.getActivity(), AccountSettingsFragment.TAG));
                } else if (view == AccountSettingsFragment.this.mPrefUserEmail) {
                    AccountSettingsFragment.this.updateEmailAddress();
                } else if (view == AccountSettingsFragment.this.mPrefUserPassword) {
                    AccountSettingsFragment.this.startChangePasswordActivity();
                }
            } catch (ActivityNotFoundException e) {
                AccountLog.e(AccountSettingsFragment.TAG, "activity not found", e);
                Toast.makeText(AccountSettingsFragment.this.getActivity(), R.string.activity_not_found_notice, 1).show();
            }
        }
    };
    private AccountManagerCallback<Boolean> mSignOutCallback = new AccountManagerCallback<Boolean>() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.5
        @Override // android.accounts.AccountManagerCallback
        public void run(AccountManagerFuture<Boolean> accountManagerFuture) {
            boolean z;
            Activity activity;
            try {
                z = accountManagerFuture.getResult().booleanValue();
            } catch (AuthenticatorException | OperationCanceledException | IOException e) {
                AccountLog.e(AccountSettingsFragment.TAG, "sign out failed", e);
                z = false;
            }
            if (z && (activity = AccountSettingsFragment.this.getActivity()) != null) {
                AccountChangedBroadcastHelper.sendBroadcast(activity, AccountSettingsFragment.this.mAccount, AccountChangedBroadcastHelper.UpdateType.POST_REMOVE);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    };

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        this.mAccount = MiAccountManager.get(this.mActivity).getXiaomiAccount();
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.account_settings_layout, viewGroup, false);
        this.mPrefUserAvatar = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_avatar);
        this.mPrefUserName = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_user_name);
        this.mPrefUserID = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_user_id);
        this.mPrefUserGender = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_user_gender);
        this.mPrefUserPhone = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_user_phone);
        this.mPrefUserEmail = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_user_email);
        this.mPrefUserPassword = (AccountPreferenceView) inflate.findViewById(R.id.pref_account_password);
        this.mPrefUserAvatar.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserName.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserID.setRightArrowVisible(false);
        this.mPrefUserGender.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserPhone.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserEmail.setOnClickListener(this.mPreferenceItemCallback);
        this.mPrefUserPassword.setOnClickListener(this.mPreferenceItemCallback);
        ((Button) inflate.findViewById(R.id.logout_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AccountSettingsFragment.this.signOut();
            }
        });
        inflate.findViewById(R.id.profile_back).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AccountSettingsFragment.this.getActivity().onBackPressed();
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signOut() {
        Activity activity = getActivity();
        AccountChangedBroadcastHelper.sendBroadcast(activity, this.mAccount, AccountChangedBroadcastHelper.UpdateType.PRE_REMOVE);
        MiAccountManager.get(activity).removeXiaomiAccount(this.mSignOutCallback, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpdateAvatarActivity(String str) {
        Intent intent = new Intent(getActivity(), UserAvatarUpdateActivity.class);
        intent.setPackage(getActivity().getPackageName());
        intent.putExtra(UserAvatarUpdateActivity.EXTRA_UPDATE_AVATAR_TYPE, str);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startChangePasswordActivity() {
        Intent newIntent = ChangePasswordActivity.newIntent(getActivity());
        getActivity().overridePendingTransition(0, 0);
        startActivityForResult(newIntent, 18);
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        queryUserInfoOnline();
    }

    private void queryUserInfoOnline() {
        if (this.mQueryUserInfoTask == null || AsyncTask.Status.RUNNING != this.mQueryUserInfoTask.getStatus()) {
            this.mQueryUserInfoTask = new QueryUserInfoTask(this.mActivity.getApplicationContext(), new UpdateOnlineUserInfoCallback(this));
            this.mQueryUserInfoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.mActivity = getActivity();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        cancelUploadProfileTask();
        Bitmap bitmap = this.mUserAvatarBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mUserAvatarBitmap = null;
        }
        GetIdentityAuthUrlTask getIdentityAuthUrlTask = this.mGetIdentityUrlTask;
        if (getIdentityAuthUrlTask != null) {
            getIdentityAuthUrlTask.cancel(true);
            this.mGetIdentityUrlTask = null;
        }
        this.mActivity = null;
        super.onDestroy();
    }

    private void cancelUploadProfileTask() {
        HashMap<UploadProfileType, UploadMiUserProfileTask> hashMap = this.mUploadTask;
        if (hashMap != null) {
            Iterator<UploadProfileType> it = hashMap.keySet().iterator();
            while (it.hasNext()) {
                UploadMiUserProfileTask uploadMiUserProfileTask = this.mUploadTask.get(it.next());
                if (uploadMiUserProfileTask != null) {
                    uploadMiUserProfileTask.cancel(true);
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUserInfo() {
        String str;
        Activity activity = this.mActivity;
        if (activity != null && !activity.isFinishing()) {
            if (this.mAccount == null) {
                this.mActivity.finish();
                return;
            }
            Activity activity2 = this.mActivity;
            if (activity2 != null) {
                UserDataProxy userDataProxy = new UserDataProxy(activity2);
                String userData = userDataProxy.getUserData(this.mAccount, Constants.ACCOUNT_USER_NAME);
                if (TextUtils.isEmpty(userData)) {
                    userData = getString(R.string.account_none_user_name);
                }
                this.mPrefUserName.setValue(userData);
                this.mPrefUserID.setValue(this.mAccount.name);
                String userData2 = userDataProxy.getUserData(this.mAccount, Constants.ACCOUNT_USER_GENDER);
                if (TextUtils.isEmpty(userData2)) {
                    str = getString(R.string.account_no_set);
                } else {
                    str = getResources().getStringArray(R.array.account_user_gender_name)[!userData2.equals(Gender.MALE.getType())];
                }
                this.mPrefUserGender.setValue(str);
                String userData3 = userDataProxy.getUserData(this.mAccount, Constants.ACCOUNT_AVATAR_FILE_NAME);
                Bitmap bitmap = this.mUserAvatarBitmap;
                if (bitmap != null) {
                    bitmap.recycle();
                }
                this.mUserAvatarBitmap = SysHelper.createPhoto(getActivity(), userData3);
                Bitmap bitmap2 = this.mUserAvatarBitmap;
                if (bitmap2 != null) {
                    this.mPrefUserAvatar.setImageBitmap(bitmap2);
                }
                String userData4 = userDataProxy.getUserData(this.mAccount, Constants.ACCOUNT_USER_EMAIL);
                AccountPreferenceView accountPreferenceView = this.mPrefUserEmail;
                if (TextUtils.isEmpty(userData4)) {
                    userData4 = getString(R.string.account_none_bind_info);
                }
                accountPreferenceView.setValue(userData4);
                String userData5 = userDataProxy.getUserData(this.mAccount, Constants.ACCOUNT_USER_PHONE);
                AccountPreferenceView accountPreferenceView2 = this.mPrefUserPhone;
                if (TextUtils.isEmpty(userData5)) {
                    userData5 = getString(R.string.account_none_bind_info);
                }
                accountPreferenceView2.setValue(userData5);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class UpdateOnlineUserInfoCallback implements QueryUserInfoTask.QueryUserInfoCallback {
        private final WeakReference<AccountSettingsFragment> mFragmentWeakRef;

        UpdateOnlineUserInfoCallback(AccountSettingsFragment accountSettingsFragment) {
            this.mFragmentWeakRef = new WeakReference<>(accountSettingsFragment);
        }

        @Override // com.xiaomi.passport.ui.settings.QueryUserInfoTask.QueryUserInfoCallback
        public void onResult(XiaomiUserCoreInfo xiaomiUserCoreInfo) {
            AccountSettingsFragment accountSettingsFragment = this.mFragmentWeakRef.get();
            if (accountSettingsFragment != null) {
                accountSettingsFragment.refreshUserInfo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserNameUpdateDialog() {
        final EditText editText = new EditText(getActivity());
        editText.setText(this.mPrefUserName.getValue());
        editText.setSelection(editText.getText().length());
        final AlertDialog show = new AlertDialog.Builder(getActivity()).setTitle(R.string.account_user_name_dialog_title).setView(editText).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).show();
        ((ViewGroup.MarginLayoutParams) editText.getLayoutParams()).setMarginStart((int) getResources().getDimension(R.dimen.preference_left_margin));
        show.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = editText.getText().toString();
                String userNameInvalidReason = AccountSettingsFragment.this.getUserNameInvalidReason(obj);
                if (!TextUtils.isEmpty(userNameInvalidReason)) {
                    editText.setError(userNameInvalidReason);
                    return;
                }
                show.dismiss();
                AccountSettingsFragment.this.startUploadUserProfileTask(UploadProfileType.TYPE_USER_NAME, obj, null, null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUploadUserProfileTask(UploadProfileType uploadProfileType, String str, Calendar calendar, Gender gender) {
        if (uploadProfileType != null) {
            UploadMiUserProfileTask uploadMiUserProfileTask = this.mUploadTask.get(uploadProfileType);
            if (uploadMiUserProfileTask != null) {
                uploadMiUserProfileTask.cancel(true);
            }
            new UploadMiUserProfileTask(getActivity(), str, gender, new setUserProfileValueAfterTask()).executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class setUserProfileValueAfterTask implements UploadMiUserProfileTask.IUploadUserProfile {
        private setUserProfileValueAfterTask() {
        }

        @Override // com.xiaomi.passport.ui.settings.UploadMiUserProfileTask.IUploadUserProfile
        public void onFinishUploading(String str, Gender gender) {
            UserDataProxy userDataProxy = new UserDataProxy(AccountSettingsFragment.this.getActivity());
            if (!TextUtils.isEmpty(str)) {
                AccountSettingsFragment.this.mPrefUserName.setValue(str);
                userDataProxy.setUserData(AccountSettingsFragment.this.mAccount, Constants.ACCOUNT_USER_NAME, str);
            } else if (gender != null) {
                AccountSettingsFragment.this.mPrefUserGender.setValue(AccountSettingsFragment.this.getResources().getStringArray(R.array.account_user_gender_name)[gender == Gender.MALE ? (char) 0 : (char) 1]);
                userDataProxy.setUserData(AccountSettingsFragment.this.mAccount, Constants.ACCOUNT_USER_GENDER, gender.getType());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getUserNameInvalidReason(String str) {
        if (TextUtils.isEmpty(str)) {
            return getString(R.string.account_empty_user_name);
        }
        if (str.length() < 2) {
            return getString(R.string.account_error_shorter_user_name);
        }
        if (str.length() > 20) {
            return getString(R.string.account_error_longer_user_name);
        }
        if (str.matches("\\s+")) {
            return getString(R.string.account_error_all_space_user_name);
        }
        if (str.contains("<") || str.contains(">") || str.contains("/")) {
            return getString(R.string.account_error_invalid_user_name);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserGenderUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.account_user_gender);
        String[] stringArray = getResources().getStringArray(R.array.account_user_gender_name);
        boolean equals = this.mPrefUserGender.getValue().toString().equals(stringArray[1]);
        builder.setSingleChoiceItems(stringArray, equals ? 1 : 0, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                AccountSettingsFragment.this.startUploadUserProfileTask(UploadProfileType.TYPE_GENDER, null, null, i == 0 ? Gender.MALE : Gender.FEMALE);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void onActivityResultOfFragment(int i, int i2, Intent intent) {
        NotificationAuthResult notificationAuthResult;
        AccountLog.d(TAG, "onActivityResult() requestCode:" + i + " resultCode:" + i2);
        switch (i) {
            case 16:
                if (i2 == -1 && (notificationAuthResult = (NotificationAuthResult) intent.getParcelableExtra("notification_auth_end")) != null) {
                    new UserDataProxy(getActivity()).setUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN, notificationAuthResult.serviceToken);
                    onPassIdentityAuth(this.mIdentityAuthReason);
                    return;
                }
                return;
            case 17:
                if (i2 == 9999) {
                    doIdentityAuth(IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE);
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.passport.ui.settings.AccountSettingsFragment$10  reason: invalid class name */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason = new int[IdentityAuthReason.values().length];

        static {
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPassIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (identityAuthReason != null && AnonymousClass10.$SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[identityAuthReason.ordinal()] == 1) {
            startBindSafeEmailActivity(false, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (this.mGetIdentityUrlTask == null) {
            this.mIdentityAuthReason = identityAuthReason;
            this.mGetIdentityUrlTask = new GetIdentityAuthUrlTask(getActivity(), new UserDataProxy(getActivity()).getUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), identityAuthReason, new GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.8
                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(int i) {
                    AccountSettingsFragment.this.mGetIdentityUrlTask = null;
                    Toast.makeText(AccountSettingsFragment.this.getActivity(), i, 1).show();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onNeedNotification(String str) {
                    AccountSettingsFragment.this.mGetIdentityUrlTask = null;
                    Intent newNotificationIntent = AuthenticatorUtil.newNotificationIntent(AccountSettingsFragment.this.getActivity(), null, str, "passportapi", true, null);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_USER_ID, AccountSettingsFragment.this.mAccount.name);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_PASSTOKEN, AuthenticatorUtil.getPassToken(AccountSettingsFragment.this.getActivity().getApplicationContext(), AccountSettingsFragment.this.mAccount));
                    AccountSettingsFragment.this.getActivity().overridePendingTransition(0, 0);
                    AccountSettingsFragment.this.getActivity().startActivityForResult(newNotificationIntent, 16);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onSuccess() {
                    AccountSettingsFragment.this.mGetIdentityUrlTask = null;
                    AccountSettingsFragment accountSettingsFragment = AccountSettingsFragment.this;
                    accountSettingsFragment.onPassIdentityAuth(accountSettingsFragment.mIdentityAuthReason);
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(ServerError serverError) {
                    AccountSettingsFragment.this.mGetIdentityUrlTask = null;
                    if (AccountSettingsFragment.this.getActivity() != null && !AccountSettingsFragment.this.getActivity().isFinishing()) {
                        CommonErrorHandler.Companion.showErrorWithTips(AccountSettingsFragment.this.getActivity(), serverError);
                    }
                }
            });
            this.mGetIdentityUrlTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEmailAddress() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(this.mAccount.name, 0);
        String string = sharedPreferences.getString(Constants.SP_UNACTIVATED_EMAIL_ADDRESS, null);
        long j = sharedPreferences.getLong(Constants.SP_UNACTIVATED_EMAIL_TIME_STAMP, 0L);
        String userData = new UserDataProxy(getActivity()).getUserData(this.mAccount, Constants.ACCOUNT_USER_EMAIL);
        if (System.currentTimeMillis() - j > 86400000) {
            sharedPreferences.edit().clear().apply();
        } else if (isStillUnactivatedEmail(userData, string)) {
            startBindSafeEmailActivity(true, string);
            return;
        }
        if (TextUtils.isEmpty(userData)) {
            doIdentityAuth(IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE);
        } else {
            showConfirmDialog(R.string.update_email_address_dialog_title, R.string.update_email_address_dialog_message, 17039370, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.AccountSettingsFragment.9
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    AccountSettingsFragment.this.doIdentityAuth(IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE);
                }
            }, 17039360, null);
        }
    }

    private boolean isStillUnactivatedEmail(String str, String str2) {
        return !TextUtils.isEmpty(str2) && (TextUtils.isEmpty(str) || !str2.equals(str));
    }

    private void startBindSafeEmailActivity(boolean z, String str) {
        Intent intent = new Intent(getActivity(), BindSafeEmailActivity.class);
        intent.putExtra(Constants.EXTRA_HAS_UNACTIVATED_EMAIL, z);
        intent.putExtra(Constants.SP_UNACTIVATED_EMAIL_ADDRESS, str);
        getActivity().startActivityForResult(intent, 17);
    }

    private void showConfirmDialog(int i, int i2, int i3, DialogInterface.OnClickListener onClickListener, int i4, DialogInterface.OnClickListener onClickListener2) {
        new AlertDialog.Builder(getActivity()).setTitle(i).setMessage(i2).setPositiveButton(i3, onClickListener).setNegativeButton(i4, onClickListener2).create().show();
    }
}
