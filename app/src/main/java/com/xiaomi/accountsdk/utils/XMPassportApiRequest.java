package com.xiaomi.accountsdk.utils;

import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import java.io.IOException;
import org.json.JSONException;

/* loaded from: classes2.dex */
public abstract class XMPassportApiRequest {

    /* loaded from: classes2.dex */
    protected enum WebMethod {
        POST,
        GET
    }

    protected abstract void addParams(EasyMap<String, String> easyMap);

    protected abstract boolean allowRetryAuthToken();

    protected abstract String getAuthToken();

    protected abstract String getCUserId();

    protected abstract String getUrl();

    protected abstract String getUserId();

    protected abstract WebMethod getWebMethod();

    protected abstract String handleDataResult(Object obj) throws JSONException;

    protected abstract void invalidateAuthToken(String str);

    public String execute() throws AccessDeniedException, InvalidResponseException, CipherException, IOException, JSONException {
        String authToken;
        ExtendedAuthToken parse;
        SimpleRequest.MapContent mapContent;
        for (int i = 0; i < 2 && (parse = ExtendedAuthToken.parse((authToken = getAuthToken()))) != null; i++) {
            String str = parse.authToken;
            String str2 = parse.security;
            if (str == null || str2 == null) {
                return null;
            }
            EasyMap<String, String> easyMap = new EasyMap<>();
            addParams(easyMap);
            EasyMap easyMap2 = new EasyMap();
            easyMap2.easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, str);
            String cUserId = getCUserId();
            if (cUserId != null) {
                easyMap2.easyPut("cUserId", cUserId);
            } else {
                easyMap2.easyPut(BaseConstants.EXTRA_USER_ID, getUserId());
            }
            try {
                if (getWebMethod() == WebMethod.GET) {
                    mapContent = SecureRequestForAccount.getAsMap(getUrl(), easyMap, easyMap2, true, str2, new AESCoder(str2));
                } else {
                    mapContent = SecureRequestForAccount.postAsMap(getUrl(), easyMap, easyMap2, true, str2, new AESCoder(str2));
                }
                if (mapContent == null) {
                    return null;
                }
                return handleDataResult(mapContent.getFromBody("data"));
            } catch (AuthenticationFailureException unused) {
                if (!allowRetryAuthToken()) {
                    return null;
                }
                invalidateAuthToken(authToken);
            }
        }
        return null;
    }
}
