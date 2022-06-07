package com.xiaomi.micolauncher.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.homepage.view.MoreAppPageView;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class MoreAppActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String MORE_APP = "more_app";
    FrameLayout a;
    ViewPager b;
    View[] c = new View[3];
    private int d;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.app_more_layout);
        this.a = (FrameLayout) findViewById(R.id.root);
        this.b = (ViewPager) findViewById(R.id.viewpager);
        this.c[0] = findViewById(R.id.first_point);
        this.c[1] = findViewById(R.id.second_point);
        this.c[2] = findViewById(R.id.third_point);
        setDefaultScheduleDuration();
        this.c[this.d].setSelected(true);
        this.b.setAdapter(new a());
        this.b.addOnPageChangeListener(this);
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.activity.-$$Lambda$MoreAppActivity$e52ZI2wvz9hwYNdgW6I48qX98d8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MoreAppActivity.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        finish();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        this.c[this.d].setSelected(false);
        this.c[i].setSelected(true);
        this.d = i;
    }

    /* loaded from: classes3.dex */
    private class a extends PagerAdapter {
        private List<List<SkillApp>> b = SkillDataManager.getManager().getThirdApps();

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        public a() {
            for (int i = 0; i < this.b.size(); i++) {
                MoreAppActivity.this.c[i].setVisibility(0);
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return ContainerUtil.getSize(this.b);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
            MoreAppPageView moreAppPageView = new MoreAppPageView(viewGroup.getContext());
            moreAppPageView.setHostActivity(MoreAppActivity.this);
            moreAppPageView.setDatas(this.b.get(i));
            viewGroup.addView(moreAppPageView);
            return moreAppPageView;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
            viewGroup.removeView((View) obj);
        }
    }
}
