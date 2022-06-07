package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.media.NotProvisionedException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Consumer;
import com.google.android.exoplayer2.util.CopyOnWriteMultiset;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* JADX INFO: Access modifiers changed from: package-private */
@RequiresApi(18)
/* loaded from: classes2.dex */
public class DefaultDrmSession implements DrmSession {
    @Nullable
    public final List<DrmInitData.SchemeData> a;
    final MediaDrmCallback b;
    final UUID c;
    final c d;
    private final ExoMediaDrm e;
    private final ProvisioningManager f;
    private final ReferenceCountListener g;
    private final int h;
    private final boolean i;
    private final boolean j;
    private final HashMap<String, String> k;
    private final CopyOnWriteMultiset<DrmSessionEventListener.EventDispatcher> l;
    private final LoadErrorHandlingPolicy m;
    private int n;
    private int o;
    @Nullable
    private HandlerThread p;
    @Nullable
    private a q;
    @Nullable
    private ExoMediaCrypto r;
    @Nullable
    private DrmSession.DrmSessionException s;
    @Nullable
    private byte[] t;
    private byte[] u;
    @Nullable
    private ExoMediaDrm.KeyRequest v;
    @Nullable
    private ExoMediaDrm.ProvisionRequest w;

    /* loaded from: classes2.dex */
    public interface ProvisioningManager {
        void onProvisionCompleted();

        void onProvisionError(Exception exc, boolean z);

        void provisionRequired(DefaultDrmSession defaultDrmSession);
    }

    /* loaded from: classes2.dex */
    public interface ReferenceCountListener {
        void onReferenceCountDecremented(DefaultDrmSession defaultDrmSession, int i);

        void onReferenceCountIncremented(DefaultDrmSession defaultDrmSession, int i);
    }

    /* loaded from: classes2.dex */
    public static final class UnexpectedDrmSessionException extends IOException {
        public UnexpectedDrmSessionException(@Nullable Throwable th) {
            super(th);
        }
    }

