package com.xiaomi.smarthome.library.http.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;

/* loaded from: classes4.dex */
public class WebViewCookieManager {
    static WebViewCookieManager a;
    private static final boolean c;
    Context b;
    private a d = new a();
    private CookieManager e;

    static {
        c = Build.VERSION.SDK_INT < 21;
        a = null;
    }

    WebViewCookieManager(Context context) {
        this.b = context;
    }

    public static void initial(Context context) {
        a = new WebViewCookieManager(context);
    }

    public static WebViewCookieManager getInstance() {
        return a;
    }

    private static void a(Context context) {
        if (c) {
            CookieSyncManager.createInstance(context).sync();
        }
    }

    public void setCookie(String str, String str2, String str3) {
        if (str != null && str2 != null && str3 != null) {
            setCookie(str3, str + "=" + str2 + "; domain=" + str3);
        }
    }

    public void setCookie(final String str, final String str2) {
        Log.d("CookieManager", "setCookie(): " + str + ", " + str2);
        if (c) {
            a(new Runnable() { // from class: com.xiaomi.smarthome.library.http.util.WebViewCookieManager.1
                @Override // java.lang.Runnable
                public void run() {
                    WebViewCookieManager.this.c().setCookie(str, str2);
                    WebViewCookieManager.this.d.a();
                }
            });
            return;
        }
        a(str, str2);
        this.d.a();
    }

    public void removeCookie(String str) {
        Log.d("CookieManager", "removeCookie(): " + str);
        String[] split = getCookie(str).split(";");
        if (split.length > 0) {
            for (String str2 : split) {
                String[] split2 = str2.split("=");
                if (split2.length > 0 && !TextUtils.isEmpty(split2[0])) {
                    setCookie(str, split2[0] + "=EXPIRED; domain=" + str + "; expires=Thu, 01-Dec-1994 16:00:00 GMT");
                }
            }
        }
    }

    public String getCookie(String str) {
        String cookie = c().getCookie(str);
        Log.d("CookieManager", "getCookie(): " + str + ", " + cookie);
        return cookie;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.xiaomi.smarthome.library.http.util.WebViewCookieManager$2] */
    public void removeAllCookies() {
        Log.d("CookieManager", "removeAllCookies()");
        if (c) {
            new AsyncTask<Void, Void, Void>() { // from class: com.xiaomi.smarthome.library.http.util.WebViewCookieManager.2
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Void doInBackground(Void... voidArr) {
                    WebViewCookieManager.this.c().removeAllCookie();
                    WebViewCookieManager.this.d.a();
                    return null;
                }
            }.execute(new Void[0]);
        } else {
            b();
        }
    }

    @TargetApi(21)
    private void b() {
        c().removeAllCookies(new ValueCallback<Boolean>() { // from class: com.xiaomi.smarthome.library.http.util.WebViewCookieManager.3
            /* renamed from: a */
            public void onReceiveValue(Boolean bool) {
                Log.d("CookieManager", "clearCookiesAsync success");
                WebViewCookieManager.this.d.a();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CookieManager c() {
        if (this.e == null) {
            a(this.b);
            this.e = CookieManager.getInstance();
            if (c) {
                this.e.removeExpiredCookie();
            }
        }
        return this.e;
    }

    @TargetApi(21)
    private void a(String str, String str2) {
        c().setCookie(str, str2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.xiaomi.smarthome.library.http.util.WebViewCookieManager$4] */
    public void a(final Runnable runnable) {
        new AsyncTask<Void, Void, Void>() { // from class: com.xiaomi.smarthome.library.http.util.WebViewCookieManager.4
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public Void doInBackground(Void... voidArr) {
                runnable.run();
                return null;
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a {
        private final Handler b;

        public a() {
            this.b = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.xiaomi.smarthome.library.http.util.WebViewCookieManager.a.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    if (message.what != 1) {
                        return false;
                    }
                    a.this.b();
                    return true;
                }
            });
        }

        public void a() {
            if (WebViewCookieManager.c) {
                this.b.sendEmptyMessageDelayed(1, 30000L);
            }
        }

        public void b() {
            this.b.removeMessages(1);
            WebViewCookieManager.this.a(new Runnable() { // from class: com.xiaomi.smarthome.library.http.util.WebViewCookieManager.a.2
                @Override // java.lang.Runnable
                public void run() {
                    if (WebViewCookieManager.c) {
                        CookieSyncManager.getInstance().sync();
                    } else {
                        a.this.c();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        @TargetApi(21)
        public void c() {
            WebViewCookieManager.this.c().flush();
        }
    }
}
