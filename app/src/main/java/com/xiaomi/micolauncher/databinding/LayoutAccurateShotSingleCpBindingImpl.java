package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.multicp.adapter.AccurateMultiShotAdapter;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public class LayoutAccurateShotSingleCpBindingImpl extends LayoutAccurateShotSingleCpBinding {
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

    static {
        c.put(R.id.vMaskTop, 5);
        c.put(R.id.vMaskBottom, 6);
        c.put(R.id.collapseView, 7);
        c.put(R.id.tv, 8);
        c.put(R.id.tvLabelPlaySource, 9);
        c.put(R.id.rvPlaySource, 10);
    }

    public LayoutAccurateShotSingleCpBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 11, b, c));
    }

    private LayoutAccurateShotSingleCpBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (FrameLayout) objArr[7], (ImageView) objArr[1], (RecyclerView) objArr[10], (TextView) objArr[8], (TextView) objArr[9], (TextView) objArr[4], (TextView) objArr[3], (View) objArr[6], (View) objArr[5], (ImageView) objArr[2]);
        this.e = -1L;
        this.ivCover.setTag(null);
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        this.tvResourceInfo.setTag(null);
        this.tvResourceName.setTag(null);
        this.vipMark.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.e = 2L;
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
    public boolean setVariable(int i, @Nullable Object obj) {
        if (9 != i) {
            return false;
        }
        setData((VideoItem) obj);
        return true;
    }

    @Override // com.xiaomi.micolauncher.databinding.LayoutAccurateShotSingleCpBinding
    public void setData(@Nullable VideoItem videoItem) {
        this.mData = videoItem;
        synchronized (this) {
            this.e |= 1;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        boolean z;
        synchronized (this) {
            j = this.e;
            this.e = 0L;
        }
        VideoItem videoItem = this.mData;
        int i = ((j & 3) > 0L ? 1 : ((j & 3) == 0L ? 0 : -1));
        int i2 = 0;
        String str2 = null;
        if (i != 0) {
            if (videoItem != null) {
                str2 = videoItem.getTitle();
                str = videoItem.detailComposition();
                str2 = videoItem.getHorizontalImage();
                z = videoItem.isVip();
            } else {
                z = false;
                str = null;
                str2 = null;
            }
            if (i != 0) {
                j = z ? j | 8 : j | 4;
            }
            if (!z) {
                i2 = 8;
            }
        } else {
            str2 = null;
            str = null;
        }
        if ((j & 3) != 0) {
            AccurateMultiShotAdapter.bindCover(this.ivCover, str2, true);
            TextViewBindingAdapter.setText(this.tvResourceInfo, str);
            TextViewBindingAdapter.setText(this.tvResourceName, str2);
            this.vipMark.setVisibility(i2);
        }
    }
}
