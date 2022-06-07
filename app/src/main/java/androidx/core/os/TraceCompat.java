package androidx.core.os;

import android.os.Build;
import android.os.Trace;
import android.util.Log;
import androidx.annotation.NonNull;
import java.lang.reflect.Method;

@Deprecated
/* loaded from: classes.dex */
public final class TraceCompat {
    private static long a;
    private static Method b;
    private static Method c;
    private static Method d;
    private static Method e;

    static {
        if (Build.VERSION.SDK_INT >= 18 && Build.VERSION.SDK_INT < 29) {
            try {
                a = Trace.class.getField("TRACE_TAG_APP").getLong(null);
                b = Trace.class.getMethod("isTagEnabled", Long.TYPE);
                c = Trace.class.getMethod("asyncTraceBegin", Long.TYPE, String.class, Integer.TYPE);
                d = Trace.class.getMethod("asyncTraceEnd", Long.TYPE, String.class, Integer.TYPE);
                e = Trace.class.getMethod("traceCounter", Long.TYPE, String.class, Integer.TYPE);
            } catch (Exception e2) {
                Log.i("TraceCompat", "Unable to initialize via reflection.", e2);
            }
        }
    }

    public static boolean isEnabled() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Trace.isEnabled();
        }
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                return ((Boolean) b.invoke(null, Long.valueOf(a))).booleanValue();
            } catch (Exception unused) {
                Log.v("TraceCompat", "Unable to invoke isTagEnabled() via reflection.");
            }
        }
        return false;
    }

    public static void beginSection(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.beginSection(str);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }

    public static void beginAsyncSection(@NonNull String str, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            Trace.beginAsyncSection(str, i);
        } else if (Build.VERSION.SDK_INT >= 18) {
            try {
                c.invoke(null, Long.valueOf(a), str, Integer.valueOf(i));
            } catch (Exception unused) {
                Log.v("TraceCompat", "Unable to invoke asyncTraceBegin() via reflection.");
            }
        }
    }

    public static void endAsyncSection(@NonNull String str, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            Trace.endAsyncSection(str, i);
        } else if (Build.VERSION.SDK_INT >= 18) {
            try {
                d.invoke(null, Long.valueOf(a), str, Integer.valueOf(i));
            } catch (Exception unused) {
                Log.v("TraceCompat", "Unable to invoke endAsyncSection() via reflection.");
            }
        }
    }

    public static void setCounter(@NonNull String str, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            Trace.setCounter(str, i);
        } else if (Build.VERSION.SDK_INT >= 18) {
            try {
                e.invoke(null, Long.valueOf(a), str, Integer.valueOf(i));
            } catch (Exception unused) {
                Log.v("TraceCompat", "Unable to invoke traceCounter() via reflection.");
            }
        }
    }

    private TraceCompat() {
    }
}
