package com.xiaomi.micolauncher.skills.alarm.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmDetailActivity;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes3.dex */
public class CalendarListAdapter extends RecyclerView.Adapter<a> {
    private List<AlarmRealmObjectBean> a;
    private long b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder {
        ImageView a;
        TextView b;
        View c;
        TextView d;

        public a(View view) {
            super(view);
            this.a = (ImageView) view.findViewById(R.id.imageViewIcon);
            this.b = (TextView) view.findViewById(R.id.textviewAlarmTime);
            this.c = view.findViewById(R.id.viewSeperator);
            this.d = (TextView) view.findViewById(R.id.textviewAlarmEvent);
        }
    }

    public CalendarListAdapter(List<AlarmRealmObjectBean> list, long j) {
        this.a = list;
        this.b = j;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public a onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_alarm_list_item, viewGroup, false));
    }

    public void onBindViewHolder(a aVar, int i) {
        if (i < this.a.size()) {
            final AlarmRealmObjectBean alarmRealmObjectBean = this.a.get(i);
            Calendar instance = Calendar.getInstance();
            instance.setTime(alarmRealmObjectBean.getLocalDate());
            int i2 = instance.get(11);
            int i3 = instance.get(12);
            aVar.b.setText(TimeCalculator.completedString(i2) + Constants.COLON_SEPARATOR + TimeCalculator.completedString(i3));
            String event = alarmRealmObjectBean.getEvent();
            if (event == null || event.length() <= 0) {
                aVar.d.setText(SkillDataManager.APP_ALARM);
                GlideUtils.bindImageView(aVar.a.getContext(), (int) R.drawable.calender_icon_clock, aVar.a);
            } else {
                aVar.d.setText(event);
                GlideUtils.bindImageView(aVar.a.getContext(), (int) R.drawable.calender_icon_notice, aVar.a);
            }
            aVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.adapter.-$$Lambda$CalendarListAdapter$uKCelX1tHWmOKRtvPhEvjJkRG1A
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CalendarListAdapter.a(AlarmRealmObjectBean.this, view);
                }
            });
            if (alarmRealmObjectBean.getBoomMilliSecondsFromMidnight() + this.b > System.currentTimeMillis()) {
                aVar.itemView.setAlpha(1.0f);
            } else {
                aVar.itemView.setAlpha(0.5f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AlarmRealmObjectBean alarmRealmObjectBean, View view) {
        Intent intent = new Intent(MicoApplication.getGlobalContext(), AlarmDetailActivity.class);
        intent.putExtra(AlarmModel.KEY_ALARM_ID, alarmRealmObjectBean.getIdStr());
        MicoApplication.getGlobalContext().startActivity(intent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<AlarmRealmObjectBean> list = this.a;
        if (list != null) {
            return list.size();
        }
        return 0;
    }
}
