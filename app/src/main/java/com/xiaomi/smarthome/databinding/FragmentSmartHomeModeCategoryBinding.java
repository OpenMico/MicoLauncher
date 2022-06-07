package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class FragmentSmartHomeModeCategoryBinding extends ViewDataBinding {
    @NonNull
    public final ImageView groupLoading;
    @NonNull
    public final HorizontalRecyclerView rvIotDevices;
    @NonNull
    public final HorizontalRefreshLayout rvRefreshLayout;
    @NonNull
    public final TextView tvFamilyInfo;

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentSmartHomeModeCategoryBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, HorizontalRecyclerView horizontalRecyclerView, HorizontalRefreshLayout horizontalRefreshLayout, TextView textView) {
        super(dataBindingComponent, view, i);
        this.groupLoading = imageView;
        this.rvIotDevices = horizontalRecyclerView;
        this.rvRefreshLayout = horizontalRefreshLayout;
        this.tvFamilyInfo = textView;
    }

    @NonNull
    public static FragmentSmartHomeModeCategoryBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentSmartHomeModeCategoryBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSmartHomeModeCategoryBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_smart_home_mode_category, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static FragmentSmartHomeModeCategoryBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentSmartHomeModeCategoryBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSmartHomeModeCategoryBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_smart_home_mode_category, null, false, dataBindingComponent);
    }

    public static FragmentSmartHomeModeCategoryBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentSmartHomeModeCategoryBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSmartHomeModeCategoryBinding) bind(dataBindingComponent, view, R.layout.fragment_smart_home_mode_category);
    }
}
