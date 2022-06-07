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
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.SweepRobotStatus;

/* loaded from: classes4.dex */
public class HouseWorkSweepRobotBindingImpl extends HouseWorkSweepRobotBinding {
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
        c.put(R.id.ivStatus, 6);
        c.put(R.id.tvStatus, 7);
        c.put(R.id.ivSweep, 8);
        c.put(R.id.ivPause, 9);
        c.put(R.id.ivSwitch, 10);
        c.put(R.id.ivDragHandle, 11);
        c.put(R.id.guideLine, 12);
    }

    public HouseWorkSweepRobotBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 13, b, c));
    }

    private HouseWorkSweepRobotBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (Guideline) objArr[12], (ImageView) objArr[3], (ImageView) objArr[11], (ImageView) objArr[4], (ImageView) objArr[9], (ImageView) objArr[6], (ImageView) objArr[8], (ImageView) objArr[10], (TextView) objArr[2], (TextView) objArr[1], (TextView) objArr[5], (TextView) objArr[7]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivCharge.setTag(null);
        this.ivOfflinePic.setTag(null);
        this.tvDeviceType.setTag(null);
        this.tvLocation.setTag(null);
        this.tvOffline.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.d = 16L;
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
        } else if (BR.status == i) {
            setStatus((String) obj);
        } else if (BR.isLocationDeviceNameExchange == i) {
            setIsLocationDeviceNameExchange(((Boolean) obj).booleanValue());
        } else if (BR.editMode != i) {
            return false;
        } else {
            setEditMode(((Boolean) obj).booleanValue());
        }
        return true;
    }

    @Override // com.xiaomi.smarthome.databinding.HouseWorkSweepRobotBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.HouseWorkSweepRobotBinding
    public void setStatus(@Nullable String str) {
        this.mStatus = str;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.status);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.HouseWorkSweepRobotBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 4;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.HouseWorkSweepRobotBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        Drawable drawable;
        DeviceInfo deviceInfo;
        int i;
        boolean z;
        String str;
        String str2;
        int i2;
        TextView textView;
        int i3;
        ImageView imageView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        String str3 = this.mStatus;
        boolean z2 = this.mIsLocationDeviceNameExchange;
        int i4 = ((j & 17) > 0L ? 1 : ((j & 17) == 0L ? 0 : -1));
        String str4 = null;
        if (i4 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            boolean z3 = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i4 != 0) {
                j = z3 ? j | 256 | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID : j | 128 | 512;
            }
            if (z3) {
                textView = this.tvOffline;
                i2 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvOffline;
                i2 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i2);
            if (z3) {
                imageView = this.ivOfflinePic;
                i3 = R.drawable.sh_ic_sweep_robot_online;
            } else {
                imageView = this.ivOfflinePic;
                i3 = R.drawable.sh_ic_sweep_robot_offline;
            }
            drawable = getDrawableFromResource(imageView, i3);
        } else {
            deviceInfo = null;
            drawable = null;
            i = 0;
        }
        int i5 = ((j & 18) > 0L ? 1 : ((j & 18) == 0L ? 0 : -1));
        if (i5 != 0) {
            String status = SweepRobotStatus.MODE_CHARGING.getStatus();
            boolean equals = status != null ? status.equals(str3) : false;
            if (i5 != 0) {
                j = equals ? j | 64 : j | 32;
            }
            z = !equals;
        } else {
            z = false;
        }
        int i6 = ((j & 21) > 0L ? 1 : ((j & 21) == 0L ? 0 : -1));
        if (!(i6 == 0 || i6 == 0)) {
            j = z2 ? j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM | 16384 : j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH | PlaybackStateCompat.ACTION_PLAY_FROM_URI;
        }
        if ((j & 30720) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            str2 = ((j & 18432) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
            str = ((j & 12288) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
        } else {
            str2 = null;
            str = null;
        }
        int i7 = ((j & 21) > 0L ? 1 : ((j & 21) == 0L ? 0 : -1));
        if (i7 != 0) {
            str4 = z2 ? str : str2;
            if (!z2) {
                str2 = str;
            }
        } else {
            str2 = null;
        }
        if ((j & 18) != 0) {
            this.ivCharge.setClickable(z);
        }
        if ((j & 17) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivOfflinePic, drawable);
            this.tvOffline.setTextColor(i);
        }
        if (i7 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str2);
        }
    }
}
