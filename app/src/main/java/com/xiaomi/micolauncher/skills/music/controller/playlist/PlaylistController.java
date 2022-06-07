package com.xiaomi.micolauncher.skills.music.controller.playlist;

import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback;
import java.util.List;

/* loaded from: classes3.dex */
public interface PlaylistController {
    boolean canLoadMore();

    boolean canPreLoadMore();

    void doLoadMore(MetadataLoadMoreCallback metadataLoadMoreCallback);

    void doPlay(int i);

    void doPreLoadMore(MetadataPreLoadMoreCallback metadataPreLoadMoreCallback);

    int getMediaType();

    MusicIndexHelper getMusicIndexHelper();

    AudioPlayer getPlayer();

    int getPlaylistCount();

    boolean isPaused();

    boolean isReleased();

    void loadMore();

    void loadMore(int i);

    void next();

    void onResumeBreakpoint();

    void pause(Boolean bool);

    void pausePlayerByFinish(int i);

    void play();

    void playByIndex(int i, boolean z);

    void prev();

    void release();

    void reportVideoListSwitch();

    void reportVideoManSwitch();

    void seekTo(long j);

    void sendMetadata();

    List<Remote.Response.TrackData> sendPlaylist(boolean z);

    void setLoopType(int i, boolean z);

    void setVolume(float f);

    void updatePlaybackStatus();
}
