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
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;

/* loaded from: classes4.dex */
public class EnvAirPurifierController1BindingImpl extends EnvAirPurifierController1Binding {
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
        c.put(R.id.ivUnit, 7);
        c.put(R.id.groupNumber, 8);
        c.put(R.id.ivDragHandle, 9);
    }

    public EnvAirPurifierController1BindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 10, b, c));
    }

    private EnvAirPurifierController1BindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (Group) objArr[8], (ImageView) objArr[5], (ImageView) objArr[9], (ImageView) objArr[6], (ImageView) objArr[7], (TextView) objArr[4], (TextView) objArr[2], (TextView) objArr[1], (TextView) objArr[3]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivDevicePic.setTag(null);
        this.ivStatus.setTag(null);
        this.tvDeviceStatus.setTag(null);
        this.tvDeviceType.setTag(null);
        this.tvLocation.setTag(null);
        this.tvNumber.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.EnvAirPurifierController1Binding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.EnvAirPurifierController1Binding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.EnvAirPurifierController1Binding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean z;
        DeviceInfo deviceInfo;
        int i;
        boolean z2;
        String str;
        long j2;
        Drawable drawable;
        String str2;
        Drawable drawable2;
        String str3;
        String str4;
        long j3;
        long j4;
        long j5;
        long j6;
        int i2;
        ImageView imageView;
        int i3;
        ImageView imageView2;
        int i4;
        TextView textView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z3 = this.mIsLocationDeviceNameExchange;
        int i5 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        boolean z4 = false;
        if (i5 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            z = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i5 != 0) {
                j = z ? j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            }
            z2 = !z;
            if (z) {
                textView = this.tvDeviceStatus;
                i4 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvDeviceStatus;
                i4 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i4);
            if ((j & 9) != 0) {
                j = z2 ? j | 32 | 128 | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE : j | 16 | 64 | 1048576;
            }
        } else {
            z2 = false;
            i = 0;
            z = false;
            deviceInfo = null;
        }
        int i6 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i6 == 0 || i6 == 0)) {
            j = z3 ? j | PlaybackStateCompat.ACTION_PLAY_FROM_URI | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED : j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM | PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        if ((j & 1847376) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            str = ((j & 528384) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
            str2 = ((j & 270336) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
            if ((j & 1048656) != 0) {
                if (deviceInfo != null) {
                    z4 = deviceInfo.currentStatus;
                    j3 = 1048576;
                } else {
                    j3 = 1048576;
                }
                if ((j & j3) == 0) {
                    j4 = 64;
                } else if (z4) {
                    j |= 512;
                    j4 = 64;
                } else {
                    j |= 256;
                    j4 = 64;
                }
                if ((j & j4) != 0) {
                    j = z4 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID : j | 16384;
                }
                if ((j & 16) == 0) {
                    j5 = 1048576;
                } else if (z4) {
                    j |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
                    j5 = 1048576;
                } else {
                    j |= 65536;
                    j5 = 1048576;
                }
                if ((j & j5) != 0) {
                    if (z4) {
                        imageView2 = this.ivStatus;
                        i3 = R.drawable.sh_ic_status_on;
                    } else {
                        imageView2 = this.ivStatus;
                        i3 = R.drawable.sh_ic_status_off;
                    }
                    drawable = getDrawableFromResource(imageView2, i3);
                    j6 = 64;
                } else {
                    drawable = null;
                    j6 = 64;
                }
                if ((j & j6) == 0) {
                    str3 = null;
                } else if (z4) {
                    str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_on);
                } else {
                    str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_off);
                }
                if ((16 & j) != 0) {
                    if (z4) {
                        imageView = this.ivDevicePic;
                        i2 = R.drawable.sh_ic_air_cleaner_on;
                    } else {
                        imageView = this.ivDevicePic;
                        i2 = R.drawable.sh_ic_air_cleaner_off;
                    }
                    drawable2 = getDrawableFromResource(imageView, i2);
                    j2 = 9;
                } else {
                    drawable2 = null;
                    j2 = 9;
                }
            } else {
                str3 = null;
                drawable2 = null;
                drawable = null;
                j2 = 9;
            }
        } else {
            j2 = 9;
            str3 = null;
            drawable2 = null;
            str2 = null;
            drawable = null;
            str = null;
        }
        int i7 = ((j2 & j) > 0L ? 1 : ((j2 & j) == 0L ? 0 : -1));
        if (i7 != 0) {
            if (z2) {
                drawable2 = getDrawableFromResource(this.ivDevicePic, R.drawable.sh_ic_air_cleaner_offline);
            }
            if (z2) {
                str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
            }
            if (z2) {
                drawable = getDrawableFromResource(this.ivStatus, R.drawable.sh_ic_status_offline);
            }
        } else {
            str3 = null;
            drawable2 = null;
            drawable = null;
        }
        int i8 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (i8 != 0) {
            str4 = z3 ? str2 : str;
            if (z3) {
                str2 = str;
            }
        } else {
            str4 = null;
            str2 = null;
        }
        if (i7 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivDevicePic, drawable2);
            ImageViewBindingAdapter.setImageDrawable(this.ivStatus, drawable);
            this.ivStatus.setClickable(z);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str3);
            this.tvDeviceStatus.setTextColor(i);
            MiotDeviceUtils.setPm25(this.tvNumber, deviceInfoWrapper);
        }
        if (i8 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str2);
        }
    }
}
