package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import org.conscrypt.NativeRef;
import org.conscrypt.SSLUtils;

/* loaded from: classes5.dex */
public abstract class NativeSslSession {
    private static final Logger logger = Logger.getLogger(NativeSslSession.class.getName());

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getCipherSuite();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract byte[] getId();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getPeerHost();

    abstract byte[] getPeerOcspStapledResponse();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int getPeerPort();

    abstract byte[] getPeerSignedCertificateTimestamp();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getProtocol();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean isSingleUse();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean isValid();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void offerToResume(NativeSsl nativeSsl) throws SSLException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract byte[] toBytes();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract SSLSession toSSLSession();

    NativeSslSession() {
    }

    public static NativeSslSession newInstance(NativeRef.SSL_SESSION ssl_session, ConscryptSession conscryptSession) throws SSLPeerUnverifiedException {
        AbstractSessionContext abstractSessionContext = (AbstractSessionContext) conscryptSession.getSessionContext();
        if (abstractSessionContext instanceof ClientSessionContext) {
            return new Impl(abstractSessionContext, ssl_session, conscryptSession.getPeerHost(), conscryptSession.getPeerPort(), conscryptSession.getPeerCertificates(), getOcspResponse(conscryptSession), conscryptSession.getPeerSignedCertificateTimestamp());
        }
        return new Impl(abstractSessionContext, ssl_session, null, -1, null, null, null);
    }

