package com.xiaomi.micolauncher.api.model;

import com.google.android.exoplayer2.offline.DownloadService;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class CourseTab {
    @SerializedName(DownloadService.KEY_CONTENT_ID)
    private String contentId;
    @SerializedName("filter_id")
    private String filterId;
    private String id;
    private String name;
    private String pcode;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getContentId() {
        return this.contentId;
    }

    public void setContentId(String str) {
        this.contentId = str;
    }

    public String getFilterId() {
        return this.filterId;
    }

    public void setFilterId(String str) {
        this.filterId = str;
    }

    public String getPcode() {
        return this.pcode;
    }

    public void setPcode(String str) {
        this.pcode = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}
