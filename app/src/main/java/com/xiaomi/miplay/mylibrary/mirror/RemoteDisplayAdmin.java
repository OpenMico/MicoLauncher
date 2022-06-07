package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes4.dex */
public class RemoteDisplayAdmin {
    private static String a = "MiPlayQuickRemoteDisplayAdmin";
    private static Class b;
    private static Method c;
    private static Method d;
    private static Object e;
    private static Class<?> f;
    private static a g = new a();
    private static WeakReference<Listener> h = null;

    /* loaded from: classes4.dex */
    public interface Listener {
        String getNextPhoto(String str, boolean z);

        String getPrevPhoto(String str, boolean z);

        void onDisplayConnected(Surface surface, int i, int i2, int i3, int i4);

        void onDisplayDisconnected();

        void onDisplayError(int i);

        void onMultiDisplayAudioCaptureInitDone(int i);

        void onMultiDisplayConnected(int i, String str);

        void onMultiDisplayDisConnected(int i);

        void onMultiDisplayError(int i, int i2, int i3);

        void onMultiDisplayInfo(int i, int i2, int i3);

        void onMultiDisplayPrepared(int i, int i2);
    }

    static {
        try {
            Class<?>[] declaredClasses = Class.forName("android.media.RemoteDisplay").getDeclaredClasses();
            for (Class<?> cls : declaredClasses) {
                Log.d(a, "interfaze.getSimpleName() = " + cls.getSimpleName());
                if (cls.getSimpleName().equalsIgnoreCase("Listener")) {
                    f = cls;
                    e = Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, g);
                    Log.d(a, "mRemoteDisplayListener inited");
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            b = Class.forName("com.qualcomm.wfd.ExtendedRemoteDisplay");
        } catch (Throwable unused) {
            Log.i(a, "ExtendedRemoteDisplay Not available.");
        }
        if (b != null) {
            Log.i(a, "ExtendedRemoteDisplay Is available. Find Methods");
            try {
                c = b.getDeclaredMethod("listen", String.class, f, Handler.class, Context.class);
            } catch (Throwable unused2) {
                Log.i(a, "ExtendedRemoteDisplay.listen Not available.");
            }
            try {
                d = b.getDeclaredMethod("dispose", new Class[0]);
            } catch (Throwable unused3) {
                Log.i(a, "ExtendedRemoteDisplay.dispose Not available.");
            }
        }
    }

    /* loaded from: classes4.dex */
    private static class a implements InvocationHandler {
        private a() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            try {
                String str = RemoteDisplayAdmin.a;
                Log.d(str, "method.getName = " + method.getName());
                if (objArr == null || !method.getName().equals("onDisplayConnected") || objArr.length != 5 || !(objArr[0] instanceof Surface) || !(objArr[1] instanceof Integer) || !(objArr[2] instanceof Integer) || !(objArr[3] instanceof Integer) || !(objArr[4] instanceof Integer)) {
                    if (objArr == null || !method.getName().equals("onDisplayError") || !(objArr[0] instanceof Integer)) {
                        if (!method.getName().equals("onDisplayDisconnected") || RemoteDisplayAdmin.h == null || RemoteDisplayAdmin.h.get() == null) {
                            return null;
                        }
                        ((Listener) RemoteDisplayAdmin.h.get()).onDisplayDisconnected();
                        return null;
                    } else if (RemoteDisplayAdmin.h == null || RemoteDisplayAdmin.h.get() == null) {
                        return null;
                    } else {
                        ((Listener) RemoteDisplayAdmin.h.get()).onDisplayError(((Integer) objArr[0]).intValue());
                        return null;
                    }
                } else if (RemoteDisplayAdmin.h == null || RemoteDisplayAdmin.h.get() == null) {
                    return null;
                } else {
                    ((Listener) RemoteDisplayAdmin.h.get()).onDisplayConnected((Surface) objArr[0], ((Integer) objArr[1]).intValue(), ((Integer) objArr[2]).intValue(), ((Integer) objArr[3]).intValue(), ((Integer) objArr[4]).intValue());
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
            }
        }
    }

    public static Object listen(String str, Listener listener, Handler handler, Context context) {
        Log.i(a, "ExtendedRemoteDisplay.listen");
        if (listener != null) {
            h = new WeakReference<>(listener);
        }
        Method method = c;
        if (method == null || d == null) {
            return null;
        }
        try {
            return method.invoke(null, str, e, handler, context);
        } catch (IllegalAccessException e2) {
            Log.i(a, "ExtendedRemoteDisplay.listen -IllegalAccessException");
            e2.printStackTrace();
            return null;
        } catch (InvocationTargetException e3) {
            Log.i(a, "ExtendedRemoteDisplay.listen - InvocationTargetException");
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(e3);
            }
        }
    }

    public static void dispose(Object obj) {
        Log.i(a, "ExtendedRemoteDisplay.dispose");
        try {
            d.invoke(obj, new Object[0]);
            h = null;
        } catch (IllegalAccessException e2) {
            Log.i(a, "ExtendedRemoteDisplay.dispose-IllegalAccessException");
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            Log.i(a, "ExtendedRemoteDisplay.dispose - InvocationTargetException");
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(e3);
            }
        }
    }

    public static boolean isAvailable() {
        if (b == null || d == null || c == null) {
            Log.i(a, "ExtendedRemoteDisplay isAvailable() : Not Available.");
            return false;
        }
        Log.i(a, "ExtendedRemoteDisplay isAvailable() : Available.");
        return true;
    }
}
