package com.xiaomi.micolauncher.module.homepage.viewholder.new_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.activity.VideoListActivity;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoCategoryViewHolder extends BaseViewHolder {
    private static int c;
    private static int d;
    private static int e;
    private final ImageView[] a = new ImageView[4];
    private List<VideoTabInfo.CategoryInfo> b;

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    public VideoCategoryViewHolder(View view) {
        super(view);
        this.a[0] = (ImageView) view.findViewById(R.id.category_image_1);
        this.a[1] = (ImageView) view.findViewById(R.id.category_image_2);
        this.a[2] = (ImageView) view.findViewById(R.id.category_image_3);
        this.a[3] = (ImageView) view.findViewById(R.id.category_image_4);
        if (c == 0) {
            c = UiUtils.getEntertainmentCornerRadius(view.getContext());
        }
        if (d == 0 || e == 0) {
            e = view.getContext().getResources().getDimensionPixelSize(R.dimen.video_category_item_width);
            d = view.getContext().getResources().getDimensionPixelSize(R.dimen.video_category_item_height);
        }
    }

    public void bind(VideoTabInfo.RecommendCategory recommendCategory) {
        if (recommendCategory == null) {
            this.b = null;
            a();
            return;
        }
        this.b = recommendCategory.getCategoryList();
        List<VideoTabInfo.CategoryInfo> list = this.b;
        if (list == null) {
            a();
            return;
        }
        int i = 0;
        for (VideoTabInfo.CategoryInfo categoryInfo : list) {
            this.a[i].setContentDescription(categoryInfo.getName());
            GlideUtils.bindImageViewWithRoundUseContext(this.itemView.getContext().getApplicationContext(), categoryInfo.getIconUrl(), this.a[i], c, R.drawable.video_category_img_loading, e, d);
            i++;
        }
    }

    private void a() {
        for (int i = 0; i < 4; i++) {
            GlideUtils.bindImageView(this.a[i], (int) R.drawable.video_category_img_loading);
        }
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    public void initInMain() {
        super.initInMain();
        for (final int i = 0; i < 4; i++) {
            AnimLifecycleManager.repeatOnAttach(this.a[i], MicoAnimConfigurator4EntertainmentAndApps.get());
            RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoCategoryViewHolder$Mr4RzMH7wSctYKjzd7D3XoIJdoU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoCategoryViewHolder.this.a(i, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Object obj) throws Exception {
        List<VideoTabInfo.CategoryInfo> list;
        if (CommonUtil.checkHasNetworkAndShowToast(this.itemView.getContext()) && (list = this.b) != null && i < list.size()) {
            Context context = this.itemView.getContext();
            Intent intent = new Intent(context, VideoListActivity.class);
            intent.putExtra(VideoListActivity.TAB_DATA, this.b.get(i));
            ActivityLifeCycleManager.startActivityQuietly(context, intent);
        }
    }
}
