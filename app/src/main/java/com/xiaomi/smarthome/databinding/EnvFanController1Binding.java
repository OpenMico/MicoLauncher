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
import com.xiaomi.smarthome.ui.widgets.VerticalShiftChooser;

/* loaded from: classes4.dex */
public abstract class EnvFanController1Binding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout container;
    @NonNull
    public final ImageView ivDevicePic;
    @NonNull
    public final ImageView ivDragHandle;
    @NonNull
    public final ImageView ivStatus;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected boolean mIsLocationDeviceNameExchange;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final VerticalShiftChooser seekView;
    @NonNull
    public final TextView tvDeviceStatus;
    @NonNull
    public final TextView tvDeviceType;
    @NonNull
    public final TextView tvLocation;

    public abstract void setEditMode(boolean z);

    public abstract void setIsLocationDeviceNameExchange(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    public EnvFanController1Binding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, VerticalShiftChooser verticalShiftChooser, TextView textView, TextView textView2, TextView textView3) {
        super(dataBindingComponent, view, i);
        this.container = constraintLayout;
        this.ivDevicePic = imageView;
        this.ivDragHandle = imageView2;
        this.ivStatus = imageView3;
        this.seekView = verticalShiftChooser;
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
    public static EnvFanController1Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static EnvFanController1Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (EnvFanController1Binding) DataBindingUtil.inflate(layoutInflater, R.layout.env_fan_controller1, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static EnvFanController1Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static EnvFanController1Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (EnvFanController1Binding) DataBindingUtil.inflate(layoutInflater, R.layout.env_fan_controller1, null, false, dataBindingComponent);
    }

    public static EnvFanController1Binding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static EnvFanController1Binding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (EnvFanController1Binding) bind(dataBindingComponent, view, R.layout.env_fan_controller1);
    }
}
