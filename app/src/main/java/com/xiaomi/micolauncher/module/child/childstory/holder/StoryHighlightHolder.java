package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.childstory.ChildSmallShadowCard;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class StoryHighlightHolder extends BaseChildStoryHolder {
    ChildSmallShadowCard a;
    ChildSmallShadowCard b;
    ChildSmallShadowCard c;
    ChildSmallShadowCard d;
    TextView e;
    ImageView f;
    TextView g;
    ImageView h;
    TextView i;
    ConstraintLayout j;
    private List<ChildStory.BlocksBean.ItemsBean> k;

    public StoryHighlightHolder(final Context context, View view) {
        super(context, view);
        this.a = (ChildSmallShadowCard) view.findViewById(R.id.shadow_card1);
        this.b = (ChildSmallShadowCard) view.findViewById(R.id.shadow_card2);
        this.c = (ChildSmallShadowCard) view.findViewById(R.id.shadow_card3);
        this.d = (ChildSmallShadowCard) view.findViewById(R.id.shadow_card4);
        this.e = (TextView) view.findViewById(R.id.everyday_content);
        this.f = (ImageView) view.findViewById(R.id.everyday_img);
        this.g = (TextView) view.findViewById(R.id.everyday_name);
        this.h = (ImageView) view.findViewById(R.id.common_more_iv);
        this.i = (TextView) view.findViewById(R.id.everyday_title);
        this.j = (ConstraintLayout) view.findViewById(R.id.everyday_card_cl);
        this.h.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryHighlightHolder$FBKEfv97ELtFSKwfCoaQkGq5_4g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryHighlightHolder.this.b(context, view2);
            }
        });
        this.j.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryHighlightHolder$xgoAuURvdfbxVDYZGBEGiOifh_s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryHighlightHolder.this.a(context, view2);
            }
        });
        this.h.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.j.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Context context, View view) {
        StationCategoryListActivity.openStationWithBlock(context, (ChildStory.BlocksBean) this.blocksBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        if (!ContainerUtil.isEmpty(this.k)) {
            ChildPlayUtil.playStory(context, this.k.get(0));
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.childstory.holder.BaseChildStoryHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        String title = this.storyBlock.getTitle();
        if (!TextUtils.isEmpty(title)) {
            this.i.setText(title);
        }
        this.k = this.storyBlock.getItems();
        if (ContainerUtil.getSize(this.k) >= 5) {
            a(this.k.get(0));
            this.a.setSmallShadowCardData(this.k.get(1), title);
            this.b.setSmallShadowCardData(this.k.get(2), title);
            this.c.setSmallShadowCardData(this.k.get(3), title);
            this.d.setSmallShadowCardData(this.k.get(4), title);
        }
    }

    private void a(ChildStory.BlocksBean.ItemsBean itemsBean) {
        this.e.setText(itemsBean.getShortDescription());
        GlideUtils.bindImageViewWithRoundCorners(this.context, itemsBean.getImages().getPoster().getUrl(), this.f, this.context.getResources().getDimensionPixelSize(R.dimen.story_small_shadow_card_corner_radius), (int) R.drawable.child_story_everyday_place_bg);
        this.g.setText(itemsBean.getTitle());
    }
}
