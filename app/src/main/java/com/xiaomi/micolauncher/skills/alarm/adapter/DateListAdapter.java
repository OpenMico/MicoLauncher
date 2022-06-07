package com.xiaomi.micolauncher.skills.alarm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.TimeUtils;
import java.util.Calendar;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class DateListAdapter extends RecyclerView.Adapter<a> {
    private List<Long> a;
    private DateSelectedListener b;
    private long c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        long a;
        TextView b;
        TextView c;
        View d;

        a(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.textviewDate);
            this.c = (TextView) view.findViewById(R.id.textviewDay);
            this.d = view.findViewById(R.id.viewDot);
        }
    }

    public DateListAdapter(List<Long> list, DateSelectedListener dateSelectedListener, long j) {
        this.a = list;
        this.b = dateSelectedListener;
        this.c = j;
    }

    public long getSelectedDateTimestamp() {
        return this.c;
    }

    public void setSelectedDateTimestamp(long j) {
        long j2 = this.c;
        if (j2 != j) {
            this.c = j;
            DateSelectedListener dateSelectedListener = this.b;
            if (dateSelectedListener != null) {
                dateSelectedListener.onSelectDateChanged(j, j2);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_day_list_item, viewGroup, false));
    }

    public void onBindViewHolder(@NotNull a aVar, int i) {
        if (i < this.a.size()) {
            Long l = this.a.get(i);
            aVar.a = l.longValue();
            aVar.itemView.setTag(l);
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(l.longValue());
            int i2 = instance.get(5);
            int i3 = instance.get(7);
            String completedString = TimeCalculator.completedString(i2);
            String dayString = CommonUtils.getDayString(i3);
            aVar.b.setText(completedString);
            aVar.c.setText(dayString);
            DateSelectedListener dateSelectedListener = this.b;
            if (dateSelectedListener != null) {
                if (dateSelectedListener.getIndicator(l.longValue())) {
                    aVar.d.setVisibility(0);
                } else {
                    aVar.d.setVisibility(4);
                }
            }
            a(aVar);
            aVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.adapter.-$$Lambda$DateListAdapter$4pdfQoiafmCaFt7t9gUhH5yZ7H4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DateListAdapter.this.a(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        long j = this.c;
        this.c = ((Long) view.getTag()).longValue();
        notifyDataSetChanged();
        DateSelectedListener dateSelectedListener = this.b;
        if (dateSelectedListener != null) {
            dateSelectedListener.onDateItemClicked(this.c, j);
        }
    }

    private void a(a aVar) {
        aVar.itemView.setAlpha(1.0f);
        if (aVar.a == TimeUtils.getMidnightTimeStamp()) {
            if (this.c == aVar.a) {
                aVar.itemView.setBackgroundResource(R.drawable.bg_calendar_day);
                aVar.b.setTextColor(-1);
                aVar.c.setTextColor(-1);
                return;
            }
            aVar.itemView.setBackground(null);
            aVar.b.setTextColor(-10451969);
            aVar.c.setTextColor(-10451969);
        } else if (this.c == aVar.a) {
            aVar.itemView.setBackgroundResource(R.drawable.bg_calendar_day_not_today);
            aVar.b.setTextColor(-1);
            aVar.c.setTextColor(-1);
        } else {
            aVar.itemView.setBackground(null);
            aVar.b.setTextColor(-637534209);
            aVar.c.setTextColor(-637534209);
            if (aVar.a < TimeUtils.getMidnightTimeStamp()) {
                aVar.itemView.setAlpha(0.7f);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }
}
