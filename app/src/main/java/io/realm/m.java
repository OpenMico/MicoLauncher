package io.realm;

import android.os.SystemClock;
import io.realm.BaseRealm;
import io.realm.exceptions.RealmFileException;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmNotifier;
import io.realm.internal.Util;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.util.Pair;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmCache.java */
/* loaded from: classes5.dex */
public final class m {
    private static final List<WeakReference<m>> d = new ArrayList();
    private static final Collection<m> f = new ConcurrentLinkedQueue();
    private final String b;
    private RealmConfiguration c;
    private final Map<Pair<e, OsSharedRealm.VersionID>, f> a = new HashMap();
    private final AtomicBoolean e = new AtomicBoolean(false);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public interface a {
        void a(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public interface b {
        void a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public static abstract class f {
        protected final ThreadLocal<Integer> a;
        protected AtomicInteger b;

        abstract void a(BaseRealm baseRealm);

        abstract boolean a();

        abstract BaseRealm b();

        abstract void c();

        abstract int d();

        private f() {
            this.a = new ThreadLocal<>();
            this.b = new AtomicInteger(0);
        }

        public void a(int i) {
            Integer num = this.a.get();
            ThreadLocal<Integer> threadLocal = this.a;
            if (num != null) {
                i += num.intValue();
            }
            threadLocal.set(Integer.valueOf(i));
        }

        public void b(int i) {
            this.a.set(Integer.valueOf(i));
        }

        public int e() {
            return this.b.get();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public static class d extends f {
        private BaseRealm c;

        private d() {
            super();
        }

        @Override // io.realm.m.f
        boolean a() {
            return this.c != null;
        }

        @Override // io.realm.m.f
        BaseRealm b() {
            return this.c;
        }

        @Override // io.realm.m.f
        void a(BaseRealm baseRealm) {
            this.c = baseRealm;
            this.a.set(0);
            this.b.incrementAndGet();
        }

        @Override // io.realm.m.f
        public void c() {
            String path = this.c.getPath();
            this.a.set(null);
            this.c = null;
            if (this.b.decrementAndGet() < 0) {
                throw new IllegalStateException("Global reference counter of Realm" + path + " not be negative.");
            }
        }

        @Override // io.realm.m.f
        int d() {
            return this.b.get();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public static class g extends f {
        private final ThreadLocal<BaseRealm> c;

        private g() {
            super();
            this.c = new ThreadLocal<>();
        }

        @Override // io.realm.m.f
        public boolean a() {
            return this.c.get() != null;
        }

        @Override // io.realm.m.f
        public BaseRealm b() {
            return this.c.get();
        }

        @Override // io.realm.m.f
        public void a(BaseRealm baseRealm) {
            this.c.set(baseRealm);
            this.a.set(0);
            this.b.incrementAndGet();
        }

        @Override // io.realm.m.f
        public void c() {
            String path = this.c.get().getPath();
            this.a.set(null);
            this.c.set(null);
            if (this.b.decrementAndGet() < 0) {
                throw new IllegalStateException("Global reference counter of Realm" + path + " can not be negative.");
            }
        }

        @Override // io.realm.m.f
        public int d() {
            Integer num = (Integer) this.a.get();
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public enum e {
        TYPED_REALM,
        DYNAMIC_REALM;

        static e a(Class<? extends BaseRealm> cls) {
            if (cls == Realm.class) {
                return TYPED_REALM;
            }
            if (cls == DynamicRealm.class) {
                return DYNAMIC_REALM;
            }
            throw new IllegalArgumentException("The type of Realm class must be Realm or DynamicRealm.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RealmCache.java */
    /* loaded from: classes5.dex */
    public static class c<T extends BaseRealm> implements Runnable {
        private final RealmConfiguration a;
        private final BaseRealm.InstanceCallback<T> b;
        private final Class<T> c;
        private final CountDownLatch d = new CountDownLatch(1);
        private final RealmNotifier e;
        private Future f;

        c(RealmNotifier realmNotifier, RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
            this.a = realmConfiguration;
            this.c = cls;
            this.b = instanceCallback;
            this.e = realmNotifier;
        }

        public void a(Future future) {
            this.f = future;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseRealm baseRealm = null;
            try {
                try {
                    baseRealm = m.a(this.a, this.c);
                    if (!this.e.post(new Runnable() { // from class: io.realm.m.c.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th;
                            if (c.this.f != null && !c.this.f.isCancelled()) {
                                BaseRealm baseRealm2 = null;
                                try {
                                    baseRealm2 = m.a(c.this.a, c.this.c);
                                    c.this.d.countDown();
                                    th = null;
                                } catch (Throwable th2) {
                                    th = th2;
                                } finally {
                                    c.this.d.countDown();
                                }
                                if (baseRealm2 != null) {
                                    c.this.b.onSuccess(baseRealm2);
                                } else {
                                    c.this.b.onError(th);
                                }
                            }
                        }
                    })) {
                        this.d.countDown();
                    }
                    if (!this.d.await(2L, TimeUnit.SECONDS)) {
                        RealmLog.warn("Timeout for creating Realm instance in foreground thread in `CreateRealmRunnable` ", new Object[0]);
                    }
                    if (baseRealm == null) {
                    }
                } catch (InterruptedException e) {
                    RealmLog.warn(e, "`CreateRealmRunnable` has been interrupted.", new Object[0]);
                }
            } finally {
                if (baseRealm != null) {
                    baseRealm.close();
                }
            }
        }
    }

    private m(String str) {
        this.b = str;
    }

    private static m a(String str, boolean z) {
        m mVar;
        synchronized (d) {
            Iterator<WeakReference<m>> it = d.iterator();
            mVar = null;
            while (it.hasNext()) {
                m mVar2 = it.next().get();
                if (mVar2 == null) {
                    it.remove();
                } else if (mVar2.b.equals(str)) {
                    mVar = mVar2;
                }
            }
            if (mVar == null && z) {
                mVar = new m(str);
                d.add(new WeakReference<>(mVar));
            }
        }
        return mVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T extends BaseRealm> RealmAsyncTask a(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        return a(realmConfiguration.getPath(), true).b(realmConfiguration, instanceCallback, cls);
    }

    private synchronized <T extends BaseRealm> RealmAsyncTask b(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        Future<?> submitTransaction;
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        androidCapabilities.checkCanDeliverNotification("Realm instances cannot be loaded asynchronously on a non-looper thread.");
        if (instanceCallback != null) {
            c cVar = new c(new AndroidRealmNotifier(null, androidCapabilities), realmConfiguration, instanceCallback, cls);
            submitTransaction = BaseRealm.b.submitTransaction(cVar);
            cVar.a(submitTransaction);
            ObjectServerFacade.getSyncFacadeIfPossible().createNativeSyncSession(realmConfiguration);
        } else {
            throw new IllegalArgumentException("The callback cannot be null.");
        }
        return new RealmAsyncTaskImpl(submitTransaction, BaseRealm.b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E extends BaseRealm> E a(RealmConfiguration realmConfiguration, Class<E> cls) {
        return (E) a(realmConfiguration.getPath(), true).b(realmConfiguration, cls, OsSharedRealm.VersionID.LIVE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E extends BaseRealm> E a(RealmConfiguration realmConfiguration, Class<E> cls, OsSharedRealm.VersionID versionID) {
        return (E) a(realmConfiguration.getPath(), true).b(realmConfiguration, cls, versionID);
    }

    private synchronized <E extends BaseRealm> E b(RealmConfiguration realmConfiguration, Class<E> cls, OsSharedRealm.VersionID versionID) {
        f a2;
        Throwable th;
        a2 = a(cls, versionID);
        boolean z = c() == 0;
        boolean z2 = !realmConfiguration.d();
        if (z) {
            d(realmConfiguration);
            OsSharedRealm osSharedRealm = null;
            try {
                if (realmConfiguration.f() && z2) {
                    ObjectServerFacade.getSyncFacadeIfPossible().wrapObjectStoreSessionIfRequired(new OsRealmConfig.Builder(realmConfiguration).build());
                    if (ObjectServerFacade.getSyncFacadeIfPossible().isPartialRealm(realmConfiguration)) {
                        OsSharedRealm instance = OsSharedRealm.getInstance(realmConfiguration, OsSharedRealm.VersionID.LIVE);
                        try {
                            ObjectServerFacade.getSyncFacadeIfPossible().downloadInitialRemoteChanges(realmConfiguration);
                            osSharedRealm = instance;
                        } catch (Throwable th2) {
                            th = th2;
                            osSharedRealm = instance;
                            if (osSharedRealm != null) {
                                osSharedRealm.close();
                            }
                            throw th;
                        }
                    } else {
                        ObjectServerFacade.getSyncFacadeIfPossible().downloadInitialRemoteChanges(realmConfiguration);
                    }
                }
                if (osSharedRealm != null) {
                    osSharedRealm.close();
                }
                this.c = realmConfiguration;
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            c(realmConfiguration);
        }
        if (!a2.a()) {
            a(cls, a2, z2, versionID);
        }
        a2.a(1);
        return (E) a2.b();
    }

    private <E extends BaseRealm> f a(Class<E> cls, OsSharedRealm.VersionID versionID) {
        Pair<e, OsSharedRealm.VersionID> pair = new Pair<>(e.a(cls), versionID);
        f fVar = this.a.get(pair);
        if (fVar == null) {
            if (versionID.equals(OsSharedRealm.VersionID.LIVE)) {
                fVar = new g();
            } else {
                fVar = new d();
            }
            this.a.put(pair, fVar);
        }
        return fVar;
    }

    private <E extends BaseRealm> void a(Class<E> cls, f fVar, boolean z, OsSharedRealm.VersionID versionID) {
        BaseRealm baseRealm;
        if (cls == Realm.class) {
            baseRealm = Realm.a(this, versionID);
            a((Realm) baseRealm, z);
        } else if (cls == DynamicRealm.class) {
            baseRealm = DynamicRealm.a(this, versionID);
        } else {
            throw new IllegalArgumentException("The type of Realm class must be Realm or DynamicRealm.");
        }
        fVar.a(baseRealm);
    }

    private static void a(Realm realm, boolean z) {
        if (z) {
            try {
                ObjectServerFacade.getSyncFacadeIfPossible().downloadInitialSubscriptions(realm);
            } catch (Throwable unused) {
                realm.close();
                b(realm.getConfiguration());
            }
        }
    }

    private static void b(RealmConfiguration realmConfiguration) {
        int i = 5;
        boolean z = false;
        while (i > 0 && !z) {
            try {
                z = BaseRealm.a(realmConfiguration);
            } catch (IllegalStateException unused) {
                i--;
                RealmLog.warn("Sync server still holds a reference to the Realm. It cannot be deleted. Retrying " + i + " more times", new Object[0]);
                if (i > 0) {
                    SystemClock.sleep(15L);
                }
            }
        }
        if (!z) {
            RealmLog.error("Failed to delete the underlying Realm file: " + realmConfiguration.getPath(), new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(BaseRealm baseRealm) {
        BaseRealm b2;
        String path = baseRealm.getPath();
        f a2 = a(baseRealm.getClass(), baseRealm.isFrozen() ? baseRealm.sharedRealm.getVersionID() : OsSharedRealm.VersionID.LIVE);
        int d2 = a2.d();
        if (d2 <= 0) {
            RealmLog.warn("%s has been closed already. refCount is %s", path, Integer.valueOf(d2));
            return;
        }
        int i = d2 - 1;
        if (i == 0) {
            a2.c();
            baseRealm.b();
            if (d() == 0) {
                this.c = null;
                for (f fVar : this.a.values()) {
                    if ((fVar instanceof d) && (b2 = fVar.b()) != null) {
                        while (!b2.isClosed()) {
                            b2.close();
                        }
                    }
                }
                ObjectServerFacade.getFacade(baseRealm.getConfiguration().f()).realmClosed(baseRealm.getConfiguration());
            }
        } else {
            a2.b(i);
        }
    }

    private void c(RealmConfiguration realmConfiguration) {
        if (!this.c.equals(realmConfiguration)) {
            if (Arrays.equals(this.c.getEncryptionKey(), realmConfiguration.getEncryptionKey())) {
                RealmMigration migration = realmConfiguration.getMigration();
                RealmMigration migration2 = this.c.getMigration();
                if (migration2 == null || migration == null || !migration2.getClass().equals(migration.getClass()) || migration.equals(migration2)) {
                    throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. \nCached configuration: \n" + this.c + "\n\nNew configuration: \n" + realmConfiguration);
                }
                throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. The most likely cause is that equals() and hashCode() are not overridden in the migration class: " + realmConfiguration.getMigration().getClass().getCanonicalName());
            }
            throw new IllegalArgumentException("Wrong key used to decrypt Realm.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(RealmConfiguration realmConfiguration, a aVar) {
        synchronized (d) {
            m a2 = a(realmConfiguration.getPath(), false);
            if (a2 == null) {
                aVar.a(0);
            } else {
                a2.a(aVar);
            }
        }
    }

    private synchronized void a(a aVar) {
        aVar.a(c());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(b bVar) {
        bVar.a();
    }

    private static void d(final RealmConfiguration realmConfiguration) {
        final File file = realmConfiguration.b() ? new File(realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()) : null;
        final String syncServerCertificateAssetName = ObjectServerFacade.getFacade(realmConfiguration.f()).getSyncServerCertificateAssetName(realmConfiguration);
        final boolean z = !Util.isEmptyString(syncServerCertificateAssetName);
        if (file != null || z) {
            OsObjectStore.callWithLock(realmConfiguration, new Runnable() { // from class: io.realm.m.1
                @Override // java.lang.Runnable
                public void run() {
                    if (file != null) {
                        m.b(realmConfiguration.c(), file);
                    }
                    if (z) {
                        m.b(syncServerCertificateAssetName, new File(ObjectServerFacade.getFacade(realmConfiguration.f()).getSyncServerCertificateFilePath(realmConfiguration)));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, File file) {
        Throwable th;
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        IOException e2;
        if (!file.exists()) {
            try {
                IOException e3 = null;
                try {
                    inputStream = BaseRealm.a.getAssets().open(str);
                    try {
                        if (inputStream != null) {
                            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                            try {
                                byte[] bArr = new byte[4096];
                                while (true) {
                                    int read = inputStream.read(bArr);
                                    if (read <= -1) {
                                        break;
                                    }
                                    fileOutputStream2.write(bArr, 0, read);
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e4) {
                                        e3 = e4;
                                    }
                                }
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException e5) {
                                    e3 = e5;
                                    if (e3 == null) {
                                    }
                                }
                                if (e3 != null) {
                                    throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, e3);
                                }
                            } catch (IOException e6) {
                                e2 = e6;
                                throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Could not resolve the path to the asset file: " + str, e2);
                            }
                        } else {
                            throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Invalid input stream to the asset file: " + str);
                        }
                    } catch (IOException e7) {
                        e2 = e7;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e8) {
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e9) {
                    e2 = e9;
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = null;
                    fileOutputStream = null;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(RealmConfiguration realmConfiguration) {
        int i = 0;
        m a2 = a(realmConfiguration.getPath(), false);
        if (a2 == null) {
            return 0;
        }
        for (f fVar : a2.a.values()) {
            i += fVar.d();
        }
        return i;
    }

    public RealmConfiguration a() {
        return this.c;
    }

    private int c() {
        int i = 0;
        for (f fVar : this.a.values()) {
            i += fVar.e();
        }
        return i;
    }

    private int d() {
        int i = 0;
        for (f fVar : this.a.values()) {
            if (fVar instanceof g) {
                i += fVar.e();
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        if (!this.e.getAndSet(true)) {
            f.add(this);
        }
    }
}
