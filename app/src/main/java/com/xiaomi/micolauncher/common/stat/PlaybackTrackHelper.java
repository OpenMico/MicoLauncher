package com.xiaomi.micolauncher.common.stat;

import android.text.TextUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;

/* loaded from: classes3.dex */
public class PlaybackTrackHelper {
    public static final String APP_TAG = "app";
    public static final String ROM_TAG = "rom";
    public static final String SWITCH_TYPE_AUTO_SWITCH = "auto_switch";
    public static final String SWITCH_TYPE_LIST_SWITCH = "media_list_switch";
    public static final String SWITCH_TYPE_MAN_SWITCH = "man_switch";
    public static final String SWITCH_TYPE_PAUSE = "pause";
    public static final String TAG_MV = "MV";
    public static final String TAG_PLAY_LIST_CLICK = "PLAY_LIST_CLICK";
    private AudioPlayer.AudioItemV1 audioItem;
    private String audioType;
    private String dialogId;
    private String dialogOrigin;
    private long duration;
    private long endTs;
    private int offset;
    private String originDialogId;
    private long positionInMs;
    private long startTs;

    public PlaybackTrackHelper(String str, String str2, AudioPlayer.AudioItemV1 audioItemV1, String str3) {
        this.originDialogId = str;
        this.dialogId = str2;
        this.audioItem = audioItemV1;
        this.audioType = str3 == null ? "" : str3.toLowerCase();
        this.dialogOrigin = dialogOriginStr(str);
    }

    public void setDialogId(String str) {
        this.dialogId = str;
    }

    public void setStartTs() {
        this.startTs = System.currentTimeMillis();
    }

    public void setEndTs() {
        this.endTs = System.currentTimeMillis();
    }

    public void setPositionInMs(int i) {
        this.positionInMs = i;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public void setOffset(int i) {
        this.offset = i;
    }

    public void postPlayEvent(String str, String str2) {
        AudioPlayer.AudioItemV1 audioItemV1 = this.audioItem;
        if (audioItemV1 != null && this.originDialogId != null) {
            ExtendJsonWrapper extendJson = extendJson(audioItemV1);
            extendJson.setPlayStartTime(this.startTs);
            extendJson.setPlayEndTime(this.endTs);
            extendJson.setPosition(this.positionInMs);
            extendJson.setDuration(this.duration);
            extendJson.setOffset(this.offset);
            extendJson.setControlType(str);
            extendJson.setSwitchType(str2);
            extendJson.setAudioType(this.audioType);
            postPlayPagePlay(extendJson, this.audioType);
        }
    }

    public static void postPlayPagePlay(ExtendJsonWrapper extendJsonWrapper, String str) {
        TrackWidget trackWidget;
        if (Common.AudioType.MUSIC.name().equalsIgnoreCase(str)) {
            trackWidget = TrackWidget.PLAY_PAGE;
        } else {
            trackWidget = TrackWidget.RADIO_PLAY_PAGE;
        }
        TrackStat.simpleCountEvent(trackWidget, EventType.PLAY, extendJsonWrapper, false);
    }

    public static void postFavoriteEvent(Remote.Response.PlayingData playingData, String str, boolean z) {
        if (playingData != null && playingData.screenExtend != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setAudioId(playingData.audioID);
            obtain.setCpName(playingData.cpOrigin);
            obtain.setCpId(playingData.cpID);
            obtain.setCpAlbumId(playingData.cpAlbumId);
            obtain.setAlbumId(playingData.albumGlobalID == null ? "" : playingData.albumGlobalID);
            String str2 = playingData.screenExtend.dialogId;
            String str3 = playingData.screenExtend.originId;
            obtain.setDialogId(str2 == null ? "" : str2);
            if (str3 == null) {
                str3 = "";
            }
            obtain.setOriginId(str3);
            obtain.setDialogOrigin(dialogOriginStr(str2));
            obtain.setLogExtension(playingData.screenExtend.log);
            obtain.setControlType(str);
            postFavorite(obtain, z);
        }
    }

    public static void postPlayMV(Remote.Response.TrackData trackData) {
        if (trackData != null) {
            postPlayMV(trackData.dialogId, trackData.originId, trackData.cpID, trackData.cpOrigin, trackData.audioID, trackData.log, "palylist");
        }
    }

    public static void postPlayMV(String str, String str2, String str3, String str4, String str5, ObjectNode objectNode, String str6) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setDialogId(str == null ? "" : str);
        if (str2 == null) {
            str2 = str;
        }
        obtain.setOriginId(str2);
        obtain.setDialogOrigin(dialogOriginStr(str));
        obtain.setCpId(str3);
        obtain.setCpName(str4);
        obtain.setAudioId(str5);
        obtain.setLogExtension(objectNode);
        obtain.setFrom(str6);
        TrackStat.simpleCountEvent(TrackWidget.PLAY_PAGE_MV, EventType.CLICK, obtain, false);
    }

