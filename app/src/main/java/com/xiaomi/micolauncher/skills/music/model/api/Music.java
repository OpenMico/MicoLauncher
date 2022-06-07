package com.xiaomi.micolauncher.skills.music.model.api;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import java.io.Serializable;
import java.util.List;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class Music {
    public static final int TYPE_ALBUM = 2;
    public static final int TYPE_ARTIST = 3;
    public static final int TYPE_AUDIO = 6;
    public static final int TYPE_CROSSTALK = 7;
    public static final int TYPE_LINK = 999;
    public static final int TYPE_SHEET = 5;
    public static final int TYPE_SONG = 1;
    public static final int TYPE_STATION = 4;

    /* loaded from: classes3.dex */
    public static class SongBook {
        public String cover;
        public long id;
        public String intro;
        public String name;
        public String origin;
        public List<Song> songList;
        public String subtitle;
    }

    /* loaded from: classes3.dex */
    public static class StationHistory {
        public String actionType;
        public String cp;
        public int episode;
        public String globalId;
        public long position;
        public String soundId;
        public long time;
    }

    /* loaded from: classes3.dex */
    public static class StationSoundList {
        public boolean isEnd;
        public List<Station> soundList;
    }

    /* loaded from: classes3.dex */
    public static class Artist implements Serializable {
        private static final long serialVersionUID = 153011036252764364L;
        public long artistID;
        public String iconURL;
        public String name;
        public boolean selected;

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Artist) && this.artistID == ((Artist) obj).artistID);
        }
    }

    /* loaded from: classes3.dex */
    public static class Album implements Serializable {
        private static final long serialVersionUID = -8009311371502390555L;
        public long albumID;
        public Artist artist;
        public String coverURL;
        public String name;
        public List<Song> songList;

        public String getArtistName() {
            Artist artist = this.artist;
            if (artist != null) {
                return artist.name;
            }
            return null;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Album) && this.albumID == ((Album) obj).albumID);
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioInfo implements Serializable {
        public String albumGlobalID;
        public String albumName;
        public int albumSaleType;
        @SerializedName(alternate = {"artistName"}, value = "artistDisplayName")
        public String artistName;
        @SerializedName(alternate = {"audioId"}, value = "audioID")
        public String audioID;
        public String audioType;
        public boolean authentication;
        public boolean bought;
        @SerializedName(alternate = {"cover"}, value = "coverURL")
        public String cover;
        public String cpAlbumId;
        public String cpDisplayName;
        @SerializedName(alternate = {"cpID"}, value = "cpId")
        public String cpID;
        @SerializedName(alternate = {"origin"}, value = "cpName")
        public String cpName;
        public long duration;
        public int episodesNum;
        public String external;
        public String lyricURL;
        public String name;
        public String playSequence;
        public String playUrl;
        public int saleType;
        public long salesPrice;
        public String songID;
        @SerializedName(alternate = {"qqUnplayableCode"}, value = "unplayableCode")
        public int unplayableCode;
        @SerializedName(alternate = {"qqVip"}, value = "vip")
        public int vip;
        public int collection = -1;
        public boolean hasTrial = true;

        /* loaded from: classes3.dex */
        public static class External implements Serializable {
            public String cmsAlbumId;
            public String cpAlbumId;
        }

        public boolean isNoPlayable() {
            return this.vip == 1 && this.unplayableCode == 2 && !this.hasTrial;
        }

        public boolean isCollected() {
            return this.collection == 1;
        }

        public static AudioInfo from(Song song) {
            AudioInfo audioInfo = new AudioInfo();
            audioInfo.audioType = "MUSIC";
            audioInfo.audioID = song.audioID;
            audioInfo.name = song.name;
            if (song.artist != null) {
                audioInfo.artistName = song.artist.name;
            }
            audioInfo.cpAlbumId = song.cpAlbumId;
            audioInfo.cpID = song.originSongID;
            audioInfo.cpName = song.origin;
            audioInfo.cpDisplayName = song.originName;
            audioInfo.albumName = song.albumName;
            audioInfo.playUrl = song.playUrl;
            audioInfo.cover = song.coverURL;
            audioInfo.vip = song.vip;
            audioInfo.unplayableCode = song.unplayableCode;
            return audioInfo;
        }

        public static AudioInfo from(Station station) {
            AudioInfo audioInfo = new AudioInfo();
            audioInfo.audioType = "BOOKS";
            audioInfo.audioID = station.audioID;
            audioInfo.name = station.title;
            audioInfo.albumGlobalID = station.albumGlobalID;
            audioInfo.cpAlbumId = station.cpAlbumId;
            audioInfo.cpID = station.cpID;
            audioInfo.cpName = station.origin;
            audioInfo.artistName = station.originName;
            audioInfo.albumName = station.albumName;
            audioInfo.playUrl = station.playUrl;
            audioInfo.cover = station.cover;
            audioInfo.duration = station.duration;
            audioInfo.episodesNum = station.episodesNum;
            audioInfo.saleType = station.saleType;
            audioInfo.albumSaleType = station.albumSaleType;
            audioInfo.bought = station.bought;
            audioInfo.salesPrice = station.salesPrice;
            audioInfo.playSequence = station.playSequence;
            return audioInfo;
        }
    }

    /* loaded from: classes3.dex */
    public static class Song implements IListItem, Serializable {
        private static final long serialVersionUID = -661694326758003726L;
        public String albumName;
        public Artist artist;
        @SerializedName(alternate = {"artistName"}, value = "artistDisplayName")
        public String artistDisplayName;
        @SerializedName(alternate = {"audioId"}, value = "audioID")
        public String audioID;
        public String audioType;
        public int collection;
        @SerializedName(alternate = {"cover"}, value = "coverURL")
        public String coverURL;
        @SerializedName("albumID")
        public String cpAlbumId;
        public long duration;
        public String lyricURL;
        public String name;
        public int offsetInMs;
        @SerializedName(alternate = {"cpName"}, value = "origin")
        public String origin;
        @SerializedName(alternate = {"cpDisplayName"}, value = "originName")
        public String originName;
        @SerializedName(alternate = {"cpId"}, value = "originSongID")
        public String originSongID;
        public String playUrl;
        public String songID;
        @SerializedName(alternate = {"qqUnplayableCode"}, value = "unplayableCode")
        public int unplayableCode;
        @SerializedName(alternate = {"qqVip"}, value = "vip")
        public int vip;
        public boolean authentication = true;
        public boolean hasTrial = true;

        /* loaded from: classes3.dex */
        public static class Simple {
            public String global_id;
            public String origin;
            public String origin_id;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTarget() {
            return "";
        }

        public boolean isNoPlayable() {
            return this.vip == 1 && this.unplayableCode == 2 && !this.hasTrial;
        }

        public String getArtistName() {
            if (!TextUtils.isEmpty(this.artistDisplayName)) {
                return this.artistDisplayName;
            }
            Artist artist = this.artist;
            if (artist != null) {
                return artist.name;
            }
            return null;
        }

        public static Song from(AudioInfo audioInfo) {
            Song song = new Song();
            song.audioType = "MUSIC";
            song.audioID = audioInfo.audioID;
            song.name = audioInfo.name;
            song.artist = new Artist();
            song.artist.name = audioInfo.artistName;
            song.cpAlbumId = audioInfo.cpAlbumId;
            song.originSongID = audioInfo.cpID;
            song.origin = audioInfo.cpName;
            song.originName = audioInfo.cpDisplayName;
            song.albumName = audioInfo.albumName;
            song.playUrl = audioInfo.playUrl;
            song.coverURL = audioInfo.cover;
            song.vip = audioInfo.vip;
            song.unplayableCode = audioInfo.unplayableCode;
            return song;
        }

        public boolean isVipSong() {
            return this.vip == 1;
        }

        public boolean isSongPreview() {
            return isVipSong() && this.unplayableCode == 2;
        }

        public boolean isLegal() {
            return !TextUtils.isEmpty(this.playUrl) && !TextUtils.isEmpty(this.origin) && !TextUtils.isEmpty(this.originSongID);
        }

        public Simple toSimple() {
            Simple simple = new Simple();
            simple.global_id = this.songID;
            simple.origin = this.origin;
            simple.origin_id = this.originSongID;
            return simple;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemImageUrl() {
            return this.coverURL;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTitle() {
            return this.name;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemId() {
            return this.songID;
        }

        public boolean isCollected() {
            return this.collection == 1;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Song song = (Song) obj;
            if (!this.songID.equals(song.songID)) {
                return false;
            }
            return this.audioID.equals(song.audioID);
        }

        public int hashCode() {
            return 527 + this.songID.hashCode() + this.audioID.hashCode();
        }
    }

    /* loaded from: classes3.dex */
    public static class Channel implements Serializable {
        private static final long serialVersionUID = -3484722954077939507L;
        public Artist artist;
        public String cover;
        public long id;
        public boolean isDefault;
        public String name;
        public int nextStartOffset = -1;
        public boolean operable;
        public String origin;
        public List<Song> songList;
        public List<String> tags;
        public String type;

        /* loaded from: classes3.dex */
        public static class Artist {
            public String artistID;
            public String name;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Channel) && this.id == ((Channel) obj).id);
        }

        public boolean isSelfBuild() {
            return "SELF_OWNED".equals(this.type);
        }
    }

    /* loaded from: classes3.dex */
    public static class Sheet implements Serializable {
        private static final long serialVersionUID = -8554258027169356112L;
        public String cover;
        public String cpName;
        public String intro;
        public String name;
        @SerializedName("id")
        public long sheetID;
        @SerializedName(DomainConfig.SongList.DOMAIN_NAME)
        public List<Long> songIDList;
        public String subtitle;
        public List<String> tags;

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Sheet) && this.sheetID == ((Sheet) obj).sheetID);
        }
    }

    /* loaded from: classes3.dex */
    public static class Station implements Serializable {
        public static final int SOUND_RESOURCE = 2;
        public static final String TYPE_ALBUM = "album";
        public static final String TYPE_RADIO = "radio";
        public static final String TYPE_SOUND = "sound";
        private static final long serialVersionUID = -4790373810258482459L;
        public boolean albumBought;
        public String albumGlobalID;
        public String albumName;
        public int albumSaleType;
        public String audioID;
        public boolean authentication;
        public boolean bought;
        public String broadcaster;
        public String category;
        public int collection;
        public String cover;
        public String cp;
        @SerializedName("albumId")
        public String cpAlbumId;
        @SerializedName("id")
        public String cpID;
        public long duration;
        public int episodes;
        public int episodesNum;
        public String globalID;
        public String isReverse;
        public String origin;
        public String originName;
        public String playSequence;
        public String playUrl;
        public int saleType;
        public long salesPrice;
        public String snippetTitle;
        public String title;
        public String titleAlias;
        public String type;

        public boolean equals(Object obj) {
            String str;
            String str2;
            String str3;
            if (this != obj) {
                if ((obj instanceof Station) && (str = this.cpID) != null) {
                    Station station = (Station) obj;
                    if (!str.equals(station.cpID) || (str2 = this.type) == null || !str2.equals(station.type) || (str3 = this.origin) == null || !str3.equals(station.origin)) {
                    }
                }
                return false;
            }
            return true;
        }

        public boolean isLegal() {
            return !TextUtils.isEmpty(this.cp) && !TextUtils.isEmpty(this.cpID);
        }

        public String getCpResourceType() {
            return "radio".equals(this.type) ? "radio" : "";
        }
    }

    /* loaded from: classes3.dex */
    public static class Favourite {
        public String favouriteId;
        public boolean isFavourite;
        public String origin;
        public String originSongID;

        public boolean isSameSong(Song song) {
            String str = this.origin;
            return str != null && this.originSongID != null && str.equals(song.origin) && this.originSongID.equals(song.originSongID);
        }
    }

    /* loaded from: classes3.dex */
    public static class BabyCourse {
        public String alarmId;
        private int listIndex;
        public String playCP;
        public String playCPId;
        public int playIndex;
        public long playPosition;
        public int stopByCount;
        public long stopByTime;

        public static /* synthetic */ void lambda$uploadPlayState$0(ResponseBody responseBody) throws Exception {
        }

        public int playStationIndex() {
            L.player.d("BabyCourse playStationIndex playIndex=%d, listIndex=%d", Integer.valueOf(this.playIndex), Integer.valueOf(this.listIndex));
            return this.playIndex + this.listIndex;
        }

        public void updatePlayIndex(int i) {
            L.player.d("BabyCourse updatePlayIndex=%d", Integer.valueOf(i));
            this.listIndex = i;
        }

        @SuppressLint({"CheckResult"})
        public void uploadPlayState() {
            ApiManager.minaService.updateCoursePlayState(SystemSetting.getDeviceID(), this.alarmId, this.playCPId, this.playCP, playStationIndex(), this.playPosition).subscribe($$Lambda$Music$BabyCourse$o3_5IpIbO5K1P7vrhwx44CpsuDM.INSTANCE, $$Lambda$Music$BabyCourse$ExlPY40xw_JDwiqK6I__MlJyKUc.INSTANCE);
        }
    }
}
