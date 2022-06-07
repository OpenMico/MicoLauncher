package com.xiaomi.micolauncher.module.miot_v2;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.video.ui.base.CustomViewPager;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MIoTLeftTabHost extends RelativeLayout {
    private final Context a;
    protected a mAdapter;
    protected FragmentManager mFragmentManager;
    protected CustomViewPager mViewPager;
    protected LinearLayout tabWidget;
    protected int currentTab = -1;
    protected int tabCount = 0;
    protected ArrayList<Fragment> mFragments = new ArrayList<>();

    public static /* synthetic */ void a(View view) {
    }

    public MIoTLeftTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = context;
        View inflate = inflate(getContext(), R.layout.view_miot_left_tab_host, this);
        this.tabWidget = (LinearLayout) inflate.findViewById(R.id.tab_widget);
        this.mViewPager = (CustomViewPager) inflate.findViewById(R.id.tab_content);
        this.tabWidget.setOnClickListener($$Lambda$MIoTLeftTabHost$H1mzX9ChpxhIgZbA6VfCv5IHm0s.INSTANCE);
        this.mViewPager.setOffscreenPageLimit(3);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mAdapter = new a(this.mFragmentManager, this.mFragments);
        this.mViewPager.setAdapter(this.mAdapter);
    }

    public LinearLayout getTabWidget() {
        return this.tabWidget;
    }

    public CustomViewPager getViewPager() {
        return this.mViewPager;
    }

    public void clear() {
        this.tabCount = 0;
        this.currentTab = -1;
        this.tabWidget.removeAllViews();
        this.mFragments.clear();
        this.mFragmentManager.getFragments().clear();
        this.mAdapter.notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment) {
        this.mFragments.add(fragment);
        this.mAdapter.notifyDataSetChanged();
    }

    public void setCurrentTab(int i) {
        setCurrentTab(i, false);
    }

    public void setCurrentTab(int i, boolean z) {
        if (this.currentTab != i) {
            if (i > 0) {
                this.mViewPager.setCurrentItem(i - 1);
            }
            switchTab(i);
        }
    }

    protected void switchTab(int i) {
        if (this.currentTab != i) {
            if (i > 0) {
                for (int i2 = 0; i2 < this.tabWidget.getChildCount(); i2++) {
                    a(this.tabWidget.getChildAt(i2), false);
                }
                a(this.tabWidget.getChildAt(i), true);
            }
            this.currentTab = i;
        }
    }

    private void a(View view, boolean z) {
        if (z) {
            TextView textView = (TextView) view.findViewById(R.id.text_view);
            textView.setTextSize(getResources().getDimension(R.dimen.miot_room_text_size_selected));
            textView.setTextColor(ContextCompat.getColor(this.a, R.color.white));
            return;
        }
        TextView textView2 = (TextView) view.findViewById(R.id.text_view);
        textView2.setTextSize(getResources().getDimension(R.dimen.miot_room_text_size_default));
        textView2.setTextColor(ContextCompat.getColor(this.a, R.color.white_90_transparent));
    }

    public View addMIoTHomeTabToFirst(String str, View.OnClickListener onClickListener) {
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.view_miot_left_tab_widget, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.text_view);
        textView.setBackgroundResource(R.drawable.miot_home_select_bg);
        textView.setText(str);
        this.tabWidget.addView(inflate, 0);
        textView.setOnClickListener(onClickListener);
        return inflate;
    }

    public Fragment addMIoTRoomTab(String str, MiotDeviceHelper miotDeviceHelper, Bundle bundle, View.OnClickListener onClickListener) {
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.view_miot_left_tab_widget, (ViewGroup) null);
        inflate.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        ((TextView) inflate.findViewById(R.id.text_view)).setText(str);
        inflate.setPadding(0, getResources().getDimensionPixelOffset(R.dimen.miot_room_view_margin_top), 0, 0);
        this.tabWidget.addView(inflate);
        MIoTListFragment newInstance = MIoTListFragment.newInstance(bundle);
        newInstance.setOperator(miotDeviceHelper);
        addFragment(newInstance);
        if (onClickListener != null) {
            inflate.setOnClickListener(onClickListener);
        } else {
            final int i = this.tabCount;
            inflate.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTLeftTabHost$UXJI8vGMG9_YIdADI1HD8zmoJBY
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MIoTLeftTabHost.this.a(i, view);
                }
            });
        }
        this.tabCount++;
        return newInstance;
    }

    public /* synthetic */ void a(int i, View view) {
        setCurrentTab(i);
    }

    /* loaded from: classes3.dex */
    public static class a extends FragmentPagerAdapter {
        private ArrayList<Fragment> a;

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

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

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public long getItemId(int i) {
            ArrayList<Fragment> arrayList = this.a;
            return arrayList == null ? super.getItemId(i) : arrayList.get(i).hashCode();
        }
    }
}
