package com.xiaomi.micolauncher.skills.video.player;

import com.xiaomi.micolauncher.skills.video.player.model.OnlineUri;

/* loaded from: classes3.dex */
public interface IVideoPlayerView {
    int getCurrentPosition();

    int getDuration();

    void handlePlayError(int i, int i2, Throwable th);

    void hideLoadingView();

    boolean isPlaying();

    boolean isResumed();

    void onFastForwardVideo(long j);

    void onPauseVideo();

    void onRestartVideo();

    void onRewindVideo(long j);

    void onSeekVideo(int i);

    void onSeekVideoPos(int i, boolean z);

    void onStartVideo();

    void onStopVideo();

    void quitPlaying();

    void showControllerView();

    void showLoadingView();

    void showSevenVipDialog();

    void showVipDialog();

    void updateTitle(String str);

    void updateUI(OnlineUri onlineUri);

    void updateVideoList();
}
