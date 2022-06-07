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
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.widgets.MagicTextView;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.DeviceListActivity;

/* loaded from: classes4.dex */
public abstract class ActivityDeviceListBinding extends ViewDataBinding {
    @NonNull
    public final Group groupButtons;
    @NonNull
    public final ImageView ivBack;
    @NonNull
    public final ImageView ivBackCircleBg;
    @NonNull
    public final ImageView ivCancel;
    @NonNull
    public final TextView ivDone;
    @NonNull
    public final ImageView ivEdit;
    @NonNull
    public final ImageView ivEditCircleBg;
    @Bindable
    protected String mCategory;
    @Bindable
    protected boolean mHideButtons;
    @Bindable
    protected boolean mIsX6A;
    @Bindable
    protected DeviceListActivity.Mode mMode;
    @NonNull
    public final TextView recyclerViewTitle;
    @NonNull
    public final RecyclerView rvDeviceList;
    @NonNull
    public final TextView title;
    @NonNull
    public final MagicTextView tvCloseAll;
    @NonNull
    public final MagicTextView tvOpenAll;
    @NonNull
    public final TextView tvSortTitle;
    @NonNull
    public final TextView tvTitle;

    public abstract void setCategory(@Nullable String str);

    public abstract void setHideButtons(boolean z);

    public abstract void setIsX6A(boolean z);

    public abstract void setMode(@Nullable DeviceListActivity.Mode mode);

    /* JADX INFO: Access modifiers changed from: protected */
    public ActivityDeviceListBinding(DataBindingComponent dataBindingComponent, View view, int i, Group group, ImageView imageView, ImageView imageView2, ImageView imageView3, TextView textView, ImageView imageView4, ImageView imageView5, TextView textView2, RecyclerView recyclerView, TextView textView3, MagicTextView magicTextView, MagicTextView magicTextView2, TextView textView4, TextView textView5) {
        super(dataBindingComponent, view, i);
        this.groupButtons = group;
        this.ivBack = imageView;
        this.ivBackCircleBg = imageView2;
        this.ivCancel = imageView3;
        this.ivDone = textView;
        this.ivEdit = imageView4;
        this.ivEditCircleBg = imageView5;
        this.recyclerViewTitle = textView2;
        this.rvDeviceList = recyclerView;
        this.title = textView3;
        this.tvCloseAll = magicTextView;
        this.tvOpenAll = magicTextView2;
        this.tvSortTitle = textView4;
        this.tvTitle = textView5;
    }

    @Nullable
    public DeviceListActivity.Mode getMode() {
        return this.mMode;
    }

    public boolean getHideButtons() {
        return this.mHideButtons;
    }

    @Nullable
    public String getCategory() {
        return this.mCategory;
    }

    public boolean getIsX6A() {
        return this.mIsX6A;
    }

    @NonNull
    public static ActivityDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_device_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ActivityDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityDeviceListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityDeviceListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_device_list, null, false, dataBindingComponent);
    }

    public static ActivityDeviceListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityDeviceListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityDeviceListBinding) bind(dataBindingComponent, view, R.layout.activity_device_list);
    }
}
