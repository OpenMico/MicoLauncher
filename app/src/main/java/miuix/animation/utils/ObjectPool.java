package miuix.animation.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes5.dex */
public class ObjectPool {
    private static final Handler a = new Handler(Looper.getMainLooper());
    private static final ConcurrentHashMap<Class<?>, a> b = new ConcurrentHashMap<>();

    /* loaded from: classes5.dex */
    public interface IPoolObject {
        void clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        final ConcurrentLinkedQueue<Object> a;
        final ConcurrentHashMap<Object, Boolean> b;
        final Runnable c;

        private a() {
            this.a = new ConcurrentLinkedQueue<>();
            this.b = new ConcurrentHashMap<>();
            this.c = new Runnable() { // from class: miuix.animation.utils.ObjectPool.a.1
                @Override // java.lang.Runnable
                public void run() {
                    a.this.a();
                }
            };
        }

        <T> T a(Class<T> cls, Object... objArr) {
            T t = (T) this.a.poll();
            if (t == null) {
                return cls != null ? (T) ObjectPool.b(cls, objArr) : t;
            }
            this.b.remove(t);
            return t;
        }

        void a(Object obj) {
            if (this.b.putIfAbsent(obj, true) == null) {
                this.a.add(obj);
                ObjectPool.a.removeCallbacks(this.c);
                if (this.a.size() > 10) {
                    ObjectPool.a.postDelayed(this.c, 5000L);
                }
            }
        }

        void a() {
            Object poll;
            while (this.a.size() > 10 && (poll = this.a.poll()) != null) {
                this.b.remove(poll);
            }
        }
    }

    private ObjectPool() {
    }

    public static <T> T acquire(Class<T> cls, Object... objArr) {
        return (T) a((Class<?>) cls, true).a(cls, objArr);
    }

    public static void release(Object obj) {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            if (obj instanceof IPoolObject) {
                ((IPoolObject) obj).clear();
            } else if (obj instanceof Collection) {
                ((Collection) obj).clear();
            } else if (obj instanceof Map) {
                ((Map) obj).clear();
            }
            a a2 = a(cls, false);
            if (a2 != null) {
                a2.a(obj);
            }
        }
    }

    private static a a(Class<?> cls, boolean z) {
        a aVar = b.get(cls);
        if (aVar != null || !z) {
            return aVar;
        }
        a aVar2 = new a();
        a putIfAbsent = b.putIfAbsent(cls, aVar2);
        return putIfAbsent != null ? putIfAbsent : aVar2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object b(Class<?> cls, Object... objArr) {
        try {
            Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                if (constructor.getParameterTypes().length == objArr.length) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(objArr);
                }
            }
            return null;
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "ObjectPool.createObject failed, clz = " + cls, e);
            return null;
        }
    }
}
