package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.widget.NoScrollViewPager;

/* loaded from: classes3.dex */
public class ActivityAudioBookBindingImpl extends ActivityAudioBookBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = new ViewDataBinding.IncludedLayouts(5);
    @Nullable
    private static final SparseIntArray c = new SparseIntArray();
    @NonNull
    private final ConstraintLayout d;
    private long e;

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, @Nullable Object obj) {
        return true;
    }

    static {
        b.setIncludes(0, new String[]{"view_loading_dialog"}, new int[]{1}, new int[]{R.layout.view_loading_dialog});
        c.put(R.id.tab, 2);
        c.put(R.id.viewpager, 3);
        c.put(R.id.emptyView, 4);
    }

    public ActivityAudioBookBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 5, b, c));
    }

    private ActivityAudioBookBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 1, (TextView) objArr[4], (ViewLoadingDialogBinding) objArr[1], (RecyclerView) objArr[2], (NoScrollViewPager) objArr[3]);
        this.e = -1L;
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.e = 2L;
        }
        this.loadingView.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.e != 0) {
                return true;
            }
            return this.loadingView.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(@Nullable LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.loadingView.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        if (i != 0) {
            return false;
        }
        return a((ViewLoadingDialogBinding) obj, i2);
    }

    private boolean a(ViewLoadingDialogBinding viewLoadingDialogBinding, int i) {
        if (i != 0) {
            return false;
        }
        synchronized (this) {
            this.e |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            long j = this.e;
            this.e = 0L;
        }
        executeBindingsOn(this.loadingView);
    }
}
