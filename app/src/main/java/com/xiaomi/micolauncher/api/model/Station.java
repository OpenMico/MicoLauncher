package com.xiaomi.micolauncher.api.model;

import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class Station {

    /* loaded from: classes3.dex */
    public static class CategoryItem {
        public List<String> cpList;
        public String imageUrl;
        public String name;
        public String parentImageUrl;
        public String parentName;
        public String queryExample;
        public int stationCategoryType;
    }

    /* loaded from: classes3.dex */
    public static class Sound {
        public boolean isEnd;
        public List<Item> soundList;
    }

    /* loaded from: classes3.dex */
    public static class Category implements BaseGroupListActivity.CategoryInterface {
        private int categoryId;
        private String categoryName;
        private String cps;
        private String saleType;
        private String sort;
        private String source;
        private String tag;

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
            return this.saleType;
        }

        public void setCategoryId(int i) {
            this.categoryId = i;
        }

        public void setCategoryName(String str) {
            this.categoryName = str;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String str) {
            this.tag = str;
        }

        public String getCps() {
            return this.cps;
        }

        public void setCps(String str) {
            this.cps = str;
        }

        public String getSaleType() {
            return this.saleType;
        }

        public void setSaleType(String str) {
            this.saleType = str;
        }

        public String getSource() {
            return this.source;
        }

        public void setSource(String str) {
            this.source = str;
        }

        public String getSort() {
            return this.sort;
        }

        public void setSort(String str) {
            this.sort = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class Item implements IListItem {
        private String albumAbstract;
        private long albumGlobalID;
        private String albumId;
        private String albumName;
        private boolean bought;
        private String broadcaster;
        private String category;
        private String cover;
        private String cp;
        private int duration;
        private int episodes;
        private int episodesNum;
        private long globalID;
        private String id;
        private boolean isFeature;
        private String isReverse;
        private String origin;
        private String originName;
        private String parentID;
        private String paymentUrl;
        private long playCount;
        private String playSequence;
        private int saleType;
        private int salesPrice;
        private Object snippetTitle;
        private String target;
        private String title;
        private Object titleAlias;
        private String type;
        private long updateTime;

        public int getTypeId() {
            String str = this.type;
            if (str == null) {
                return 0;
            }
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 92896879) {
                if (hashCode != 108270587) {
                    if (hashCode == 109627663 && str.equals("sound")) {
                        c = 2;
                    }
                } else if (str.equals("radio")) {
                    c = 1;
                }
            } else if (str.equals("album")) {
                c = 0;
            }
            switch (c) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                default:
                    return 0;
            }
        }

        public long getGlobalID() {
            return this.globalID;
        }

        public void setGlobalID(long j) {
            this.globalID = j;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getBroadcaster() {
            return this.broadcaster;
        }

        public void setBroadcaster(String str) {
            this.broadcaster = str;
        }

        public String getCover() {
            return this.cover;
        }

        public void setCover(String str) {
            this.cover = str;
        }

        public String getCp() {
            return this.cp;
        }

        public void setCp(String str) {
            this.cp = str;
        }

        public String getOrigin() {
            return this.origin;
        }

        public void setOrigin(String str) {
            this.origin = str;
        }

        public String getOriginName() {
            return this.originName;
        }

        public void setOriginName(String str) {
            this.originName = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public int getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(int i) {
            this.episodes = i;
        }

        public int getEpisodesNum() {
            return this.episodesNum;
        }

        public void setEpisodesNum(int i) {
            this.episodesNum = i;
        }

        public Object getSnippetTitle() {
            return this.snippetTitle;
        }

        public void setSnippetTitle(Object obj) {
            this.snippetTitle = obj;
        }

        public Object getTitleAlias() {
            return this.titleAlias;
        }

        public void setTitleAlias(Object obj) {
            this.titleAlias = obj;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String str) {
            this.category = str;
        }

        public String getParentID() {
            return this.parentID;
        }

        public void setParentID(String str) {
            this.parentID = str;
        }

        public String getAlbumId() {
            return this.albumId;
        }

        public void setAlbumId(String str) {
            this.albumId = str;
        }

        public long getAlbumGlobalID() {
            return this.albumGlobalID;
        }

        public void setAlbumGlobalID(long j) {
            this.albumGlobalID = j;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setDuration(int i) {
            this.duration = i;
        }

        public String getAlbumName() {
            return this.albumName;
        }

        public void setAlbumName(String str) {
            this.albumName = str;
        }

        public String getAlbumAbstract() {
            return this.albumAbstract;
        }

        public void setAlbumAbstract(String str) {
            this.albumAbstract = str;
        }

        public int getSaleType() {
            return this.saleType;
        }

        public void setSaleType(int i) {
            this.saleType = i;
        }

        public long getPlayCount() {
            return this.playCount;
        }

        public void setPlayCount(int i) {
            this.playCount = i;
        }

        public long getUpdateTime() {
            return this.updateTime;
        }

        public void setUpdateTime(long j) {
            this.updateTime = j;
        }

        public boolean isBought() {
            return this.bought;
        }

        public void setBought(boolean z) {
            this.bought = z;
        }

        public int getSalesPrice() {
            return this.salesPrice;
        }

        public void setSalesPrice(int i) {
            this.salesPrice = i;
        }

        public String getPaymentUrl() {
            return this.paymentUrl;
        }

        public void setPaymentUrl(String str) {
            this.paymentUrl = str;
        }

        public String getIsReverse() {
            return this.isReverse;
        }

        public void setIsReverse(String str) {
            this.isReverse = str;
        }

        public String getPlaySequence() {
            return this.playSequence;
        }

        public void setPlaySequence(String str) {
            this.playSequence = str;
        }

        public boolean isIsFeature() {
            return this.isFeature;
        }

        public void setIsFeature(boolean z) {
            this.isFeature = z;
        }

        public String toString() {
            return "Item{title='" + this.title + "', category='" + this.category + "'}";
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemImageUrl() {
            return getCover();
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTitle() {
            return getTitle();
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTarget() {
            return this.target;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemId() {
            return getId();
        }

        public String getTarget() {
            return this.target;
        }

        public void setTarget(String str) {
            this.target = str;
        }

        public Item convertFromSource(ChildStory.BlocksBean.ItemsBean itemsBean) {
            Item item = new Item();
            if (itemsBean == null) {
                return item;
            }
            item.setCover(itemsBean.getImages().getPoster().getUrl());
            item.setTitle(itemsBean.getTitle());
            item.setTarget(itemsBean.getTarget());
            item.setId(itemsBean.getId());
            return item;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Item item = (Item) obj;
            return this.globalID == item.globalID && this.episodes == item.episodes && this.episodesNum == item.episodesNum && this.albumGlobalID == item.albumGlobalID && this.duration == item.duration && this.saleType == item.saleType && this.playCount == item.playCount && this.updateTime == item.updateTime && this.bought == item.bought && this.salesPrice == item.salesPrice && this.isFeature == item.isFeature && Objects.equals(this.id, item.id) && Objects.equals(this.title, item.title) && Objects.equals(this.broadcaster, item.broadcaster) && Objects.equals(this.cover, item.cover) && Objects.equals(this.cp, item.cp) && Objects.equals(this.origin, item.origin) && Objects.equals(this.originName, item.originName) && Objects.equals(this.type, item.type) && Objects.equals(this.snippetTitle, item.snippetTitle) && Objects.equals(this.titleAlias, item.titleAlias) && Objects.equals(this.category, item.category) && Objects.equals(this.parentID, item.parentID) && Objects.equals(this.albumId, item.albumId) && Objects.equals(this.albumName, item.albumName) && Objects.equals(this.albumAbstract, item.albumAbstract) && Objects.equals(this.paymentUrl, item.paymentUrl) && Objects.equals(this.isReverse, item.isReverse) && Objects.equals(this.playSequence, item.playSequence) && Objects.equals(this.target, item.target);
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.globalID), this.id, this.title, this.broadcaster, this.cover, this.cp, this.origin, this.originName, this.type, Integer.valueOf(this.episodes), Integer.valueOf(this.episodesNum), this.snippetTitle, this.titleAlias, this.category, this.parentID, this.albumId, Long.valueOf(this.albumGlobalID), Integer.valueOf(this.duration), this.albumName, this.albumAbstract, Integer.valueOf(this.saleType), Long.valueOf(this.playCount), Long.valueOf(this.updateTime), Boolean.valueOf(this.bought), Integer.valueOf(this.salesPrice), this.paymentUrl, this.isReverse, this.playSequence, Boolean.valueOf(this.isFeature), this.target);
        }
    }

    /* loaded from: classes3.dex */
    public static class Block {
        public long categoryId;
        public String categoryName;
        public List<Item> itemList;

        public Block() {
        }

        public Block(long j, String str, List<Item> list) {
            this.categoryId = j;
            this.categoryName = str;
            this.itemList = list;
        }
    }

    /* loaded from: classes3.dex */
    public static class HomeStation {
        private String cp;
        private String cpId;

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
    }
}
