package com.xiaomi.smarthome.core.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/* loaded from: classes4.dex */
public class ViewModelUtils {
    public static <T extends ViewModel> T obtainViewModel(Fragment fragment, Class<T> cls) {
        return (T) new ViewModelProvider(fragment, ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication())).get(cls);
    }

    public static <T extends ViewModel> T obtainViewModel(FragmentActivity fragmentActivity, Class<T> cls) {
        return (T) new ViewModelProvider(fragmentActivity, ViewModelProvider.AndroidViewModelFactory.getInstance(fragmentActivity.getApplication())).get(cls);
    }
}
