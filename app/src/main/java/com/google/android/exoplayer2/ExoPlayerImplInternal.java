package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.DefaultMediaClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ExoPlayerImplInternal implements Handler.Callback, DefaultMediaClock.PlaybackParametersListener, MediaSourceList.MediaSourceListInfoRefreshListener, PlayerMessage.Sender, MediaPeriod.Callback, TrackSelector.InvalidationListener {
    private boolean A;
    private boolean B;
    private boolean C;
    private int D;
    private boolean E;
    private boolean F;
    private boolean G;
    private boolean H;
    private int I;
    @Nullable
    private e J;
    private long K;
    private int L;
    private boolean M;
    @Nullable
    private ExoPlaybackException N;
    private long O;
    private final Renderer[] a;
    private final RendererCapabilities[] b;
    private final TrackSelector c;
    private final TrackSelectorResult d;
    private final LoadControl e;
    private final BandwidthMeter f;
    private final HandlerWrapper g;
    private final HandlerThread h;
    private final Looper i;
    private final Timeline.Window j;
    private final Timeline.Period k;
    private final long l;
    private final boolean m;
    private final DefaultMediaClock n;
    private final ArrayList<c> o;
    private final Clock p;
    private final PlaybackInfoUpdateListener q;
    private final e r;
    private final MediaSourceList s;
    private final LivePlaybackSpeedControl t;
    private final long u;
    private SeekParameters v;
    private g w;
    private PlaybackInfoUpdate x;
    private boolean y;
    private boolean z;

    /* loaded from: classes.dex */
    public interface PlaybackInfoUpdateListener {
        void onPlaybackInfoUpdate(PlaybackInfoUpdate playbackInfoUpdate);
    }

    /* loaded from: classes.dex */
    public static final class PlaybackInfoUpdate {
        private boolean a;
        public int discontinuityReason;
        public boolean hasPlayWhenReadyChangeReason;
        public int operationAcks;
        public int playWhenReadyChangeReason;
        public g playbackInfo;
        public boolean positionDiscontinuity;

        public PlaybackInfoUpdate(g gVar) {
            this.playbackInfo = gVar;
        }

        public void incrementPendingOperationAcks(int i) {
            this.a |= i > 0;
            this.operationAcks += i;
        }

        public void setPlaybackInfo(g gVar) {
            this.a |= this.playbackInfo != gVar;
            this.playbackInfo = gVar;
        }

        public void setPositionDiscontinuity(int i) {
            boolean z = true;
            if (!this.positionDiscontinuity || this.discontinuityReason == 5) {
                this.a = true;
                this.positionDiscontinuity = true;
                this.discontinuityReason = i;
                return;
            }
            if (i != 5) {
                z = false;
            }
            Assertions.checkArgument(z);
        }

        public void setPlayWhenReadyChangeReason(int i) {
            this.a = true;
            this.hasPlayWhenReadyChangeReason = true;
            this.playWhenReadyChangeReason = i;
        }
    }

    public ExoPlayerImplInternal(Renderer[] rendererArr, TrackSelector trackSelector, TrackSelectorResult trackSelectorResult, LoadControl loadControl, BandwidthMeter bandwidthMeter, int i, boolean z, @Nullable AnalyticsCollector analyticsCollector, SeekParameters seekParameters, LivePlaybackSpeedControl livePlaybackSpeedControl, long j, boolean z2, Looper looper, Clock clock, PlaybackInfoUpdateListener playbackInfoUpdateListener) {
        this.q = playbackInfoUpdateListener;
        this.a = rendererArr;
        this.c = trackSelector;
        this.d = trackSelectorResult;
        this.e = loadControl;
        this.f = bandwidthMeter;
        this.D = i;
        this.E = z;
        this.v = seekParameters;
        this.t = livePlaybackSpeedControl;
        this.u = j;
        this.O = j;
        this.z = z2;
        this.p = clock;
        this.l = loadControl.getBackBufferDurationUs();
        this.m = loadControl.retainBackBufferFromKeyframe();
        this.w = g.a(trackSelectorResult);
        this.x = new PlaybackInfoUpdate(this.w);
        this.b = new RendererCapabilities[rendererArr.length];
        for (int i2 = 0; i2 < rendererArr.length; i2++) {
            rendererArr[i2].setIndex(i2);
            this.b[i2] = rendererArr[i2].getCapabilities();
        }
        this.n = new DefaultMediaClock(this, clock);
        this.o = new ArrayList<>();
        this.j = new Timeline.Window();
        this.k = new Timeline.Period();
        trackSelector.init(this, bandwidthMeter);
        this.M = true;
        Handler handler = new Handler(looper);
        this.r = new e(analyticsCollector, handler);
        this.s = new MediaSourceList(this, analyticsCollector, handler);
        this.h = new HandlerThread("ExoPlayer:Playback", -16);
        this.h.start();
        this.i = this.h.getLooper();
        this.g = clock.createHandler(this.i, this);
    }

    public void a(long j) {
        this.O = j;
    }

    public void a(boolean z) {
        this.g.obtainMessage(24, z ? 1 : 0, 0).sendToTarget();
    }

    public void a() {
        this.g.obtainMessage(0).sendToTarget();
    }

    public void a(boolean z, int i) {
        this.g.obtainMessage(1, z ? 1 : 0, i).sendToTarget();
    }

    public void b(boolean z) {
        this.g.obtainMessage(23, z ? 1 : 0, 0).sendToTarget();
    }

    public void a(int i) {
        this.g.obtainMessage(11, i, 0).sendToTarget();
    }

    public void c(boolean z) {
        this.g.obtainMessage(12, z ? 1 : 0, 0).sendToTarget();
    }

    public void a(Timeline timeline, int i, long j) {
        this.g.obtainMessage(3, new e(timeline, i, j)).sendToTarget();
    }

    public void a(PlaybackParameters playbackParameters) {
        this.g.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void a(SeekParameters seekParameters) {
        this.g.obtainMessage(5, seekParameters).sendToTarget();
    }

    public void b() {
        this.g.obtainMessage(6).sendToTarget();
    }

    public void a(List<MediaSourceList.c> list, int i, long j, ShuffleOrder shuffleOrder) {
        this.g.obtainMessage(17, new a(list, shuffleOrder, i, j)).sendToTarget();
    }

    public void a(int i, List<MediaSourceList.c> list, ShuffleOrder shuffleOrder) {
        this.g.obtainMessage(18, i, 0, new a(list, shuffleOrder, -1, C.TIME_UNSET)).sendToTarget();
    }

    public void a(int i, int i2, ShuffleOrder shuffleOrder) {
        this.g.obtainMessage(20, i, i2, shuffleOrder).sendToTarget();
    }

    public void a(int i, int i2, int i3, ShuffleOrder shuffleOrder) {
        this.g.obtainMessage(19, new b(i, i2, i3, shuffleOrder)).sendToTarget();
    }

    public void a(ShuffleOrder shuffleOrder) {
        this.g.obtainMessage(21, shuffleOrder).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Sender
    public synchronized void sendMessage(PlayerMessage playerMessage) {
        if (!this.y && this.h.isAlive()) {
            this.g.obtainMessage(14, playerMessage).sendToTarget();
            return;
        }
        Log.w("ExoPlayerImplInternal", "Ignoring messages sent after release.");
        playerMessage.markAsProcessed(false);
    }

    public synchronized boolean d(boolean z) {
        if (!this.y && this.h.isAlive()) {
            if (z) {
                this.g.obtainMessage(13, 1, 0).sendToTarget();
                return true;
            }
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            this.g.obtainMessage(13, 0, 0, atomicBoolean).sendToTarget();
            Objects.requireNonNull(atomicBoolean);
            a(new Supplier() { // from class: com.google.android.exoplayer2.-$$Lambda$WCNOc30RWlKfWWqwDTxwdaoGH-w
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return Boolean.valueOf(atomicBoolean.get());
                }
            }, this.O);
            return atomicBoolean.get();
        }
        return true;
    }

    public synchronized boolean c() {
        if (!this.y && this.h.isAlive()) {
            this.g.sendEmptyMessage(7);
            a(new Supplier() { // from class: com.google.android.exoplayer2.-$$Lambda$ExoPlayerImplInternal$w8WEeUn0ezpDZF3sInGFb0QJh-I
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    Boolean J;
                    J = ExoPlayerImplInternal.this.J();
                    return J;
                }
            }, this.u);
            return this.y;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean J() {
        return Boolean.valueOf(this.y);
    }

    public Looper d() {
        return this.i;
    }

    @Override // com.google.android.exoplayer2.MediaSourceList.MediaSourceListInfoRefreshListener
    public void onPlaylistUpdateRequested() {
        this.g.sendEmptyMessage(22);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod mediaPeriod) {
        this.g.obtainMessage(8, mediaPeriod).sendToTarget();
    }

    /* renamed from: a */
    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.g.obtainMessage(9, mediaPeriod).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
    public void onTrackSelectionsInvalidated() {
        this.g.sendEmptyMessage(10);
    }

    @Override // com.google.android.exoplayer2.DefaultMediaClock.PlaybackParametersListener
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        this.g.obtainMessage(16, playbackParameters).sendToTarget();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        b d2;
        int i = 1000;
        try {
            switch (message.what) {
                case 0:
                    f();
                    break;
                case 1:
                    a(message.arg1 != 0, message.arg2, true, 1);
                    break;
                case 2:
                    m();
                    break;
                case 3:
                    a((e) message.obj);
                    break;
                case 4:
                    b((PlaybackParameters) message.obj);
                    break;
                case 5:
                    b((SeekParameters) message.obj);
                    break;
                case 6:
                    a(false, true);
                    break;
                case 7:
                    o();
                    return true;
                case 8:
                    b((MediaPeriod) message.obj);
                    break;
                case 9:
                    c((MediaPeriod) message.obj);
                    break;
                case 10:
                    p();
                    break;
                case 11:
                    c(message.arg1);
                    break;
                case 12:
                    h(message.arg1 != 0);
                    break;
                case 13:
                    a(message.arg1 != 0, (AtomicBoolean) message.obj);
                    break;
                case 14:
                    a((PlayerMessage) message.obj);
                    break;
                case 15:
                    c((PlayerMessage) message.obj);
                    break;
                case 16:
                    a((PlaybackParameters) message.obj, false);
                    break;
                case 17:
                    a((a) message.obj);
                    break;
                case 18:
                    a((a) message.obj, message.arg1);
                    break;
                case 19:
                    a((b) message.obj);
                    break;
                case 20:
                    b(message.arg1, message.arg2, (ShuffleOrder) message.obj);
                    break;
                case 21:
                    b((ShuffleOrder) message.obj);
                    break;
                case 22:
                    g();
                    break;
                case 23:
                    f(message.arg1 != 0);
                    break;
                case 24:
                    g(message.arg1 == 1);
                    break;
                case 25:
                    j();
                    break;
                default:
                    return false;
            }
        } catch (ExoPlaybackException e2) {
            e = e2;
            if (e.type == 1 && (d2 = this.r.d()) != null) {
                e = e.a(d2.f.a);
            }
            if (!e.isRecoverable || this.N != null) {
                ExoPlaybackException exoPlaybackException = this.N;
                if (exoPlaybackException != null) {
                    exoPlaybackException.addSuppressed(e);
                    e = this.N;
                }
                Log.e("ExoPlayerImplInternal", "Playback error", e);
                a(true, false);
                this.w = this.w.a(e);
            } else {
                Log.w("ExoPlayerImplInternal", "Recoverable renderer error", e);
                this.N = e;
                HandlerWrapper handlerWrapper = this.g;
                handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(25, e));
            }
        } catch (ParserException e3) {
            if (e3.dataType == 1) {
                i = e3.contentIsMalformed ? 3001 : 3003;
            } else if (e3.dataType == 4) {
                i = e3.contentIsMalformed ? 3002 : PlaybackException.ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED;
            }
            a(e3, i);
        } catch (DrmSession.DrmSessionException e4) {
            a(e4, e4.errorCode);
        } catch (BehindLiveWindowException e5) {
            a(e5, 1002);
        } catch (DataSourceException e6) {
            a(e6, e6.reason);
        } catch (IOException e7) {
            a(e7, 2000);
        } catch (RuntimeException e8) {
            if ((e8 instanceof IllegalStateException) || (e8 instanceof IllegalArgumentException)) {
                i = 1004;
            }
            ExoPlaybackException createForUnexpected = ExoPlaybackException.createForUnexpected(e8, i);
            Log.e("ExoPlayerImplInternal", "Playback error", createForUnexpected);
            a(true, false);
            this.w = this.w.a(createForUnexpected);
        }
        e();
        return true;
    }

    private void a(IOException iOException, int i) {
        ExoPlaybackException createForSource = ExoPlaybackException.createForSource(iOException, i);
        b c2 = this.r.c();
        if (c2 != null) {
            createForSource = createForSource.a(c2.f.a);
        }
        Log.e("ExoPlayerImplInternal", "Playback error", createForSource);
        a(false, false);
        this.w = this.w.a(createForSource);
    }

    private synchronized void a(Supplier<Boolean> supplier, long j) {
        long elapsedRealtime = this.p.elapsedRealtime() + j;
        boolean z = false;
        while (!supplier.get().booleanValue() && j > 0) {
            try {
                this.p.onThreadBlocked();
                wait(j);
            } catch (InterruptedException unused) {
                z = true;
            }
            j = elapsedRealtime - this.p.elapsedRealtime();
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    private void b(int i) {
        if (this.w.e != i) {
            this.w = this.w.a(i);
        }
    }

    private void e() {
        this.x.setPlaybackInfo(this.w);
        if (this.x.a) {
            this.q.onPlaybackInfoUpdate(this.x);
            this.x = new PlaybackInfoUpdate(this.w);
        }
    }

    private void f() {
        this.x.incrementPendingOperationAcks(1);
        a(false, false, false, true);
        this.e.onPrepared();
        b(this.w.a.isEmpty() ? 4 : 2);
        this.s.a(this.f.getTransferListener());
        this.g.sendEmptyMessage(2);
    }

    private void a(a aVar) throws ExoPlaybackException {
        this.x.incrementPendingOperationAcks(1);
        if (aVar.c != -1) {
            this.J = new e(new h(aVar.a, aVar.b), aVar.c, aVar.d);
        }
        a(this.s.a(aVar.a, aVar.b), false);
    }

    private void a(a aVar, int i) throws ExoPlaybackException {
        this.x.incrementPendingOperationAcks(1);
        MediaSourceList mediaSourceList = this.s;
        if (i == -1) {
            i = mediaSourceList.b();
        }
        a(mediaSourceList.a(i, aVar.a, aVar.b), false);
    }

    private void a(b bVar) throws ExoPlaybackException {
        this.x.incrementPendingOperationAcks(1);
        a(this.s.a(bVar.a, bVar.b, bVar.c, bVar.d), false);
    }

    private void b(int i, int i2, ShuffleOrder shuffleOrder) throws ExoPlaybackException {
        this.x.incrementPendingOperationAcks(1);
        a(this.s.a(i, i2, shuffleOrder), false);
    }

    private void g() throws ExoPlaybackException {
        a(this.s.d(), true);
    }

    private void b(ShuffleOrder shuffleOrder) throws ExoPlaybackException {
        this.x.incrementPendingOperationAcks(1);
        a(this.s.a(shuffleOrder), false);
    }

    private void e(boolean z) {
        for (b c2 = this.r.c(); c2 != null; c2 = c2.g()) {
            ExoTrackSelection[] exoTrackSelectionArr = c2.i().selections;
            for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlayWhenReadyChanged(z);
                }
            }
        }
    }

    private void a(boolean z, int i, boolean z2, int i2) throws ExoPlaybackException {
        this.x.incrementPendingOperationAcks(z2 ? 1 : 0);
        this.x.setPlayWhenReadyChangeReason(i2);
        this.w = this.w.a(z, i);
        this.B = false;
        e(z);
        if (!I()) {
            i();
            k();
        } else if (this.w.e == 3) {
            h();
            this.g.sendEmptyMessage(2);
        } else if (this.w.e == 2) {
            this.g.sendEmptyMessage(2);
        }
    }

    private void f(boolean z) throws ExoPlaybackException {
        this.z = z;
        z();
        if (this.A && this.r.d() != this.r.c()) {
            i(true);
            k(false);
        }
    }

    private void g(boolean z) {
        if (z != this.H) {
            this.H = z;
            int i = this.w.e;
            if (z || i == 4 || i == 1) {
                this.w = this.w.b(z);
            } else {
                this.g.sendEmptyMessage(2);
            }
        }
    }

    private void c(int i) throws ExoPlaybackException {
        this.D = i;
        if (!this.r.a(this.w.a, i)) {
            i(true);
        }
        k(false);
    }

    private void h(boolean z) throws ExoPlaybackException {
        this.E = z;
        if (!this.r.a(this.w.a, z)) {
            i(true);
        }
        k(false);
    }

    private void i(boolean z) throws ExoPlaybackException {
        MediaSource.MediaPeriodId mediaPeriodId = this.r.c().f.a;
        long a2 = a(mediaPeriodId, this.w.s, true, false);
        if (a2 != this.w.s) {
            this.w = a(mediaPeriodId, a2, this.w.c, this.w.d, z, 5);
        }
    }

    private void h() throws ExoPlaybackException {
        this.B = false;
        this.n.a();
        Renderer[] rendererArr = this.a;
        for (Renderer renderer : rendererArr) {
            if (c(renderer)) {
                renderer.start();
            }
        }
    }

    private void i() throws ExoPlaybackException {
        this.n.b();
        Renderer[] rendererArr = this.a;
        for (Renderer renderer : rendererArr) {
            if (c(renderer)) {
                a(renderer);
            }
        }
    }

    private void j() throws ExoPlaybackException {
        i(true);
    }

    private void k() throws ExoPlaybackException {
        b c2 = this.r.c();
        if (c2 != null) {
            long readDiscontinuity = c2.d ? c2.a.readDiscontinuity() : -9223372036854775807L;
            if (readDiscontinuity != C.TIME_UNSET) {
                b(readDiscontinuity);
                if (readDiscontinuity != this.w.s) {
                    this.w = a(this.w.b, readDiscontinuity, this.w.c, readDiscontinuity, true, 5);
                }
            } else {
                this.K = this.n.a(c2 != this.r.d());
                long b2 = c2.b(this.K);
                c(this.w.s, b2);
                this.w.s = b2;
            }
            this.w.q = this.r.b().d();
            this.w.r = H();
            if (this.w.l && this.w.e == 3 && a(this.w.a, this.w.b) && this.w.n.speed == 1.0f) {
                float adjustedPlaybackSpeed = this.t.getAdjustedPlaybackSpeed(n(), H());
                if (this.n.getPlaybackParameters().speed != adjustedPlaybackSpeed) {
                    this.n.setPlaybackParameters(this.w.n.withSpeed(adjustedPlaybackSpeed));
                    a(this.w.n, this.n.getPlaybackParameters().speed, false, false);
                }
            }
        }
    }

    private void l() {
        for (b c2 = this.r.c(); c2 != null; c2 = c2.g()) {
            ExoTrackSelection[] exoTrackSelectionArr = c2.i().selections;
            for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onRebuffer();
                }
            }
        }
    }

    private void m() throws ExoPlaybackException, IOException {
        boolean z;
        boolean z2;
        boolean z3;
        long uptimeMillis = this.p.uptimeMillis();
        t();
        if (this.w.e == 1 || this.w.e == 4) {
            this.g.removeMessages(2);
            return;
        }
        b c2 = this.r.c();
        if (c2 == null) {
            a(uptimeMillis, 10L);
            return;
        }
        TraceUtil.beginSection("doSomeWork");
        k();
        if (c2.d) {
            long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
            c2.a.discardBuffer(this.w.s - this.l, this.m);
            z2 = true;
            z = true;
            int i = 0;
            while (true) {
                Renderer[] rendererArr = this.a;
                if (i >= rendererArr.length) {
                    break;
                }
                Renderer renderer = rendererArr[i];
                if (c(renderer)) {
                    renderer.render(this.K, elapsedRealtime);
                    z2 = z2 && renderer.isEnded();
                    boolean z4 = c2.c[i] != renderer.getStream();
                    boolean z5 = z4 || (!z4 && renderer.hasReadStreamToEnd()) || renderer.isReady() || renderer.isEnded();
                    z = z && z5;
                    if (!z5) {
                        renderer.maybeThrowStreamError();
                    }
                }
                i++;
            }
        } else {
            c2.a.maybeThrowPrepareError();
            z2 = true;
            z = true;
        }
        long j = c2.f.e;
        boolean z6 = z2 && c2.d && (j == C.TIME_UNSET || j <= this.w.s);
        if (z6 && this.A) {
            this.A = false;
            a(false, this.w.m, false, 5);
        }
        if (z6 && c2.f.i) {
            b(4);
            i();
        } else if (this.w.e == 2 && j(z)) {
            b(3);
            this.N = null;
            if (I()) {
                h();
            }
        } else if (this.w.e == 3 && (this.I != 0 ? !z : !r())) {
            this.B = I();
            b(2);
            if (this.B) {
                l();
                this.t.notifyRebuffer();
            }
            i();
        }
        if (this.w.e == 2) {
            int i2 = 0;
            while (true) {
                Renderer[] rendererArr2 = this.a;
                if (i2 >= rendererArr2.length) {
                    break;
                }
                if (c(rendererArr2[i2]) && this.a[i2].getStream() == c2.c[i2]) {
                    this.a[i2].maybeThrowStreamError();
                }
                i2++;
            }
            if (!this.w.g && this.w.r < 500000 && E()) {
                throw new IllegalStateException("Playback stuck buffering and not loading");
            }
        }
        if (this.H != this.w.o) {
            this.w = this.w.b(this.H);
        }
        if ((!I() || this.w.e != 3) && this.w.e != 2) {
            if (this.I == 0 || this.w.e == 4) {
                this.g.removeMessages(2);
            } else {
                a(uptimeMillis, 1000L);
            }
            z3 = false;
        } else {
            z3 = !b(uptimeMillis, 10L);
        }
        if (this.w.p != z3) {
            this.w = this.w.c(z3);
        }
        this.G = false;
        TraceUtil.endSection();
    }

    private long n() {
        return a(this.w.a, this.w.b.periodUid, this.w.s);
    }

    private long a(Timeline timeline, Object obj, long j) {
        timeline.getWindow(timeline.getPeriodByUid(obj, this.k).windowIndex, this.j);
        return (this.j.windowStartTimeMs == C.TIME_UNSET || !this.j.isLive() || !this.j.isDynamic) ? C.TIME_UNSET : C.msToUs(this.j.getCurrentUnixTimeMs() - this.j.windowStartTimeMs) - (j + this.k.getPositionInWindowUs());
    }

    private boolean a(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (mediaPeriodId.isAd() || timeline.isEmpty()) {
            return false;
        }
        timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.k).windowIndex, this.j);
        return this.j.isLive() && this.j.isDynamic && this.j.windowStartTimeMs != C.TIME_UNSET;
    }

    private void a(long j, long j2) {
        this.g.removeMessages(2);
        this.g.sendEmptyMessageAtTime(2, j + j2);
    }

    private boolean b(long j, long j2) {
        if (this.H && this.G) {
            return false;
        }
        a(j, j2);
        return true;
    }

    private void a(e eVar) throws ExoPlaybackException {
        long j;
        boolean z;
        MediaSource.MediaPeriodId mediaPeriodId;
        long j2;
        long j3;
        Throwable th;
        long j4;
        long j5;
        this.x.incrementPendingOperationAcks(1);
        Pair<Object, Long> a2 = a(this.w.a, eVar, true, this.D, this.E, this.j, this.k);
        if (a2 == null) {
            Pair<MediaSource.MediaPeriodId, Long> a3 = a(this.w.a);
            mediaPeriodId = (MediaSource.MediaPeriodId) a3.first;
            j2 = ((Long) a3.second).longValue();
            z = !this.w.a.isEmpty();
            j = -9223372036854775807L;
        } else {
            Object obj = a2.first;
            long longValue = ((Long) a2.second).longValue();
            j = eVar.c == C.TIME_UNSET ? -9223372036854775807L : longValue;
            MediaSource.MediaPeriodId a4 = this.r.a(this.w.a, obj, longValue);
            if (a4.isAd()) {
                this.w.a.getPeriodByUid(a4.periodUid, this.k);
                j2 = this.k.getFirstAdIndexToPlay(a4.adGroupIndex) == a4.adIndexInAdGroup ? this.k.getAdResumePositionUs() : 0L;
                mediaPeriodId = a4;
                z = true;
            } else {
                z = eVar.c == C.TIME_UNSET;
                mediaPeriodId = a4;
                j2 = longValue;
            }
        }
        try {
            if (this.w.a.isEmpty()) {
                this.J = eVar;
            } else if (a2 == null) {
                if (this.w.e != 1) {
                    b(4);
                }
                a(false, true, false, true);
            } else {
                if (mediaPeriodId.equals(this.w.b)) {
                    b c2 = this.r.c();
                    j5 = (c2 == null || !c2.d || j2 == 0) ? j2 : c2.a.getAdjustedSeekPositionUs(j2, this.v);
                    if (C.usToMs(j5) == C.usToMs(this.w.s) && (this.w.e == 2 || this.w.e == 3)) {
                        long j6 = this.w.s;
                        this.w = a(mediaPeriodId, j6, j, j6, z, 2);
                        return;
                    }
                } else {
                    j5 = j2;
                }
                long a5 = a(mediaPeriodId, j5, this.w.e == 4);
                boolean z2 = z | (j2 != a5);
                try {
                    a(this.w.a, mediaPeriodId, this.w.a, this.w.b, j);
                    z = z2;
                    j4 = a5;
                    this.w = a(mediaPeriodId, j4, j, j4, z, 2);
                } catch (Throwable th2) {
                    th = th2;
                    z = z2;
                    j3 = a5;
                    this.w = a(mediaPeriodId, j3, j, j3, z, 2);
                    throw th;
                }
            }
            j4 = j2;
            this.w = a(mediaPeriodId, j4, j, j4, z, 2);
        } catch (Throwable th3) {
            th = th3;
            j3 = j2;
        }
    }

    private long a(MediaSource.MediaPeriodId mediaPeriodId, long j, boolean z) throws ExoPlaybackException {
        return a(mediaPeriodId, j, this.r.c() != this.r.d(), z);
    }

    private long a(MediaSource.MediaPeriodId mediaPeriodId, long j, boolean z, boolean z2) throws ExoPlaybackException {
        i();
        this.B = false;
        if (z2 || this.w.e == 3) {
            b(2);
        }
        b c2 = this.r.c();
        b bVar = c2;
        while (bVar != null && !mediaPeriodId.equals(bVar.f.a)) {
            bVar = bVar.g();
        }
        if (z || c2 != bVar || (bVar != null && bVar.a(j) < 0)) {
            for (Renderer renderer : this.a) {
                b(renderer);
            }
            if (bVar != null) {
                while (this.r.c() != bVar) {
                    this.r.f();
                }
                this.r.a(bVar);
                bVar.c(0L);
                G();
            }
        }
        if (bVar != null) {
            this.r.a(bVar);
            if (!bVar.d) {
                bVar.f = bVar.f.a(j);
            } else if (bVar.e) {
                long seekToUs = bVar.a.seekToUs(j);
                bVar.a.discardBuffer(seekToUs - this.l, this.m);
                j = seekToUs;
            }
            b(j);
            C();
        } else {
            this.r.g();
            b(j);
        }
        k(false);
        this.g.sendEmptyMessage(2);
        return j;
    }

    private void b(long j) throws ExoPlaybackException {
        b c2 = this.r.c();
        if (c2 != null) {
            j = c2.a(j);
        }
        this.K = j;
        this.n.a(this.K);
        Renderer[] rendererArr = this.a;
        for (Renderer renderer : rendererArr) {
            if (c(renderer)) {
                renderer.resetPosition(this.K);
            }
        }
        q();
    }

    private void b(PlaybackParameters playbackParameters) throws ExoPlaybackException {
        this.n.setPlaybackParameters(playbackParameters);
        a(this.n.getPlaybackParameters(), true);
    }

    private void b(SeekParameters seekParameters) {
        this.v = seekParameters;
    }

    private void a(boolean z, @Nullable AtomicBoolean atomicBoolean) {
        if (this.F != z) {
            this.F = z;
            if (!z) {
                Renderer[] rendererArr = this.a;
                for (Renderer renderer : rendererArr) {
                    if (!c(renderer)) {
                        renderer.reset();
                    }
                }
            }
        }
        if (atomicBoolean != null) {
            synchronized (this) {
                atomicBoolean.set(true);
                notifyAll();
            }
        }
    }

    private void a(boolean z, boolean z2) {
        a(z || !this.F, false, true, false);
        this.x.incrementPendingOperationAcks(z2 ? 1 : 0);
        this.e.onStopped();
        b(1);
    }

    private void o() {
        a(true, false, true, false);
        this.e.onReleased();
        b(1);
        this.h.quit();
        synchronized (this) {
            this.y = true;
            notifyAll();
        }
    }

    private void a(boolean z, boolean z2, boolean z3, boolean z4) {
        long j;
        boolean z5;
        TrackGroupArray trackGroupArray;
        TrackSelectorResult trackSelectorResult;
        List list;
        this.g.removeMessages(2);
        ExoPlaybackException exoPlaybackException = null;
        this.N = null;
        this.B = false;
        this.n.b();
        this.K = 0L;
        for (Renderer renderer : this.a) {
            try {
                b(renderer);
            } catch (ExoPlaybackException | RuntimeException e2) {
                Log.e("ExoPlayerImplInternal", "Disable failed.", e2);
            }
        }
        if (z) {
            for (Renderer renderer2 : this.a) {
                try {
                    renderer2.reset();
                } catch (RuntimeException e3) {
                    Log.e("ExoPlayerImplInternal", "Reset failed.", e3);
                }
            }
        }
        this.I = 0;
        MediaSource.MediaPeriodId mediaPeriodId = this.w.b;
        long j2 = this.w.s;
        if (this.w.b.isAd() || a(this.w, this.k)) {
            j = this.w.c;
        } else {
            j = this.w.s;
        }
        if (z2) {
            this.J = null;
            Pair<MediaSource.MediaPeriodId, Long> a2 = a(this.w.a);
            MediaSource.MediaPeriodId mediaPeriodId2 = (MediaSource.MediaPeriodId) a2.first;
            long longValue = ((Long) a2.second).longValue();
            if (!mediaPeriodId2.equals(this.w.b)) {
                z5 = true;
                mediaPeriodId = mediaPeriodId2;
                j2 = longValue;
                j = -9223372036854775807L;
            } else {
                z5 = false;
                mediaPeriodId = mediaPeriodId2;
                j2 = longValue;
                j = -9223372036854775807L;
            }
        } else {
            z5 = false;
        }
        this.r.g();
        this.C = false;
        Timeline timeline = this.w.a;
        int i = this.w.e;
        if (!z4) {
            exoPlaybackException = this.w.f;
        }
        if (z5) {
            trackGroupArray = TrackGroupArray.EMPTY;
        } else {
            trackGroupArray = this.w.h;
        }
        if (z5) {
            trackSelectorResult = this.d;
        } else {
            trackSelectorResult = this.w.i;
        }
        if (z5) {
            list = ImmutableList.of();
        } else {
            list = this.w.j;
        }
        this.w = new g(timeline, mediaPeriodId, j, j2, i, exoPlaybackException, false, trackGroupArray, trackSelectorResult, list, mediaPeriodId, this.w.l, this.w.m, this.w.n, j2, 0L, j2, this.H, false);
        if (z3) {
            this.s.c();
        }
    }

    private Pair<MediaSource.MediaPeriodId, Long> a(Timeline timeline) {
        if (timeline.isEmpty()) {
            return Pair.create(g.a(), 0L);
        }
        Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.j, this.k, timeline.getFirstWindowIndex(this.E), C.TIME_UNSET);
        MediaSource.MediaPeriodId a2 = this.r.a(timeline, periodPosition.first, 0L);
        long longValue = ((Long) periodPosition.second).longValue();
        if (a2.isAd()) {
            timeline.getPeriodByUid(a2.periodUid, this.k);
            longValue = a2.adIndexInAdGroup == this.k.getFirstAdIndexToPlay(a2.adGroupIndex) ? this.k.getAdResumePositionUs() : 0L;
        }
        return Pair.create(a2, Long.valueOf(longValue));
    }

    private void a(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (playerMessage.getPositionMs() == C.TIME_UNSET) {
            b(playerMessage);
        } else if (this.w.a.isEmpty()) {
            this.o.add(new c(playerMessage));
        } else {
            c cVar = new c(playerMessage);
            if (a(cVar, this.w.a, this.w.a, this.D, this.E, this.j, this.k)) {
                this.o.add(cVar);
                Collections.sort(this.o);
                return;
            }
            playerMessage.markAsProcessed(false);
        }
    }

    private void b(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (playerMessage.getLooper() == this.i) {
            d(playerMessage);
            if (this.w.e == 3 || this.w.e == 2) {
                this.g.sendEmptyMessage(2);
                return;
            }
            return;
        }
        this.g.obtainMessage(15, playerMessage).sendToTarget();
    }

    private void c(final PlayerMessage playerMessage) {
        Looper looper = playerMessage.getLooper();
        if (!looper.getThread().isAlive()) {
            Log.w("TAG", "Trying to send message on a dead thread.");
            playerMessage.markAsProcessed(false);
            return;
        }
        this.p.createHandler(looper, null).post(new Runnable() { // from class: com.google.android.exoplayer2.-$$Lambda$ExoPlayerImplInternal$bUbYZq_V6NrvpolsV3XG31MPLcQ
            @Override // java.lang.Runnable
            public final void run() {
                ExoPlayerImplInternal.this.e(playerMessage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(PlayerMessage playerMessage) {
        try {
            d(playerMessage);
        } catch (ExoPlaybackException e2) {
            Log.e("ExoPlayerImplInternal", "Unexpected error delivering message on external thread.", e2);
            throw new RuntimeException(e2);
        }
    }

    private void d(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (!playerMessage.isCanceled()) {
            try {
                playerMessage.getTarget().handleMessage(playerMessage.getType(), playerMessage.getPayload());
            } finally {
                playerMessage.markAsProcessed(true);
            }
        }
    }

    private void a(Timeline timeline, Timeline timeline2) {
        if (!timeline.isEmpty() || !timeline2.isEmpty()) {
            for (int size = this.o.size() - 1; size >= 0; size--) {
                if (!a(this.o.get(size), timeline, timeline2, this.D, this.E, this.j, this.k)) {
                    this.o.get(size).a.markAsProcessed(false);
                    this.o.remove(size);
                }
            }
            Collections.sort(this.o);
        }
    }

    private void c(long j, long j2) throws ExoPlaybackException {
        if (!this.o.isEmpty() && !this.w.b.isAd()) {
            if (this.M) {
                j--;
                this.M = false;
            }
            int indexOfPeriod = this.w.a.getIndexOfPeriod(this.w.b.periodUid);
            int min = Math.min(this.L, this.o.size());
            c cVar = min > 0 ? this.o.get(min - 1) : null;
            while (cVar != null && (cVar.b > indexOfPeriod || (cVar.b == indexOfPeriod && cVar.c > j))) {
                min--;
                cVar = min > 0 ? this.o.get(min - 1) : null;
            }
            c cVar2 = min < this.o.size() ? this.o.get(min) : null;
            while (cVar2 != null && cVar2.d != null && (cVar2.b < indexOfPeriod || (cVar2.b == indexOfPeriod && cVar2.c <= j))) {
                min++;
                cVar2 = min < this.o.size() ? this.o.get(min) : null;
            }
            while (cVar2 != null && cVar2.d != null && cVar2.b == indexOfPeriod && cVar2.c > j && cVar2.c <= j2) {
                try {
                    b(cVar2.a);
                    if (cVar2.a.getDeleteAfterDelivery() || cVar2.a.isCanceled()) {
                        this.o.remove(min);
                    } else {
                        min++;
                    }
                    cVar2 = min < this.o.size() ? this.o.get(min) : null;
                } catch (Throwable th) {
                    if (cVar2.a.getDeleteAfterDelivery() || cVar2.a.isCanceled()) {
                        this.o.remove(min);
                    }
                    throw th;
                }
            }
            this.L = min;
        }
    }

    private void a(Renderer renderer) throws ExoPlaybackException {
        if (renderer.getState() == 2) {
            renderer.stop();
        }
    }

    private void b(Renderer renderer) throws ExoPlaybackException {
        if (c(renderer)) {
            this.n.b(renderer);
            a(renderer);
            renderer.disable();
            this.I--;
        }
    }

    private void p() throws ExoPlaybackException {
        boolean z;
        float f = this.n.getPlaybackParameters().speed;
        b d2 = this.r.d();
        boolean z2 = true;
        for (b c2 = this.r.c(); c2 != null && c2.d; c2 = c2.g()) {
            TrackSelectorResult b2 = c2.b(f, this.w.a);
            if (!b2.isEquivalent(c2.i())) {
                if (z2) {
                    b c3 = this.r.c();
                    boolean a2 = this.r.a(c3);
                    boolean[] zArr = new boolean[this.a.length];
                    long a3 = c3.a(b2, this.w.s, a2, zArr);
                    boolean z3 = (this.w.e == 4 || a3 == this.w.s) ? false : true;
                    this.w = a(this.w.b, a3, this.w.c, this.w.d, z3, 5);
                    if (z3) {
                        b(a3);
                    }
                    boolean[] zArr2 = new boolean[this.a.length];
                    int i = 0;
                    while (true) {
                        Renderer[] rendererArr = this.a;
                        if (i >= rendererArr.length) {
                            break;
                        }
                        Renderer renderer = rendererArr[i];
                        zArr2[i] = c(renderer);
                        SampleStream sampleStream = c3.c[i];
                        if (zArr2[i]) {
                            if (sampleStream != renderer.getStream()) {
                                b(renderer);
                            } else if (zArr[i]) {
                                renderer.resetPosition(this.K);
                            }
                        }
                        i++;
                    }
                    a(zArr2);
                    z = true;
                } else {
                    this.r.a(c2);
                    if (c2.d) {
                        c2.a(b2, Math.max(c2.f.b, c2.b(this.K)), false);
                        z = true;
                    } else {
                        z = true;
                    }
                }
                k(z);
                if (this.w.e != 4) {
                    C();
                    k();
                    this.g.sendEmptyMessage(2);
                    return;
                }
                return;
            }
            if (c2 == d2) {
                z2 = false;
            }
        }
    }

    private void a(float f) {
        for (b c2 = this.r.c(); c2 != null; c2 = c2.g()) {
            ExoTrackSelection[] exoTrackSelectionArr = c2.i().selections;
            for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlaybackSpeed(f);
                }
            }
        }
    }

    private void q() {
        for (b c2 = this.r.c(); c2 != null; c2 = c2.g()) {
            ExoTrackSelection[] exoTrackSelectionArr = c2.i().selections;
            for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onDiscontinuity();
                }
            }
        }
    }

    private boolean j(boolean z) {
        if (this.I == 0) {
            return r();
        }
        if (!z) {
            return false;
        }
        if (!this.w.g) {
            return true;
        }
        long targetLiveOffsetUs = a(this.w.a, this.r.c().f.a) ? this.t.getTargetLiveOffsetUs() : C.TIME_UNSET;
        b b2 = this.r.b();
        return (b2.c() && b2.f.i) || (b2.f.a.isAd() && !b2.d) || this.e.shouldStartPlayback(H(), this.n.getPlaybackParameters().speed, this.B, targetLiveOffsetUs);
    }

    private boolean r() {
        b c2 = this.r.c();
        long j = c2.f.e;
        return c2.d && (j == C.TIME_UNSET || this.w.s < j || !I());
    }

    private void a(Timeline timeline, boolean z) throws ExoPlaybackException {
        Throwable th;
        boolean z2;
        boolean z3;
        d a2 = a(timeline, this.w, this.J, this.r, this.D, this.E, this.j, this.k);
        MediaSource.MediaPeriodId mediaPeriodId = a2.a;
        long j = a2.c;
        boolean z4 = a2.d;
        long j2 = a2.b;
        boolean z5 = !this.w.b.equals(mediaPeriodId) || j2 != this.w.s;
        int i = 3;
        e eVar = null;
        long j3 = C.TIME_UNSET;
        try {
            if (a2.e) {
                if (this.w.e != 1) {
                    b(4);
                }
                a(false, false, false, true);
            }
            try {
                if (!z5) {
                    try {
                        i = 4;
                        z3 = false;
                        if (!this.r.a(timeline, this.K, s())) {
                            i(false);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        i = 4;
                        Timeline timeline2 = this.w.a;
                        MediaSource.MediaPeriodId mediaPeriodId2 = this.w.b;
                        if (a2.f) {
                            j3 = j2;
                        }
                        a(timeline, mediaPeriodId, timeline2, mediaPeriodId2, j3);
                        if (z5 || j != this.w.c) {
                            Object obj = this.w.b.periodUid;
                            Timeline timeline3 = this.w.a;
                            boolean z6 = z5 && z && !timeline3.isEmpty() && !timeline3.getPeriodByUid(obj, this.k).isPlaceholder;
                            long j4 = this.w.d;
                            if (timeline.getIndexOfPeriod(obj) == -1) {
                            }
                            this.w = a(mediaPeriodId, j2, j, j4, z6, i);
                        }
                        z();
                        a(timeline, this.w.a);
                        this.w = this.w.a(timeline);
                        if (!timeline.isEmpty()) {
                            this.J = eVar;
                            z2 = false;
                        } else {
                            z2 = false;
                        }
                        k(z2);
                        throw th;
                    }
                } else {
                    i = 4;
                    z3 = false;
                    if (!timeline.isEmpty()) {
                        for (b c2 = this.r.c(); c2 != null; c2 = c2.g()) {
                            if (c2.f.a.equals(mediaPeriodId)) {
                                c2.f = this.r.a(timeline, c2.f);
                                c2.j();
                            }
                        }
                        j2 = a(mediaPeriodId, j2, z4);
                    }
                }
                a(timeline, mediaPeriodId, this.w.a, this.w.b, a2.f ? j2 : -9223372036854775807L);
                if (z5 || j != this.w.c) {
                    Object obj2 = this.w.b.periodUid;
                    Timeline timeline4 = this.w.a;
                    boolean z7 = (!z5 || !z || timeline4.isEmpty() || timeline4.getPeriodByUid(obj2, this.k).isPlaceholder) ? z3 : true;
                    long j5 = this.w.d;
                    if (timeline.getIndexOfPeriod(obj2) == -1) {
                    }
                    this.w = a(mediaPeriodId, j2, j, j5, z7, i);
                }
                z();
                a(timeline, this.w.a);
                this.w = this.w.a(timeline);
                if (!timeline.isEmpty()) {
                    this.J = null;
                }
                k(z3);
            } catch (Throwable th3) {
                th = th3;
                eVar = null;
            }
        } catch (Throwable th4) {
            th = th4;
            i = 4;
        }
    }

    private void a(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline2, MediaSource.MediaPeriodId mediaPeriodId2, long j) {
        if (!timeline.isEmpty() && a(timeline, mediaPeriodId)) {
            timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.k).windowIndex, this.j);
            this.t.setLiveConfiguration((MediaItem.LiveConfiguration) Util.castNonNull(this.j.liveConfiguration));
            if (j != C.TIME_UNSET) {
                this.t.setTargetLiveOffsetOverrideUs(a(timeline, mediaPeriodId.periodUid, j));
                return;
            }
            Object obj = this.j.uid;
            Object obj2 = null;
            if (!timeline2.isEmpty()) {
                obj2 = timeline2.getWindow(timeline2.getPeriodByUid(mediaPeriodId2.periodUid, this.k).windowIndex, this.j).uid;
            }
            if (!Util.areEqual(obj2, obj)) {
                this.t.setTargetLiveOffsetOverrideUs(C.TIME_UNSET);
            }
        } else if (this.n.getPlaybackParameters().speed != this.w.n.speed) {
            this.n.setPlaybackParameters(this.w.n);
        }
    }

    private long s() {
        b d2 = this.r.d();
        if (d2 == null) {
            return 0L;
        }
        long a2 = d2.a();
        if (!d2.d) {
            return a2;
        }
        int i = 0;
        while (true) {
            Renderer[] rendererArr = this.a;
            if (i >= rendererArr.length) {
                return a2;
            }
            if (c(rendererArr[i]) && this.a[i].getStream() == d2.c[i]) {
                long readingPositionUs = this.a[i].getReadingPositionUs();
                if (readingPositionUs == Long.MIN_VALUE) {
                    return Long.MIN_VALUE;
                }
                a2 = Math.max(readingPositionUs, a2);
            }
            i++;
        }
    }

    private void t() throws ExoPlaybackException, IOException {
        if (!this.w.a.isEmpty() && this.s.a()) {
            u();
            v();
            w();
            y();
        }
    }

    private void u() throws ExoPlaybackException {
        d a2;
        this.r.a(this.K);
        if (this.r.a() && (a2 = this.r.a(this.K, this.w)) != null) {
            b a3 = this.r.a(this.b, this.c, this.e.getAllocator(), this.s, a2, this.d);
            a3.a.prepare(this, a2.b);
            if (this.r.c() == a3) {
                b(a3.b());
            }
            k(false);
        }
        if (this.C) {
            this.C = E();
            F();
            return;
        }
        C();
    }

    private void v() {
        b d2 = this.r.d();
        if (d2 != null) {
            int i = 0;
            if (d2.g() == null || this.A) {
                if (d2.f.i || this.A) {
                    while (true) {
                        Renderer[] rendererArr = this.a;
                        if (i < rendererArr.length) {
                            Renderer renderer = rendererArr[i];
                            SampleStream sampleStream = d2.c[i];
                            if (sampleStream != null && renderer.getStream() == sampleStream && renderer.hasReadStreamToEnd()) {
                                a(renderer, (d2.f.e == C.TIME_UNSET || d2.f.e == Long.MIN_VALUE) ? -9223372036854775807L : d2.a() + d2.f.e);
                            }
                            i++;
                        } else {
                            return;
                        }
                    }
                }
            } else if (B()) {
                if (d2.g().d || this.K >= d2.g().b()) {
                    TrackSelectorResult i2 = d2.i();
                    b e2 = this.r.e();
                    TrackSelectorResult i3 = e2.i();
                    if (!e2.d || e2.a.readDiscontinuity() == C.TIME_UNSET) {
                        for (int i4 = 0; i4 < this.a.length; i4++) {
                            boolean isRendererEnabled = i2.isRendererEnabled(i4);
                            boolean isRendererEnabled2 = i3.isRendererEnabled(i4);
                            if (isRendererEnabled && !this.a[i4].isCurrentStreamFinal()) {
                                boolean z = this.b[i4].getTrackType() == 7;
                                RendererConfiguration rendererConfiguration = i2.rendererConfigurations[i4];
                                RendererConfiguration rendererConfiguration2 = i3.rendererConfigurations[i4];
                                if (!isRendererEnabled2 || !rendererConfiguration2.equals(rendererConfiguration) || z) {
                                    a(this.a[i4], e2.b());
                                }
                            }
                        }
                        return;
                    }
                    c(e2.b());
                }
            }
        }
    }

    private void w() throws ExoPlaybackException {
        b d2 = this.r.d();
        if (d2 != null && this.r.c() != d2 && !d2.g && x()) {
            G();
        }
    }

    private boolean x() throws ExoPlaybackException {
        b d2 = this.r.d();
        TrackSelectorResult i = d2.i();
        int i2 = 0;
        boolean z = false;
        while (true) {
            Renderer[] rendererArr = this.a;
            if (i2 >= rendererArr.length) {
                return !z;
            }
            Renderer renderer = rendererArr[i2];
            if (c(renderer)) {
                boolean z2 = renderer.getStream() != d2.c[i2];
                if (!i.isRendererEnabled(i2) || z2) {
                    if (!renderer.isCurrentStreamFinal()) {
                        renderer.replaceStream(a(i.selections[i2]), d2.c[i2], d2.b(), d2.a());
                    } else if (renderer.isEnded()) {
                        b(renderer);
                    } else {
                        z = true;
                    }
                }
            }
            i2++;
        }
    }

    private void y() throws ExoPlaybackException {
        boolean z = false;
        while (A()) {
            if (z) {
                e();
            }
            b c2 = this.r.c();
            b f = this.r.f();
            this.w = a(f.f.a, f.f.b, f.f.c, f.f.b, true, 0);
            a(this.w.a, f.f.a, this.w.a, c2.f.a, C.TIME_UNSET);
            z();
            k();
            z = true;
        }
    }

    private void z() {
        b c2 = this.r.c();
        this.A = c2 != null && c2.f.h && this.z;
    }

    private boolean A() {
        b c2;
        b g;
        return I() && !this.A && (c2 = this.r.c()) != null && (g = c2.g()) != null && this.K >= g.b() && g.g;
    }

    private boolean B() {
        b d2 = this.r.d();
        if (!d2.d) {
            return false;
        }
        int i = 0;
        while (true) {
            Renderer[] rendererArr = this.a;
            if (i >= rendererArr.length) {
                return true;
            }
            Renderer renderer = rendererArr[i];
            SampleStream sampleStream = d2.c[i];
            if (renderer.getStream() != sampleStream || (sampleStream != null && !renderer.hasReadStreamToEnd() && !a(renderer, d2))) {
                break;
            }
            i++;
        }
        return false;
    }

    private boolean a(Renderer renderer, b bVar) {
        b g = bVar.g();
        return bVar.f.f && g.d && ((renderer instanceof TextRenderer) || renderer.getReadingPositionUs() >= g.b());
    }

    private void c(long j) {
        Renderer[] rendererArr = this.a;
        for (Renderer renderer : rendererArr) {
            if (renderer.getStream() != null) {
                a(renderer, j);
            }
        }
    }

    private void a(Renderer renderer, long j) {
        renderer.setCurrentStreamFinal();
        if (renderer instanceof TextRenderer) {
            ((TextRenderer) renderer).setFinalStreamEndPositionUs(j);
        }
    }

    private void b(MediaPeriod mediaPeriod) throws ExoPlaybackException {
        if (this.r.a(mediaPeriod)) {
            b b2 = this.r.b();
            b2.a(this.n.getPlaybackParameters().speed, this.w.a);
            a(b2.h(), b2.i());
            if (b2 == this.r.c()) {
                b(b2.f.b);
                G();
                this.w = a(this.w.b, b2.f.b, this.w.c, b2.f.b, false, 5);
            }
            C();
        }
    }

    private void c(MediaPeriod mediaPeriod) {
        if (this.r.a(mediaPeriod)) {
            this.r.a(this.K);
            C();
        }
    }

    private void a(PlaybackParameters playbackParameters, boolean z) throws ExoPlaybackException {
        a(playbackParameters, playbackParameters.speed, true, z);
    }

    private void a(PlaybackParameters playbackParameters, float f, boolean z, boolean z2) throws ExoPlaybackException {
        if (z) {
            if (z2) {
                this.x.incrementPendingOperationAcks(1);
            }
            this.w = this.w.a(playbackParameters);
        }
        a(playbackParameters.speed);
        Renderer[] rendererArr = this.a;
        for (Renderer renderer : rendererArr) {
            if (renderer != null) {
                renderer.setPlaybackSpeed(f, playbackParameters.speed);
            }
        }
    }

    private void C() {
        this.C = D();
        if (this.C) {
            this.r.b().e(this.K);
        }
        F();
    }

    private boolean D() {
        long j;
        if (!E()) {
            return false;
        }
        b b2 = this.r.b();
        long d2 = d(b2.e());
        if (b2 == this.r.c()) {
            j = b2.b(this.K);
        } else {
            j = b2.b(this.K) - b2.f.b;
        }
        return this.e.shouldContinueLoading(j, d2, this.n.getPlaybackParameters().speed);
    }

    private boolean E() {
        b b2 = this.r.b();
        return (b2 == null || b2.e() == Long.MIN_VALUE) ? false : true;
    }

    private void F() {
        b b2 = this.r.b();
        boolean z = this.C || (b2 != null && b2.a.isLoading());
        if (z != this.w.g) {
            this.w = this.w.a(z);
        }
    }

    @CheckResult
    private g a(MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, long j3, boolean z, int i) {
        TrackSelectorResult trackSelectorResult;
        this.M = this.M || j != this.w.s || !mediaPeriodId.equals(this.w.b);
        z();
        TrackGroupArray trackGroupArray = this.w.h;
        TrackSelectorResult trackSelectorResult2 = this.w.i;
        List<Metadata> list = this.w.j;
        if (this.s.a()) {
            b c2 = this.r.c();
            if (c2 == null) {
                trackGroupArray = TrackGroupArray.EMPTY;
            } else {
                trackGroupArray = c2.h();
            }
            if (c2 == null) {
                trackSelectorResult = this.d;
            } else {
                trackSelectorResult = c2.i();
            }
            list = a(trackSelectorResult.selections);
            if (!(c2 == null || c2.f.c == j2)) {
                c2.f = c2.f.b(j2);
            }
            trackSelectorResult2 = trackSelectorResult;
        } else if (!mediaPeriodId.equals(this.w.b)) {
            trackGroupArray = TrackGroupArray.EMPTY;
            trackSelectorResult2 = this.d;
            list = ImmutableList.of();
        }
        if (z) {
            this.x.setPositionDiscontinuity(i);
        }
        return this.w.a(mediaPeriodId, j, j2, j3, H(), trackGroupArray, trackSelectorResult2, list);
    }

    private ImmutableList<Metadata> a(ExoTrackSelection[] exoTrackSelectionArr) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        boolean z = false;
        for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
            if (exoTrackSelection != null) {
                Format format = exoTrackSelection.getFormat(0);
                if (format.metadata == null) {
                    builder.add((ImmutableList.Builder) new Metadata(new Metadata.Entry[0]));
                } else {
                    builder.add((ImmutableList.Builder) format.metadata);
                    z = true;
                }
            }
        }
        return z ? builder.build() : ImmutableList.of();
    }

    private void G() throws ExoPlaybackException {
        a(new boolean[this.a.length]);
    }

    private void a(boolean[] zArr) throws ExoPlaybackException {
        b d2 = this.r.d();
        TrackSelectorResult i = d2.i();
        for (int i2 = 0; i2 < this.a.length; i2++) {
            if (!i.isRendererEnabled(i2)) {
                this.a[i2].reset();
            }
        }
        for (int i3 = 0; i3 < this.a.length; i3++) {
            if (i.isRendererEnabled(i3)) {
                a(i3, zArr[i3]);
            }
        }
        d2.g = true;
    }

    private void a(int i, boolean z) throws ExoPlaybackException {
        Renderer renderer = this.a[i];
        if (!c(renderer)) {
            b d2 = this.r.d();
            boolean z2 = d2 == this.r.c();
            TrackSelectorResult i2 = d2.i();
            RendererConfiguration rendererConfiguration = i2.rendererConfigurations[i];
            Format[] a2 = a(i2.selections[i]);
            boolean z3 = I() && this.w.e == 3;
            boolean z4 = !z && z3;
            this.I++;
            renderer.enable(rendererConfiguration, a2, d2.c[i], this.K, z4, z2, d2.b(), d2.a());
            renderer.handleMessage(103, new Renderer.WakeupListener() { // from class: com.google.android.exoplayer2.ExoPlayerImplInternal.1
                @Override // com.google.android.exoplayer2.Renderer.WakeupListener
                public void onSleep(long j) {
                    if (j >= SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                        ExoPlayerImplInternal.this.G = true;
                    }
                }

                @Override // com.google.android.exoplayer2.Renderer.WakeupListener
                public void onWakeup() {
                    ExoPlayerImplInternal.this.g.sendEmptyMessage(2);
                }
            });
            this.n.a(renderer);
            if (z3) {
                renderer.start();
            }
        }
    }

    private void k(boolean z) {
        long j;
        b b2 = this.r.b();
        MediaSource.MediaPeriodId mediaPeriodId = b2 == null ? this.w.b : b2.f.a;
        boolean z2 = !this.w.k.equals(mediaPeriodId);
        if (z2) {
            this.w = this.w.a(mediaPeriodId);
        }
        g gVar = this.w;
        if (b2 == null) {
            j = gVar.s;
        } else {
            j = b2.d();
        }
        gVar.q = j;
        this.w.r = H();
        if ((z2 || z) && b2 != null && b2.d) {
            a(b2.h(), b2.i());
        }
    }

    private long H() {
        return d(this.w.q);
    }

    private long d(long j) {
        b b2 = this.r.b();
        if (b2 == null) {
            return 0L;
        }
        return Math.max(0L, j - b2.b(this.K));
    }

    private void a(TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult) {
        this.e.onTracksSelected(this.a, trackGroupArray, trackSelectorResult.selections);
    }

    private boolean I() {
        return this.w.l && this.w.m == 0;
    }

    private static d a(Timeline timeline, g gVar, @Nullable e eVar, e eVar2, int i, boolean z, Timeline.Window window, Timeline.Period period) {
        long j;
        boolean z2;
        boolean z3;
        boolean z4;
        int i2;
        MediaSource.MediaPeriodId mediaPeriodId;
        int i3;
        long j2;
        long j3;
        e eVar3;
        long j4;
        if (timeline.isEmpty()) {
            return new d(g.a(), 0L, C.TIME_UNSET, false, true, false);
        }
        MediaSource.MediaPeriodId mediaPeriodId2 = gVar.b;
        Object obj = mediaPeriodId2.periodUid;
        boolean a2 = a(gVar, period);
        if (gVar.b.isAd() || a2) {
            j = gVar.c;
        } else {
            j = gVar.s;
        }
        boolean z5 = false;
        if (eVar != null) {
            i2 = -1;
            Pair<Object, Long> a3 = a(timeline, eVar, true, i, z, window, period);
            if (a3 == null) {
                i3 = timeline.getFirstWindowIndex(z);
                j2 = j;
                z4 = false;
                z2 = false;
                z3 = true;
            } else {
                if (eVar.c == C.TIME_UNSET) {
                    i3 = timeline.getPeriodByUid(a3.first, period).windowIndex;
                    j2 = j;
                    z2 = false;
                } else {
                    obj = a3.first;
                    j2 = ((Long) a3.second).longValue();
                    i3 = -1;
                    z2 = true;
                }
                z4 = gVar.e == 4;
                z3 = false;
            }
            mediaPeriodId = mediaPeriodId2;
        } else {
            i2 = -1;
            if (gVar.a.isEmpty()) {
                i3 = timeline.getFirstWindowIndex(z);
                j2 = j;
                z4 = false;
                z3 = false;
                z2 = false;
                mediaPeriodId = mediaPeriodId2;
            } else if (timeline.getIndexOfPeriod(obj) == -1) {
                Object a4 = a(window, period, i, z, obj, gVar.a, timeline);
                if (a4 == null) {
                    i3 = timeline.getFirstWindowIndex(z);
                    z3 = true;
                } else {
                    i3 = timeline.getPeriodByUid(a4, period).windowIndex;
                    z3 = false;
                }
                j2 = j;
                z4 = false;
                z2 = false;
                mediaPeriodId = mediaPeriodId2;
            } else if (j == C.TIME_UNSET) {
                i3 = timeline.getPeriodByUid(obj, period).windowIndex;
                j2 = j;
                z4 = false;
                z3 = false;
                z2 = false;
                mediaPeriodId = mediaPeriodId2;
            } else if (a2) {
                mediaPeriodId = mediaPeriodId2;
                gVar.a.getPeriodByUid(mediaPeriodId.periodUid, period);
                if (gVar.a.getWindow(period.windowIndex, window).firstPeriodIndex == gVar.a.getIndexOfPeriod(mediaPeriodId.periodUid)) {
                    Pair<Object, Long> periodPosition = timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(obj, period).windowIndex, j + period.getPositionInWindowUs());
                    Object obj2 = periodPosition.first;
                    j2 = ((Long) periodPosition.second).longValue();
                    obj = obj2;
                } else {
                    j2 = j;
                }
                i3 = -1;
                z4 = false;
                z3 = false;
                z2 = true;
            } else {
                mediaPeriodId = mediaPeriodId2;
                i3 = -1;
                j2 = j;
                z4 = false;
                z3 = false;
                z2 = false;
            }
        }
        if (i3 != i2) {
            Pair<Object, Long> periodPosition2 = timeline.getPeriodPosition(window, period, i3, C.TIME_UNSET);
            obj = periodPosition2.first;
            j3 = ((Long) periodPosition2.second).longValue();
            j2 = -9223372036854775807L;
            eVar3 = eVar2;
        } else {
            eVar3 = eVar2;
            j3 = j2;
        }
        MediaSource.MediaPeriodId a5 = eVar3.a(timeline, obj, j3);
        boolean z6 = a5.nextAdGroupIndex == i2 || (mediaPeriodId.nextAdGroupIndex != i2 && a5.adGroupIndex >= mediaPeriodId.nextAdGroupIndex);
        boolean equals = mediaPeriodId.periodUid.equals(obj);
        boolean z7 = equals && !mediaPeriodId.isAd() && !a5.isAd() && z6;
        timeline.getPeriodByUid(obj, period);
        if (equals && !a2 && j == j2 && ((a5.isAd() && period.isServerSideInsertedAdGroup(a5.adGroupIndex)) || (mediaPeriodId.isAd() && period.isServerSideInsertedAdGroup(mediaPeriodId.adGroupIndex)))) {
            z5 = true;
        }
        if (z7 || z5) {
            a5 = mediaPeriodId;
        }
        if (!a5.isAd()) {
            j4 = j3;
        } else if (a5.equals(mediaPeriodId)) {
            j4 = gVar.s;
        } else {
            timeline.getPeriodByUid(a5.periodUid, period);
            j4 = a5.adIndexInAdGroup == period.getFirstAdIndexToPlay(a5.adGroupIndex) ? period.getAdResumePositionUs() : 0L;
        }
        return new d(a5, j4, j2, z4, z3, z2);
    }

    private static boolean a(g gVar, Timeline.Period period) {
        MediaSource.MediaPeriodId mediaPeriodId = gVar.b;
        Timeline timeline = gVar.a;
        return timeline.isEmpty() || timeline.getPeriodByUid(mediaPeriodId.periodUid, period).isPlaceholder;
    }

    private static boolean a(c cVar, Timeline timeline, Timeline timeline2, int i, boolean z, Timeline.Window window, Timeline.Period period) {
        if (cVar.d == null) {
            Pair<Object, Long> a2 = a(timeline, new e(cVar.a.getTimeline(), cVar.a.getWindowIndex(), cVar.a.getPositionMs() == Long.MIN_VALUE ? C.TIME_UNSET : C.msToUs(cVar.a.getPositionMs())), false, i, z, window, period);
            if (a2 == null) {
                return false;
            }
            cVar.a(timeline.getIndexOfPeriod(a2.first), ((Long) a2.second).longValue(), a2.first);
            if (cVar.a.getPositionMs() == Long.MIN_VALUE) {
                a(timeline, cVar, window, period);
            }
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(cVar.d);
        if (indexOfPeriod == -1) {
            return false;
        }
        if (cVar.a.getPositionMs() == Long.MIN_VALUE) {
            a(timeline, cVar, window, period);
            return true;
        }
        cVar.b = indexOfPeriod;
        timeline2.getPeriodByUid(cVar.d, period);
        if (period.isPlaceholder && timeline2.getWindow(period.windowIndex, window).firstPeriodIndex == timeline2.getIndexOfPeriod(cVar.d)) {
            Pair<Object, Long> periodPosition = timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(cVar.d, period).windowIndex, cVar.c + period.getPositionInWindowUs());
            cVar.a(timeline.getIndexOfPeriod(periodPosition.first), ((Long) periodPosition.second).longValue(), periodPosition.first);
        }
        return true;
    }

    private static void a(Timeline timeline, c cVar, Timeline.Window window, Timeline.Period period) {
        int i = timeline.getWindow(timeline.getPeriodByUid(cVar.d, period).windowIndex, window).lastPeriodIndex;
        cVar.a(i, period.durationUs != C.TIME_UNSET ? period.durationUs - 1 : Long.MAX_VALUE, timeline.getPeriod(i, period, true).uid);
    }

    @Nullable
    private static Pair<Object, Long> a(Timeline timeline, e eVar, boolean z, int i, boolean z2, Timeline.Window window, Timeline.Period period) {
        Object a2;
        Timeline timeline2 = eVar.a;
        if (timeline.isEmpty()) {
            return null;
        }
        Timeline timeline3 = timeline2.isEmpty() ? timeline : timeline2;
        try {
            Pair<Object, Long> periodPosition = timeline3.getPeriodPosition(window, period, eVar.b, eVar.c);
            if (timeline.equals(timeline3)) {
                return periodPosition;
            }
            if (timeline.getIndexOfPeriod(periodPosition.first) != -1) {
                return (!timeline3.getPeriodByUid(periodPosition.first, period).isPlaceholder || timeline3.getWindow(period.windowIndex, window).firstPeriodIndex != timeline3.getIndexOfPeriod(periodPosition.first)) ? periodPosition : timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(periodPosition.first, period).windowIndex, eVar.c);
            }
            if (!z || (a2 = a(window, period, i, z2, periodPosition.first, timeline3, timeline)) == null) {
                return null;
            }
            return timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(a2, period).windowIndex, C.TIME_UNSET);
        } catch (IndexOutOfBoundsException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static Object a(Timeline.Window window, Timeline.Period period, int i, boolean z, Object obj, Timeline timeline, Timeline timeline2) {
        int indexOfPeriod = timeline.getIndexOfPeriod(obj);
        int periodCount = timeline.getPeriodCount();
        int i2 = indexOfPeriod;
        int i3 = -1;
        for (int i4 = 0; i4 < periodCount && i3 == -1; i4++) {
            i2 = timeline.getNextPeriodIndex(i2, period, window, i, z);
            if (i2 == -1) {
                break;
            }
            i3 = timeline2.getIndexOfPeriod(timeline.getUidOfPeriod(i2));
        }
        if (i3 == -1) {
            return null;
        }
        return timeline2.getUidOfPeriod(i3);
    }

    private static Format[] a(ExoTrackSelection exoTrackSelection) {
        int length = exoTrackSelection != null ? exoTrackSelection.length() : 0;
        Format[] formatArr = new Format[length];
        for (int i = 0; i < length; i++) {
            formatArr[i] = exoTrackSelection.getFormat(i);
        }
        return formatArr;
    }

    private static boolean c(Renderer renderer) {
        return renderer.getState() != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class e {
        public final Timeline a;
        public final int b;
        public final long c;

        public e(Timeline timeline, int i, long j) {
            this.a = timeline;
            this.b = i;
            this.c = j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class d {
        public final MediaSource.MediaPeriodId a;
        public final long b;
        public final long c;
        public final boolean d;
        public final boolean e;
        public final boolean f;

        public d(MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, boolean z, boolean z2, boolean z3) {
            this.a = mediaPeriodId;
            this.b = j;
            this.c = j2;
            this.d = z;
            this.e = z2;
            this.f = z3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class c implements Comparable<c> {
        public final PlayerMessage a;
        public int b;
        public long c;
        @Nullable
        public Object d;

        public c(PlayerMessage playerMessage) {
            this.a = playerMessage;
        }

        public void a(int i, long j, Object obj) {
            this.b = i;
            this.c = j;
            this.d = obj;
        }

        /* renamed from: a */
        public int compareTo(c cVar) {
            if ((this.d == null) != (cVar.d == null)) {
                return this.d != null ? -1 : 1;
            }
            if (this.d == null) {
                return 0;
            }
            int i = this.b - cVar.b;
            return i != 0 ? i : Util.compareLong(this.c, cVar.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        private final List<MediaSourceList.c> a;
        private final ShuffleOrder b;
        private final int c;
        private final long d;

        private a(List<MediaSourceList.c> list, ShuffleOrder shuffleOrder, int i, long j) {
            this.a = list;
            this.b = shuffleOrder;
            this.c = i;
            this.d = j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b {
        public final int a;
        public final int b;
        public final int c;
        public final ShuffleOrder d;

        public b(int i, int i2, int i3, ShuffleOrder shuffleOrder) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = shuffleOrder;
        }
    }
}
