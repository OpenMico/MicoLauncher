package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class ActivityBaikeX08Binding extends ViewDataBinding {
    @NonNull
    public final ViewBaikeX08NormalBinding normal;
    @NonNull
    public final ViewBaikeX08SingleImageBinding singleImage;

    public ActivityBaikeX08Binding(DataBindingComponent dataBindingComponent, View view, int i, ViewBaikeX08NormalBinding viewBaikeX08NormalBinding, ViewBaikeX08SingleImageBinding viewBaikeX08SingleImageBinding) {
        super(dataBindingComponent, view, i);
        this.normal = viewBaikeX08NormalBinding;
        setContainedBinding(this.normal);
        this.singleImage = viewBaikeX08SingleImageBinding;
        setContainedBinding(this.singleImage);
    }

    @NonNull
    public static ActivityBaikeX08Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityBaikeX08Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityBaikeX08Binding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_baike_x08, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ActivityBaikeX08Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityBaikeX08Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityBaikeX08Binding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_baike_x08, null, false, dataBindingComponent);
    }

    public static ActivityBaikeX08Binding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityBaikeX08Binding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityBaikeX08Binding) bind(dataBindingComponent, view, R.layout.activity_baike_x08);
    }
}
