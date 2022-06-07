package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes.dex */
public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    @Deprecated
    public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
    private final FragmentManager a;
    private final int b;
    private FragmentTransaction c;
    private ArrayList<Fragment.SavedState> d;
    private ArrayList<Fragment> e;
    private Fragment f;
    private boolean g;

    @NonNull
    public abstract Fragment getItem(int i);

    @Deprecated
    public FragmentStatePagerAdapter(@NonNull FragmentManager fragmentManager) {
        this(fragmentManager, 0);
    }

    public FragmentStatePagerAdapter(@NonNull FragmentManager fragmentManager, int i) {
        this.c = null;
        this.d = new ArrayList<>();
        this.e = new ArrayList<>();
        this.f = null;
        this.a = fragmentManager;
        this.b = i;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(@NonNull ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        Fragment.SavedState savedState;
        Fragment fragment;
        if (this.e.size() > i && (fragment = this.e.get(i)) != null) {
            return fragment;
        }
        if (this.c == null) {
            this.c = this.a.beginTransaction();
        }
        Fragment item = getItem(i);
        if (this.d.size() > i && (savedState = this.d.get(i)) != null) {
            item.setInitialSavedState(savedState);
        }
        while (this.e.size() <= i) {
            this.e.add(null);
        }
        item.setMenuVisibility(false);
        if (this.b == 0) {
            item.setUserVisibleHint(false);
        }
        this.e.set(i, item);
        this.c.add(viewGroup.getId(), item);
        if (this.b == 1) {
            this.c.setMaxLifecycle(item, Lifecycle.State.STARTED);
        }
        return item;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        Fragment fragment = (Fragment) obj;
        if (this.c == null) {
            this.c = this.a.beginTransaction();
        }
        while (this.d.size() <= i) {
            this.d.add(null);
        }
        this.d.set(i, fragment.isAdded() ? this.a.saveFragmentInstanceState(fragment) : null);
        this.e.set(i, null);
        this.c.remove(fragment);
        if (fragment.equals(this.f)) {
            this.f = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.f;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                if (this.b == 1) {
                    if (this.c == null) {
                        this.c = this.a.beginTransaction();
                    }
                    this.c.setMaxLifecycle(this.f, Lifecycle.State.STARTED);
                } else {
                    this.f.setUserVisibleHint(false);
                }
            }
            fragment.setMenuVisibility(true);
            if (this.b == 1) {
                if (this.c == null) {
                    this.c = this.a.beginTransaction();
                }
                this.c.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            } else {
                fragment.setUserVisibleHint(true);
            }
            this.f = fragment;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(@NonNull ViewGroup viewGroup) {
        FragmentTransaction fragmentTransaction = this.c;
        if (fragmentTransaction != null) {
            if (!this.g) {
                try {
                    this.g = true;
                    fragmentTransaction.commitNowAllowingStateLoss();
                } finally {
                    this.g = false;
                }
            }
            this.c = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @Nullable
    public Parcelable saveState() {
        Bundle bundle;
        if (this.d.size() > 0) {
            bundle = new Bundle();
            Fragment.SavedState[] savedStateArr = new Fragment.SavedState[this.d.size()];
            this.d.toArray(savedStateArr);
            bundle.putParcelableArray("states", savedStateArr);
        } else {
            bundle = null;
        }
        for (int i = 0; i < this.e.size(); i++) {
            Fragment fragment = this.e.get(i);
            if (fragment != null && fragment.isAdded()) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                this.a.putFragment(bundle, "f" + i, fragment);
            }
        }
        return bundle;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(@Nullable Parcelable parcelable, @Nullable ClassLoader classLoader) {
        if (parcelable != null) {
            Bundle bundle = (Bundle) parcelable;
            bundle.setClassLoader(classLoader);
            Parcelable[] parcelableArray = bundle.getParcelableArray("states");
            this.d.clear();
            this.e.clear();
            if (parcelableArray != null) {
                for (Parcelable parcelable2 : parcelableArray) {
                    this.d.add((Fragment.SavedState) parcelable2);
                }
            }
            for (String str : bundle.keySet()) {
                if (str.startsWith("f")) {
                    int parseInt = Integer.parseInt(str.substring(1));
                    Fragment fragment = this.a.getFragment(bundle, str);
                    if (fragment != null) {
                        while (this.e.size() <= parseInt) {
                            this.e.add(null);
                        }
                        fragment.setMenuVisibility(false);
                        this.e.set(parseInt, fragment);
                    } else {
                        Log.w("FragmentStatePagerAdapt", "Bad fragment at key " + str);
                    }
                }
            }
        }
    }
}
