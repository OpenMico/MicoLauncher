package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public abstract class LayoutAccurateShotSingleCpBinding extends ViewDataBinding {
    @NonNull
    public final FrameLayout collapseView;
    @NonNull
    public final ImageView ivCover;
    @Bindable
    protected VideoItem mData;
    @NonNull
    public final RecyclerView rvPlaySource;
    @NonNull
    public final TextView tv;
    @NonNull
    public final TextView tvLabelPlaySource;
    @NonNull
    public final TextView tvResourceInfo;
    @NonNull
    public final TextView tvResourceName;
    @NonNull
    public final View vMaskBottom;
    @NonNull
    public final View vMaskTop;
    @NonNull
    public final ImageView vipMark;

    public abstract void setData(@Nullable VideoItem videoItem);

    /* JADX INFO: Access modifiers changed from: protected */
    public LayoutAccurateShotSingleCpBinding(DataBindingComponent dataBindingComponent, View view, int i, FrameLayout frameLayout, ImageView imageView, RecyclerView recyclerView, TextView textView, TextView textView2, TextView textView3, TextView textView4, View view2, View view3, ImageView imageView2) {
        super(dataBindingComponent, view, i);
        this.collapseView = frameLayout;
        this.ivCover = imageView;
        this.rvPlaySource = recyclerView;
        this.tv = textView;
        this.tvLabelPlaySource = textView2;
        this.tvResourceInfo = textView3;
        this.tvResourceName = textView4;
        this.vMaskBottom = view2;
        this.vMaskTop = view3;
        this.vipMark = imageView2;
    }

    @Nullable
    public VideoItem getData() {
        return this.mData;
    }

    @NonNull
    public static LayoutAccurateShotSingleCpBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static LayoutAccurateShotSingleCpBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (LayoutAccurateShotSingleCpBinding) DataBindingUtil.inflate(layoutInflater, R.layout.layout_accurate_shot_single_cp, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static LayoutAccurateShotSingleCpBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static LayoutAccurateShotSingleCpBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (LayoutAccurateShotSingleCpBinding) DataBindingUtil.inflate(layoutInflater, R.layout.layout_accurate_shot_single_cp, null, false, dataBindingComponent);
    }

    public static LayoutAccurateShotSingleCpBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutAccurateShotSingleCpBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (LayoutAccurateShotSingleCpBinding) bind(dataBindingComponent, view, R.layout.layout_accurate_shot_single_cp);
    }
}
