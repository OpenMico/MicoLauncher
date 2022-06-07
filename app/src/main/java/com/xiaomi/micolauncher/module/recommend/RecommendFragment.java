package com.xiaomi.micolauncher.module.recommend;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xiaomi.mico.base.utils.XMStringUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.ShowHideBackgroundEvent;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.widget.StackViewPager;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class RecommendFragment extends BaseFragment implements StackViewPager.PageTransformer {
    public static final String CATEGORY = "category";
    ViewGroup a;
    private List<MainScreen.RecommendPage> b;
    private MainScreen.RecommendPage c;
    private int d;
    private String e = MainScreen.RecommendPage.CATEGORY_MEDIA;

    @Override // com.xiaomi.micolauncher.common.widget.StackViewPager.PageTransformer
    public boolean onPageEnter(StackViewPager.Direction direction) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.widget.StackViewPager.PageTransformer
    public boolean transformPage(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recommend, viewGroup, false);
        this.a = (ViewGroup) inflate.findViewById(R.id.custom_layer);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.e = arguments.getString("category", MainScreen.RecommendPage.CATEGORY_MEDIA);
        }
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        inflate.setTag(this);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        EventBusRegistry.getEventBus().post(new ShowHideBackgroundEvent(this.e, false, null));
    }

    private void a() {
        List<MainScreen.RecommendPage> list = this.b;
        if (list != null && list.size() > 0) {
            List<MainScreen.RecommendPage> list2 = this.b;
            this.c = list2.get(this.d % list2.size());
            this.d++;
            View page = PageFactory.getPage(getContext(), this.a, this.c);
            this.a.removeAllViews();
            this.a.addView(page);
            if (XMStringUtils.isUrl(this.c.backgroundImage.middle)) {
                Glide.with(this).load(this.c.backgroundImage.middle).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().dontAnimate()).into((RequestBuilder<Drawable>) new CustomTarget<Drawable>() { // from class: com.xiaomi.micolauncher.module.recommend.RecommendFragment.1
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(@Nullable Drawable drawable) {
                    }

                    /* renamed from: a */
                    public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                        if (RecommendFragment.this.isVisible()) {
                            EventBusRegistry.getEventBus().post(new ShowHideBackgroundEvent(RecommendFragment.this.e, true, drawable));
                        }
                    }
                });
                return;
            }
            L.base.i("RecommendPage url is empty %s", this.c.pageId);
            EventBusRegistry.getEventBus().post(new ShowHideBackgroundEvent(this.e, false, null));
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EventBusRegistry.getEventBus().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRecommendDataChanged(RecommendEvent.DataUpdated dataUpdated) {
        List<MainScreen.RecommendPage> list = this.b;
        boolean z = list == null || list.size() == 0;
        if (this.e.equalsIgnoreCase(MainScreen.RecommendPage.CATEGORY_MEDIA)) {
            this.b = RecommendManager.getInstance().getRecommendMedias();
        } else {
            this.b = RecommendManager.getInstance().getRecommendSkills();
        }
        if (z) {
            a();
        }
        for (MainScreen.RecommendPage recommendPage : this.b) {
            Glide.with(this).load(recommendPage.backgroundImage.middle).preload();
        }
    }

    public void onViewClicked() {
        if (!TextUtils.isEmpty(this.c.action)) {
            SchemaManager.handleSchema(getContext(), this.c.action);
        }
    }
}
