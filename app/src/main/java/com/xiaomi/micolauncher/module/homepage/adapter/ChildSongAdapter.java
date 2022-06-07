package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.module.homepage.manager.ChildSongDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.ChildHotSongViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.ChildSongCategoryHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.ChildSongQueryViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.ChildSongRecentAndFavHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.ChildSongRecommendViewHolder;

/* loaded from: classes3.dex */
public class ChildSongAdapter extends BaseAdapter<BaseMusicViewHolder> {
    public static final int HOME_CHILD_SONG_LIST_ITEM_COUNT = 7;
    public static final int HOME_CHILD_SONG_LIST_ITEM_COUNT_HIDE = 0;
    public static final int VIEW_TYPE_CATEGORY = 5;
    public static final int VIEW_TYPE_CHINESE_CHILD_SONG = 3;
    public static final int VIEW_TYPE_ENGLISH_CHILD_SONG = 6;
    public static final int VIEW_TYPE_RECENT_AND_FAV = 1;
    public static final int VIEW_TYPE_RECOMMENDATION = 0;
    public static final int VIEW_TYPE_SPEECH_QUERY_0 = 2;
    public static final int VIEW_TYPE_SPEECH_QUERY_1 = 4;
    private int a = 7;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseMusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseMusicViewHolder baseMusicViewHolder;
        long beginTiming = DebugHelper.beginTiming();
        switch (i) {
            case 0:
                baseMusicViewHolder = new ChildSongRecommendViewHolder(inflateView(viewGroup, R.layout.music_find_like_child_mode));
                break;
            case 1:
                baseMusicViewHolder = new ChildSongRecentAndFavHolder(inflateView(viewGroup, R.layout.music_find_child_recent_and_fav));
                break;
            case 2:
                baseMusicViewHolder = new ChildSongQueryViewHolder(i, inflateView(viewGroup, R.layout.music_find_child_song_query));
                break;
            case 3:
                baseMusicViewHolder = new ChildHotSongViewHolder(i, inflateView(viewGroup, R.layout.music_find_child_hot_song));
                break;
            case 4:
                baseMusicViewHolder = new ChildSongQueryViewHolder(i, inflateView(viewGroup, R.layout.music_find_child_song_query));
                break;
            case 5:
                baseMusicViewHolder = new ChildSongCategoryHolder(inflateView(viewGroup, R.layout.music_find_child_song_category));
                break;
            case 6:
                baseMusicViewHolder = new ChildHotSongViewHolder(i, inflateView(viewGroup, R.layout.music_find_child_hot_song));
                break;
            default:
                baseMusicViewHolder = new ChildSongRecommendViewHolder(inflateView(viewGroup, R.layout.music_find_like_child_mode));
                break;
        }
        baseMusicViewHolder.initInMain();
        DebugHelper.endTiming(beginTiming, "onCreateViewHolder %d ", Integer.valueOf(i));
        return baseMusicViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseMusicViewHolder baseMusicViewHolder, int i) {
        long beginTiming = DebugHelper.beginTiming();
        baseMusicViewHolder.setData(ChildSongDataManager.getManager().getBlock(i));
        DebugHelper.endTiming(beginTiming, "onBindViewHolder : %d , address : %s", Integer.valueOf(i), this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a;
    }

    public void showItems() {
        this.a = 7;
    }

    public void hideItems() {
        this.a = 0;
    }
}
