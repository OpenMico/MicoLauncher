package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public abstract class ItemSelectionListBinding extends ViewDataBinding {
    @NonNull
    public final ImageView ivBadge;
    @NonNull
    public final ImageView ivPlaying;
    @Bindable
    protected VideoItem mItem;
    @NonNull
    public final TextView tvNo;
    @NonNull
    public final View vBg;

    public abstract void setItem(@Nullable VideoItem videoItem);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemSelectionListBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, ImageView imageView2, TextView textView, View view2) {
        super(dataBindingComponent, view, i);
        this.ivBadge = imageView;
        this.ivPlaying = imageView2;
        this.tvNo = textView;
        this.vBg = view2;
    }

    @Nullable
    public VideoItem getItem() {
        return this.mItem;
    }

    @NonNull
    public static ItemSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemSelectionListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_selection_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemSelectionListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_selection_list, null, false, dataBindingComponent);
    }

    public static ItemSelectionListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemSelectionListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemSelectionListBinding) bind(dataBindingComponent, view, R.layout.item_selection_list);
    }
}
