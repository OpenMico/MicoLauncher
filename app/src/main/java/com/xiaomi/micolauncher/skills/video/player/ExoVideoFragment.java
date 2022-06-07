package com.xiaomi.micolauncher.skills.video.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentActivity;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.base.DialogActivity;
import com.xiaomi.micolauncher.common.player.base.ExoPlayerUtil;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.child.view.ChildDialog;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.player.ExoVideoFragment;
import com.xiaomi.micolauncher.skills.video.player.model.OnlineUri;
import com.xiaomi.micolauncher.skills.video.player.widget.VideoController;
import com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl;
import com.xiaomi.micolauncher.skills.video.view.SelectionListFragment;

/* loaded from: classes3.dex */
public class ExoVideoFragment extends BaseFragment implements IVideoPlayerAction, IVideoPlayerView, VideoControllerControl {
    private VideoController a;
    private PlayerView b;
    protected ChildDialog childDialog;
    private FrameLayout d;
    private VideoPlayerPresenter e;
    private boolean f;
    private SimpleExoPlayer i;
    private MediaSourceFactory j;
    private SelectionListFragment k;
    private Context c = null;
    private volatile int g = 0;
    private volatile int h = 0;

    static /* synthetic */ int d(ExoVideoFragment exoVideoFragment) {
        int i = exoVideoFragment.h;
        exoVideoFragment.h = i + 1;
        return i;
    }

    static /* synthetic */ int g(ExoVideoFragment exoVideoFragment) {
        int i = exoVideoFragment.g;
        exoVideoFragment.g = i + 1;
        return i;
    }

    public static ExoVideoFragment newInstance(Intent intent) {
        ExoVideoFragment exoVideoFragment = new ExoVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putAll(intent.getExtras());
        exoVideoFragment.setArguments(bundle);
        return exoVideoFragment;
    }

    private ExoVideoFragment() {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.d = (FrameLayout) layoutInflater.inflate(R.layout.fragment_video, viewGroup, false);
        this.a = (VideoController) this.d.findViewById(R.id.video_controller);
        this.b = (PlayerView) this.d.findViewById(R.id.video_player_view);
        if (VideoMode.MITV_SERIAL.equals(VideoModel.getInstance().getMode())) {
            this.e = new MiTvPlayerPresenter(getContext(), this);
        } else {
            this.e = new VideoPlayerPresenter(getContext(), this);
        }
        a();
        this.a.setTitleVisibility(getArguments().getBoolean(BaikeModel.KEY_HIDE_TITLE, false));
        initPlay();
        return this.d;
    }

