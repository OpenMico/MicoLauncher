package com.xiaomi.miot.support.ui.pager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.ui.DeviceOperator;
import com.xiaomi.miot.support.ui.MiotDeviceView;
import com.xiaomi.miot.support.util.MiotUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotDevicePagerView extends MiotDeviceView {
    private static final int NO_POSITION = -1;
    private MiotDevicePagerAdapter mAdapter;
    private int mColumns;
    private LinearLayout mIndicator;
    private int mLastPosition = -1;
    private int mPaddingBottom;
    private int mPaddingEnd;
    private int mPaddingStart;
    private int mPaddingTop;
    private View mPagerView;
    private int mRows;
    private int mSpacingH;
    private int mSpacingV;
    private ViewPager mViewPager;

    public MiotDevicePagerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRows = 2;
        this.mColumns = this.mRows;
        this.mPaddingStart = MiotUtil.dp2px(getContext(), 24.0f);
        this.mPaddingEnd = this.mPaddingStart;
        this.mPaddingTop = MiotUtil.dp2px(getContext(), 24.0f);
        this.mPaddingBottom = this.mPaddingTop;
        this.mSpacingH = MiotUtil.dp2px(getContext(), 10.0f);
        this.mSpacingV = this.mSpacingH;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiotDevicePagerView);
            this.mRows = obtainStyledAttributes.getInt(R.styleable.MiotDevicePagerView_miot_support_numRows, this.mRows);
            this.mColumns = obtainStyledAttributes.getInt(R.styleable.MiotDevicePagerView_miot_support_numColumns, this.mColumns);
            this.mPaddingStart = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDevicePagerView_miot_support_paddingStart, this.mPaddingStart);
            this.mPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDevicePagerView_miot_support_paddingEnd, this.mPaddingEnd);
            this.mPaddingTop = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDevicePagerView_miot_support_paddingTop, this.mPaddingTop);
            this.mPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDevicePagerView_miot_support_paddingBottom, this.mPaddingBottom);
            this.mSpacingH = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDevicePagerView_miot_support_spacingH, this.mSpacingH);
            this.mSpacingV = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDevicePagerView_miot_support_spacingV, this.mSpacingV);
            obtainStyledAttributes.recycle();
        }
        LayoutInflater.from(getContext()).inflate(R.layout.miot_support_device_pager_view, this);
        this.mPagerView = findViewById(R.id.miot_support_device_pager);
        this.mViewPager = (ViewPager) findViewById(R.id.miot_support_device_pager_view);
        this.mIndicator = (LinearLayout) findViewById(R.id.miot_support_device_pager_indicator);
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected View getDeviceView() {
        return this.mPagerView;
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected void setupAdapter(List<DeviceInfo> list, DeviceOperator deviceOperator) {
        this.mAdapter = new MiotDevicePagerAdapter(getContext(), deviceOperator, this.mRows, this.mColumns, this.mPaddingStart, this.mPaddingEnd, this.mPaddingTop, this.mPaddingBottom, this.mSpacingH, this.mSpacingV);
        this.mAdapter.setDevices(list);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.xiaomi.miot.support.ui.pager.MiotDevicePagerView.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                MiotDevicePagerView.this.mIndicator.getChildAt(MiotDevicePagerView.this.mLastPosition).setSelected(false);
                MiotDevicePagerView.this.mIndicator.getChildAt(i).setSelected(true);
                MiotDevicePagerView.this.mLastPosition = i;
            }
        });
        updateIndicator();
        this.mViewPager.setAdapter(this.mAdapter);
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected void updateDeviceList(List<DeviceInfo> list) {
        this.mAdapter.setDevices(list);
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    public void showDeviceView() {
        super.showDeviceView();
        updateIndicator();
    }

    private void updateIndicator() {
        int childCount = this.mIndicator.getChildCount();
        int count = this.mAdapter.getCount();
        if (childCount < count) {
            while (childCount < count) {
                ImageView imageView = new ImageView(this.mIndicator.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                int dp2px = MiotUtil.dp2px(this.mIndicator.getContext(), 6.0f);
                layoutParams.setMarginStart(dp2px);
                layoutParams.setMarginEnd(dp2px);
                imageView.setImageResource(R.drawable.miot_support_indicator_circle);
                this.mIndicator.addView(imageView, layoutParams);
                childCount++;
            }
        } else if (childCount > count) {
            this.mIndicator.removeViews(count, childCount - count);
        }
        int i = 0;
        if (count > 0) {
            int currentItem = this.mViewPager.getCurrentItem();
            int i2 = this.mLastPosition;
            if (i2 == -1) {
                this.mLastPosition = currentItem;
                this.mIndicator.getChildAt(currentItem).setSelected(true);
            } else if (i2 != currentItem) {
                this.mLastPosition = currentItem;
                int i3 = 0;
                while (i3 < count) {
                    this.mIndicator.getChildAt(i3).setSelected(i3 == currentItem);
                    i3++;
                }
            }
        } else {
            this.mLastPosition = -1;
        }
        LinearLayout linearLayout = this.mIndicator;
        if (count <= 1) {
            i = 8;
        }
        linearLayout.setVisibility(i);
    }
}
