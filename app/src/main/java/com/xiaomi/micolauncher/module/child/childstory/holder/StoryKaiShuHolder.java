package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.module.child.childstory.ChildSmallCard;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class StoryKaiShuHolder extends BaseChildStoryHolder {
    ChildSmallCard a;
    ChildSmallCard b;
    ChildSmallCard c;
    ChildSmallCard d;
    ChildSmallCard e;
    ChildSmallCard f;
    ImageView g;
    TextView h;
    private ChildSmallCard[] i;

    public StoryKaiShuHolder(final Context context, View view) {
        super(context, view);
        this.a = (ChildSmallCard) view.findViewById(R.id.kaishu_card1);
        this.b = (ChildSmallCard) view.findViewById(R.id.kaishu_card2);
        this.c = (ChildSmallCard) view.findViewById(R.id.kaishu_card3);
        this.d = (ChildSmallCard) view.findViewById(R.id.kaishu_card4);
        this.e = (ChildSmallCard) view.findViewById(R.id.kaishu_card5);
        this.f = (ChildSmallCard) view.findViewById(R.id.kaishu_card6);
        this.g = (ImageView) view.findViewById(R.id.common_more_iv);
        this.h = (TextView) view.findViewById(R.id.kaishu_title);
        this.i = new ChildSmallCard[]{this.a, this.b, this.c, this.d, this.e, this.f};
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryKaiShuHolder$aGU01joqFPnUDmaOzHT9IiNVzMs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryKaiShuHolder.this.a(context, view2);
            }
        });
        this.g.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        StationCategoryListActivity.openStationWithBlock(context, (ChildStory.BlocksBean) this.blocksBean);
    }

    @Override // com.xiaomi.micolauncher.module.child.childstory.holder.BaseChildStoryHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        if (iBlockBean != null) {
            super.setBlocksBean(iBlockBean);
            String title = this.storyBlock.getTitle();
            if (!TextUtils.isEmpty(title)) {
                this.h.setText(title);
            }
            List<ChildStory.BlocksBean.ItemsBean> items = this.storyBlock.getItems();
            int min = Math.min(ContainerUtil.getSize(items), 6);
            for (int i = 0; i < min; i++) {
                this.i[i].setSmallCardData(this.context, items.get(i), 1, title, false);
            }
        }
    }
}
