package com.xiaomi.phonenum.obtain;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.phonenum.Constant;
import com.xiaomi.phonenum.bean.HttpError;
import com.xiaomi.phonenum.http.HttpClient;
import com.xiaomi.phonenum.http.HttpClientConfig;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.http.UrlConnHttpFactory;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import com.xiaomi.phonenum.utils.MapUtil;
import com.xiaomi.phonenum.utils.RSAEncryptUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class EncryptHttpClient implements HttpClient {
    private HttpClient a;
    private RSAEncryptUtil b;
    private Logger c = LoggerManager.getLogger();

    EncryptHttpClient(@NonNull HttpClient httpClient) {
        this.a = httpClient;
        try {
            this.b = new RSAEncryptUtil();
        } catch (RSAEncryptUtil.EncryptException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.phonenum.http.HttpClient
    public Response excute(@NonNull Request request) throws IOException {
        HashMap hashMap;
        if (!request.url.startsWith(Constant.BASE_URL)) {
            return this.a.excute(request);
        }
        if (this.b == null) {
            return HttpError.ENCRYPT.result();
        }
        Request request2 = null;
        try {
            URI uri = request.uri;
            ArrayList arrayList = new ArrayList();
            arrayList.add(MapUtil.joinToQuery(request.formBody));
            arrayList.add(uri.getQuery());
            String join = MapUtil.join(arrayList, MusicGroupListActivity.SPECIAL_SYMBOL);
            if (!TextUtils.isEmpty(join)) {
                RSAEncryptUtil.EncryptResult encrypt = this.b.encrypt(join);
                hashMap = new HashMap();
                hashMap.put("params", encrypt.content);
                hashMap.put("secretKey", encrypt.encryptedKey);
            } else {
                hashMap = null;
            }
            request2 = new Request.Builder().url(new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString()).headers(request.headers).formBody(hashMap).build();
        } catch (RSAEncryptUtil.EncryptException e) {
            this.c.e("EncryptHttpClient", "encryptedRequest Exception" + request, e);
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException("unexpected newQuery: " + request.url);
        }
        if (request2 == null) {
            return HttpError.ENCRYPT.result();
        }
        Response excute = this.a.excute(request2);
        if (excute == null) {
            return HttpError.DECRYPT.result();
        }
        if (excute.body == null) {
            return excute;
        }
        try {
            return new Response.Builder(excute).body(this.b.aesDecrypt(excute.body)).build();
        } catch (RSAEncryptUtil.EncryptException e2) {
            this.c.e("EncryptHttpClient", "decryptedResponse Exception" + excute, e2);
            return HttpError.DECRYPT.result();
        }
    }

    /* loaded from: classes4.dex */
    public static class HttpFactory extends UrlConnHttpFactory {
        public HttpFactory(Context context) {
            super(context);
        }

        @Override // com.xiaomi.phonenum.http.UrlConnHttpFactory, com.xiaomi.phonenum.http.HttpFactory
        public HttpClient createHttpClient(HttpClientConfig httpClientConfig) {
            return new EncryptHttpClient(super.createHttpClient(httpClientConfig));
        }
    }
}
