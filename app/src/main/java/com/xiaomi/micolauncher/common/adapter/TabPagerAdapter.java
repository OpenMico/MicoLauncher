package com.xiaomi.micolauncher.common.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

/* loaded from: classes3.dex */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private List<TabPage> a;

    /* loaded from: classes3.dex */
    public static class TabPage {
        private Fragment a;
        private CharSequence b;

        public static TabPage newPage(Fragment fragment) {
            return newPage(fragment, null);
        }

        public static TabPage newPage(Fragment fragment, CharSequence charSequence) {
            return new TabPage(fragment, charSequence);
        }

        private TabPage(Fragment fragment, CharSequence charSequence) {
            this.a = fragment;
            this.b = charSequence;
        }
    }

    public TabPagerAdapter(FragmentManager fragmentManager, List<TabPage> list) {
        super(fragmentManager);
        this.a = list;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.a.get(i).a;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.a.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        CharSequence charSequence = this.a.get(i).b;
        return charSequence != null ? charSequence : "";
    }
}
