package com.xiaomi.miot.support.ui.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.ui.DeviceOperator;
import com.xiaomi.miot.support.ui.MiotDeviceView;
import com.xiaomi.miot.support.ui.header.MiotHeaderData;
import com.xiaomi.miot.support.ui.header.MiotHeaderItem;
import com.xiaomi.miot.support.util.MiotUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotDeviceGridView extends MiotDeviceView {
    private MiotDeviceGridAdapter mAdapter;
    private HeaderGridView mGridView;
    private MiotHeaderData mHeaderData;
    private LinearLayout mHeaderView;
    private Typeface mNumberTypeface;

    public MiotDeviceGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i;
        int i2;
        int i3;
        int dp2px = MiotUtil.dp2px(getContext(), 24.0f);
        int dp2px2 = MiotUtil.dp2px(getContext(), 24.0f);
        int dp2px3 = MiotUtil.dp2px(getContext(), 7.0f);
        int i4 = 2;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiotDeviceGridView);
            i4 = obtainStyledAttributes.getInt(R.styleable.MiotDeviceGridView_miot_support_numColumns, 2);
            i = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDeviceGridView_miot_support_paddingStart, dp2px);
            dp2px = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDeviceGridView_miot_support_paddingEnd, dp2px);
            dp2px2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDeviceGridView_miot_support_paddingTop, dp2px2);
            i2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDeviceGridView_miot_support_paddingBottom, dp2px2);
            dp2px3 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDeviceGridView_miot_support_spacingH, dp2px3);
            i3 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiotDeviceGridView_miot_support_spacingV, dp2px3);
            obtainStyledAttributes.recycle();
        } else {
            i = dp2px;
            i2 = dp2px2;
            i3 = dp2px3;
        }
        LayoutInflater.from(getContext()).inflate(R.layout.miot_support_device_grid_view, this);
        this.mGridView = (HeaderGridView) findViewById(R.id.miot_support_device_grid);
        this.mGridView.setNumColumns(i4);
        this.mGridView.setPaddingRelative(i, dp2px2, dp2px, i2);
        this.mGridView.setHorizontalSpacing(dp2px3);
        this.mGridView.setVerticalSpacing(i3);
        this.mNumberTypeface = Typeface.createFromAsset(getResources().getAssets(), "numbers.ttf");
        this.mHeaderView = new LinearLayout(getContext());
        this.mHeaderView.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.miot_support_header_height)));
        this.mHeaderView.setGravity(16);
        this.mHeaderView.setOrientation(0);
        this.mGridView.addHeaderView(this.mHeaderView);
        this.mHeaderView.setVisibility(8);
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected View getDeviceView() {
        return this.mGridView;
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected void setupAdapter(List<DeviceInfo> list, DeviceOperator deviceOperator) {
        this.mAdapter = new MiotDeviceGridAdapter(getContext(), deviceOperator);
        this.mAdapter.setDevices(list);
        this.mGridView.setAdapter((ListAdapter) this.mAdapter);
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected void updateDeviceList(List<DeviceInfo> list) {
        this.mAdapter.setDevices(list);
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceView
    protected void updateEvents(List<MiotDeviceEvent> list, boolean z) {
        if (z || (this.mGridView.getHeaderViewCount() == 0 && !list.isEmpty())) {
            MiotHeaderData parseEvents = this.mHelper.parseEvents(this.mHelper.getHostDeviceRoomId(), list);
            if (parseEvents == null) {
                this.mHeaderData = null;
                this.mHeaderView.setVisibility(8);
            } else {
                this.mHeaderView.setVisibility(0);
                if (!parseEvents.equals(this.mHeaderData)) {
                    this.mHeaderData = parseEvents;
                    updateHeaderView();
                }
            }
        }
        MiotDeviceGridAdapter miotDeviceGridAdapter = this.mAdapter;
        if (miotDeviceGridAdapter != null) {
            miotDeviceGridAdapter.notifyDataSetChanged();
        }
    }

    private void updateHeaderView() {
        this.mHeaderView.removeAllViews();
        if (this.mHeaderData.mTemperature != null) {
            fillHeaderItem(0, MiotDeviceEvent.Type.TEMPERATURE, this.mHeaderData.mTemperature);
            if (this.mHeaderData.mHumidity != null) {
                addDivider();
                fillHeaderItem(2, MiotDeviceEvent.Type.HUMIDITY, this.mHeaderData.mHumidity);
                if (this.mHeaderData.mPM25 != null) {
                    addDivider();
                    fillHeaderItem(4, MiotDeviceEvent.Type.PM25, this.mHeaderData.mPM25);
                }
            } else if (this.mHeaderData.mPM25 != null) {
                addDivider();
                fillHeaderItem(2, MiotDeviceEvent.Type.PM25, this.mHeaderData.mPM25);
            }
        } else if (this.mHeaderData.mHumidity != null) {
            fillHeaderItem(0, MiotDeviceEvent.Type.HUMIDITY, this.mHeaderData.mHumidity);
            if (this.mHeaderData.mPM25 != null) {
                addDivider();
                fillHeaderItem(2, MiotDeviceEvent.Type.PM25, this.mHeaderData.mPM25);
            }
        } else if (this.mHeaderData.mPM25 != null) {
            fillHeaderItem(0, MiotDeviceEvent.Type.PM25, this.mHeaderData.mPM25);
        }
    }

    private void fillHeaderItem(int i, MiotDeviceEvent.Type type, MiotHeaderData.Item item) {
        View childAt = this.mHeaderView.getChildAt(i);
        if (childAt == null) {
            childAt = LayoutInflater.from(getContext()).inflate(R.layout.miot_support_header_item, (ViewGroup) this.mHeaderView, false);
            this.mHeaderView.addView(childAt);
        } else {
            childAt.setVisibility(0);
        }
        ((MiotHeaderItem) childAt).updateItem(type, item, this.mNumberTypeface);
    }

    private void addDivider() {
        this.mHeaderView.addView(LayoutInflater.from(getContext()).inflate(R.layout.miot_support_header_divider, (ViewGroup) this.mHeaderView, false));
    }

    private void hideHeaderItem(int i) {
        View childAt = this.mHeaderView.getChildAt(i);
        if (childAt != null) {
            childAt.setVisibility(8);
        }
    }
}
