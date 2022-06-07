package com.xiaomi.micolauncher.module.childsong;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.homepage.view.ChildHotSongCardView;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallTitle;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildSongListSquareAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context a;
    private List<? extends IListItem> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ChildSongListSquareAdapter(Context context) {
        this.a = context;
    }

    public void setDataList(List<? extends IListItem> list) {
        this.b = list;
        notifyDataSetChanged();
    }

    public void addDataList(List list) {
        this.b.addAll(list);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<? extends IListItem> list = this.b;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.childsong.ChildSongListSquareAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    if (ChildSongListSquareAdapter.this.getItemViewType(i) == 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.b.get(i) instanceof Header ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case 0:
                view = new MusicPatchWallTitle(this.a);
                break;
            case 1:
                view = LayoutInflater.from(this.a).inflate(R.layout.view_music_square, viewGroup, false);
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
        final IListItem iListItem = (IListItem) this.b.get(i);
        if (iListItem != null) {
            switch (baseRecyclerViewHolder.getViewHolderType()) {
                case 0:
                    ((MusicPatchWallTitle) baseRecyclerViewHolder.itemView).setupView(iListItem.getItemTitle());
                    return;
                case 1:
                    View view = baseRecyclerViewHolder.itemView;
                    if (Hardware.isBigScreen()) {
                        ((ChildHotSongCardView) view.findViewById(R.id.card_view)).updateTitleAndImage(iListItem.getItemTitle(), iListItem.getItemImageUrl(), R.drawable.home_child_song_img_holder_with_bg_color_4342ff);
                    } else {
                        GlideUtils.bindImageViewWithRoundCorners(this.a, iListItem.getItemImageUrl(), (ImageView) view.findViewById(R.id.image_iv), this.a.getResources().getDimensionPixelSize(R.dimen.size_16dp), (int) R.drawable.music_card_view_default);
                        ((TextView) view.findViewById(R.id.title_tv)).setText(iListItem.getItemTitle());
                    }
                    view.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.ChildSongListSquareAdapter.2
                        @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                        public void onAvoidFastDoubleClick(View view2) {
                            if (!TextUtils.isEmpty(iListItem.getItemTarget())) {
                                SchemaManager.handleSchema(ChildSongListSquareAdapter.this.a, iListItem.getItemTarget());
                            }
                        }
                    });
                    return;
                default:
                    return;
            }
        }
    }
}
