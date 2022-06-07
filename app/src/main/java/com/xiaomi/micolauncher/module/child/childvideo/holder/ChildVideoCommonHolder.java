package com.xiaomi.micolauncher.module.child.childvideo.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.AsyncTaskUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoCommonHolder extends BaseChildVideoHolder implements View.OnClickListener {
    public static final int COLOR1 = 1;
    public static final int COLOR2 = 2;
    public static final int COLOR3 = 3;
    public static final int COLOR4 = 4;
    public static final int COLOR5 = 0;
    TextView a;
    ConstraintLayout b;
    ImageView c;
    private int d;
    private final TextView[] e;
    private final ImageView[] f;
    private String g;
    private final Context h;
    private final boolean i;
    private final int j;
    private final int k;
    private List<ChildVideo.ItemsBean> l;

    private int a(int i) {
        if (i == 0) {
            return R.drawable.child_video_place_bg5;
        }
        switch (i) {
            case 2:
                return R.drawable.child_video_place_bg2;
            case 3:
                return R.drawable.child_video_place_bg3;
            case 4:
                return R.drawable.child_video_place_bg4;
            default:
                return R.drawable.child_video_place_bg1;
        }
    }

    public ChildVideoCommonHolder(final Context context, View view, int i, int i2, final boolean z) {
        super(context, view);
        this.h = context;
        this.j = i;
        this.k = i2;
        this.i = z;
        this.e = new TextView[]{(TextView) view.findViewById(R.id.small_card_name1), (TextView) view.findViewById(R.id.small_card_name2), (TextView) view.findViewById(R.id.small_card_name3), (TextView) view.findViewById(R.id.small_card_name4)};
        this.f = new ImageView[]{(ImageView) view.findViewById(R.id.small_card_img1), (ImageView) view.findViewById(R.id.small_card_img2), (ImageView) view.findViewById(R.id.small_card_img3), (ImageView) view.findViewById(R.id.small_card_img4)};
        this.a = (TextView) view.findViewById(R.id.common_title);
        this.b = (ConstraintLayout) view.findViewById(R.id.common_cl);
        this.c = (ImageView) view.findViewById(R.id.common_more_iv);
        for (final int i3 = 0; i3 < ContainerUtil.getSize(this.f); i3++) {
            this.f[i3].setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoCommonHolder$eUhvTbZc9naVoGhtw5WWt0mUqCk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ChildVideoCommonHolder.this.a(i3, context, z, view2);
                }
            });
            AnimLifecycleManager.repeatOnAttach(this.f[i3], MicoAnimConfigurator4EntertainmentAndApps.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Context context, boolean z, View view) {
        if (this.videoBlock != null && !ContainerUtil.isOutOfBound(i, this.videoBlock.getItems())) {
            ChildVideo.ItemsBean itemsBean = this.videoBlock.getItems().get(i);
            String content_desc = itemsBean.getContent_desc();
            if (CommonUtil.checkHasNetworkAndShowToast(context) && !TextUtils.isEmpty(itemsBean.getMediaId())) {
                ChildPlayUtil.playVideo(context, itemsBean.getMediaId(), itemsBean.getTitle(), itemsBean.getItemImageUrl(), itemsBean.getMiTvType());
                if (!itemsBean.isChildVideo()) {
                    ChildStatHelper.recordTeachClassDiscoveryClick(itemsBean);
                } else if (z) {
                    ChildStatHelper.recordChildTabCardClick(content_desc.concat("–").concat(itemsBean.getTitle()));
                    ChildStatHelper.recordKidVideoDiscoveryClick(TrackWidget.KID_VIDEO_TAB, itemsBean, this.videoBlock.getTitle());
                } else {
                    ChildStatHelper.recordKidVideoDiscoveryClick(TrackWidget.KID_VIDEO_DISCOVERY, itemsBean, this.videoBlock.getTitle());
                }
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        if (!TextUtils.isEmpty(this.videoBlock.getTitle())) {
            this.a.setText(this.videoBlock.getTitle());
        }
        if (!ContainerUtil.isEmpty(this.videoBlock.getItems())) {
            this.l = this.videoBlock.getItems();
            int min = Math.min(4, this.videoBlock.getItems().size());
            for (int i = 0; i < min; i++) {
                if (this.l.get(i) != null) {
                    a(this.e[i], this.l.get(i).getContent_desc());
                    a(this.f[i], this.l.get(i).getItemImageUrl(), this.d);
                }
            }
            if (ContainerUtil.getSize(this.videoBlock.getItems()) > 4) {
                this.g = this.videoBlock.getItems().get(4).getTarget().getUrl();
                if (TextUtils.isEmpty(this.g)) {
                    this.c.setVisibility(8);
                } else {
                    this.c.setVisibility(0);
                    this.c.setOnClickListener(this);
                    AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4EntertainmentAndApps.get());
                }
            } else {
                this.c.setVisibility(8);
            }
            a();
        }
    }

    private void a() {
        AsyncTaskUtils.runAsync(new Runnable() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoCommonHolder$Ch8ncf3bplYhzeXrOlvBTSA5Ipk
            @Override // java.lang.Runnable
            public final void run() {
                ChildVideoCommonHolder.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        this.l = this.videoBlock.getItems();
        int min = Math.min(4, this.videoBlock.getItems().size());
        for (int i = 0; i < min; i++) {
            if (this.l.get(i) != null) {
                ChildVideo.ItemsBean itemsBean = this.l.get(i);
                if (itemsBean.isChildCourse()) {
                    ChildStatHelper.recordTeachClassDiscoveryExpose(itemsBean);
                } else if (this.i) {
                    ChildStatHelper.recordKidVideoDiscoveryExpose(TrackWidget.KID_VIDEO_TAB, itemsBean, this.videoBlock.getTitle());
                } else {
                    ChildStatHelper.recordKidVideoDiscoveryExpose(TrackWidget.KID_VIDEO_DISCOVERY, itemsBean, this.videoBlock.getTitle());
                }
            }
        }
    }

    public void setBackground(int i) {
        if (i > 2) {
            i %= 5;
        }
        this.b.setBackground(this.h.getDrawable(a(i, this.i)));
    }

    private int a(int i, boolean z) {
        this.d = i;
        if (i == 0) {
            return z ? R.drawable.home_kids_video_big_card_bg5 : R.drawable.child_video_big_card_bg5;
        }
        switch (i) {
            case 2:
                return z ? R.drawable.home_kids_video_big_card_bg2 : R.drawable.child_video_big_card_bg2;
            case 3:
                return z ? R.drawable.home_kids_video_big_card_bg3 : R.drawable.child_video_big_card_bg3;
            case 4:
                return z ? R.drawable.home_kids_video_big_card_bg4 : R.drawable.child_video_big_card_bg4;
            default:
                return z ? R.drawable.home_kids_video_big_card_bg1 : R.drawable.child_video_big_card_bg1;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.common_more_iv) {
            StationCategoryListActivity.openStationWithVideoMore(this.h, this.videoBlock.getTitle(), this.g);
        }
    }

    private void a(ImageView imageView, String str, int i) {
        Context context = this.h;
        GlideUtils.bindImageViewWithRoundUseContext(context, str, imageView, context.getResources().getDimensionPixelSize(R.dimen.child_video_small_card_corner_radius), a(i), this.j, this.k);
    }

    private void a(TextView textView, String str) {
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
        }
    }
}
