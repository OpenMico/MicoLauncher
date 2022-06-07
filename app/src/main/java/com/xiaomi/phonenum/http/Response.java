package com.xiaomi.phonenum.http;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class Response {
    public final String body;
    public final int code;
    public final Map<String, List<String>> headers;
    public final String location;
    public final String setCookie;
    public long time;

    public Response(Builder builder) {
        this.code = builder.b;
        this.body = builder.c;
        this.headers = builder.a;
        this.setCookie = builder.d;
        this.location = builder.e;
        this.time = builder.f;
    }

    public String toString() {
        return "{code:" + this.code + ", body:" + this.body + "}";
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        Map<String, List<String>> a;
        int b;
        String c;
        String d;
        String e;
        long f;

        public Builder() {
            this.f = 0L;
        }

        public Builder(Response response) {
            this.f = 0L;
            this.b = response.code;
            this.c = response.body;
            this.a = response.headers;
            this.d = response.setCookie;
            this.e = response.location;
            this.f = response.time;
        }

        public Builder headers(Map<String, List<String>> map) {
            this.a = map;
            return this;
        }

        public Builder body(String str) {
            this.c = str;
            return this;
        }

        public Builder code(int i) {
            this.b = i;
            return this;
        }

        public Builder setCookie(String str) {
            this.d = str;
            return this;
        }

        public Builder location(String str) {
            this.e = str;
            return this;
        }

        public Builder time(long j) {
            this.f = j;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
