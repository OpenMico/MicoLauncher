package com.xiaomi.micolauncher.module.homepage.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.HttpsUtil;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.ThirdPartySchemaHandler;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.micolauncher.module.homepage.viewholder.home.PlayHelper;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class BannerAdapter extends PagerAdapter {
    private Context a;
    private List<MainPage.LongVideo> b;
    private List<ImageView> c = new ArrayList();
    private String d = ThirdPartySchemaHandler.SCHEME;
    private int e;
    private int f;
    private ViewGroup.LayoutParams g;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    public BannerAdapter(Context context, List<MainPage.LongVideo> list) {
        this.a = context;
        this.b = list;
        this.e = UiUtils.getSize(context, R.dimen.banner_width);
        this.f = UiUtils.getSize(context, R.dimen.banner_height);
        this.g = new ViewGroup.LayoutParams(this.e, this.f);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<MainPage.LongVideo> list = this.b;
        if (list == null) {
            return 0;
        }
        return list.size() == 1 ? 1 : 500;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        final ImageView imageView;
        if (ContainerUtil.isEmpty(this.b)) {
            return null;
        }
        final int size = i % this.b.size();
        final MainPage.LongVideo longVideo = this.b.get(size);
        if (this.c.isEmpty()) {
            imageView = new ImageView(this.a);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = this.c.remove(0);
        }
        UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$BannerAdapter$3Fh61DTGtegDov4KZqPVPvP02Ek
            @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
            public final void onLayoutComplete(boolean z) {
                BannerAdapter.this.a(longVideo, imageView, z);
            }
        }, imageView);
        viewGroup.addView(imageView, this.g);
        RxViewHelp.debounceClicksWithOneSeconds(imageView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$BannerAdapter$ffBXWSI3fX5Sp8At4U6zr8XakQs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BannerAdapter.this.a(longVideo, size, obj);
            }
        });
        if (longVideo.isVideo()) {
            HomePageRecordHelper.recordLongVideoShow(longVideo, "A", size);
        }
        return imageView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MainPage.LongVideo longVideo, ImageView imageView, boolean z) {
        GlideUtils.bindImageViewCornersWithListener(this.a, longVideo.getImageURL(), imageView, R.drawable.homepage_loading_banner, UiUtils.getSize(this.a, R.dimen.banner_width), UiUtils.getSize(this.a, R.dimen.banner_height));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MainPage.LongVideo longVideo, int i, Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(this.a)) {
            if (longVideo.isVideo()) {
                PlayHelper.play(this.a, longVideo);
                RecommendDataManager.getManager().updateRecentPlayVideo(longVideo);
                HomePageRecordHelper.recordLongVideoClick(longVideo, "A", i);
            } else if (!TextUtils.isEmpty(longVideo.getActionURL())) {
                Uri parse = Uri.parse(longVideo.getActionURL());
                if (HttpsUtil.isHttpOrHttpsSchema(parse)) {
                    SimpleWebActivity.startSimpleWebActivity(this.a, longVideo.getActionURL());
                } else {
                    SchemaManager.handleSchema(this.a, parse);
                }
            }
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        ImageView imageView = (ImageView) obj;
        viewGroup.removeView(imageView);
        this.c.add(imageView);
    }
}
