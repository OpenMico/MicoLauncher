package com.xiaomi.micolauncher.skills.video.player.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.player.utils.Utils;
import com.xiaomi.micolauncher.skills.video.player.widget.VideoControlBar;
import com.xiaomi.micolauncher.skills.video.view.ShortVideoAdapter;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoController extends RelativeLayout implements VideoControlBar.VideoControlBarActionListener {
    private static final long l = TimeUnit.SECONDS.toMillis(7);
    GestureDetector a;
    private VideoControllerControl c;
    private TextView d;
    private PlayerLoadingView e;
    private RecyclerView f;
    private TextView g;
    private VideoControlBar h;
    private int i;
    private int j;
    private ShortVideoAdapter k;
    private boolean m;
    private boolean o;
    private long r;
    private boolean n = false;
    private boolean p = false;
    private boolean q = false;
    GestureDetector.OnGestureListener b = new GestureDetector.OnGestureListener() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.VideoController.1
        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            L.video.d("OnGestureListener onDown");
            return true;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            L.video.d("OnGestureListener onSingleTapUp");
            VideoController.this.onClickView();
            return true;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!VideoController.this.m) {
                float rawX = motionEvent.getRawX() - motionEvent2.getRawX();
                if (Math.abs(motionEvent.getRawY() - motionEvent2.getRawY()) > 100.0f) {
                    return false;
                }
                if (rawX > 200.0f || f < -1500.0f) {
                    VideoController.this.h();
                    return true;
                } else if (rawX < -200.0f || f > 1500.0f) {
                    VideoController.this.i();
                    return true;
                }
            }
            return false;
        }
    };
    private final Runnable s = new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$VideoController$G24keotIpegfVVGpMLXVuRme0_Y
        @Override // java.lang.Runnable
        public final void run() {
            VideoController.this.j();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void j() {
        hideTitleAndControlBar();
        hideVideoList();
    }

    public VideoController(Context context) {
        super(context);
        a(context);
    }

    public VideoController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        a(context);
    }

    public VideoController(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.video_controllor, (ViewGroup) this, true);
        this.d = (TextView) findViewById(R.id.video_title);
        this.e = (PlayerLoadingView) findViewById(R.id.loading_view);
        this.f = (RecyclerView) findViewById(R.id.serial_recycler_view);
        this.g = (TextView) findViewById(R.id.error_tips);
        this.h = (VideoControlBar) findViewById(R.id.play_control_bar);
        this.i = getContext().getResources().getDimensionPixelSize(R.dimen.video_player_serial_recycler_margin_start);
        this.j = getContext().getResources().getDimensionPixelSize(R.dimen.video_player_serial_recycler_space);
        this.f.setLayoutManager(new LinearLayoutManager(getContext(), 0, false) { // from class: com.xiaomi.micolauncher.skills.video.player.widget.VideoController.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    L.video.i("onLayoutChildren exception : %s", e);
                }
            }
        });
        this.f.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.VideoController.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view) < 1) {
                    rect.left = VideoController.this.i;
                } else {
                    rect.left = VideoController.this.j;
                }
            }
        });
        this.f.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.VideoController.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0) {
                    VideoController.this.startAutoDismiss();
                    if (VideoController.this.k != null) {
                        VideoController.this.k.notifyExpose();
                    }
                } else if (i == 1) {
                    VideoController.this.clearAutoDismiss();
                }
            }
        });
        this.e.setTrafficStatsUid(Utils.getUid(context, context.getPackageName()));
        a();
        initControlBar();
        this.a = new GestureDetector(this.b);
        this.d.setSelected(true);
        this.d.setTypeface(Typeface.defaultFromStyle(1));
    }

    private void a() {
        this.k = new ShortVideoAdapter();
        this.f.setAdapter(this.k);
    }

    protected void initControlBar() {
        this.h.setController(this.c);
        this.h.setVideoControlBarActionListener(this);
    }

    public boolean isShowingTitleAndControlBar() {
        return this.m;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        L.video.i("onDetachedFromWindow");
        super.onDetachedFromWindow();
        f();
        clearAutoDismiss();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.a.onTouchEvent(motionEvent);
    }

    public void attachPlayerControl(VideoControllerControl videoControllerControl) {
        this.c = videoControllerControl;
        this.h.setController(videoControllerControl);
    }

    public void onClickView() {
        L.video.d("VideoController onClickView");
        if (isShowingTitleAndControlBar()) {
            hideTitleAndControlBar();
            hideVideoList();
            if (!this.o) {
                e();
                return;
            }
            return;
        }
        showTitleAndControlBar();
        if (!this.n) {
            d();
        } else if (b()) {
            d();
            f();
        }
    }

    private boolean b() {
        return this.r > 0 && System.currentTimeMillis() - this.r > 3000;
    }

    public void showTitleAndStartAutoDismiss() {
        L.video.d("VideoController showTitleAndStartAutoDismiss");
        if (this.q) {
            hideTitle();
        } else {
            showTitle();
        }
        hideControlBar();
        startAutoDismiss();
    }

    public void showTitleAndControlBar() {
        L.video.d("VideoController showTitleAndControlBar");
        if (this.q) {
            hideTitle();
        } else {
            showTitle();
        }
        c();
        startAutoDismiss();
    }

    public void hideControlBar() {
        L.video.d("VideoController hideControlBar");
        this.m = false;
        this.h.hide();
    }

    private void c() {
        L.video.d("VideoController showControlBar");
        this.h.show();
        this.m = true;
    }

    public void hideTitleAndControlBar() {
        L.video.d("VideoController hideTitleAndControlBar");
        clearAutoDismiss();
        hideTitle();
        hideControlBar();
    }

    private void d() {
        L.video.d("VideoController showVideoList");
        if (this.k == null) {
            return;
        }
        if (!VideoModel.getInstance().isLongVideo()) {
            this.k.update();
            int playIndex = VideoModel.getInstance().getPlayIndex();
            if (this.k.getItemCount() > playIndex) {
                this.f.scrollToPosition(playIndex);
            }
            this.f.setVisibility(0);
            return;
        }
        this.f.setVisibility(8);
    }

    public void hideVideoList() {
        L.video.d("VideoController hideVideoList");
        RecyclerView recyclerView = this.f;
        if (recyclerView != null) {
            recyclerView.setVisibility(8);
        }
    }

    public void startLoading() {
        L.video.d("VideoController startLoading");
        this.o = false;
        this.r = System.currentTimeMillis();
        e();
    }

    public void stopLoading() {
        L.video.d("VideoController stopLoading");
        this.o = true;
        f();
    }

    private void e() {
        L.video.d("VideoController showLoadingView");
        if (!this.n) {
            if (this.e.getVisibility() == 4 || this.e.getVisibility() == 8) {
                this.e.show(true);
            }
            hideVideoList();
            this.n = true;
        }
    }

    private void f() {
        L.video.d("VideoController hideLoadingView");
        PlayerLoadingView playerLoadingView = this.e;
        if (playerLoadingView != null) {
            playerLoadingView.hide();
            this.n = false;
        }
    }

    public void setTitleText(String str) {
        TextView textView = this.d;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void showTitle() {
        L.video.d("VideoController showTitle");
        TextView textView = this.d;
        if (textView != null) {
            textView.setVisibility(0);
        }
    }

    public void hideTitle() {
        L.video.d("VideoController hideTitle");
        TextView textView = this.d;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void setTitleVisibility(boolean z) {
        Logger logger = L.video;
        logger.i("VideoController setTitleVisibility=" + z);
        this.q = z;
        if (z) {
            hideTitle();
        } else {
            showTitle();
        }
    }

    public void startAutoDismiss() {
        clearAutoDismiss();
        postDelayed(this.s, l);
    }

    public void clearAutoDismiss() {
        removeCallbacks(this.s);
    }

    public void onPrepared(int i) {
        L.video.d("VideoController onPrepared");
        this.h.setDuration(i);
    }

    public void resetControlBar() {
        VideoControlBar videoControlBar = this.h;
        if (videoControlBar != null) {
            videoControlBar.reset(false);
        }
    }

    public void onVideoPaused() {
        this.p = true;
        this.h.setPlayButtonState(false);
        Logger logger = L.video;
        logger.i("VideoController pause mVideoPause:" + this.p);
        f();
    }

    public void onVideoStart() {
        this.p = false;
        this.h.setPlayButtonState(true);
        Logger logger = L.video;
        logger.i("VideoController start mVideoPause:" + this.p);
    }

    public void updateVideoList() {
        ShortVideoAdapter shortVideoAdapter = this.k;
        if (shortVideoAdapter != null) {
            shortVideoAdapter.update();
        }
    }

    private void g() {
        L.video.i("VideoController pauseOrStart");
        VideoControllerControl videoControllerControl = this.c;
        if (videoControllerControl != null) {
            if (videoControllerControl.isPlaying() || this.n) {
                VideoPlayerApi.pause();
                if (this.h.getVisibility() == 0) {
                    clearAutoDismiss();
                    return;
                }
                return;
            }
            VideoPlayerApi.play();
            if (this.h.getVisibility() == 0) {
                hideTitleAndControlBar();
                hideVideoList();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        L.video.i("VideoController doPlayNext");
        VideoPlayerApi.next();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        L.video.i("VideoController doPlayPre");
        VideoPlayerApi.prev();
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControlBar.VideoControlBarActionListener
    public void onClickPlayButton() {
        L.video.i("VideoController onClickPlayButton");
        g();
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControlBar.VideoControlBarActionListener
    public void onStartTracking() {
        clearAutoDismiss();
        hideVideoList();
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControlBar.VideoControlBarActionListener
    public void onProgressChanged(int i) {
        VideoControllerControl videoControllerControl = this.c;
        if (videoControllerControl != null && videoControllerControl.isPlaying()) {
            L.video.d("PlayerSeekBar mediaPlayerControl.isPlaying");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControlBar.VideoControlBarActionListener
    public void onStopTracking(int i, boolean z, boolean z2) {
        if (this.c != null) {
            if (z) {
                VideoPlayerApi.next();
            } else {
                VideoPlayerApi.seekToPos(i, z2);
            }
            e();
            startAutoDismiss();
        }
    }
}
