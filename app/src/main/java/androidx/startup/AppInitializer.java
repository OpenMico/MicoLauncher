package androidx.startup;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.tracing.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class AppInitializer {
    private static volatile AppInitializer d;
    private static final Object e = new Object();
    @NonNull
    final Context c;
    @NonNull
    final Set<Class<? extends Initializer<?>>> b = new HashSet();
    @NonNull
    final Map<Class<?>, Object> a = new HashMap();

    AppInitializer(@NonNull Context context) {
        this.c = context.getApplicationContext();
    }

    @NonNull
    public static AppInitializer getInstance(@NonNull Context context) {
        if (d == null) {
            synchronized (e) {
                if (d == null) {
                    d = new AppInitializer(context);
                }
            }
        }
        return d;
    }

    @NonNull
    public <T> T initializeComponent(@NonNull Class<? extends Initializer<T>> cls) {
        return (T) a(cls, new HashSet());
    }

    public boolean isEagerlyInitialized(@NonNull Class<? extends Initializer<?>> cls) {
        return this.b.contains(cls);
    }

    @NonNull
    <T> T a(@NonNull Class<? extends Initializer<?>> cls, @NonNull Set<Class<?>> set) {
        T t;
        synchronized (e) {
            if (Trace.isEnabled()) {
                Trace.beginSection(cls.getSimpleName());
            }
            if (!set.contains(cls)) {
                if (!this.a.containsKey(cls)) {
                    set.add(cls);
                    try {
                        Initializer initializer = (Initializer) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                        List<Class<? extends Initializer<?>>> dependencies = initializer.dependencies();
                        if (!dependencies.isEmpty()) {
                            for (Class<? extends Initializer<?>> cls2 : dependencies) {
                                if (!this.a.containsKey(cls2)) {
                                    a(cls2, set);
                                }
                            }
                        }
                        t = (T) initializer.create(this.c);
                        set.remove(cls);
                        this.a.put(cls, t);
                    } catch (Throwable th) {
                        throw new StartupException(th);
                    }
                } else {
                    t = (T) this.a.get(cls);
                }
                Trace.endSection();
            } else {
                throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", cls.getName()));
            }
        }
        return t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a() {
        try {
            try {
                Trace.beginSection("Startup");
                Bundle bundle = this.c.getPackageManager().getProviderInfo(new ComponentName(this.c.getPackageName(), InitializationProvider.class.getName()), 128).metaData;
                String string = this.c.getString(R.string.androidx_startup);
                if (bundle != null) {
                    HashSet hashSet = new HashSet();
                    for (String str : bundle.keySet()) {
                        if (string.equals(bundle.getString(str, null))) {
                            Class<?> cls = Class.forName(str);
                            if (Initializer.class.isAssignableFrom(cls)) {
                                this.b.add(cls);
                                a(cls, hashSet);
                            }
                        }
                    }
                }
            } finally {
                Trace.endSection();
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e2) {
            throw new StartupException(e2);
        }
    }
}
