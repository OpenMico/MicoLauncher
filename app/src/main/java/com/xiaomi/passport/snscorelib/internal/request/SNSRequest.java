package com.xiaomi.passport.snscorelib.internal.request;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.snscorelib.internal.entity.SNSLoginParameter;
import com.xiaomi.passport.snscorelib.internal.entity.SNSTokenLoginResult;
import com.xiaomi.passport.snscorelib.internal.exception.SNSLoginException;
import com.xiaomi.passport.snscorelib.internal.utils.CodeUtil;
import com.xiaomi.passport.ui.internal.SnsWebLoginBaseFragment;
import java.io.IOException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class SNSRequest {
    private static final int INVALID_RESPONSE_EXCEPTION = 3;
    private static final String TAG = "SNSRequest";
    private static final String URL_GET_SNS_ACCESS_TOKEN = URLs.URL_ACCOUNT_BASE + "/sns/login/load";
    private static final String URL_SNS_TOKEN_LOGIN = URLs.URL_ACCOUNT_BASE + "/sns/login/load/token";
    public static final String URL_UNBIND_SNS = URLs.URL_ACCOUNT_API_V2_BASE + "/safe/user/accessToken/full/delete";
    private static final String URL_BIND_SNS_BY_CODE = URLs.URL_ACCOUNT_BASE + "/sns/bind/bindSns";
    private static final String URL_BIND_SNS_BY_TOKENE = URLs.URL_ACCOUNT_BASE + "/sns/token/bind/try";

    /* loaded from: classes4.dex */
    public static class BindLimitException extends Exception {
    }

    public static AccountInfo snsLoginByCode(SNSLoginParameter sNSLoginParameter) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, SNSLoginException, NeedNotificationException, NeedLoginForBindException, BindLimitException, RedirectToWebLoginException {
        String sNSTokenLoginUrl = getSNSTokenLoginUrl(sNSLoginParameter);
        StringBuilder sb = new StringBuilder();
        sb.append(sNSTokenLoginUrl);
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        sb.append("_auto=" + String.valueOf(sNSLoginParameter.autoGenerateAccount));
        if (!TextUtils.isEmpty(sNSLoginParameter.phones)) {
            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            sb.append("_phones=" + String.valueOf(URLEncoder.encode(sNSLoginParameter.phones)));
        }
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        sb.append("_snsQuickLogin=" + String.valueOf(sNSLoginParameter.snsQuickLogin));
        String sb2 = sb.toString();
        String property = System.getProperty("http.agent");
        EasyMap easyMap = new EasyMap();
        return getAccountInfo(SimpleRequestForAccount.getAsString(sb2, null, easyMap.easyPut("User-Agent", property + " AndroidSnsSDK/1.0"), null, true).getBody());
    }

    public static AccountInfo snsLoginByAccessToken(SNSLoginParameter sNSLoginParameter) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, SNSLoginException, NeedNotificationException, NeedLoginForBindException, BindLimitException, RedirectToWebLoginException {
        String str = DataModel.CIRCULATEFAIL_NO_SUPPORT;
        if (!TextUtils.isEmpty(sNSLoginParameter.expires_in)) {
            str = sNSLoginParameter.expires_in;
        }
        EasyMap easyPutOpt = new EasyMap().easyPutOpt("enToken", sNSLoginParameter.enToken).easyPutOpt("token", sNSLoginParameter.token).easyPutOpt(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2, str).easyPutOpt("openId", sNSLoginParameter.openId);
        if (!TextUtils.isEmpty(sNSLoginParameter.phones)) {
            easyPutOpt.easyPutOpt("_phones", sNSLoginParameter.phones);
        }
        easyPutOpt.easyPut("_auto", String.valueOf(sNSLoginParameter.autoGenerateAccount)).easyPut("_snsQuickLogin", String.valueOf(sNSLoginParameter.snsQuickLogin)).easyPut("_json", "true");
        JSONObject jSONObject = new JSONObject();
        try {
            Log.i("liujunsns", "snsLoginByAccessToken..sid=" + sNSLoginParameter.sid);
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put("callback", URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            easyPutOpt.easyPutOpt(XiaomiOAuthConstants.EXTRA_STATE_2, CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            Log.i("liujunsns", "snsLoginByAccessToken..state=" + CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            String property = System.getProperty("http.agent");
            EasyMap easyMap = new EasyMap();
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_SNS_TOKEN_LOGIN, easyPutOpt, easyMap.easyPut("User-Agent", property + " AndroidSnsSDK/1.0"), null, true);
            if (asString != null) {
                return getAccountInfo(asString.getBody());
            }
            throw new SNSLoginException(3, "failed to snsLoginByAccessToken : stringContent is null");
        } catch (JSONException e) {
            e.printStackTrace();
            AccountLog.e(TAG, "snsLoginByAccessToken :invalid state params", e);
            throw new SNSLoginException(3, "snsLoginByAccessToken :invalid state params:" + e.toString());
        }
    }

    private static AccountInfo getAccountInfo(String str) throws NeedNotificationException, NeedLoginForBindException, SNSLoginException, BindLimitException, RedirectToWebLoginException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("code");
            String optString = jSONObject.optString("description");
            if (optInt == 0) {
                return getAccountInfo(new SNSTokenLoginResult.Builder().status(jSONObject.optInt("Status")).sid(jSONObject.optString("Sid")).webViewCallback(jSONObject.optString("WebViewCallback")).callback(jSONObject.optString("Callback")).notificationUrl(jSONObject.optString("NotificationUrl")).userId(jSONObject.optString(BaseConstants.EXTRA_USER_ID)).passToken(jSONObject.optString(BaseConstants.EXTRA_PASSTOKEN)).snsBindTryUrl(jSONObject.optString("snsBindTryUrl")).snsTokenPh(jSONObject.optString(SnsWebLoginBaseFragment.SNS_TOKEN_PH)).openId(jSONObject.optString("openId")).snsLoginUrl(jSONObject.optString("snsLoginUrl")).bindLimit(jSONObject.optBoolean("bindLimit")).build());
            }
            AccountLog.w(TAG, "getAccountInfo :code=" + optInt + ";message = " + optString);
            throw new SNSLoginException(optInt, optString);
        } catch (SNSLoginException e) {
            throw new SNSLoginException(e.getCode(), e.getMessage());
        } catch (JSONException e2) {
            AccountLog.e(TAG, "getAccountInfo:fail to parse JSONObject " + str, e2);
            throw new SNSLoginException(3, "getAccountInfo:fail to parse JSONObject: " + str);
        }
    }

    public static SNSBindParameter getSNSBindParameterByCode(SNSLoginParameter sNSLoginParameter, AccountInfo accountInfo) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, SNSLoginException {
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_PASSTOKEN, accountInfo.passToken).easyPut(BaseConstants.EXTRA_USER_ID, accountInfo.userId);
        EasyMap easyPut2 = new EasyMap().easyPutOpt("code", sNSLoginParameter.code).easyPut("_json", "true").easyPut(BaseConstants.EXTRA_USER_ID, accountInfo.userId);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put("callback", URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            easyPut2.easyPutOpt(XiaomiOAuthConstants.EXTRA_STATE_2, CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            String property = System.getProperty("http.agent");
            EasyMap easyMap = new EasyMap();
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_BIND_SNS_BY_CODE, easyPut2, easyMap.easyPut("User-Agent", property + " AndroidSnsSDK/1.0"), easyPut, true);
            if (asString != null) {
                return getSNSBindParameter(asString.getBody());
            }
            throw new SNSLoginException(3, "failed to getSNSBindParameterByCode : stringContent is null");
        } catch (JSONException e) {
            e.printStackTrace();
            AccountLog.e(TAG, "getSNSBindParameterByCode :invalid state params", e);
            throw new SNSLoginException(3, "getSNSBindParameterByCode:invalid state params:" + e.toString());
        }
    }

    public static SNSBindParameter getSNSBindParameterByToken(SNSLoginParameter sNSLoginParameter, AccountInfo accountInfo) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, SNSLoginException {
        String str = DataModel.CIRCULATEFAIL_NO_SUPPORT;
        if (!TextUtils.isEmpty(sNSLoginParameter.expires_in)) {
            str = sNSLoginParameter.expires_in;
        }
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_PASSTOKEN, accountInfo.passToken).easyPut(BaseConstants.EXTRA_USER_ID, accountInfo.userId);
        EasyMap easyPut2 = new EasyMap().easyPutOpt("enToken", sNSLoginParameter.enToken).easyPutOpt("token", sNSLoginParameter.token).easyPutOpt(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2, str).easyPutOpt("openId", sNSLoginParameter.openId).easyPutOpt(BaseConstants.EXTRA_USER_ID, accountInfo.userId).easyPut("_json", "true");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put("callback", URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            easyPut2.easyPutOpt(XiaomiOAuthConstants.EXTRA_STATE_2, CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            String property = System.getProperty("http.agent");
            EasyMap easyMap = new EasyMap();
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_BIND_SNS_BY_TOKENE, easyPut2, easyMap.easyPut("User-Agent", property + " AndroidSnsSDK/1.0"), easyPut, true);
            if (asString != null) {
                return getSNSBindParameter(asString.getBody());
            }
            throw new SNSLoginException(3, "failed to getSNSBindParameterByToken : stringContent is null");
        } catch (JSONException e) {
            e.printStackTrace();
            AccountLog.e(TAG, "getSNSBindParameterByToken :invalid state params", e);
            throw new SNSLoginException(3, "getSNSBindParameterByToken :invalid state params:" + e.toString());
        }
    }

    private static SNSBindParameter getSNSBindParameter(String str) throws SNSLoginException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("code");
            String optString = jSONObject.optString("description");
            if (optInt == 0) {
                return new SNSBindParameter.Builder().snsBindUrl(jSONObject.optString("snsBindTryUrl")).sns_token_ph(jSONObject.optString(SnsWebLoginBaseFragment.SNS_TOKEN_PH)).sns_weixin_openId(jSONObject.optString("openId")).build();
            }
            AccountLog.w(TAG, "getSNSBindParameter :code=" + optInt + ";message = " + optString);
            throw new SNSLoginException(optInt, optString);
        } catch (SNSLoginException e) {
            throw new SNSLoginException(e.getCode(), e.getMessage());
        } catch (JSONException e2) {
            AccountLog.e(TAG, "getSNSBindParameter: fail to parse JSONObject " + str, e2);
            throw new SNSLoginException(3, "getSNSBindParameter: fail to parse JSONObject:" + e2.toString());
        }
    }

    private static AccountInfo getAccountInfo(SNSTokenLoginResult sNSTokenLoginResult) throws NeedNotificationException, NeedLoginForBindException, BindLimitException, RedirectToWebLoginException {
        int i = sNSTokenLoginResult.status;
        switch (i) {
            case 0:
                String str = sNSTokenLoginResult.notificationUrl;
                String str2 = sNSTokenLoginResult.sid;
                if (TextUtils.isEmpty(str)) {
                    return new AccountInfo.Builder().userId(sNSTokenLoginResult.userId).passToken(sNSTokenLoginResult.passToken).build();
                }
                throw new NeedNotificationException(str2, str);
            case 1:
                String str3 = sNSTokenLoginResult.snsLoginUrl;
                boolean z = sNSTokenLoginResult.bindLimit;
                String str4 = sNSTokenLoginResult.snsBindTryUrl;
                String str5 = sNSTokenLoginResult.snsTokenPh;
                String str6 = sNSTokenLoginResult.openId;
                String str7 = sNSTokenLoginResult.sid;
                if (z) {
                    throw new BindLimitException();
                } else if (!TextUtils.isEmpty(str3)) {
                    throw new RedirectToWebLoginException(new SNSBindParameter.Builder().snsBindUrl(str3).sns_token_ph(str5).sns_weixin_openId(str6).snsSid(str7).build());
                } else {
                    throw new NeedLoginForBindException(new SNSBindParameter.Builder().snsBindUrl(str4).sns_token_ph(str5).sns_weixin_openId(str6).snsSid(str7).build());
                }
            default:
                throw new IllegalStateException("unknown error:status=" + i);
        }
    }

    private static String getSNSTokenLoginUrl(SNSLoginParameter sNSLoginParameter) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException, SNSLoginException {
        EasyMap easyPut = new EasyMap().easyPutOpt("code", sNSLoginParameter.code).easyPut("_json", "true");
        JSONObject jSONObject = new JSONObject();
        try {
            Log.i("liujunsns", "getSNSTokenLoginUrl..sid=" + sNSLoginParameter.sid);
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put("callback", URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            easyPut.easyPutOpt(XiaomiOAuthConstants.EXTRA_STATE_2, CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            Log.i("liujunsns", "getSNSTokenLoginUrl..state=" + CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            String property = System.getProperty("http.agent");
            EasyMap easyMap = new EasyMap();
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_GET_SNS_ACCESS_TOKEN, easyPut, easyMap.easyPut("User-Agent", property + " AndroidSnsSDK/1.0"), null, true);
            if (asString != null) {
                try {
                    JSONObject jSONObject2 = new JSONObject(asString.getBody());
                    int optInt = jSONObject2.optInt("code");
                    String optString = jSONObject2.optString("description");
                    if (optInt == 0) {
                        return jSONObject2.getJSONObject("data").optString("location");
                    }
                    AccountLog.w(TAG, "getSNSTokenLoginUrl :code=" + optInt + ";message = " + optString);
                    throw new SNSLoginException(optInt, optString);
                } catch (SNSLoginException e) {
                    throw new SNSLoginException(e.getCode(), e.getMessage());
                } catch (JSONException e2) {
                    AccountLog.e(TAG, "getSNSTokenLoginUrl: fail to parse JSONObject " + asString.toString(), e2);
                    throw new SNSLoginException(3, "getSNSTokenLoginUrl: fail to parse JSONObject:" + e2.toString());
                }
            } else {
                throw new SNSLoginException(3, "failed to getSNSTokenLoginUrl : stringContent is null");
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
            AccountLog.e(TAG, "getSNSTokenLoginUrl :invalid state params", e3);
            throw new SNSLoginException(3, "getSNSTokenLoginUrl:invalid state params:" + e3.toString());
        }
    }

    /* loaded from: classes4.dex */
    public static class NeedLoginForBindException extends Exception {
        private final SNSBindParameter mSNSBindParameter;

        public NeedLoginForBindException(SNSBindParameter sNSBindParameter) {
            this.mSNSBindParameter = sNSBindParameter;
        }

        public SNSBindParameter getSNSBindParameter() {
            return this.mSNSBindParameter;
        }
    }

    /* loaded from: classes4.dex */
    public static class RedirectToWebLoginException extends Exception {
        private final SNSBindParameter snsBindParameter;

        public RedirectToWebLoginException(SNSBindParameter sNSBindParameter) {
            this.snsBindParameter = sNSBindParameter;
        }

        public SNSBindParameter getSNSBindParameter() {
            return this.snsBindParameter;
        }
    }
}
