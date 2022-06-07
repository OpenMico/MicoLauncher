package com.xiaomi.micolauncher.databinding;

import android.graphics.drawable.Drawable;
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
import androidx.databinding.adapters.ViewBindingAdapter;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.view.SelectionListAdapter;

/* loaded from: classes3.dex */
public class ItemSelectionListBindingImpl extends ItemSelectionListBinding {
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

    public ItemSelectionListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 5, b, c));
    }

    private ItemSelectionListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[3], (ImageView) objArr[2], (TextView) objArr[4], (View) objArr[1]);
        this.e = -1L;
        this.ivBadge.setTag(null);
        this.ivPlaying.setTag(null);
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        this.tvNo.setTag(null);
        this.vBg.setTag(null);
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

    @Override // com.xiaomi.micolauncher.databinding.ItemSelectionListBinding
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
        Drawable drawable;
        int i;
        int i2;
        int i3;
        boolean z;
        boolean z2;
        int i4;
        View view;
        synchronized (this) {
            j = this.e;
            this.e = 0L;
        }
        VideoItem videoItem = this.mItem;
        int i5 = ((j & 3) > 0L ? 1 : ((j & 3) == 0L ? 0 : -1));
        String str = null;
        int i6 = 0;
        if (i5 != 0) {
            if (videoItem != null) {
                z = videoItem.isPlaying();
                i3 = videoItem.getCi();
                z2 = videoItem.isFee();
            } else {
                z2 = false;
                z = false;
                i3 = 0;
            }
            if (i5 != 0) {
                j = z ? j | 8 | 128 | 512 : j | 4 | 64 | 256;
            }
            if ((j & 3) != 0) {
                j = z2 ? j | 32 : j | 16;
            }
            i = z ? 0 : 8;
            if (z) {
                view = this.vBg;
                i4 = R.drawable.bg_selection_item_focused;
            } else {
                view = this.vBg;
                i4 = R.drawable.bg_selection_item;
            }
            drawable = getDrawableFromResource(view, i4);
            i2 = z ? 8 : 0;
            str = String.valueOf(i3);
            if (!z2) {
                i6 = 8;
            }
        } else {
            drawable = null;
            i2 = 0;
            i = 0;
        }
        if ((2 & j) != 0) {
            SelectionListAdapter.loadImage(this.ivBadge, getDrawableFromResource(this.ivBadge, R.drawable.icon_selection_childrenvip));
            SelectionListAdapter.loadImage(this.ivPlaying, getDrawableFromResource(this.ivPlaying, R.drawable.icon_playing));
        }
        if ((j & 3) != 0) {
            this.ivBadge.setVisibility(i6);
            this.ivPlaying.setVisibility(i);
            this.tvNo.setVisibility(i2);
            TextViewBindingAdapter.setText(this.tvNo, str);
            ViewBindingAdapter.setBackground(this.vBg, drawable);
        }
    }
}
