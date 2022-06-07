package com.xiaomi.micolauncher.api.interceptor;

import android.text.TextUtils;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.service.AiMiService;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class ParamsInterceptor implements Interceptor {
    public static final String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
    public static final String READ_TIMEOUT = "READ_TIMEOUT";
    public static final String WRITE_TIMEOUT = "WRITE_TIMEOUT";

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (request.url().host().contains(ApiConstants.API_SERVER_BASE_URL)) {
            Request.Builder newBuilder = request.newBuilder();
            if (request.method().equals("GET")) {
                newBuilder.url(request.url().newBuilder().addQueryParameter("requestId", a()).build());
            } else if (request.method().equals("POST")) {
                RequestBody body = request.body();
                if (body instanceof FormBody) {
                    FormBody.Builder builder = new FormBody.Builder();
                    FormBody formBody = (FormBody) body;
                    for (int i = 0; i < formBody.size(); i++) {
                        builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                    newBuilder.post(builder.addEncoded("requestId", a()).build());
                }
            }
            return chain.proceed(newBuilder.build());
        } else if (!request.url().uri().getPath().equals(AiMiService.UPLOAD_INTERCOM_ORIGIN_AUDIO)) {
            return chain.proceed(request);
        } else {
            int connectTimeoutMillis = chain.connectTimeoutMillis();
            int readTimeoutMillis = chain.readTimeoutMillis();
            int writeTimeoutMillis = chain.writeTimeoutMillis();
            String header = request.header(CONNECT_TIMEOUT);
            String header2 = request.header(READ_TIMEOUT);
            String header3 = request.header(WRITE_TIMEOUT);
            if (!TextUtils.isEmpty(header)) {
                connectTimeoutMillis = Integer.parseInt(header);
            }
            if (!TextUtils.isEmpty(header2)) {
                readTimeoutMillis = Integer.parseInt(header2);
            }
            if (!TextUtils.isEmpty(header3)) {
                writeTimeoutMillis = Integer.parseInt(header3);
            }
            return chain.withConnectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS).withReadTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS).withWriteTimeout(writeTimeoutMillis, TimeUnit.MILLISECONDS).proceed(request);
        }
    }

    private static String a() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return sb.toString();
    }
}
