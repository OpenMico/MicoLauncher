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
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class FragmentSmartHomeModeCategoryBindingImpl extends FragmentSmartHomeModeCategoryBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = new SparseIntArray();
    @NonNull
    private final ConstraintLayout d;
    private long e;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, @Nullable Object obj) {
        return true;
    }

    static {
        c.put(R.id.tvFamilyInfo, 1);
        c.put(R.id.rvRefreshLayout, 2);
        c.put(R.id.rvIotDevices, 3);
        c.put(R.id.groupLoading, 4);
    }

    public FragmentSmartHomeModeCategoryBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 5, b, c));
    }

    private FragmentSmartHomeModeCategoryBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[4], (HorizontalRecyclerView) objArr[3], (HorizontalRefreshLayout) objArr[2], (TextView) objArr[1]);
        this.e = -1L;
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.e = 1L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.e != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            long j = this.e;
            this.e = 0L;
        }
    }
}
