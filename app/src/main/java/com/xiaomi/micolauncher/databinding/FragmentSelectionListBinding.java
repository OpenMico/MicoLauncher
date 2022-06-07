package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class FragmentSelectionListBinding extends ViewDataBinding {
    @NonNull
    public final RecyclerView rvSelections;
    @NonNull
    public final View vBgLeft;
    @NonNull
    public final View vBgRight;

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentSelectionListBinding(DataBindingComponent dataBindingComponent, View view, int i, RecyclerView recyclerView, View view2, View view3) {
        super(dataBindingComponent, view, i);
        this.rvSelections = recyclerView;
        this.vBgLeft = view2;
        this.vBgRight = view3;
    }

    @NonNull
    public static FragmentSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSelectionListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_selection_list, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static FragmentSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentSelectionListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSelectionListBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_selection_list, null, false, dataBindingComponent);
    }

    public static FragmentSelectionListBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentSelectionListBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentSelectionListBinding) bind(dataBindingComponent, view, R.layout.fragment_selection_list);
    }
}
