package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class HouseWorkSweepRobotBinding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout container;
    @NonNull
    public final Guideline guideLine;
    @NonNull
    public final ImageView ivCharge;
    @NonNull
    public final ImageView ivDragHandle;
    @NonNull
    public final ImageView ivOfflinePic;
    @NonNull
    public final ImageView ivPause;
    @NonNull
    public final ImageView ivStatus;
    @NonNull
    public final ImageView ivSweep;
    @NonNull
    public final ImageView ivSwitch;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected boolean mIsLocationDeviceNameExchange;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @Bindable
    protected String mStatus;
    @NonNull
    public final TextView tvDeviceType;
    @NonNull
    public final TextView tvLocation;
    @NonNull
    public final TextView tvOffline;
    @NonNull
    public final TextView tvStatus;

    public abstract void setEditMode(boolean z);

    public abstract void setIsLocationDeviceNameExchange(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    public abstract void setStatus(@Nullable String str);

    /* JADX INFO: Access modifiers changed from: protected */
    public HouseWorkSweepRobotBinding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, Guideline guideline, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(dataBindingComponent, view, i);
        this.container = constraintLayout;
        this.guideLine = guideline;
        this.ivCharge = imageView;
        this.ivDragHandle = imageView2;
        this.ivOfflinePic = imageView3;
        this.ivPause = imageView4;
        this.ivStatus = imageView5;
        this.ivSweep = imageView6;
        this.ivSwitch = imageView7;
        this.tvDeviceType = textView;
        this.tvLocation = textView2;
        this.tvOffline = textView3;
        this.tvStatus = textView4;
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

    @Nullable
    public String getStatus() {
        return this.mStatus;
    }

    @NonNull
    public static HouseWorkSweepRobotBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static HouseWorkSweepRobotBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (HouseWorkSweepRobotBinding) DataBindingUtil.inflate(layoutInflater, R.layout.house_work_sweep_robot, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static HouseWorkSweepRobotBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static HouseWorkSweepRobotBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (HouseWorkSweepRobotBinding) DataBindingUtil.inflate(layoutInflater, R.layout.house_work_sweep_robot, null, false, dataBindingComponent);
    }

    public static HouseWorkSweepRobotBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HouseWorkSweepRobotBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (HouseWorkSweepRobotBinding) bind(dataBindingComponent, view, R.layout.house_work_sweep_robot);
    }
}
