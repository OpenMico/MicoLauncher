package com.jeremyliao.liveeventbus.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.core.content.FileProvider;
import com.xiaomi.idm.api.IDMServer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class AppUtils {
    private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();
    private static final String PERMISSION_ACTIVITY_CLASS_NAME = "com.blankj.utilcode.util.PermissionUtils$PermissionActivity";
    @SuppressLint({"StaticFieldLeak"})
    private static Application sApplication;

    /* loaded from: classes2.dex */
    public interface OnActivityDestroyedListener {
        void onActivityDestroyed(Activity activity);
    }

    /* loaded from: classes2.dex */
    public interface OnAppStatusChangedListener {
        void onBackground();

        void onForeground();
    }

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context) {
        if (context == null) {
            init(getApplicationByReflect());
        } else {
            init((Application) context.getApplicationContext());
        }
    }

    public static void init(Application application) {
        if (sApplication == null) {
            if (application == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = application;
            }
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        } else if (application != null && application.getClass() != sApplication.getClass()) {
            sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            ACTIVITY_LIFECYCLE.mActivityList.clear();
            sApplication = application;
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
    }

    public static Application getApp() {
        Application application = sApplication;
        if (application != null) {
            return application;
        }
        Application applicationByReflect = getApplicationByReflect();
        init(applicationByReflect);
        return applicationByReflect;
    }

    private static Application getApplicationByReflect() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]), new Object[0]);
            if (invoke != null) {
                return (Application) invoke;
            }
            throw new NullPointerException("u should init first");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NullPointerException("u should init first");
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            throw new NullPointerException("u should init first");
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            throw new NullPointerException("u should init first");
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            throw new NullPointerException("u should init first");
        }
    }

    static ActivityLifecycleImpl getActivityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.mActivityList;
    }

    static Context getTopActivityOrApp() {
        if (!isAppForeground()) {
            return getApp();
        }
        Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
        return topActivity == null ? getApp() : topActivity;
    }

    static boolean isAppForeground() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) getApp().getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null || runningAppProcesses.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100) {
                return runningAppProcessInfo.processName.equals(getApp().getPackageName());
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
        final LinkedList<Activity> mActivityList = new LinkedList<>();
        final Map<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap();
        final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap();
        private int mForegroundCount = 0;
        private int mConfigCount = 0;
        private boolean mIsBackground = false;

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        ActivityLifecycleImpl() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            setTopActivity(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            if (!this.mIsBackground) {
                setTopActivity(activity);
            }
            int i = this.mConfigCount;
            if (i < 0) {
                this.mConfigCount = i + 1;
            } else {
                this.mForegroundCount++;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
            if (this.mIsBackground) {
                this.mIsBackground = false;
                postStatus(true);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                this.mConfigCount--;
                return;
            }
            this.mForegroundCount--;
            if (this.mForegroundCount <= 0) {
                this.mIsBackground = true;
                postStatus(false);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            this.mActivityList.remove(activity);
            consumeOnActivityDestroyedListener(activity);
            fixSoftInputLeaks(activity);
        }

        Activity getTopActivity() {
            Activity last;
            if (!this.mActivityList.isEmpty() && (last = this.mActivityList.getLast()) != null) {
                return last;
            }
            Activity topActivityByReflect = getTopActivityByReflect();
            if (topActivityByReflect != null) {
                setTopActivity(topActivityByReflect);
            }
            return topActivityByReflect;
        }

        void addOnAppStatusChangedListener(Object obj, OnAppStatusChangedListener onAppStatusChangedListener) {
            this.mStatusListenerMap.put(obj, onAppStatusChangedListener);
        }

        void removeOnAppStatusChangedListener(Object obj) {
            this.mStatusListenerMap.remove(obj);
        }

        void removeOnActivityDestroyedListener(Activity activity) {
            if (activity != null) {
                this.mDestroyedListenerMap.remove(activity);
            }
        }

        void addOnActivityDestroyedListener(Activity activity, OnActivityDestroyedListener onActivityDestroyedListener) {
            Set<OnActivityDestroyedListener> set;
            if (activity != null && onActivityDestroyedListener != null) {
                if (!this.mDestroyedListenerMap.containsKey(activity)) {
                    set = new HashSet<>();
                    this.mDestroyedListenerMap.put(activity, set);
                } else {
                    set = this.mDestroyedListenerMap.get(activity);
                    if (set.contains(onActivityDestroyedListener)) {
                        return;
                    }
                }
                set.add(onActivityDestroyedListener);
            }
        }

        private void postStatus(boolean z) {
            OnAppStatusChangedListener next;
            if (!this.mStatusListenerMap.isEmpty()) {
                Iterator<OnAppStatusChangedListener> it = this.mStatusListenerMap.values().iterator();
                while (it.hasNext() && (next = it.next()) != null) {
                    if (z) {
                        next.onForeground();
                    } else {
                        next.onBackground();
                    }
                }
            }
        }

        private void setTopActivity(Activity activity) {
            if (!AppUtils.PERMISSION_ACTIVITY_CLASS_NAME.equals(activity.getClass().getName())) {
                if (!this.mActivityList.contains(activity)) {
                    this.mActivityList.addLast(activity);
                } else if (!this.mActivityList.getLast().equals(activity)) {
                    this.mActivityList.remove(activity);
                    this.mActivityList.addLast(activity);
                }
            }
        }

        private void consumeOnActivityDestroyedListener(Activity activity) {
            Iterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> it = this.mDestroyedListenerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Activity, Set<OnActivityDestroyedListener>> next = it.next();
                if (next.getKey() == activity) {
                    for (OnActivityDestroyedListener onActivityDestroyedListener : next.getValue()) {
                        onActivityDestroyedListener.onActivityDestroyed(activity);
                    }
                    it.remove();
                }
            }
        }

        private Activity getTopActivityByReflect() {
            Map map;
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
                Field declaredField = cls.getDeclaredField("mActivityList");
                declaredField.setAccessible(true);
                map = (Map) declaredField.get(invoke);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (NoSuchFieldException e3) {
                e3.printStackTrace();
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
            if (map == null) {
                return null;
            }
            for (Object obj : map.values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField(IDMServer.PERSIST_TYPE_ACTIVITY);
                    declaredField3.setAccessible(true);
                    return (Activity) declaredField3.get(obj);
                }
            }
            return null;
        }

        private static void fixSoftInputLeaks(Activity activity) {
            InputMethodManager inputMethodManager;
            if (!(activity == null || (inputMethodManager = (InputMethodManager) AppUtils.getApp().getSystemService("input_method")) == null)) {
                for (String str : new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"}) {
                    try {
                        Field declaredField = InputMethodManager.class.getDeclaredField(str);
                        if (declaredField != null) {
                            if (!declaredField.isAccessible()) {
                                declaredField.setAccessible(true);
                            }
                            Object obj = declaredField.get(inputMethodManager);
                            if ((obj instanceof View) && ((View) obj).getRootView() == activity.getWindow().getDecorView().getRootView()) {
                                declaredField.set(inputMethodManager, null);
                            }
                        }
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class FileProvider4UtilCode extends FileProvider {
        @Override // androidx.core.content.FileProvider, android.content.ContentProvider
        public boolean onCreate() {
            AppUtils.init(getContext());
            return true;
        }
    }
}
