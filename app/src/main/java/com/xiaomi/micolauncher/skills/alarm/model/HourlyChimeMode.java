package com.xiaomi.micolauncher.skills.alarm.model;

import android.annotation.SuppressLint;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.event.HourlyChimeEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class HourlyChimeMode {
    private static volatile HourlyChimeMode a;
    private final List<HourlyChimeObject> b = new ArrayList();
    private int c = -1;

    private HourlyChimeMode() {
        MicoDate.updateHolidayAndWorkday();
        EventBusRegistry.getEventBus().register(this);
        a();
    }

    public static HourlyChimeMode getInstance() {
        if (a == null) {
            synchronized (HourlyChimeMode.class) {
                if (a == null) {
                    a = new HourlyChimeMode();
                }
            }
        }
        return a;
    }

    private void a(List<HourlyChimeObject> list) {
        this.b.clear();
        if (list != null) {
            for (HourlyChimeObject hourlyChimeObject : list) {
                this.b.add(hourlyChimeObject);
                L.hourlychime.d("loadHourlyChime %s", hourlyChimeObject);
            }
            L.hourlychime.d("loadHourlyChime count:%s", Integer.valueOf(this.b.size()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hourlyChimeChange(HourlyChimeEvent hourlyChimeEvent) {
        L.hourlychime.d("HourlyChimeMode  hourlyChimeChange()");
        a();
    }

    @SuppressLint({"CheckResult"})
    private void a() {
        HourlyChimeUtils.cancelAlarmManager();
        HourlyChimeUtils.loadHourlyChime().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeMode$JMUFvZzhEAbNbswXP6lbvngd8Hw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HourlyChimeMode.this.b((List) obj);
            }
        });
    }

    public /* synthetic */ void b(List list) throws Exception {
        a(list);
        if (this.b.size() > 0) {
            resetHourlyChimeAfterTimeSync();
        }
    }

    public void addHourlyChime(HourlyChimeObject hourlyChimeObject) {
        if (ThreadUtil.isMainThread()) {
            if (hourlyChimeObject.getId() >= this.b.get(0).getId()) {
                int id = hourlyChimeObject.getId();
                List<HourlyChimeObject> list = this.b;
                if (id <= list.get(list.size() - 1).getId()) {
                    int i = -1;
                    Iterator<HourlyChimeObject> it = this.b.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        i++;
                        if (hourlyChimeObject.getId() <= it.next().getId()) {
                            this.b.add(i, hourlyChimeObject);
                            break;
                        }
                    }
                } else {
                    this.b.add(hourlyChimeObject);
                }
            } else {
                this.b.add(0, hourlyChimeObject);
            }
            HourlyChimeUtils.cancelAlarmManager();
            c();
            requestHourlyResource();
            return;
        }
        throw new IllegalThreadStateException("function addHourlyChime must run in main thread");
    }

    public void deleteHourlyChime(HourlyChimeObject hourlyChimeObject) {
        if (ThreadUtil.isMainThread()) {
            Iterator<HourlyChimeObject> it = this.b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                HourlyChimeObject next = it.next();
                if (hourlyChimeObject.getId() == next.getId()) {
                    this.b.remove(next);
                    break;
                }
            }
            HourlyChimeUtils.cancelAlarmManager();
            c();
            requestHourlyResource();
            return;
        }
        throw new IllegalThreadStateException("function deleteHourlyChime must run in main thread");
    }

    public void resetHourlyChimeAfterTimeSync() {
        if (ThreadUtil.isMainThread()) {
            b();
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeMode$D55l4hH5ijFHJjFexK69Jc8_A9w
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeMode.this.b();
                }
            });
        }
    }

    public void b() {
        c();
        requestHourlyResource();
    }

    private void c() {
        boolean z = false;
        for (int i = 0; i < this.b.size(); i++) {
            HourlyChimeObject hourlyChimeObject = this.b.get(i);
            if (!TimeUtils.hasExpired(hourlyChimeObject.getPosition())) {
                hourlyChimeObject.setBoom(true);
                this.c = i;
                L.hourlychime.d("resetHourlyChimeAfterTimeSync for hour:  %s  boom: %b", hourlyChimeObject.getContent(), Boolean.valueOf(hourlyChimeObject.isBoom()));
                z = true;
            } else {
                hourlyChimeObject.setBoom(false);
            }
            if (z) {
                return;
            }
        }
    }

    public void requestHourlyResource() {
        if (ThreadUtil.isMainThread()) {
            d();
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeMode$LMVGHh6ATARaDPh_gyp7Tj-6kMk
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeMode.this.d();
                }
            });
        }
    }

    public void playHourlyResource() {
        if (ThreadUtil.isMainThread()) {
            e();
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$gwhWBktaJvCWopWX9jZtoFrzg7U
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeMode.this.playHourlyResource();
                }
            });
        }
    }

    public void d() {
        L.hourlychime.d("requestHourlyResource  hourlyChimeObjectIndex: %d, hourlyChimeListSize: %d ", Integer.valueOf(this.c), Integer.valueOf(this.b.size()));
        int i = this.c;
        if (i < 0 || i >= this.b.size()) {
            L.hourlychime.d("requestHourlyResource fail");
            return;
        }
        HourlyChimeObject hourlyChimeObject = this.b.get(this.c);
        long hourlyRandomTimeDuration = TimeUtils.getHourlyRandomTimeDuration(hourlyChimeObject.getPosition(), 30);
        L.hourlychime.d("requestHourlyResource  hourlyChimeItem: %s ", hourlyChimeObject);
        L.hourlychime.d("requestHourlyResource  delayMillis: %s", Long.valueOf(hourlyRandomTimeDuration));
        HourlyChimeUtils.startAlarmManager(hourlyChimeObject, hourlyRandomTimeDuration, 1);
    }

    private void e() {
        int i = this.c;
        if (i < 0 || i >= this.b.size()) {
            L.hourlychime.d("playHourlyResource fail");
            return;
        }
        HourlyChimeObject hourlyChimeObject = this.b.get(this.c);
        long hourlyDuration = TimeUtils.getHourlyDuration(hourlyChimeObject.getPosition());
        L.hourlychime.d("playHourlyResource  hourlyChimeObjectIndex: %d, hourlyChimeItem: %s", Integer.valueOf(this.c), hourlyChimeObject);
        L.hourlychime.d("playHourlyResource  delayMillis: %s", Long.valueOf(hourlyDuration));
        HourlyChimeUtils.startAlarmManager(hourlyChimeObject, hourlyDuration, 2);
        this.c++;
    }

    public void resetHourlyChimeAfterMidnight() {
        for (HourlyChimeObject hourlyChimeObject : this.b) {
            hourlyChimeObject.setBoom(false);
        }
        if (this.b.size() > 0) {
            resetHourlyChimeAfterTimeSync();
        }
    }
}
