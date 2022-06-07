package com.xiaomi.ai.a.a;

import com.xiaomi.ai.a.a;
import com.xiaomi.ai.b.b;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.b.g;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class d extends a {
    private String d;
    private String e;
    private String f;

    public d(com.xiaomi.ai.core.a aVar) {
        super(7, aVar);
        c();
    }

    private String a(String str) {
        return str == null ? "" : str;
    }

    private String a(String str, String str2, String str3, String str4, URI uri) {
        String a = a("GET", str4, uri);
        Logger.a("ServerAuthProvider", "StringToSign " + a);
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getUrlDecoder().decode(str3), "HmacSHA256");
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(secretKeySpec);
            return String.format("%s client_id:%s,key_id:%s,signature:%s", "DS-SIGNATURE-V1", str, str2, b.a(instance.doFinal(a.getBytes("utf-8"))));
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
            Logger.d("ServerAuthProvider", Logger.throwableToString(e));
            return null;
        }
    }

    private String a(String str, String str2, URI uri) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("\n");
        stringBuffer.append(a(uri.getPath()));
        stringBuffer.append("\n");
        stringBuffer.append(a(uri.getQuery()));
        stringBuffer.append("\n");
        stringBuffer.append(str2);
        stringBuffer.append("\n");
        stringBuffer.append(a(uri.getHost()));
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    private void c() {
        this.d = this.b.getAivsConfig().getString(AivsConfig.Auth.CLIENT_ID);
        if (!f.a(this.d)) {
            this.e = this.b.getAivsConfig().getString(AivsConfig.Auth.ServerAuth.KEY);
            if (!f.a(this.e)) {
                this.f = this.b.getAivsConfig().getString(AivsConfig.Auth.ServerAuth.SECRET);
                if (f.a(this.f)) {
                    Logger.d("ServerAuthProvider", "initProvider: AivsConfig.Auth.ServerAuth.SECRET is not set");
                    throw new IllegalArgumentException("AivsConfig.Auth.ServerAuth.SECRET is not set");
                }
                return;
            }
            Logger.d("ServerAuthProvider", "initProvider: AivsConfig.Auth.ServerAuth.KEY is not set");
            throw new IllegalArgumentException("AivsConfig.Auth.ServerAuth.KEY is not set");
        }
        Logger.d("ServerAuthProvider", "initProvider: CLIENT_ID is not set");
        throw new IllegalArgumentException("CLIENT_ID is not set");
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2) {
        return null;
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2, Map<String, String> map) {
        String a = g.a();
        try {
            if (this.b.getHttpDns() == null) {
                return null;
            }
            URI uri = new URI(this.b.getHttpDns().b());
            Logger.a("ServerAuthProvider", "getAuthHeader mDate=" + a);
            if (map != null) {
                map.put("X-Xiaomi-Date", a);
            }
            return a(this.d, this.e, this.f, a, uri);
        } catch (URISyntaxException unused) {
            return null;
        }
    }
}
