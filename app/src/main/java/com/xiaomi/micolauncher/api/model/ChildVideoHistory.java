package com.xiaomi.micolauncher.api.model;

import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoHistory {
    private List<HistoryBean> data;
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

    public List<HistoryBean> getData() {
        return this.data;
    }

    public void setData(List<HistoryBean> list) {
        this.data = list;
    }

    /* loaded from: classes3.dex */
    public static class HistoryBean {
        private int ci;
        private String date;
        private int mediaid;
        private double moviepercent;
        private double percent;
        private int seconds;
        private int source;

        public int getCi() {
            return this.ci;
        }

        public void setCi(int i) {
            this.ci = i;
        }

        public int getMediaid() {
            return this.mediaid;
        }

        public void setMediaid(int i) {
            this.mediaid = i;
        }

        public int getSeconds() {
            return this.seconds;
        }

        public void setSeconds(int i) {
            this.seconds = i;
        }

        public double getPercent() {
            return this.percent;
        }

        public void setPercent(double d) {
            this.percent = d;
        }

        public int getSource() {
            return this.source;
        }

        public void setSource(int i) {
            this.source = i;
        }

        public double getMoviepercent() {
            return this.moviepercent;
        }

        public void setMoviepercent(double d) {
            this.moviepercent = d;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String str) {
            this.date = str;
        }
    }
}
