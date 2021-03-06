package com.xiaomi.accountsdk.account;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import java.io.File;

/* loaded from: classes2.dex */
public class XMPassportSettings {
    private static final String IS_STAGING = "is_staging";
    private static final String NAME_STAGING_SP = "staging_sp";
    @SuppressLint({"StaticFieldLeak"})
    private static volatile Application sApplication = null;
    private static String sDeviceId = null;
    @SuppressLint({"StaticFieldLeak"})
    private static volatile Context sGlobalContext = null;
    private static boolean sNonNullApplicationContextContract = false;
    private static volatile String sOwnerHandleId;
    private static volatile String sUserHandleId;

    public static String getUserAgent() {
        return XMPassportUserAgent.getUserAgent(sApplication);
    }

    @Deprecated
    public static void setUserAgent(String str) {
        XMPassportUserAgent.setUserAgentForReplacement(str);
    }

    public static String getDeviceId() {
        return sDeviceId;
    }

    public static void setDeviceId(String str) {
        sDeviceId = str;
    }

    public static synchronized String getUserHandleId() {
        String str;
        synchronized (XMPassportSettings.class) {
            str = sUserHandleId;
        }
        return str;
    }

    public static synchronized void setUserHandleId(String str) {
        synchronized (XMPassportSettings.class) {
            sUserHandleId = str;
        }
    }

    public static synchronized String getOwnerHandleId() {
        String str;
        synchronized (XMPassportSettings.class) {
            str = sOwnerHandleId;
        }
        return str;
    }

    public static synchronized void setOwnerHandleId(String str) {
        synchronized (XMPassportSettings.class) {
            sOwnerHandleId = str;
        }
    }

    public static void setNonNullApplicationContextContract(boolean z) {
        sNonNullApplicationContextContract = z;
    }

    public static synchronized Application getApplicationContext() {
        Application application;
        synchronized (XMPassportSettings.class) {
            if (sNonNullApplicationContextContract && sApplication == null) {
                throw new IllegalStateException("getApplicationContext should be called after setApplicationContext() or ensureApplicationContext()");
            }
            application = sApplication;
        }
        return application;
    }

    public static synchronized void setApplicationContext(Application application) {
        synchronized (XMPassportSettings.class) {
            if (application != null) {
                sApplication = application;
            } else {
                throw new IllegalArgumentException("application == null");
            }
        }
    }

    public static synchronized void ensureApplicationContext(Application application) {
        synchronized (XMPassportSettings.class) {
            if (application == null) {
                throw new IllegalArgumentException("application == null");
            } else if (sApplication == null) {
                sApplication = application;
            }
        }
    }

    public static void setLocalStaging(Context context, boolean z) {
        setGlobalContext(context);
        context.getSharedPreferences(NAME_STAGING_SP, 0).edit().putBoolean(IS_STAGING, z).apply();
    }

    public static boolean isLocalStaging(Context context) {
        setGlobalContext(context);
        return isStaging();
    }

    public static boolean isStaging() {
        boolean exists = new File("/data/system/xiaomi_account_preview").exists();
        Context globalContext = getGlobalContext();
        return (globalContext == null || AccountIntent.PACKAGE_XIAOMI_ACCOUNT.equals(globalContext.getPackageName())) ? exists : exists || globalContext.getSharedPreferences(NAME_STAGING_SP, 0).getBoolean(IS_STAGING, false);
    }

    private static Context getGlobalContext() {
        if (sApplication != null) {
            return sApplication;
        }
        return sGlobalContext;
    }

    private static void setGlobalContext(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            throw new IllegalArgumentException("globalContext == null or globalContext.getApplicationContext() == null");
        }
        sGlobalContext = context.getApplicationContext();
    }
}
