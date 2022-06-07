package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class ItemWidgetEnvironmentControllerInDeviceListBinding extends ViewDataBinding {
    @NonNull
    public final Group decreaseButton;
    @NonNull
    public final Group groupTemp;
    @NonNull
    public final Group increaseButton;
    @NonNull
    public final ImageView ivConditionMode;
    @NonNull
    public final ImageView ivDecrease;
    @NonNull
    public final ImageView ivDevicePic;
    @NonNull
    public final ImageView ivDragHandle;
    @NonNull
    public final ImageView ivIncrease;
    @NonNull
    public final ImageView ivStatus;
    @NonNull
    public final ImageView ivTempSymbol;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final TextView tvLocation;
    @NonNull
    public final TextView tvStatus;
    @NonNull
    public final TextView tvTemperature;
    @NonNull
    public final View vBgDecrease;
    @NonNull
    public final View vBgIncrease;

    public abstract void setEditMode(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemWidgetEnvironmentControllerInDeviceListBinding(DataBindingComponent dataBindingComponent, View view, int i, Group group, Group group2, Group group3, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, TextView textView, TextView textView2, TextView textView3, View view2, View view3) {
        super(dataBindingComponent, view, i);
        this.decreaseButton = group;
        this.groupTemp = group2;
        this.increaseButton = group3;
        this.ivConditionMode = imageView;
        this.ivDecrease = imageView2;
        this.ivDevicePic = imageView3;
        this.ivDragHandle = imageView4;
        this.ivIncrease = imageView5;
        this.ivStatus = imageView6;
        this.ivTempSymbol = imageView7;
        this.tvLocation = textView;
        this.tvStatus = textView2;
        this.tvTemperature = textView3;
        this.vBgDecrease = view2;
        this.vBgIncrease = view3;
    }

    @Nullable
    public DeviceInfoWrapper getItem() {
        return this.mItem;
    }

    public boolean getEditMode() {
        return this.mEditMode;
    }

    @NonNull
    public static ItemWidgetEnvironmentControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemWidgetEnvironmentControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetEnvironmentControllerInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_widget_environment_controller_in_device_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemWidgetEnvironmentControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemWidgetEnvironmentControllerInDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetEnvironmentControllerInDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_widget_environment_controller_in_device_list, null, false, dataBindingComponent);
    }

    public static ItemWidgetEnvironmentControllerInDeviceListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemWidgetEnvironmentControllerInDeviceListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemWidgetEnvironmentControllerInDeviceListBinding) bind(dataBindingComponent, view, R.layout.item_widget_environment_controller_in_device_list);
    }
}
