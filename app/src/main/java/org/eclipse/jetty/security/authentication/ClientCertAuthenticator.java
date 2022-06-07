package org.eclipse.jetty.security.authentication;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.security.CertificateUtils;
import org.eclipse.jetty.util.security.CertificateValidator;
import org.eclipse.jetty.util.security.Password;

/* loaded from: classes5.dex */
public class ClientCertAuthenticator extends LoginAuthenticator {
    private static final String PASSWORD_PROPERTY = "org.eclipse.jetty.ssl.password";
    private String _crlPath;
    private String _ocspResponderURL;
    private transient Password _trustStorePassword;
    private String _trustStorePath;
    private String _trustStoreProvider;
    private boolean _validateCerts;
    private String _trustStoreType = "JKS";
    private int _maxCertPathLength = -1;
    private boolean _enableCRLDP = false;
    private boolean _enableOCSP = false;

    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return "CLIENT_CERT";
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z) throws ServerAuthException {
        if (!z) {
            return new DeferredAuthentication(this);
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        X509Certificate[] x509CertificateArr = (X509Certificate[]) ((HttpServletRequest) servletRequest).getAttribute("javax.servlet.request.X509Certificate");
        if (x509CertificateArr != null) {
            try {
                if (x509CertificateArr.length > 0) {
                    if (this._validateCerts) {
                        new CertificateValidator(getKeyStore(null, this._trustStorePath, this._trustStoreType, this._trustStoreProvider, this._trustStorePassword == null ? null : this._trustStorePassword.toString()), loadCRL(this._crlPath)).validate(x509CertificateArr);
                    }
                    for (X509Certificate x509Certificate : x509CertificateArr) {
                        if (x509Certificate != null) {
                            Principal subjectDN = x509Certificate.getSubjectDN();
                            if (subjectDN == null) {
                                subjectDN = x509Certificate.getIssuerDN();
                            }
                            UserIdentity login = login(subjectDN == null ? "clientcert" : subjectDN.getName(), B64Code.encode(x509Certificate.getSignature()), servletRequest);
                            if (login != null) {
                                return new UserAuthentication(getAuthMethod(), login);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new ServerAuthException(e.getMessage());
            }
        }
        if (DeferredAuthentication.isDeferred(httpServletResponse)) {
            return Authentication.UNAUTHENTICATED;
        }
        httpServletResponse.sendError(403);
        return Authentication.SEND_FAILURE;
    }

    protected KeyStore getKeyStore(InputStream inputStream, String str, String str2, String str3, String str4) throws Exception {
        return CertificateUtils.getKeyStore(inputStream, str, str2, str3, str4);
    }

    protected Collection<? extends CRL> loadCRL(String str) throws Exception {
        return CertificateUtils.loadCRL(str);
    }

    public boolean isValidateCerts() {
        return this._validateCerts;
    }

    public void setValidateCerts(boolean z) {
        this._validateCerts = z;
    }

    public String getTrustStore() {
        return this._trustStorePath;
    }

    public void setTrustStore(String str) {
        this._trustStorePath = str;
    }

    public String getTrustStoreProvider() {
        return this._trustStoreProvider;
    }

    public void setTrustStoreProvider(String str) {
        this._trustStoreProvider = str;
    }

    public String getTrustStoreType() {
        return this._trustStoreType;
    }

    public void setTrustStoreType(String str) {
        this._trustStoreType = str;
    }

    public void setTrustStorePassword(String str) {
        this._trustStorePassword = Password.getPassword("org.eclipse.jetty.ssl.password", str, null);
    }

    public String getCrlPath() {
        return this._crlPath;
    }

    public void setCrlPath(String str) {
        this._crlPath = str;
    }

    public int getMaxCertPathLength() {
        return this._maxCertPathLength;
    }

    public void setMaxCertPathLength(int i) {
        this._maxCertPathLength = i;
    }

    public boolean isEnableCRLDP() {
        return this._enableCRLDP;
    }

    public void setEnableCRLDP(boolean z) {
        this._enableCRLDP = z;
    }

    public boolean isEnableOCSP() {
        return this._enableOCSP;
    }

    public void setEnableOCSP(boolean z) {
        this._enableOCSP = z;
    }

    public String getOcspResponderURL() {
        return this._ocspResponderURL;
    }

    public void setOcspResponderURL(String str) {
        this._ocspResponderURL = str;
    }
}
