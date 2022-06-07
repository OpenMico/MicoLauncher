package com.xiaomi.accountsdk.request;

import android.text.TextUtils;
import com.xiaomi.account.data.PassportCAToken;
import com.xiaomi.account.exception.PassportCAException;
import com.xiaomi.accountsdk.account.PassportCATokenManager;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AESWithIVCoder;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.accountsdk.utils.CryptCoder;
import com.xiaomi.accountsdk.utils.EasyMap;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class PassportCARequest extends PassportRequest {
    private static final String KEY_PASSPORT_CA_TOKEN = "passport_ca_token";
    private static final String TAG = "com.xiaomi.accountsdk.request.PassportCARequest";
    private final PassportCATokenManager caTokenManager;
    private boolean handled401Already = false;
    private final PassportSimpleRequest originalRequest;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface EncryptRule {
        boolean shouldEncrypt(String str);
    }

    public PassportCARequest(PassportSimpleRequest passportSimpleRequest, PassportCATokenManager passportCATokenManager) {
        this.originalRequest = passportSimpleRequest;
        this.caTokenManager = passportCATokenManager;
    }

    @Override // com.xiaomi.accountsdk.request.PassportRequest
    public SimpleRequest.StringContent execute() throws IOException, PassportRequestException {
        if (getCaTokenManager() == null || !getCaTokenManager().isReady()) {
            throw new PassportRequestException(new PassportCAException("null CA Manager"));
        }
        try {
            return executeInternal();
        } catch (PassportCAException e) {
            throw new PassportRequestException(e);
        } catch (AccessDeniedException e2) {
            throw new PassportRequestException(e2);
        } catch (AuthenticationFailureException e3) {
            if (!this.handled401Already) {
                this.handled401Already = true;
                return handle401Error(e3);
            }
            throw new PassportRequestException(e3);
        } catch (InvalidResponseException e4) {
            throw new PassportRequestException(e4);
        } catch (PassportRequestException e5) {
            if (!(e5.getCause() instanceof AuthenticationFailureException) || this.handled401Already) {
                throw e5;
            }
            this.handled401Already = true;
            return handle401Error((AuthenticationFailureException) e5.getCause());
        }
    }

    PassportCATokenManager getCaTokenManager() {
        return this.caTokenManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ParamEncryptRule implements EncryptRule {
        private ParamEncryptRule() {
        }

        @Override // com.xiaomi.accountsdk.request.PassportCARequest.EncryptRule
        public boolean shouldEncrypt(String str) {
            return str != null && !str.startsWith("_");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class CookiesEncryptRule implements EncryptRule {
        private CookiesEncryptRule() {
        }

        @Override // com.xiaomi.accountsdk.request.PassportCARequest.EncryptRule
        public boolean shouldEncrypt(String str) {
            return str != null && !str.startsWith("__") && !str.equals(PassportCARequest.KEY_PASSPORT_CA_TOKEN);
        }
    }

    SimpleRequest.StringContent executeInternal() throws PassportCAException, IOException, InvalidResponseException, AccessDeniedException, AuthenticationFailureException, PassportRequestException {
        if (!this.originalRequest.isHTTPS()) {
            PassportSimpleRequest copy = this.originalRequest.copy();
            PassportRequestArguments passportRequestArguments = copy.arguments;
            PassportCAToken passportCAToken = getPassportCAToken(passportRequestArguments.url, getCaTokenManager());
            if (passportCAToken == null || !passportCAToken.isLegal()) {
                throw new PassportCAException("null CA token");
            }
            passportRequestArguments.params.put("_nonce", CloudCoder.generateNonce());
            passportRequestArguments.cookies.put(KEY_PASSPORT_CA_TOKEN, passportCAToken.token);
            passportRequestArguments.headers.put("caTag", "noSafe");
            AESWithIVCoder aESWithIVCoder = new AESWithIVCoder(passportCAToken.security);
            encrypt(passportRequestArguments.params, aESWithIVCoder, new ParamEncryptRule());
            encrypt(passportRequestArguments.cookies, aESWithIVCoder, new CookiesEncryptRule());
            passportRequestArguments.params.put("_caSign", CloudCoder.generateSignature(copy.getMethod(), passportRequestArguments.url, passportRequestArguments.params, passportCAToken.security));
            SimpleRequest.StringContent execute = new RequestWithIP(copy, new IPStrategy(), new CALoginStatHelper()).execute();
            if (execute != null) {
                return parseResponse(aESWithIVCoder, execute);
            }
            throw new IOException("no response from server");
        }
        throw new IllegalStateException("https request should not use PassportCA");
    }

    private static SimpleRequest.StringContent parseResponse(CryptCoder cryptCoder, SimpleRequest.StringContent stringContent) throws InvalidResponseException {
        String body = stringContent.getBody();
        boolean z = true;
        boolean z2 = stringContent.getHttpCode() == 302;
        if (!TextUtils.isEmpty(body)) {
            try {
                SimpleRequest.StringContent stringContent2 = new SimpleRequest.StringContent(cryptCoder.decrypt(body));
                stringContent2.setHttpCode(stringContent.getHttpCode());
                Map<String, String> headers = stringContent.getHeaders();
                for (String str : stringContent.getCookieKeys()) {
                    try {
                        headers.put(str, cryptCoder.decrypt(stringContent.getHeader(str)));
                    } catch (CipherException unused) {
                    }
                }
                stringContent2.putHeaders(headers);
                return stringContent2;
            } catch (CipherException e) {
                boolean isHtml = isHtml(body);
                if (!z2 && !isHtml) {
                    z = false;
                }
                throw new InvalidResponseException("failed to decrypt response", e, z);
            }
        } else {
            throw new InvalidResponseException("invalid response from server", null, z2);
        }
    }

    static boolean isHtml(String str) {
        return str != null && (str.contains("html") || str.contains("http"));
    }

    private static PassportCAToken getPassportCAToken(String str, PassportCATokenManager passportCATokenManager) throws PassportCAException, AuthenticationFailureException {
        try {
            return passportCATokenManager.getPassportCAToken(str);
        } catch (AccessDeniedException e) {
            throw new PassportCAException(e);
        } catch (CipherException e2) {
            throw new PassportCAException(e2);
        } catch (InvalidResponseException e3) {
            throw new PassportCAException(e3);
        } catch (IOException e4) {
            throw new PassportCAException(e4);
        } catch (InvalidKeyException e5) {
            throw new PassportCAException(e5);
        } catch (NoSuchAlgorithmException e6) {
            throw new PassportCAException(e6);
        } catch (CertificateException e7) {
            throw new PassportCAException(e7);
        } catch (BadPaddingException e8) {
            throw new PassportCAException(e8);
        } catch (IllegalBlockSizeException e9) {
            throw new PassportCAException(e9);
        } catch (NoSuchPaddingException e10) {
            throw new PassportCAException(e10);
        } catch (JSONException e11) {
            throw new PassportCAException(e11);
        }
    }

    private static void encrypt(EasyMap<String, String> easyMap, CryptCoder cryptCoder, EncryptRule encryptRule) throws PassportCAException {
        try {
            for (Map.Entry<String, String> entry : easyMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!(key == null || value == null || !encryptRule.shouldEncrypt(key))) {
                    easyMap.put(key, cryptCoder.encrypt(value));
                }
            }
        } catch (CipherException e) {
            throw new PassportCAException(e);
        }
    }

    SimpleRequest.StringContent handle401Error(AuthenticationFailureException authenticationFailureException) throws IOException, PassportRequestException {
        Long l;
        String wwwAuthenticateHeader = authenticationFailureException.getWwwAuthenticateHeader();
        if ("passportCA".equals(wwwAuthenticateHeader)) {
            getCaTokenManager().invalidateAllCAToken();
            return execute();
        } else if ("passportCA-Disabled".equals(wwwAuthenticateHeader)) {
            try {
                l = Long.valueOf(authenticationFailureException.getCaDisableSecondsHeader());
            } catch (NumberFormatException e) {
                AccountLog.w(TAG, e);
                l = null;
            }
            getCaTokenManager().onCADisabledForSeconds(l);
            throw new PassportRequestException(new PassportCAException("PassportCA Disabled"));
        } else {
            throw new PassportRequestException(authenticationFailureException);
        }
    }
}
