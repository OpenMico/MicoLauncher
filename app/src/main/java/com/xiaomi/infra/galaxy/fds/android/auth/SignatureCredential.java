package com.xiaomi.infra.galaxy.fds.android.auth;

import com.google.common.collect.LinkedListMultimap;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.auth.signature.SignAlgorithm;
import com.xiaomi.infra.galaxy.fds.auth.signature.Signer;
import com.xiaomi.infra.galaxy.fds.model.HttpMethod;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;

/* loaded from: classes3.dex */
public class SignatureCredential implements GalaxyFDSCredential {
    private static final ThreadLocal<SimpleDateFormat> a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.android.auth.SignatureCredential.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };
    private final String b;
    private final String c;

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public String addParam(String str) {
        return str;
    }

    public SignatureCredential(String str, String str2) {
        this.b = str;
        this.c = str2;
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public void addHeader(HttpRequestBase httpRequestBase) throws GalaxyFDSClientException {
        httpRequestBase.setHeader(Common.DATE, a.get().format(new Date()));
        try {
            URI uri = httpRequestBase.getURI();
            LinkedListMultimap create = LinkedListMultimap.create();
            Header[] allHeaders = httpRequestBase.getAllHeaders();
            for (Header header : allHeaders) {
                create.put(header.getName(), header.getValue());
            }
            httpRequestBase.setHeader("Authorization", Signer.getAuthorizationHeader(HttpMethod.valueOf(httpRequestBase.getMethod()), uri, create, this.b, this.c, SignAlgorithm.HmacSHA1));
        } catch (InvalidKeyException e) {
            throw new GalaxyFDSClientException("Fail to get signature for request:" + httpRequestBase, e);
        } catch (NoSuchAlgorithmException e2) {
            throw new GalaxyFDSClientException("Fail to get signature for request:" + httpRequestBase, e2);
        }
    }
}
