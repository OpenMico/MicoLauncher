package com.xiaomi.micolauncher.api.model;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class MainPage {

    /* loaded from: classes3.dex */
    public static class KidMenuItem {
        public String actionUrl;
        public String iconUrl;
        public int menuCode;
        public boolean needNetwork;
        public String statName;
    }

    /* loaded from: classes3.dex */
    public interface MainPageVideo {
        String coverImageUrl();

        boolean isShortVideo();

        VideoItem transform();

        String videoCategory();

        String videoTitle();
    }

    /* loaded from: classes3.dex */
    public class News {
        private String action;
        private BackgroundImageBean backgroundImage;
        private ExtendBean extend;
        private int interval;
        private String pageId;
        private String screenCategory;
        private String status;
        private String subTitle1;
        private String subTitle2;
        private String tag;
        private List<String> tips;
        private String title;
        private String trackKey;
        private String type;

        public News() {
            MainPage.this = r1;
        }

        public String toString() {
            return "News{title='" + this.title + "'}";
        }

        public String getPageId() {
            return this.pageId;
        }

        public void setPageId(String str) {
            this.pageId = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getSubTitle1() {
            return this.subTitle1;
        }

        public void setSubTitle1(String str) {
            this.subTitle1 = str;
        }

        public String getSubTitle2() {
            return this.subTitle2;
        }

        public void setSubTitle2(String str) {
            this.subTitle2 = str;
        }

        public int getInterval() {
            return this.interval;
        }

        public void setInterval(int i) {
            this.interval = i;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String str) {
            this.action = str;
        }

        public String getTrackKey() {
            return this.trackKey;
        }

        public void setTrackKey(String str) {
            this.trackKey = str;
        }

        public ExtendBean getExtend() {
            return this.extend;
        }

        public void setExtend(ExtendBean extendBean) {
            this.extend = extendBean;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String str) {
            this.tag = str;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public String getScreenCategory() {
            return this.screenCategory;
        }

        public void setScreenCategory(String str) {
            this.screenCategory = str;
        }

        public List<String> getTips() {
            return this.tips;
        }

        public void setTips(List<String> list) {
            this.tips = list;
        }

        public BackgroundImageBean getBackgroundImage() {
            return this.backgroundImage;
        }

        public void setBackgroundImage(BackgroundImageBean backgroundImageBean) {
            this.backgroundImage = backgroundImageBean;
        }

        /* loaded from: classes3.dex */
        public class ExtendBean {
            public static final String TYPE_SOUND = "sound";
            public static final String TYPE_VIDEO = "video";
            private String auth;
            private String cp;
            private String newsId;
            private String origin;
            private String playUrl;
            private String url;

            public ExtendBean() {
                News.this = r1;
            }

            public String getPlayUrl() {
                return this.playUrl;
            }

            public void setPlayUrl(String str) {
                this.playUrl = str;
            }

            public String getOrigin() {
                return this.origin;
            }

            public void setOrigin(String str) {
                this.origin = str;
            }

            public String getCp() {
                return this.cp;
            }

            public void setCp(String str) {
                this.cp = str;
            }

            public String getNewsId() {
                return this.newsId;
            }

            public void setNewsId(String str) {
                this.newsId = str;
            }

            public void setUrl(String str) {
                this.url = str;
            }

            public String getUrl() {
                return this.url;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ExtendBean extendBean = (ExtendBean) obj;
                return Objects.equals(this.playUrl, extendBean.playUrl) && Objects.equals(this.origin, extendBean.origin) && Objects.equals(this.cp, extendBean.cp) && Objects.equals(this.newsId, extendBean.newsId);
            }

            public int hashCode() {
                return Objects.hash(this.playUrl, this.origin, this.cp, this.newsId);
            }
        }

        /* loaded from: classes3.dex */
        public class BackgroundImageBean {
            private String large;
            private String middle;
            private String small;

            public BackgroundImageBean() {
                News.this = r1;
            }

            public String getLarge() {
                return this.large;
            }

            public void setLarge(String str) {
                this.large = str;
            }

            public String getMiddle() {
                return this.middle;
            }

            public void setMiddle(String str) {
                this.middle = str;
            }

            public String getSmall() {
                return this.small;
            }

            public void setSmall(String str) {
                this.small = str;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                BackgroundImageBean backgroundImageBean = (BackgroundImageBean) obj;
                return Objects.equals(this.large, backgroundImageBean.large) && Objects.equals(this.middle, backgroundImageBean.middle) && Objects.equals(this.small, backgroundImageBean.small);
            }

            public int hashCode() {
                return Objects.hash(this.large, this.middle, this.small);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            News news = (News) obj;
            return this.interval == news.interval && Objects.equals(this.pageId, news.pageId) && Objects.equals(this.type, news.type) && Objects.equals(this.title, news.title) && Objects.equals(this.subTitle1, news.subTitle1) && Objects.equals(this.subTitle2, news.subTitle2) && Objects.equals(this.action, news.action) && Objects.equals(this.trackKey, news.trackKey) && Objects.equals(this.extend, news.extend) && Objects.equals(this.tag, news.tag) && Objects.equals(this.status, news.status) && Objects.equals(this.screenCategory, news.screenCategory) && Objects.equals(this.tips, news.tips) && Objects.equals(this.backgroundImage, news.backgroundImage);
        }

        public int hashCode() {
            return Objects.hash(this.pageId, this.type, this.title, this.subTitle1, this.subTitle2, Integer.valueOf(this.interval), this.action, this.trackKey, this.extend, this.tag, this.status, this.screenCategory, this.tips, this.backgroundImage);
        }
    }

    /* loaded from: classes3.dex */
    public class Music implements FadeInCardView.FadeInCardItem {
        private String action;
        private BackgroundImageBean backgroundImage;
        private ExtendBean extend;
        private int interval;
        private String pageId;
        private String screenCategory;
        private String status;
        private String subTitle1;
        private String subTitle2;
        private String tag;
        private List<String> tips;
        private String title;
        private String trackKey;
        private String type;

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public int getBgResId() {
            return R.drawable.icon_main_music;
        }

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public int getTypeResId() {
            return R.drawable.icon_main_music;
        }

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public void handleItemClick(Context context) {
        }

        public Music() {
            MainPage.this = r1;
        }

        public String getPageId() {
            return this.pageId;
        }

        public void setPageId(String str) {
            this.pageId = str;
        }

        public String getType() {
            return this.type;
        }

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public String getCardAction() {
            return getAction();
        }

        public void setType(String str) {
            this.type = str;
        }

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public String getImgUrl() {
            return getBackgroundImage().getSmall();
        }

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public String getTitle() {
            return this.title;
        }

        @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
        public String getCardType() {
            return getTag();
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getSubTitle1() {
            return this.subTitle1;
        }

        public void setSubTitle1(String str) {
            this.subTitle1 = str;
        }

        public String getSubTitle2() {
            return this.subTitle2;
        }

        public void setSubTitle2(String str) {
            this.subTitle2 = str;
        }

        public BackgroundImageBean getBackgroundImage() {
            return this.backgroundImage;
        }

        public void setBackgroundImage(BackgroundImageBean backgroundImageBean) {
            this.backgroundImage = backgroundImageBean;
        }

        public int getInterval() {
            return this.interval;
        }

        public void setInterval(int i) {
            this.interval = i;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String str) {
            this.action = str;
        }

        public String getTrackKey() {
            return this.trackKey;
        }

        public void setTrackKey(String str) {
            this.trackKey = str;
        }

        public ExtendBean getExtend() {
            return this.extend;
        }

        public void setExtend(ExtendBean extendBean) {
            this.extend = extendBean;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String str) {
            this.tag = str;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public String getScreenCategory() {
            return this.screenCategory;
        }

        public void setScreenCategory(String str) {
            this.screenCategory = str;
        }

        public List<String> getTips() {
            return this.tips;
        }

        public void setTips(List<String> list) {
            this.tips = list;
        }

        /* loaded from: classes3.dex */
        public class BackgroundImageBean {
            private String large;
            private String middle;
            private String small;

            public BackgroundImageBean() {
                Music.this = r1;
            }

            public String getLarge() {
                return this.large;
            }

            public void setLarge(String str) {
                this.large = str;
            }

            public String getMiddle() {
                return this.middle;
            }

            public void setMiddle(String str) {
                this.middle = str;
            }

            public String getSmall() {
                return this.small;
            }

            public void setSmall(String str) {
                this.small = str;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                BackgroundImageBean backgroundImageBean = (BackgroundImageBean) obj;
                return Objects.equals(this.large, backgroundImageBean.large) && Objects.equals(this.middle, backgroundImageBean.middle) && Objects.equals(this.small, backgroundImageBean.small);
            }

            public int hashCode() {
                return Objects.hash(this.large, this.middle, this.small);
            }
        }

        /* loaded from: classes3.dex */
        public class ExtendBean {
            private String subType;

            public ExtendBean() {
                Music.this = r1;
            }

            public String getSubType() {
                return this.subType;
            }

            public void setSubType(String str) {
                this.subType = str;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                return Objects.equals(this.subType, ((ExtendBean) obj).subType);
            }

            public int hashCode() {
                return Objects.hash(this.subType);
            }
        }
    }

    /* loaded from: classes3.dex */
    public class Station {
        private String action;
        private BackgroundImageBean backgroundImage;
        private Object extend;
        private int interval;
        private String pageId;
        private String screenCategory;
        private String status;
        private String subTitle1;
        private String subTitle2;
        private String tag;
        private Object tips;
        private String title;
        private String trackKey;
        private String type;

        public Station() {
            MainPage.this = r1;
        }

        public String getPageId() {
            return this.pageId;
        }

        public void setPageId(String str) {
            this.pageId = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getSubTitle1() {
            return this.subTitle1;
        }

        public void setSubTitle1(String str) {
            this.subTitle1 = str;
        }

        public String getSubTitle2() {
            return this.subTitle2;
        }

        public void setSubTitle2(String str) {
            this.subTitle2 = str;
        }

        public BackgroundImageBean getBackgroundImage() {
            return this.backgroundImage;
        }

        public void setBackgroundImage(BackgroundImageBean backgroundImageBean) {
            this.backgroundImage = backgroundImageBean;
        }

        public Object getTips() {
            return this.tips;
        }

        public void setTips(Object obj) {
            this.tips = obj;
        }

        public int getInterval() {
            return this.interval;
        }

        public void setInterval(int i) {
            this.interval = i;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String str) {
            this.action = str;
        }

        public String getTrackKey() {
            return this.trackKey;
        }

        public void setTrackKey(String str) {
            this.trackKey = str;
        }

        public Object getExtend() {
            return this.extend;
        }

        public void setExtend(Object obj) {
            this.extend = obj;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String str) {
            this.tag = str;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public String getScreenCategory() {
            return this.screenCategory;
        }

        public void setScreenCategory(String str) {
            this.screenCategory = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Station station = (Station) obj;
            return this.interval == station.interval && Objects.equals(this.pageId, station.pageId) && Objects.equals(this.type, station.type) && Objects.equals(this.title, station.title) && Objects.equals(this.subTitle1, station.subTitle1) && Objects.equals(this.subTitle2, station.subTitle2) && Objects.equals(this.backgroundImage, station.backgroundImage) && Objects.equals(this.tips, station.tips) && Objects.equals(this.action, station.action) && Objects.equals(this.trackKey, station.trackKey) && Objects.equals(this.extend, station.extend) && Objects.equals(this.tag, station.tag) && Objects.equals(this.status, station.status) && Objects.equals(this.screenCategory, station.screenCategory);
        }

        public int hashCode() {
            return Objects.hash(this.pageId, this.type, this.title, this.subTitle1, this.subTitle2, this.backgroundImage, this.tips, Integer.valueOf(this.interval), this.action, this.trackKey, this.extend, this.tag, this.status, this.screenCategory);
        }

        /* loaded from: classes3.dex */
        public class BackgroundImageBean {
            private String large;
            private String middle;
            private String small;

            public BackgroundImageBean() {
                Station.this = r1;
            }

            public String getLarge() {
                return this.large;
            }

            public void setLarge(String str) {
                this.large = str;
            }

            public String getMiddle() {
                return this.middle;
            }

            public void setMiddle(String str) {
                this.middle = str;
            }

            public String getSmall() {
                return this.small;
            }

            public void setSmall(String str) {
                this.small = str;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                BackgroundImageBean backgroundImageBean = (BackgroundImageBean) obj;
                return Objects.equals(this.large, backgroundImageBean.large) && Objects.equals(this.middle, backgroundImageBean.middle) && Objects.equals(this.small, backgroundImageBean.small);
            }

            public int hashCode() {
                return Objects.hash(this.large, this.middle, this.small);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Video implements MainPageVideo {
        private String actors;
        private String cateory;
        private String cp;
        private String desc;
        private String directors;
        private int episode_count;
        private Map<String, String> episodes_id;
        private int episods;
        private String iqiyi_id;
        private boolean is_vip;
        private String last_episod;
        private String medianame;
        private MitvBean mitv;
        private int play_length;
        private String poster;
        private double rating;
        private int show_status;
        private String type;
        private int year;

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public boolean isShortVideo() {
            return false;
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public String videoTitle() {
            return null;
        }

        public String getDirectors() {
            return this.directors;
        }

        public void setDirectors(String str) {
            this.directors = str;
        }

        public String getActors() {
            return this.actors;
        }

        public void setActors(String str) {
            this.actors = str;
        }

        public int getEpisode_count() {
            return this.episode_count;
        }

        public void setEpisode_count(int i) {
            this.episode_count = i;
        }

        public int getYear() {
            return this.year;
        }

        public void setYear(int i) {
            this.year = i;
        }

        public String getCateory() {
            return this.cateory;
        }

        public void setCateory(String str) {
            this.cateory = str;
        }

        public boolean isIs_vip() {
            return this.is_vip;
        }

        public void setIs_vip(boolean z) {
            this.is_vip = z;
        }

        public double getRating() {
            return this.rating;
        }

        public void setRating(double d) {
            this.rating = d;
        }

        public String getLast_episod() {
            return this.last_episod;
        }

        public void setLast_episod(String str) {
            this.last_episod = str;
        }

        public String getIqiyi_id() {
            return this.iqiyi_id;
        }

        public String getCp() {
            if (TextUtils.isEmpty(this.cp) && !TextUtils.isEmpty(this.iqiyi_id)) {
                this.cp = "iqiyi";
            }
            return this.cp;
        }

        public void setIqiyi_id(String str) {
            this.iqiyi_id = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public MitvBean getMitv() {
            return this.mitv;
        }

        public void setMitv(MitvBean mitvBean) {
            this.mitv = mitvBean;
        }

        public String getMedianame() {
            return this.medianame;
        }

        public void setMedianame(String str) {
            this.medianame = str;
        }

        public Map<String, String> getEpisodes_id() {
            return this.episodes_id;
        }

        public void setEpisodes_id(Map<String, String> map) {
            this.episodes_id = map;
        }

        public int getPlay_length() {
            return this.play_length;
        }

        public void setPlay_length(int i) {
            this.play_length = i;
        }

        public int getEpisods() {
            return this.episods;
        }

        public void setEpisods(int i) {
            this.episods = i;
        }

        public int getShow_status() {
            return this.show_status;
        }

        public void setShow_status(int i) {
            this.show_status = i;
        }

        public String getPoster() {
            return this.poster;
        }

        public void setPoster(String str) {
            this.poster = str;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public String coverImageUrl() {
            MitvBean.ImagesBean images;
            MitvBean mitvBean = this.mitv;
            if (mitvBean == null || (images = mitvBean.getImages()) == null || images.getPoster() == null) {
                return null;
            }
            return images.getPoster().getUrl();
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public String videoCategory() {
            return this.cateory;
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public VideoItem transform() {
            return new VideoItem(this);
        }

        /* loaded from: classes3.dex */
        public static class MitvBean {
            private ImagesBean images;
            private int media_id;
            private String media_type;

            public ImagesBean getImages() {
                return this.images;
            }

            public void setImages(ImagesBean imagesBean) {
                this.images = imagesBean;
            }

            public String getMedia_type() {
                return this.media_type;
            }

            public void setMedia_type(String str) {
                this.media_type = str;
            }

            public int getMedia_id() {
                return this.media_id;
            }

            public void setMedia_id(int i) {
                this.media_id = i;
            }

            /* loaded from: classes3.dex */
            public static class ImagesBean {
                private ForeBean fore;
                private PosterBean poster;

                public PosterBean getPoster() {
                    return this.poster;
                }

                public void setPoster(PosterBean posterBean) {
                    this.poster = posterBean;
                }

                public ForeBean getFore() {
                    return this.fore;
                }

                public void setFore(ForeBean foreBean) {
                    this.fore = foreBean;
                }

                /* loaded from: classes3.dex */
                public static class PosterBean {
                    private String md5;
                    private String url;

                    public String getUrl() {
                        return this.url;
                    }

                    public void setUrl(String str) {
                        this.url = str;
                    }

                    public String getMd5() {
                        return this.md5;
                    }

                    public void setMd5(String str) {
                        this.md5 = str;
                    }
                }

                /* loaded from: classes3.dex */
                public static class ForeBean {
                    private String align;
                    private String md5;
                    private String url;

                    public String getUrl() {
                        return this.url;
                    }

                    public void setUrl(String str) {
                        this.url = str;
                    }

                    public String getAlign() {
                        return this.align;
                    }

                    public void setAlign(String str) {
                        this.align = str;
                    }

                    public String getMd5() {
                        return this.md5;
                    }

                    public void setMd5(String str) {
                        this.md5 = str;
                    }
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class LongVideo {
        private static final String BANNER_TYPE_AD = "AD";
        private static final String BANNER_TYPE_VIDEO = "VIDEO";
        private String actionURL;
        private String bannerType;
        private String cp;
        private String cpId;
        private String description;
        private int id;
        private String imageURL;
        private String title;
        private String trackKey;
        private String type;

        public LongVideo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
            this.title = str;
            this.cp = str2;
            this.cpId = str3;
            this.imageURL = str4;
            this.description = str5;
            this.actionURL = str6;
            this.bannerType = str7;
            this.trackKey = str8;
        }

        public String getBannerType() {
            return this.bannerType;
        }

        public void setBannerType(String str) {
            this.bannerType = str;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int i) {
            this.id = i;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getCp() {
            return this.cp;
        }

        public void setCp(String str) {
            this.cp = str;
        }

        public String getCpId() {
            return this.cpId;
        }

        public void setCpId(String str) {
            this.cpId = str;
        }

        public String getImageURL() {
            return this.imageURL;
        }

        public void setImageURL(String str) {
            this.imageURL = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public String getActionURL() {
            return this.actionURL;
        }

        public void setActionURL(String str) {
            this.actionURL = str;
        }

        public boolean isVideo() {
            return TextUtils.equals(this.bannerType, BANNER_TYPE_VIDEO);
        }

        public boolean isAd() {
            return TextUtils.equals(this.bannerType, BANNER_TYPE_AD);
        }

        public String getTrackKey() {
            return this.trackKey;
        }

        public void setTrackKey(String str) {
            this.trackKey = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            LongVideo longVideo = (LongVideo) obj;
            return this.id == longVideo.id && Objects.equals(this.title, longVideo.title) && Objects.equals(this.cp, longVideo.cp) && Objects.equals(this.cpId, longVideo.cpId) && Objects.equals(this.imageURL, longVideo.imageURL) && Objects.equals(this.type, longVideo.type) && Objects.equals(this.description, longVideo.description) && Objects.equals(this.actionURL, longVideo.actionURL) && Objects.equals(this.bannerType, longVideo.bannerType);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), this.title, this.cp, this.cpId, this.imageURL, this.type, this.description, this.actionURL, this.bannerType);
        }

        public String toString() {
            return "LongVideo{id=" + this.id + ", title='" + this.title + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public static class SkillApp {
        private String actionURL;
        private String description;
        private int id;
        private String imageURL;
        private String tipColor;
        private List<String> tips;
        private String title;

        public int getId() {
            return this.id;
        }

        public void setId(int i) {
            this.id = i;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public String getActionURL() {
            return this.actionURL;
        }

        public void setActionURL(String str) {
            this.actionURL = str;
        }

        public String getImageURL() {
            return this.imageURL;
        }

        public void setImageURL(String str) {
            this.imageURL = str;
        }

        public List<String> getTips() {
            return this.tips;
        }

        public void setTips(List<String> list) {
            this.tips = list;
        }

        public String getTipColor() {
            return this.tipColor;
        }

        public void setTipColor(String str) {
            this.tipColor = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SkillApp skillApp = (SkillApp) obj;
            return this.id == skillApp.id && Objects.equals(this.title, skillApp.title) && Objects.equals(this.description, skillApp.description) && Objects.equals(this.actionURL, skillApp.actionURL) && Objects.equals(this.imageURL, skillApp.imageURL) && Objects.equals(this.tips, skillApp.tips) && Objects.equals(this.tipColor, skillApp.tipColor);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), this.title, this.description, this.actionURL, this.imageURL, this.tips, this.tipColor);
        }
    }
}
