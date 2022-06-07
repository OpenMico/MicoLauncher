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
public class OsOutletMultiInDeviceListBindingImpl extends OsOutletMultiInDeviceListBinding {
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
        c.put(R.id.rv_status, 5);
        c.put(R.id.ivDragHandle, 6);
    }

    public OsOutletMultiInDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private OsOutletMultiInDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[3], (ImageView) objArr[6], (RecyclerView) objArr[5], (TextView) objArr[4], (TextView) objArr[2], (TextView) objArr[1]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivDeviceTypePic.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.OsOutletMultiInDeviceListBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsOutletMultiInDeviceListBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsOutletMultiInDeviceListBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        DeviceInfo deviceInfo;
        int i;
        boolean z;
        String str;
        String str2;
        String str3;
        Drawable drawable;
        String str4;
        long j2;
        int i2;
        ImageView imageView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z2 = this.mIsLocationDeviceNameExchange;
        int i3 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        if (i3 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            boolean z3 = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i3 != 0) {
                j = z3 ? j | 128 : j | 64;
            }
            z = !z3;
            if (z3) {
                i = getColorFromResource(this.tvDeviceStatus, R.color.device_status_online_text_color);
            } else {
                i = getColorFromResource(this.tvDeviceStatus, R.color.device_status_offline_text_color);
            }
            if ((j & 9) != 0) {
                j = z ? j | 32 | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : j | 16 | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            }
        } else {
            z = false;
            i = 0;
            deviceInfo = null;
        }
        int i4 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i4 == 0 || i4 == 0)) {
            j = z2 ? j | 512 | PlaybackStateCompat.ACTION_PLAY_FROM_URI : j | 256 | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        if ((j & 14096) != 0) {
            str = (j & 16) != 0 ? MiotDeviceUtils.getSwitchStatusText(deviceInfoWrapper) : null;
            if ((14080 & j) != 0) {
                if (deviceInfoWrapper != null) {
                    deviceInfo = deviceInfoWrapper.getDeviceInfo();
                }
                str3 = ((8448 & j) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
                if ((4608 & j) == 0 || deviceInfo == null) {
                    str2 = null;
                    j2 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                } else {
                    str2 = deviceInfo.roomInfo;
                    j2 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                }
                int i5 = ((j2 & j) > 0L ? 1 : ((j2 & j) == 0L ? 0 : -1));
                if (i5 != 0) {
                    boolean z4 = deviceInfo != null ? deviceInfo.currentStatus : false;
                    if (i5 != 0) {
                        j = z4 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID : j | 16384;
                    }
                    if (z4) {
                        imageView = this.ivDeviceTypePic;
                        i2 = R.drawable.sh_ic_outlet_multi_on;
                    } else {
                        imageView = this.ivDeviceTypePic;
                        i2 = R.drawable.ic_outlet_multi_off;
                    }
                    drawable = getDrawableFromResource(imageView, i2);
                } else {
                    drawable = null;
                }
            } else {
                str3 = null;
                str2 = null;
                drawable = null;
            }
        } else {
            str3 = null;
            str2 = null;
            drawable = null;
            str = null;
        }
        int i6 = ((9 & j) > 0L ? 1 : ((9 & j) == 0L ? 0 : -1));
        if (i6 != 0) {
            if (z) {
                str = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
            }
            if (z) {
                drawable = getDrawableFromResource(this.ivDeviceTypePic, R.drawable.sh_ic_outlet_multi_offline);
            }
        } else {
            drawable = null;
            str = null;
        }
        int i7 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (i7 != 0) {
            str4 = z2 ? str2 : str3;
            if (!z2) {
                str3 = str2;
            }
        } else {
            str3 = null;
            str4 = null;
        }
        if (i6 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivDeviceTypePic, drawable);
            this.tvDeviceStatus.setTextColor(i);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str);
        }
        if (i7 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str3);
        }
    }
}
