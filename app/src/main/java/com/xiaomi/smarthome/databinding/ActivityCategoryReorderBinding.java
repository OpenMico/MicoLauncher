package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class ActivityCategoryReorderBinding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout actionBar;
    @NonNull
    public final ImageView defaultBgIv;
    @NonNull
    public final ImageView ivCancel;
    @NonNull
    public final ImageView ivCancelCircleBg;
    @NonNull
    public final ConstraintLayout root;
    @NonNull
    public final RecyclerView rvCategoryList;
    @NonNull
    public final TextView title;
    @NonNull
    public final TextView tvDone;

    public ActivityCategoryReorderBinding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, ConstraintLayout constraintLayout2, RecyclerView recyclerView, TextView textView, TextView textView2) {
        super(dataBindingComponent, view, i);
        this.actionBar = constraintLayout;
        this.defaultBgIv = imageView;
        this.ivCancel = imageView2;
        this.ivCancelCircleBg = imageView3;
        this.root = constraintLayout2;
        this.rvCategoryList = recyclerView;
        this.title = textView;
        this.tvDone = textView2;
    }

    @NonNull
    public static ActivityCategoryReorderBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityCategoryReorderBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityCategoryReorderBinding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_category_reorder, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ActivityCategoryReorderBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityCategoryReorderBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityCategoryReorderBinding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_category_reorder, null, false, dataBindingComponent);
    }

    public static ActivityCategoryReorderBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCategoryReorderBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityCategoryReorderBinding) bind(dataBindingComponent, view, R.layout.activity_category_reorder);
    }
}
