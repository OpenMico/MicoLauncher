package com.umeng.umcrash;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.efs.sdk.base.EfsReporter;
import com.efs.sdk.pa.PAFactory;
import com.efs.sdk.pa.config.IEfsReporter;
import com.efs.sdk.pa.config.PackageLevel;
import com.uc.crashsdk.export.CrashApi;
import com.uc.crashsdk.export.CustomLogInfo;
import com.uc.crashsdk.export.ICrashClient;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class UMCrash {
    public static final String KEY_APM_DEFAULT_SECRET = "NEej8y@anWa*8hep";
    public static final String KEY_APM_ROOT_NAME = "UApm";
    public static final String KEY_CALLBACK_PAGE_ACTION = "um_action_log";
    public static final String KEY_CALLBACK_UMID = "um_umid";
    private static final String KEY_CALLBACK_USER_STRING = "um_user_string";
    public static final String KEY_HEADER_APPKEY = "um_app_key";
    public static final String KEY_HEADER_CARRIER = "um_app_carrier";
    public static final String KEY_HEADER_CHANNEL = "um_app_channel";
    public static final String KEY_HEADER_CRASH_VERSION = "um_crash_sdk_version";
    public static final String KEY_HEADER_OS = "um_os";
    public static final String KEY_HEADER_PROVIDER = "um_app_provider";
    public static final String KEY_HEADER_PUID = "um_app_puid";
    public static final String KEY_HEADER_START_TIME = "um_app_start_time";
    public static final String KEY_HEADER_UMID = "um_umid_header";
    private static final int KEY_MAX_LENGTH = 256;
    private static final String TAG = "UMCrash";
    private static String crashSdkVersion = "1.2.0";
    private static boolean enableANRLog = true;
    private static boolean enableNativeLog = true;
    private static boolean enableUnexpLog = false;
    private static boolean isDebug = true;
    private static boolean isEncrypt = false;
    private static boolean isIntl = false;
    private static boolean isZip = true;
    private static Context mContext;
    private static UMCrashCallback mUMCrashCallback;
    public static EfsReporter sReporter;
    private static Object pageArrayLock = new Object();
    private static ArrayList<String> mArrayList = new ArrayList<>(10);
    private static boolean isPA = false;

    /* loaded from: classes2.dex */
    private static class CrashClientImpl implements ICrashClient {
        @Override // com.uc.crashsdk.export.ICrashClient
        public void onAddCrashStats(String str, int i, int i2) {
        }

        @Override // com.uc.crashsdk.export.ICrashClient
        public File onBeforeUploadLog(File file) {
            return file;
        }

        @Override // com.uc.crashsdk.export.ICrashClient
        public void onClientProcessLogGenerated(String str, File file, String str2) {
        }

        @Override // com.uc.crashsdk.export.ICrashClient
        public void onCrashRestarting(boolean z) {
        }

        @Override // com.uc.crashsdk.export.ICrashClient
        public void onLogGenerated(File file, String str) {
        }

        private CrashClientImpl() {
        }

        @Override // com.uc.crashsdk.export.ICrashClient
        public String onGetCallbackInfo(String str, boolean z) {
            String onCallback;
            String str2 = null;
            if (UMCrash.KEY_CALLBACK_PAGE_ACTION.equals(str)) {
                try {
                    synchronized (UMCrash.pageArrayLock) {
                        if (UMCrash.mArrayList != null && UMCrash.mArrayList.size() > 0) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("source", 0);
                            jSONObject.put("action_name", "page_view");
                            JSONArray jSONArray = new JSONArray();
                            for (int i = 0; i < UMCrash.mArrayList.size(); i++) {
                                String str3 = (String) UMCrash.mArrayList.get(i);
                                if (str3 != null) {
                                    JSONObject jSONObject2 = new JSONObject();
                                    jSONObject2.put("name", str3);
                                    jSONArray.put(jSONObject2);
                                }
                            }
                            jSONObject.put("action_parameter", jSONArray);
                            str2 = jSONObject.toString();
                            if (UMCrash.isDebug) {
                                String str4 = UMCrash.TAG;
                                Log.i(str4, "page json is " + str2);
                            }
                        }
                    }
                } catch (Throwable unused) {
                }
                return str2;
            } else if (UMCrash.KEY_CALLBACK_UMID.equals(str)) {
                return UMCrash.getUMID(UMCrash.mContext);
            } else {
                if (!UMCrash.KEY_CALLBACK_USER_STRING.equals(str) || UMCrash.mUMCrashCallback == null || (onCallback = UMCrash.mUMCrashCallback.onCallback()) == null) {
                    return null;
                }
                return onCallback.trim().getBytes().length > 256 ? UMCrashUtils.splitByByte(onCallback, 256) : onCallback;
            }
        }
    }

    public static synchronized String getUMID(Context context) {
        Class<?> cls;
        Method method;
        synchronized (UMCrash.class) {
            String str = null;
            if (context == null) {
                return null;
            }
            try {
                cls = Class.forName("com.umeng.commonsdk.UMConfigure");
            } catch (ClassNotFoundException unused) {
                cls = null;
            }
            if (cls != null) {
                try {
                    method = cls.getMethod("getUMIDString", Context.class);
                } catch (NoSuchMethodException unused2) {
                    method = null;
                }
                if (method != null) {
                    try {
                        Object invoke = method.invoke(null, context);
                        if (invoke != null) {
                            str = invoke.toString();
                        }
                    } catch (IllegalAccessException | InvocationTargetException unused3) {
                    }
                }
            }
            return str;
        }
    }

    public static String getUMAPMFlag() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", 0);
            jSONObject.put("crash", 1);
            if (enableNativeLog) {
                jSONObject.put("crashNative", 1);
            } else {
                jSONObject.put("crashNative", 0);
            }
            if (enableANRLog) {
                jSONObject.put("anr", 1);
            } else {
                jSONObject.put("anr", 0);
            }
            if (isPA) {
                jSONObject.put("pa", 1);
            } else {
                jSONObject.put("pa", 0);
            }
        } catch (Throwable unused) {
        }
        return jSONObject.toString();
    }

    public static void setDebug(boolean z) {
        isDebug = z;
    }

    public static void enableNativeLog(boolean z) {
        enableNativeLog = z;
    }

    public static void enableANRLog(boolean z) {
        enableANRLog = z;
    }

    public static void enableUnexpLog(boolean z) {
        enableUnexpLog = z;
    }

    private static void useIntlServices(boolean z) {
        isIntl = z;
        if (isDebug) {
            String str = TAG;
            Log.i(str, "useIntlServices is " + isIntl);
        }
    }

    public static void init(Context context, String str, String str2) {
        if (context == null || str == null) {
            Log.e(TAG, "context is null or appkey is null, init failed.");
            return;
        }
        mContext = context;
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("mDebug", isDebug);
            bundle.putBoolean("mEncryptLog", isEncrypt);
            bundle.putBoolean("mZipLog", isZip);
            bundle.putBoolean("enableNativeLog", enableNativeLog);
            bundle.putBoolean("enableANRLog", enableANRLog);
            bundle.putBoolean("enableUnexpLog", enableUnexpLog);
            bundle.putBoolean("mIsInternational", isIntl);
            CrashApi createInstanceEx = CrashApi.createInstanceEx(context, str, isDebug, bundle, new CrashClientImpl());
            if (createInstanceEx != null) {
                createInstanceEx.addHeaderInfo(KEY_HEADER_APPKEY, str);
                createInstanceEx.addHeaderInfo(KEY_HEADER_CHANNEL, str2);
                createInstanceEx.addHeaderInfo(KEY_HEADER_OS, "android");
                createInstanceEx.addHeaderInfo(KEY_HEADER_CRASH_VERSION, crashSdkVersion);
                createInstanceEx.addHeaderInfo(KEY_HEADER_UMID, getUMID(context));
                String[] activeUser = UMCrashUtils.getActiveUser(context);
                if (activeUser != null && activeUser.length == 2) {
                    createInstanceEx.addHeaderInfo(KEY_HEADER_PUID, activeUser[0]);
                    createInstanceEx.addHeaderInfo(KEY_HEADER_PROVIDER, activeUser[1]);
                }
                createInstanceEx.addHeaderInfo(KEY_HEADER_CARRIER, UMCrashUtils.getNetworkOperatorName(context));
                if (context instanceof Application) {
                    ((Application) context).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.umeng.umcrash.UMCrash.1
                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivityCreated(Activity activity, Bundle bundle2) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivityDestroyed(Activity activity) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivityPaused(Activity activity) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivitySaveInstanceState(Activity activity, Bundle bundle2) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivityStarted(Activity activity) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivityStopped(Activity activity) {
                        }

                        @Override // android.app.Application.ActivityLifecycleCallbacks
                        public void onActivityResumed(Activity activity) {
                            synchronized (UMCrash.pageArrayLock) {
                                if (UMCrash.mArrayList != null) {
                                    if (UMCrash.mArrayList.size() >= 10) {
                                        UMCrash.mArrayList.remove(0);
                                    }
                                    ArrayList arrayList = UMCrash.mArrayList;
                                    arrayList.add(activity.getLocalClassName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + System.currentTimeMillis());
                                }
                            }
                        }
                    });
                    createInstanceEx.registerInfoCallback(KEY_CALLBACK_UMID, 1048593);
                    createInstanceEx.registerInfoCallback(KEY_CALLBACK_PAGE_ACTION, 1048593);
                } else {
                    Log.e(TAG, "context not instanceof application.");
                }
            } else {
                Log.e(TAG, "create CrashAPI is null.");
            }
        } catch (Throwable unused) {
        }
        try {
            HashMap hashMap = new HashMap(1);
            hashMap.put(KEY_HEADER_UMID, getUMID(context));
            hashMap.put(KEY_HEADER_CHANNEL, str2);
            hashMap.put(KEY_HEADER_CARRIER, UMCrashUtils.getNetworkOperatorName(context));
            hashMap.put(KEY_HEADER_OS, "android");
            sReporter = new EfsReporter.Builder(context.getApplicationContext(), str, KEY_APM_DEFAULT_SECRET).debug(isDebug).efsDirRootName(KEY_APM_ROOT_NAME).printLogDetail(isDebug).intl(isIntl).build();
            sReporter.addPublicParams(hashMap);
            PAFactory.Builder builder = new PAFactory.Builder(context.getApplicationContext(), new IEfsReporter() { // from class: com.umeng.umcrash.UMCrash.2
                @Override // com.efs.sdk.pa.config.IEfsReporter
                public EfsReporter getReporter() {
                    return UMCrash.sReporter;
                }
            });
            builder.packageLevel(PackageLevel.RELEASE);
            PAFactory build = builder.build();
            build.getPaInstance().start();
            isPA = build.getConfigManager().enableTracer();
        } catch (Throwable unused2) {
        }
    }

    public static void registerUMCrashCallback(UMCrashCallback uMCrashCallback) {
        if (uMCrashCallback != null) {
            mUMCrashCallback = uMCrashCallback;
            if (CrashApi.getInstance() != null) {
                CrashApi.getInstance().registerInfoCallback(KEY_CALLBACK_USER_STRING, 1048593);
            } else {
                Log.e(TAG, "callback error, instance is null.");
            }
        } else {
            Log.e(TAG, "callback error.");
        }
    }

    public static void generateCustomLog(Throwable th, String str) {
        if (th == null || TextUtils.isEmpty(str)) {
            Log.e(TAG, "generate custom log failed ! e is null or type is empty");
            return;
        }
        try {
            CustomLogInfo build = new UMCustomLogInfoBuilder(str).stack(th).build();
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(KEY_CALLBACK_UMID);
            arrayList.add(KEY_CALLBACK_PAGE_ACTION);
            build.mCallbacks = arrayList;
            CrashApi instance = CrashApi.getInstance();
            if (instance == null) {
                Log.e(TAG, "CrashApi is null, not init .");
            } else {
                instance.generateCustomLog(build);
            }
        } catch (Throwable unused) {
        }
    }

    public static void generateCustomLog(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e(TAG, "generate custom log failed ! e is null or type is empty");
            return;
        }
        try {
            CustomLogInfo customLogInfo = new CustomLogInfo(null, "exception");
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(KEY_CALLBACK_UMID);
            arrayList.add(KEY_CALLBACK_PAGE_ACTION);
            customLogInfo.mCallbacks = arrayList;
            HashMap hashMap = new HashMap(20);
            hashMap.put(UMCustomLogInfoBuilder.LOG_KEY_CT, "exception");
            hashMap.put(UMCustomLogInfoBuilder.LOG_KEY_AC, str2);
            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry entry : hashMap.entrySet()) {
                stringBuffer.append((String) entry.getKey());
                stringBuffer.append(Constants.COLON_SEPARATOR);
                stringBuffer.append((String) entry.getValue());
                stringBuffer.append("\n");
            }
            String str3 = "Exception message:\nBack traces starts.\n" + str + "\nBack traces ends.";
            if (!TextUtils.isEmpty(str3)) {
                stringBuffer.append(str3);
                stringBuffer.append("\n");
            }
            customLogInfo.mData = stringBuffer;
            CrashApi instance = CrashApi.getInstance();
            if (instance == null) {
                Log.e(TAG, "CrashApi is null, not init .");
            } else {
                instance.generateCustomLog(customLogInfo);
            }
        } catch (Throwable unused) {
        }
    }
}
