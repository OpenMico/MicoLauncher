package com.zhy.http.okhttp.request;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.CountingRequestBody;
import com.zhy.http.okhttp.utils.Exceptions;
import java.io.File;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public class PostFileRequest extends OkHttpRequest {
    private static MediaType a = MediaType.parse("application/octet-stream");
    private File b;
    private MediaType c;

    public PostFileRequest(String str, Object obj, Map<String, String> map, Map<String, String> map2, File file, MediaType mediaType, int i) {
        super(str, obj, map, map2, i);
        this.b = file;
        this.c = mediaType;
        if (this.b == null) {
            Exceptions.illegalArgument("the file can not be null !", new Object[0]);
        }
        if (this.c == null) {
            this.c = a;
        }
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected RequestBody buildRequestBody() {
        return RequestBody.create(this.c, this.b);
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return callback == null ? requestBody : new CountingRequestBody(requestBody, new CountingRequestBody.Listener() { // from class: com.zhy.http.okhttp.request.PostFileRequest.1
            @Override // com.zhy.http.okhttp.request.CountingRequestBody.Listener
            public void onRequestProgress(final long j, final long j2) {
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() { // from class: com.zhy.http.okhttp.request.PostFileRequest.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Callback callback2 = callback;
                        long j3 = j2;
                        callback2.inProgress((((float) j) * 1.0f) / ((float) j3), j3, PostFileRequest.this.id);
                    }
                });
            }
        });
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected Request buildRequest(RequestBody requestBody) {
        return this.builder.post(requestBody).build();
    }
}
