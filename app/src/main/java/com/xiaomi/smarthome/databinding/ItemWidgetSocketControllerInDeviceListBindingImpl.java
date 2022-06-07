package com.xiaomi.smarthome.databinding;

import android.graphics.drawable.Drawable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class ItemWidgetSocketControllerInDeviceListBindingImpl extends ItemWidgetSocketControllerInDeviceListBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = new SparseIntArray();
    private long d;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        c.put(R.id.tvLocation, 4);
        c.put(R.id.ivDeviceTypePic, 5);
    }

    public ItemWidgetSocketControllerInDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 6, b, c));
    }

    private ItemWidgetSocketControllerInDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[5], (ImageView) objArr[3], (ImageView) objArr[1], (TextView) objArr[4], (TextView) objArr[2]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivDragHandle.setTag(null);
        this.ivStatus.setTag(null);
        this.tvStatus.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.d = 4L;
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
    public boolean setVariable(int i, @Nullable Object obj) {
        if (BR.item == i) {
            setItem((DeviceInfoWrapper) obj);
        } else if (BR.editMode != i) {
            return false;
        } else {
            setEditMode(((Boolean) obj).booleanValue());
        }
        return true;
    }

    @Override // com.xiaomi.smarthome.databinding.ItemWidgetSocketControllerInDeviceListBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ItemWidgetSocketControllerInDeviceListBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.editMode);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        int i2;
        Drawable drawable;
        Drawable drawable2;
        DeviceInfo deviceInfo;
        boolean z;
        boolean z2;
        long j2;
        int i3;
        int i4;
        int i5;
        boolean z3;
        int i6;
        TextView textView;
        int i7;
        ConstraintLayout constraintLayout;
        int i8;
        ImageView imageView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z4 = this.mEditMode;
        Drawable drawable3 = null;
        if ((j & 7) != 0) {
            if ((j & 6) != 0) {
                j = z4 ? j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            if ((j & 7) != 0) {
                j = z4 ? j | 65536 : j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
            }
            if ((j & 512) != 0) {
                j = z4 ? j | PlaybackStateCompat.ACTION_SET_REPEAT_MODE : j | PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
            }
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            int i9 = ((j & 5) > 0L ? 1 : ((j & 5) == 0L ? 0 : -1));
            if (i9 != 0) {
                if (deviceInfo != null) {
                    z2 = deviceInfo.isOnline;
                    z3 = deviceInfo.currentStatus;
                } else {
                    z2 = false;
                    z3 = false;
                }
                if (i9 != 0) {
                    j = z2 ? j | 16 | 16384 : j | 8 | PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                }
                if ((j & PlaybackStateCompat.ACTION_PREPARE_FROM_URI) != 0) {
                    j = z2 ? j | 256 : j | 128;
                }
                if ((j & PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID) != 0) {
                    j = z2 ? j | 1048576 : j | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
                }
                if ((j & 5) != 0) {
                    j = z3 ? j | 64 : j | 32;
                }
                if (z2) {
                    textView = this.tvStatus;
                    i6 = R.color.sh_status_text_online_color;
                } else {
                    textView = this.tvStatus;
                    i6 = R.color.sh_status_text_offline_color;
                }
                i5 = getColorFromResource(textView, i6);
                if (z2) {
                    constraintLayout = this.container;
                    i7 = R.drawable.shape_widgets_online;
                } else {
                    constraintLayout = this.container;
                    i7 = R.drawable.shape_widgets_offline;
                }
                drawable = getDrawableFromResource(constraintLayout, i7);
                if (z3) {
                    imageView = this.ivStatus;
                    i8 = R.drawable.x08l_ic_status_on;
                } else {
                    imageView = this.ivStatus;
                    i8 = R.drawable.x08l_ic_status_off;
                }
                drawable3 = getDrawableFromResource(imageView, i8);
            } else {
                drawable = null;
                z2 = false;
                i5 = 0;
            }
            z = !(deviceInfo != null ? deviceInfo.showSlideButton : false);
            if ((j & 7) != 0) {
                j = z ? j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID : j | 512;
            }
            if ((j & 6) != 0) {
                i = z4 ? 0 : 8;
                i2 = i5;
                drawable2 = drawable3;
            } else {
                i2 = i5;
                drawable2 = drawable3;
                i = 0;
            }
        } else {
            deviceInfo = null;
            drawable2 = null;
            drawable = null;
            z2 = false;
            z = false;
            i2 = 0;
            i = 0;
        }
        if ((j & 163840) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            if (deviceInfo != null) {
                z2 = deviceInfo.isOnline;
            }
            if ((j & 5) != 0) {
                j = z2 ? j | 16 | 16384 : j | 8 | PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            }
            if ((j & PlaybackStateCompat.ACTION_PREPARE_FROM_URI) != 0) {
                j = z2 ? j | 256 : j | 128;
            }
            if ((j & PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID) != 0) {
                j = z2 ? j | 1048576 : j | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
            }
            i4 = (j & PlaybackStateCompat.ACTION_PREPARE_FROM_URI) != 0 ? z2 ? 0 : 8 : 0;
            if ((j & PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID) != 0) {
                i3 = z2 ? 8 : 0;
                j2 = 7;
            } else {
                i3 = 0;
                j2 = 7;
            }
        } else {
            i4 = 0;
            i3 = 0;
            j2 = 7;
        }
        int i10 = ((j2 & j) > 0L ? 1 : ((j2 & j) == 0L ? 0 : -1));
        if (i10 == 0) {
            i3 = 0;
        } else if (z4) {
            i3 = 8;
        }
        if ((j & 512) == 0) {
            i4 = 0;
        } else if (z4) {
            i4 = 8;
        }
        if (i10 == 0) {
            i4 = 0;
        } else if (z) {
            i4 = 8;
        }
        if ((j & 5) != 0) {
            ViewBindingAdapter.setBackground(this.container, drawable);
            ImageViewBindingAdapter.setImageDrawable(this.ivStatus, drawable2);
            this.tvStatus.setTextColor(i2);
        }
        if ((j & 6) != 0) {
            this.ivDragHandle.setVisibility(i);
        }
        if (i10 != 0) {
            this.ivStatus.setVisibility(i4);
            this.tvStatus.setVisibility(i3);
        }
    }
}
