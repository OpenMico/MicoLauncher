package org.eclipse.jetty.util.ssl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;

/* loaded from: classes5.dex */
public class AliasedX509ExtendedKeyManager extends X509ExtendedKeyManager {
    private String _keyAlias;
    private X509KeyManager _keyManager;

    public AliasedX509ExtendedKeyManager(String str, X509KeyManager x509KeyManager) throws Exception {
        this._keyAlias = str;
        this._keyManager = x509KeyManager;
    }

    @Override // javax.net.ssl.X509KeyManager
    public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
        String str = this._keyAlias;
        return str == null ? this._keyManager.chooseClientAlias(strArr, principalArr, socket) : str;
    }

    @Override // javax.net.ssl.X509KeyManager
    public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
        String str2 = this._keyAlias;
        return str2 == null ? this._keyManager.chooseServerAlias(str, principalArr, socket) : str2;
    }

    @Override // javax.net.ssl.X509KeyManager
    public String[] getClientAliases(String str, Principal[] principalArr) {
        return this._keyManager.getClientAliases(str, principalArr);
    }

    @Override // javax.net.ssl.X509KeyManager
    public String[] getServerAliases(String str, Principal[] principalArr) {
        return this._keyManager.getServerAliases(str, principalArr);
    }

    @Override // javax.net.ssl.X509KeyManager
    public X509Certificate[] getCertificateChain(String str) {
        return this._keyManager.getCertificateChain(str);
    }

    @Override // javax.net.ssl.X509KeyManager
    public PrivateKey getPrivateKey(String str) {
        return this._keyManager.getPrivateKey(str);
    }

    @Override // javax.net.ssl.X509ExtendedKeyManager
    public String chooseEngineServerAlias(String str, Principal[] principalArr, SSLEngine sSLEngine) {
        String str2 = this._keyAlias;
        return str2 == null ? super.chooseEngineServerAlias(str, principalArr, sSLEngine) : str2;
    }

    @Override // javax.net.ssl.X509ExtendedKeyManager
    public String chooseEngineClientAlias(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        String str = this._keyAlias;
        return str == null ? super.chooseEngineClientAlias(strArr, principalArr, sSLEngine) : str;
    }
}
