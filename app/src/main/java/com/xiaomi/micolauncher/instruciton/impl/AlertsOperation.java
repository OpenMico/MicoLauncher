package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Alerts;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.alarm.AlarmStatHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.StartCountDownEvent;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmEntity;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlertType;
import com.xiaomi.micolauncher.skills.alarm.model.bean.Circle;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AlertsOperation extends BaseOperation {
    public AlertsOperation(Instruction instruction) {
        super(instruction);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        char c;
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        String fullName = fullName();
        switch (fullName.hashCode()) {
            case -1663923229:
                if (fullName.equals(AIApiConstants.Alerts.StopAlert)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -795955311:
                if (fullName.equals(AIApiConstants.Alerts.SetAlert)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -77583889:
                if (fullName.equals(AIApiConstants.Alerts.UploadAlerts)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 670244535:
                if (fullName.equals(AIApiConstants.Alerts.UpdateAlerts)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 850442777:
                if (fullName.equals(AIApiConstants.Alerts.DeleteAlerts)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return a(this.instruction);
            case 1:
                return b(this.instruction);
            case 2:
                return c(this.instruction);
            case 3:
                return d(this.instruction);
            case 4:
            default:
                return opState;
        }
    }

    private BaseOperation.OpState a(Instruction instruction) {
        Alerts.SetAlert setAlert = (Alerts.SetAlert) instruction.getPayload();
        Alerts.AlertType type = setAlert.getType();
        AlarmEntity a = a(setAlert);
        if (a == null) {
            L.alarm.i("Alerts.SetAlert parse data error");
            return BaseOperation.OpState.STATE_FAIL;
        }
        if (type == Alerts.AlertType.TIMER) {
            a(a);
        } else {
            AlarmModel.getInstance().createAlarmSafe(a);
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private BaseOperation.OpState b(Instruction instruction) {
        if (!(instruction.getPayload() instanceof Alerts.DeleteAlerts)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        Alerts.DeleteAlerts deleteAlerts = (Alerts.DeleteAlerts) instruction.getPayload();
        List<String> a = a(deleteAlerts.getAlerts());
        if (!ContainerUtil.isEmpty(a)) {
            L.alarm.i("to remove alarms, data:%s", a);
            AlarmModel.getInstance().removeAlarm(a);
        }
        if (!ContainerUtil.isEmpty(b(deleteAlerts.getAlerts()))) {
            CountDownModel.getInstance().cancel();
        }
        AlarmStatHelper.recordAlarmDelete(AlarmStatHelper.Source.QUERY);
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private BaseOperation.OpState c(Instruction instruction) {
        if (instruction.getPayload() instanceof Alerts.UpdateAlerts) {
            Alerts.UpdateAlerts updateAlerts = (Alerts.UpdateAlerts) instruction.getPayload();
            List<String> a = a(updateAlerts.getAlerts());
            List<String> b = b(updateAlerts.getAlerts());
            Alerts.AlertOperation operation = updateAlerts.getOperation();
            boolean z = false;
            boolean a2 = !ContainerUtil.isEmpty(a) ? a(a, operation) : false;
            if (!ContainerUtil.isEmpty(b)) {
                z = b(a, operation);
            }
            if (z || a2) {
                return BaseOperation.OpState.STATE_SUCCESS;
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private BaseOperation.OpState d(Instruction instruction) {
        if (AlarmModel.getInstance().getIsFiring()) {
            AlarmModel.getInstance().closeActivity();
        } else {
            CountDownModel.getInstance().cancel();
            AlarmStatHelper.recordTimerDelete(AlarmStatHelper.Source.QUERY);
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private AlarmEntity a(@NonNull Alerts.SetAlert setAlert) {
        AlarmEntity alarmEntity = new AlarmEntity();
        try {
            alarmEntity.source = AlarmStatHelper.Source.QUERY;
            alarmEntity.id = setAlert.getId();
            if (setAlert.getCircle().isPresent()) {
                alarmEntity.circle = setAlert.getCircle().get().getId();
            }
            if (setAlert.getCircleExtra().isPresent()) {
                alarmEntity.extra = AlarmRealmObjectBean.convertCircleExtraFromBrainToApp(setAlert.getCircleExtra().get());
            }
            if (alarmEntity.circle != Alerts.AlertCircleType.WEEKLY.getId() || !TextUtils.isEmpty(alarmEntity.extra)) {
                if (setAlert.getReminder().isPresent()) {
                    alarmEntity.reminder = setAlert.getReminder().get();
                }
                if (setAlert.getTimeReminder().isPresent()) {
                    alarmEntity.timeReminder = setAlert.getTimeReminder().get();
                }
                if (setAlert.getRingtone().isPresent()) {
                    alarmEntity.ringtoneType = setAlert.getRingtone().get().getType().get();
                    alarmEntity.ringtoneQuery = setAlert.getRingtone().get().getRequest().get().getQuery();
                }
                if (setAlert.getEvent().isPresent()) {
                    alarmEntity.event = setAlert.getEvent().get();
                }
                Alerts.AlertType type = setAlert.getType();
                alarmEntity.type = type.name();
                if (setAlert.getAdvanceReminder().isPresent()) {
                    alarmEntity.advanceReminder = setAlert.getAdvanceReminder().get().intValue();
                }
                if (setAlert.getOffset().isPresent()) {
                    alarmEntity.offset = setAlert.getOffset().get();
                    alarmEntity.offsetSeconds = TimeCalculator.formatSeconds(alarmEntity.offset);
                }
                if (!TextUtils.isEmpty(setAlert.getDatetime())) {
                    long transformTime = TimeUtils.transformTime(setAlert.getDatetime(), TimeUtils.PATTERN_8601);
                    alarmEntity.dateTime = setAlert.getDatetime();
                    alarmEntity.timestamp = TimeUnit.MILLISECONDS.toSeconds(transformTime);
                    alarmEntity.dateTime = setAlert.getDatetime();
                }
                if (type == Alerts.AlertType.TIMER && alarmEntity.offsetSeconds > 0) {
                    alarmEntity.timestampMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(alarmEntity.offsetSeconds);
                }
                if (!TextUtils.isEmpty(alarmEntity.offset) || !TextUtils.isEmpty(alarmEntity.dateTime)) {
                    L.alarm.i("Alerts.SetAlert alarmEntity=%s", alarmEntity.toString());
                    return alarmEntity;
                }
                L.alarm.i("alarm entity time is empty");
                return null;
            }
            L.alarm.i("alarm circle extra is empty");
            return null;
        } catch (NoSuchElementException e) {
            L.alarm.e("Alerts.SetAlert parse data error", e);
            return null;
        }
    }

    private List<String> a(List<Alerts.AlertOpItem> list) {
        if (ContainerUtil.isEmpty(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Alerts.AlertOpItem alertOpItem : list) {
            if (alertOpItem.getType() == Alerts.AlertType.ALARM || alertOpItem.getType() == Alerts.AlertType.REMINDER) {
                arrayList.add(alertOpItem.getId());
            }
        }
        return arrayList;
    }

    private List<String> b(List<Alerts.AlertOpItem> list) {
        if (ContainerUtil.isEmpty(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Alerts.AlertOpItem alertOpItem : list) {
            if (alertOpItem.getType() == Alerts.AlertType.TIMER) {
                arrayList.add(alertOpItem.getId());
            }
        }
        return arrayList;
    }

    private boolean a(List<String> list, Alerts.AlertOperation alertOperation) {
        switch (alertOperation) {
            case OPEN:
                Log.d("MICO.alarm", "operateAlarm open");
                AlarmModel.getInstance().modifyAlarmStatus(list, "on");
                return true;
            case CLOSE:
                Log.d("MICO.alarm", "operateAlarm close");
                AlarmModel.getInstance().modifyAlarmStatus(list, "off");
                return true;
            case DELETE:
                AlarmModel.getInstance().removeAlarm(list);
                return true;
            case QUERY:
            case PROCEED:
            default:
                return false;
            case PAUSE:
                AlarmModel.getInstance().closeActivity();
                return false;
            case UNKNOWN:
                L.alarm.i("update alerts, operation is unknown");
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean b(List<String> list, Alerts.AlertOperation alertOperation) {
        switch (alertOperation) {
            case OPEN:
            case QUERY:
                if (CountDownModel.getInstance().getStatus() != CountDownModel.CountDownStatus.STOP) {
                    EventBusRegistry.getEventBus().post(new StartCountDownEvent(0));
                    break;
                }
                break;
            case CLOSE:
            case DELETE:
            case PAUSE:
                break;
            case PROCEED:
            default:
                return true;
            case UNKNOWN:
                L.alarm.i("update alerts, operation is unknown");
                return true;
        }
        CountDownModel.getInstance().cancel();
        return true;
    }

    private static AlarmEntity a(@NonNull Template.AlarmItem alarmItem) {
        AlarmEntity alarmEntity = new AlarmEntity();
        try {
            alarmEntity.source = AlarmStatHelper.Source.QUERY;
            alarmEntity.id = alarmItem.getId();
            if (alarmItem.getCircle().isPresent()) {
                alarmEntity.circle = alarmItem.getCircle().get().getId();
            }
            if (alarmItem.getCircleExtra().isPresent()) {
                alarmEntity.extra = AlarmRealmObjectBean.convertCircleExtraFromBrainToApp(alarmItem.getCircleExtra().get());
            }
            if (alarmEntity.circle != Alerts.AlertCircleType.WEEKLY.getId() || !TextUtils.isEmpty(alarmEntity.extra)) {
                if (alarmItem.getReminder().isPresent()) {
                    alarmEntity.reminder = alarmItem.getReminder().get();
                }
                if (alarmItem.getTimeReminder().isPresent()) {
                    alarmEntity.timeReminder = alarmItem.getTimeReminder().get();
                }
                if (alarmItem.getRingtone().isPresent()) {
                    alarmEntity.ringtoneType = alarmItem.getRingtone().get().getType().get();
                    alarmEntity.ringtoneQuery = alarmItem.getRingtone().get().getRequest().get().getQuery();
                }
                if (alarmItem.getEvent().isPresent()) {
                    alarmEntity.event = alarmItem.getEvent().get();
                }
                alarmEntity.type = alarmItem.getType().name();
                if (alarmItem.getAdvanceReminder().isPresent()) {
                    alarmEntity.advanceReminder = alarmItem.getAdvanceReminder().get().intValue();
                }
                if (alarmItem.getType() == Alerts.AlertType.TIMER) {
                    if (alarmItem.getOffset().isPresent()) {
                        alarmEntity.dateTime = alarmItem.getOffset().get();
                    }
                } else if (!TextUtils.isEmpty(alarmItem.getDatetime())) {
                    long transformTime = TimeUtils.transformTime(alarmItem.getDatetime(), TimeUtils.PATTERN_8601);
                    alarmEntity.dateTime = alarmItem.getDatetime();
                    alarmEntity.timestamp = transformTime / 1000;
                    alarmEntity.dateTime = alarmItem.getDatetime();
                }
                L.alarm.i("Template.AlarmItem alarmEntity=%s", alarmEntity.toString());
                return alarmEntity;
            }
            L.alarm.i("alarm circle extra is empty");
            return null;
        } catch (NoSuchElementException e) {
            L.alarm.e("Template.AlarmItem parse data error", e);
            return null;
        }
    }

    private void a(AlarmEntity alarmEntity) {
        int i = alarmEntity.offsetSeconds;
        L.alarm.i("create timer offsetSeconds=%s", Integer.valueOf(i));
        if (i > 0) {
            AlarmRealmObjectBean alarmRealmObjectBean = new AlarmRealmObjectBean(alarmEntity);
            CountDownModel.getInstance().setIntention(alarmRealmObjectBean);
            EventBusRegistry.getEventBus().post(new StartCountDownEvent(i));
            AlarmStatHelper.recordTimerAdd(alarmRealmObjectBean, AlarmStatHelper.Source.QUERY);
        }
    }

    public static void storeAlerts() {
        Alerts.StoreAlerts storeAlerts = new Alerts.StoreAlerts();
        List<Alerts.AlertItem> a = a();
        storeAlerts.setAlertItems(a);
        Logger logger = L.alarm;
        logger.i("storeAlerts alertItems size:" + a.size());
        try {
            L.alarm.json(Gsons.getGson().toJson(a));
        } catch (Exception e) {
            L.alarm.e("AlertsOperation#storeAlerts() catches the exception bellow:\n%s", e);
        }
        Event buildEvent = APIUtils.buildEvent(storeAlerts);
        if (SpeechEventUtil.peekInstance() != null) {
            SpeechEventUtil.getInstance().sendEventRequest(buildEvent);
        }
    }

    private static List<Alerts.AlertItem> a() {
        ArrayList arrayList = new ArrayList();
        List<AlarmRealmObjectBean> alarmList = AlarmModel.getInstance().getAlarmList();
        if (ContainerUtil.hasData(alarmList)) {
            for (AlarmRealmObjectBean alarmRealmObjectBean : alarmList) {
                arrayList.add(a(alarmRealmObjectBean));
            }
        }
        AlarmRealmObjectBean alarmItem = CountDownModel.getInstance().getAlarmItem();
        if (alarmItem != null) {
            arrayList.add(a(alarmItem));
        }
        return arrayList;
    }

    private static Alerts.AlertItem a(AlarmRealmObjectBean alarmRealmObjectBean) {
        Alerts.AlertItem alertItem = new Alerts.AlertItem();
        alertItem.setId(alarmRealmObjectBean.getIdStr());
        alertItem.setEvent(alarmRealmObjectBean.getEvent());
        alertItem.setReminder(alarmRealmObjectBean.getReminder());
        alertItem.setTimeReminder(alarmRealmObjectBean.getTimeReminder());
        if (!TextUtils.isEmpty(alarmRealmObjectBean.getAlarmType())) {
            alertItem.setType(a(alarmRealmObjectBean.getAlarmType()));
        }
        String circle = alarmRealmObjectBean.getCircle();
        if (!TextUtils.isEmpty(circle)) {
            alertItem.setCircle(b(circle));
        }
        String circleExtra = alarmRealmObjectBean.getCircleExtra();
        if (!TextUtils.isEmpty(circleExtra)) {
            alertItem.setCircleExtra(circleExtra);
        }
        alertItem.setRingtoneType(alarmRealmObjectBean.getRingtoneType());
        alertItem.setRingtoneQuery(alarmRealmObjectBean.getRingtone());
        alertItem.setRingtone(alarmRealmObjectBean.getRingtone());
        if (alertItem.getType() == Alerts.AlertType.TIMER) {
            alertItem.setOffset(alarmRealmObjectBean.getDateTime());
            alertItem.setDatetime(TimeUtils.transformTimestamp(alarmRealmObjectBean.getTimestamp(), TimeUtils.PATTERN_8601));
        } else {
            String dateTime = alarmRealmObjectBean.getDateTime();
            if (TextUtils.isEmpty(dateTime)) {
                dateTime = TimeUtils.transformTimestamp(alarmRealmObjectBean.getLocalTimestamp(), TimeUtils.PATTERN_8601);
            }
            alertItem.setDatetime(dateTime);
        }
        if ("on".equals(alarmRealmObjectBean.getStatus())) {
            alertItem.setStatus(Alerts.AlertStatus.ON);
        } else {
            alertItem.setStatus(Alerts.AlertStatus.OFF);
        }
        return alertItem;
    }

    private static Alerts.AlertType a(String str) {
        if ("alarm".equalsIgnoreCase(str)) {
            return Alerts.AlertType.ALARM;
        }
        if (AlertType.TIMER.equalsIgnoreCase(str)) {
            return Alerts.AlertType.TIMER;
        }
        if ("reminder".equalsIgnoreCase(str)) {
            return Alerts.AlertType.REMINDER;
        }
        return Alerts.AlertType.UNKNOWN;
    }

    private static Alerts.AlertCircleType b(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            return Alerts.AlertCircleType.ONCE;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -734561654:
                if (str.equals(Circle.YEARLY)) {
                    c = '\b';
                    break;
                }
                break;
            case -318797514:
                if (str.equals(Circle.MONTOFRI)) {
                    c = 3;
                    break;
                }
                break;
            case 3415681:
                if (str.equals(Circle.ONCE)) {
                    c = 0;
                    break;
                }
                break;
            case 151588239:
                if (str.equals(Circle.EVERYWEEK)) {
                    c = 6;
                    break;
                }
                break;
            case 281966241:
                if (str.equals(Circle.EVERYDAY)) {
                    c = 1;
                    break;
                }
                break;
            case 1091905624:
                if (str.equals(Circle.HOLIDAY)) {
                    c = 4;
                    break;
                }
                break;
            case 1226863719:
                if (str.equals(Circle.WEEKEND)) {
                    c = 5;
                    break;
                }
                break;
            case 1236635661:
                if (str.equals(Circle.MONTHLY)) {
                    c = 7;
                    break;
                }
                break;
            case 1525159659:
                if (str.equals(Circle.WORKDAY)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Alerts.AlertCircleType.ONCE;
            case 1:
                return Alerts.AlertCircleType.EVERYDAY;
            case 2:
                return Alerts.AlertCircleType.WORKDAY;
            case 3:
                return Alerts.AlertCircleType.MONDAY_TO_FRIDAY;
            case 4:
                return Alerts.AlertCircleType.HOLIDAY;
            case 5:
                return Alerts.AlertCircleType.WEEKEND;
            case 6:
                return Alerts.AlertCircleType.WEEKLY;
            case 7:
                return Alerts.AlertCircleType.MONTHLY;
            case '\b':
                return Alerts.AlertCircleType.YEARLY;
            default:
                return Alerts.AlertCircleType.ONCE;
        }
    }

    public static List<AlarmRealmObjectBean> parseAlarmItems(@NonNull List<Template.AlarmItem> list) {
        ArrayList arrayList = new ArrayList();
        for (Template.AlarmItem alarmItem : list) {
            AlarmEntity a = a(alarmItem);
            if (a != null) {
                arrayList.add(new AlarmRealmObjectBean(a));
            }
        }
        return arrayList;
    }
}
