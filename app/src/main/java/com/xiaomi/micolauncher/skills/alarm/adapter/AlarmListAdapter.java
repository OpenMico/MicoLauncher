package com.xiaomi.micolauncher.skills.alarm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmItemViewHolder;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmVolumeItemViewHolder;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class AlarmListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_ALARM = 100000;
    public static final int ITEM_ALARM_VOLUME = 200000;
    private final Activity a;
    private final List<AlarmRealmObjectBean> b;
    private int c;

    public AlarmListAdapter(Activity activity, List<AlarmRealmObjectBean> list) {
        this.a = activity;
        this.b = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (ContainerUtil.getSize(this.b) <= 0 || i < ContainerUtil.getSize(this.b)) ? 100000 : 200000;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i != 200000) {
            return new AlarmItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alarm_view, viewGroup, false));
        }
        return new AlarmVolumeItemViewHolder(this.a, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alarm_volume_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof AlarmItemViewHolder) {
            AlarmItemViewHolder alarmItemViewHolder = (AlarmItemViewHolder) viewHolder;
            if (i < this.b.size()) {
                alarmItemViewHolder.refreshView(this.b.get(i), i);
            }
        } else if (viewHolder instanceof AlarmVolumeItemViewHolder) {
            ((AlarmVolumeItemViewHolder) viewHolder).refreshView();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (ContainerUtil.hasData(this.b)) {
            return this.b.size() + 1;
        }
        return 0;
    }

    public boolean isEditing() {
        return this.c > 0;
    }

    public void update() {
        this.c = 0;
        notifyDataSetChanged();
    }
}
