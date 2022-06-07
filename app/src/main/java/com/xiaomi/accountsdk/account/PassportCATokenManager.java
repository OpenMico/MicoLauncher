package com.xiaomi.accountsdk.account;

import com.xiaomi.account.data.PassportCAToken;
import com.xiaomi.account.exception.PassportCAException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.IPStatHelper;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.PassportRequestArguments;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.EasyMap;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PassportCATokenManager {
    private static final long DEFAULT_CA_DISABLED_DURATION_IN_SECONDS = 86400;
    private static final long MAX_CA_DISABLED_DURATION_IN_SECONDS = 604800;
    private static final String TAG = "PassportCATokenManager";
    private static PassportCATokenManager sInstance = new PassportCATokenManager();
    private PassportCAExternal passportCAExternal = null;

    PassportCATokenManager() {
    }

    public boolean isReady() {
        if (this.passportCAExternal == null || XMPassport.USE_PREVIEW) {
            return false;
        }
        return System.currentTimeMillis() >= this.passportCAExternal.loadNextCAEnabledTime(0L);
    }

    public String getCaUrl(String str) {
        return URLs.getCaUrl(str);
    }

    public void onCADisabledForSeconds(Long l) {
        if (this.passportCAExternal != null) {
            if (l == null) {
                l = 86400L;
            } else if (l.longValue() > MAX_CA_DISABLED_DURATION_IN_SECONDS) {
                l = Long.valueOf((long) MAX_CA_DISABLED_DURATION_IN_SECONDS);
            }
            this.passportCAExternal.saveNextCAEnabledTime(System.currentTimeMillis() + (l.longValue() * 1000));
        }
    }

    protected PassportCAToken getCATokenOnline(String str) throws IOException, CertificateException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, AccessDeniedException, AuthenticationFailureException, JSONException, InvalidResponseException, CipherException, PassportCAException {
        String format = String.format("https://%s/ca/getTokenCA", str);
        EasyMap easyMap = new EasyMap();
        easyMap.easyPut(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, XMPassportSettings.getDeviceId());
        EasyMap easyMap2 = new EasyMap();
        easyMap2.put("_ver", PassportCAConstants.IMPL_VERSION);
        PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
        passportRequestArguments.setUrl(format);
        passportRequestArguments.putAllParams(easyMap2);
        passportRequestArguments.putAllCookies(easyMap);
        passportRequestArguments.setReadBody(true);
        try {
            JSONObject jSONObject = new JSONObject(XMPassport.removeSafePrefixAndGetRealBody(new PassportSimpleRequest.GetAsString(passportRequestArguments).executeEx()));
            int i = jSONObject.getInt("code");
            if (i == 0) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                return new PassportCAToken(jSONObject2.getString("passport_ca_token"), jSONObject2.getString("casecurity"));
            } else if (i == 10008) {
                throw new PassportCAException("when getting Token server returns code: " + i);
            } else {
                throw new InvalidResponseException("error result");
            }
        } catch (InvalidCredentialException e) {
            throw new IllegalStateException(e);
        } catch (InvalidUserNameException e2) {
            throw new IllegalStateException(e2);
        } catch (NeedCaptchaException e3) {
            throw new IllegalStateException(e3);
        } catch (NeedNotificationException e4) {
            throw new IllegalStateException(e4);
        } catch (NeedVerificationException e5) {
            throw new IllegalStateException(e5);
        }
    }

    @Deprecated
    public PassportCAToken getPassportCAToken(String str) throws IOException, CertificateException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, AccessDeniedException, AuthenticationFailureException, JSONException, InvalidResponseException, CipherException, PassportCAException {
        return getPassportCAToken();
    }

    public PassportCAToken getPassportCAToken() throws IOException, CertificateException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, AccessDeniedException, AuthenticationFailureException, JSONException, InvalidResponseException, CipherException, PassportCAException {
        PassportCAExternal passportCAExternal = this.passportCAExternal;
        if (passportCAExternal != null) {
            PassportCAToken loadCAToken = passportCAExternal.loadCAToken();
            if (loadCAToken == null) {
                String host = new URL(URLs.URL_PASSPORT_CA_ACCOUNT_BASE).getHost();
                getTokenStatHelper gettokenstathelper = new getTokenStatHelper();
                gettokenstathelper.onGetTokenOnlineStart();
                try {
                    loadCAToken = getCATokenOnline(host);
                    if (loadCAToken != null) {
                        this.passportCAExternal.saveCAToken(loadCAToken);
                    }
                } finally {
                    gettokenstathelper.onGetTokenOnlineEnd(loadCAToken);
                }
            }
            return loadCAToken;
        }
        throw new IllegalStateException("passportCATokenExternal is null");
    }

    public void invalidateAllCAToken() {
        PassportCAExternal passportCAExternal = this.passportCAExternal;
        if (passportCAExternal != null) {
            passportCAExternal.saveCAToken(PassportCAToken.INVALID_INSTANCE);
            return;
        }
        throw new IllegalStateException("passportCATokenExternal is null");
    }

    public void setPassportCAExternal(PassportCAExternal passportCAExternal) {
        if (passportCAExternal != null) {
            this.passportCAExternal = passportCAExternal;
            return;
        }
        throw new IllegalArgumentException("passportCAExternal should not be null");
    }

    public static PassportCATokenManager getInstance() {
        return sInstance;
    }

    /* loaded from: classes2.dex */
    public static class getTokenStatHelper extends IPStatHelper {
        private long startTime = 0;

        getTokenStatHelper() {
        }

        public void onGetTokenOnlineStart() {
            this.startTime = System.currentTimeMillis();
        }

        public void onGetTokenOnlineEnd(PassportCAToken passportCAToken) {
            statDummyUrl(buildDummyUrl(passportCAToken, Long.valueOf(System.currentTimeMillis() - this.startTime), Boolean.valueOf(passportCAToken != null)));
        }

        String buildDummyUrl(PassportCAToken passportCAToken, Object obj, Object obj2) {
            return String.format("http://dummyurl/getTokenDiagnosis?_ver=%s&_time=%s&_result=%s&hdid=%s", PassportCAConstants.IMPL_VERSION, obj, obj2, new HashedDeviceIdUtil(XMPassportSettings.getApplicationContext()).getHashedDeviceIdNoThrow());
        }
    }
}
