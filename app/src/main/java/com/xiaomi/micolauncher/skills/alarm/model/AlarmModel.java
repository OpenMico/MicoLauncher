package com.xiaomi.micolauncher.skills.alarm.model;

import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.services.job.JobTaskUtil;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.DateUtil;
import com.xiaomi.micolauncher.instruciton.impl.AlertsOperation;
import com.xiaomi.micolauncher.skills.alarm.AlarmStatHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmCreationPromptEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmDeleteEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmModifyEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmTimeoutEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.MidnightEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.TimeSyncEvent;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.Circle;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.AlarmEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AlarmModel {
    public static final String KEY_ALARM_ID = "alarm_id";
    public static final String KEY_SELECT_TIMESTAMP = "SELECT_TIMESTAMP";
    public static final String STATUS_OFF = "off";
    public static final String STATUS_ON = "on";
    private static volatile AlarmModel a;
    private long h;
    private long i;
    private volatile boolean k;
    private boolean l;
    private volatile AlarmRealmObjectBean m;
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    public static final long HOUR_MILLISECONDS = TimeUnit.HOURS.toMillis(1);
    public static final long DAY_SECONDS = TimeUnit.DAYS.toSeconds(1);
    public static final long DAY_MILLISECONDS = TimeUnit.DAYS.toMillis(1);
    private final AtomicBoolean c = new AtomicBoolean(true);
    private final Map<String, AlarmRealmObjectBean> d = new ConcurrentHashMap(8);
    private List<Integer> e = new ArrayList();
    private final Runnable g = new $$Lambda$AlarmModel$yviCjurvFT3jjIBKBzUfA0lxOeU(this);
    private long j = Long.MAX_VALUE;
    private volatile boolean n = false;
    private final Handler f = ThreadUtil.getLightWorkHandler();

    /* loaded from: classes3.dex */
    public interface SyncTimeListener {
        void onSyncTimeFail();

        void onSyncTimeSuccess(long j);
    }

    private AlarmModel() {
        MicoDate.updateHolidayAndWorkday();
        EventBusRegistry.getEventBus().register(this);
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$EgPCBQCFXN9-wWJZJYqSrHvv23E
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.d();
            }
        });
    }

    public static AlarmModel getInstance() {
        if (a == null) {
            synchronized (AlarmModel.class) {
                if (a == null) {
                    a = new AlarmModel();
                }
            }
        }
        return a;
    }

    public void enable() {
        this.c.set(true);
        this.n = true;
        L.alarm.i("enable");
    }

    public void disable() {
        this.c.set(false);
        this.n = true;
        L.alarm.i("disable");
    }

    public boolean getIsFiring() {
        return this.k;
    }

    public void setFiringStatus() {
        this.k = true;
        this.n = true;
    }

    public void clearFiringStatus() {
        this.k = false;
        this.n = true;
    }

    public AlarmRealmObjectBean getLastFiringAlarmItem() {
        return this.m;
    }

    public boolean hasAlarmInDay(long j) {
        String status;
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            if (value.getLocalTimestamp() <= DAY_MILLISECONDS + j && ((status = value.getStatus()) == null || !status.equals("off"))) {
                if (value.isInDay(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private LinkedList<AlarmRealmObjectBean> a() {
        return getAlarmListByDay(TimeUtils.getMidnightTimeStamp());
    }

    public LinkedList<AlarmRealmObjectBean> getAlarmListByDay(long j) {
        String status;
        LinkedList<AlarmRealmObjectBean> linkedList = null;
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            if (value.getLocalTimestamp() <= DAY_MILLISECONDS + j && ((status = value.getStatus()) == null || !status.equals("off"))) {
                if (value.isInDay(j)) {
                    if (linkedList == null) {
                        linkedList = new LinkedList<>();
                    }
                    linkedList.add(value);
                }
            }
        }
        a(linkedList);
        return linkedList;
    }

    public AlarmRealmObjectBean getAlarm(String str) {
        return this.d.get(str);
    }

    public int getAlarmSize() {
        return this.d.size();
    }

    public boolean hasAvailableAlarm() {
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            if (value.getStatus() != null && value.getStatus().equals("on")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAlarm(int i) {
        LinkedList<AlarmRealmObjectBean> a2 = a();
        if (a2 == null || a2.size() <= 0) {
            return false;
        }
        Iterator<AlarmRealmObjectBean> it = a2.iterator();
        Calendar instance = Calendar.getInstance();
        while (it.hasNext()) {
            AlarmRealmObjectBean next = it.next();
            instance.setTimeInMillis(next.getLocalTimestamp());
            int i2 = instance.get(11);
            if (i2 >= i && i2 <= i + 1 && !next.expireInDay()) {
                return true;
            }
        }
        return false;
    }

    public void createAlarm(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, String str11, String str12, String str13, String str14, AlarmStatHelper.Source source) {
        a(str, str2, j, str3, str4, str5, str6, str7, str8, str9, str10, i, str11, str12, str13, str14, source);
    }

    public void modifyAlarmStatus(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i, String str10, String str11, String str12, String str13) {
        a(str, str2, j, str3, str4, str5, str6, str7, str8, str9, i, str10, str11, str12, str13);
    }

    public void modifyAlarmStatus(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        modifyAlarmStatus(arrayList, str2);
    }

    public void modifyAlarmStatus(List<String> list, String str) {
        a(list, str);
    }

    public boolean removeAlarm(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        b(arrayList);
        return true;
    }

    public void removeAlarm(List<String> list) {
        b(list);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMiotInitFinish(SpeechManager.UpdateMiotInitFinish updateMiotInitFinish) {
        g();
    }

    public void closeActivity() {
        b();
        EventBusRegistry.getEventBus().post(new AlarmVoiceCloseEvent());
    }

    private void b() {
        this.j = Long.MAX_VALUE;
    }

    public void c() {
        boolean z;
        boolean z2 = this.n;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.i > HOUR_MILLISECONDS) {
            L.alarm.d("AlarmModel alarmLoop");
            this.i = currentTimeMillis;
        }
        char c = 0;
        boolean z3 = true;
        if (TimeUtils.isMidnight()) {
            L.alarm.d("now is midnight, ts:%s", Long.valueOf(currentTimeMillis));
            e();
            HourlyChimeMode.getInstance().resetHourlyChimeAfterMidnight();
        } else if (currentTimeMillis - this.h > DAY_MILLISECONDS) {
            L.alarm.w("check midnight failed, reset alarm.");
            e();
            HourlyChimeMode.getInstance().resetHourlyChimeAfterMidnight();
        }
        long milliSecondsFromMidnight = TimeUtils.getMilliSecondsFromMidnight();
        AlarmRealmObjectBean alarmRealmObjectBean = null;
        boolean z4 = false;
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            boolean checkBoom = value.checkBoom(milliSecondsFromMidnight);
            if (z2) {
                Logger logger = L.alarm;
                Object[] objArr = new Object[2];
                objArr[c] = value;
                z3 = true;
                objArr[1] = Boolean.valueOf(checkBoom);
                logger.d("when AlarmModel alarmLoop checked alarm %s, boom=%s", objArr);
            }
            if (checkBoom) {
                value.setBoom(z3);
                Logger logger2 = L.alarm;
                Object[] objArr2 = new Object[2];
                objArr2[0] = Long.valueOf(milliSecondsFromMidnight);
                Date localDate = value.getLocalDate();
                char c2 = z3 ? 1 : 0;
                char c3 = z3 ? 1 : 0;
                char c4 = z3 ? 1 : 0;
                objArr2[c2] = localDate;
                logger2.d("alarm boom,millisFromMidnight=%d alarm date:%s", objArr2);
                String circle = value.getCircle();
                if (circle == null || !circle.equals(Circle.ONCE)) {
                    alarmRealmObjectBean = value;
                } else {
                    alarmRealmObjectBean = value;
                    z4 = true;
                }
            }
            c = 0;
            z3 = true;
        }
        if (alarmRealmObjectBean != null) {
            this.m = alarmRealmObjectBean;
            f();
            if (z4) {
                g();
            }
            L.alarm.i("this.enable.get() : %s", Boolean.valueOf(this.c.get()));
            if (this.c.get()) {
                String event = this.m.getEvent();
                String translateDate = DateUtil.translateDate(this.m.getLocalDate());
                if (!VoipModel.getInstance().isVoipActive()) {
                    if (this.m.isBabyCourse()) {
                        PlayerApi.playBabyCourse(this.m.getIdStr());
                    } else {
                        EventBusRegistry.getEventBus().postSticky(AlarmBoomEvent.ofAlarm(event, translateDate));
                    }
                    ScreenUtil.turnScreenOn(MicoApplication.getGlobalContext());
                    AlarmStatHelper.recordAlarmRing();
                } else {
                    L.alarm.i("OperatorModel.isCalling, stop Alarm");
                    AlarmEvent alarmEvent = new AlarmEvent();
                    if (this.m.isBabyCourse()) {
                        alarmEvent.type = AlarmEvent.AlarmType.BABY_COURSE;
                    } else {
                        alarmEvent.type = AlarmEvent.AlarmType.ALARM;
                    }
                    alarmEvent.event = event;
                    alarmEvent.time = translateDate;
                    alarmEvent.alarmId = this.m.getIdStr();
                    EventBusRegistry.getEventBus().post(alarmEvent);
                }
            } else if (z2) {
                L.alarm.w("disabled alarm, ignored");
            }
        }
        if (currentTimeMillis > this.j) {
            L.alarm.d("alarm fire for 10 minutes, stop alarm");
            b();
            EventBusRegistry.getEventBus().post(new AlarmTimeoutEvent());
            z = false;
        } else {
            z = false;
        }
        this.n = z;
        this.f.removeCallbacks(new $$Lambda$AlarmModel$yviCjurvFT3jjIBKBzUfA0lxOeU(this));
        this.f.postDelayed(new $$Lambda$AlarmModel$yviCjurvFT3jjIBKBzUfA0lxOeU(this), b);
    }

    public void d() {
        this.h = System.currentTimeMillis();
        Iterator<AlarmRealmObjectBean> it = AlarmRealmHelper.getInstance().queryAllAlarm().iterator();
        while (it.hasNext()) {
            AlarmRealmObjectBean next = it.next();
            next.resetLocalTimeStamp();
            this.d.put(next.getIdStr(), next);
            a(next);
            Log.d("MICO.alarm", "loadAlarm alarmRealmItem toString=%s" + next);
        }
        L.alarm.i("loadAlarm alarm count:%s", Integer.valueOf(this.d.size()));
        AlertsOperation.storeAlerts();
    }

    private static void a(List<AlarmRealmObjectBean> list) {
        if (ContainerUtil.getSize(list) > 1) {
            list.sort($$Lambda$AlarmModel$1Ei3qXtmVYY2tzBmWdy5NxwyZLk.INSTANCE);
        }
    }

    public static /* synthetic */ int a(AlarmRealmObjectBean alarmRealmObjectBean, AlarmRealmObjectBean alarmRealmObjectBean2) {
        return Long.compare(alarmRealmObjectBean.getBoomMilliSecondsFromMidnight(), alarmRealmObjectBean2.getBoomMilliSecondsFromMidnight());
    }

    private void e() {
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            entry.getValue().setBoom(false);
        }
        EventBusRegistry.getEventBus().post(new MidnightEvent());
        this.h = System.currentTimeMillis();
    }

    public void resetAlarmAfterTimeSync() {
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$75DAMg60A8HkfJztm9I2R7ihGK8
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.h();
            }
        });
    }

    public /* synthetic */ void h() {
        if (this.l) {
            L.alarm.w("alarm looped, return!");
            return;
        }
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            if (value.expireInDay()) {
                value.setBoom(true);
            } else {
                value.setBoom(false);
            }
        }
        this.h = System.currentTimeMillis();
        EventBusRegistry.getEventBus().post(new TimeSyncEvent());
        this.f.removeCallbacks(this.g);
        this.f.post(this.g);
        this.l = true;
        restartPeakCacheJob();
    }

    private void f() {
        this.j = System.currentTimeMillis() + 600000;
    }

    public String toJsonString(boolean z) {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
                AlarmRealmObjectBean value = entry.getValue();
                if (!value.isBabyCourse()) {
                    JSONObject jSONObject2 = new JSONObject();
                    String circle = value.getCircle();
                    if (circle == null || !circle.equals(Circle.ONCE) || value.getLocalTimestamp() >= System.currentTimeMillis()) {
                        jSONObject2.put("id", (Object) value.getIdStr());
                        if (z) {
                            jSONObject2.put("timestamp", (Object) Long.valueOf(value.getLocalTimestamp() / 1000));
                        } else {
                            jSONObject2.put("timestamp", (Object) Long.valueOf(value.getTimestamp() / 1000));
                        }
                        jSONObject2.put("circle", (Object) Integer.valueOf(AlarmRealmObjectBean.getCircleValue(value.getCircle())));
                        if (value.getStatus() == null || !value.getStatus().equals("on")) {
                            jSONObject2.put("status", (Object) 1);
                        } else {
                            jSONObject2.put("status", (Object) 0);
                        }
                        jSONObject2.put(SchemaActivity.KEY_VOLUME, (Object) Integer.valueOf(value.getVolume()));
                        jSONObject2.put("event", (Object) value.getEvent());
                        jSONObject2.put("reminder", (Object) value.getReminder());
                        jSONObject2.put("time_reminder", (Object) value.getTimeReminder());
                        jSONObject2.put("ringtone_query", (Object) value.getRingtone());
                        jSONObject2.put("ringtone_type", (Object) value.getRingtoneType());
                        jSONObject2.put("display_txt", (Object) value.getDisplayTxt());
                        jSONObject2.put("ringtone_video", (Object) value.getRingtoneVideo());
                        jSONObject2.put("ringtone_video_image", (Object) value.getRingtoneVideoImage());
                        jSONObject2.put("updatetimestamp", (Object) Long.valueOf(value.getUpdateTimestamp()));
                        String circleExtra = value.getCircleExtra();
                        if (circleExtra != null && circleExtra.length() > 0) {
                            if (z) {
                                jSONObject2.put("extra", (Object) AlarmRealmObjectBean.convertCircleExtraFromBrainToApp(circleExtra));
                            } else {
                                jSONObject2.put("circle_extra", (Object) circleExtra);
                            }
                        }
                        jSONArray.add(jSONObject2);
                    }
                }
            }
            jSONObject.put("alarm", (Object) jSONArray);
            return jSONObject.toString();
        } catch (Exception e) {
            L.alarm.w(e);
            return "";
        }
    }

    private void a(AlarmRealmObjectBean alarmRealmObjectBean) {
        int isHourly = TimeUtils.isHourly(alarmRealmObjectBean.getLocalTimestamp());
        if (isHourly >= 0) {
            this.e.add(Integer.valueOf(isHourly));
            L.alarm.d("addHourlyAlarmClock alarmRealmItem: %s, hourlyAlarmClock=%d", alarmRealmObjectBean, Integer.valueOf(isHourly));
        }
    }

    private void b(AlarmRealmObjectBean alarmRealmObjectBean) {
        int isHourly = TimeUtils.isHourly(alarmRealmObjectBean.getLocalTimestamp());
        if (this.e.contains(Integer.valueOf(isHourly))) {
            this.e.remove(Integer.valueOf(isHourly));
            L.alarm.d("removeHourlyAlarmClock alarmRealmItem: %s, hourlyAlarmClock=%d", alarmRealmObjectBean, Integer.valueOf(isHourly));
        }
    }

    private void g() {
        L.alarm.i("sync alarm to server");
        AlertsOperation.storeAlerts();
    }

    private void a(final String str, final String str2, final long j, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final int i, final String str11, final String str12, final String str13, final String str14, final AlarmStatHelper.Source source) {
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$a1tflAqRF3u6QTGyqsjr90SzHDs
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.a(str, str2, j, str3, str5, str6, str7, str8, str9, str10, i, str11, str12, str13, str14, source, str4);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v9 */
    public /* synthetic */ void a(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i, String str10, String str11, String str12, String str13, AlarmStatHelper.Source source, String str14) {
        int i2;
        L.alarm.i("to create, idStr=%s, type=%s, timestamp=%s, reminder=%s, circle=%s, circleExtra=%s, status=%s, event=%s, ringtone=%s, timeReminder=%s, volume=%s, ringtoneType=%s, displayTxt=%s, ringtoneVideo=%s, ringtoneVideoImage=%s, source=%s", str, str2, Long.valueOf(j), str3, str4, str5, str6, str7, str8, str9, Integer.valueOf(i), str10, str11, str12, str13, source);
        AlarmRealmObjectBean alarmRealmObjectBean = new AlarmRealmObjectBean(0, str, str2, j, str3, str14, str4, str5, str6, str7, str8, str9, i, str10, str11, str12, str13);
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            if (entry.getValue().equals(alarmRealmObjectBean)) {
                L.alarm.w("there is a same alarm exist, so ignore Create alarm!!!");
                return;
            }
        }
        if (!alarmRealmObjectBean.isInDay(TimeUtils.getMidnightTimeStamp())) {
            i2 = 1;
        } else if (alarmRealmObjectBean.expireInDay()) {
            i2 = 1;
            alarmRealmObjectBean.setBoom(true);
        } else {
            i2 = 1;
        }
        this.d.put(str, alarmRealmObjectBean);
        a(alarmRealmObjectBean);
        AlarmRealmHelper.getInstance().a(alarmRealmObjectBean);
        Logger logger = L.alarm;
        Object[] objArr = new Object[i2];
        objArr[0] = str8;
        logger.i("AlarmModel#createAlarmSafe scheduleJobWhenCreateAlarm ringtone=%s", objArr);
        JobTaskUtil.scheduleJobWhenCreateAlarm(alarmRealmObjectBean);
        AlarmCreationPromptEvent alarmCreationPromptEvent = new AlarmCreationPromptEvent();
        alarmCreationPromptEvent.alarmItem = alarmRealmObjectBean;
        EventBusRegistry.getEventBus().post(alarmCreationPromptEvent);
        AlarmStatHelper.recordAlarmAdd(alarmRealmObjectBean, source);
        g();
        this.n = i2;
    }

    private void a(final String str, final String str2, final long j, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final int i, final String str10, final String str11, final String str12, final String str13) {
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$CVbDD3pD_DZ9_J66nEyC2aX_ZCc
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.b(str, str2, j, str3, str4, str5, str6, str7, str8, str9, i, str10, str11, str12, str13);
            }
        });
    }

    public /* synthetic */ void b(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i, String str10, String str11, String str12, String str13) {
        boolean z;
        L.alarm.i("to modify, idStr %s, type %s, timestamp %s, reminder %s, circle %s, circleExtra %s, status %s, event %s, ringtone %s, timeReminder %s, volume %s, ringtoneType %s, displayTxt %s, ringtoneVideo %s, ringtoneVideoImage %s", str, str2, Long.valueOf(j), str3, str4, str5, str6, str7, str8, str9, Integer.valueOf(i), str10, str11, str12, str13);
        AlarmRealmObjectBean alarmRealmObjectBean = this.d.get(str);
        if (alarmRealmObjectBean == null) {
            L.alarm.i("not found %s in %s alarms", str, Integer.valueOf(ContainerUtil.getSize(this.d)));
            return;
        }
        AlarmRealmObjectBean alarmRealmObjectBean2 = new AlarmRealmObjectBean(alarmRealmObjectBean.getId(), alarmRealmObjectBean.getIdStr(), str2, j, str3, alarmRealmObjectBean.getPosition(), str4, str5, str6, str7, str8, str9, i, str10, str11, str12, str13);
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            if (entry.getValue().equals(alarmRealmObjectBean2)) {
                L.alarm.w("there is a same alarm exist, so ignore Update alarm!!!");
                return;
            }
        }
        long midnightTimeStamp = TimeUtils.getMidnightTimeStamp();
        if (alarmRealmObjectBean != null) {
            AlarmRealmHelper instance = AlarmRealmHelper.getInstance();
            alarmRealmObjectBean.setAlarmType(str2);
            alarmRealmObjectBean.setTimestamp(j);
            alarmRealmObjectBean.setReminder(str3);
            alarmRealmObjectBean.setTimeReminder(str9);
            alarmRealmObjectBean.setCircle(str4);
            alarmRealmObjectBean.setCircleExtra(str5);
            alarmRealmObjectBean.setStatus(str6);
            alarmRealmObjectBean.setEvent(str7);
            alarmRealmObjectBean.setRingtone(str8);
            alarmRealmObjectBean.setVolume(i);
            alarmRealmObjectBean.setRingtoneType(str10);
            alarmRealmObjectBean.setDisplayTxt(str11);
            alarmRealmObjectBean.setRingtoneVideo(str12);
            alarmRealmObjectBean.setRingtoneVideoImage(str13);
            alarmRealmObjectBean.resetLocalTimeStamp();
            if (!str6.equals("on") || !alarmRealmObjectBean.isInDay(midnightTimeStamp)) {
                z = true;
            } else if (alarmRealmObjectBean.expireInDay()) {
                z = true;
                alarmRealmObjectBean.setBoom(true);
            } else {
                z = true;
                alarmRealmObjectBean.setBoom(false);
            }
            instance.b(alarmRealmObjectBean);
            L.alarm.i("AlarmModel#modifyAlarmStatusSafe scheduleJobWhenUpdateAlarm ");
            JobTaskUtil.scheduleJobWhenUpdateAlarm(alarmRealmObjectBean);
            AlarmModifyEvent alarmModifyEvent = new AlarmModifyEvent();
            alarmModifyEvent.alarmItem = alarmRealmObjectBean;
            EventBusRegistry.getEventBus().post(alarmModifyEvent);
            g();
            L.alarm.i("modify success");
            this.n = z;
            return;
        }
        L.alarm.i("not found %s in %s alarms", str, Integer.valueOf(ContainerUtil.getSize(this.d)));
    }

    private void a(final List<String> list, final String str) {
        if (ContainerUtil.isEmpty(list)) {
            L.alarm.e("no input arg");
            DebugHelper.printStackTrace("modifyAlarmStatus");
            return;
        }
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$FFVUdLAb-dCA-vVBCMjF7IPS8Ro
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.b(list, str);
            }
        });
    }

    public /* synthetic */ void b(List list, String str) {
        L.alarm.i("modifyAlarmStatus ids=%s, status=%s", TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, list), str);
        long midnightTimeStamp = TimeUtils.getMidnightTimeStamp();
        AlarmRealmHelper instance = AlarmRealmHelper.getInstance();
        Iterator it = list.iterator();
        boolean z = false;
        while (it.hasNext()) {
            String str2 = (String) it.next();
            AlarmRealmObjectBean alarmRealmObjectBean = this.d.get(str2);
            if (alarmRealmObjectBean != null) {
                if (!str.equals(alarmRealmObjectBean.getStatus())) {
                    if (str.equals("on") && alarmRealmObjectBean.isInDay(midnightTimeStamp)) {
                        if (alarmRealmObjectBean.expireInDay()) {
                            alarmRealmObjectBean.setBoom(true);
                        } else {
                            alarmRealmObjectBean.setBoom(false);
                        }
                    }
                    alarmRealmObjectBean.setStatus(str);
                    instance.b(alarmRealmObjectBean);
                    AlarmModifyEvent alarmModifyEvent = new AlarmModifyEvent();
                    alarmModifyEvent.alarmItem = alarmRealmObjectBean;
                    EventBusRegistry.getEventBus().post(alarmModifyEvent);
                    JobTaskUtil.scheduleJobWhenUpdateAlarm(alarmRealmObjectBean);
                    L.alarm.i("modify AlarmStatus success, and scheduled update alarm");
                }
                z = true;
            } else {
                L.alarm.i("not found %s in %s alarms", str2, Integer.valueOf(ContainerUtil.getSize(this.d)));
            }
        }
        if (z) {
            this.n = true;
            g();
        }
    }

    private void b(final List<String> list) {
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$2QpOkAMSnecaYCM4hDsnSJSWdYo
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.c(list);
            }
        });
    }

    public /* synthetic */ void c(List list) {
        boolean z = false;
        L.alarm.i("to remove %s alarms, data:%s", Integer.valueOf(ContainerUtil.getSize(list)), TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, list));
        AlarmRealmHelper instance = AlarmRealmHelper.getInstance();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            AlarmRealmObjectBean alarmRealmObjectBean = this.d.get(str);
            if (alarmRealmObjectBean != null) {
                instance.a(alarmRealmObjectBean.getId());
                this.d.remove(str);
                b(alarmRealmObjectBean);
                L.alarm.i("AlarmModel#removeAlarmSafe cancel cache Job ");
                JobTaskUtil.cancelJob(alarmRealmObjectBean.getId(), alarmRealmObjectBean.getTimestamp());
                z = true;
            }
        }
        if (z) {
            this.n = true;
            EventBusRegistry.getEventBus().post(new AlarmDeleteEvent());
            g();
        }
    }

    public LinkedList<AlarmRealmObjectBean> getAlarmWillRing() {
        long millis = TimeUnit.MINUTES.toMillis(15L);
        long milliSecondsFromMidnight = TimeUtils.getMilliSecondsFromMidnight();
        LinkedList<AlarmRealmObjectBean> linkedList = null;
        AlarmRealmObjectBean alarmRealmObjectBean = null;
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            String status = value.getStatus();
            if (status == null || !status.equals("off")) {
                String circle = value.getCircle();
                if (circle == null || !circle.equals(Circle.ONCE) || value.getLocalTimestamp() >= System.currentTimeMillis()) {
                    long boomMilliSecondsFromMidnight = value.getBoomMilliSecondsFromMidnight() - milliSecondsFromMidnight;
                    if (boomMilliSecondsFromMidnight > 0 && boomMilliSecondsFromMidnight < millis) {
                        alarmRealmObjectBean = value;
                        millis = boomMilliSecondsFromMidnight;
                    }
                    if (linkedList == null) {
                        linkedList = new LinkedList<>();
                    }
                    linkedList.add(value);
                }
            }
        }
        a(linkedList);
        if (alarmRealmObjectBean != null) {
            linkedList.remove(alarmRealmObjectBean);
            linkedList.addFirst(alarmRealmObjectBean);
        }
        return linkedList;
    }

    public void restartPeakCacheJob() {
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            String status = value.getStatus();
            boolean z = Circle.ONCE.equalsIgnoreCase(value.getCircle()) && value.getAlarmTimestampWithTimeZone() <= System.currentTimeMillis();
            L.alarm.d("restartPeakCacheJob alarmRealmObject status %s, onceExpire %s,", status, Boolean.valueOf(z));
            if (status == null || !status.equals("off")) {
                if (!z) {
                    L.alarm.d("restartPeakCacheJob -> scheduleJobWhenUpdateAlarm ");
                    JobTaskUtil.scheduleJobWhenUpdateAlarm(value);
                }
            }
        }
    }

    public boolean isLooping() {
        return this.l;
    }

    public List<Integer> getHourlyAlarmClock() {
        return this.e;
    }

    public void syncTime(final SyncTimeListener syncTimeListener) {
        L.alarm.i("syncTime");
        ApiManager.rawService.pingTime().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<ResponseBody>() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmModel.1
            /* renamed from: a */
            public void onSuccess(ResponseBody responseBody) {
                try {
                    long longValue = JSONObject.parseObject(responseBody.string()).getJSONObject("data").getLong(ai.aF).longValue();
                    L.alarm.i("syncTime success serverTime=%d", Long.valueOf(longValue));
                    SystemClock.setCurrentTimeMillis(longValue);
                    if (syncTimeListener != null) {
                        syncTimeListener.onSyncTimeSuccess(longValue);
                    }
                } catch (IOException e) {
                    L.init.e("syncTime", e);
                }
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
                SyncTimeListener syncTimeListener2 = syncTimeListener;
                if (syncTimeListener2 != null) {
                    syncTimeListener2.onSyncTimeFail();
                }
            }
        });
    }

    public void createAlarmSafe(final AlarmEntity alarmEntity) {
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$1JHbj6udwUH4jNyG_EdvNy6EV8E
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.b(alarmEntity);
            }
        });
    }

    public /* synthetic */ void b(AlarmEntity alarmEntity) {
        AlarmRealmObjectBean alarmRealmObjectBean = new AlarmRealmObjectBean(alarmEntity);
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            if (entry.getValue().equals(alarmRealmObjectBean)) {
                L.alarm.w("there is a same alarm exist, so ignore Create alarm by xiaoai brain!!!");
                return;
            }
        }
        if (alarmRealmObjectBean.isInDay(TimeUtils.getMidnightTimeStamp()) && alarmRealmObjectBean.expireInDay()) {
            alarmRealmObjectBean.setBoom(true);
        }
        this.d.put(alarmRealmObjectBean.getIdStr(), alarmRealmObjectBean);
        a(alarmRealmObjectBean);
        AlarmRealmHelper.getInstance().a(alarmRealmObjectBean);
        L.alarm.i("create alarm by xiaoai brain alarmItem=%s", alarmRealmObjectBean);
        if ("alarm".equalsIgnoreCase(alarmEntity.type)) {
            JobTaskUtil.scheduleJobWhenCreateAlarm(alarmRealmObjectBean);
        }
        AlarmStatHelper.recordAlarmAdd(alarmRealmObjectBean, alarmEntity.source);
        g();
        this.n = true;
    }

    public void modifyAlarmStatusSafe(final AlarmEntity alarmEntity) {
        this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmModel$my_sANMROJoaAUCY4-dLybbVBRY
            @Override // java.lang.Runnable
            public final void run() {
                AlarmModel.this.a(alarmEntity);
            }
        });
    }

    public /* synthetic */ void a(AlarmEntity alarmEntity) {
        long midnightTimeStamp = TimeUtils.getMidnightTimeStamp();
        AlarmRealmObjectBean alarmRealmObjectBean = this.d.get(alarmEntity.id);
        if (alarmRealmObjectBean != null) {
            AlarmRealmHelper instance = AlarmRealmHelper.getInstance();
            alarmRealmObjectBean.setAlarmType(alarmEntity.type);
            alarmRealmObjectBean.setTimestamp(alarmEntity.getTimestampMillisTimeZone());
            alarmRealmObjectBean.setReminder(alarmEntity.reminder);
            alarmRealmObjectBean.setTimeReminder(alarmEntity.timeReminder);
            alarmRealmObjectBean.setCircle(alarmEntity.getCircleString());
            alarmRealmObjectBean.setCircleExtra(alarmEntity.getCircleExtra());
            alarmRealmObjectBean.setStatus(alarmEntity.getStatus());
            alarmRealmObjectBean.setEvent(alarmEntity.event);
            alarmRealmObjectBean.setRingtone(alarmEntity.ringtoneQuery);
            alarmRealmObjectBean.setVolume(alarmEntity.volume);
            alarmRealmObjectBean.setRingtoneType(alarmEntity.ringtoneType);
            alarmRealmObjectBean.setDisplayTxt(alarmEntity.displayTxt);
            alarmRealmObjectBean.setRingtoneVideo(alarmEntity.ringtoneVideo);
            alarmRealmObjectBean.setRingtoneVideoImage(alarmEntity.ringtoneVideoImage);
            alarmRealmObjectBean.resetLocalTimeStamp();
            if ("on".equals(alarmEntity.getStatus()) && alarmRealmObjectBean.isInDay(midnightTimeStamp)) {
                if (alarmRealmObjectBean.expireInDay()) {
                    alarmRealmObjectBean.setBoom(true);
                } else {
                    alarmRealmObjectBean.setBoom(false);
                }
            }
            instance.b(alarmRealmObjectBean);
            AlarmModifyEvent alarmModifyEvent = new AlarmModifyEvent();
            alarmModifyEvent.alarmItem = alarmRealmObjectBean;
            EventBusRegistry.getEventBus().post(alarmModifyEvent);
            L.alarm.i("to modify alarmItem=%s", alarmRealmObjectBean);
            g();
            L.alarm.i("modify success");
            this.n = true;
            return;
        }
        L.alarm.i("not found %s in %s alarms", alarmEntity.id, Integer.valueOf(ContainerUtil.getSize(this.d)));
    }

    public List<AlarmRealmObjectBean> getAlarmList() {
        String circle;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, AlarmRealmObjectBean> entry : this.d.entrySet()) {
            AlarmRealmObjectBean value = entry.getValue();
            if (!value.isBabyCourse() && ((circle = value.getCircle()) == null || !circle.equals(Circle.ONCE) || value.getLocalTimestamp() >= System.currentTimeMillis())) {
                arrayList.add(value);
            }
        }
        return arrayList;
    }
}
