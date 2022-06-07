package com.efs.sdk.base.http;

import androidx.annotation.NonNull;
import com.efs.sdk.base.a.h.a.b;
import com.efs.sdk.base.a.h.b.a;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class HttpEnv {
    private List<b<HttpResponse>> mHttpListenerList;
    private IHttpUtil mHttpUtil;

    private HttpEnv() {
        this.mHttpUtil = a.a();
        this.mHttpListenerList = new ArrayList(1);
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final HttpEnv INSTANCE = new HttpEnv();

        private SingletonHolder() {
        }
    }

    public static HttpEnv getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public IHttpUtil getHttpUtil() {
        return this.mHttpUtil;
    }

    public void setHttpUtil(IHttpUtil iHttpUtil) {
        this.mHttpUtil = iHttpUtil;
    }

    public List<b<HttpResponse>> getHttpListenerList() {
        return new ArrayList(this.mHttpListenerList);
    }

    public void addListener(@NonNull AbsHttpListener absHttpListener) {
        this.mHttpListenerList.add(absHttpListener);
    }

    public void removeListener(@NonNull AbsHttpListener absHttpListener) {
        this.mHttpListenerList.remove(absHttpListener);
    }
}
