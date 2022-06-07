package com.xiaomi.micolauncher.module.homepage.viewholder.homekids;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.child.BaseChildHolder;
import com.xiaomi.micolauncher.module.child.ChildVideoClickHelper;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.homepage.activity.ChildCourseActivity;
import com.xiaomi.micolauncher.module.homepage.activity.ChildSongActivity;
import com.xiaomi.micolauncher.module.homepage.activity.ChildStoryActivity;
import com.xiaomi.micolauncher.module.homepage.activity.ChildVideoActivity;
import com.xiaomi.micolauncher.module.homepage.view.FadeInCardView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class HomeKidsBannerHolder extends BaseChildHolder {
    FadeInCardView a;
    private a b = new a(this);
    private List<ChildVideo.ItemsBean> c;
    protected int currentIndex;
    private ChildVideo.ItemsBean d;

    public HomeKidsBannerHolder(final Context context, View view) {
        super(context, view);
        view.findViewById(R.id.kids_course).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsBannerHolder$NZFQSL2fyZb5vRrBUBvrIDuCXMY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HomeKidsBannerHolder.e(context, view2);
            }
        });
        view.findViewById(R.id.kids_cartoon).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsBannerHolder$QilE2PJW97p-Ud-VXpUIBrzXO3w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HomeKidsBannerHolder.d(context, view2);
            }
        });
        view.findViewById(R.id.kids_song).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsBannerHolder$Y5_5JyyZbItfAnVqLHT6ZNTadCo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HomeKidsBannerHolder.c(context, view2);
            }
        });
        view.findViewById(R.id.kids_story).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsBannerHolder$amhWojGd1p5jPJ7yVgCN9Z7_5FU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HomeKidsBannerHolder.b(context, view2);
            }
        });
        this.a = (FadeInCardView) view.findViewById(R.id.kids_banner);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsBannerHolder$n5urx5ghQOZ80duQOAHr6IwDo6A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HomeKidsBannerHolder.this.a(context, view2);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.a, MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.kids_course), MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.kids_cartoon), MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.kids_song), MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.kids_story), MicoAnimConfigurator4EntertainmentAndApps.get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(Context context, View view) {
        ChildStatHelper.recordChildTabCardClick(context.getString(R.string.child_course));
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, ChildCourseActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(Context context, View view) {
        ChildStatHelper.recordChildTabCardClick(context.getString(R.string.child_video));
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, ChildVideoActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Context context, View view) {
        ChildStatHelper.recordChildTabCardClick(context.getString(R.string.child_song));
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, ChildSongActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Context context, View view) {
        ChildStatHelper.recordChildTabCardClick(context.getString(R.string.child_story));
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, ChildStoryActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        ChildStatHelper.recordChildTabCardClick("banner" + this.currentIndex);
        ChildVideoClickHelper.clickBanner(context, this.d);
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        if (!ContainerUtil.isEmpty(iBlockBean.getListItems())) {
            this.c = new ArrayList();
            for (IListItem iListItem : iBlockBean.getListItems()) {
                ChildVideo.ItemsBean itemsBean = (ChildVideo.ItemsBean) iListItem;
                if (!itemsBean.isFilterItem() && !TextUtils.isEmpty(itemsBean.getItemImageUrl())) {
                    this.c.add(itemsBean);
                }
            }
            a();
            this.b.removeCallbacksAndMessages(null);
            this.b.sendEmptyMessageDelayed(123, RtspMediaSource.DEFAULT_TIMEOUT_MS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (ContainerUtil.hasData(this.c)) {
            List<ChildVideo.ItemsBean> list = this.c;
            this.d = list.get(this.currentIndex % ContainerUtil.getSize(list));
            this.a.refreshPage(this.d, R.drawable.home_kids_banner_place);
        }
    }

    /* loaded from: classes3.dex */
    static class a extends Handler {
        private final WeakReference<HomeKidsBannerHolder> a;

        a(HomeKidsBannerHolder homeKidsBannerHolder) {
            this.a = new WeakReference<>(homeKidsBannerHolder);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            HomeKidsBannerHolder homeKidsBannerHolder = this.a.get();
            if (homeKidsBannerHolder != null) {
                homeKidsBannerHolder.currentIndex++;
                homeKidsBannerHolder.a();
                sendEmptyMessageDelayed(123, RtspMediaSource.DEFAULT_TIMEOUT_MS);
                ChildStatHelper.recordKidVideoDiscoveryExpose(TrackWidget.KID_VIDEO_TAB, homeKidsBannerHolder.d, "banner");
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void removeAllMessages() {
        super.removeAllMessages();
        this.b.removeCallbacksAndMessages(null);
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void startAllMessages() {
        super.startAllMessages();
        this.b.sendEmptyMessageDelayed(123, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }
}
