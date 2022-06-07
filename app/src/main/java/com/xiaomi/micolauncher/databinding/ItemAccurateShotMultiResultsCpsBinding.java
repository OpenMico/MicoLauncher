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
import com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public abstract class ItemAccurateShotMultiResultsCpsBinding extends ViewDataBinding {
    @NonNull
    public final ContentProviderBar contentProviderBar;
    @NonNull
    public final ImageView ivCover;
    @NonNull
    public final TextView loadingTip;
    @Bindable
    protected VideoItem mItem;
    @NonNull
    public final TextView tvName;
    @NonNull
    public final View vCoverMask;
    @NonNull
    public final ImageView vipMark;

    public abstract void setItem(@Nullable VideoItem videoItem);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemAccurateShotMultiResultsCpsBinding(DataBindingComponent dataBindingComponent, View view, int i, ContentProviderBar contentProviderBar, ImageView imageView, TextView textView, TextView textView2, View view2, ImageView imageView2) {
        super(dataBindingComponent, view, i);
        this.contentProviderBar = contentProviderBar;
        this.ivCover = imageView;
        this.loadingTip = textView;
        this.tvName = textView2;
        this.vCoverMask = view2;
        this.vipMark = imageView2;
    }

    @Nullable
    public VideoItem getItem() {
        return this.mItem;
    }

    @NonNull
    public static ItemAccurateShotMultiResultsCpsBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemAccurateShotMultiResultsCpsBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemAccurateShotMultiResultsCpsBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_accurate_shot_multi_results_cps, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemAccurateShotMultiResultsCpsBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemAccurateShotMultiResultsCpsBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemAccurateShotMultiResultsCpsBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_accurate_shot_multi_results_cps, null, false, dataBindingComponent);
    }

    public static ItemAccurateShotMultiResultsCpsBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemAccurateShotMultiResultsCpsBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemAccurateShotMultiResultsCpsBinding) bind(dataBindingComponent, view, R.layout.item_accurate_shot_multi_results_cps);
    }
}
