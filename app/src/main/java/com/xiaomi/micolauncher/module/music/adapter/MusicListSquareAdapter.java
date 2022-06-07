package com.xiaomi.micolauncher.module.music.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.micolauncher.module.music.bean.Footer;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallFooter;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallTitle;
import com.xiaomi.micolauncher.module.music.view.MusicSquareView;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicListSquareAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context a;
    private String b;
    private List<Object> c;
    private List<String> d = new ArrayList();
    private int e;
    private int f;
    private int g;

    public MusicListSquareAdapter(Context context) {
        this.a = context;
        this.e = context.getResources().getDimensionPixelOffset(R.dimen.common_radius);
        this.f = context.getResources().getDimensionPixelOffset(R.dimen.card_width);
        this.g = context.getResources().getDimensionPixelOffset(R.dimen.card_height);
    }

    public void setDataList(List<Object> list) {
        this.c = list;
        notifyDataSetChanged();
    }

    public void setBlackList(List<String> list) {
        this.d = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Object> list = this.c;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.music.adapter.MusicListSquareAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    int itemViewType = MusicListSquareAdapter.this.getItemViewType(i);
                    if (itemViewType == 0 || itemViewType == 3) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        Object obj = this.c.get(i);
        if (obj == null) {
            return 1;
        }
        if (obj instanceof Header) {
            return 0;
        }
        if (obj instanceof Footer) {
            return 3;
        }
        return (!(obj instanceof SquareItem) || ((SquareItem) obj).type != 1) ? 1 : 4;
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
                view = new MusicSquareView(this.a);
                break;
            case 2:
            default:
                view = null;
                break;
            case 3:
                view = new MusicPatchWallFooter(this.a);
                break;
            case 4:
                view = LayoutInflater.from(this.a).inflate(R.layout.view_music_group, viewGroup, false);
                break;
        }
        BaseRecyclerViewHolder baseRecyclerViewHolder = new BaseRecyclerViewHolder(view);
        baseRecyclerViewHolder.setViewHolderType(i);
        return baseRecyclerViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        final String str;
        final String str2;
        Object obj = this.c.get(i);
        if (obj != null) {
            int itemViewType = baseRecyclerViewHolder.getItemViewType();
            if (itemViewType != 4) {
                switch (itemViewType) {
                    case 0:
                        Header header = (Header) obj;
                        this.b = header.title;
                        ((MusicPatchWallTitle) baseRecyclerViewHolder.itemView).setupView(header.title);
                        return;
                    case 1:
                        break;
                    default:
                        return;
                }
            }
            View view = baseRecyclerViewHolder.itemView;
            final ImageView imageView = (ImageView) view.findViewById(R.id.image_iv);
            final TextView textView = (TextView) view.findViewById(R.id.title_tv);
            View findViewById = view.findViewById(R.id.black_fore);
            if (obj instanceof SquareItem) {
                SquareItem squareItem = (SquareItem) obj;
                String str3 = squareItem.title;
                String str4 = squareItem.imageUrl;
                str = squareItem.target;
                Context context = this.a;
                GlideUtils.bindImageViewWithRoundUseContext(context, str4, imageView, UiUtils.getSize(context, R.dimen.corner_radius_music_list_square_item), R.drawable.app_icon_placeholder, this.f, this.g);
                textView.setText(str3);
                if (LocalPlayerManager.getInstance().isBlack(str3)) {
                    findViewById.setVisibility(0);
                } else if (findViewById.getVisibility() == 0) {
                    findViewById.setVisibility(8);
                }
                str2 = str3;
            } else {
                PatchWall.Item item = (PatchWall.Item) obj;
                str2 = item.title;
                L.homepage.i("onBindViewHolder PatchWall : %s", str2);
                String itemImageUrl = item.getItemImageUrl();
                str = item.target;
                GlideUtils.bindImageView(imageView, itemImageUrl, (int) R.drawable.bg_over_lay_view_music_group, UiUtils.getSize(this.a, R.dimen.recommend_title_height), new ImageViewTarget<Bitmap>(imageView) { // from class: com.xiaomi.micolauncher.module.music.adapter.MusicListSquareAdapter.2
                    /* renamed from: a */
                    public void setResource(@Nullable Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        textView.setText(str2);
                    }
                }, this.e);
            }
            view.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.music.adapter.MusicListSquareAdapter.3
                @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                public void onAvoidFastDoubleClick(View view2) {
                    if (LocalPlayerManager.getInstance().isBlack(str2)) {
                        SchemaManager.handleSchema(MusicListSquareAdapter.this.a, HomepageSchemaHandler.PATH_BLACKLIST);
                    } else if (!TextUtils.isEmpty(str)) {
                        PreferenceUtils.setSettingString(MusicListSquareAdapter.this.a, MusicGroupListActivity.GROUP_TITLE, str2.contains(MusicGroupListActivity.SPECIAL_SYMBOL) ? str2 : "");
                        SchemaManager.handleSchema(MusicListSquareAdapter.this.a, str);
                    }
                }
            });
        }
    }
}
