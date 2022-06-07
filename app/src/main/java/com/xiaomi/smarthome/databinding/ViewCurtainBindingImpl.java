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
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class ViewCurtainBindingImpl extends ViewCurtainBinding {
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
        c.put(R.id.ivOpen, 5);
        c.put(R.id.ivPause, 6);
        c.put(R.id.ivClose, 7);
        c.put(R.id.ivStatus, 8);
        c.put(R.id.groupOnline, 9);
        c.put(R.id.ivDragHandle, 10);
    }

    public ViewCurtainBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 11, b, c));
    }

    private ViewCurtainBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ConstraintLayout) objArr[0], (Group) objArr[9], (ImageView) objArr[7], (ImageView) objArr[10], (ImageView) objArr[3], (ImageView) objArr[5], (ImageView) objArr[6], (ImageView) objArr[8], (TextView) objArr[2], (TextView) objArr[1], (TextView) objArr[4]);
        this.d = -1L;
        this.container.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.ViewCurtainBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.d |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ViewCurtainBinding
    public void setIsLocationDeviceNameExchange(boolean z) {
        this.mIsLocationDeviceNameExchange = z;
        synchronized (this) {
            this.d |= 2;
        }
        notifyPropertyChanged(BR.isLocationDeviceNameExchange);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ViewCurtainBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        Drawable drawable;
        DeviceInfo deviceInfo;
        String str;
        int i;
        String str2;
        String str3;
        int i2;
        TextView textView;
        int i3;
        ImageView imageView;
        int i4;
        Resources resources;
        synchronized (this) {
            j = this.d;
            this.d = 0L;
        }
        DeviceInfoWrapper deviceInfoWrapper = this.mItem;
        boolean z = this.mIsLocationDeviceNameExchange;
        int i5 = ((j & 9) > 0L ? 1 : ((j & 9) == 0L ? 0 : -1));
        boolean z2 = false;
        String str4 = null;
        if (i5 != 0) {
            deviceInfo = deviceInfoWrapper != null ? deviceInfoWrapper.getDeviceInfo() : null;
            boolean z3 = deviceInfo != null ? deviceInfo.isOnline : false;
            if (i5 != 0) {
                j = z3 ? j | 32 | 128 : j | 16 | 64;
            }
            if (!z3) {
                z2 = true;
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
                i3 = R.drawable.sh_ic_curtain_online;
            } else {
                imageView = this.ivOfflinePic;
                i3 = R.drawable.sh_ic_curtain_offline;
            }
            drawable = getDrawableFromResource(imageView, i3);
            if ((j & 9) != 0) {
                j = z2 ? j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : j | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            }
            if (z2) {
                resources = this.tvOffline.getResources();
                i4 = R.string.device_status_offline;
            } else {
                resources = this.tvOffline.getResources();
                i4 = R.string.device_status_on;
            }
            str = resources.getString(i4);
        } else {
            i = 0;
            str = null;
            deviceInfo = null;
            drawable = null;
        }
        int i6 = ((j & 11) > 0L ? 1 : ((j & 11) == 0L ? 0 : -1));
        if (!(i6 == 0 || i6 == 0)) {
            j = z ? j | 512 | PlaybackStateCompat.ACTION_PLAY_FROM_URI : j | 256 | PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        if ((j & 13056) != 0) {
            if (deviceInfoWrapper != null) {
                deviceInfo = deviceInfoWrapper.getDeviceInfo();
            }
            str3 = ((j & 8448) == 0 || deviceInfo == null) ? null : deviceInfo.deviceName;
            str2 = ((j & 4608) == 0 || deviceInfo == null) ? null : deviceInfo.roomInfo;
        } else {
            str3 = null;
            str2 = null;
        }
        int i7 = ((11 & j) > 0L ? 1 : ((11 & j) == 0L ? 0 : -1));
        if (i7 != 0) {
            str4 = z ? str2 : str3;
            if (!z) {
                str3 = str2;
            }
        } else {
            str3 = null;
        }
        if ((j & 9) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivOfflinePic, drawable);
            TextViewBindingAdapter.setText(this.tvOffline, str);
            this.tvOffline.setTextColor(i);
        }
        if (i7 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceType, str4);
            TextViewBindingAdapter.setText(this.tvLocation, str3);
        }
    }
}
