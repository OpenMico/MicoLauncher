package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class ViewLoadingDialogBinding extends ViewDataBinding {
    @NonNull
    public final TextView loadingTv;
    @NonNull
    public final LottieAnimationView lottieView;
    @NonNull
    public final ImageView retryBtn;
    @NonNull
    public final LinearLayout viewGroup;

    public ViewLoadingDialogBinding(DataBindingComponent dataBindingComponent, View view, int i, TextView textView, LottieAnimationView lottieAnimationView, ImageView imageView, LinearLayout linearLayout) {
        super(dataBindingComponent, view, i);
        this.loadingTv = textView;
        this.lottieView = lottieAnimationView;
        this.retryBtn = imageView;
        this.viewGroup = linearLayout;
    }

    @NonNull
    public static ViewLoadingDialogBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewLoadingDialogBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewLoadingDialogBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_loading_dialog, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ViewLoadingDialogBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewLoadingDialogBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewLoadingDialogBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_loading_dialog, null, false, dataBindingComponent);
    }

    public static ViewLoadingDialogBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ViewLoadingDialogBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewLoadingDialogBinding) bind(dataBindingComponent, view, R.layout.view_loading_dialog);
    }
}
