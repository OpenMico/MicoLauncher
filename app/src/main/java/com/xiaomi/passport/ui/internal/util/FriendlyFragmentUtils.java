package com.xiaomi.passport.ui.internal.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/* loaded from: classes4.dex */
public class FriendlyFragmentUtils {
    public static void addFragment(FragmentManager fragmentManager, int i, Fragment fragment) {
        addFragmentIfNonExist(fragmentManager, i, fragment);
    }

    public static Fragment addFragmentIfNonExist(FragmentManager fragmentManager, int i, Fragment fragment) {
        String name = fragment.getClass().getName();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(name);
        if (findFragmentByTag != null) {
            return findFragmentByTag;
        }
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(i, fragment, name);
        beginTransaction.commitAllowingStateLoss();
        return fragment;
    }

    public static void popUpFragment(Fragment fragment) {
        Activity activity = fragment.getActivity();
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }
}
