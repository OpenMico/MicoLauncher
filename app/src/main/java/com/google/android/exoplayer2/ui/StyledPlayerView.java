package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public class StyledPlayerView extends FrameLayout implements AdViewProvider {
    public static final int SHOW_BUFFERING_ALWAYS = 2;
    public static final int SHOW_BUFFERING_NEVER = 0;
    public static final int SHOW_BUFFERING_WHEN_PLAYING = 1;
    private boolean A;
    private final a a;
    @Nullable
    private final AspectRatioFrameLayout b;
    @Nullable
    private final View c;
    @Nullable
    private final View d;
    private final boolean e;
    @Nullable
    private final ImageView f;
    @Nullable
    private final SubtitleView g;
    @Nullable
    private final View h;
    @Nullable
    private final TextView i;
    @Nullable
    private final StyledPlayerControlView j;
    @Nullable
    private final FrameLayout k;
    @Nullable
    private final FrameLayout l;
    @Nullable
    private Player m;
    private boolean n;
    @Nullable
    private StyledPlayerControlView.VisibilityListener o;
    private boolean p;
    @Nullable
    private Drawable q;
    private int r;
    private boolean s;
    @Nullable
    private ErrorMessageProvider<? super PlaybackException> t;
    @Nullable
    private CharSequence u;
    private int v;
    private boolean w;
    private boolean x;
    private boolean y;
    private int z;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ShowBuffering {
    }

    @SuppressLint({"InlinedApi"})
    private boolean a(int i) {
        return i == 19 || i == 270 || i == 22 || i == 271 || i == 20 || i == 269 || i == 21 || i == 268 || i == 23;
    }

    public StyledPlayerView(Context context) {
        this(context, null);
    }

    public StyledPlayerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Finally extract failed */
    public StyledPlayerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        int i3;
        boolean z;
        int i4;
        boolean z2;
        int i5;
        boolean z3;
        boolean z4;
        int i6;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        int i7;
        this.a = new a();
        if (isInEditMode()) {
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = false;
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = null;
            this.k = null;
            this.l = null;
            ImageView imageView = new ImageView(context);
            if (Util.SDK_INT >= 23) {
                a(getResources(), imageView);
            } else {
                b(getResources(), imageView);
            }
            addView(imageView);
            return;
        }
        int i8 = R.layout.exo_styled_player_view;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.StyledPlayerView, i, 0);
            try {
                z3 = obtainStyledAttributes.hasValue(R.styleable.StyledPlayerView_shutter_background_color);
                i5 = obtainStyledAttributes.getColor(R.styleable.StyledPlayerView_shutter_background_color, 0);
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.StyledPlayerView_player_layout_id, i8);
                z2 = obtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_use_artwork, true);
                i4 = obtainStyledAttributes.getResourceId(R.styleable.StyledPlayerView_default_artwork, 0);
                z = obtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_use_controller, true);
                i3 = obtainStyledAttributes.getInt(R.styleable.StyledPlayerView_surface_type, 1);
                i2 = obtainStyledAttributes.getInt(R.styleable.StyledPlayerView_resize_mode, 0);
                int i9 = obtainStyledAttributes.getInt(R.styleable.StyledPlayerView_show_timeout, 5000);
                z5 = obtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_hide_on_touch, true);
                boolean z10 = obtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_auto_show, true);
                i6 = obtainStyledAttributes.getInteger(R.styleable.StyledPlayerView_show_buffering, 0);
                this.s = obtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_keep_content_on_player_reset, this.s);
                boolean z11 = obtainStyledAttributes.getBoolean(R.styleable.StyledPlayerView_hide_during_ads, true);
                obtainStyledAttributes.recycle();
                z4 = z11;
                z6 = z10;
                i8 = resourceId;
                i7 = i9;
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            i7 = 5000;
            z6 = true;
            z5 = true;
            i6 = 0;
            z4 = true;
            z3 = false;
            i5 = 0;
            z2 = true;
            i4 = 0;
            z = true;
            i3 = 1;
            i2 = 0;
        }
        LayoutInflater.from(context).inflate(i8, this);
        setDescendantFocusability(262144);
        this.b = (AspectRatioFrameLayout) findViewById(R.id.exo_content_frame);
        AspectRatioFrameLayout aspectRatioFrameLayout = this.b;
        if (aspectRatioFrameLayout != null) {
            a(aspectRatioFrameLayout, i2);
        }
        this.c = findViewById(R.id.exo_shutter);
        View view = this.c;
        if (view != null && z3) {
            view.setBackgroundColor(i5);
        }
        if (this.b == null || i3 == 0) {
            z7 = true;
            this.d = null;
            z8 = false;
        } else {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            switch (i3) {
                case 2:
                    z7 = true;
                    this.d = new TextureView(context);
                    z8 = false;
                    break;
                case 3:
                    try {
                        z7 = true;
                        this.d = (View) Class.forName("com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView").getConstructor(Context.class).newInstance(context);
                        z8 = true;
                        break;
                    } catch (Exception e) {
                        throw new IllegalStateException("spherical_gl_surface_view requires an ExoPlayer dependency", e);
                    }
                case 4:
                    try {
                        this.d = (View) Class.forName("com.google.android.exoplayer2.video.VideoDecoderGLSurfaceView").getConstructor(Context.class).newInstance(context);
                        z7 = true;
                        z8 = false;
                        break;
                    } catch (Exception e2) {
                        throw new IllegalStateException("video_decoder_gl_surface_view requires an ExoPlayer dependency", e2);
                    }
                default:
                    z7 = true;
                    this.d = new SurfaceView(context);
                    z8 = false;
                    break;
            }
            this.d.setLayoutParams(layoutParams);
            this.d.setOnClickListener(this.a);
            this.d.setClickable(false);
            this.b.addView(this.d, 0);
        }
        this.e = z8;
        this.k = (FrameLayout) findViewById(R.id.exo_ad_overlay);
        this.l = (FrameLayout) findViewById(R.id.exo_overlay);
        this.f = (ImageView) findViewById(R.id.exo_artwork);
        this.p = (!z2 || this.f == null) ? false : z7;
        if (i4 != 0) {
            this.q = ContextCompat.getDrawable(getContext(), i4);
        }
        this.g = (SubtitleView) findViewById(R.id.exo_subtitles);
        SubtitleView subtitleView = this.g;
        if (subtitleView != null) {
            subtitleView.setUserDefaultStyle();
            this.g.setUserDefaultTextSize();
        }
        this.h = findViewById(R.id.exo_buffering);
        View view2 = this.h;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        this.r = i6;
        this.i = (TextView) findViewById(R.id.exo_error_message);
        TextView textView = this.i;
        if (textView != null) {
            textView.setVisibility(8);
        }
        StyledPlayerControlView styledPlayerControlView = (StyledPlayerControlView) findViewById(R.id.exo_controller);
        View findViewById = findViewById(R.id.exo_controller_placeholder);
        if (styledPlayerControlView != null) {
            this.j = styledPlayerControlView;
            z9 = false;
        } else if (findViewById != null) {
            z9 = false;
            this.j = new StyledPlayerControlView(context, null, 0, attributeSet);
            this.j.setId(R.id.exo_controller);
            this.j.setLayoutParams(findViewById.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById);
            viewGroup.removeView(findViewById);
            viewGroup.addView(this.j, indexOfChild);
        } else {
            z9 = false;
            this.j = null;
        }
        if (this.j == null) {
            i7 = z9;
        }
        this.v = i7;
        this.y = z5;
        this.w = z6;
        this.x = z4;
        if (z && this.j != null) {
            z9 = z7;
        }
        this.n = z9;
        StyledPlayerControlView styledPlayerControlView2 = this.j;
        if (styledPlayerControlView2 != null) {
            styledPlayerControlView2.hideImmediately();
            this.j.addVisibilityListener(this.a);
        }
        j();
    }

    public static void switchTargetView(Player player, @Nullable StyledPlayerView styledPlayerView, @Nullable StyledPlayerView styledPlayerView2) {
        if (styledPlayerView != styledPlayerView2) {
            if (styledPlayerView2 != null) {
                styledPlayerView2.setPlayer(player);
            }
            if (styledPlayerView != null) {
                styledPlayerView.setPlayer(null);
            }
        }
    }

    @Nullable
    public Player getPlayer() {
        return this.m;
    }

    public void setPlayer(@Nullable Player player) {
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        Assertions.checkArgument(player == null || player.getApplicationLooper() == Looper.getMainLooper());
        Player player2 = this.m;
        if (player2 != player) {
            if (player2 != null) {
                player2.removeListener((Player.Listener) this.a);
                View view = this.d;
                if (view instanceof TextureView) {
                    player2.clearVideoTextureView((TextureView) view);
                } else if (view instanceof SurfaceView) {
                    player2.clearVideoSurfaceView((SurfaceView) view);
                }
            }
            SubtitleView subtitleView = this.g;
            if (subtitleView != null) {
                subtitleView.setCues(null);
            }
            this.m = player;
            if (a()) {
                this.j.setPlayer(player);
            }
            h();
            i();
            c(true);
            if (player != null) {
                if (player.isCommandAvailable(26)) {
                    View view2 = this.d;
                    if (view2 instanceof TextureView) {
                        player.setVideoTextureView((TextureView) view2);
                    } else if (view2 instanceof SurfaceView) {
                        player.setVideoSurfaceView((SurfaceView) view2);
                    }
                    l();
                }
                if (this.g != null && player.isCommandAvailable(27)) {
                    this.g.setCues(player.getCurrentCues());
                }
                player.addListener((Player.Listener) this.a);
                a(false);
                return;
            }
            hideController();
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        View view = this.d;
        if (view instanceof SurfaceView) {
            view.setVisibility(i);
        }
    }

    public void setResizeMode(int i) {
        Assertions.checkStateNotNull(this.b);
        this.b.setResizeMode(i);
    }

    public int getResizeMode() {
        Assertions.checkStateNotNull(this.b);
        return this.b.getResizeMode();
    }

    public boolean getUseArtwork() {
        return this.p;
    }

    public void setUseArtwork(boolean z) {
        Assertions.checkState(!z || this.f != null);
        if (this.p != z) {
            this.p = z;
            c(false);
        }
    }

    @Nullable
    public Drawable getDefaultArtwork() {
        return this.q;
    }

    public void setDefaultArtwork(@Nullable Drawable drawable) {
        if (this.q != drawable) {
            this.q = drawable;
            c(false);
        }
    }

    public boolean getUseController() {
        return this.n;
    }

    public void setUseController(boolean z) {
        Assertions.checkState(!z || this.j != null);
        if (this.n != z) {
            this.n = z;
            if (a()) {
                this.j.setPlayer(this.m);
            } else {
                StyledPlayerControlView styledPlayerControlView = this.j;
                if (styledPlayerControlView != null) {
                    styledPlayerControlView.hide();
                    this.j.setPlayer(null);
                }
            }
            j();
        }
    }

    public void setShutterBackgroundColor(int i) {
        View view = this.c;
        if (view != null) {
            view.setBackgroundColor(i);
        }
    }

    public void setKeepContentOnPlayerReset(boolean z) {
        if (this.s != z) {
            this.s = z;
            c(false);
        }
    }

    public void setShowBuffering(int i) {
        if (this.r != i) {
            this.r = i;
            h();
        }
    }

    public void setErrorMessageProvider(@Nullable ErrorMessageProvider<? super PlaybackException> errorMessageProvider) {
        if (this.t != errorMessageProvider) {
            this.t = errorMessageProvider;
            i();
        }
    }

    public void setCustomErrorMessage(@Nullable CharSequence charSequence) {
        Assertions.checkState(this.i != null);
        this.u = charSequence;
        i();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Player player = this.m;
        if (player != null && player.isPlayingAd()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        boolean a2 = a(keyEvent.getKeyCode());
        if (a2 && a() && !this.j.isFullyVisible()) {
            a(true);
            return true;
        } else if (dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
            a(true);
            return true;
        } else {
            if (a2 && a()) {
                a(true);
            }
            return false;
        }
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        return a() && this.j.dispatchMediaKeyEvent(keyEvent);
    }

    public boolean isControllerFullyVisible() {
        StyledPlayerControlView styledPlayerControlView = this.j;
        return styledPlayerControlView != null && styledPlayerControlView.isFullyVisible();
    }

    public void showController() {
        b(d());
    }

    public void hideController() {
        StyledPlayerControlView styledPlayerControlView = this.j;
        if (styledPlayerControlView != null) {
            styledPlayerControlView.hide();
        }
    }

    public int getControllerShowTimeoutMs() {
        return this.v;
    }

    public void setControllerShowTimeoutMs(int i) {
        Assertions.checkStateNotNull(this.j);
        this.v = i;
        if (this.j.isFullyVisible()) {
            showController();
        }
    }

    public boolean getControllerHideOnTouch() {
        return this.y;
    }

    public void setControllerHideOnTouch(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.y = z;
        j();
    }

    public boolean getControllerAutoShow() {
        return this.w;
    }

    public void setControllerAutoShow(boolean z) {
        this.w = z;
    }

    public void setControllerHideDuringAds(boolean z) {
        this.x = z;
    }

    public void setControllerVisibilityListener(@Nullable StyledPlayerControlView.VisibilityListener visibilityListener) {
        Assertions.checkStateNotNull(this.j);
        StyledPlayerControlView.VisibilityListener visibilityListener2 = this.o;
        if (visibilityListener2 != visibilityListener) {
            if (visibilityListener2 != null) {
                this.j.removeVisibilityListener(visibilityListener2);
            }
            this.o = visibilityListener;
            if (visibilityListener != null) {
                this.j.addVisibilityListener(visibilityListener);
            }
        }
    }

    public void setControllerOnFullScreenModeChangedListener(@Nullable StyledPlayerControlView.OnFullScreenModeChangedListener onFullScreenModeChangedListener) {
        Assertions.checkStateNotNull(this.j);
        this.j.setOnFullScreenModeChangedListener(onFullScreenModeChangedListener);
    }

    @Deprecated
    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        Assertions.checkStateNotNull(this.j);
        this.j.setControlDispatcher(controlDispatcher);
    }

    public void setShowRewindButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowRewindButton(z);
    }

    public void setShowFastForwardButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowFastForwardButton(z);
    }

    public void setShowPreviousButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowPreviousButton(z);
    }

    public void setShowNextButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowNextButton(z);
    }

    public void setRepeatToggleModes(int i) {
        Assertions.checkStateNotNull(this.j);
        this.j.setRepeatToggleModes(i);
    }

    public void setShowShuffleButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowShuffleButton(z);
    }

    public void setShowSubtitleButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowSubtitleButton(z);
    }

    public void setShowVrButton(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowVrButton(z);
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        Assertions.checkStateNotNull(this.j);
        this.j.setShowMultiWindowTimeBar(z);
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        Assertions.checkStateNotNull(this.j);
        this.j.setExtraAdGroupMarkers(jArr, zArr);
    }

    public void setAspectRatioListener(@Nullable AspectRatioFrameLayout.AspectRatioListener aspectRatioListener) {
        Assertions.checkStateNotNull(this.b);
        this.b.setAspectRatioListener(aspectRatioListener);
    }

    @Nullable
    public View getVideoSurfaceView() {
        return this.d;
    }

    @Nullable
    public FrameLayout getOverlayFrameLayout() {
        return this.l;
    }

    @Nullable
    public SubtitleView getSubtitleView() {
        return this.g;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!a() || this.m == null) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.A = true;
                return true;
            case 1:
                if (!this.A) {
                    return false;
                }
                this.A = false;
                return performClick();
            default:
                return false;
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        return c();
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (!a() || this.m == null) {
            return false;
        }
        a(true);
        return true;
    }

    public void onResume() {
        View view = this.d;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onResume();
        }
    }

    public void onPause() {
        View view = this.d;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onPause();
        }
    }

    protected void onContentAspectRatioChanged(@Nullable AspectRatioFrameLayout aspectRatioFrameLayout, float f) {
        if (aspectRatioFrameLayout != null) {
            aspectRatioFrameLayout.setAspectRatio(f);
        }
    }

    @Override // com.google.android.exoplayer2.ui.AdViewProvider
    public ViewGroup getAdViewGroup() {
        return (ViewGroup) Assertions.checkStateNotNull(this.k, "exo_ad_overlay must be present for ad playback");
    }

    @Override // com.google.android.exoplayer2.ui.AdViewProvider
    public List<AdOverlayInfo> getAdOverlayInfos() {
        ArrayList arrayList = new ArrayList();
        FrameLayout frameLayout = this.l;
        if (frameLayout != null) {
            arrayList.add(new AdOverlayInfo(frameLayout, 3, "Transparent overlay does not impact viewability"));
        }
        StyledPlayerControlView styledPlayerControlView = this.j;
        if (styledPlayerControlView != null) {
            arrayList.add(new AdOverlayInfo(styledPlayerControlView, 0));
        }
        return ImmutableList.copyOf((Collection) arrayList);
    }

    @EnsuresNonNullIf(expression = {"controller"}, result = true)
    private boolean a() {
        if (!this.n) {
            return false;
        }
        Assertions.checkStateNotNull(this.j);
        return true;
    }

    @EnsuresNonNullIf(expression = {"artworkView"}, result = true)
    private boolean b() {
        if (!this.p) {
            return false;
        }
        Assertions.checkStateNotNull(this.f);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        if (!a() || this.m == null) {
            return false;
        }
        if (!this.j.isFullyVisible()) {
            a(true);
            return true;
        } else if (!this.y) {
            return false;
        } else {
            this.j.hide();
            return true;
        }
    }

    private void a(boolean z) {
        if ((!e() || !this.x) && a()) {
            boolean z2 = this.j.isFullyVisible() && this.j.getShowTimeoutMs() <= 0;
            boolean d = d();
            if (z || z2 || d) {
                b(d);
            }
        }
    }

    private boolean d() {
        Player player = this.m;
        if (player == null) {
            return true;
        }
        int playbackState = player.getPlaybackState();
        return this.w && !this.m.getCurrentTimeline().isEmpty() && (playbackState == 1 || playbackState == 4 || !((Player) Assertions.checkNotNull(this.m)).getPlayWhenReady());
    }

    private void b(boolean z) {
        if (a()) {
            this.j.setShowTimeoutMs(z ? 0 : this.v);
            this.j.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        Player player = this.m;
        return player != null && player.isPlayingAd() && this.m.getPlayWhenReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(boolean z) {
        Player player = this.m;
        if (player != null && !player.getCurrentTrackGroups().isEmpty()) {
            if (z && !this.s) {
                g();
            }
            TrackSelectionArray currentTrackSelections = player.getCurrentTrackSelections();
            for (int i = 0; i < currentTrackSelections.length; i++) {
                TrackSelection trackSelection = currentTrackSelections.get(i);
                if (trackSelection != null) {
                    for (int i2 = 0; i2 < trackSelection.length(); i2++) {
                        if (MimeTypes.getTrackType(trackSelection.getFormat(i2).sampleMimeType) == 2) {
                            f();
                            return;
                        }
                    }
                    continue;
                }
            }
            g();
            if (!b() || (!a(player.getMediaMetadata()) && !a(this.q))) {
                f();
            }
        } else if (!this.s) {
            f();
            g();
        }
    }

    @RequiresNonNull({"artworkView"})
    private boolean a(MediaMetadata mediaMetadata) {
        if (mediaMetadata.artworkData == null) {
            return false;
        }
        return a(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(mediaMetadata.artworkData, 0, mediaMetadata.artworkData.length)));
    }

    @RequiresNonNull({"artworkView"})
    private boolean a(@Nullable Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                onContentAspectRatioChanged(this.b, intrinsicWidth / intrinsicHeight);
                this.f.setImageDrawable(drawable);
                this.f.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    private void f() {
        ImageView imageView = this.f;
        if (imageView != null) {
            imageView.setImageResource(17170445);
            this.f.setVisibility(4);
        }
    }

    private void g() {
        View view = this.c;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        int i;
        if (this.h != null) {
            Player player = this.m;
            boolean z = true;
            int i2 = 0;
            if (player == null || player.getPlaybackState() != 2 || ((i = this.r) != 2 && (i != 1 || !this.m.getPlayWhenReady()))) {
                z = false;
            }
            View view = this.h;
            if (!z) {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        ErrorMessageProvider<? super PlaybackException> errorMessageProvider;
        TextView textView = this.i;
        if (textView != null) {
            CharSequence charSequence = this.u;
            if (charSequence != null) {
                textView.setText(charSequence);
                this.i.setVisibility(0);
                return;
            }
            Player player = this.m;
            PlaybackException playerError = player != null ? player.getPlayerError() : null;
            if (playerError == null || (errorMessageProvider = this.t) == null) {
                this.i.setVisibility(8);
                return;
            }
            this.i.setText((CharSequence) errorMessageProvider.getErrorMessage(playerError).second);
            this.i.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        StyledPlayerControlView styledPlayerControlView = this.j;
        String str = null;
        if (styledPlayerControlView == null || !this.n) {
            setContentDescription(null);
        } else if (styledPlayerControlView.isFullyVisible()) {
            if (this.y) {
                str = getResources().getString(R.string.exo_controls_hide);
            }
            setContentDescription(str);
        } else {
            setContentDescription(getResources().getString(R.string.exo_controls_show));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        if (!e() || !this.x) {
            a(false);
        } else {
            hideController();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        Player player = this.m;
        VideoSize videoSize = player != null ? player.getVideoSize() : VideoSize.UNKNOWN;
        int i = videoSize.width;
        int i2 = videoSize.height;
        int i3 = videoSize.unappliedRotationDegrees;
        float f = (i2 == 0 || i == 0) ? 0.0f : (i * videoSize.pixelWidthHeightRatio) / i2;
        if (this.d instanceof TextureView) {
            if (f > 0.0f && (i3 == 90 || i3 == 270)) {
                f = 1.0f / f;
            }
            if (this.z != 0) {
                this.d.removeOnLayoutChangeListener(this.a);
            }
            this.z = i3;
            if (this.z != 0) {
                this.d.addOnLayoutChangeListener(this.a);
            }
            b((TextureView) this.d, this.z);
        }
        AspectRatioFrameLayout aspectRatioFrameLayout = this.b;
        if (this.e) {
            f = 0.0f;
        }
        onContentAspectRatioChanged(aspectRatioFrameLayout, f);
    }

    @RequiresApi(23)
    private static void a(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, null));
        imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color, null));
    }

    private static void b(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo));
        imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color));
    }

    private static void a(AspectRatioFrameLayout aspectRatioFrameLayout, int i) {
        aspectRatioFrameLayout.setResizeMode(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(TextureView textureView, int i) {
        Matrix matrix = new Matrix();
        float width = textureView.getWidth();
        float height = textureView.getHeight();
        if (!(width == 0.0f || height == 0.0f || i == 0)) {
            float f = width / 2.0f;
            float f2 = height / 2.0f;
            matrix.postRotate(i, f, f2);
            RectF rectF = new RectF(0.0f, 0.0f, width, height);
            RectF rectF2 = new RectF();
            matrix.mapRect(rectF2, rectF);
            matrix.postScale(width / rectF2.width(), height / rectF2.height(), f, f2);
        }
        textureView.setTransform(matrix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a implements View.OnClickListener, View.OnLayoutChangeListener, Player.Listener, StyledPlayerControlView.VisibilityListener {
        private final Timeline.Period b = new Timeline.Period();
        @Nullable
        private Object c;

        public a() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> list) {
            if (StyledPlayerView.this.g != null) {
                StyledPlayerView.this.g.onCues(list);
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            StyledPlayerView.this.l();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onRenderedFirstFrame() {
            if (StyledPlayerView.this.c != null) {
                StyledPlayerView.this.c.setVisibility(4);
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            Player player = (Player) Assertions.checkNotNull(StyledPlayerView.this.m);
            Timeline currentTimeline = player.getCurrentTimeline();
            if (currentTimeline.isEmpty()) {
                this.c = null;
            } else if (!player.getCurrentTrackGroups().isEmpty()) {
                this.c = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), this.b, true).uid;
            } else {
                Object obj = this.c;
                if (obj != null) {
                    int indexOfPeriod = currentTimeline.getIndexOfPeriod(obj);
                    if (indexOfPeriod == -1 || player.getCurrentWindowIndex() != currentTimeline.getPeriod(indexOfPeriod, this.b).windowIndex) {
                        this.c = null;
                    } else {
                        return;
                    }
                }
            }
            StyledPlayerView.this.c(false);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i) {
            StyledPlayerView.this.h();
            StyledPlayerView.this.i();
            StyledPlayerView.this.k();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean z, int i) {
            StyledPlayerView.this.h();
            StyledPlayerView.this.k();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            if (StyledPlayerView.this.e() && StyledPlayerView.this.x) {
                StyledPlayerView.this.hideController();
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            StyledPlayerView.b((TextureView) view, StyledPlayerView.this.z);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            StyledPlayerView.this.c();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.VisibilityListener
        public void onVisibilityChange(int i) {
            StyledPlayerView.this.j();
        }
    }
}
