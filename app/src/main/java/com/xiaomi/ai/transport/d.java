package com.xiaomi.ai.transport;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/* loaded from: classes3.dex */
public class d implements Interceptor {
    private com.xiaomi.ai.core.a a;
    private a b;
    private byte[] c;
    private OkHttpClient d = new OkHttpClient();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a {
        byte[] a;
        String b;

        private a() {
        }
    }

    public d(com.xiaomi.ai.core.a aVar) {
        this.a = aVar;
    }

    private String a(Request request) {
        String httpUrl = request.url().toString();
        int indexOf = httpUrl.indexOf(63);
        if (indexOf <= 0) {
            return httpUrl;
        }
        String substring = httpUrl.substring(0, indexOf);
        String substring2 = httpUrl.substring(indexOf + 1);
        return substring + "?data=" + b(substring2);
    }

    private String a(RequestBody requestBody) {
        if (requestBody == null) {
            return null;
        }
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

    private String a(byte[] bArr) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr);
        long value = crc32.getValue();
        Formatter formatter = new Formatter();
        for (int i = 0; i < 4; i++) {
            formatter.format("%02x", Byte.valueOf((byte) value));
            value >>= 8;
        }
        return formatter.toString();
    }

    private PublicKey a(String str) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.xiaomi.ai.b.a.a(str.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("-----END PUBLIC KEY-----", "").replace("\n", ""), 0)));
    }

    private Request a(Request request, String str) {
        RequestBody requestBody;
        byte[] bArr = this.b.a;
        Request.Builder addHeader = request.newBuilder().url(a(request)).removeHeader("Date").addHeader("Date", str);
        String str2 = this.b.b;
        if (str2 == null) {
            addHeader.addHeader("AIVS-Encryption-Key", f()).addHeader("AIVS-Encryption-CRC", a(bArr));
        } else {
            addHeader.addHeader("AIVS-Encryption-Token", str2);
        }
        List<String> headers = request.headers("Authorization");
        addHeader.removeHeader("Authorization");
        for (String str3 : headers) {
            addHeader.addHeader("Authorization", b(str3));
        }
        if (request.method().equals("GET")) {
            addHeader.get();
        } else if (request.method().equals("POST")) {
            RequestBody body = request.body();
            MediaType contentType = body.contentType();
            if (MimeTypes.BASE_TYPE_APPLICATION.equals(contentType.type()) && "json".equals(contentType.subtype())) {
                requestBody = RequestBody.create(contentType, String.format("{ \"data\": \"%s\"}", b(a(body))));
            } else if (body instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("data", b(a(body)));
                requestBody = builder.build();
            }
            addHeader.post(requestBody);
        }
        return addHeader.build();
    }

    private Response a(Response response) {
        ResponseBody body = response.body();
        if (body == null) {
            return response;
        }
        for (Cookie cookie : Cookie.parseAll(response.request().url(), response.headers())) {
            if (cookie.name().equals("AIVS-Encryption-Token")) {
                a(cookie.value(), cookie.expiresAt());
            }
        }
        if (!response.header("AIVS-Encryption-Body", "false").equals("true")) {
            return response;
        }
        return response.newBuilder().body(ResponseBody.create(body.contentType(), c(body.string()))).build();
    }

    private byte[] a(byte[] bArr, Key key) {
        return a(bArr, key, true);
    }

    private byte[] a(byte[] bArr, Key key, boolean z) {
        int i = z ? 1 : 2;
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(i, key);
        return instance.doFinal(bArr);
    }

    private String b(String str) {
        return com.xiaomi.ai.b.a.b(a(1, str.getBytes(Charset.forName("UTF-8"))), 10);
    }

    private byte[] b(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            Logger.d("LiteHttpCryptInterceptor", Logger.throwableToString(e));
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0056 A[Catch: Exception -> 0x007f, all -> 0x007d, TryCatch #0 {Exception -> 0x007f, blocks: (B:4:0x0003, B:7:0x003c, B:11:0x0045, B:17:0x0056, B:18:0x0073), top: B:28:0x0003, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0073 A[Catch: Exception -> 0x007f, all -> 0x007d, TRY_LEAVE, TryCatch #0 {Exception -> 0x007f, blocks: (B:4:0x0003, B:7:0x003c, B:11:0x0045, B:17:0x0056, B:18:0x0073), top: B:28:0x0003, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.ai.transport.d.a c() {
        /*
            r9 = this;
            java.lang.String r0 = "http_aes_key_info"
            monitor-enter(r0)
            com.xiaomi.ai.transport.d$a r1 = new com.xiaomi.ai.transport.d$a     // Catch: Exception -> 0x007f, all -> 0x007d
            r2 = 0
            r1.<init>()     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r2 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.b r2 = r2.getListener()     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r3 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            java.lang.String r4 = "http_aes_key"
            java.lang.String r2 = r2.a(r3, r4)     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r3 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.b r3 = r3.getListener()     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r4 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            java.lang.String r5 = "http_aes_token"
            java.lang.String r3 = r3.a(r4, r5)     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r4 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.b r4 = r4.getListener()     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r5 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            java.lang.String r6 = "http_aes_expire_at"
            java.lang.String r4 = r4.a(r5, r6)     // Catch: Exception -> 0x007f, all -> 0x007d
            boolean r5 = com.xiaomi.ai.b.f.a(r4)     // Catch: Exception -> 0x007f, all -> 0x007d
            if (r5 == 0) goto L_0x003c
            r4 = 0
            goto L_0x0040
        L_0x003c:
            long r4 = java.lang.Long.parseLong(r4)     // Catch: Exception -> 0x007f, all -> 0x007d
        L_0x0040:
            r6 = 0
            if (r2 == 0) goto L_0x0053
            if (r3 == 0) goto L_0x0051
            long r7 = java.lang.System.currentTimeMillis()     // Catch: Exception -> 0x007f, all -> 0x007d
            long r4 = r4 - r7
            r7 = 10000(0x2710, double:4.9407E-320)
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 >= 0) goto L_0x0051
            goto L_0x0053
        L_0x0051:
            r4 = r6
            goto L_0x0054
        L_0x0053:
            r4 = 1
        L_0x0054:
            if (r4 == 0) goto L_0x0073
            byte[] r2 = r9.i()     // Catch: Exception -> 0x007f, all -> 0x007d
            r1.a = r2     // Catch: Exception -> 0x007f, all -> 0x007d
            r9.a()     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r2 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.b r2 = r2.getListener()     // Catch: Exception -> 0x007f, all -> 0x007d
            com.xiaomi.ai.core.a r3 = r9.a     // Catch: Exception -> 0x007f, all -> 0x007d
            java.lang.String r4 = "http_aes_key"
            byte[] r5 = r1.a     // Catch: Exception -> 0x007f, all -> 0x007d
            java.lang.String r5 = com.xiaomi.ai.b.a.b(r5, r6)     // Catch: Exception -> 0x007f, all -> 0x007d
            r2.a(r3, r4, r5)     // Catch: Exception -> 0x007f, all -> 0x007d
            goto L_0x007b
        L_0x0073:
            byte[] r2 = com.xiaomi.ai.b.a.a(r2, r6)     // Catch: Exception -> 0x007f, all -> 0x007d
            r1.a = r2     // Catch: Exception -> 0x007f, all -> 0x007d
            r1.b = r3     // Catch: Exception -> 0x007f, all -> 0x007d
        L_0x007b:
            monitor-exit(r0)     // Catch: all -> 0x007d
            return r1
        L_0x007d:
            r1 = move-exception
            goto L_0x0093
        L_0x007f:
            r1 = move-exception
            java.lang.String r2 = "LiteHttpCryptInterceptor"
            java.lang.String r3 = com.xiaomi.ai.log.Logger.throwableToString(r1)     // Catch: all -> 0x007d
            com.xiaomi.ai.log.Logger.d(r2, r3)     // Catch: all -> 0x007d
            java.io.IOException r2 = new java.io.IOException     // Catch: all -> 0x007d
            java.lang.String r1 = r1.toString()     // Catch: all -> 0x007d
            r2.<init>(r1)     // Catch: all -> 0x007d
            throw r2     // Catch: all -> 0x007d
        L_0x0093:
            monitor-exit(r0)     // Catch: all -> 0x007d
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.transport.d.c():com.xiaomi.ai.transport.d$a");
    }

    private String c(String str) {
        return new String(a(2, com.xiaomi.ai.b.a.a(str, 8)), Charset.forName("UTF-8"));
    }

    private JsonNode d() {
        synchronized ("http_pubkey_info") {
            String a2 = this.a.getListener().a(this.a, "http_pubkey_info");
            if (!f.a(a2)) {
                JsonNode readTree = APIUtils.getObjectMapper().readTree(a2);
                if (readTree == null) {
                    return h();
                } else if (!readTree.has("expire_at") || !readTree.has("public_key")) {
                    return h();
                } else if (readTree.path("expire_at").asLong() - System.currentTimeMillis() >= 10000) {
                    return readTree;
                } else {
                    return h();
                }
            } else {
                return h();
            }
        }
    }

    private String e() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(new Date());
    }

    private String f() {
        JsonNode d = d();
        String b = com.xiaomi.ai.b.a.b(a(this.b.a, a(d.path("public_key").asText())), 10);
        return "pubkeyid:" + d.path("key_id").asText() + ",key:" + b;
    }

    private String g() {
        String str;
        StringBuilder sb = new StringBuilder();
        int i = this.a.getAivsConfig().getInt(AivsConfig.ENV);
        if (i == 2) {
            str = "http://account-staging.ai.xiaomi.com/ws/session/rsa/public";
        } else {
            if (i != 1) {
            }
            str = "https://account.ai.xiaomi.com/ws/session/rsa/public";
        }
        sb.append(str);
        String string = this.a.getAivsConfig().getString(AivsConfig.Auth.CLIENT_ID);
        if (this.a.getClientInfo().getDeviceId().isPresent()) {
            sb.append("?client_id=");
            sb.append(string);
            sb.append("&key_length=2048&device_id=");
            sb.append(this.a.getClientInfo().getDeviceId().get());
            return sb.toString();
        }
        throw new IllegalArgumentException("device id not set");
    }

    private JsonNode h() {
        try {
            Response execute = this.d.newCall(new Request.Builder().url(g()).get().build()).execute();
            if (execute.isSuccessful()) {
                String string = execute.body().string();
                JsonNode readTree = APIUtils.getObjectMapper().readTree(string);
                if (!readTree.has("key_id") || !readTree.has("expire_at") || !readTree.has("public_key")) {
                    Logger.d("LiteHttpCryptInterceptor", "refreshPublicKeyInfo: invalid body " + string);
                    throw new Exception("invalid body " + string);
                }
                a(readTree.path("public_key").asText());
                Logger.b("LiteHttpCryptInterceptor", "new public key is valid");
                this.a.getListener().a(this.a, "http_pubkey_info", readTree.toString());
                return readTree;
            }
            String string2 = execute.body().string();
            Logger.d("LiteHttpCryptInterceptor", "refreshPublicKeyInfo: " + execute + ", body=" + string2);
            throw new Exception(execute.toString());
        } catch (Exception e) {
            Logger.d("LiteHttpCryptInterceptor", Logger.throwableToString(e));
            throw e;
        }
    }

    private byte[] i() {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(UUID.randomUUID().toString().getBytes());
        instance.init(128, secureRandom);
        return instance.generateKey().getEncoded();
    }

    void a() {
        synchronized ("http_aes_key_info") {
            this.a.getListener().b(this.a, "http_aes_key");
            this.a.getListener().b(this.a, "http_aes_token");
            this.a.getListener().b(this.a, "http_aes_expire_at");
        }
    }

    public void a(String str, long j) {
        synchronized ("http_aes_key_info") {
            Logger.b("LiteHttpCryptInterceptor", "update aes token");
            this.a.getListener().a(this.a, "http_aes_token", str);
            this.a.getListener().a(this.a, "http_aes_expire_at", Long.toString(j));
        }
    }

    public byte[] a(int i, byte[] bArr) {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(i, new SecretKeySpec(this.b.a, "AES"), new IvParameterSpec(this.c));
        return instance.doFinal(bArr);
    }

    void b() {
        synchronized ("http_pubkey_info") {
            this.a.getListener().b(this.a, "http_pubkey_info");
        }
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) {
        this.b = c();
        String e = e();
        this.c = b(e.getBytes("UTF-8"));
        try {
            return a(chain.proceed(a(chain.request(), e)));
        } catch (Exception e2) {
            Logger.d("LiteHttpCryptInterceptor", Logger.throwableToString(e2));
            a();
            b();
            Logger.d("LiteHttpCryptInterceptor", "clear all auth info");
            throw new IOException(e2);
        }
    }
}
