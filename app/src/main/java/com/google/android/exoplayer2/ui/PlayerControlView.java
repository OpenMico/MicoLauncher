package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class PlayerControlView extends FrameLayout {
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
    @Nullable
    private Player G;
    private ControlDispatcher H;
    @Nullable
    private ProgressUpdateListener I;
    private boolean J;
    private boolean K;
    private boolean L;
    private boolean M;
    private int N;
    private int O;
    private int P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private boolean U;
    private long V;
    private long[] W;
    private final b a;
    private boolean[] aa;
    private long[] ab;
    private boolean[] ac;
    private long ad;
    private long ae;
    private long af;
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
    private final View h;
    @Nullable
    private final ImageView i;
    @Nullable
    private final ImageView j;
    @Nullable
    private final View k;
    @Nullable
    private final TextView l;
    @Nullable
    private final TextView m;
    @Nullable
    private final TimeBar n;
    private final StringBuilder o;
    private final Formatter p;
    private final Timeline.Period q;
    private final Timeline.Window r;
    private final Runnable s;
    private final Runnable t;
    private final Drawable u;
    private final Drawable v;
    private final Drawable w;
    private final String x;
    private final String y;
    private final String z;

    /* loaded from: classes2.dex */
    public interface ProgressUpdateListener {
        void onProgressUpdate(long j, long j2);
    }

    /* loaded from: classes2.dex */
    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    @SuppressLint({"InlinedApi"})
    private static boolean a(int i) {
        return i == 90 || i == 89 || i == 85 || i == 79 || i == 126 || i == 127 || i == 87 || i == 88;
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public PlayerControlView(Context context) {
        this(context, null);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, attributeSet);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i, @Nullable AttributeSet attributeSet2) {
        super(context, attributeSet, i);
        int i2 = R.layout.exo_player_control_view;
        this.N = 5000;
        this.P = 0;
        this.O = 200;
        this.V = C.TIME_UNSET;
        this.Q = true;
        this.R = true;
        this.S = true;
        this.T = true;
        this.U = false;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.PlayerControlView, i, 0);
            try {
                this.N = obtainStyledAttributes.getInt(R.styleable.PlayerControlView_show_timeout, this.N);
                i2 = obtainStyledAttributes.getResourceId(R.styleable.PlayerControlView_controller_layout_id, i2);
                this.P = a(obtainStyledAttributes, this.P);
                this.Q = obtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_rewind_button, this.Q);
                this.R = obtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_fastforward_button, this.R);
                this.S = obtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_previous_button, this.S);
                this.T = obtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_next_button, this.T);
                this.U = obtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_shuffle_button, this.U);
                setTimeBarMinUpdateInterval(obtainStyledAttributes.getInt(R.styleable.PlayerControlView_time_bar_min_update_interval, this.O));
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.b = new CopyOnWriteArrayList<>();
        this.q = new Timeline.Period();
        this.r = new Timeline.Window();
        this.o = new StringBuilder();
        this.p = new Formatter(this.o, Locale.getDefault());
        this.W = new long[0];
        this.aa = new boolean[0];
        this.ab = new long[0];
        this.ac = new boolean[0];
        this.a = new b();
        this.H = new DefaultControlDispatcher();
        this.s = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$PlayerControlView$0r5zKAuj5vuHQxawzkh05pt8oVM
            @Override // java.lang.Runnable
            public final void run() {
                PlayerControlView.this.h();
            }
        };
        this.t = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$1vmvJI4HM5BSJdnh7cGvyaODZdE
            @Override // java.lang.Runnable
            public final void run() {
                PlayerControlView.this.hide();
            }
        };
        LayoutInflater.from(context).inflate(i2, this);
        setDescendantFocusability(262144);
        TimeBar timeBar = (TimeBar) findViewById(R.id.exo_progress);
        View findViewById = findViewById(R.id.exo_progress_placeholder);
        if (timeBar != null) {
            this.n = timeBar;
        } else if (findViewById != null) {
            DefaultTimeBar defaultTimeBar = new DefaultTimeBar(context, null, 0, attributeSet2);
            defaultTimeBar.setId(R.id.exo_progress);
            defaultTimeBar.setLayoutParams(findViewById.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById);
            viewGroup.removeView(findViewById);
            viewGroup.addView(defaultTimeBar, indexOfChild);
            this.n = defaultTimeBar;
        } else {
            this.n = null;
        }
        this.l = (TextView) findViewById(R.id.exo_duration);
        this.m = (TextView) findViewById(R.id.exo_position);
        TimeBar timeBar2 = this.n;
        if (timeBar2 != null) {
            timeBar2.addListener(this.a);
        }
        this.e = findViewById(R.id.exo_play);
        View view = this.e;
        if (view != null) {
            view.setOnClickListener(this.a);
        }
        this.f = findViewById(R.id.exo_pause);
        View view2 = this.f;
        if (view2 != null) {
            view2.setOnClickListener(this.a);
        }
        this.c = findViewById(R.id.exo_prev);
        View view3 = this.c;
        if (view3 != null) {
            view3.setOnClickListener(this.a);
        }
        this.d = findViewById(R.id.exo_next);
        View view4 = this.d;
        if (view4 != null) {
            view4.setOnClickListener(this.a);
        }
        this.h = findViewById(R.id.exo_rew);
        View view5 = this.h;
        if (view5 != null) {
            view5.setOnClickListener(this.a);
        }
        this.g = findViewById(R.id.exo_ffwd);
        View view6 = this.g;
        if (view6 != null) {
            view6.setOnClickListener(this.a);
        }
        this.i = (ImageView) findViewById(R.id.exo_repeat_toggle);
        ImageView imageView = this.i;
        if (imageView != null) {
            imageView.setOnClickListener(this.a);
        }
        this.j = (ImageView) findViewById(R.id.exo_shuffle);
        ImageView imageView2 = this.j;
        if (imageView2 != null) {
            imageView2.setOnClickListener(this.a);
        }
        this.k = findViewById(R.id.exo_vr);
        setShowVrButton(false);
        a(false, false, this.k);
        Resources resources = context.getResources();
        this.C = resources.getInteger(R.integer.exo_media_button_opacity_percentage_enabled) / 100.0f;
        this.D = resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled) / 100.0f;
        this.u = resources.getDrawable(R.drawable.exo_controls_repeat_off);
        this.v = resources.getDrawable(R.drawable.exo_controls_repeat_one);
        this.w = resources.getDrawable(R.drawable.exo_controls_repeat_all);
        this.A = resources.getDrawable(R.drawable.exo_controls_shuffle_on);
        this.B = resources.getDrawable(R.drawable.exo_controls_shuffle_off);
        this.x = resources.getString(R.string.exo_controls_repeat_off_description);
        this.y = resources.getString(R.string.exo_controls_repeat_one_description);
        this.z = resources.getString(R.string.exo_controls_repeat_all_description);
        this.E = resources.getString(R.string.exo_controls_shuffle_on_description);
        this.F = resources.getString(R.string.exo_controls_shuffle_off_description);
    }

    @Nullable
    public Player getPlayer() {
        return this.G;
    }

    public void setPlayer(@Nullable Player player) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player == null || player.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player2 = this.G;
        if (player2 != player) {
            if (player2 != null) {
                player2.removeListener((Player.Listener) this.a);
            }
            this.G = player;
            if (player != null) {
                player.addListener((Player.Listener) this.a);
            }
            b();
        }
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        this.K = z;
        g();
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        boolean z = false;
        if (jArr == null) {
            this.ab = new long[0];
            this.ac = new boolean[0];
        } else {
            boolean[] zArr2 = (boolean[]) Assertions.checkNotNull(zArr);
            if (jArr.length == zArr2.length) {
                z = true;
            }
            Assertions.checkArgument(z);
            this.ab = jArr;
            this.ac = zArr2;
        }
        g();
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkNotNull(visibilityListener);
        this.b.add(visibilityListener);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.b.remove(visibilityListener);
    }

    public void setProgressUpdateListener(@Nullable ProgressUpdateListener progressUpdateListener) {
        this.I = progressUpdateListener;
    }

    @Deprecated
    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.H != controlDispatcher) {
            this.H = controlDispatcher;
            d();
        }
    }

    public void setShowRewindButton(boolean z) {
        this.Q = z;
        d();
    }

    public void setShowFastForwardButton(boolean z) {
        this.R = z;
        d();
    }

    public void setShowPreviousButton(boolean z) {
        this.S = z;
        d();
    }

    public void setShowNextButton(boolean z) {
        this.T = z;
        d();
    }

    public int getShowTimeoutMs() {
        return this.N;
    }

    public void setShowTimeoutMs(int i) {
        this.N = i;
        if (isVisible()) {
            a();
        }
    }

    public int getRepeatToggleModes() {
        return this.P;
    }

    public void setRepeatToggleModes(int i) {
        this.P = i;
        Player player = this.G;
        if (player != null) {
            int repeatMode = player.getRepeatMode();
            if (i == 0 && repeatMode != 0) {
                this.H.dispatchSetRepeatMode(this.G, 0);
            } else if (i == 1 && repeatMode == 2) {
                this.H.dispatchSetRepeatMode(this.G, 1);
            } else if (i == 2 && repeatMode == 1) {
                this.H.dispatchSetRepeatMode(this.G, 2);
            }
        }
        e();
    }

    public boolean getShowShuffleButton() {
        return this.U;
    }

    public void setShowShuffleButton(boolean z) {
        this.U = z;
        f();
    }

    public boolean getShowVrButton() {
        View view = this.k;
        return view != null && view.getVisibility() == 0;
    }

    public void setShowVrButton(boolean z) {
        View view = this.k;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        View view = this.k;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            a(getShowVrButton(), onClickListener != null, this.k);
        }
    }

    public void setTimeBarMinUpdateInterval(int i) {
        this.O = Util.constrainValue(i, 16, 1000);
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(0);
            Iterator<VisibilityListener> it = this.b.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(getVisibility());
            }
            b();
            i();
            j();
        }
        a();
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(8);
            Iterator<VisibilityListener> it = this.b.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(getVisibility());
            }
            removeCallbacks(this.s);
            removeCallbacks(this.t);
            this.V = C.TIME_UNSET;
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    private void a() {
        removeCallbacks(this.t);
        if (this.N > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = this.N;
            this.V = uptimeMillis + i;
            if (this.J) {
                postDelayed(this.t, i);
                return;
            }
            return;
        }
        this.V = C.TIME_UNSET;
    }

    private void b() {
        c();
        d();
        e();
        f();
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        boolean z;
        boolean z2;
        boolean z3;
        if (isVisible() && this.J) {
            boolean k = k();
            View view = this.e;
            int i = 8;
            boolean z4 = true;
            if (view != null) {
                z2 = (k && view.isFocused()) | false;
                if (Util.SDK_INT < 21) {
                    z3 = z2;
                } else {
                    z3 = k && a.a(this.e);
                }
                z = z3 | false;
                this.e.setVisibility(k ? 8 : 0);
            } else {
                z2 = false;
                z = false;
            }
            View view2 = this.f;
            if (view2 != null) {
                z2 |= !k && view2.isFocused();
                if (Util.SDK_INT < 21) {
                    z4 = z2;
                } else if (k || !a.a(this.f)) {
                    z4 = false;
                }
                z |= z4;
                View view3 = this.f;
                if (k) {
                    i = 0;
                }
                view3.setVisibility(i);
            }
            if (z2) {
                i();
            }
            if (z) {
                j();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        boolean z;
        boolean z2;
        boolean z3;
        if (isVisible() && this.J) {
            Player player = this.G;
            boolean z4 = false;
            if (player != null) {
                boolean isCommandAvailable = player.isCommandAvailable(4);
                boolean isCommandAvailable2 = player.isCommandAvailable(6);
                z = player.isCommandAvailable(10) && this.H.isRewindEnabled();
                if (player.isCommandAvailable(11) && this.H.isFastForwardEnabled()) {
                    z4 = true;
                }
                z3 = player.isCommandAvailable(8);
                z4 = isCommandAvailable2;
                z2 = isCommandAvailable;
            } else {
                z4 = false;
                z3 = false;
                z2 = false;
                z = false;
            }
            a(this.S, z4, this.c);
            a(this.Q, z, this.h);
            a(this.R, z4, this.g);
            a(this.T, z3, this.d);
            TimeBar timeBar = this.n;
            if (timeBar != null) {
                timeBar.setEnabled(z2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        ImageView imageView;
        if (isVisible() && this.J && (imageView = this.i) != null) {
            if (this.P == 0) {
                a(false, false, (View) imageView);
                return;
            }
            Player player = this.G;
            if (player == null) {
                a(true, false, (View) imageView);
                this.i.setImageDrawable(this.u);
                this.i.setContentDescription(this.x);
                return;
            }
            a(true, true, (View) imageView);
            switch (player.getRepeatMode()) {
                case 0:
                    this.i.setImageDrawable(this.u);
                    this.i.setContentDescription(this.x);
                    break;
                case 1:
                    this.i.setImageDrawable(this.v);
                    this.i.setContentDescription(this.y);
                    break;
                case 2:
                    this.i.setImageDrawable(this.w);
                    this.i.setContentDescription(this.z);
                    break;
            }
            this.i.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        ImageView imageView;
        String str;
        if (isVisible() && this.J && (imageView = this.j) != null) {
            Player player = this.G;
            if (!this.U) {
                a(false, false, (View) imageView);
            } else if (player == null) {
                a(true, false, (View) imageView);
                this.j.setImageDrawable(this.B);
                this.j.setContentDescription(this.F);
            } else {
                a(true, true, (View) imageView);
                this.j.setImageDrawable(player.getShuffleModeEnabled() ? this.A : this.B);
                ImageView imageView2 = this.j;
                if (player.getShuffleModeEnabled()) {
                    str = this.E;
                } else {
                    str = this.F;
                }
                imageView2.setContentDescription(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        long j;
        int i;
        Player player = this.G;
        if (player != null) {
            boolean z = true;
            this.L = this.K && a(player.getCurrentTimeline(), this.r);
            this.ad = 0L;
            Timeline currentTimeline = player.getCurrentTimeline();
            if (!currentTimeline.isEmpty()) {
                int currentWindowIndex = player.getCurrentWindowIndex();
                int i2 = this.L ? 0 : currentWindowIndex;
                int windowCount = this.L ? currentTimeline.getWindowCount() - 1 : currentWindowIndex;
                long j2 = 0;
                i = 0;
                while (true) {
                    if (i2 > windowCount) {
                        break;
                    }
                    if (i2 == currentWindowIndex) {
                        this.ad = C.usToMs(j2);
                    }
                    currentTimeline.getWindow(i2, this.r);
                    if (this.r.durationUs == C.TIME_UNSET) {
                        Assertions.checkState(this.L ^ z);
                        break;
                    }
                    for (int i3 = this.r.firstPeriodIndex; i3 <= this.r.lastPeriodIndex; i3++) {
                        currentTimeline.getPeriod(i3, this.q);
                        int adGroupCount = this.q.getAdGroupCount();
                        for (int removedAdGroupCount = this.q.getRemovedAdGroupCount(); removedAdGroupCount < adGroupCount; removedAdGroupCount++) {
                            long adGroupTimeUs = this.q.getAdGroupTimeUs(removedAdGroupCount);
                            if (adGroupTimeUs == Long.MIN_VALUE) {
                                if (this.q.durationUs != C.TIME_UNSET) {
                                    adGroupTimeUs = this.q.durationUs;
                                }
                            }
                            long positionInWindowUs = adGroupTimeUs + this.q.getPositionInWindowUs();
                            if (positionInWindowUs >= 0) {
                                long[] jArr = this.W;
                                if (i == jArr.length) {
                                    int length = jArr.length == 0 ? 1 : jArr.length * 2;
                                    this.W = Arrays.copyOf(this.W, length);
                                    this.aa = Arrays.copyOf(this.aa, length);
                                }
                                this.W[i] = C.usToMs(j2 + positionInWindowUs);
                                this.aa[i] = this.q.hasPlayedAdGroup(removedAdGroupCount);
                                i++;
                            }
                        }
                    }
                    j2 += this.r.durationUs;
                    i2++;
                    z = true;
                }
                j = j2;
            } else {
                i = 0;
                j = 0;
            }
            long usToMs = C.usToMs(j);
            TextView textView = this.l;
            if (textView != null) {
                textView.setText(Util.getStringForTime(this.o, this.p, usToMs));
            }
            TimeBar timeBar = this.n;
            if (timeBar != null) {
                timeBar.setDuration(usToMs);
                int length2 = this.ab.length;
                int i4 = i + length2;
                long[] jArr2 = this.W;
                if (i4 > jArr2.length) {
                    this.W = Arrays.copyOf(jArr2, i4);
                    this.aa = Arrays.copyOf(this.aa, i4);
                }
                System.arraycopy(this.ab, 0, this.W, i, length2);
                System.arraycopy(this.ac, 0, this.aa, i, length2);
                this.n.setAdGroupTimesMs(this.W, this.aa, i4);
            }
            h();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        long j;
        if (isVisible() && this.J) {
            Player player = this.G;
            long j2 = 0;
            if (player != null) {
                j2 = this.ad + player.getContentPosition();
                j = this.ad + player.getContentBufferedPosition();
            } else {
                j = 0;
            }
            boolean z = false;
            boolean z2 = j2 != this.ae;
            if (j != this.af) {
                z = true;
            }
            this.ae = j2;
            this.af = j;
            TextView textView = this.m;
            if (textView != null && !this.M && z2) {
                textView.setText(Util.getStringForTime(this.o, this.p, j2));
            }
            TimeBar timeBar = this.n;
            if (timeBar != null) {
                timeBar.setPosition(j2);
                this.n.setBufferedPosition(j);
            }
            if (this.I != null && (z2 || z)) {
                this.I.onProgressUpdate(j2, j);
            }
            removeCallbacks(this.s);
            int playbackState = player == null ? 1 : player.getPlaybackState();
            long j3 = 1000;
            if (player != null && player.isPlaying()) {
                TimeBar timeBar2 = this.n;
                long min = Math.min(timeBar2 != null ? timeBar2.getPreferredUpdateDelay() : 1000L, 1000 - (j2 % 1000));
                float f = player.getPlaybackParameters().speed;
                if (f > 0.0f) {
                    j3 = ((float) min) / f;
                }
                postDelayed(this.s, Util.constrainValue(j3, this.O, 1000L));
            } else if (playbackState != 4 && playbackState != 1) {
                postDelayed(this.s, 1000L);
            }
        }
    }

    private void i() {
        View view;
        View view2;
        boolean k = k();
        if (!k && (view2 = this.e) != null) {
            view2.requestFocus();
        } else if (k && (view = this.f) != null) {
            view.requestFocus();
        }
    }

    private void j() {
        View view;
        View view2;
        boolean k = k();
        if (!k && (view2 = this.e) != null) {
            view2.sendAccessibilityEvent(8);
        } else if (k && (view = this.f) != null) {
            view.sendAccessibilityEvent(8);
        }
    }

    private void a(boolean z, boolean z2, @Nullable View view) {
        if (view != null) {
            view.setEnabled(z2);
            view.setAlpha(z2 ? this.C : this.D);
            view.setVisibility(z ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Player player, long j) {
        int i;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (this.L && !currentTimeline.isEmpty()) {
            int windowCount = currentTimeline.getWindowCount();
            i = 0;
            while (true) {
                long durationMs = currentTimeline.getWindow(i, this.r).getDurationMs();
                if (j < durationMs) {
                    break;
                } else if (i == windowCount - 1) {
                    j = durationMs;
                    break;
                } else {
                    j -= durationMs;
                    i++;
                }
            }
        } else {
            i = player.getCurrentWindowIndex();
        }
        a(player, i, j);
        h();
    }

    private boolean a(Player player, int i, long j) {
        return this.H.dispatchSeekTo(player, i, j);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.J = true;
        long j = this.V;
        if (j != C.TIME_UNSET) {
            long uptimeMillis = j - SystemClock.uptimeMillis();
            if (uptimeMillis <= 0) {
                hide();
            } else {
                postDelayed(this.t, uptimeMillis);
            }
        } else if (isVisible()) {
            a();
        }
        b();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.J = false;
        removeCallbacks(this.s);
        removeCallbacks(this.t);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            removeCallbacks(this.t);
        } else if (motionEvent.getAction() == 1) {
            a();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Player player = this.G;
        if (player == null || !a(keyCode)) {
            return false;
        }
        if (keyEvent.getAction() != 0) {
            return true;
        }
        if (keyCode == 90) {
            if (player.getPlaybackState() == 4) {
                return true;
            }
            this.H.dispatchFastForward(player);
            return true;
        } else if (keyCode == 89) {
            this.H.dispatchRewind(player);
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
                    this.H.dispatchNext(player);
                    return true;
                case 88:
                    this.H.dispatchPrevious(player);
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

    private boolean k() {
        Player player = this.G;
        return (player == null || player.getPlaybackState() == 4 || this.G.getPlaybackState() == 1 || !this.G.getPlayWhenReady()) ? false : true;
    }

    private void a(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
            b(player);
        } else {
            c(player);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            this.H.dispatchPrepare(player);
        } else if (playbackState == 4) {
            a(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }
        this.H.dispatchSetPlayWhenReady(player, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Player player) {
        this.H.dispatchSetPlayWhenReady(player, false);
    }

    private static boolean a(Timeline timeline, Timeline.Window window) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            if (timeline.getWindow(i, window).durationUs == C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }

    private static int a(TypedArray typedArray, int i) {
        return typedArray.getInt(R.styleable.PlayerControlView_repeat_toggle_modes, i);
    }

    /* loaded from: classes2.dex */
    private final class b implements View.OnClickListener, Player.Listener, TimeBar.OnScrubListener {
        private b() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(5, 6)) {
                PlayerControlView.this.c();
            }
            if (events.containsAny(5, 6, 8)) {
                PlayerControlView.this.h();
            }
            if (events.contains(9)) {
                PlayerControlView.this.e();
            }
            if (events.contains(10)) {
                PlayerControlView.this.f();
            }
            if (events.containsAny(9, 10, 12, 0, 14)) {
                PlayerControlView.this.d();
            }
            if (events.containsAny(12, 0)) {
                PlayerControlView.this.g();
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStart(TimeBar timeBar, long j) {
            PlayerControlView.this.M = true;
            if (PlayerControlView.this.m != null) {
                PlayerControlView.this.m.setText(Util.getStringForTime(PlayerControlView.this.o, PlayerControlView.this.p, j));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubMove(TimeBar timeBar, long j) {
            if (PlayerControlView.this.m != null) {
                PlayerControlView.this.m.setText(Util.getStringForTime(PlayerControlView.this.o, PlayerControlView.this.p, j));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStop(TimeBar timeBar, long j, boolean z) {
            PlayerControlView.this.M = false;
            if (!z && PlayerControlView.this.G != null) {
                PlayerControlView playerControlView = PlayerControlView.this;
                playerControlView.a(playerControlView.G, j);
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Player player = PlayerControlView.this.G;
            if (player != null) {
                if (PlayerControlView.this.d == view) {
                    PlayerControlView.this.H.dispatchNext(player);
                } else if (PlayerControlView.this.c == view) {
                    PlayerControlView.this.H.dispatchPrevious(player);
                } else if (PlayerControlView.this.g == view) {
                    if (player.getPlaybackState() != 4) {
                        PlayerControlView.this.H.dispatchFastForward(player);
                    }
                } else if (PlayerControlView.this.h == view) {
                    PlayerControlView.this.H.dispatchRewind(player);
                } else if (PlayerControlView.this.e == view) {
                    PlayerControlView.this.b(player);
                } else if (PlayerControlView.this.f == view) {
                    PlayerControlView.this.c(player);
                } else if (PlayerControlView.this.i == view) {
                    PlayerControlView.this.H.dispatchSetRepeatMode(player, RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), PlayerControlView.this.P));
                } else if (PlayerControlView.this.j == view) {
                    PlayerControlView.this.H.dispatchSetShuffleModeEnabled(player, !player.getShuffleModeEnabled());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes2.dex */
    public static final class a {
        @DoNotInline
        public static boolean a(View view) {
            return view.isAccessibilityFocused();
        }
    }
}
