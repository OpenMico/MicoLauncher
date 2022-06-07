package com.xiaomi.micolauncher.skills.music.model.api;

import com.xiaomi.micolauncher.api.model.MainPage;

/* loaded from: classes3.dex */
public class NewsItem {
    private String cover;
    private String cp;
    private String cpId;
    private String newsId = "";
    private String playUrl;
    private String title;

    public NewsItem(MainPage.News news) {
        this.title = news.getTitle();
        this.playUrl = news.getExtend().getPlayUrl();
        this.cp = news.getExtend().getCp();
        this.cpId = news.getExtend().getNewsId();
        this.cover = news.getBackgroundImage().getSmall();
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String str) {
        this.newsId = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getPlayUrl() {
        return this.playUrl;
    }

    public void setPlayUrl(String str) {
        this.playUrl = str;
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

    public String getCover() {
        return this.cover;
    }

    public void setCover(String str) {
        this.cover = str;
    }
}
