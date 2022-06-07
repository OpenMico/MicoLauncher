package com.xiaomi.smarthome.library.http;

import com.xiaomi.smarthome.library.http.async.AsyncHandler;
import com.xiaomi.smarthome.library.http.async.HttpAsyncHandle;
import com.xiaomi.smarthome.library.http.sync.SyncHandler;
import com.xiaomi.smarthome.library.http.util.HeaderUtil;
import com.xiaomi.smarthome.library.http.util.KeyValuePairUtil;
import com.xiaomi.smarthome.library.http.util.RequestParamUtil;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes4.dex */
public class HttpApi {
    private static OkHttpClient a;
    private static Object b = new Object();

    public static <R> R sendRequest(OkHttpClient okHttpClient, Request request, SyncHandler<R> syncHandler) throws Exception {
        if (okHttpClient == null) {
            throw new RuntimeException("client is null");
        } else if (syncHandler != null) {
            Request.Builder builder = new Request.Builder();
            Headers headers = HeaderUtil.getHeaders(request.getHeaders());
            if (headers != null) {
                builder.headers(headers);
            }
            if (request.getMethod().equals("POST")) {
                builder.url(request.getUrl()).post(KeyValuePairUtil.getRequestBody(request.getQueryParams()));
            } else if (request.getMethod().equals("GET")) {
                builder.url(KeyValuePairUtil.getUrlWithQueryString(request.getUrl(), request.getQueryParams()));
            } else {
                throw new RuntimeException("method unsupported");
            }
            try {
                return syncHandler.processResponse(okHttpClient.newCall(builder.build()).execute());
            } catch (IOException e) {
                throw new RuntimeException("failure:" + e.getMessage());
            }
        } else {
            throw new RuntimeException("handler is null");
        }
    }

    public static HttpAsyncHandle sendRequest(OkHttpClient okHttpClient, Request request, final AsyncHandler asyncHandler) {
        if (okHttpClient != null) {
            Request.Builder builder = new Request.Builder();
            Headers headers = HeaderUtil.getHeaders(request.getHeaders());
            if (headers != null) {
                builder.headers(headers);
            }
            if (request.getMethod().equals("POST")) {
                builder.url(request.getUrl()).post(KeyValuePairUtil.getRequestBody(request.getQueryParams()));
            } else if (request.getMethod().equals("GET")) {
                builder.url(KeyValuePairUtil.getUrlWithQueryString(request.getUrl(), request.getQueryParams()));
            } else {
                throw new RuntimeException("method unsupported");
            }
            Call newCall = okHttpClient.newCall(builder.build());
            newCall.enqueue(new Callback() { // from class: com.xiaomi.smarthome.library.http.HttpApi.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    AsyncHandler asyncHandler2 = AsyncHandler.this;
                    if (asyncHandler2 != null) {
                        asyncHandler2.processFailure(call, iOException);
                    }
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    AsyncHandler asyncHandler2 = AsyncHandler.this;
                    if (asyncHandler2 != null) {
                        asyncHandler2.processResponse(response);
                    }
                }
            });
            return new HttpAsyncHandle(newCall);
        }
        throw new RuntimeException("client is null");
    }

    public static HttpAsyncHandle sendRequestDefault(Request request, final AsyncHandler asyncHandler) {
        Request.Builder builder = new Request.Builder();
        Headers headers = HeaderUtil.getHeaders(request.getHeaders());
        if (headers != null) {
            builder.headers(headers);
        }
        if (request.getMethod().equals("POST")) {
            builder.url(request.getUrl()).post(KeyValuePairUtil.getRequestBody(request.getQueryParams()));
        } else if (request.getMethod().equals("GET")) {
            builder.url(KeyValuePairUtil.getUrlWithQueryString(request.getUrl(), request.getQueryParams()));
        } else {
            throw new RuntimeException("method unsupported");
        }
        Call newCall = a().newCall(builder.build());
        newCall.enqueue(new Callback() { // from class: com.xiaomi.smarthome.library.http.HttpApi.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                AsyncHandler asyncHandler2 = AsyncHandler.this;
                if (asyncHandler2 != null) {
                    asyncHandler2.processFailure(call, iOException);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                AsyncHandler asyncHandler2 = AsyncHandler.this;
                if (asyncHandler2 != null) {
                    asyncHandler2.processResponse(response);
                }
            }
        });
        return new HttpAsyncHandle(newCall);
    }

    @Deprecated
    public static <R> R sendRequest(OkHttpClient okHttpClient, Request2 request2, SyncHandler<R> syncHandler) throws Exception {
        if (okHttpClient == null) {
            throw new RuntimeException("client is null");
        } else if (syncHandler != null) {
            Request.Builder builder = new Request.Builder();
            Headers headers = HeaderUtil.getHeaders(request2.getHeaders());
            if (headers != null) {
                builder.headers(headers);
            }
            if (request2.getMethod().equals("POST")) {
                builder.url(request2.getUrl()).post(RequestParamUtil.getRequestBody(request2.getQueryParams()));
            } else if (request2.getMethod().equals("GET")) {
                builder.url(RequestParamUtil.getUrlWithQueryString(request2.getUrl(), request2.getQueryParams()));
            } else {
                throw new RuntimeException("method unsupported");
            }
            try {
                return syncHandler.processResponse(okHttpClient.newCall(builder.build()).execute());
            } catch (IOException e) {
                throw new RuntimeException("failure:" + e.getMessage());
            }
        } else {
            throw new RuntimeException("handler is null");
        }
    }

    @Deprecated
    public static HttpAsyncHandle sendRequest(OkHttpClient okHttpClient, Request2 request2, final AsyncHandler asyncHandler) {
        if (okHttpClient != null) {
            Request.Builder builder = new Request.Builder();
            Headers headers = HeaderUtil.getHeaders(request2.getHeaders());
            if (headers != null) {
                builder.headers(headers);
            }
            if (request2.getMethod().equals("POST")) {
                builder.url(request2.getUrl()).post(RequestParamUtil.getRequestBody(request2.getQueryParams()));
            } else if (request2.getMethod().equals("GET")) {
                builder.url(RequestParamUtil.getUrlWithQueryString(request2.getUrl(), request2.getQueryParams()));
            } else {
                throw new RuntimeException("method unsupported");
            }
            Call newCall = okHttpClient.newCall(builder.build());
            newCall.enqueue(new Callback() { // from class: com.xiaomi.smarthome.library.http.HttpApi.3
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    AsyncHandler asyncHandler2 = AsyncHandler.this;
                    if (asyncHandler2 != null) {
                        asyncHandler2.processFailure(call, iOException);
                    }
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    AsyncHandler asyncHandler2 = AsyncHandler.this;
                    if (asyncHandler2 != null) {
                        asyncHandler2.processResponse(response);
                    }
                }
            });
            return new HttpAsyncHandle(newCall);
        }
        throw new RuntimeException("client is null");
    }

    private static OkHttpClient a() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = ClientUtil.create();
                }
            }
        }
        return a;
    }
}
