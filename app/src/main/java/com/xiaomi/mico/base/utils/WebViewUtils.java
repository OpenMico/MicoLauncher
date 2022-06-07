package com.xiaomi.mico.base.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class WebViewUtils {
    private static String a = "WebViewUtils";

    public static void callWebViewMethod(WebView webView, String str, Object... objArr) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = "";
            if (objArr != null && objArr.length > 0) {
                ArrayList arrayList = new ArrayList(objArr.length);
                for (Object obj : objArr) {
                }
                str2 = TextUtils.join(", ", arrayList);
            }
            String format = String.format("javascript:%s(%s)", str, str2);
            Log.i(a, "try to load url " + format);
            webView.loadUrl(format);
        }
    }

    public static void destroyWebView(WebView webView, boolean z, boolean z2) {
        webView.removeAllViews();
        webView.clearHistory();
        if (z) {
            webView.clearCache(true);
        }
        if (z2) {
            clearCookie();
        }
        webView.destroy();
    }

    public static void clearCookie() {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() { // from class: com.xiaomi.mico.base.utils.WebViewUtils.1
                /* renamed from: a */
                public void onReceiveValue(Boolean bool) {
                    Log.i(WebViewUtils.a, String.format("Cookie deleted %s", bool.toString()));
                }
            });
        } else {
            CookieManager.getInstance().removeAllCookie();
        }
    }
}
