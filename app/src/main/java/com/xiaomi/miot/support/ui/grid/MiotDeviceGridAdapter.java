package com.xiaomi.miot.support.ui.grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.ui.DeviceOperator;
import com.xiaomi.miot.support.ui.MiotCardItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotDeviceGridAdapter extends BaseAdapter {
    private final Context mContext;
    private List<DeviceInfo> mDevices = new ArrayList();
    private final DeviceOperator mOperator;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MiotDeviceGridAdapter(Context context, DeviceOperator deviceOperator) {
        this.mContext = context;
        this.mOperator = deviceOperator;
    }

    public void setDevices(List<DeviceInfo> list) {
        this.mDevices.clear();
        this.mDevices.addAll(list);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mDevices.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mDevices.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.miot_support_card_item, viewGroup, false);
        }
        ((MiotCardItem) view).updateDevice(this.mDevices.get(i), this.mOperator);
        return view;
    }
}
