package com.xiaomi.miot.support.ui.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.ui.DeviceOperator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotDevicePagerAdapter extends PagerAdapter {
    private final int mColumns;
    private final Context mContext;
    private final DeviceOperator mOperator;
    private final int mPaddingBottom;
    private final int mPaddingEnd;
    private final int mPaddingStart;
    private final int mPaddingTop;
    private final int mRows;
    private final int mSpacingH;
    private final int mSpacingV;
    private List<DeviceInfo> mDevices = new ArrayList();
    private List<Object> mCacheItems = new ArrayList();

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object obj) {
        return -2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MiotDevicePagerAdapter(Context context, DeviceOperator deviceOperator, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mContext = context;
        this.mOperator = deviceOperator;
        this.mRows = i;
        this.mColumns = i2;
        this.mPaddingStart = i3;
        this.mPaddingEnd = i4;
        this.mPaddingTop = i5;
        this.mPaddingBottom = i6;
        this.mSpacingH = i7;
        this.mSpacingV = i8;
    }

    public void setDevices(List<DeviceInfo> list) {
        this.mDevices.clear();
        this.mDevices.addAll(list);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        int i = this.mRows * this.mColumns;
        if (this.mDevices.size() % i == 0) {
            return this.mDevices.size() / i;
        }
        return (this.mDevices.size() / i) + 1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        MiotDevicePagerItem miotDevicePagerItem;
        if (this.mCacheItems.size() > 0) {
            miotDevicePagerItem = (MiotDevicePagerItem) this.mCacheItems.get(0);
            this.mCacheItems.remove(miotDevicePagerItem);
        } else {
            miotDevicePagerItem = (MiotDevicePagerItem) LayoutInflater.from(this.mContext).inflate(R.layout.miot_support_device_pager_item, viewGroup, false);
            miotDevicePagerItem.addItems(this.mRows, this.mColumns, this.mPaddingStart, this.mPaddingEnd, this.mPaddingTop, this.mPaddingBottom, this.mSpacingH, this.mSpacingV);
        }
        viewGroup.addView(miotDevicePagerItem);
        miotDevicePagerItem.updateItems(getDevicesForPage(i), this.mOperator);
        return miotDevicePagerItem;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        viewGroup.removeView((View) obj);
        this.mCacheItems.add(obj);
    }

    private List<DeviceInfo> getDevicesForPage(int i) {
        int i2 = this.mRows * this.mColumns;
        int i3 = i * i2;
        int i4 = (i + 1) * i2;
        if (i4 > this.mDevices.size()) {
            i4 = this.mDevices.size();
        }
        return this.mDevices.subList(i3, i4);
    }
}
