package com.xiaomi.passport.utils;

import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.data.OnlinePreference;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PassportOnlinePreference {
    private static final String TAG = "PassportOnlinePreference";
    static final String URL_GET_LOGIN_PREFERENCE = URLs.ACCOUNT_DOMAIN + "/pass/preference";

    public static OnlinePreference getOnlineConfig() throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(XMPassportUtil.buildUrlWithLocaleQueryParam(URL_GET_LOGIN_PREFERENCE), null, null, true);
        if (asString != null) {
            String removeSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(asString);
            try {
                return OnlinePreference.parse(new JSONObject(removeSafePrefixAndGetRealBody));
            } catch (JSONException e) {
                AccountLog.e(TAG, "realBody", e);
                throw new InvalidResponseException(removeSafePrefixAndGetRealBody);
            }
        } else {
            throw new InvalidResponseException("result content is null");
        }
    }
}
