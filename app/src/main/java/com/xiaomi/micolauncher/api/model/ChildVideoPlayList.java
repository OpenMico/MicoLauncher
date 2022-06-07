package com.xiaomi.micolauncher.api.model;

import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoPlayList {
    private VideoBean data;
    private String encoding;
    private String language;
    private int status;
    private int ver;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public VideoBean getData() {
        return this.data;
    }

    public void setData(VideoBean videoBean) {
        this.data = videoBean;
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

    /* loaded from: classes3.dex */
    public static class VideoBean {
        private int display_style;
        private List<MediaciinfoBean> mediaciinfo;
        private int mediaid;
        private String medianame;
        private PagesBean pages;

        public String getMedianame() {
            return this.medianame;
        }

        public void setMedianame(String str) {
            this.medianame = str;
        }

        public int getMediaid() {
            return this.mediaid;
        }

        public void setMediaid(int i) {
            this.mediaid = i;
        }

        public PagesBean getPages() {
            return this.pages;
        }

        public void setPages(PagesBean pagesBean) {
            this.pages = pagesBean;
        }

        public int getDisplay_style() {
            return this.display_style;
        }

        public void setDisplay_style(int i) {
            this.display_style = i;
        }

        public List<MediaciinfoBean> getMediaciinfo() {
            return this.mediaciinfo;
        }

        public void setMediaciinfo(List<MediaciinfoBean> list) {
            this.mediaciinfo = list;
        }

        /* loaded from: classes3.dex */
        public static class PagesBean {
            private boolean has_next;
            private boolean has_pre;
            private int page;
            private int playcount;
            private int total;

            public boolean isHas_next() {
                return this.has_next;
            }

            public void setHas_next(boolean z) {
                this.has_next = z;
            }

            public int getPlaycount() {
                return this.playcount;
            }

            public void setPlaycount(int i) {
                this.playcount = i;
            }

            public int getTotal() {
                return this.total;
            }

            public void setTotal(int i) {
                this.total = i;
            }

            public int getPage() {
                return this.page;
            }

            public void setPage(int i) {
                this.page = i;
            }

            public boolean isHas_pre() {
                return this.has_pre;
            }

            public void setHas_pre(boolean z) {
                this.has_pre = z;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class MediaciinfoBean {
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
