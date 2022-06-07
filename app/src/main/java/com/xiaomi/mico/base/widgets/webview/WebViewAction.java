package com.xiaomi.mico.base.widgets.webview;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebView;

/* loaded from: classes3.dex */
public interface WebViewAction {
    void call(Context context, WebView webView, Uri uri);

    String getSchema();
}
