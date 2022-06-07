package com.umeng.commonsdk;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.k;
import com.umeng.analytics.pro.z;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.a;
import com.umeng.commonsdk.listener.OnGetOaidListener;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.b;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class UMConfigure {
    public static final int DEVICE_TYPE_BOX = 2;
    public static final int DEVICE_TYPE_PHONE = 1;
    private static final String KEY_FILE_NAME_APPKEY = "APPKEY";
    private static final String KEY_FILE_NAME_LOG = "LOG";
    private static final String KEY_METHOD_NAME_PUSH_SETCHANNEL = "setMessageChannel";
    private static final String KEY_METHOD_NAME_PUSH_SET_SECRET = "setSecret";
    private static final String KEY_METHOD_NAME_SETAPPKEY = "setAppkey";
    private static final String KEY_METHOD_NAME_SETCHANNEL = "setChannel";
    private static final String KEY_METHOD_NAME_SETDEBUGMODE = "setDebugMode";
    private static final String TAG = "UMConfigure";
    private static final String WRAPER_TYPE_COCOS2DX_X = "Cocos2d-x";
    private static final String WRAPER_TYPE_COCOS2DX_XLUA = "Cocos2d-x_lua";
    private static final String WRAPER_TYPE_FLUTTER = "flutter";
    private static final String WRAPER_TYPE_HYBRID = "hybrid";
    private static final String WRAPER_TYPE_NATIVE = "native";
    private static final String WRAPER_TYPE_PHONEGAP = "phonegap";
    private static final String WRAPER_TYPE_REACTNATIVE = "react-native";
    private static final String WRAPER_TYPE_UNITY = "Unity";
    private static final String WRAPER_TYPE_WEEX = "weex";
    private static boolean debugLog = false;
    private static OnGetOaidListener mOnGetOaidListener;
    public static UMLog umDebugLog = new UMLog();
    private static boolean preInitComplete = false;
    private static Object PreInitLock = new Object();
    public static String sAppkey = "";
    public static String sChannel = "";
    public static boolean isInit = false;
    private static boolean isFinish = false;
    private static Object lockObject = new Object();

    private static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private static Object getDecInstanceObject(Class<?> cls) {
        Constructor<?> constructor;
        if (cls == null) {
            return null;
        }
        try {
            constructor = cls.getDeclaredConstructor(new Class[0]);
        } catch (NoSuchMethodException unused) {
            constructor = null;
        }
        if (constructor == null) {
            return null;
        }
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(new Object[0]);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException unused2) {
            return null;
        }
    }

    private static Method getDecMethod(Class<?> cls, String str, Class<?>[] clsArr) {
        Method method = null;
        if (cls != null) {
            try {
                method = cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException unused) {
            }
            if (method != null) {
                method.setAccessible(true);
            }
        }
        return method;
    }

    private static void invoke(Method method, Object obj, Object[] objArr) {
        if (method != null && obj != null) {
            try {
                method.invoke(obj, objArr);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            }
        }
    }

    private static void invoke(Method method, Object[] objArr) {
        if (method != null) {
            try {
                method.invoke(null, objArr);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            }
        }
    }

    private static void setFile(Class<?> cls, String str, String str2) {
        if (cls != null) {
            try {
                cls.getField(str).set(str, str2);
            } catch (Exception unused) {
            }
        }
    }

    private static void setFile(Class<?> cls, String str, boolean z) {
        if (cls != null) {
            try {
                cls.getField(str).set(str, Boolean.valueOf(z));
            } catch (Exception unused) {
            }
        }
    }

    public static boolean getInitStatus() {
        boolean z;
        synchronized (lockObject) {
            z = isFinish;
        }
        return z;
    }

    private static boolean checkShareSdk(Class<?> cls) {
        try {
            return cls.getDeclaredField("isZyb") != null;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void init(Context context, int i, String str) {
        init(context, null, null, i, str);
    }

    private static boolean isPreInit() {
        boolean z;
        synchronized (PreInitLock) {
            z = preInitComplete;
        }
        return z;
    }

    public static void preInit(Context context, String str, String str2) {
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (TextUtils.isEmpty(str)) {
                str = UMUtils.getAppkeyByXML(applicationContext);
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = UMUtils.getChannelByXML(applicationContext);
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = "Unknown";
            }
            if (!TextUtils.isEmpty(str)) {
                sAppkey = str;
                sChannel = str2;
                UMGlobalContext.getInstance(applicationContext);
                k.a(applicationContext);
                if (!needSendZcfgEnv(applicationContext)) {
                    FieldManager.a().a(applicationContext);
                }
                synchronized (PreInitLock) {
                    preInitComplete = true;
                }
            }
        } else if (debugLog) {
            Log.e(TAG, "preInit: context is null, pls check!");
        }
    }

    /* JADX WARN: Type inference failed for: r3v11, types: [com.umeng.commonsdk.UMConfigure$2] */
    /* JADX WARN: Type inference failed for: r3v15, types: [com.umeng.commonsdk.UMConfigure$1] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.umeng.commonsdk.UMConfigure$3] */
    public static void init(Context context, String str, String str2, int i, String str3) {
        Method declaredMethod;
        Object invoke;
        Method declaredMethod2;
        Method declaredMethod3;
        Method declaredMethod4;
        Method declaredMethod5;
        Method declaredMethod6;
        Object invoke2;
        Class<?> cls;
        Method declaredMethod7;
        Method declaredMethod8;
        try {
            try {
                if (debugLog) {
                    Log.i(TAG, "common version is 9.3.8");
                    Log.i(TAG, "common type is " + SdkVersion.SDK_TYPE);
                }
            } catch (Exception e) {
                if (debugLog) {
                    Log.e(TAG, "init e is " + e);
                }
            }
        } catch (Throwable th) {
            if (debugLog) {
                Log.e(TAG, "init e is " + th);
            }
        }
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "context is null !!!");
            }
        } else if (!isInit) {
            final Context applicationContext = context.getApplicationContext();
            try {
                if (getClass("com.umeng.umzid.ZIDManager") == null) {
                    Log.e(TAG, "--->>> SDK 初始化失败，请检查是否集成umeng-asms-1.2.x.aar库。<<<--- ");
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, "SDK 初始化失败，请检查是否集成umeng-asms-1.2.X.aar库。", 1).show();
                                Looper.loop();
                            } catch (Throwable unused) {
                            }
                        }
                    }.start();
                    return;
                }
            } catch (Throwable unused) {
            }
            try {
                Class<?> cls2 = getClass("com.umeng.message.PushAgent");
                if (cls2 != null && !checkShareSdk(cls2)) {
                    Log.e("UMLog", UMLogCommon.SC_10015);
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.2
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, UMLogCommon.SC_10015, 1).show();
                                Looper.loop();
                            } catch (Throwable unused2) {
                            }
                        }
                    }.start();
                }
            } catch (Throwable unused2) {
            }
            try {
                Class<?> cls3 = getClass("com.umeng.socialize.UMShareAPI");
                if (cls3 != null && !checkShareSdk(cls3)) {
                    Log.e("UMLog", UMLogCommon.SC_10015);
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.3
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, UMLogCommon.SC_10015, 1).show();
                                Looper.loop();
                            } catch (Throwable unused3) {
                            }
                        }
                    }.start();
                }
            } catch (Throwable unused3) {
            }
            if (!isPreInit()) {
                preInit(applicationContext, str, str2);
                if (!isPreInit()) {
                    return;
                }
            }
            UMUtils.setAppkey(applicationContext, sAppkey);
            String lastAppkey = UMUtils.getLastAppkey(applicationContext);
            if (!TextUtils.isEmpty(sAppkey) && !sAppkey.equals(lastAppkey)) {
                if (!TextUtils.isEmpty(lastAppkey) && debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10008, 2, "");
                }
                UMUtils.setLastAppkey(applicationContext, sAppkey);
            }
            if (debugLog) {
                Log.i(TAG, "current appkey is " + sAppkey + ", last appkey is " + lastAppkey);
            }
            if (debugLog) {
                String appkeyByXML = UMUtils.getAppkeyByXML(applicationContext);
                if (!TextUtils.isEmpty(sAppkey) && !TextUtils.isEmpty(appkeyByXML) && !sAppkey.equals(appkeyByXML)) {
                    UMLog.mutlInfo(UMLogCommon.SC_10011, 3, "", new String[]{"@", "#"}, new String[]{sAppkey, appkeyByXML});
                }
            }
            UMUtils.setChannel(applicationContext, sChannel);
            if (debugLog) {
                Log.i(TAG, "channel is " + sChannel);
            }
            try {
                Class<?> cls4 = Class.forName("com.umeng.analytics.MobclickAgent");
                if (cls4 != null) {
                    Method declaredMethod9 = cls4.getDeclaredMethod("init", Context.class);
                    if (declaredMethod9 != null) {
                        declaredMethod9.setAccessible(true);
                        declaredMethod9.invoke(cls4, applicationContext);
                        if (FieldManager.allow(b.F)) {
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> FirstResumeTrigger enabled.");
                            k.a(applicationContext).b(applicationContext);
                        } else {
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> FirstResumeTrigger disabled.");
                        }
                        if (debugLog) {
                            UMLog.mutlInfo(UMLogCommon.SC_10003, 2, "");
                        }
                    }
                    if (!(Class.forName("com.umeng.analytics.game.UMGameAgent") == null || (declaredMethod8 = cls4.getDeclaredMethod("setGameScenarioType", Context.class)) == null)) {
                        declaredMethod8.setAccessible(true);
                        declaredMethod8.invoke(cls4, applicationContext);
                    }
                }
                if (!(com.umeng.commonsdk.statistics.b.a.indexOf("e") < 0 || (cls = Class.forName("com.umeng.analytics.MobclickAgent")) == null || (declaredMethod7 = cls.getDeclaredMethod("disableExceptionCatch", new Class[0])) == null)) {
                    declaredMethod7.setAccessible(true);
                    declaredMethod7.invoke(cls, new Object[0]);
                }
            } catch (Throwable unused4) {
            }
            try {
                Class<?> cls5 = Class.forName("com.umeng.message.MessageSharedPrefs");
                if (!(cls5 == null || (declaredMethod6 = cls5.getDeclaredMethod("getInstance", Context.class)) == null || (invoke2 = declaredMethod6.invoke(cls5, applicationContext)) == null)) {
                    Method declaredMethod10 = cls5.getDeclaredMethod("setMessageAppKey", String.class);
                    if (declaredMethod10 != null) {
                        declaredMethod10.setAccessible(true);
                        declaredMethod10.invoke(invoke2, sAppkey);
                        if (debugLog) {
                            UMLog uMLog = umDebugLog;
                            UMLog.mutlInfo(UMLogCommon.SC_10004, 2, "");
                        }
                    }
                    Method declaredMethod11 = cls5.getDeclaredMethod(KEY_METHOD_NAME_PUSH_SETCHANNEL, String.class);
                    if (declaredMethod11 != null) {
                        declaredMethod11.setAccessible(true);
                        declaredMethod11.invoke(invoke2, sChannel);
                        if (debugLog) {
                            UMLog uMLog2 = umDebugLog;
                            UMLog.mutlInfo(UMLogCommon.SC_10005, 2, "");
                        }
                    }
                    if (TextUtils.isEmpty(str3)) {
                        boolean z = debugLog;
                    } else {
                        if (debugLog) {
                            Log.i(TAG, "push secret is " + str3);
                        }
                        Method declaredMethod12 = cls5.getDeclaredMethod("setMessageAppSecret", String.class);
                        if (declaredMethod12 != null) {
                            declaredMethod12.setAccessible(true);
                            declaredMethod12.invoke(invoke2, str3);
                            if (debugLog) {
                                UMLog uMLog3 = umDebugLog;
                                UMLog.mutlInfo(UMLogCommon.SC_10009, 2, "");
                            }
                        }
                    }
                }
            } catch (Exception unused5) {
            }
            try {
                Class<?> cls6 = getClass("com.umeng.socialize.UMShareAPI");
                setFile(cls6, KEY_FILE_NAME_APPKEY, sAppkey);
                if (!(cls6 == null || (declaredMethod5 = cls6.getDeclaredMethod("init", Context.class, String.class)) == null)) {
                    declaredMethod5.setAccessible(true);
                    declaredMethod5.invoke(cls6, applicationContext, sAppkey);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10006, 2, "");
                    }
                }
            } catch (Throwable unused6) {
            }
            AnalyticsConstants.setDeviceType(i);
            try {
                Class<?> cls7 = Class.forName("com.umeng.error.UMError");
                if (!(cls7 == null || (declaredMethod4 = cls7.getDeclaredMethod("init", Context.class)) == null)) {
                    declaredMethod4.setAccessible(true);
                    declaredMethod4.invoke(cls7, applicationContext);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10010, 2, "");
                    }
                }
            } catch (Throwable unused7) {
            }
            try {
                Class<?> cls8 = Class.forName("com.umeng.umcrash.UMCrash");
                if (cls8 != null) {
                    if (SdkVersion.SDK_TYPE == 1 && (declaredMethod3 = cls8.getDeclaredMethod("useIntlServices", Boolean.TYPE)) != null) {
                        declaredMethod3.setAccessible(true);
                        declaredMethod3.invoke(cls8, true);
                    }
                    Method declaredMethod13 = cls8.getDeclaredMethod("init", Context.class, String.class, String.class);
                    if (declaredMethod13 != null) {
                        declaredMethod13.setAccessible(true);
                        declaredMethod13.invoke(cls8, applicationContext, sAppkey, sChannel);
                        if (debugLog) {
                            UMLog.mutlInfo(UMLogCommon.SC_10014, 2, "");
                        }
                    }
                }
            } catch (Throwable unused8) {
            }
            try {
                Method declaredMethod14 = Class.forName("com.umeng.vt.facade.EventFacade").getDeclaredMethod("init", Application.class, String.class, String.class, Integer.TYPE, String.class);
                if (declaredMethod14 != null) {
                    declaredMethod14.invoke(null, applicationContext, sAppkey, sChannel, Integer.valueOf(i), str3);
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>初始化 EventFacade 成功.");
                }
            } catch (Throwable unused9) {
            }
            try {
                Method declaredMethod15 = Class.forName("com.umeng.vt.common.VTTracker").getDeclaredMethod("init", Application.class, String.class);
                if (declaredMethod15 != null) {
                    declaredMethod15.invoke(null, applicationContext, sAppkey);
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>初始化 VTTracker 成功.");
                }
            } catch (Throwable unused10) {
            }
            synchronized (lockObject) {
                isFinish = true;
            }
            if (needSendZcfgEnv(applicationContext)) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 走零号报文发送逻辑");
                UMWorkDispatch.sendEvent(applicationContext, a.p, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
            } else {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 走正常逻辑.");
                if (FieldManager.b()) {
                    UMWorkDispatch.sendEvent(applicationContext, a.y, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
                }
            }
            if (isDebugLog()) {
                UMConfigureInternation.doSelfCheck(applicationContext);
            }
            try {
                Context applicationContext2 = context.getApplicationContext();
                Class<?> cls9 = Class.forName("com.umeng.cconfig.UMRemoteConfig");
                if (!(cls9 == null || (declaredMethod = cls9.getDeclaredMethod("getInstance", new Class[0])) == null || (invoke = declaredMethod.invoke(cls9, new Object[0])) == null || (declaredMethod2 = cls9.getDeclaredMethod("init", Context.class)) == null)) {
                    declaredMethod2.setAccessible(true);
                    declaredMethod2.invoke(invoke, applicationContext2);
                }
            } catch (Exception unused11) {
            }
            if (!isInit) {
                isInit = true;
            }
        } else if (debugLog) {
            Log.e(TAG, "has inited !!!");
        }
    }

    public static boolean needSendZcfgEnv(Context context) {
        File filesDir = context.getFilesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(filesDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append(am.l);
        return !new File(sb.toString()).exists();
    }

    public static boolean isDebugLog() {
        return debugLog;
    }

    public static void setLogEnabled(boolean z) {
        try {
            debugLog = z;
            MLog.DEBUG = z;
            Class<?> cls = getClass("com.umeng.message.PushAgent");
            invoke(getDecMethod(cls, KEY_METHOD_NAME_SETDEBUGMODE, new Class[]{Boolean.TYPE}), getDecInstanceObject(cls), new Object[]{Boolean.valueOf(z)});
            setFile(getClass("com.umeng.socialize.Config"), "DEBUG", z);
            invoke(getDecMethod(getClass("com.umeng.umcrash.UMCrash"), "setDebug", new Class[]{Boolean.TYPE}), new Object[]{Boolean.valueOf(z)});
        } catch (Exception e) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + e);
            }
        } catch (Throwable th) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + th);
            }
        }
    }

    public static void setEncryptEnabled(boolean z) {
        com.umeng.commonsdk.statistics.b.a(z);
    }

    public static String getUMIDString(Context context) {
        if (context != null) {
            return UMUtils.getUMId(context.getApplicationContext());
        }
        return null;
    }

    public static String getUmengZID(Context context) {
        if (context != null) {
            return UMUtils.getZid(context.getApplicationContext());
        }
        return null;
    }

    public static void setProcessEvent(boolean z) {
        AnalyticsConstants.SUB_PROCESS_EVENT = z;
    }

    private static void setLatencyWindow(long j) {
        com.umeng.commonsdk.statistics.a.c = ((int) j) * 1000;
    }

    private static void setCheckDevice(boolean z) {
        AnalyticsConstants.CHECK_DEVICE = z;
    }

    private static void setWraperType(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals("native")) {
                com.umeng.commonsdk.stateless.a.a = "native";
                com.umeng.commonsdk.statistics.a.a = "native";
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_X)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_COCOS2DX_X;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_COCOS2DX_X;
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_XLUA)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_COCOS2DX_XLUA;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_COCOS2DX_XLUA;
            } else if (str.equals(WRAPER_TYPE_UNITY)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_UNITY;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_UNITY;
            } else if (str.equals(WRAPER_TYPE_REACTNATIVE)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_REACTNATIVE;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_REACTNATIVE;
            } else if (str.equals(WRAPER_TYPE_PHONEGAP)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_PHONEGAP;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_PHONEGAP;
            } else if (str.equals(WRAPER_TYPE_WEEX)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_WEEX;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_WEEX;
            } else if (str.equals(WRAPER_TYPE_HYBRID)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_HYBRID;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_HYBRID;
            } else if (str.equals(WRAPER_TYPE_FLUTTER)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_FLUTTER;
                com.umeng.commonsdk.statistics.a.a = WRAPER_TYPE_FLUTTER;
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            com.umeng.commonsdk.stateless.a.b = str2;
            com.umeng.commonsdk.statistics.a.b = str2;
        }
    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] strArr = new String[2];
        if (context != null) {
            try {
                strArr[0] = DeviceConfig.getDeviceIdForGeneral(context);
                strArr[1] = DeviceConfig.getMac(context);
            } catch (Exception unused) {
            }
        }
        return strArr;
    }

    public static void getOaid(Context context, OnGetOaidListener onGetOaidListener) {
        if (context != null) {
            final Context applicationContext = context.getApplicationContext();
            mOnGetOaidListener = onGetOaidListener;
            new Thread(new Runnable() { // from class: com.umeng.commonsdk.UMConfigure.4
                @Override // java.lang.Runnable
                public void run() {
                    String a = z.a(applicationContext);
                    if (UMConfigure.mOnGetOaidListener != null) {
                        UMConfigure.mOnGetOaidListener.onGetOaid(a);
                    }
                }
            }).start();
        } else if (debugLog) {
            Log.e(TAG, "context is null !!!");
        }
    }
}
