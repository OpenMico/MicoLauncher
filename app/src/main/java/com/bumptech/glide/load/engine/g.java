package com.bumptech.glide.load.engine;

import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.DataFetcherGenerator;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.GlideTrace;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeJob.java */
/* loaded from: classes.dex */
public class g<R> implements DataFetcherGenerator.FetcherReadyCallback, FactoryPools.Poolable, Comparable<g<?>>, Runnable {
    private DataSource A;
    private DataFetcher<?> B;
    private volatile DataFetcherGenerator C;
    private volatile boolean D;
    private volatile boolean E;
    private boolean F;
    private final d d;
    private final Pools.Pool<g<?>> e;
    private GlideContext h;
    private Key i;
    private Priority j;
    private j k;
    private int l;
    private int m;
    private DiskCacheStrategy n;
    private Options o;
    private a<R> p;
    private int q;
    private EnumC0041g r;
    private f s;
    private long t;
    private boolean u;
    private Object v;
    private Thread w;
    private Key x;
    private Key y;
    private Object z;
    private final f<R> a = new f<>();
    private final List<Throwable> b = new ArrayList();
    private final StateVerifier c = StateVerifier.newInstance();
    private final c<?> f = new c<>();
    private final e g = new e();

    /* compiled from: DecodeJob.java */
    /* loaded from: classes.dex */
    public interface a<R> {
        void a(GlideException glideException);

        void a(Resource<R> resource, DataSource dataSource, boolean z);

        void a(g<?> gVar);
    }

    /* compiled from: DecodeJob.java */
    /* loaded from: classes.dex */
    public interface d {
        DiskCache a();
    }

    /* compiled from: DecodeJob.java */
    /* loaded from: classes.dex */
    public enum f {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    /* compiled from: DecodeJob.java */
    /* renamed from: com.bumptech.glide.load.engine.g$g */
    /* loaded from: classes.dex */
    public enum EnumC0041g {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    public g(d dVar, Pools.Pool<g<?>> pool) {
        this.d = dVar;
        this.e = pool;
    }

    public g<R> a(GlideContext glideContext, Object obj, j jVar, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, boolean z3, Options options, a<R> aVar, int i3) {
        this.a.a(glideContext, obj, key, i, i2, diskCacheStrategy, cls, cls2, priority, options, map, z, z2, this.d);
        this.h = glideContext;
        this.i = key;
        this.j = priority;
        this.k = jVar;
        this.l = i;
        this.m = i2;
        this.n = diskCacheStrategy;
        this.u = z3;
        this.o = options;
        this.p = aVar;
        this.q = i3;
        this.s = f.INITIALIZE;
        this.v = obj;
        return this;
    }

    public boolean a() {
        EnumC0041g a2 = a(EnumC0041g.INITIALIZE);
        return a2 == EnumC0041g.RESOURCE_CACHE || a2 == EnumC0041g.DATA_CACHE;
    }

    public void a(boolean z) {
        if (this.g.a(z)) {
            e();
        }
    }

    private void c() {
        if (this.g.a()) {
            e();
        }
    }

    private void d() {
        if (this.g.b()) {
            e();
        }
    }

    private void e() {
        this.g.c();
        this.f.b();
        this.a.a();
        this.D = false;
        this.h = null;
        this.i = null;
        this.o = null;
        this.j = null;
        this.k = null;
        this.p = null;
        this.r = null;
        this.C = null;
        this.w = null;
        this.x = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.t = 0L;
        this.E = false;
        this.v = null;
        this.b.clear();
        this.e.release(this);
    }

    /* renamed from: a */
    public int compareTo(@NonNull g<?> gVar) {
        int f2 = f() - gVar.f();
        return f2 == 0 ? this.q - gVar.q : f2;
    }

    private int f() {
        return this.j.ordinal();
    }

