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
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.ui.widgets.VerticalSeekView;

/* loaded from: classes4.dex */
public class EnvHeaterController1BindingImpl extends EnvHeaterController1Binding {
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
        c.put(R.id.ivDragHandle, 7);
    }

    public EnvHeaterController1BindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 8, b, c));
    }

    private EnvHeaterController1BindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[3], (ImageView) objArr[7], (ImageView) objArr[5], (VerticalSeekView) objArr[6], (TextView) objArr[4], (TextView) objArr[2], (TextView) objArr[1]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivDevicePic.setTag(null);
        this.ivStatus.setTag(null);
        this.seekView.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.EnvHeaterController1Binding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.EnvHeaterController1Binding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.EnvHeaterController1Binding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        boolean z;
        DeviceInfo deviceInfo;
        boolean z2;
        long j2;
        String str;
        Drawable drawable;
        Drawable drawable2;
        String str2;
        String str3;
        String str4;
        long j3;
        long j4;
        long j5;
        long j6;
        int i2;
        ImageView imageView;
        int i3;
        TextView textView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        double d = 0.0d;
        boolean z3 = this.mIsLocationDeviceNameExchange;
        int i4 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        boolean z4 = false;
        if (i4 != 0) {
            d = MiotDeviceUtils.value2SeekViewVolume(deviceInfoWrapper, PropDic.P_TARGET_TEMP);
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            z = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i4 != 0) {
                j = z ? j | PlaybackStateCompat.ACTION_PLAY_FROM_URI : j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
            }
            z2 = !z;
            if (z) {
                textView = this.tvDeviceStatus;
                i3 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvDeviceStatus;
                i3 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i3);
            if ((j & 9) != 0) {
                j = z2 ? j | 32 | 128 | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE : j | 16 | 64 | 1048576;
            }
        } else {
            z2 = false;
            z = false;
            i = 0;
            deviceInfo = null;
        }
        int i5 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i5 == 0 || i5 == 0)) {
            j = z3 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED : j | 16384 | PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        if ((j & 1884240) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            str3 = ((j & 540672) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
            str = ((j & 294912) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
            if ((j & 1048656) != 0) {
                if (deviceInfo != null) {
                    z4 = deviceInfo.currentStatus;
                }
                if ((j & 1048576) == 0) {
                    j3 = 16;
                } else if (z4) {
                    j |= 512;
                    j3 = 16;
                } else {
                    j |= 256;
                    j3 = 16;
                }
                if ((j & j3) == 0) {
                    j4 = 64;
                } else if (z4) {
                    j |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                    j4 = 64;
                } else {
                    j |= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                    j4 = 64;
                }
                if ((j & j4) != 0) {
                    j = z4 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_URI : j | 65536;
                }
                if ((j & 1048576) != 0) {
                    if (z4) {
                        drawable2 = getDrawableFromResource(this.ivStatus, R.drawable.sh_ic_status_on);
                    } else {
                        drawable2 = getDrawableFromResource(this.ivStatus, R.drawable.sh_ic_status_off);
                    }
                    j5 = 16;
                } else {
                    drawable2 = null;
                    j5 = 16;
                }
                if ((j & j5) != 0) {
                    if (z4) {
                        imageView = this.ivDevicePic;
                        i2 = R.drawable.sh_ic_heater_online_on;
                    } else {
                        imageView = this.ivDevicePic;
                        i2 = R.drawable.sh_ic_heater_online_off;
                    }
                    drawable = getDrawableFromResource(imageView, i2);
                    j6 = 64;
                } else {
                    drawable = null;
                    j6 = 64;
                }
                if ((j & j6) != 0) {
                    if (z4) {
                        str2 = this.tvDeviceStatus.getResources().getString(R.string.device_status_on);
                    } else {
                        str2 = this.tvDeviceStatus.getResources().getString(R.string.device_status_off);
                    }
                    j2 = 9;
                } else {
                    str2 = null;
                    j2 = 9;
                }
            } else {
                j2 = 9;
                str2 = null;
                drawable2 = null;
                drawable = null;
            }
        } else {
            j2 = 9;
            str3 = null;
            str2 = null;
            drawable2 = null;
            drawable = null;
            str = null;
        }
        int i6 = ((j & j2) > 0L ? 1 : ((j & j2) == 0L ? 0 : -1));
        if (i6 != 0) {
            if (z2) {
                drawable = getDrawableFromResource(this.ivDevicePic, R.drawable.sh_ic_heater_offline);
            }
            if (z2) {
                str2 = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
            }
            if (z2) {
                drawable2 = getDrawableFromResource(this.ivStatus, R.drawable.sh_ic_status_offline);
            }
        } else {
            str2 = null;
            drawable2 = null;
            drawable = null;
        }
        int i7 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (i7 != 0) {
            str4 = z3 ? str : str3;
            if (z3) {
                str = str3;
            }
        } else {
            str4 = null;
            str = null;
        }
        if (i6 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivDevicePic, drawable);
            ImageViewBindingAdapter.setImageDrawable(this.ivStatus, drawable2);
            this.ivStatus.setClickable(z);
            this.seekView.setVolume(d);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str2);
            this.tvDeviceStatus.setTextColor(i);
        }
        if (i7 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str);
        }
    }
}
