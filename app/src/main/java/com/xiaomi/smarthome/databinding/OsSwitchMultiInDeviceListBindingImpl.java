package com.xiaomi.smarthome.databinding;

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
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;

/* loaded from: classes4.dex */
public class OsSwitchMultiInDeviceListBindingImpl extends OsSwitchMultiInDeviceListBinding {
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
        c.put(R.id.ivDeviceTypePic, 4);
        c.put(R.id.rv_status, 5);
        c.put(R.id.ivDragHandle, 6);
    }

    public OsSwitchMultiInDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private OsSwitchMultiInDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[4], (ImageView) objArr[6], (RecyclerView) objArr[5], (TextView) objArr[3], (TextView) objArr[2], (TextView) objArr[1]);
        this.d = -1L;
        this.container.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.OsSwitchMultiInDeviceListBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsSwitchMultiInDeviceListBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsSwitchMultiInDeviceListBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        DeviceInfo deviceInfo;
        int i;
        String str;
        String str2;
        String str3;
        int i2;
        TextView textView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z = this.mIsLocationDeviceNameExchange;
        int i3 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        boolean z2 = false;
        String str4 = null;
        if (i3 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            boolean z3 = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i3 != 0) {
                j = z3 ? j | 128 : j | 64;
            }
            if (!z3) {
                z2 = true;
            }
            if (z3) {
                textView = this.tvDeviceStatus;
                i2 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvDeviceStatus;
                i2 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i2);
            if ((j & 9) != 0) {
                j = z2 ? j | 32 : j | 16;
            }
        } else {
            i = 0;
            deviceInfo = null;
        }
        int i4 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i4 == 0 || i4 == 0)) {
            j = z ? j | 512 | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : j | 256 | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        }
        if ((j & 3856) != 0) {
            str3 = (16 & j) != 0 ? MiotDeviceUtils.getSwitchStatusText(deviceInfoWrapper) : null;
            if ((j & 3840) != 0) {
                if (deviceInfoWrapper != null) {
                    deviceInfo = deviceInfoWrapper.getDeviceInfo();
                }
                str2 = ((j & 2304) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
                str = ((j & 1536) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
            } else {
                str2 = null;
                str = null;
            }
        } else {
            str2 = null;
            str3 = null;
            str = null;
        }
        int i5 = ((9 & j) > 0L ? 1 : ((9 & j) == 0L ? 0 : -1));
        if (i5 == 0) {
            str3 = null;
        } else if (z2) {
            str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
        }
        int i6 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (i6 != 0) {
            str4 = z ? str : str2;
            if (!z) {
                str2 = str;
            }
        } else {
            str2 = null;
        }
        if (i5 != 0) {
            this.tvDeviceStatus.setTextColor(i);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str3);
        }
        if (i6 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str2);
        }
    }
}
