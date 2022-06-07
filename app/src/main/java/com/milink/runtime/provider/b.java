package com.milink.runtime.provider;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.Objects;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DataProviderInfo.java */
/* loaded from: classes2.dex */
public class b {
    final String a;
    final String b;
    final String c;
    final Class<? extends AccessFilter>[] d;
    private final a e;

    private b(@NonNull String str, @NonNull String str2, @NonNull String str3, Class<? extends AccessFilter>[] clsArr, @NonNull a aVar) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = clsArr;
        this.e = (a) Objects.requireNonNull(aVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static b a(@NonNull Class<? extends DataProvider> cls) {
        ProviderConfiguration providerConfiguration = (ProviderConfiguration) ((Class) Objects.requireNonNull(cls)).getAnnotation(ProviderConfiguration.class);
        if (providerConfiguration != null) {
            String a2 = a(providerConfiguration.path());
            try {
                return new b(providerConfiguration.readPermission(), providerConfiguration.writePermission(), (String) Objects.requireNonNull(a2), providerConfiguration.accessFilters(), new a(cls.getConstructor(new Class[0]), a2));
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("not found empty param constructor", e);
            }
        } else {
            throw new IllegalStateException("you need declared ProviderConfiguration");
        }
    }

    private static String a(String str) {
        if (str == null) {
            throw new IllegalStateException("root path is null");
        } else if (!str.contains("*") && !str.endsWith("/")) {
            return str;
        } else {
            throw new IllegalStateException("root path can't contains '*' or end with '/'.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final DataProvider a(@NonNull Context context) {
        return this.e.a(context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DataProviderInfo.java */
    /* loaded from: classes2.dex */
    public static class a {
        private static final Object a = new Object();
        private final Constructor<? extends DataProvider> b;
        private final String c;
        private volatile Object d = a;

        a(Constructor<? extends DataProvider> constructor, String str) {
            this.b = constructor;
            this.c = str;
        }

        DataProvider a(@NonNull Context context) {
            if (this.d != a) {
                return (DataProvider) this.d;
            }
            synchronized (this) {
                if (this.d != a) {
                    return (DataProvider) this.d;
                }
                DataProvider a2 = a();
                a2.a(context);
                a2.a(this.c);
                if (!a2.onCreate()) {
                    a2 = null;
                }
                this.d = a2;
                return (DataProvider) this.d;
            }
        }

        @NonNull
        private DataProvider a() {
            try {
                this.b.setAccessible(true);
                return (DataProvider) this.b.newInstance(new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("new DataProvider instance fail", e);
            }
        }
    }
}
