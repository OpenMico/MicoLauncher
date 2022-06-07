package org.eclipse.jetty.client.security;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.onetrack.api.b;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.util.TypeUtil;

/* loaded from: classes5.dex */
public class DigestAuthentication implements Authentication {
    private static final String NC = "00000001";
    Map details;
    Realm securityRealm;

    public DigestAuthentication(Realm realm, Map map) {
        this.securityRealm = realm;
        this.details = map;
    }

    @Override // org.eclipse.jetty.client.security.Authentication
    public void setCredentials(HttpExchange httpExchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Digest");
        sb.append(StringUtils.SPACE);
        sb.append("username");
        sb.append('=');
        sb.append('\"');
        sb.append(this.securityRealm.getPrincipal());
        sb.append('\"');
        sb.append(", ");
        sb.append("realm");
        sb.append('=');
        sb.append('\"');
        sb.append(String.valueOf(this.details.get("realm")));
        sb.append('\"');
        sb.append(", ");
        sb.append("nonce");
        sb.append('=');
        sb.append('\"');
        sb.append(String.valueOf(this.details.get("nonce")));
        sb.append('\"');
        sb.append(", ");
        sb.append(MiSoundBoxCommandExtras.URI);
        sb.append('=');
        sb.append('\"');
        sb.append(httpExchange.getURI());
        sb.append('\"');
        sb.append(", ");
        sb.append("algorithm");
        sb.append('=');
        sb.append(String.valueOf(this.details.get("algorithm")));
        String newCnonce = newCnonce(httpExchange, this.securityRealm, this.details);
        sb.append(", ");
        sb.append(b.I);
        sb.append('=');
        sb.append('\"');
        sb.append(newResponse(newCnonce, httpExchange, this.securityRealm, this.details));
        sb.append('\"');
        sb.append(", ");
        sb.append("qop");
        sb.append('=');
        sb.append(String.valueOf(this.details.get("qop")));
        sb.append(", ");
        sb.append("nc");
        sb.append('=');
        sb.append(NC);
        sb.append(", ");
        sb.append("cnonce");
        sb.append('=');
        sb.append('\"');
        sb.append(newCnonce);
        sb.append('\"');
        httpExchange.setRequestHeader("Authorization", new String(sb.toString().getBytes("ISO-8859-1")));
    }

    protected String newResponse(String str, HttpExchange httpExchange, Realm realm, Map map) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(realm.getPrincipal().getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(String.valueOf(map.get("realm")).getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(realm.getCredentials().getBytes("ISO-8859-1"));
            byte[] digest = instance.digest();
            instance.reset();
            instance.update(httpExchange.getMethod().getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(httpExchange.getURI().getBytes("ISO-8859-1"));
            byte[] digest2 = instance.digest();
            instance.update(TypeUtil.toString(digest, 16).getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(String.valueOf(map.get("nonce")).getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(NC.getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(str.getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(String.valueOf(map.get("qop")).getBytes("ISO-8859-1"));
            instance.update((byte) 58);
            instance.update(TypeUtil.toString(digest2, 16).getBytes("ISO-8859-1"));
            return encode(instance.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String newCnonce(HttpExchange httpExchange, Realm realm, Map map) {
        try {
            return encode(MessageDigest.getInstance("MD5").digest(String.valueOf(System.currentTimeMillis()).getBytes("ISO-8859-1")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encode(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            sb.append(Integer.toHexString((bArr[i] & 240) >>> 4));
            sb.append(Integer.toHexString(bArr[i] & 15));
        }
        return sb.toString();
    }
}
