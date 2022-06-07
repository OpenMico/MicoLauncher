package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class WidgetCpsBarBinding extends ViewDataBinding {
    @NonNull
    public final ImageView ivArrow;
    @NonNull
    public final RecyclerView rv;

    /* JADX INFO: Access modifiers changed from: protected */
    public WidgetCpsBarBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, RecyclerView recyclerView) {
        super(dataBindingComponent, view, i);
        this.ivArrow = imageView;
        this.rv = recyclerView;
    }

    @NonNull
    public static WidgetCpsBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static WidgetCpsBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (WidgetCpsBarBinding) DataBindingUtil.inflate(layoutInflater, R.layout.widget_cps_bar, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static WidgetCpsBarBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static WidgetCpsBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (WidgetCpsBarBinding) DataBindingUtil.inflate(layoutInflater, R.layout.widget_cps_bar, null, false, dataBindingComponent);
    }

    public static WidgetCpsBarBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WidgetCpsBarBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (WidgetCpsBarBinding) bind(dataBindingComponent, view, R.layout.widget_cps_bar);
    }
}
