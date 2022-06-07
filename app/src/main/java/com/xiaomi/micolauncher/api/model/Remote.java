package com.xiaomi.micolauncher.api.model;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Template;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.api.NewsItem;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class Remote {
    private static final String UNKNOWN = "未知";
    private static Gson sGson = new GsonBuilder().disableHtmlEscaping().create();

    public static Gson getGson() {
        return sGson;
    }

    /* loaded from: classes3.dex */
    public static class Request {

        /* loaded from: classes3.dex */
        public static class PlayerIndex {
            public int index;
            public String media = "app_android";
        }

        /* loaded from: classes3.dex */
        public static class SetPosition {
            public String media = "app_android";
            public long position;
        }

        /* loaded from: classes3.dex */
        public static class SetVolume {
            public int volume;
        }

        /* loaded from: classes3.dex */
        public static class Playlist_Simple {
            public String id;
            public String media = "app_android";
            @SerializedName("startOffset")
            public int start;
            public int type;

            private Playlist_Simple(int i, long j, int i2) {
                this.type = i;
                this.id = String.valueOf(j);
                this.start = i2;
            }
        }

        /* loaded from: classes3.dex */
        public static class Playlist_Message {
            @Expose(deserialize = false, serialize = false)
            private Playlist_Message_Payload mPayload;
            public String media = "app_android";
            public String music;
            @SerializedName("startOffset")
            public int start;

            private Playlist_Message(Playlist_Message_Payload playlist_Message_Payload, int i) {
                this.music = Remote.sGson.toJson(playlist_Message_Payload);
                this.start = i;
            }

            private Playlist_Message(PlaylistSongList playlistSongList, int i) {
                this.music = Remote.sGson.toJson(playlistSongList);
                this.start = i;
            }

            private Playlist_Message(Playlist_StationList playlist_StationList, int i) {
                this.music = Remote.sGson.toJson(playlist_StationList);
                this.start = i;
            }

            private Playlist_Message() {
            }

            public static Playlist_Message fromSongPlaylist(List<Music.Song> list, int i) {
                Playlist_Message playlist_Message = new Playlist_Message();
                playlist_Message.start = i;
                playlist_Message.music = Remote.sGson.toJson(PlayV3Pact.buildPLayV3Pact(list));
                return playlist_Message;
            }

            public Playlist_Message_Payload getPayload() {
                if (this.mPayload == null && !TextUtils.isEmpty(this.music)) {
                    this.mPayload = (Playlist_Message_Payload) Remote.sGson.fromJson(this.music, (Class<Object>) Playlist_Message_Payload.class);
                }
                return this.mPayload;
            }
        }

        /* loaded from: classes3.dex */
        public static class Playlist_Message_Payload {
            public List<PlaylistSong> musics;
            public List<PlaylistStation> stations;

            public Object getByIndex(int i) {
                if (getList() != null) {
                    return getList().get(i);
                }
                return null;
            }

            public List getList() {
                List<PlaylistSong> list = this.musics;
                if (list != null) {
                    return list;
                }
                List<PlaylistStation> list2 = this.stations;
                if (list2 != null) {
                    return list2;
                }
                return null;
            }
        }

        /* loaded from: classes3.dex */
        public static class PlaylistStation {
            public static final int TYPE_STATION_ALBUM = 0;
            public static final int TYPE_STATION_RADIO = 1;
            public static final int TYPE_STATION_SOUND = 2;
            public boolean albumBought;
            public String albumId;
            public String albumName;
            public int albumSaleType;
            public boolean bought;
            public String cover;
            public int episodesNum;
            public String globalID;
            public String id;
            public String origin;
            public String playSequence;
            public int saleType;
            public long salesPrice;
            public String stationId;
            public String title;
            public String type;

            public static PlaylistStation buildStation(Music.Station station) {
                PlaylistStation playlistStation = new PlaylistStation();
                playlistStation.id = station.cpID;
                playlistStation.type = station.type;
                playlistStation.origin = station.origin;
                playlistStation.title = station.title;
                playlistStation.cover = station.cover;
                playlistStation.globalID = station.globalID;
                playlistStation.episodesNum = station.episodesNum;
                playlistStation.albumName = station.albumName;
                playlistStation.albumId = station.cpAlbumId;
                playlistStation.saleType = station.saleType;
                playlistStation.salesPrice = station.salesPrice;
                playlistStation.bought = station.bought;
                playlistStation.playSequence = station.playSequence;
                playlistStation.albumBought = station.albumBought;
                playlistStation.albumSaleType = station.albumSaleType;
                return playlistStation;
            }

            public boolean isLegalResource() {
                return !TextUtils.isEmpty(this.id) && !TextUtils.isEmpty(this.origin);
            }

            public static PlaylistStation buildStation(Music.Station station, String str) {
                PlaylistStation buildStation = buildStation(station);
                buildStation.stationId = str;
                return buildStation;
            }

            public static int convertStationType(String str) {
                if ("album".equals(str)) {
                    return 0;
                }
                return "radio".equals(str) ? 1 : 2;
            }

            public static List<PlaylistStation> buildStationPlayList(List<Music.Station> list) {
                ArrayList arrayList = new ArrayList(list.size());
                for (Music.Station station : list) {
                    arrayList.add(buildStation(station));
                }
                return arrayList;
            }
        }

        /* loaded from: classes3.dex */
        public static class Playlist_StationList {
            public List<PlaylistStation> stations;

            private static Playlist_StationList buildStationList(List<Music.Station> list) {
                Playlist_StationList playlist_StationList = new Playlist_StationList();
                playlist_StationList.stations = new ArrayList(list.size());
                for (Music.Station station : list) {
                    playlist_StationList.stations.add(PlaylistStation.buildStation(station));
                }
                return playlist_StationList;
            }

            private static Playlist_StationList buildStationList(List<Music.Station> list, String str) {
                Playlist_StationList playlist_StationList = new Playlist_StationList();
                playlist_StationList.stations = new ArrayList(list.size());
                for (Music.Station station : list) {
                    playlist_StationList.stations.add(PlaylistStation.buildStation(station, str));
                }
                return playlist_StationList;
            }
        }

        /* loaded from: classes3.dex */
        public static class PlaylistSong {
            @SerializedName("artist_name")
            public String artistName;
            public String audioID;
            public String origin;
            @SerializedName("id")
            public String originSongID;
            @SerializedName("global_id")
            public String songID;
            @SerializedName(IBlockBean.BLOCK_TYPE_SONG)
            public String songName;
            public int unplayableCode;
            public int vip;

            public static PlaylistSong buildSong(Music.Song song) {
                PlaylistSong playlistSong = new PlaylistSong();
                playlistSong.songID = song.songID;
                playlistSong.origin = song.origin;
                playlistSong.originSongID = song.originSongID;
                playlistSong.songName = song.name;
                playlistSong.artistName = song.getArtistName();
                playlistSong.audioID = song.audioID;
                playlistSong.vip = song.vip;
                playlistSong.unplayableCode = song.unplayableCode;
                return playlistSong;
            }

            public boolean isLegalResource() {
                return !TextUtils.isEmpty(this.originSongID) && !TextUtils.isEmpty(this.origin);
            }
        }

        /* loaded from: classes3.dex */
        private static class PlaylistSongList {
            public List<PlaylistSong> musics;

            private PlaylistSongList() {
            }

            private static PlaylistSongList buildSongList(List<Music.Song> list) {
                PlaylistSongList playlistSongList = new PlaylistSongList();
                playlistSongList.musics = new ArrayList(list.size());
                for (Music.Song song : list) {
                    playlistSongList.musics.add(PlaylistSong.buildSong(song));
                }
                return playlistSongList;
            }
        }

        /* loaded from: classes3.dex */
        public static class LoopType {
            public static final int LOOP_TYPE_LIST_LOOP = 1;
            public static final int LOOP_TYPE_NONE = 4;
            public static final int LOOP_TYPE_ORDER = 2;
            public static final int LOOP_TYPE_SHUFFLE = 3;
            public static final int LOOP_TYPE_SINGLE_LOOP = 0;
            public String media = "app_android";
            public int type;

            public static boolean isValidLoop(int i) {
                return i >= 0 && i < 4;
            }

            public static int nextLoop(int i) {
                if (i == 3) {
                    return 0;
                }
                return i == 0 ? 1 : 3;
            }

            public boolean isValidLoop() {
                int i = this.type;
                return i >= 0 && i < 4;
            }
        }

        public static String buildLoopTypeMessage(int i) {
            LoopType loopType = new LoopType();
            loopType.type = i;
            return Remote.sGson.toJson(loopType);
        }

        /* loaded from: classes3.dex */
        public static class SongFavorite {
            public String origin;
            public String originSongID;

            public static SongFavorite buildSongFavourite(Music.Song song) {
                SongFavorite songFavorite = new SongFavorite();
                songFavorite.origin = song.origin;
                songFavorite.originSongID = song.originSongID;
                return songFavorite;
            }
        }

        public static String buildSongListFavouriteMessage(List<Music.Song> list) {
            ArrayList arrayList = new ArrayList(list.size());
            for (Music.Song song : list) {
                arrayList.add(SongFavorite.buildSongFavourite(song));
            }
            return Remote.sGson.toJson(arrayList);
        }

        public static String buildSongFavouriteMessage(String str, String str2) {
            ArrayList arrayList = new ArrayList();
            SongFavorite songFavorite = new SongFavorite();
            songFavorite.origin = str2;
            songFavorite.originSongID = str;
            arrayList.add(songFavorite);
            return Remote.sGson.toJson(arrayList);
        }

        /* loaded from: classes3.dex */
        public static class PlayerShutdown {
            public static String ACTION_CANCEL = "cancel_ending";
            public static String ACTION_PAUSE_AFTER_FINISH = "pause_after_finish";
            public static String ACTION_PAUSE_LATER = "pause_later";
            public String action;
            public int hour;
            public long interval;
            public int minute;
            public int second;

            public PlayerShutdown() {
            }

            public PlayerShutdown(String str, long j) {
                this.action = str;
                this.interval = j;
            }

            public static PlayerShutdown parser(String str) {
                String str2;
                String str3;
                String str4;
                if (str != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        String string = jSONObject.getString("action");
                        if (string != null && (ACTION_PAUSE_LATER.equals(string) || ACTION_CANCEL.equals(string) || ACTION_PAUSE_AFTER_FINISH.equals(string))) {
                            if (jSONObject.has("hour")) {
                                str2 = jSONObject.getString("hour");
                            } else {
                                str2 = null;
                            }
                            if (jSONObject.has("minute")) {
                                str3 = jSONObject.getString("minute");
                            } else {
                                str3 = null;
                            }
                            if (jSONObject.has("second")) {
                                str4 = jSONObject.getString("second");
                            } else {
                                str4 = null;
                            }
                            PlayerShutdown playerShutdown = new PlayerShutdown();
                            playerShutdown.action = string;
                            int i = 0;
                            playerShutdown.hour = str2 != null ? Integer.parseInt(str2) : 0;
                            playerShutdown.minute = str3 != null ? Integer.parseInt(str3) : 0;
                            if (str4 != null) {
                                i = Integer.parseInt(str4);
                            }
                            playerShutdown.second = i;
                            return playerShutdown;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            public boolean canExecute() {
                return ACTION_PAUSE_AFTER_FINISH.equals(this.action) || ACTION_CANCEL.equals(this.action) || (ACTION_PAUSE_LATER.equals(this.action) && (this.hour > 0 || this.minute > 0 || this.second > 0));
            }

            public long getInterval() {
                if (this.interval == 0) {
                    this.interval = TimeUnit.HOURS.toSeconds(this.hour) + TimeUnit.MINUTES.toSeconds(this.minute) + this.second;
                }
                return this.interval;
            }

            public String toString() {
                return "PlayerShutdown{action='" + this.action + "', hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + '}';
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Response {

        /* loaded from: classes3.dex */
        public static class ControlAction {
            public static final String ACTION_NEXT = "next";
            public static final String ACTION_PAUSE = "pause";
            public static final String ACTION_PLAY = "play";
            public static final String ACTION_PREV = "prev";
            public static final String ACTION_STOP = "stop";
            public static final String ACTION_TOGGLE = "toggle";
            public String action;
            public String media = "app_android";
        }

        /* loaded from: classes3.dex */
        public static class Memo {
            public int duration;
            public long id;
            public long timestamp;
            public String value;
            public String voiceUrl;
        }

        /* loaded from: classes3.dex */
        public static class PlayerShutdownTimer {
            public static final int TYPE_BY_SONG_FINISH = 2;
            public static final int TYPE_BY_TIME = 1;
            public static final int TYPE_NO_TIMER = 0;
            @SerializedName("remain_time")
            public long remainTime;
            public int type;
        }

        /* loaded from: classes3.dex */
        public static class PlayerStatus implements Serializable {
            public static final int MEDIA_TYPE_ALBUM = 8;
            public static final int MEDIA_TYPE_BLUETOOTH = 20;
            public static final int MEDIA_TYPE_CLOCK_DEFAULT = 2;
            public static final int MEDIA_TYPE_CLOCK_EVENT = 10;
            public static final int MEDIA_TYPE_DIRECTIVE_MEDIA = 15;
            public static final int MEDIA_TYPE_DLNA = 13;
            public static final int MEDIA_TYPE_MIPLAY = 21;
            public static final int MEDIA_TYPE_SHEET = 9;
            public static final int MEDIA_TYPE_SONG_LIST = 3;
            public static final int MEDIA_TYPE_STATION_ALBUM = 5;
            public static final int MEDIA_TYPE_STATION_RADIO = 6;
            public static final int MEDIA_TYPE_STATION_SOUND = 4;
            public static final int MEDIA_TYPE_TTS_CONVERSATION = 7;
            public static final int MEDIA_TYPE_TTS_DIRECTIVE = 12;
            public static final int MEDIA_TYPE_TTS_NO_LIGHT = 11;
            public static final int MEDIA_TYPE_TTS_TIP = 1;
            public static final int MEDIA_TYPE_UNKNOWN = 0;
            public static final int MEDIA_TYPE_VOIP_RING = 14;
            public static final int PLAYER_STATUS_PAUSE = 2;
            public static final int PLAYER_STATUS_PLAY = 1;
            public static final int PLAYER_STATUS_STOP = 0;
            public static final int PLAYER_STATUS_UNKNOWN = -1;
            public static final String TYPE_BOOKS = "BOOKS";
            public static final String TYPE_MUSIC = "MUSIC";
            public static final String TYPE_NEWS = "NEWS";
            public List<TrackData> extra_track_list;
            public transient boolean isManualLoadMore;
            @SerializedName("album_playlist_id")
            public long list_id;
            public int loop_type = -1;
            public int media_type;
            public PlayingData play_song_detail;
            public int status;
            public List<Long> track_list;
            public int volume;

            /* loaded from: classes3.dex */
            public static class ScreenExtra {
                public static final int PLAER_STATUS_AUTH_INVALID = 22;
                public static final int PLAER_STATUS_ERROR = 21;
            }

            public boolean isPlayingStatus() {
                return this.status == 1;
            }

            public boolean isStopStatus() {
                return this.status == 0;
            }

            public boolean isPauseStatus() {
                return this.status == 2;
            }

            public boolean isBluetooth() {
                return this.media_type == 20;
            }

            public boolean isMiPlay() {
                return this.media_type == 21;
            }
        }

        /* loaded from: classes3.dex */
        public static class PlayingData implements Serializable {
            private static final long serialVersionUID = -4838092758195800251L;
            public boolean albumBought;
            public String albumGlobalID;
            @SerializedName("album")
            public String albumName;
            public int albumSaleType;
            @Expose(serialize = false)
            public String artist;
            @SerializedName("audio_id")
            public String audioID;
            public String audioType;
            public boolean bought;
            @Expose(serialize = false)
            public String cover;
            public transient Bitmap coverBitmap;
            @SerializedName("albumId")
            public String cpAlbumId;
            @SerializedName("cp_id")
            public String cpID;
            @SerializedName("cp_origin")
            public String cpOrigin;
            public long duration;
            public String lrc;
            @SerializedName("global_id")
            @Expose(serialize = false)
            public String musicID;
            public transient Bitmap outCoverBitmap;
            public String playSequence;
            public long position;
            public int saleType;
            public long salesPrice;
            @Expose(serialize = false)
            public transient ScreenExtend screenExtend = new ScreenExtend();
            @Expose(serialize = false)
            public String title;
            public int unplayableCode;
            public int vip;

            public String getSubTitle() {
                if (this.screenExtend.mediaType == 3 || this.screenExtend.mediaType == 9 || this.screenExtend.mediaType == 20 || this.screenExtend.mediaType == 21) {
                    if (!TextUtils.isEmpty(this.artist) && !Remote.UNKNOWN.equals(this.artist)) {
                        return this.artist;
                    }
                } else if (this.screenExtend.mediaType == 15 || this.screenExtend.mediaType == 6) {
                    if (!TextUtils.isEmpty(this.artist)) {
                        return this.artist;
                    }
                    return MusicHelper.getCpName(this.cpOrigin);
                }
                if (TextUtils.isEmpty(this.cpOrigin)) {
                    return StringUtils.SPACE;
                }
                String cpName = MusicHelper.getCpName(this.cpOrigin);
                return !TextUtils.isEmpty(cpName) ? cpName : StringUtils.SPACE;
            }

            public boolean isPlayingFM() {
                return this.screenExtend.mediaType == 6;
            }

            public String getSimpleId() {
                if (!TextUtils.isEmpty(this.audioID)) {
                    return this.audioID;
                }
                return this.musicID;
            }

            public boolean isSong() {
                return "MUSIC".equalsIgnoreCase(this.audioType);
            }

            public boolean isPreview() {
                return "MUSIC".equalsIgnoreCase(this.audioType) ? this.vip == 1 && this.unplayableCode == 2 : this.albumSaleType == 2 && !this.albumBought;
            }

            public boolean isBookLocked() {
                return "BOOKS".equalsIgnoreCase(this.audioType) && this.albumSaleType > 0 && !this.albumBought && this.saleType > 0 && !this.bought;
            }

            public boolean isResourceChange(PlayingData playingData) {
                return !TextUtils.equals(this.musicID, playingData.musicID) || !TextUtils.equals(this.cpID, playingData.cpID) || !TextUtils.equals(this.cpAlbumId, playingData.cpAlbumId) || !TextUtils.equals(this.albumGlobalID, playingData.albumGlobalID);
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof PlayingData)) {
                    return false;
                }
                if (TextUtils.isEmpty(this.audioID)) {
                    PlayingData playingData = (PlayingData) obj;
                    if (TextUtils.isEmpty(playingData.audioID)) {
                        return !TextUtils.isEmpty(this.musicID) ? TextUtils.equals(this.musicID, playingData.musicID) && this.unplayableCode == playingData.unplayableCode && this.bought == playingData.bought && this.albumBought == playingData.albumBought : !TextUtils.isEmpty(this.cpID) && !TextUtils.isEmpty(this.cpOrigin) && this.cpID.equals(playingData.cpID) && this.cpOrigin.equals(playingData.cpOrigin) && this.unplayableCode == playingData.unplayableCode && this.bought == playingData.bought && this.albumBought == playingData.albumBought;
                    }
                }
                PlayingData playingData2 = (PlayingData) obj;
                return TextUtils.equals(this.audioID, playingData2.audioID) && TextUtils.equals(this.cover, playingData2.cover) && this.unplayableCode == playingData2.unplayableCode && this.bought == playingData2.bought && this.albumBought == playingData2.albumBought;
            }

            public static PlayingData from(AudioPlayer.AudioItemV1 audioItemV1) {
                if (audioItemV1 == null) {
                    return null;
                }
                PlayingData playingData = new PlayingData();
                String audioId = audioItemV1.getItemId().getAudioId();
                playingData.musicID = audioId;
                playingData.audioID = audioId;
                playingData.albumGlobalID = audioItemV1.getItemId().getAlbumId().isPresent() ? audioItemV1.getItemId().getAlbumId().get() : "";
                if (audioItemV1.getItemId().getCp().isPresent()) {
                    AudioPlayer.ContentProvider contentProvider = audioItemV1.getItemId().getCp().get();
                    playingData.cpID = contentProvider.getId();
                    playingData.cpOrigin = contentProvider.getName();
                    if (contentProvider.getAlbumId().isPresent()) {
                        playingData.cpAlbumId = contentProvider.getAlbumId().get();
                    }
                }
                return playingData;
            }

            public static PlayingData from(Music.AudioInfo audioInfo) {
                if (audioInfo == null) {
                    return null;
                }
                PlayingData playingData = new PlayingData();
                playingData.musicID = audioInfo.audioID;
                playingData.audioID = audioInfo.audioID;
                playingData.cpID = audioInfo.cpID;
                playingData.cpOrigin = audioInfo.cpName;
                playingData.title = audioInfo.name;
                playingData.artist = audioInfo.artistName;
                playingData.cover = audioInfo.cover;
                playingData.albumName = audioInfo.albumName;
                playingData.cpAlbumId = audioInfo.cpAlbumId;
                playingData.audioType = audioInfo.audioType;
                playingData.vip = audioInfo.vip;
                playingData.unplayableCode = audioInfo.unplayableCode;
                playingData.saleType = audioInfo.saleType;
                playingData.albumSaleType = audioInfo.albumSaleType;
                playingData.bought = audioInfo.bought;
                playingData.albumBought = audioInfo.bought;
                if (audioInfo.albumGlobalID != null) {
                    playingData.albumGlobalID = audioInfo.albumGlobalID;
                }
                return playingData;
            }

            public static PlayingData from(Music.Song song) {
                if (song == null) {
                    return null;
                }
                PlayingData playingData = new PlayingData();
                playingData.audioID = song.audioID;
                playingData.musicID = song.songID;
                playingData.cpID = song.originSongID;
                playingData.cpOrigin = song.origin;
                playingData.cover = song.coverURL;
                playingData.artist = song.getArtistName();
                playingData.albumName = song.albumName;
                playingData.title = song.name;
                playingData.audioType = "MUSIC";
                playingData.vip = song.vip;
                playingData.unplayableCode = song.unplayableCode;
                return playingData;
            }

            public static PlayingData from(NewsItem newsItem) {
                if (newsItem == null) {
                    return null;
                }
                PlayingData playingData = new PlayingData();
                playingData.musicID = newsItem.getNewsId();
                playingData.cpID = newsItem.getCpId();
                playingData.cpOrigin = newsItem.getCp();
                playingData.cover = newsItem.getCover();
                playingData.title = newsItem.getTitle();
                playingData.audioType = "NEWS";
                return playingData;
            }

            public static PlayingData from(Music.Station station) {
                if (station == null) {
                    return null;
                }
                PlayingData playingData = new PlayingData();
                playingData.audioID = station.audioID;
                if (!TextUtils.isEmpty(station.globalID)) {
                    playingData.musicID = station.globalID;
                }
                playingData.cpID = station.cpID;
                playingData.cpOrigin = station.origin;
                playingData.cover = station.cover;
                playingData.artist = station.originName;
                playingData.title = station.title;
                playingData.cpAlbumId = station.cpAlbumId;
                playingData.albumName = station.albumName;
                playingData.albumGlobalID = station.albumGlobalID;
                playingData.audioType = "BOOKS";
                playingData.saleType = station.saleType;
                playingData.salesPrice = station.salesPrice;
                playingData.bought = station.bought;
                playingData.albumSaleType = station.albumSaleType;
                playingData.albumBought = station.albumBought;
                playingData.playSequence = station.playSequence;
                return playingData;
            }

            public static PlayingData from(TrackData trackData) {
                PlayingData playingData = new PlayingData();
                playingData.audioID = trackData.audioID;
                playingData.musicID = trackData.musicID;
                playingData.albumGlobalID = trackData.albumGlobalID;
                playingData.cpID = trackData.cpID;
                playingData.cpOrigin = trackData.cpOrigin;
                playingData.cpAlbumId = trackData.cpAlbumId;
                playingData.albumName = trackData.album;
                playingData.artist = trackData.artist;
                playingData.title = trackData.title;
                playingData.cover = trackData.cover;
                playingData.audioType = trackData.audioType;
                playingData.saleType = trackData.saleType;
                playingData.salesPrice = trackData.salesPrice;
                playingData.bought = trackData.bought;
                playingData.albumBought = trackData.albumBought;
                playingData.albumSaleType = trackData.albumSaleType;
                playingData.vip = trackData.vip;
                playingData.unplayableCode = trackData.unplayableCode;
                playingData.screenExtend.mvStream = trackData.mvStream;
                playingData.screenExtend.log = trackData.log;
                playingData.screenExtend.dialogId = trackData.dialogId;
                playingData.screenExtend.originId = trackData.originId;
                return playingData;
            }

            public String toString() {
                return "PlayingData{musicID=" + this.musicID + ", audioID='" + this.audioID + "', cpID='" + this.cpID + "', cpOrigin='" + this.cpOrigin + "', title='" + this.title + "', cover='" + this.cover + "', position=" + this.position + ", duration=" + this.duration + ", artist='" + this.artist + "', albumName='" + this.albumName + "', clAlbumId='" + this.cpAlbumId + "', screenExtend=" + this.screenExtend.toString() + '}';
            }
        }

        /* loaded from: classes3.dex */
        public static class ScreenExtend implements Serializable {
            private static final long serialVersionUID = 9218078537552876742L;
            public String dialogId;
            public long favoritesId;
            public int index;
            public ObjectNode log;
            public int loopMode;
            public int mediaType;
            public StreamInfo mvStream;
            public int offset;
            public String originId;
            public long playlistId;

            public boolean hasMV() {
                StreamInfo streamInfo = this.mvStream;
                return streamInfo != null && streamInfo.isValid();
            }

            public String toString() {
                return "ScreenExtend{mediaType=" + this.mediaType + ", playlistId=" + this.playlistId + ", offset=" + this.offset + ", loopMode=" + this.loopMode + ", index=" + this.index + '}';
            }
        }

        /* loaded from: classes3.dex */
        public static class TrackData implements Serializable {
            private static final long serialVersionUID = -5892706702999821743L;
            public String album;
            public boolean albumBought;
            public String albumGlobalID;
            public int albumSaleType;
            @Expose(serialize = false)
            public String artist;
            @SerializedName("audio_id")
            public String audioID;
            public String audioType;
            public boolean bought;
            public String cover;
            public String cpAlbumId;
            @SerializedName("cp_id")
            public String cpID;
            @SerializedName("cp_origin")
            public String cpOrigin;
            public transient String dialogId;
            public int episodesNum;
            public transient ObjectNode log;
            @SerializedName("global_id")
            public String musicID;
            public StreamInfo mvStream;
            public transient String originId;
            public String playSequence;
            public int saleType;
            public long salesPrice;
            public String title;
            @Expose(serialize = false)
            public transient String type;
            public int unplayableCode;
            public int vip;

            public boolean isSong() {
                return "MUSIC".equalsIgnoreCase(this.audioType);
            }

            public boolean isVipSong() {
                return "MUSIC".equalsIgnoreCase(this.audioType) && this.vip == 1;
            }

            public boolean isSongPreview() {
                return "MUSIC".equalsIgnoreCase(this.audioType) && this.vip == 1 && this.unplayableCode == 2;
            }

            public boolean isAlbumUnPay() {
                return "BOOKS".equalsIgnoreCase(this.audioType) && this.albumSaleType == 2 && !this.albumBought && this.saleType > 0 && !this.bought;
            }

            public boolean isStationItemUnPay() {
                return "BOOKS".equalsIgnoreCase(this.audioType) && this.albumSaleType == 1 && this.saleType > 0 && !this.bought;
            }

            public boolean stationIsLocked() {
                return "BOOKS".equalsIgnoreCase(this.audioType) && this.albumSaleType > 0 && !this.albumBought && this.saleType > 0 && !this.bought;
            }

            public static TrackData from(Request.PlaylistSong playlistSong, int i) {
                TrackData trackData = new TrackData();
                trackData.title = playlistSong.songName;
                trackData.musicID = playlistSong.songID;
                trackData.cpID = playlistSong.originSongID;
                trackData.cpOrigin = playlistSong.origin;
                trackData.artist = playlistSong.artistName;
                trackData.audioType = "MUSIC";
                trackData.vip = playlistSong.vip;
                trackData.unplayableCode = playlistSong.unplayableCode;
                return trackData;
            }

            public String getListSubTitle(int i) {
                if (i == 3 || i == 9) {
                    if (!TextUtils.isEmpty(this.artist) && !Remote.UNKNOWN.equals(this.artist)) {
                        return this.artist;
                    }
                } else if (i == 4 || i == 5) {
                    if (!TextUtils.isEmpty(this.album)) {
                        return this.album;
                    }
                } else if ((i == 15 || i == 6) && !TextUtils.isEmpty(this.artist)) {
                    return this.artist;
                }
                if (TextUtils.isEmpty(this.cpOrigin)) {
                    return "";
                }
                String cpName = MusicHelper.getCpName(this.cpOrigin);
                return !TextUtils.isEmpty(cpName) ? cpName : "";
            }

            public boolean equals(Object obj) {
                if (this != obj) {
                    if (obj instanceof TrackData) {
                        TrackData trackData = (TrackData) obj;
                        if (this.unplayableCode != trackData.unplayableCode || this.bought != trackData.bought || this.albumBought != trackData.albumBought || !CommonUtils.equals(this.musicID, trackData.musicID) || !CommonUtils.equals(this.audioID, trackData.audioID) || !CommonUtils.equals(this.cpID, trackData.cpID) || !CommonUtils.equals(this.cpOrigin, trackData.cpOrigin) || !CommonUtils.equals(this.title, trackData.title)) {
                        }
                    }
                    return false;
                }
                return true;
            }

            public static TrackData from(Music.Song song, int i) {
                TrackData trackData = new TrackData();
                trackData.musicID = song.audioID;
                trackData.audioID = song.audioID;
                trackData.cpID = song.originSongID;
                trackData.title = song.name;
                trackData.cover = song.coverURL;
                trackData.artist = song.getArtistName();
                trackData.cpOrigin = song.origin;
                trackData.audioType = "MUSIC";
                trackData.vip = song.vip;
                trackData.unplayableCode = song.unplayableCode;
                return trackData;
            }

            public static TrackData from(Music.Station station, int i, String str) {
                TrackData trackData = new TrackData();
                trackData.episodesNum = station.episodesNum;
                trackData.cpOrigin = station.origin;
                trackData.audioID = station.audioID;
                if (!TextUtils.isEmpty(station.globalID)) {
                    trackData.musicID = station.globalID;
                }
                trackData.albumGlobalID = station.albumGlobalID;
                trackData.title = station.title;
                trackData.cover = station.cover;
                trackData.cpID = station.cpID;
                trackData.audioID = station.audioID;
                trackData.saleType = station.saleType;
                trackData.salesPrice = station.salesPrice;
                trackData.bought = station.bought;
                trackData.audioType = "BOOKS";
                trackData.albumBought = station.albumBought;
                trackData.albumSaleType = station.albumSaleType;
                trackData.playSequence = station.playSequence;
                trackData.cpAlbumId = station.cpAlbumId;
                if (TextUtils.isEmpty(trackData.cpAlbumId)) {
                    trackData.cpAlbumId = str;
                }
                trackData.album = station.albumName;
                return trackData;
            }

            public static TrackData from(NewsItem newsItem, int i) {
                TrackData trackData = new TrackData();
                trackData.musicID = newsItem.getNewsId();
                trackData.cpID = newsItem.getCpId();
                trackData.title = newsItem.getTitle();
                trackData.cover = newsItem.getCover();
                trackData.cpOrigin = newsItem.getCp();
                return trackData;
            }

            public static TrackData from(Music.AudioInfo audioInfo, int i) {
                TrackData trackData = new TrackData();
                trackData.audioType = audioInfo.audioType;
                if (!TextUtils.isEmpty(audioInfo.external)) {
                    trackData.albumGlobalID = ((Music.AudioInfo.External) Remote.getGson().fromJson(audioInfo.external, (Class<Object>) Music.AudioInfo.External.class)).cmsAlbumId;
                } else {
                    trackData.albumGlobalID = audioInfo.albumGlobalID;
                }
                trackData.musicID = audioInfo.audioID;
                trackData.audioID = audioInfo.audioID;
                trackData.cpID = audioInfo.cpID;
                trackData.cpOrigin = audioInfo.cpName;
                trackData.cpAlbumId = audioInfo.cpAlbumId;
                trackData.title = audioInfo.name;
                trackData.artist = audioInfo.artistName;
                trackData.cover = audioInfo.cover;
                trackData.episodesNum = audioInfo.episodesNum;
                trackData.vip = audioInfo.vip;
                trackData.unplayableCode = audioInfo.unplayableCode;
                trackData.albumSaleType = audioInfo.albumSaleType;
                trackData.saleType = audioInfo.saleType;
                trackData.albumBought = audioInfo.bought;
                trackData.bought = audioInfo.bought;
                trackData.album = audioInfo.albumName;
                trackData.salesPrice = audioInfo.salesPrice;
                trackData.playSequence = audioInfo.playSequence;
                return trackData;
            }

            public static TrackData from(AudioPlayer.AudioItemV1 audioItemV1, Template.PlayInfoItem playInfoItem, int i, Common.AudioType audioType, Music.AudioInfo audioInfo) {
                TrackData trackData = new TrackData();
                trackData.musicID = audioItemV1.getItemId().getAudioId();
                trackData.albumGlobalID = audioItemV1.getItemId().getAlbumId().isPresent() ? audioItemV1.getItemId().getAlbumId().get() : "";
                trackData.audioID = audioItemV1.getItemId().getAudioId();
                trackData.audioType = audioType.name();
                if (audioItemV1.getItemId().getCp().isPresent()) {
                    AudioPlayer.ContentProvider contentProvider = audioItemV1.getItemId().getCp().get();
                    trackData.cpID = contentProvider.getId();
                    trackData.cpOrigin = contentProvider.getName();
                }
                if (playInfoItem != null) {
                    trackData.title = playInfoItem.getTitle().getMainTitle();
                    if (Common.AudioType.MUSIC == audioType || Common.AudioType.NEWS == audioType || Common.AudioType.RADIO_STATION == audioType) {
                        trackData.artist = playInfoItem.getTitle().getSubTitle();
                    } else if (Common.AudioType.BOOKS == audioType) {
                        trackData.album = playInfoItem.getTitle().getSubTitle();
                    }
                    if (playInfoItem.getMvStream().isPresent()) {
                        trackData.mvStream = new StreamInfo(playInfoItem.getMvStream().get());
                    }
                    if (playInfoItem.getCover().isPresent() && playInfoItem.getCover().get().getSources().size() > 0) {
                        trackData.cover = playInfoItem.getCover().get().getSources().get(0).getUrl();
                    }
                }
                if (audioItemV1.getLog().isPresent()) {
                    trackData.log = audioItemV1.getLog().get();
                }
                if (audioInfo != null) {
                    trackData.cpAlbumId = audioInfo.cpAlbumId;
                    trackData.episodesNum = audioInfo.episodesNum;
                    trackData.vip = audioInfo.vip;
                    trackData.unplayableCode = audioInfo.unplayableCode;
                    trackData.albumSaleType = audioInfo.albumSaleType;
                    trackData.saleType = audioInfo.saleType;
                    trackData.albumBought = audioInfo.bought;
                    trackData.bought = audioInfo.bought;
                    trackData.album = audioInfo.albumName;
                    trackData.salesPrice = audioInfo.salesPrice;
                    trackData.playSequence = audioInfo.playSequence;
                    if (TextUtils.isEmpty(trackData.cover)) {
                        trackData.cover = audioInfo.cover;
                    }
                    if (TextUtils.isEmpty(trackData.title)) {
                        trackData.title = audioInfo.name;
                    }
                    if (TextUtils.isEmpty(trackData.artist)) {
                        trackData.artist = audioInfo.artistName;
                    }
                    if (TextUtils.isEmpty(trackData.cpID)) {
                        trackData.cpID = audioInfo.cpID;
                    }
                    if (TextUtils.isEmpty(trackData.cpOrigin)) {
                        trackData.cpOrigin = audioInfo.cpName;
                    }
                    if (TextUtils.isEmpty(trackData.audioType)) {
                        trackData.audioType = audioInfo.audioType;
                    }
                }
                if (TextUtils.isEmpty(trackData.cpAlbumId) && audioItemV1.getItemId().getCp().isPresent()) {
                    AudioPlayer.ContentProvider contentProvider2 = audioItemV1.getItemId().getCp().get();
                    if (contentProvider2.getAlbumId().isPresent()) {
                        trackData.cpAlbumId = contentProvider2.getAlbumId().get();
                    }
                }
                return trackData;
            }
        }

        /* loaded from: classes3.dex */
        public static class Directive {
            public String cover;
            public String id;
            @Expose
            public int index;
            public String origin;
            public String title;

            public boolean equals(Object obj) {
                if (this != obj) {
                    if (obj instanceof Directive) {
                        Directive directive = (Directive) obj;
                        if (!CommonUtils.equals(this.id, directive.id) || !CommonUtils.equals(this.origin, directive.origin) || !CommonUtils.equals(this.title, directive.title)) {
                        }
                    }
                    return false;
                }
                return true;
            }
        }
    }
}
