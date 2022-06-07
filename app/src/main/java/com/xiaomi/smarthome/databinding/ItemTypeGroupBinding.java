package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class ItemTypeGroupBinding extends ViewDataBinding {
    @NonNull
    public final TextView groupLabel;
    @NonNull
    public final RecyclerView rvDevices;

    public ItemTypeGroupBinding(DataBindingComponent dataBindingComponent, View view, int i, TextView textView, RecyclerView recyclerView) {
        super(dataBindingComponent, view, i);
        this.groupLabel = textView;
        this.rvDevices = recyclerView;
    }

    @NonNull
    public static ItemTypeGroupBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemTypeGroupBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemTypeGroupBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_type_group, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemTypeGroupBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemTypeGroupBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemTypeGroupBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_type_group, null, false, dataBindingComponent);
    }

    public static ItemTypeGroupBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemTypeGroupBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemTypeGroupBinding) bind(dataBindingComponent, view, R.layout.item_type_group);
    }
}
