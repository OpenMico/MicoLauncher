package com.xiaomi.miplay.mylibrary.mirror;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Looper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public final class Workarounds {
    private Workarounds() {
    }

    public static void prepareMainLooper() {
        Looper.prepareMainLooper();
    }

    @SuppressLint({"PrivateApi"})
    public static void fillAppInfo() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Constructor<?> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            Object newInstance = declaredConstructor.newInstance(new Object[0]);
            Field declaredField = cls.getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            declaredField.set(null, newInstance);
            Class<?> cls2 = Class.forName("android.app.ActivityThread$AppBindData");
            Constructor<?> declaredConstructor2 = cls2.getDeclaredConstructor(new Class[0]);
            declaredConstructor2.setAccessible(true);
            Object newInstance2 = declaredConstructor2.newInstance(new Object[0]);
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.packageName = "com.genymobile.scrcpy";
            Field declaredField2 = cls2.getDeclaredField("appInfo");
            declaredField2.setAccessible(true);
            declaredField2.set(newInstance2, applicationInfo);
            Field declaredField3 = cls.getDeclaredField("mBoundApplication");
            declaredField3.setAccessible(true);
            declaredField3.set(newInstance, newInstance2);
            Application newApplication = Instrumentation.newApplication(Application.class, (Context) cls.getDeclaredMethod("getSystemContext", new Class[0]).invoke(newInstance, new Object[0]));
            Field declaredField4 = cls.getDeclaredField("mInitialApplication");
            declaredField4.setAccessible(true);
            declaredField4.set(newInstance, newApplication);
        } catch (Throwable th) {
            Ln.w("Could not fill app info: " + th.getMessage());
        }
    }
}
