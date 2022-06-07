package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.multicp.adapter.AccurateMultiShotAdapter;
import com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public class ItemAccurateShotMultiResultsCpsBindingImpl extends ItemAccurateShotMultiResultsCpsBinding {
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
        c.put(R.id.vCoverMask, 5);
        c.put(R.id.contentProviderBar, 6);
    }

    public ItemAccurateShotMultiResultsCpsBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private ItemAccurateShotMultiResultsCpsBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ContentProviderBar) objArr[6], (ImageView) objArr[1], (TextView) objArr[3], (TextView) objArr[2], (View) objArr[5], (ImageView) objArr[4]);
        this.e = -1L;
        this.ivCover.setTag(null);
        this.loadingTip.setTag(null);
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        this.tvName.setTag(null);
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
        if (2 != i) {
            return false;
        }
        setItem((VideoItem) obj);
        return true;
    }

    @Override // com.xiaomi.micolauncher.databinding.ItemAccurateShotMultiResultsCpsBinding
    public void setItem(@Nullable VideoItem videoItem) {
        this.mItem = videoItem;
        synchronized (this) {
            this.e |= 1;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        int i2;
        int i3;
        boolean z;
        boolean z2;
        synchronized (this) {
            j = this.e;
            this.e = 0L;
        }
        VideoItem videoItem = this.mItem;
        int i4 = ((j & 3) > 0L ? 1 : ((j & 3) == 0L ? 0 : -1));
        String str = null;
        if (i4 != 0) {
            if (videoItem != null) {
                str = videoItem.getTitle();
                z = videoItem.isPlaying2();
                str = videoItem.getHorizontalImage();
                z2 = videoItem.isVip();
            } else {
                str = null;
                z2 = false;
                z = false;
            }
            if (i4 != 0) {
                j = z ? j | 32 | 128 : j | 16 | 64;
            }
            if ((j & 3) != 0) {
                j = z2 ? j | 8 : j | 4;
            }
            i3 = 8;
            i = z ? 0 : 8;
            i2 = z ? 8 : 0;
            if (z2) {
                i3 = 0;
            }
        } else {
            str = null;
            i3 = 0;
            i2 = 0;
            i = 0;
        }
        if ((j & 3) != 0) {
            AccurateMultiShotAdapter.bindCover(this.ivCover, str, false);
            this.loadingTip.setVisibility(i);
            TextViewBindingAdapter.setText(this.tvName, str);
            this.tvName.setVisibility(i2);
            this.vipMark.setVisibility(i3);
        }
    }
}
