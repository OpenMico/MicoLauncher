package com.xiaomi.smarthome.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.DeviceStatusIndicatorViewHolder;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceStatusIndicatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Boolean> a;

    public void updateDeviceStatus(List<Boolean> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DeviceStatusIndicatorViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.os_status_indicator, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        List<Boolean> list = this.a;
        if (list != null && list.size() >= i) {
            DeviceStatusIndicatorViewHolder deviceStatusIndicatorViewHolder = (DeviceStatusIndicatorViewHolder) viewHolder;
            switch (i) {
                case 0:
                    if (this.a.size() > 1) {
                        deviceStatusIndicatorViewHolder.bind(this.a.get(1).booleanValue());
                        return;
                    }
                    return;
                case 1:
                    deviceStatusIndicatorViewHolder.bind(this.a.get(0).booleanValue());
                    return;
                case 2:
                    deviceStatusIndicatorViewHolder.bind(this.a.get(2).booleanValue());
                    return;
                default:
                    deviceStatusIndicatorViewHolder.bind(this.a.get(i).booleanValue());
                    return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Boolean> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
