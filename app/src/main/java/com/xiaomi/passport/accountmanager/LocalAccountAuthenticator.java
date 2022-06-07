package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.accounts.AbstractAccountAuthenticator;
import com.xiaomi.accounts.AccountAuthenticatorResponse;
import com.xiaomi.accounts.AccountManager;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.io.IOException;

/* loaded from: classes4.dex */
public class LocalAccountAuthenticator extends AbstractAccountAuthenticator {
    private static final boolean DEBUG = true;
    private static final String TAG = "LocalAccountAuthenticat";
    private Context mContext;

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str) {
        return null;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public String getAuthTokenLabel(String str) {
        return null;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) throws NetworkErrorException {
        return null;
    }

    public LocalAccountAuthenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws NetworkErrorException {
        Account[] accountsByType = AccountManager.get(this.mContext).getAccountsByType("com.xiaomi");
        Bundle bundle2 = new Bundle();
        if (accountsByType.length > 0) {
            AccountLog.d(TAG, "a xiaomi account already exists");
            Account account = accountsByType[0];
            bundle2.putString("authAccount", account.name);
            bundle2.putString("accountType", account.type);
        } else {
            if (TextUtils.isEmpty(str2)) {
                AccountLog.w(TAG, "no service id contained, use passportapi");
                str2 = "passportapi";
            }
            bundle2.putParcelable("intent", AuthenticatorUtil.newAddAccountIntent(this.mContext, str2, bundle, accountAuthenticatorResponse));
        }
        return bundle2;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0078  */
    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.os.Bundle confirmCredentials(com.xiaomi.accounts.AccountAuthenticatorResponse r9, android.accounts.Account r10, android.os.Bundle r11) throws android.accounts.NetworkErrorException {
        /*
            r8 = this;
            android.os.Bundle r7 = new android.os.Bundle
            r7.<init>()
            r0 = 0
            r1 = 1
            r2 = 0
            if (r11 == 0) goto L_0x008b
            java.lang.String r3 = "password"
            boolean r3 = r11.containsKey(r3)
            if (r3 != 0) goto L_0x0014
            goto L_0x008b
        L_0x0014:
            java.lang.String r9 = r10.name
            java.lang.String r10 = "password"
            java.lang.String r10 = r11.getString(r10)
            java.lang.String r3 = "captcha_code"
            java.lang.String r3 = r11.getString(r3)
            java.lang.String r4 = "captcha_ick"
            java.lang.String r11 = r11.getString(r4)
            com.xiaomi.accountsdk.account.data.AccountInfo r10 = com.xiaomi.passport.utils.AccountHelper.getServiceTokenByPassword(r9, r10, r3, r11, r2)     // Catch: IOException -> 0x0082, IllegalDeviceException -> 0x0063, InvalidResponseException -> 0x005e, InvalidCredentialException -> 0x0055, AccessDeniedException -> 0x0050, AuthenticationFailureException -> 0x004b, NeedVerificationException -> 0x003d, NeedCaptchaException -> 0x0034, InvalidUserNameException -> 0x002f
        L_0x002c:
            r11 = r2
            r2 = r10
            goto L_0x0068
        L_0x002f:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x0067
        L_0x0034:
            r10 = move-exception
            java.lang.String r11 = r10.getCaptchaUrl()
            r10.printStackTrace()
            goto L_0x0068
        L_0x003d:
            com.xiaomi.accountsdk.account.data.AccountInfo$Builder r10 = new com.xiaomi.accountsdk.account.data.AccountInfo$Builder
            r10.<init>()
            com.xiaomi.accountsdk.account.data.AccountInfo$Builder r10 = r10.userId(r9)
            com.xiaomi.accountsdk.account.data.AccountInfo r10 = r10.build()
            goto L_0x002c
        L_0x004b:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x0067
        L_0x0050:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x0067
        L_0x0055:
            r10 = move-exception
            java.lang.String r11 = r10.getCaptchaUrl()
            r10.printStackTrace()
            goto L_0x0068
        L_0x005e:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x0067
        L_0x0063:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0067:
            r11 = r2
        L_0x0068:
            java.lang.String r10 = "authAccount"
            r7.putString(r10, r9)
            java.lang.String r9 = "accountType"
            java.lang.String r10 = "com.xiaomi"
            r7.putString(r9, r10)
            java.lang.String r9 = "booleanResult"
            if (r2 == 0) goto L_0x0079
            r0 = r1
        L_0x0079:
            r7.putBoolean(r9, r0)
            java.lang.String r9 = "captcha_url"
            r7.putString(r9, r11)
            goto L_0x00cb
        L_0x0082:
            r9 = move-exception
            android.accounts.NetworkErrorException r10 = new android.accounts.NetworkErrorException
            java.lang.String r11 = "IO exception when sending request to passport server"
            r10.<init>(r11, r9)
            throw r10
        L_0x008b:
            android.content.Context r3 = r8.mContext
            com.xiaomi.accounts.AccountManager r3 = com.xiaomi.accounts.AccountManager.get(r3)
            java.lang.String r3 = r3.getPassword(r10)
            if (r11 == 0) goto L_0x00a1
            java.lang.String r4 = "verify_only"
            boolean r4 = r11.getBoolean(r4, r1)
            if (r4 == 0) goto L_0x00a0
            goto L_0x00a1
        L_0x00a0:
            r1 = r0
        L_0x00a1:
            if (r1 == 0) goto L_0x00b7
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 == 0) goto L_0x00b7
            if (r11 != 0) goto L_0x00b0
            android.os.Bundle r11 = new android.os.Bundle
            r11.<init>()
        L_0x00b0:
            java.lang.String r1 = "verify_only"
            r11.putBoolean(r1, r0)
            r6 = r11
            goto L_0x00b8
        L_0x00b7:
            r6 = r11
        L_0x00b8:
            r3 = 0
            if (r6 == 0) goto L_0x00c3
            java.lang.String r11 = "service_id"
            java.lang.String r11 = r6.getString(r11)
            r5 = r11
            goto L_0x00c4
        L_0x00c3:
            r5 = r2
        L_0x00c4:
            r0 = r8
            r1 = r7
            r2 = r9
            r4 = r10
            r0.fillQuickLoginIntent(r1, r2, r3, r4, r5, r6)
        L_0x00cb:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.accountmanager.LocalAccountAuthenticator.confirmCredentials(com.xiaomi.accounts.AccountAuthenticatorResponse, android.accounts.Account, android.os.Bundle):android.os.Bundle");
    }

    private String getRealPasstoken(Account account) {
        return AuthenticatorUtil.getPassToken(this.mContext, account);
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) {
        return recheckAuthTokenResult(getAuthTokenBundle(accountAuthenticatorResponse, account, str, bundle));
    }

    private Bundle getAuthTokenBundle(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) {
        String str2;
        String str3;
        String string = bundle.getString(AccountManager.KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        Object[] objArr = new Object[2];
        objArr[0] = str.startsWith("weblogin:") ? "websso" : str;
        objArr[1] = string;
        AccountLog.i(TAG, String.format("getting AuthToken, type: %s, package name: %s", objArr));
        Bundle bundle2 = new Bundle();
        Account xiaomiAccount = AuthenticatorUtil.getXiaomiAccount(this.mContext);
        if (xiaomiAccount == null || !xiaomiAccount.name.equals(account.name)) {
            bundle2.putBoolean("booleanResult", false);
            return bundle2;
        }
        if (TextUtils.isEmpty(str)) {
            AccountLog.w(TAG, "getting auth token, but no service url contained, use passportapi");
            str2 = "passportapi";
            str3 = null;
        } else if (str.startsWith("weblogin:")) {
            str3 = str.substring(9);
            str2 = "passportapi";
            if (!AccountHelper.isTrustedWebSsoUrl(str3)) {
                bundle2.putInt("errorCode", 7);
                bundle2.putString("errorMessage", "untrusted web sso url");
                return bundle2;
            }
        } else {
            str2 = str;
            str3 = null;
        }
        String realPasstoken = getRealPasstoken(account);
        if (TextUtils.isEmpty(realPasstoken)) {
            fillQuickLoginIntent(bundle2, accountAuthenticatorResponse, true, account, str2, bundle);
            AccountLog.d(TAG, "passToken is null");
            return bundle2;
        }
        try {
        } catch (IllegalDeviceException e) {
            AccountLog.w(TAG, "get device id failed when getting service token", e);
            bundle2.putInt("errorCode", 3);
            bundle2.putString("errorMessage", "illegal device exception");
        } catch (InvalidCredentialException e2) {
            AccountLog.w(TAG, "invalid credential, passToken is invalid", e2);
            AccountManager.get(this.mContext).clearPassword(account);
            bundle.putString("captcha_url", e2.getCaptchaUrl());
            fillQuickLoginIntent(bundle2, accountAuthenticatorResponse, true, account, str2, bundle);
        } catch (InvalidUserNameException e3) {
            AccountLog.e(TAG, "no such a user", e3);
        } catch (NeedNotificationException e4) {
            AccountLog.w(TAG, "need notification ", e4);
            if (str3 != null) {
                AccountLog.i(TAG, "websso return notification url: " + str2);
                bundle2.putString("authAccount", account.name);
                bundle2.putString("accountType", str3);
                bundle2.putString("authtoken", e4.getNotificationUrl());
                return bundle2;
            }
            bundle2.putParcelable("intent", AuthenticatorUtil.newNotificationIntent(this.mContext, accountAuthenticatorResponse, e4.getNotificationUrl(), str2, true, bundle));
        } catch (AccessDeniedException e5) {
            AccountLog.w(TAG, "access denied", e5);
            bundle2.putInt("errorCode", 5);
            bundle2.putString("errorMessage", e5.toString());
        } catch (AuthenticationFailureException e6) {
            AccountLog.w(TAG, "auth failure", e6);
            bundle2.putInt("errorCode", 5);
            bundle2.putString("errorMessage", e6.toString());
        } catch (InvalidResponseException e7) {
            AccountLog.w(TAG, "invalid response received when getting service token", e7);
            bundle2.putInt("errorCode", 5);
            bundle2.putString("errorMessage", e7.toString());
        } catch (IOException e8) {
            AccountLog.w(TAG, "io exception when getting service token", e8);
            bundle2.putInt("errorCode", 3);
            bundle2.putString("errorMessage", e8.toString());
        }
        if (str3 != null) {
            AccountInfo serviceTokenByPassToken = AccountHelper.getServiceTokenByPassToken(account.name, realPasstoken, null, str3);
            bundle2.putString("authAccount", serviceTokenByPassToken.getUserId());
            bundle2.putString("accountType", str3);
            bundle2.putString("authtoken", serviceTokenByPassToken.getAutoLoginUrl());
            AccountLog.i(TAG, "web sso getAuthToken succeed");
            return bundle2;
        }
        AccountInfo serviceTokenByPassToken2 = AccountHelper.getServiceTokenByPassToken(account.name, realPasstoken, str2);
        AuthenticatorUtil.saveAccountInfoInAM(this.mContext, account, serviceTokenByPassToken2);
        AuthenticatorUtil.updatePassTokenIfNeed(this.mContext, account, serviceTokenByPassToken2);
        Bundle authenticatorResponseBundle = AccountHelper.getAuthenticatorResponseBundle(serviceTokenByPassToken2, false);
        if (authenticatorResponseBundle != null) {
            bundle2.putAll(authenticatorResponseBundle);
        }
        AccountLog.i(TAG, String.format("type: %s, package name: %s, getAuthToken succeed", str, string));
        return bundle2;
    }

    Bundle recheckAuthTokenResult(Bundle bundle) {
        if (AuthenticatorUtil.getXiaomiAccount(this.mContext) != null) {
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("booleanResult", false);
        return bundle2;
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException("updateCredentials not supported");
    }

    @Override // com.xiaomi.accounts.AbstractAccountAuthenticator
    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanResult", true);
        return bundle;
    }

    private void fillQuickLoginIntent(Bundle bundle, AccountAuthenticatorResponse accountAuthenticatorResponse, boolean z, Account account, String str, Bundle bundle2) {
        if (!TextUtils.equals(AccountManager.get(this.mContext).getUserData(account, "has_password"), String.valueOf(true)) && !TextUtils.isEmpty(AuthenticatorUtil.getPassToken(this.mContext, account))) {
            AuthenticatorUtil.isSetPasswordAndUpdateAM(this.mContext, account, null);
        }
        String userData = AccountManager.get(this.mContext).getUserData(account, "has_password");
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        bundle2.putString(BaseConstants.EXTRA_USER_ID, account.name);
        bundle2.putBoolean("need_retry_on_authenticator_response_result", z);
        bundle2.putBoolean("has_password", TextUtils.equals(userData, String.valueOf(true)));
        Intent newQuickLoginIntent = AuthenticatorUtil.newQuickLoginIntent(this.mContext, accountAuthenticatorResponse, bundle2);
        newQuickLoginIntent.putExtra("service_id", str);
        bundle.putParcelable("intent", newQuickLoginIntent);
    }
}
