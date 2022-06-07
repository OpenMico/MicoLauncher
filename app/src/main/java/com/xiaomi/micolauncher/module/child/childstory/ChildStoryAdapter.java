package com.xiaomi.micolauncher.module.child.childstory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.module.child.childstory.holder.BaseChildStoryHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryAgeHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryBannerHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryCommonHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryHighlightHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryHotHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryKaiShuHolder;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildStoryAdapter extends BaseAdapter<BaseChildStoryHolder> {
    public static final int ITEM_STABLE_SIZE_MIN = 5;
    private ChildStory a;
    private int b;
    private Context c;
    private List<BaseChildStoryHolder> d = new ArrayList();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
        onBindViewHolder((BaseChildStoryHolder) viewHolder, i, (List<Object>) list);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ChildStoryAdapter(Context context) {
        this.c = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseChildStoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                this.b++;
                return new StoryBannerHolder(this.c, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_story_header, viewGroup, false));
            case 1:
                this.b++;
                return new StoryHighlightHolder(this.c, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_everyday, viewGroup, false));
            case 2:
                this.b++;
                return new StoryAgeHolder(this.c, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_age, viewGroup, false));
            case 3:
                this.b++;
                return new StoryKaiShuHolder(this.c, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_kaishu, viewGroup, false));
            case 4:
                this.b++;
                return new StoryHotHolder(this.c, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_hot_anim, viewGroup, false));
            default:
                return new StoryCommonHolder(this.c, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_common, viewGroup, false), false);
        }
    }

    public void onBindViewHolder(@NonNull BaseChildStoryHolder baseChildStoryHolder, int i) {
        if (baseChildStoryHolder instanceof StoryCommonHolder) {
            ((StoryCommonHolder) baseChildStoryHolder).setBackground(i);
        }
        if (!ContainerUtil.isEmpty(this.a.getBlocks())) {
            baseChildStoryHolder.setBlocksBean(this.a.getBlocks().get(i));
        }
    }

    public void onBindViewHolder(@NonNull BaseChildStoryHolder baseChildStoryHolder, int i, @NonNull List<Object> list) {
        if (this.a != null) {
            if (ContainerUtil.isEmpty(list)) {
                onBindViewHolder(baseChildStoryHolder, i);
            } else if (baseChildStoryHolder instanceof StoryBannerHolder) {
                switch (((Bundle) list.get(0)).getInt("cover_payloads")) {
                    case 0:
                        ((StoryBannerHolder) baseChildStoryHolder).bindStoryRecentInner();
                        return;
                    case 1:
                        ((StoryBannerHolder) baseChildStoryHolder).bindStoryCollectInner();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ChildStory childStory = this.a;
        return Math.max(5, ContainerUtil.getSize(childStory == null ? ContainerUtil.getEmptyList() : childStory.getBlocks()));
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ChildStory childStory = this.a;
        if (childStory == null || ContainerUtil.isOutOfBound(i, childStory.getBlocks())) {
            return i;
        }
        String name = this.a.getBlocks().get(i).getBlockUiType().getName();
        char c = 65535;
        switch (name.hashCode()) {
            case -1249085860:
                if (name.equals(ChildStory.BLOCK_KAISHU)) {
                    c = 1;
                    break;
                }
                break;
            case -88283376:
                if (name.equals(ChildStory.BLOCK_AGE)) {
                    c = 3;
                    break;
                }
                break;
            case -88276386:
                if (name.equals(ChildStory.BLOCK_HOT)) {
                    c = 2;
                    break;
                }
                break;
            case 178285061:
                if (name.equals(ChildStory.BLOCK_HIGHLIGHT)) {
                    c = 4;
                    break;
                }
                break;
            case 1127998723:
                if (name.equals("block_grid_rich")) {
                    c = 5;
                    break;
                }
                break;
            case 1848700429:
                if (name.equals(ChildStory.BLOCK_RECOMMEND)) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
                return 1;
            case 5:
                return 5;
            default:
                return super.getItemViewType(i);
        }
    }

    public void setData(ChildStory childStory) {
        if (childStory != null) {
            this.a = childStory;
            ChildStory.BlocksBean blocksBean = new ChildStory.BlocksBean();
            ChildStory.BlocksBean.BlockUiTypeBean blockUiTypeBean = new ChildStory.BlocksBean.BlockUiTypeBean();
            blockUiTypeBean.setName(ChildStory.BLOCK_AGE);
            blocksBean.setBlockUiType(blockUiTypeBean);
            childStory.getBlocks().add(2, blocksBean);
            notifyDataSetChanged();
        }
    }

    public void onViewDetachedFromWindow(@NonNull BaseChildStoryHolder baseChildStoryHolder) {
        this.d.remove(baseChildStoryHolder);
    }

    public void onViewAttachedToWindow(@NonNull BaseChildStoryHolder baseChildStoryHolder) {
        this.d.add(baseChildStoryHolder);
    }

    public void removeMessages() {
        for (BaseChildStoryHolder baseChildStoryHolder : this.d) {
            baseChildStoryHolder.removeAllMessages();
        }
    }

    public void startMessages() {
        for (BaseChildStoryHolder baseChildStoryHolder : this.d) {
            baseChildStoryHolder.startAllMessages();
        }
    }
}
