package com.blankj.utilcode.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class FragmentUtils {

    /* loaded from: classes.dex */
    public interface OnBackClickListener {
        boolean onBackClick();
    }

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, (String) null, false, false);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, (String) null, z, false);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, boolean z2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, (String) null, z, z2);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, null, false, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, null, z, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, null, false, i2, i3, i4, i5);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, null, z, i2, i3, i4, i5);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, @NonNull View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (viewArr != null) {
            add(fragmentManager, fragment, i, (String) null, false, viewArr);
        } else {
            throw new NullPointerException("Argument 'sharedElements' of type View[] (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, @NonNull View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (viewArr != null) {
            add(fragmentManager, fragment, i, (String) null, z, viewArr);
        } else {
            throw new NullPointerException("Argument 'sharedElements' of type View[] (#4 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> list, @IdRes int i, int i2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (list != null) {
            add(fragmentManager, (Fragment[]) list.toArray(new Fragment[0]), i, (String[]) null, i2);
        } else {
            throw new NullPointerException("Argument 'adds' of type List<Fragment> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment[] fragmentArr, @IdRes int i, int i2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragmentArr != null) {
            add(fragmentManager, fragmentArr, i, (String[]) null, i2);
        } else {
            throw new NullPointerException("Argument 'adds' of type Fragment[] (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, str, false, false);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, str, z, false);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, boolean z2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            a(fragment, new a(i, str, z, z2));
            a(1, fragmentManager, null, fragment);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, str, false, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, str, z, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            add(fragmentManager, fragment, i, str, false, i2, i3, i4, i5);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            a(fragment, new a(i, str, false, z));
            a(beginTransaction, i2, i3, i4, i5);
            a(1, fragmentManager, beginTransaction, (Fragment) null, fragment);
        } else {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, @NonNull View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (viewArr != null) {
            add(fragmentManager, fragment, i, str, false, viewArr);
        } else {
            throw new NullPointerException("Argument 'sharedElements' of type View[] (#4 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, @NonNull View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'add' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (viewArr != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            a(fragment, new a(i, str, false, z));
            a(beginTransaction, viewArr);
            a(1, fragmentManager, beginTransaction, (Fragment) null, fragment);
        } else {
            throw new NullPointerException("Argument 'sharedElements' of type View[] (#5 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> list, @IdRes int i, String[] strArr, int i2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (list != null) {
            add(fragmentManager, (Fragment[]) list.toArray(new Fragment[0]), i, strArr, i2);
        } else {
            throw new NullPointerException("Argument 'adds' of type List<Fragment> (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment[] fragmentArr, @IdRes int i, String[] strArr, int i2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragmentArr != null) {
            if (strArr == null) {
                int length = fragmentArr.length;
                int i3 = 0;
                while (i3 < length) {
                    a(fragmentArr[i3], new a(i, null, i2 != i3, false));
                    i3++;
                }
            } else {
                int length2 = fragmentArr.length;
                int i4 = 0;
                while (i4 < length2) {
                    a(fragmentArr[i4], new a(i, strArr[i4], i2 != i4, false));
                    i4++;
                }
            }
            a(1, fragmentManager, null, fragmentArr);
        } else {
            throw new NullPointerException("Argument 'adds' of type Fragment[] (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void show(@NonNull Fragment fragment) {
        if (fragment != null) {
            a(fragment, false);
            a(2, fragment.getFragmentManager(), null, fragment);
            return;
        }
        throw new NullPointerException("Argument 'show' of type Fragment (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void show(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            for (Fragment fragment : fragments) {
                a(fragment, false);
            }
            a(2, fragmentManager, null, (Fragment[]) fragments.toArray(new Fragment[0]));
            return;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void hide(@NonNull Fragment fragment) {
        if (fragment != null) {
            a(fragment, true);
            a(4, fragment.getFragmentManager(), null, fragment);
            return;
        }
        throw new NullPointerException("Argument 'hide' of type Fragment (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void hide(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            for (Fragment fragment : fragments) {
                a(fragment, true);
            }
            a(4, fragmentManager, null, (Fragment[]) fragments.toArray(new Fragment[0]));
            return;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull Fragment fragment2) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'show' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            showHide(fragment, Collections.singletonList(fragment2));
        } else {
            throw new NullPointerException("Argument 'hide' of type Fragment (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void showHide(int i, @NonNull Fragment... fragmentArr) {
        if (fragmentArr != null) {
            showHide(fragmentArr[i], fragmentArr);
            return;
        }
        throw new NullPointerException("Argument 'fragments' of type Fragment[] (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull Fragment... fragmentArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'show' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragmentArr != null) {
            showHide(fragment, Arrays.asList(fragmentArr));
        } else {
            throw new NullPointerException("Argument 'hide' of type Fragment[] (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void showHide(int i, @NonNull List<Fragment> list) {
        if (list != null) {
            showHide(list.get(i), list);
            return;
        }
        throw new NullPointerException("Argument 'fragments' of type List<Fragment> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull List<Fragment> list) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'show' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (list != null) {
            Iterator<Fragment> it = list.iterator();
            while (true) {
                boolean z = false;
                if (it.hasNext()) {
                    Fragment next = it.next();
                    if (next != fragment) {
                        z = true;
                    }
                    a(next, z);
                } else {
                    a(8, fragment.getFragmentManager(), fragment, (Fragment[]) list.toArray(new Fragment[0]));
                    return;
                }
            }
        } else {
            throw new NullPointerException("Argument 'hide' of type List<Fragment> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull Fragment fragment2, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'show' of type Fragment (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            showHide(fragment, Collections.singletonList(fragment2), i, i2, i3, i4);
        } else {
            throw new NullPointerException("Argument 'hide' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void showHide(int i, @NonNull List<Fragment> list, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (list != null) {
            showHide(list.get(i), list, i2, i3, i4, i5);
            return;
        }
        throw new NullPointerException("Argument 'fragments' of type List<Fragment> (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull List<Fragment> list, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'show' of type Fragment (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (list != null) {
            Iterator<Fragment> it = list.iterator();
            while (true) {
                boolean z = false;
                if (!it.hasNext()) {
                    break;
                }
                Fragment next = it.next();
                if (next != fragment) {
                    z = true;
                }
                a(next, z);
            }
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                a(beginTransaction, i, i2, i3, i4);
                a(8, fragmentManager, beginTransaction, fragment, (Fragment[]) list.toArray(new Fragment[0]));
            }
        } else {
            throw new NullPointerException("Argument 'hide' of type List<Fragment> (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, false);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, z);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, false, i, i2, 0, 0);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, z, i, i2, 0, 0);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, false, i, i2, i3, i4);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, z, i, i2, i3, i4);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, View... viewArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, false, viewArr);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z, View... viewArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, (String) null, z, viewArr);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, (String) null, false);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, (String) null, z);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, null, false, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, null, z, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, null, false, i2, i3, i4, i5);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, null, z, i2, i3, i4, i5);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, (String) null, false, viewArr);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, boolean z, View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, (String) null, z, viewArr);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, str, false);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, boolean z) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null) {
                replace(fragmentManager, fragment2, a(fragment).a, str, z);
            }
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, str, false, i, i2, 0, 0);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, boolean z, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, str, z, i, i2, 0, 0);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, str, false, i, i2, i3, i4);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, boolean z, @AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null) {
                replace(fragmentManager, fragment2, a(fragment).a, str, z, i, i2, i3, i4);
            }
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, View... viewArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            replace(fragment, fragment2, str, false, viewArr);
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, String str, boolean z, View... viewArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'srcFragment' of type Fragment (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment2 != null) {
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null) {
                replace(fragmentManager, fragment2, a(fragment).a, str, z, viewArr);
            }
        } else {
            throw new NullPointerException("Argument 'destFragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, str, false);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            a(fragment, new a(i, str, false, z));
            a(16, fragmentManager, beginTransaction, (Fragment) null, fragment);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, str, false, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, str, z, i2, i3, 0, 0);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, str, false, i2, i3, i4, i5);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 8, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            a(fragment, new a(i, str, false, z));
            a(beginTransaction, i2, i3, i4, i5);
            a(16, fragmentManager, beginTransaction, (Fragment) null, fragment);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 9, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            replace(fragmentManager, fragment, i, str, false, viewArr);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i, String str, boolean z, View... viewArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            a(fragment, new a(i, str, false, z));
            a(beginTransaction, viewArr);
            a(16, fragmentManager, beginTransaction, (Fragment) null, fragment);
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void pop(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            pop(fragmentManager, true);
            return;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void pop(@NonNull FragmentManager fragmentManager, boolean z) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            fragmentManager.popBackStackImmediate();
        } else {
            fragmentManager.popBackStack();
        }
    }

    public static void popTo(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> cls, boolean z) {
        if (fragmentManager != null) {
            popTo(fragmentManager, cls, z, true);
            return;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void popTo(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> cls, boolean z, boolean z2) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z2) {
            fragmentManager.popBackStackImmediate(cls.getName(), (int) z);
        } else {
            fragmentManager.popBackStack(cls.getName(), z ? 1 : 0);
        }
    }

    public static void popAll(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            popAll(fragmentManager, true);
            return;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void popAll(@NonNull FragmentManager fragmentManager, boolean z) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(0);
            if (z) {
                fragmentManager.popBackStackImmediate(backStackEntryAt.getId(), 1);
            } else {
                fragmentManager.popBackStack(backStackEntryAt.getId(), 1);
            }
        }
    }

    public static void remove(@NonNull Fragment fragment) {
        if (fragment != null) {
            a(32, fragment.getFragmentManager(), null, fragment);
            return;
        }
        throw new NullPointerException("Argument 'remove' of type Fragment (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void removeTo(@NonNull Fragment fragment, boolean z) {
        if (fragment != null) {
            a(64, fragment.getFragmentManager(), z ? fragment : null, fragment);
            return;
        }
        throw new NullPointerException("Argument 'removeTo' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void removeAll(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            a(32, fragmentManager, null, (Fragment[]) getFragments(fragmentManager).toArray(new Fragment[0]));
            return;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static void a(Fragment fragment, a aVar) {
        Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            fragment.setArguments(arguments);
        }
        arguments.putInt("args_id", aVar.a);
        arguments.putBoolean("args_is_hide", aVar.b);
        arguments.putBoolean("args_is_add_stack", aVar.c);
        arguments.putString("args_tag", aVar.d);
    }

    private static void a(Fragment fragment, boolean z) {
        Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            fragment.setArguments(arguments);
        }
        arguments.putBoolean("args_is_hide", z);
    }

    private static a a(Fragment fragment) {
        Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            arguments = Bundle.EMPTY;
        }
        return new a(arguments.getInt("args_id", fragment.getId()), arguments.getBoolean("args_is_hide"), arguments.getBoolean("args_is_add_stack"));
    }

    private static void a(int i, @Nullable FragmentManager fragmentManager, Fragment fragment, Fragment... fragmentArr) {
        if (fragmentManager != null) {
            a(i, fragmentManager, fragmentManager.beginTransaction(), fragment, fragmentArr);
        }
    }

    private static void a(int i, @NonNull FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, Fragment fragment, Fragment... fragmentArr) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null || !fragment.isRemoving()) {
            int i2 = 0;
            if (i == 4) {
                int length = fragmentArr.length;
                while (i2 < length) {
                    fragmentTransaction.hide(fragmentArr[i2]);
                    i2++;
                }
            } else if (i == 8) {
                fragmentTransaction.show(fragment);
                int length2 = fragmentArr.length;
                while (i2 < length2) {
                    Fragment fragment2 = fragmentArr[i2];
                    if (fragment2 != fragment) {
                        fragmentTransaction.hide(fragment2);
                    }
                    i2++;
                }
            } else if (i == 16) {
                Bundle arguments = fragmentArr[0].getArguments();
                if (arguments != null) {
                    String string = arguments.getString("args_tag", fragmentArr[0].getClass().getName());
                    fragmentTransaction.replace(arguments.getInt("args_id"), fragmentArr[0], string);
                    if (arguments.getBoolean("args_is_add_stack")) {
                        fragmentTransaction.addToBackStack(string);
                    }
                } else {
                    return;
                }
            } else if (i == 32) {
                int length3 = fragmentArr.length;
                while (i2 < length3) {
                    Fragment fragment3 = fragmentArr[i2];
                    if (fragment3 != fragment) {
                        fragmentTransaction.remove(fragment3);
                    }
                    i2++;
                }
            } else if (i != 64) {
                switch (i) {
                    case 1:
                        int length4 = fragmentArr.length;
                        while (i2 < length4) {
                            Fragment fragment4 = fragmentArr[i2];
                            Bundle arguments2 = fragment4.getArguments();
                            if (arguments2 != null) {
                                String string2 = arguments2.getString("args_tag", fragment4.getClass().getName());
                                Fragment findFragmentByTag = fragmentManager.findFragmentByTag(string2);
                                if (findFragmentByTag != null && findFragmentByTag.isAdded()) {
                                    fragmentTransaction.remove(findFragmentByTag);
                                }
                                fragmentTransaction.add(arguments2.getInt("args_id"), fragment4, string2);
                                if (arguments2.getBoolean("args_is_hide")) {
                                    fragmentTransaction.hide(fragment4);
                                }
                                if (arguments2.getBoolean("args_is_add_stack")) {
                                    fragmentTransaction.addToBackStack(string2);
                                }
                                i2++;
                            } else {
                                return;
                            }
                        }
                        break;
                    case 2:
                        int length5 = fragmentArr.length;
                        while (i2 < length5) {
                            fragmentTransaction.show(fragmentArr[i2]);
                            i2++;
                        }
                        break;
                }
            } else {
                int length6 = fragmentArr.length - 1;
                while (true) {
                    if (length6 < 0) {
                        break;
                    }
                    Fragment fragment5 = fragmentArr[length6];
                    if (fragment5 != fragmentArr[0]) {
                        fragmentTransaction.remove(fragment5);
                        length6--;
                    } else if (fragment != null) {
                        fragmentTransaction.remove(fragment5);
                    }
                }
            }
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } else {
            Log.e("FragmentUtils", fragment.getClass().getName() + " is isRemoving");
        }
    }

    private static void a(FragmentTransaction fragmentTransaction, int i, int i2, int i3, int i4) {
        fragmentTransaction.setCustomAnimations(i, i2, i3, i4);
    }

    private static void a(FragmentTransaction fragmentTransaction, View... viewArr) {
        if (Build.VERSION.SDK_INT >= 21) {
            for (View view : viewArr) {
                fragmentTransaction.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    public static Fragment getTop(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            return a(fragmentManager, null, false);
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Fragment getTopInStack(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            return a(fragmentManager, null, true);
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static Fragment a(@NonNull FragmentManager fragmentManager, Fragment fragment, boolean z) {
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            for (int size = fragments.size() - 1; size >= 0; size--) {
                Fragment fragment2 = fragments.get(size);
                if (fragment2 != null) {
                    if (!z) {
                        return a(fragment2.getChildFragmentManager(), fragment2, false);
                    }
                    Bundle arguments = fragment2.getArguments();
                    if (arguments != null && arguments.getBoolean("args_is_add_stack")) {
                        return a(fragment2.getChildFragmentManager(), fragment2, true);
                    }
                }
            }
            return fragment;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Fragment getTopShow(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            return b(fragmentManager, null, false);
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Fragment getTopShowInStack(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            return b(fragmentManager, null, true);
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static Fragment b(@NonNull FragmentManager fragmentManager, Fragment fragment, boolean z) {
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            for (int size = fragments.size() - 1; size >= 0; size--) {
                Fragment fragment2 = fragments.get(size);
                if (fragment2 != null && fragment2.isResumed() && fragment2.isVisible() && fragment2.getUserVisibleHint()) {
                    if (!z) {
                        return b(fragment2.getChildFragmentManager(), fragment2, false);
                    }
                    Bundle arguments = fragment2.getArguments();
                    if (arguments != null && arguments.getBoolean("args_is_add_stack")) {
                        return b(fragment2.getChildFragmentManager(), fragment2, true);
                    }
                }
            }
            return fragment;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static List<Fragment> getFragments(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            List<Fragment> fragments = fragmentManager.getFragments();
            return (fragments == null || fragments.isEmpty()) ? Collections.emptyList() : fragments;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static List<Fragment> getFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        Bundle arguments;
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            ArrayList arrayList = new ArrayList();
            for (Fragment fragment : fragments) {
                if (!(fragment == null || (arguments = fragment.getArguments()) == null || !arguments.getBoolean("args_is_add_stack"))) {
                    arrayList.add(fragment);
                }
            }
            return arrayList;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static List<FragmentNode> getAllFragments(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            return a(fragmentManager, new ArrayList());
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static List<FragmentNode> a(@NonNull FragmentManager fragmentManager, List<FragmentNode> list) {
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            for (int size = fragments.size() - 1; size >= 0; size--) {
                Fragment fragment = fragments.get(size);
                if (fragment != null) {
                    list.add(new FragmentNode(fragment, a(fragment.getChildFragmentManager(), new ArrayList())));
                }
            }
            return list;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static List<FragmentNode> getAllFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            return b(fragmentManager, new ArrayList());
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static List<FragmentNode> b(@NonNull FragmentManager fragmentManager, List<FragmentNode> list) {
        Bundle arguments;
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            for (int size = fragments.size() - 1; size >= 0; size--) {
                Fragment fragment = fragments.get(size);
                if (!(fragment == null || (arguments = fragment.getArguments()) == null || !arguments.getBoolean("args_is_add_stack"))) {
                    list.add(new FragmentNode(fragment, b(fragment.getChildFragmentManager(), new ArrayList())));
                }
            }
            return list;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Fragment findFragment(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> cls) {
        if (fragmentManager != null) {
            return fragmentManager.findFragmentByTag(cls.getName());
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Fragment findFragment(@NonNull FragmentManager fragmentManager, @NonNull String str) {
        if (fragmentManager == null) {
            throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str != null) {
            return fragmentManager.findFragmentByTag(str);
        } else {
            throw new NullPointerException("Argument 'tag' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean dispatchBackPress(@NonNull Fragment fragment) {
        if (fragment != null) {
            return fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint() && (fragment instanceof OnBackClickListener) && ((OnBackClickListener) fragment).onBackClick();
        }
        throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean dispatchBackPress(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            List<Fragment> fragments = getFragments(fragmentManager);
            if (fragments == null || fragments.isEmpty()) {
                return false;
            }
            for (int size = fragments.size() - 1; size >= 0; size--) {
                Fragment fragment = fragments.get(size);
                if (fragment != null && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint() && (fragment instanceof OnBackClickListener) && ((OnBackClickListener) fragment).onBackClick()) {
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'fm' of type FragmentManager (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setBackgroundColor(@NonNull Fragment fragment, @ColorInt int i) {
        if (fragment != null) {
            View view = fragment.getView();
            if (view != null) {
                view.setBackgroundColor(i);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setBackgroundResource(@NonNull Fragment fragment, @DrawableRes int i) {
        if (fragment != null) {
            View view = fragment.getView();
            if (view != null) {
                view.setBackgroundResource(i);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setBackground(@NonNull Fragment fragment, Drawable drawable) {
        if (fragment != null) {
            View view = fragment.getView();
            if (view != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    view.setBackground(drawable);
                } else {
                    view.setBackgroundDrawable(drawable);
                }
            }
        } else {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String getSimpleName(Fragment fragment) {
        return fragment == null ? "null" : fragment.getClass().getSimpleName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        final int a;
        final boolean b;
        final boolean c;
        final String d;

        a(int i, boolean z, boolean z2) {
            this(i, null, z, z2);
        }

        a(int i, String str, boolean z, boolean z2) {
            this.a = i;
            this.d = str;
            this.b = z;
            this.c = z2;
        }
    }

    /* loaded from: classes.dex */
    public static class FragmentNode {
        final Fragment a;
        final List<FragmentNode> b;

        public FragmentNode(Fragment fragment, List<FragmentNode> list) {
            this.a = fragment;
            this.b = list;
        }

        public Fragment getFragment() {
            return this.a;
        }

        public List<FragmentNode> getNext() {
            return this.b;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a.getClass().getSimpleName());
            sb.append("->");
            List<FragmentNode> list = this.b;
            sb.append((list == null || list.isEmpty()) ? "no child" : this.b.toString());
            return sb.toString();
        }
    }
}
