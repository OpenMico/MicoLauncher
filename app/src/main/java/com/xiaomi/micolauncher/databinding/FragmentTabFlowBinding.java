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
import androidx.databinding.ViewStubProxy;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class FragmentTabFlowBinding extends ViewDataBinding {
    @NonNull
    public final TextView emptyView;
    @NonNull
    public final ViewStubProxy errorView;
    @NonNull
    public final View loading;
    @NonNull
    public final RecyclerView rvCardList;

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentTabFlowBinding(DataBindingComponent dataBindingComponent, View view, int i, TextView textView, ViewStubProxy viewStubProxy, View view2, RecyclerView recyclerView) {
        super(dataBindingComponent, view, i);
        this.emptyView = textView;
        this.errorView = viewStubProxy;
        this.loading = view2;
        this.rvCardList = recyclerView;
    }

    @NonNull
    public static FragmentTabFlowBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentTabFlowBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentTabFlowBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_tab_flow, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static FragmentTabFlowBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static FragmentTabFlowBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentTabFlowBinding) DataBindingUtil.inflate(layoutInflater, R.layout.fragment_tab_flow, null, false, dataBindingComponent);
    }

    public static FragmentTabFlowBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentTabFlowBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (FragmentTabFlowBinding) bind(dataBindingComponent, view, R.layout.fragment_tab_flow);
    }
}
