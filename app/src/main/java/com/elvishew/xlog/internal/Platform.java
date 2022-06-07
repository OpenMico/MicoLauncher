package com.elvishew.xlog.internal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.elvishew.xlog.formatter.message.object.BundleFormatter;
import com.elvishew.xlog.formatter.message.object.IntentFormatter;
import com.elvishew.xlog.formatter.message.object.ObjectFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/internal/Platform.class */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get() {
        return PLATFORM;
    }

    @SuppressLint({"NewApi"})
    public String lineSeparator() {
        return System.lineSeparator();
    }

    public Printer defaultPrinter() {
        return new ConsolePrinter();
    }

    public Map<Class<?>, ObjectFormatter<?>> builtinObjectFormatters() {
        return Collections.emptyMap();
    }

    public void warn(String msg) {
        System.out.println(msg);
    }

    public void error(String msg) {
        System.out.println(msg);
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException e) {
        }
        return new Platform();
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/internal/Platform$Android.class */
    public static class Android extends Platform {
        private static final Map<Class<?>, ObjectFormatter<?>> BUILTIN_OBJECT_FORMATTERS;

        Android() {
        }

        static {
            Map<Class<?>, ObjectFormatter<?>> objectFormatters = new HashMap<>();
            objectFormatters.put(Bundle.class, new BundleFormatter());
            objectFormatters.put(Intent.class, new IntentFormatter());
            BUILTIN_OBJECT_FORMATTERS = Collections.unmodifiableMap(objectFormatters);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.elvishew.xlog.internal.Platform
        public String lineSeparator() {
            if (Build.VERSION.SDK_INT < 19) {
                return "\n";
            }
            return System.lineSeparator();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.elvishew.xlog.internal.Platform
        public Printer defaultPrinter() {
            return new AndroidPrinter();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.elvishew.xlog.internal.Platform
        public Map<Class<?>, ObjectFormatter<?>> builtinObjectFormatters() {
            return BUILTIN_OBJECT_FORMATTERS;
        }

        @Override // com.elvishew.xlog.internal.Platform
        public void warn(String msg) {
            Log.w("XLog", msg);
        }

        @Override // com.elvishew.xlog.internal.Platform
        public void error(String msg) {
            Log.e("XLog", msg);
        }
    }

    /* loaded from: classes.dex */
    static class a extends Platform {
        private static final Map<Class<?>, ObjectFormatter<?>> a;

        a() {
        }

        static {
            HashMap hashMap = new HashMap();
            hashMap.put(Bundle.class, new BundleFormatter());
            hashMap.put(Intent.class, new IntentFormatter());
            a = Collections.unmodifiableMap(hashMap);
        }

        String a() {
            return Build.VERSION.SDK_INT < 19 ? "\n" : System.lineSeparator();
        }

        Printer b() {
            return new AndroidPrinter();
        }

        Map<Class<?>, ObjectFormatter<?>> c() {
            return a;
        }

        @Override // com.elvishew.xlog.internal.Platform
        public void warn(String str) {
            Log.w("XLog", str);
        }

        @Override // com.elvishew.xlog.internal.Platform
        public void error(String str) {
            Log.e("XLog", str);
        }
    }
}
