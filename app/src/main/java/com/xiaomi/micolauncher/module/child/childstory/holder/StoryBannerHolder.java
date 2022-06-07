package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.childstory.ChildStoryDataManager;
import com.xiaomi.micolauncher.module.child.view.BannerView;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import com.xiaomi.micolauncher.module.station.StationGroupListActivity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class StoryBannerHolder extends BaseChildStoryHolder {
    public static final String COVER_PAYLOADS = "cover_payloads";
    public static final int PAYLOAD_COLLECT = 1;
    public static final int PAYLOAD_RECENT = 0;
    BannerView a;
    FrameLayout b;
    FrameLayout c;
    ImageView d;
    ImageView e;
    ImageView f;
    ImageView g;
    ImageView h;
    ImageView i;
    private List<ChildStory.BlocksBean.ItemsBean> j;
    private IListItem k;
    private List<IListItem> l = new ArrayList();

    public StoryBannerHolder(final Context context, View view) {
        super(context, view);
        this.a = (BannerView) view.findViewById(R.id.banner_switcher);
        this.b = (FrameLayout) view.findViewById(R.id.story_history_recent);
        this.c = (FrameLayout) view.findViewById(R.id.story_history_collect);
        this.d = (ImageView) view.findViewById(R.id.story_history_recent_inner);
        this.e = (ImageView) view.findViewById(R.id.story_history_collect_inner);
        this.f = (ImageView) view.findViewById(R.id.story_classify_chinese);
        this.g = (ImageView) view.findViewById(R.id.story_classify_english);
        this.h = (ImageView) view.findViewById(R.id.story_classify_sleep);
        this.i = (ImageView) view.findViewById(R.id.story_classify_all);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$TN2iTMCkTy1BXlwnJDjnG7fxdT4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryBannerHolder.b(context, view2);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$mBlOhFSHiKPuLxFUzt-QSVTKUnw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryBannerHolder.a(context, view2);
            }
        });
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$fMbs3VR0FU8GYUlTRu_ERu-ahj0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryBannerHolder.this.d(view2);
            }
        });
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$jh8HOTzKQ4_cMPO2B_vrk3EiBDY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryBannerHolder.this.c(view2);
            }
        });
        this.h.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$FWbKeaZd1Kxz1EyxoydnLHVBWdk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryBannerHolder.this.b(view2);
            }
        });
        this.i.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$LtuYVNNPDY2Pg84dPUXhqGlQG90
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryBannerHolder.this.a(view2);
            }
        });
        this.a.setOnBannerClickListener(new BannerView.OnBannerClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryBannerHolder$BVXb7TxgwEdNL09kHsp18EHECxQ
            @Override // com.xiaomi.micolauncher.module.child.view.BannerView.OnBannerClickListener
            public final void onBannerClick(int i) {
                StoryBannerHolder.this.a(context, i);
            }
        });
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.f.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.g.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.h.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.i.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Context context, View view) {
        if (CommonUtil.checkHasNetworkAndShowToast(context)) {
            StationCategoryListActivity.openStationWithTitleCollection(context, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, View view) {
        if (CommonUtil.checkHasNetworkAndShowToast(context)) {
            StationCategoryListActivity.openStationWithTitleRecent(context, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(View view) {
        a(1002, R.string.story_chinese);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        a(1003, R.string.story_english);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        a(1001, R.string.story_sleep);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a(0, R.string.story_all);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, int i) {
        if (CommonUtil.checkHasNetworkAndShowToast(context)) {
            L.childContent.i("click banner %d    %d", Integer.valueOf(i), Integer.valueOf(this.j.size()));
            this.k = this.j.get(i);
            IListItem iListItem = this.k;
            if (iListItem != null && !TextUtils.isEmpty(iListItem.getItemTarget())) {
                L.childContent.i("click banner target  %s", this.k.getItemTarget());
                ChildPlayUtil.playStory(context, this.k);
            }
        }
    }

    private void a(int i, int i2) {
        StationGroupListActivity.openStationGroupListActivity(this.itemView.getContext(), i, true);
    }

    @Override // com.xiaomi.micolauncher.module.child.childstory.holder.BaseChildStoryHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        if (ContainerUtil.hasData(this.storyBlock.getItems())) {
            this.j = this.storyBlock.getItems();
            this.l.clear();
            this.l.addAll(this.j);
            this.a.setData(this.l);
        }
        bindStoryCollectInner();
        bindStoryRecentInner();
    }

    public void bindStoryRecentInner() {
        GlideUtils.bindImageViewWithRoundUseContext(this.context, ChildStoryDataManager.getManager().getRecentCover(), this.d, this.context.getResources().getDimensionPixelSize(R.dimen.story_header_cover_radius), R.drawable.child_story_recent_place_bg);
    }

    public void bindStoryCollectInner() {
        String collectCover = ChildStoryDataManager.getManager().getCollectCover();
        L.childContent.i("load story collect cover %s", collectCover);
        if (!TextUtils.isEmpty(collectCover)) {
            GlideUtils.bindImageViewWithRoundUseContext(this.context, ChildStoryDataManager.getManager().getCollectCover(), this.e, this.context.getResources().getDimensionPixelSize(R.dimen.story_header_cover_radius), R.drawable.child_story_collect_place_bg);
        } else {
            this.e.setImageResource(R.drawable.child_story_collect_place_bg);
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void removeAllMessages() {
        this.a.removeMessages();
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void startAllMessages() {
        this.a.startMessages();
    }
}
