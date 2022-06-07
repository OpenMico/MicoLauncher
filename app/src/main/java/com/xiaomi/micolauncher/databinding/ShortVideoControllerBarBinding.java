package com.xiaomi.micolauncher.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public abstract class ShortVideoControllerBarBinding extends ViewDataBinding {
    @NonNull
    public final ImageView ivPlayState;
    @NonNull
    public final SeekBar seekBar;
    @NonNull
    public final LinearLayout seekBarContainer;
    @NonNull
    public final TextView slash;
    @NonNull
    public final TextView tvDurationTime;
    @NonNull
    public final TextView tvSeekTime;

    public ShortVideoControllerBarBinding(DataBindingComponent dataBindingComponent, View view, int i, ImageView imageView, SeekBar seekBar, LinearLayout linearLayout, TextView textView, TextView textView2, TextView textView3) {
        super(dataBindingComponent, view, i);
        this.ivPlayState = imageView;
        this.seekBar = seekBar;
        this.seekBarContainer = linearLayout;
        this.slash = textView;
        this.tvDurationTime = textView2;
        this.tvSeekTime = textView3;
    }

    @NonNull
    public static ShortVideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ShortVideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (ShortVideoControllerBarBinding) DataBindingUtil.inflate(layoutInflater, R.layout.short_video_controller_bar, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static ShortVideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static ShortVideoControllerBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return (ShortVideoControllerBarBinding) DataBindingUtil.inflate(layoutInflater, R.layout.short_video_controller_bar, null, false, dataBindingComponent);
    }

    public static ShortVideoControllerBarBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ShortVideoControllerBarBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        return (ShortVideoControllerBarBinding) bind(dataBindingComponent, view, R.layout.short_video_controller_bar);
    }
}
