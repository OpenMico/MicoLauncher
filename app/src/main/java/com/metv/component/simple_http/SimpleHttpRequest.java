package com.metv.component.simple_http;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SimpleHttpRequest {
    private volatile List<SimpleHttpHeader> a;
    private String b;
    private volatile List<SimpleHttpParam> c;
    private String d = "GET";
    private int e = 10000;
    private int f = 15000;

    public void addHeader(SimpleHttpHeader simpleHttpHeader) {
        if (simpleHttpHeader != null) {
            if (this.a == null) {
                synchronized (this) {
                    if (this.a == null) {
                        this.a = new ArrayList(4);
                    }
                }
            }
            this.a.add(simpleHttpHeader);
        }
    }

    public void addHttpParam(SimpleHttpParam simpleHttpParam) {
        if (simpleHttpParam != null && !TextUtils.isEmpty(simpleHttpParam.getKey())) {
            if (this.c == null) {
                synchronized (this) {
                    if (this.c == null) {
                        this.c = new ArrayList(4);
                    }
                }
            }
            this.c.add(simpleHttpParam);
        }
    }

    public void setMethodGet() {
        this.d = "GET";
    }

    public void setMethodPost() {
        this.d = "POST";
    }

    public String getPath() {
        return this.b;
    }

    public void setPath(String str) {
        this.b = str;
    }

    public String getMethod() {
        return this.d;
    }

    public List<SimpleHttpHeader> getHeaders() {
        return this.a;
    }

    public List<SimpleHttpParam> getParams() {
        return this.c;
    }

    public int getReadTimeout() {
        return this.e;
    }

    public void setReadTimeout(int i) {
        this.e = i;
    }

    public int getConnectTimeout() {
        return this.f;
    }

    public void setConnectTimeout(int i) {
        this.f = i;
    }
}
