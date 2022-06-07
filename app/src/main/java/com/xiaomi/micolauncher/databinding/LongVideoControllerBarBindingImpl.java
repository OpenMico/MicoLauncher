package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class LongVideoControllerBarBindingImpl extends LongVideoControllerBarBinding {
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
        c.put(R.id.tv_seek_time, 1);
        c.put(R.id.seek_bar, 2);
        c.put(R.id.tv_duration_time, 3);
        c.put(R.id.iv_play_state, 4);
        c.put(R.id.iv_next, 5);
        c.put(R.id.tv_open_selections, 6);
    }

    public LongVideoControllerBarBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private LongVideoControllerBarBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[5], (ImageView) objArr[4], (SeekBar) objArr[2], (TextView) objArr[3], (TextView) objArr[6], (TextView) objArr[1]);
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
