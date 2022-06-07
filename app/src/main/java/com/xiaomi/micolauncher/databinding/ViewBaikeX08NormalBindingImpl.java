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
import com.xiaomi.micolauncher.skills.baike.view.BaikeTextView;

/* loaded from: classes3.dex */
public class ViewBaikeX08NormalBindingImpl extends ViewBaikeX08NormalBinding {
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
        c.put(R.id.iconContainer, 1);
        c.put(R.id.baikeIcon, 2);
        c.put(R.id.textContainer, 3);
        c.put(R.id.baikeTitle, 4);
        c.put(R.id.baikeTextView, 5);
    }

    public ViewBaikeX08NormalBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 6, b, c));
    }

    private ViewBaikeX08NormalBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (RoundImageView) objArr[2], (BaikeTextView) objArr[5], (TextView) objArr[4], (ConstraintLayout) objArr[0], (ConstraintLayout) objArr[1], (ConstraintLayout) objArr[3]);
        this.d = -1L;
        this.countdownLayout.setTag(null);
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
