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
public class ActivityBaikeX08BindingImpl extends ActivityBaikeX08Binding {
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
        b.setIncludes(0, new String[]{"view_baike_x08_normal", "view_baike_x08_single_image"}, new int[]{1, 2}, new int[]{R.layout.view_baike_x08_normal, R.layout.view_baike_x08_single_image});
    }

    public ActivityBaikeX08BindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 3, b, c));
    }

    private ActivityBaikeX08BindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 2, (ViewBaikeX08NormalBinding) objArr[1], (ViewBaikeX08SingleImageBinding) objArr[2]);
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
        this.normal.invalidateAll();
        this.singleImage.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.e != 0) {
                return true;
            }
            return this.normal.hasPendingBindings() || this.singleImage.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(@Nullable LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.normal.setLifecycleOwner(lifecycleOwner);
        this.singleImage.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        switch (i) {
            case 0:
                return a((ViewBaikeX08NormalBinding) obj, i2);
            case 1:
                return a((ViewBaikeX08SingleImageBinding) obj, i2);
            default:
                return false;
        }
    }

    private boolean a(ViewBaikeX08NormalBinding viewBaikeX08NormalBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.e |= 1;
        }
        return true;
    }

    private boolean a(ViewBaikeX08SingleImageBinding viewBaikeX08SingleImageBinding, int i) {
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
        executeBindingsOn(this.normal);
        executeBindingsOn(this.singleImage);
    }
}