    private static byte[] getOcspResponse(ConscryptSession conscryptSession) {
        List<byte[]> statusResponses = conscryptSession.getStatusResponses();
        if (statusResponses.size() >= 1) {
            return statusResponses.get(0);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0094 A[Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3, TryCatch #3 {IOException -> 0x00e8, BufferUnderflowException -> 0x00e3, blocks: (B:3:0x0005, B:5:0x000f, B:7:0x0027, B:8:0x0033, B:10:0x003c, B:11:0x005a, B:12:0x005b, B:14:0x0061, B:16:0x006b, B:18:0x0079, B:21:0x008e, B:23:0x0094, B:25:0x009d, B:27:0x00a5, B:29:0x00ab, B:31:0x00b6, B:33:0x00cc, B:34:0x00e2), top: B:43:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ab A[Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3, TryCatch #3 {IOException -> 0x00e8, BufferUnderflowException -> 0x00e3, blocks: (B:3:0x0005, B:5:0x000f, B:7:0x0027, B:8:0x0033, B:10:0x003c, B:11:0x005a, B:12:0x005b, B:14:0x0061, B:16:0x006b, B:18:0x0079, B:21:0x008e, B:23:0x0094, B:25:0x009d, B:27:0x00a5, B:29:0x00ab, B:31:0x00b6, B:33:0x00cc, B:34:0x00e2), top: B:43:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b6 A[Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3, TryCatch #3 {IOException -> 0x00e8, BufferUnderflowException -> 0x00e3, blocks: (B:3:0x0005, B:5:0x000f, B:7:0x0027, B:8:0x0033, B:10:0x003c, B:11:0x005a, B:12:0x005b, B:14:0x0061, B:16:0x006b, B:18:0x0079, B:21:0x008e, B:23:0x0094, B:25:0x009d, B:27:0x00a5, B:29:0x00ab, B:31:0x00b6, B:33:0x00cc, B:34:0x00e2), top: B:43:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.conscrypt.NativeSslSession newInstance(org.conscrypt.AbstractSessionContext r14, byte[] r15, java.lang.String r16, int r17) {
        /*
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.wrap(r15)
            r1 = 0
            int r2 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            boolean r3 = org.conscrypt.SSLUtils.SessionType.isSupportedType(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            if (r3 == 0) goto L_0x00cc
            int r3 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r3)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            byte[] r3 = new byte[r3]     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r0.get(r3)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r4 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r4)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.security.cert.X509Certificate[] r10 = new java.security.cert.X509Certificate[r4]     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r5 = 0
        L_0x0025:
            if (r5 >= r4) goto L_0x005b
            int r6 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r6)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            byte[] r6 = new byte[r6]     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r0.get(r6)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            org.conscrypt.OpenSSLX509Certificate r6 = org.conscrypt.OpenSSLX509Certificate.fromX509Der(r6)     // Catch: Exception -> 0x003c, IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r10[r5] = r6     // Catch: Exception -> 0x003c, IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r5 = r5 + 1
            goto L_0x0025
        L_0x003c:
            java.io.IOException r0 = new java.io.IOException     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r2.<init>()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.String r3 = "Can not read certificate "
            r2.append(r3)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r2.append(r5)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.String r3 = "/"
            r2.append(r3)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r2.append(r4)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.String r2 = r2.toString()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r0.<init>(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            throw r0     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
        L_0x005b:
            org.conscrypt.SSLUtils$SessionType r4 = org.conscrypt.SSLUtils.SessionType.OPEN_SSL_WITH_OCSP     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r4 = r4.value     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            if (r2 < r4) goto L_0x008d
            int r4 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r4)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r5 = 1
            if (r4 < r5) goto L_0x008d
            int r6 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r6)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            byte[] r6 = new byte[r6]     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r0.get(r6)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
        L_0x0077:
            if (r5 >= r4) goto L_0x008b
            int r7 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r7)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r8 = r0.position()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r8 = r8 + r7
            r0.position(r8)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r5 = r5 + 1
            goto L_0x0077
        L_0x008b:
            r11 = r6
            goto L_0x008e
        L_0x008d:
            r11 = r1
        L_0x008e:
            org.conscrypt.SSLUtils$SessionType r4 = org.conscrypt.SSLUtils.SessionType.OPEN_SSL_WITH_TLS_SCT     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            int r4 = r4.value     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            if (r2 != r4) goto L_0x00a4
            int r2 = r0.getInt()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            checkRemaining(r0, r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            if (r2 <= 0) goto L_0x00a4
            byte[] r2 = new byte[r2]     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r0.get(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r12 = r2
            goto L_0x00a5
        L_0x00a4:
            r12 = r1
        L_0x00a5:
            int r0 = r0.remaining()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            if (r0 == 0) goto L_0x00b6
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.String r2 = "Read entire session, but data still remains; rejecting"
            r0.<init>(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            log(r0)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            return r1
        L_0x00b6:
            org.conscrypt.NativeRef$SSL_SESSION r7 = new org.conscrypt.NativeRef$SSL_SESSION     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            long r2 = org.conscrypt.NativeCrypto.d2i_SSL_SESSION(r3)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r7.<init>(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            org.conscrypt.NativeSslSession$Impl r0 = new org.conscrypt.NativeSslSession$Impl     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r13 = 0
            r5 = r0
            r6 = r14
            r8 = r16
            r9 = r17
            r5.<init>(r6, r7, r8, r9, r10, r11, r12)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            return r0
        L_0x00cc:
            java.io.IOException r0 = new java.io.IOException     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r3.<init>()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.String r4 = "Unexpected type ID: "
            r3.append(r4)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r3.append(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            java.lang.String r2 = r3.toString()     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            r0.<init>(r2)     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
            throw r0     // Catch: IOException -> 0x00e8, BufferUnderflowException -> 0x00e3
        L_0x00e3:
            r0 = move-exception
            log(r0)
            return r1
        L_0x00e8:
            r0 = move-exception
            log(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.conscrypt.NativeSslSession.newInstance(org.conscrypt.AbstractSessionContext, byte[], java.lang.String, int):org.conscrypt.NativeSslSession");
    }

    /* loaded from: classes5.dex */
    public static final class Impl extends NativeSslSession {
        private final String cipherSuite;
        private final AbstractSessionContext context;
        private final String host;
        private final X509Certificate[] peerCertificates;
        private final byte[] peerOcspStapledResponse;
        private final byte[] peerSignedCertificateTimestamp;
        private final int port;
        private final String protocol;
        private final NativeRef.SSL_SESSION ref;

        private Impl(AbstractSessionContext abstractSessionContext, NativeRef.SSL_SESSION ssl_session, String str, int i, X509Certificate[] x509CertificateArr, byte[] bArr, byte[] bArr2) {
            this.context = abstractSessionContext;
            this.host = str;
            this.port = i;
            this.peerCertificates = x509CertificateArr;
            this.peerOcspStapledResponse = bArr;
            this.peerSignedCertificateTimestamp = bArr2;
            this.protocol = NativeCrypto.SSL_SESSION_get_version(ssl_session.address);
            this.cipherSuite = NativeCrypto.cipherSuiteToJava(NativeCrypto.SSL_SESSION_cipher(ssl_session.address));
            this.ref = ssl_session;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public byte[] getId() {
            return NativeCrypto.SSL_SESSION_session_id(this.ref.address);
        }

        public long getCreationTime() {
            return NativeCrypto.SSL_SESSION_get_time(this.ref.address);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public boolean isValid() {
            return System.currentTimeMillis() - (Math.max(0L, Math.min((long) this.context.getSessionTimeout(), NativeCrypto.SSL_SESSION_get_timeout(this.ref.address))) * 1000) < getCreationTime();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public boolean isSingleUse() {
            return NativeCrypto.SSL_SESSION_should_be_single_use(this.ref.address);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public void offerToResume(NativeSsl nativeSsl) throws SSLException {
            nativeSsl.offerToResumeSession(this.ref.address);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public String getCipherSuite() {
            return this.cipherSuite;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public String getProtocol() {
            return this.protocol;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public String getPeerHost() {
            return this.host;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public int getPeerPort() {
            return this.port;
        }

        @Override // org.conscrypt.NativeSslSession
        byte[] getPeerOcspStapledResponse() {
            return this.peerOcspStapledResponse;
        }

        @Override // org.conscrypt.NativeSslSession
        byte[] getPeerSignedCertificateTimestamp() {
            return this.peerSignedCertificateTimestamp;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public byte[] toBytes() {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeInt(SSLUtils.SessionType.OPEN_SSL_WITH_TLS_SCT.value);
                byte[] i2d_SSL_SESSION = NativeCrypto.i2d_SSL_SESSION(this.ref.address);
                dataOutputStream.writeInt(i2d_SSL_SESSION.length);
                dataOutputStream.write(i2d_SSL_SESSION);
                dataOutputStream.writeInt(this.peerCertificates.length);
                for (X509Certificate x509Certificate : this.peerCertificates) {
                    byte[] encoded = x509Certificate.getEncoded();
                    dataOutputStream.writeInt(encoded.length);
                    dataOutputStream.write(encoded);
                }
                if (this.peerOcspStapledResponse != null) {
                    dataOutputStream.writeInt(1);
                    dataOutputStream.writeInt(this.peerOcspStapledResponse.length);
                    dataOutputStream.write(this.peerOcspStapledResponse);
                } else {
                    dataOutputStream.writeInt(0);
                }
                if (this.peerSignedCertificateTimestamp != null) {
                    dataOutputStream.writeInt(this.peerSignedCertificateTimestamp.length);
                    dataOutputStream.write(this.peerSignedCertificateTimestamp);
                } else {
                    dataOutputStream.writeInt(0);
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                NativeSslSession.logger.log(Level.WARNING, "Failed to convert saved SSL Session: ", (Throwable) e);
                return null;
            } catch (CertificateEncodingException e2) {
                NativeSslSession.log(e2);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.conscrypt.NativeSslSession
        public SSLSession toSSLSession() {
            return new SSLSession() { // from class: org.conscrypt.NativeSslSession.Impl.1
                @Override // javax.net.ssl.SSLSession
                public byte[] getId() {
                    return Impl.this.getId();
                }

                @Override // javax.net.ssl.SSLSession
                public String getCipherSuite() {
                    return Impl.this.getCipherSuite();
                }

                @Override // javax.net.ssl.SSLSession
                public String getProtocol() {
                    return Impl.this.getProtocol();
                }

                @Override // javax.net.ssl.SSLSession
                public String getPeerHost() {
                    return Impl.this.getPeerHost();
                }

                @Override // javax.net.ssl.SSLSession
                public int getPeerPort() {
                    return Impl.this.getPeerPort();
                }

                @Override // javax.net.ssl.SSLSession
                public long getCreationTime() {
                    return Impl.this.getCreationTime();
                }

                @Override // javax.net.ssl.SSLSession
                public boolean isValid() {
                    return Impl.this.isValid();
                }

                @Override // javax.net.ssl.SSLSession
                public SSLSessionContext getSessionContext() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public long getLastAccessedTime() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public void invalidate() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public void putValue(String str, Object obj) {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public Object getValue(String str) {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public void removeValue(String str) {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public String[] getValueNames() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public Certificate[] getLocalCertificates() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public Principal getLocalPrincipal() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public int getPacketBufferSize() {
                    throw new UnsupportedOperationException();
                }

                @Override // javax.net.ssl.SSLSession
                public int getApplicationBufferSize() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    public static void log(Throwable th) {
        logger.log(Level.INFO, "Error inflating SSL session: {0}", th.getMessage() != null ? th.getMessage() : th.getClass().getName());
    }

    private static void checkRemaining(ByteBuffer byteBuffer, int i) throws IOException {
        if (i < 0) {
            throw new IOException("Length is negative: " + i);
        } else if (i > byteBuffer.remaining()) {
            throw new IOException("Length of blob is longer than available: " + i + " > " + byteBuffer.remaining());
        }
    }
}
