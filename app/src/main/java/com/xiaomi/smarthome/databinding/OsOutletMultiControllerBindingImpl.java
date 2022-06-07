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
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;

/* loaded from: classes4.dex */
public class OsOutletMultiControllerBindingImpl extends OsOutletMultiControllerBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = null;
    private long d;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public OsOutletMultiControllerBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private OsOutletMultiControllerBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[3], (ImageView) objArr[6], (RecyclerView) objArr[5], (TextView) objArr[4], (TextView) objArr[2], (TextView) objArr[1]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivDeviceTypePic.setTag(null);
        this.ivDragHandle.setTag(null);
        this.rvStatus.setTag(null);
        this.tvDeviceStatus.setTag(null);
        this.tvDeviceType.setTag(null);
        this.tvLocation.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.d = 8L;
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
        } else if (BR.isLocationDeviceNameExchange == i) {
            setIsLocationDeviceNameExchange(((Boolean) obj).booleanValue());
        } else if (BR.editMode != i) {
            return false;
        } else {
            setEditMode(((Boolean) obj).booleanValue());
        }
        return true;
    }

    @Override // com.xiaomi.smarthome.databinding.OsOutletMultiControllerBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsOutletMultiControllerBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsOutletMultiControllerBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
        synchronized (this) {
            this.d |= 4;
        }
        notifyPropertyChanged(BR.editMode);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        DeviceInfo deviceInfo;
        int i;
        boolean z;
        int i2;
        int i3;
        Drawable drawable;
        String str;
        String str2;
        String str3;
        String str4;
        long j2;
        int i4;
        TextView textView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z2 = this.mIsLocationDeviceNameExchange;
        boolean z3 = this.mEditMode;
        int i5 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        if (i5 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            boolean z4 = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i5 != 0) {
                j = z4 ? j | 128 : j | 64;
            }
            z = !z4;
            if (z4) {
                textView = this.tvDeviceStatus;
                i4 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvDeviceStatus;
                i4 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i4);
            if ((j & 9) != 0) {
                j = z ? j | 32 | PlaybackStateCompat.ACTION_PLAY_FROM_URI : j | 16 | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
            }
        } else {
            z = false;
            i = 0;
            deviceInfo = null;
        }
        int i6 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i6 == 0 || i6 == 0)) {
            j = z2 ? j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID : j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID | 16384;
        }
        int i7 = ((j & 12) > 0L ? 1 : ((j & 12) == 0L ? 0 : -1));
        if (i7 != 0) {
            if (i7 != 0) {
                j = z3 ? j | 512 | PlaybackStateCompat.ACTION_PREPARE_FROM_URI : j | 256 | 65536;
            }
            i2 = 8;
            i3 = z3 ? 0 : 8;
            if (!z3) {
                i2 = 0;
            }
        } else {
            i3 = 0;
            i2 = 0;
        }
        if ((j & 56336) != 0) {
            str3 = (16 & j) != 0 ? MiotDeviceUtils.getSwitchStatusText(deviceInfoWrapper) : null;
            if ((j & 56320) != 0) {
                if (deviceInfoWrapper != null) {
                    deviceInfo = deviceInfoWrapper.getDeviceInfo();
                }
                str2 = ((j & 33792) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
                if ((j & 18432) == 0 || deviceInfo == null) {
                    str = null;
                    j2 = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
                } else {
                    str = deviceInfo.roomInfo;
                    j2 = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
                }
                int i8 = ((j & j2) > 0L ? 1 : ((j & j2) == 0L ? 0 : -1));
                if (i8 != 0) {
                    boolean z5 = deviceInfo != null ? deviceInfo.currentStatus : false;
                    if (i8 != 0) {
                        j = z5 ? j | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED : j | PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
                    }
                    if (z5) {
                        drawable = getDrawableFromResource(this.ivDeviceTypePic, R.drawable.sh_ic_outlet_multi_on);
                    } else {
                        drawable = getDrawableFromResource(this.ivDeviceTypePic, R.drawable.ic_outlet_multi_off);
                    }
                } else {
                    drawable = null;
                }
            } else {
                str2 = null;
                str = null;
                drawable = null;
            }
        } else {
            str2 = null;
            str3 = null;
            str = null;
            drawable = null;
        }
        int i9 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        if (i9 != 0) {
            if (z) {
                str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
            }
            if (z) {
                drawable = getDrawableFromResource(this.ivDeviceTypePic, R.drawable.sh_ic_outlet_multi_offline);
            }
        } else {
            str3 = null;
            drawable = null;
        }
        int i10 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (i10 != 0) {
            str4 = z2 ? str : str2;
            if (!z2) {
                str2 = str;
            }
        } else {
            str2 = null;
            str4 = null;
        }
        if (i9 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivDeviceTypePic, drawable);
            this.tvDeviceStatus.setTextColor(i);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str3);
        }
        if ((j & 12) != 0) {
            this.ivDragHandle.setVisibility(i3);
            this.rvStatus.setVisibility(i2);
        }
        if (i10 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str2);
        }
    }
}
