package retrofit2;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Platform.java */
/* loaded from: classes6.dex */
public class h {
    private static final h a = f();
    private final boolean b;
    @Nullable
    private final Constructor<MethodHandles.Lookup> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Executor b() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static h a() {
        return a;
    }

    private static h f() {
        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
            return new a();
        }
        return new h(true);
    }

    h(boolean z) {
        this.b = z;
        Constructor<MethodHandles.Lookup> constructor = null;
        if (z) {
            try {
                constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                constructor.setAccessible(true);
            } catch (NoClassDefFoundError | NoSuchMethodException unused) {
            }
        }
        this.c = constructor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<? extends CallAdapter.Factory> a(@Nullable Executor executor) {
        c cVar = new c(executor);
        return this.b ? Arrays.asList(b.a, cVar) : Collections.singletonList(cVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.b ? 2 : 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<? extends Converter.Factory> d() {
        return this.b ? Collections.singletonList(f.a) : Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int e() {
        return this.b ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @IgnoreJRERequirement
    public boolean a(Method method) {
        return this.b && method.isDefault();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    @IgnoreJRERequirement
    public Object a(Method method, Class<?> cls, Object obj, Object... objArr) throws Throwable {
        Constructor<MethodHandles.Lookup> constructor = this.c;
        return (constructor != null ? constructor.newInstance(cls, -1) : MethodHandles.lookup()).unreflectSpecial(method, cls).bindTo(obj).invokeWithArguments(objArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Platform.java */
    /* loaded from: classes6.dex */
    public static final class a extends h {
        a() {
            super(Build.VERSION.SDK_INT >= 24);
        }

        @Override // retrofit2.h
        public Executor b() {
            return new ExecutorC0395a();
        }

        @Override // retrofit2.h
        @Nullable
        Object a(Method method, Class<?> cls, Object obj, Object... objArr) throws Throwable {
            if (Build.VERSION.SDK_INT >= 26) {
                return h.super.a(method, cls, obj, objArr);
            }
            throw new UnsupportedOperationException("Calling default methods on API 24 and 25 is not supported");
        }

        /* compiled from: Platform.java */
        /* renamed from: retrofit2.h$a$a  reason: collision with other inner class name */
        /* loaded from: classes6.dex */
        static final class ExecutorC0395a implements Executor {
            private final Handler a = new Handler(Looper.getMainLooper());

            ExecutorC0395a() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                this.a.post(runnable);
            }
        }
    }
}
