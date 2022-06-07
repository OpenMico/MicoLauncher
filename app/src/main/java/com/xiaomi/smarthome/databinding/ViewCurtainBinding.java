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
public abstract class ViewCurtainBinding extends ViewDataBinding {
    @NonNull
    public final ConstraintLayout container;
    @NonNull
    public final Group groupOnline;
    @NonNull
    public final ImageView ivClose;
    @NonNull
    public final ImageView ivDragHandle;
    @NonNull
    public final ImageView ivOfflinePic;
    @NonNull
    public final ImageView ivOpen;
    @NonNull
    public final ImageView ivPause;
    @NonNull
    public final ImageView ivStatus;
    @Bindable
    protected boolean mEditMode;
    @Bindable
    protected boolean mIsLocationDeviceNameExchange;
    @Bindable
    protected DeviceInfoWrapper mItem;
    @NonNull
    public final TextView tvDeviceType;
    @NonNull
    public final TextView tvLocation;
    @NonNull
    public final TextView tvOffline;

    public abstract void setEditMode(boolean z);

    public abstract void setIsLocationDeviceNameExchange(boolean z);

    public abstract void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper);

    /* JADX INFO: Access modifiers changed from: protected */
    public ViewCurtainBinding(DataBindingComponent dataBindingComponent, View view, int i, ConstraintLayout constraintLayout, Group group, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, TextView textView, TextView textView2, TextView textView3) {
        super(dataBindingComponent, view, i);
        this.container = constraintLayout;
        this.groupOnline = group;
        this.ivClose = imageView;
        this.ivDragHandle = imageView2;
        this.ivOfflinePic = imageView3;
        this.ivOpen = imageView4;
        this.ivPause = imageView5;
        this.ivStatus = imageView6;
        this.tvDeviceType = textView;
        this.tvLocation = textView2;
        this.tvOffline = textView3;
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
    public static ViewCurtainBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewCurtainBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewCurtainBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_curtain, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ViewCurtainBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ViewCurtainBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewCurtainBinding) DataBindingUtil.inflate(layoutInflater, R.layout.view_curtain, null, false, dataBindingComponent);
    }

    public static ViewCurtainBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ViewCurtainBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ViewCurtainBinding) bind(dataBindingComponent, view, R.layout.view_curtain);
    }
}
