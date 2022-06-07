package io.realm;

import android.content.Context;
import io.realm.Realm;
import io.realm.annotations.RealmModule;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Util;
import io.realm.internal.modules.CompositeMediator;
import io.realm.internal.modules.FilterableMediator;
import io.realm.rx.RealmObservableFactory;
import io.realm.rx.RxObservableFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class RealmConfiguration {
    protected static final RealmProxyMediator DEFAULT_MODULE_MEDIATOR;
    public static final String DEFAULT_REALM_NAME = "default.realm";
    public static final int KEY_LENGTH = 64;
    private static final Object a = Realm.getDefaultModule();
    private static Boolean b;
    private final File c;
    private final String d;
    private final String e;
    private final String f;
    private final byte[] g;
    private final long h;
    private final RealmMigration i;
    private final boolean j;
    private final OsRealmConfig.Durability k;
    private final RealmProxyMediator l;
    private final RxObservableFactory m;
    private final Realm.Transaction n;
    private final boolean o;
    private final CompactOnLaunchCallback p;
    private final long q;
    private final boolean r;

    public boolean f() {
        return false;
    }

    static {
        Object obj = a;
        if (obj != null) {
            RealmProxyMediator a2 = a(obj.getClass().getCanonicalName());
            if (a2.transformerApplied()) {
                DEFAULT_MODULE_MEDIATOR = a2;
                return;
            }
            throw new ExceptionInInitializerError("RealmTransformer doesn't seem to be applied. Please update the project configuration to use the Realm Gradle plugin. See https://realm.io/news/android-installation-change/");
        }
        DEFAULT_MODULE_MEDIATOR = null;
    }

    protected RealmConfiguration(@Nullable File file, @Nullable String str, String str2, @Nullable String str3, @Nullable byte[] bArr, long j, @Nullable RealmMigration realmMigration, boolean z, OsRealmConfig.Durability durability, RealmProxyMediator realmProxyMediator, @Nullable RxObservableFactory rxObservableFactory, @Nullable Realm.Transaction transaction, boolean z2, @Nullable CompactOnLaunchCallback compactOnLaunchCallback, boolean z3, long j2) {
        this.c = file;
        this.d = str;
        this.e = str2;
        this.f = str3;
        this.g = bArr;
        this.h = j;
        this.i = realmMigration;
        this.j = z;
        this.k = durability;
        this.l = realmProxyMediator;
        this.m = rxObservableFactory;
        this.n = transaction;
        this.o = z2;
        this.p = compactOnLaunchCallback;
        this.r = z3;
        this.q = j2;
    }

    public File getRealmDirectory() {
        return this.c;
    }

    public String getRealmFileName() {
        return this.d;
    }

    public byte[] getEncryptionKey() {
        byte[] bArr = this.g;
        if (bArr == null) {
            return null;
        }
        return Arrays.copyOf(bArr, bArr.length);
    }

    public long getSchemaVersion() {
        return this.h;
    }

    public RealmMigration getMigration() {
        return this.i;
    }

    public boolean shouldDeleteRealmIfMigrationNeeded() {
        return this.j;
    }

    public OsRealmConfig.Durability getDurability() {
        return this.k;
    }

    public RealmProxyMediator getSchemaMediator() {
        return this.l;
    }

    public Realm.Transaction a() {
        return this.n;
    }

    public boolean b() {
        return !Util.isEmptyString(this.f);
    }

    public String c() {
        return this.f;
    }

    public CompactOnLaunchCallback getCompactOnLaunchCallback() {
        return this.p;
    }

    public Set<Class<? extends RealmModel>> getRealmObjectClasses() {
        return this.l.getModelClasses();
    }

    public String getPath() {
        return this.e;
    }

    public boolean d() {
        return new File(this.e).exists();
    }

    public RxObservableFactory getRxFactory() {
        RxObservableFactory rxObservableFactory = this.m;
        if (rxObservableFactory != null) {
            return rxObservableFactory;
        }
        throw new UnsupportedOperationException("RxJava seems to be missing from the classpath. Remember to add it as a compile dependency. See https://realm.io/docs/java/latest/#rxjava for more details.");
    }

    public boolean isReadOnly() {
        return this.o;
    }

    public boolean isRecoveryConfiguration() {
        return this.r;
    }

    public long getMaxNumberOfActiveVersions() {
        return this.q;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RealmConfiguration realmConfiguration = (RealmConfiguration) obj;
        if (this.h != realmConfiguration.h || this.j != realmConfiguration.j || this.o != realmConfiguration.o || this.r != realmConfiguration.r) {
            return false;
        }
        File file = this.c;
        if (file == null ? realmConfiguration.c != null : !file.equals(realmConfiguration.c)) {
            return false;
        }
        String str = this.d;
        if (str == null ? realmConfiguration.d != null : !str.equals(realmConfiguration.d)) {
            return false;
        }
        if (!this.e.equals(realmConfiguration.e)) {
            return false;
        }
        String str2 = this.f;
        if (str2 == null ? realmConfiguration.f != null : !str2.equals(realmConfiguration.f)) {
            return false;
        }
        if (!Arrays.equals(this.g, realmConfiguration.g)) {
            return false;
        }
        RealmMigration realmMigration = this.i;
        if (realmMigration == null ? realmConfiguration.i != null : !realmMigration.equals(realmConfiguration.i)) {
            return false;
        }
        if (this.k != realmConfiguration.k || !this.l.equals(realmConfiguration.l)) {
            return false;
        }
        RxObservableFactory rxObservableFactory = this.m;
        if (rxObservableFactory == null ? realmConfiguration.m != null : !rxObservableFactory.equals(realmConfiguration.m)) {
            return false;
        }
        Realm.Transaction transaction = this.n;
        if (transaction == null ? realmConfiguration.n != null : !transaction.equals(realmConfiguration.n)) {
            return false;
        }
        CompactOnLaunchCallback compactOnLaunchCallback = this.p;
        if (compactOnLaunchCallback == null ? realmConfiguration.p == null : compactOnLaunchCallback.equals(realmConfiguration.p)) {
            return this.q == realmConfiguration.q;
        }
        return false;
    }

    public int hashCode() {
        File file = this.c;
        int i = 0;
        int hashCode = (file != null ? file.hashCode() : 0) * 31;
        String str = this.d;
        int hashCode2 = (((hashCode + (str != null ? str.hashCode() : 0)) * 31) + this.e.hashCode()) * 31;
        String str2 = this.f;
        int hashCode3 = str2 != null ? str2.hashCode() : 0;
        long j = this.h;
        int hashCode4 = (((((hashCode2 + hashCode3) * 31) + Arrays.hashCode(this.g)) * 31) + ((int) (j ^ (j >>> 32)))) * 31;
        RealmMigration realmMigration = this.i;
        int hashCode5 = (((((((hashCode4 + (realmMigration != null ? realmMigration.hashCode() : 0)) * 31) + (this.j ? 1 : 0)) * 31) + this.k.hashCode()) * 31) + this.l.hashCode()) * 31;
        RxObservableFactory rxObservableFactory = this.m;
        int hashCode6 = (hashCode5 + (rxObservableFactory != null ? rxObservableFactory.hashCode() : 0)) * 31;
        Realm.Transaction transaction = this.n;
        int hashCode7 = (((hashCode6 + (transaction != null ? transaction.hashCode() : 0)) * 31) + (this.o ? 1 : 0)) * 31;
        CompactOnLaunchCallback compactOnLaunchCallback = this.p;
        if (compactOnLaunchCallback != null) {
            i = compactOnLaunchCallback.hashCode();
        }
        long j2 = this.q;
        return ((((hashCode7 + i) * 31) + (this.r ? 1 : 0)) * 31) + ((int) (j2 ^ (j2 >>> 32)));
    }

    protected static RealmProxyMediator createSchemaMediator(Set<Object> set, Set<Class<? extends RealmModel>> set2) {
        if (set2.size() > 0) {
            return new FilterableMediator(DEFAULT_MODULE_MEDIATOR, set2);
        }
        if (set.size() == 1) {
            return a(set.iterator().next().getClass().getCanonicalName());
        }
        RealmProxyMediator[] realmProxyMediatorArr = new RealmProxyMediator[set.size()];
        int i = 0;
        for (Object obj : set) {
            realmProxyMediatorArr[i] = a(obj.getClass().getCanonicalName());
            i++;
        }
        return new CompositeMediator(realmProxyMediatorArr);
    }

    private static RealmProxyMediator a(String str) {
        String[] split = str.split("\\.");
        String format = String.format(Locale.US, "io.realm.%s%s", split[split.length - 1], "Mediator");
        try {
            Constructor<?> constructor = Class.forName(format).getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return (RealmProxyMediator) constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            throw new RealmException("Could not find " + format, e);
        } catch (IllegalAccessException e2) {
            throw new RealmException("Could not create an instance of " + format, e2);
        } catch (InstantiationException e3) {
            throw new RealmException("Could not create an instance of " + format, e3);
        } catch (InvocationTargetException e4) {
            throw new RealmException("Could not create an instance of " + format, e4);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("realmDirectory: ");
        File file = this.c;
        sb.append(file != null ? file.toString() : "");
        sb.append("\n");
        sb.append("realmFileName : ");
        sb.append(this.d);
        sb.append("\n");
        sb.append("canonicalPath: ");
        sb.append(this.e);
        sb.append("\n");
        sb.append("key: ");
        sb.append("[length: ");
        sb.append(this.g == null ? 0 : 64);
        sb.append("]");
        sb.append("\n");
        sb.append("schemaVersion: ");
        sb.append(Long.toString(this.h));
        sb.append("\n");
        sb.append("migration: ");
        sb.append(this.i);
        sb.append("\n");
        sb.append("deleteRealmIfMigrationNeeded: ");
        sb.append(this.j);
        sb.append("\n");
        sb.append("durability: ");
        sb.append(this.k);
        sb.append("\n");
        sb.append("schemaMediator: ");
        sb.append(this.l);
        sb.append("\n");
        sb.append("readOnly: ");
        sb.append(this.o);
        sb.append("\n");
        sb.append("compactOnLaunch: ");
        sb.append(this.p);
        sb.append("\n");
        sb.append("maxNumberOfActiveVersions: ");
        sb.append(this.q);
        return sb.toString();
    }

    static synchronized boolean e() {
        boolean booleanValue;
        synchronized (RealmConfiguration.class) {
            if (b == null) {
                try {
                    Class.forName("io.reactivex.Flowable");
                    b = true;
                } catch (ClassNotFoundException unused) {
                    b = false;
                }
            }
            booleanValue = b.booleanValue();
        }
        return booleanValue;
    }

    protected static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            RealmFileException.Kind kind = RealmFileException.Kind.ACCESS_ERROR;
            throw new RealmFileException(kind, "Could not resolve the canonical path to the Realm file: " + file.getAbsolutePath(), e);
        }
    }

    /* loaded from: classes5.dex */
    public static class Builder {
        private File a;
        private String b;
        private String c;
        private byte[] d;
        private long e;
        private RealmMigration f;
        private boolean g;
        private OsRealmConfig.Durability h;
        private HashSet<Object> i;
        private HashSet<Class<? extends RealmModel>> j;
        private RxObservableFactory k;
        private Realm.Transaction l;
        private boolean m;
        private CompactOnLaunchCallback n;
        private long o;

        public Builder() {
            this(BaseRealm.a);
        }

        public Builder(Context context) {
            this.i = new HashSet<>();
            this.j = new HashSet<>();
            this.o = Long.MAX_VALUE;
            if (context != null) {
                RealmCore.loadLibrary(context);
                a(context);
                return;
            }
            throw new IllegalStateException("Call `Realm.init(Context)` before creating a RealmConfiguration");
        }

        private void a(Context context) {
            this.a = context.getFilesDir();
            this.b = "default.realm";
            this.d = null;
            this.e = 0L;
            this.f = null;
            this.g = false;
            this.h = OsRealmConfig.Durability.FULL;
            this.m = false;
            this.n = null;
            if (RealmConfiguration.a != null) {
                this.i.add(RealmConfiguration.a);
            }
        }

        public Builder name(String str) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("A non-empty filename must be provided");
            }
            this.b = str;
            return this;
        }

        public Builder directory(File file) {
            if (file == null) {
                throw new IllegalArgumentException("Non-null 'dir' required.");
            } else if (file.isFile()) {
                throw new IllegalArgumentException("'dir' is a file, not a directory: " + file.getAbsolutePath() + ".");
            } else if (!file.exists() && !file.mkdirs()) {
                throw new IllegalArgumentException("Could not create the specified directory: " + file.getAbsolutePath() + ".");
            } else if (file.canWrite()) {
                this.a = file;
                return this;
            } else {
                throw new IllegalArgumentException("Realm directory is not writable: " + file.getAbsolutePath() + ".");
            }
        }

        public Builder encryptionKey(byte[] bArr) {
            if (bArr == null) {
                throw new IllegalArgumentException("A non-null key must be provided");
            } else if (bArr.length == 64) {
                this.d = Arrays.copyOf(bArr, bArr.length);
                return this;
            } else {
                throw new IllegalArgumentException(String.format(Locale.US, "The provided key must be %s bytes. Yours was: %s", 64, Integer.valueOf(bArr.length)));
            }
        }

        public Builder schemaVersion(long j) {
            if (j >= 0) {
                this.e = j;
                return this;
            }
            throw new IllegalArgumentException("Realm schema version numbers must be 0 (zero) or higher. Yours was: " + j);
        }

        public Builder migration(RealmMigration realmMigration) {
            if (realmMigration != null) {
                this.f = realmMigration;
                return this;
            }
            throw new IllegalArgumentException("A non-null migration must be provided");
        }

        public Builder deleteRealmIfMigrationNeeded() {
            String str = this.c;
            if (str == null || str.length() == 0) {
                this.g = true;
                return this;
            }
            throw new IllegalStateException("Realm cannot clear its schema when previously configured to use an asset file by calling assetFile().");
        }

        public Builder inMemory() {
            if (Util.isEmptyString(this.c)) {
                this.h = OsRealmConfig.Durability.MEM_ONLY;
                return this;
            }
            throw new RealmException("Realm can not use in-memory configuration if asset file is present.");
        }

        public Builder modules(Object obj, Object... objArr) {
            this.i.clear();
            addModule(obj);
            if (objArr != null) {
                for (Object obj2 : objArr) {
                    addModule(obj2);
                }
            }
            return this;
        }

        public final Builder addModule(Object obj) {
            if (obj != null) {
                a(obj);
                this.i.add(obj);
            }
            return this;
        }

        public Builder rxFactory(RxObservableFactory rxObservableFactory) {
            this.k = rxObservableFactory;
            return this;
        }

        public Builder initialData(Realm.Transaction transaction) {
            this.l = transaction;
            return this;
        }

        public Builder assetFile(String str) {
            if (Util.isEmptyString(str)) {
                throw new IllegalArgumentException("A non-empty asset file path must be provided");
            } else if (this.h == OsRealmConfig.Durability.MEM_ONLY) {
                throw new RealmException("Realm can not use in-memory configuration if asset file is present.");
            } else if (!this.g) {
                this.c = str;
                return this;
            } else {
                throw new IllegalStateException("Realm cannot use an asset file when previously configured to clear its schema in migration by calling deleteRealmIfMigrationNeeded().");
            }
        }

        public Builder readOnly() {
            this.m = true;
            return this;
        }

        public Builder compactOnLaunch() {
            return compactOnLaunch(new DefaultCompactOnLaunchCallback());
        }

        public Builder compactOnLaunch(CompactOnLaunchCallback compactOnLaunchCallback) {
            if (compactOnLaunchCallback != null) {
                this.n = compactOnLaunchCallback;
                return this;
            }
            throw new IllegalArgumentException("A non-null compactOnLaunch must be provided");
        }

        public Builder maxNumberOfActiveVersions(long j) {
            if (j >= 1) {
                this.o = j;
                return this;
            }
            throw new IllegalArgumentException("Only positive numbers above 0 are allowed. Yours was: " + j);
        }

        public RealmConfiguration build() {
            if (this.m) {
                if (this.l != null) {
                    throw new IllegalStateException("This Realm is marked as read-only. Read-only Realms cannot use initialData(Realm.Transaction).");
                } else if (this.c == null) {
                    throw new IllegalStateException("Only Realms provided using 'assetFile(path)' can be marked read-only. No such Realm was provided.");
                } else if (this.g) {
                    throw new IllegalStateException("'deleteRealmIfMigrationNeeded()' and read-only Realms cannot be combined");
                } else if (this.n != null) {
                    throw new IllegalStateException("'compactOnLaunch()' and read-only Realms cannot be combined");
                }
            }
            if (this.k == null && RealmConfiguration.e()) {
                this.k = new RealmObservableFactory(true);
            }
            File file = this.a;
            String str = this.b;
            return new RealmConfiguration(file, str, RealmConfiguration.getCanonicalPath(new File(file, str)), this.c, this.d, this.e, this.f, this.g, this.h, RealmConfiguration.createSchemaMediator(this.i, this.j), this.k, this.l, this.m, this.n, false, this.o);
        }

        private void a(Object obj) {
            if (!obj.getClass().isAnnotationPresent(RealmModule.class)) {
                throw new IllegalArgumentException(obj.getClass().getCanonicalName() + " is not a RealmModule. Add @RealmModule to the class definition.");
            }
        }
    }
}