    public DefaultDrmSession(UUID uuid, ExoMediaDrm exoMediaDrm, ProvisioningManager provisioningManager, ReferenceCountListener referenceCountListener, @Nullable List<DrmInitData.SchemeData> list, int i, boolean z, boolean z2, @Nullable byte[] bArr, HashMap<String, String> hashMap, MediaDrmCallback mediaDrmCallback, Looper looper, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        if (i == 1 || i == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.c = uuid;
        this.f = provisioningManager;
        this.g = referenceCountListener;
        this.e = exoMediaDrm;
        this.h = i;
        this.i = z;
        this.j = z2;
        if (bArr != null) {
            this.u = bArr;
            this.a = null;
        } else {
            this.a = Collections.unmodifiableList((List) Assertions.checkNotNull(list));
        }
        this.k = hashMap;
        this.b = mediaDrmCallback;
        this.l = new CopyOnWriteMultiset<>();
        this.m = loadErrorHandlingPolicy;
        this.n = 2;
        this.d = new c(looper);
    }

    public boolean a(byte[] bArr) {
        return Arrays.equals(this.t, bArr);
    }

    public void a(int i) {
        if (i == 2) {
            f();
        }
    }

    public void a() {
        this.w = this.e.getProvisionRequest();
        ((a) Util.castNonNull(this.q)).a(0, Assertions.checkNotNull(this.w), true);
    }

    public void b() {
        if (c()) {
            a(true);
        }
    }

    public void a(Exception exc, boolean z) {
        a(exc, z ? 1 : 3);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    public final int getState() {
        return this.n;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    public boolean playClearSamplesWithoutKeys() {
        return this.i;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    @Nullable
    public final DrmSession.DrmSessionException getError() {
        if (this.n == 1) {
            return this.s;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    public final UUID getSchemeUuid() {
        return this.c;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    @Nullable
    public final ExoMediaCrypto getMediaCrypto() {
        return this.r;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    @Nullable
    public Map<String, String> queryKeyStatus() {
        byte[] bArr = this.t;
        if (bArr == null) {
            return null;
        }
        return this.e.queryKeyStatus(bArr);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    @Nullable
    public byte[] getOfflineLicenseKeySetId() {
        return this.u;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    public void acquire(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        boolean z = false;
        Assertions.checkState(this.o >= 0);
        if (eventDispatcher != null) {
            this.l.add(eventDispatcher);
        }
        int i = this.o + 1;
        this.o = i;
        if (i == 1) {
            if (this.n == 2) {
                z = true;
            }
            Assertions.checkState(z);
            this.p = new HandlerThread("ExoPlayer:DrmRequestHandler");
            this.p.start();
            this.q = new a(this.p.getLooper());
            if (c()) {
                a(true);
            }
        } else if (eventDispatcher != null && g() && this.l.count(eventDispatcher) == 1) {
            eventDispatcher.drmSessionAcquired(this.n);
        }
        this.g.onReferenceCountIncremented(this, this.o);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSession
    public void release(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        Assertions.checkState(this.o > 0);
        int i = this.o - 1;
        this.o = i;
        if (i == 0) {
            this.n = 0;
            ((c) Util.castNonNull(this.d)).removeCallbacksAndMessages(null);
            ((a) Util.castNonNull(this.q)).a();
            this.q = null;
            ((HandlerThread) Util.castNonNull(this.p)).quit();
            this.p = null;
            this.r = null;
            this.s = null;
            this.v = null;
            this.w = null;
            byte[] bArr = this.t;
            if (bArr != null) {
                this.e.closeSession(bArr);
                this.t = null;
            }
        }
        if (eventDispatcher != null) {
            this.l.remove(eventDispatcher);
            if (this.l.count(eventDispatcher) == 0) {
                eventDispatcher.drmSessionReleased();
            }
        }
        this.g.onReferenceCountDecremented(this, this.o);
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    private boolean c() {
        if (g()) {
            return true;
        }
        try {
            this.t = this.e.openSession();
            this.r = this.e.createMediaCrypto(this.t);
            this.n = 3;
            final int i = this.n;
            a(new Consumer() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DefaultDrmSession$F-ZVkkI83KJ4j14ssqeKGTkr9bY
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    ((DrmSessionEventListener.EventDispatcher) obj).drmSessionAcquired(i);
                }
            });
            Assertions.checkNotNull(this.t);
            return true;
        } catch (NotProvisionedException unused) {
            this.f.provisionRequired(this);
            return false;
        } catch (Exception e) {
            a(e, 1);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, Object obj2) {
        if (obj != this.w) {
            return;
        }
        if (this.n == 2 || g()) {
            this.w = null;
            if (obj2 instanceof Exception) {
                this.f.onProvisionError((Exception) obj2, false);
                return;
            }
            try {
                this.e.provideProvisionResponse((byte[]) obj2);
                this.f.onProvisionCompleted();
            } catch (Exception e) {
                this.f.onProvisionError(e, true);
            }
        }
    }

    @RequiresNonNull({"sessionId"})
    private void a(boolean z) {
        if (!this.j) {
            byte[] bArr = (byte[]) Util.castNonNull(this.t);
            switch (this.h) {
                case 0:
                case 1:
                    if (this.u == null) {
                        a(bArr, 1, z);
                        return;
                    } else if (this.n == 4 || d()) {
                        long e = e();
                        if (this.h == 0 && e <= 60) {
                            StringBuilder sb = new StringBuilder(88);
                            sb.append("Offline license has expired or will expire soon. Remaining seconds: ");
                            sb.append(e);
                            Log.d("DefaultDrmSession", sb.toString());
                            a(bArr, 2, z);
                            return;
                        } else if (e <= 0) {
                            a(new KeysExpiredException(), 2);
                            return;
                        } else {
                            this.n = 4;
                            a($$Lambda$HbN0kSVsD6YcIJxw09z6YQauRzY.INSTANCE);
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    if (this.u == null || d()) {
                        a(bArr, 2, z);
                        return;
                    }
                    return;
                case 3:
                    Assertions.checkNotNull(this.u);
                    Assertions.checkNotNull(this.t);
                    a(this.u, 3, z);
                    return;
                default:
                    return;
            }
        }
    }

    @RequiresNonNull({"sessionId", "offlineLicenseKeySetId"})
    private boolean d() {
        try {
            this.e.restoreKeys(this.t, this.u);
            return true;
        } catch (Exception e) {
            a(e, 1);
            return false;
        }
    }

    private long e() {
        if (!C.WIDEVINE_UUID.equals(this.c)) {
            return Long.MAX_VALUE;
        }
        Pair pair = (Pair) Assertions.checkNotNull(WidevineUtil.getLicenseDurationRemainingSec(this));
        return Math.min(((Long) pair.first).longValue(), ((Long) pair.second).longValue());
    }

    private void a(byte[] bArr, int i, boolean z) {
        try {
            this.v = this.e.getKeyRequest(bArr, this.a, i, this.k);
            ((a) Util.castNonNull(this.q)).a(1, Assertions.checkNotNull(this.v), z);
        } catch (Exception e) {
            b(e, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Object obj, Object obj2) {
        if (obj == this.v && g()) {
            this.v = null;
            if (obj2 instanceof Exception) {
                b((Exception) obj2, false);
                return;
            }
            try {
                byte[] bArr = (byte[]) obj2;
                if (this.h == 3) {
                    this.e.provideKeyResponse((byte[]) Util.castNonNull(this.u), bArr);
                    a($$Lambda$pre3sEqF1vViKhCFp1NAV3_mgZk.INSTANCE);
                    return;
                }
                byte[] provideKeyResponse = this.e.provideKeyResponse(this.t, bArr);
                if (!((this.h != 2 && (this.h != 0 || this.u == null)) || provideKeyResponse == null || provideKeyResponse.length == 0)) {
                    this.u = provideKeyResponse;
                }
                this.n = 4;
                a($$Lambda$BrYRYnbSvqr_udlxRiVssV28H70.INSTANCE);
            } catch (Exception e) {
                b(e, true);
            }
        }
    }

    private void f() {
        if (this.h == 0 && this.n == 4) {
            Util.castNonNull(this.t);
            a(false);
        }
    }

    private void b(Exception exc, boolean z) {
        if (exc instanceof NotProvisionedException) {
            this.f.provisionRequired(this);
        } else {
            a(exc, z ? 1 : 2);
        }
    }

    private void a(final Exception exc, int i) {
        this.s = new DrmSession.DrmSessionException(exc, DrmUtil.getErrorCodeForMediaDrmException(exc, i));
        Log.e("DefaultDrmSession", "DRM session error", exc);
        a(new Consumer() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DefaultDrmSession$RIOrhEoymWHpCckc0BedvcVGRus
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                ((DrmSessionEventListener.EventDispatcher) obj).drmSessionManagerError(exc);
            }
        });
        if (this.n != 4) {
            this.n = 1;
        }
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    private boolean g() {
        int i = this.n;
        return i == 3 || i == 4;
    }

    private void a(Consumer<DrmSessionEventListener.EventDispatcher> consumer) {
        for (DrmSessionEventListener.EventDispatcher eventDispatcher : this.l.elementSet()) {
            consumer.accept(eventDispatcher);
        }
    }

    @SuppressLint({"HandlerLeak"})
    /* loaded from: classes2.dex */
    private class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Pair pair = (Pair) message.obj;
            Object obj = pair.first;
            Object obj2 = pair.second;
            switch (message.what) {
                case 0:
                    DefaultDrmSession.this.a(obj, obj2);
                    return;
                case 1:
                    DefaultDrmSession.this.b(obj, obj2);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    /* loaded from: classes2.dex */
    public class a extends Handler {
        @GuardedBy("this")
        private boolean b;

        public a(Looper looper) {
            super(looper);
        }

        void a(int i, Object obj, boolean z) {
            obtainMessage(i, new b(LoadEventInfo.getNewId(), z, SystemClock.elapsedRealtime(), obj)).sendToTarget();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Throwable, java.lang.Exception] */
        /* JADX WARN: Unknown variable types count: 1 */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                java.lang.Object r0 = r6.obj
                com.google.android.exoplayer2.drm.DefaultDrmSession$b r0 = (com.google.android.exoplayer2.drm.DefaultDrmSession.b) r0
                int r1 = r6.what     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                switch(r1) {
                    case 0: goto L_0x001d;
                    case 1: goto L_0x000c;
                    default: goto L_0x0009;
                }     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
            L_0x0009:
                java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                goto L_0x002e
            L_0x000c:
                com.google.android.exoplayer2.drm.DefaultDrmSession r1 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                com.google.android.exoplayer2.drm.MediaDrmCallback r1 = r1.b     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                com.google.android.exoplayer2.drm.DefaultDrmSession r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                java.util.UUID r2 = r2.c     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                java.lang.Object r3 = r0.d     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                com.google.android.exoplayer2.drm.ExoMediaDrm$KeyRequest r3 = (com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest) r3     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                byte[] r1 = r1.executeKeyRequest(r2, r3)     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                goto L_0x0043
            L_0x001d:
                com.google.android.exoplayer2.drm.DefaultDrmSession r1 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                com.google.android.exoplayer2.drm.MediaDrmCallback r1 = r1.b     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                com.google.android.exoplayer2.drm.DefaultDrmSession r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                java.util.UUID r2 = r2.c     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                java.lang.Object r3 = r0.d     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                com.google.android.exoplayer2.drm.ExoMediaDrm$ProvisionRequest r3 = (com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest) r3     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                byte[] r1 = r1.executeProvisionRequest(r2, r3)     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                goto L_0x0043
            L_0x002e:
                r1.<init>()     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
                throw r1     // Catch: MediaDrmCallbackException -> 0x003b, Exception -> 0x0032
            L_0x0032:
                r1 = move-exception
                java.lang.String r2 = "DefaultDrmSession"
                java.lang.String r3 = "Key/provisioning request produced an unexpected exception. Not retrying."
                com.google.android.exoplayer2.util.Log.w(r2, r3, r1)
                goto L_0x0043
            L_0x003b:
                r1 = move-exception
                boolean r2 = r5.a(r6, r1)
                if (r2 == 0) goto L_0x0043
                return
            L_0x0043:
                com.google.android.exoplayer2.drm.DefaultDrmSession r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.this
                com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.a(r2)
                long r3 = r0.a
                r2.onLoadTaskConcluded(r3)
                monitor-enter(r5)
                boolean r2 = r5.b     // Catch: all -> 0x0068
                if (r2 != 0) goto L_0x0066
                com.google.android.exoplayer2.drm.DefaultDrmSession r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch: all -> 0x0068
                com.google.android.exoplayer2.drm.DefaultDrmSession$c r2 = r2.d     // Catch: all -> 0x0068
                int r6 = r6.what     // Catch: all -> 0x0068
                java.lang.Object r0 = r0.d     // Catch: all -> 0x0068
                android.util.Pair r0 = android.util.Pair.create(r0, r1)     // Catch: all -> 0x0068
                android.os.Message r6 = r2.obtainMessage(r6, r0)     // Catch: all -> 0x0068
                r6.sendToTarget()     // Catch: all -> 0x0068
            L_0x0066:
                monitor-exit(r5)     // Catch: all -> 0x0068
                return
            L_0x0068:
                r6 = move-exception
                monitor-exit(r5)     // Catch: all -> 0x0068
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.DefaultDrmSession.a.handleMessage(android.os.Message):void");
        }

        private boolean a(Message message, MediaDrmCallbackException mediaDrmCallbackException) {
            IOException iOException;
            b bVar = (b) message.obj;
            if (!bVar.b) {
                return false;
            }
            bVar.e++;
            if (bVar.e > DefaultDrmSession.this.m.getMinimumLoadableRetryCount(3)) {
                return false;
            }
            LoadEventInfo loadEventInfo = new LoadEventInfo(bVar.a, mediaDrmCallbackException.dataSpec, mediaDrmCallbackException.uriAfterRedirects, mediaDrmCallbackException.responseHeaders, SystemClock.elapsedRealtime(), SystemClock.elapsedRealtime() - bVar.c, mediaDrmCallbackException.bytesLoaded);
            MediaLoadData mediaLoadData = new MediaLoadData(3);
            if (mediaDrmCallbackException.getCause() instanceof IOException) {
                iOException = (IOException) mediaDrmCallbackException.getCause();
            } else {
                iOException = new UnexpectedDrmSessionException(mediaDrmCallbackException.getCause());
            }
            long retryDelayMsFor = DefaultDrmSession.this.m.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, mediaLoadData, iOException, bVar.e));
            if (retryDelayMsFor == C.TIME_UNSET) {
                return false;
            }
            synchronized (this) {
                if (this.b) {
                    return false;
                }
                sendMessageDelayed(Message.obtain(message), retryDelayMsFor);
                return true;
            }
        }

        public synchronized void a() {
            removeCallbacksAndMessages(null);
            this.b = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        public final long a;
        public final boolean b;
        public final long c;
        public final Object d;
        public int e;

        public b(long j, boolean z, long j2, Object obj) {
            this.a = j;
            this.b = z;
            this.c = j2;
            this.d = obj;
        }
    }
}
