package javax.servlet;

import javax.servlet.annotation.MultipartConfig;

/* loaded from: classes5.dex */
public class MultipartConfigElement {
    private String a;
    private long b;
    private long c;
    private int d;

    public MultipartConfigElement(String str) {
        if (str == null) {
            this.a = "";
        } else {
            this.a = str;
        }
        this.b = -1L;
        this.c = -1L;
        this.d = 0;
    }

    public MultipartConfigElement(String str, long j, long j2, int i) {
        if (str == null) {
            this.a = "";
        } else {
            this.a = str;
        }
        this.b = j;
        this.c = j2;
        this.d = i;
    }

    public MultipartConfigElement(MultipartConfig multipartConfig) {
        this.a = multipartConfig.location();
        this.d = multipartConfig.fileSizeThreshold();
        this.b = multipartConfig.maxFileSize();
        this.c = multipartConfig.maxRequestSize();
    }

    public String getLocation() {
        return this.a;
    }

    public long getMaxFileSize() {
        return this.b;
    }

    public long getMaxRequestSize() {
        return this.c;
    }

    public int getFileSizeThreshold() {
        return this.d;
    }
}
