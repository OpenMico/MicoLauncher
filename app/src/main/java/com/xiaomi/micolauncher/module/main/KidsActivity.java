package com.xiaomi.micolauncher.module.main;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.main.recommend.BaseRecommendForChildModeFragment;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class KidsActivity extends BaseActivity {
    private ViewPager a;
    private a b;
    private List<Fragment> c = new ArrayList();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_kids);
        this.a = (ViewPager) findViewById(R.id.view_pager);
        this.b = new a(getSupportFragmentManager());
        this.a.setAdapter(this.b);
        this.a.setOffscreenPageLimit(4);
        this.a.setClipToPadding(false);
        this.a.setPadding(DisplayUtils.dip2px((Activity) this, 12.0f), 0, DisplayUtils.dip2px((Activity) this, 12.0f), 0);
        this.c.add(new BoardFragmentForChild());
        this.c.add(BaseRecommendForChildModeFragment.newInstance(1));
        this.c.add(BaseRecommendForChildModeFragment.newInstance(2));
        this.c.add(BaseRecommendForChildModeFragment.newInstance(3));
        this.b.a(this.c);
        setDefaultScheduleDuration();
        ChildModeRecommendApiHelper.getInstance().loadRecommendForChildModeForce(this);
        SystemSetting.setKeyIsTempChild(true);
        VideoDataManager.getManager().refreshChildModeCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SystemSetting.setKeyIsTempChild(false);
        VideoDataManager.getManager().refreshChildModeCache();
        super.onDestroy();
    }

    /* loaded from: classes3.dex */
    static class a extends FragmentPagerAdapter {
        private List<Fragment> a = new ArrayList();

        void a(List<Fragment> list) {
            this.a = list;
            notifyDataSetChanged();
        }

        a(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return ContainerUtil.getSize(this.a);
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.a.get(i);
        }
    }
}
