package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.micolauncher.module.video.childmode.bean.RecommendPlayItem;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class PatchWall {
    public static final String BLOCK_DAILY_SONGS = "daily_songs";
    public static final String BLOCK_GRID = "block_grid";
    public static final String BLOCK_GRID_BUTTON = "block_grid_button";
    public static final String BLOCK_GRID_CIRCLE = "block_grid_circle";
    public static final String BLOCK_GRID_CIRCLE_HAS_GROUP_HR = "block_grid_circle_has_group_hr";
    public static final String BLOCK_GRID_CIRCLE_HR = "block_grid_circle_hr";
    public static final String BLOCK_GRID_HAS_DETAILS = "block_grid_has_details";
    public static final String BLOCK_GRID_HR = "block_grid_hr";
    public static final String BLOCK_GRID_PANEL = "block_grid_panel";
    public static final String BLOCK_GRID_QQ_MUSIC_GROUP = "block_grid_qq_music_group";
    public static final String BLOCK_GRID_RICH = "block_grid_rich";
    public static final String BLOCK_RECOMMENDATION = "block_recommendation";
    public List<Block> blocks;
    public int status;

    /* loaded from: classes3.dex */
    public static class BlockStat {
        public String traceid;
    }

    /* loaded from: classes3.dex */
    public static class BlockUIType {
        public int columns;
        public String name;
        public float ratio;
    }

    /* loaded from: classes3.dex */
    public static class Extra {
        @SerializedName("mi_play_url")
        public String miPlayUrl;
    }

    /* loaded from: classes3.dex */
    public static class Pos {
        public int h;
        public int w;
        public int x;
        public int y;
    }

    public String toString() {
        return "PatchWall{status=" + this.status + ", blocks=" + this.blocks + '}';
    }

    /* loaded from: classes3.dex */
    public static class Block {
        public BlockUIType blockUiType;
        public String description;
        public boolean displayMore;
        public long id;
        public List<Item> items;
        public List<Music.Song> songList;
        public String title;
        public int viewType;

        public Block() {
            this.items = new ArrayList();
        }

        public Block(Category category) {
            this.items = new ArrayList();
            this.items = category.items;
        }

        public String toString() {
            return "Block{title='" + this.title + "'blockUiType=" + this.blockUiType.name + "items=" + this.items + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class Item implements IListItem {
        private static final int CP_INDEX_INCREASE = 4;
        public String extra;
        public String groupName;
        public int groupTypeId;
        public List<Group> groups;
        public String id;
        public Image images;
        public ItemUIType itemUiType;
        public long playCount;
        public String shortDescription;
        public List<Song> songs;
        public String target;
        public String title;

        public Item() {
        }

        public Item(RecommendPlayItem recommendPlayItem) {
            this.images = new Image();
            this.images.poster = new Poster();
            this.images.poster.url = recommendPlayItem.getImgUrl();
            this.title = recommendPlayItem.getTitle();
            this.target = recommendPlayItem.getTarget();
        }

        public void setGroups(List<Group> list) {
            this.groups = list;
        }

        public boolean hasImage() {
            Image image = this.images;
            return (image == null || image.poster == null || TextUtils.isEmpty(this.images.poster.url)) ? false : true;
        }

        public String toString() {
            return "Item{title='" + this.title + "', target='" + this.target + "', images=" + this.images + '}';
        }

        public String getCpFromTarget() {
            int indexOf = this.target.indexOf(MusicGroupListActivity.SPECIAL_SYMBOL);
            return this.target.substring(this.target.indexOf("?") + 4, indexOf);
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemImageUrl() {
            Image image = this.images;
            return (image == null || image.poster == null) ? "" : this.images.poster.url;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTitle() {
            return this.title;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTarget() {
            return this.target;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemId() {
            return this.id;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Item item = (Item) obj;
            return this.groupTypeId == item.groupTypeId && this.playCount == item.playCount && Objects.equals(this.id, item.id) && Objects.equals(this.title, item.title) && Objects.equals(this.target, item.target) && Objects.equals(this.images, item.images) && Objects.equals(this.itemUiType, item.itemUiType) && Objects.equals(this.groupName, item.groupName) && Objects.equals(this.songs, item.songs) && Objects.equals(this.shortDescription, item.shortDescription) && Objects.equals(this.groups, item.groups);
        }

        public int hashCode() {
            return Objects.hash(this.id, this.title, this.target, this.images, this.itemUiType, Integer.valueOf(this.groupTypeId), this.groupName, this.songs, Long.valueOf(this.playCount), this.shortDescription, this.groups);
        }

        public String getMiPlayUrl() {
            Extra extra;
            if (!TextUtils.isEmpty(this.extra) && (extra = (Extra) Gsons.getGson().fromJson(this.extra, (Class<Object>) Extra.class)) != null) {
                return extra.miPlayUrl;
            }
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static class Image {
        public Poster poster;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.poster, ((Image) obj).poster);
        }

        public int hashCode() {
            return Objects.hash(this.poster);
        }
    }

    /* loaded from: classes3.dex */
    public static class Poster {
        public String url;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.url, ((Poster) obj).url);
        }

        public int hashCode() {
            return Objects.hash(this.url);
        }
    }

    /* loaded from: classes3.dex */
    public static class ItemUIType {
        public String name;
        public Pos pos;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ItemUIType itemUIType = (ItemUIType) obj;
            return Objects.equals(this.name, itemUIType.name) && Objects.equals(this.pos, itemUIType.pos);
        }

        public int hashCode() {
            return Objects.hash(this.name, this.pos);
        }
    }

    /* loaded from: classes3.dex */
    public static class Song {
        public Artist artist;
        public String name;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Song song = (Song) obj;
            return Objects.equals(this.name, song.name) && Objects.equals(this.artist, song.artist);
        }

        public int hashCode() {
            return Objects.hash(this.name, this.artist);
        }
    }

    /* loaded from: classes3.dex */
    public static class Artist {
        public String name;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.name, ((Artist) obj).name);
        }

        public int hashCode() {
            return Objects.hash(this.name);
        }
    }

    /* loaded from: classes3.dex */
    public static class Group {
        public String categoryId;
        public String categoryName;
        public String detailPageCoverUrl;
        public String imageUrl;
        public String queryExample;
        public String target;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Group group = (Group) obj;
            return Objects.equals(this.categoryId, group.categoryId) && Objects.equals(this.categoryName, group.categoryName) && Objects.equals(this.imageUrl, group.imageUrl) && Objects.equals(this.detailPageCoverUrl, group.detailPageCoverUrl) && Objects.equals(this.queryExample, group.queryExample) && Objects.equals(this.target, group.target);
        }

        public int hashCode() {
            return Objects.hash(this.categoryId, this.categoryName, this.imageUrl, this.detailPageCoverUrl, this.queryExample, this.target);
        }
    }

    /* loaded from: classes3.dex */
    public static class Category {
        public BlockStat blockStat;
        public List<Item> items;

        /* loaded from: classes3.dex */
        public static class Block implements IBlockBean {
            public long categoryId;
            public String categoryName;
            public List<Item> itemList;

            @Override // com.xiaomi.micolauncher.api.model.IBlockBean
            public String getBlockType() {
                return IBlockBean.BLOCK_TYPE_SONG;
            }

            @Override // com.xiaomi.micolauncher.api.model.IBlockBean
            public String getUiType() {
                return "";
            }

            public Block(long j, String str, List<Item> list) {
                this.categoryId = j;
                this.categoryName = str;
                this.itemList = list;
            }

            @Override // com.xiaomi.micolauncher.api.model.IBlockBean
            public String getBlockTitle() {
                return this.categoryName;
            }

            @Override // com.xiaomi.micolauncher.api.model.IBlockBean
            public List<? extends IListItem> getListItems() {
                return this.itemList;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class FavoriteSongBook {
        private static final int SONG_LIST_TYPE_CHILD_MODEL = 2;
        public String cover;
        public String id;
        public String intro;
        public boolean isDefault;
        public String name;
        public boolean operable;
        public String origin;
        public int songCount;
        public String songList;
        public int songListType;
        public String subTitle;
        public String tags;
        public String type;

        public boolean isBabyLike() {
            return this.songListType == 2;
        }
    }
}
