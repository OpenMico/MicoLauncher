package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class VideoControllerBarBinding extends ViewDataBinding {
    @NonNull
    public final LongVideoControllerBarBinding longVideoControllerBar;
    @NonNull
    public final ShortVideoControllerBarBinding shortVideoControllerBar;

    /* JADX INFO: Access modifiers changed from: protected */
    public VideoControllerBarBinding(DataBindingComponent dataBindingComponent, View view, int i, LongVideoControllerBarBinding longVideoControllerBarBinding, ShortVideoControllerBarBinding shortVideoControllerBarBinding) {
        super(dataBindingComponent, view, i);
        this.longVideoControllerBar = longVideoControllerBarBinding;
        setContainedBinding(this.longVideoControllerBar);
        this.shortVideoControllerBar = shortVideoControllerBarBinding;
        setContainedBinding(this.shortVideoControllerBar);
    }

    @NonNull
    public static VideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static VideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (VideoControllerBarBinding) DataBindingUtil.inflate(layoutInflater, R.layout.video_controller_bar, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static VideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static VideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (VideoControllerBarBinding) DataBindingUtil.inflate(layoutInflater, R.layout.video_controller_bar, null, false, dataBindingComponent);
    }

    public static VideoControllerBarBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static VideoControllerBarBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (VideoControllerBarBinding) bind(dataBindingComponent, view, R.layout.video_controller_bar);
    }
}
