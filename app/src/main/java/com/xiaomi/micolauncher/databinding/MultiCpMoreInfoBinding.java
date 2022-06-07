package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class MultiCpMoreInfoBinding extends ViewDataBinding {
    @NonNull
    public final TextView tv;

    /* JADX INFO: Access modifiers changed from: protected */
    public MultiCpMoreInfoBinding(DataBindingComponent dataBindingComponent, View view, int i, TextView textView) {
        super(dataBindingComponent, view, i);
        this.tv = textView;
    }

    @NonNull
    public static MultiCpMoreInfoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static MultiCpMoreInfoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (MultiCpMoreInfoBinding) DataBindingUtil.inflate(layoutInflater, R.layout.multi_cp_more_info, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static MultiCpMoreInfoBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static MultiCpMoreInfoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (MultiCpMoreInfoBinding) DataBindingUtil.inflate(layoutInflater, R.layout.multi_cp_more_info, null, false, dataBindingComponent);
    }

    public static MultiCpMoreInfoBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static MultiCpMoreInfoBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (MultiCpMoreInfoBinding) bind(dataBindingComponent, view, R.layout.multi_cp_more_info);
    }
}
