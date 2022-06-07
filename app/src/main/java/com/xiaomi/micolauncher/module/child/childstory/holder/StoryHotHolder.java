package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class StoryHotHolder extends BaseChildStoryHolder {
    ImageView a;
    ImageView b;
    ImageView c;
    TextView d;
    TextView e;
    TextView f;
    ImageView g;
    TextView h;
    private ChildStory.BlocksBean.ItemsBean i;
    private ChildStory.BlocksBean.ItemsBean j;
    private ChildStory.BlocksBean.ItemsBean k;

    @SuppressLint({"CheckResult"})
    public StoryHotHolder(final Context context, View view) {
        super(context, view);
        this.a = (ImageView) view.findViewById(R.id.hot_rank1_iv);
        this.b = (ImageView) view.findViewById(R.id.hot_rank2_iv);
        this.c = (ImageView) view.findViewById(R.id.hot_rank3_iv);
        this.d = (TextView) view.findViewById(R.id.hot_rank1_tv);
        this.e = (TextView) view.findViewById(R.id.hot_rank2_tv);
        this.f = (TextView) view.findViewById(R.id.hot_rank3_tv);
        this.g = (ImageView) view.findViewById(R.id.common_more_iv);
        this.h = (TextView) view.findViewById(R.id.hot_title);
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryHotHolder$3MXfEufA-ZmZsn-IutlYccPKTFg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StoryHotHolder.this.c(context, obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryHotHolder$r7OTEX25YbZJmjqB3NkLfL9osQ0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StoryHotHolder.this.b(context, obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryHotHolder$71I7kRlpL1mZdMvF4ZzcdtXMdx4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StoryHotHolder.this.a(context, obj);
            }
        });
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryHotHolder$m-s6qqwAmcK6knQiO-qlvgMa5A8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryHotHolder.this.a(context, view2);
            }
        });
        this.g.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Context context, Object obj) throws Exception {
        ChildStory.BlocksBean.ItemsBean itemsBean;
        if (CommonUtil.checkHasNetworkAndShowToast(context) && (itemsBean = this.i) != null && !TextUtils.isEmpty(itemsBean.getTarget())) {
            ChildPlayUtil.playStory(context, this.i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Context context, Object obj) throws Exception {
        ChildStory.BlocksBean.ItemsBean itemsBean;
        if (CommonUtil.checkHasNetworkAndShowToast(context) && (itemsBean = this.j) != null && !TextUtils.isEmpty(itemsBean.getTarget())) {
            ChildPlayUtil.playStory(context, this.j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, Object obj) throws Exception {
        ChildStory.BlocksBean.ItemsBean itemsBean;
        if (CommonUtil.checkHasNetworkAndShowToast(context) && (itemsBean = this.k) != null && !TextUtils.isEmpty(itemsBean.getTarget())) {
            ChildPlayUtil.playStory(context, this.k);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        StationCategoryListActivity.openStationWithBlock(context, (ChildStory.BlocksBean) this.blocksBean);
    }

    @Override // com.xiaomi.micolauncher.module.child.childstory.holder.BaseChildStoryHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        if (!TextUtils.isEmpty(this.storyBlock.getTitle())) {
            this.h.setText(this.storyBlock.getTitle());
        }
        List<ChildStory.BlocksBean.ItemsBean> items = this.storyBlock.getItems();
        if (ContainerUtil.getSize(items) >= 3) {
            this.i = items.get(0);
            this.j = items.get(1);
            this.k = items.get(2);
            GlideUtils.bindImageViewCircleUseContext(this.context, this.i.getImages().getPoster().getUrl(), this.a, R.drawable.child_story_hot_1_place_bg);
            GlideUtils.bindImageViewCircleUseContext(this.context, this.j.getImages().getPoster().getUrl(), this.b, R.drawable.child_story_hot_2_place_bg);
            GlideUtils.bindImageViewCircleUseContext(this.context, this.k.getImages().getPoster().getUrl(), this.c, R.drawable.child_story_hot_3_place_bg);
            this.d.setText(this.i.getTitle());
            this.e.setText(this.j.getTitle());
            this.f.setText(this.k.getTitle());
        }
    }
}
