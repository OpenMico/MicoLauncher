package com.xiaomi.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class FragmentSmartHomeModeRoomBindingImpl extends FragmentSmartHomeModeRoomBinding {
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
        c.put(R.id.roomView, 1);
        c.put(R.id.rvRefreshLayout, 2);
        c.put(R.id.devicesView, 3);
        c.put(R.id.room_loading_view, 4);
        c.put(R.id.column_1_1, 5);
        c.put(R.id.column_1_2, 6);
        c.put(R.id.column_2_1, 7);
        c.put(R.id.column_2_2, 8);
        c.put(R.id.column_3_1, 9);
        c.put(R.id.column_3_2, 10);
        c.put(R.id.column_4_1, 11);
        c.put(R.id.column_4_2, 12);
    }

    public FragmentSmartHomeModeRoomBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 13, b, c));
    }

    private FragmentSmartHomeModeRoomBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (View) objArr[5], (View) objArr[6], (View) objArr[7], (View) objArr[8], (View) objArr[9], (View) objArr[10], (View) objArr[11], (View) objArr[12], (HorizontalRecyclerView) objArr[3], (RelativeLayout) objArr[4], (RecyclerView) objArr[1], (HorizontalRefreshLayout) objArr[2]);
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
