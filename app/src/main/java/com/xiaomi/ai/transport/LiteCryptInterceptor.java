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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Call;
import okhttp3.Callback;
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
public class LiteCryptInterceptor implements Interceptor {
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

    public LiteCryptInterceptor(com.xiaomi.ai.core.a aVar) {
        this.a = aVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[Catch: Exception -> 0x00b6, all -> 0x00b4, TryCatch #1 {Exception -> 0x00b6, blocks: (B:4:0x0003, B:7:0x003c, B:11:0x0045, B:17:0x0057, B:18:0x008f), top: B:29:0x0003, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008f A[Catch: Exception -> 0x00b6, all -> 0x00b4, TRY_LEAVE, TryCatch #1 {Exception -> 0x00b6, blocks: (B:4:0x0003, B:7:0x003c, B:11:0x0045, B:17:0x0057, B:18:0x008f), top: B:29:0x0003, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.ai.transport.LiteCryptInterceptor.a a() {
        /*
            r11 = this;
            java.lang.String r0 = "aes_key_info"
            monitor-enter(r0)
            com.xiaomi.ai.transport.LiteCryptInterceptor$a r1 = new com.xiaomi.ai.transport.LiteCryptInterceptor$a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r2 = 0
            r1.<init>()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r2 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.b r2 = r2.getListener()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r3 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r4 = "aes_key"
            java.lang.String r2 = r2.a(r3, r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r3 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.b r3 = r3.getListener()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r4 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r5 = "aes_token"
            java.lang.String r3 = r3.a(r4, r5)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r4 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.b r4 = r4.getListener()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r5 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r6 = "aes_expire_at"
            java.lang.String r4 = r4.a(r5, r6)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            boolean r5 = com.xiaomi.ai.b.f.a(r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            if (r5 == 0) goto L_0x003c
            r4 = 0
            goto L_0x0040
        L_0x003c:
            long r4 = java.lang.Long.parseLong(r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
        L_0x0040:
            r6 = 0
            if (r2 == 0) goto L_0x0054
            if (r3 == 0) goto L_0x0052
            long r7 = java.lang.System.currentTimeMillis()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            long r7 = r4 - r7
            r9 = 10000(0x2710, double:4.9407E-320)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 >= 0) goto L_0x0052
            goto L_0x0054
        L_0x0052:
            r7 = r6
            goto L_0x0055
        L_0x0054:
            r7 = 1
        L_0x0055:
            if (r7 == 0) goto L_0x008f
            java.lang.String r2 = "LiteCryptInterceptor"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r3.<init>()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r7 = "getCurrentAesKeyOrToken: expireAt="
            r3.append(r7)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r3.append(r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r4 = " , refresh aes key"
            r3.append(r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r3 = r3.toString()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.log.Logger.b(r2, r3)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            byte[] r2 = r11.g()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r1.a = r2     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r11.clearAes()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r2 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.b r2 = r2.getListener()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.core.a r3 = r11.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r4 = "aes_key"
            byte[] r5 = r1.a     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r5 = com.xiaomi.ai.b.a.b(r5, r6)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r2.a(r3, r4, r5)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            goto L_0x00b2
        L_0x008f:
            java.lang.String r7 = "LiteCryptInterceptor"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r8.<init>()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r9 = "getCurrentAesKeyOrToken: expireAt="
            r8.append(r9)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r8.append(r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r4 = " , use cached aes key"
            r8.append(r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            java.lang.String r4 = r8.toString()     // Catch: Exception -> 0x00b6, all -> 0x00b4
            com.xiaomi.ai.log.Logger.b(r7, r4)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            byte[] r2 = com.xiaomi.ai.b.a.a(r2, r6)     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r1.a = r2     // Catch: Exception -> 0x00b6, all -> 0x00b4
            r1.b = r3     // Catch: Exception -> 0x00b6, all -> 0x00b4
        L_0x00b2:
            monitor-exit(r0)     // Catch: all -> 0x00b4
            return r1
        L_0x00b4:
            r1 = move-exception
            goto L_0x00ca
        L_0x00b6:
            r1 = move-exception
            java.lang.String r2 = "LiteCryptInterceptor"
            java.lang.String r3 = com.xiaomi.ai.log.Logger.throwableToString(r1)     // Catch: all -> 0x00b4
            com.xiaomi.ai.log.Logger.d(r2, r3)     // Catch: all -> 0x00b4
            java.io.IOException r2 = new java.io.IOException     // Catch: all -> 0x00b4
            java.lang.String r1 = r1.toString()     // Catch: all -> 0x00b4
            r2.<init>(r1)     // Catch: all -> 0x00b4
            throw r2     // Catch: all -> 0x00b4
        L_0x00ca:
            monitor-exit(r0)     // Catch: all -> 0x00b4
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.transport.LiteCryptInterceptor.a():com.xiaomi.ai.transport.LiteCryptInterceptor$a");
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

    /* JADX INFO: Access modifiers changed from: private */
    public PublicKey a(String str) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.xiaomi.ai.b.a.a(str.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("-----END PUBLIC KEY-----", "").replace("\n", ""), 0)));
    }

    private Request a(Request request, String str) {
        RequestBody requestBody;
        byte[] bArr = this.b.a;
        Request.Builder addHeader = request.newBuilder().url(a(request)).removeHeader("Date").addHeader("Date", str);
        String str2 = this.b.b;
        if (str2 == null) {
            addHeader.addHeader("AIVS-Encryption-Key", d()).addHeader("AIVS-Encryption-CRC", a(bArr));
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
                updateAesToken(cookie.value(), cookie.expiresAt());
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

    private JsonNode b() {
        synchronized ("pubkey_info") {
            JsonNode jsonNode = null;
            String a2 = this.a.getListener().a(this.a, "pubkey_info");
            if (!f.a(a2) && (jsonNode = APIUtils.getObjectMapper().readTree(a2)) != null && jsonNode.has("expire_at") && jsonNode.has("public_key")) {
                long asLong = jsonNode.path("expire_at").asLong();
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.setSeed(UUID.randomUUID().toString().getBytes());
                long nextInt = secureRandom.nextInt(1800000) + 10000;
                Logger.b("LiteCryptInterceptor", "getPubkeyInfo: expireAt:" + asLong + ", now:" + System.currentTimeMillis() + " , aheadTime:" + nextInt);
                if (asLong - System.currentTimeMillis() >= nextInt) {
                    return jsonNode;
                }
                Logger.c("LiteCryptInterceptor", "getPubkeyInfo: public key expired");
            }
            JsonNode f = f();
            if (f != null) {
                return f;
            }
            if (jsonNode != null) {
                Logger.c("LiteCryptInterceptor", "getPubkeyInfo: get public key failed, use expired key");
                return jsonNode;
            }
            throw new Exception("refreshPublicKeyInfo failed!");
        }
    }

    private String b(String str) {
        return com.xiaomi.ai.b.a.b(aesCrypt(1, str.getBytes(Charset.forName("UTF-8"))), 10);
    }

    private byte[] b(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            Logger.d("LiteCryptInterceptor", Logger.throwableToString(e));
            return null;
        }
    }

    private String c() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(new Date());
    }

    private String c(String str) {
        return new String(aesCrypt(2, com.xiaomi.ai.b.a.a(str, 8)), Charset.forName("UTF-8"));
    }

    private String d() {
        JsonNode b = b();
        String b2 = com.xiaomi.ai.b.a.b(a(this.b.a, a(b.path("public_key").asText())), 10);
        return "pubkeyid:" + b.path("key_id").asText() + ",key:" + b2;
    }

    private String e() {
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

    private JsonNode f() {
        Logger.b("LiteCryptInterceptor", "refreshPublicKeyInfo");
        this.d.newCall(new Request.Builder().url(e()).get().build()).enqueue(new Callback() { // from class: com.xiaomi.ai.transport.LiteCryptInterceptor.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Logger.d("LiteCryptInterceptor", Logger.throwableToString(iOException));
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        JsonNode readTree = APIUtils.getObjectMapper().readTree(string);
                        if (!readTree.has("key_id") || !readTree.has("expire_at") || !readTree.has("public_key")) {
                            Logger.d("LiteCryptInterceptor", "refreshPublicKeyInfo: invalid body " + string);
                            throw new Exception("invalid body " + string);
                        }
                        LiteCryptInterceptor.this.a(readTree.path("public_key").asText());
                        Logger.b("LiteCryptInterceptor", "new public key is valid");
                        LiteCryptInterceptor.this.a.getListener().a(LiteCryptInterceptor.this.a, "pubkey_info", readTree.toString());
                        return;
                    }
                    String string2 = response.body().string();
                    Logger.d("LiteCryptInterceptor", "refreshPublicKeyInfo: " + response + ", body=" + string2);
                    throw new Exception(response.toString());
                } catch (Exception e) {
                    Logger.d("LiteCryptInterceptor", Logger.throwableToString(e));
                }
            }
        });
        try {
            return this.a.getAivsConfig().getInt(AivsConfig.ENV) == 2 ? APIUtils.getObjectMapper().readTree("{\"key_id\": \"iJne1qqnSWxYsjJq54vnKg==\",\"public_key\": \"-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoHVeIxKvdR/x6jljxTrk\\nWEh1pAEj1ZQy1m+myMfxOeRMuYd9OTOE60UC79lx2qt5qmUZegBqVM3Oorcurzy7\\ndCVtWOJE8AuXrlRtMbGGeitpKD8pc3keOXJKEwZ/I47Ara/5isjYfZ0aWxBVyhYj\\nNXku/JT0VjzgYWAc5a1almaPSPG4WY76K8qSvJIvvB4nOC0YzEPtX2WPk7/cU8k9\\n91Wn0wK7n+0xVxhrSn00iNu8cvChXP6ePjL5869z2P5Gv3YONvSiDbcDlW+cxKZM\\nabaRLxqDH6zoiUE/3aTwb80M3QCuqBW1/857yv8QcA/C+JdHPwpZheOLj4rd8ST7\\nVQIDAQAB\\n-----END PUBLIC KEY-----\\n\"}") : APIUtils.getObjectMapper().readTree("{\"key_id\": \"CZWhJoB4ihbNyMcTTbWh/g==\",\"public_key\": \"-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsca6B9KeE39zsuIuE+iH\\nXPR0QDjb7Tq0nBYP9USiWFfPE+ER1CwmIXPEHMpN2YumgzatonnScJJs3d1ZyuTH\\nlIpe6bjmQl7TaQGlMBhjKAhsSSFfIud62nW7UCNsBpqBaW4XmQ6DKUc9OorNA2ME\\ngsNtW9b9L8VFXfH0vrEH8gKjSwUOkBQNAg8H9vPh5bUY+JN/ELNsFDMMTzCxJy7K\\n+/o/bLvkOt137knMeR1kCNzBwcVZusnn3CsQ89+P4YU6AaE6MTDJqDOpud1MMwDH\\nnzXGHK3GFhp0uDjFdE374tOrNp/A8y8IYkfKNJoRA+mRQnvp+X6H79wj9/jIXxrn\\nHwIDAQAB\\n-----END PUBLIC KEY-----\\n\"}");
        } catch (IOException e) {
            Logger.d("LiteCryptInterceptor", Logger.throwableToString(e));
            return null;
        }
    }

    private byte[] g() {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(UUID.randomUUID().toString().getBytes());
        instance.init(128, secureRandom);
        return instance.generateKey().getEncoded();
    }

    public byte[] aesCrypt(int i, byte[] bArr) {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(i, new SecretKeySpec(this.b.a, "AES"), new IvParameterSpec(this.c));
        return instance.doFinal(bArr);
    }

    public void clearAes() {
        synchronized ("aes_key_info") {
            this.a.getListener().b(this.a, "aes_key");
            this.a.getListener().b(this.a, "aes_token");
            this.a.getListener().b(this.a, "aes_expire_at");
        }
    }

    public void clearPubkey() {
        synchronized ("pubkey_info") {
            this.a.getListener().b(this.a, "pubkey_info");
        }
    }

    public Map<String, String> getAuthHeaders(boolean z) {
        HashMap hashMap = new HashMap();
        String a2 = this.a.getAuthProvider().a(z, true, (Map<String, String>) hashMap);
        if (a2 == null) {
            return null;
        }
        try {
            this.b = a();
            String c = c();
            hashMap.put("Date", c);
            this.c = b(c.getBytes("UTF-8"));
            byte[] bArr = this.b.a;
            String str = this.b.b;
            if (str == null) {
                String a3 = a(bArr);
                hashMap.put("AIVS-Encryption-Key", d());
                hashMap.put("AIVS-Encryption-CRC", a3);
            } else {
                hashMap.put("AIVS-Encryption-Token", str);
            }
            hashMap.put("Authorization", b(a2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) {
        this.b = a();
        String c = c();
        this.c = b(c.getBytes("UTF-8"));
        try {
            return a(chain.proceed(a(chain.request(), c)));
        } catch (Exception e) {
            Logger.d("LiteCryptInterceptor", Logger.throwableToString(e));
            clearAes();
            clearPubkey();
            Logger.d("LiteCryptInterceptor", "clear all auth info");
            throw new IOException(e);
        }
    }

    public void updateAesToken(String str, long j) {
        synchronized ("aes_key_info") {
            Logger.b("LiteCryptInterceptor", "update aes token");
            this.a.getListener().a(this.a, "aes_token", str);
            this.a.getListener().a(this.a, "aes_expire_at", Long.toString(j));
        }
    }
}
