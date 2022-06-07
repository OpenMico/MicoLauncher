package com.xiaomi.micolauncher.module;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.skills.alarm.adapter.CalendarListAdapter;
import com.xiaomi.micolauncher.skills.alarm.adapter.DateListAdapter;
import com.xiaomi.micolauncher.skills.alarm.adapter.DateSelectedListener;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmCreationPromptEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmDeleteEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmModifyEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.MidnightEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.TimeSyncEvent;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmUiUtil;
import java.util.LinkedList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class CalendarFragment extends BaseFragment implements DateSelectedListener {
    private Handler a;
    private boolean b;
    private LinearLayoutManager c;
    private RecyclerView d;
    private DateListAdapter e;
    private int f;
    private Runnable g;
    private TextView h;
    private TextView i;
    private RecyclerView j;
    private CalendarListAdapter k;

    @Override // com.xiaomi.micolauncher.skills.alarm.adapter.DateSelectedListener
    public void onSelectDateChanged(long j, long j2) {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_calendar, viewGroup, false);
        this.a = getActivity().getMainThreadHandler();
        this.d = (RecyclerView) inflate.findViewById(R.id.recyclerViewDays);
        this.c = new LinearLayoutManager(getActivity(), 0, false);
        this.d.setLayoutManager(this.c);
        this.e = new DateListAdapter(AlarmUiUtil.getDayList(), this, TimeUtils.getMidnightTimeStamp());
        this.d.setAdapter(this.e);
        this.d.scrollToPosition(30);
        this.d.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.CalendarFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    L.base.d("CalendarFragment, setDelayScrollToInitialPosRunnable");
                    CalendarFragment calendarFragment = CalendarFragment.this;
                    calendarFragment.a(calendarFragment.c);
                    return;
                }
                CalendarFragment.this.a.removeCallbacks(CalendarFragment.this.g);
            }
        });
        this.d.getVerticalScrollbarPosition();
        this.g = new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$CalendarFragment$M9-dDgaMmkdOGkE3vtg4BYTw4k0
            @Override // java.lang.Runnable
            public final void run() {
                CalendarFragment.this.e();
            }
        };
        this.h = (TextView) inflate.findViewById(R.id.textViewGuidance);
        this.i = (TextView) inflate.findViewById(R.id.textViewProposal);
        this.j = (RecyclerView) inflate.findViewById(R.id.recyclerViewAlarmList);
        this.j.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.j.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.CalendarFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    CalendarFragment calendarFragment = CalendarFragment.this;
                    calendarFragment.a(calendarFragment.c);
                    return;
                }
                CalendarFragment.this.a.removeCallbacks(CalendarFragment.this.g);
            }
        });
        c();
        d();
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        inflate.setTag(this);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        Logger logger = L.base;
        logger.d("CalendarFragment, delay scroll to position:" + this.f);
        this.d.scrollToPosition(this.f);
        this.e.setSelectedDateTimestamp(TimeUtils.getMidnightTimeStamp());
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(LinearLayoutManager linearLayoutManager) {
        if (linearLayoutManager.findFirstVisibleItemPosition() > 30) {
            this.f = 30;
            this.a.removeCallbacks(this.g);
            this.a.postDelayed(this.g, 60000L);
            return;
        }
        this.f = 36;
        this.a.removeCallbacks(this.g);
        this.a.postDelayed(this.g, 60000L);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmCreateEvent(AlarmCreationPromptEvent alarmCreationPromptEvent) {
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmModifyEvent(AlarmModifyEvent alarmModifyEvent) {
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmDeleteEvent(AlarmDeleteEvent alarmDeleteEvent) {
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmBoomEvent(AlarmBoomEvent alarmBoomEvent) {
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMidnightEvent(MidnightEvent midnightEvent) {
        L.alarm.d("onMidnightEvent");
        this.e = new DateListAdapter(AlarmUiUtil.getDayList(), this, TimeUtils.getMidnightTimeStamp());
        this.d.setAdapter(this.e);
        this.d.scrollToPosition(30);
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeSyncEvent(TimeSyncEvent timeSyncEvent) {
        L.alarm.d("onTimeSyncEvent");
        this.e = new DateListAdapter(AlarmUiUtil.getDayList(), this, TimeUtils.getMidnightTimeStamp());
        this.d.setAdapter(this.e);
        this.d.scrollToPosition(30);
        a();
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.adapter.DateSelectedListener
    public boolean getIndicator(long j) {
        return AlarmModel.getInstance().hasAlarmInDay(j);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.adapter.DateSelectedListener
    public void onDateItemClicked(long j, long j2) {
        if (j != j2) {
            a(this.c);
            c();
            d();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        this.b = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        if (this.b) {
            this.b = false;
            if (this.c.findFirstVisibleItemPosition() > 30) {
                this.f = 30;
            } else {
                this.f = 36;
            }
            Logger logger = L.base;
            logger.d("CalendarFragment, delay scroll to position:" + this.f);
            this.a.removeCallbacks(this.g);
            this.a.post(this.g);
        }
        MiotDeviceManager.getInstance().refreshMIoTDevice();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.b = false;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    private void a() {
        b();
        c();
        d();
    }

    private void b() {
        this.e.notifyDataSetChanged();
    }

    private void c() {
        long selectedDateTimestamp = this.e.getSelectedDateTimestamp();
        LinkedList<AlarmRealmObjectBean> alarmListByDay = AlarmModel.getInstance().getAlarmListByDay(selectedDateTimestamp);
        this.k = new CalendarListAdapter(alarmListByDay, selectedDateTimestamp);
        this.j.setAdapter(this.k);
        if (alarmListByDay != null && alarmListByDay.size() > 0) {
            for (int i = 0; i < alarmListByDay.size(); i++) {
                if (!alarmListByDay.get(i).isBoom()) {
                    this.j.scrollToPosition(i);
                    return;
                }
            }
        }
    }

    private void d() {
        if (this.k.getItemCount() > 0) {
            this.h.setVisibility(4);
            this.i.setVisibility(4);
            this.j.setVisibility(0);
            return;
        }
        this.h.setVisibility(0);
        this.i.setVisibility(0);
        this.j.setVisibility(4);
    }
}
