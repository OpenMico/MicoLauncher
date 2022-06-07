package com.xiaomi.micolauncher.module.skill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.music.bean.Footer;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallFooter;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallTitle;
import com.xiaomi.micolauncher.module.skill.bean.SkillItem;
import com.xiaomi.micolauncher.module.skill.ui.SkillCategoryDetailListActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillCategoryListAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private String a;
    private Context b;
    private List<Object> c;

    public SkillCategoryListAdapter(Context context, String str) {
        this.b = context;
        this.a = str;
    }

    public void setDataList(List<Object> list) {
        this.c = list;
        notifyDataSetChanged();
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
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.skill.ui.adapter.SkillCategoryListAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    int itemViewType = SkillCategoryListAdapter.this.getItemViewType(i);
                    if (itemViewType == 0 || itemViewType == 1) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        Object obj;
        if (i >= this.c.size() || (obj = this.c.get(i)) == null) {
            return 2;
        }
        if (obj instanceof Header) {
            return 0;
        }
        return obj instanceof Footer ? 1 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case 0:
                view = new MusicPatchWallTitle(this.b);
                break;
            case 1:
                view = new MusicPatchWallFooter(this.b);
                break;
            case 2:
                view = LayoutInflater.from(this.b).inflate(R.layout.view_skill_category_list_item, viewGroup, false);
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
        Object obj = this.c.get(i);
        if (obj != null) {
            int itemViewType = baseRecyclerViewHolder.getItemViewType();
            if (itemViewType == 0) {
                ((MusicPatchWallTitle) baseRecyclerViewHolder.itemView).setupView(((Header) obj).title);
            } else if (itemViewType == 2) {
                final SkillItem skillItem = (SkillItem) obj;
                View view = baseRecyclerViewHolder.itemView;
                GlideUtils.bindImageViewWithRoundCorners(this.b, skillItem.iconUrl, (ImageView) view.findViewById(R.id.icon_iv), 42);
                ((TextView) view.findViewById(R.id.name_tv)).setText(skillItem.name);
                ((RatingBar) view.findViewById(R.id.rating_bar)).setRating(skillItem.rating);
                view.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.adapter.-$$Lambda$SkillCategoryListAdapter$fi1tDNl4nVF9W5GFHSnZxZXvfUA
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SkillCategoryListAdapter.this.a(skillItem, view2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SkillItem skillItem, View view) {
        SkillCategoryDetailListActivity.openSkillCategoryDetailListActivity(this.b, this.a, skillItem.id);
    }
}
