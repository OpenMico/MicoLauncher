package com.xiaomi.miot.support.ui.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.ui.DeviceOperator;
import com.xiaomi.miot.support.ui.MiotCardItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotDevicePagerItem extends LinearLayout {
    private List<MiotCardItem> mItems = new ArrayList();

    public MiotDevicePagerItem(Context context) {
        super(context);
    }

    public MiotDevicePagerItem(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void addItems(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        setPaddingRelative(i3, i5, i4, i6);
        for (int i9 = 0; i9 < i; i9++) {
            addRow(i9, i2, i7, i8);
        }
    }

    private void addRow(int i, int i2, int i3, int i4) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        if (i > 0) {
            layoutParams.topMargin = i4;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            addColumn(linearLayout, i5, i3);
        }
        addView(linearLayout, layoutParams);
    }

    private void addColumn(LinearLayout linearLayout, int i, int i2) {
        MiotCardItem miotCardItem = (MiotCardItem) LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.miot_support_card_item, (ViewGroup) linearLayout, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
        if (i > 0) {
            layoutParams.setMarginStart(i2);
        }
        linearLayout.addView(miotCardItem, layoutParams);
        this.mItems.add(miotCardItem);
    }

    public void updateItems(List<DeviceInfo> list, DeviceOperator deviceOperator) {
        int i = 0;
        while (i < list.size() && i < this.mItems.size()) {
            this.mItems.get(i).setVisibility(0);
            this.mItems.get(i).updateDevice(list.get(i), deviceOperator);
            i++;
        }
        while (i < this.mItems.size()) {
            this.mItems.get(i).setVisibility(4);
            i++;
        }
    }
}
