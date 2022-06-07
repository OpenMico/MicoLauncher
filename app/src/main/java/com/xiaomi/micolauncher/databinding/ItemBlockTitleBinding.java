package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class ItemBlockTitleBinding extends ViewDataBinding {
    @NonNull
    public final View line1;
    @NonNull
    public final View line2;
    @NonNull
    public final TextView tvTitle;

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemBlockTitleBinding(DataBindingComponent dataBindingComponent, View view, int i, View view2, View view3, TextView textView) {
        super(dataBindingComponent, view, i);
        this.line1 = view2;
        this.line2 = view3;
        this.tvTitle = textView;
    }

    @NonNull
    public static ItemBlockTitleBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemBlockTitleBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemBlockTitleBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_block_title, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemBlockTitleBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemBlockTitleBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemBlockTitleBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_block_title, null, false, dataBindingComponent);
    }

    public static ItemBlockTitleBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemBlockTitleBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemBlockTitleBinding) bind(dataBindingComponent, view, R.layout.item_block_title);
    }
}
