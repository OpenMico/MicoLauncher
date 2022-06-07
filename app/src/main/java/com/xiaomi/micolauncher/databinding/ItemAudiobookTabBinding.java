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
public abstract class ItemAudiobookTabBinding extends ViewDataBinding {
    @NonNull
    public final TextView tvTabItem;

    /* JADX INFO: Access modifiers changed from: protected */
    public ItemAudiobookTabBinding(DataBindingComponent dataBindingComponent, View view, int i, TextView textView) {
        super(dataBindingComponent, view, i);
        this.tvTabItem = textView;
    }

    @NonNull
    public static ItemAudiobookTabBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemAudiobookTabBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemAudiobookTabBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_audiobook_tab, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ItemAudiobookTabBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ItemAudiobookTabBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemAudiobookTabBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_audiobook_tab, null, false, dataBindingComponent);
    }

    public static ItemAudiobookTabBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemAudiobookTabBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ItemAudiobookTabBinding) bind(dataBindingComponent, view, R.layout.item_audiobook_tab);
    }
}
