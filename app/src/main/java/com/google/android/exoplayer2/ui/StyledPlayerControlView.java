package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ForwardingPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class StyledPlayerControlView extends FrameLayout {
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    public static final int DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private final Drawable A;
    private final Drawable B;
    private final float C;
    private final float D;
    private final String E;
    private final String F;
    private final Drawable G;
    private final Drawable H;
    private final String I;
    private final String J;
    private final Drawable K;
    private final Drawable L;
    private final String M;
    private final String N;
    @Nullable
    private Player O;
    private ControlDispatcher P;
    @Nullable
    private ProgressUpdateListener Q;
    @Nullable
    private OnFullScreenModeChangedListener R;
    private boolean S;
    private boolean T;
    private boolean U;
    private boolean V;
    private boolean W;
    private final b a;
    private int aa;
    private int ab;
    private int ac;
    private long[] ad;
    private boolean[] ae;
    private long[] af;
    private boolean[] ag;
    private long ah;
    private c ai;
    private Resources aj;
    private RecyclerView ak;
    private e al;
    private c am;
    private PopupWindow an;
    private boolean ao;
    private int ap;
    @Nullable
    private DefaultTrackSelector aq;
    private i ar;
    private i as;
    private TrackNameProvider at;
    @Nullable
    private ImageView au;
    @Nullable
    private ImageView av;
    @Nullable
    private ImageView aw;
    @Nullable
    private View ax;
    @Nullable
    private View ay;
    @Nullable
    private View az;
    private final CopyOnWriteArrayList<VisibilityListener> b;
    @Nullable
    private final View c;
    @Nullable
    private final View d;
    @Nullable
    private final View e;
    @Nullable
    private final View f;
    @Nullable
    private final View g;
    @Nullable
    private final TextView h;
    @Nullable
    private final TextView i;
    @Nullable
    private final ImageView j;
    @Nullable
    private final ImageView k;
    @Nullable
    private final View l;
    @Nullable
    private final TextView m;
    @Nullable
    private final TextView n;
    @Nullable
    private final TimeBar o;
    private final StringBuilder p;
    private final Formatter q;
    private final Timeline.Period r;
    private final Timeline.Window s;
    private final Runnable t;
    private final Drawable u;
    private final Drawable v;
    private final Drawable w;
    private final String x;
    private final String y;
    private final String z;

    /* loaded from: classes2.dex */
    public interface OnFullScreenModeChangedListener {
        void onFullScreenModeChanged(boolean z);
    }

    /* loaded from: classes2.dex */
    public interface ProgressUpdateListener {
        void onProgressUpdate(long j, long j2);
    }

    /* loaded from: classes2.dex */
    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    @SuppressLint({"InlinedApi"})
    private static boolean b(int i2) {
        return i2 == 90 || i2 == 89 || i2 == 85 || i2 == 79 || i2 == 126 || i2 == 127 || i2 == 87 || i2 == 88;
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public StyledPlayerControlView(Context context) {
        this(context, null);
    }

    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.google.android.exoplayer2.ui.StyledPlayerControlView$1, android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StyledPlayerControlView(android.content.Context r22, @androidx.annotation.Nullable android.util.AttributeSet r23, int r24, @androidx.annotation.Nullable android.util.AttributeSet r25) {
        /*
            Method dump skipped, instructions count: 1159
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerControlView.<init>(android.content.Context, android.util.AttributeSet, int, android.util.AttributeSet):void");
    }

    @Nullable
    public Player getPlayer() {
        return this.O;
    }

    public void setPlayer(@Nullable Player player) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player == null || player.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player2 = this.O;
        if (player2 != player) {
            if (player2 != null) {
                player2.removeListener((Player.Listener) this.a);
            }
            this.O = player;
            if (player != null) {
                player.addListener((Player.Listener) this.a);
            }
            if (player instanceof ForwardingPlayer) {
                player = ((ForwardingPlayer) player).getWrappedPlayer();
            }
            if (player instanceof ExoPlayer) {
                TrackSelector trackSelector = ((ExoPlayer) player).getTrackSelector();
                if (trackSelector instanceof DefaultTrackSelector) {
                    this.aq = (DefaultTrackSelector) trackSelector;
                }
            } else {
                this.aq = null;
            }
            b();
        }
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        this.U = z;
        l();
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        boolean z = false;
        if (jArr == null) {
            this.af = new long[0];
            this.ag = new boolean[0];
        } else {
            boolean[] zArr2 = (boolean[]) Assertions.checkNotNull(zArr);
            if (jArr.length == zArr2.length) {
                z = true;
            }
            Assertions.checkArgument(z);
            this.af = jArr;
            this.ag = zArr2;
        }
        l();
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkNotNull(visibilityListener);
        this.b.add(visibilityListener);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.b.remove(visibilityListener);
    }

    public void setProgressUpdateListener(@Nullable ProgressUpdateListener progressUpdateListener) {
        this.Q = progressUpdateListener;
    }

    @Deprecated
    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.P != controlDispatcher) {
            this.P = controlDispatcher;
            e();
        }
    }

    public void setShowRewindButton(boolean z) {
        this.ai.a(this.g, z);
        e();
    }

    public void setShowFastForwardButton(boolean z) {
        this.ai.a(this.f, z);
        e();
    }

    public void setShowPreviousButton(boolean z) {
        this.ai.a(this.c, z);
        e();
    }

    public void setShowNextButton(boolean z) {
        this.ai.a(this.d, z);
        e();
    }

    public int getShowTimeoutMs() {
        return this.aa;
    }

    public void setShowTimeoutMs(int i2) {
        this.aa = i2;
        if (isFullyVisible()) {
            this.ai.e();
        }
    }

    public int getRepeatToggleModes() {
        return this.ac;
    }

    public void setRepeatToggleModes(int i2) {
        this.ac = i2;
        Player player = this.O;
        boolean z = false;
        if (player != null) {
            int repeatMode = player.getRepeatMode();
            if (i2 == 0 && repeatMode != 0) {
                this.P.dispatchSetRepeatMode(this.O, 0);
            } else if (i2 == 1 && repeatMode == 2) {
                this.P.dispatchSetRepeatMode(this.O, 1);
            } else if (i2 == 2 && repeatMode == 1) {
                this.P.dispatchSetRepeatMode(this.O, 2);
            }
        }
        c cVar = this.ai;
        ImageView imageView = this.j;
        if (i2 != 0) {
            z = true;
        }
        cVar.a(imageView, z);
        h();
    }

    public boolean getShowShuffleButton() {
        return this.ai.a(this.k);
    }

    public void setShowShuffleButton(boolean z) {
        this.ai.a(this.k, z);
        i();
    }

    public boolean getShowSubtitleButton() {
        return this.ai.a(this.au);
    }

    public void setShowSubtitleButton(boolean z) {
        this.ai.a(this.au, z);
    }

    public boolean getShowVrButton() {
        return this.ai.a(this.l);
    }

    public void setShowVrButton(boolean z) {
        this.ai.a(this.l, z);
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        View view = this.l;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            a(onClickListener != null, this.l);
        }
    }

    public void setAnimationEnabled(boolean z) {
        this.ai.a(z);
    }

    public boolean isAnimationEnabled() {
        return this.ai.d();
    }

    public void setTimeBarMinUpdateInterval(int i2) {
        this.ab = Util.constrainValue(i2, 16, 1000);
    }

    public void setOnFullScreenModeChangedListener(@Nullable OnFullScreenModeChangedListener onFullScreenModeChangedListener) {
        this.R = onFullScreenModeChangedListener;
        boolean z = true;
        a((View) this.av, onFullScreenModeChangedListener != null);
        ImageView imageView = this.aw;
        if (onFullScreenModeChangedListener == null) {
            z = false;
        }
        a((View) imageView, z);
    }

    public void show() {
        this.ai.a();
    }

    public void hide() {
        this.ai.b();
    }

    public void hideImmediately() {
        this.ai.c();
    }

    public boolean isFullyVisible() {
        return this.ai.i();
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public void a() {
        Iterator<VisibilityListener> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().onVisibilityChange(getVisibility());
        }
    }

    public void b() {
        d();
        e();
        h();
        i();
        j();
        n();
        l();
    }

    public void d() {
        if (isVisible() && this.T && this.e != null) {
            if (p()) {
                ((ImageView) this.e).setImageDrawable(this.aj.getDrawable(R.drawable.exo_styled_controls_pause));
                this.e.setContentDescription(this.aj.getString(R.string.exo_controls_pause_description));
                return;
            }
            ((ImageView) this.e).setImageDrawable(this.aj.getDrawable(R.drawable.exo_styled_controls_play));
            this.e.setContentDescription(this.aj.getString(R.string.exo_controls_play_description));
        }
    }

    public void e() {
        boolean z;
        boolean z2;
        boolean z3;
        if (isVisible() && this.T) {
            Player player = this.O;
            boolean z4 = false;
            if (player != null) {
                boolean isCommandAvailable = player.isCommandAvailable(4);
                z2 = player.isCommandAvailable(6);
                z4 = player.isCommandAvailable(10) && this.P.isRewindEnabled();
                if (player.isCommandAvailable(11) && this.P.isFastForwardEnabled()) {
                    z4 = true;
                }
                z3 = player.isCommandAvailable(8);
                z = isCommandAvailable;
            } else {
                z4 = false;
                z3 = false;
                z2 = false;
                z = false;
            }
            if (z4) {
                f();
            }
            if (z4) {
                g();
            }
            a(z2, this.c);
            a(z4, this.g);
            a(z4, this.f);
            a(z3, this.d);
            TimeBar timeBar = this.o;
            if (timeBar != null) {
                timeBar.setEnabled(z);
            }
        }
    }

    private void f() {
        Player player;
        ControlDispatcher controlDispatcher = this.P;
        int rewindIncrementMs = (int) (((!(controlDispatcher instanceof DefaultControlDispatcher) || (player = this.O) == null) ? 5000L : ((DefaultControlDispatcher) controlDispatcher).getRewindIncrementMs(player)) / 1000);
        TextView textView = this.i;
        if (textView != null) {
            textView.setText(String.valueOf(rewindIncrementMs));
        }
        View view = this.g;
        if (view != null) {
            view.setContentDescription(this.aj.getQuantityString(R.plurals.exo_controls_rewind_by_amount_description, rewindIncrementMs, Integer.valueOf(rewindIncrementMs)));
        }
    }

    private void g() {
        Player player;
        ControlDispatcher controlDispatcher = this.P;
        int fastForwardIncrementMs = (int) (((!(controlDispatcher instanceof DefaultControlDispatcher) || (player = this.O) == null) ? 15000L : ((DefaultControlDispatcher) controlDispatcher).getFastForwardIncrementMs(player)) / 1000);
        TextView textView = this.h;
        if (textView != null) {
            textView.setText(String.valueOf(fastForwardIncrementMs));
        }
        View view = this.f;
        if (view != null) {
            view.setContentDescription(this.aj.getQuantityString(R.plurals.exo_controls_fastforward_by_amount_description, fastForwardIncrementMs, Integer.valueOf(fastForwardIncrementMs)));
        }
    }

    public void h() {
        ImageView imageView;
        if (isVisible() && this.T && (imageView = this.j) != null) {
            if (this.ac == 0) {
                a(false, (View) imageView);
                return;
            }
            Player player = this.O;
            if (player == null) {
                a(false, (View) imageView);
                this.j.setImageDrawable(this.u);
                this.j.setContentDescription(this.x);
                return;
            }
            a(true, (View) imageView);
            switch (player.getRepeatMode()) {
                case 0:
                    this.j.setImageDrawable(this.u);
                    this.j.setContentDescription(this.x);
                    return;
                case 1:
                    this.j.setImageDrawable(this.v);
                    this.j.setContentDescription(this.y);
                    return;
                case 2:
                    this.j.setImageDrawable(this.w);
                    this.j.setContentDescription(this.z);
                    return;
                default:
                    return;
            }
        }
    }

    public void i() {
        ImageView imageView;
        String str;
        if (isVisible() && this.T && (imageView = this.k) != null) {
            Player player = this.O;
            if (!this.ai.a(imageView)) {
                a(false, (View) this.k);
            } else if (player == null) {
                a(false, (View) this.k);
                this.k.setImageDrawable(this.B);
                this.k.setContentDescription(this.F);
            } else {
                a(true, (View) this.k);
                this.k.setImageDrawable(player.getShuffleModeEnabled() ? this.A : this.B);
                ImageView imageView2 = this.k;
                if (player.getShuffleModeEnabled()) {
                    str = this.E;
                } else {
                    str = this.F;
                }
                imageView2.setContentDescription(str);
            }
        }
    }

    public void j() {
        k();
        a(this.ar.getItemCount() > 0, this.au);
    }

    private void k() {
        DefaultTrackSelector defaultTrackSelector;
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo;
        this.ar.a();
        this.as.a();
        if (!(this.O == null || (defaultTrackSelector = this.aq) == null || (currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo()) == null)) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (int i2 = 0; i2 < currentMappedTrackInfo.getRendererCount(); i2++) {
                if (currentMappedTrackInfo.getRendererType(i2) == 3 && this.ai.a(this.au)) {
                    a(currentMappedTrackInfo, i2, arrayList);
                    arrayList3.add(Integer.valueOf(i2));
                } else if (currentMappedTrackInfo.getRendererType(i2) == 1) {
                    a(currentMappedTrackInfo, i2, arrayList2);
                    arrayList4.add(Integer.valueOf(i2));
                }
            }
            this.ar.a(arrayList3, arrayList, currentMappedTrackInfo);
            this.as.a(arrayList4, arrayList2, currentMappedTrackInfo);
        }
    }

    private void a(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i2, List<h> list) {
        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
        TrackSelection trackSelection = ((Player) Assertions.checkNotNull(this.O)).getCurrentTrackSelections().get(i2);
        for (int i3 = 0; i3 < trackGroups.length; i3++) {
            TrackGroup trackGroup = trackGroups.get(i3);
            for (int i4 = 0; i4 < trackGroup.length; i4++) {
                Format format = trackGroup.getFormat(i4);
                if (mappedTrackInfo.getTrackSupport(i2, i3, i4) == 4) {
                    list.add(new h(i2, i3, i4, this.at.getTrackName(format), (trackSelection == null || trackSelection.indexOf(format) == -1) ? false : true));
                }
            }
        }
    }

    public void l() {
        long j;
        int i2;
        Player player = this.O;
        if (player != null) {
            boolean z = true;
            this.V = this.U && a(player.getCurrentTimeline(), this.s);
            this.ah = 0L;
            Timeline currentTimeline = player.getCurrentTimeline();
            if (!currentTimeline.isEmpty()) {
                int currentWindowIndex = player.getCurrentWindowIndex();
                int i3 = this.V ? 0 : currentWindowIndex;
                int windowCount = this.V ? currentTimeline.getWindowCount() - 1 : currentWindowIndex;
                long j2 = 0;
                i2 = 0;
                while (true) {
                    if (i3 > windowCount) {
                        break;
                    }
                    if (i3 == currentWindowIndex) {
                        this.ah = C.usToMs(j2);
                    }
                    currentTimeline.getWindow(i3, this.s);
                    if (this.s.durationUs == C.TIME_UNSET) {
                        Assertions.checkState(this.V ^ z);
                        break;
                    }
                    for (int i4 = this.s.firstPeriodIndex; i4 <= this.s.lastPeriodIndex; i4++) {
                        currentTimeline.getPeriod(i4, this.r);
                        int adGroupCount = this.r.getAdGroupCount();
                        for (int removedAdGroupCount = this.r.getRemovedAdGroupCount(); removedAdGroupCount < adGroupCount; removedAdGroupCount++) {
                            long adGroupTimeUs = this.r.getAdGroupTimeUs(removedAdGroupCount);
                            if (adGroupTimeUs == Long.MIN_VALUE) {
                                if (this.r.durationUs != C.TIME_UNSET) {
                                    adGroupTimeUs = this.r.durationUs;
                                }
                            }
                            long positionInWindowUs = adGroupTimeUs + this.r.getPositionInWindowUs();
                            if (positionInWindowUs >= 0) {
                                long[] jArr = this.ad;
                                if (i2 == jArr.length) {
                                    int length = jArr.length == 0 ? 1 : jArr.length * 2;
                                    this.ad = Arrays.copyOf(this.ad, length);
                                    this.ae = Arrays.copyOf(this.ae, length);
                                }
                                this.ad[i2] = C.usToMs(j2 + positionInWindowUs);
                                this.ae[i2] = this.r.hasPlayedAdGroup(removedAdGroupCount);
                                i2++;
                            }
                        }
                    }
                    j2 += this.s.durationUs;
                    i3++;
                    z = true;
                }
                j = j2;
            } else {
                i2 = 0;
                j = 0;
            }
            long usToMs = C.usToMs(j);
            TextView textView = this.m;
            if (textView != null) {
                textView.setText(Util.getStringForTime(this.p, this.q, usToMs));
            }
            TimeBar timeBar = this.o;
            if (timeBar != null) {
                timeBar.setDuration(usToMs);
                int length2 = this.af.length;
                int i5 = i2 + length2;
                long[] jArr2 = this.ad;
                if (i5 > jArr2.length) {
                    this.ad = Arrays.copyOf(jArr2, i5);
                    this.ae = Arrays.copyOf(this.ae, i5);
                }
                System.arraycopy(this.af, 0, this.ad, i2, length2);
                System.arraycopy(this.ag, 0, this.ae, i2, length2);
                this.o.setAdGroupTimesMs(this.ad, this.ae, i5);
            }
            m();
        }
    }

    public void m() {
        long j;
        if (isVisible() && this.T) {
            Player player = this.O;
            long j2 = 0;
            if (player != null) {
                j2 = this.ah + player.getContentPosition();
                j = this.ah + player.getContentBufferedPosition();
            } else {
                j = 0;
            }
            TextView textView = this.n;
            if (textView != null && !this.W) {
                textView.setText(Util.getStringForTime(this.p, this.q, j2));
            }
            TimeBar timeBar = this.o;
            if (timeBar != null) {
                timeBar.setPosition(j2);
                this.o.setBufferedPosition(j);
            }
            ProgressUpdateListener progressUpdateListener = this.Q;
            if (progressUpdateListener != null) {
                progressUpdateListener.onProgressUpdate(j2, j);
            }
            removeCallbacks(this.t);
            int playbackState = player == null ? 1 : player.getPlaybackState();
            long j3 = 1000;
            if (player != null && player.isPlaying()) {
                TimeBar timeBar2 = this.o;
                long min = Math.min(timeBar2 != null ? timeBar2.getPreferredUpdateDelay() : 1000L, 1000 - (j2 % 1000));
                float f2 = player.getPlaybackParameters().speed;
                if (f2 > 0.0f) {
                    j3 = ((float) min) / f2;
                }
                postDelayed(this.t, Util.constrainValue(j3, this.ab, 1000L));
            } else if (playbackState != 4 && playbackState != 1) {
                postDelayed(this.t, 1000L);
            }
        }
    }

    public void n() {
        Player player = this.O;
        if (player != null) {
            this.am.a(player.getPlaybackParameters().speed);
            this.al.a(0, this.am.a());
        }
    }

    private void o() {
        this.ak.measure(0, 0);
        this.an.setWidth(Math.min(this.ak.getMeasuredWidth(), getWidth() - (this.ap * 2)));
        this.an.setHeight(Math.min(getHeight() - (this.ap * 2), this.ak.getMeasuredHeight()));
    }

    public void a(RecyclerView.Adapter<?> adapter) {
        this.ak.setAdapter(adapter);
        o();
        this.ao = false;
        this.an.dismiss();
        this.ao = true;
        this.an.showAsDropDown(this, (getWidth() - this.an.getWidth()) - this.ap, (-this.an.getHeight()) - this.ap);
    }

    public void setPlaybackSpeed(float f2) {
        Player player = this.O;
        if (player != null) {
            this.P.dispatchSetPlaybackParameters(player, player.getPlaybackParameters().withSpeed(f2));
        }
    }

    public void c() {
        View view = this.e;
        if (view != null) {
            view.requestFocus();
        }
    }

    private void a(boolean z, @Nullable View view) {
        if (view != null) {
            view.setEnabled(z);
            view.setAlpha(z ? this.C : this.D);
        }
    }

    public void a(Player player, long j) {
        int i2;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (this.V && !currentTimeline.isEmpty()) {
            int windowCount = currentTimeline.getWindowCount();
            i2 = 0;
            while (true) {
                long durationMs = currentTimeline.getWindow(i2, this.s).getDurationMs();
                if (j < durationMs) {
                    break;
                } else if (i2 == windowCount - 1) {
                    j = durationMs;
                    break;
                } else {
                    j -= durationMs;
                    i2++;
                }
            }
        } else {
            i2 = player.getCurrentWindowIndex();
        }
        a(player, i2, j);
        m();
    }

    private boolean a(Player player, int i2, long j) {
        return this.P.dispatchSeekTo(player, i2, j);
    }

    public void a(View view) {
        if (this.R != null) {
            this.S = !this.S;
            a(this.av, this.S);
            a(this.aw, this.S);
            OnFullScreenModeChangedListener onFullScreenModeChangedListener = this.R;
            if (onFullScreenModeChangedListener != null) {
                onFullScreenModeChangedListener.onFullScreenModeChanged(this.S);
            }
        }
    }

    private void a(@Nullable ImageView imageView, boolean z) {
        if (imageView != null) {
            if (z) {
                imageView.setImageDrawable(this.K);
                imageView.setContentDescription(this.M);
                return;
            }
            imageView.setImageDrawable(this.L);
            imageView.setContentDescription(this.N);
        }
    }

    public void a(int i2) {
        if (i2 == 0) {
            a(this.am);
        } else if (i2 == 1) {
            a(this.as);
        } else {
            this.an.dismiss();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.ai.g();
        this.T = true;
        if (isFullyVisible()) {
            this.ai.e();
        }
        b();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.ai.h();
        this.T = false;
        removeCallbacks(this.t);
        this.ai.f();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Player player = this.O;
        if (player == null || !b(keyCode)) {
            return false;
        }
        if (keyEvent.getAction() != 0) {
            return true;
        }
        if (keyCode == 90) {
            if (player.getPlaybackState() == 4) {
                return true;
            }
            this.P.dispatchFastForward(player);
            return true;
        } else if (keyCode == 89) {
            this.P.dispatchRewind(player);
            return true;
        } else if (keyEvent.getRepeatCount() != 0) {
            return true;
        } else {
            switch (keyCode) {
                case 79:
                case 85:
                    a(player);
                    return true;
                case 87:
                    this.P.dispatchNext(player);
                    return true;
                case 88:
                    this.P.dispatchPrevious(player);
                    return true;
                case 126:
                    b(player);
                    return true;
                case 127:
                    c(player);
                    return true;
                default:
                    return true;
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        this.ai.a(z, i2, i3, i4, i5);
    }

    public void a(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        int i10 = i5 - i3;
        int i11 = i9 - i7;
        if (!(i4 - i2 == i8 - i6 && i10 == i11) && this.an.isShowing()) {
            o();
            this.an.update(view, (getWidth() - this.an.getWidth()) - this.ap, (-this.an.getHeight()) - this.ap, -1, -1);
        }
    }

    private boolean p() {
        Player player = this.O;
        return (player == null || player.getPlaybackState() == 4 || this.O.getPlaybackState() == 1 || !this.O.getPlayWhenReady()) ? false : true;
    }

    public void a(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
            b(player);
        } else {
            c(player);
        }
    }

    private void b(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            this.P.dispatchPrepare(player);
        } else if (playbackState == 4) {
            a(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }
        this.P.dispatchSetPlayWhenReady(player, true);
    }

    private void c(Player player) {
        this.P.dispatchSetPlayWhenReady(player, false);
    }

    private static boolean a(Timeline timeline, Timeline.Window window) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i2 = 0; i2 < windowCount; i2++) {
            if (timeline.getWindow(i2, window).durationUs == C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }

    private static void a(View view, View.OnClickListener onClickListener) {
        if (view != null) {
            view.setVisibility(8);
            view.setOnClickListener(onClickListener);
        }
    }

    private static void a(@Nullable View view, boolean z) {
        if (view != null) {
            if (z) {
                view.setVisibility(0);
            } else {
                view.setVisibility(8);
            }
        }
    }

    private static int a(TypedArray typedArray, int i2) {
        return typedArray.getInt(R.styleable.StyledPlayerControlView_repeat_toggle_modes, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b implements View.OnClickListener, PopupWindow.OnDismissListener, Player.Listener, TimeBar.OnScrubListener {
        private b() {
            StyledPlayerControlView.this = r1;
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(5, 6)) {
                StyledPlayerControlView.this.d();
            }
            if (events.containsAny(5, 6, 8)) {
                StyledPlayerControlView.this.m();
            }
            if (events.contains(9)) {
                StyledPlayerControlView.this.h();
            }
            if (events.contains(10)) {
                StyledPlayerControlView.this.i();
            }
            if (events.containsAny(9, 10, 12, 0, 17, 18, 14)) {
                StyledPlayerControlView.this.e();
            }
            if (events.containsAny(12, 0)) {
                StyledPlayerControlView.this.l();
            }
            if (events.contains(13)) {
                StyledPlayerControlView.this.n();
            }
            if (events.contains(2)) {
                StyledPlayerControlView.this.j();
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStart(TimeBar timeBar, long j) {
            StyledPlayerControlView.this.W = true;
            if (StyledPlayerControlView.this.n != null) {
                StyledPlayerControlView.this.n.setText(Util.getStringForTime(StyledPlayerControlView.this.p, StyledPlayerControlView.this.q, j));
            }
            StyledPlayerControlView.this.ai.f();
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubMove(TimeBar timeBar, long j) {
            if (StyledPlayerControlView.this.n != null) {
                StyledPlayerControlView.this.n.setText(Util.getStringForTime(StyledPlayerControlView.this.p, StyledPlayerControlView.this.q, j));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStop(TimeBar timeBar, long j, boolean z) {
            StyledPlayerControlView.this.W = false;
            if (!z && StyledPlayerControlView.this.O != null) {
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                styledPlayerControlView.a(styledPlayerControlView.O, j);
            }
            StyledPlayerControlView.this.ai.e();
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            if (StyledPlayerControlView.this.ao) {
                StyledPlayerControlView.this.ai.e();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Player player = StyledPlayerControlView.this.O;
            if (player != null) {
                StyledPlayerControlView.this.ai.e();
                if (StyledPlayerControlView.this.d == view) {
                    StyledPlayerControlView.this.P.dispatchNext(player);
                } else if (StyledPlayerControlView.this.c == view) {
                    StyledPlayerControlView.this.P.dispatchPrevious(player);
                } else if (StyledPlayerControlView.this.f == view) {
                    if (player.getPlaybackState() != 4) {
                        StyledPlayerControlView.this.P.dispatchFastForward(player);
                    }
                } else if (StyledPlayerControlView.this.g == view) {
                    StyledPlayerControlView.this.P.dispatchRewind(player);
                } else if (StyledPlayerControlView.this.e == view) {
                    StyledPlayerControlView.this.a(player);
                } else if (StyledPlayerControlView.this.j == view) {
                    StyledPlayerControlView.this.P.dispatchSetRepeatMode(player, RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), StyledPlayerControlView.this.ac));
                } else if (StyledPlayerControlView.this.k == view) {
                    StyledPlayerControlView.this.P.dispatchSetShuffleModeEnabled(player, !player.getShuffleModeEnabled());
                } else if (StyledPlayerControlView.this.ax == view) {
                    StyledPlayerControlView.this.ai.f();
                    StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                    styledPlayerControlView.a(styledPlayerControlView.al);
                } else if (StyledPlayerControlView.this.ay == view) {
                    StyledPlayerControlView.this.ai.f();
                    StyledPlayerControlView styledPlayerControlView2 = StyledPlayerControlView.this;
                    styledPlayerControlView2.a(styledPlayerControlView2.am);
                } else if (StyledPlayerControlView.this.az == view) {
                    StyledPlayerControlView.this.ai.f();
                    StyledPlayerControlView styledPlayerControlView3 = StyledPlayerControlView.this;
                    styledPlayerControlView3.a(styledPlayerControlView3.as);
                } else if (StyledPlayerControlView.this.au == view) {
                    StyledPlayerControlView.this.ai.f();
                    StyledPlayerControlView styledPlayerControlView4 = StyledPlayerControlView.this;
                    styledPlayerControlView4.a(styledPlayerControlView4.ar);
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public class e extends RecyclerView.Adapter<d> {
        private final String[] b;
        private final String[] c;
        private final Drawable[] d;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            return i;
        }

        public e(String[] strArr, Drawable[] drawableArr) {
            StyledPlayerControlView.this = r1;
            this.b = strArr;
            this.c = new String[strArr.length];
            this.d = drawableArr;
        }

        /* renamed from: a */
        public d onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new d(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_settings_list_item, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(d dVar, int i) {
            dVar.b.setText(this.b[i]);
            if (this.c[i] == null) {
                dVar.c.setVisibility(8);
            } else {
                dVar.c.setText(this.c[i]);
            }
            if (this.d[i] == null) {
                dVar.d.setVisibility(8);
            } else {
                dVar.d.setImageDrawable(this.d[i]);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.b.length;
        }

        public void a(int i, String str) {
            this.c[i] = str;
        }
    }

    /* loaded from: classes2.dex */
    public final class d extends RecyclerView.ViewHolder {
        private final TextView b;
        private final TextView c;
        private final ImageView d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(View view) {
            super(view);
            StyledPlayerControlView.this = r2;
            if (Util.SDK_INT < 26) {
                view.setFocusable(true);
            }
            this.b = (TextView) view.findViewById(R.id.exo_main_text);
            this.c = (TextView) view.findViewById(R.id.exo_sub_text);
            this.d = (ImageView) view.findViewById(R.id.exo_icon);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$StyledPlayerControlView$d$scwy5dLFnNGp-v0tBKmALRBvi4w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    StyledPlayerControlView.d.this.a(view2);
                }
            });
        }

        public /* synthetic */ void a(View view) {
            StyledPlayerControlView.this.a(getAdapterPosition());
        }
    }

    /* loaded from: classes2.dex */
    public final class c extends RecyclerView.Adapter<f> {
        private final String[] b;
        private final int[] c;
        private int d;

        public c(String[] strArr, int[] iArr) {
            StyledPlayerControlView.this = r1;
            this.b = strArr;
            this.c = iArr;
        }

        public void a(float f) {
            int round = Math.round(f * 100.0f);
            int i = 0;
            int i2 = Integer.MAX_VALUE;
            int i3 = 0;
            while (true) {
                int[] iArr = this.c;
                if (i < iArr.length) {
                    int abs = Math.abs(round - iArr[i]);
                    if (abs < i2) {
                        i3 = i;
                        i2 = abs;
                    }
                    i++;
                } else {
                    this.d = i3;
                    return;
                }
            }
        }

        public String a() {
            return this.b[this.d];
        }

        /* renamed from: a */
        public f onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new f(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(f fVar, final int i) {
            if (i < this.b.length) {
                fVar.a.setText(this.b[i]);
            }
            fVar.b.setVisibility(i == this.d ? 0 : 4);
            fVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$StyledPlayerControlView$c$smKNqsTUrfevTOh-yUtof0-avoU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StyledPlayerControlView.c.this.a(i, view);
                }
            });
        }

        public /* synthetic */ void a(int i, View view) {
            if (i != this.d) {
                StyledPlayerControlView.this.setPlaybackSpeed(this.c[i] / 100.0f);
            }
            StyledPlayerControlView.this.an.dismiss();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.b.length;
        }
    }

    /* loaded from: classes2.dex */
    public static final class h {
        public final int a;
        public final int b;
        public final int c;
        public final String d;
        public final boolean e;

        public h(int i, int i2, int i3, String str, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = str;
            this.e = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class g extends i {
        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        public void a(String str) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private g() {
            super();
            StyledPlayerControlView.this = r1;
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        public void a(List<Integer> list, List<h> list2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo) {
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= list2.size()) {
                    break;
                } else if (list2.get(i).e) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (StyledPlayerControlView.this.au != null) {
                StyledPlayerControlView.this.au.setImageDrawable(z ? StyledPlayerControlView.this.G : StyledPlayerControlView.this.H);
                StyledPlayerControlView.this.au.setContentDescription(z ? StyledPlayerControlView.this.I : StyledPlayerControlView.this.J);
            }
            this.b = list;
            this.c = list2;
            this.d = mappedTrackInfo;
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        public void a(f fVar) {
            boolean z;
            fVar.a.setText(R.string.exo_track_selection_none);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.c.size()) {
                    z = true;
                    break;
                } else if (((h) this.c.get(i2)).e) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            View view = fVar.b;
            if (!z) {
                i = 4;
            }
            view.setVisibility(i);
            fVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$StyledPlayerControlView$g$oGWMlKBOcI9KhJV92iBYN3KbmEc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    StyledPlayerControlView.g.this.a(view2);
                }
            });
        }

        public /* synthetic */ void a(View view) {
            if (StyledPlayerControlView.this.aq != null) {
                DefaultTrackSelector.ParametersBuilder buildUpon = StyledPlayerControlView.this.aq.getParameters().buildUpon();
                for (int i = 0; i < this.b.size(); i++) {
                    int intValue = ((Integer) this.b.get(i)).intValue();
                    buildUpon = buildUpon.clearSelectionOverrides(intValue).setRendererDisabled(intValue, true);
                }
                ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.aq)).setParameters(buildUpon);
                StyledPlayerControlView.this.an.dismiss();
            }
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        /* renamed from: a */
        public void onBindViewHolder(f fVar, int i) {
            super.onBindViewHolder(fVar, i);
            if (i > 0) {
                fVar.b.setVisibility(((h) this.c.get(i + (-1))).e ? 0 : 4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a extends i {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a() {
            super();
            StyledPlayerControlView.this = r1;
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        public void a(f fVar) {
            boolean z;
            fVar.a.setText(R.string.exo_track_selection_auto);
            DefaultTrackSelector.Parameters parameters = ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.aq)).getParameters();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.b.size()) {
                    z = false;
                    break;
                }
                int intValue = ((Integer) this.b.get(i2)).intValue();
                if (parameters.hasSelectionOverride(intValue, ((MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.d)).getTrackGroups(intValue))) {
                    z = true;
                    break;
                }
                i2++;
            }
            View view = fVar.b;
            if (z) {
                i = 4;
            }
            view.setVisibility(i);
            fVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$StyledPlayerControlView$a$3xR2beQq6pOuLm47KhH7q1oej7g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    StyledPlayerControlView.a.this.a(view2);
                }
            });
        }

        public /* synthetic */ void a(View view) {
            if (StyledPlayerControlView.this.aq != null) {
                DefaultTrackSelector.ParametersBuilder buildUpon = StyledPlayerControlView.this.aq.getParameters().buildUpon();
                for (int i = 0; i < this.b.size(); i++) {
                    buildUpon = buildUpon.clearSelectionOverrides(((Integer) this.b.get(i)).intValue());
                }
                ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.aq)).setParameters(buildUpon);
            }
            StyledPlayerControlView.this.al.a(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_auto));
            StyledPlayerControlView.this.an.dismiss();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        public void a(String str) {
            StyledPlayerControlView.this.al.a(1, str);
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.i
        public void a(List<Integer> list, List<h> list2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo) {
            boolean z;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    z = false;
                    break;
                }
                int intValue = list.get(i2).intValue();
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(intValue);
                if (StyledPlayerControlView.this.aq != null && StyledPlayerControlView.this.aq.getParameters().hasSelectionOverride(intValue, trackGroups)) {
                    z = true;
                    break;
                }
                i2++;
            }
            if (!list2.isEmpty()) {
                if (z) {
                    while (true) {
                        if (i >= list2.size()) {
                            break;
                        }
                        h hVar = list2.get(i);
                        if (hVar.e) {
                            StyledPlayerControlView.this.al.a(1, hVar.d);
                            break;
                        }
                        i++;
                    }
                } else {
                    StyledPlayerControlView.this.al.a(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_auto));
                }
            } else {
                StyledPlayerControlView.this.al.a(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_none));
            }
            this.b = list;
            this.c = list2;
            this.d = mappedTrackInfo;
        }
    }

    /* loaded from: classes2.dex */
    public abstract class i extends RecyclerView.Adapter<f> {
        protected List<Integer> b = new ArrayList();
        protected List<h> c = new ArrayList();
        @Nullable
        protected MappingTrackSelector.MappedTrackInfo d = null;

        public abstract void a(f fVar);

        public abstract void a(String str);

        public abstract void a(List<Integer> list, List<h> list2, MappingTrackSelector.MappedTrackInfo mappedTrackInfo);

        public i() {
            StyledPlayerControlView.this = r1;
        }

        /* renamed from: a */
        public f onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new f(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(f fVar, int i) {
            if (StyledPlayerControlView.this.aq != null && this.d != null) {
                if (i == 0) {
                    a(fVar);
                    return;
                }
                boolean z = true;
                final h hVar = this.c.get(i - 1);
                int i2 = 0;
                if (!((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.aq)).getParameters().hasSelectionOverride(hVar.a, this.d.getTrackGroups(hVar.a)) || !hVar.e) {
                    z = false;
                }
                fVar.a.setText(hVar.d);
                View view = fVar.b;
                if (!z) {
                    i2 = 4;
                }
                view.setVisibility(i2);
                fVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$StyledPlayerControlView$i$dRaaNwFbWRr2lGXL6LXPy8pujrk
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        StyledPlayerControlView.i.this.a(hVar, view2);
                    }
                });
            }
        }

        public /* synthetic */ void a(h hVar, View view) {
            if (!(this.d == null || StyledPlayerControlView.this.aq == null)) {
                DefaultTrackSelector.ParametersBuilder buildUpon = StyledPlayerControlView.this.aq.getParameters().buildUpon();
                for (int i = 0; i < this.b.size(); i++) {
                    int intValue = this.b.get(i).intValue();
                    if (intValue == hVar.a) {
                        buildUpon = buildUpon.setSelectionOverride(intValue, ((MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.d)).getTrackGroups(intValue), new DefaultTrackSelector.SelectionOverride(hVar.b, hVar.c)).setRendererDisabled(intValue, false);
                    } else {
                        buildUpon = buildUpon.clearSelectionOverrides(intValue).setRendererDisabled(intValue, true);
                    }
                }
                ((DefaultTrackSelector) Assertions.checkNotNull(StyledPlayerControlView.this.aq)).setParameters(buildUpon);
                a(hVar.d);
                StyledPlayerControlView.this.an.dismiss();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (this.c.isEmpty()) {
                return 0;
            }
            return this.c.size() + 1;
        }

        public void a() {
            this.c = Collections.emptyList();
            this.d = null;
        }
    }

    /* loaded from: classes2.dex */
    public static class f extends RecyclerView.ViewHolder {
        public final TextView a;
        public final View b;

        public f(View view) {
            super(view);
            if (Util.SDK_INT < 26) {
                view.setFocusable(true);
            }
            this.a = (TextView) view.findViewById(R.id.exo_text);
            this.b = view.findViewById(R.id.exo_check);
        }
    }
}
