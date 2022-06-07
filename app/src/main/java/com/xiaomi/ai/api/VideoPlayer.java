package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoPlayer {

    @NamespaceName(name = "PlayMV", namespace = AIApiConstants.VideoPlayer.NAME)
    /* loaded from: classes3.dex */
    public static class PlayMV implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum VideoPlayerStatus {
        UNKNOWN(-1),
        PLAYING(0);
        
        private int id;

        VideoPlayerStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "LaunchPlayApp", namespace = AIApiConstants.VideoPlayer.NAME)
    /* loaded from: classes3.dex */
    public static class LaunchPlayApp implements InstructionPayload {
        @Required
        private List<VideoMeta> items;
        private Optional<String> loadmore_token = Optional.empty();
        private Optional<Boolean> needs_loadmore = Optional.empty();
        private Optional<Integer> episode = Optional.empty();
        private Optional<Common.PageLoadType> load_type = Optional.empty();

        public LaunchPlayApp() {
        }

        public LaunchPlayApp(List<VideoMeta> list) {
            this.items = list;
        }

        @Required
        public LaunchPlayApp setItems(List<VideoMeta> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<VideoMeta> getItems() {
            return this.items;
        }

        public LaunchPlayApp setLoadmoreToken(String str) {
            this.loadmore_token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoadmoreToken() {
            return this.loadmore_token;
        }

        public LaunchPlayApp setNeedsLoadmore(boolean z) {
            this.needs_loadmore = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedsLoadmore() {
            return this.needs_loadmore;
        }

        public LaunchPlayApp setEpisode(int i) {
            this.episode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisode() {
            return this.episode;
        }

        public LaunchPlayApp setLoadType(Common.PageLoadType pageLoadType) {
            this.load_type = Optional.ofNullable(pageLoadType);
            return this;
        }

        public Optional<Common.PageLoadType> getLoadType() {
            return this.load_type;
        }
    }

    @NamespaceName(name = "Play", namespace = AIApiConstants.VideoPlayer.NAME)
    /* loaded from: classes3.dex */
    public static class Play implements InstructionPayload {
        @Required
        private VideoStream stream;
        private Optional<VideoId> id = Optional.empty();
        private Optional<VideoMeta> meta = Optional.empty();
        private Optional<ObjectNode> log = Optional.empty();

        public Play() {
        }

        public Play(VideoStream videoStream) {
            this.stream = videoStream;
        }

        @Required
        public Play setStream(VideoStream videoStream) {
            this.stream = videoStream;
            return this;
        }

        @Required
        public VideoStream getStream() {
            return this.stream;
        }

        public Play setId(VideoId videoId) {
            this.id = Optional.ofNullable(videoId);
            return this;
        }

        public Optional<VideoId> getId() {
            return this.id;
        }

        public Play setMeta(VideoMeta videoMeta) {
            this.meta = Optional.ofNullable(videoMeta);
            return this;
        }

        public Optional<VideoMeta> getMeta() {
            return this.meta;
        }

        public Play setLog(ObjectNode objectNode) {
            this.log = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLog() {
            return this.log;
        }
    }

    @NamespaceName(name = "PlayList", namespace = AIApiConstants.VideoPlayer.NAME)
    /* loaded from: classes3.dex */
    public static class PlayList implements InstructionPayload {
        @Required
        private List<Play> items;
        private Optional<String> loadmore_token = Optional.empty();
        private Optional<Boolean> needs_loadmore = Optional.empty();

        public PlayList() {
        }

        public PlayList(List<Play> list) {
            this.items = list;
        }

        @Required
        public PlayList setItems(List<Play> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<Play> getItems() {
            return this.items;
        }

        public PlayList setLoadmoreToken(String str) {
            this.loadmore_token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoadmoreToken() {
            return this.loadmore_token;
        }

        public PlayList setNeedsLoadmore(boolean z) {
            this.needs_loadmore = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedsLoadmore() {
            return this.needs_loadmore;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoId {
        @Required
        private String id;

        public VideoId() {
        }

        public VideoId(String str) {
            this.id = str;
        }

        @Required
        public VideoId setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoMeta {
        private Optional<Integer> season = Optional.empty();
        private Optional<Integer> episode = Optional.empty();
        @Deprecated
        private Optional<Video.ThirdPartyContentProvider> cp = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<Template.Image> horizontal_poster = Optional.empty();
        private Optional<List<Video.ThirdPartyContentProvider>> cp_list = Optional.empty();
        private Optional<ObjectNode> log = Optional.empty();
        private Optional<Template.VideoLengthType> length_type = Optional.empty();
        private Optional<Common.VideoMediaType> video_media_type = Optional.empty();
        private Optional<String> publish_time = Optional.empty();
        private Optional<String> area = Optional.empty();
        private Optional<Float> rating = Optional.empty();
        private Optional<Integer> duration_in_min = Optional.empty();

        public VideoMeta setSeason(int i) {
            this.season = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSeason() {
            return this.season;
        }

        public VideoMeta setEpisode(int i) {
            this.episode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisode() {
            return this.episode;
        }

        @Deprecated
        public VideoMeta setCp(Video.ThirdPartyContentProvider thirdPartyContentProvider) {
            this.cp = Optional.ofNullable(thirdPartyContentProvider);
            return this;
        }

        @Deprecated
        public Optional<Video.ThirdPartyContentProvider> getCp() {
            return this.cp;
        }

        public VideoMeta setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public VideoMeta setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public VideoMeta setHorizontalPoster(Template.Image image) {
            this.horizontal_poster = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getHorizontalPoster() {
            return this.horizontal_poster;
        }

        public VideoMeta setCpList(List<Video.ThirdPartyContentProvider> list) {
            this.cp_list = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Video.ThirdPartyContentProvider>> getCpList() {
            return this.cp_list;
        }

        public VideoMeta setLog(ObjectNode objectNode) {
            this.log = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLog() {
            return this.log;
        }

        public VideoMeta setLengthType(Template.VideoLengthType videoLengthType) {
            this.length_type = Optional.ofNullable(videoLengthType);
            return this;
        }

        public Optional<Template.VideoLengthType> getLengthType() {
            return this.length_type;
        }

        public VideoMeta setVideoMediaType(Common.VideoMediaType videoMediaType) {
            this.video_media_type = Optional.ofNullable(videoMediaType);
            return this;
        }

        public Optional<Common.VideoMediaType> getVideoMediaType() {
            return this.video_media_type;
        }

        public VideoMeta setPublishTime(String str) {
            this.publish_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPublishTime() {
            return this.publish_time;
        }

        public VideoMeta setArea(String str) {
            this.area = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getArea() {
            return this.area;
        }

        public VideoMeta setRating(float f) {
            this.rating = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getRating() {
            return this.rating;
        }

        public VideoMeta setDurationInMin(int i) {
            this.duration_in_min = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDurationInMin() {
            return this.duration_in_min;
        }
    }

    @NamespaceName(name = "VideoPlaybackState", namespace = AIApiConstants.VideoPlayer.NAME)
    /* loaded from: classes3.dex */
    public static class VideoPlaybackState implements ContextPayload {
        @Required
        private VideoPlayerStatus status;

        public VideoPlaybackState() {
        }

        public VideoPlaybackState(VideoPlayerStatus videoPlayerStatus) {
            this.status = videoPlayerStatus;
        }

        @Required
        public VideoPlaybackState setStatus(VideoPlayerStatus videoPlayerStatus) {
            this.status = videoPlayerStatus;
            return this;
        }

        @Required
        public VideoPlayerStatus getStatus() {
            return this.status;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoStream {
        @Required
        private boolean authentication;
        private Optional<Integer> offset_in_ms = Optional.empty();
        private Optional<String> token = Optional.empty();
        @Required
        private String url;

        public VideoStream() {
        }

        public VideoStream(String str, boolean z) {
            this.url = str;
            this.authentication = z;
        }

        @Required
        public VideoStream setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        @Required
        public VideoStream setAuthentication(boolean z) {
            this.authentication = z;
            return this;
        }

        @Required
        public boolean isAuthentication() {
            return this.authentication;
        }

        public VideoStream setOffsetInMs(int i) {
            this.offset_in_ms = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getOffsetInMs() {
            return this.offset_in_ms;
        }

        public VideoStream setToken(String str) {
            this.token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToken() {
            return this.token;
        }
    }
}
