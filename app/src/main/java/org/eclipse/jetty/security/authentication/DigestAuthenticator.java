package org.eclipse.jetty.security.authentication;

import com.xiaomi.mipush.sdk.Constants;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Credential;

/* loaded from: classes5.dex */
public class DigestAuthenticator extends LoginAuthenticator {
    private static final Logger LOG = Log.getLogger(DigestAuthenticator.class);
    SecureRandom _random = new SecureRandom();
    private long _maxNonceAgeMs = 60000;
    private ConcurrentMap<String, Nonce> _nonceCount = new ConcurrentHashMap();
    private Queue<Nonce> _nonceQueue = new ConcurrentLinkedQueue();

    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return "DIGEST";
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class Nonce {
        AtomicInteger _nc = new AtomicInteger();
        final String _nonce;
        final long _ts;

        public Nonce(String str, long j) {
            this._nonce = str;
            this._ts = j;
        }
    }

    @Override // org.eclipse.jetty.security.authentication.LoginAuthenticator, org.eclipse.jetty.security.Authenticator
    public void setConfiguration(Authenticator.AuthConfiguration authConfiguration) {
        super.setConfiguration(authConfiguration);
        String initParameter = authConfiguration.getInitParameter("maxNonceAge");
        if (initParameter != null) {
            synchronized (this) {
                this._maxNonceAgeMs = Long.valueOf(initParameter).longValue();
            }
        }
    }

    public synchronized void setMaxNonceAge(long j) {
        this._maxNonceAgeMs = j;
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x00f6 A[Catch: IOException -> 0x0148, TryCatch #0 {IOException -> 0x0148, blocks: (B:7:0x0017, B:9:0x001f, B:10:0x0037, B:11:0x004a, B:13:0x0050, B:15:0x005a, B:24:0x006e, B:26:0x0076, B:27:0x0079, B:29:0x0081, B:30:0x0084, B:32:0x008c, B:33:0x008f, B:35:0x0097, B:36:0x009a, B:38:0x00a2, B:39:0x00a5, B:41:0x00ad, B:42:0x00b0, B:44:0x00b8, B:45:0x00bb, B:47:0x00c3, B:52:0x00d1, B:54:0x00da, B:56:0x00e2, B:61:0x00f0, B:63:0x00f6, B:66:0x00fe, B:68:0x0145), top: B:73:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0145 A[Catch: IOException -> 0x0148, TRY_LEAVE, TryCatch #0 {IOException -> 0x0148, blocks: (B:7:0x0017, B:9:0x001f, B:10:0x0037, B:11:0x004a, B:13:0x0050, B:15:0x005a, B:24:0x006e, B:26:0x0076, B:27:0x0079, B:29:0x0081, B:30:0x0084, B:32:0x008c, B:33:0x008f, B:35:0x0097, B:36:0x009a, B:38:0x00a2, B:39:0x00a5, B:41:0x00ad, B:42:0x00b0, B:44:0x00b8, B:45:0x00bb, B:47:0x00c3, B:52:0x00d1, B:54:0x00da, B:56:0x00e2, B:61:0x00f0, B:63:0x00f6, B:66:0x00fe, B:68:0x0145), top: B:73:0x0017 }] */
    @Override // org.eclipse.jetty.security.Authenticator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.server.Authentication validateRequest(javax.servlet.ServletRequest r11, javax.servlet.ServletResponse r12, boolean r13) throws org.eclipse.jetty.security.ServerAuthException {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.security.authentication.DigestAuthenticator.validateRequest(javax.servlet.ServletRequest, javax.servlet.ServletResponse, boolean):org.eclipse.jetty.server.Authentication");
    }

    public String newNonce(Request request) {
        Nonce nonce;
        do {
            byte[] bArr = new byte[24];
            this._random.nextBytes(bArr);
            nonce = new Nonce(new String(B64Code.encode(bArr)), request.getTimeStamp());
        } while (this._nonceCount.putIfAbsent(nonce._nonce, nonce) != null);
        this._nonceQueue.add(nonce);
        return nonce._nonce;
    }

    private int checkNonce(Digest digest, Request request) {
        long timeStamp;
        synchronized (this) {
            timeStamp = request.getTimeStamp() - this._maxNonceAgeMs;
        }
        Nonce peek = this._nonceQueue.peek();
        while (peek != null && peek._ts < timeStamp) {
            this._nonceQueue.remove(peek);
            this._nonceCount.remove(peek._nonce);
            peek = this._nonceQueue.peek();
        }
        try {
            Nonce nonce = this._nonceCount.get(digest.nonce);
            if (nonce == null) {
                return 0;
            }
            long parseLong = Long.parseLong(digest.nc, 16);
            if (parseLong > 2147483647L) {
                return 0;
            }
            int i = nonce._nc.get();
            while (!nonce._nc.compareAndSet(i, (int) parseLong)) {
                i = nonce._nc.get();
            }
            return parseLong <= ((long) i) ? -1 : 1;
        } catch (Exception e) {
            LOG.ignore(e);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class Digest extends Credential {
        private static final long serialVersionUID = -2484639019549527724L;
        final String method;
        String username = "";
        String realm = "";
        String nonce = "";
        String nc = "";
        String cnonce = "";
        String qop = "";
        String uri = "";
        String response = "";

        Digest(String str) {
            this.method = str;
        }

        @Override // org.eclipse.jetty.util.security.Credential
        public boolean check(Object obj) {
            byte[] bArr;
            if (obj instanceof char[]) {
                obj = new String((char[]) obj);
            }
            String obj2 = obj instanceof String ? obj : obj.toString();
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                if (obj instanceof Credential.MD5) {
                    bArr = ((Credential.MD5) obj).getDigest();
                } else {
                    instance.update(this.username.getBytes("ISO-8859-1"));
                    instance.update((byte) 58);
                    instance.update(this.realm.getBytes("ISO-8859-1"));
                    instance.update((byte) 58);
                    instance.update(obj2.getBytes("ISO-8859-1"));
                    bArr = instance.digest();
                }
                instance.reset();
                instance.update(this.method.getBytes("ISO-8859-1"));
                instance.update((byte) 58);
                instance.update(this.uri.getBytes("ISO-8859-1"));
                byte[] digest = instance.digest();
                instance.update(TypeUtil.toString(bArr, 16).getBytes("ISO-8859-1"));
                instance.update((byte) 58);
                instance.update(this.nonce.getBytes("ISO-8859-1"));
                instance.update((byte) 58);
                instance.update(this.nc.getBytes("ISO-8859-1"));
                instance.update((byte) 58);
                instance.update(this.cnonce.getBytes("ISO-8859-1"));
                instance.update((byte) 58);
                instance.update(this.qop.getBytes("ISO-8859-1"));
                instance.update((byte) 58);
                instance.update(TypeUtil.toString(digest, 16).getBytes("ISO-8859-1"));
                return TypeUtil.toString(instance.digest(), 16).equalsIgnoreCase(this.response);
            } catch (Exception e) {
                DigestAuthenticator.LOG.warn(e);
                return false;
            }
        }

        public String toString() {
            return this.username + Constants.ACCEPT_TIME_SEPARATOR_SP + this.response;
        }
    }
}