    private void a() {
        ExoPlayerUtil.getInstance();
        this.j = new DefaultMediaSourceFactory(ExoPlayerUtil.createDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "MicoApplication"), null));
        if (this.i == null) {
            this.i = new SimpleExoPlayer.Builder(getContext()).build();
            this.i.addListener(new a());
            this.i.setThrowsWhenUsingWrongThread(true);
            this.i.setPlayWhenReady(true);
            this.b.setPlayer(this.i);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.c = context;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        VideoPlayerPresenter videoPlayerPresenter = this.e;
        if (videoPlayerPresenter != null) {
            videoPlayerPresenter.a();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        VideoPlayerPresenter videoPlayerPresenter = this.e;
        if (videoPlayerPresenter != null) {
            videoPlayerPresenter.b();
            this.e.fakePlayerStop();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        b();
        VideoPlayerPresenter videoPlayerPresenter = this.e;
        if (videoPlayerPresenter != null) {
            videoPlayerPresenter.c();
            this.e = null;
        }
        ChildDialog childDialog = this.childDialog;
        if (childDialog != null && childDialog.isShowing()) {
            this.childDialog.dismiss();
            this.childDialog = null;
        }
    }

    private void a(long j) {
        int currentPosition = getCurrentPosition();
        int duration = getDuration();
        int i = (int) (currentPosition + j);
        if (i > duration) {
            i = duration;
        }
        seekTo(i);
    }

    private void b(long j) {
        int currentPosition = (int) (getCurrentPosition() - j);
        if (currentPosition < 0) {
            currentPosition = 0;
        }
        seekTo(currentPosition);
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public void seekTo(int i) {
        L.video.i("ExoVideoFragment seekTo=%s isPlaying=%s", Integer.valueOf(i), Boolean.valueOf(this.f));
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.seekTo(i);
            this.i.setPlayWhenReady(this.f);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void updateUI(OnlineUri onlineUri) {
        a(onlineUri.uri);
        VideoController videoController = this.a;
        if (videoController != null) {
            videoController.setTitleText(onlineUri.title);
            this.a.showTitleAndStartAutoDismiss();
            this.a.resetControlBar();
            this.a.attachPlayerControl(this);
        }
    }

    private void a(String str) {
        this.i.setMediaSource(this.j.createMediaSource(MediaItem.fromUri(str)));
        this.i.prepare();
        this.i.setPlayWhenReady(true);
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void showLoadingView() {
        VideoController videoController = this.a;
        if (videoController != null) {
            videoController.startLoading();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void hideLoadingView() {
        VideoController videoController = this.a;
        if (videoController != null) {
            videoController.stopLoading();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void showControllerView() {
        VideoController videoController = this.a;
        if (videoController != null) {
            videoController.onClickView();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void updateTitle(String str) {
        L.video.d("ExoVideoFragment updateTitle=%s", str);
        if (this.a != null && !TextUtils.isEmpty(str)) {
            this.a.setTitleText(str);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void updateVideoList() {
        VideoController videoController = this.a;
        if (videoController != null) {
            videoController.updateVideoList();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void quitPlaying() {
        if (getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            ((VideoPlayerActivity) getActivity()).closeActivity(true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onFastForwardVideo(long j) {
        a(j);
        this.e.reportVideoPlayEvent(VideoTrackHelper.SWITCH_TYPE_FORWARD);
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onRewindVideo(long j) {
        b(j);
        this.e.reportVideoPlayEvent(VideoTrackHelper.SWITCH_TYPE_REWIND);
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onSeekVideo(int i) {
        if (i < 0) {
            i = 0;
        }
        seekTo(i * 1000);
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onRestartVideo() {
        seekTo(0);
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onSeekVideoPos(int i, boolean z) {
        seekTo(i);
        if (z) {
            this.e.reportVideoPlayEvent(VideoTrackHelper.SWITCH_TYPE_FORWARD);
        } else {
            this.e.reportVideoPlayEvent(VideoTrackHelper.SWITCH_TYPE_REWIND);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onStopVideo() {
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
        }
        EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_STOP));
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView, com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public int getCurrentPosition() {
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            return (int) simpleExoPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public void openSelectionList() {
        if (this.k == null) {
            this.k = SelectionListFragment.newInstance();
            this.k.setStyle(0, R.style.Dialog_FullScreen);
            this.k.setCancelable(true);
        }
        if (!this.k.isAdded() && !this.k.isRemoving() && !this.k.isVisible()) {
            this.k.showNow(getChildFragmentManager(), "SelectionList");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public void next() {
        this.e.playNext();
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView, com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public boolean isPlaying() {
        if (this.i != null) {
            return this.f;
        }
        return false;
    }

    public void setPlaying(boolean z) {
        this.f = z;
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public void pause() {
        onPauseVideo();
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onPauseVideo() {
        L.video.d("ExoVideoFragment onPauseVideo");
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            this.f = false;
            simpleExoPlayer.setPlayWhenReady(false);
            VideoController videoController = this.a;
            if (videoController != null) {
                videoController.onVideoPaused();
            }
            EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_PAUSE));
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void onStartVideo() {
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            this.f = true;
            if (simpleExoPlayer.getPlaybackState() != 1 && this.i.getPlaybackState() == 4) {
                SimpleExoPlayer simpleExoPlayer2 = this.i;
                simpleExoPlayer2.seekTo(simpleExoPlayer2.getCurrentWindowIndex(), C.TIME_UNSET);
            }
            this.i.setPlayWhenReady(true);
            EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_PLAYING));
            VideoController videoController = this.a;
            if (videoController != null) {
                videoController.onVideoStart();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void handlePlayError(int i, int i2, Throwable th) {
        String str;
        L.video.e("ExoVideoFragment handlePlayError -- what: %s, extra: %s, throwable: %s", Integer.valueOf(i), Integer.valueOf(i2), th);
        if (isAdded()) {
            if (i2 == -1004) {
                str = this.c.getString(R.string.setting_update_network_broken);
            } else {
                str = this.c.getString(R.string.vp_error_tips, Integer.valueOf(i), Integer.valueOf(i2));
            }
            Intent intent = new Intent(MicoApplication.getGlobalContext(), DialogActivity.class);
            intent.putExtra(DialogActivity.KEY_DIALOG_MESSAGE, str);
            intent.putExtra(DialogActivity.KEY_DIALOG_CONFIRM_BUTTON, getString(R.string.button_ok));
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.startActivity(intent);
                activity.finish();
            }
            this.e.c();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView, com.xiaomi.micolauncher.skills.video.player.widget.VideoControllerControl
    public int getDuration() {
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            return (int) simpleExoPlayer.getDuration();
        }
        return 0;
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void showVipDialog() {
        int i;
        int i2;
        int i3;
        if (ChildVideo.MiTvType.CHILD_VIDEO.equals(VideoModel.getInstance().getMiTvType())) {
            i3 = R.string.child_vip_title;
            i2 = R.string.child_vip_popup_subtitle;
            i = R.drawable.child_vip_popup_crown;
        } else {
            i3 = R.string.course_vip_title;
            i2 = R.string.course_vip_popup_subtitle;
            i = R.drawable.img_eduyvip_popup_crown;
        }
        showChildDialog(i3, i2, R.string.child_vip_popup_no, R.string.child_vip_popup_ok, i, new ChildDialog.OnDialogClickListener() { // from class: com.xiaomi.micolauncher.skills.video.player.ExoVideoFragment.1
            @Override // com.xiaomi.micolauncher.module.child.view.ChildDialog.OnDialogClickListener
            public void onCancelClickListener() {
                ExoVideoFragment.this.quitPlaying();
                ChildStatHelper.recordChildVideoVipPopClick(false);
            }

            @Override // com.xiaomi.micolauncher.module.child.view.ChildDialog.OnDialogClickListener
            public void onConfirmClickListener() {
                Context context = ExoVideoFragment.this.getContext();
                ChildStatHelper.recordChildVideoVipPopClick(true);
                Intent intent = new Intent(context, MiTvVipActivity.class);
                intent.putExtra(MiTvVipActivity.INTENT_EXTRA_MI_TV_TYPE, VideoModel.getInstance().getMiTvType());
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 291);
                } else {
                    ActivityLifeCycleManager.startActivity(context, intent, 291, false);
                }
            }
        });
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerView
    public void showSevenVipDialog() {
        showChildDialog(R.string.child_vip_seven_title, R.string.child_vip_seven_popup_subtitle, R.string.child_vip_seven_popup_no, R.string.child_vip_seven_popup_ok, 0, new ChildDialog.OnDialogClickListener() { // from class: com.xiaomi.micolauncher.skills.video.player.ExoVideoFragment.2
            @Override // com.xiaomi.micolauncher.module.child.view.ChildDialog.OnDialogClickListener
            public void onCancelClickListener() {
                L.video.i("cancel get seven day vip,dialog show time ++");
                SystemSetting.setKeyGetSevenVipDialogShowTimesIncrease();
                ExoVideoFragment.this.quitPlaying();
            }

            @Override // com.xiaomi.micolauncher.module.child.view.ChildDialog.OnDialogClickListener
            public void onConfirmClickListener() {
                L.video.i("get seven day vip now");
                if (ExoVideoFragment.this.e != null) {
                    ((MiTvPlayerPresenter) ExoVideoFragment.this.e).getSevenDayVip();
                }
            }
        });
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerAction
    public void handleIntent(Intent intent) {
        if (this.c != null) {
            this.a.setTitleVisibility(intent.getBooleanExtra(BaikeModel.KEY_HIDE_TITLE, false));
        }
    }

    @Override // com.xiaomi.micolauncher.skills.video.player.IVideoPlayerAction
    public void initPlay() {
        L.video.d("ExoVideoFragment initPlay.");
        VideoPlayerPresenter videoPlayerPresenter = this.e;
        if (videoPlayerPresenter != null) {
            videoPlayerPresenter.initTrackHelper();
            this.e.playByIndex(VideoModel.getInstance().getPlayIndex());
            return;
        }
        L.video.w("ExoVideoFragment video item null.");
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        this.e.handleActivityResult(i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a implements Player.EventListener {
        private a() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i) {
            L.video.d("ExoVideoFragment onPlaybackStateChanged=%s", Integer.valueOf(i));
            switch (i) {
                case 1:
                    ExoVideoFragment.this.f = false;
                    L.video.i("onPlaybackStateChanged idle onVideoPaused");
                    ExoVideoFragment.this.a.onVideoPaused();
                    return;
                case 2:
                    ExoVideoFragment.this.showLoadingView();
                    return;
                case 3:
                    ExoVideoFragment.this.a.onPrepared(ExoVideoFragment.this.getDuration());
                    ExoVideoFragment.this.a.hideControlBar();
                    if (ExoVideoFragment.this.f) {
                        ExoVideoFragment.this.a.onVideoStart();
                        ExoVideoFragment.this.e.setPaused(false);
                    } else {
                        ExoVideoFragment.this.a.onVideoPaused();
                    }
                    ExoVideoFragment.this.a.hideVideoList();
                    ExoVideoFragment.this.hideLoadingView();
                    return;
                case 4:
                    ExoVideoFragment.this.f = false;
                    L.video.i("OnCompletionListener onCompletion");
                    ExoVideoFragment.this.a.resetControlBar();
                    ExoVideoFragment.this.e.completionVideo();
                    return;
                default:
                    return;
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(final PlaybackException playbackException) {
            L.video.e("ExoVideoFragment onPlayerError=", playbackException);
            if (ExoVideoFragment.this.e == null) {
                ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$ExoVideoFragment$a$Zk8YWli6lB7lKPhuhLhajUUSqrM
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExoVideoFragment.a.this.c(playbackException);
                    }
                }, 150L);
                return;
            }
            ExoVideoFragment.d(ExoVideoFragment.this);
            if (ExoVideoFragment.this.e.isPaused()) {
                L.video.i("handle play error, was paused");
                ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$ExoVideoFragment$a$AEp_62WU2R3jBYt8LpyWYWuVyMA
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExoVideoFragment.a.this.a(playbackException);
                    }
                }, 150L);
            } else if (ExoVideoFragment.this.g < 3 && ExoVideoFragment.this.h < 99) {
                ExoVideoFragment.this.e.playByIndex(VideoModel.getInstance().getPlayIndex());
                ExoVideoFragment.g(ExoVideoFragment.this);
            } else if (!ExoVideoFragment.this.e.hasNextResource() || ExoVideoFragment.this.h >= 99) {
                ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$ExoVideoFragment$a$5uMJXTN7pg66l0L3qTs1fnEWVaY
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExoVideoFragment.a.this.b(playbackException);
                    }
                }, 150L);
            } else {
                ExoVideoFragment.this.g = 0;
                ExoVideoFragment.this.e.playNext();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(PlaybackException playbackException) {
            ExoVideoFragment.this.handlePlayError(-1004, -1004, playbackException);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(PlaybackException playbackException) {
            ExoVideoFragment.this.handlePlayError(-1004, -1004, playbackException);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(PlaybackException playbackException) {
            ExoVideoFragment.this.handlePlayError(-1004, -1004, playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            L.video.i("onTracksChanged=");
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsPlayingChanged(boolean z) {
            L.video.d("ExoVideoFragment onIsPlayingChanged=%s", Boolean.valueOf(z));
            if (z) {
                ExoVideoFragment.this.e.d();
            } else {
                ExoVideoFragment.this.e.reportVideoPlayEvent("pause");
            }
            if (ExoVideoFragment.this.f && ExoVideoFragment.this.e != null) {
                ExoVideoFragment.this.e.e();
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean z) {
            L.video.d("ExoVideoFragment onIsLoadingChanged=%s", Boolean.valueOf(z));
        }
    }

    private void b() {
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            this.i = null;
            this.a.attachPlayerControl(null);
        }
    }

    protected void showChildDialog(int i, int i2, int i3, int i4, int i5, ChildDialog.OnDialogClickListener onDialogClickListener) {
        if (this.childDialog == null) {
            this.childDialog = new ChildDialog.Builder().setTitle(getString(i)).setSubTitle(getString(i2)).setNegative(getString(i3)).setPositive(getString(i4)).setImageResId(i5).build(getContext());
            this.childDialog.setOnDialogClickListener(onDialogClickListener);
        }
        this.childDialog.show();
    }
}
