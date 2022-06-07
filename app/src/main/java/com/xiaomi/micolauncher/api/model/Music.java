package com.xiaomi.micolauncher.api.model;

import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;

/* loaded from: classes3.dex */
public class Music {

    /* loaded from: classes3.dex */
    public static class Category implements BaseGroupListActivity.CategoryInterface {
        private String categoryDesc;
        private int categoryId;
        private String categoryImgurl;
        private String categoryName;
        private String categorySharePic;
        private int categoryShowDetail;
        private int categoryShowType;
        private int groupId;

        public String getCategoryDesc() {
            return this.categoryDesc;
        }

        public void setCategoryDesc(String str) {
            this.categoryDesc = str;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public long getCategoryId() {
            return this.categoryId;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryName() {
            return this.categoryName;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryType() {
            return this.categoryShowType + "";
        }

        public void setCategoryId(int i) {
            this.categoryId = i;
        }

        public String getCategoryImgurl() {
            return this.categoryImgurl;
        }

        public void setCategoryImgurl(String str) {
            this.categoryImgurl = str;
        }

        public void setCategoryName(String str) {
            this.categoryName = str;
        }

        public String getCategorySharePic() {
            return this.categorySharePic;
        }

        public void setCategorySharePic(String str) {
            this.categorySharePic = str;
        }

        public int getCategoryShowDetail() {
            return this.categoryShowDetail;
        }

        public void setCategoryShowDetail(int i) {
            this.categoryShowDetail = i;
        }

        public int getCategoryShowType() {
            return this.categoryShowType;
        }

        public void setCategoryShowType(int i) {
            this.categoryShowType = i;
        }

        public int getGroupId() {
            return this.groupId;
        }

        public void setGroupId(int i) {
            this.groupId = i;
        }
    }
}
