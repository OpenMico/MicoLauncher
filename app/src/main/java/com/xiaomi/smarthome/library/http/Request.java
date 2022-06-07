package com.xiaomi.smarthome.library.http;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class Request {
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    private String a;
    private String b;
    private Map<String, String> c;
    private List<KeyValuePair> d;

    public Request(Builder builder) {
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

    public List<KeyValuePair> getQueryParams() {
        return this.d;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        private String a;
        private String b;
        private Map<String, String> c = new HashMap();
        private List<KeyValuePair> d = new ArrayList(8);

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

        public Builder addQueries(List<KeyValuePair> list) {
            if (list != null) {
                this.d = list;
                return this;
            }
            throw new IllegalArgumentException("queries == null");
        }

        public Builder addQueries(Map<String, String> map) {
            if (map != null) {
                this.d = new ArrayList();
                for (String str : map.keySet()) {
                    this.d.add(new KeyValuePair(str, map.get(str)));
                }
                return this;
            }
            throw new IllegalArgumentException("queries == null");
        }

        public Request build() {
            return new Request(this);
        }
    }
}
