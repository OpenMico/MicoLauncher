package com.xiaomi.micolauncher.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.micolauncher.module.multicp.adapter.ContentProviderAdapter;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;

/* loaded from: classes3.dex */
public class ItemPlaySourceBindingImpl extends ItemPlaySourceBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = null;
    @NonNull
    private final ConstraintLayout d;
    private long e;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public ItemPlaySourceBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 3, b, c));
    }

    private ItemPlaySourceBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[1], (ImageView) objArr[2]);
        this.e = -1L;
        this.ivSourceLogo.setTag(null);
        this.ivSourceStatus.setTag(null);
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
        if (10 != i) {
            return false;
        }
        setCp((VideoContentProvider) obj);
        return true;
    }

    @Override // com.xiaomi.micolauncher.databinding.ItemPlaySourceBinding
    public void setCp(@Nullable VideoContentProvider videoContentProvider) {
        this.mCp = videoContentProvider;
        synchronized (this) {
            this.e |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean z;
        synchronized (this) {
            j = this.e;
            this.e = 0L;
        }
        VideoContentProvider videoContentProvider = this.mCp;
        int i = ((j & 3) > 0L ? 1 : ((j & 3) == 0L ? 0 : -1));
        int i2 = 0;
        if (i != 0) {
            if (videoContentProvider != null) {
                i2 = videoContentProvider.resId;
                z = videoContentProvider.isSelected;
            } else {
                z = false;
                i2 = 0;
            }
            if (i != 0) {
                j = z ? j | 8 : j | 4;
            }
            if (!z) {
                i2 = 8;
            }
        } else {
            i2 = 0;
        }
        if ((j & 3) != 0) {
            ContentProviderAdapter.loadImage(this.ivSourceLogo, i2);
            this.ivSourceStatus.setVisibility(i2);
        }
    }
}
