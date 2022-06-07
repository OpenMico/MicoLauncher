package com.xiaomi.micolauncher.skills.video.model;

import com.xiaomi.mico.common.ContainerUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class RecommendCartoon {
    public String action;
    public BackgroundImage backgroundImage;
    public Extend extend;
    public int interval;
    public String pageId;
    public String screenCategory;
    public String status;
    public String subTitle1;
    public String subTitle2;
    public String tag;
    public List tips;
    public String title;
    public String trackKey;
    public String type;

    /* loaded from: classes3.dex */
    public static class BackgroundImage {
        public String large;
        public String middle;
        public String small;
    }

    /* loaded from: classes3.dex */
    public static class Extend {
    }

    public String getImageUrl() {
        BackgroundImage backgroundImage = this.backgroundImage;
        if (backgroundImage == null) {
            return "";
        }
        if (ContainerUtil.hasData(backgroundImage.small)) {
            return this.backgroundImage.small;
        }
        if (ContainerUtil.hasData(this.backgroundImage.middle)) {
            return this.backgroundImage.middle;
        }
        return this.backgroundImage.large;
    }
}
