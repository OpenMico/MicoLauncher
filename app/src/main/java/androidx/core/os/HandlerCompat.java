package androidx.core.os;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public final class HandlerCompat {
    @NonNull
    public static Handler createAsync(@NonNull Looper looper) {
        Throwable e;
        if (Build.VERSION.SDK_INT >= 28) {
            return a.a(looper);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, true);
            } catch (IllegalAccessException e2) {
                e = e2;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper);
            } catch (InstantiationException e3) {
                e = e3;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper);
            } catch (NoSuchMethodException e4) {
                e = e4;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper);
            } catch (InvocationTargetException e5) {
                Throwable cause = e5.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                } else if (cause instanceof Error) {
                    throw ((Error) cause);
                } else {
                    throw new RuntimeException(cause);
                }
            }
        }
        return new Handler(looper);
    }

    @NonNull
    public static Handler createAsync(@NonNull Looper looper, @NonNull Handler.Callback callback) {
        Throwable e;
        if (Build.VERSION.SDK_INT >= 28) {
            return a.a(looper, callback);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, callback, true);
            } catch (IllegalAccessException e2) {
                e = e2;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper, callback);
            } catch (InstantiationException e3) {
                e = e3;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper, callback);
            } catch (NoSuchMethodException e4) {
                e = e4;
                Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
                return new Handler(looper, callback);
            } catch (InvocationTargetException e5) {
                Throwable cause = e5.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                } else if (cause instanceof Error) {
                    throw ((Error) cause);
                } else {
                    throw new RuntimeException(cause);
                }
            }
        }
        return new Handler(looper, callback);
    }

    public static boolean postDelayed(@NonNull Handler handler, @NonNull Runnable runnable, @Nullable Object obj, long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            return a.a(handler, runnable, obj, j);
        }
        Message obtain = Message.obtain(handler, runnable);
        obtain.obj = obj;
        return handler.sendMessageDelayed(obtain, j);
    }

    @RequiresApi(16)
    public static boolean hasCallbacks(@NonNull Handler handler, @NonNull Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 29) {
            return b.a(handler, runnable);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                return ((Boolean) Handler.class.getMethod("hasCallbacks", Runnable.class).invoke(handler, runnable)).booleanValue();
            } catch (IllegalAccessException e) {
                e = e;
            } catch (NoSuchMethodException e2) {
                e = e2;
            } catch (NullPointerException e3) {
                e = e3;
            } catch (InvocationTargetException e4) {
                Throwable cause = e4.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                } else if (cause instanceof Error) {
                    throw ((Error) cause);
                } else {
                    throw new RuntimeException(cause);
                }
            }
        } else {
            e = null;
        }
        throw new UnsupportedOperationException("Failed to call Handler.hasCallbacks(), but there is no safe failure mode for this method. Raising exception.", e);
    }

    private HandlerCompat() {
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    private static class b {
        public static boolean a(Handler handler, Runnable runnable) {
            return handler.hasCallbacks(runnable);
        }
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    private static class a {
        public static Handler a(Looper looper) {
            return Handler.createAsync(looper);
        }

        public static Handler a(Looper looper, Handler.Callback callback) {
            return Handler.createAsync(looper, callback);
        }

        public static boolean a(Handler handler, Runnable runnable, Object obj, long j) {
            return handler.postDelayed(runnable, obj, j);
        }
    }
}
