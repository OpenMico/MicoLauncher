package com.xiaomi.passport.ui.settings;

import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.ui.settings.GetUserBindIdAndLimitTask;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class CloudHelper {
    private static final int RESULT_CODE_SUCCESS = 0;
    private static final String TAG = "CloudHelper";
    private static final String URL_MODIFY_SAFE_PHONE = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/modifySafePhone";
    private static final String URL_SEND_MODIFY_SAFE_PHONE_TICKET = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/sendModifySafePhoneTicket";
    private static final String URL_USER_BIND_ID_AND_LIMIT = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/getUserBindIdAndLimit";
    private static final String URL_CHANGE_PWD = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/native/changePassword";

    CloudHelper() {
    }

    public static void modifySafePhone(PassportInfo passportInfo, String str, String str2, MiuiActivatorInfo miuiActivatorInfo, boolean z, String str3, String str4) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, InvalidVerifyCodeException, UserRestrictedException, InvalidPhoneNumException, NeedVerificationException {
        if (passportInfo == null) {
            AccountLog.i(TAG, "passport info should be not null");
            return;
        }
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("phone", str).easyPutOpt("ticket", str2).easyPutOpt("sid", str4).easyPut("replace", String.valueOf(z)).easyPut("authST", str3).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        if (miuiActivatorInfo != null) {
            easyPut.easyPutOpt("simId", miuiActivatorInfo.simId).easyPutOpt("vKey2", miuiActivatorInfo.vKey2).easyPutOpt("nonce", miuiActivatorInfo.vKey2Nonce);
        }
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URL_MODIFY_SAFE_PHONE, easyPut, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (postAsMap != null) {
            Object fromBody = postAsMap.getFromBody("code");
            Object fromBody2 = postAsMap.getFromBody("description");
            ServerError serverError = new ServerError(postAsMap);
            String str5 = "code: " + fromBody + "; description: " + fromBody2;
            AccountLog.d(TAG, "modifySafePhone: " + str5);
            if (fromBody instanceof Integer) {
                int intValue = ((Integer) fromBody).intValue();
                if (intValue == 0) {
                    return;
                }
                if (intValue == 20023) {
                    throw new UserRestrictedException();
                } else if (intValue == 70008) {
                    throw new InvalidPhoneNumException(str5);
                } else if (intValue == 70012) {
                    throw new NeedVerificationException(null);
                } else if (intValue == 70014) {
                    throw new InvalidVerifyCodeException(str5);
                }
            }
            throw new InvalidResponseException(serverError);
        }
        throw new InvalidResponseException("failed to modifySafePhone");
    }

    public static String changePassword(PassportInfo passportInfo, String str, String str2, String str3, String str4) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, InvalidCredentialException, InvalidParameterException {
        if (passportInfo != null) {
            SimpleRequest.StringContent postAsString = SecureRequestForAccount.postAsString(URL_CHANGE_PWD, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("pwd", str2).easyPut(BaseConstants.EXTRA_PASSTOKEN, str).easyPutOpt("sid", str4).easyPut("authST", str3).easyPut("traceId", UUID.randomUUID().toString().substring(0, 15)).easyPut("_json", String.valueOf(true)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (postAsString != null) {
                String removeSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(postAsString);
                try {
                    JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody);
                    int i = jSONObject.getInt("code");
                    String str5 = "code: " + i + ", desc: " + jSONObject.optString("description");
                    ServerError serverError = new ServerError(jSONObject);
                    AccountLog.d(TAG, "changePassword: " + str5);
                    if (i == 0) {
                        return jSONObject.getJSONObject("data").getString(BaseConstants.EXTRA_PASSTOKEN);
                    }
                    if (i == 21317) {
                        throw new InvalidCredentialException(i, str5, false);
                    } else if (i == 70003) {
                        throw new InvalidParameterException(i, str5);
                    } else if (i != 85110) {
                        throw new InvalidResponseException(serverError);
                    } else {
                        throw new InvalidParameterException(i, str5);
                    }
                } catch (JSONException unused) {
                    throw new InvalidResponseException("result not json: " + removeSafePrefixAndGetRealBody);
                }
            } else {
                throw new InvalidResponseException("result content is null");
            }
        } else {
            AccountLog.i(TAG, "passport info should be not null");
            throw new AuthenticationFailureException("passport info is null");
        }
    }

    public static void sendModifySafePhoneTicket(PassportInfo passportInfo, String str, String str2, String str3, String str4) throws AccessDeniedException, AuthenticationFailureException, InvalidResponseException, CipherException, IOException, ReachLimitException, NeedCaptchaException, InvalidPhoneNumException {
        if (passportInfo == null) {
            AccountLog.i(TAG, "passport info should be not null");
            return;
        }
        EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("phone", str).easyPutOpt("sid", str4).easyPutOpt("icode", str2).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        easyPut.putAll(XMPassportUtil.getDefaultLocaleParam());
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        passportCookie.easyPutOpt("ick", str3);
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URL_SEND_MODIFY_SAFE_PHONE_TICKET, easyPut, passportCookie, true, passportInfo.getSecurity());
        if (postAsMap != null) {
            int intValue = ((Integer) postAsMap.getFromBody("code")).intValue();
            String str5 = (String) postAsMap.getFromBody("description");
            String str6 = "code: " + intValue + "; description: " + str5;
            ServerError serverError = new ServerError(postAsMap);
            AccountLog.d(TAG, "send modify ticket: " + str6);
            if (intValue != 0) {
                if (intValue != 20031) {
                    if (intValue == 70008) {
                        throw new InvalidPhoneNumException(str6);
                    } else if (intValue == 70022) {
                        throw new ReachLimitException(str6);
                    } else if (intValue != 87001) {
                        throw new InvalidResponseException(serverError);
                    }
                }
                throw new NeedCaptchaException(intValue, str5, URLs.ACCOUNT_DOMAIN + ((String) postAsMap.getFromBody("info")));
            }
            return;
        }
        throw new InvalidResponseException("failed to send ticket");
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo != null) {
            return new EasyMap().easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, passportInfo.getServiceToken()).easyPut("cUserId", passportInfo.getEncryptedUserId());
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    public static GetUserBindIdAndLimitTask.UserBindIdAndLimitResult getUserBindIdAndLimit(PassportInfo passportInfo, String str) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidPhoneNumException {
        return getUserBindIdAndLimit(passportInfo, str, URL_USER_BIND_ID_AND_LIMIT);
    }

    private static GetUserBindIdAndLimitTask.UserBindIdAndLimitResult getUserBindIdAndLimit(PassportInfo passportInfo, String str, String str2) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidPhoneNumException {
        if (passportInfo != null) {
            SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(str2, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("type", "PH").easyPut("externalId", str), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (asMap != null) {
                int intValue = ((Integer) asMap.getFromBody("code")).intValue();
                String str3 = (String) asMap.getFromBody("description");
                ServerError serverError = new ServerError(asMap);
                if (intValue == 0) {
                    Object fromBody = asMap.getFromBody("data");
                    if (fromBody instanceof Map) {
                        Map map = (Map) fromBody;
                        int i = 0;
                        String str4 = "";
                        long j = 0;
                        try {
                            if (map.containsKey("times")) {
                                i = Integer.parseInt(String.valueOf(map.get("times")));
                            }
                            if (map.containsKey(BaseConstants.EXTRA_USER_ID)) {
                                str4 = map.get(BaseConstants.EXTRA_USER_ID).toString();
                            }
                            if (map.containsKey("ts")) {
                                j = Long.parseLong(String.valueOf(map.get("ts")));
                            }
                            return new GetUserBindIdAndLimitTask.UserBindIdAndLimitResult(str4, j, i);
                        } catch (Exception unused) {
                            return null;
                        }
                    }
                } else if (intValue != 70008) {
                    throw new InvalidResponseException(serverError);
                }
                throw new InvalidPhoneNumException(str3);
            }
            throw new IOException("failed to getUserBindIdAndLimit");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }
}
