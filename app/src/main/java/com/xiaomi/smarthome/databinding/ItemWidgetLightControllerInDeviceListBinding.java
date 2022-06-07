package com.xiaomi.smarthome.databinding;

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
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.VerticalSeekView;

/* loaded from: classes4.dex */
public abstract class ItemWidgetLightControllerInDeviceListBinding extends ViewDataBinding {
    @NonNull
    public final ImageView ivDevicePic;
    @NonNull
    public final ImageView ivDragHandle;
    @NonNull
    public final ImageView ivStatus;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final VerticalSeekView seekView;
    @NonNull
    public final TextView tvLocation;
    @NonNull
    public final TextView tvStatus;

    public abstract void setEditMode(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemWidgetLightControllerInDeviceListBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, ImageView imageView2, ImageView imageView3, VerticalSeekView verticalSeekView, TextView textView, TextView textView2) {
        super(dataBindingComponent, view, i);
        this.ivDevicePic = imageView;
        this.ivDragHandle = imageView2;
        this.ivStatus = imageView3;
        this.seekView = verticalSeekView;
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
    public static ItemWidgetLightControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemWidgetLightControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetLightControllerInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_widget_light_controller_in_device_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemWidgetLightControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemWidgetLightControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetLightControllerInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_widget_light_controller_in_device_list, null, false, dataBindingComponent);
    }

    public static ItemWidgetLightControllerInDeviceListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemWidgetLightControllerInDeviceListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetLightControllerInDeviceListBinding) bind(dataBindingComponent, view, R.layout.item_widget_light_controller_in_device_list);
    }
}
