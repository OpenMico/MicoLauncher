package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class FragmentSmartHomeModeRoomBinding extends ViewDataBinding {
    @NonNull
    public final View column11;
    @NonNull
    public final View column12;
    @NonNull
    public final View column21;
    @NonNull
    public final View column22;
    @NonNull
    public final View column31;
    @NonNull
    public final View column32;
    @NonNull
    public final View column41;
    @NonNull
    public final View column42;
    @NonNull
    public final HorizontalRecyclerView devicesView;
    @NonNull
    public final RelativeLayout roomLoadingView;
    @NonNull
    public final RecyclerView roomView;
    @NonNull
    public final HorizontalRefreshLayout rvRefreshLayout;

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentSmartHomeModeRoomBinding(DataBindingComponent dataBindingComponent, View view, int i, View view2, View view3, View view4, View view5, View view6, View view7, View view8, View view9, HorizontalRecyclerView horizontalRecyclerView, RelativeLayout relativeLayout, RecyclerView recyclerView, HorizontalRefreshLayout horizontalRefreshLayout) {
        super(dataBindingComponent, view, i);
        this.column11 = view2;
        this.column12 = view3;
        this.column21 = view4;
        this.column22 = view5;
        this.column31 = view6;
        this.column32 = view7;
        this.column41 = view8;
        this.column42 = view9;
        this.devicesView = horizontalRecyclerView;
        this.roomLoadingView = relativeLayout;
        this.roomView = recyclerView;
        this.rvRefreshLayout = horizontalRefreshLayout;
    }

    @NonNull
    public static FragmentSmartHomeModeRoomBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentSmartHomeModeRoomBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSmartHomeModeRoomBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_smart_home_mode_room, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static FragmentSmartHomeModeRoomBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentSmartHomeModeRoomBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSmartHomeModeRoomBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_smart_home_mode_room, null, false, dataBindingComponent);
    }

    public static FragmentSmartHomeModeRoomBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentSmartHomeModeRoomBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSmartHomeModeRoomBinding) bind(dataBindingComponent, view, R.layout.fragment_smart_home_mode_room);
    }
}
