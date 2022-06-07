package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.EntertainmentHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicAdapter extends BaseAdapter<BaseMusicViewHolder> {
    public static final int VIEW_TYPE_CATEGORY_FIRST = 12;
    public static final int VIEW_TYPE_CATEGORY_SECOND = 13;
    public static final int VIEW_TYPE_CATEGORY_THIRD = 14;
    public static final int VIEW_TYPE_EMPTY = 15;
    public static final int VIEW_TYPE_HOT = 8;
    public static final int VIEW_TYPE_LIKE = 0;
    public static final int VIEW_TYPE_MV = 1;
    public static final int VIEW_TYPE_RADIO_FIRST = 5;
    public static final int VIEW_TYPE_RADIO_SECOND = 6;
    public static final int VIEW_TYPE_RADIO_THIRD = 7;
    public static final int VIEW_TYPE_RECOMMEND_FIRST = 2;
    public static final int VIEW_TYPE_RECOMMEND_SECOND = 3;
    public static final int VIEW_TYPE_RECOMMEND_THIRD = 4;
    public static final int VIEW_TYPE_SINGER_FIRST = 9;
    public static final int VIEW_TYPE_SINGER_SECOND = 10;
    public static final int VIEW_TYPE_SINGER_THIRD = 11;
    public List<PatchWall.Block> blocks;
    public boolean isAuthStatus;

    public MusicAdapter(List<PatchWall.Block> list) {
        this.blocks = list;
    }

    public void setAuthStatus(boolean z) {
        this.isAuthStatus = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseMusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return EntertainmentHolderCacheManager.getManager().getMusicViewHolder(i);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (ContainerUtil.isEmpty(this.blocks) && this.isAuthStatus) {
            return 15;
        }
        PatchWall.Block block = this.blocks.get(i);
        String str = block.blockUiType.name;
        char c = 65535;
        switch (str.hashCode()) {
            case -1147287790:
                if (str.equals(PatchWall.BLOCK_GRID_CIRCLE_HR)) {
                    c = 5;
                    break;
                }
                break;
            case -674811177:
                if (str.equals(PatchWall.BLOCK_GRID_CIRCLE_HAS_GROUP_HR)) {
                    c = 3;
                    break;
                }
                break;
            case -534328426:
                if (str.equals(PatchWall.BLOCK_GRID_HAS_DETAILS)) {
                    c = 4;
                    break;
                }
                break;
            case 606147261:
                if (str.equals(PatchWall.BLOCK_GRID_PANEL)) {
                    c = 1;
                    break;
                }
                break;
            case 1071357355:
                if (str.equals(PatchWall.BLOCK_RECOMMENDATION)) {
                    c = 0;
                    break;
                }
                break;
            case 1286164408:
                if (str.equals(PatchWall.BLOCK_GRID)) {
                    c = 2;
                    break;
                }
                break;
            case 1554729901:
                if (str.equals(PatchWall.BLOCK_GRID_QQ_MUSIC_GROUP)) {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return block.viewType;
            case 3:
                return block.viewType;
            case 4:
                return 8;
            case 5:
                return block.viewType;
            case 6:
                return block.viewType;
            default:
                return 0;
        }
    }

    public void onBindViewHolder(@NonNull BaseMusicViewHolder baseMusicViewHolder, int i) {
        if (!ContainerUtil.isEmpty(this.blocks) && !this.isScrolling) {
            baseMusicViewHolder.setData(this.blocks.get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.isAuthStatus) {
            return 1;
        }
        return this.blocks.size();
    }
}
