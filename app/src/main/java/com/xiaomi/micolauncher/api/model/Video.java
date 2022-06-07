package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class Video {

    /* loaded from: classes3.dex */
    public static class Category implements BaseGroupListActivity.CategoryInterface {
        public static final int ANY_CATEGORY_ID = 6;
        public static final String ANY_CATEGORY_TYPE = "ANY";
        public String category;
        public long id;
        public String imageUrl;
        public String origin;
        public String type;

        public Category(long j, String str) {
            this.id = j;
            this.category = str;
            this.type = str;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Category) {
                return this.id == ((Category) obj).id;
            }
            return super.equals(obj);
        }

        public int hashCode() {
            long j = this.id;
            return 37 + ((int) (j ^ (j >>> 32)));
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public long getCategoryId() {
            return this.id;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryName() {
            return this.category;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryType() {
            return this.type;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryOrigin() {
            return this.origin;
        }
    }

    /* loaded from: classes3.dex */
    public static class Item {
        public String actors;
        public String area;
        public String bigHorizontalImgUrl;
        public String category;
        public String cp;
        public String cpId;
        public String desc;
        public String directors;
        public Map<String, String> episodesId;
        public String horizontalImgUrl;
        public String id;
        public boolean isVip;
        public String lastEpisode;
        public String name;
        public int playLength;
        public String rating;
        public String style;
        public int totalEpisodes;
        public String type;
        public String verticalImgUrl;
        public int year;

        public String getHorizontalImgUrl() {
            if (!TextUtils.isEmpty(this.horizontalImgUrl)) {
                return this.horizontalImgUrl;
            }
            if (!TextUtils.isEmpty(this.bigHorizontalImgUrl)) {
                return this.bigHorizontalImgUrl;
            }
            if (!TextUtils.isEmpty(this.verticalImgUrl)) {
                return this.verticalImgUrl;
            }
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static class Block {
        public long categoryId;
        public String categoryName;
        public String categoryType;
        public List<Item> itemList;

        public Block() {
        }

        public Block(long j, String str, String str2, List<Item> list) {
            this.categoryId = j;
            this.categoryName = str;
            this.categoryType = str2;
            this.itemList = list;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShortVideo implements MainPage.MainPageVideo {
        public boolean auth;
        public String cp;
        public String cpId;
        public String horizontalImgUrl;
        public String id;
        public String name;
        public int playLength;
        public String style;
        public String url;

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public boolean isShortVideo() {
            return true;
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public String coverImageUrl() {
            return this.horizontalImgUrl;
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public String videoCategory() {
            return this.style;
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public VideoItem transform() {
            return new VideoItem(this);
        }

        @Override // com.xiaomi.micolauncher.api.model.MainPage.MainPageVideo
        public String videoTitle() {
            return this.name;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ShortVideo shortVideo = (ShortVideo) obj;
            return this.playLength == shortVideo.playLength && Objects.equals(this.id, shortVideo.id) && Objects.equals(this.name, shortVideo.name) && Objects.equals(this.cp, shortVideo.cp) && Objects.equals(this.cpId, shortVideo.cpId) && Objects.equals(this.horizontalImgUrl, shortVideo.horizontalImgUrl) && Objects.equals(this.style, shortVideo.style);
        }

        public int hashCode() {
            return Objects.hash(this.id, this.name, this.cp, this.cpId, this.horizontalImgUrl, Integer.valueOf(this.playLength), this.style);
        }
    }
}
