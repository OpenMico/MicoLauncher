package com.xiaomi.micolauncher.api.model;

import java.util.List;

/* loaded from: classes3.dex */
public interface IBlockBean {
    public static final String BLOCK_TYPE_APP = "app";
    public static final String BLOCK_TYPE_SONG = "song";
    public static final String BLOCK_TYPE_STORY = "story";
    public static final String BLOCK_TYPE_VIDEO = "video";

    String getBlockTitle();

    String getBlockType();

    List<? extends IListItem> getListItems();

    String getUiType();
}