    public static void postPlayListClick(Remote.Response.TrackData trackData, int i) {
        postPlayList(trackData, i, EventType.CLICK);
    }

    public static void postPlayListExpose(Remote.Response.TrackData trackData, int i) {
        postPlayList(trackData, i, EventType.EXPOSE);
    }

    public static void postPlayList(Remote.Response.TrackData trackData, int i, EventType eventType) {
        if (trackData != null && trackData.audioType != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setDialogId(trackData.dialogId == null ? "" : trackData.dialogId);
            obtain.setOriginId(trackData.originId == null ? "" : trackData.originId);
            obtain.setDialogOrigin(dialogOriginStr(trackData.dialogId));
            obtain.setCpId(trackData.cpID);
            obtain.setCpName(trackData.cpOrigin);
            obtain.setAudioId(trackData.audioID);
            obtain.setLogExtension(trackData.log);
            obtain.setOffset(i + 1);
            obtain.setAudioType(trackData.audioType.toLowerCase());
            if (trackData.cpAlbumId != null) {
                obtain.setCpAlbumId(trackData.cpAlbumId);
            }
            if (trackData.episodesNum > 0) {
                obtain.setEpisodeIndex(trackData.episodesNum);
            }
            if (Common.AudioType.MUSIC.name().equalsIgnoreCase(trackData.audioType)) {
                TrackStat.simpleCountEvent(TrackWidget.PLAY_LIST, eventType, obtain);
            } else {
                TrackStat.simpleCountEvent(TrackWidget.RADIO_PLAY_PAGE, eventType, obtain);
            }
        }
    }

    private ExtendJsonWrapper extendJson(AudioPlayer.AudioItemV1 audioItemV1) {
        ExtendJsonWrapper extendJson = extendJson();
        AudioPlayer.ItemId itemId = audioItemV1.getItemId();
        extendJson.setAudioId(itemId.getAudioId());
        if (itemId.getAlbumId().isPresent()) {
            extendJson.setAlbumId(itemId.getAlbumId().get());
        } else {
            extendJson.setAlbumId("");
        }
        if (itemId.getCp().isPresent()) {
            AudioPlayer.ContentProvider contentProvider = itemId.getCp().get();
            extendJson.setCpName(contentProvider.getName());
            extendJson.setCpId(contentProvider.getId());
            if (contentProvider.getEpisodeIndex().isPresent()) {
                extendJson.setEpisodeIndex(contentProvider.getEpisodeIndex().get().intValue());
            }
            if (TextUtils.isEmpty(extendJson.getCpAlbumId()) && contentProvider.getAlbumId().isPresent()) {
                extendJson.setCpAlbumId(contentProvider.getAlbumId().get());
            }
        }
        if (audioItemV1.getLog().isPresent()) {
            extendJson.setLogExtension(audioItemV1.getLog().get());
        }
        return extendJson;
    }

    private ExtendJsonWrapper extendJson() {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setDialogId(this.dialogId);
        obtain.setOriginId(this.originDialogId);
        obtain.setDialogOrigin(this.dialogOrigin);
        obtain.setAudioType(this.audioType);
        return obtain;
    }

    private static ExtendJsonWrapper extendJson(String str) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setDialogId(str == null ? "" : str);
        obtain.setOriginId(str == null ? "" : str);
        obtain.setDialogOrigin(dialogOriginStr(str));
        return obtain;
    }

    private static String dialogOriginStr(String str) {
        return str == null ? "soundbox" : str.startsWith("app") ? "app" : str.startsWith(ROM_TAG) ? "soundbox" : "voice";
    }

    private static void postFavorite(ExtendJsonWrapper extendJsonWrapper, boolean z) {
        TrackStat.simpleCountEvent(TrackWidget.FAVORITE_GENERAL, z ? EventType.FAVORITE : EventType.CANCEL_FAVORITE, extendJsonWrapper);
    }

    public void monitorLog(String str) {
        String str2 = "";
        String str3 = "";
        AudioPlayer.AudioItemV1 audioItemV1 = this.audioItem;
        if (!(audioItemV1 == null || audioItemV1.getItemId() == null)) {
            str2 = this.audioItem.getItemId().getAudioId();
        }
        AudioPlayer.AudioItemV1 audioItemV12 = this.audioItem;
        if (!(audioItemV12 == null || audioItemV12.getStream() == null)) {
            str3 = this.audioItem.getStream().getUrl();
        }
        L.monitor.i("dialog_id=%s, load_dialog_id=%s, player_name=%s, action=%s, audio_id=%s, play_url=%s", this.originDialogId, this.dialogId, "MusicAudioPlayer", str, str2, str3);
    }
}
