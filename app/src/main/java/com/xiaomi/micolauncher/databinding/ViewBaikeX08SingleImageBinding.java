package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.view.RoundImageView;

/* loaded from: classes3.dex */
public abstract class ViewBaikeX08SingleImageBinding extends ViewDataBinding {
    @NonNull
    public final RoundImageView baikeIcon;
    @NonNull
    public final TextView baikeTitle;
    @NonNull
    public final ConstraintLayout iconContainer;

    /* JADX INFO: Access modifiers changed from: protected */
    public ViewBaikeX08SingleImageBinding(DataBindingComponent dataBindingComponent, View view, int i, RoundImageView roundImageView, TextView textView, ConstraintLayout constraintLayout) {
        super(dataBindingComponent, view, i);
        this.baikeIcon = roundImageView;
        this.baikeTitle = textView;
        this.iconContainer = constraintLayout;
    }

    @NonNull
    public static ViewBaikeX08SingleImageBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewBaikeX08SingleImageBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewBaikeX08SingleImageBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_baike_x08_single_image, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ViewBaikeX08SingleImageBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewBaikeX08SingleImageBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewBaikeX08SingleImageBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_baike_x08_single_image, null, false, dataBindingComponent);
    }

    public static ViewBaikeX08SingleImageBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ViewBaikeX08SingleImageBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewBaikeX08SingleImageBinding) bind(dataBindingComponent, view, R.layout.view_baike_x08_single_image);
    }
}
