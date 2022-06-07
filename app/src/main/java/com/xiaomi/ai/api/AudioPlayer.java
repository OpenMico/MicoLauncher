package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioPlayer {

    /* loaded from: classes3.dex */
    public enum AudioPlayerPlayMode {
        UNKNOWN(-1),
        SINGLE(0),
        LIST(1),
        SEQUENCE(2),
        RANDOM(3),
        NONE(4);
        
        private int id;

        AudioPlayerPlayMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AudioPlayerStatus {
        UNKNOWN(-1),
        STOP(0),
        PLAYING(1),
        PAUSE(2);
        
        private int id;

        AudioPlayerStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum FavoritesType {
        UNKNOWN(-1),
        MUSIC(0),
        STATION(1),
        FAV_LIST(2);
        
        private int id;

        FavoritesType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PlayBehavior {
        UNKNOWN(-1),
        REPLACE_ALL(0),
        APPEND(1),
        REPLACE_PENDING(2),
        INSERT_FRONT(3);
        
        private int id;

        PlayBehavior(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PlaybackSwitch {
        UNKNOWN(-1),
        PAUSE(0),
        MANUALLY_NEXT(1),
        AUTOMATICALLY_NEXT(2),
        MANUALLY_OTHER(3);
        
        private int id;

        PlaybackSwitch(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SkipControlType {
        NEXT(0),
        PREV(1);
        
        private int id;

        SkipControlType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "AddToFavorites", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class AddToFavorites implements InstructionPayload {
        @Required
        private FavoritesType type;

        public AddToFavorites() {
        }

        public AddToFavorites(FavoritesType favoritesType) {
            this.type = favoritesType;
        }

        @Required
        public AddToFavorites setType(FavoritesType favoritesType) {
            this.type = favoritesType;
            return this;
        }

        @Required
        public FavoritesType getType() {
            return this.type;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppAudioItem {
        @Required
        private String album_id;
        @Required
        private String album_name;
        @Required
        private String artist_id;
        @Required
        private String artist_name;
        @Required
        private String audio_id;
        @Required
        private String audio_name;

        public AppAudioItem() {
        }

        public AppAudioItem(String str, String str2, String str3, String str4, String str5, String str6) {
            this.audio_id = str;
            this.audio_name = str2;
            this.artist_name = str3;
            this.artist_id = str4;
            this.album_name = str5;
            this.album_id = str6;
        }

        @Required
        public AppAudioItem setAudioId(String str) {
            this.audio_id = str;
            return this;
        }

        @Required
        public String getAudioId() {
            return this.audio_id;
        }

        @Required
        public AppAudioItem setAudioName(String str) {
            this.audio_name = str;
            return this;
        }

        @Required
        public String getAudioName() {
            return this.audio_name;
        }

        @Required
        public AppAudioItem setArtistName(String str) {
            this.artist_name = str;
            return this;
        }

        @Required
        public String getArtistName() {
            return this.artist_name;
        }

        @Required
        public AppAudioItem setArtistId(String str) {
            this.artist_id = str;
            return this;
        }

        @Required
        public String getArtistId() {
            return this.artist_id;
        }

        @Required
        public AppAudioItem setAlbumName(String str) {
            this.album_name = str;
            return this;
        }

        @Required
        public String getAlbumName() {
            return this.album_name;
        }

        @Required
        public AppAudioItem setAlbumId(String str) {
            this.album_id = str;
            return this;
        }

        @Required
        public String getAlbumId() {
            return this.album_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioItemV1 {
        @Required
        private ItemId item_id;
        @Required
        private Stream stream;
        private Optional<Integer> play_times = Optional.empty();
        private Optional<List<SkipControlType>> skip_controls = Optional.empty();
        private Optional<Boolean> is_playlist_hidden = Optional.empty();
        private Optional<ObjectNode> log = Optional.empty();
        private Optional<String> text = Optional.empty();
        private Optional<Template.Image> background_image = Optional.empty();
        private Optional<String> publish_time = Optional.empty();

        public AudioItemV1() {
        }

        public AudioItemV1(ItemId itemId, Stream stream) {
            this.item_id = itemId;
            this.stream = stream;
        }

        @Required
        public AudioItemV1 setItemId(ItemId itemId) {
            this.item_id = itemId;
            return this;
        }

        @Required
        public ItemId getItemId() {
            return this.item_id;
        }

        @Required
        public AudioItemV1 setStream(Stream stream) {
            this.stream = stream;
            return this;
        }

        @Required
        public Stream getStream() {
            return this.stream;
        }

        public AudioItemV1 setPlayTimes(int i) {
            this.play_times = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPlayTimes() {
            return this.play_times;
        }

        public AudioItemV1 setSkipControls(List<SkipControlType> list) {
            this.skip_controls = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SkipControlType>> getSkipControls() {
            return this.skip_controls;
        }

        public AudioItemV1 setIsPlaylistHidden(boolean z) {
            this.is_playlist_hidden = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPlaylistHidden() {
            return this.is_playlist_hidden;
        }

        public AudioItemV1 setLog(ObjectNode objectNode) {
            this.log = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLog() {
            return this.log;
        }

        public AudioItemV1 setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }

        public AudioItemV1 setBackgroundImage(Template.Image image) {
            this.background_image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getBackgroundImage() {
            return this.background_image;
        }

        public AudioItemV1 setPublishTime(String str) {
            this.publish_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPublishTime() {
            return this.publish_time;
        }
    }

    @NamespaceName(name = "CancelFromFavorites", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class CancelFromFavorites implements InstructionPayload {
        @Required
        private FavoritesType type;

        public CancelFromFavorites() {
        }

        public CancelFromFavorites(FavoritesType favoritesType) {
            this.type = favoritesType;
        }

        @Required
        public CancelFromFavorites setType(FavoritesType favoritesType) {
            this.type = favoritesType;
            return this;
        }

        @Required
        public FavoritesType getType() {
            return this.type;
        }
    }

    /* loaded from: classes3.dex */
    public static class ContentProvider {
        @Required
        private String id;
        @Required
        private String name;
        private Optional<String> label = Optional.empty();
        private Optional<String> label_id = Optional.empty();
        private Optional<String> album_id = Optional.empty();
        private Optional<Integer> episode_index = Optional.empty();
        private Optional<String> chinese_name = Optional.empty();

        public ContentProvider() {
        }

        public ContentProvider(String str, String str2) {
            this.name = str;
            this.id = str2;
        }

        @Required
        public ContentProvider setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public ContentProvider setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        public ContentProvider setLabel(String str) {
            this.label = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLabel() {
            return this.label;
        }

        public ContentProvider setLabelId(String str) {
            this.label_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLabelId() {
            return this.label_id;
        }

        public ContentProvider setAlbumId(String str) {
            this.album_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAlbumId() {
            return this.album_id;
        }

        public ContentProvider setEpisodeIndex(int i) {
            this.episode_index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisodeIndex() {
            return this.episode_index;
        }

        public ContentProvider setChineseName(String str) {
            this.chinese_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getChineseName() {
            return this.chinese_name;
        }
    }

    /* loaded from: classes3.dex */
    public static class ItemId {
        @Required
        private String audio_id;
        private Optional<ContentProvider> cp = Optional.empty();
        @Deprecated
        private Optional<String> album_id = Optional.empty();

        public ItemId() {
        }

        public ItemId(String str) {
            this.audio_id = str;
        }

        public ItemId setCp(ContentProvider contentProvider) {
            this.cp = Optional.ofNullable(contentProvider);
            return this;
        }

        public Optional<ContentProvider> getCp() {
            return this.cp;
        }

        @Required
        public ItemId setAudioId(String str) {
            this.audio_id = str;
            return this;
        }

        @Required
        public String getAudioId() {
            return this.audio_id;
        }

        @Deprecated
        public ItemId setAlbumId(String str) {
            this.album_id = Optional.ofNullable(str);
            return this;
        }

        @Deprecated
        public Optional<String> getAlbumId() {
            return this.album_id;
        }
    }

    @NamespaceName(name = "Play", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class Play implements InstructionPayload {
        @Required
        private List<AudioItemV1> audio_items;
        @Required
        private PlayBehavior play_behavior;
        private Optional<Long> favorites_id = Optional.empty();
        private Optional<String> loadmore_token = Optional.empty();
        private Optional<Common.AudioType> audio_type = Optional.empty();
        private Optional<Boolean> needs_loadmore = Optional.empty();
        private Optional<Long> play_duration = Optional.empty();
        private Optional<String> origin_id = Optional.empty();
        private Optional<Integer> loadmore_delta = Optional.empty();
        private Optional<Boolean> is_night_mode_play_audio = Optional.empty();

        public Play() {
        }

        public Play(PlayBehavior playBehavior, List<AudioItemV1> list) {
            this.play_behavior = playBehavior;
            this.audio_items = list;
        }

        @Required
        public Play setPlayBehavior(PlayBehavior playBehavior) {
            this.play_behavior = playBehavior;
            return this;
        }

        @Required
        public PlayBehavior getPlayBehavior() {
            return this.play_behavior;
        }

        @Required
        public Play setAudioItems(List<AudioItemV1> list) {
            this.audio_items = list;
            return this;
        }

        @Required
        public List<AudioItemV1> getAudioItems() {
            return this.audio_items;
        }

        public Play setFavoritesId(long j) {
            this.favorites_id = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getFavoritesId() {
            return this.favorites_id;
        }

        public Play setLoadmoreToken(String str) {
            this.loadmore_token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoadmoreToken() {
            return this.loadmore_token;
        }

        public Play setAudioType(Common.AudioType audioType) {
            this.audio_type = Optional.ofNullable(audioType);
            return this;
        }

        public Optional<Common.AudioType> getAudioType() {
            return this.audio_type;
        }

        public Play setNeedsLoadmore(boolean z) {
            this.needs_loadmore = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedsLoadmore() {
            return this.needs_loadmore;
        }

        public Play setPlayDuration(long j) {
            this.play_duration = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPlayDuration() {
            return this.play_duration;
        }

        public Play setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public Play setLoadmoreDelta(int i) {
            this.loadmore_delta = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getLoadmoreDelta() {
            return this.loadmore_delta;
        }

        public Play setIsNightModePlayAudio(boolean z) {
            this.is_night_mode_play_audio = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNightModePlayAudio() {
            return this.is_night_mode_play_audio;
        }
    }

    @NamespaceName(name = "PlayApp", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class PlayApp implements InstructionPayload {
        @Required

        /* renamed from: app */
        private Template.AndroidApp f195app;
        @Required
        private List<AppAudioItem> audio_items;
        @Required
        private int play_length;

        public PlayApp() {
        }

        public PlayApp(List<AppAudioItem> list, Template.AndroidApp androidApp, int i) {
            this.audio_items = list;
            this.f195app = androidApp;
            this.play_length = i;
        }

        @Required
        public PlayApp setAudioItems(List<AppAudioItem> list) {
            this.audio_items = list;
            return this;
        }

        @Required
        public List<AppAudioItem> getAudioItems() {
            return this.audio_items;
        }

        @Required
        public PlayApp setApp(Template.AndroidApp androidApp) {
            this.f195app = androidApp;
            return this;
        }

        @Required
        public Template.AndroidApp getApp() {
            return this.f195app;
        }

        @Required
        public PlayApp setPlayLength(int i) {
            this.play_length = i;
            return this;
        }

        @Required
        public int getPlayLength() {
            return this.play_length;
        }
    }

    @NamespaceName(name = "PlayFavorites", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class PlayFavorites implements InstructionPayload {
        private Optional<String> app_name = Optional.empty();
        @Required
        private FavoritesType type;

        public PlayFavorites() {
        }

        public PlayFavorites(FavoritesType favoritesType) {
            this.type = favoritesType;
        }

        @Required
        public PlayFavorites setType(FavoritesType favoritesType) {
            this.type = favoritesType;
            return this;
        }

        @Required
        public FavoritesType getType() {
            return this.type;
        }

        public PlayFavorites setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }
    }

    /* loaded from: classes3.dex */
    public static class PlaybackAudioMeta {
        private Optional<String> audio_id = Optional.empty();
        private Optional<Long> favorites_id = Optional.empty();
        private Optional<ContentProvider> cp = Optional.empty();
        private Optional<Common.AudioType> audio_type = Optional.empty();
        private Optional<Boolean> is_alarm = Optional.empty();

        public PlaybackAudioMeta setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public PlaybackAudioMeta setFavoritesId(long j) {
            this.favorites_id = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getFavoritesId() {
            return this.favorites_id;
        }

        public PlaybackAudioMeta setCp(ContentProvider contentProvider) {
            this.cp = Optional.ofNullable(contentProvider);
            return this;
        }

        public Optional<ContentProvider> getCp() {
            return this.cp;
        }

        public PlaybackAudioMeta setAudioType(Common.AudioType audioType) {
            this.audio_type = Optional.ofNullable(audioType);
            return this;
        }

        public Optional<Common.AudioType> getAudioType() {
            return this.audio_type;
        }

        public PlaybackAudioMeta setIsAlarm(boolean z) {
            this.is_alarm = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAlarm() {
            return this.is_alarm;
        }
    }

    /* loaded from: classes3.dex */
    public static class PlaybackOnError {
        @Required
        private int code;
        @Required
        private String msg;
        @Required
        private long ts;

        public PlaybackOnError() {
        }

        public PlaybackOnError(long j, int i, String str) {
            this.ts = j;
            this.code = i;
            this.msg = str;
        }

        @Required
        public PlaybackOnError setTs(long j) {
            this.ts = j;
            return this;
        }

        @Required
        public long getTs() {
            return this.ts;
        }

        @Required
        public PlaybackOnError setCode(int i) {
            this.code = i;
            return this;
        }

        @Required
        public int getCode() {
            return this.code;
        }

        @Required
        public PlaybackOnError setMsg(String str) {
            this.msg = str;
            return this;
        }

        @Required
        public String getMsg() {
            return this.msg;
        }
    }

    /* loaded from: classes3.dex */
    public static class PlaybackOnSwitch {
        @Required
        private long end_ts;
        @Required
        private long loading_in_ms;
        @Required
        private PlaybackSwitch playback_switch;
        @Required
        private int position_in_ms;
        @Required
        private long start_ts;

        public PlaybackOnSwitch() {
        }

        public PlaybackOnSwitch(long j, long j2, long j3, int i, PlaybackSwitch playbackSwitch) {
            this.loading_in_ms = j;
            this.start_ts = j2;
            this.end_ts = j3;
            this.position_in_ms = i;
            this.playback_switch = playbackSwitch;
        }

        @Required
        public PlaybackOnSwitch setLoadingInMs(long j) {
            this.loading_in_ms = j;
            return this;
        }

        @Required
        public long getLoadingInMs() {
            return this.loading_in_ms;
        }

        @Required
        public PlaybackOnSwitch setStartTs(long j) {
            this.start_ts = j;
            return this;
        }

        @Required
        public long getStartTs() {
            return this.start_ts;
        }

        @Required
        public PlaybackOnSwitch setEndTs(long j) {
            this.end_ts = j;
            return this;
        }

        @Required
        public long getEndTs() {
            return this.end_ts;
        }

        @Required
        public PlaybackOnSwitch setPositionInMs(int i) {
            this.position_in_ms = i;
            return this;
        }

        @Required
        public int getPositionInMs() {
            return this.position_in_ms;
        }

        @Required
        public PlaybackOnSwitch setPlaybackSwitch(PlaybackSwitch playbackSwitch) {
            this.playback_switch = playbackSwitch;
            return this;
        }

        @Required
        public PlaybackSwitch getPlaybackSwitch() {
            return this.playback_switch;
        }
    }

    @NamespaceName(name = "PlaybackState", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class PlaybackState implements ContextPayload {
        @Required
        private AudioPlayerStatus status;
        private Optional<AudioPlayerPlayMode> play_mode = Optional.empty();
        private Optional<Integer> volume = Optional.empty();
        @Deprecated
        private Optional<String> audio_id = Optional.empty();
        @Deprecated
        private Optional<Long> favorites_id = Optional.empty();
        private Optional<PlaybackAudioMeta> audio_meta = Optional.empty();
        @Deprecated
        private Optional<Boolean> kid_mode = Optional.empty();
        private Optional<Boolean> disable_personalize = Optional.empty();
        private Optional<List<ThirdPartyPlaybackAudioMeta>> third_party_meta = Optional.empty();

        public PlaybackState() {
        }

        public PlaybackState(AudioPlayerStatus audioPlayerStatus) {
            this.status = audioPlayerStatus;
        }

        @Required
        public PlaybackState setStatus(AudioPlayerStatus audioPlayerStatus) {
            this.status = audioPlayerStatus;
            return this;
        }

        @Required
        public AudioPlayerStatus getStatus() {
            return this.status;
        }

        public PlaybackState setPlayMode(AudioPlayerPlayMode audioPlayerPlayMode) {
            this.play_mode = Optional.ofNullable(audioPlayerPlayMode);
            return this;
        }

        public Optional<AudioPlayerPlayMode> getPlayMode() {
            return this.play_mode;
        }

        public PlaybackState setVolume(int i) {
            this.volume = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVolume() {
            return this.volume;
        }

        @Deprecated
        public PlaybackState setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        @Deprecated
        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        @Deprecated
        public PlaybackState setFavoritesId(long j) {
            this.favorites_id = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        @Deprecated
        public Optional<Long> getFavoritesId() {
            return this.favorites_id;
        }

        public PlaybackState setAudioMeta(PlaybackAudioMeta playbackAudioMeta) {
            this.audio_meta = Optional.ofNullable(playbackAudioMeta);
            return this;
        }

        public Optional<PlaybackAudioMeta> getAudioMeta() {
            return this.audio_meta;
        }

        @Deprecated
        public PlaybackState setKidMode(boolean z) {
            this.kid_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        @Deprecated
        public Optional<Boolean> isKidMode() {
            return this.kid_mode;
        }

        public PlaybackState setDisablePersonalize(boolean z) {
            this.disable_personalize = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDisablePersonalize() {
            return this.disable_personalize;
        }

        public PlaybackState setThirdPartyMeta(List<ThirdPartyPlaybackAudioMeta> list) {
            this.third_party_meta = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ThirdPartyPlaybackAudioMeta>> getThirdPartyMeta() {
            return this.third_party_meta;
        }
    }

    @NamespaceName(name = "PlaybackTrack", namespace = "AudioPlayer")
    /* loaded from: classes3.dex */
    public static class PlaybackTrack implements EventPayload {
        @Required
        private AudioItemV1 audio_item;
        @Required
        private String dialog_id;
        private Optional<PlaybackOnSwitch> on_switch = Optional.empty();
        private Optional<PlaybackOnError> on_error = Optional.empty();

        public PlaybackTrack() {
        }

        public PlaybackTrack(String str, AudioItemV1 audioItemV1) {
            this.dialog_id = str;
            this.audio_item = audioItemV1;
        }

        @Required
        public PlaybackTrack setDialogId(String str) {
            this.dialog_id = str;
            return this;
        }

        @Required
        public String getDialogId() {
            return this.dialog_id;
        }

        @Required
        public PlaybackTrack setAudioItem(AudioItemV1 audioItemV1) {
            this.audio_item = audioItemV1;
            return this;
        }

        @Required
        public AudioItemV1 getAudioItem() {
            return this.audio_item;
        }

        public PlaybackTrack setOnSwitch(PlaybackOnSwitch playbackOnSwitch) {
            this.on_switch = Optional.ofNullable(playbackOnSwitch);
            return this;
        }

        public Optional<PlaybackOnSwitch> getOnSwitch() {
            return this.on_switch;
        }

        public PlaybackTrack setOnError(PlaybackOnError playbackOnError) {
            this.on_error = Optional.ofNullable(playbackOnError);
            return this;
        }

        public Optional<PlaybackOnError> getOnError() {
            return this.on_error;
        }
    }

    /* loaded from: classes3.dex */
    public static class Stream {
        @Required
        private boolean authentication;
        @Required
        private int duration_in_ms;
        @Required
        private int offset_in_ms;
        private Optional<Boolean> redirect = Optional.empty();
        private Optional<String> token = Optional.empty();
        @Required
        private String url;

        public Stream() {
        }

        public Stream(String str, boolean z, int i, int i2) {
            this.url = str;
            this.authentication = z;
            this.offset_in_ms = i;
            this.duration_in_ms = i2;
        }

        @Required
        public Stream setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        @Required
        public Stream setAuthentication(boolean z) {
            this.authentication = z;
            return this;
        }

        @Required
        public boolean isAuthentication() {
            return this.authentication;
        }

        @Required
        public Stream setOffsetInMs(int i) {
            this.offset_in_ms = i;
            return this;
        }

        @Required
        public int getOffsetInMs() {
            return this.offset_in_ms;
        }

        @Required
        public Stream setDurationInMs(int i) {
            this.duration_in_ms = i;
            return this;
        }

        @Required
        public int getDurationInMs() {
            return this.duration_in_ms;
        }

        public Stream setRedirect(boolean z) {
            this.redirect = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isRedirect() {
            return this.redirect;
        }

        public Stream setToken(String str) {
            this.token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToken() {
            return this.token;
        }
    }

    /* loaded from: classes3.dex */
    public static class ThirdPartyPlaybackAudioMeta {
        private Optional<String> app_name = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<String> artist = Optional.empty();
        private Optional<String> album = Optional.empty();

        public ThirdPartyPlaybackAudioMeta setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }

        public ThirdPartyPlaybackAudioMeta setTitle(String str) {
            this.title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTitle() {
            return this.title;
        }

        public ThirdPartyPlaybackAudioMeta setArtist(String str) {
            this.artist = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getArtist() {
            return this.artist;
        }

        public ThirdPartyPlaybackAudioMeta setAlbum(String str) {
            this.album = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAlbum() {
            return this.album;
        }
    }
}
