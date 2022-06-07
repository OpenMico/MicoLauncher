package com.xiaomi.micolauncher.skills.music.model;

import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.skills.music.vip.OrderType;
import java.util.List;

/* loaded from: classes3.dex */
public class PlayerEvent {
    public static final String ACTION_MUSIC_META_CHANGE = "com.xiaomi.action.music.meta_change";
    public static final String ACTION_MUSIC_STATE_CHANGE = "com.xiaomi.action.music.state_change";
    public static final String METADATA = "metadata";
    public static final String MUSIC_SOURCE_BT = "BT";
    public static final String MUSIC_SOURCE_IDLE = "IDLE";
    public static final String MUSIC_SOURCE_MI_PLAY = "MiPlay";
    public static final String MUSIC_SOURCE_WIFI = "WiFi";
    public static final String PLAYSTATE = "playstate";
    public static final String SOURCE = "source";

    /* loaded from: classes3.dex */
    public static class ClosePlayerListActivityV2Event {
    }

    /* loaded from: classes3.dex */
    public static class OnMusicAuthInvalid {
    }

    /* loaded from: classes3.dex */
    public static class OnPlayFinish {
    }

    /* loaded from: classes3.dex */
    public static class OnShowVipDialog {
    }

    /* loaded from: classes3.dex */
    public static class OnMediaMetadataChanged {
        public Remote.Response.PlayerStatus playerStatus;

        public OnMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
            this.playerStatus = playerStatus;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnMediaFileChanged {
        public final Remote.Response.PlayerStatus playerStatus;

        public OnMediaFileChanged(Remote.Response.PlayerStatus playerStatus) {
            this.playerStatus = playerStatus;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnMediaListChanged {
        public List<Remote.Response.TrackData> playList;

        public OnMediaListChanged(List<Remote.Response.TrackData> list) {
            this.playList = list;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnMediaCoverChanged {
        public final Remote.Response.PlayerStatus playerStatus;

        public OnMediaCoverChanged(Remote.Response.PlayerStatus playerStatus) {
            this.playerStatus = playerStatus;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnProgress {
        public Remote.Response.PlayerStatus playerStatus;

        public OnProgress(Remote.Response.PlayerStatus playerStatus) {
            this.playerStatus = playerStatus;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnPlayError {
        public Remote.Response.PlayerStatus playerStatus;

        public OnPlayError() {
        }

        public OnPlayError(int i) {
            this.playerStatus = new Remote.Response.PlayerStatus();
            this.playerStatus.status = i;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnPlayIndexChange {
        public int index;

        public OnPlayIndexChange(int i) {
            this.index = i;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnPlayPaySuccess {
        public List<Integer> indexList;
        public OrderType orderType;

        public OnPlayPaySuccess(OrderType orderType, List<Integer> list) {
            this.orderType = orderType;
            this.indexList = list;
        }
    }
}
