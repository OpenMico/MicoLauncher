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
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class ItemWidgetSocketControllerInDeviceListBinding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout container;
    @NonNull
    public final ImageView ivDeviceTypePic;
    @NonNull
    public final ImageView ivDragHandle;
    @NonNull
    public final ImageView ivStatus;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final TextView tvLocation;
    @NonNull
    public final TextView tvStatus;

    public abstract void setEditMode(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemWidgetSocketControllerInDeviceListBinding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, TextView textView, TextView textView2) {
        super(dataBindingComponent, view, i);
        this.container = constraintLayout;
        this.ivDeviceTypePic = imageView;
        this.ivDragHandle = imageView2;
        this.ivStatus = imageView3;
        this.tvLocation = textView;
        this.tvStatus = textView2;
    }

    @Nullable
    public DeviceInfoWrapper getItem() {
        return this.mItem;
    }

    public boolean getEditMode() {
        return this.mEditMode;
    }

    @NonNull
    public static ItemWidgetSocketControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemWidgetSocketControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetSocketControllerInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_widget_socket_controller_in_device_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemWidgetSocketControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemWidgetSocketControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetSocketControllerInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_widget_socket_controller_in_device_list, null, false, dataBindingComponent);
    }

    public static ItemWidgetSocketControllerInDeviceListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemWidgetSocketControllerInDeviceListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetSocketControllerInDeviceListBinding) bind(dataBindingComponent, view, R.layout.item_widget_socket_controller_in_device_list);
    }
}
