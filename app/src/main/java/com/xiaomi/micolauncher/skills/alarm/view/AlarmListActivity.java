package com.xiaomi.micolauncher.skills.alarm.view;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
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
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public final class AlarmListActivity extends BaseEventActivity implements DateSelectedListener {
    private Handler a;
    private long b;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_calendar);
        this.a = getHandler();
        this.b = getIntent().getLongExtra(AlarmModel.KEY_SELECT_TIMESTAMP, TimeUtils.getMidnightTimeStamp());
        a();
        d();
        e();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        findViewById(R.id.frameLayoutCalendar).setBackgroundResource(R.drawable.baike_bg);
        this.d = (RecyclerView) findViewById(R.id.recyclerViewDays);
        this.c = new LinearLayoutManager(this, 0, false);
        this.d.setLayoutManager(this.c);
        this.e = new DateListAdapter(AlarmUiUtil.getDayList(this.b), this, this.b);
        this.d.setAdapter(this.e);
        this.d.scrollToPosition(30);
        this.d.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmListActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    AlarmListActivity alarmListActivity = AlarmListActivity.this;
                    alarmListActivity.a(alarmListActivity.c);
                    return;
                }
                AlarmListActivity.this.a.removeCallbacks(AlarmListActivity.this.g);
            }
        });
        this.d.getVerticalScrollbarPosition();
        this.g = new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmListActivity$oeW1jA5ME8Y5T9MFZrZ4V2-d8K8
            @Override // java.lang.Runnable
            public final void run() {
                AlarmListActivity.this.f();
            }
        };
        this.h = (TextView) findViewById(R.id.textViewGuidance);
        this.i = (TextView) findViewById(R.id.textViewProposal);
        this.j = (RecyclerView) findViewById(R.id.recyclerViewAlarmList);
        this.j.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.j.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmListActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    AlarmListActivity alarmListActivity = AlarmListActivity.this;
                    alarmListActivity.a(alarmListActivity.c);
                    return;
                }
                AlarmListActivity.this.a.removeCallbacks(AlarmListActivity.this.g);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() {
        Logger logger = L.base;
        logger.d("CalendarFragment, delay scroll to position:" + this.f);
        this.d.scrollToPosition(this.f);
        this.e.setSelectedDateTimestamp(TimeUtils.getMidnightTimeStamp());
        b();
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
    public void onAlarmCreate(AlarmCreationPromptEvent alarmCreationPromptEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmModifyEvent(AlarmModifyEvent alarmModifyEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmDelete(AlarmDeleteEvent alarmDeleteEvent) {
        if (AlarmModel.getInstance().getAlarmSize() > 0) {
            b();
        } else {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmBoomEvent(AlarmBoomEvent alarmBoomEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMidnightEvent(MidnightEvent midnightEvent) {
        L.alarm.d("onMidnightEvent");
        this.e = new DateListAdapter(AlarmUiUtil.getDayList(), this, TimeUtils.getMidnightTimeStamp());
        this.d.setAdapter(this.e);
        this.d.scrollToPosition(30);
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeSyncEvent(TimeSyncEvent timeSyncEvent) {
        L.alarm.d("onTimeSyncEvent");
        this.e = new DateListAdapter(AlarmUiUtil.getDayList(), this, TimeUtils.getMidnightTimeStamp());
        this.d.setAdapter(this.e);
        this.d.scrollToPosition(30);
        b();
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.adapter.DateSelectedListener
    public boolean getIndicator(long j) {
        return AlarmModel.getInstance().hasAlarmInDay(j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        finish();
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.adapter.DateSelectedListener
    public void onDateItemClicked(long j, long j2) {
        if (j != j2) {
            a(this.c);
            d();
            e();
        }
    }

    private void b() {
        c();
        d();
        e();
    }

    private void c() {
        this.e.notifyDataSetChanged();
    }

    private void d() {
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

    private void e() {
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
