package com.xiaomi.micolauncher.common.adapter;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public abstract class MyFragmentPagerAdapter extends PagerAdapter {
    private static final Logger a = XLog.tag("StackViewPager").build();
    private final FragmentManager b;
    private FragmentTransaction c = null;
    private Fragment d = null;

    public abstract Fragment getItem(int i);

    public long getItemId(int i) {
        return i;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        this.b = fragmentManager;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.c == null) {
            this.c = this.b.beginTransaction();
        }
        long itemId = getItemId(i);
        Fragment findFragmentByTag = this.b.findFragmentByTag(a(viewGroup.getId(), itemId));
        if (findFragmentByTag != null) {
            Logger logger = a;
            logger.v("Attaching item #" + itemId + ": f=" + findFragmentByTag);
            this.c.attach(findFragmentByTag);
        } else {
            findFragmentByTag = getItem(i);
            Logger logger2 = a;
            logger2.v("Adding item #" + itemId + ": f=" + findFragmentByTag);
            this.c.add(viewGroup.getId(), findFragmentByTag, a(viewGroup.getId(), itemId));
        }
        if (findFragmentByTag != this.d) {
            findFragmentByTag.setMenuVisibility(false);
            findFragmentByTag.setUserVisibleHint(false);
        }
        return findFragmentByTag;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.c == null) {
            this.c = this.b.beginTransaction();
        }
        Logger logger = a;
        StringBuilder sb = new StringBuilder();
        sb.append("Detaching item #");
        sb.append(i);
        sb.append(": f=");
        sb.append(obj);
        sb.append(" v=");
        Fragment fragment = (Fragment) obj;
        sb.append(fragment.getView());
        logger.v(sb.toString());
        this.c.detach(fragment);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.d;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                this.d.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            this.d = fragment;
        }
    }

    public FragmentTransaction getCurrentTransaction() {
        FragmentTransaction fragmentTransaction = this.c;
        if (fragmentTransaction != null) {
            return fragmentTransaction;
        }
        this.c = this.b.beginTransaction();
        return this.c;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.c != null) {
            a.v("finishUpdate commitNowAllowingStateLoss start");
            this.c.commitAllowingStateLoss();
            this.c = null;
            a.v("finishUpdate commitNowAllowingStateLoss end");
            return;
        }
        a.v("finishUpdate failed mCurTransaction is null");
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    private static String a(int i, long j) {
        return "android:switcher:" + i + Constants.COLON_SEPARATOR + j;
    }
}
