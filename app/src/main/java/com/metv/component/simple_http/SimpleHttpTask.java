package com.metv.component.simple_http;

import android.text.TextUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class SimpleHttpTask implements Runnable {
    private String a;
    private SimpleHttpRequest b;
    private SimpleHttpCallback c;

    public SimpleHttpTask(String str, SimpleHttpRequest simpleHttpRequest, SimpleHttpCallback simpleHttpCallback) {
        if (SimpleHttpUtils.checkHost(str)) {
            this.a = str;
            if (simpleHttpRequest == null || TextUtils.isEmpty(simpleHttpRequest.getPath())) {
                throw new IllegalArgumentException("Error request: path is null");
            }
            this.b = simpleHttpRequest;
            if (simpleHttpCallback != null) {
                this.c = simpleHttpCallback;
                return;
            }
            throw new IllegalArgumentException("Error request: callback is null");
        }
        throw new IllegalArgumentException("Error host: " + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0171 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0167 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x017b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.metv.component.simple_http.SimpleHttpTask.run():void");
    }

    private String a() {
        String path = this.b.getPath();
        String str = this.a;
        if (!path.startsWith("/")) {
            str = str + "/";
        }
        String str2 = str + path;
        if (!"GET".equals(this.b.getMethod())) {
            "POST".equals(this.b.getMethod());
            return str2;
        } else if (this.b.getParams() == null) {
            return str2;
        } else {
            return str2 + "?" + a(this.b.getParams());
        }
    }

    private String a(List<SimpleHttpParam> list) {
        StringBuilder sb = new StringBuilder();
        for (SimpleHttpParam simpleHttpParam : list) {
            sb.append(simpleHttpParam.getKey() + "=" + simpleHttpParam.getValue());
        }
        return sb.toString();
    }
}
