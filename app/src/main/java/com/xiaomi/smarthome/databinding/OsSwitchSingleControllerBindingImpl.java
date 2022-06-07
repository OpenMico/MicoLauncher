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

/* loaded from: classes4.dex */
public class OsSwitchSingleControllerBindingImpl extends OsSwitchSingleControllerBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = null;
    private long d;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public OsSwitchSingleControllerBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private OsSwitchSingleControllerBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[3], (ImageView) objArr[6], (ImageView) objArr[5], (TextView) objArr[4], (TextView) objArr[2], (TextView) objArr[1]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivDeviceTypePic.setTag(null);
        this.ivDragHandle.setTag(null);
        this.ivStatus.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.OsSwitchSingleControllerBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsSwitchSingleControllerBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.OsSwitchSingleControllerBinding
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
        boolean z2;
        int i2;
        int i3;
        long j2;
        String str;
        String str2;
        long j3;
        Drawable drawable;
        Drawable drawable2;
        String str3;
        String str4;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        long j9;
        int i4;
        ImageView imageView;
        int i5;
        ImageView imageView2;
        int i6;
        TextView textView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z3 = this.mIsLocationDeviceNameExchange;
        boolean z4 = this.mEditMode;
        int i7 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        boolean z5 = false;
        if (i7 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            z = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i7 != 0) {
                j = z ? j | 512 : j | 256;
            }
            z2 = !z;
            if (z) {
                textView = this.tvDeviceStatus;
                i6 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvDeviceStatus;
                i6 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i6);
            if ((j & 9) != 0) {
                j = z2 ? j | 32 | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE : j | 16 | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID | 1048576;
            }
        } else {
            z2 = false;
            z = false;
            i = 0;
            deviceInfo = null;
        }
        int i8 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i8 == 0 || i8 == 0)) {
            j = z3 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED : j | 16384 | PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        int i9 = ((j & 12) > 0L ? 1 : ((j & 12) == 0L ? 0 : -1));
        if (i9 != 0) {
            if (i9 != 0) {
                j = z4 ? j | PlaybackStateCompat.ACTION_PLAY_FROM_URI | 33554432 : j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM | 16777216;
            }
            i2 = 8;
            i3 = z4 ? 0 : 8;
            if (!z4) {
                i2 = 0;
            }
        } else {
            i3 = 0;
            i2 = 0;
        }
        if ((j & 1885200) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            str2 = ((j & 540672) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
            str = ((j & 294912) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
            if ((j & 1049616) != 0) {
                if (deviceInfo != null) {
                    z5 = deviceInfo.currentStatus;
                    j4 = 1048576;
                } else {
                    j4 = 1048576;
                }
                if ((j & j4) == 0) {
                    j5 = 16;
                } else if (z5) {
                    j |= 128;
                    j5 = 16;
                } else {
                    j |= 64;
                    j5 = 16;
                }
                if ((j & j5) == 0) {
                    j6 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                } else if (z5) {
                    j |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
                    j6 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                } else {
                    j |= 65536;
                    j6 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                }
                if ((j & j6) == 0) {
                    j7 = 1048576;
                } else if (z5) {
                    j |= 8388608;
                    j7 = 1048576;
                } else {
                    j |= PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED;
                    j7 = 1048576;
                }
                if ((j7 & j) != 0) {
                    if (z5) {
                        imageView2 = this.ivStatus;
                        i5 = R.drawable.sh_ic_status_on;
                    } else {
                        imageView2 = this.ivStatus;
                        i5 = R.drawable.sh_ic_status_off;
                    }
                    drawable = getDrawableFromResource(imageView2, i5);
                    j8 = 16;
                } else {
                    drawable = null;
                    j8 = 16;
                }
                if ((j8 & j) != 0) {
                    if (z5) {
                        str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_on);
                    } else {
                        str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_off);
                    }
                    j9 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                } else {
                    str3 = null;
                    j9 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                }
                if ((j9 & j) != 0) {
                    if (z5) {
                        imageView = this.ivDeviceTypePic;
                        i4 = R.drawable.sh_ic_switch_single_online_on;
                    } else {
                        imageView = this.ivDeviceTypePic;
                        i4 = R.drawable.sh_ic_switch_single_online_off;
                    }
                    drawable2 = getDrawableFromResource(imageView, i4);
                    j2 = 9;
                } else {
                    drawable2 = null;
                    j2 = 9;
                }
            } else {
                j2 = 9;
                str3 = null;
                drawable2 = null;
                drawable = null;
            }
        } else {
            j2 = 9;
            str2 = null;
            str3 = null;
            drawable2 = null;
            str = null;
            drawable = null;
        }
        int i10 = ((j2 & j) > 0L ? 1 : ((j2 & j) == 0L ? 0 : -1));
        if (i10 != 0) {
            if (z2) {
                str3 = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
            }
            if (z2) {
                drawable2 = getDrawableFromResource(this.ivDeviceTypePic, R.drawable.sh_ic_switch_single_offline);
            }
            if (z2) {
                drawable = getDrawableFromResource(this.ivStatus, R.drawable.sh_ic_status_offline);
            }
            j3 = 11;
        } else {
            str3 = null;
            drawable2 = null;
            drawable = null;
            j3 = 11;
        }
        int i11 = ((j3 & j) > 0L ? 1 : ((j3 & j) == 0L ? 0 : -1));
        if (i11 != 0) {
            str4 = z3 ? str : str2;
            if (!z3) {
                str2 = str;
            }
        } else {
            str4 = null;
            str2 = null;
        }
        if (i10 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivDeviceTypePic, drawable2);
            ImageViewBindingAdapter.setImageDrawable(this.ivStatus, drawable);
            this.ivStatus.setClickable(z);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str3);
            this.tvDeviceStatus.setTextColor(i);
        }
        if ((j & 12) != 0) {
            this.ivDragHandle.setVisibility(i3);
            this.ivStatus.setVisibility(i2);
        }
        if (i11 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str2);
        }
    }
}
