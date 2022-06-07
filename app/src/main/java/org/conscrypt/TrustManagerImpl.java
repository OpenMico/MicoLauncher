package org.conscrypt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import org.conscrypt.ct.CTPolicy;
import org.conscrypt.ct.CTVerifier;

/* loaded from: classes5.dex */
public final class TrustManagerImpl extends X509ExtendedTrustManager {
    private static ConscryptHostnameVerifier defaultHostnameVerifier;
    private final X509Certificate[] acceptedIssuers;
    private final CertBlacklist blacklist;
    private boolean ctEnabledOverride;
    private CTPolicy ctPolicy;
    private CTVerifier ctVerifier;
    private final Exception err;
    private final CertificateFactory factory;
    private ConscryptHostnameVerifier hostnameVerifier;
    private final TrustedCertificateIndex intermediateIndex;
    private CertPinManager pinManager;
    private final KeyStore rootKeyStore;
    private final TrustedCertificateIndex trustedCertificateIndex;
    private final ConscryptCertStore trustedCertificateStore;
    private final CertPathValidator validator;
    private static final Logger logger = Logger.getLogger(TrustManagerImpl.class.getName());
    private static final TrustAnchorComparator TRUST_ANCHOR_COMPARATOR = new TrustAnchorComparator();

    public TrustManagerImpl(KeyStore keyStore) {
        this(keyStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager) {
        this(keyStore, certPinManager, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager, ConscryptCertStore conscryptCertStore) {
        this(keyStore, certPinManager, conscryptCertStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager certPinManager, ConscryptCertStore conscryptCertStore, CertBlacklist certBlacklist) {
        this(keyStore, certPinManager, conscryptCertStore, certBlacklist, null, null, null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:2|(6:52|3|44|4|46|5)|(2:7|(10:(2:50|10)|42|11|12|20|(1:35)|(1:37)|(1:39)|40|41))|48|17|54|18|19|20|(0)|(0)|(0)|40|41|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(20:2|52|3|44|4|46|5|(2:7|(10:(2:50|10)|42|11|12|20|(1:35)|(1:37)|(1:39)|40|41))|48|17|54|18|19|20|(0)|(0)|(0)|40|41|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
        r6 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
        r3 = r6;
        r2 = r8;
        r8 = null;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0057, code lost:
        r6 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0058, code lost:
        r2 = r8;
        r8 = null;
        r3 = null;
        r1 = r1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TrustManagerImpl(java.security.KeyStore r6, org.conscrypt.CertPinManager r7, org.conscrypt.ConscryptCertStore r8, org.conscrypt.CertBlacklist r9, org.conscrypt.ct.CTLogStore r10, org.conscrypt.ct.CTVerifier r11, org.conscrypt.ct.CTPolicy r12) {
        /*
            r5 = this;
            r5.<init>()
            r11 = 0
            java.lang.String r0 = "PKIX"
            java.security.cert.CertPathValidator r0 = java.security.cert.CertPathValidator.getInstance(r0)     // Catch: Exception -> 0x0064
            java.lang.String r1 = "X509"
            java.security.cert.CertificateFactory r1 = java.security.cert.CertificateFactory.getInstance(r1)     // Catch: Exception -> 0x0060
            java.lang.String r2 = "AndroidCAStore"
            java.lang.String r3 = r6.getType()     // Catch: Exception -> 0x005c
            boolean r2 = r2.equals(r3)     // Catch: Exception -> 0x005c
            if (r2 == 0) goto L_0x003e
            boolean r2 = org.conscrypt.Platform.supportsConscryptCertStore()     // Catch: Exception -> 0x005c
            if (r2 == 0) goto L_0x003e
            if (r8 == 0) goto L_0x0025
            goto L_0x0029
        L_0x0025:
            org.conscrypt.ConscryptCertStore r8 = org.conscrypt.Platform.newDefaultCertStore()     // Catch: Exception -> 0x0037
        L_0x0029:
            org.conscrypt.TrustedCertificateIndex r2 = new org.conscrypt.TrustedCertificateIndex     // Catch: Exception -> 0x0030
            r2.<init>()     // Catch: Exception -> 0x0030
            r3 = r11
            goto L_0x004d
        L_0x0030:
            r2 = move-exception
            r3 = r11
            r4 = r8
            r8 = r6
            r6 = r2
            r2 = r4
            goto L_0x006a
        L_0x0037:
            r8 = move-exception
            r2 = r11
            r3 = r2
            r4 = r8
            r8 = r6
            r6 = r4
            goto L_0x006a
        L_0x003e:
            java.security.cert.X509Certificate[] r6 = acceptedIssuers(r6)     // Catch: Exception -> 0x0057
            org.conscrypt.TrustedCertificateIndex r2 = new org.conscrypt.TrustedCertificateIndex     // Catch: Exception -> 0x0051
            java.util.Set r3 = trustAnchors(r6)     // Catch: Exception -> 0x0051
            r2.<init>(r3)     // Catch: Exception -> 0x0051
            r3 = r6
            r6 = r11
        L_0x004d:
            r4 = r2
            r2 = r11
            r11 = r4
            goto L_0x006e
        L_0x0051:
            r2 = move-exception
            r3 = r6
            r6 = r2
            r2 = r8
            r8 = r11
            goto L_0x006a
        L_0x0057:
            r6 = move-exception
            r2 = r8
            r8 = r11
            r3 = r8
            goto L_0x006a
        L_0x005c:
            r6 = move-exception
            r8 = r11
            r2 = r8
            goto L_0x0069
        L_0x0060:
            r6 = move-exception
            r8 = r11
            r1 = r8
            goto L_0x0068
        L_0x0064:
            r6 = move-exception
            r8 = r11
            r0 = r8
            r1 = r0
        L_0x0068:
            r2 = r1
        L_0x0069:
            r3 = r2
        L_0x006a:
            r4 = r2
            r2 = r6
            r6 = r8
            r8 = r4
        L_0x006e:
            if (r9 != 0) goto L_0x0074
            org.conscrypt.CertBlacklist r9 = org.conscrypt.Platform.newDefaultBlacklist()
        L_0x0074:
            if (r10 != 0) goto L_0x007a
            org.conscrypt.ct.CTLogStore r10 = org.conscrypt.Platform.newDefaultLogStore()
        L_0x007a:
            if (r12 != 0) goto L_0x0080
            org.conscrypt.ct.CTPolicy r12 = org.conscrypt.Platform.newDefaultPolicy(r10)
        L_0x0080:
            r5.pinManager = r7
            r5.rootKeyStore = r6
            r5.trustedCertificateStore = r8
            r5.validator = r0
            r5.factory = r1
            r5.trustedCertificateIndex = r11
            org.conscrypt.TrustedCertificateIndex r6 = new org.conscrypt.TrustedCertificateIndex
            r6.<init>()
            r5.intermediateIndex = r6
            r5.acceptedIssuers = r3
            r5.err = r2
            r5.blacklist = r9
            org.conscrypt.ct.CTVerifier r6 = new org.conscrypt.ct.CTVerifier
            r6.<init>(r10)
            r5.ctVerifier = r6
            r5.ctPolicy = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.conscrypt.TrustManagerImpl.<init>(java.security.KeyStore, org.conscrypt.CertPinManager, org.conscrypt.ConscryptCertStore, org.conscrypt.CertBlacklist, org.conscrypt.ct.CTLogStore, org.conscrypt.ct.CTVerifier, org.conscrypt.ct.CTPolicy):void");
    }

    private static X509Certificate[] acceptedIssuers(KeyStore keyStore) {
        try {
            ArrayList arrayList = new ArrayList();
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(aliases.nextElement());
                if (x509Certificate != null) {
                    arrayList.add(x509Certificate);
                }
            }
            return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
        } catch (KeyStoreException unused) {
            return new X509Certificate[0];
        }
    }

    private static Set<TrustAnchor> trustAnchors(X509Certificate[] x509CertificateArr) {
        HashSet hashSet = new HashSet(x509CertificateArr.length);
        for (X509Certificate x509Certificate : x509CertificateArr) {
            hashSet.add(new TrustAnchor(x509Certificate, null));
        }
        return hashSet;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        checkTrusted(x509CertificateArr, str, null, null, true);
    }

    public List<X509Certificate> checkClientTrusted(X509Certificate[] x509CertificateArr, String str, String str2) throws CertificateException {
        return checkTrusted(x509CertificateArr, null, null, str, str2, true);
    }

    private static SSLSession getHandshakeSessionOrThrow(SSLSocket sSLSocket) throws CertificateException {
        SSLSession handshakeSession = sSLSocket.getHandshakeSession();
        if (handshakeSession != null) {
            return handshakeSession;
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        SSLParameters sSLParameters;
        SSLSession sSLSession = null;
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLSession handshakeSessionOrThrow = getHandshakeSessionOrThrow(sSLSocket);
            sSLParameters = sSLSocket.getSSLParameters();
            sSLSession = handshakeSessionOrThrow;
        } else {
            sSLParameters = null;
        }
        checkTrusted(x509CertificateArr, str, sSLSession, sSLParameters, true);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        SSLSession handshakeSession = sSLEngine.getHandshakeSession();
        if (handshakeSession != null) {
            checkTrusted(x509CertificateArr, str, handshakeSession, sSLEngine.getSSLParameters(), true);
            return;
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        checkTrusted(x509CertificateArr, str, null, null, false);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, String str2) throws CertificateException {
        return checkTrusted(x509CertificateArr, null, null, str, str2, false);
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        SSLParameters sSLParameters;
        SSLSession sSLSession = null;
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLSession handshakeSessionOrThrow = getHandshakeSessionOrThrow(sSLSocket);
            sSLParameters = sSLSocket.getSSLParameters();
            sSLSession = handshakeSessionOrThrow;
        } else {
            sSLParameters = null;
        }
        return checkTrusted(x509CertificateArr, str, sSLSession, sSLParameters, false);
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        SSLSession handshakeSession = sSLEngine.getHandshakeSession();
        if (handshakeSession != null) {
            return checkTrusted(x509CertificateArr, str, handshakeSession, sSLEngine.getSSLParameters(), false);
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) throws CertificateException {
        getTrustedChainForServer(x509CertificateArr, str, socket);
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) throws CertificateException {
        getTrustedChainForServer(x509CertificateArr, str, sSLEngine);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLSession sSLSession) throws CertificateException {
        return checkTrusted(x509CertificateArr, str, sSLSession, null, false);
    }

    public void handleTrustStorageUpdate() {
        X509Certificate[] x509CertificateArr = this.acceptedIssuers;
        if (x509CertificateArr == null) {
            this.trustedCertificateIndex.reset();
        } else {
            this.trustedCertificateIndex.reset(trustAnchors(x509CertificateArr));
        }
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] x509CertificateArr, String str, SSLSession sSLSession, SSLParameters sSLParameters, boolean z) throws CertificateException {
        String str2;
        byte[] bArr;
        byte[] bArr2;
        if (sSLSession != null) {
            str2 = sSLSession.getPeerHost();
            bArr2 = getOcspDataFromSession(sSLSession);
            bArr = getTlsSctDataFromSession(sSLSession);
        } else {
            bArr2 = null;
            bArr = null;
            str2 = null;
        }
        if (sSLSession == null || sSLParameters == null || !"HTTPS".equalsIgnoreCase(sSLParameters.getEndpointIdentificationAlgorithm()) || getHttpsVerifier().verify(str2, sSLSession)) {
            return checkTrusted(x509CertificateArr, bArr2, bArr, str, str2, z);
        }
        throw new CertificateException("No subjectAltNames on the certificate match");
    }

    private byte[] getOcspDataFromSession(SSLSession sSLSession) {
        List<byte[]> list;
        if (sSLSession instanceof ConscryptSession) {
            list = ((ConscryptSession) sSLSession).getStatusResponses();
        } else {
            try {
                Method declaredMethod = sSLSession.getClass().getDeclaredMethod("getStatusResponses", new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(sSLSession, new Object[0]);
                list = invoke instanceof List ? (List) invoke : null;
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException unused) {
                list = null;
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private byte[] getTlsSctDataFromSession(SSLSession sSLSession) {
        if (sSLSession instanceof ConscryptSession) {
            return ((ConscryptSession) sSLSession).getPeerSignedCertificateTimestamp();
        }
        try {
            Method declaredMethod = sSLSession.getClass().getDeclaredMethod("getPeerSignedCertificateTimestamp", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(sSLSession, new Object[0]);
            if (invoke instanceof byte[]) {
                return (byte[]) invoke;
            }
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException unused) {
            return null;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, String str2, boolean z) throws CertificateException {
        if (x509CertificateArr == null || x509CertificateArr.length == 0 || str == null || str.length() == 0) {
            throw new IllegalArgumentException("null or zero-length parameter");
        }
        Exception exc = this.err;
        if (exc == null) {
            HashSet hashSet = new HashSet();
            ArrayList<X509Certificate> arrayList = new ArrayList<>();
            ArrayList<TrustAnchor> arrayList2 = new ArrayList<>();
            X509Certificate x509Certificate = x509CertificateArr[0];
            TrustAnchor findTrustAnchorBySubjectAndPublicKey = findTrustAnchorBySubjectAndPublicKey(x509Certificate);
            if (findTrustAnchorBySubjectAndPublicKey != null) {
                arrayList2.add(findTrustAnchorBySubjectAndPublicKey);
                hashSet.add(findTrustAnchorBySubjectAndPublicKey.getTrustedCert());
            } else {
                arrayList.add(x509Certificate);
            }
            hashSet.add(x509Certificate);
            return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str2, z, arrayList, arrayList2, hashSet);
        }
        throw new CertificateException(exc);
    }

    private List<X509Certificate> checkTrustedRecursive(X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2, String str, boolean z, ArrayList<X509Certificate> arrayList, ArrayList<TrustAnchor> arrayList2, Set<X509Certificate> set) throws CertificateException {
        X509Certificate x509Certificate;
        if (arrayList2.isEmpty()) {
            x509Certificate = arrayList.get(arrayList.size() - 1);
        } else {
            x509Certificate = arrayList2.get(arrayList2.size() - 1).getTrustedCert();
        }
        checkBlacklist(x509Certificate);
        if (x509Certificate.getIssuerDN().equals(x509Certificate.getSubjectDN())) {
            return verifyChain(arrayList, arrayList2, str, z, bArr, bArr2);
        }
        boolean z2 = false;
        CertificateException e = null;
        for (TrustAnchor trustAnchor : sortPotentialAnchors(findAllTrustAnchorsByIssuerAndSignature(x509Certificate))) {
            X509Certificate trustedCert = trustAnchor.getTrustedCert();
            if (!set.contains(trustedCert)) {
                set.add(trustedCert);
                arrayList2.add(trustAnchor);
                try {
                    return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str, z, arrayList, arrayList2, set);
                } catch (CertificateException e2) {
                    e = e2;
                    arrayList2.remove(arrayList2.size() - 1);
                    set.remove(trustedCert);
                    z2 = true;
                }
            }
        }
        if (arrayList2.isEmpty()) {
            for (int i = 1; i < x509CertificateArr.length; i++) {
                X509Certificate x509Certificate2 = x509CertificateArr[i];
                if (!set.contains(x509Certificate2) && x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
                    try {
                        x509Certificate2.checkValidity();
                        ChainStrengthAnalyzer.checkCert(x509Certificate2);
                        set.add(x509Certificate2);
                        arrayList.add(x509Certificate2);
                        try {
                            return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str, z, arrayList, arrayList2, set);
                        } catch (CertificateException e3) {
                            e = e3;
                            set.remove(x509Certificate2);
                            arrayList.remove(arrayList.size() - 1);
                        }
                    } catch (CertificateException e4) {
                        e = new CertificateException("Unacceptable certificate: " + x509Certificate2.getSubjectX500Principal(), e4);
                    }
                }
            }
            for (TrustAnchor trustAnchor2 : sortPotentialAnchors(this.intermediateIndex.findAllByIssuerAndSignature(x509Certificate))) {
                X509Certificate trustedCert2 = trustAnchor2.getTrustedCert();
                if (!set.contains(trustedCert2)) {
                    set.add(trustedCert2);
                    arrayList.add(trustedCert2);
                    try {
                        return checkTrustedRecursive(x509CertificateArr, bArr, bArr2, str, z, arrayList, arrayList2, set);
                    } catch (CertificateException e5) {
                        e = e5;
                        arrayList.remove(arrayList.size() - 1);
                        set.remove(trustedCert2);
                    }
                }
            }
            if (e != null) {
                throw e;
            }
            throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, this.factory.generateCertPath(arrayList), -1));
        } else if (!z2) {
            return verifyChain(arrayList, arrayList2, str, z, bArr, bArr2);
        } else {
            throw e;
        }
    }

    private List<X509Certificate> verifyChain(List<X509Certificate> list, List<TrustAnchor> list2, String str, boolean z, byte[] bArr, byte[] bArr2) throws CertificateException {
        try {
            CertPath generateCertPath = this.factory.generateCertPath(list);
            if (!list2.isEmpty()) {
                ArrayList<X509Certificate> arrayList = new ArrayList();
                arrayList.addAll(list);
                for (TrustAnchor trustAnchor : list2) {
                    arrayList.add(trustAnchor.getTrustedCert());
                }
                if (this.pinManager != null) {
                    this.pinManager.checkChainPinning(str, arrayList);
                }
                for (X509Certificate x509Certificate : arrayList) {
                    checkBlacklist(x509Certificate);
                }
                if (!z && (this.ctEnabledOverride || (str != null && Platform.isCTVerificationRequired(str)))) {
                    checkCT(str, arrayList, bArr, bArr2);
                }
                if (list.isEmpty()) {
                    return arrayList;
                }
                ChainStrengthAnalyzer.check(list);
                try {
                    try {
                        HashSet hashSet = new HashSet();
                        hashSet.add(list2.get(0));
                        PKIXParameters pKIXParameters = new PKIXParameters(hashSet);
                        pKIXParameters.setRevocationEnabled(false);
                        X509Certificate x509Certificate2 = list.get(0);
                        setOcspResponses(pKIXParameters, x509Certificate2, bArr);
                        pKIXParameters.addCertPathChecker(new ExtendedKeyUsagePKIXCertPathChecker(z, x509Certificate2));
                        this.validator.validate(generateCertPath, pKIXParameters);
                        for (int i = 1; i < list.size(); i++) {
                            this.intermediateIndex.index(list.get(i));
                        }
                        return arrayList;
                    } catch (InvalidAlgorithmParameterException e) {
                        throw new CertificateException("Chain validation failed", e);
                    }
                } catch (CertPathValidatorException e2) {
                    throw new CertificateException("Chain validation failed", e2);
                }
            } else {
                throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, generateCertPath, -1));
            }
        } catch (CertificateException e3) {
            Logger logger2 = logger;
            logger2.fine("Rejected candidate cert chain due to error: " + e3.getMessage());
            throw e3;
        }
    }

    private void checkBlacklist(X509Certificate x509Certificate) throws CertificateException {
        CertBlacklist certBlacklist = this.blacklist;
        if (certBlacklist != null && certBlacklist.isPublicKeyBlackListed(x509Certificate.getPublicKey())) {
            throw new CertificateException("Certificate blacklisted by public key: " + x509Certificate);
        }
    }

    private void checkCT(String str, List<X509Certificate> list, byte[] bArr, byte[] bArr2) throws CertificateException {
        if (!this.ctPolicy.doesResultConformToPolicy(this.ctVerifier.verifySignedCertificateTimestamps(list, bArr2, bArr), str, (X509Certificate[]) list.toArray(new X509Certificate[list.size()]))) {
            throw new CertificateException("Certificate chain does not conform to required transparency policy.");
        }
    }

    private void setOcspResponses(PKIXParameters pKIXParameters, X509Certificate x509Certificate, byte[] bArr) {
        if (bArr != null) {
            PKIXRevocationChecker pKIXRevocationChecker = null;
            List<PKIXCertPathChecker> arrayList = new ArrayList<>(pKIXParameters.getCertPathCheckers());
            Iterator<PKIXCertPathChecker> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PKIXCertPathChecker next = it.next();
                if (next instanceof PKIXRevocationChecker) {
                    pKIXRevocationChecker = (PKIXRevocationChecker) next;
                    break;
                }
            }
            if (pKIXRevocationChecker == null) {
                try {
                    pKIXRevocationChecker = (PKIXRevocationChecker) this.validator.getRevocationChecker();
                    arrayList.add(pKIXRevocationChecker);
                    pKIXRevocationChecker.setOptions(Collections.singleton(PKIXRevocationChecker.Option.ONLY_END_ENTITY));
                } catch (UnsupportedOperationException unused) {
                    return;
                }
            }
            pKIXRevocationChecker.setOcspResponses(Collections.singletonMap(x509Certificate, bArr));
            pKIXParameters.setCertPathCheckers(arrayList);
        }
    }

    private static Collection<TrustAnchor> sortPotentialAnchors(Set<TrustAnchor> set) {
        if (set.size() <= 1) {
            return set;
        }
        ArrayList arrayList = new ArrayList(set);
        Collections.sort(arrayList, TRUST_ANCHOR_COMPARATOR);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class TrustAnchorComparator implements Comparator<TrustAnchor> {
        private static final CertificatePriorityComparator CERT_COMPARATOR = new CertificatePriorityComparator();

        private TrustAnchorComparator() {
        }

        public int compare(TrustAnchor trustAnchor, TrustAnchor trustAnchor2) {
            return CERT_COMPARATOR.compare(trustAnchor.getTrustedCert(), trustAnchor2.getTrustedCert());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class ExtendedKeyUsagePKIXCertPathChecker extends PKIXCertPathChecker {
        private static final String EKU_anyExtendedKeyUsage = "2.5.29.37.0";
        private static final String EKU_clientAuth = "1.3.6.1.5.5.7.3.2";
        private static final String EKU_msSGC = "1.3.6.1.4.1.311.10.3.3";
        private static final String EKU_nsSGC = "2.16.840.1.113730.4.1";
        private static final String EKU_serverAuth = "1.3.6.1.5.5.7.3.1";
        private final boolean clientAuth;
        private final X509Certificate leaf;
        private static final String EKU_OID = "2.5.29.37";
        private static final Set<String> SUPPORTED_EXTENSIONS = Collections.unmodifiableSet(new HashSet(Arrays.asList(EKU_OID)));

        @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
        public void init(boolean z) throws CertPathValidatorException {
        }

        @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
        public boolean isForwardCheckingSupported() {
            return true;
        }

        private ExtendedKeyUsagePKIXCertPathChecker(boolean z, X509Certificate x509Certificate) {
            this.clientAuth = z;
            this.leaf = x509Certificate;
        }

        @Override // java.security.cert.PKIXCertPathChecker
        public Set<String> getSupportedExtensions() {
            return SUPPORTED_EXTENSIONS;
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0052, code lost:
            r6.remove(org.conscrypt.TrustManagerImpl.ExtendedKeyUsagePKIXCertPathChecker.EKU_OID);
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0057, code lost:
            return;
         */
        @Override // java.security.cert.PKIXCertPathChecker
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void check(java.security.cert.Certificate r5, java.util.Collection<java.lang.String> r6) throws java.security.cert.CertPathValidatorException {
            /*
                r4 = this;
                java.security.cert.X509Certificate r0 = r4.leaf
                if (r5 == r0) goto L_0x0005
                return
            L_0x0005:
                java.util.List r5 = r0.getExtendedKeyUsage()     // Catch: CertificateParsingException -> 0x0060
                if (r5 != 0) goto L_0x000c
                return
            L_0x000c:
                r0 = 0
                java.util.Iterator r5 = r5.iterator()
            L_0x0011:
                boolean r1 = r5.hasNext()
                r2 = 1
                if (r1 == 0) goto L_0x004f
                java.lang.Object r1 = r5.next()
                java.lang.String r1 = (java.lang.String) r1
                java.lang.String r3 = "2.5.29.37.0"
                boolean r3 = r1.equals(r3)
                if (r3 == 0) goto L_0x0027
                goto L_0x0050
            L_0x0027:
                boolean r3 = r4.clientAuth
                if (r3 == 0) goto L_0x0034
                java.lang.String r3 = "1.3.6.1.5.5.7.3.2"
                boolean r1 = r1.equals(r3)
                if (r1 == 0) goto L_0x0011
                goto L_0x0050
            L_0x0034:
                java.lang.String r3 = "1.3.6.1.5.5.7.3.1"
                boolean r3 = r1.equals(r3)
                if (r3 == 0) goto L_0x003d
                goto L_0x0050
            L_0x003d:
                java.lang.String r3 = "2.16.840.1.113730.4.1"
                boolean r3 = r1.equals(r3)
                if (r3 == 0) goto L_0x0046
                goto L_0x0050
            L_0x0046:
                java.lang.String r3 = "1.3.6.1.4.1.311.10.3.3"
                boolean r1 = r1.equals(r3)
                if (r1 == 0) goto L_0x0011
                goto L_0x0050
            L_0x004f:
                r2 = r0
            L_0x0050:
                if (r2 == 0) goto L_0x0058
                java.lang.String r5 = "2.5.29.37"
                r6.remove(r5)
                return
            L_0x0058:
                java.security.cert.CertPathValidatorException r5 = new java.security.cert.CertPathValidatorException
                java.lang.String r6 = "End-entity certificate does not have a valid extendedKeyUsage."
                r5.<init>(r6)
                throw r5
            L_0x0060:
                r5 = move-exception
                java.security.cert.CertPathValidatorException r6 = new java.security.cert.CertPathValidatorException
                r6.<init>(r5)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.conscrypt.TrustManagerImpl.ExtendedKeyUsagePKIXCertPathChecker.check(java.security.cert.Certificate, java.util.Collection):void");
        }
    }

    private Set<TrustAnchor> findAllTrustAnchorsByIssuerAndSignature(X509Certificate x509Certificate) {
        ConscryptCertStore conscryptCertStore;
        Set<TrustAnchor> findAllByIssuerAndSignature = this.trustedCertificateIndex.findAllByIssuerAndSignature(x509Certificate);
        if (!findAllByIssuerAndSignature.isEmpty() || (conscryptCertStore = this.trustedCertificateStore) == null) {
            return findAllByIssuerAndSignature;
        }
        Set<X509Certificate> findAllIssuers = conscryptCertStore.findAllIssuers(x509Certificate);
        if (findAllIssuers.isEmpty()) {
            return findAllByIssuerAndSignature;
        }
        HashSet hashSet = new HashSet(findAllIssuers.size());
        for (X509Certificate x509Certificate2 : findAllIssuers) {
            hashSet.add(this.trustedCertificateIndex.index(x509Certificate2));
        }
        return hashSet;
    }

    private TrustAnchor findTrustAnchorBySubjectAndPublicKey(X509Certificate x509Certificate) {
        X509Certificate trustAnchor;
        TrustAnchor findBySubjectAndPublicKey = this.trustedCertificateIndex.findBySubjectAndPublicKey(x509Certificate);
        if (findBySubjectAndPublicKey != null) {
            return findBySubjectAndPublicKey;
        }
        ConscryptCertStore conscryptCertStore = this.trustedCertificateStore;
        if (conscryptCertStore == null || (trustAnchor = conscryptCertStore.getTrustAnchor(x509Certificate)) == null) {
            return null;
        }
        return new TrustAnchor(trustAnchor, null);
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] x509CertificateArr = this.acceptedIssuers;
        return x509CertificateArr != null ? (X509Certificate[]) x509CertificateArr.clone() : acceptedIssuers(this.rootKeyStore);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void setDefaultHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        synchronized (TrustManagerImpl.class) {
            defaultHostnameVerifier = conscryptHostnameVerifier;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized ConscryptHostnameVerifier getDefaultHostnameVerifier() {
        ConscryptHostnameVerifier conscryptHostnameVerifier;
        synchronized (TrustManagerImpl.class) {
            conscryptHostnameVerifier = defaultHostnameVerifier;
        }
        return conscryptHostnameVerifier;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setHostnameVerifier(ConscryptHostnameVerifier conscryptHostnameVerifier) {
        this.hostnameVerifier = conscryptHostnameVerifier;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConscryptHostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public enum GlobalHostnameVerifierAdapter implements ConscryptHostnameVerifier {
        INSTANCE;

        @Override // org.conscrypt.ConscryptHostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return HttpsURLConnection.getDefaultHostnameVerifier().verify(str, sSLSession);
        }
    }

    private ConscryptHostnameVerifier getHttpsVerifier() {
        ConscryptHostnameVerifier conscryptHostnameVerifier = this.hostnameVerifier;
        if (conscryptHostnameVerifier != null) {
            return conscryptHostnameVerifier;
        }
        ConscryptHostnameVerifier defaultHostnameVerifier2 = getDefaultHostnameVerifier();
        return defaultHostnameVerifier2 != null ? defaultHostnameVerifier2 : GlobalHostnameVerifierAdapter.INSTANCE;
    }

    public void setCTEnabledOverride(boolean z) {
        this.ctEnabledOverride = z;
    }

    public void setCTVerifier(CTVerifier cTVerifier) {
        this.ctVerifier = cTVerifier;
    }

    public void setCTPolicy(CTPolicy cTPolicy) {
        this.ctPolicy = cTPolicy;
    }
}
