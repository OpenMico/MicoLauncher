package com.xiaomi.micolauncher.module.video.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class CustomTabHost extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private SwitchTabListener a;
    protected a mAdapter;
    protected FragmentManager mFragmentManager;
    protected LinearLayout mTabWidget;
    protected CustomViewPager mViewPager;
    protected ScrollView scrollView;
    protected int mCurrentTab = -1;
    protected int mTabCount = 0;
    protected ArrayList<Fragment> mFragments = new ArrayList<>();

    /* loaded from: classes3.dex */
    public interface SwitchTabListener {
        void switchTab(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view) {
    }

    protected abstract View inflateView();

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    public CustomTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        View inflateView = inflateView();
        this.mTabWidget = (LinearLayout) inflateView.findViewById(R.id.tab_widget);
        this.mViewPager = (CustomViewPager) inflateView.findViewById(R.id.tab_content);
        this.scrollView = (ScrollView) inflateView.findViewById(R.id.scroll_view);
        this.mTabWidget.setOnClickListener($$Lambda$CustomTabHost$cA5DYdgpRKqFuXKUpPC7zoVMVqc.INSTANCE);
        this.mViewPager.setOffscreenPageLimit(3);
        this.mViewPager.setOnPageChangeListener(this);
    }

    public LinearLayout getTabWidget() {
        return this.mTabWidget;
    }

    public View getTabWidgetView(int i) {
        return this.mTabWidget.getChildAt(i);
    }

    public CustomViewPager getViewPager() {
        return this.mViewPager;
    }

    public int getTabSize() {
        return this.mAdapter.getCount();
    }

    public List<Fragment> getFragmentList() {
        return this.mFragments;
    }

    public Fragment addTab(View view, Class<?> cls, Bundle bundle, View.OnClickListener onClickListener) {
        Fragment fragment;
        InstantiationException e;
        IllegalAccessException e2;
        try {
            fragment = (Fragment) cls.newInstance();
        } catch (IllegalAccessException e3) {
            e2 = e3;
            fragment = null;
        } catch (InstantiationException e4) {
            e = e4;
            fragment = null;
        }
        try {
            fragment.setArguments(bundle);
            this.mFragments.add(fragment);
            this.mAdapter.notifyDataSetChanged();
            this.mTabWidget.addView(view);
            if (onClickListener != null) {
                view.setOnClickListener(onClickListener);
            } else {
                final int i = this.mTabCount;
                view.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.base.-$$Lambda$CustomTabHost$zJ8ZuUDsDH_PalyGw_zb-MUm37k
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        CustomTabHost.this.a(i, view2);
                    }
                });
            }
            this.mTabCount++;
        } catch (IllegalAccessException e5) {
            e2 = e5;
            XLog.e(e2);
            return fragment;
        } catch (InstantiationException e6) {
            e = e6;
            XLog.e(e);
            return fragment;
        }
        return fragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, View view) {
        setCurrentTab(i);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mAdapter = new a(this.mFragmentManager, this.mFragments);
        this.mViewPager.setAdapter(this.mAdapter);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        switchTab(i);
    }

    public void setCurrentTab(int i) {
        if (this.mCurrentTab != i) {
            this.mViewPager.setCurrentItem(i);
            a(i);
            switchTab(i);
        }
    }

    public void setCurrentTab(int i, boolean z) {
        if (this.mCurrentTab != i) {
            this.mViewPager.setCurrentItem(i, z);
            switchTab(i);
        }
    }

    public int getCurrentTab() {
        return this.mCurrentTab;
    }

    public void clear() {
        this.mFragments.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void switchTab(int i) {
        if (this.mCurrentTab != i) {
            for (int i2 = 0; i2 < this.mFragments.size(); i2++) {
                if (i2 == i) {
                    this.mTabWidget.getChildAt(i2).setSelected(true);
                    SwitchTabListener switchTabListener = this.a;
                    if (switchTabListener != null) {
                        switchTabListener.switchTab(i);
                    }
                } else {
                    this.mTabWidget.getChildAt(i2).setSelected(false);
                }
            }
            this.mCurrentTab = i;
        }
    }

    public void setSwitchTabListener(SwitchTabListener switchTabListener) {
        this.a = switchTabListener;
    }

    private void a(final int i) {
        this.mTabWidget.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.video.ui.base.-$$Lambda$CustomTabHost$8H4PZPqoJn_dq0annJi8Z-7pAKY
            @Override // java.lang.Runnable
            public final void run() {
                CustomTabHost.this.b(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i) {
        this.scrollView.smoothScrollTo((int) this.mTabWidget.getChildAt(i).getX(), this.mTabWidget.getChildAt(i).getMeasuredHeight() * i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends FragmentPagerAdapter {
        private ArrayList<Fragment> a;

        public a(FragmentManager fragmentManager, ArrayList<Fragment> arrayList) {
            super(fragmentManager);
            this.a = arrayList;
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            ArrayList<Fragment> arrayList = this.a;
            if (arrayList == null) {
                return null;
            }
            return arrayList.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            ArrayList<Fragment> arrayList = this.a;
            if (arrayList == null) {
                return 0;
            }
            return arrayList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return super.getItemPosition(obj);
        }
    }
}
