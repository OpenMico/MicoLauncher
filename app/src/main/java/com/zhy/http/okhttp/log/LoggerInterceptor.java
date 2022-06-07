package com.zhy.http.okhttp.log;

import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/* loaded from: classes4.dex */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String a;
    private boolean b;

    public LoggerInterceptor(String str, boolean z) {
        str = TextUtils.isEmpty(str) ? TAG : str;
        this.b = z;
        this.a = str;
    }

    public LoggerInterceptor(String str) {
        this(str, false);
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        a(request);
        return a(chain.proceed(request));
    }

    private Response a(Response response) {
        ResponseBody body;
        MediaType contentType;
        try {
            Log.e(this.a, "========response'log=======");
            Response build = response.newBuilder().build();
            String str = this.a;
            Log.e(str, "url : " + build.request().url());
            String str2 = this.a;
            Log.e(str2, "code : " + build.code());
            String str3 = this.a;
            Log.e(str3, "protocol : " + build.protocol());
            if (!TextUtils.isEmpty(build.message())) {
                String str4 = this.a;
                Log.e(str4, "message : " + build.message());
            }
            if (!(!this.b || (body = build.body()) == null || (contentType = body.contentType()) == null)) {
                String str5 = this.a;
                Log.e(str5, "responseBody's contentType : " + contentType.toString());
                if (a(contentType)) {
                    String string = body.string();
                    String str6 = this.a;
                    Log.e(str6, "responseBody's content : " + string);
                    return response.newBuilder().body(ResponseBody.create(contentType, string)).build();
                }
                Log.e(this.a, "responseBody's content :  maybe [file part] , too large too print , ignored!");
            }
            Log.e(this.a, "========response'log=======end");
        } catch (Exception unused) {
        }
        return response;
    }

    private void a(Request request) {
        MediaType contentType;
        try {
            String httpUrl = request.url().toString();
            Headers headers = request.headers();
            Log.e(this.a, "========request'log=======");
            String str = this.a;
            Log.e(str, "method : " + request.method());
            String str2 = this.a;
            Log.e(str2, "url : " + httpUrl);
            if (headers != null && headers.size() > 0) {
                String str3 = this.a;
                Log.e(str3, "headers : " + headers.toString());
            }
            RequestBody body = request.body();
            if (!(body == null || (contentType = body.contentType()) == null)) {
                String str4 = this.a;
                Log.e(str4, "requestBody's contentType : " + contentType.toString());
                if (a(contentType)) {
                    String str5 = this.a;
                    Log.e(str5, "requestBody's content : " + b(request));
                } else {
                    Log.e(this.a, "requestBody's content :  maybe [file part] , too large too print , ignored!");
                }
            }
            Log.e(this.a, "========request'log=======end");
        } catch (Exception unused) {
        }
    }

    private boolean a(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            return mediaType.subtype().equals("json") || mediaType.subtype().equals("xml") || mediaType.subtype().equals("html") || mediaType.subtype().equals("webviewhtml");
        }
        return false;
    }

    private String b(Request request) {
        try {
            Request build = request.newBuilder().build();
            Buffer buffer = new Buffer();
            build.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException unused) {
            return "something error when show requestBody.";
        }
    }
}
