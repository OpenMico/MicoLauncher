package com.xiaomi.micolauncher.module.music.bean;

import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Video;

/* loaded from: classes3.dex */
public class SquareItem {
    public static final int TYPE_MUSIC_GROUP = 1;
    public static final int TYPE_MUSIC_OTHER = 2;
    public static final int TYPE_VIDEO = 3;
    public String imageUrl;
    public String target;
    public String title;
    public int type;

    public SquareItem(int i) {
        this.type = i;
    }

    public SquareItem(PatchWall.Group group) {
        if (group != null) {
            this.imageUrl = group.imageUrl;
            this.title = group.categoryName;
            this.target = group.target;
            this.type = 1;
        }
    }

    public SquareItem(PatchWall.Item item) {
        if (item != null && item.hasImage()) {
            this.imageUrl = item.images.poster.url;
            this.title = item.title;
            this.target = item.target;
            this.type = 2;
        }
    }

    public SquareItem(PatchWall.FavoriteSongBook favoriteSongBook) {
        if (favoriteSongBook != null) {
            this.imageUrl = favoriteSongBook.cover;
            this.title = favoriteSongBook.name;
            this.target = "mico://homepage/playlist?id=" + favoriteSongBook.id;
            this.type = 2;
        }
    }

    public SquareItem(Video.Item item) {
        if (item != null) {
            this.imageUrl = item.horizontalImgUrl;
            this.title = item.name;
            this.type = 3;
        }
    }
}
