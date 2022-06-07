package com.milink.runtime.provider;

import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;
import com.milink.base.utils.Logger;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class DataProviderManager {
    private final SparseArrayCompat<b> a;
    private final AtomicInteger b;
    private final UriMatcher c;
    private final Map<Class<? extends AccessFilter>, AccessFilter> d;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a {
        static final DataProviderManager a = new DataProviderManager();
    }

    private DataProviderManager() {
        this.a = new SparseArrayCompat<>();
        this.b = new AtomicInteger(1);
        this.c = new UriMatcher(-1);
        this.d = new ArrayMap(5);
    }

    public static DataProviderManager getInstance() {
        return a.a;
    }

    public DataProviderManager deploy(@NonNull Class<? extends DataProvider> cls) {
        b a2 = b.a((Class) Objects.requireNonNull(cls));
        int andIncrement = this.b.getAndIncrement();
        synchronized (this.a) {
            this.c.addURI("*", a2.c, andIncrement);
            UriMatcher uriMatcher = this.c;
            uriMatcher.addURI("*", a2.c + "/*", andIncrement);
            this.a.put(andIncrement, a2);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public b a(@NonNull Uri uri) {
        synchronized (this.a) {
            int match = this.c.match(uri);
            if (match == -1) {
                return null;
            }
            return this.a.get(match);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(@NonNull Context context, @NonNull b bVar, @NonNull Uri uri, int i) {
        Class<? extends AccessFilter>[] clsArr = bVar.d;
        if (clsArr == null || clsArr.length == 0) {
            return 0;
        }
        int i2 = 0;
        for (Class<? extends AccessFilter> cls : clsArr) {
            if (cls != null) {
                synchronized (this.d) {
                    AccessFilter accessFilter = this.d.get(cls);
                    if (accessFilter == null) {
                        accessFilter = a(cls);
                        if (accessFilter != null) {
                            this.d.put(cls, accessFilter);
                        }
                    }
                    i2 = accessFilter.onAcceptAccess(context, uri, i);
                    if (i2 != 0) {
                        break;
                    }
                }
            }
        }
        return i2;
    }

    @Nullable
    private AccessFilter a(@NonNull Class<? extends AccessFilter> cls) {
        try {
            return (AccessFilter) cls.newInstance();
        } catch (Exception e) {
            Logger.e("DataProviderManager", e, "create access filter fail: %s", cls);
            return null;
        }
    }
}
