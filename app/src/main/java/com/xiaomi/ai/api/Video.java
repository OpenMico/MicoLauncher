package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Video {

    /* loaded from: classes3.dex */
    public enum CPAppType {
        TENCENT_VIDEO(0),
        IQIYI(1),
        YOUKU(2),
        MANGGUO_TV(3),
        BILIBILI(4),
        MI_PLAYER(5),
        VIPKID(6),
        MAOYAN(7),
        DOUBAN(8),
        SOUHU(9);
        
        private int id;

        CPAppType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MediaVipType {
        UNKNOWN(-1),
        MOVIES(1),
        KIDS(2),
        EDU(3);
        
        private int id;

        MediaVipType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PageType {
        UNKNOWN(-1),
        SEARCH_PAGE(0),
        DETAIL_PAGE(1),
        PLAYING_PAGE(2);
        
        private int id;

        PageType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoRecogAction {
        RECG(0),
        FIND(1),
        RUNAPP(2),
        UNKNOWN(3);
        
        private int id;

        VideoRecogAction(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoRecogActionType {
        FIRST_APPEARANCE(0),
        TIMES(1);
        
        private int id;

        VideoRecogActionType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoRecogDirection {
        RIGHT(0),
        LEFT(1),
        UP(2),
        DOWN(3),
        FRONT(4),
        BACK(5),
        MID(6);
        
        private int id;

        VideoRecogDirection(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoRecogTarget {
        PERSON(0),
        LOCATION(1),
        OBJECT(2);
        
        private int id;

        VideoRecogTarget(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "CurrentPageState", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class CurrentPageState implements ContextPayload {
        @Required
        private List<ResourceEntity> entity;
        @Required
        private PageType page_type;

        public CurrentPageState() {
        }

        public CurrentPageState(PageType pageType, List<ResourceEntity> list) {
            this.page_type = pageType;
            this.entity = list;
        }

        @Required
        public CurrentPageState setPageType(PageType pageType) {
            this.page_type = pageType;
            return this;
        }

        @Required
        public PageType getPageType() {
            return this.page_type;
        }

        @Required
        public CurrentPageState setEntity(List<ResourceEntity> list) {
            this.entity = list;
            return this;
        }

        @Required
        public List<ResourceEntity> getEntity() {
            return this.entity;
        }
    }

    @NamespaceName(name = "Disambiguation", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class Disambiguation implements InstructionPayload {
        @Required
        private List<VideoSearchItemWithTag> candidates;

        public Disambiguation() {
        }

        public Disambiguation(List<VideoSearchItemWithTag> list) {
            this.candidates = list;
        }

        @Required
        public Disambiguation setCandidates(List<VideoSearchItemWithTag> list) {
            this.candidates = list;
            return this;
        }

        @Required
        public List<VideoSearchItemWithTag> getCandidates() {
            return this.candidates;
        }
    }

    /* loaded from: classes3.dex */
    public static class DisplayCornerImage {
        private Optional<String> url = Optional.empty();
        private Optional<Integer> tv_screen_type = Optional.empty();

        public DisplayCornerImage setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public DisplayCornerImage setTvScreenType(int i) {
            this.tv_screen_type = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTvScreenType() {
            return this.tv_screen_type;
        }
    }

    @NamespaceName(name = "DisplayDetails", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class DisplayDetails implements EventPayload {
        @Required
        private String ref;
        @Required
        private long ts;
        @Required
        private String video_id;

        public DisplayDetails() {
        }

        public DisplayDetails(String str, String str2, long j) {
            this.video_id = str;
            this.ref = str2;
            this.ts = j;
        }

        @Required
        public DisplayDetails setVideoId(String str) {
            this.video_id = str;
            return this;
        }

        @Required
        public String getVideoId() {
            return this.video_id;
        }

        @Required
        public DisplayDetails setRef(String str) {
            this.ref = str;
            return this;
        }

        @Required
        public String getRef() {
            return this.ref;
        }

        @Required
        public DisplayDetails setTs(long j) {
            this.ts = j;
            return this;
        }

        @Required
        public long getTs() {
            return this.ts;
        }
    }

    /* loaded from: classes3.dex */
    public static class DisplayRating {
        private Optional<String> rating_source = Optional.empty();
        private Optional<Double> score = Optional.empty();

        public DisplayRating setRatingSource(String str) {
            this.rating_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRatingSource() {
            return this.rating_source;
        }

        public DisplayRating setScore(double d) {
            this.score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getScore() {
            return this.score;
        }
    }

    /* loaded from: classes3.dex */
    public static class ResourceEntity {
        @Required
        private String id;
        @Required
        private String name;
        private Optional<Integer> index = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<Integer> cur_episode = Optional.empty();
        private Optional<String> result_type = Optional.empty();
        private Optional<Boolean> labeled = Optional.empty();
        private Optional<String> meta_id = Optional.empty();

        public ResourceEntity() {
        }

        public ResourceEntity(String str, String str2) {
            this.name = str;
            this.id = str2;
        }

        @Required
        public ResourceEntity setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public ResourceEntity setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        public ResourceEntity setIndex(int i) {
            this.index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIndex() {
            return this.index;
        }

        public ResourceEntity setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public ResourceEntity setCurEpisode(int i) {
            this.cur_episode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCurEpisode() {
            return this.cur_episode;
        }

        public ResourceEntity setResultType(String str) {
            this.result_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultType() {
            return this.result_type;
        }

        public ResourceEntity setLabeled(boolean z) {
            this.labeled = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLabeled() {
            return this.labeled;
        }

        public ResourceEntity setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }
    }

    @NamespaceName(name = "SearchHistory", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class SearchHistory implements InstructionPayload {
        private Optional<String> date = Optional.empty();
        @Required
        private boolean play_after_search;
        @Required
        private List<VideoSearchTags> slots;

        public SearchHistory() {
        }

        public SearchHistory(List<VideoSearchTags> list, boolean z) {
            this.slots = list;
            this.play_after_search = z;
        }

        @Required
        public SearchHistory setSlots(List<VideoSearchTags> list) {
            this.slots = list;
            return this;
        }

        @Required
        public List<VideoSearchTags> getSlots() {
            return this.slots;
        }

        @Required
        public SearchHistory setPlayAfterSearch(boolean z) {
            this.play_after_search = z;
            return this;
        }

        @Required
        public boolean isPlayAfterSearch() {
            return this.play_after_search;
        }

        public SearchHistory setDate(String str) {
            this.date = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDate() {
            return this.date;
        }
    }

    @NamespaceName(name = "SearchTagsReset", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class SearchTagsReset implements EventPayload {
        private Optional<List<VideoSearchTags>> tags = Optional.empty();

        public SearchTagsReset setTags(List<VideoSearchTags> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<VideoSearchTags>> getTags() {
            return this.tags;
        }
    }

    @NamespaceName(name = "SearchTagsTVPersonPhrase2", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class SearchTagsTVPersonPhrase2 implements EventPayload {
        private Optional<List<VideoSearchTags>> tags = Optional.empty();

        public SearchTagsTVPersonPhrase2 setTags(List<VideoSearchTags> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<VideoSearchTags>> getTags() {
            return this.tags;
        }
    }

    @NamespaceName(name = "ShowDetailPage", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class ShowDetailPage implements InstructionPayload {
        @Required
        private VideoSearchItem item;

        public ShowDetailPage() {
        }

        public ShowDetailPage(VideoSearchItem videoSearchItem) {
            this.item = videoSearchItem;
        }

        @Required
        public ShowDetailPage setItem(VideoSearchItem videoSearchItem) {
            this.item = videoSearchItem;
            return this;
        }

        @Required
        public VideoSearchItem getItem() {
            return this.item;
        }
    }

    @NamespaceName(name = "ShowSearchPage", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class ShowSearchPage implements InstructionPayload {
        @Required
        private List<VideoSearchItem> items;
        private Optional<VideoSearchCursor> cursor = Optional.empty();
        private Optional<List<VideoSearchTags>> tags = Optional.empty();
        private Optional<Integer> total_size = Optional.empty();
        private Optional<String> accuracy = Optional.empty();
        private Optional<Common.LoadmoreInfo> loadmore_info = Optional.empty();
        private Optional<Common.PageLoadType> load_type = Optional.empty();

        public ShowSearchPage() {
        }

        public ShowSearchPage(List<VideoSearchItem> list) {
            this.items = list;
        }

        @Required
        public ShowSearchPage setItems(List<VideoSearchItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<VideoSearchItem> getItems() {
            return this.items;
        }

        public ShowSearchPage setCursor(VideoSearchCursor videoSearchCursor) {
            this.cursor = Optional.ofNullable(videoSearchCursor);
            return this;
        }

        public Optional<VideoSearchCursor> getCursor() {
            return this.cursor;
        }

        public ShowSearchPage setTags(List<VideoSearchTags> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<VideoSearchTags>> getTags() {
            return this.tags;
        }

        public ShowSearchPage setTotalSize(int i) {
            this.total_size = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTotalSize() {
            return this.total_size;
        }

        public ShowSearchPage setAccuracy(String str) {
            this.accuracy = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAccuracy() {
            return this.accuracy;
        }

        public ShowSearchPage setLoadmoreInfo(Common.LoadmoreInfo loadmoreInfo) {
            this.loadmore_info = Optional.ofNullable(loadmoreInfo);
            return this;
        }

        public Optional<Common.LoadmoreInfo> getLoadmoreInfo() {
            return this.loadmore_info;
        }

        public ShowSearchPage setLoadType(Common.PageLoadType pageLoadType) {
            this.load_type = Optional.ofNullable(pageLoadType);
            return this;
        }

        public Optional<Common.PageLoadType> getLoadType() {
            return this.load_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class ThirdPartyContentProvider {
        @Required
        private String name;
        private Optional<String> resource_id = Optional.empty();
        private Optional<Template.Launcher> launcher = Optional.empty();
        private Optional<Boolean> is_free = Optional.empty();
        private Optional<Template.Image> icon = Optional.empty();
        private Optional<List<String>> episode_ids = Optional.empty();
        private Optional<CPAppType> play_app = Optional.empty();
        private Optional<VideoPlayer.VideoStream> stream = Optional.empty();
        private Optional<MediaVipType> media_vip_type = Optional.empty();
        private Optional<String> audio_id = Optional.empty();
        private Optional<Boolean> is_history = Optional.empty();

        public ThirdPartyContentProvider() {
        }

        public ThirdPartyContentProvider(String str) {
            this.name = str;
        }

        @Required
        public ThirdPartyContentProvider setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public ThirdPartyContentProvider setResourceId(String str) {
            this.resource_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceId() {
            return this.resource_id;
        }

        public ThirdPartyContentProvider setLauncher(Template.Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Template.Launcher> getLauncher() {
            return this.launcher;
        }

        public ThirdPartyContentProvider setIsFree(boolean z) {
            this.is_free = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFree() {
            return this.is_free;
        }

        public ThirdPartyContentProvider setIcon(Template.Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getIcon() {
            return this.icon;
        }

        public ThirdPartyContentProvider setEpisodeIds(List<String> list) {
            this.episode_ids = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getEpisodeIds() {
            return this.episode_ids;
        }

        public ThirdPartyContentProvider setPlayApp(CPAppType cPAppType) {
            this.play_app = Optional.ofNullable(cPAppType);
            return this;
        }

        public Optional<CPAppType> getPlayApp() {
            return this.play_app;
        }

        public ThirdPartyContentProvider setStream(VideoPlayer.VideoStream videoStream) {
            this.stream = Optional.ofNullable(videoStream);
            return this;
        }

        public Optional<VideoPlayer.VideoStream> getStream() {
            return this.stream;
        }

        public ThirdPartyContentProvider setMediaVipType(MediaVipType mediaVipType) {
            this.media_vip_type = Optional.ofNullable(mediaVipType);
            return this;
        }

        public Optional<MediaVipType> getMediaVipType() {
            return this.media_vip_type;
        }

        public ThirdPartyContentProvider setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public ThirdPartyContentProvider setIsHistory(boolean z) {
            this.is_history = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHistory() {
            return this.is_history;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoExtra {
        private Optional<Integer> publish_year = Optional.empty();
        private Optional<Float> rating = Optional.empty();
        private Optional<ThirdPartyContentProvider> third_cp = Optional.empty();
        private Optional<String> language = Optional.empty();
        private Optional<Boolean> free = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<ObjectNode> log = Optional.empty();
        private Optional<DisplayRating> display_rating = Optional.empty();
        private Optional<DisplayCornerImage> display_corner_image = Optional.empty();

        public VideoExtra setPublishYear(int i) {
            this.publish_year = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPublishYear() {
            return this.publish_year;
        }

        public VideoExtra setRating(float f) {
            this.rating = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getRating() {
            return this.rating;
        }

        public VideoExtra setThirdCp(ThirdPartyContentProvider thirdPartyContentProvider) {
            this.third_cp = Optional.ofNullable(thirdPartyContentProvider);
            return this;
        }

        public Optional<ThirdPartyContentProvider> getThirdCp() {
            return this.third_cp;
        }

        public VideoExtra setLanguage(String str) {
            this.language = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLanguage() {
            return this.language;
        }

        public VideoExtra setFree(boolean z) {
            this.free = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFree() {
            return this.free;
        }

        public VideoExtra setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public VideoExtra setLog(ObjectNode objectNode) {
            this.log = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLog() {
            return this.log;
        }

        public VideoExtra setDisplayRating(DisplayRating displayRating) {
            this.display_rating = Optional.ofNullable(displayRating);
            return this;
        }

        public Optional<DisplayRating> getDisplayRating() {
            return this.display_rating;
        }

        public VideoExtra setDisplayCornerImage(DisplayCornerImage displayCornerImage) {
            this.display_corner_image = Optional.ofNullable(displayCornerImage);
            return this;
        }

        public Optional<DisplayCornerImage> getDisplayCornerImage() {
            return this.display_corner_image;
        }
    }

    @NamespaceName(name = "VideoRecgV0", namespace = AIApiConstants.Video.NAME)
    /* loaded from: classes3.dex */
    public static class VideoRecgV0 implements InstructionPayload {
        @Required
        private String operation;
        @Required
        private String target;
        private Optional<String> direction = Optional.empty();
        private Optional<String> artist = Optional.empty();
        private Optional<String> gender = Optional.empty();

        public VideoRecgV0() {
        }

        public VideoRecgV0(String str, String str2) {
            this.operation = str;
            this.target = str2;
        }

        @Required
        public VideoRecgV0 setOperation(String str) {
            this.operation = str;
            return this;
        }

        @Required
        public String getOperation() {
            return this.operation;
        }

        @Required
        public VideoRecgV0 setTarget(String str) {
            this.target = str;
            return this;
        }

        @Required
        public String getTarget() {
            return this.target;
        }

        public VideoRecgV0 setDirection(String str) {
            this.direction = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDirection() {
            return this.direction;
        }

        public VideoRecgV0 setArtist(String str) {
            this.artist = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getArtist() {
            return this.artist;
        }

        public VideoRecgV0 setGender(String str) {
            this.gender = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGender() {
            return this.gender;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoRecogIntention {
        @Required
        private VideoRecogAction action;
        @Required
        private VideoRecogTarget target;
        private Optional<VideoRecogActionType> type = Optional.empty();
        private Optional<VideoRecogDirection> direction = Optional.empty();
        private Optional<Common.GenderType> gender = Optional.empty();
        private Optional<String> artist = Optional.empty();

        public VideoRecogIntention() {
        }

        public VideoRecogIntention(VideoRecogAction videoRecogAction, VideoRecogTarget videoRecogTarget) {
            this.action = videoRecogAction;
            this.target = videoRecogTarget;
        }

        @Required
        public VideoRecogIntention setAction(VideoRecogAction videoRecogAction) {
            this.action = videoRecogAction;
            return this;
        }

        @Required
        public VideoRecogAction getAction() {
            return this.action;
        }

        @Required
        public VideoRecogIntention setTarget(VideoRecogTarget videoRecogTarget) {
            this.target = videoRecogTarget;
            return this;
        }

        @Required
        public VideoRecogTarget getTarget() {
            return this.target;
        }

        public VideoRecogIntention setType(VideoRecogActionType videoRecogActionType) {
            this.type = Optional.ofNullable(videoRecogActionType);
            return this;
        }

        public Optional<VideoRecogActionType> getType() {
            return this.type;
        }

        public VideoRecogIntention setDirection(VideoRecogDirection videoRecogDirection) {
            this.direction = Optional.ofNullable(videoRecogDirection);
            return this;
        }

        public Optional<VideoRecogDirection> getDirection() {
            return this.direction;
        }

        public VideoRecogIntention setGender(Common.GenderType genderType) {
            this.gender = Optional.ofNullable(genderType);
            return this;
        }

        public Optional<Common.GenderType> getGender() {
            return this.gender;
        }

        public VideoRecogIntention setArtist(String str) {
            this.artist = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getArtist() {
            return this.artist;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSearchCursor {
        private Optional<String> url = Optional.empty();
        private Optional<String> intent = Optional.empty();

        public VideoSearchCursor setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public VideoSearchCursor setIntent(String str) {
            this.intent = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIntent() {
            return this.intent;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSearchItem {
        @Required
        private String id;
        @Required
        private String name;
        @Required
        private Template.Image poster;
        private Optional<VideoExtra> extra = Optional.empty();
        private Optional<String> meta_id = Optional.empty();

        public VideoSearchItem() {
        }

        public VideoSearchItem(String str, String str2, Template.Image image) {
            this.name = str;
            this.id = str2;
            this.poster = image;
        }

        @Required
        public VideoSearchItem setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public VideoSearchItem setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public VideoSearchItem setPoster(Template.Image image) {
            this.poster = image;
            return this;
        }

        @Required
        public Template.Image getPoster() {
            return this.poster;
        }

        public VideoSearchItem setExtra(VideoExtra videoExtra) {
            this.extra = Optional.ofNullable(videoExtra);
            return this;
        }

        public Optional<VideoExtra> getExtra() {
            return this.extra;
        }

        public VideoSearchItem setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSearchItemWithTag {
        @Required
        private VideoSearchItem item;
        @Required
        private List<VideoSearchTags> tags;

        public VideoSearchItemWithTag() {
        }

        public VideoSearchItemWithTag(VideoSearchItem videoSearchItem, List<VideoSearchTags> list) {
            this.item = videoSearchItem;
            this.tags = list;
        }

        @Required
        public VideoSearchItemWithTag setItem(VideoSearchItem videoSearchItem) {
            this.item = videoSearchItem;
            return this;
        }

        @Required
        public VideoSearchItem getItem() {
            return this.item;
        }

        @Required
        public VideoSearchItemWithTag setTags(List<VideoSearchTags> list) {
            this.tags = list;
            return this;
        }

        @Required
        public List<VideoSearchTags> getTags() {
            return this.tags;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSearchSlot {
        @Required
        private String attribute;
        @Required
        private String value;

        public VideoSearchSlot() {
        }

        public VideoSearchSlot(String str, String str2) {
            this.attribute = str;
            this.value = str2;
        }

        @Required
        public VideoSearchSlot setAttribute(String str) {
            this.attribute = str;
            return this;
        }

        @Required
        public String getAttribute() {
            return this.attribute;
        }

        @Required
        public VideoSearchSlot setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSearchTags {
        @Required
        private VideoSearchSlot slot;
        @Required
        private String text;

        public VideoSearchTags() {
        }

        public VideoSearchTags(String str, VideoSearchSlot videoSearchSlot) {
            this.text = str;
            this.slot = videoSearchSlot;
        }

        @Required
        public VideoSearchTags setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public VideoSearchTags setSlot(VideoSearchSlot videoSearchSlot) {
            this.slot = videoSearchSlot;
            return this;
        }

        @Required
        public VideoSearchSlot getSlot() {
            return this.slot;
        }
    }
}
