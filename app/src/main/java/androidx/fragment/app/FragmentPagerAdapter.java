package androidx.fragment.app;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import com.xiaomi.mipush.sdk.Constants;

@Deprecated
/* loaded from: classes.dex */
public abstract class FragmentPagerAdapter extends PagerAdapter {
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    @Deprecated
    public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
    private final FragmentManager a;
    private final int b;
    private FragmentTransaction c;
    private Fragment d;
    private boolean e;

    @NonNull
    public abstract Fragment getItem(int i);

    public long getItemId(int i) {
        return i;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(@Nullable Parcelable parcelable, @Nullable ClassLoader classLoader) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @Nullable
    public Parcelable saveState() {
        return null;
    }

    @Deprecated
    public FragmentPagerAdapter(@NonNull FragmentManager fragmentManager) {
        this(fragmentManager, 0);
    }

    public FragmentPagerAdapter(@NonNull FragmentManager fragmentManager, int i) {
        this.c = null;
        this.d = null;
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
        if (this.c == null) {
            this.c = this.a.beginTransaction();
        }
        long itemId = getItemId(i);
        Fragment findFragmentByTag = this.a.findFragmentByTag(a(viewGroup.getId(), itemId));
        if (findFragmentByTag != null) {
            this.c.attach(findFragmentByTag);
        } else {
            findFragmentByTag = getItem(i);
            this.c.add(viewGroup.getId(), findFragmentByTag, a(viewGroup.getId(), itemId));
        }
        if (findFragmentByTag != this.d) {
            findFragmentByTag.setMenuVisibility(false);
            if (this.b == 1) {
                this.c.setMaxLifecycle(findFragmentByTag, Lifecycle.State.STARTED);
            } else {
                findFragmentByTag.setUserVisibleHint(false);
            }
        }
        return findFragmentByTag;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        Fragment fragment = (Fragment) obj;
        if (this.c == null) {
            this.c = this.a.beginTransaction();
        }
        this.c.detach(fragment);
        if (fragment.equals(this.d)) {
            this.d = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.d;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                if (this.b == 1) {
                    if (this.c == null) {
                        this.c = this.a.beginTransaction();
                    }
                    this.c.setMaxLifecycle(this.d, Lifecycle.State.STARTED);
                } else {
                    this.d.setUserVisibleHint(false);
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
            this.d = fragment;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(@NonNull ViewGroup viewGroup) {
        FragmentTransaction fragmentTransaction = this.c;
        if (fragmentTransaction != null) {
            if (!this.e) {
                try {
                    this.e = true;
                    fragmentTransaction.commitNowAllowingStateLoss();
                } finally {
                    this.e = false;
                }
            }
            this.c = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    private static String a(int i, long j) {
        return "android:switcher:" + i + Constants.COLON_SEPARATOR + j;
    }
}