    public void b() {
        this.E = true;
        DataFetcherGenerator dataFetcherGenerator = this.C;
        if (dataFetcherGenerator != null) {
            dataFetcherGenerator.b();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        DataFetcher<?> dataFetcher;
        try {
            GlideTrace.beginSectionFormat("DecodeJob#run(reason=%s, model=%s)", this.s, this.v);
            dataFetcher = this.B;
            try {
                if (this.E) {
                    j();
                    return;
                }
                g();
                if (dataFetcher != null) {
                    dataFetcher.cleanup();
                }
                GlideTrace.endSection();
            } catch (b e2) {
                throw e2;
            }
        } finally {
            if (dataFetcher != null) {
                dataFetcher.cleanup();
            }
            GlideTrace.endSection();
        }
    }

    private void g() {
        switch (this.s) {
            case INITIALIZE:
                this.r = a(EnumC0041g.INITIALIZE);
                this.C = h();
                i();
                return;
            case SWITCH_TO_SOURCE_SERVICE:
                i();
                return;
            case DECODE_DATA:
                l();
                return;
            default:
                throw new IllegalStateException("Unrecognized run reason: " + this.s);
        }
    }

    private DataFetcherGenerator h() {
        switch (this.r) {
            case RESOURCE_CACHE:
                return new o(this.a, this);
            case DATA_CACHE:
                return new c(this.a, this);
            case SOURCE:
                return new r(this.a, this);
            case FINISHED:
                return null;
            default:
                throw new IllegalStateException("Unrecognized stage: " + this.r);
        }
    }

    private void i() {
        this.w = Thread.currentThread();
        this.t = LogTime.getLogTime();
        boolean z = false;
        while (!this.E && this.C != null && !(z = this.C.a())) {
            this.r = a(this.r);
            this.C = h();
            if (this.r == EnumC0041g.SOURCE) {
                reschedule();
                return;
            }
        }
        if ((this.r == EnumC0041g.FINISHED || this.E) && !z) {
            j();
        }
    }

    private void j() {
        k();
        this.p.a(new GlideException("Failed to load resource", new ArrayList(this.b)));
        d();
    }

    private void a(Resource<R> resource, DataSource dataSource, boolean z) {
        k();
        this.p.a(resource, dataSource, z);
    }

    private void k() {
        Throwable th;
        this.c.throwIfRecycled();
        if (this.D) {
            if (this.b.isEmpty()) {
                th = null;
            } else {
                List<Throwable> list = this.b;
                th = list.get(list.size() - 1);
            }
            throw new IllegalStateException("Already notified", th);
        }
        this.D = true;
    }

    private EnumC0041g a(EnumC0041g gVar) {
        switch (gVar) {
            case RESOURCE_CACHE:
                if (this.n.decodeCachedData()) {
                    return EnumC0041g.DATA_CACHE;
                }
                return a(EnumC0041g.DATA_CACHE);
            case DATA_CACHE:
                return this.u ? EnumC0041g.FINISHED : EnumC0041g.SOURCE;
            case SOURCE:
            case FINISHED:
                return EnumC0041g.FINISHED;
            case INITIALIZE:
                if (this.n.decodeCachedResource()) {
                    return EnumC0041g.RESOURCE_CACHE;
                }
                return a(EnumC0041g.RESOURCE_CACHE);
            default:
                throw new IllegalArgumentException("Unrecognized stage: " + gVar);
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void reschedule() {
        this.s = f.SWITCH_TO_SOURCE_SERVICE;
        this.p.a((g<?>) this);
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherReady(Key key, Object obj, DataFetcher<?> dataFetcher, DataSource dataSource, Key key2) {
        this.x = key;
        this.z = obj;
        this.B = dataFetcher;
        this.A = dataSource;
        this.y = key2;
        boolean z = false;
        if (key != this.a.o().get(0)) {
            z = true;
        }
        this.F = z;
        if (Thread.currentThread() != this.w) {
            this.s = f.DECODE_DATA;
            this.p.a((g<?>) this);
            return;
        }
        GlideTrace.beginSection("DecodeJob.decodeFromRetrievedData");
        try {
            l();
        } finally {
            GlideTrace.endSection();
        }
    }

    @Override // com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback
    public void onDataFetcherFailed(Key key, Exception exc, DataFetcher<?> dataFetcher, DataSource dataSource) {
        dataFetcher.cleanup();
        GlideException glideException = new GlideException("Fetching data failed", exc);
        glideException.a(key, dataSource, dataFetcher.getDataClass());
        this.b.add(glideException);
        if (Thread.currentThread() != this.w) {
            this.s = f.SWITCH_TO_SOURCE_SERVICE;
            this.p.a((g<?>) this);
            return;
        }
        i();
    }

    private void l() {
        if (Log.isLoggable("DecodeJob", 2)) {
            long j = this.t;
            a("Retrieved data", j, "data: " + this.z + ", cache key: " + this.x + ", fetcher: " + this.B);
        }
        Resource<R> resource = null;
        try {
            resource = a(this.B, (DataFetcher<?>) this.z, this.A);
        } catch (GlideException e2) {
            e2.a(this.y, this.A);
            this.b.add(e2);
        }
        if (resource != null) {
            b(resource, this.A, this.F);
        } else {
            i();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b(Resource<R> resource, DataSource dataSource, boolean z) {
        GlideTrace.beginSection("DecodeJob.notifyEncodeAndRelease");
        try {
            if (resource instanceof Initializable) {
                ((Initializable) resource).initialize();
            }
            n nVar = 0;
            if (this.f.a()) {
                resource = n.a(resource);
                nVar = resource;
            }
            a(resource, dataSource, z);
            this.r = EnumC0041g.ENCODE;
            if (this.f.a()) {
                this.f.a(this.d, this.o);
            }
            if (nVar != 0) {
                nVar.a();
            }
            c();
        } finally {
            GlideTrace.endSection();
        }
    }

    private <Data> Resource<R> a(DataFetcher<?> dataFetcher, Data data, DataSource dataSource) throws GlideException {
        if (data == null) {
            return null;
        }
        try {
            long logTime = LogTime.getLogTime();
            Resource<R> a2 = a((g<R>) data, dataSource);
            if (Log.isLoggable("DecodeJob", 2)) {
                a("Decoded result " + a2, logTime);
            }
            return a2;
        } finally {
            dataFetcher.cleanup();
        }
    }

    private <Data> Resource<R> a(Data data, DataSource dataSource) throws GlideException {
        return a((g<R>) data, dataSource, (LoadPath<g<R>, ResourceType, R>) ((LoadPath<Data, ?, R>) this.a.b((Class) data.getClass())));
    }

    @NonNull
    private Options a(DataSource dataSource) {
        Options options = this.o;
        if (Build.VERSION.SDK_INT < 26) {
            return options;
        }
        boolean z = dataSource == DataSource.RESOURCE_DISK_CACHE || this.a.m();
        Boolean bool = (Boolean) options.get(Downsampler.ALLOW_HARDWARE_CONFIG);
        if (bool != null && (!bool.booleanValue() || z)) {
            return options;
        }
        Options options2 = new Options();
        options2.putAll(this.o);
        options2.set(Downsampler.ALLOW_HARDWARE_CONFIG, Boolean.valueOf(z));
        return options2;
    }

    private <Data, ResourceType> Resource<R> a(Data data, DataSource dataSource, LoadPath<Data, ResourceType, R> loadPath) throws GlideException {
        Options a2 = a(dataSource);
        DataRewinder<Data> rewinder = this.h.getRegistry().getRewinder(data);
        try {
            return loadPath.load(rewinder, a2, this.l, this.m, new b(dataSource));
        } finally {
            rewinder.cleanup();
        }
    }

    private void a(String str, long j) {
        a(str, j, (String) null);
    }

    private void a(String str, long j, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" in ");
        sb.append(LogTime.getElapsedMillis(j));
        sb.append(", load key: ");
        sb.append(this.k);
        if (str2 != null) {
            str3 = ", " + str2;
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        Log.v("DecodeJob", sb.toString());
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    @NonNull
    public StateVerifier getVerifier() {
        return this.c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    <Z> Resource<Z> a(DataSource dataSource, @NonNull Resource<Z> resource) {
        Transformation<Z> transformation;
        Resource<Z> resource2;
        EncodeStrategy encodeStrategy;
        ResourceEncoder resourceEncoder;
        Key key;
        Class<?> cls = resource.get().getClass();
        if (dataSource != DataSource.RESOURCE_DISK_CACHE) {
            Transformation<Z> c2 = this.a.c(cls);
            resource2 = c2.transform(this.h, resource, this.l, this.m);
            transformation = c2;
        } else {
            resource2 = resource;
            transformation = null;
        }
        if (!resource.equals(resource2)) {
            resource.recycle();
        }
        if (this.a.a((Resource<?>) resource2)) {
            ResourceEncoder b2 = this.a.b((Resource) resource2);
            encodeStrategy = b2.getEncodeStrategy(this.o);
            resourceEncoder = b2;
        } else {
            encodeStrategy = EncodeStrategy.NONE;
            resourceEncoder = null;
        }
        if (!this.n.isResourceCacheable(!this.a.a(this.x), dataSource, encodeStrategy)) {
            return resource2;
        }
        if (resourceEncoder != null) {
            switch (encodeStrategy) {
                case SOURCE:
                    key = new d(this.x, this.i);
                    break;
                case TRANSFORMED:
                    key = new p(this.a.i(), this.x, this.i, this.l, this.m, transformation, cls, this.o);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown strategy: " + encodeStrategy);
            }
            n a2 = n.a(resource2);
            this.f.a(key, resourceEncoder, a2);
            return a2;
        }
        throw new Registry.NoResultEncoderAvailableException(resource2.get().getClass());
    }

    /* compiled from: DecodeJob.java */
    /* loaded from: classes.dex */
    public final class b<Z> implements DecodePath.a<Z> {
        private final DataSource b;

        b(DataSource dataSource) {
            g.this = r1;
            this.b = dataSource;
        }

        @Override // com.bumptech.glide.load.engine.DecodePath.a
        @NonNull
        public Resource<Z> a(@NonNull Resource<Z> resource) {
            return g.this.a(this.b, resource);
        }
    }

    /* compiled from: DecodeJob.java */
    /* loaded from: classes.dex */
    public static class e {
        private boolean a;
        private boolean b;
        private boolean c;

        e() {
        }

        synchronized boolean a(boolean z) {
            this.a = true;
            return b(z);
        }

        synchronized boolean a() {
            this.b = true;
            return b(false);
        }

        synchronized boolean b() {
            this.c = true;
            return b(false);
        }

        synchronized void c() {
            this.b = false;
            this.a = false;
            this.c = false;
        }

        private boolean b(boolean z) {
            return (this.c || z || this.b) && this.a;
        }
    }

    /* compiled from: DecodeJob.java */
    /* loaded from: classes.dex */
    public static class c<Z> {
        private Key a;
        private ResourceEncoder<Z> b;
        private n<Z> c;

        c() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        <X> void a(Key key, ResourceEncoder<X> resourceEncoder, n<X> nVar) {
            this.a = key;
            this.b = resourceEncoder;
            this.c = nVar;
        }

        void a(d dVar, Options options) {
            GlideTrace.beginSection("DecodeJob.encode");
            try {
                dVar.a().put(this.a, new e(this.b, this.c, options));
            } finally {
                this.c.a();
                GlideTrace.endSection();
            }
        }

        boolean a() {
            return this.c != null;
        }

        void b() {
            this.a = null;
            this.b = null;
            this.c = null;
        }
    }
}
