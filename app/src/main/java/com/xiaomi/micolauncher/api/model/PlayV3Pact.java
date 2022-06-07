package com.xiaomi.micolauncher.api.model;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PlayV3Pact {
    public static final String LIST_TYPE_PLAYLIST = "PLAYLIST";
    public static final String LIST_TYPE_SONGBOOK = "SONGBOOK";
    public static final String LIST_TYPE_STATION = "STATION";
    public static final String NOT_REVERSE = "NOT_REVERSE";
    public static final String REVERSE = "REVERSE";
    public static final String TYPE_ALERT_SOUND = "ALERT_SOUND";
    public static final String TYPE_ANCIENT_POEM = "ANCIENT_POEM";
    public static final String TYPE_BOOKS = "BOOKS";
    public static final String TYPE_JOKE = "JOKE";
    public static final String TYPE_MUSIC = "MUSIC";
    public static final String TYPE_NEWS = "NEWS";
    public static final String TYPE_RADIO_STATION = "RADIO_STATION";
    public static final String TYPE_TRANSLATION = "TRANSLATION";
    public static final String TYPE_TTS = "TTS";
    public static final String TYPE_VOICE = "VOICE";
    public static final String TYPE_WHITE_NOISE = "WHITE_NOISE";
    public V3Payload payload;

    /* loaded from: classes3.dex */
    public static class ListParams {
        public String listId;
        public int loadmore_offset;
        public String order;
        public String origin;
        public String type;
    }

    public static PlayV3Pact buildPLayV3Pact(List<Music.Song> list) {
        PlayV3Pact playV3Pact = new PlayV3Pact();
        playV3Pact.payload = V3Payload.buildV3Payload(list);
        return playV3Pact;
    }

    /* loaded from: classes3.dex */
    public static class V3Payload {
        public List<AudioItem> audio_items;
        public String audio_type;
        public ListParams list_params;
        public boolean needs_loadmore;
        public String play_behavior;

        public static V3Payload buildV3Payload(List<Music.Song> list) {
            V3Payload v3Payload = new V3Payload();
            v3Payload.audio_type = "MUSIC";
            v3Payload.play_behavior = "REPLACE_ALL";
            v3Payload.needs_loadmore = false;
            ArrayList arrayList = new ArrayList();
            for (Music.Song song : list) {
                arrayList.add(AudioItem.buildSong(song));
            }
            v3Payload.audio_items = arrayList;
            return v3Payload;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioItem {
        public ItemId item_id;
        public Stream stream;

        public static AudioItem buildSong(Music.Song song) {
            AudioItem audioItem = new AudioItem();
            audioItem.item_id = new ItemId(song);
            audioItem.stream = new Stream(song);
            return audioItem;
        }

        public static AudioItem buildStation(Music.Station station) {
            AudioItem audioItem = new AudioItem();
            audioItem.item_id = new ItemId(station);
            audioItem.stream = new Stream(station);
            return audioItem;
        }
    }

    /* loaded from: classes3.dex */
    public static class ItemId {
        public String audio_id;
        public ContentProvider cp;

        public ItemId(Music.Song song) {
            this.audio_id = song.audioID;
            this.cp = new ContentProvider(song);
        }

        public ItemId(Music.Station station) {
            this.audio_id = station.audioID;
            this.cp = new ContentProvider(station);
        }

        /* loaded from: classes3.dex */
        public static class ContentProvider {
            public String album_id;
            public int episode_index;
            public String id;
            public String label;
            public String label_id;
            public String name;

            public ContentProvider(Music.Song song) {
                this.name = song.origin;
                this.id = song.originSongID;
                this.album_id = song.cpAlbumId;
            }

            public ContentProvider(Music.Station station) {
                this.name = station.origin;
                this.id = station.cpID;
                this.episode_index = station.episodesNum;
                this.album_id = station.cpAlbumId;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Stream {
        public boolean authentication;
        public long duration_in_ms;
        public int offset_in_ms;
        public boolean redirect;
        public String url;

        public Stream(Music.Song song) {
            this.authentication = song.authentication;
            this.duration_in_ms = song.duration;
            this.offset_in_ms = song.offsetInMs;
            this.url = song.playUrl;
        }

        public Stream(Music.Station station) {
            this.authentication = station.authentication;
            this.duration_in_ms = station.duration;
            this.offset_in_ms = 0;
            this.url = station.playUrl;
        }
    }
}
