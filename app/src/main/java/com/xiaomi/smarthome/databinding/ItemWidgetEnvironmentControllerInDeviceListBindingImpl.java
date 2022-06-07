package com.xiaomi.smarthome.databinding;

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
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.smarthome.BR;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class ItemWidgetEnvironmentControllerInDeviceListBindingImpl extends ItemWidgetEnvironmentControllerInDeviceListBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts b = null;
    @Nullable
    private static final SparseIntArray c = new SparseIntArray();
    @NonNull
    private final ConstraintLayout d;
    private long e;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        c.put(R.id.tvTemperature, 9);
        c.put(R.id.ivTempSymbol, 10);
        c.put(R.id.ivConditionMode, 11);
        c.put(R.id.vBgIncrease, 12);
        c.put(R.id.ivIncrease, 13);
        c.put(R.id.vBgDecrease, 14);
        c.put(R.id.ivDecrease, 15);
    }

    public ItemWidgetEnvironmentControllerInDeviceListBindingImpl(@Nullable DataBindingComponent dataBindingComponent, @NonNull View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 16, b, c));
    }

    private ItemWidgetEnvironmentControllerInDeviceListBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (Group) objArr[4], (Group) objArr[2], (Group) objArr[3], (ImageView) objArr[11], (ImageView) objArr[15], (ImageView) objArr[5], (ImageView) objArr[8], (ImageView) objArr[13], (ImageView) objArr[6], (ImageView) objArr[10], (TextView) objArr[1], (TextView) objArr[7], (TextView) objArr[9], (View) objArr[14], (View) objArr[12]);
        this.e = -1L;
        this.decreaseButton.setTag(null);
        this.groupTemp.setTag(null);
        this.increaseButton.setTag(null);
        this.ivDevicePic.setTag(null);
        this.ivDragHandle.setTag(null);
        this.ivStatus.setTag(null);
        this.d = (ConstraintLayout) objArr[0];
        this.d.setTag(null);
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

    @Override // com.xiaomi.smarthome.databinding.ItemWidgetEnvironmentControllerInDeviceListBinding
    public void setItem(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        this.mItem = deviceInfoWrapper;
        synchronized (this) {
            this.e |= 1;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override // com.xiaomi.smarthome.databinding.ItemWidgetEnvironmentControllerInDeviceListBinding
    public void setEditMode(boolean z) {
        this.mEditMode = z;
        synchronized (this) {
            this.e |= 2;
        }
        notifyPropertyChanged(BR.editMode);
        super.requestRebind();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r12v10, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r12v13 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 848
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.databinding.ItemWidgetEnvironmentControllerInDeviceListBindingImpl.executeBindings():void");
    }
}
