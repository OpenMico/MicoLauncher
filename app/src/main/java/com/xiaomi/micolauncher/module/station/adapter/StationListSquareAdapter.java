package com.xiaomi.micolauncher.module.station.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.homepage.view.ChildStoryCardView;
import com.xiaomi.micolauncher.module.homepage.view.ChildVideoCardView;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallTitle;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import java.util.List;

/* loaded from: classes3.dex */
public class StationListSquareAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    public static final int CARD_TYPE_AGE_SECONDARY = 1001;
    public static final int CARD_TYPE_COMMON = 1000;
    public static final int CARD_TYPE_VIDEO = 1003;
    public static final int CARD_TYPE_VIDEO_SECONDARY = 1002;
    private Context a;
    private String b;
    private List<? extends IListItem> c;
    private int d;
    private int e;
    private int f;
    private int g;
    private String h;

    public StationListSquareAdapter(Context context, int i) {
        this.a = context;
        this.d = i;
        a();
    }

    public void setDataList(List<? extends IListItem> list) {
        this.c = list;
        L.childContent.i("load finish data size %d", Integer.valueOf(list.size()));
        notifyDataSetChanged();
    }

    public void setCategoryName(String str) {
        this.h = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.c);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.station.adapter.StationListSquareAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    if (StationListSquareAdapter.this.getItemViewType(i) == 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.c.get(i) instanceof Header ? 0 : 1;
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
                int i2 = this.d;
                if (i2 == 1003) {
                    view = LayoutInflater.from(this.a).inflate(R.layout.view_child_video_square, viewGroup, false);
                } else if (i2 == 1002) {
                    view = LayoutInflater.from(this.a).inflate(R.layout.view_video_second_square, viewGroup, false);
                } else {
                    view = LayoutInflater.from(this.a).inflate(R.layout.view_story_square, viewGroup, false);
                }
                if (Hardware.isBigScreen()) {
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = this.a.getResources().getDimensionPixelSize(this.e);
                    layoutParams.width = this.a.getResources().getDimensionPixelSize(this.f);
                    view.setLayoutParams(layoutParams);
                    break;
                }
                break;
            default:
                view = null;
                break;
        }
        BaseRecyclerViewHolder baseRecyclerViewHolder = new BaseRecyclerViewHolder(view);
        baseRecyclerViewHolder.setViewHolderType(i);
        return baseRecyclerViewHolder;
    }

    private void a() {
        switch (this.d) {
            case 1001:
                this.e = R.dimen.child_mode_secondary_age_height;
                this.f = R.dimen.child_mode_secondary_age_width;
                this.g = R.drawable.home_child_song_img_holder_with_bg_color_4342ff;
                return;
            case 1002:
                this.e = R.dimen.child_group_item_height;
                this.f = R.dimen.child_group_item_width;
                this.g = R.drawable.home_child_video_second_img_holder_with_bg_color_4342ff;
                return;
            case 1003:
                this.e = R.dimen.child_recent_video_item_height;
                this.f = R.dimen.child_recent_video_item_width;
                this.g = R.drawable.home_child_video_img_holder_with_bg_color_4342ff;
                return;
            default:
                this.e = R.dimen.child_mode_colletion_height;
                this.f = R.dimen.child_mode_colletion_width;
                this.g = R.drawable.home_child_song_img_holder_with_bg_color_4342ff;
                return;
        }
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        final IListItem iListItem = (IListItem) this.c.get(baseRecyclerViewHolder.getAdapterPosition());
        if (iListItem != null) {
            switch (baseRecyclerViewHolder.getViewHolderType()) {
                case 0:
                    Header header = (Header) iListItem;
                    this.b = header.title;
                    ((MusicPatchWallTitle) baseRecyclerViewHolder.itemView).setupView(header.title);
                    return;
                case 1:
                    View view = baseRecyclerViewHolder.itemView;
                    if (Hardware.isBigScreen()) {
                        int i2 = this.d;
                        if (i2 == 1003 || i2 == 1002) {
                            ((ChildVideoCardView) view.findViewById(R.id.card_view)).updateTitleAndImage(iListItem, this.g);
                            return;
                        } else {
                            ((ChildStoryCardView) view.findViewById(R.id.card_view)).updateTitleAndImage(iListItem, this.g);
                            return;
                        }
                    } else {
                        ImageView imageView = (ImageView) view.findViewById(R.id.image_iv);
                        GlideUtils.bindImageViewWithRoundCorners(imageView, iListItem.getItemImageUrl(), DisplayUtils.dip2px(this.a, 16.0f), R.drawable.music_card_view_default, imageView.getWidth(), imageView.getHeight());
                        ((TextView) view.findViewById(R.id.title_tv)).setText(iListItem.getItemTitle());
                        view.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.station.adapter.StationListSquareAdapter.2
                            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                            public void onAvoidFastDoubleClick(View view2) {
                                IListItem iListItem2 = iListItem;
                                if (iListItem2 instanceof Station.Item) {
                                    Station.Item item = (Station.Item) iListItem2;
                                    PlayerApi.playStation(StationListSquareAdapter.this.a, item.getId(), item.getOrigin(), item.getTypeId());
                                }
                            }
                        });
                        return;
                    }
                default:
                    return;
            }
        }
    }
}
