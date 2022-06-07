package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.media.ResourceBusyException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.drm.DefaultDrmSession;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@RequiresApi(18)
/* loaded from: classes2.dex */
public class DefaultDrmSessionManager implements DrmSessionManager {
    public static final long DEFAULT_SESSION_KEEPALIVE_MS = 300000;
    public static final int INITIAL_DRM_REQUEST_RETRY_COUNT = 3;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    @Nullable
    volatile b a;
    private final UUID b;
    private final ExoMediaDrm.Provider c;
    private final MediaDrmCallback d;
    private final HashMap<String, String> e;
    private final boolean f;
    private final int[] g;
    private final boolean h;
    private final d i;
    private final LoadErrorHandlingPolicy j;
    private final e k;
    private final long l;
    private final List<DefaultDrmSession> m;
    private final Set<c> n;
    private final Set<DefaultDrmSession> o;
    private int p;
    @Nullable
    private ExoMediaDrm q;
    @Nullable
    private DefaultDrmSession r;
    @Nullable
    private DefaultDrmSession s;
    private Looper t;
    private Handler u;
    private int v;
    @Nullable
    private byte[] w;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Mode {
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private boolean d;
        private boolean f;
        private final HashMap<String, String> a = new HashMap<>();
        private UUID b = C.WIDEVINE_UUID;
        private ExoMediaDrm.Provider c = FrameworkMediaDrm.DEFAULT_PROVIDER;
        private LoadErrorHandlingPolicy g = new DefaultLoadErrorHandlingPolicy();
        private int[] e = new int[0];
        private long h = 300000;

        public Builder setKeyRequestParameters(@Nullable Map<String, String> map) {
            this.a.clear();
            if (map != null) {
                this.a.putAll(map);
            }
            return this;
        }

        public Builder setUuidAndExoMediaDrmProvider(UUID uuid, ExoMediaDrm.Provider provider) {
            this.b = (UUID) Assertions.checkNotNull(uuid);
            this.c = (ExoMediaDrm.Provider) Assertions.checkNotNull(provider);
            return this;
        }

        public Builder setMultiSession(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setUseDrmSessionsForClearContent(int... iArr) {
            for (int i : iArr) {
                boolean z = true;
                if (!(i == 2 || i == 1)) {
                    z = false;
                }
                Assertions.checkArgument(z);
            }
            this.e = (int[]) iArr.clone();
            return this;
        }

        public Builder setPlayClearSamplesWithoutKeys(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.g = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy);
            return this;
        }

        public Builder setSessionKeepaliveMs(long j) {
            Assertions.checkArgument(j > 0 || j == C.TIME_UNSET);
            this.h = j;
            return this;
        }

        public DefaultDrmSessionManager build(MediaDrmCallback mediaDrmCallback) {
            return new DefaultDrmSessionManager(this.b, this.c, mediaDrmCallback, this.a, this.d, this.e, this.f, this.g, this.h);
        }
    }

