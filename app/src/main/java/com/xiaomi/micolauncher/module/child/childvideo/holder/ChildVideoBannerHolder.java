package com.xiaomi.micolauncher.module.child.childvideo.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.module.child.ChildVideoClickHelper;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoGroupListActivity;
import com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.child.view.BannerView;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoBannerHolder extends BaseChildVideoHolder {
    public static final String COVER_PAYLOADS = "cover_payloads";
    public static final String VIP_TITLE_PAYLOADS = "vip_title_payloads";
    BannerView a;
    ImageView b;
    FrameLayout c;
    ImageView d;
    ImageView e;
    ImageView f;
    ImageView g;
    ImageView h;
    TextView i;
    TextView j;
    private List<IListItem> k = new ArrayList();

    public ChildVideoBannerHolder(final Context context, final View view) {
        super(context, view);
        this.a = (BannerView) view.findViewById(R.id.banner_switcher);
        this.b = (ImageView) view.findViewById(R.id.child_video_vip);
        this.c = (FrameLayout) view.findViewById(R.id.child_video_history);
        this.d = (ImageView) view.findViewById(R.id.child_video_history_inner);
        this.e = (ImageView) view.findViewById(R.id.child_video_cartoon);
        this.f = (ImageView) view.findViewById(R.id.child_video_film);
        this.g = (ImageView) view.findViewById(R.id.child_video_child_song);
        this.h = (ImageView) view.findViewById(R.id.child_video_all);
        this.i = (TextView) view.findViewById(R.id.vip_title);
        this.j = (TextView) view.findViewById(R.id.vip_subtitle);
        this.i.getPaint().setShader(new LinearGradient(0.0f, 0.0f, 0.0f, this.i.getPaint().getTextSize(), context.getColor(R.color.color_FFE620), context.getColor(R.color.color_FFE4B5), Shader.TileMode.CLAMP));
        this.j.getPaint().setShader(new LinearGradient(0.0f, 0.0f, 0.0f, this.j.getPaint().getTextSize(), context.getColor(R.color.color_FFE620), context.getColor(R.color.color_FFE4B5), Shader.TileMode.CLAMP));
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$jEUkiLbKzVGAcNeeNVp27BpMQbY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoBannerHolder.a(view, view2);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$vmgFudz0-U9DqrRCYURQa6rRP9s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoBannerHolder.a(context, view2);
            }
        });
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$9rZkGEqRAZy3Uc-qhGG56365Vlo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoBannerHolder.this.d(view2);
            }
        });
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$eV6ES5nZaKq0odgZoBv_XZ9zezQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoBannerHolder.this.c(view2);
            }
        });
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$dYuQ6WdYXznmx1-dKyarShWIfvc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoBannerHolder.this.b(view2);
            }
        });
        this.h.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$GhG0HaYp39s2x4xX-TbFg7IwIrI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoBannerHolder.this.a(view2);
            }
        });
        this.a.setOnBannerClickListener(new BannerView.OnBannerClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$UOeAiL20Aq7EAjRHx-szbfxNbj0
            @Override // com.xiaomi.micolauncher.module.child.view.BannerView.OnBannerClickListener
            public final void onBannerClick(int i) {
                ChildVideoBannerHolder.this.a(context, i);
            }
        });
        this.a.setOnBannerChangeListener(new BannerView.OnBannerChangeListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoBannerHolder$lvOWo-uYIjbk351JqrMwaoyU9X0
            @Override // com.xiaomi.micolauncher.module.child.view.BannerView.OnBannerChangeListener
            public final void onBannerChange(int i) {
                ChildVideoBannerHolder.this.a(i);
            }
        });
        this.e.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.g.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.f.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.h.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view, View view2) {
        ChildStatHelper.resetPayOriginId();
        Intent intent = new Intent(view.getContext(), MiTvVipActivity.class);
        intent.putExtra(MiTvVipActivity.INTENT_EXTRA_MI_TV_TYPE, ChildVideo.MiTvType.CHILD_VIDEO);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, View view) {
        if (CommonUtil.checkHasNetworkAndShowToast(context)) {
            StationCategoryListActivity.openStationWithVideoRecent(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(View view) {
        a(BaseChildVideoHolder.CATEGORY_ID_VIDEO_CARTOON, R.string.video_cartoon);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        a(1778, R.string.video_song);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        a(1779, R.string.video_film);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a(0, R.string.video_all);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, int i) {
        L.childContent.i("click banner %d    %d", Integer.valueOf(i), Integer.valueOf(this.k.size()));
        ChildVideo.ItemsBean itemsBean = (ChildVideo.ItemsBean) this.k.get(i);
        ChildStatHelper.recordKidVideoDiscoveryClick(TrackWidget.KID_VIDEO_DISCOVERY, itemsBean, "banner");
        ChildVideoClickHelper.clickBanner(context, itemsBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i) {
        ChildStatHelper.recordKidVideoDiscoveryExpose(TrackWidget.KID_VIDEO_DISCOVERY, this.k.get(i), "banner");
    }

    private void a(int i, int i2) {
        ChildVideoGroupListActivity.openChildVideoGroupListActivity(this.itemView.getContext(), i, true);
    }

    @Override // com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        if (ContainerUtil.hasData(iBlockBean.getListItems())) {
            this.k.clear();
            for (IListItem iListItem : iBlockBean.getListItems()) {
                ChildVideo.ItemsBean itemsBean = (ChildVideo.ItemsBean) iListItem;
                if (!TextUtils.isEmpty(itemsBean.getItemImageUrl())) {
                    this.k.add(itemsBean);
                }
                if (itemsBean.isVipDisplayItem()) {
                    bindVipTitle(itemsBean);
                }
            }
            this.a.setData(this.k);
        }
    }

    public void bindVipTitle(ChildVideo.ItemsBean itemsBean) {
        if (itemsBean != null) {
            this.i.setText(itemsBean.getTitle());
            this.j.setText(itemsBean.getSubtitle());
        }
    }

    public void bindVideoHistoryInner(String str) {
        if (!TextUtils.isEmpty(str)) {
            GlideUtils.bindImageViewWithRoundCorners(this.context, str, this.d, this.context.getResources().getDimensionPixelSize(R.dimen.story_header_cover_radius), (int) R.drawable.child_video_history_place_bg);
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
