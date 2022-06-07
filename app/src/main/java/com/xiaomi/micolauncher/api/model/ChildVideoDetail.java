package com.xiaomi.micolauncher.api.model;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoDetail {
    private List<BlocksBeanX> blocks;
    private String encoding;
    private String language;
    private MediaBean media;
    private StatBean stat;
    private int status;
    private UiTypeBean ui_type;
    private int ver;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public UiTypeBean getUi_type() {
        return this.ui_type;
    }

    public void setUi_type(UiTypeBean uiTypeBean) {
        this.ui_type = uiTypeBean;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int i) {
        this.ver = i;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }

    public MediaBean getMedia() {
        return this.media;
    }

    public void setMedia(MediaBean mediaBean) {
        this.media = mediaBean;
    }

    public StatBean getStat() {
        return this.stat;
    }

    public void setStat(StatBean statBean) {
        this.stat = statBean;
    }

    public List<BlocksBeanX> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<BlocksBeanX> list) {
        this.blocks = list;
    }

    /* loaded from: classes3.dex */
    public static class UiTypeBean {
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class MediaBean {
        private List<?> actorlist;
        private String audience_ages;
        private List<?> awards;
        private String bgmd5;
        private String bgurl;
        private List<CastInfoBean> cast_info;
        private String category;
        private List<Integer> cplist;
        private List<CpmidBean> cpmid;
        private String desc;
        private List<?> directorlist;
        private int display_style;
        private double douban_rating;
        private String edu_goals;
        private String focus;
        private List<String> genres;
        private boolean has_copy_right;
        private HighlightedPhotosBean highlighted_photos;
        private String language;
        private LogoIconBean logo_icon;
        private int maxresolution;
        private String md5;
        private List<MediaciinfoBean> mediaciinfo;
        private int mediaid;
        private String medianame;
        private int midtype;
        private NameindexBean nameindex;
        private int onlinestatus;
        private List<?> photos;
        private int playtime;
        private String posterurl;
        private String premiere_date;
        private List<?> producers;
        private List<ProductBean> product;
        private RatingInfoBean rating_info;
        private List<String> regions;
        private int resolution;
        private int setcount;
        private int setnow;
        private int show_logo;
        private List<Integer> sub_sources;
        private String title_icon_label;
        private int title_icon_type;
        private TrailerBean trailer;
        private int video_play_type;
        private VipIconBean vip_icon;
        private VipIconBigBean vip_icon_big;
        private double xiaomi_rating;

        /* loaded from: classes3.dex */
        public static class NameindexBean {
        }

        /* loaded from: classes3.dex */
        public static class TrailerBean {
        }

        /* loaded from: classes3.dex */
        public static class VipIconBean {
        }

        /* loaded from: classes3.dex */
        public static class VipIconBigBean {
        }

        public String getEdu_goals() {
            return this.edu_goals;
        }

        public void setEdu_goals(String str) {
            this.edu_goals = str;
        }

        public int getMidtype() {
            return this.midtype;
        }

        public void setMidtype(int i) {
            this.midtype = i;
        }

        public String getPosterurl() {
            return this.posterurl;
        }

        public void setPosterurl(String str) {
            this.posterurl = str;
        }

        public String getFocus() {
            return this.focus;
        }

        public void setFocus(String str) {
            this.focus = str;
        }

        public int getPlaytime() {
            return this.playtime;
        }

        public void setPlaytime(int i) {
            this.playtime = i;
        }

        public String getAudience_ages() {
            return this.audience_ages;
        }

        public void setAudience_ages(String str) {
            this.audience_ages = str;
        }

        public LogoIconBean getLogo_icon() {
            return this.logo_icon;
        }

        public void setLogo_icon(LogoIconBean logoIconBean) {
            this.logo_icon = logoIconBean;
        }

        public boolean isHas_copy_right() {
            return this.has_copy_right;
        }

        public void setHas_copy_right(boolean z) {
            this.has_copy_right = z;
        }

        public String getBgmd5() {
            return this.bgmd5;
        }

        public void setBgmd5(String str) {
            this.bgmd5 = str;
        }

        public int getTitle_icon_type() {
            return this.title_icon_type;
        }

        public void setTitle_icon_type(int i) {
            this.title_icon_type = i;
        }

        public HighlightedPhotosBean getHighlighted_photos() {
            return this.highlighted_photos;
        }

        public void setHighlighted_photos(HighlightedPhotosBean highlightedPhotosBean) {
            this.highlighted_photos = highlightedPhotosBean;
        }

        public String getBgurl() {
            return this.bgurl;
        }

        public void setBgurl(String str) {
            this.bgurl = str;
        }

        public String getPremiere_date() {
            return this.premiere_date;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String str) {
            this.language = str;
        }

        public int getOnlinestatus() {
            return this.onlinestatus;
        }

        public void setOnlinestatus(int i) {
            this.onlinestatus = i;
        }

        public int getSetcount() {
            return this.setcount;
        }

        public void setSetcount(int i) {
            this.setcount = i;
        }

        public RatingInfoBean getRating_info() {
            return this.rating_info;
        }

        public void setRating_info(RatingInfoBean ratingInfoBean) {
            this.rating_info = ratingInfoBean;
        }

        public int getShow_logo() {
            return this.show_logo;
        }

        public void setShow_logo(int i) {
            this.show_logo = i;
        }

        public int getDisplay_style() {
            return this.display_style;
        }

        public void setDisplay_style(int i) {
            this.display_style = i;
        }

        public String getTitle_icon_label() {
            return this.title_icon_label;
        }

        public void setTitle_icon_label(String str) {
            this.title_icon_label = str;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String str) {
            this.category = str;
        }

        public VipIconBigBean getVip_icon_big() {
            return this.vip_icon_big;
        }

        public void setVip_icon_big(VipIconBigBean vipIconBigBean) {
            this.vip_icon_big = vipIconBigBean;
        }

        public double getXiaomi_rating() {
            return this.xiaomi_rating;
        }

        public void setXiaomi_rating(double d) {
            this.xiaomi_rating = d;
        }

        public NameindexBean getNameindex() {
            return this.nameindex;
        }

        public void setNameindex(NameindexBean nameindexBean) {
            this.nameindex = nameindexBean;
        }

        public int getSetnow() {
            return this.setnow;
        }

        public void setSetnow(int i) {
            this.setnow = i;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String str) {
            this.md5 = str;
        }

        public int getMediaid() {
            return this.mediaid;
        }

        public void setMediaid(int i) {
            this.mediaid = i;
        }

        public int getVideo_play_type() {
            return this.video_play_type;
        }

        public void setVideo_play_type(int i) {
            this.video_play_type = i;
        }

        public String getMedianame() {
            return this.medianame;
        }

        public void setMedianame(String str) {
            this.medianame = str;
        }

        public VipIconBean getVip_icon() {
            return this.vip_icon;
        }

        public void setVip_icon(VipIconBean vipIconBean) {
            this.vip_icon = vipIconBean;
        }

        public TrailerBean getTrailer() {
            return this.trailer;
        }

        public void setTrailer(TrailerBean trailerBean) {
            this.trailer = trailerBean;
        }

        public double getDouban_rating() {
            return this.douban_rating;
        }

        public void setDouban_rating(double d) {
            this.douban_rating = d;
        }

        public int getMaxresolution() {
            return this.maxresolution;
        }

        public void setMaxresolution(int i) {
            this.maxresolution = i;
        }

        public int getResolution() {
            return this.resolution;
        }

        public void setResolution(int i) {
            this.resolution = i;
        }

        public List<String> getRegions() {
            return this.regions;
        }

        public void setRegions(List<String> list) {
            this.regions = list;
        }

        public List<Integer> getCplist() {
            return this.cplist;
        }

        public void setCplist(List<Integer> list) {
            this.cplist = list;
        }

        public List<?> getPhotos() {
            return this.photos;
        }

        public void setPhotos(List<?> list) {
            this.photos = list;
        }

        public List<MediaciinfoBean> getMediaciinfo() {
            return this.mediaciinfo;
        }

        public void setMediaciinfo(List<MediaciinfoBean> list) {
            this.mediaciinfo = list;
        }

        public List<?> getDirectorlist() {
            return this.directorlist;
        }

        public void setDirectorlist(List<?> list) {
            this.directorlist = list;
        }

        public List<String> getGenres() {
            return this.genres;
        }

        public void setGenres(List<String> list) {
            this.genres = list;
        }

        public List<CastInfoBean> getCast_info() {
            return this.cast_info;
        }

        public void setCast_info(List<CastInfoBean> list) {
            this.cast_info = list;
        }

        public List<CpmidBean> getCpmid() {
            return this.cpmid;
        }

        public void setCpmid(List<CpmidBean> list) {
            this.cpmid = list;
        }

        public List<ProductBean> getProduct() {
            return this.product;
        }

        public void setProduct(List<ProductBean> list) {
            this.product = list;
        }

        public List<Integer> getSub_sources() {
            return this.sub_sources;
        }

        public void setSub_sources(List<Integer> list) {
            this.sub_sources = list;
        }

        public List<?> getProducers() {
            return this.producers;
        }

        public void setProducers(List<?> list) {
            this.producers = list;
        }

        public List<?> getAwards() {
            return this.awards;
        }

        public void setAwards(List<?> list) {
            this.awards = list;
        }

        public List<?> getActorlist() {
            return this.actorlist;
        }

        public void setActorlist(List<?> list) {
            this.actorlist = list;
        }

        /* loaded from: classes3.dex */
        public static class LogoIconBean {
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
        public static class HighlightedPhotosBean {
            private List<?> data;
            private String url;

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String str) {
                this.url = str;
            }

            public List<?> getData() {
                return this.data;
            }

            public void setData(List<?> list) {
                this.data = list;
            }
        }

        /* loaded from: classes3.dex */
        public static class RatingInfoBean {
            private String display_name;
            private double value;

            public String getDisplay_name() {
                return this.display_name;
            }

            public void setDisplay_name(String str) {
                this.display_name = str;
            }

            public double getValue() {
                return this.value;
            }

            public void setValue(double d) {
                this.value = d;
            }
        }

        /* loaded from: classes3.dex */
        public static class MediaciinfoBean {
            private List<Integer> cplist;
            private int orderby;
            private int style;
            private List<VideosBean> videos;

            public int getOrderby() {
                return this.orderby;
            }

            public void setOrderby(int i) {
                this.orderby = i;
            }

            public int getStyle() {
                return this.style;
            }

            public void setStyle(int i) {
                this.style = i;
            }

            public List<VideosBean> getVideos() {
                return this.videos;
            }

            public void setVideos(List<VideosBean> list) {
                this.videos = list;
            }

            public List<Integer> getCplist() {
                return this.cplist;
            }

            public void setCplist(List<Integer> list) {
                this.cplist = list;
            }

            /* loaded from: classes3.dex */
            public static class VideosBean {
                private int android_version_code;
                private int ci;
                private int content_type;
                private String date;
                private int episode_id;
                private int fee;
                private List<?> guest;
                private String hotspot;
                private int mediaid;
                private int pay_type;
                private int source;
                private int trial_length;
                private int trial_status;
                private String videoname;

                public String getVideoname() {
                    return this.videoname;
                }

                public void setVideoname(String str) {
                    this.videoname = str;
                }

                public int getCi() {
                    return this.ci;
                }

                public void setCi(int i) {
                    this.ci = i;
                }

                public int getAndroid_version_code() {
                    return this.android_version_code;
                }

                public void setAndroid_version_code(int i) {
                    this.android_version_code = i;
                }

                public int getEpisode_id() {
                    return this.episode_id;
                }

                public void setEpisode_id(int i) {
                    this.episode_id = i;
                }

                public int getContent_type() {
                    return this.content_type;
                }

                public void setContent_type(int i) {
                    this.content_type = i;
                }

                public int getTrial_length() {
                    return this.trial_length;
                }

                public void setTrial_length(int i) {
                    this.trial_length = i;
                }

                public String getDate() {
                    return this.date;
                }

                public void setDate(String str) {
                    this.date = str;
                }

                public int getPay_type() {
                    return this.pay_type;
                }

                public void setPay_type(int i) {
                    this.pay_type = i;
                }

                public int getFee() {
                    return this.fee;
                }

                public void setFee(int i) {
                    this.fee = i;
                }

                public int getMediaid() {
                    return this.mediaid;
                }

                public void setMediaid(int i) {
                    this.mediaid = i;
                }

                public int getTrial_status() {
                    return this.trial_status;
                }

                public void setTrial_status(int i) {
                    this.trial_status = i;
                }

                public String getHotspot() {
                    return this.hotspot;
                }

                public void setHotspot(String str) {
                    this.hotspot = str;
                }

                public int getSource() {
                    return this.source;
                }

                public void setSource(int i) {
                    this.source = i;
                }

                public List<?> getGuest() {
                    return this.guest;
                }

                public void setGuest(List<?> list) {
                    this.guest = list;
                }
            }
        }

        /* loaded from: classes3.dex */
        public static class CastInfoBean {
            private String display_name;
            private List<?> value;

            public String getDisplay_name() {
                return this.display_name;
            }

            public void setDisplay_name(String str) {
                this.display_name = str;
            }

            public List<?> getValue() {
                return this.value;
            }

            public void setValue(List<?> list) {
                this.value = list;
            }
        }

        /* loaded from: classes3.dex */
        public static class CpmidBean {
            private String mid;
            private int source;

            public int getSource() {
                return this.source;
            }

            public void setSource(int i) {
                this.source = i;
            }

            public String getMid() {
                return this.mid;
            }

            public void setMid(String str) {
                this.mid = str;
            }
        }

        /* loaded from: classes3.dex */
        public static class ProductBean {
            private BgImageBean bg_image;
            private int bssid;
            private String channel_id;
            private String channel_title;
            private int cpid;
            private String display_name;
            private int id;
            private int login;
            private String name;
            private List<PosterBean> poster;
            private int renewable;
            private int status;
            private int type;

            public int getStatus() {
                return this.status;
            }

            public void setStatus(int i) {
                this.status = i;
            }

            public String getChannel_title() {
                return this.channel_title;
            }

            public void setChannel_title(String str) {
                this.channel_title = str;
            }

            public int getBssid() {
                return this.bssid;
            }

            public void setBssid(int i) {
                this.bssid = i;
            }

            public BgImageBean getBg_image() {
                return this.bg_image;
            }

            public void setBg_image(BgImageBean bgImageBean) {
                this.bg_image = bgImageBean;
            }

            public int getCpid() {
                return this.cpid;
            }

            public void setCpid(int i) {
                this.cpid = i;
            }

            public int getId() {
                return this.id;
            }

            public void setId(int i) {
                this.id = i;
            }

            public int getRenewable() {
                return this.renewable;
            }

            public void setRenewable(int i) {
                this.renewable = i;
            }

            public String getDisplay_name() {
                return this.display_name;
            }

            public void setDisplay_name(String str) {
                this.display_name = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getChannel_id() {
                return this.channel_id;
            }

            public void setChannel_id(String str) {
                this.channel_id = str;
            }

            public int getLogin() {
                return this.login;
            }

            public void setLogin(int i) {
                this.login = i;
            }

            public int getType() {
                return this.type;
            }

            public void setType(int i) {
                this.type = i;
            }

            public List<PosterBean> getPoster() {
                return this.poster;
            }

            public void setPoster(List<PosterBean> list) {
                this.poster = list;
            }

            /* loaded from: classes3.dex */
            public static class BgImageBean {
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
            public static class PosterBean {
                private String md5;
                private int type;
                private String url;

                public String getUrl() {
                    return this.url;
                }

                public void setUrl(String str) {
                    this.url = str;
                }

                public int getType() {
                    return this.type;
                }

                public void setType(int i) {
                    this.type = i;
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

    /* loaded from: classes3.dex */
    public static class StatBean {
        private String path;
        private String tp;

        public String getPath() {
            return this.path;
        }

        public void setPath(String str) {
            this.path = str;
        }

        public String getTp() {
            return this.tp;
        }

        public void setTp(String str) {
            this.tp = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class BlocksBeanX {
        private List<ChildVideo.BlocksBean> blocks;
        private UiTypeBeanX ui_type;

        public UiTypeBeanX getUi_type() {
            return this.ui_type;
        }

        public void setUi_type(UiTypeBeanX uiTypeBeanX) {
            this.ui_type = uiTypeBeanX;
        }

        public List<ChildVideo.BlocksBean> getBlocks() {
            return this.blocks;
        }

        public void setBlocks(List<ChildVideo.BlocksBean> list) {
            this.blocks = list;
        }

        /* loaded from: classes3.dex */
        public static class UiTypeBeanX {
            private String name;

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }
        }
    }
}
