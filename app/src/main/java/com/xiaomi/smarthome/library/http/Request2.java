package com.xiaomi.smarthome.library.http;

import android.text.TextUtils;
import com.xiaomi.smarthome.library.apache.http.NameValuePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes4.dex */
public class Request2 {
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    private String a;
    private String b;
    private Map<String, String> c;
    private List<NameValuePair> d;

    public Request2(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
    }

    public String getUrl() {
        return this.b;
    }

    public Map<String, String> getHeaders() {
        return this.c;
    }

    public String getMethod() {
        if (!TextUtils.isEmpty(this.a)) {
            return this.a;
        }
        throw new IllegalArgumentException("method == null");
    }

    public List<NameValuePair> getQueryParams() {
        return this.d;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        private String a;
        private String b;
        private Map<String, String> c = new HashMap();
        private List<NameValuePair> d = new ArrayList(8);

        public Builder method(String str) {
            if (str != null) {
                this.a = str;
                return this;
            }
            throw new IllegalArgumentException("method == null");
        }

        public Builder url(String str) {
            if (str != null) {
                this.b = str;
                return this;
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder addHeaders(Map<String, String> map) {
            if (map != null) {
                this.c = map;
                return this;
            }
            throw new IllegalArgumentException("headers == null");
        }

        public Builder addQueries(List<NameValuePair> list) {
            if (list != null) {
                this.d = list;
                return this;
            }
            throw new IllegalArgumentException("queries == null");
        }

        public Request2 build() {
            return new Request2(this);
        }
    }
}
