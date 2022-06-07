package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class LayoutAccurateShotMultiCpBinding extends ViewDataBinding {
    @NonNull
    public final FrameLayout collapseView;
    @NonNull
    public final RecyclerView rv;
    @NonNull
    public final TextView tv;
    @NonNull
    public final View vMaskBottom;
    @NonNull
    public final View vMaskTop;

    /* JADX INFO: Access modifiers changed from: protected */
    public LayoutAccurateShotMultiCpBinding(DataBindingComponent dataBindingComponent, View view, int i, FrameLayout frameLayout, RecyclerView recyclerView, TextView textView, View view2, View view3) {
        super(dataBindingComponent, view, i);
        this.collapseView = frameLayout;
        this.rv = recyclerView;
        this.tv = textView;
        this.vMaskBottom = view2;
        this.vMaskTop = view3;
    }

    @NonNull
    public static LayoutAccurateShotMultiCpBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static LayoutAccurateShotMultiCpBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (LayoutAccurateShotMultiCpBinding) DataBindingUtil.inflate(layoutInflater, R.layout.layout_accurate_shot_multi_cp, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static LayoutAccurateShotMultiCpBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static LayoutAccurateShotMultiCpBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (LayoutAccurateShotMultiCpBinding) DataBindingUtil.inflate(layoutInflater, R.layout.layout_accurate_shot_multi_cp, null, false, dataBindingComponent);
    }

    public static LayoutAccurateShotMultiCpBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutAccurateShotMultiCpBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (LayoutAccurateShotMultiCpBinding) bind(dataBindingComponent, view, R.layout.layout_accurate_shot_multi_cp);
    }
}
