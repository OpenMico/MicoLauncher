package com.xiaomi.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class ShortCardWidgetLightControllerBinding extends ViewDataBinding {
    @NonNull
    public final View brightnessConstraintLayout;
    @NonNull
    public final View colorTempConstraintLayout;
    @NonNull
    public final View deviceConstraintLayout;

    /* JADX INFO: Access modifiers changed from: protected */
    public ShortCardWidgetLightControllerBinding(DataBindingComponent dataBindingComponent, View view, int i, View view2, View view3, View view4) {
        super(dataBindingComponent, view, i);
        this.brightnessConstraintLayout = view2;
        this.colorTempConstraintLayout = view3;
        this.deviceConstraintLayout = view4;
    }

    @NonNull
    public static ShortCardWidgetLightControllerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ShortCardWidgetLightControllerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ShortCardWidgetLightControllerBinding) DataBindingUtil.inflate(layoutInflater, R.layout.short_card_widget_light_controller, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ShortCardWidgetLightControllerBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ShortCardWidgetLightControllerBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ShortCardWidgetLightControllerBinding) DataBindingUtil.inflate(layoutInflater, R.layout.short_card_widget_light_controller, null, false, dataBindingComponent);
    }

    public static ShortCardWidgetLightControllerBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ShortCardWidgetLightControllerBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ShortCardWidgetLightControllerBinding) bind(dataBindingComponent, view, R.layout.short_card_widget_light_controller);
    }
}
