package com.xiaomi.accountsdk.utils;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.FidNonce;
import com.xiaomi.accountsdk.utils.ServerTimeUtil;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class WebViewFidNonceUtil {

    /* loaded from: classes2.dex */
    public static final class ServerTimeAlignedListenerImpl implements ServerTimeUtil.ServerTimeAlignedListener {
        private final WeakReference<WebView> webViewWeakReference;

        public ServerTimeAlignedListenerImpl(WebView webView) {
            this.webViewWeakReference = new WeakReference<>(webView);
        }

        @Override // com.xiaomi.accountsdk.utils.ServerTimeUtil.ServerTimeAlignedListener
        public void onServerTimeAligned() {
            WebView webView = this.webViewWeakReference.get();
            if (webView != null) {
                new WebViewFidNonceUtil().setupFidNonceForAccountWeb(webView);
            }
        }
    }

    public final void setupFidNonceForAccountWeb(WebView webView) {
        FidNonce build;
        if (webView != null && (build = new FidNonce.Builder().build(FidNonce.Type.WEB_VIEW)) != null) {
            CookieSyncManager.createInstance(webView.getContext());
            setFidNonceCookie(build, CookieManager.getInstance());
            CookieSyncManager.getInstance().sync();
        }
    }

    public final void setFidNonceCookie(FidNonce fidNonce, CookieManager cookieManager) {
        AbstractAccountWebViewSingleCookieUtil.setAccountCookie(cookieManager, SimpleRequestForAccount.COOKIE_NAME_FID_NONCE_PLAIN, fidNonce.plain);
        AbstractAccountWebViewSingleCookieUtil.setAccountCookie(cookieManager, SimpleRequestForAccount.COOKIE_NAME_FID_NONCE_SIGN, fidNonce.sign);
    }
}
