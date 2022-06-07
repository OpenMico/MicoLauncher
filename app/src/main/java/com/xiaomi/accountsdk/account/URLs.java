package com.xiaomi.accountsdk.account;

import android.content.Context;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.smarthome.setting.LoginSetting;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class URLs {
    public static final String ACCOUNT_DOMAIN;
    public static final String CA_ACCOUNT_DOMAIN;
    static final String HOST_URL_ACCOUNT_BASE;
    static final String OPEN_URL_GET_ACCESS_TOKEN;
    static final String OPEN_URL_REFRESH_ACCESS_TOKEN;
    public static final String URL_ACCOUNT_API_V2_BASE;
    public static final String URL_ACCOUNT_API_V3_BASE;
    public static final String URL_ACCOUNT_BASE;
    private static final String URL_ACCOUNT_BASE_PASS2 = "https://account.xiaomi.com/pass2";
    public static final String URL_ACCOUNT_OAUTH_BASE;
    public static final String URL_ACCOUNT_SAFE_API_BASE;
    static final String URL_ACCOUNT_USER_PROFILE;
    @Deprecated
    public static final String URL_ACOUNT_API_BASE;
    public static final String URL_ACOUNT_API_BASE_SECURE;
    public static final String URL_ACOUNT_API_BASE_V2_SECURE;
    static final String URL_ADD_BIND_EMAIL;
    static final String URL_ADD_BIND_PHONE;
    static final String URL_AUTH2_AUTHORIZE;
    public static final String URL_CHANGE_PASSWORD;
    static final String URL_CHECK_PHONE_ACTIVATE_STATUS;
    static final String URL_CHECK_SAFE_EMAIL_AVAILABILITY;
    static final String URL_COMMIT_UPDATE_ICON;
    static final String URL_DELETE_BIND_PHONE;
    public static final String URL_DEVICES_SETTING;
    public static final String URL_DEV_BASE;
    public static final String URL_DEV_SETTING;
    static final String URL_EMAIL_REGISTER;
    public static final String URL_FAIL_RECEIVE_SMS_CODE_FAQ = "https://static.account.xiaomi.com/html/faq/faqSMSerror.html";
    static final String URL_GENERATE_RANDOM_PASSWORD;
    public static final String URL_GET_BIND_EMAIL_CAPTCODE;
    public static final String URL_GET_DEVICE_MODEL_INFOS;
    public static final String URL_GET_USER_CORE_INFO;
    static final String URL_IDENTITY_AUTH_FOR_ADDING_EMAIL;
    static final String URL_IDENTITY_AUTH_FOR_ADDING_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_CHANGE_PWD;
    static final String URL_IDENTITY_AUTH_FOR_DELETING_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_MODIFY_SAFE_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_REPLACING_EMAIL;
    static final String URL_IDENTITY_AUTH_FOR_REPLACING_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_SEND_EMAIL_ACTIVATE_MESSAGE;
    static final String URL_IDENTITY_AUTH_FOR_SET_SECURITY_QUESTIONS;
    @Deprecated
    public static final String URL_LOGIN;
    public static final String URL_LOGIN_AUTH2;
    static String URL_LOGIN_AUTH2_HTTPS = null;
    static final String URL_LOGIN_AUTH2_PASSPORT_CA;
    public static final String URL_LOGIN_AUTH_STEP2;
    static String URL_LOGIN_HTTPS = null;
    static final String URL_LOGIN_PASSPORT_CA;
    public static final String URL_OPEN_ACCOUNT_THIRD_BASE;
    static final String URL_PASS2_MODIFY_TWO_FACTOR_AUTH_TYPE = "https://account.xiaomi.com/pass2/modify/twoFactorAuthType/status";
    static final String URL_PASS2_USER_STATUS = "https://account.xiaomi.com/pass2/user/status";
    static final String URL_PASSPORT_CA_ACCOUNT_BASE;
    static final String URL_REG;
    static final String URL_REG_CHECK_VERIFY_CODE;
    public static final String URL_REG_GET_CAPTCHA_CODE;
    static final String URL_REG_GET_VERIFY_CODE;
    static final String URL_REG_PHONE;
    static final String URL_REG_SEND_PHONE_TICKET;
    static final String URL_REG_TOKEN;
    static final String URL_REG_VERIFY_PHONE;
    static final String URL_REPLACE_BIND_EMAIL;
    static final String URL_REPLACE_BIND_PHONE;
    static final String URL_REQUEST_UPDATE_ICON;
    public static final String URL_RESEND_EMAIL;
    static final String URL_RESET_PASSWORD;
    static final String URL_SEND_BIND_EMAIL_VERIFY_CODE;
    static final String URL_SEND_BIND_PHONE_VERIFY_CODE;
    static final String URL_SEND_EMAIL_ACTIVATE_MESSAGE;
    static final String URL_SET_SECURITY_QUESTIONS;
    public static final String URL_SET_USER_EDUCATION;
    public static final String URL_SET_USER_INCOME;
    public static final String URL_SET_USER_LOCATION;
    public static final String URL_SET_USER_REGION;
    public static final String URL_USER_EXISTS;
    static final boolean USE_PREVIEW = XMPassportSettings.isStaging();
    static final Map<String, String> caUrlMap;

    @Deprecated
    public static void setLocalUsePreview(Context context, boolean z) {
        XMPassportSettings.setLocalStaging(context, z);
    }

    static {
        ACCOUNT_DOMAIN = USE_PREVIEW ? "http://account.preview.n.xiaomi.net" : "https://account.xiaomi.com";
        CA_ACCOUNT_DOMAIN = USE_PREVIEW ? "http://account.preview.n.xiaomi.net" : "https://c.id.mi.com";
        HOST_URL_ACCOUNT_BASE = USE_PREVIEW ? "account.preview.n.xiaomi.net" : LoginSetting.COOKIE_DOMAIN_PASSPORT_API;
        URL_ACCOUNT_BASE = USE_PREVIEW ? "http://account.preview.n.xiaomi.net/pass" : "https://account.xiaomi.com/pass";
        URL_PASSPORT_CA_ACCOUNT_BASE = USE_PREVIEW ? "http://account.preview.n.xiaomi.net/pass" : "http://c.id.mi.com/pass";
        URL_ACOUNT_API_BASE = USE_PREVIEW ? "http://api.account.preview.n.xiaomi.net/pass" : "http://api.account.xiaomi.com/pass";
        URL_ACOUNT_API_BASE_SECURE = USE_PREVIEW ? "http://api.account.preview.n.xiaomi.net/pass" : "https://api.account.xiaomi.com/pass";
        URL_ACOUNT_API_BASE_V2_SECURE = USE_PREVIEW ? "http://api.account.preview.n.xiaomi.net/pass/v2" : "https://api.account.xiaomi.com/pass/v2";
        URL_ACCOUNT_SAFE_API_BASE = USE_PREVIEW ? "http://api.account.preview.n.xiaomi.net/pass/v2/safe" : "https://api.account.xiaomi.com/pass/v2/safe";
        URL_ACCOUNT_API_V2_BASE = USE_PREVIEW ? "http://api.account.preview.n.xiaomi.net/pass/v2" : "https://api.account.xiaomi.com/pass/v2";
        URL_ACCOUNT_API_V3_BASE = USE_PREVIEW ? "http://api.account.preview.n.xiaomi.net/pass/v3" : "https://api.account.xiaomi.com/pass/v3";
        URL_ACCOUNT_OAUTH_BASE = USE_PREVIEW ? "http://account.preview.n.xiaomi.net/oauth2/" : "https://account.xiaomi.com/oauth2/";
        URL_DEV_BASE = USE_PREVIEW ? "http://api.device.preview.n.xiaomi.net" : "https://api.device.xiaomi.net";
        URL_GET_DEVICE_MODEL_INFOS = URL_DEV_BASE + "/modelinfos";
        URL_DEV_SETTING = URL_DEV_BASE + "/api/user/device/setting";
        URL_DEVICES_SETTING = URL_DEV_BASE + "/api/user/devices/setting";
        URL_LOGIN_AUTH2 = URL_ACCOUNT_BASE + "/serviceLoginAuth2";
        URL_LOGIN_AUTH2_HTTPS = URL_ACCOUNT_BASE + "/serviceLoginAuth2";
        URL_LOGIN_AUTH2_PASSPORT_CA = URL_PASSPORT_CA_ACCOUNT_BASE + "/serviceLoginAuth2CA";
        URL_LOGIN_AUTH_STEP2 = URL_ACCOUNT_BASE + "/loginStep2";
        URL_USER_EXISTS = URL_ACCOUNT_API_V3_BASE + "/user@id";
        URL_GET_USER_CORE_INFO = URL_ACCOUNT_SAFE_API_BASE + "/user/coreInfo";
        URL_OPEN_ACCOUNT_THIRD_BASE = USE_PREVIEW ? "http://open.account.preview.n.xiaomi.net/third/" : "https://open.account.xiaomi.com/third/";
        URL_REQUEST_UPDATE_ICON = URL_ACCOUNT_SAFE_API_BASE + "/user/updateIconRequest";
        URL_COMMIT_UPDATE_ICON = URL_ACCOUNT_SAFE_API_BASE + "/user/updateIconCommit";
        URL_REG = URL_ACOUNT_API_BASE_V2_SECURE + "/user/full";
        URL_REG_PHONE = URL_ACOUNT_API_BASE_SECURE + "/user/full/@phone";
        URL_RESEND_EMAIL = URL_ACOUNT_API_BASE_SECURE + "/sendActivateMessage";
        URL_REG_GET_VERIFY_CODE = URL_ACCOUNT_BASE + "/sendPhoneTicket";
        URL_REG_GET_CAPTCHA_CODE = URL_ACCOUNT_BASE + "/getCode?icodeType=register";
        URL_REG_CHECK_VERIFY_CODE = URL_ACCOUNT_BASE + "/verifyPhoneRegTicket";
        URL_REG_SEND_PHONE_TICKET = URL_ACCOUNT_BASE + "/sendPhoneRegTicket";
        URL_REG_VERIFY_PHONE = URL_ACCOUNT_BASE + "/verifyRegPhone";
        URL_REG_TOKEN = URL_ACCOUNT_BASE + "/tokenRegister";
        URL_RESET_PASSWORD = URL_ACCOUNT_BASE + "/auth/resetPassword";
        URL_AUTH2_AUTHORIZE = URL_ACCOUNT_OAUTH_BASE + "authorize";
        URL_LOGIN = URL_ACCOUNT_BASE + "/serviceLogin";
        URL_LOGIN_HTTPS = URL_ACCOUNT_BASE + "/serviceLogin";
        URL_LOGIN_PASSPORT_CA = URL_PASSPORT_CA_ACCOUNT_BASE + "/serviceLoginCA";
        OPEN_URL_GET_ACCESS_TOKEN = URL_OPEN_ACCOUNT_THIRD_BASE + "getToken";
        OPEN_URL_REFRESH_ACCESS_TOKEN = URL_OPEN_ACCOUNT_THIRD_BASE + "refreshToken";
        URL_ACCOUNT_USER_PROFILE = URL_ACCOUNT_SAFE_API_BASE + XiaomiOAuthConstants.OPEN_API_PATH_PROFILE;
        URL_CHECK_SAFE_EMAIL_AVAILABILITY = URL_ACCOUNT_SAFE_API_BASE + "/user/checkSafeEmailBindParams";
        URL_SEND_BIND_EMAIL_VERIFY_CODE = URL_ACCOUNT_SAFE_API_BASE + "/user/sendBindSafeEmailVerifyMessage";
        URL_SEND_BIND_PHONE_VERIFY_CODE = URL_ACCOUNT_SAFE_API_BASE + "/user/sendBindAuthPhoneVerifyMessage";
        URL_ADD_BIND_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/addPhone";
        URL_REPLACE_BIND_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/updatePhone";
        URL_DELETE_BIND_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/deletePhone";
        URL_REPLACE_BIND_EMAIL = URL_ACCOUNT_SAFE_API_BASE + "/user/replaceSafeEmailAddress";
        URL_ADD_BIND_EMAIL = URL_ACCOUNT_SAFE_API_BASE + "/user/addSafeEmailAddress";
        URL_SEND_EMAIL_ACTIVATE_MESSAGE = URL_ACCOUNT_SAFE_API_BASE + "/user/sendEmailActivateMessage";
        URL_SET_SECURITY_QUESTIONS = URL_ACCOUNT_SAFE_API_BASE + "/user/setSafeQuestions";
        URL_IDENTITY_AUTH_FOR_ADDING_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/addPhoneAuth";
        URL_IDENTITY_AUTH_FOR_REPLACING_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/updatePhoneAuth";
        URL_IDENTITY_AUTH_FOR_DELETING_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/deletePhoneAuth";
        URL_IDENTITY_AUTH_FOR_REPLACING_EMAIL = URL_ACCOUNT_SAFE_API_BASE + "/user/replaceSafeEmailAddressAuth";
        URL_IDENTITY_AUTH_FOR_ADDING_EMAIL = URL_ACCOUNT_SAFE_API_BASE + "/user/addSafeEmailAddressAuth";
        URL_IDENTITY_AUTH_FOR_SEND_EMAIL_ACTIVATE_MESSAGE = URL_ACCOUNT_SAFE_API_BASE + "/user/sendEmailActivateMessageAuth";
        URL_IDENTITY_AUTH_FOR_SET_SECURITY_QUESTIONS = URL_ACCOUNT_SAFE_API_BASE + "/user/setSafeQuestionsAuth";
        URL_IDENTITY_AUTH_FOR_MODIFY_SAFE_PHONE = URL_ACCOUNT_SAFE_API_BASE + "/user/modifySafePhoneAuth";
        URL_IDENTITY_AUTH_FOR_CHANGE_PWD = URL_ACCOUNT_SAFE_API_BASE + "/user/native/changePasswordAuth";
        URL_CHECK_PHONE_ACTIVATE_STATUS = URL_ACCOUNT_SAFE_API_BASE + "/user/checkPhoneActivateStatus";
        URL_GET_BIND_EMAIL_CAPTCODE = URL_ACCOUNT_BASE + "/getCode?icodeType=antispam";
        URL_CHANGE_PASSWORD = URL_ACCOUNT_SAFE_API_BASE + "/user/changePassword";
        URL_SET_USER_REGION = URL_ACCOUNT_SAFE_API_BASE + "/user/region";
        URL_SET_USER_LOCATION = URL_ACCOUNT_SAFE_API_BASE + "/user/setLocation";
        URL_SET_USER_EDUCATION = URL_ACCOUNT_SAFE_API_BASE + "/user/setEducation";
        URL_SET_USER_INCOME = URL_ACCOUNT_SAFE_API_BASE + "/user/setIncome";
        URL_GENERATE_RANDOM_PASSWORD = ACCOUNT_DOMAIN + "/appConf/randomPwd";
        URL_EMAIL_REGISTER = URL_ACCOUNT_BASE + "/register";
        caUrlMap = new HashMap();
        caUrlMap.put(URL_LOGIN_HTTPS, URL_LOGIN_PASSPORT_CA);
        caUrlMap.put(URL_LOGIN_AUTH2_HTTPS, URL_LOGIN_AUTH2_PASSPORT_CA);
    }

    public static String getCaUrl(String str) {
        return caUrlMap.get(str);
    }
}
