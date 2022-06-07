package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class OsSwitchMultiInDeviceListBinding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout container;
    @NonNull
    public final ImageView ivDeviceTypePic;
    @NonNull
    public final ImageView ivDragHandle;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected boolean mIsLocationDeviceNameExchange;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final RecyclerView rvStatus;
    @NonNull
    public final TextView tvDeviceStatus;
    @NonNull
    public final TextView tvDeviceType;
    @NonNull
    public final TextView tvLocation;

    public abstract void setEditMode(boolean z);

    public abstract void setIsLocationDeviceNameExchange(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    /* JADX INFO: Access modifiers changed from: protected */
    public OsSwitchMultiInDeviceListBinding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, RecyclerView recyclerView, TextView textView, TextView textView2, TextView textView3) {
        super(dataBindingComponent, view, i);
        this.container = constraintLayout;
        this.ivDeviceTypePic = imageView;
        this.ivDragHandle = imageView2;
        this.rvStatus = recyclerView;
        this.tvDeviceStatus = textView;
        this.tvDeviceType = textView2;
        this.tvLocation = textView3;
    }

    @Nullable
    public DeviceInfoWrapper getItem() {
        return this.mItem;
    }

    public boolean getIsLocationDeviceNameExchange() {
        return this.mIsLocationDeviceNameExchange;
    }

    public boolean getEditMode() {
        return this.mEditMode;
    }

    @NonNull
    public static OsSwitchMultiInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static OsSwitchMultiInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (OsSwitchMultiInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.os_switch_multi_in_device_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static OsSwitchMultiInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static OsSwitchMultiInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (OsSwitchMultiInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.os_switch_multi_in_device_list, null, false, dataBindingComponent);
    }

    public static OsSwitchMultiInDeviceListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static OsSwitchMultiInDeviceListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (OsSwitchMultiInDeviceListBinding) bind(dataBindingComponent, view, R.layout.os_switch_multi_in_device_list);
    }
}
