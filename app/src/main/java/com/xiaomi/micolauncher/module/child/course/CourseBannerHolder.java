package com.xiaomi.micolauncher.module.child.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.module.child.ChildVideoClickHelper;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity;
import com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.homepage.view.FadeInCardView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class CourseBannerHolder extends BaseChildVideoHolder {
    TextView a;
    TextView b;
    FadeInCardView c;
    protected int currentIndex;
    ImageView d;
    private a e = new a(this);
    private List<ChildVideo.ItemsBean> f;
    private ChildVideo.ItemsBean g;
    private ChildVideo.ItemsBean h;

    public CourseBannerHolder(final Context context, final View view) {
        super(context, view);
        this.a = (TextView) view.findViewById(R.id.vip_subtitle);
        this.b = (TextView) view.findViewById(R.id.vip_title);
        this.c = (FadeInCardView) view.findViewById(R.id.course_banner);
        this.d = (ImageView) view.findViewById(R.id.vip_img);
        this.a.getPaint().setShader(new LinearGradient(0.0f, 0.0f, 0.0f, this.a.getPaint().getTextSize(), context.getColor(R.color.color_FFE620), context.getColor(R.color.color_FFE4B5), Shader.TileMode.CLAMP));
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$CourseBannerHolder$VbCZcxYdFMzELyekj2CP0MISz1A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CourseBannerHolder.this.a(context, view2);
            }
        });
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$CourseBannerHolder$kUnN8NqxHwxOcsHCv9QKMxTFfGQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CourseBannerHolder.a(view, view2);
            }
        });
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        ChildStatHelper.recordTeachClassDiscoveryClick(this.g);
        ChildVideoClickHelper.clickBanner(context, this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view, View view2) {
        Intent intent = new Intent(view.getContext(), MiTvVipActivity.class);
        ChildVideo.MiTvType miTvType = ChildVideoDataManager.getManager().getMiTvType();
        if (miTvType == null || ChildVideo.MiTvType.CHILD_VIDEO.equals(miTvType)) {
            miTvType = ChildVideo.MiTvType.COURSE_PRIMARY;
        }
        intent.putExtra(MiTvVipActivity.INTENT_EXTRA_MI_TV_TYPE, miTvType);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Override // com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        if (!ContainerUtil.isEmpty(iBlockBean.getListItems())) {
            this.f = new ArrayList();
            for (IListItem iListItem : iBlockBean.getListItems()) {
                ChildVideo.ItemsBean itemsBean = (ChildVideo.ItemsBean) iListItem;
                if (!itemsBean.isFilterItem() && !TextUtils.isEmpty(itemsBean.getItemImageUrl())) {
                    this.f.add(itemsBean);
                }
                if (itemsBean.isVipDisplayItem()) {
                    this.h = itemsBean;
                }
            }
            b();
            a();
            this.e.removeCallbacksAndMessages(null);
            this.e.sendEmptyMessageDelayed(123, RtspMediaSource.DEFAULT_TIMEOUT_MS);
        }
    }

    private void a() {
        ChildVideo.ItemsBean itemsBean = this.h;
        if (itemsBean != null) {
            this.b.setText(itemsBean.getTitle());
            this.a.setText(this.h.getSubtitle());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (ContainerUtil.hasData(this.f)) {
            List<ChildVideo.ItemsBean> list = this.f;
            this.g = list.get(this.currentIndex % ContainerUtil.getSize(list));
            this.c.refreshPage(this.g, R.drawable.home_kids_banner_place);
            ChildStatHelper.recordTeachClassDiscoveryExpose(this.g);
        }
    }

    /* loaded from: classes3.dex */
    static class a extends Handler {
        private final WeakReference<CourseBannerHolder> a;

        a(CourseBannerHolder courseBannerHolder) {
            this.a = new WeakReference<>(courseBannerHolder);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            CourseBannerHolder courseBannerHolder = this.a.get();
            if (courseBannerHolder != null) {
                courseBannerHolder.currentIndex++;
                courseBannerHolder.b();
                sendEmptyMessageDelayed(123, RtspMediaSource.DEFAULT_TIMEOUT_MS);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void removeAllMessages() {
        super.removeAllMessages();
        this.e.removeCallbacksAndMessages(null);
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void startAllMessages() {
        super.startAllMessages();
        this.e.sendEmptyMessageDelayed(123, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }
}
