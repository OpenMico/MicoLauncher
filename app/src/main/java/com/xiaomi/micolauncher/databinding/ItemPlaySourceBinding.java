package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;

/* loaded from: classes3.dex */
public abstract class ItemPlaySourceBinding extends ViewDataBinding {
    @NonNull
    public final ImageView ivSourceLogo;
    @NonNull
    public final ImageView ivSourceStatus;
    @Bindable
    protected VideoContentProvider mCp;

    public abstract void setCp(@Nullable VideoContentProvider videoContentProvider);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemPlaySourceBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, ImageView imageView2) {
        super(dataBindingComponent, view, i);
        this.ivSourceLogo = imageView;
        this.ivSourceStatus = imageView2;
    }

    @Nullable
    public VideoContentProvider getCp() {
        return this.mCp;
    }

    @NonNull
    public static ItemPlaySourceBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemPlaySourceBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemPlaySourceBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_play_source, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemPlaySourceBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemPlaySourceBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemPlaySourceBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_play_source, null, false, dataBindingComponent);
    }

    public static ItemPlaySourceBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemPlaySourceBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemPlaySourceBinding) bind(dataBindingComponent, view, R.layout.item_play_source);
    }
}
