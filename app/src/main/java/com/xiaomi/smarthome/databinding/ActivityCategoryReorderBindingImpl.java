package com.xiaomi.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class ActivityCategoryReorderBindingImpl extends ActivityCategoryReorderBinding {
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
        c.put(R.id.action_bar, 1);
        c.put(R.id.title, 2);
        c.put(R.id.ivCancel, 3);
        c.put(R.id.ivCancelCircleBg, 4);
        c.put(R.id.tvDone, 5);
        c.put(R.id.rvCategoryList, 6);
        c.put(R.id.defaultBgIv, 7);
    }

    public ActivityCategoryReorderBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 8, b, c));
    }

    private ActivityCategoryReorderBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[1], (ImageView) objArr[7], (ImageView) objArr[3], (ImageView) objArr[4], (ConstraintLayout) objArr[0], (RecyclerView) objArr[6], (TextView) objArr[2], (TextView) objArr[5]);
        this.d = -1L;
        this.root.setTag(null);
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
