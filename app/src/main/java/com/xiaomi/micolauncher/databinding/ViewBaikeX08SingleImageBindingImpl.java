package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.view.RoundImageView;

/* loaded from: classes3.dex */
public class ViewBaikeX08SingleImageBindingImpl extends ViewBaikeX08SingleImageBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = new SparseIntArray();
    private long d;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, @Nullable Object obj) {
        return true;
    }

    static {
        c.put(R.id.baikeIcon, 1);
        c.put(R.id.baikeTitle, 2);
    }

    public ViewBaikeX08SingleImageBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 3, b, c));
    }

    private ViewBaikeX08SingleImageBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (RoundImageView) objArr[1], (TextView) objArr[2], (ConstraintLayout) objArr[0]);
        this.d = -1L;
        this.iconContainer.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.d = 1L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.d != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            long j = this.d;
            this.d = 0L;
        }
    }
}
