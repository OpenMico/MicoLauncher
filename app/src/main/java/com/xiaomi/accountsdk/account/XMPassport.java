package com.xiaomi.accountsdk.account;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.core.app.NotificationCompat;
import com.milink.base.contract.MiLinkKeys;
import com.umeng.analytics.pro.ai;
import com.xiaomi.account.exception.PassportCAException;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.BindingType;
import com.xiaomi.accountsdk.account.data.CheckRegPhoneParams;
import com.xiaomi.accountsdk.account.data.DeviceModelInfo;
import com.xiaomi.accountsdk.account.data.EmailRegisterParams;
import com.xiaomi.accountsdk.account.data.Gender;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.data.MiCloudAuthInfo;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.accountsdk.account.data.OAuthParameter;
import com.xiaomi.accountsdk.account.data.PassTokenLoginParams;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.data.QRLoginUrlInfo;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.data.SecurityQuestion;
import com.xiaomi.accountsdk.account.data.SendEmailActMsgParams;
import com.xiaomi.accountsdk.account.data.SendPhoneTicketParams;
import com.xiaomi.accountsdk.account.data.SetPasswordParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.data.XiaomiUserInfo;
import com.xiaomi.accountsdk.account.data.XiaomiUserProfile;
import com.xiaomi.accountsdk.account.exception.DeleteSafeAddressException;
import com.xiaomi.accountsdk.account.exception.InvalidBindAddressException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneOrTicketException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedOAuthorizeException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.PassportIOException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.RegisteredPhoneException;
import com.xiaomi.accountsdk.account.exception.SendVerifyCodeExceedLimitException;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.accountsdk.account.exception.UsedEmailAddressException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.PassportLoginRequest;
import com.xiaomi.accountsdk.request.PassportRequestArguments;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.request.UploadFileRequest;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.homepage.adapter.AudiobookAdapterWrap;
import com.xiaomi.passport.PassportUserEnvironment;
import com.xiaomi.passport.utils.AntiSpamIpAddressController;
import com.xiaomi.passport.utils.PassportEnvEncryptUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.hapjs.features.channel.IChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class XMPassport {
    private static final String EXTRA_SCOPE = "extra_scope";
    private static final String ICON_SIZE_SUFFIX_320 = "_320";
    public static final String PASSPORT_SAFE_PREFIX = "&&&START&&&";
    private static final String PASSPORT_SID = "passport";
    private static final int RESULT_CODE_ADDRESS_USED_BY_OTHERS = 70013;
    private static final int RESULT_CODE_ADDRESS_USED_BY_SELF = 70021;
    private static final int RESULT_CODE_DELETE_SECURE_ADDRESS = 25009;
    private static final int RESULT_CODE_EMPTY_VERIFY_CODE = 70012;
    private static final long RESULT_CODE_ERROR_INVALID_PWD = 110021001;
    private static final int RESULT_CODE_ERROR_OLD_PWD = 70001;
    private static final long RESULT_CODE_ERROR_PWD_SAME_AS_EMAIL = 110071001;
    private static final int RESULT_CODE_ERROR_REACH_LIMIT = 70022;
    private static final int RESULT_CODE_ERROR_VERIFY_CODE = 70014;
    private static final int RESULT_CODE_EXTERNAL_ADDRESS_USED = 25005;
    private static final int RESULT_CODE_ILLEGAL_PARAM = 10017;
    private static final int RESULT_CODE_INVALID_EMAIL_ADDRESS = 70006;
    private static final int RESULT_CODE_INVALID_PWD_SAME_AS_EMAIL = 10017;
    private static final int RESULT_CODE_LACK_OF_PARAM = 10016;
    private static final int RESULT_CODE_REGISTERED_PHONE = 25001;
    private static final int RESULT_CODE_REQUEST_RESTRICTED = 10031;
    private static final int RESULT_CODE_SUCCESS = 0;
    private static final int RESULT_CODE_USERNAME = 20003;
    private static final int RESULT_CODE_USER_CREATION_OVER_LIMIT = 25004;
    private static final int RESULT_CODE_USER_RESTRICTED = 20023;
    private static final int RESULT_CODE_VERIFICATION = 81003;
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String TAG = "XMPassport";
    private static final int USER_ADDR_TYPE_EMAIL = 2;
    private static final int USER_ADDR_TYPE_MIUI_FORUM_ALIAS = 9;
    private static final int USER_ADDR_TYPE_PHONE = 1;
    private static final String USER_ID_EXIST = "1";
    private static final String USER_ID_NOT_EXIST = "-1";
    public static final boolean USE_PREVIEW = URLs.USE_PREVIEW;
    @Deprecated
    public static final String ACCOUNT_DOMAIN = URLs.ACCOUNT_DOMAIN;
    @Deprecated
    public static final String URL_ACCOUNT_BASE = URLs.URL_ACCOUNT_BASE;
    @Deprecated
    public static final String URL_ACOUNT_API_BASE = URLs.URL_ACOUNT_API_BASE;
    @Deprecated
    public static final String URL_ACOUNT_API_BASE_SECURE = URLs.URL_ACOUNT_API_BASE_SECURE;
    @Deprecated
    public static final String URL_ACOUNT_API_BASE_V2_SECURE = URLs.URL_ACOUNT_API_BASE_V2_SECURE;
    @Deprecated
    public static final String URL_ACCOUNT_SAFE_API_BASE = URLs.URL_ACCOUNT_SAFE_API_BASE;
    @Deprecated
    public static final String URL_ACCOUNT_API_V2_BASE = URLs.URL_ACCOUNT_API_V2_BASE;
    @Deprecated
    public static final String URL_ACCOUNT_API_V3_BASE = URLs.URL_ACCOUNT_API_V3_BASE;
    private static final Integer INT_0 = 0;
    static boolean sDisableLoginFallbackForTest = false;
    private static final Integer TIMEOUT_LONG_POLLING = Integer.valueOf((int) AudiobookAdapterWrap.ITEM_TYPE_CONTENT);

    /* loaded from: classes2.dex */
    public enum CheckAvailibilityType {
        EMAIL,
        PHONE
    }

    public static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException {
        try {
            return loginByPassword(str, str2, str3, str4, str5, str6, metaLoginData, false);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    public static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData, boolean z) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException, NeedNotificationException {
        return loginByPassword(str, str2, str3, str4, str5, str6, metaLoginData, z, null);
    }

    public static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData, boolean z, String[] strArr) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException, NeedNotificationException {
        try {
            return loginByPassword(str, str2, str3, str4, str5, str6, metaLoginData, z, strArr, PassportCATokenManager.getInstance(), false);
        } catch (PassportCAException unused) {
            throw new IllegalStateException("this should never happen in product environment.Have you set sDisableLoginFallbackForTest to be true? ");
        }
    }

    public static AccountInfo getStsUrlByPassword(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException, NeedNotificationException {
        try {
            return loginByPassword(str, str3, str4, str2, str5, str6, null, true, strArr, PassportCATokenManager.getInstance(), true);
        } catch (PassportCAException unused) {
            throw new IllegalStateException("this should never happen in product environment.Have you set sDisableLoginFallbackForTest to be true? ");
        }
    }

    @Deprecated
    public static AccountInfo confirmPassword(String str, String str2, String str3, String str4, String str5, String str6) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException, NeedNotificationException {
        try {
            return loginByPassword(str, str3, str4, str2, str5, str6, null, false, null, PassportCATokenManager.getInstance(), true);
        } catch (PassportCAException unused) {
            throw new IllegalStateException("this should never happen in product environment.Have you set sDisableLoginFallbackForTest to be true? ");
        } catch (NeedNotificationException unused2) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData, boolean z, String[] strArr, PassportCATokenManager passportCATokenManager, boolean z2) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException, NeedNotificationException, PassportCAException {
        return loginByPassword(new PasswordLoginParams.Builder().setUserId(str).setPassword(str4).setDeviceId(str3).setCaptCode(str5).setCaptIck(str6).setServiceId(str2).setMetaLoginData(metaLoginData).setNeedProcessNotification(z).setIsReturnStsUrl(z2).setHashedEnvFactors(strArr).build());
    }

    public static AccountInfo loginByPassword(PasswordLoginParams passwordLoginParams) throws InvalidResponseException, InvalidCredentialException, InvalidUserNameException, NeedVerificationException, NeedCaptchaException, IOException, AccessDeniedException, AuthenticationFailureException, NeedNotificationException {
        if (passwordLoginParams == null || passwordLoginParams.password == null) {
            throw new IllegalArgumentException("password params should not be null");
        }
        String str = passwordLoginParams.userId;
        String str2 = passwordLoginParams.password;
        String str3 = passwordLoginParams.deviceId;
        String str4 = TextUtils.isEmpty(passwordLoginParams.serviceId) ? PASSPORT_SID : passwordLoginParams.serviceId;
        String str5 = passwordLoginParams.captIck;
        String str6 = passwordLoginParams.captCode;
        String[] strArr = passwordLoginParams.hashedEnvFactors;
        boolean z = passwordLoginParams.returnStsUrl;
        boolean z2 = passwordLoginParams.needProcessNotification;
        MetaLoginData metaLoginData = passwordLoginParams.metaLoginData;
        ActivatorPhoneInfo activatorPhoneInfo = passwordLoginParams.activatorPhoneInfo;
        EasyMap easyPut = new EasyMap().easyPutOpt("user", str).easyPut("hash", CloudCoder.getMd5DigestUpperCase(str2)).easyPutOpt("sid", str4).easyPutOpt("captCode", str6).easyPut("_json", "true");
        addEnvToParams(easyPut, strArr);
        EasyMap easyPutOpt = new EasyMap().easyPutOpt("ick", str5).easyPutOpt("ticketToken", passwordLoginParams.ticketToken);
        addDeviceIdInCookies(easyPutOpt, str3);
        addAntiSpamIpAddressInCookies(easyPutOpt);
        if (activatorPhoneInfo != null) {
            easyPut.easyPutOpt("userHash", activatorPhoneInfo.phoneHash);
            easyPutOpt.easyPutOpt(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, activatorPhoneInfo.activatorToken);
        }
        PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
        passportRequestArguments.putAllParams(easyPut);
        passportRequestArguments.putAllCookies(easyPutOpt);
        passportRequestArguments.setUrl(URLs.URL_LOGIN_AUTH2_HTTPS);
        passportRequestArguments.setReadBody(true);
        try {
            SimpleRequest.StringContent executeEx = new PassportLoginRequest.ByPassword(passportRequestArguments, str, str4, metaLoginData).executeEx();
            if (executeEx != null) {
                return processLoginContent(executeEx, str4, z2, z);
            }
            throw new IOException("failed to get response from server");
        } catch (PassportCAException unused) {
            throw new IllegalStateException("this should never happen in product environment.Have you set sDisableLoginFallbackForTest to be true? ");
        }
    }

    public static AccountInfo loginByPassToken(String str, String str2, String str3, String str4) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException {
        return loginByPassToken(str, str2, str3, str4, URLs.URL_LOGIN_HTTPS);
    }

    public static AccountInfo getStsUrlByPassToken(String str, String str2, String str3, String str4) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, NeedNotificationException {
        return loginByPassToken(new PassTokenLoginParams.Builder(str, str4, str2).loginRequestUrl(URLs.URL_LOGIN_HTTPS).deviceId(str3).isReturnStsUrl(true).isGetPhoneTicketLoginMetaData(false).build());
    }

    public static AccountInfo loginByPassTokenNE(String str, String str2, String str3, String str4) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, NeedNotificationException {
        return loginByPassTokenNE(str, str2, str3, str4, URLs.URL_LOGIN_HTTPS);
    }

    public static AccountInfo loginByPassToken(String str, String str2, String str3, String str4, String str5) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException {
        try {
            return loginByPassTokenNE(str, str2, str3, str4, str5);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    public static AccountInfo loginByPassTokenNE(String str, String str2, String str3, String str4, String str5) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, NeedNotificationException {
        return loginByPassToken(new PassTokenLoginParams.Builder(str, str4, str2).loginRequestUrl(str5).deviceId(str3).isReturnStsUrl(false).isGetPhoneTicketLoginMetaData(false).build());
    }

    public static AccountInfo loginByPassToken(PassTokenLoginParams passTokenLoginParams) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, NeedNotificationException {
        if (passTokenLoginParams != null) {
            String str = passTokenLoginParams.loginRequestUrl;
            if (TextUtils.isEmpty(str)) {
                str = URLs.URL_LOGIN_HTTPS;
            }
            String str2 = passTokenLoginParams.serviceId;
            String str3 = TextUtils.isEmpty(str2) ? PASSPORT_SID : str2;
            String str4 = passTokenLoginParams.userId;
            String str5 = passTokenLoginParams.passToken;
            String str6 = passTokenLoginParams.deviceId;
            boolean z = passTokenLoginParams.returnStsUrl;
            String queryParameter = Uri.parse(str).getQueryParameter("sid");
            EasyMap easyMap = new EasyMap();
            if (TextUtils.isEmpty(queryParameter)) {
                easyMap.easyPut("sid", str3);
            }
            easyMap.easyPut("_json", "true");
            if (passTokenLoginParams.isGetPhoneTicketLoginMetaData) {
                easyMap.put("_loginSign", "ticket");
            }
            EasyMap easyPutOpt = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str4).easyPutOpt(BaseConstants.EXTRA_PASSTOKEN, str5);
            addDeviceIdInCookies(easyPutOpt, str6);
            addAntiSpamIpAddressInCookies(easyPutOpt);
            PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
            passportRequestArguments.setUrl(str);
            passportRequestArguments.putAllCookies(easyPutOpt);
            passportRequestArguments.putAllParams(easyMap);
            passportRequestArguments.setReadBody(true);
            PassportLoginRequest.ByPassToken byPassToken = new PassportLoginRequest.ByPassToken(passportRequestArguments);
            try {
                SimpleRequest.StringContent executeEx = byPassToken.executeEx();
                if (executeEx != null) {
                    return processLoginContent(str4, executeEx, str3, true, byPassToken.isResultFromCA(), z);
                }
                throw new IOException("failed to get response from service server");
            } catch (PassportCAException unused) {
                throw new IllegalStateException();
            } catch (NeedCaptchaException unused2) {
                throw new InvalidResponseException("Unexpected NeedCaptchaException");
            } catch (NeedVerificationException unused3) {
                throw new InvalidResponseException("Unexpected NeedVerificationException");
            }
        } else {
            throw new IllegalArgumentException("passToken login params can not be empty");
        }
    }

    public static AccountInfo loginByStep2(String str, String str2, String str3, String str4, MetaLoginData metaLoginData, boolean z, String str5) throws NeedVerificationException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException {
        return loginByStep2(str, str2, str3, str4, metaLoginData, z, str5, false);
    }

    public static AccountInfo getStsUrlByStep2(String str, String str2, String str3, String str4, MetaLoginData metaLoginData, boolean z, String str5) throws NeedVerificationException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException {
        return loginByStep2(str, str2, str3, str4, metaLoginData, z, str5, true);
    }

    private static AccountInfo loginByStep2(String str, String str2, String str3, String str4, MetaLoginData metaLoginData, boolean z, String str5, boolean z2) throws NeedVerificationException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException {
        return loginByStep2(new Step2LoginParams.Builder().setUserId(str).setServiceId(str2).setDeviceId(str3).setStep2code(str4).setMetaLoginData(metaLoginData).setTrust(z).setStep1Token(str5).setReturnStsUrl(z2).build());
    }

    public static AccountInfo loginByStep2(Step2LoginParams step2LoginParams) throws NeedVerificationException, IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException {
        if (step2LoginParams != null) {
            String str = step2LoginParams.userId;
            String str2 = step2LoginParams.step2code;
            MetaLoginData metaLoginData = step2LoginParams.metaLoginData;
            String str3 = TextUtils.isEmpty(step2LoginParams.serviceId) ? PASSPORT_SID : step2LoginParams.serviceId;
            boolean z = step2LoginParams.trust;
            boolean z2 = step2LoginParams.returnStsUrl;
            String str4 = step2LoginParams.deviceId;
            String str5 = step2LoginParams.step1Token;
            if (str == null || str2 == null || metaLoginData == null) {
                throw new NullPointerException("invalid params");
            }
            EasyMap easyPut = new EasyMap().easyPut("user", str).easyPut("code", str2).easyPut("_sign", metaLoginData.sign).easyPut("qs", metaLoginData.qs).easyPut("callback", metaLoginData.callback).easyPut("trust", z ? "true" : "false").easyPutOpt("sid", str3).easyPut("_json", "true");
            EasyMap easyPut2 = new EasyMap().easyPut("step1Token", str5);
            addDeviceIdInCookies(easyPut2, str4);
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(URLs.URL_LOGIN_AUTH_STEP2, easyPut, easyPut2, true);
            if (postAsString != null) {
                try {
                    return processLoginContent(postAsString, str3, false, z2);
                } catch (InvalidCredentialException unused) {
                    throw new InvalidResponseException("Unexpected InvalidCredentialException");
                } catch (InvalidUserNameException unused2) {
                    throw new InvalidResponseException("Unexpected InvalidUserNameException");
                } catch (NeedCaptchaException unused3) {
                    throw new InvalidResponseException("Unexpected NeedCaptchaException");
                } catch (NeedNotificationException unused4) {
                    throw new InvalidResponseException("Unexpected NeedNotificationException");
                }
            } else {
                throw new IOException("failed to get response from service server");
            }
        } else {
            throw new IllegalArgumentException("step2 params is null");
        }
    }

    public static Pair<Bitmap, String> getCaptchaImage(String str) {
        return getCaptchaImageAndIck(ACCOUNT_DOMAIN + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0023 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0024 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.util.Pair<android.graphics.Bitmap, java.lang.String> getCaptchaImageAndIck(java.lang.String r3) {
        /*
            r0 = 0
            com.xiaomi.accountsdk.request.SimpleRequest$StreamContent r3 = com.xiaomi.accountsdk.request.SimpleRequestForAccount.getAsStream(r3, r0, r0)     // Catch: IOException -> 0x0018, AccessDeniedException -> 0x000f, AuthenticationFailureException -> 0x0006
            goto L_0x0021
        L_0x0006:
            r3 = move-exception
            java.lang.String r1 = "XMPassport"
            java.lang.String r2 = "getCaptchaImageAndIck"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r3)
            goto L_0x0020
        L_0x000f:
            r3 = move-exception
            java.lang.String r1 = "XMPassport"
            java.lang.String r2 = "getCaptchaImageAndIck"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r3)
            goto L_0x0020
        L_0x0018:
            r3 = move-exception
            java.lang.String r1 = "XMPassport"
            java.lang.String r2 = "getCaptchaImageAndIck"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r3)
        L_0x0020:
            r3 = r0
        L_0x0021:
            if (r3 != 0) goto L_0x0024
            return r0
        L_0x0024:
            java.io.InputStream r0 = r3.getStream()     // Catch: all -> 0x003a
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r0)     // Catch: all -> 0x003a
            java.lang.String r1 = "ick"
            java.lang.String r1 = r3.getHeader(r1)     // Catch: all -> 0x003a
            android.util.Pair r0 = android.util.Pair.create(r0, r1)     // Catch: all -> 0x003a
            r3.closeStream()
            return r0
        L_0x003a:
            r0 = move-exception
            r3.closeStream()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.accountsdk.account.XMPassport.getCaptchaImageAndIck(java.lang.String):android.util.Pair");
    }

    protected static String getClientSign(Long l, String str) {
        TreeMap treeMap = new TreeMap();
        treeMap.put("nonce", String.valueOf(l));
        return CloudCoder.generateSignature(null, null, treeMap, str);
    }

    public static MetaLoginData getMetaLoginData(String str, String str2) throws IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException {
        try {
            loginByPassToken(str, str2, null, null);
            throw new InvalidResponseException("Unexpected login success with empty pass token");
        } catch (InvalidCredentialException e) {
            return e.getMetaLoginData();
        }
    }

    private static MetaLoginData getMetaLoginData(String str) throws IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException {
        try {
            loginByPassToken(null, str, null, null);
            throw new InvalidResponseException("Unexpected login success with empty pass token");
        } catch (InvalidCredentialException e) {
            return e.getMetaLoginData();
        } catch (InvalidUserNameException unused) {
            throw new InvalidResponseException("should not be throw this exception");
        }
    }

    private static AccountInfo processLoginContent(SimpleRequest.StringContent stringContent, String str, boolean z, boolean z2) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, NeedVerificationException, NeedCaptchaException, InvalidUserNameException, NeedNotificationException, AuthenticationFailureException {
        return processLoginContent(stringContent, str, z, false, z2);
    }

    private static AccountInfo processLoginContent(SimpleRequest.StringContent stringContent, String str, boolean z, boolean z2, boolean z3) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, NeedVerificationException, NeedCaptchaException, InvalidUserNameException, NeedNotificationException, AuthenticationFailureException {
        return processLoginContent(null, stringContent, str, z, z2, z3);
    }

    private static AccountInfo processLoginContent(String str, SimpleRequest.StringContent stringContent, String str2, boolean z, boolean z2, boolean z3) throws InvalidResponseException, InvalidCredentialException, IOException, AccessDeniedException, NeedVerificationException, NeedCaptchaException, InvalidUserNameException, NeedNotificationException, AuthenticationFailureException {
        String str3;
        String str4;
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
            int i = jSONObject.getInt("code");
            String string = jSONObject.getString(IChannel.EXTRA_ERROR_DESC);
            ServerError serverError = new ServerError(jSONObject);
            AccountLog.i(TAG, "processLoginContent, code: " + i + ", desc: " + string);
            if (i == 0) {
                if (z2) {
                    str4 = jSONObject.optString(BaseConstants.EXTRA_USER_ID);
                    str3 = jSONObject.optString(BaseConstants.EXTRA_PASSTOKEN);
                } else {
                    str4 = stringContent.getHeader(BaseConstants.EXTRA_USER_ID);
                    str3 = stringContent.getHeader(BaseConstants.EXTRA_PASSTOKEN);
                }
                boolean z4 = (!z || str == null || !jSONObject.optBoolean("disableHotfixMiui73508", false)) ? z : false;
                int optInt = jSONObject.optInt("securityStatus", 0);
                AccountLog.i(TAG, "securityStatus: " + optInt);
                if (z4 && optInt != 0) {
                    String string2 = jSONObject.getString("notificationUrl");
                    if (string2 == null) {
                        throw new InvalidResponseException("noticationUrl is null");
                    } else if (string2.startsWith("http")) {
                        throw new NeedNotificationException(str4, string2, stringContent);
                    } else {
                        throw new NeedNotificationException(str4, ACCOUNT_DOMAIN + string2, stringContent);
                    }
                } else if (TextUtils.isEmpty(str4)) {
                    throw new InvalidResponseException("no user Id");
                } else if (!TextUtils.isEmpty(str3)) {
                    return parseLoginResult(str4, stringContent, str2, null, z2, z3);
                } else {
                    throw new InvalidResponseException("no passToken in login response");
                }
            } else if (i == RESULT_CODE_USERNAME) {
                throw new InvalidUserNameException();
            } else if (i == 70002) {
                throw new InvalidCredentialException(i, string, false);
            } else if (i == 70016) {
                String string3 = jSONObject.getString("_sign");
                String string4 = jSONObject.getString("qs");
                String string5 = jSONObject.getString("callback");
                String string6 = jSONObject.getString("captchaUrl");
                if (TextUtils.equals("null", string6)) {
                    string6 = null;
                }
                throw new InvalidCredentialException(i, string, true).metaLoginData(new MetaLoginData(string3, string4, string5)).captchaUrl(string6);
            } else if (i == RESULT_CODE_VERIFICATION) {
                throw new NeedVerificationException(new MetaLoginData(jSONObject.getString("_sign"), jSONObject.getString("qs"), jSONObject.getString("callback")), stringContent.getHeader("step1Token"), jSONObject.optString(BaseConstants.EXTRA_USER_ID));
            } else if (i != 87001) {
                throw new InvalidResponseException(serverError);
            } else {
                throw new NeedCaptchaException(i, string, jSONObject.getString("captchaUrl"));
            }
        } catch (JSONException unused) {
            AccountLog.e(TAG, "processLoginContent: " + stringContent);
            throw new InvalidResponseException("processLoginContent JSONException");
        }
    }

    private static AccountInfo parseLoginResult(String str, SimpleRequest.StringContent stringContent, String str2, String str3, boolean z, boolean z2) throws InvalidResponseException, IOException, AccessDeniedException, AuthenticationFailureException {
        String str4;
        String str5;
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
            if (z) {
                str4 = jSONObject.optString(BaseConstants.EXTRA_PASSTOKEN);
                str5 = jSONObject.optString("cUserId");
            } else {
                str4 = stringContent.getHeader(BaseConstants.EXTRA_PASSTOKEN);
                str5 = stringContent.getHeader("cUserId");
            }
            String optString = jSONObject.optString("ssecurity");
            Long valueOf = Long.valueOf(jSONObject.optLong("nonce"));
            String optString2 = jSONObject.optString("psecurity");
            if (optString == null || valueOf == null || optString2 == null) {
                try {
                    String header = stringContent.getHeader("extension-pragma");
                    if (TextUtils.isEmpty(header)) {
                        header = stringContent.getHeader("Extension-Pragma");
                        if (TextUtils.isEmpty(header)) {
                            throw new InvalidResponseException("empty extension-pragma");
                        }
                    }
                    JSONObject jSONObject2 = new JSONObject(header);
                    optString = jSONObject2.optString("ssecurity");
                    valueOf = Long.valueOf(jSONObject2.optLong("nonce"));
                    optString2 = jSONObject2.optString("psecurity");
                } catch (JSONException unused) {
                }
            }
            if (optString == null || valueOf == null || optString2 == null) {
                throw new InvalidResponseException("security, nonce or psecurity is null");
            }
            String header2 = stringContent.getHeader("re-pass-token");
            boolean z3 = true;
            if (jSONObject.optInt("pwd") != 1) {
                z3 = false;
            }
            String string = jSONObject.getString("location");
            AccountInfo.Builder psecurity = new AccountInfo.Builder().userId(str).encryptedUserId(str5).serviceId(str2).passToken(str4).psecurity(optString2);
            if (str3 == null) {
                str3 = string;
            }
            AccountInfo build = psecurity.autoLoginUrl(str3).rePassToken(header2).hasPwd(z3).security(optString).build();
            if (TextUtils.isEmpty(str2) || PASSPORT_SID.equals(str2) || z2) {
                return build;
            }
            try {
                try {
                    return getServiceTokenByStsUrl(build, valueOf);
                } catch (AccessDeniedException e) {
                    AccountLog.e(TAG, "sts url request error", e);
                    e.stsUrlRequestError(str2);
                    throw e;
                } catch (InvalidResponseException e2) {
                    AccountLog.e(TAG, "sts url request error", e2);
                    e2.stsUrlRequestError(str2);
                    throw e2;
                }
            } catch (AuthenticationFailureException e3) {
                AccountLog.e(TAG, "sts url request error", e3);
                e3.stsUrlRequestError(str2);
                throw e3;
            } catch (IOException e4) {
                AccountLog.e(TAG, "sts url request error", e4);
                PassportIOException passportIOException = new PassportIOException(e4);
                passportIOException.stsUrlRequestError(str2);
                throw passportIOException;
            }
        } catch (JSONException unused2) {
            AccountLog.e(TAG, "parseLoginResult: " + stringContent);
            throw new InvalidResponseException("parseLoginResult JSONException");
        }
    }

    private static AccountInfo getServiceTokenByStsUrl(AccountInfo accountInfo, Long l) throws IOException, AccessDeniedException, InvalidResponseException, AuthenticationFailureException {
        String str = accountInfo.serviceId;
        AccountLog.i(TAG, "start sts request: " + str);
        String clientSign = getClientSign(l, accountInfo.security);
        if (clientSign != null) {
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(accountInfo.getAutoLoginUrl(), new EasyMap().easyPut("clientSign", clientSign).easyPut("_userIdNeedEncrypt", "true"), null, false);
            if (asString != null) {
                String header = asString.getHeader(String.format("%s_serviceToken", str));
                if (TextUtils.isEmpty(header)) {
                    header = asString.getHeader(AuthorizeActivityBase.KEY_SERVICETOKEN);
                    if (TextUtils.isEmpty(header)) {
                        throw new InvalidResponseException(0, "no service token contained in callback cookies: " + str);
                    }
                }
                String header2 = asString.getHeader(str + LoginManager.KEY_SLH_SUFFIX);
                return new AccountInfo.Builder().userId(accountInfo.userId).serviceId(str).passToken(accountInfo.passToken).encryptedUserId(accountInfo.encryptedUserId).serviceToken(header).security(accountInfo.security).psecurity(accountInfo.psecurity).slh(header2).ph(asString.getHeader(str + LoginManager.KEY_PH_SUFFIX)).rePassToken(accountInfo.rePassToken).hasPwd(accountInfo.hasPwd).build();
            }
            throw new InvalidResponseException(0, "no response when get service token");
        }
        AccountLog.e(TAG, "failed to get client sign");
        throw new InvalidResponseException(0, "sign parameters failure");
    }

    @Deprecated
    public static String regByPhone(String str, String str2, String str3) throws IOException, InvalidResponseException {
        Object obj;
        EasyMap easyPut = new EasyMap().easyPut("phone", str).easyPut("password", str2).easyPut("ticket", str3);
        EasyMap easyMap = new EasyMap();
        SimpleRequest.MapContent mapContent = null;
        addDeviceIdInCookies(easyMap, null);
        try {
            mapContent = SimpleRequestForAccount.postAsMap(URLs.URL_REG_PHONE, easyPut, easyMap, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (mapContent != null) {
            Object fromBody = mapContent.getFromBody("code");
            if (INT_0.equals(fromBody)) {
                Object fromBody2 = mapContent.getFromBody("data");
                if ((fromBody2 instanceof Map) && (obj = ((Map) fromBody2).get(BaseConstants.EXTRA_USER_ID)) != null) {
                    return obj.toString();
                }
            }
            if (USE_PREVIEW) {
                AccountLog.w(TAG, String.format("register failed, code: %s, description: %s", fromBody, mapContent.getFromBody("description")));
            }
            throw new InvalidResponseException("failed to register due to invalid response from server");
        }
        throw new IOException("failed to register, no response");
    }

    @Deprecated
    public static String regByEmail(String str, String str2, String str3, String str4) throws IOException, InvalidResponseException, NeedCaptchaException {
        try {
            return regByEmail(new EmailRegisterParams.Builder().email(str).password(str2).captchaCode(str3, str4).build());
        } catch (UsedEmailAddressException e) {
            AccountLog.w(TAG, "email used");
            throw new InvalidResponseException(e.getMessage());
        }
    }

    public static String regByEmail(EmailRegisterParams emailRegisterParams) throws IOException, InvalidResponseException, NeedCaptchaException, UsedEmailAddressException {
        if (emailRegisterParams != null) {
            String str = emailRegisterParams.emailAddress;
            String str2 = emailRegisterParams.password;
            String str3 = emailRegisterParams.captCode;
            String str4 = emailRegisterParams.captIck;
            String str5 = emailRegisterParams.region;
            EasyMap easyPut = new EasyMap().easyPut(NotificationCompat.CATEGORY_EMAIL, str).easyPut("password", str2).easyPut("_json", "true").easyPutOpt("inputcode", str3).easyPutOpt("region", str5).easyPutOpt("sid", emailRegisterParams.serviceId).easyPut("acceptLicense", "true");
            EasyMap easyPutOpt = new EasyMap().easyPutOpt("ick", str4);
            SimpleRequest.StringContent stringContent = null;
            addDeviceIdInCookies(easyPutOpt, null);
            addAntiSpamIpAddressInCookies(easyPutOpt);
            try {
                stringContent = SimpleRequestForAccount.postAsString(replaceUrlHost(URLs.URL_EMAIL_REGISTER, str5), easyPut, easyPutOpt, true);
            } catch (AccessDeniedException e) {
                e.printStackTrace();
            } catch (AuthenticationFailureException e2) {
                e2.printStackTrace();
            }
            if (stringContent != null) {
                try {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
                    int i = jSONObject.getInt("code");
                    ServerError serverError = new ServerError(jSONObject);
                    String optString = jSONObject.optString("description");
                    if (i == 0) {
                        return jSONObject.getString(BaseConstants.EXTRA_USER_ID);
                    }
                    if (i == RESULT_CODE_EXTERNAL_ADDRESS_USED) {
                        throw new UsedEmailAddressException(optString);
                    } else if (i != 87001) {
                        throw new InvalidResponseException(serverError);
                    } else {
                        throw new NeedCaptchaException(i, optString, URLs.URL_REG_GET_CAPTCHA_CODE);
                    }
                } catch (JSONException e3) {
                    AccountLog.w(TAG, "json error", e3);
                    throw new InvalidResponseException("json error");
                }
            } else {
                throw new IOException("failed to register, no response");
            }
        } else {
            throw new IllegalArgumentException("email params should not be null");
        }
    }

    private static String replaceUrlHost(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return str;
        }
        Application applicationContext = XMPassportSettings.getApplicationContext();
        String blockingGetRegHostName = applicationContext == null ? null : new RegionConfig(applicationContext).blockingGetRegHostName(str2);
        return TextUtils.isEmpty(blockingGetRegHostName) ? str : str.replaceFirst(URLs.HOST_URL_ACCOUNT_BASE, blockingGetRegHostName);
    }

    public static void getRegisterVerifyCode(String str, String str2, String str3) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException, RegisteredPhoneException, NeedCaptchaException {
        EasyMap easyPut = new EasyMap().easyPut("phone", str);
        if (str2 != null) {
            easyPut.easyPut("icode", str2);
        }
        EasyMap easyMap = null;
        if (str3 != null) {
            easyMap = new EasyMap().easyPutOpt("ick", str3);
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(URLs.URL_REG_GET_VERIFY_CODE, easyPut, easyMap, true)));
            int i = jSONObject.getInt("code");
            if (i == 0) {
                return;
            }
            if (i == 20031) {
                throw new NeedCaptchaException(i, "", jSONObject.getString("info"));
            } else if (i != RESULT_CODE_REGISTERED_PHONE) {
                throw new InvalidResponseException("process result is failed");
            } else {
                throw new RegisteredPhoneException("phone is registered");
            }
        } catch (JSONException e) {
            AccountLog.e(TAG, "getRegisterVerifyCode ", e);
            throw new InvalidResponseException("process result is failed");
        }
    }

    public static void checkRegisterVerifyCode(String str, String str2) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException {
        try {
            if (new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(URLs.URL_REG_CHECK_VERIFY_CODE, new EasyMap().easyPut("phone", str).easyPut("ticket", str2), null, true))).getInt("code") != 0) {
                throw new InvalidResponseException("invalid response, failed to check register verify code");
            }
        } catch (JSONException unused) {
            throw new InvalidResponseException("invalid response, fail to convert to JSON");
        }
    }

    public static boolean checkEmailAvailability(String str) throws IOException, InvalidResponseException {
        String userIdForAddress = getUserIdForAddress(str, CheckAvailibilityType.EMAIL);
        if ("1".equals(userIdForAddress)) {
            return false;
        }
        if ("-1".equals(userIdForAddress)) {
            return true;
        }
        throw new InvalidResponseException(String.format("url %s should only return 1 or -1 as user id, but actually return %s", URLs.URL_USER_EXISTS, userIdForAddress));
    }

    public static boolean checkPhoneAvailability(Context context, String str, String str2) throws IOException, InvalidResponseException {
        String userIdForAddress = getUserIdForAddress(str, CheckAvailibilityType.PHONE);
        if ("1".equals(userIdForAddress)) {
            return false;
        }
        if ("-1".equals(userIdForAddress)) {
            return true;
        }
        throw new InvalidResponseException(String.format("url %s should only return 1 or -1 as user id, but actually return %s", URLs.URL_USER_EXISTS, userIdForAddress));
    }

    private static String getUserIdForAddress(String str, CheckAvailibilityType checkAvailibilityType) throws IOException, InvalidResponseException {
        Object obj;
        SimpleRequest.MapContent mapContent = null;
        try {
            mapContent = SimpleRequestForAccount.getAsMap(URLs.URL_USER_EXISTS, new EasyMap().easyPut("type", checkAvailibilityType == CheckAvailibilityType.EMAIL ? "EM" : "PH").easyPut("externalId", str), null, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (mapContent != null) {
            if (INT_0.equals(mapContent.getFromBody("code"))) {
                Object fromBody = mapContent.getFromBody("data");
                if ((fromBody instanceof Map) && (obj = ((Map) fromBody).get(BaseConstants.EXTRA_USER_ID)) != null) {
                    return obj.toString();
                }
            }
            throw new InvalidResponseException(String.format("server error when getting user id, reason:%s, description:%s, code:%s", mapContent.getFromBody(IChannel.EXTRA_CLOSE_REASON), mapContent.getFromBody("description"), mapContent.getFromBody("code")));
        }
        throw new IOException("failed to get response when getting user id");
    }

    @Deprecated
    public static void sendActivateEmail(String str, String str2) throws IOException, InvalidResponseException {
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut("addressType", "EM").easyPut("address", str2);
        SimpleRequest.MapContent mapContent = null;
        try {
            mapContent = SimpleRequestForAccount.getAsMap(URLs.URL_RESEND_EMAIL, easyPut, null, true);
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

    @Deprecated
    public static void sendPhoneRegTicket(String str, String str2, String str3, String str4) throws NeedCaptchaException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, IOException, SendVerifyCodeExceedLimitException {
        sendPhoneRegTicket(new SendPhoneTicketParams.Builder().phone(str).deviceId(str2).captchaCode(str3, str4).build());
    }

    public static void sendPhoneRegTicket(SendPhoneTicketParams sendPhoneTicketParams) throws NeedCaptchaException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, IOException, SendVerifyCodeExceedLimitException {
        if (sendPhoneTicketParams != null) {
            String str = sendPhoneTicketParams.phone;
            String str2 = sendPhoneTicketParams.deviceId;
            String str3 = sendPhoneTicketParams.region;
            String str4 = sendPhoneTicketParams.captCode;
            String str5 = sendPhoneTicketParams.captIck;
            EasyMap easyPutOpt = new EasyMap().easyPut("phone", str).easyPutOpt("icode", str4).easyPutOpt("region", str3);
            easyPutOpt.putAll(XMPassportUtil.getDefaultLocaleParam());
            EasyMap easyMap = new EasyMap();
            easyMap.easyPutOpt("ick", str5);
            addDeviceIdInCookies(easyMap, str2);
            try {
                JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(replaceUrlHost(URLs.URL_REG_SEND_PHONE_TICKET, str3), easyPutOpt, easyMap, true)));
                int i = jSONObject.getInt("code");
                String optString = jSONObject.optString("description");
                ServerError serverError = new ServerError(jSONObject);
                if (i != 0) {
                    if (i != 20031) {
                        if (i == 70022) {
                            throw new SendVerifyCodeExceedLimitException(optString);
                        } else if (i != 87001) {
                            throw new InvalidResponseException(serverError);
                        }
                    }
                    throw new NeedCaptchaException(i, optString, jSONObject.getString("info"));
                }
            } catch (JSONException e) {
                throw new InvalidResponseException("JSON error", e);
            }
        } else {
            throw new IllegalArgumentException("send phone reg ticket params should not be null");
        }
    }

    @Deprecated
    public static RegisterUserInfo checkRegisterPhone(String str, String str2, String str3, String str4) throws IOException, AccessDeniedException, InvalidPhoneOrTicketException, AuthenticationFailureException, InvalidResponseException, UserRestrictedException {
        return checkRegisterPhone(new CheckRegPhoneParams.Builder().phoneTicket(str, str2).activatedPhone(str3, null, null).deviceId(str4).build());
    }

    @Deprecated
    public static RegisterUserInfo checkRegisterPhone(String str, String str2, String str3, String str4, String str5, String str6) throws IOException, AccessDeniedException, InvalidPhoneOrTicketException, AuthenticationFailureException, InvalidResponseException, UserRestrictedException {
        return checkRegisterPhone(new CheckRegPhoneParams.Builder().phoneTicket(str, str2).activatedPhone(str3, str5, str6).deviceId(str4).build());
    }

    public static RegisterUserInfo checkRegisterPhone(CheckRegPhoneParams checkRegPhoneParams) throws IOException, AccessDeniedException, InvalidPhoneOrTicketException, AuthenticationFailureException, InvalidResponseException, UserRestrictedException {
        if (checkRegPhoneParams != null) {
            String str = checkRegPhoneParams.phone;
            String str2 = checkRegPhoneParams.ticket;
            String str3 = checkRegPhoneParams.hashedSimId;
            String str4 = checkRegPhoneParams.vKey2;
            String str5 = checkRegPhoneParams.vKey2Nonce;
            String str6 = checkRegPhoneParams.deviceId;
            String str7 = checkRegPhoneParams.region;
            EasyMap easyPutOpt = new EasyMap().easyPut("phone", str).easyPutOpt("ticket", str2).easyPutOpt("simId", str3).easyPutOpt("vkey2", str4).easyPutOpt("nonce", str5).easyPutOpt("region", str7);
            addEnvToParams(easyPutOpt, PassportUserEnvironment.Holder.getInstance().getEnvInfoArray(XMPassportSettings.getApplicationContext()));
            EasyMap easyMap = new EasyMap();
            addDeviceIdInCookies(easyMap, str6);
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(replaceUrlHost(URLs.URL_REG_VERIFY_PHONE, str7), easyPutOpt, easyMap, true);
            try {
                JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(postAsString));
                ServerError serverError = new ServerError(jSONObject);
                int i = jSONObject.getInt("code");
                String str8 = "code: " + i + ", desc: " + jSONObject.optString("description");
                if (i == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    String header = postAsString.getHeader("ticketToken");
                    if (header != null) {
                        return new RegisterUserInfo.Builder(jSONObject2.getInt("status")).phone(str).userId(jSONObject2.optString(BaseConstants.EXTRA_USER_ID, null)).userName(jSONObject2.optString("userName", null)).avatarAddress(jSONObject2.optString("portraitUrl", null)).bindTime(jSONObject2.optLong("bindTime", 0L)).needGetActiveTime(jSONObject2.optBoolean("needGetActiveTime", false)).needToast(jSONObject2.optBoolean("needToast", false)).ticketToken(header).build();
                    }
                    throw new InvalidResponseException("fail to get ticketToken");
                } else if (i == 10017) {
                    throw new InvalidPhoneOrTicketException(str8);
                } else if (i == 20023) {
                    throw new UserRestrictedException(str8);
                } else {
                    throw new InvalidResponseException(serverError);
                }
            } catch (JSONException e) {
                throw new InvalidResponseException("process result is failed", e);
            }
        } else {
            throw new IllegalArgumentException("check reg phone params can not be null");
        }
    }

    private static void addEnvToParams(EasyMap<String, String> easyMap, String[] strArr) {
        if (strArr != null && easyMap != null) {
            try {
                PassportEnvEncryptUtils.EncryptResult encrypt = PassportEnvEncryptUtils.encrypt(strArr);
                easyMap.easyPutOpt(SchemaActivity.KEY_ENV, encrypt.content);
                easyMap.easyPutOpt("envKey", encrypt.encryptedKey);
            } catch (PassportEnvEncryptUtils.EncryptException e) {
                AccountLog.w(TAG, e);
            }
        }
    }

    @Deprecated
    public static String regByPhoneWithToken(String str, String str2, String str3, String str4) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException, InvalidParameterException, TokenExpiredException {
        try {
            return regByPhoneWithToken(new PhoneTokenRegisterParams.Builder().phoneTicketToken(str, str3).password(str2).region(str4).build()).getUserId();
        } catch (ReachLimitException e) {
            throw new InvalidResponseException(e.getMessage());
        } catch (UserRestrictedException e2) {
            throw new InvalidResponseException(e2.getMessage());
        }
    }

    public static AccountInfo regByPhoneWithToken(PhoneTokenRegisterParams phoneTokenRegisterParams) throws IOException, AccessDeniedException, AuthenticationFailureException, InvalidResponseException, InvalidParameterException, TokenExpiredException, UserRestrictedException, ReachLimitException {
        if (phoneTokenRegisterParams != null) {
            String str = phoneTokenRegisterParams.phone;
            String str2 = phoneTokenRegisterParams.phoneHash;
            String str3 = phoneTokenRegisterParams.password;
            String str4 = phoneTokenRegisterParams.region;
            String str5 = phoneTokenRegisterParams.ticketToken;
            String str6 = phoneTokenRegisterParams.activatorToken;
            EasyMap easyPut = new EasyMap().easyPutOpt("phone", str).easyPutOpt("phoneHash", str2).easyPutOpt("password", str3).easyPut("noPwd", String.valueOf(phoneTokenRegisterParams.noPwd)).easyPut("_locale", XMPassportUtil.getISOLocaleString(Locale.getDefault())).easyPutOpt("region", str4).easyPutOpt("sid", phoneTokenRegisterParams.serviceId).easyPut("_json", "true").easyPut("acceptLicense", "true");
            EasyMap easyPutOpt = new EasyMap().easyPutOpt(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, str6).easyPutOpt("ticketToken", str5);
            addDeviceIdInCookies(easyPutOpt, null);
            addAntiSpamIpAddressInCookies(easyPutOpt);
            String replaceUrlHost = replaceUrlHost(URLs.URL_REG_TOKEN, str4);
            boolean z = true;
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(replaceUrlHost, easyPut, easyPutOpt, true);
            try {
                JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(postAsString));
                int i = jSONObject.getInt("code");
                String optString = jSONObject.optString("description");
                String str7 = "code: " + i + ", desc: " + optString;
                if (i == 0) {
                    String header = postAsString.getHeader(BaseConstants.EXTRA_USER_ID);
                    String header2 = postAsString.getHeader("cUserId");
                    String header3 = postAsString.getHeader(BaseConstants.EXTRA_PASSTOKEN);
                    String optString2 = jSONObject.optString("user_synced_url");
                    AccountInfo.Builder passToken = new AccountInfo.Builder().userId(header).encryptedUserId(header2).passToken(header3);
                    if (TextUtils.isEmpty(str3)) {
                        z = false;
                    }
                    return passToken.hasPwd(z).userSyncedUrl(optString2).build();
                } else if (i == 10017) {
                    throw new InvalidParameterException(i, optString);
                } else if (i == 21317) {
                    throw new TokenExpiredException(str7);
                } else if (i == 20023) {
                    throw new UserRestrictedException(str7);
                } else if (i == RESULT_CODE_USER_CREATION_OVER_LIMIT) {
                    throw new ReachLimitException(str7);
                } else {
                    throw new InvalidResponseException(str7);
                }
            } catch (JSONException e) {
                throw new InvalidResponseException("process result is failed", e);
            }
        } else {
            throw new IllegalArgumentException("phone can not be empty");
        }
    }

    public static void resetPassword(String str, String str2, String str3, String str4, String str5) throws InvalidResponseException, AccessDeniedException, AuthenticationFailureException, IOException, InvalidParameterException {
        try {
            String removeSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(URLs.URL_RESET_PASSWORD, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut("password", str2).easyPut("_json", "true").easyPutOpt("passportsecurity_ph", str4), new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPutOpt(AuthorizeActivityBase.KEY_SERVICETOKEN, str3).easyPutOpt("passportsecurity_ph", str4).easyPutOpt("passportsecurity_slh", str5), true));
            long j = new JSONObject(removeSafePrefixAndGetRealBody).getLong("result");
            if (j != 0) {
                if (!(j == RESULT_CODE_ERROR_INVALID_PWD || j == RESULT_CODE_ERROR_PWD_SAME_AS_EMAIL)) {
                    throw new InvalidResponseException("reset password fail: " + removeSafePrefixAndGetRealBody);
                }
                throw new InvalidParameterException("invalid password");
            }
        } catch (JSONException e) {
            throw new InvalidResponseException("process result is failed", e);
        }
    }

    @Deprecated
    public static XiaomiUserInfo getXiaomiUserInfo(String str, String str2, String str3) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        return getXiaomiUserInfo(new PassportInfo(str, null, null, str2, str3));
    }

    @Deprecated
    public static XiaomiUserInfo getXiaomiUserInfo(String str, String str2, String str3, String str4) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        return getXiaomiUserInfo(new PassportInfo(str, str2, null, str3, str4));
    }

    public static XiaomiUserInfo getXiaomiUserInfo(PassportInfo passportInfo) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(XiaomiUserCoreInfo.Flag.BASE_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.BIND_ADDRESS);
        return new XiaomiUserInfo(passportInfo.getUserId(), getXiaomiUserCoreInfo(passportInfo, null, arrayList));
    }

    @Deprecated
    public static XiaomiUserProfile getXiaomiUserProfile(String str, String str2, String str3, String str4, String str5) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        return getXiaomiUserProfile(new PassportInfo(str, str2, str3, str4, str5));
    }

    public static XiaomiUserProfile getXiaomiUserProfile(PassportInfo passportInfo) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(XiaomiUserCoreInfo.Flag.BASE_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.EXTRA_INFO);
        XiaomiUserCoreInfo xiaomiUserCoreInfo = getXiaomiUserCoreInfo(passportInfo, null, arrayList);
        if (xiaomiUserCoreInfo == null) {
            return null;
        }
        XiaomiUserProfile xiaomiUserProfile = new XiaomiUserProfile(passportInfo.getUserId());
        xiaomiUserProfile.setUserName(xiaomiUserCoreInfo.userName);
        xiaomiUserProfile.setBirthday(xiaomiUserCoreInfo.birthday);
        xiaomiUserProfile.setGender(xiaomiUserCoreInfo.gender);
        return xiaomiUserProfile;
    }

    @Deprecated
    public static MiCloudAuthInfo getOAuthInfo(Context context, String str, String str2, String str3, String str4, Bundle bundle, String str5, String str6) throws NeedOAuthorizeException, IOException, AuthenticationFailureException, AccessDeniedException {
        String string = bundle.getString("extra_scope");
        if (string == null || !string.equals("3")) {
            throw new NeedOAuthorizeException();
        }
        try {
            return tryGetOAuthInfoQuietly(new OAuthParameter.Builder().userId(str).clientId(str2).redirectUri(str3).deviceId(str4).scope("3").serviceToken(str5).responseType("token").build());
        } catch (InvalidResponseException unused) {
            throw new NeedOAuthorizeException();
        }
    }

    public static MiCloudAuthInfo tryGetOAuthInfoQuietly(OAuthParameter oAuthParameter) throws IOException, InvalidResponseException, AccessDeniedException, NeedOAuthorizeException, AuthenticationFailureException {
        EasyMap easyPutOpt = new EasyMap().easyPutOpt(AuthorizeActivityBase.KEY_SERVICETOKEN, oAuthParameter.serviceToken);
        if (!oAuthParameter.useCUserId || TextUtils.isEmpty(oAuthParameter.cUserId)) {
            easyPutOpt.easyPutOpt(BaseConstants.EXTRA_USER_ID, oAuthParameter.userId);
        } else {
            easyPutOpt.easyPutOpt("cUserId", oAuthParameter.cUserId);
        }
        easyPutOpt.easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, oAuthParameter.deviceId);
        easyPutOpt.easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID, UserSpaceIdUtil.getNullableUserSpaceIdCookie());
        if (TextUtils.isEmpty(oAuthParameter.responseType)) {
            oAuthParameter.responseType = "token";
        }
        EasyMap easyPut = new EasyMap().easyPut("client_id", oAuthParameter.clientId).easyPut(AuthorizeActivityBase.KEY_REDIRECT_URI, oAuthParameter.redirectUri).easyPut("response_type", oAuthParameter.responseType).easyPut("scope", oAuthParameter.scope).easyPut("skip_confirm", "true").easyPut(XiaomiOAuthConstants.EXTRA_STATE_2, oAuthParameter.state).easyPut("_json", "true");
        if (!TextUtils.isEmpty(oAuthParameter.aUthorizedDeviceId) && !TextUtils.isEmpty(oAuthParameter.aUthorizedDeviceId.trim())) {
            easyPut.easyPutOpt("device_id", oAuthParameter.aUthorizedDeviceId);
        }
        easyPut.easyPutOpt("pt", oAuthParameter.platform);
        return parseOAuthInfoResult(oAuthParameter.responseType, SimpleRequestForAccount.getAsString(URLs.URL_AUTH2_AUTHORIZE, easyPut, easyPutOpt, true));
    }

    static MiCloudAuthInfo parseOAuthInfoResult(String str, SimpleRequest.StringContent stringContent) throws IOException, InvalidResponseException, NeedOAuthorizeException, AuthenticationFailureException {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        if (str == null) {
            throw new IllegalArgumentException("response type is null");
        } else if (stringContent != null) {
            String str8 = stringContent.getHeaders().get("Content-Type");
            if (str8 == null || !str8.toLowerCase().contains("json")) {
                throw new NeedOAuthorizeException("contentType error : " + str8);
            }
            try {
                String removeSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(stringContent);
                if (removeSafePrefixAndGetRealBody != null) {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody);
                    int i = jSONObject.getInt("code");
                    if (i == 70016) {
                        throw new AuthenticationFailureException(removeSafePrefixAndGetRealBody);
                    } else if (i != 0 || !str.equals(jSONObject.getString("result"))) {
                        throw new NeedOAuthorizeException();
                    } else {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        String str9 = null;
                        if ("code".equals(str)) {
                            String queryParameter = Uri.parse(jSONObject2.getString("redirectUrl")).getQueryParameter("code");
                            if (!TextUtils.isEmpty(queryParameter)) {
                                str7 = queryParameter;
                                str3 = null;
                                str2 = null;
                                str6 = null;
                                str5 = null;
                                str4 = null;
                            } else {
                                throw new NeedOAuthorizeException();
                            }
                        } else {
                            str3 = jSONObject2.getString(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2);
                            if (!TextUtils.isEmpty(str3)) {
                                String string = jSONObject2.getString(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2);
                                str6 = jSONObject2.getString("scope");
                                str5 = jSONObject2.getString(XiaomiOAuthConstants.EXTRA_TOKEN_TYPE_2);
                                str4 = jSONObject2.getString(XiaomiOAuthConstants.EXTRA_MAC_KEY_2);
                                str2 = jSONObject2.getString(XiaomiOAuthConstants.EXTRA_MAC_ALGORITHM_2);
                                str7 = null;
                                str9 = string;
                            } else {
                                throw new NeedOAuthorizeException();
                            }
                        }
                        MiCloudAuthInfo miCloudAuthInfo = new MiCloudAuthInfo();
                        miCloudAuthInfo.setAccessToken(str3);
                        if (str9 != null) {
                            miCloudAuthInfo.setExpires(Integer.valueOf(str9).intValue());
                        }
                        miCloudAuthInfo.setScope(str6);
                        miCloudAuthInfo.setTokenType(str5);
                        miCloudAuthInfo.setMacKey(str4);
                        miCloudAuthInfo.setMacAlgorithm(str2);
                        miCloudAuthInfo.setCode(str7);
                        return miCloudAuthInfo;
                    }
                } else {
                    throw new InvalidResponseException("empty response");
                }
            } catch (JSONException e) {
                throw new NeedOAuthorizeException(e.getMessage());
            }
        } else {
            throw new IOException("failed to get response to get Auth2 auth info");
        }
    }

    public static String removeSafePrefixAndGetRealBody(SimpleRequest.StringContent stringContent) throws IOException {
        if (stringContent != null) {
            String body = stringContent.getBody();
            return body.startsWith(PASSPORT_SAFE_PREFIX) ? body.substring(11) : body;
        }
        throw new IOException("failed to get response to check register verify code");
    }

    public static String getThirdPartyAccessToken(String str, String str2, String str3, String str4) throws IOException, AccessDeniedException, AuthenticationFailureException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.OPEN_URL_GET_ACCESS_TOKEN, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut("snsType", str3).easyPut("sid", str2), new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, str4), true);
        if (asString != null) {
            return asString.toString();
        }
        throw new IOException("failed to get response to get access token");
    }

    public static String refreshThirdPartyAccessToken(String str, String str2, String str3, String str4) throws IOException, AccessDeniedException, AuthenticationFailureException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.OPEN_URL_REFRESH_ACCESS_TOKEN, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut("snsType", str3).easyPut("sid", str2), new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, str).easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, str4), true);
        if (asString != null) {
            return asString.toString();
        }
        throw new IOException("failed to get response to refresh access token");
    }

    public static HashMap<String, Object> getDeviceInfo(PassportInfo passportInfo, String str, List<String> list) throws IOException, AccessDeniedException, CipherException, InvalidResponseException, AuthenticationFailureException {
        return DeviceInfoHelper.getDeviceInfo(passportInfo, str, list);
    }

    public static ArrayList<HashMap<String, Object>> getAllDevicesInfo(PassportInfo passportInfo, ArrayList<String> arrayList) throws IOException, AccessDeniedException, AuthenticationFailureException, CipherException, InvalidResponseException {
        return DeviceInfoHelper.getAllDevicesInfo(passportInfo, arrayList);
    }

    @Deprecated
    public static boolean uploadDeviceInfo(String str, String str2, String str3, String str4, String str5, Map<String, Object> map) throws IOException, AccessDeniedException, InvalidResponseException, CipherException, AuthenticationFailureException {
        return uploadDeviceInfo(new PassportInfo(str, str2, null, str3, str4), str5, map);
    }

    public static boolean uploadDeviceInfo(PassportInfo passportInfo, String str, Map<String, Object> map) throws IOException, AccessDeniedException, InvalidResponseException, CipherException, AuthenticationFailureException {
        return DeviceInfoHelper.uploadDeviceInfo(passportInfo, str, map);
    }

    @Deprecated
    public static void uploadXiaomiUserName(String str, String str2, String str3, String str4, String str5, String str6) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        uploadXiaomiUserProfile(str, str2, str3, str4, str5, str6, null, null);
    }

    @Deprecated
    public static void uploadXiaomiUserBirthday(String str, String str2, String str3, String str4, String str5, Calendar calendar) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        uploadXiaomiUserProfile(str, str2, str3, str4, str5, null, calendar, null);
    }

    @Deprecated
    public static void uploadXiaomiUserGender(String str, String str2, String str3, String str4, String str5, Gender gender) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        uploadXiaomiUserProfile(str, str2, str3, str4, str5, null, null, gender);
    }

    @Deprecated
    public static void uploadXiaomiUserProfile(String str, String str2, String str3, String str4, String str5, String str6, Calendar calendar, Gender gender) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        uploadXiaomiUserProfile(new PassportInfo(str, str2, str3, str4, str5), new XiaomiUserProfile(str, str6, calendar, gender));
    }

    public static void uploadXiaomiUserProfile(PassportInfo passportInfo, XiaomiUserProfile xiaomiUserProfile) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        if (passportInfo == null || xiaomiUserProfile == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        Calendar birthday = xiaomiUserProfile.getBirthday();
        String str = null;
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)).easyPut("userName", xiaomiUserProfile.getUserName()).easyPut("birthday", birthday != null ? new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(birthday.getTime()) : null);
        if (xiaomiUserProfile.getGender() != null) {
            str = xiaomiUserProfile.getGender().getType();
        }
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_ACCOUNT_USER_PROFILE, easyPut.easyPut("gender", str), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (postAsMap != null) {
            Integer num = (Integer) postAsMap.getFromBody("code");
            ServerError serverError = new ServerError(postAsMap);
            if (!INT_0.equals(num)) {
                String str2 = (String) postAsMap.getFromBody("description");
                AccountLog.i(TAG, "failed to upload xiaomi user info, " + ("code: " + num + ", desc: " + str2));
                int intValue = num.intValue();
                if (intValue == 10017) {
                    throw new InvalidParameterException(num.intValue(), str2);
                } else if (intValue != 66108) {
                    throw new InvalidResponseException(serverError);
                } else {
                    throw new InvalidParameterException(num.intValue(), str2);
                }
            }
        } else {
            throw new InvalidResponseException("failed to upload xiaomi user profile");
        }
    }

    @Deprecated
    public static String uploadXiaomiUserIcon(String str, String str2, String str3, String str4, String str5, Bitmap bitmap) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        return uploadXiaomiUserIcon(new PassportInfo(str, str2, str3, str4, str5), bitmap);
    }

    public static String uploadXiaomiUserIcon(PassportInfo passportInfo, Bitmap bitmap) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        if (passportInfo == null || bitmap == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        AccountLog.i(TAG, "requestUploadUserIcon start: ");
        String requestUploadUserIcon = requestUploadUserIcon(passportInfo);
        AccountLog.i(TAG, "uploadIconToServer start: ");
        JSONObject uploadIconToServer = uploadIconToServer(requestUploadUserIcon, bitmap);
        AccountLog.i(TAG, "commitUploadUserIcon start: ");
        return commitUploadUserIcon(passportInfo, uploadIconToServer);
    }

    private static String requestUploadUserIcon(PassportInfo passportInfo) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(URLs.URL_REQUEST_UPDATE_ICON, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut(SchemaActivity.KEY_METHOD, "json"), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (asMap != null) {
            Object fromBody = asMap.getFromBody("code");
            ServerError serverError = new ServerError(asMap);
            if (INT_0.equals(fromBody)) {
                Object fromBody2 = asMap.getFromBody("data");
                if (fromBody2 instanceof Map) {
                    Object obj = ((Map) fromBody2).get("uploadUrl");
                    if (obj != null) {
                        return obj.toString();
                    }
                    throw new InvalidResponseException("uploadUrl is null");
                }
            }
            Object fromBody3 = asMap.getFromBody("description");
            AccountLog.d(TAG, "requestUploadUserIcon failed, code: " + fromBody + "; description: " + fromBody3);
            throw new InvalidResponseException(serverError);
        }
        throw new InvalidResponseException("requestUploadUserIcon request content is null");
    }

    private static JSONObject uploadIconToServer(String str, Bitmap bitmap) throws IOException, InvalidResponseException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        String execute = UploadFileRequest.execute(str, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), "userfile", "icon.jpg");
        try {
            if (!TextUtils.isEmpty(execute)) {
                return new JSONObject(execute);
            }
        } catch (JSONException e) {
            AccountLog.e(TAG, "uploadIconToServer error", e);
        }
        throw new InvalidResponseException("upload error: " + execute);
    }

    private static String commitUploadUserIcon(PassportInfo passportInfo, JSONObject jSONObject) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_COMMIT_UPDATE_ICON, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)).easyPut("json", Base64.encodeToString(jSONObject.toString().getBytes(), 2)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (postAsMap != null) {
            Integer num = (Integer) postAsMap.getFromBody("code");
            String str = (String) postAsMap.getFromBody("description");
            ServerError serverError = new ServerError(postAsMap);
            AccountLog.d(TAG, "commitUploadUserIcon failed, code: " + num + "; description: " + str);
            int intValue = num.intValue();
            if (intValue == 0) {
                Object fromBody = postAsMap.getFromBody("data");
                if (!(fromBody instanceof Map)) {
                    return null;
                }
                Object obj = ((Map) fromBody).get("downloadUrl");
                if (obj != null) {
                    return obj.toString();
                }
                throw new InvalidResponseException("downloadUrl is null");
            } else if (intValue != 66108) {
                throw new InvalidResponseException(serverError);
            } else {
                throw new InvalidParameterException(num.intValue(), str);
            }
        } else {
            throw new InvalidResponseException("commitUploadUserIcon content is null");
        }
    }

    public static String getIdentityAuthUrl(PassportInfo passportInfo, String str, IdentityAuthReason identityAuthReason) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        return getIdentityAuthUrl(passportInfo, str, getUrlForIdentityAuth(identityAuthReason));
    }

    private static String getIdentityAuthUrl(PassportInfo passportInfo, String str, String str2) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException {
        if (passportInfo != null) {
            String substring = UUID.randomUUID().toString().substring(0, 15);
            SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(str2, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("_json", String.valueOf(true)).easyPut("authST", str).easyPut("transId", substring).easyPut("traceId", substring), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (asMap != null) {
                Object fromBody = asMap.getFromBody("code");
                ServerError serverError = new ServerError(asMap);
                AccountLog.d(TAG, "getIdentityAuthUrl" + ("code: " + fromBody + ", desc: " + asMap.getFromBody("description")));
                if (fromBody instanceof Integer) {
                    int intValue = ((Integer) fromBody).intValue();
                    if (intValue == 0) {
                        return null;
                    }
                    if (intValue == 2) {
                        Object fromBody2 = asMap.getFromBody("url");
                        if (fromBody2 != null) {
                            return fromBody2.toString();
                        }
                        throw new InvalidResponseException("identityUrl is null");
                    }
                }
                throw new InvalidResponseException(serverError);
            }
            throw new IOException("getIdentityAuthUrl result should not be null");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    private static String getUrlForIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (identityAuthReason != null) {
            switch (identityAuthReason) {
                case ADD_SAFE_EMAIL:
                    return URLs.URL_IDENTITY_AUTH_FOR_ADDING_EMAIL;
                case REPLACE_SAFE_EMAIL:
                    return URLs.URL_IDENTITY_AUTH_FOR_REPLACING_EMAIL;
                case ADD_PHONE:
                    return URLs.URL_IDENTITY_AUTH_FOR_ADDING_PHONE;
                case REPLACE_PHONE:
                    return URLs.URL_IDENTITY_AUTH_FOR_REPLACING_PHONE;
                case DELETE_PHONE:
                    return URLs.URL_IDENTITY_AUTH_FOR_DELETING_PHONE;
                case SEND_EMAIL_ACTIVATE_MESSAGE:
                    return URLs.URL_IDENTITY_AUTH_FOR_SEND_EMAIL_ACTIVATE_MESSAGE;
                case SET_SECURITY_QUESTIONS:
                    return URLs.URL_IDENTITY_AUTH_FOR_SET_SECURITY_QUESTIONS;
                case MODIFY_SAFE_PHONE:
                    return URLs.URL_IDENTITY_AUTH_FOR_MODIFY_SAFE_PHONE;
                case CHANGE_PASSWORD:
                    return URLs.URL_IDENTITY_AUTH_FOR_CHANGE_PWD;
                default:
                    throw new IllegalArgumentException("invalid identityAuthReason");
            }
        } else {
            throw new IllegalArgumentException("identityAuthReason is null");
        }
    }

    private static JSONArray convertSQsToJsonArray(List<SecurityQuestion> list) {
        JSONArray jSONArray = new JSONArray();
        for (SecurityQuestion securityQuestion : list) {
            if (securityQuestion != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("q", securityQuestion.question);
                    jSONObject.put(ai.at, securityQuestion.answer);
                } catch (JSONException e) {
                    AccountLog.e(TAG, "convertSQsToJsonArray", e);
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    @Deprecated
    public static void setSecurityQuestions(PassportInfo passportInfo, List<SecurityQuestion> list, String str) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidParameterException {
        if (passportInfo == null || list == null || list.size() == 0) {
            throw new IllegalArgumentException("invalid param");
        }
        JSONArray convertSQsToJsonArray = convertSQsToJsonArray(list);
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_SET_SECURITY_QUESTIONS, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("questions", convertSQsToJsonArray != null ? convertSQsToJsonArray.toString() : null).easyPut("sid", passportInfo.getServiceId()).easyPut("authST", str), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (postAsMap != null) {
            Object fromBody = postAsMap.getFromBody("code");
            Object fromBody2 = postAsMap.getFromBody("description");
            if (fromBody instanceof Integer) {
                int intValue = ((Integer) fromBody).intValue();
                if (intValue != 0) {
                    switch (intValue) {
                        case 10016:
                        case ServerErrorCode.ERROR_ILLEGAL_PARAM /* 10017 */:
                            throw new InvalidParameterException("code: " + fromBody + " ;description: " + fromBody2);
                    }
                } else {
                    return;
                }
            }
            throw new InvalidResponseException("code: " + fromBody + " ;description: " + fromBody2);
        }
        throw new IOException("failed to setSecurityQuestions");
    }

    public static void sendEmailActivateMessage(PassportInfo passportInfo, String str, String str2, String str3, String str4, String str5) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, NeedCaptchaException, UsedEmailAddressException, ReachLimitException {
        if (passportInfo != null) {
            sendEmailActivateMessage(new SendEmailActMsgParams.Builder().passportInfo(passportInfo).emailAddress(str).deviceId(CloudCoder.hashDeviceInfo(str3)).identityAuthToken(str2).captcha(str4, str5).build());
            return;
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    public static void sendEmailActivateMessage(SendEmailActMsgParams sendEmailActMsgParams) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, NeedCaptchaException, UsedEmailAddressException, ReachLimitException {
        if (sendEmailActMsgParams == null || sendEmailActMsgParams.passportInfo == null) {
            throw new IllegalArgumentException("params should not be null!");
        }
        PassportInfo passportInfo = sendEmailActMsgParams.passportInfo;
        String str = sendEmailActMsgParams.email;
        String str2 = sendEmailActMsgParams.deviceId;
        String str3 = sendEmailActMsgParams.identityAuthToken;
        String str4 = sendEmailActMsgParams.captcha;
        String str5 = sendEmailActMsgParams.captIck;
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("address", str).easyPut("sid", passportInfo.getServiceId()).easyPut(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, str2).easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID, UserSpaceIdUtil.getNullableUserSpaceIdCookie()).easyPut("authST", str3).easyPut("icode", str4);
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        passportCookie.easyPut("ick", str5);
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_SEND_EMAIL_ACTIVATE_MESSAGE, easyPut, passportCookie, true, passportInfo.getSecurity());
        if (postAsMap != null) {
            Integer num = (Integer) postAsMap.getFromBody("code");
            String str6 = (String) postAsMap.getFromBody("description");
            String str7 = "code: " + num + " ;description: " + str6;
            ServerError serverError = new ServerError(postAsMap);
            int intValue = num.intValue();
            if (intValue != 0) {
                if (intValue != 20031) {
                    if (intValue != RESULT_CODE_INVALID_EMAIL_ADDRESS) {
                        if (intValue != RESULT_CODE_ADDRESS_USED_BY_OTHERS) {
                            if (intValue != 87001) {
                                switch (intValue) {
                                    case RESULT_CODE_ADDRESS_USED_BY_SELF /* 70021 */:
                                        break;
                                    case 70022:
                                        throw new ReachLimitException(str7);
                                    default:
                                        throw new InvalidResponseException(serverError);
                                }
                            }
                        }
                        throw new UsedEmailAddressException(str7);
                    }
                    throw new InvalidBindAddressException(str7);
                }
                throw new NeedCaptchaException(num.intValue(), str6, (String) postAsMap.getFromBody("info"));
            }
            return;
        }
        throw new IOException("failed to checkAvailabilityOfBindingEmail");
    }

    @Deprecated
    public static boolean checkEmailAvailabilityForBinding(PassportInfo passportInfo, String str, String str2, String str3) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, NeedCaptchaException {
        if (passportInfo != null) {
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("address", str).easyPut("icode", str2);
            EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
            passportCookie.easyPut("ick", str3);
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_CHECK_SAFE_EMAIL_AVAILABILITY, easyPut, passportCookie, true, passportInfo.getSecurity());
            if (postAsMap != null) {
                int intValue = ((Integer) postAsMap.getFromBody("code")).intValue();
                String str4 = (String) postAsMap.getFromBody("description");
                int intValue2 = Integer.valueOf(intValue).intValue();
                if (intValue2 == 0) {
                    return true;
                }
                if (intValue2 == RESULT_CODE_INVALID_EMAIL_ADDRESS) {
                    throw new InvalidBindAddressException("code: " + intValue + " ;description: " + str4);
                } else if (intValue2 == RESULT_CODE_ADDRESS_USED_BY_OTHERS || intValue2 == RESULT_CODE_ADDRESS_USED_BY_SELF) {
                    return false;
                } else {
                    if (intValue2 != 87001) {
                        throw new InvalidResponseException(intValue, str4);
                    }
                    throw new NeedCaptchaException(intValue, str4, null);
                }
            } else {
                throw new IOException("failed to checkAvailabilityOfBindingEmail");
            }
        } else {
            throw new IllegalArgumentException("passportInfo is null");
        }
    }

    @Deprecated
    public static void sendVerifyCodeForBindingPhoneOrEmail(PassportInfo passportInfo, String str, BindingType bindingType) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, SendVerifyCodeExceedLimitException {
        if (bindingType != null) {
            sendVerifyCode(passportInfo, str, bindingType.isBindingEmail() ? URLs.URL_SEND_BIND_EMAIL_VERIFY_CODE : URLs.URL_SEND_BIND_PHONE_VERIFY_CODE);
            return;
        }
        throw new IllegalArgumentException("type is null");
    }

    private static void sendVerifyCode(PassportInfo passportInfo, String str, String str2) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, SendVerifyCodeExceedLimitException {
        if (passportInfo != null) {
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("address", str);
            easyPut.putAll(XMPassportUtil.getDefaultLocaleParam());
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(str2, easyPut, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (postAsMap != null) {
                Object fromBody = postAsMap.getFromBody("code");
                Object fromBody2 = postAsMap.getFromBody("description");
                if (fromBody instanceof Integer) {
                    int intValue = ((Integer) fromBody).intValue();
                    if (intValue == 0) {
                        return;
                    }
                    if (intValue == RESULT_CODE_INVALID_EMAIL_ADDRESS || intValue == 70008) {
                        throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
                    } else if (intValue == 70022) {
                        throw new SendVerifyCodeExceedLimitException("code: " + fromBody + " ;description: " + fromBody2);
                    }
                }
                throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
            }
            throw new IOException("failed to sendVerifyCode");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    @Deprecated
    public static String updateBindedPhoneOrEmail(PassportInfo passportInfo, String str, BindingType bindingType, String str2, String str3, String str4) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidVerifyCodeException, InvalidBindAddressException, UserRestrictedException {
        return updateBindedPhoneOrEmail(passportInfo, getUrlForBindingPhoneOrEmail(bindingType), generateUpdateBindingParams(passportInfo, str, bindingType, str2, str3, str4));
    }

    private static String getUrlForBindingPhoneOrEmail(BindingType bindingType) {
        if (bindingType != null) {
            switch (bindingType) {
                case ADD_SAFE_EMAIL:
                    return URLs.URL_ADD_BIND_EMAIL;
                case REPLACE_SAFE_EMAIL:
                    return URLs.URL_REPLACE_BIND_EMAIL;
                case ADD_PHONE:
                    return URLs.URL_ADD_BIND_PHONE;
                case REPLACE_PHONE:
                    return URLs.URL_REPLACE_BIND_PHONE;
                default:
                    throw new IllegalArgumentException("invalid bindingType");
            }
        } else {
            throw new IllegalArgumentException("bindingType is null");
        }
    }

    private static EasyMap<String, String> generateUpdateBindingParams(PassportInfo passportInfo, String str, BindingType bindingType, String str2, String str3, String str4) {
        if (bindingType != null) {
            EasyMap<String, String> easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("vkey", str2).easyPut("authST", str3);
            if (bindingType == BindingType.REPLACE_PHONE) {
                easyPut.easyPut("oldAuthPhone", str4).easyPut("newAuthPhone", str);
            } else {
                easyPut.easyPut("address", str);
            }
            return easyPut;
        }
        throw new IllegalArgumentException("type is null");
    }

    private static String updateBindedPhoneOrEmail(PassportInfo passportInfo, String str, EasyMap<String, String> easyMap) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidVerifyCodeException, InvalidBindAddressException, UserRestrictedException {
        if (passportInfo != null) {
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(str, easyMap, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (postAsMap != null) {
                Object fromBody = postAsMap.getFromBody("code");
                Object fromBody2 = postAsMap.getFromBody("description");
                if (fromBody instanceof Integer) {
                    int intValue = ((Integer) fromBody).intValue();
                    if (intValue == 0) {
                        Object fromBody3 = postAsMap.getFromBody("data");
                        if (fromBody3 instanceof Map) {
                            Object obj = ((Map) fromBody3).get("address");
                            if (obj != null) {
                                return obj.toString();
                            }
                            throw new InvalidResponseException("address is null");
                        }
                    } else if (intValue == 20023) {
                        throw new UserRestrictedException();
                    } else if (!(intValue == RESULT_CODE_INVALID_EMAIL_ADDRESS || intValue == 70008)) {
                        if (intValue == RESULT_CODE_ERROR_VERIFY_CODE) {
                            throw new InvalidVerifyCodeException("code: " + fromBody + "; description: " + fromBody2);
                        }
                    }
                    throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
                }
                throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
            }
            throw new IOException("failed to updateBindedPhoneOrEmail");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    @Deprecated
    public static void deleteBindedPhone(PassportInfo passportInfo, String str, String str2) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, DeleteSafeAddressException {
        if (passportInfo != null) {
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_DELETE_BIND_PHONE, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("address", str).easyPut("authST", str2), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (postAsMap != null) {
                Object fromBody = postAsMap.getFromBody("code");
                Object fromBody2 = postAsMap.getFromBody("description");
                if (fromBody instanceof Integer) {
                    int intValue = ((Integer) fromBody).intValue();
                    if (intValue == 0) {
                        return;
                    }
                    if (intValue == RESULT_CODE_DELETE_SECURE_ADDRESS) {
                        throw new DeleteSafeAddressException("code: " + fromBody + " ;description: " + fromBody2);
                    } else if (intValue == 70008) {
                        throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
                    }
                }
                throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
            }
            throw new IOException("failed to deleteBindedPhone");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    @Deprecated
    public static String checkPhoneActivateStatus(PassportInfo passportInfo, String str, String str2, String str3) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException {
        if (passportInfo != null) {
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_CHECK_PHONE_ACTIVATE_STATUS, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("address", str).easyPut("simId", CloudCoder.hashDeviceInfo(str2)).easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID, UserSpaceIdUtil.getNullableUserSpaceIdCookie()).easyPut(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, CloudCoder.hashDeviceInfo(str3)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (postAsMap != null) {
                Object fromBody = postAsMap.getFromBody("code");
                Object fromBody2 = postAsMap.getFromBody("description");
                if (fromBody instanceof Integer) {
                    int intValue = ((Integer) fromBody).intValue();
                    if (intValue == 0) {
                        Object fromBody3 = postAsMap.getFromBody("data");
                        if (!(fromBody3 instanceof Map)) {
                            return null;
                        }
                        Object obj = ((Map) fromBody3).get("key");
                        if (obj != null) {
                            return obj.toString();
                        }
                        throw new InvalidResponseException("key is null");
                    } else if (intValue == 10017) {
                        return null;
                    } else {
                        if (intValue == 70008) {
                            throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
                        }
                    }
                }
                throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
            }
            throw new IOException("failed to checkPhoneActivateStatus");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    public static void changePassword(PassportInfo passportInfo, String str, String str2, String str3, String str4) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, NeedCaptchaException, UserRestrictedException, InvalidParameterException, InvalidCredentialException {
        if (passportInfo != null) {
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("oldPassword", str).easyPut("password", str2).easyPut("icode", str3);
            EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
            passportCookie.easyPut("ick", str4);
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_CHANGE_PASSWORD, easyPut, passportCookie, true, passportInfo.getSecurity());
            if (postAsMap != null) {
                int intValue = ((Integer) postAsMap.getFromBody("code")).intValue();
                String str5 = (String) postAsMap.getFromBody("description");
                String str6 = "code: " + intValue + " ;description: " + str5;
                if (intValue != 0) {
                    if (intValue != 10017) {
                        if (intValue != 20023) {
                            if (intValue != 20031) {
                                if (intValue == RESULT_CODE_ERROR_OLD_PWD) {
                                    throw new InvalidCredentialException(Integer.valueOf(intValue).intValue(), str6, true);
                                } else if (intValue != 70003) {
                                    if (intValue != 87001) {
                                        throw new InvalidResponseException(intValue, str5);
                                    }
                                }
                            }
                            throw new NeedCaptchaException(intValue, str5, (String) postAsMap.getFromBody("info"));
                        }
                        throw new UserRestrictedException();
                    }
                    throw new InvalidParameterException(str6);
                }
                return;
            }
            throw new IOException("failed to changePassword");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo != null) {
            EasyMap<String, String> easyPut = new EasyMap().easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, passportInfo.getServiceToken());
            if (TextUtils.isEmpty(passportInfo.getEncryptedUserId())) {
                easyPut.easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId());
            } else {
                easyPut.easyPut("cUserId", passportInfo.getEncryptedUserId());
            }
            return easyPut;
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    public static HashMap<String, DeviceModelInfo> getDeviceModelInfos(ArrayList<String> arrayList) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException {
        return DeviceInfoHelper.getDeviceModelInfos(arrayList);
    }

    public static void setUserRegion(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, InvalidParameterException {
        if (passportInfo != null) {
            processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_REGION, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("region", str).easyPut("sid", str2).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            return;
        }
        throw new IllegalArgumentException("null passport info");
    }

    public static void setUserLocation(PassportInfo passportInfo, String str, String str2, String str3) throws InvalidParameterException, CipherException, IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException {
        if (passportInfo != null) {
            processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_LOCATION, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("region", str).easyPut("location", str2).easyPut("sid", str3).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            return;
        }
        throw new IllegalArgumentException("null passport info");
    }

    public static void setUserEducation(PassportInfo passportInfo, XiaomiUserCoreInfo.Education education, String str) throws InvalidParameterException, CipherException, IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException {
        if (passportInfo == null || education == null) {
            throw new IllegalArgumentException("invalid params");
        }
        processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_EDUCATION, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("education", education.level).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
    }

    public static void setUserIncome(PassportInfo passportInfo, XiaomiUserCoreInfo.Income income, String str) throws InvalidParameterException, CipherException, IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException {
        if (passportInfo == null || income == null) {
            throw new IllegalArgumentException("invalid params");
        }
        processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_INCOME, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("income", income.level).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
    }

    private static void processPostSecureRequestForSetUserInfo(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, InvalidParameterException {
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(str, map, map2, z, str2);
        if (postAsMap != null) {
            Object fromBody = postAsMap.getFromBody("code");
            Object fromBody2 = postAsMap.getFromBody("description");
            if (fromBody instanceof Integer) {
                int intValue = ((Integer) fromBody).intValue();
                if (intValue != 0) {
                    switch (intValue) {
                        case 10016:
                        case ServerErrorCode.ERROR_ILLEGAL_PARAM /* 10017 */:
                            throw new InvalidParameterException(fromBody2 != null ? fromBody2.toString() : "invalid params");
                    }
                } else {
                    return;
                }
            }
            throw new InvalidResponseException("code: " + fromBody + "description: " + fromBody2);
        }
        throw new InvalidResponseException("invalid response content");
    }

    public static XiaomiUserCoreInfo getXiaomiUserCoreInfo(PassportInfo passportInfo, String str, List<XiaomiUserCoreInfo.Flag> list) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException {
        int i;
        if (passportInfo != null) {
            if (list != null) {
                i = 0;
                for (XiaomiUserCoreInfo.Flag flag : list) {
                    i |= flag.value;
                }
            } else {
                i = 0;
            }
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
            if (i != 0) {
                easyPut.easyPut(MiLinkKeys.PARAM_FLAGS, String.valueOf(i));
            }
            return processCoreInfoContent(passportInfo.getUserId(), SecureRequestForAccount.getAsMap(URLs.URL_GET_USER_CORE_INFO, easyPut, getPassportCookie(passportInfo), true, passportInfo.getSecurity()));
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    public static JSONObject getXiaomiUserStatus(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        } else if (!TextUtils.isEmpty(str2)) {
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
            easyPut.easyPut("securityFlag", str2);
            return processJsonContent(SimpleRequestForAccount.getAsString("https://account.xiaomi.com/pass2/user/status", easyPut, getPassportCookie(passportInfo), true)).getJsonContent();
        } else {
            throw new IllegalArgumentException("securityFlag is empty");
        }
    }

    public static boolean modifyUserTwoFactorAuthType(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        } else if (!TextUtils.isEmpty(str2)) {
            int resultCode = processJsonContent(SimpleRequestForAccount.postAsString("https://account.xiaomi.com/pass2/modify/twoFactorAuthType/status", new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("type", str2).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true)).getResultCode();
            AccountLog.i(TAG, "modifyUserTwoFactorAuthType: code=" + resultCode);
            return resultCode == 0;
        } else {
            throw new IllegalArgumentException("authMethod is empty");
        }
    }

    /* loaded from: classes2.dex */
    public static class GeneralJsonResult {
        private final JSONObject jsonContent;
        private final int resultCode;

        GeneralJsonResult(JSONObject jSONObject) {
            this.jsonContent = jSONObject;
            this.resultCode = jSONObject.optInt("code", -1);
        }

        public JSONObject getJsonContent() {
            return this.jsonContent;
        }

        public int getResultCode() {
            return this.resultCode;
        }
    }

    private static GeneralJsonResult processJsonContent(SimpleRequest.StringContent stringContent) throws IOException, InvalidResponseException {
        try {
            GeneralJsonResult generalJsonResult = new GeneralJsonResult(new JSONObject(removeSafePrefixAndGetRealBody(stringContent)));
            if (generalJsonResult.getResultCode() == 0) {
                return generalJsonResult;
            }
            throw new InvalidResponseException(stringContent.toString());
        } catch (JSONException e) {
            AccountLog.e(TAG, "JSON ERROR", e);
            throw new InvalidResponseException(e.getMessage());
        }
    }

    private static XiaomiUserCoreInfo processCoreInfoContent(String str, SimpleRequest.MapContent mapContent) throws InvalidResponseException {
        if (mapContent != null) {
            Object fromBody = mapContent.getFromBody("code");
            if (INT_0.equals(fromBody)) {
                XiaomiUserCoreInfo.Builder builder = new XiaomiUserCoreInfo.Builder(str);
                Object fromBody2 = mapContent.getFromBody("data");
                if (fromBody2 instanceof Map) {
                    Map map = (Map) fromBody2;
                    Object obj = map.get("userName");
                    if (obj instanceof String) {
                        builder.setUserName((String) obj);
                    }
                    Object obj2 = map.get("icon");
                    if (obj2 instanceof String) {
                        String str2 = (String) obj2;
                        int lastIndexOf = str2.lastIndexOf(".");
                        if (str2.length() > 0 && lastIndexOf > 0) {
                            String substring = str2.substring(0, lastIndexOf);
                            String substring2 = str2.substring(str2.lastIndexOf("."));
                            builder.setAvatarAddress(substring + ICON_SIZE_SUFFIX_320 + substring2);
                        }
                    }
                    Object obj3 = map.get("sns");
                    if (obj3 instanceof List) {
                        builder.setSnsInfoList(XiaomiUserCoreInfo.SnsInfo.parseSnsList((List) obj3));
                    }
                    Object obj4 = map.get("userAddresses");
                    if (obj4 instanceof List) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (Object obj5 : (List) obj4) {
                            if (obj5 instanceof Map) {
                                Map map2 = (Map) obj5;
                                Object obj6 = map2.get("addressType");
                                Object obj7 = map2.get("address");
                                Object obj8 = map2.get(MiLinkKeys.PARAM_FLAGS);
                                if ((obj6 instanceof Integer) && (obj7 instanceof String)) {
                                    Integer num = (Integer) obj6;
                                    String str3 = (String) obj7;
                                    Integer num2 = INT_0;
                                    if (obj8 instanceof Integer) {
                                        num2 = (Integer) obj8;
                                    }
                                    boolean z = (num2.intValue() & 2) != 0;
                                    int intValue = num.intValue();
                                    if (intValue != 9) {
                                        switch (intValue) {
                                            case 1:
                                                if (!z) {
                                                    if (num2.intValue() == 8) {
                                                        arrayList.add(str3);
                                                        break;
                                                    } else {
                                                        break;
                                                    }
                                                } else {
                                                    builder.setSafePhone(str3);
                                                    arrayList.add(0, str3);
                                                    continue;
                                                }
                                            case 2:
                                                if (z) {
                                                    builder.setEmailAddress(str3);
                                                    break;
                                                } else {
                                                    continue;
                                                }
                                        }
                                    } else {
                                        int lastIndexOf2 = str3.lastIndexOf("@ALIAS");
                                        if (lastIndexOf2 > 0) {
                                            str3 = str3.substring(0, lastIndexOf2);
                                        }
                                        builder.setNickName(str3);
                                    }
                                }
                            }
                        }
                        builder.setPhoneList(arrayList);
                    }
                    Object obj9 = map.get("birthday");
                    if ((obj9 instanceof String) && !TextUtils.isEmpty((String) obj9)) {
                        Calendar instance = Calendar.getInstance();
                        try {
                            instance.setTime(new SimpleDateFormat(SIMPLE_DATE_FORMAT).parse((String) obj9));
                            builder.setBirthday(instance);
                        } catch (ParseException e) {
                            AccountLog.e(TAG, "getXiaomiUserProfile", e);
                        }
                    }
                    Object obj10 = map.get("gender");
                    if (obj10 instanceof String) {
                        String str4 = (String) obj10;
                        if (!TextUtils.isEmpty(str4)) {
                            if (MessageElement.XPATH_PREFIX.equals(str4)) {
                                builder.setGender(Gender.MALE);
                            } else if ("f".equals(str4)) {
                                builder.setGender(Gender.FEMALE);
                            }
                        }
                    }
                    Object obj11 = map.get("isSetSafeQuestions");
                    if (obj11 != null && (obj11 instanceof Boolean)) {
                        builder.setIsSetSafeQuestions(((Boolean) obj11).booleanValue());
                    }
                    Object obj12 = map.get("locale");
                    if (obj12 instanceof String) {
                        String str5 = (String) obj12;
                        if (!TextUtils.isEmpty(str5)) {
                            builder.setLocale(str5);
                        }
                    }
                    Object obj13 = map.get("region");
                    if (obj13 instanceof String) {
                        String str6 = (String) obj13;
                        if (!TextUtils.isEmpty(str6)) {
                            builder.setRegion(str6);
                        }
                    }
                    Object obj14 = map.get("location");
                    if (obj14 != null && (obj14 instanceof String)) {
                        builder.setLocationZipCode((String) obj14);
                    }
                    Object obj15 = map.get("education");
                    if (obj15 instanceof String) {
                        String str7 = (String) obj15;
                        if (!TextUtils.isEmpty(str7)) {
                            XiaomiUserCoreInfo.Education educationTypeByName = XiaomiUserCoreInfo.Education.getEducationTypeByName(str7);
                            if (educationTypeByName != null) {
                                builder.setEducation(educationTypeByName);
                            } else {
                                throw new InvalidResponseException("invalid education value: " + obj15);
                            }
                        }
                    }
                    Object obj16 = map.get("income");
                    if (obj16 instanceof String) {
                        String str8 = (String) obj16;
                        if (!TextUtils.isEmpty(str8)) {
                            XiaomiUserCoreInfo.Income incomeTypeByName = XiaomiUserCoreInfo.Income.getIncomeTypeByName(str8);
                            if (incomeTypeByName != null) {
                                builder.setIncome(incomeTypeByName);
                            } else {
                                throw new InvalidResponseException("invalid income value: " + obj16);
                            }
                        }
                    }
                }
                return builder.build();
            }
            Object fromBody3 = mapContent.getFromBody("description");
            throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody3);
        }
        throw new InvalidResponseException("result content is null");
    }

    public static String generateRandomPasswordFromServer() throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.URL_GENERATE_RANDOM_PASSWORD, null, null, true);
        if (asString != null) {
            try {
                JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(asString));
                if (jSONObject.getInt("code") == 0) {
                    return new JSONObject(jSONObject.getString("data")).optString("pwd");
                }
                throw new InvalidResponseException(asString.toString());
            } catch (JSONException e) {
                AccountLog.e(TAG, "JSON ERROR", e);
                throw new InvalidResponseException(e.getMessage());
            }
        } else {
            throw new InvalidResponseException("result content is null");
        }
    }

    public static int sendPhoneLoginTicket(SendPhoneTicketParams sendPhoneTicketParams) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, NeedCaptchaException, ReachLimitException, TokenExpiredException, InvalidPhoneNumException {
        if (sendPhoneTicketParams != null) {
            EasyMap easyPut = new EasyMap().easyPutOpt("user", sendPhoneTicketParams.phone).easyPutOpt("userHash", sendPhoneTicketParams.phoneHash).easyPutOpt("sid", sendPhoneTicketParams.serviceId).easyPutOpt("captCode", sendPhoneTicketParams.captCode).easyPut("_json", "true");
            easyPut.putAll(XMPassportUtil.getDefaultLocaleParam());
            EasyMap easyPutOpt = new EasyMap().easyPutOpt(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, sendPhoneTicketParams.activatorToken).easyPutOpt("ick", sendPhoneTicketParams.captIck);
            addDeviceIdInCookies(easyPutOpt, sendPhoneTicketParams.deviceId);
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(URLs.URL_ACCOUNT_BASE + "/sendServiceLoginTicket", easyPut, easyPutOpt, true);
            if (postAsString != null) {
                try {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(postAsString));
                    int i = jSONObject.getInt("code");
                    String optString = jSONObject.optString("description");
                    ServerError serverError = new ServerError(jSONObject);
                    String str = "code: " + i + ", desc: " + optString;
                    AccountLog.i(TAG, "sendPhoneLoginTicket: " + str);
                    if (i == 0) {
                        return jSONObject.getJSONObject("data").optInt("vCodeLen");
                    }
                    if (i == 21317) {
                        throw new TokenExpiredException(str);
                    } else if (i == 70008) {
                        throw new InvalidPhoneNumException(optString);
                    } else if (i == 70022) {
                        throw new ReachLimitException(str);
                    } else if (i != 87001) {
                        throw new InvalidResponseException(serverError);
                    } else {
                        throw new NeedCaptchaException(i, optString, jSONObject.getString("captchaUrl"));
                    }
                } catch (JSONException unused) {
                    throw new InvalidResponseException("result not json");
                }
            } else {
                throw new InvalidResponseException("result content is null");
            }
        } else {
            throw new IllegalArgumentException("send phone ticket params is null");
        }
    }

    public static RegisterUserInfo queryPhoneUserInfo(QueryPhoneInfoParams queryPhoneInfoParams) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, InvalidVerifyCodeException, InvalidPhoneNumException {
        if (queryPhoneInfoParams != null) {
            EasyMap easyPut = new EasyMap().easyPutOpt("user", queryPhoneInfoParams.phone).easyPutOpt("ticket", queryPhoneInfoParams.ticket).easyPutOpt("userHash", queryPhoneInfoParams.phoneHash).easyPutOpt("sid", queryPhoneInfoParams.serviceId).easyPut("_json", "true");
            EasyMap easyPutOpt = new EasyMap().easyPutOpt(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, queryPhoneInfoParams.activatorToken);
            addDeviceIdInCookies(easyPutOpt, queryPhoneInfoParams.deviceId);
            boolean z = true;
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(URLs.URL_ACCOUNT_BASE + "/phoneInfo", easyPut, easyPutOpt, true);
            if (postAsString != null) {
                try {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(postAsString));
                    int i = jSONObject.getInt("code");
                    String str = "code: " + i + ", desc: " + jSONObject.optString("description");
                    AccountLog.i(TAG, "queryPhoneUserInfo: " + str);
                    if (i == 0) {
                        String header = postAsString.getHeader("ticketToken");
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        RegisterUserInfo.Builder needToast = new RegisterUserInfo.Builder(jSONObject2.getInt("status")).userId(jSONObject2.getString("id")).userName(jSONObject2.optString("nickname")).avatarAddress(jSONObject2.optString("portrait")).phone(jSONObject2.optString("phone")).ticketToken(header).maskedUserId(jSONObject2.optString("maskedUserId")).hasPwd(jSONObject2.optInt("pwd") == 1).bindTime(jSONObject2.optLong("bindTime", 0L)).needGetActiveTime(jSONObject2.optBoolean("needGetActiveTime", false)).needToast(jSONObject2.optBoolean("needToast", false));
                        if (jSONObject2.optInt("registerPwd") != 1) {
                            z = false;
                        }
                        return needToast.registerPwd(z).build();
                    } else if (i == RESULT_CODE_REQUEST_RESTRICTED) {
                        throw new InvalidVerifyCodeException(str);
                    } else if (i != 70008) {
                        throw new InvalidResponseException(i, str);
                    } else {
                        throw new InvalidPhoneNumException(str);
                    }
                } catch (JSONException unused) {
                    throw new InvalidResponseException("result not json");
                }
            } else {
                throw new InvalidResponseException("result content is null");
            }
        } else {
            throw new IllegalArgumentException("invalid params");
        }
    }

    public static AccountInfo loginByPhone(PhoneTicketLoginParams phoneTicketLoginParams) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, InvalidUserNameException, NeedNotificationException, InvalidVerifyCodeException, InvalidPhoneNumException {
        if (phoneTicketLoginParams != null) {
            MetaLoginData phoneLoginMetaLoginData = getPhoneLoginMetaLoginData(phoneTicketLoginParams.phone, phoneTicketLoginParams.serviceId);
            String str = TextUtils.isEmpty(phoneTicketLoginParams.serviceId) ? PASSPORT_SID : phoneTicketLoginParams.serviceId;
            EasyMap easyPut = new EasyMap().easyPutOpt("user", phoneTicketLoginParams.phone).easyPutOpt("userHash", phoneTicketLoginParams.phoneHash).easyPutOpt("ticket", phoneTicketLoginParams.ticket).easyPut("sid", str).easyPut("_json", "true").easyPut("_sign", phoneLoginMetaLoginData.sign).easyPut("qs", phoneLoginMetaLoginData.qs).easyPut("callback", phoneLoginMetaLoginData.callback);
            addEnvToParams(easyPut, phoneTicketLoginParams.hashedEnvFactors);
            EasyMap easyPutOpt = new EasyMap().easyPutOpt(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, phoneTicketLoginParams.activatorToken).easyPutOpt("ticketToken", phoneTicketLoginParams.ticketToken);
            addDeviceIdInCookies(easyPutOpt, phoneTicketLoginParams.deviceId);
            addAntiSpamIpAddressInCookies(easyPutOpt);
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(URLs.URL_ACCOUNT_BASE + "/serviceLoginTicketAuth", easyPut, easyPutOpt, true);
            if (postAsString != null) {
                return processPhoneLoginContent(postAsString, str, phoneTicketLoginParams.returnStsUrl);
            }
            throw new InvalidResponseException("result content is null");
        }
        throw new IllegalArgumentException("null phone ticket login params");
    }

    private static MetaLoginData getPhoneLoginMetaLoginData(String str, String str2) throws IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, InvalidUserNameException, NeedNotificationException {
        try {
            loginByPassToken(new PassTokenLoginParams.Builder(str, null, str2).isGetPhoneTicketLoginMetaData(true).build());
            throw new InvalidResponseException("Unexpected login success with empty pass token");
        } catch (InvalidCredentialException e) {
            return e.getMetaLoginData();
        }
    }

    private static AccountInfo processPhoneLoginContent(SimpleRequest.StringContent stringContent, String str, boolean z) throws IOException, InvalidResponseException, NeedNotificationException, AccessDeniedException, InvalidVerifyCodeException, InvalidPhoneNumException, AuthenticationFailureException {
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
            int i = jSONObject.getInt("code");
            String str2 = "code: " + i + ", desc: " + jSONObject.optString(IChannel.EXTRA_ERROR_DESC);
            AccountLog.i(TAG, "processPhoneLoginContent: " + str2);
            if (i == 0) {
                String header = stringContent.getHeader(BaseConstants.EXTRA_USER_ID);
                String header2 = stringContent.getHeader(BaseConstants.EXTRA_PASSTOKEN);
                if (jSONObject.optInt("securityStatus", 0) != 0) {
                    String string = jSONObject.getString("notificationUrl");
                    if (string != null) {
                        if (!string.startsWith("http")) {
                            string = ACCOUNT_DOMAIN + string;
                        }
                        throw new NeedNotificationException(header, string, stringContent);
                    }
                    throw new InvalidResponseException("notificationUrl is null");
                } else if (TextUtils.isEmpty(header)) {
                    throw new InvalidResponseException("no user Id in login response");
                } else if (!TextUtils.isEmpty(header2)) {
                    return parseLoginResult(header, stringContent, str, null, false, z);
                } else {
                    throw new InvalidResponseException("no passToken in login response");
                }
            } else if (i == 70008) {
                throw new InvalidPhoneNumException(str2);
            } else if (i != RESULT_CODE_ERROR_VERIFY_CODE) {
                throw new InvalidResponseException(str2);
            } else {
                throw new InvalidVerifyCodeException(str2);
            }
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static String sendSetPasswordTicket(PassportInfo passportInfo, String str) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, ReachLimitException, InvalidPhoneNumException {
        if (passportInfo != null) {
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPutOpt("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
            easyPut.putAll(XMPassportUtil.getDefaultLocaleParam());
            EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
            addDeviceIdInCookies(passportCookie, null);
            SimpleRequest.StringContent postAsString = SecureRequestForAccount.postAsString(URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/sendSetPasswordTicket", easyPut, passportCookie, true, passportInfo.getSecurity());
            if (postAsString != null) {
                try {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(postAsString));
                    int i = jSONObject.getInt("code");
                    String str2 = "code: " + i + ", desc: " + jSONObject.optString("description");
                    AccountLog.d(TAG, "requestSetPassword: " + str2);
                    if (i == 0) {
                        return jSONObject.getJSONObject("data").getString("maskedPhone");
                    }
                    if (i == 70009) {
                        throw new InvalidPhoneNumException(str2);
                    } else if (i != 70022) {
                        throw new InvalidResponseException(i, str2);
                    } else {
                        throw new ReachLimitException(str2);
                    }
                } catch (JSONException unused) {
                    throw new InvalidResponseException("result not json");
                }
            } else {
                throw new InvalidResponseException("result content is null");
            }
        } else {
            throw new IllegalArgumentException("passport info should not be null");
        }
    }

    public static String setPassword(SetPasswordParams setPasswordParams) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, InvalidVerifyCodeException, InvalidParameterException, InvalidCredentialException, UserRestrictedException, CipherException {
        if (setPasswordParams != null) {
            PassportInfo passportInfo = setPasswordParams.passportApiInfo;
            if (passportInfo != null) {
                EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, setPasswordParams.userId).easyPut("pwd", setPasswordParams.pwd).easyPut(BaseConstants.EXTRA_PASSTOKEN, setPasswordParams.passToken).easyPutOpt("sid", setPasswordParams.serviceId).easyPutOpt("ticket", setPasswordParams.ticket).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
                MiuiActivatorInfo miuiActivatorInfo = setPasswordParams.miuiActivatorInfo;
                if (miuiActivatorInfo != null) {
                    easyPut.easyPutOpt("phone", miuiActivatorInfo.phone).easyPutOpt("simId", miuiActivatorInfo.simId).easyPutOpt("vKey2", miuiActivatorInfo.vKey2).easyPutOpt("nonce", miuiActivatorInfo.vKey2Nonce);
                }
                EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
                addDeviceIdInCookies(passportCookie, setPasswordParams.deviceId);
                SimpleRequest.StringContent postAsString = SecureRequestForAccount.postAsString(URLs.URL_ACCOUNT_API_V2_BASE + "/safe/user/setPassword", easyPut, passportCookie, true, passportInfo.getSecurity());
                if (postAsString != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(postAsString));
                        int i = jSONObject.getInt("code");
                        String str = "code: " + i + ", desc: " + jSONObject.optString("description");
                        AccountLog.d(TAG, "requestSetPassword: " + str);
                        if (i == 0) {
                            return jSONObject.getJSONObject("data").getString(BaseConstants.EXTRA_PASSTOKEN);
                        }
                        if (i == RESULT_CODE_REQUEST_RESTRICTED) {
                            throw new UserRestrictedException(str);
                        } else if (i == 21317) {
                            throw new InvalidCredentialException(i, str, false);
                        } else if (i == 70003) {
                            throw new InvalidParameterException(str);
                        } else if (i == RESULT_CODE_EMPTY_VERIFY_CODE || i == RESULT_CODE_ERROR_VERIFY_CODE) {
                            throw new InvalidVerifyCodeException(str);
                        } else {
                            throw new InvalidResponseException(str);
                        }
                    } catch (JSONException unused) {
                        throw new InvalidResponseException("result not json");
                    }
                } else {
                    throw new InvalidResponseException("result content is null");
                }
            } else {
                throw new IllegalArgumentException("passport info should not be null");
            }
        } else {
            throw new IllegalArgumentException("set password params should not be null");
        }
    }

    public static QRLoginUrlInfo getQRLoginUrl(String str, String str2) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, IOException {
        if (TextUtils.isEmpty(str)) {
            str = PASSPORT_SID;
        }
        MetaLoginData metaLoginData = getMetaLoginData(str);
        EasyMap easyMap = new EasyMap();
        easyMap.easyPut("sid", str).easyPut("callback", metaLoginData.callback);
        if (!TextUtils.isEmpty(str2)) {
            easyMap.easyPut("csid", str2).easyPut("ccallback", getMetaLoginData(str2).callback);
        }
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.ACCOUNT_DOMAIN + "/longPolling/loginUrl", easyMap, null, true);
        if (asString != null) {
            String removeSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(asString);
            try {
                JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody);
                int i = jSONObject.getInt("code");
                String string = jSONObject.getString("description");
                AccountLog.i(TAG, "getQRLoginUrl code: " + i + ", desc: " + string);
                if (i == 0) {
                    return new QRLoginUrlInfo(str, jSONObject.getString("lp"), jSONObject.getString("loginUrl"), jSONObject.getString("qr"));
                }
                throw new InvalidResponseException(i, string);
            } catch (JSONException unused) {
                throw new InvalidResponseException("JSONException: " + removeSafePrefixAndGetRealBody);
            }
        } else {
            throw new InvalidResponseException("qr login url content is null");
        }
    }

    public static AccountInfo loginByLongPolling(QRLoginUrlInfo qRLoginUrlInfo) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, InvalidCredentialException, NeedNotificationException {
        if (qRLoginUrlInfo != null) {
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(qRLoginUrlInfo.lp, null, null, null, true, TIMEOUT_LONG_POLLING);
            if (asString != null) {
                try {
                    return processLoginContent(asString, qRLoginUrlInfo.serviceId, true, true, false);
                } catch (InvalidUserNameException | NeedCaptchaException | NeedVerificationException e) {
                    throw new InvalidResponseException("should not reach here!", e);
                }
            } else {
                throw new InvalidResponseException("long polling result is null");
            }
        } else {
            throw new IllegalArgumentException("null long polling para");
        }
    }

    private static void addDeviceIdInCookies(EasyMap<String, String> easyMap, String str) {
        if (easyMap != null) {
            XMPassportSettings.getApplicationContext();
            if (TextUtils.isEmpty(str)) {
                str = getHashedDeviceId();
            }
            easyMap.easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, str).easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID, UserSpaceIdUtil.getNullableUserSpaceIdCookie());
            return;
        }
        throw new IllegalArgumentException("cookie params should not be null");
    }

    private static String getHashedDeviceId() {
        return new HashedDeviceIdUtil(XMPassportSettings.getApplicationContext()).getHashedDeviceIdNoThrow();
    }

    private static void addAntiSpamIpAddressInCookies(Map<String, String> map) {
        Map<String, String> blockingGetIPAddressCookie = new AntiSpamIpAddressController().blockingGetIPAddressCookie();
        if (blockingGetIPAddressCookie != null && blockingGetIPAddressCookie.size() > 0) {
            map.putAll(blockingGetIPAddressCookie);
        }
    }
}
