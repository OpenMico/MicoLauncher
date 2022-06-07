package com.xiaomi.micolauncher.skills.music.controller.mediacontroller;

import android.media.Rating;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.xiaomi.micolauncher.api.model.Remote;
import java.util.List;

/* loaded from: classes3.dex */
public interface MediaControllerProxy {
    Remote.Response.PlayingData getCurrentPlayingData();

    int getMediaType();

    int getPlayStatus();

    Remote.Response.PlayerStatus getPlayerStatus();

    List<Remote.Response.TrackData> getPlaylist();

    long getSongDuration();

    boolean hasHistoryData();

    boolean isReleased();

    void pause();

    void play();

    void pushMediaState();

    void release();

    void seekTo(long j);

    void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver);

    void setIsActive(boolean z);

    void setRating(Rating rating);

    void setVolume(int i);

    void skipToNext();

    void skipToPrevious();

    void stop();
}
