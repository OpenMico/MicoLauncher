package com.xiaomi.smarthome.databinding;

import android.content.res.Resources;
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
public class SecurityCameraInDeviceListBindingImpl extends SecurityCameraInDeviceListBinding {
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
        c.put(R.id.ivDragHandle, 6);
    }

    public SecurityCameraInDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 7, b, c));
    }

    private SecurityCameraInDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[3], (ImageView) objArr[6], (ImageView) objArr[5], (TextView) objArr[4], (TextView) objArr[2], (TextView) objArr[1]);
        this.d = -1L;
        this.container.setTag(null);
        this.ivCameraPic.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.SecurityCameraInDeviceListBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.SecurityCameraInDeviceListBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.SecurityCameraInDeviceListBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean z;
        int i;
        DeviceInfo deviceInfo;
        boolean z2;
        boolean z3;
        long j2;
        String str;
        String str2;
        Drawable drawable;
        String str3;
        String str4;
        long j3;
        Drawable drawable2;
        long j4;
        long j5;
        long j6;
        int i2;
        ImageView imageView;
        long j7;
        long j8;
        long j9;
        int i3;
        Resources resources;
        int i4;
        ImageView imageView2;
        int i5;
        TextView textView;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z4 = this.mIsLocationDeviceNameExchange;
        int i6 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        boolean z5 = false;
        if (i6 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            z = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i6 != 0) {
                j = z ? j | PlaybackStateCompat.ACTION_PLAY_FROM_URI : j | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
            }
            z2 = !z;
            if (z) {
                textView = this.tvDeviceStatus;
                i5 = R.color.device_status_online_text_color;
            } else {
                textView = this.tvDeviceStatus;
                i5 = R.color.device_status_offline_text_color;
            }
            i = getColorFromResource(textView, i5);
            if ((j & 9) != 0) {
                j = z2 ? j | 32 | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE : j | 16 | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID | 1048576;
            }
        } else {
            z2 = false;
            i = 0;
            z = false;
            deviceInfo = null;
        }
        int i7 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i7 == 0 || i7 == 0)) {
            j = z4 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED : j | 16384 | PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        if ((j & 1885200) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
                j7 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            } else {
                j7 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            }
            int i8 = ((j & j7) > 0L ? 1 : ((j & j7) == 0L ? 0 : -1));
            if (i8 != 0) {
                z3 = !(deviceInfo != null ? deviceInfo.showSlideButton : false);
                if (i8 != 0) {
                    j = z3 ? j | 512 : j | 256;
                }
            } else {
                z3 = false;
            }
            str3 = ((j & 540672) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
            str = ((j & 294912) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
            if ((j & 1048592) != 0) {
                if (deviceInfo != null) {
                    z5 = deviceInfo.currentStatus;
                    j8 = 1048576;
                } else {
                    j8 = 1048576;
                }
                if ((j & j8) != 0) {
                    j = z5 ? j | 128 : j | 64;
                }
                if ((j & 16) != 0) {
                    j = z5 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_URI : j | 65536;
                }
                if ((j & 256) == 0) {
                    j9 = 1048576;
                } else if (z5) {
                    j |= 8388608;
                    j9 = 1048576;
                } else {
                    j |= PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED;
                    j9 = 1048576;
                }
                if ((j & j9) != 0) {
                    if (z5) {
                        imageView2 = this.ivStatus;
                        i4 = R.drawable.sh_ic_status_on;
                    } else {
                        imageView2 = this.ivStatus;
                        i4 = R.drawable.sh_ic_status_off;
                    }
                    drawable = getDrawableFromResource(imageView2, i4);
                } else {
                    drawable = null;
                }
                if ((j & 16) != 0) {
                    if (z5) {
                        resources = this.tvDeviceStatus.getResources();
                        i3 = R.string.device_status_on;
                    } else {
                        resources = this.tvDeviceStatus.getResources();
                        i3 = R.string.device_status_off;
                    }
                    str2 = resources.getString(i3);
                    j2 = 9;
                } else {
                    str2 = null;
                    j2 = 9;
                }
            } else {
                j2 = 9;
                drawable = null;
                str2 = null;
            }
        } else {
            j2 = 9;
            z3 = false;
            str3 = null;
            drawable = null;
            str2 = null;
            str = null;
        }
        if ((j & j2) != 0) {
            if (z2) {
                str2 = this.tvDeviceStatus.getResources().getString(R.string.device_status_offline);
            }
            if (z2) {
                drawable = getDrawableFromResource(this.ivStatus, R.drawable.sh_ic_status_offline);
            }
        } else {
            drawable = null;
            str2 = null;
        }
        if ((j & 11) != 0) {
            str4 = z4 ? str : str3;
            if (!z4) {
                str3 = str;
            }
        } else {
            str3 = null;
            str4 = null;
        }
        if ((j & 256) != 0) {
            if (deviceInfo != null) {
                z5 = deviceInfo.currentStatus;
                j5 = 1048576;
            } else {
                j5 = 1048576;
            }
            if ((j & j5) == 0) {
                j6 = 16;
            } else if (z5) {
                j |= 128;
                j6 = 16;
            } else {
                j |= 64;
                j6 = 16;
            }
            if ((j & j6) != 0) {
                j = z5 ? j | PlaybackStateCompat.ACTION_PREPARE_FROM_URI : j | 65536;
            }
            if ((j & 256) != 0) {
                j = z5 ? j | 8388608 : j | PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED;
            }
            if (z5) {
                imageView = this.ivCameraPic;
                i2 = R.drawable.sh_ic_camera_online_on;
            } else {
                imageView = this.ivCameraPic;
                i2 = R.drawable.sh_ic_camera_online_off;
            }
            drawable2 = getDrawableFromResource(imageView, i2);
            j3 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        } else {
            drawable2 = null;
            j3 = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        }
        if ((j & j3) != 0) {
            if (z3) {
                drawable2 = getDrawableFromResource(this.ivCameraPic, R.drawable.sh_ic_camera_online_on);
            }
            j4 = 9;
        } else {
            drawable2 = null;
            j4 = 9;
        }
        int i9 = ((j & j4) > 0L ? 1 : ((j & j4) == 0L ? 0 : -1));
        if (i9 == 0) {
            drawable2 = null;
        } else if (z2) {
            drawable2 = getDrawableFromResource(this.ivCameraPic, R.drawable.sh_ic_camera_offline);
        }
        if (i9 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivCameraPic, drawable2);
            ImageViewBindingAdapter.setImageDrawable(this.ivStatus, drawable);
            this.ivStatus.setClickable(z);
            TextViewBindingAdapter.setText(this.tvDeviceStatus, str2);
            this.tvDeviceStatus.setTextColor(i);
        }
        if ((j & 11) != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str3);
        }
    }
}
