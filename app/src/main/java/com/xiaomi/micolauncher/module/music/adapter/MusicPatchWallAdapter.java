package com.xiaomi.micolauncher.module.music.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.music.bean.Footer;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallCircleView;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallFooter;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallSquareView;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicPatchWallAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context a;
    private List<Object> b;
    private RecyclerView.RecycledViewPool c = new RecyclerView.RecycledViewPool();

    public MusicPatchWallAdapter(Context context) {
        this.a = context;
        this.c.setMaxRecycledViews(0, 100);
    }

    public void setDataList(List<Object> list) {
        this.b = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Object> list = this.b;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        PatchWall.Block block;
        Object obj = this.b.get(i);
        if (obj == null) {
            return 1;
        }
        if (obj instanceof Header) {
            return 0;
        }
        if (obj instanceof Footer) {
            return 4;
        }
        if (!(obj instanceof PatchWall.Block) || (block = (PatchWall.Block) obj) == null) {
            return 1;
        }
        String str = block.blockUiType.name;
        char c = 65535;
        switch (str.hashCode()) {
            case -1147287790:
                if (str.equals(PatchWall.BLOCK_GRID_CIRCLE_HR)) {
                    c = 2;
                    break;
                }
                break;
            case -674811177:
                if (str.equals(PatchWall.BLOCK_GRID_CIRCLE_HAS_GROUP_HR)) {
                    c = 0;
                    break;
                }
                break;
            case -534328426:
                if (str.equals(PatchWall.BLOCK_GRID_HAS_DETAILS)) {
                    c = 4;
                    break;
                }
                break;
            case 1246022455:
                if (str.equals(PatchWall.BLOCK_GRID_CIRCLE)) {
                    c = 1;
                    break;
                }
                break;
            case 1554729901:
                if (str.equals(PatchWall.BLOCK_GRID_QQ_MUSIC_GROUP)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return 2;
            case 3:
            case 4:
                return 3;
            default:
                return 1;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case 0:
                view = new MusicPatchWallHeader(this.a);
                break;
            case 1:
            case 3:
                view = new MusicPatchWallSquareView(this.a);
                break;
            case 2:
                view = new MusicPatchWallCircleView(this.a);
                break;
            case 4:
                view = new MusicPatchWallFooter(this.a);
                break;
            default:
                view = null;
                break;
        }
        BaseRecyclerViewHolder baseRecyclerViewHolder = new BaseRecyclerViewHolder(view);
        baseRecyclerViewHolder.setViewHolderType(i);
        return baseRecyclerViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        switch (baseRecyclerViewHolder.getViewHolderType()) {
            case 1:
                ((MusicPatchWallSquareView) baseRecyclerViewHolder.itemView).setPatchBlockData((PatchWall.Block) this.b.get(i));
                return;
            case 2:
                ((MusicPatchWallCircleView) baseRecyclerViewHolder.itemView).setPatchBlockData((PatchWall.Block) this.b.get(i));
                return;
            case 3:
                ((MusicPatchWallSquareView) baseRecyclerViewHolder.itemView).setPatchBlockData((PatchWall.Block) this.b.get(i));
                return;
            default:
                return;
        }
    }
}
