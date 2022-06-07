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
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.VerticalSeekView;

/* loaded from: classes4.dex */
public class ItemWidgetLightControllerInDeviceListBindingImpl extends ItemWidgetLightControllerInDeviceListBinding {
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

    public ItemWidgetLightControllerInDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private ItemWidgetLightControllerInDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ImageView) objArr[3], (ImageView) objArr[6], (ImageView) objArr[4], (VerticalSeekView) objArr[2], (TextView) objArr[1], (TextView) objArr[5]);
        this.e = -1L;
        this.ivDevicePic.setTag(null);
        this.ivDragHandle.setTag(null);
        this.ivStatus.setTag(null);
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
        this.seekView.setTag(null);
        this.tvLocation.setTag(null);
        this.tvStatus.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.e = 4L;
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
        if (BR.item == i) {
            setItem((DeviceInfoWrapper) obj);
        } else if (BR.editMode != i) {
            return false;
        } else {
            setEditMode(((Boolean) obj).booleanValue());
        }
        return true;
    }

    @Override // com.xiaomi.smarthome.databinding.ItemWidgetLightControllerInDeviceListBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.e |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ItemWidgetLightControllerInDeviceListBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
        synchronized (this) {
            this.e |= 2;
        }
        notifyPropertyChanged(BR.editMode);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        long j2;
        int i2;
        Drawable drawable;
        DeviceInfo deviceInfo;
        String str;
        boolean z;
        Drawable drawable2;
        boolean z2;
        long j3;
        long j4;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z3;
        int i7;
        TextView textView;
        int i8;
        ConstraintLayout constraintLayout;
        int i9;
        ImageView imageView;
        synchronized (this) {
            j = this.e;
            this.e = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z4 = this.mEditMode;
        int i10 = ((j & 7) > 0L ? 1 : ((j & 7) == 0L ? 0 : -1));
        Drawable drawable3 = null;
        if (i10 != 0) {
            if (i10 != 0) {
                j = z4 ? j | 64 | 1048576 | 67108864 : j | 32 | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED | 33554432;
            }
            if ((j & 6) != 0) {
                j = z4 ? j | 16384 : j | PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            }
            if ((j & PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) != 0) {
                j = z4 ? j | PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED : j | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
            }
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            int i11 = ((j & 5) > 0L ? 1 : ((j & 5) == 0L ? 0 : -1));
            if (i11 != 0) {
                if (deviceInfo != null) {
                    z = deviceInfo.isOnline;
                    str = deviceInfo.roomInfo;
                    z3 = deviceInfo.currentStatus;
                } else {
                    str = null;
                    z3 = false;
                    z = false;
                }
                if (i11 != 0) {
                    j = z ? j | 16 | PlaybackStateCompat.ACTION_SET_REPEAT_MODE : j | 8 | PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
                }
                if ((j & PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) != 0) {
                    j = z ? j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID : j | 512;
                }
                if ((j & 32) != 0) {
                    j = z ? j | 65536 : j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
                }
                if ((j & 34078720) != 0) {
                    j = z ? j | 16777216 : j | 8388608;
                }
                if ((j & 5) != 0) {
                    j = z3 ? j | 256 : j | 128;
                }
                if (z) {
                    textView = this.tvStatus;
                    i7 = R.color.sh_status_text_online_color;
                } else {
                    textView = this.tvStatus;
                    i7 = R.color.sh_status_text_offline_color;
                }
                i6 = getColorFromResource(textView, i7);
                if (z) {
                    constraintLayout = this.d;
                    i8 = R.drawable.shape_widgets_online;
                } else {
                    constraintLayout = this.d;
                    i8 = R.drawable.shape_widgets_offline;
                }
                drawable = getDrawableFromResource(constraintLayout, i8);
                if (z3) {
                    imageView = this.ivStatus;
                    i9 = R.drawable.x08l_ic_status_on;
                } else {
                    imageView = this.ivStatus;
                    i9 = R.drawable.x08l_ic_status_off;
                }
                drawable3 = getDrawableFromResource(imageView, i9);
            } else {
                str = null;
                drawable = null;
                i6 = 0;
                z = false;
            }
            z2 = !(deviceInfo != null ? deviceInfo.showSlideButton : false);
            j2 = 0;
            if ((j & 7) != 0) {
                j = z2 ? j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            if ((j & 6) != 0) {
                i2 = z4 ? 0 : 8;
                i = i6;
                drawable2 = drawable3;
            } else {
                i = i6;
                drawable2 = drawable3;
                i2 = 0;
            }
        } else {
            j2 = 0;
            drawable2 = null;
            str = null;
            deviceInfo = null;
            drawable = null;
            z2 = false;
            z = false;
            i2 = 0;
            i = 0;
        }
        int i12 = 4;
        if ((j & 36175904) != j2) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            if (deviceInfo != null) {
                z = deviceInfo.isOnline;
            }
            if ((j & 5) != 0) {
                j = z ? j | 16 | PlaybackStateCompat.ACTION_SET_REPEAT_MODE : j | 8 | PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
            }
            j3 = 0;
            if ((j & PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) != 0) {
                j = z ? j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID : j | 512;
            }
            if ((j & 32) != 0) {
                j = z ? j | 65536 : j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
            }
            if ((j & 34078720) != 0) {
                j = z ? j | 16777216 : j | 8388608;
            }
            i4 = (j & PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) != 0 ? z ? 0 : 8 : 0;
            i12 = (j & 32) != 0 ? z ? 0 : 4 : 0;
            if ((j & 34078720) != 0) {
                i3 = z ? 8 : 0;
                j4 = 7;
            } else {
                i3 = 0;
                j4 = 7;
            }
        } else {
            j3 = 0;
            i4 = 0;
            i3 = 0;
            i12 = 0;
            j4 = 7;
        }
        int i13 = ((j4 & j) > j3 ? 1 : ((j4 & j) == j3 ? 0 : -1));
        if (i13 != 0) {
            if (!z4) {
            }
            i5 = z4 ? 8 : i3;
            if (z4) {
                i3 = 0;
            }
        } else {
            i3 = 0;
            i5 = 0;
            i12 = 0;
        }
        if ((j & PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) == 0) {
            i4 = 0;
        } else if (z4) {
            i4 = 8;
        }
        if (i13 == 0) {
            i4 = 0;
        } else if (z2) {
            i4 = 8;
        }
        if (i13 != 0) {
            this.ivDevicePic.setVisibility(i3);
            this.ivStatus.setVisibility(i4);
            this.seekView.setVisibility(i12);
            this.tvStatus.setVisibility(i5);
        }
        if ((j & 6) != 0) {
            this.ivDragHandle.setVisibility(i2);
        }
        if ((j & 5) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivStatus, drawable2);
            ViewBindingAdapter.setBackground(this.d, drawable);
            TextViewBindingAdapter.setText(this.tvLocation, str);
            this.tvStatus.setTextColor(i);
        }
    }
}
