package com.xiaomi.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.widgets.MagicTextView;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.DeviceListActivity;

/* loaded from: classes4.dex */
public class ActivityDeviceListBindingImpl extends ActivityDeviceListBinding {
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
        c.put(R.id.ivBack, 4);
        c.put(R.id.title, 5);
        c.put(R.id.ivBackCircleBg, 6);
        c.put(R.id.ivCancel, 7);
        c.put(R.id.ivEditCircleBg, 8);
        c.put(R.id.ivDone, 9);
        c.put(R.id.tvCloseAll, 10);
        c.put(R.id.tvOpenAll, 11);
        c.put(R.id.tvSortTitle, 12);
        c.put(R.id.rvDeviceList, 13);
        c.put(R.id.recycler_view_title, 14);
    }

    public ActivityDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 15, b, c));
    }

    private ActivityDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (Group) objArr[3], (ImageView) objArr[4], (ImageView) objArr[6], (ImageView) objArr[7], (TextView) objArr[9], (ImageView) objArr[1], (ImageView) objArr[8], (TextView) objArr[14], (RecyclerView) objArr[13], (TextView) objArr[5], (MagicTextView) objArr[10], (MagicTextView) objArr[11], (TextView) objArr[12], (TextView) objArr[2]);
        this.e = -1L;
        this.groupButtons.setTag(null);
        this.ivEdit.setTag(null);
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.e = 16L;
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
        if (BR.category == i) {
            setCategory((String) obj);
        } else if (BR.hideButtons == i) {
            setHideButtons(((Boolean) obj).booleanValue());
        } else if (BR.mode == i) {
            setMode((DeviceListActivity.Mode) obj);
        } else if (BR.isX6A != i) {
            return false;
        } else {
            setIsX6A(((Boolean) obj).booleanValue());
        }
        return true;
    }

    @Override // com.xiaomi.smarthome.databinding.ActivityDeviceListBinding
    public void setCategory(@Nullable String str) {
        this.mCategory = str;
        synchronized (this) {
            this.e |= 1;
        }
        notifyPropertyChanged(BR.category);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ActivityDeviceListBinding
    public void setHideButtons(boolean z) {
        this.mHideButtons = z;
        synchronized (this) {
            this.e |= 2;
        }
        notifyPropertyChanged(BR.hideButtons);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ActivityDeviceListBinding
    public void setMode(@Nullable DeviceListActivity.Mode mode) {
        this.mMode = mode;
    }

    @Override // com.xiaomi.smarthome.databinding.ActivityDeviceListBinding
    public void setIsX6A(boolean z) {
        this.mIsX6A = z;
        synchronized (this) {
            this.e |= 8;
        }
        notifyPropertyChanged(BR.isX6A);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        int i2;
        synchronized (this) {
            j = this.e;
            this.e = 0L;
        }
        String str = this.mCategory;
        boolean z = this.mHideButtons;
        boolean z2 = this.mIsX6A;
        int i3 = ((j & 26) > 0L ? 1 : ((j & 26) == 0L ? 0 : -1));
        int i4 = 8;
        if (i3 != 0) {
            if (i3 != 0) {
                j = z2 ? j | 64 | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : j | 32 | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            if ((j & 24) != 0) {
                j = z2 ? j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID : j | 512;
            }
            i = (j & 24) != 0 ? z2 ? 8 : 0 : 0;
        } else {
            i = 0;
        }
        if ((j & 2080) != 0) {
            if ((j & 32) != 0) {
                j = z ? j | 256 : j | 128;
            }
            if ((j & PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) != 0) {
                j = z ? j | 16384 : j | PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            }
            i2 = (32 & j) != 0 ? z ? 0 : 8 : 0;
            i4 = (PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH & j) != 0 ? z ? 4 : 0 : 0;
        } else {
            i4 = 0;
            i2 = 0;
        }
        int i5 = ((26 & j) > 0L ? 1 : ((26 & j) == 0L ? 0 : -1));
        if (i5 != 0) {
            if (z2) {
                i2 = 8;
            }
            if (!z2) {
            }
        } else {
            i4 = 0;
            i2 = 0;
        }
        if (i5 != 0) {
            this.groupButtons.setVisibility(i4);
            this.tvTitle.setVisibility(i2);
        }
        if ((j & 24) != 0) {
            this.ivEdit.setVisibility(i);
        }
        if ((j & 17) != 0) {
            TextViewBindingAdapter.setText(this.tvTitle, str);
        }
    }
}
