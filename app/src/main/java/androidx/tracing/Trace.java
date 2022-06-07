package androidx.tracing;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class Trace {
    private static long a;
    private static Method b;
    private static Method c;
    private static Method d;
    private static Method e;

    @SuppressLint({"NewApi"})
    public static boolean isEnabled() {
        try {
            if (b == null) {
                return android.os.Trace.isEnabled();
            }
        } catch (NoClassDefFoundError | NoSuchMethodError unused) {
        }
        return a();
    }

    public static void beginSection(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 18) {
            a.a(str);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            a.a();
        }
    }

    @SuppressLint({"NewApi"})
    public static void beginAsyncSection(@NonNull String str, int i) {
        try {
            if (c == null) {
                b.a(str, i);
                return;
            }
        } catch (NoClassDefFoundError | NoSuchMethodError unused) {
        }
        a(str, i);
    }

    @SuppressLint({"NewApi"})
    public static void endAsyncSection(@NonNull String str, int i) {
        try {
            if (d == null) {
                b.b(str, i);
                return;
            }
        } catch (NoClassDefFoundError | NoSuchMethodError unused) {
        }
        b(str, i);
    }

    @SuppressLint({"NewApi"})
    public static void setCounter(@NonNull String str, int i) {
        try {
            if (e == null) {
                b.c(str, i);
                return;
            }
        } catch (NoClassDefFoundError | NoSuchMethodError unused) {
        }
        c(str, i);
    }

    private static boolean a() {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (b == null) {
                    a = android.os.Trace.class.getField("TRACE_TAG_APP").getLong(null);
                    b = android.os.Trace.class.getMethod("isTagEnabled", Long.TYPE);
                }
                return ((Boolean) b.invoke(null, Long.valueOf(a))).booleanValue();
            } catch (Exception e2) {
                a("isTagEnabled", e2);
            }
        }
        return false;
    }

    private static void a(@NonNull String str, int i) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (c == null) {
                    c = android.os.Trace.class.getMethod("asyncTraceBegin", Long.TYPE, String.class, Integer.TYPE);
                }
                c.invoke(null, Long.valueOf(a), str, Integer.valueOf(i));
            } catch (Exception e2) {
                a("asyncTraceBegin", e2);
            }
        }
    }

    private static void b(@NonNull String str, int i) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (d == null) {
                    d = android.os.Trace.class.getMethod("asyncTraceEnd", Long.TYPE, String.class, Integer.TYPE);
                }
                d.invoke(null, Long.valueOf(a), str, Integer.valueOf(i));
            } catch (Exception e2) {
                a("asyncTraceEnd", e2);
            }
        }
    }

    private static void c(@NonNull String str, int i) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (e == null) {
                    e = android.os.Trace.class.getMethod("traceCounter", Long.TYPE, String.class, Integer.TYPE);
                }
                e.invoke(null, Long.valueOf(a), str, Integer.valueOf(i));
            } catch (Exception e2) {
                a("traceCounter", e2);
            }
        }
    }

    private static void a(@NonNull String str, @NonNull Exception exc) {
        if (exc instanceof InvocationTargetException) {
            Throwable cause = exc.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new RuntimeException(cause);
        }
        Log.v("Trace", "Unable to call " + str + " via reflection", exc);
    }

    private Trace() {
    }
}
