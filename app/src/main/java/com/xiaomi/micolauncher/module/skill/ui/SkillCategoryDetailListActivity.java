package com.xiaomi.micolauncher.module.skill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.common.view.base.FluidLayout;
import com.xiaomi.micolauncher.common.view.carouselview.CarouselView;
import com.xiaomi.micolauncher.common.view.carouselview.CustomCarouselViewAdapter;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.skill.bean.SkillItem;
import com.xiaomi.micolauncher.module.skill.manager.SkillAnimationUtils;
import com.xiaomi.micolauncher.module.skill.manager.SkillPagingManager;
import com.xiaomi.micolauncher.module.skill.ui.SkillCategoryDetailListActivity;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SkillCategoryDetailListActivity extends BaseActivity {
    private CarouselView a;
    private a b;
    private String c;
    private String d;
    private List<SkillItem> e = new ArrayList();
    private boolean f = false;
    private boolean g = true;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_skill_category_detail_list);
        if (getIntent() != null) {
            this.c = getIntent().getStringExtra("EXTRA_SKILL_ID");
            this.d = getIntent().getStringExtra("EXTRA_CATEGORY_ID");
        }
        a();
        b();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        this.a = (CarouselView) findViewById(R.id.carousel_view);
        this.a.setIsAutoSwitch(false);
        this.a.setIsInfinite(false);
        this.a.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.SkillCategoryDetailListActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                SkillPagingManager.getManager().setIndex(i);
                if (i >= SkillCategoryDetailListActivity.this.e.size() - 4) {
                    SkillCategoryDetailListActivity.this.d();
                }
            }
        });
    }

    private void b() {
        SkillPagingManager.getManager().loadData(this, this.d).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryDetailListActivity$Sh3iqcH6JnP2mLAK2Ry0zIsBfPM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillCategoryDetailListActivity.this.b((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryDetailListActivity$nMcF7CrXMUXzb7-orfvzMeXnN2s
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillCategoryDetailListActivity.this.b((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(List list) throws Exception {
        if (!ContainerUtil.isEmpty(list)) {
            this.e.clear();
            this.e.addAll(list);
            this.b = new a(this, this.e);
            this.a.setCustomCarouselViewAdapter(this.b);
            for (int i = 0; i < this.e.size(); i++) {
                if (this.e.get(i).id.equals(this.c)) {
                    this.a.setCurrentIndex(i);
                    SkillPagingManager.getManager().setIndex(i);
                }
            }
            return;
        }
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        c();
        L.skillpage.e("load category detail list error", th);
    }

    private void c() {
        ToastUtil.showToast((int) R.string.error_get_data_failed);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (this.g && !this.f) {
            this.f = true;
            SkillPagingManager.getManager().autoPaging(this, this.d).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryDetailListActivity$gi2zasdayNt1jEvem6x2SVBruDs
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillCategoryDetailListActivity.this.a((List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryDetailListActivity$M2Rma7YeUTNVeXMEtIqXqRQtnC0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillCategoryDetailListActivity.this.a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        this.f = false;
        this.e.addAll(list);
        this.b.setDataList(this.e);
        if (list.size() < 0) {
            this.g = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.f = false;
        this.g = false;
        L.skillpage.e("paging category detail list error", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends CustomCarouselViewAdapter {
        private Context b;

        public a(Context context, List list) {
            super(list);
            this.b = context;
        }

        @Override // com.xiaomi.micolauncher.common.view.carouselview.CarouselViewAdapter, androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return getPageCount();
        }

        @Override // com.xiaomi.micolauncher.common.view.carouselview.CustomCarouselViewAdapter
        public View getPageView() {
            return LayoutInflater.from(this.b).inflate(R.layout.view_skill_category_detail_info, (ViewGroup) null, false);
        }

        @Override // com.xiaomi.micolauncher.common.view.carouselview.CustomCarouselViewAdapter
        public void bindPageView(View view, Object obj) {
            final SkillItem skillItem = (SkillItem) obj;
            GlideUtils.bindImageViewWithRoundCorners(this.b, skillItem.iconUrl, (ImageView) view.findViewById(R.id.icon_iv), 42);
            ((TextView) view.findViewById(R.id.title_tv)).setText(skillItem.name);
            a(skillItem, (FluidLayout) view.findViewById(R.id.fluid_layout));
            ((ImageView) view.findViewById(R.id.about_iv)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillCategoryDetailListActivity$a$OSa1z00SieceET3O5sbnRNisYqo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SkillCategoryDetailListActivity.a.this.a(skillItem, view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(SkillItem skillItem, View view) {
            SkillDetailAboutActivity.openSkillDetailAboutActivity(this.b, skillItem.id);
        }

        private void a(SkillItem skillItem, FluidLayout fluidLayout) {
            if (skillItem != null) {
                fluidLayout.removeAllViews();
                if (skillItem.isMiBrainSkill(this.b)) {
                    c(skillItem, fluidLayout);
                } else {
                    b(skillItem, fluidLayout);
                }
            }
        }

        private void b(SkillItem skillItem, FluidLayout fluidLayout) {
            if (!ContainerUtil.isEmpty(skillItem.tips)) {
                a(fluidLayout, a(skillItem.tips.get(0), fluidLayout));
            }
            if (!ContainerUtil.isEmpty(skillItem.instructions)) {
                for (String str : skillItem.instructions) {
                    if (!ContainerUtil.isEmpty(str)) {
                        a(fluidLayout, a(str));
                    }
                }
            }
        }

        private void c(SkillItem skillItem, FluidLayout fluidLayout) {
            if (!ContainerUtil.isEmpty(skillItem.tips)) {
                a(fluidLayout, a(skillItem.tips.get(0), fluidLayout));
            }
            if (!ContainerUtil.isEmpty(skillItem.instructions)) {
                for (String str : skillItem.instructions) {
                    if (!ContainerUtil.isEmpty(str)) {
                        a(fluidLayout, a(str, fluidLayout));
                    }
                }
            }
        }

        private View a(final String str, final FluidLayout fluidLayout) {
            final View inflate = LayoutInflater.from(this.b).inflate(R.layout.view_skill_detail_info, (ViewGroup) null);
            final TextView textView = (TextView) inflate.findViewById(R.id.title_tv);
            textView.setText(str);
            final ImageView imageView = (ImageView) inflate.findViewById(R.id.image_iv);
            inflate.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.SkillCategoryDetailListActivity.a.1
                @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                public void onAvoidFastDoubleClick(View view) {
                    if (CommonUtil.checkHasNetworkAndShowToast(a.this.b)) {
                        SpeechManager.getInstance().nlpTtsRequest(str, true);
                        SkillAnimationUtils.startClickAnimation(imageView, textView.getMeasuredWidth() - (DisplayUtils.dip2px(a.this.b, 5.0f) * 2));
                        fluidLayout.startAnimationSet(inflate);
                    }
                }
            });
            return inflate;
        }

        private TextView a(String str) {
            TextView textView = (TextView) LayoutInflater.from(this.b).inflate(R.layout.view_skill_detail_info_unclickable, (ViewGroup) null);
            textView.setText(str);
            return textView;
        }

        private void a(FluidLayout fluidLayout, View view) {
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
            int dip2px = DisplayUtils.dip2px(this.b, 16.0f);
            marginLayoutParams.setMargins(0, 0, dip2px, dip2px);
            fluidLayout.addView(view, marginLayoutParams);
        }
    }

    public static void openSkillCategoryDetailListActivity(Context context, String str, String str2) {
        Intent intent = new Intent(context, SkillCategoryDetailListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", str);
        intent.putExtra("EXTRA_SKILL_ID", str2);
        context.startActivity(intent);
    }
}
