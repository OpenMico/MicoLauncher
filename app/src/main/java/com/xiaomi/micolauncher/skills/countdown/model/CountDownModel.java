package com.xiaomi.micolauncher.skills.countdown.model;

import android.os.Handler;
import android.os.Looper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.DateUtil;
import com.xiaomi.micolauncher.instruciton.impl.AlertsOperation;
import com.xiaomi.micolauncher.skills.alarm.AlarmStatHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlertType;
import com.xiaomi.micolauncher.skills.countdown.controller.uievent.CountDownCancelEvent;
import com.xiaomi.micolauncher.skills.countdown.controller.uievent.DismissCountDownUiEvent;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.AlarmEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class CountDownModel {
    public static String KEY_COUNTDOWN_SECONDS = "KEY_COUNTDOWN_SECONDS";
    private static volatile CountDownModel a;
    private CountDownStatus b = CountDownStatus.STOP;
    private Handler c = new Handler(Looper.getMainLooper());
    private Runnable d = new Runnable() { // from class: com.xiaomi.micolauncher.skills.countdown.model.-$$Lambda$CountDownModel$Q7SvKegdZXOQKDkcOuI_ZMn40vg
        @Override // java.lang.Runnable
        public final void run() {
            CountDownModel.this.a();
        }
    };
    private long e;
    private long f;
    private AlarmRealmObjectBean g;

    /* loaded from: classes3.dex */
    public enum CountDownStatus {
        START,
        FIRING,
        STOP
    }

    private CountDownModel() {
        EventBusRegistry.getEventBus().register(this);
    }

    public static CountDownModel getInstance() {
        if (a == null) {
            synchronized (CountDownModel.class) {
                if (a == null) {
                    a = new CountDownModel();
                }
            }
        }
        return a;
    }

    public void setIntention(AlarmRealmObjectBean alarmRealmObjectBean) {
        if (this.b == CountDownStatus.START) {
            cancel();
        }
        this.g = alarmRealmObjectBean;
    }

    public AlarmRealmObjectBean getAlarmItem() {
        return this.g;
    }

    public synchronized boolean start(long j) {
        Logger logger = L.countdown;
        logger.d("start countdown, seconds:" + j);
        if (j >= 15 && j <= 2592000) {
            if (!(this.b == CountDownStatus.START || this.b == CountDownStatus.FIRING)) {
                this.e = j;
                this.f = System.currentTimeMillis();
                this.b = CountDownStatus.START;
                this.c.postDelayed(this.d, this.e * 1000);
                b();
                return true;
            }
            L.countdown.d("start countdown failed, cancel last countdown first.");
            return false;
        }
        return false;
    }

    public synchronized void cancel() {
        if (this.b == CountDownStatus.STOP) {
            L.alarm.w("cancel in wrong status.");
            return;
        }
        L.countdown.d("cancel countdown");
        if (clear()) {
            EventBusRegistry.getEventBus().post(new CountDownCancelEvent());
            clearAlarmItemAndReport();
        }
    }

    public synchronized void a() {
        L.countdown.d("fire countdown");
        if (this.g != null) {
            String event = this.g.getEvent();
            String translateDate = DateUtil.translateDate(this.g.getLocalDate());
            if (!VoipModel.getInstance().isVoipActive()) {
                this.b = CountDownStatus.FIRING;
                EventBusRegistry.getEventBus().post(AlarmBoomEvent.ofCountDown(event, translateDate));
                EventBusRegistry.getEventBus().post(new AlarmVoiceCloseEvent());
                AlarmStatHelper.recordTimerRing();
            } else {
                AlarmEvent alarmEvent = new AlarmEvent();
                alarmEvent.type = AlarmEvent.AlarmType.COUNTDOWN;
                alarmEvent.event = event;
                alarmEvent.time = translateDate;
                EventBusRegistry.getEventBus().post(alarmEvent);
                getInstance().clear();
                getInstance().clearAlarmItemAndReport();
                EventBusRegistry.getEventBus().post(new CountDownCancelEvent());
            }
        }
    }

    public void notifyDismissUi() {
        EventBusRegistry.getEventBus().post(new DismissCountDownUiEvent());
    }

    public synchronized boolean clear() {
        boolean z;
        L.countdown.d("clear countdown");
        if (!(this.b == CountDownStatus.START || this.b == CountDownStatus.FIRING)) {
            z = false;
            this.c.removeCallbacks(this.d);
            this.b = CountDownStatus.STOP;
        }
        z = true;
        this.c.removeCallbacks(this.d);
        this.b = CountDownStatus.STOP;
        return z;
    }

    public synchronized void clearAlarmItemAndReport() {
        L.countdown.d("clear item and report server");
        this.g = null;
        b();
    }

    public synchronized CountDownStatus getStatus() {
        return this.b;
    }

    public synchronized long getRemainSeconds() {
        long currentTimeMillis;
        currentTimeMillis = this.e - ((System.currentTimeMillis() - this.f) / 1000);
        if (currentTimeMillis < 0) {
            currentTimeMillis = 0;
        }
        return currentTimeMillis;
    }

    public synchronized String toJsonString(boolean z) {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (this.g != null) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("id", (Object) this.g.getIdStr());
            if (z) {
                jSONObject2.put("timestamp", (Object) Long.valueOf((this.g.getLocalTimestamp() / 1000) + 3));
            } else {
                jSONObject2.put("timestamp", (Object) Long.valueOf(this.g.getTimestamp() / 1000));
            }
            if (this.g.getStatus() == null || !this.g.getStatus().equals("on")) {
                jSONObject2.put("status", (Object) 1);
            } else {
                jSONObject2.put("status", (Object) 0);
            }
            jSONObject2.put(SchemaActivity.KEY_VOLUME, (Object) 100);
            jSONObject2.put("event", (Object) this.g.getEvent());
            jSONArray.add(jSONObject2);
        }
        jSONObject.put(AlertType.TIMER, (Object) jSONArray);
        return jSONObject.toString();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMiotInitFinish(SpeechManager.UpdateMiotInitFinish updateMiotInitFinish) {
        b();
    }

    private void b() {
        AlertsOperation.storeAlerts();
    }
}
