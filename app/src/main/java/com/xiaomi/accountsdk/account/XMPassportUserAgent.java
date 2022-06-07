package com.xiaomi.accountsdk.account;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.WebView;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class XMPassportUserAgent {
    private static final String KEY_APP_PACKAGE_NAME = "APP/";
    private static final String KEY_APP_VERSION = "APPV/";
    private static final String TAG = "XMPassportUserAgent";
    private static volatile Set<String> sExtendedUASet = new LinkedHashSet();
    private static volatile String sUserAgentCache;
    private static volatile String sUserAgentForReplacement;
    private static volatile String sWebViewUserAgentCache;

    private XMPassportUserAgent() {
    }

    public static synchronized void addExtendedUserAgent(String str) {
        synchronized (XMPassportUserAgent.class) {
            sExtendedUASet.add(str);
            invalidateUACache();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void setUserAgentForReplacement(String str) {
        synchronized (XMPassportUserAgent.class) {
            sUserAgentForReplacement = str;
            invalidateUACache();
        }
    }

    public static synchronized String getUserAgent(Context context) {
        String str;
        synchronized (XMPassportUserAgent.class) {
            if (TextUtils.isEmpty(sUserAgentCache)) {
                sUserAgentCache = new UserAgentBuilder(context, TextUtils.isEmpty(sUserAgentForReplacement) ? getDefaultUA() : sUserAgentForReplacement, sExtendedUASet, false).build();
            }
            str = sUserAgentCache;
        }
        return str;
    }

    public static synchronized String getWebViewUserAgent(WebView webView, Context context) {
        String str;
        synchronized (XMPassportUserAgent.class) {
            checkThread();
            if (TextUtils.isEmpty(sWebViewUserAgentCache)) {
                sWebViewUserAgentCache = new UserAgentBuilder(context, webView.getSettings().getUserAgentString(), sExtendedUASet, true).build();
            }
            str = sWebViewUserAgentCache;
        }
        return str;
    }

    private static void checkThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalThreadStateException("cannot be called without main thread");
        }
    }

    private static synchronized void invalidateUACache() {
        synchronized (XMPassportUserAgent.class) {
            sUserAgentCache = null;
            sWebViewUserAgentCache = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class UserAgentBuilder {
        private final Context context;
        private final Set<String> extendedUASet;
        private final boolean isWebView;
        private final String majorUserAgent;

        private UserAgentBuilder(Context context, String str, Set<String> set, boolean z) {
            this.context = context;
            this.majorUserAgent = str;
            this.extendedUASet = set;
            this.isWebView = z;
        }

        public String build() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.majorUserAgent);
            sb.append(StringUtils.SPACE);
            sb.append(XMPassportUserAgent.KEY_APP_PACKAGE_NAME);
            sb.append(getStrippedPackageName(this.context));
            String appVersion = getAppVersion(this.context);
            if (!TextUtils.isEmpty(appVersion)) {
                sb.append(StringUtils.SPACE);
                sb.append(XMPassportUserAgent.KEY_APP_VERSION);
                sb.append(appVersion);
            }
            if (this.isWebView) {
                sb.append(StringUtils.SPACE);
                sb.append("XiaoMi/HybridView/");
            }
            for (String str : this.extendedUASet) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(StringUtils.SPACE);
                    sb.append(str);
                }
            }
            return sb.toString();
        }

        private String getAppVersion(Context context) {
            if (context == null) {
                return null;
            }
            try {
                return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException unused) {
                AccountLog.i(XMPassportUserAgent.TAG, context.getPackageName() + " NameNotFound");
                return null;
            }
        }

        private String getStrippedPackageName(Context context) {
            String packageName = context == null ? "unknown" : context.getPackageName();
            String[] split = packageName.split("\\.");
            if (split.length <= 2) {
                return packageName;
            }
            return split[split.length - 2] + "." + split[split.length - 1];
        }
    }

    private static String getDefaultUA() {
        return System.getProperty("http.agent");
    }
}
