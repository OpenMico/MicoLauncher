package com.xiaomi.passport.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.passport.PassportUserEnvironment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class AccountHelper {
    private static final String MI_ACCOUNT_HOST_SUFFIX = ".account.xiaomi.com";
    private static final String TAG = "AccountHelper";
    private static final Integer INT_0 = 0;
    private static final String URL_IS_SET_PASSWORD = URLs.URL_ACCOUNT_API_V2_BASE + "/safe/user/isSetPassword";

    public static AccountInfo getStsUrlByPassword(String str, String str2, String str3, String str4, String str5) throws IOException, InvalidResponseException, NeedVerificationException, AuthenticationFailureException, NeedCaptchaException, InvalidCredentialException, AccessDeniedException, InvalidUserNameException, NeedNotificationException, IllegalDeviceException {
        return XMPassport.getStsUrlByPassword(str, str2, str3, getHashedDeviceId(), str4, str5, getEnvInfoArray());
    }

    public static AccountInfo getServiceTokenByPassword(String str, String str2, String str3, String str4, String str5) throws IOException, NeedCaptchaException, InvalidUserNameException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, InvalidCredentialException, NeedVerificationException, IllegalDeviceException {
        try {
            return getServiceTokenByPassword(str, str2, str3, str4, str5, false);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    public static AccountInfo getServiceTokenByPassword(String str, String str2, String str3, String str4, String str5, boolean z) throws IOException, NeedCaptchaException, InvalidUserNameException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, InvalidCredentialException, NeedVerificationException, NeedNotificationException, IllegalDeviceException {
        return XMPassport.loginByPassword(str, str5, getHashedDeviceId(), str2, str3, str4, null, z, getEnvInfoArray());
    }

    public static AccountInfo loginByPassword(PasswordLoginParams passwordLoginParams) throws IOException, NeedCaptchaException, InvalidUserNameException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, InvalidCredentialException, NeedVerificationException, NeedNotificationException, IllegalDeviceException {
        if (passwordLoginParams != null) {
            PasswordLoginParams.Builder copyFrom = PasswordLoginParams.copyFrom(passwordLoginParams);
            if (TextUtils.isEmpty(passwordLoginParams.deviceId)) {
                copyFrom.setDeviceId(getHashedDeviceId());
            }
            if (passwordLoginParams.hashedEnvFactors == null) {
                copyFrom.setHashedEnvFactors(getEnvInfoArray());
            }
            return XMPassport.loginByPassword(copyFrom.build());
        }
        throw new IllegalArgumentException("password login params is null");
    }

    public static AccountInfo getServiceTokenByPassToken(String str, String str2, String str3, String str4) throws IOException, InvalidResponseException, InvalidCredentialException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, IllegalDeviceException, NeedNotificationException {
        return XMPassport.loginByPassTokenNE(str, str3, getHashedDeviceId(), str2, str4);
    }

    public static AccountInfo getServiceTokenByPassToken(String str, String str2, String str3) throws IOException, InvalidResponseException, InvalidCredentialException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, IllegalDeviceException, NeedNotificationException {
        return XMPassport.loginByPassTokenNE(str, str3, getHashedDeviceId(), str2);
    }

    public static AccountInfo getStsUrlByPassToken(String str, String str2, String str3) throws IOException, InvalidResponseException, InvalidCredentialException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, IllegalDeviceException, NeedNotificationException {
        return XMPassport.getStsUrlByPassToken(str, str3, getHashedDeviceId(), str2);
    }

    public static AccountInfo loginByStep2(Step2LoginParams step2LoginParams) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidCredentialException, InvalidResponseException, NeedVerificationException, InvalidUserNameException {
        return XMPassport.loginByStep2(step2LoginParams);
    }

    public static AccountInfo getStsUrlByStep2(String str, String str2, MetaLoginData metaLoginData, boolean z, String str3, String str4) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidCredentialException, InvalidResponseException, NeedVerificationException, InvalidUserNameException, IllegalDeviceException {
        return XMPassport.getStsUrlByStep2(str, str4, getHashedDeviceId(), str2, metaLoginData, z, str3);
    }

    public static AccountInfo getServiceTokenByStep2(String str, String str2, MetaLoginData metaLoginData, boolean z, String str3, String str4) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidCredentialException, InvalidResponseException, NeedVerificationException, InvalidUserNameException, IllegalDeviceException {
        return XMPassport.loginByStep2(str, str4, getHashedDeviceId(), str2, metaLoginData, z, str3);
    }

    public static String getHashedDeviceId() {
        return new HashedDeviceIdUtil(XMPassportSettings.getApplicationContext()).getHashedDeviceIdNoThrow();
    }

    @Deprecated
    public static Pair<Bitmap, String> getCaptchaImage(String str) {
        return XMPassport.getCaptchaImage(str);
    }

    @Deprecated
    public static Pair<Bitmap, String> getIckImage(String str) {
        return XMPassport.getCaptchaImageAndIck(str);
    }

    public static void sendActivateEmail(String str, String str2, String str3) throws IOException, InvalidResponseException {
        EasyMap easyPutOpt = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut("addressType", "EM").easyPut("address", str2).easyPutOpt("region", str3);
        SimpleRequest.MapContent mapContent = null;
        try {
            mapContent = SimpleRequestForAccount.getAsMap(URLs.URL_RESEND_EMAIL, easyPutOpt, null, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (mapContent != null) {
            if (!INT_0.equals(mapContent.getFromBody("code"))) {
                throw new InvalidResponseException("invalid response, failed to send activate email");
            }
            return;
        }
        throw new IOException("failed to register, no response");
    }

    public static Bundle getAccountAuthenticatorResponseResult(int i, String str, String str2) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("authAccount", str);
            bundle.putString("accountType", "com.xiaomi");
        }
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString("authtoken", str2);
        }
        bundle.putBoolean("booleanResult", i == -1);
        if (i == 0) {
            bundle.putInt("errorCode", 4);
        }
        return bundle;
    }

    public static Bundle getAccountAuthenticatorResponseResult(int i, AccountInfo accountInfo, boolean z) {
        Bundle authenticatorResponseBundle = getAuthenticatorResponseBundle(accountInfo, z);
        if (i == 0) {
            authenticatorResponseBundle.putInt("errorCode", 4);
        }
        return authenticatorResponseBundle;
    }

    public static Bundle getAuthenticatorResponseBundle(AccountInfo accountInfo, boolean z) {
        Bundle bundle = new Bundle();
        if (accountInfo == null || accountInfo.userId == null) {
            bundle.putBoolean("booleanResult", false);
            return bundle;
        }
        bundle.putString("authAccount", accountInfo.userId);
        bundle.putString("accountType", "com.xiaomi");
        if (!TextUtils.isEmpty(accountInfo.encryptedUserId)) {
            bundle.putString("encrypted_user_id", accountInfo.encryptedUserId);
        }
        bundle.putBoolean("has_password", accountInfo.getHasPwd());
        if (!TextUtils.isEmpty(accountInfo.autoLoginUrl)) {
            bundle.putString(AccountIntent.EXTRA_STS_URL_RESULT, accountInfo.autoLoginUrl);
            bundle.putString("sts_url", accountInfo.autoLoginUrl);
        }
        String serviceId = accountInfo.getServiceId();
        String serviceToken = accountInfo.getServiceToken();
        if (!TextUtils.isEmpty(serviceId) && !TextUtils.isEmpty(serviceToken)) {
            bundle.putString("authtoken", ExtendedAuthToken.build(serviceToken, accountInfo.getSecurity()).toPlain());
        }
        bundle.putBoolean("booleanResult", true);
        bundle.putBoolean(b.M, z);
        return bundle;
    }

    public static boolean isWebSsoAuthTokenType(String str) {
        return str != null && str.startsWith("weblogin:");
    }

    public static boolean isTrustedWebSsoUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            URL url = new URL(str);
            URL url2 = new URL(XMPassport.ACCOUNT_DOMAIN);
            return url2.getProtocol().equalsIgnoreCase(url.getProtocol()) && url2.getHost().equalsIgnoreCase(url.getHost());
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    public static String[] getEnvInfoArray() {
        return PassportUserEnvironment.Holder.getInstance().getEnvInfoArray(XMPassportSettings.getApplicationContext());
    }

    private static URL parseUrlWithDoubleCheck(String str) {
        try {
            return new URL(new URL(str).toString());
        } catch (MalformedURLException e) {
            AccountLog.w(TAG, e);
            return null;
        }
    }

    private static boolean isAccountDomainUrlWithHttps(String str) {
        URL parseUrlWithDoubleCheck = parseUrlWithDoubleCheck(str);
        return parseUrlWithDoubleCheck != null && "https".equals(parseUrlWithDoubleCheck.getProtocol()) && parseUrlWithDoubleCheck.getUserInfo() == null && parseUrlWithDoubleCheck.getHost().endsWith(MI_ACCOUNT_HOST_SUFFIX);
    }

    public static boolean isMiAccountLoginQRCodeScanResult(String str) {
        return !TextUtils.isEmpty(str) && isAccountDomainUrlWithHttps(str);
    }

    public static boolean isSetPassword(PassportInfo passportInfo, String str, String str2, String str3) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException {
        if (passportInfo != null) {
            SimpleRequest.StringContent asString = SecureRequestForAccount.getAsString(URL_IS_SET_PASSWORD, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPutOpt("sid", str).easyPut("transId", str3), new EasyMap().easyPut("cUserId", passportInfo.getEncryptedUserId()).easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, passportInfo.getServiceToken()).easyPut(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, str2).easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID, UserSpaceIdUtil.getNullableUserSpaceIdCookie()), true, passportInfo.getSecurity());
            if (asString != null) {
                String removeSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(asString);
                try {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody);
                    int i = jSONObject.getInt("code");
                    if (i == 0) {
                        return jSONObject.getJSONObject("data").getBoolean("status");
                    }
                    throw new InvalidResponseException("code: " + i + "desc: " + jSONObject.optString("description"));
                } catch (JSONException unused) {
                    throw new InvalidResponseException("json error: " + removeSafePrefixAndGetRealBody);
                }
            } else {
                throw new InvalidResponseException("http response result should not be null");
            }
        } else {
            throw new IllegalArgumentException("passport info should not be null");
        }
    }

    public static XiaomiUserCoreInfo getXiaomiUserCoreInfo(PassportInfo passportInfo, String str, List<XiaomiUserCoreInfo.Flag> list) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException {
        if (passportInfo != null) {
            return XMPassport.getXiaomiUserCoreInfo(passportInfo, str, list);
        }
        AccountLog.e(TAG, "passportinfo is null");
        return null;
    }
}
