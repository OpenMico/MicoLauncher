package com.zhy.http.okhttp.request;

import com.google.common.net.HttpHeaders;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.CountingRequestBody;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public class PostFormRequest extends OkHttpRequest {
    private List<PostFormBuilder.FileInput> a;

    public PostFormRequest(String str, Object obj, Map<String, String> map, Map<String, String> map2, List<PostFormBuilder.FileInput> list, int i) {
        super(str, obj, map, map2, i);
        this.a = list;
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected RequestBody buildRequestBody() {
        List<PostFormBuilder.FileInput> list = this.a;
        if (list == null || list.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            a(builder);
            return builder.build();
        }
        MultipartBody.Builder type = new MultipartBody.Builder().setType(MultipartBody.FORM);
        a(type);
        for (int i = 0; i < this.a.size(); i++) {
            PostFormBuilder.FileInput fileInput = this.a.get(i);
            type.addFormDataPart(fileInput.key, fileInput.filename, RequestBody.create(MediaType.parse(a(fileInput.filename)), fileInput.file));
        }
        return type.build();
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return callback == null ? requestBody : new CountingRequestBody(requestBody, new CountingRequestBody.Listener() { // from class: com.zhy.http.okhttp.request.PostFormRequest.1
            @Override // com.zhy.http.okhttp.request.CountingRequestBody.Listener
            public void onRequestProgress(final long j, final long j2) {
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() { // from class: com.zhy.http.okhttp.request.PostFormRequest.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Callback callback2 = callback;
                        long j3 = j2;
                        callback2.inProgress((((float) j) * 1.0f) / ((float) j3), j3, PostFormRequest.this.id);
                    }
                });
            }
        });
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected Request buildRequest(RequestBody requestBody) {
        return this.builder.post(requestBody).build();
    }

    private String a(String str) {
        String str2;
        try {
            str2 = URLConnection.getFileNameMap().getContentTypeFor(URLEncoder.encode(str, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str2 = null;
        }
        return str2 == null ? "application/octet-stream" : str2;
    }

    private void a(MultipartBody.Builder builder) {
        if (!(this.params == null || this.params.isEmpty())) {
            for (String str : this.params.keySet()) {
                builder.addPart(Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"" + str + "\""), RequestBody.create((MediaType) null, (String) this.params.get(str)));
            }
        }
    }

    private void a(FormBody.Builder builder) {
        if (this.params != null) {
            for (String str : this.params.keySet()) {
                builder.add(str, (String) this.params.get(str));
            }
        }
    }
}
