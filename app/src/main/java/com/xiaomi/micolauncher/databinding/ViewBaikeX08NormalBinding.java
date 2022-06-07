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
import com.xiaomi.micolauncher.skills.baike.view.BaikeTextView;

/* loaded from: classes3.dex */
public abstract class ViewBaikeX08NormalBinding extends ViewDataBinding {
    @NonNull
    public final RoundImageView baikeIcon;
    @NonNull
    public final BaikeTextView baikeTextView;
    @NonNull
    public final TextView baikeTitle;
    @NonNull
    public final ConstraintLayout countdownLayout;
    @NonNull
    public final ConstraintLayout iconContainer;
    @NonNull
    public final ConstraintLayout textContainer;

    public ViewBaikeX08NormalBinding(DataBindingComponent dataBindingComponent, View view, int i, RoundImageView roundImageView, BaikeTextView baikeTextView, TextView textView, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3) {
        super(dataBindingComponent, view, i);
        this.baikeIcon = roundImageView;
        this.baikeTextView = baikeTextView;
        this.baikeTitle = textView;
        this.countdownLayout = constraintLayout;
        this.iconContainer = constraintLayout2;
        this.textContainer = constraintLayout3;
    }

    @NonNull
    public static ViewBaikeX08NormalBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewBaikeX08NormalBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewBaikeX08NormalBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_baike_x08_normal, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ViewBaikeX08NormalBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewBaikeX08NormalBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewBaikeX08NormalBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_baike_x08_normal, null, false, dataBindingComponent);
    }

    public static ViewBaikeX08NormalBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ViewBaikeX08NormalBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewBaikeX08NormalBinding) bind(dataBindingComponent, view, R.layout.view_baike_x08_normal);
    }
}
