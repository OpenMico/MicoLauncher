package com.xiaomi.micolauncher.module.homepage.event;

import android.media.MediaMetadata;
import com.xiaomi.micolauncher.api.model.Remote;

/* loaded from: classes3.dex */
public class MusicPlayEvent {
    public MediaMetadata metadata;
    public Remote.Response.PlayerStatus playerStatus;

    public MusicPlayEvent(MediaMetadata mediaMetadata, Remote.Response.PlayerStatus playerStatus) {
        this.metadata = mediaMetadata;
        this.playerStatus = playerStatus;
    }
}
