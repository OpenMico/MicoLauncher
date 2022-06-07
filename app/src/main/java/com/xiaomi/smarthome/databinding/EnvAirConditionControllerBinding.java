package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class EnvAirConditionControllerBinding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout container;
    @NonNull
    public final Group groupTemp;
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
    protected boolean mIsLocationDeviceNameExchange;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final TextView tvDeviceStatus;
    @NonNull
    public final TextView tvDeviceType;
    @NonNull
    public final TextView tvLocation;
    @NonNull
    public final TextView tvTemperature;

    public abstract void setEditMode(boolean z);

    public abstract void setIsLocationDeviceNameExchange(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    /* JADX INFO: Access modifiers changed from: protected */
    public EnvAirConditionControllerBinding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, Group group, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(dataBindingComponent, view, i);
        this.container = constraintLayout;
        this.groupTemp = group;
        this.ivConditionMode = imageView;
        this.ivDecrease = imageView2;
        this.ivDevicePic = imageView3;
        this.ivDragHandle = imageView4;
        this.ivIncrease = imageView5;
        this.ivStatus = imageView6;
        this.ivTempSymbol = imageView7;
        this.tvDeviceStatus = textView;
        this.tvDeviceType = textView2;
        this.tvLocation = textView3;
        this.tvTemperature = textView4;
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
    public static EnvAirConditionControllerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static EnvAirConditionControllerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (EnvAirConditionControllerBinding) DataBindingUtil.inflate(layoutInflater, R.layout.env_air_condition_controller, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static EnvAirConditionControllerBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static EnvAirConditionControllerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (EnvAirConditionControllerBinding) DataBindingUtil.inflate(layoutInflater, R.layout.env_air_condition_controller, null, false, dataBindingComponent);
    }

    public static EnvAirConditionControllerBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static EnvAirConditionControllerBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (EnvAirConditionControllerBinding) bind(dataBindingComponent, view, R.layout.env_air_condition_controller);
    }
}
