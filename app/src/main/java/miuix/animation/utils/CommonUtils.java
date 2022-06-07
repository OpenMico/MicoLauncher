package miuix.animation.utils;

import android.animation.ArgbEvaluator;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import miuix.animation.IAnimTarget;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;

/* loaded from: classes5.dex */
public class CommonUtils {
    public static final String TAG = "miuix_anim";
    public static final int UNIT_SECOND = 1000;
    private static float a;
    public static final ArgbEvaluator sArgbEvaluator = new Object();
    private static final Class<?>[] b = {String.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class, Short.TYPE, Short.class, Float.TYPE, Float.class, Double.TYPE, Double.class};

    /* loaded from: classes5.dex */
    public interface PreDrawTask {
        boolean call();
    }

    public static boolean hasFlags(long j, long j2) {
        return (j & j2) != 0;
    }

    private CommonUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a implements ViewTreeObserver.OnPreDrawListener {
        PreDrawTask a;
        WeakReference<View> b;

        a(final Runnable runnable) {
            this.a = new PreDrawTask() { // from class: miuix.animation.utils.CommonUtils.a.1
                @Override // miuix.animation.utils.CommonUtils.PreDrawTask
                public boolean call() {
                    runnable.run();
                    return true;
                }
            };
        }

        a(PreDrawTask preDrawTask) {
            this.a = preDrawTask;
        }

        public void a(View view) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.b = new WeakReference<>(view);
            viewTreeObserver.addOnPreDrawListener(this);
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0017  */
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onPreDraw() {
            /*
                r3 = this;
                java.lang.ref.WeakReference<android.view.View> r0 = r3.b
                java.lang.Object r0 = r0.get()
                android.view.View r0 = (android.view.View) r0
                r1 = 1
                if (r0 == 0) goto L_0x001f
                miuix.animation.utils.CommonUtils$PreDrawTask r2 = r3.a
                if (r2 == 0) goto L_0x0014
                boolean r2 = r2.call()     // Catch: Exception -> 0x0014
                goto L_0x0015
            L_0x0014:
                r2 = r1
            L_0x0015:
                if (r2 == 0) goto L_0x0020
                android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
                r0.removeOnPreDrawListener(r3)
                goto L_0x0020
            L_0x001f:
                r2 = r1
            L_0x0020:
                if (r2 == 0) goto L_0x0025
                r0 = 0
                r3.a = r0
            L_0x0025:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: miuix.animation.utils.CommonUtils.a.onPreDraw():boolean");
        }
    }

    public static void runOnPreDraw(View view, Runnable runnable) {
        if (view != null) {
            new a(runnable).a(view);
        }
    }

    public static void runOnPreDraw(View view, PreDrawTask preDrawTask) {
        if (view != null) {
            new a(preDrawTask).a(view);
        }
    }

    public static int[] toIntArray(float[] fArr) {
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            iArr[i] = (int) fArr[i];
        }
        return iArr;
    }

    public static String mapsToString(Map[] mapArr) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < mapArr.length; i++) {
            sb.append('\n');
            sb.append(i);
            sb.append('.');
            sb.append((CharSequence) mapToString(mapArr[i], "    "));
        }
        sb.append('\n');
        sb.append(']');
        return sb.toString();
    }

    public static <K, V> StringBuilder mapToString(Map<K, V> map, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        if (map != null && map.size() > 0) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                sb.append('\n');
                sb.append(str);
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
            }
            sb.append('\n');
        }
        sb.append('}');
        return sb;
    }

    @SafeVarargs
    public static <T> T[] mergeArray(T[] tArr, T... tArr2) {
        if (tArr == null) {
            return tArr2;
        }
        if (tArr2 == null) {
            return tArr;
        }
        Object newInstance = Array.newInstance(tArr.getClass().getComponentType(), tArr.length + tArr2.length);
        System.arraycopy(tArr, 0, newInstance, 0, tArr.length);
        System.arraycopy(tArr2, 0, newInstance, tArr.length, tArr2.length);
        return (T[]) ((Object[]) newInstance);
    }

    public static int toIntValue(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }
        throw new IllegalArgumentException("toFloat failed, value is " + obj);
    }

    public static float toFloatValue(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).floatValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).floatValue();
        }
        throw new IllegalArgumentException("toFloat failed, value is " + obj);
    }

    public static <T> boolean isArrayEmpty(T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static <T> boolean inArray(T[] tArr, T t) {
        if (!(t == null || tArr == null || tArr.length <= 0)) {
            for (T t2 : tArr) {
                if (t2.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static float getTouchSlop(View view) {
        if (a == 0.0f && view != null) {
            a = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        }
        return a;
    }

    public static double getDistance(float f, float f2, float f3, float f4) {
        return Math.sqrt(Math.pow(f3 - f, 2.0d) + Math.pow(f4 - f2, 2.0d));
    }

    public static void getRect(IAnimTarget iAnimTarget, RectF rectF) {
        rectF.left = iAnimTarget.getValue(ViewProperty.X);
        rectF.top = iAnimTarget.getValue(ViewProperty.Y);
        rectF.right = rectF.left + iAnimTarget.getValue(ViewProperty.WIDTH);
        rectF.bottom = rectF.top + iAnimTarget.getValue(ViewProperty.HEIGHT);
    }

    public static float getSize(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        if (floatProperty == ViewProperty.X) {
            floatProperty = ViewProperty.WIDTH;
        } else if (floatProperty == ViewProperty.Y) {
            floatProperty = ViewProperty.HEIGHT;
        } else if (!(floatProperty == ViewProperty.WIDTH || floatProperty == ViewProperty.HEIGHT)) {
            floatProperty = null;
        }
        if (floatProperty == null) {
            return 0.0f;
        }
        return iAnimTarget.getValue(floatProperty);
    }

    public static boolean isBuiltInClass(Class<?> cls) {
        return inArray(b, cls);
    }

    public static <T> void addTo(Collection<T> collection, Collection<T> collection2) {
        for (T t : collection) {
            if (!collection2.contains(t)) {
                collection2.add(t);
            }
        }
    }

    public static String readProp(String str) {
        Throwable th;
        InputStreamReader inputStreamReader;
        IOException e;
        BufferedReader bufferedReader;
        try {
            BufferedReader bufferedReader2 = null;
            try {
                inputStreamReader = new InputStreamReader(Runtime.getRuntime().exec("getprop " + str).getInputStream());
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                } catch (IOException e2) {
                    e = e2;
                }
                try {
                    String readLine = bufferedReader.readLine();
                    a(bufferedReader);
                    a(inputStreamReader);
                    return readLine;
                } catch (IOException e3) {
                    e = e3;
                    bufferedReader2 = bufferedReader;
                    Log.i(TAG, "readProp failed", e);
                    a(bufferedReader2);
                    a(inputStreamReader);
                    return "";
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader2 = bufferedReader;
                    a(bufferedReader2);
                    a(inputStreamReader);
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                inputStreamReader = null;
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.w(TAG, "close " + closeable + " failed", e);
            }
        }
    }

    public static <T> T getLocal(ThreadLocal<T> threadLocal, Class cls) {
        T t = threadLocal.get();
        if (t != null || cls == null) {
            return t;
        }
        T t2 = (T) ObjectPool.acquire(cls, new Object[0]);
        threadLocal.set(t2);
        return t2;
    }
}
