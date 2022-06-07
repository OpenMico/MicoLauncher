package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class VideoControllerBarBindingImpl extends VideoControllerBarBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = new ViewDataBinding.IncludedLayouts(3);
    @Nullable
    private static final SparseIntArray c = null;
    @NonNull
    private final FrameLayout d;
    private long e;

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, @Nullable Object obj) {
        return true;
    }

    static {
        b.setIncludes(0, new String[]{"long_video_controller_bar", "short_video_controller_bar"}, new int[]{1, 2}, new int[]{R.layout.long_video_controller_bar, R.layout.short_video_controller_bar});
    }

    public VideoControllerBarBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 3, b, c));
    }

    private VideoControllerBarBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 2, (LongVideoControllerBarBinding) objArr[1], (ShortVideoControllerBarBinding) objArr[2]);
        this.e = -1L;
        this.d = (FrameLayout) objArr[0];
        this.d.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.e = 4L;
        }
        this.longVideoControllerBar.invalidateAll();
        this.shortVideoControllerBar.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.e != 0) {
                return true;
            }
            return this.longVideoControllerBar.hasPendingBindings() || this.shortVideoControllerBar.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(@Nullable LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.longVideoControllerBar.setLifecycleOwner(lifecycleOwner);
        this.shortVideoControllerBar.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        switch (i) {
            case 0:
                return a((ShortVideoControllerBarBinding) obj, i2);
            case 1:
                return a((LongVideoControllerBarBinding) obj, i2);
            default:
                return false;
        }
    }

    private boolean a(ShortVideoControllerBarBinding shortVideoControllerBarBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.e |= 1;
        }
        return true;
    }

    private boolean a(LongVideoControllerBarBinding longVideoControllerBarBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.e |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            long j = this.e;
            this.e = 0L;
        }
        executeBindingsOn(this.longVideoControllerBar);
        executeBindingsOn(this.shortVideoControllerBar);
    }
}