    /* loaded from: classes2.dex */
    public static final class MissingSchemeDataException extends Exception {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private MissingSchemeDataException(java.util.UUID r3) {
            /*
                r2 = this;
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r0 = java.lang.String.valueOf(r3)
                int r0 = r0.length()
                int r0 = r0 + 29
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>(r0)
                java.lang.String r0 = "Media does not support uuid: "
                r1.append(r0)
                r1.append(r3)
                java.lang.String r3 = r1.toString()
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.DefaultDrmSessionManager.MissingSchemeDataException.<init>(java.util.UUID):void");
        }
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> hashMap) {
        this(uuid, exoMediaDrm, mediaDrmCallback, hashMap == null ? new HashMap<>() : hashMap, false, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> hashMap, boolean z) {
        this(uuid, exoMediaDrm, mediaDrmCallback, hashMap == null ? new HashMap<>() : hashMap, z, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> hashMap, boolean z, int i) {
        this(uuid, new ExoMediaDrm.AppManagedProvider(exoMediaDrm), mediaDrmCallback, hashMap == null ? new HashMap<>() : hashMap, z, new int[0], false, new DefaultLoadErrorHandlingPolicy(i), 300000L);
    }

    private DefaultDrmSessionManager(UUID uuid, ExoMediaDrm.Provider provider, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z, int[] iArr, boolean z2, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j) {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(!C.COMMON_PSSH_UUID.equals(uuid), "Use C.CLEARKEY_UUID instead");
        this.b = uuid;
        this.c = provider;
        this.d = mediaDrmCallback;
        this.e = hashMap;
        this.f = z;
        this.g = iArr;
        this.h = z2;
        this.j = loadErrorHandlingPolicy;
        this.i = new d(this);
        this.k = new e();
        this.v = 0;
        this.m = new ArrayList();
        this.n = Sets.newIdentityHashSet();
        this.o = Sets.newIdentityHashSet();
        this.l = j;
    }

    public void setMode(int i, @Nullable byte[] bArr) {
        Assertions.checkState(this.m.isEmpty());
        if (i == 1 || i == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.v = i;
        this.w = bArr;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public final void prepare() {
        int i = this.p;
        this.p = i + 1;
        if (i == 0) {
            if (this.q == null) {
                this.q = this.c.acquireExoMediaDrm(this.b);
                this.q.setOnEventListener(new a());
            } else if (this.l != C.TIME_UNSET) {
                for (int i2 = 0; i2 < this.m.size(); i2++) {
                    this.m.get(i2).acquire(null);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public final void release() {
        int i = this.p - 1;
        this.p = i;
        if (i == 0) {
            if (this.l != C.TIME_UNSET) {
                ArrayList arrayList = new ArrayList(this.m);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ((DefaultDrmSession) arrayList.get(i2)).release(null);
                }
            }
            b();
            c();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public DrmSessionManager.DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        Assertions.checkState(this.p > 0);
        a(looper);
        c cVar = new c(eventDispatcher);
        cVar.a(format);
        return cVar;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    @Nullable
    public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        Assertions.checkState(this.p > 0);
        a(looper);
        return a(looper, eventDispatcher, format, true);
    }

    @Nullable
    public DrmSession a(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format, boolean z) {
        List<DrmInitData.SchemeData> list;
        b(looper);
        if (format.drmInitData == null) {
            return a(MimeTypes.getTrackType(format.sampleMimeType), z);
        }
        DefaultDrmSession defaultDrmSession = null;
        if (this.w == null) {
            list = a((DrmInitData) Assertions.checkNotNull(format.drmInitData), this.b, false);
            if (list.isEmpty()) {
                MissingSchemeDataException missingSchemeDataException = new MissingSchemeDataException(this.b);
                Log.e("DefaultDrmSessionMgr", "DRM error", missingSchemeDataException);
                if (eventDispatcher != null) {
                    eventDispatcher.drmSessionManagerError(missingSchemeDataException);
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(missingSchemeDataException, PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR));
            }
        } else {
            list = null;
        }
        if (this.f) {
            Iterator<DefaultDrmSession> it = this.m.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DefaultDrmSession next = it.next();
                if (Util.areEqual(next.a, list)) {
                    defaultDrmSession = next;
                    break;
                }
            }
        } else {
            defaultDrmSession = this.s;
        }
        if (defaultDrmSession == null) {
            defaultDrmSession = a(list, false, eventDispatcher, z);
            if (!this.f) {
                this.s = defaultDrmSession;
            }
            this.m.add(defaultDrmSession);
        } else {
            defaultDrmSession.acquire(eventDispatcher);
        }
        return defaultDrmSession;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    @Nullable
    public Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format) {
        Class<? extends ExoMediaCrypto> exoMediaCryptoType = ((ExoMediaDrm) Assertions.checkNotNull(this.q)).getExoMediaCryptoType();
        if (format.drmInitData != null) {
            return a(format.drmInitData) ? exoMediaCryptoType : UnsupportedMediaCrypto.class;
        }
        if (Util.linearSearch(this.g, MimeTypes.getTrackType(format.sampleMimeType)) != -1) {
            return exoMediaCryptoType;
        }
        return null;
    }

    @Nullable
    private DrmSession a(int i, boolean z) {
        ExoMediaDrm exoMediaDrm = (ExoMediaDrm) Assertions.checkNotNull(this.q);
        if ((FrameworkMediaCrypto.class.equals(exoMediaDrm.getExoMediaCryptoType()) && FrameworkMediaCrypto.WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC) || Util.linearSearch(this.g, i) == -1 || UnsupportedMediaCrypto.class.equals(exoMediaDrm.getExoMediaCryptoType())) {
            return null;
        }
        DefaultDrmSession defaultDrmSession = this.r;
        if (defaultDrmSession == null) {
            DefaultDrmSession a2 = a((List<DrmInitData.SchemeData>) ImmutableList.of(), true, (DrmSessionEventListener.EventDispatcher) null, z);
            this.m.add(a2);
            this.r = a2;
        } else {
            defaultDrmSession.acquire(null);
        }
        return this.r;
    }

    private boolean a(DrmInitData drmInitData) {
        if (this.w != null) {
            return true;
        }
        if (a(drmInitData, this.b, true).isEmpty()) {
            if (drmInitData.schemeDataCount != 1 || !drmInitData.get(0).matches(C.COMMON_PSSH_UUID)) {
                return false;
            }
            String valueOf = String.valueOf(this.b);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 72);
            sb.append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ");
            sb.append(valueOf);
            Log.w("DefaultDrmSessionMgr", sb.toString());
        }
        String str = drmInitData.schemeType;
        if (str == null || C.CENC_TYPE_cenc.equals(str)) {
            return true;
        }
        return C.CENC_TYPE_cbcs.equals(str) ? Util.SDK_INT >= 25 : !C.CENC_TYPE_cbc1.equals(str) && !C.CENC_TYPE_cens.equals(str);
    }

    @EnsuresNonNull({"this.playbackLooper", "this.playbackHandler"})
    private synchronized void a(Looper looper) {
        if (this.t == null) {
            this.t = looper;
            this.u = new Handler(looper);
        } else {
            Assertions.checkState(this.t == looper);
            Assertions.checkNotNull(this.u);
        }
    }

    private void b(Looper looper) {
        if (this.a == null) {
            this.a = new b(looper);
        }
    }

    private DefaultDrmSession a(@Nullable List<DrmInitData.SchemeData> list, boolean z, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, boolean z2) {
        DefaultDrmSession a2 = a(list, z, eventDispatcher);
        if (a(a2) && !this.o.isEmpty()) {
            a();
            a(a2, eventDispatcher);
            a2 = a(list, z, eventDispatcher);
        }
        if (!a(a2) || !z2 || this.n.isEmpty()) {
            return a2;
        }
        b();
        if (!this.o.isEmpty()) {
            a();
        }
        a(a2, eventDispatcher);
        return a(list, z, eventDispatcher);
    }

    private static boolean a(DrmSession drmSession) {
        return drmSession.getState() == 1 && (Util.SDK_INT < 19 || (((DrmSession.DrmSessionException) Assertions.checkNotNull(drmSession.getError())).getCause() instanceof ResourceBusyException));
    }

    private void a(DrmSession drmSession, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        drmSession.release(eventDispatcher);
        if (this.l != C.TIME_UNSET) {
            drmSession.release(null);
        }
    }

    private void a() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.o).iterator();
        while (it.hasNext()) {
            ((DrmSession) it.next()).release(null);
        }
    }

    private void b() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.n).iterator();
        while (it.hasNext()) {
            ((c) it.next()).release();
        }
    }

    private DefaultDrmSession a(@Nullable List<DrmInitData.SchemeData> list, boolean z, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        Assertions.checkNotNull(this.q);
        DefaultDrmSession defaultDrmSession = new DefaultDrmSession(this.b, this.q, this.i, this.k, list, this.v, this.h | z, z, this.w, this.e, this.d, (Looper) Assertions.checkNotNull(this.t), this.j);
        defaultDrmSession.acquire(eventDispatcher);
        if (this.l != C.TIME_UNSET) {
            defaultDrmSession.acquire(null);
        }
        return defaultDrmSession;
    }

    public void c() {
        if (this.q != null && this.p == 0 && this.m.isEmpty() && this.n.isEmpty()) {
            ((ExoMediaDrm) Assertions.checkNotNull(this.q)).release();
            this.q = null;
        }
    }

    private static List<DrmInitData.SchemeData> a(DrmInitData drmInitData, UUID uuid, boolean z) {
        ArrayList arrayList = new ArrayList(drmInitData.schemeDataCount);
        for (int i = 0; i < drmInitData.schemeDataCount; i++) {
            DrmInitData.SchemeData schemeData = drmInitData.get(i);
            if ((schemeData.matches(uuid) || (C.CLEARKEY_UUID.equals(uuid) && schemeData.matches(C.COMMON_PSSH_UUID))) && (schemeData.data != null || z)) {
                arrayList.add(schemeData);
            }
        }
        return arrayList;
    }

    @SuppressLint({"HandlerLeak"})
    /* loaded from: classes2.dex */
    public class b extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(Looper looper) {
            super(looper);
            DefaultDrmSessionManager.this = r1;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            byte[] bArr = (byte[]) message.obj;
            if (bArr != null) {
                for (DefaultDrmSession defaultDrmSession : DefaultDrmSessionManager.this.m) {
                    if (defaultDrmSession.a(bArr)) {
                        defaultDrmSession.a(message.what);
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public class d implements DefaultDrmSession.ProvisioningManager {
        private final Set<DefaultDrmSession> a = new HashSet();
        @Nullable
        private DefaultDrmSession b;

        public d(DefaultDrmSessionManager defaultDrmSessionManager) {
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void provisionRequired(DefaultDrmSession defaultDrmSession) {
            this.a.add(defaultDrmSession);
            if (this.b == null) {
                this.b = defaultDrmSession;
                defaultDrmSession.a();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void onProvisionCompleted() {
            this.b = null;
            ImmutableList copyOf = ImmutableList.copyOf((Collection) this.a);
            this.a.clear();
            UnmodifiableIterator it = copyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).b();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void onProvisionError(Exception exc, boolean z) {
            this.b = null;
            ImmutableList copyOf = ImmutableList.copyOf((Collection) this.a);
            this.a.clear();
            UnmodifiableIterator it = copyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).a(exc, z);
            }
        }

        public void a(DefaultDrmSession defaultDrmSession) {
            this.a.remove(defaultDrmSession);
            if (this.b == defaultDrmSession) {
                this.b = null;
                if (!this.a.isEmpty()) {
                    this.b = this.a.iterator().next();
                    this.b.a();
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public class e implements DefaultDrmSession.ReferenceCountListener {
        private e() {
            DefaultDrmSessionManager.this = r1;
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ReferenceCountListener
        public void onReferenceCountIncremented(DefaultDrmSession defaultDrmSession, int i) {
            if (DefaultDrmSessionManager.this.l != C.TIME_UNSET) {
                DefaultDrmSessionManager.this.o.remove(defaultDrmSession);
                ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u)).removeCallbacksAndMessages(defaultDrmSession);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ReferenceCountListener
        public void onReferenceCountDecremented(final DefaultDrmSession defaultDrmSession, int i) {
            if (i == 1 && DefaultDrmSessionManager.this.p > 0 && DefaultDrmSessionManager.this.l != C.TIME_UNSET) {
                DefaultDrmSessionManager.this.o.add(defaultDrmSession);
                ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u)).postAtTime(new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DefaultDrmSessionManager$e$9vba4bcbKGQwt79-uAsTaV3FUoI
                    @Override // java.lang.Runnable
                    public final void run() {
                        DefaultDrmSession.this.release(null);
                    }
                }, defaultDrmSession, SystemClock.uptimeMillis() + DefaultDrmSessionManager.this.l);
            } else if (i == 0) {
                DefaultDrmSessionManager.this.m.remove(defaultDrmSession);
                if (DefaultDrmSessionManager.this.r == defaultDrmSession) {
                    DefaultDrmSessionManager.this.r = null;
                }
                if (DefaultDrmSessionManager.this.s == defaultDrmSession) {
                    DefaultDrmSessionManager.this.s = null;
                }
                DefaultDrmSessionManager.this.i.a(defaultDrmSession);
                if (DefaultDrmSessionManager.this.l != C.TIME_UNSET) {
                    ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u)).removeCallbacksAndMessages(defaultDrmSession);
                    DefaultDrmSessionManager.this.o.remove(defaultDrmSession);
                }
            }
            DefaultDrmSessionManager.this.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a implements ExoMediaDrm.OnEventListener {
        private a() {
            DefaultDrmSessionManager.this = r1;
        }

        @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener
        public void onEvent(ExoMediaDrm exoMediaDrm, @Nullable byte[] bArr, int i, int i2, @Nullable byte[] bArr2) {
            ((b) Assertions.checkNotNull(DefaultDrmSessionManager.this.a)).obtainMessage(i, bArr).sendToTarget();
        }
    }

    /* loaded from: classes2.dex */
    public class c implements DrmSessionManager.DrmSessionReference {
        @Nullable
        private final DrmSessionEventListener.EventDispatcher b;
        @Nullable
        private DrmSession c;
        private boolean d;

        public c(DrmSessionEventListener.EventDispatcher eventDispatcher) {
            DefaultDrmSessionManager.this = r1;
            this.b = eventDispatcher;
        }

        public void a(final Format format) {
            ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u)).post(new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DefaultDrmSessionManager$c$zn8YojDzWtQb6F2KRXk9afM2li4
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultDrmSessionManager.c.this.b(format);
                }
            });
        }

        public /* synthetic */ void b(Format format) {
            if (DefaultDrmSessionManager.this.p != 0 && !this.d) {
                DefaultDrmSessionManager defaultDrmSessionManager = DefaultDrmSessionManager.this;
                this.c = defaultDrmSessionManager.a((Looper) Assertions.checkNotNull(defaultDrmSessionManager.t), this.b, format, false);
                DefaultDrmSessionManager.this.n.add(this);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionManager.DrmSessionReference
        public void release() {
            Util.postOrRun((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u), new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DefaultDrmSessionManager$c$uJY4_RMsKXTO6AAnbKYGQxDA8cg
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultDrmSessionManager.c.this.a();
                }
            });
        }

        public /* synthetic */ void a() {
            if (!this.d) {
                DrmSession drmSession = this.c;
                if (drmSession != null) {
                    drmSession.release(this.b);
                }
                DefaultDrmSessionManager.this.n.remove(this);
                this.d = true;
            }
        }
    }
}
