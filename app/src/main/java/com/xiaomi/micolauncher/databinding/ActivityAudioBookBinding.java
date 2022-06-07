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
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.widget.NoScrollViewPager;

/* loaded from: classes3.dex */
public abstract class ActivityAudioBookBinding extends ViewDataBinding {
    @NonNull
    public final TextView emptyView;
    @NonNull
    public final ViewLoadingDialogBinding loadingView;
    @NonNull
    public final RecyclerView tab;
    @NonNull
    public final NoScrollViewPager viewpager;

    public ActivityAudioBookBinding(DataBindingComponent dataBindingComponent, View view, int i, TextView textView, ViewLoadingDialogBinding viewLoadingDialogBinding, RecyclerView recyclerView, NoScrollViewPager noScrollViewPager) {
        super(dataBindingComponent, view, i);
        this.emptyView = textView;
        this.loadingView = viewLoadingDialogBinding;
        setContainedBinding(this.loadingView);
        this.tab = recyclerView;
        this.viewpager = noScrollViewPager;
    }

    @NonNull
    public static ActivityAudioBookBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityAudioBookBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityAudioBookBinding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_audio_book, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ActivityAudioBookBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ActivityAudioBookBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityAudioBookBinding) DataBindingUtil.inflate(layoutInflater, R.layout.activity_audio_book, null, false, dataBindingComponent);
    }

    public static ActivityAudioBookBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAudioBookBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ActivityAudioBookBinding) bind(dataBindingComponent, view, R.layout.activity_audio_book);
    }
}
