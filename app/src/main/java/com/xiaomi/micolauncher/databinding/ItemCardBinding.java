package com.xiaomi.micolauncher.databinding;

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
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class ItemCardBinding extends ViewDataBinding {
    @NonNull
    public final ImageView ivBadge;
    @NonNull
    public final ImageView ivCover;
    @NonNull
    public final TextView tvDesc;
    @NonNull
    public final View vMaskBottom;
    @NonNull
    public final View vMaskTop;

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemCardBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, ImageView imageView2, TextView textView, View view2, View view3) {
        super(dataBindingComponent, view, i);
        this.ivBadge = imageView;
        this.ivCover = imageView2;
        this.tvDesc = textView;
        this.vMaskBottom = view2;
        this.vMaskTop = view3;
    }

    @NonNull
    public static ItemCardBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemCardBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemCardBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_card, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemCardBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemCardBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemCardBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_card, null, false, dataBindingComponent);
    }

    public static ItemCardBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCardBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemCardBinding) bind(dataBindingComponent, view, R.layout.item_card);
    }
}
