package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class HomeVideoData {
    @SerializedName("hasMore")
    private boolean a;
    @SerializedName("hitLevel")
    private String b;
    @SerializedName("hits")
    private String c;
    @SerializedName("traceId")
    private String d;
    @SerializedName("expId")
    private String e;
    @SerializedName("videoList")
    private List<VideoData> f;

    public boolean isHasMore() {
        return this.a;
    }

    public void setHasMore(boolean z) {
        this.a = z;
    }

    public String getHitLevel() {
        return this.b;
    }

    public void setHitLevel(String str) {
        this.b = str;
    }

    public String getHits() {
        return this.c;
    }

    public void setHits(String str) {
        this.c = str;
    }

    public String getTraceId() {
        return this.d;
    }

    public void setTraceId(String str) {
        this.d = str;
    }

    public String getExpId() {
        return this.e;
    }

    public void setExpId(String str) {
        this.e = str;
    }

    public List<VideoData> getVideoList() {
        return this.f;
    }

    public void setVideoList(List<VideoData> list) {
        this.f = list;
    }
}
